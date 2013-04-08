package es.usc.citius.servando.android.agenda;

import android.content.Context;
import es.usc.citius.servando.android.ServandoPlatformFacade;
import es.usc.citius.servando.android.models.protocol.MedicalActionExecution;
import es.usc.citius.servando.android.sound.SoundHelper;

public class MedicalActionExecutor {
	//
	// private static List<Integer> notifications = new ArrayList<Integer>();
	//
	// /**
	// * Servando paltform logger for this class
	// */
	// private static final ILog log = ServandoLoggerFactory.getLogger(MedicalActionExecutor.class);
	//
	// private static final String ACTIONS_ACTIVITY =
	// "es.usc.citius.servando.android.app.activities.PatientHomeActivity";
	// private static final String APPLICATION_PACKAGE = "es.usc.citius.servando.android.app";
	//
	// private static int requestCode = 0;
	//
	// private static int PENDING_ACTIONS_CODE = "PENDING_ACTIONS_CODE".hashCode();
	// //
	// // public synchronized static void showActionReadyNotification(MedicalActionExecution mae, Context ctx)
	// // {
	// //
	// // // Notification options
	// // String rollMessage = "Action " + mae.getAction().getDisplayName() + " ready.";
	// // String description = "There are medical actions pending";
	// //
	// // int icon = R.drawable.ic_servando_notification;
	// //
	// // // Intent wich starts the medical actions activity
	// // Intent startActionIntent = new Intent();
	// // startActionIntent.setClassName(APPLICATION_PACKAGE, ACTIONS_ACTIVITY);
	// // startActionIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
	// // startActionIntent.putExtra("from_notification", true);
	// //
	// // // notification to show
	// // Notification n = new Notification(icon, rollMessage, System.currentTimeMillis());
	// // PendingIntent appIntent = PendingIntent.getActivity(ctx, PENDING_ACTIONS_CODE, startActionIntent,
	// PendingIntent.FLAG_ONE_SHOT);
	// // n.setLatestEventInfo(ctx, "Servando", description, appIntent);
	// // Vibrator v = (Vibrator) ctx.getSystemService(Context.VIBRATOR_SERVICE);
	// // v.vibrate(new long[] { 100, 50, 100, 50, 100, 50, 100 }, -1);
	// //
	// // String path = StorageModule.getInstance().getBasePath() + "/sound.ogg";
	// // n.sound = Uri.parse(path);
	// // ((NotificationManager) ctx.getSystemService(Context.NOTIFICATION_SERVICE)).notify(PENDING_ACTIONS_CODE, n);
	// //
	// // }
	// //
	// // public static void removeActionsReadyNotification(Context ctx)
	// // {
	// // ((NotificationManager) ctx.getSystemService(Context.NOTIFICATION_SERVICE)).cancel(PENDING_ACTIONS_CODE);
	// // }
	// //
	// // public synchronized static void showActionReadyNotification_(MedicalActionExecution mae, Context ctx)
	// // {
	// //
	// // log.debug("Showing notification for new action execution (" + mae.getAction().getId() + ") ...");
	// //
	// // int actionId = mae.getUniqueId();
	// // String actionType = mae.getAction().getId();
	// //
	// // // Notification options
	// // String rollMessage = "New medical action ready";
	// // String description = "[" + DateTime.now().toString() + "] Action " + actionId + " can be performed";
	// // String title = mae.getAction().getDisplayName();
	// // ArrayList<String> parameters = mae.getParameters().asList();
	// //
	// // // log.debug("Parameters: " + parameters.size());
	// //
	// // int icon = R.drawable.ic_servando_notification;
	// //
	// // // Intent wich starts the medical actions activity
	// // Intent startActionIntent = new Intent();
	// // startActionIntent.setClassName(APPLICATION_PACKAGE, ACTIONS_ACTIVITY);
	// // startActionIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
	// // startActionIntent.putExtra("action_id", actionId);
	// // startActionIntent.putExtra("action_type", actionType);
	// //
	// // // notification to show
	// // Notification n = new Notification(icon, rollMessage, System.currentTimeMillis());
	// //
	// // requestCode = (requestCode + 1) % 10;
	// //
	// // PendingIntent appIntent = PendingIntent.getActivity(ctx, requestCode, startActionIntent,
	// PendingIntent.FLAG_ONE_SHOT);
	// //
	// // n.setLatestEventInfo(ctx, title, description, appIntent);
	// // // n.vibrate = new long[] { 50, 50, 50, 50, 50, 50, 50 };
	// //
	// // // Get instance of Vibrator from current Context
	// // Vibrator v = (Vibrator) ctx.getSystemService(Context.VIBRATOR_SERVICE);
	// // // Vibrate for 300 milliseconds
	// // v.vibrate(new long[] { 100, 50, 100, 50, 100, 50, 100 }, -1);
	// //
	// // String path = StorageModule.getInstance().getBasePath() + "/sound.ogg";
	// // n.sound = Uri.parse(path);
	// //
	// // // NotificationMgr.getInstance().ttsNotification("Cruce la rotonda");
	// //
	// // ((NotificationManager) ctx.getSystemService(Context.NOTIFICATION_SERVICE)).notify(actionId, n);
	// //
	// // notifications.add(actionId);
	// //
	// // log.debug("Notification ids: " + Arrays.toString(notifications.toArray()));
	// //
	// // try
	// // {
	// // Thread.currentThread().sleep(1000);
	// // } catch (InterruptedException e)
	// // {
	// // e.printStackTrace();
	// // }
	// // }
	// //
	// // public static void removeActionReadyNotification(MedicalActionExecution mae, Context ctx)
	// // {
	// // int id = mae.getUniqueId();
	// // notifications.remove(new Integer(id));
	// // ((NotificationManager) ctx.getSystemService(Context.NOTIFICATION_SERVICE)).cancel(id);
	// // }
	//
	// public static void removeActionReadyNotification(int id, Context ctx)
	// {
	// notifications.remove(new Integer(id));
	// ((NotificationManager) ctx.getSystemService(Context.NOTIFICATION_SERVICE)).cancel(id);
	// }
	//
	// public static void removeAllNotifications(Context ctx)
	// {
	//
	// NotificationManager nmgr = ((NotificationManager) ctx.getSystemService(Context.NOTIFICATION_SERVICE));
	//
	// for (Integer id : notifications)
	// {
	// nmgr.cancel(id);
	// }
	//
	// nmgr.cancel(PENDING_ACTIONS_CODE);
	//
	// }



	public static void startExecution(MedicalActionExecution e, Context ctx)
	{
		// ServandoService.updateServandoNotification(ctx, true, true, "");
		ServandoPlatformFacade.getInstance().requireUserAtention(ctx, SoundHelper.sounds.get(SoundHelper.SOUND_ACTION_READY));
	}

	public static void abortOrFinish(MedicalActionExecution e, Context ctx)
	{
		ServandoPlatformFacade.getInstance().requireUserAtention(ctx);
		// if (ServandoPlatformFacade.getInstance().getProtocolEngine().getAdvisedActions().getExecutions().size() == 0)
		// {
		// ServandoService.updateServandoNotification(ctx, false, false, "");
		// }
	}

}
