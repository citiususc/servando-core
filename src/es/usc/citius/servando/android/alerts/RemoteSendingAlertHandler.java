package es.usc.citius.servando.android.alerts;

import es.usc.citius.servando.android.alerts.AlertMgr.AlertHandler;
import es.usc.citius.servando.android.communications.PlatformService;
import es.usc.citius.servando.android.logging.ILog;
import es.usc.citius.servando.android.logging.ServandoLoggerFactory;

public class RemoteSendingAlertHandler implements AlertHandler {

	ILog log = ServandoLoggerFactory.getLogger(RemoteSendingAlertHandler.class);

	@Override
	public void onAlert(AlertMsg m)
	{
		if (AlertType.CAREER_NOTIFICATION == m.getType())
		{
			log.debug("Sending alert " + m.toString() + " ...");
			PlatformService.getTransporter().send(m);

		} else if (AlertType.PATIENT_NOTIFICATION == m.getType())
		{

		}
	}
}
