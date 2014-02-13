package es.usc.citius.servando.android.advices;

import java.util.ArrayList;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;

import es.usc.citius.servando.android.advices.storage.SQLiteAdviceDAO;
import es.usc.citius.servando.android.logging.ILog;
import es.usc.citius.servando.android.logging.ServandoLoggerFactory;

/**
 * This class generate the daily reports using {@link Advice}
 * 
 * @author pablojose.viqueira
 * 
 */
public class DailyReport {

	public static final String NURSE_NAME = "Ana";

	ILog logger = ServandoLoggerFactory.getLogger(DailyReport.class);

	private static final String OPENING_MESSAGGE = "Estamos viendo que: \n";

	private static DailyReport instance;

	private DailyReport()
	{

	}

	public static DailyReport getInstance()
	{
		if (instance == null)
		{
			instance = new DailyReport();
		}
		return instance;
	}

	/**
	 * This method return
	 * 
	 * @return
	 */
	public Advice generateDailyReport()
	{
		logger.debug("Creating daily report...");
		List<Advice> list = SQLiteAdviceDAO.getInstance().getAllReports();
		HashMap<String, Advice> uniques = new HashMap<String, Advice>();
		if (list.size() > 0)
		{
			// Nos quedamos sólo con un mensaje de cada tipo
			for (Advice adv : list)
			{
				uniques.put(adv.getMsg(), adv);
			}
			// Formamos el nuevo mensaje
			String msg = OPENING_MESSAGGE;
			List<Advice> reports = new ArrayList<Advice>();
			for (String key : uniques.keySet())
			{
				Advice a = uniques.get(key);
				reports.add(a);
				msg = msg + a.getMsg() + "\n";
			}

			Advice report = new Advice(NURSE_NAME, msg, new GregorianCalendar().getTime(), false, false);
			// Insertamos el nuevo mensaje
			SQLiteAdviceDAO.getInstance().add(report);
			// Eliminamos las alertas utilizadas para crear el report.
			SQLiteAdviceDAO.getInstance().removeAllReports(reports);

			return report;
		}
		return null;
	}
}
