package es.usc.citius.servando.android;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Level;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.bluetooth.BluetoothAdapter;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.net.Uri;
import android.os.Environment;
import android.util.Log;
import es.usc.citius.servando.R;
import es.usc.citius.servando.android.advices.storage.SQLiteAdviceDAO;
import es.usc.citius.servando.android.agenda.ProtocolEngine;
import es.usc.citius.servando.android.agenda.ProtocolEngineListener;
import es.usc.citius.servando.android.agenda.ServandoBackgroundService;
import es.usc.citius.servando.android.alerts.AlertMgr;
import es.usc.citius.servando.android.alerts.AlertMsg;
import es.usc.citius.servando.android.alerts.AlertMsg.Builder;
import es.usc.citius.servando.android.alerts.AlertType;
import es.usc.citius.servando.android.alerts.PatientAdviceAlertHandler;
import es.usc.citius.servando.android.alerts.RemoteSendingAlertHandler;
import es.usc.citius.servando.android.communications.CommunicableService;
import es.usc.citius.servando.android.communications.ObjectTransporter;
import es.usc.citius.servando.android.communications.ServandoCommunicationsModule;
import es.usc.citius.servando.android.device.SystemStatusMonitor;
import es.usc.citius.servando.android.httpServer.WebServer;
import es.usc.citius.servando.android.httpServer.WebServerConfig;
import es.usc.citius.servando.android.logging.ILog;
import es.usc.citius.servando.android.logging.Log4JConfig;
import es.usc.citius.servando.android.logging.ServandoLoggerFactory;
import es.usc.citius.servando.android.models.patients.Patient;
import es.usc.citius.servando.android.models.protocol.MedicalAction;
import es.usc.citius.servando.android.models.protocol.MedicalActionExecution;
import es.usc.citius.servando.android.models.protocol.MedicalActionMgr;
import es.usc.citius.servando.android.models.services.IPlatformService;
import es.usc.citius.servando.android.models.services.ServiceManager;
import es.usc.citius.servando.android.models.services.StorageAvailableService;
import es.usc.citius.servando.android.settings.ServandoSettings;
import es.usc.citius.servando.android.settings.StorageModule;
import es.usc.citius.servando.android.sound.SoundHelper;
import es.usc.citius.servando.android.ui.animation.AnimationStore;
import es.usc.citius.servando.android.util.BluetoothUtils;
import es.usc.citius.servando.android.xml.helpers.SimpleXMLSerializator;

/**
 * This class is the main platform facade, implemented as singleton that provides different methods to interact with the
 * platform, like start or stop it. During the platform startup, the services listed in the settings.xml configuration
 * file are loaded, and onPlatformStarted method is called for each of them.
 * 
 * This class provides access to the {@link ServandoCommunicationsModule} too, so that the services wich implement the
 * {@link CommunicableService} interface can request an {@link ObjectTransporter} for its own communication tasks.
 * 
 * @author Ángel Piñeiro
 */
public class ServandoPlatformFacade {

	private static final String DEBUG_TAG = ServandoPlatformFacade.class.getSimpleName();

	public static final String SPLASH_ATIVITY = "es.usc.citius.servando.android.app.activities.SplashActivity";
	public static final String ACTION_ATIVITY = "es.usc.citius.servando.android.app.activities.SwitcherActivity";
	public static final String HOME_ACTIVITY = "es.usc.citius.servando.android.app.activities.PatientHomeActivity";
	public static final String APPLICATION_PACKAGE = "es.usc.citius.servando.android.app";
	public static final String NOTIFICATIONS_UPDATE = "es.usc.citius.servando.android.NOTIFICATIONS_UPDATE";
	public static final int SERVANDO_ID = "es.usc.citius.servando.android.NOTIFICATION".hashCode();

	/**
	 * Singleton unique instance
	 */
	private static ServandoPlatformFacade instance;
	/**
	 * ServandoPlatform faccede Logger
	 */
	private final ILog log;

	/**
	 * Reference to a service manager
	 */
	private final ServiceManager serviceManager;

	/**
	 * Platform settings
	 */
	private ServandoSettings settings;
	/**
	 * Reference to the {@link ServandoCommunicationsModule}
	 */
	private final ServandoCommunicationsModule communicationsModule;
	/**
	 * Reference to the {@link StorageModule}
	 */
	private final StorageModule storageModule;
	/**
	 *
	 */
	private static Object lock = new Object();

	/**
	 * Indicates whether the platform is initializad or not
	 */
	private static boolean started = false;

	/**
	 * HTTP server
	 */
	private WebServer webServer;

	/**
	 * Patient
	 */
	private Patient patient;

	/**
	 * Alert manager
	 */
	private AlertMgr alertMgr;

	/**
	 * PlatformFacadeListeners
	 */
	private List<PlatformFacadeListener> listeners;

	/**
	 * Private constructor to avoid multiple instances
	 */
	private ServandoPlatformFacade()
	{
		// Firs of all, instantiate storage module to initialize platform paths
		storageModule = StorageModule.getInstance();
		// initialize logs
		initializeLoggingEngine();
		// get a logger for this class
		log = ServandoLoggerFactory.getLogger(ServandoPlatformFacade.class);

		log.debug("Logging engine initialized");

		try
		{	// load the platform settings
			settings = storageModule.getSettings();
			logSerializable(settings);

		} catch (IOException io)
		{
			log.error("Error reading platform settings.", io);
		}

		serviceManager = ServiceManager.getInstance();
		communicationsModule = ServandoCommunicationsModule.getInstance();

		alertMgr = new AlertMgr();
		alertMgr.registerHandler(new RemoteSendingAlertHandler());
		alertMgr.registerHandler(new PatientAdviceAlertHandler());

		// medicalActionStore = new MedicalActionStore();

		log.info("ServandoPlatform facade instantiated. Started: " + started);
	}

	/**
	 * Static member to obtain the unique instance
	 */
	public static ServandoPlatformFacade getInstance()
	{
		synchronized (lock)
		{
			if (instance == null)
			{
				instance = new ServandoPlatformFacade();
			}
		}
		return instance;
	}

	/**
	 * Registers a service in the platform, and, if it is a {@link CommunicableService}, in the
	 * {@link ServandoCommunicationsModule}
	 * 
	 * @param service the service to register
	 */
	public void registerService(IPlatformService service)
	{
		// register communicable services
		if (service instanceof CommunicableService)
		{
			communicationsModule.registerService((CommunicableService) service);
		}
		// register services with storage access
		if (service instanceof StorageAvailableService)
		{
			storageModule.registerService((StorageAvailableService) service);
		}

		// get service actions an add them to the medical action store
		List<MedicalAction> serviceActions = service.getProvidedActions();

		// if service provides any action
		if (serviceActions != null && !serviceActions.isEmpty())
		{
			for (MedicalAction a : serviceActions)
			{
				try
				{
					MedicalActionMgr.getInstance().addMedicalAction(a);
				} catch (Exception e)
				{
					// log
				}
			}

			try
			{	// add service actions
				// medicalActionStore.addServiceActions(service);
			} catch (Exception e)
			{
				log.error("An error ocurred while loading " + service.getId() + " actions.", e);
			}
		}
	}

	/**
	 * Unregisters a service from the platform
	 * 
	 * @param service the service to register
	 */
	public void unregisterService(IPlatformService service)
	{
		// register communicable services
		if (service instanceof CommunicableService)
		{
			communicationsModule.unregisterService((CommunicableService) service);
		}
		// register services with storage access
		if (service instanceof StorageAvailableService)
		{
			storageModule.unregisterService((StorageAvailableService) service);
		}
		ServiceManager.getInstance().unregisterService(service);
	}

	/**
	 * Get the {@link ObjectTransporter} of a registered service
	 * 
	 * @param serviceId The service id
	 * @return An Object Transporter ready for use
	 */
	public ObjectTransporter getServiceObjectTransporter(String serviceId)
	{
		return communicationsModule.getServiceObjectTransporter(serviceId);
	}

	/**
	 * Get the {@link ObjectTransporter} of a registered service
	 * 
	 * @param serviceId The service id
	 * @return An Object Transporter ready for use
	 */
	// public ObjectTransporter createObjectTransporter(String id)
	// {
	//
	// }

	/**
	 * Start the platform by reading the platform configuration file (settings.xml) and loading the listed services.
	 * 
	 * @param ctx
	 * @throws IOException
	 */
	public synchronized void start(Context ctx, boolean force) throws Exception
	{
		log.debug("Start called [started: " + started + ", force: " + force + "]");

		if (!started || force)
		{
			log.debug("Starting platform facade...");


			patient = loadPatient();

			if (patient != null)
			{
				log.debug("Patient loaded: " + patient.getName());
				SharedPreferences prefs = ctx.getSharedPreferences("servando", Context.MODE_PRIVATE);
				prefs.edit().putString("patient_id", patient.getClinicalHistoryNumber()).commit();
			}

			Collection<IPlatformService> loadedServices = serviceManager.loadServices(ctx);
			// register loaded services
			for (IPlatformService s : loadedServices)
			{
				registerService(s);
			}

			// notify platform start event
			for (IPlatformService s : loadedServices)
			{
				s.onPlatformStarted();
			}

			// for (IPlatformService s : loadedServices)
			// {
			// for (MedicalAction a : s.getProvidedActions())
			// {
			// log.debug("Service " + s.getId() + ": " + a.getId());
			// }
			// }

			// start HTTP server if needed
			if (settings.isHttpServerEnabled())
			{
				log.debug("HTTPServer starting...");
				startHttpServer(ctx);
			}

			AnimationStore.getInstance().initialize(ctx);
			SoundHelper.initSounds(ctx);
			ProtocolEngine.getInstance().addProtocolListener(new AgendaListener());
			SQLiteAdviceDAO.getInstance().initialize(ctx);


			SystemStatusMonitor.getInstance().updateStatus(ctx);
			log.info(SystemStatusMonitor.getInstance().toString());

			if (BluetoothUtils.getInstance().getAdapter() == null)
			{
				BluetoothUtils.getInstance().setAdapter(BluetoothAdapter.getDefaultAdapter());
			}

			log.debug("Platform facade started!");

			doInitialLogs();

			sendStartEvent();
			// if (!ProtocolEngine.getInstance().isStarted())
			// {
			// ctx.startService(new Intent(ctx, ServandoBackgroundService.class));
			// } else
			// {
			raiseOnReady();
			// }


			started = true;
		}
	}

	private void sendStartEvent()
	{
		log.debug("Sending start alert...");
		Builder builder = new AlertMsg.Builder();
		AlertMsg a = builder.setType(AlertType.SYSTEM_EVENT).setDisplayName("Inicio").setDescription("Servando se ha iniciado").create();
		// uncomment for sending
		ServandoPlatformFacade.getInstance().alert(a);

	}

	private void doInitialLogs()
	{
		logExternalStorageAvailability();
		log.debug("Base path: " + StorageModule.getInstance().getBasePath());
		log.debug("Settings path: " + StorageModule.getInstance().getSettingsPath());
		log.debug("Client VPN ip: " + settings.getVpnClient());
		log.debug("Server URL: " + settings.getServerUrl());
		log.debug("Communications enabled: " + settings.isCommunicationsModuleEnabled());

		log.debug("== Patient =============================");
		logSerializableToServandoLog(getPatient());
		log.debug("== END Patient =========================");
	}

	/**
	 * Log external storage available availability
	 */
	public void logExternalStorageAvailability()
	{
		// get external media state
		String state = Environment.getExternalStorageState();

		if (Environment.MEDIA_MOUNTED.equals(state))
		{
			log.debug("External storage is available and writeable");
		} else if (Environment.MEDIA_MOUNTED_READ_ONLY.equals(state))
		{
			log.debug("External storage available but not writeable");
		} else
		{
			log.debug("External storage unavailable");
		}
	}

	/**
	 * Stops the platform
	 * 
	 * @param ctx
	 */
	public synchronized void stop(Context ctx)
	{
		// if platform is started
		if (started)
		{
			unrequireUserAttention(ctx);
			ProtocolEngine.getInstance().stop();
			started = false;
			// stop http server
			stopHttpServer();
			// notify stop to services
			for (IPlatformService service : serviceManager.getRegisteredServicesList())
			{
				service.onPlatformStopping();
				unregisterService(service);
			}
			// stop servando background service
			ctx.stopService(new Intent(ctx, ServandoBackgroundService.class));
		}
	}

	/**
	 * Send alert
	 */
	public void alert(AlertMsg alert)
	{
		alertMgr.alert(alert);
	}

	/**
	 * Gets a Map with the registered services
	 * 
	 * @return
	 */
	public Map<String, IPlatformService> getRegisteredServices()
	{
		return serviceManager.getRegisteredServices();
	}

	private void initializeLoggingEngine()
	{
		// logs initialization
		String logsFilename = StorageModule.getInstance().getPlatformLogsPath() + "/" + "servando.log";
		Log4JConfig.initialize(logsFilename, Level.ALL, false, true);

	}

	private void startHttpServer(Context ctx)
	{
		// server root directory
		File serverRoot = new File(storageModule.getBasePath());
		// Web server config
		WebServerConfig cfg = new WebServerConfig.Builder(ctx, serverRoot).fileServerPath("servando").showHiddenFiles(false).build();
		webServer = new WebServer(cfg);
		webServer.startServer();
	}

	/**
	 * Stops the http server
	 */
	private void stopHttpServer()
	{
		// stop http server if needed
		if (webServer != null)
		{
			webServer.stopServer();
		}
	}

	/**
	 * @return the settings
	 */
	public ServandoSettings getSettings()
	{
		return settings;
	}

	/**
	 * @return the protocol engine reference
	 */
	public ProtocolEngine getProtocolEngine()
	{
		// TODO return ProtocolEngineServiceBinder.getInstance().getProtocolEngine();
		return ProtocolEngine.getInstance();
	}

	/**
	 * @return the protocol engine reference
	 */
	public Patient getPatient()
	{
		return patient;
	}

	/**
	 * Indicates whether the platform is running or not
	 */
	public boolean isRunning()
	{
		return started;
	}

	public void saveSettings() throws Exception
	{
		storageModule.saveSettings(settings);
	}

	/**
	 * Load patient from file defined in {@link ServandoSettings#getPatientFile()} file
	 * 
	 * @throws Exception
	 */
	public Patient loadPatient() throws Exception
	{

		String patientFile = StorageModule.getInstance().getBasePath() + "/" + settings.getPatientFile();
		log.debug("Loading patient from " + patientFile);
		SimpleXMLSerializator sxmls = new SimpleXMLSerializator();
		File file = new File(patientFile);
		return (Patient) sxmls.deserialize(file, Patient.class);

	}

	/**
	 * Load patient from file defined in {@link ServandoSettings#getPatientFile()} file
	 * 
	 * @throws Exception
	 */
	public void savePatient() throws Exception
	{
		SimpleXMLSerializator sxmls = new SimpleXMLSerializator();
		File file = new File(StorageModule.getInstance().getBasePath() + "/" + settings.getPatientFile());
		sxmls.serialize(patient, file);

	}

	/**
	 * TODO: Move to utility class
	 * 
	 * @param o
	 */
	public void logSerializable(Object o)
	{
		SimpleXMLSerializator xmls = new SimpleXMLSerializator();
		StringWriter writter = new StringWriter();
		try
		{
			xmls.getSerializer().write(o, writter);
			Log.d(DEBUG_TAG, writter.getBuffer().toString());
		} catch (Exception e)
		{
			Log.e(DEBUG_TAG, "Error serializating object", e);
		}
	}

	public void logSerializableToServandoLog(Object o)
	{
		SimpleXMLSerializator xmls = new SimpleXMLSerializator();
		StringWriter writter = new StringWriter();
		try
		{
			xmls.getSerializer().write(o, writter);
			log.debug(writter.getBuffer().toString());
		} catch (Exception e)
		{
			log.error("Error serializating object", e);
		}
	}


	private void raiseOnReady()
	{
		if (listeners != null)
		{
			for (PlatformFacadeListener l : listeners)
			{
				l.onReady();
			}
		}
	}

	/**
	 * @param listeners the listeners to set
	 */
	public void addListener(PlatformFacadeListener l)
	{
		if (listeners == null)
		{
			listeners = new ArrayList<ServandoPlatformFacade.PlatformFacadeListener>();
		}
		listeners.add(l);
	}

	public interface PlatformFacadeListener {
		void onReady();
	}

	public Resources getResources()
	{
		return ServandoBackgroundService.$.getInstance().getResources();
	}

	public class AgendaListener implements ProtocolEngineListener {

		@Override
		public void onExecutionStart(MedicalActionExecution target)
		{
			// TODO Auto-generated method stub

		}

		@Override
		public void onExecutionAbort(MedicalActionExecution target)
		{
			// TODO Auto-generated method stub

		}

		@Override
		public void onExecutionFinish(MedicalActionExecution target)
		{
			// TODO Auto-generated method stub

		}

		@Override
		public void onLoadDayActions()
		{
			// TODO Auto-generated method stub
		}

		@Override
		public void onProtocolChanged()
		{
			// TODO Auto-generated method stub
		}

		@Override
		public void onProtocolEngineStart()
		{
			raiseOnReady();
		}

		@Override
		public void onReminder(long minutes)
		{
			// TODO Auto-generated method stub

		}
	}

	public void requireUserAtention(Context ctx)
	{
		// PlatformService.getTransporter().send(new Ping());

		int icon = R.drawable.ic_servando_notification;
		// Create intent to open patient activity
		Intent contentIntent = new Intent();
		contentIntent.setClassName("es.usc.citius.servando.android.app", SPLASH_ATIVITY);
		// Create pending intent
		PendingIntent appIntent = PendingIntent.getActivity(ctx, 0, contentIntent, PendingIntent.FLAG_CANCEL_CURRENT);
		// Create notification
		Notification n = new Notification(icon, "Servando requires your attention", System.currentTimeMillis());
		n.flags = Notification.FLAG_SHOW_LIGHTS;
		n.ledOnMS = 2000;
		n.ledOffMS = 4000;
		n.ledARGB = 0xFF2072b9;
		n.setLatestEventInfo(ctx, "Servando", "Servando requires your attention.", appIntent);
		n.defaults |= Notification.DEFAULT_VIBRATE;
		n.defaults |= Notification.DEFAULT_SOUND;

		// candel previous notification if any
		unrequireUserAttention(ctx);
		// Show notification
		((NotificationManager) ctx.getSystemService(Context.NOTIFICATION_SERVICE)).notify(SERVANDO_ID, n);
		// play sound
	}

	public void requireUserAtention(Context ctx, Uri sound)
	{
		// PlatformService.getTransporter().send(new Ping());

		int icon = R.drawable.ic_servando_notification;
		// Create intent to open patient activity
		Intent contentIntent = new Intent();
		contentIntent.setClassName("es.usc.citius.servando.android.app", SPLASH_ATIVITY);
		// Create pending intent
		PendingIntent appIntent = PendingIntent.getActivity(ctx, 0, contentIntent, PendingIntent.FLAG_CANCEL_CURRENT);
		// Create notification
		Notification n = new Notification(icon, "Servando requires your attention", System.currentTimeMillis());
		n.flags = Notification.FLAG_SHOW_LIGHTS;
		n.ledOnMS = 2000;
		n.ledOffMS = 4000;
		n.ledARGB = 0xFF2072b9;
		n.setLatestEventInfo(ctx, "Servando", "Servando requires your attention.", appIntent);
		n.defaults = Notification.DEFAULT_VIBRATE;
		// n.defaults |= Notification.DEFAULT_SOUND; // comment for sound
		n.sound = sound;
		// candel previous notification if any
		unrequireUserAttention(ctx);
		// Show notification
		((NotificationManager) ctx.getSystemService(Context.NOTIFICATION_SERVICE)).notify(SERVANDO_ID, n);
		// play sound

	}

	public void unrequireUserAttention(Context ctx)
	{
		((NotificationManager) ctx.getSystemService(Context.NOTIFICATION_SERVICE)).cancel(SERVANDO_ID);
	}

	public static boolean isStarted()
	{
		// ServandoBackgroundService.$.getInstance();
		return started;
	}

	public String version(Context ctx)
	{
		int resId = getResources().getIdentifier("version_code", "string", ServandoPlatformFacade.APPLICATION_PACKAGE);
		return ctx.getString(resId);
	}

	public String readVersionNotes()
	{
		String versionNotes = "";
		try
		{
			int resId = getResources().getIdentifier("version_notes", "raw", ServandoPlatformFacade.APPLICATION_PACKAGE);
			InputStream in_s = getResources().openRawResource(resId);
			byte[] b = new byte[in_s.available()];
			in_s.read(b);
			versionNotes = new String(b);

		} catch (Exception e)
		{
			log.error("Could not read version notes from raw file");
			versionNotes = "";
		}

		return versionNotes;
	}

}
