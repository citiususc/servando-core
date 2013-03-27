package es.usc.citius.servando.android.sound;

import android.content.Context;
import android.media.AudioManager;
import android.media.SoundPool;
import android.util.SparseIntArray;
import es.usc.citius.servando.R;

public class SoundHelper {

	public static final int SOUND_ACTION_READY = 1;
	public static final int SOUND_ACTION_REMINDER = 2;
	public static final int SOUND_ACTION_DONE = 3;
	public static final int SOUND_ACTION_ABORT = 4;

	private static SoundPool soundPool;
	private static SparseIntArray soundPoolMap;

	int streamId;

	public static void initSounds(Context ctx)
	{
		soundPool = new SoundPool(4, AudioManager.STREAM_MUSIC, 100);
		soundPoolMap = new SparseIntArray();

		soundPoolMap.put(SOUND_ACTION_READY, soundPool.load(ctx, R.raw.action_ready, 1));
		soundPoolMap.put(SOUND_ACTION_REMINDER, soundPool.load(ctx, R.raw.action_reminder, 1));
		soundPoolMap.put(SOUND_ACTION_DONE, soundPool.load(ctx, R.raw.action_done, 1));
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
			}
		}).start();
	}

	private static void play(Context ctx, int sound)
	{
		/* Updated: The next 4 lines calculate the current volume in a scale of 0.0 to 1.0 */
		AudioManager mgr = (AudioManager) ctx.getSystemService(Context.AUDIO_SERVICE);
		float streamVolumeCurrent = mgr.getStreamVolume(AudioManager.STREAM_MUSIC);
		float streamVolumeMax = mgr.getStreamMaxVolume(AudioManager.STREAM_MUSIC);
		float volume = streamVolumeCurrent / streamVolumeMax;

		/* Play the sound with the correct volume */
		soundPool.play(soundPoolMap.get(sound), streamVolumeCurrent, streamVolumeCurrent, 1, 0, 1f);
	}

}
