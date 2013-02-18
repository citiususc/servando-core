package es.usc.citius.servando.android.agenda;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;

/**
 * 
 * @author Ángel Piñeiro
 * 
 */
public class ProtocolEngineServiceBinder {

	private ProtocolEngineServiceBinderListener listener;

	/**
	 * Singleton unique instance
	 */
	private static final ProtocolEngineServiceBinder instance = new ProtocolEngineServiceBinder();

	/**
	 * Private constructor to avoid multiple instances
	 */
	private ProtocolEngineServiceBinder()
	{
	}

	/**
	 * Static member to obtain the unique instance
	 */
	public static ProtocolEngineServiceBinder getInstance()
	{
		return instance;
	}

	ProtocolEngineService mService;
	boolean mBound = false;

	public void startAndBindToAppContext(Context ctx)
	{

		Context appContext = ctx.getApplicationContext();
		// Bind to LocalService
		appContext.startService(new Intent(appContext, ProtocolEngine.class));
		appContext.bindService(new Intent(appContext, ProtocolEngine.class), mConnection, Context.BIND_AUTO_CREATE);
	}

	public void unbind(Context ctx)
	{
		// Unbind from the service
		if (mBound)
		{
			ctx.getApplicationContext().unbindService(mConnection);
			mBound = false;
		}
	}

	/** Defines callbacks for service binding, passed to bindService() */
	private ServiceConnection mConnection = new ServiceConnection()
	{

		@Override
		public void onServiceConnected(ComponentName className, IBinder service)
		{
			// We've bound to LocalService, cast the IBinder and get LocalService instance
			ProtocolEngineService.ProtocolEngineBinder binder = (ProtocolEngineService.ProtocolEngineBinder) service;
			mService = binder.getService();
			mBound = true;
			if (listener != null)
			{
				listener.onBind(getProtocolEngine());
			}
		}

		@Override
		public void onServiceDisconnected(ComponentName arg0)
		{
			mBound = false;
		}
	};

	public ProtocolEngine getProtocolEngine()
	{
		return mService.getProtocolEngine();
	}

	/**
	 * @return the listener
	 */
	public ProtocolEngineServiceBinderListener getBindingListener()
	{
		return listener;
	}

	/**
	 * @param listener the listener to set
	 */
	public void setBindingListener(ProtocolEngineServiceBinderListener listener)
	{
		this.listener = listener;
	}

	public interface ProtocolEngineServiceBinderListener {
		public void onBind(ProtocolEngine engine);
	}

}
