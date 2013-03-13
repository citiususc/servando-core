package es.usc.citius.servando.android.alerts;

import es.usc.citius.servando.android.alerts.AlertMgr.AlertHandler;
import es.usc.citius.servando.android.communications.PlatformService;
import es.usc.citius.servando.android.logging.ILog;
import es.usc.citius.servando.android.logging.ServandoLoggerFactory;

public class RemoteSendingAlertHandler implements AlertHandler {

	ILog log = ServandoLoggerFactory.getLogger(RemoteSendingAlertHandler.class);

	@Override
	public void onAlert(final AlertMsg m)
	{
		if (mustSendAlert(m.getType()))
		{
			log.debug("Sending alert " + m.toString() + " ...");
			new Thread(new Runnable()
			{

				@Override
				public void run()
				{
					PlatformService.getTransporter().send(m);
				}
			}).start();
		}

	}

	boolean mustSendAlert(AlertType t)
	{
		boolean mustSend = false;

		mustSend |= t == AlertType.PROTOCOL_NON_COMPILANCE;
		mustSend |= t == AlertType.BLOOD_PRESSURE_VALUE;
		mustSend |= t == AlertType.WEIGHT_VALUE;

		return mustSend;

	}

}
