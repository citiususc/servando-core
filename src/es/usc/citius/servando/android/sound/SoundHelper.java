package es.usc.citius.servando.android.sound;

import android.content.Context;
import android.media.AudioManager;
import android.media.SoundPool;
import android.net.Uri;
import android.os.Vibrator;
import android.util.SparseArray;
import android.util.SparseIntArray;
import es.usc.citius.servando.R;
import es.usc.citius.servando.android.ServandoPlatformFacade;
import es.usc.citius.servando.android.logging.ILog;
import es.usc.citius.servando.android.logging.ServandoLoggerFactory;

public class SoundHelper {

	private static final ILog log = ServandoLoggerFactory.getLogger(SoundHelper.class);

	public static final int SOUND_ACTION_READY = 1;
	public static final int SOUND_ACTION_REMINDER = 2;
	public static final int SOUND_ACTION_DONE = 3;
	public static final int SOUND_ACTION_ABORT = 4;
	public static final int SOUND_NOTIFICATION = 5;

	static final long[] pattern = new long[] { 0, 300, 300, 300, 300, 300 };

	private static SoundPool soundPool;
	private static SparseIntArray soundPoolMap;

	public static SparseArray<Uri> sounds;
	
	static{
		sounds = new SparseArray<Uri>();
		
		sounds.put(SOUND_ACTION_READY, Uri.parse("android.resource://" + ServandoPlatformFacade.APPLICATION_PACKAGE + "/" + R.raw.action_ready));
		sounds.put(SOUND_ACTION_REMINDER,
 Uri.parse("android.resource://" + ServandoPlatformFacade.APPLICATION_PACKAGE + "/" + R.raw.action_reminder));
		sounds.put(SOUND_ACTION_DONE, Uri.parse("android.resource://" + ServandoPlatformFacade.APPLICATION_PACKAGE + "/" + R.raw.action_done));
		sounds.put(SOUND_ACTION_ABORT, Uri.parse("android.resource://" + ServandoPlatformFacade.APPLICATION_PACKAGE + "/" + R.raw.action_abort_2));
		sounds.put(SOUND_NOTIFICATION, Uri.parse("android.resource://" + ServandoPlatformFacade.APPLICATION_PACKAGE + "/" + R.raw.action_abort_2));
	}
	
	int streamId;

	public static void initSounds(Context ctx)
	{
		soundPool = new SoundPool(1, AudioManager.STREAM_NOTIFICATION, 100);
		soundPoolMap = new SparseIntArray();

		soundPoolMap.put(SOUND_ACTION_READY, soundPool.load(ctx, R.raw.action_ready, 1));
		soundPoolMap.put(SOUND_ACTION_REMINDER, soundPool.load(ctx, R.raw.action_reminder, 1));
		soundPoolMap.put(SOUND_ACTION_DONE, soundPool.load(ctx, R.raw.action_done, 1));
		soundPoolMap.put(SOUND_ACTION_ABORT, soundPool.load(ctx, R.raw.action_abort_2, 1));
		soundPoolMap.put(SOUND_NOTIFICATION, soundPool.load(ctx, R.raw.action_abort_2, 1));
	}

	public static void playSound(final Context ctx, final int sound)
	{
		new Thread(new Runnable()
		{
			@Override
			public void run()
			{
				play(ctx, sound);
			}

		}).start();
	}

	public static void playSoundAndVIbrate(final Context ctx, final int sound)
	{
		new Thread(new Runnable()
		{
			@Override
			public void run()
			{
				play(ctx, sound);
				vibrate(ctx);
			}

		}).start();
	}

	private static void play(Context ctx, int sound)
	{
		// /* Updated: The next 4 lines calculate the current volume in a scale of 0.0 to 1.0 */
		// AudioManager mgr = (AudioManager) ctx.getSystemService(Context.AUDIO_SERVICE);
		// float streamVolumeCurrent = mgr.getStreamVolume(AudioManager.STREAM_NOTIFICATION);
		// float streamVolumeMax = mgr.getStreamMaxVolume(AudioManager.STREAM_NOTIFICATION);
		// float volume = streamVolumeCurrent / streamVolumeMax;
		// /* Play the sound with the correct volume */
		//
		// log.debug("Play sound " + sound + "(Volume: " + volume + ")");
		//
		// soundPool.play(soundPoolMap.get(sound), volume, volume, 1, 0, 1f);
	}

	private static void playWithSoundPool(Context ctx, int sound)
	{
		/* Updated: The next 4 lines calculate the current volume in a scale of 0.0 to 1.0 */
		AudioManager mgr = (AudioManager) ctx.getSystemService(Context.AUDIO_SERVICE);
		float streamVolumeCurrent = mgr.getStreamVolume(AudioManager.STREAM_NOTIFICATION);
		float streamVolumeMax = mgr.getStreamMaxVolume(AudioManager.STREAM_NOTIFICATION);
		float volume = streamVolumeCurrent / streamVolumeMax;
		/* Play the sound with the correct volume */

		log.debug("Play sound " + sound + "(Volume: " + volume + ")");

		soundPool.play(soundPoolMap.get(sound), volume, volume, 1, 0, 1f);
	}

	public static void vibrate(Context ctx)
	{

		Vibrator v = (Vibrator) ctx.getSystemService(Context.VIBRATOR_SERVICE);
		v.vibrate(pattern, -1);
	}


}
