package es.usc.citius.servando.android.agenda;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.net.URLConnection;
import java.util.Scanner;

import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.os.IBinder;
import android.os.PowerManager;
import android.os.PowerManager.WakeLock;
import android.util.Log;
import es.usc.citius.servando.android.ServandoPlatformFacade;
import es.usc.citius.servando.android.logging.ILog;
import es.usc.citius.servando.android.logging.ServandoLoggerFactory;
import es.usc.citius.servando.android.models.protocol.MedicalAction;
import es.usc.citius.servando.android.models.protocol.MedicalActionMgr;
import es.usc.citius.servando.android.models.services.IPlatformService;
import es.usc.citius.servando.android.settings.ServandoStartConfig;
import es.usc.citius.servando.android.settings.StorageModule;

public class ServandoBackgroundService extends Service {

	private String version_notes_file = "version_notes";

	private ILog log = ServandoLoggerFactory.getLogger(ServandoBackgroundService.class);

	public static int RTC_WAKE_UP = 192837;
	public static int DONT_SLEEP = 692457;

	private boolean started = false;

	private static ProtocolEngine agenda;
	private static ServandoPlatformFacade facade;

	private boolean enableWakeLoks = false;

	PowerManager mgr;
	WakeLock wakeLock;
	BroadcastReceiver wifiReceiver;

	@Override
	public IBinder onBind(Intent intent)
	{
		// do not bind
		return null;
	}

	@Override
	public void onCreate()
	{
		Log.v("AgendaService", "AgendaService Created");
		mgr = (PowerManager) this.getSystemService(Context.POWER_SERVICE);
		wakeLock = mgr.newWakeLock(PowerManager.PARTIAL_WAKE_LOCK, "AgendaWakeLock");
	}

	@Override
	public int onStartCommand(Intent intent, int flags, int startId)
	{
		Log.v("AgendaService", "ServandoBackgroundService -- onStartCommand()");

		if (facade == null)
		{
			log.debug("AgendaService, AgendaService was not started, starting...");

			try
			{
				$.setInstance(this);

				ServandoPlatformFacade.getInstance().start(this.getApplicationContext(), true);
				facade = ServandoPlatformFacade.getInstance();
				loadServices();
				agenda = ProtocolEngine.getInstance();
				agenda.start();

				IntentFilter intentFilter = new IntentFilter();
				intentFilter.addAction(WifiManager.SUPPLICANT_CONNECTION_CHANGE_ACTION);
				wifiReceiver = new WifiConnectionReceiver();
				registerReceiver(wifiReceiver, intentFilter);

				checkForUpdates();

			} catch (Exception e)
			{
				log.error("Error OnStartCommand", e);
			}

		} else
		{
			log.debug("AgendaService", "AgendaService was started. Ignoring...");
		}
		// We want this service to continue running until it is explicitly stopped, so return sticky.
		return START_STICKY;
	}

	@Override
	public void onDestroy()
	{
		if (wifiReceiver != null)
		{
			unregisterReceiver(wifiReceiver);
		}
		facade = null;
		Log.v("AgendaService", "AgendaService Destroyed");
	}

	public void acquireWakeLock()
	{
		if (!wakeLock.isHeld())
		{
			wakeLock.acquire();
			Log.d("AgendaService", "WakeLock acquired!");
		}
	}

	public void releaseWakeLock()
	{
		if (wakeLock.isHeld())
		{
			wakeLock.release();
			Log.d("AgendaService", "WakeLock released!");
		}
	}

	private void loadServices()
	{
		for (IPlatformService service : ServandoPlatformFacade.getInstance().getRegisteredServices().values())
		{
			for (MedicalAction a : service.getProvidedActions())
			{
				MedicalActionMgr.getInstance().addMedicalAction(a);
			}
		}
	}

	private class WifiConnectionReceiver extends BroadcastReceiver {

		@Override
		public void onReceive(Context context, Intent intent)
		{

			final String action = intent.getAction();
			if (action.equals(WifiManager.SUPPLICANT_CONNECTION_CHANGE_ACTION))
			{
				if (intent.getBooleanExtra(WifiManager.EXTRA_SUPPLICANT_CONNECTED, false))
				{
					// check for updates on wifi

				} else
				{
					// wifi connection was lost
				}
			}
		}
	}

	@Deprecated
	public void checkForUpdatesOld()
	{

		new Thread(new Runnable()
		{

			@Override
			public void run()
			{
				acquireWakeLock();

				Integer currVersion;
				Integer lastVersion;

				String versionFilePath = StorageModule.getInstance().getBasePath() + "/version";

				URL url = null;
				try
				{
					url = new URL(ServandoStartConfig.getInstance().get(ServandoStartConfig.CHECK_UPDATES_URL));

					URLConnection connection = url.openConnection();
					connection.setConnectTimeout(4000);
					connection.connect();
					// download the file
					InputStream input = new BufferedInputStream(url.openStream());
					OutputStream output = new FileOutputStream(versionFilePath);

					byte data[] = new byte[1024];
					int count;

					while ((count = input.read(data)) != -1)
					{
						output.write(data, 0, count);
					}

					output.flush();
					output.close();
					input.close();

					Scanner scanner = new Scanner(new File(versionFilePath));

					if (scanner.hasNextInt())
					{
						lastVersion = scanner.nextInt();
						currVersion = readVersion(getApplication());

						log.debug("lastVersion: " + lastVersion + ", currentVersion: " + currVersion);

						if (lastVersion > currVersion || currVersion == -1)
						{
							Intent intent = new Intent();
							intent.setClassName("es.usc.citius.servando.android.app", "es.usc.citius.servando.android.app.UpdateActivity");
							intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
							intent.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
							getApplicationContext().getApplicationContext().startActivity(intent);
						}
					}

				} catch (Exception e)
				{
					log.error("Error in checkforupdates action", e);
				} finally
				{
					releaseWakeLock();
				}
			}
		}).start();

	}

	public void checkForUpdates()
	{

		new Thread(new Runnable()
		{

			@Override
			public void run()
			{
				acquireWakeLock();

				File tmpVersion = null;
				URL url = null;
				try
				{
					tmpVersion = File.createTempFile("version", "properties");
					url = new URL(ServandoStartConfig.getInstance().get(ServandoStartConfig.CHECK_UPDATES_URL));

					URLConnection connection = url.openConnection();
					connection.setConnectTimeout(4000);
					connection.connect();
					// download the file
					InputStream input = new BufferedInputStream(url.openStream());
					OutputStream output = new FileOutputStream(tmpVersion);

					byte data[] = new byte[1024];
					int count;

					while ((count = input.read(data)) != -1)
					{
						output.write(data, 0, count);
					}

					output.flush();
					output.close();
					input.close();

					Scanner scanner = new Scanner(tmpVersion);
					String version = ServandoPlatformFacade.getInstance().version(ServandoBackgroundService.this);
					String lastVersionCode = "";

					if (scanner.hasNextLine())
					{
						lastVersionCode = scanner.nextLine();
					}

					log.debug("Checking for updates: version-" + version + ", lastVersion-" + lastVersionCode);

					if (lastVersionCode.compareTo(version) > 0)
					{
						startUpdateActivity();
					}

				} catch (Exception e)
				{
					log.error("Error in checkforupdates action", e);
				} finally
				{
					releaseWakeLock();
				}
			}
		}).start();

	}

	void startUpdateActivity()
	{
		Intent intent = new Intent();
		intent.setClassName("es.usc.citius.servando.android.app", "es.usc.citius.servando.android.app.UpdateActivity");
		intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		intent.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
		getApplicationContext().getApplicationContext().startActivity(intent);
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

	public static class $ {

		private static ServandoBackgroundService instance;

		public static ServandoBackgroundService getInstance()
		{
			return instance;
		}

		public static void setInstance(ServandoBackgroundService agendaService)
		{
			instance = agendaService;
		}
	}
}
