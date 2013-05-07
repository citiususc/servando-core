package es.usc.citius.servando.android.alerts;

import es.usc.citius.servando.android.ServandoPlatformFacade;
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
		} else
		{
			log.debug("Alerts of type " + m.getType() + " are not configured to be sent to server.");
		}

	}

	boolean mustSendAlert(AlertType t)
	{
		boolean mustSend = false;

		mustSend |= t == AlertType.PROTOCOL_NON_COMPILANCE;
		mustSend |= t == AlertType.THERAPY_NON_COMPILANCE;
		mustSend |= t == AlertType.BLOOD_PRESSURE_VALUE;
		mustSend |= t == AlertType.WEIGHT_VALUE;
		mustSend |= t == AlertType.SYMPTOM;
		mustSend |= t == AlertType.SMOKE_INTAKE;

		if (ServandoPlatformFacade.getInstance().getSettings().isSystemEventsSendingEnabled())
		{
			mustSend |= t == AlertType.SYSTEM_EVENT;
		}

		return mustSend;

	}

}
