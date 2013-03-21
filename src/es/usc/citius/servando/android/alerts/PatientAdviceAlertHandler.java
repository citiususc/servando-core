package es.usc.citius.servando.android.alerts;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.joda.time.DateMidnight;
import org.joda.time.DateTime;

import android.content.res.Resources;
import es.usc.citius.servando.R;
import es.usc.citius.servando.android.ServandoPlatformFacade;
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
			{

				SQLiteAdviceDAO.getInstance().add(a);
			}
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
			Resources r = ServandoPlatformFacade.getInstance().getProtocolEngine().getResources();
			String adviceToNow = String.format(r.getString(R.string.alert_action_expired), actionName);
			String adviceToTomorrow = r.getString(R.string.alert_protocol_non_compilance);
			advices.add(new Advice("Servando", adviceToNow, now));
			advices.add(new Advice(Advice.SERVANDO_SENDER_NAME, adviceToTomorrow, tomorrow));
			break;
		case SALT_INTAKE:
			advices.add(new Advice(Advice.SERVANDO_SENDER_NAME, m.getPatientMsg(), tomorrow));
			break;
		case ALCOHOL_INTAKE:
			advices.add(new Advice(Advice.SERVANDO_SENDER_NAME, m.getPatientMsg(), tomorrow));
			break;
		case SMOKE_INTAKE:
			advices.add(new Advice(Advice.SERVANDO_SENDER_NAME, m.getPatientMsg(), tomorrow));
			break;
		default:
			break;
		}

		return advices;

	}
}
