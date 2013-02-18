package es.usc.citius.servando.android.ui;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;
import android.widget.Toast;
import es.usc.citius.servando.R;
import es.usc.citius.servando.android.ServandoPlatformFacade;

public class ServandoService extends Service {

	public static final String NOTIFICATIONS_UPDATE = "es.usc.citius.servando.android.NOTIFICATIONS_UPDATE";

	private static final String DEBUG_TAG = ServandoService.class.getSimpleName();

	// Activities
	private static final String NOTIFICATIONS_ACTIVITY = "es.usc.citius.servando.android.app.activities.NotificationsActivity";
	private static final String HOME_ACTIVITY = "es.usc.citius.servando.android.app.activities.HomeActivity";
	private static final String APPLICATION_PACKAGE = "es.usc.citius.servando.android.app";

	// Notifications ID
	private static final int SERVANDO_ID = "es.usc.citius.servando.android.NOTIFICATION".hashCode();

	private static final boolean WIDGET_UPDATE_ENABLED = false;
	private static NotificationManager notificationMgr;
	private final IBinder mBinder = new ServandoBinder();

	private static boolean isRunning = false;

	@Override
	public void onCreate()
	{
		super.onCreate();
		notificationMgr = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
	}

	@Override
	public int onStartCommand(Intent intent, int flags, int startId)
	{
		Log.d(DEBUG_TAG, "ServandoService started");
		if (!isRunning)
		{
			isRunning = true;
			try
			{
				ServandoPlatformFacade.getInstance().start(this);

			} catch (Exception e)
			{
				e.printStackTrace();
			}
			startForeground();
		}
		return START_STICKY;
	}

	@Override
	public void onDestroy()
	{

		if (isRunning)
		{
			isRunning = false;
			updateWidget(-1);
			stopForeground(true);
			notificationMgr.cancel(SERVANDO_ID);

			if (ServandoPlatformFacade.getInstance().isRunning())
			{
				ServandoPlatformFacade.getInstance().stop(this);
			}

			// UiUtils.showToast("ServandoService destroyed", this);
			Log.d(DEBUG_TAG, "ServandoService destroyed");

		}
		super.onDestroy();

	}

	/**
	 * @see android.app.Service#onBind(Intent)
	 */
	@Override
	public IBinder onBind(Intent intent)
	{
		isRunning = true;
		startForeground();
		return mBinder;
	}

	private void startForeground()
	{
		CharSequence text = "Servando is running";
		// Set the icon, scrolling text and timestamp
		Notification notification = new Notification(R.drawable.ic_servando_notification, text, System.currentTimeMillis());
		// The PendingIntent to launch our activity if the user selects this notification
		Intent i = new Intent();
		i.setClassName(APPLICATION_PACKAGE, HOME_ACTIVITY);
		PendingIntent contentIntent = PendingIntent.getActivity(this, 0, i, 0);
		// Set the info for the views that show in the notification panel.
		notification.setLatestEventInfo(this, "Servando", text, contentIntent);
		startForeground(SERVANDO_ID, notification);
		updateWidget(0);
	}

	public void updateNotifications(int count)
	{

		String ativity = count > 0 ? NOTIFICATIONS_ACTIVITY : HOME_ACTIVITY;
		String rollMessage = count > 0 ? "Servando requires your atention!" : null;
		String description = count > 0 ? count + (count == 1 ? " notification" : " notifications") : null;
		String title = "ServandoPlatform";
		int icon = count > 0 ? R.drawable.ic_servando_notification_alert : R.drawable.ic_servando_notification;

		Intent contentIntent = new Intent();
		contentIntent.setClassName("es.usc.citius.servando.android.app", ativity);
		Notification n = new Notification(icon, rollMessage, System.currentTimeMillis());
		PendingIntent appIntent = PendingIntent.getActivity(this, 0, contentIntent, 0);

		n.setLatestEventInfo(this, title, description, appIntent);
		notificationMgr.notify(SERVANDO_ID, n);

		LocalBroadcastManager.getInstance(this).sendBroadcast(new Intent(NOTIFICATIONS_UPDATE));

		updateWidget(count);
	}

	private void updateWidget(int count)
	{

		if (WIDGET_UPDATE_ENABLED)
		{
			try
			{
				Intent updateWidgetIntent = new Intent();
				updateWidgetIntent.setClassName(APPLICATION_PACKAGE, APPLICATION_PACKAGE + ".widget.UpdateWidgetService");
				updateWidgetIntent.putExtra("notification_count", count);
				updateWidgetIntent.putExtra("next_action_name", "");
				updateWidgetIntent.putExtra("next_action_time", "");
				startService(updateWidgetIntent);

			} catch (Exception e)
			{
				e.printStackTrace();
			}
		}
	}

	public class ServandoBinder extends Binder {
		public ServandoService getService()
		{
			return ServandoService.this;
		}
	}

	public void toast(String msg)
	{
		Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_SHORT).show();
	}

	public static boolean isRunning()
	{
		return isRunning;
	}

	public static void setRunning(boolean running)
	{
		isRunning = running;
	}

}
