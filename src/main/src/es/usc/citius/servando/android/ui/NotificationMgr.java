package es.usc.citius.servando.android.ui;

import java.util.ArrayList;
import java.util.List;

import android.speech.tts.TextToSpeech;
import android.util.Log;

public class NotificationMgr {

	private static final String DEBUG_TAG = NotificationMgr.class.getSimpleName();

	private List<Notification> notifications;

	private TextToSpeech tts;

	/**
	 * Singleton unique instance
	 */
	private static final NotificationMgr instance = new NotificationMgr();

	/**
	 * Private constructor to avoid multiple instances
	 */
	private NotificationMgr()
	{
		notifications = new ArrayList<Notification>();
	}

	/**
	 * Static member to obtain the unique instance
	 */
	public static NotificationMgr getInstance()
	{
		return instance;
	}

	public int getCount()
	{
		return notifications.size();
	}

	public void add(Notification n, boolean showOnStatusBar)
	{
		notifications.add(n);
	}

	public Notification remove(int idx)
	{
		Notification n = notifications.remove(idx);
		return n;
	}

	public void clear()
	{
		notifications.clear();
		// servandoService.updateNotifications(0);
	}

	public List<Notification> getNotifications()
	{
		return notifications;
	}

	public void setNotifications(List<Notification> notifications)
	{
		this.notifications = notifications;
	}

	public void ttsNotification(String text)
	{
		if (tts != null)
		{
			try
			{
				tts.speak(text, TextToSpeech.QUEUE_FLUSH, null);
			} catch (Exception e)
			{
				Log.e(DEBUG_TAG, "TTS error", e);
			}
		}
	}

	public void onExit()
	{
		if (tts != null)
		{
			tts.stop();
			tts.shutdown();
			Log.d(DEBUG_TAG, "TTS Shutdown!");
		}
	}

	public void setTextToSpeech(TextToSpeech tts)
	{
		this.tts = tts;
	}
}
