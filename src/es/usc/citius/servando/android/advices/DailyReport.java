package es.usc.citius.servando.android.advices;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import android.text.format.DateFormat;
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

	ILog logger = ServandoLoggerFactory.getLogger(DailyReport.class);

	private static final String OPENING_MESSAGGE = "Detectamos unha serie de problemas: \n";

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

	private List<Advice> generateReport(List<Advice> advices)
	{
		List<Advice> reports = new ArrayList<Advice>();

		// Ordenamos la lista por fecha
		Collections.sort(advices);

		HashMap<String, List<Advice>> groups = new HashMap<String, List<Advice>>();
		List<Advice> toRemove = new ArrayList<Advice>();

		for (Advice advice : advices)
		{
			logger.debug("Messagge id: " + advice.getId() + " Messagge date: " + this.stringDate(advice.getDate()));
			if (Advice.SERVANDO_SENDER_NAME.equals(advice.getSender()))
			{
				String date = this.stringDate(advice.getDate());
				// Si ya existe ese grupo lo insertamos
				if (groups.containsKey(date))
				{
					groups.get(date).add(advice);
				} else
				// Si no existe ese grupo lo creamos y metemos el primer elemento
				{
					List<Advice> group = new ArrayList<Advice>();
					group.add(advice);
					groups.put(date, group);
				}
				// Añadimos este advice para eliminarlo
				toRemove.add(advice);
			}
		}
		// Borramos de la lista los que hemos agrupado
		advices.removeAll(toRemove);
		// Metemos en reports los que quedan de la lista original
		reports.addAll(advices);

		for (String key : groups.keySet())
		{
			List<Advice> list = groups.get(key);
			// Si hay más de un mensaje, los juntamos todos en uno
			if (list.size() > 1)
			{
				// Creamos un nuevo informe
				Advice report = new Advice(Advice.SERVANDO_SENDER_NAME, "", new Date());
				String msg = DailyReport.OPENING_MESSAGGE; // Ponemos el mensaje de apertura
				for (Advice adv : list)
				{
					msg = msg + adv.getMsg() + "\n"; // Añadimos las recomendaciones
					report.addSubAdvice(adv); // Añadimos a la lista de 'submensajes' del informe
				}
				Date date = list.get(0).getDate(); // Cogemos la fecha del primero
				// Nos quedamos sólo con el día
				date.setHours(0);
				date.setMinutes(0);
				date.setSeconds(0);
				report.setDate(date); // actualizamos la fecha
				report.setMsg(msg); // actualizamos el mensaje
				// Lo añadimos a la lista de informes
				reports.add(report);
			} else if (list.size() == 1)// Si sólo hay uno lo metemos directamente
			{
				reports.add(list.get(0));
			}
		}
		return reports;
	}

	/**
	 * Return all advices grouped. The advices sended by servando will be grouped in a advice per day. In this case
	 * return all messages, seen and not seen. Order by date. Lastest first
	 * 
	 * @return
	 */
	public List<Advice> getAll()
	{
		List<Advice> list = this.generateReport(SQLiteAdviceDAO.getInstance().getAll());
		Collections.sort(list);
		Collections.reverse(list);
		return list;
	}

	/**
	 * Return all advices grouped. The advices sended by servando will be grouped in a advice per day. Return only not
	 * seen messagges. Order by date. Lastest first
	 * 
	 * @return
	 */
	public List<Advice> getNotSeen()
	{
		List<Advice> list = this.generateReport(SQLiteAdviceDAO.getInstance().getNotSeen());
		Collections.sort(list);
		Collections.reverse(list);
		return list;
	}

	public Advice getLastNotSeen()
	{
		List<Advice> list = this.getNotSeen();
		if (list != null && list.size() > 0)
		{
			return list.get(0);
		}
		return null;
	}

	private String stringDate(Date date)
	{
		return DateFormat.format("dd-MM-yyyy", date).toString();
	}
}
