package es.usc.citius.servando.android.notifications;

import java.util.ArrayList;
import java.util.List;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import es.usc.citius.servando.R;

public class StatusBarNotificator {

	public static final String FALLBACK_ACTIVITY = "es.usc.citius.servando.android.app.activities.SplashActivity";
	public static final int SERVANDO_ID = "es.usc.citius.servando.android.NOTIFICATION".hashCode();

	private static List<Integer> notificationIds = new ArrayList<Integer>();

	public static void notifyDefault(Context ctx)
	{
		int icon = R.drawable.ic_servando_notification;
		// Create intent to open patient activity
		Intent contentIntent = new Intent();
		contentIntent.setClassName("es.usc.citius.servando.android.app", FALLBACK_ACTIVITY);
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
		// Show notification
		((NotificationManager) ctx.getSystemService(Context.NOTIFICATION_SERVICE)).notify(SERVANDO_ID, n);
	}

	public static void notifyWithSound(Context ctx, Uri sound)
	{
		int icon = R.drawable.ic_servando_notification;
		// Create intent to open patient activity
		Intent contentIntent = new Intent();
		contentIntent.setClassName("es.usc.citius.servando.android.app", FALLBACK_ACTIVITY);
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
		n.sound = sound;

		// Show notification
		((NotificationManager) ctx.getSystemService(Context.NOTIFICATION_SERVICE)).notify(SERVANDO_ID, n);
	}

	public static void notifyDefaultWithMessage(Context ctx, String ticker, String content)
	{
		int icon = R.drawable.ic_servando_notification;
		// Create intent to open patient activity
		Intent contentIntent = new Intent();
		contentIntent.setClassName("es.usc.citius.servando.android.app", FALLBACK_ACTIVITY);
		// Create pending intent
		PendingIntent appIntent = PendingIntent.getActivity(ctx, 0, contentIntent, PendingIntent.FLAG_CANCEL_CURRENT);
		// Create notification
		Notification n = new Notification(icon, ticker, System.currentTimeMillis());
		n.flags = Notification.FLAG_SHOW_LIGHTS;
		n.ledOnMS = 2000;
		n.ledOffMS = 4000;
		n.ledARGB = 0xFF2072b9;
		n.setLatestEventInfo(ctx, "Servando", content, appIntent);
		n.defaults = Notification.DEFAULT_VIBRATE;
		n.defaults |= Notification.DEFAULT_SOUND;
		// Show notification
		((NotificationManager) ctx.getSystemService(Context.NOTIFICATION_SERVICE)).notify(SERVANDO_ID, n);
	}

	/**
	 * Show a notification with the given parameters
	 * 
	 * @param ctx
	 * @param notificationId
	 * @param ticker
	 * @param content
	 */
	public static void notifyWithMessage(Context ctx, int notificationId, String ticker, String content)
	{
		int icon = R.drawable.ic_servando_notification;
		// Create intent to open patient activity
		Intent contentIntent = new Intent();
		contentIntent.setClassName("es.usc.citius.servando.android.app", FALLBACK_ACTIVITY);
		// Create pending intent
		PendingIntent appIntent = PendingIntent.getActivity(ctx, 0, contentIntent, PendingIntent.FLAG_CANCEL_CURRENT);
		// Create notification
		Notification n = new Notification(icon, ticker, System.currentTimeMillis());
		n.flags = Notification.FLAG_SHOW_LIGHTS;
		n.ledOnMS = 2000;
		n.ledOffMS = 4000;
		n.ledARGB = 0xFF2072b9;
		n.setLatestEventInfo(ctx, "Servando", content, appIntent);
		n.defaults = Notification.DEFAULT_VIBRATE;
		n.defaults |= Notification.DEFAULT_SOUND;
		// Show notification
		((NotificationManager) ctx.getSystemService(Context.NOTIFICATION_SERVICE)).notify(notificationId, n);

		Integer oid = Integer.valueOf(notificationId);
		if (!notificationIds.contains(oid))
		{
			notificationIds.add(oid);
		}

	}

	/**
	 * Cancel default notification
	 * 
	 * @param ctx
	 */
	public static void cancelDefault(Context ctx)
	{
		((NotificationManager) ctx.getSystemService(Context.NOTIFICATION_SERVICE)).cancel(SERVANDO_ID);
	}

	/**
	 * Cancel all notifications shown by this class before
	 * 
	 * @param ctx
	 */
	public static void cancelAll(Context ctx)
	{
		for (Integer id : notificationIds)
		{
			((NotificationManager) ctx.getSystemService(Context.NOTIFICATION_SERVICE)).cancel(id.intValue());
		}
	}

	/**
	 * Cancel notification specified by id
	 * 
	 * @param ctx
	 */
	public static void cancel(Context ctx, int id)
	{
		((NotificationManager) ctx.getSystemService(Context.NOTIFICATION_SERVICE)).cancel(id);
	}

}
