package es.usc.citius.servando.android.device;

import android.content.Context;
import es.usc.citius.servando.android.logging.ILog;
import es.usc.citius.servando.android.logging.ServandoLoggerFactory;

/**
 * Para implementar esta clase o mellor será rexistrar unha serie de broadcastReceivers sobre os eventos que android
 * envía, con información de diferentes partes do sistema
 * 
 * http://www.vogella.com/articles/AndroidBroadcastReceiver/article.html
 * 
 * @author Ángel Piñeiro
 */
public class SystemStatusMonitor {

	/**
	 * Servando paltform logger for this class
	 */
	private static final ILog log = ServandoLoggerFactory.getLogger(SystemStatusMonitor.class);

	/**
	 * Singleton unique instance
	 */
	private static final SystemStatusMonitor instance = new SystemStatusMonitor();

	/**
	 * Private constructor to avoid multiple instances
	 */
	private SystemStatusMonitor()
	{
		batteryMonitor = new BatteryMonitor();
	}

	/**
	 * Static member to obtain the unique instance
	 */
	public static SystemStatusMonitor getInstance()
	{
		return instance;
	}

	BatteryMonitor batteryMonitor;

	public void updateStatus(Context ctx)
	{
		batteryMonitor.update(ctx);
	}

	@Override
	public String toString()
	{
		StringBuilder builder = new StringBuilder();
		builder.append("SystemStatusMonitor[\n");
		if (batteryMonitor != null)
		{
			builder.append("batteryMonitor=");
			builder.append(batteryMonitor);
		}
		builder.append("]");
		return builder.toString();
	}

}
