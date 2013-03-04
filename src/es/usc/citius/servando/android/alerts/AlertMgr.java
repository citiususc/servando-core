package es.usc.citius.servando.android.alerts;

import java.util.ArrayList;
import java.util.List;

import es.usc.citius.servando.android.logging.ILog;
import es.usc.citius.servando.android.logging.ServandoLoggerFactory;

public class AlertMgr {


	ILog log = ServandoLoggerFactory.getLogger(AlertMgr.class);

	private List<AlertHandler> handlers;

	public AlertMgr()
	{
		handlers = new ArrayList<AlertHandler>();
	}

	public void registerHandler(AlertHandler h)
	{
		if (!handlers.contains(h))
			handlers.add(h);
	}

	public void unregisterHandler(AlertHandler h)
	{
		if (handlers.contains(h))
			handlers.remove(h);
	}

	public void alert(AlertMsg alert)
	{
		for (AlertHandler h : handlers)
		{
			h.onAlert(alert);
		}

	}

	/**
	 * Interface for alarm handlers
	 */
	public interface AlertHandler {
		void onAlert(AlertMsg m);
	}

}
