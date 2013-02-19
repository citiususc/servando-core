package es.usc.citius.servando.android;

import java.io.File;
import java.io.IOException;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

import org.apache.log4j.Level;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import es.usc.citius.servando.android.advices.storage.SQLiteAdviceDAO;
import es.usc.citius.servando.android.agenda.ProtocolEngine;
import es.usc.citius.servando.android.agenda.ProtocolEngineServiceBinder;
import es.usc.citius.servando.android.agenda.ProtocolEngineServiceBinder.ProtocolEngineServiceBinderListener;
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
import es.usc.citius.servando.android.models.protocol.MedicalActionMgr;
import es.usc.citius.servando.android.models.protocol.MedicalActionStore;
import es.usc.citius.servando.android.models.services.IPlatformService;
import es.usc.citius.servando.android.models.services.ServiceManager;
import es.usc.citius.servando.android.models.services.StorageAvailableService;
import es.usc.citius.servando.android.settings.ServandoSettings;
import es.usc.citius.servando.android.settings.StorageModule;
import es.usc.citius.servando.android.ui.ServandoService;
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
public class ServandoPlatformFacade implements ProtocolEngineServiceBinderListener {

	private static final String DEBUG_TAG = ServandoPlatformFacade.class.getSimpleName();

	/**
	 * Singleton unique instance
	 */
	private static final ServandoPlatformFacade instance = new ServandoPlatformFacade();
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
	private MedicalActionStore medicalActionStore;

	/**
	 * Indicates whether the platform is initializad or not
	 */
	private boolean started = false;

	/**
	 * HTTP server
	 */
	private WebServer webServer;

	/**
	 * Patient
	 */
	private Patient patient;

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

		medicalActionStore = new MedicalActionStore();

		log.info("ServandoPlatform facade instantiated");
	}

	/**
	 * Static member to obtain the unique instance
	 */
	public static ServandoPlatformFacade getInstance()
	{
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
				medicalActionStore.addServiceActions(service);
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
	 * Start the platform by reading the platform configuration file (settings.xml) and loading the listed services.
	 * 
	 * @param ctx
	 * @throws IOException
	 */
	public synchronized void start(Context ctx) throws Exception
	{
		if (!started)
		{

			patient = loadPatient();

			if (patient != null)
			{
				log.debug("Patient loaded: " + patient.getName());
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

			// start HTTP server if needed
			if (settings.isHttpServerEnabled())
			{
				log.debug("HTTPServer requested...");
				startHttpServer(ctx);
			}

			ProtocolEngineServiceBinder.getInstance().setBindingListener(this);
			ProtocolEngineServiceBinder.getInstance().startAndBindToAppContext(ctx);

			SystemStatusMonitor.getInstance().updateStatus(ctx);
			log.info(SystemStatusMonitor.getInstance().toString());

			SQLiteAdviceDAO.getInstance().initialize(ctx);

			started = true;
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
			if (ServandoService.isRunning())
			{
				ProtocolEngineServiceBinder.getInstance().unbind(ctx);
				ctx.stopService(new Intent(ctx, ServandoService.class));
			}
		}
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
		return ProtocolEngineServiceBinder.getInstance().getProtocolEngine();
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
		SimpleXMLSerializator sxmls = new SimpleXMLSerializator();
		File file = new File(StorageModule.getInstance().getBasePath() + "/" + settings.getPatientFile());
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

	@Override
	public void onBind(final ProtocolEngine engine)
	{
		// load day actions
		Timer t = new Timer();
		t.schedule(new TimerTask()
		{

			@Override
			public void run()
			{
				engine.loadDayActions();

			}
		}, 1000);

		// notify platform ready
		raiseOnReady();
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

}
