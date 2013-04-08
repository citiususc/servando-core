package es.usc.citius.servando.android.agenda;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.net.URLConnection;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.List;

import org.joda.time.DateTime;
import org.joda.time.Duration;
import org.joda.time.Interval;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import android.util.Log;
import es.usc.citius.servando.android.ServandoPlatformFacade;
import es.usc.citius.servando.android.logging.ILog;
import es.usc.citius.servando.android.logging.ServandoLoggerFactory;
import es.usc.citius.servando.android.models.protocol.MedicalAction;
import es.usc.citius.servando.android.models.protocol.MedicalActionExecution;
import es.usc.citius.servando.android.models.protocol.MedicalActionExecutionComparator;
import es.usc.citius.servando.android.models.protocol.MedicalActionExecutionList;
import es.usc.citius.servando.android.models.protocol.MedicalProtocol;
import es.usc.citius.servando.android.models.protocol.ProtocolAction;
import es.usc.citius.servando.android.models.services.IPlatformService;
import es.usc.citius.servando.android.settings.ServandoStartConfig;
import es.usc.citius.servando.android.settings.StorageModule;
import es.usc.citius.servando.android.xml.helpers.SimpleXMLSerializator;

public class ProtocolEngineHelper {

	private ILog log = ServandoLoggerFactory.getLogger(getClass());

	static Long MAX_DURATION_IN_SECONDS = (long) (5 * 365 * 24 * 3600); // 5 year

	private static boolean PROTOCOL_UPDATES_ENABLED = true;

	private SimpleXMLSerializator serializator;

	private MedicalProtocol protocol;

	private String basePath;

	private String protocolFileName;

	private int logTime = 60;

	private String uncompletedActionsFileName;

	private String finishedActionsFileName;

	private int uniqueId = 100;

	DateTimeFormatter fmt = DateTimeFormat.forPattern("HH:mm:ss, dd/MM");

	private Object lock = new Object();

	public ProtocolEngineHelper()
	{
		basePath = StorageModule.getInstance().getBasePath();

		try
		{
			protocolFileName = StorageModule.getInstance().getSettings().getProtocolFile();
			uncompletedActionsFileName = StorageModule.getInstance().getSettings().getUncompletedActionsFile();
			finishedActionsFileName = StorageModule.getInstance().getSettings().getFinishedActionsFile();
			logTime = StorageModule.getInstance().getSettings().getLogTime();

		} catch (IOException e)
		{
			log.error("IOException initializing ProtocolEngineHelper", e);
		}
		serializator = new SimpleXMLSerializator();
	}

	public synchronized MedicalProtocol loadProtocol()
	{
		File protocolFile = null;

		try
		{
			protocolFile = new File(basePath + "/" + protocolFileName);
			protocol = (MedicalProtocol) serializator.deserialize(protocolFile, MedicalProtocol.class);

			if (protocol.getStartDate() == null)
			{
				// protocol.setStartDate(new GregorianCalendar());
				saveProtocol(protocol);
			}
			// Actualizamos las referencias a las actuaciones médicas y creamos las nuevas ejecuciones.
			for (int i = 0; i < protocol.getActions().size(); i++)
			{
				ProtocolAction protocolAction = protocol.getActions().get(i);

				try
				{
					protocolAction.setAction(getActionCompleteInstance(protocolAction.getAction()));

				} catch (IllegalArgumentException e)
				{
					// Si no encontramos la actuación, la eliminamos del protocolo, registrando el error
					log.error("No hay ningún servicio proveedor de la actuación médica solicitada '" + protocolAction.getAction().getId() + "'");
					protocol.getActions().remove(protocolAction);
					i--;
				}

			}

		} catch (Exception e)
		{
			log.error("No se ha podido cargar el protocolo desde el archivo correspondiente", e);
			protocol = new MedicalProtocol();
		}
		return protocol;
	}

	public synchronized void saveProtocol(MedicalProtocol protocol)
	{
		File protocolFile = new File(basePath + "/" + protocolFileName);
		try
		{
			serializator.serialize(protocol, protocolFile);
		} catch (Exception ex)
		{
			log.error("No se ha podido guardar el protocolo de seguimiento", ex);
		}
	}

	/**
	 * Obtiene las instancias de actuaciones médicas que se pueden generar a partir de este protocolo y cuya ventana
	 * temporal de validez {@link MedicalActionExecution#getTimeWindow()} incluye algún momento del día indicado en el
	 * argumento jdkCal. Las actuaciones contenidas en la lista devuelta no estarán preparadas para ser ejecutadas.
	 * Simplemente incluyen las propiedades adecuadas que permitan su instanciación en el momento de su ejecución por
	 * parte del servicio proveedor {@link MedicalAction#getProvider()}
	 * 
	 * @param jdkCal Fecha por la cual se filtrará el conjunto de actuaciones médicas devuelto
	 * @return
	 */
	public List<MedicalActionExecution> getDayActions(Calendar jdkCal)
	{

		if (PROTOCOL_UPDATES_ENABLED)
		{
			checkForProtocolUpdates();

			synchronized (lock)
			{
				try
				{
					lock.wait();
				} catch (InterruptedException e)
				{
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}

		DateTime date = new DateTime(jdkCal);
		DateTime protocolStart = new DateTime(protocol.getStartDate());

		// Si el protocolo no tiene asociada una fecha de inicio, no podremos obtener las actuaciones.
		if (protocol.getStartDate() == null)
		{
			throw new NullPointerException(
					"No se conoce la fecha de inicio de aplicación de este protocolo. Imposible calcular las actuaciones derivadas del mismo");
		}

		log("ProtocolStart: " + protocolStart.toString(fmt));
		log("Today: " + date.toString(fmt));

		// Fechas de inicio y fin de día
		DateTime begin, end;
		// Valor de retorno
		List<MedicalActionExecution> dayActions = new ArrayList<MedicalActionExecution>();

		// Establecemos la hora de inicio del día a las 00:00:00
		begin = date.withHourOfDay(0).withMinuteOfHour(0).withSecondOfMinute(0);
		// Y la hora de final a las 23:59:59
		end = begin.plusDays(1).minusSeconds(1);
		// Intervalo que abarca desde el inicio hasta el final del día
		Interval dayInterval = new Interval(begin, end);

		log("Day interval [" + begin.toString(fmt) + ", " + end.toString(fmt) + "]");
		// Debemos recorrer todas las acciones del protocolo.
		log("Looping over protocol actions:");
		for (ProtocolAction actionDefinition : protocol.getActions())
		{

			if (actionDefinition.getDuration() == 0)
			{
				actionDefinition.setDuration(MAX_DURATION_IN_SECONDS);
			}

			log(" Action: " + actionDefinition.toString());

			DateTime actionStart, actionEnd;

			// Primero comprobamos que el rango de tiempos de la acción abarque la fecha solicitada.
			actionStart = protocolStart.plusSeconds((int) actionDefinition.getStartGap());

			// Calculamos la fecha límite de la ventana temporal de la última iteración que generará esta acción.
			/*******/
			// int realActionWindow = (int) (actionDefinition.getInterval() == 0 ? actionDefinition.getTimeWindow() :
			// actionDefinition.getInterval()
			// * (actionDefinition.getDuration() / actionDefinition.getInterval()));
			//
			// actionEnd = actionStart.plusSeconds(realActionWindow);
			/*******/

			long interval = actionDefinition.getInterval();
			// Si la acción no se repite
			if (interval == 0)
			{
				// calculamos el final sumando al inicio la ventana temporal de la actuación
				actionEnd = actionStart.plusSeconds((int) actionDefinition.getTimeWindow());
			} else
			{
				// si se repite, lo calculamos dividiendo la duración de la actuación por el intervalo, y sumamos la
				// ventana
				actionEnd = actionStart.plusSeconds((int) (interval * (actionDefinition.getDuration() / interval))).plusSeconds(
						(int) actionDefinition.getTimeWindow());
			}

			// actionEnd = actionDefinition.getInterval() == 0 ? actionStart.plusSeconds((int)
			// actionDefinition.getTimeWindow())
			// : actionStart.plusSeconds(
			// (int) (actionDefinition.getInterval() * (actionDefinition.getDuration() /
			// actionDefinition.getInterval()))).plusSeconds(
			// (int) actionDefinition.getTimeWindow());

			log("Action " + actionDefinition.getAction().getId() + " [start: " + actionStart.toString(fmt) + ", end: " + actionEnd.toString(fmt)
					+ "]");

			// Intervalo que abarca dende o inicio ata o final da acción médica
			Interval actionInterval = new Interval(actionStart, actionEnd);

			if (actionInterval.overlaps(dayInterval))
			{
				log("Action interval overlaps day interval");
				/*
				 * En primer lugar, buscamos aquellas actuaciones que comienzan durante el día solicitado.
				 */
				dayActions.addAll(getExecutionsStartingBetween(actionDefinition, dayInterval));
				/*
				 * Ahora buscamos las actuaciones que comienzan antes del día solicitado, pero cuya ventana temporal
				 * está activa en algún momento durante el mismo.
				 */
				// Buscamos el momento más pronto posible en el que pueda comenzar una actuación para que su
				// ventana temporal esté dentro de este día.
				DateTime earliestTime = begin.minus(Duration.standardSeconds(actionDefinition.getTimeWindow() - 1));
				// Ahora añadimos todas las actuaciones que comiencen entre el momento más pronto y el inicio del día
				// menos un segundo.
				dayActions.addAll(getExecutionsStartingBetween(actionDefinition, new Interval(earliestTime, begin.minusSeconds(1))));
			} else
			{
				log("Day interval doesnt contains action interval");
			}
		}

		// Antes de salir, ordenamos la lista resultado
		Collections.sort(dayActions, MedicalActionExecutionComparator.getInstance());

		for (MedicalActionExecution m : dayActions)
		{
			m.setUniqueId(uniqueId++);
		}

		return dayActions;
	}

	/**
	 * Rutina auxiliar, que calcula para una determinada acción de protocolo, las instancias de actuación que tendrían
	 * como momento de inicio una fecha dentro del intervalo pasado como argumento.
	 * 
	 * @param action Acción de protocolo de la cual debemos calcular las actuaciones generadas.
	 * @param interval Intervalo
	 * @return
	 */
	private List<MedicalActionExecution> getExecutionsStartingBetween(ProtocolAction action, Interval interval)
	{

		List<MedicalActionExecution> executions = new ArrayList<MedicalActionExecution>();
		DateTime protocolStart = new DateTime(protocol.getStartDate());
		DateTime begin = interval.getStart();
		DateTime end = interval.getEnd();

		// Calculamos el momento de inicio de aplicación de la acción.
		DateTime actionStart = protocolStart.plusSeconds((int) action.getStartGap());

		// Si la actuación no es iterativa, sólo comprobamos que la fecha de inicio se encuentre en el intervalo
		// solicitado.
		if (action.getInterval() == 0)
		{
			if (interval.contains(actionStart))
			{
				executions.add(new MedicalActionExecution(action.getAction(), action.getParameters(), action.getPriority(),
						actionStart.toGregorianCalendar(), action.getTimeWindow()));
			}
			return executions;
		}
		// Si la actuación es iterativa, el algoritmo deberá calcular todas las posibles instancias generadas.
		// Calculamos el indice de la primera iteración que está en el intervalo que va desde el comienzo de aplicación
		// de la acción al comienzo del intervalo solicitado.

		/*
		 * TODO Revisar
		 * 
		 * Nº iteración:
		 * 
		 * Si actionStart < begin calcular gap dividir por intervalo facer Math.ceil
		 * 
		 * En otro caso , 0, pues el rango de la acción comienza durante el intervalo, por lo que comenzaremos en la
		 * primera iteración.
		 */
		int iteration = 0;

		if (actionStart.isBefore(begin))
		{
			iteration = (int) Math.ceil(new Interval(actionStart, begin).toDuration().getStandardSeconds() / (double) action.getInterval());
		}

		boolean cond1 = !actionStart.plusSeconds((int) (iteration * action.getInterval())).isAfter(end);
		boolean cond2 = iteration * action.getInterval() <= action.getDuration();
		boolean condition = cond1 && cond2;

		// Mientras la acción genere nuevas actuaciones con fecha de inicio durante el intervalo
		while (condition)
		{
			log.debug("Action: " + action.getAction().getId() + ", cond1: " + cond1 + ", cond2: " + cond2);

			// Si no es una de las excepciones, la añadimos a la lista.
			if (!action.getExceptions().contains(iteration))
			{
				executions.add(new MedicalActionExecution(action.getAction(), action.getParameters(), action.getPriority(), actionStart.plusSeconds(
						(int) (iteration * action.getInterval())).toGregorianCalendar(), action.getTimeWindow()));
			}
			// Pasamos a la siguiente iteración
			iteration++;

			cond1 = !actionStart.plusSeconds((int) (iteration * action.getInterval())).isAfter(end);
			cond2 = iteration * action.getInterval() <= action.getDuration();
			condition = cond1 && cond2;
		}
		return executions;
	}

	public MedicalAction getActionCompleteInstance(MedicalAction incomplete)
	{
		// Buscamos en todas las actuaciones médicas proporcionadas por los servicios.
		for (IPlatformService service : ServandoPlatformFacade.getInstance().getRegisteredServices().values())
		{
			for (MedicalAction action : service.getProvidedActions())
			{
				if (incomplete.getId().equals(action.getId()))
				{
					log.debug("Provider of " + action.getId() + " found, provider: " + String.valueOf((action.getProvider() != null)));

					return action;
				}
			}
		}

		// Si no la encontramos, lanzamos una excepción.
		throw new IllegalArgumentException("No se encuentra ninguna actuación médica con ese identificador");
	}

	public MedicalActionExecutionList loadFinishedActions()
	{
		MedicalActionExecutionList list = null;

		File finishedActionsFile = new File(basePath + "/" + finishedActionsFileName);
		try
		{
			list = (MedicalActionExecutionList) serializator.deserialize(finishedActionsFile, MedicalActionExecutionList.class);
		} catch (FileNotFoundException e)
		{
			log.debug("No se ha encontrado el fichero de acciones finalizadas [" + finishedActionsFile.getAbsolutePath() + "]");
			list = new MedicalActionExecutionList();

		} catch (Exception e)
		{
			log.error("Se ha producido un error cargando las actuaciones finalizadas", e);
			list = new MedicalActionExecutionList();
		}
		return list;

	}

	public MedicalActionExecutionList loadUncompletedActions()
	{
		MedicalActionExecutionList list = null;

		File uncompletedActionsFile = new File(basePath + "/" + uncompletedActionsFileName);

		if (!uncompletedActionsFile.exists())
		{
			log.debug("File " + uncompletedActionsFile.getAbsolutePath() + " doenst exists");
			return new MedicalActionExecutionList();
		}

		try
		{

			// log.debug("Deserializing " + uncompletedActionsFile.getAbsolutePath());

			list = (MedicalActionExecutionList) serializator.deserialize(uncompletedActionsFile, MedicalActionExecutionList.class);

			// log.debug("Loaded uncompleted: " + list.getExecutions().size());

			List<MedicalActionExecution> expired = new ArrayList<MedicalActionExecution>();
			// Si hay alguna actuación ya caducada, la eliminamos de las no completadas.
			for (MedicalActionExecution e : list.getExecutions())
			{

				DateTime startDate = new DateTime(e.getStartDate());

				if (startDate.plusSeconds((int) e.getTimeWindow()).isBefore(DateTime.now()))
				{
					// log.debug("Action " + e.getAction().getId() + " [" + startDate.toString(fmt) + ", " +
					// e.getTimeWindow() + "] expired");
					expired.add(e);
				}
			}

			list.getExecutions().removeAll(expired);

		} catch (Exception ex)
		{
			log.error("No hay ningún registro de actuaciones no completadas en el sistema", ex);
			list = new MedicalActionExecutionList();
		}
		return list;
	}

	public synchronized void saveFinishedActions(MedicalActionExecutionList list)
	{
		File finishedActionsFile = null;
		try
		{
			// Convertimos cada actuación de la lista a una representación plana, para evitar posibles errores con
			// subclases
			// de MedicalActionExecution. Además, eliminamos aquellas actuaciones de más de 60 días de antigüedad, a
			// menos
			// que su ventana de actuación aún esté activa.
			// Lista que guardaremos

			finishedActionsFile = new File(basePath + "/" + finishedActionsFileName);

			if (list != null && list.getExecutions().size() > 0)
			{

				MedicalActionExecutionList toStore = new MedicalActionExecutionList();
				for (MedicalActionExecution finishedAction : list.getExecutions())
				{
					// Si el momento de inicio de la actuación es anterior a LogTime días desde este momento, y la
					// ventana
					// de ejecución
					// ya no está activa, la actuación no se guardará.
					if (new Duration(new DateTime(finishedAction.getStartDate()), DateTime.now()).getStandardDays() < logTime)
					{
						toStore.getExecutions().add(
								new MedicalActionExecution(finishedAction.getAction(), finishedAction.getParameters(), finishedAction.getPriority(),
										finishedAction.getStartDate(), finishedAction.getTimeWindow(), finishedAction.getState()));
					}
				}
				serializator.serialize(toStore, finishedActionsFile);
			} else
			{
				finishedActionsFile.delete();
			}

		} catch (Exception ex)
		{
			log.error("No se ha podido guardar el registro de actuaciones finalizadas", ex);
		}

	}

	public synchronized void saveUncompletedActions(MedicalActionExecutionList list)
	{
		File uncompletedActionsFile = null;
		try
		{
			// Convertimos cada actuación de la lista a una representación plana, para evitar posibles errores con
			// subclases de MedicalActionExecution. Además, eliminamos aquellas actuaciones de más de 60 días de
			// antigüedad, a

			uncompletedActionsFile = new File(basePath + "/" + uncompletedActionsFileName);

			// menos que su ventana de actuación aún esté activa.
			if (list != null && list.getExecutions().size() > 0)
			{
				// Lista que guardaremos
				MedicalActionExecutionList toStore = new MedicalActionExecutionList();

				for (MedicalActionExecution uncompletedAction : list.getExecutions())
				{
					// Si el momento de inicio de la actuación es anterior a LogTime días desde este momento, y la
					// ventana
					// de ejecución ya no está activa, la actuación no se guardará.
					if (new Duration(new DateTime(uncompletedAction.getStartDate()), DateTime.now()).getStandardDays() < logTime)
					{
						toStore.getExecutions().add(
								new MedicalActionExecution(uncompletedAction.getAction(), uncompletedAction.getParameters(),
										uncompletedAction.getPriority(), uncompletedAction.getStartDate(), uncompletedAction.getTimeWindow(),
										uncompletedAction.getState()));
					}
				}
				uncompletedActionsFile = new File(basePath + "/" + uncompletedActionsFileName);
				serializator.serialize(toStore, uncompletedActionsFile);
			} else
			{
				uncompletedActionsFile.delete();
			}

		} catch (Exception ex)
		{
			log.error("No se ha podido guardar el registro de actuaciones comenzadas", ex);
		}

	}

	void checkForProtocolUpdates()
	{
		new Thread(new Runnable()
		{
			@Override
			public void run()
			{
				URL url = null;
				File tmpProtocolFile = null;
				try
				{
					String urlStr = ServandoStartConfig.getInstance()
														.get(ServandoStartConfig.PROTOCOL_UPDATE_URL)
														.replaceAll("%PATIENT_ID%", ServandoPlatformFacade.getInstance().getSettings().getVpnClient());

					tmpProtocolFile = File.createTempFile("protocol", "xml");
					url = new URL(urlStr);

					URLConnection connection = url.openConnection();
					connection.setConnectTimeout(4000);
					connection.connect();
					// download the file
					InputStream input = new BufferedInputStream(url.openStream());
					OutputStream output = new FileOutputStream(tmpProtocolFile);

					byte data[] = new byte[1024];
					int count;

					while ((count = input.read(data)) != -1)
					{
						output.write(data, 0, count);
					}

					output.flush();
					output.close();
					input.close();

					log.debug("Protocol: " + getStringFromFile(tmpProtocolFile));

					SimpleXMLSerializator s = new SimpleXMLSerializator();
					MedicalProtocol tmpProtocol = (MedicalProtocol) s.deserialize(tmpProtocolFile, MedicalProtocol.class);

					if (tmpProtocol.getVersion() > protocol.getVersion())
					{
						protocol = tmpProtocol;
						saveProtocol(tmpProtocol);
						loadProtocol();


					}

				} catch (Exception e)
				{
					log.error("Error in checkforupdates action", e);
				} finally
				{
					synchronized (lock)
					{
						lock.notify();
					}
				}
			}
		}).start();

	}

	void log(String str)
	{
		Log.i("ProtocolEngineHelper", str);
	}

	private String getStringFromFile(File f)
	{
		FileInputStream stream = null;
		try
		{
			stream = new FileInputStream(f);
			FileChannel fc = stream.getChannel();
			MappedByteBuffer bb = fc.map(FileChannel.MapMode.READ_ONLY, 0, fc.size());
			return Charset.defaultCharset().decode(bb).toString();
		} catch (Exception e)
		{
			// TODO: handle exception

		} finally
		{
			try
			{
				stream.close();
			} catch (IOException e)
			{
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return "";
	}

}
