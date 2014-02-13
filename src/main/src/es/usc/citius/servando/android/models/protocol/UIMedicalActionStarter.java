package es.usc.citius.servando.android.models.protocol;

import android.os.Handler;

@Deprecated
public class UIMedicalActionStarter {

	/**
	 * Singleton unique instance
	 */
	private static final UIMedicalActionStarter instance = new UIMedicalActionStarter();

	/**
	 * Private constructor to avoid multiple instances
	 */
	private UIMedicalActionStarter()
	{
	}

	/**
	 * Static member to obtain the unique instance
	 */
	public static UIMedicalActionStarter getInstance()
	{
		return instance;
	}

	private Handler handler;

	public void setHandler(Handler handler)
	{
		this.handler = handler;
	}

}
