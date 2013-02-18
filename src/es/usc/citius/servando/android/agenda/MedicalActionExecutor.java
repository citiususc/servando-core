package es.usc.citius.servando.android.agenda;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.joda.time.DateTime;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Vibrator;
import es.usc.citius.servando.R;
import es.usc.citius.servando.android.logging.ILog;
import es.usc.citius.servando.android.logging.ServandoLoggerFactory;
import es.usc.citius.servando.android.models.protocol.MedicalActionExecution;
import es.usc.citius.servando.android.settings.StorageModule;

public class MedicalActionExecutor {

	private static List<Integer> notifications = new ArrayList<Integer>();

	/**
	 * Servando paltform logger for this class
	 */
	private static final ILog log = ServandoLoggerFactory.getLogger(MedicalActionExecutor.class);

	private static final String ACTIONS_ACTIVITY = "es.usc.citius.servando.android.app.activities.SwitcherActivity";
	private static final String APPLICATION_PACKAGE = "es.usc.citius.servando.android.app";

	private static int requestCode = 0;

	public synchronized static void showActionReadyNotification(MedicalActionExecution mae, Context ctx)
	{

		log.debug("Showing notification for new action execution (" + mae.getAction().getId() + ") ...");

		int actionId = mae.getUniqueId();
		String actionType = mae.getAction().getId();

		// Notification options
		String rollMessage = "New medical action ready";
		String description = "[" + DateTime.now().toString() + "] Action " + actionId + " can be performed";
		String title = mae.getAction().getDisplayName();
		ArrayList<String> parameters = mae.getParameters().asList();

		// log.debug("Parameters: " + parameters.size());

		int icon = R.drawable.ic_servando_notification;

		// Intent wich starts the medical actions activity
		Intent startActionIntent = new Intent();
		startActionIntent.setClassName(APPLICATION_PACKAGE, ACTIONS_ACTIVITY);
		startActionIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
		startActionIntent.putExtra("action_id", actionId);
		startActionIntent.putExtra("action_type", actionType);

		// notification to show
		Notification n = new Notification(icon, rollMessage, System.currentTimeMillis());

		requestCode = (requestCode + 1) % 10;

		PendingIntent appIntent = PendingIntent.getActivity(ctx, requestCode, startActionIntent, PendingIntent.FLAG_ONE_SHOT);

		n.setLatestEventInfo(ctx, title, description, appIntent);
		// n.vibrate = new long[] { 50, 50, 50, 50, 50, 50, 50 };

		// Get instance of Vibrator from current Context
		Vibrator v = (Vibrator) ctx.getSystemService(Context.VIBRATOR_SERVICE);
		// Vibrate for 300 milliseconds
		v.vibrate(new long[] { 100, 50, 100, 50, 100, 50, 100 }, -1);

		String path = StorageModule.getInstance().getBasePath() + "/sound.ogg";
		n.sound = Uri.parse(path);

		// NotificationMgr.getInstance().ttsNotification("Cruce la rotonda");

		((NotificationManager) ctx.getSystemService(Context.NOTIFICATION_SERVICE)).notify(actionId, n);

		notifications.add(actionId);

		log.debug("Notification ids: " + Arrays.toString(notifications.toArray()));

		try
		{
			Thread.currentThread().sleep(1500);
		} catch (InterruptedException e)
		{
			e.printStackTrace();
		}
	}

	public static void removeActionReadyNotification(MedicalActionExecution mae, Context ctx)
	{
		int id = mae.getUniqueId();
		notifications.remove(new Integer(id));
		((NotificationManager) ctx.getSystemService(Context.NOTIFICATION_SERVICE)).cancel(id);
	}

	public static void removeActionReadyNotification(int id, Context ctx)
	{
		notifications.remove(new Integer(id));
		((NotificationManager) ctx.getSystemService(Context.NOTIFICATION_SERVICE)).cancel(id);
	}

	public static void removeAllNotifications(Context ctx)
	{

		NotificationManager nmgr = ((NotificationManager) ctx.getSystemService(Context.NOTIFICATION_SERVICE));

		for (Integer id : notifications)
		{
			nmgr.cancel(id);
		}

	}

}
