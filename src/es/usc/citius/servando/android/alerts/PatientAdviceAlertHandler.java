package es.usc.citius.servando.android.alerts;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.joda.time.DateMidnight;
import org.joda.time.DateTime;

import es.usc.citius.servando.android.advices.Advice;
import es.usc.citius.servando.android.advices.storage.SQLiteAdviceDAO;
import es.usc.citius.servando.android.alerts.AlertMgr.AlertHandler;
import es.usc.citius.servando.android.logging.ILog;
import es.usc.citius.servando.android.logging.ServandoLoggerFactory;

public class PatientAdviceAlertHandler implements AlertHandler {

	ILog log = ServandoLoggerFactory.getLogger(PatientAdviceAlertHandler.class);

	@Override
	public void onAlert(AlertMsg m)
	{
		if (mustAdvicePatient(m.getType()))
		{
			log.debug("Adding advices " + m.toString() + " ...");
			List<Advice> advices = generateFromAlert(m);

			for (Advice a : advices)
				SQLiteAdviceDAO.getInstance().add(a);
		}
	}

	boolean mustAdvicePatient(AlertType t)
	{
		boolean mustSend = false;
		mustSend |= t == AlertType.PROTOCOL_NON_COMPILANCE;
		mustSend |= t == AlertType.ALCOHOL_INTAKE;
		mustSend |= t == AlertType.SALT_INTAKE;
		mustSend |= t == AlertType.SMOKE_INTAKE;
		return mustSend;
	}

	private List<Advice> generateFromAlert(AlertMsg m)
	{

		Date now = DateTime.now().toGregorianCalendar().getTime();
		Date tomorrow = new DateMidnight().plusDays(1).toGregorianCalendar().getTime();

		List<Advice> advices = new ArrayList<Advice>();

		switch (m.getType()) {

		case PROTOCOL_NON_COMPILANCE:
			String actionName = m.getParameters().get("action");
			advices.add(new Advice("Servando", "A acción " + actionName + " caducou", now));
			advices.add(new Advice(Advice.SERVANDO_SENDER_NAME, "O protocolo estase a incumplir", tomorrow));
			break;

		case SALT_INTAKE:

			break;

		default:
			break;
		}

		return advices;

	}

}
