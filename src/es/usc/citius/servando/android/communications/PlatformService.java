package es.usc.citius.servando.android.communications;

import java.net.URI;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import es.usc.citius.servando.android.ServandoPlatformFacade;
import es.usc.citius.servando.android.advices.Advice;
import es.usc.citius.servando.android.advices.DailyReport;
import es.usc.citius.servando.android.advices.ServandoAdviceMgr;
import es.usc.citius.servando.android.agenda.PlatformResources;
import es.usc.citius.servando.android.agenda.PlatformResources.Available;
import es.usc.citius.servando.android.agenda.ServandoBackgroundService;
import es.usc.citius.servando.android.logging.ILog;
import es.usc.citius.servando.android.logging.ServandoLoggerFactory;
import es.usc.citius.servando.android.models.protocol.MedicalAction;
import es.usc.citius.servando.android.models.protocol.MedicalActionExecution;
import es.usc.citius.servando.android.models.protocol.MedicalActionPriority;
import es.usc.citius.servando.android.models.protocol.MedicalActionState;
import es.usc.citius.servando.android.models.services.IPlatformService;
import es.usc.citius.servando.android.models.util.ParameterList;

public class PlatformService implements IPlatformService, CommunicableService {

	private static final String id = "SERVANDO";

	private static final ILog log = ServandoLoggerFactory.getLogger(PlatformService.class);

	public static final String DAILY_REPORT = "dailyreport";
	public static final String CHEK_FOR_UPDATES = "checkforupdates";

	private Map<String, MedicalAction> providedActions;
	private Map<String, PlatformResources> resources;

	private static ObjectTransporter transporter;

	@Override
	public String getId()
	{
		return id;
	}

	@Override
	public void onPlatformStarted()
	{
		transporter = ServandoPlatformFacade.getInstance().getServiceObjectTransporter(id);
	}

	@Override
	public void onPlatformStopping()
	{

	}

	@Override
	public List<MedicalAction> getProvidedActions()
	{
		if (providedActions == null)
		{
			providedActions = new HashMap<String, MedicalAction>();
			resources = new HashMap<String, PlatformResources>();

			MedicalAction dailyReport = new MedicalAction(DAILY_REPORT);
			dailyReport.setDisplayName("DR");
			dailyReport.setProvider(this);
			dailyReport.setDescription("DR");

			MedicalAction chek4Updates = new MedicalAction(CHEK_FOR_UPDATES);
			chek4Updates.setDisplayName("UPDATES");
			chek4Updates.setProvider(this);
			chek4Updates.setDescription("UPDATES");

			providedActions.put(dailyReport.getId(), dailyReport);
			resources.put(dailyReport.getId(), PlatformResources.with(Available.NONE));
			providedActions.put(chek4Updates.getId(), chek4Updates);
			resources.put(chek4Updates.getId(), PlatformResources.with(Available.NONE));
		}

		return new ArrayList<MedicalAction>(providedActions.values());
	}

	@Override
	public MedicalActionExecution newExecution(MedicalAction action, PlatformResources neededResources, ParameterList parameters,
			MedicalActionPriority priority, Calendar startDate, long timeWindow)
	{
		if (!providedActions.containsKey(action.getId()))
		{
			throw new IllegalArgumentException("Action " + action.getId() + " is not provided by the service " + this.getId());
		}

		MedicalActionExecution execution = null;

		if (CHEK_FOR_UPDATES.equalsIgnoreCase(action.getId()))
		{
			execution = new CheckForUpdatesAction(providedActions.get(action.getId()), parameters, priority, (GregorianCalendar) startDate,
					timeWindow);
		} else if (DAILY_REPORT.equalsIgnoreCase(action.getId()))
		{
			execution = new DailyReportMedicalActionExection(providedActions.get(action.getId()), parameters, priority,
					(GregorianCalendar) startDate, timeWindow);
		} else
		{
			throw new IllegalArgumentException("Action " + action.getId() + " is not provided by the service " + this.getId());
		}

		// Set execution needed resources
		execution.setResources(resources.get(action.getId()));

		log.debug("Creating new execution for action " + action.getId() + ", provider: "
				+ (execution.getAction().getProvider() != null ? execution.getAction().getProvider().getId() : "null"));

		return execution;
	}

	@Override
	public MedicalActionExecution restoreExecution(MedicalAction action, PlatformResources neededResources, ParameterList parameters,
			MedicalActionPriority priority, Calendar startDate, long timeWindow)
	{
		return newExecution(action, neededResources, parameters, priority, startDate, timeWindow);
	}

	private class DailyReportMedicalActionExection extends MedicalActionExecution {

		public DailyReportMedicalActionExection(MedicalAction medicalAction, ParameterList parameters, MedicalActionPriority priority, GregorianCalendar startDate, long timeWindow)
		{
			super(medicalAction, parameters, priority, startDate, timeWindow);
		}

		@Override
		public void start(Context ctx)
		{
			log.debug("Starting  " + getAction().getId() + ", state: " + state);

			if (state == MedicalActionState.NotStarted)
			{
				state = MedicalActionState.Uncompleted;

				Advice a = DailyReport.getInstance().generateDailyReport();
				if (a != null)
				{
					ServandoAdviceMgr.getInstance().setHomeAdvice(a);
				}

			}

			if (listener != null)
			{
				listener.onStart(this);
			}

			this.finish(ctx);
		}
	}

	private class CheckForUpdatesAction extends MedicalActionExecution {

		public CheckForUpdatesAction(MedicalAction medicalAction, ParameterList parameters, MedicalActionPriority priority, GregorianCalendar startDate, long timeWindow)
		{
			super(medicalAction, parameters, priority, startDate, timeWindow);
		}

		@Override
		public void start(final Context ctx)
		{
			ServandoBackgroundService.$.getInstance().checkForUpdates();
			// new Thread(new Runnable()
			// {
			//
			// @Override
			// public void run()
			// {
			// Integer currVersion;
			// Integer lastVersion;
			//
			// String versionFilePath = StorageModule.getInstance().getBasePath() + "/version";
			//
			// URL url = null;
			// try
			// {
			// url = new URL(ServandoStartConfig.getInstance().get(ServandoStartConfig.CHECK_UPDATES_URL));
			//
			// URLConnection connection = url.openConnection();
			// connection.setConnectTimeout(4000);
			// connection.connect();
			// // download the file
			// InputStream input = new BufferedInputStream(url.openStream());
			// OutputStream output = new FileOutputStream(versionFilePath);
			//
			// byte data[] = new byte[1024];
			// int count;
			//
			// while ((count = input.read(data)) != -1)
			// {
			// output.write(data, 0, count);
			// }
			//
			// output.flush();
			// output.close();
			// input.close();
			//
			// Scanner scanner = new Scanner(new File(versionFilePath));
			//
			// if (scanner.hasNextInt())
			// {
			// lastVersion = scanner.nextInt();
			// currVersion = readVersion(ctx);
			//
			// log.debug("lastVersion: " + lastVersion + ", currentVersion: " + currVersion);
			//
			// if (lastVersion > currVersion || currVersion == -1)
			// {
			// Intent intent = new Intent();
			// intent.setClassName("es.usc.citius.servando.android.app",
			// "es.usc.citius.servando.android.app.UpdateActivity");
			// intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
			// ctx.getApplicationContext().startActivity(intent);
			// }
			// }
			//
			// } catch (Exception e)
			// {
			// log.error("Error in checkforupdates action", e);
			// }
			//
			// finish(ctx);
			// }
			// }).start();

		}

		private int readVersion(Context ctx)
		{
			int version = -1;
			try
			{
				ApplicationInfo ai = ctx.getPackageManager().getApplicationInfo(ctx.getPackageName(), PackageManager.GET_META_DATA);
				Bundle bundle = ai.metaData;
				version = bundle.getInt("version");
			} catch (Exception e)
			{
				log.error("Failed to load meta-data", e);
			}
			return version;
		}
	}

	public static ObjectTransporter getTransporter()
	{
		return transporter;
	}

	@Override
	public URI getServiceRemoteUri()
	{
		return ServandoPlatformFacade.getInstance().getSettings().getServerUri();
	}

	@Override
	public boolean processSend(Object obj, String client)
	{
		return false;
	}

	@Override
	public void processISend(Object obj, String client)
	{

	}

	@Override
	public Object processSendReceive(Object obj, String client)
	{
		return null;
	}

}
