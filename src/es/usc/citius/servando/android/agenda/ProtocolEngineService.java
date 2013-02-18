package es.usc.citius.servando.android.agenda;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import es.usc.citius.servando.android.logging.ILog;
import es.usc.citius.servando.android.logging.ServandoLoggerFactory;

public abstract class ProtocolEngineService extends Service {

	/**
	 * Servando paltform logger for this class
	 */
	private static final ILog log = ServandoLoggerFactory.getLogger(ProtocolEngineService.class);

	// Binder given to clients
	private final IBinder mBinder = new ProtocolEngineBinder();

	/**
	 * Class used for the client Binder. Because we know this service always runs in the same process as its clients, we
	 * don't need to deal with IPC.
	 */
	public class ProtocolEngineBinder extends Binder {
		ProtocolEngineService getService()
		{
			// Return this instance of LocalService so clients can call public methods
			return ProtocolEngineService.this;
		}
	}

	@Override
	public IBinder onBind(Intent intent)
	{
		return mBinder;
	}

	public abstract ProtocolEngine getProtocolEngine();

}
