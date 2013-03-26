package es.usc.citius.servando.android.agenda;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Collections;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TimerTask;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import org.joda.time.DateMidnight;
import org.joda.time.DateTime;
import org.joda.time.Duration;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import android.util.Log;
import es.usc.citius.servando.android.ServandoPlatformFacade;
import es.usc.citius.servando.android.agenda.ExecutionInfo.ExecutionInfoListener;
import es.usc.citius.servando.android.alerts.AlertMsg;
import es.usc.citius.servando.android.alerts.AlertType;
import es.usc.citius.servando.android.logging.ILog;
import es.usc.citius.servando.android.logging.ServandoLoggerFactory;
import es.usc.citius.servando.android.models.protocol.MedicalActionExecution;
import es.usc.citius.servando.android.models.protocol.MedicalActionExecutionComparator;
import es.usc.citius.servando.android.models.protocol.MedicalActionExecutionList;
import es.usc.citius.servando.android.models.protocol.MedicalProtocol;
import es.usc.citius.servando.android.models.protocol.ProtocolAction;
import es.usc.citius.servando.android.models.services.IPlatformService;

/**
 * 
 * @author Ángel Piñeiro
 * 
 */
public class ProtocolEngine extends ProtocolEngineService implements MedicalActionExecutionListener, ExecutionInfoListener {

	private ILog log = ServandoLoggerFactory.getLogger(getClass());

	private static final int EXECUTOR_CORE_POOL_SIZE = 10;

	private static final int DELAY_TIME_IN_SECONDS = 0;

	private boolean started = false;

	private SimpleDateFormat sdf = new SimpleDateFormat("dd/MM, HH:mm:ss");
	private ProtocolEngineHelper engineHelper;

	private List<MedicalActionExecution> pendingActions;
	private List<MedicalActionExecution> executingActions;
	private List<MedicalActionExecution> lockedActions;
	// private List<MedicalActionExecution> scheduledActions;

	private List<MedicalActionExecution> allDayActions;

	private Map<MedicalActionExecution, ExecutionInfo> executionsInfo;

	private MedicalActionExecutionList finishedActions;
	private MedicalActionExecutionList uncompletedActions;

	private MedicalProtocol protocol;

	private ScheduledThreadPoolExecutor executor;

	private BeginDayTimerInvokedTask beginDayTimerTask;

	private HashMap<MedicalActionExecution, TimerTask> timerMappings;

	private List<ProtocolEngineListener> protocolListeners;

	private HashMap<MedicalActionExecution, Object> locks;

	private Object engineLock;

	// Auto finish actions in 20 seconds
	private boolean autoFinishActions = false;

	private List<ScheduledFuture<?>> futures;

	DateTimeFormatter fmt = DateTimeFormat.forPattern("HH:mm:ss, dd/MM");

	public ProtocolEngine()
	{
		inititialize();
	}

	private void inititialize()
	{

		if (!started)
		{
			started = false;
			engineHelper = new ProtocolEngineHelper();
			pendingActions = new ArrayList<MedicalActionExecution>();
			executingActions = new ArrayList<MedicalActionExecution>();
			// scheduledActions = new ArrayList<MedicalActionExecution>();
			lockedActions = new ArrayList<MedicalActionExecution>();
			executionsInfo = new HashMap<MedicalActionExecution, ExecutionInfo>();
			timerMappings = new HashMap<MedicalActionExecution, TimerTask>();

			executor = new ScheduledThreadPoolExecutor(EXECUTOR_CORE_POOL_SIZE);
			executor.setKeepAliveTime(25 * 60 * 60, TimeUnit.SECONDS); // 24 horas + margen acojone

			protocolListeners = new ArrayList<ProtocolEngineListener>();
			locks = new HashMap<MedicalActionExecution, Object>();

			futures = new ArrayList<ScheduledFuture<?>>();
			// TODO Remove this fucking shit from here
			protocol = engineHelper.loadProtocol();
			// protocol.setStartDate(new DateTime(new DateMidnight()).toGregorianCalendar());

			finishedActions = engineHelper.loadFinishedActions();
			uncompletedActions = engineHelper.loadUncompletedActions();

			listenToProtocolFileChanges();

			engineLock = new Object();
		}
	}

	private void listenToProtocolFileChanges()
	{
		// FileObserver observer = new FileObserver(StorageModule.getInstance().getBasePath() + "/", FileObserver.CREATE
		// | FileObserver.MODIFY)
		// {
		// @Override
		// public void onEvent(int event, String file)
		// {
		// log.debug("File: " + file + ", event: " + event);
		//
		// if (file.endsWith("protocol.xml"))
		// {
		// log.debug("Protocol file changed on SDCARD");
		// updateBeginDayTimerTask(true);
		// }
		// }
		// };
		// observer.startWatching(); // start the observer
	}

	// TODO private
	private void loadDayActions()
	{

		DateTime now = DateTime.now();
		// Obtenemos las instancias de actuaciones médicas para el día de hoy
		List<MedicalActionExecution> dayActions = engineHelper.getDayActions(now.toGregorianCalendar());

		log.info("DayActions: " + dayActions.size());

		for (MedicalActionExecution exec : dayActions)
		{

			DateTime startDate = new DateTime(exec.getStartDate());

			// Comprobamos si la actuación cumple las condiciones para entrar en el protocolo.
			// En primer lugar comprobamos que esté dentro de la ventana temporal.
			boolean canEnter = startDate.plusSeconds((int) exec.getTimeWindow()).isAfter(now);

			if (actionHasTimers(exec))
			{
				log.debug("Action " + exec.getAction().getId() + " has timers");
				canEnter = false;
			} else
			{
				log.debug("Action " + exec.getAction().getId() + " has no timers");
			}

			// Ahora, comprobamos que no se encuentre en ejecución, ni bloqueada, ni pendiente.
			canEnter &= !(executingActions.contains(exec) || lockedActions.contains(exec) || pendingActions.contains(exec));
			// Comprobamos si la actuación aparece en el registro de actuaciones completas.
			canEnter &= !finishedActions.getExecutions().contains(exec);

			if (canEnter)
			{

				IPlatformService provider = exec.getAction().getProvider();
				MedicalActionExecution execution;

				log.info("Execution provider: " + (provider != null ? provider.getId() : "NULL"));

				if (uncompletedActions.getExecutions().contains(exec))
				{
					execution = provider.restoreExecution(exec.getAction(), exec.getResources(), exec.getParameters(), exec.getPriority(),
							exec.getStartDate(), exec.getTimeWindow());
					execution.setUniqueId(exec.getUniqueId());
				} else
				{
					execution = provider.newExecution(exec.getAction(), exec.getResources(), exec.getParameters(), exec.getPriority(),
							exec.getStartDate(), exec.getTimeWindow());
					execution.setUniqueId(exec.getUniqueId());
				}

				execution.setExecutionListener(this);

				// Añadimos los temporizadores, en función de si ya ha comenzado o no la ventana de ejecución.
				if (startDate.isAfterNow())
				{
					long dueTime = new Duration(DateTime.now(), startDate).getStandardSeconds();
					TimerTask task = new MedicalActionStartTimerInvokedTask(execution);
					// executor.schedule(task, dueTime, TimeUnit.SECONDS);
					scheduleTask(task, dueTime, TimeUnit.SECONDS);
					timerMappings.put(execution, task);
					// scheduledActions.add(execution);
					// TODO Test
					// ============================================================ //
					ExecutionInfo info = getInfo(execution);
					info.setScheduledTime(execution.getStartDate());
					info.setStatus(ExecutionInfo.EXEC_SCHEDULED);
					// ============================================================ //

					log.debug("Action " + execution.getAction().getId() + " sucssesfully scheduled to execute in " + dueTime + " seconds");

				} else
				{
					pendingActions.add(execution);
					Collections.sort(pendingActions, MedicalActionExecutionComparator.getInstance());

					long dueTime = new Duration(DateTime.now(), startDate.plusSeconds((int) execution.getTimeWindow())).getStandardSeconds();

					TimerTask task = new MedicalActionExpiredTimerInvokedTask(execution);
					// executor.schedule(task, dueTime, TimeUnit.SECONDS);
					scheduleTask(task, dueTime, TimeUnit.SECONDS);
					timerMappings.put(execution, task);

					log.debug("Action " + execution.getAction().getId() + " expiration sucssesfully scheduled to execute in " + dueTime + " seconds");

					// TODO Test
					// ============================================================ //
					ExecutionInfo info = getInfo(execution);
					info.setScheduledTime(GregorianCalendar.getInstance()); // Ventana iniciada
					info.setAbortScheduledTime(startDate.plusSeconds((int) execution.getTimeWindow()).toGregorianCalendar());
					info.setStatus(ExecutionInfo.EXEC_PENDING);
					// ============================================================ //

				}

				// Añadimos el objeto que usarémos como bloqueo para esta ejecución
				locks.put(execution, new Object());
			}
		}
		raiseOnLoadDayActions();

		schedule();
	}

	private void scheduleTask(TimerTask task, long delay, TimeUnit timeUnit)
	{
		ScheduledFuture<?> future = executor.schedule(task, delay, timeUnit);
		futures.add(future);
	}

	private void cancelTask(TimerTask task)
	{
		task.cancel();
		executor.remove(task);

	}

	private class LogScheduledTasksRemainingDelay extends TimerTask {

		@Override
		public void run()
		{
			log.debug("Time: " + DateTime.now().toString(fmt));
			log.debug(executor.getCompletedTaskCount() + " completed tasks.");
			log.debug(executor.getQueue().size() + " tasks queued.");
			log.debug(executor.getPoolSize() + " threads occupied.");
			log.debug(executor.getLargestPoolSize() + " largest pool size.");

			log.debug("Details:");
			for (ScheduledFuture<?> f : futures)
			{
				if (!f.isDone())
					log.debug("\tTask " + f.getClass().getSimpleName() + " will be executed in " + f.getDelay(TimeUnit.SECONDS) + " seconds");
			}
		}
	}

	private boolean actionHasTimers(MedicalActionExecution exec)
	{
		// Comprobamos que no tenga temporizadores activados
		if (timerMappings.containsKey(exec))
		{
			return true;
		}

		// for (MedicalActionExecution e : timerMappings.keySet())
		// {
		// if (e.equals(exec))
		// return true;
		// }

		return false;

	}

	/**
	 * Inicializa el singleton. Es absolutamente necesaria la ejecución de este método para poder usarlo. La ejecución
	 * de este método solo tiene sentido la primera vez, las siguientes se ignorará. De producirse la excepción <see
	 * cref="CannotLoadProtocolException"/> solamente se producirá en esta llamada. Una vez inicializado el singleton,
	 * se invocará el evento <see cref="ProtocolChanged"/>
	 */
	public void start()
	{
		// Lo inicializamos unicamente si no está iniciado
		if (!started)
		{
			// Programamos el timer de carga diaria para las 00:00
			// Duration timeToMidnight = new Duration(DateTime.now(), new DateMidnight().plusDays(1));
			//
			// // test: recargar accions cada 2 minutos
			// timeToMidnight = new Duration(DateTime.now(), DateTime.now().plusMinutes(1));
			//
			// beginDayTimerTask = new BeginDayTimerInvokedTask();
			// executor.schedule(beginDayTimerTask, timeToMidnight.getMillis(), TimeUnit.MILLISECONDS);

			updateBeginDayTimerTask(false);

			// Obtenemos las actuaciones ya finalizadas, y las iniciadas pero no completadas
			finishedActions = engineHelper.loadFinishedActions();
			uncompletedActions = engineHelper.loadUncompletedActions();

			log.debug("FinishedActions: " + (finishedActions != null ? finishedActions.getExecutions().size() : 0));
			log.debug("UncompletedActions: " + (uncompletedActions != null ? uncompletedActions.getExecutions().size() : 0));

			// Añadimos las actuaciones del día de hoy al planificador
			loadDayActions();
			// Indicamos que ya está el singleton iniciado
			started = true;

			executor.scheduleAtFixedRate(new LogScheduledTasksRemainingDelay(), 0, 60 * 25, TimeUnit.SECONDS);
			// Lanzamos el evento de modificación del protocolo.
			raiseOnProtocolChangeEvent();
		}
	}

	// / <summary>
	// / Realiza la planificación de las actuaciones médicas, comprobando si se puede comenzar la actuación de alguna
	// / nueva de las de la lista de espera. En general este método traspasará actuaciones desde las listas <see
	// cref="pendingActions"/>
	// / y <see cref="lockedActions"/> a la lista <see cref="executingActions"/>.
	// / </summary>
	private void schedule()
	{
		synchronized (engineLock)
		{
			log.debug("Action scheduling started...");
			log.debug("Trying to unlock locked actions... ");
			// Intentamos desbloquear las actuaciones que estén bloqueadas.
			for (int i = 0; i < lockedActions.size(); i++)
			{
				MedicalActionExecution locked = lockedActions.get(i);
				// Si no tiene conflicto con ninguna en ejecución, la relanzamos
				boolean conflict = false;
				for (MedicalActionExecution running : executingActions)
				{
					if (conflict(locked, running))
					{
						conflict = true;
						break;
					}
				}
				// Si no tiene conflicto, la desbloqueamos y continuamos.
				if (!conflict)
				{
					lockedActions.remove(i);
					executingActions.add(locked);
					locks.get(locked).notify();
					return;
				}
			}

			List<MedicalActionExecution> executedNow = new ArrayList<MedicalActionExecution>();

			// Ahora intentamos ejecutar las actuaciones pendientes
			log.debug("Trying to execute pending actions... (" + pendingActions.size() + ")");

			for (int i = 0; i < pendingActions.size(); i++)
			{
				MedicalActionExecution pending = pendingActions.get(i);
				// Si no tiene conflicto con ninguna en ejecución, la lanzamos
				boolean conflict = false;
				for (MedicalActionExecution running : executingActions)
				{

					if (conflict(pending, running))
					{
						getInfo(pending).setStatus(ExecutionInfo.EXEC_PENDING_CONFLICT);
						conflict = true;
						break;
					}
				}

				if (!conflict)
				{
					log.debug("Trying to execute action " + pending.getAction().getId() + ", conflict: " + conflict);
					executedNow.add(pending);
					executingActions.add(pending);
					// Iniciamos la ejecución en un nuevo hilo
					// executor.schedule(new StartMedicalActionExecutionTask(pending), DELAY_TIME_IN_SECONDS,
					// TimeUnit.SECONDS);
					scheduleTask(new StartMedicalActionExecutionTask(pending), DELAY_TIME_IN_SECONDS, TimeUnit.SECONDS);
					log.debug("Action " + pending.getAction().getId() + " sucssesfully scheduled to execute now!");
				}
			}

			if (!executedNow.isEmpty())
			{
				pendingActions.removeAll(executedNow);
			}
		}
	}

	private void handleOnExecutionResourcesChanged(MedicalActionExecution execution)
	{

		log.debug("Medical action execution " + execution.getAction().getId() + " resources changed ["
				+ Integer.toBinaryString(execution.getResources().getValue()) + "]");

		// Si la actuación no estaba en ejecución, ejecutamos el planificador
		if (!executingActions.contains(execution))
		{
			schedule();
		} else
		{
			// En otro caso, si estaba en ejecución y entra en conflicto con otras ya ejecutándose, la bloqueamos
			// hasta que no haya conflicto.
			boolean conflictive = false;
			synchronized (engineLock)
			{
				for (MedicalActionExecution running : executingActions)
				{
					if (!running.equals(execution) && conflict(execution, running))
					{
						conflictive = true;
						break;
					}
				}
				if (conflictive)
				{
					// Cambiamos la lista a la que pertenece la actuación.
					getInfo(execution).setStatus(ExecutionInfo.EXEC_BLOCKED);
					executingActions.remove(execution);
					lockedActions.add(execution);
					Collections.sort(lockedActions, MedicalActionExecutionComparator.getInstance());
				}
			}

			if (conflictive)
			{
				// Bloqueamos la actuación en su WaitHandle
				try
				{
					String prevStatus = getInfo(execution).getStatus();
					getInfo(execution).setStatus(ExecutionInfo.EXEC_BLOCKED);
					locks.get(execution).wait();
					getInfo(execution).setStatus(prevStatus);
				} catch (InterruptedException e)
				{
					log.error("An error ocurred while handling a resource change", e);
				}
			} else
			{
				schedule();
			}
		}

	}

	private void deleteAction(MedicalActionExecution execution)
	{
		log.debug("Deleting execution " + execution.getAction().getId());
		// Eliminamos los gestores de eventos
		execution.removeExecutionListener();
		// Y la eliminamos de todas las listas y diccionarios, excepto del protocolo si ya ha finalizado.
		synchronized (engineLock)
		{
			executingActions.remove(execution);
			lockedActions.remove(execution);
			pendingActions.remove(execution);
			// executor.remove(timerMappings.remove(execution));
			cancelTask(timerMappings.remove(execution));
			locks.remove(execution);

			// La añadimos al conjunto de actuaciones finalizadas, y la eliminamos del conjunto de actuaciones iniciadas
			finishedActions.getExecutions().add(execution);
			uncompletedActions.getExecutions().remove(execution);
			engineHelper.saveFinishedActions(finishedActions);
			engineHelper.saveUncompletedActions(uncompletedActions);
		}
		// Lanzamos el evento
		raiseOnProtocolChangeEvent();
	}

	private boolean conflict(MedicalActionExecution locked, MedicalActionExecution running)
	{
		return locked.getResources().conflictWith(running.getResources());
	}

	/**
	 * Events
	 */
	@Override
	public void onStart(MedicalActionExecution target)
	{
		if (autoFinishActions)
		{
			log.debug("Scheduling autofinish for " + target.getAction().getId() + " action in 20 seconds...");
			// executor.schedule(new MedicalActionAutoFinishTask(target), (long) (10 + Math.random() * 10),
			// TimeUnit.SECONDS);
			scheduleTask(new MedicalActionAutoFinishTask(target), (long) (10 + Math.random() * 10), TimeUnit.SECONDS);
		}

		raiseOnMedicalActionStart(target);
	}

	@Override
	public void onAbort(MedicalActionExecution target)
	{

		// SQLiteAdviceDAO.getInstance().add(new Advice("Servando", "A acción " + target.getAction().getDisplayName() +
		// " caducou", new Date()));
		// SQLiteAdviceDAO.getInstance().add(
		// new Advice(Advice.SERVANDO_SENDER_NAME, "O protocolo estase a incumplir",
		// DateTime.now().plusDays(1).toDate()));

		log.debug("On abort called");
		deleteAction(target);
		schedule();
		raiseOnMedicalActionAbort(target);

		AlertMsg a = new AlertMsg(AlertType.PROTOCOL_NON_COMPILANCE, "Action not done", "Action " + target.getAction().getDisplayName()
				+ " dont done");

		a.addParameter("action", target.getAction().getDisplayName());
		ServandoPlatformFacade.getInstance().alert(a);
	}

	@Override
	public void onFinish(MedicalActionExecution target)
	{
		deleteAction(target);
		schedule();
		raiseOnMedicalActionFinish(target);
	}

	@Override
	public void onResourcesChange(MedicalActionExecution target)
	{
		handleOnExecutionResourcesChanged(target);
	}

	private void raiseOnProtocolChangeEvent()
	{
		log.debug("Raising protocol change...");
		for (ProtocolEngineListener l : protocolListeners)
		{
			l.onProtocolChanged();
		}
	}

	private void raiseOnMedicalActionStart(MedicalActionExecution mae)
	{
		log.debug("Raising medical action start...");
		for (ProtocolEngineListener l : protocolListeners)
		{
			l.onExecutionStart(mae);
		}
	}

	private void raiseOnMedicalActionAbort(MedicalActionExecution mae)
	{
		log.debug("Raising medical action abort...");
		for (ProtocolEngineListener l : protocolListeners)
		{
			l.onExecutionAbort(mae);
		}
	}

	private void raiseOnMedicalActionFinish(MedicalActionExecution mae)
	{
		log.debug("Raising medical action finish...");
		for (ProtocolEngineListener l : protocolListeners)
		{
			l.onExecutionFinish(mae);
		}
	}

	private void raiseOnLoadDayActions()
	{
		log.debug("Raising medical action finish...");
		for (ProtocolEngineListener l : protocolListeners)
		{
			l.onLoadDayActions();
		}
	}

	public void addProtocolListener(ProtocolEngineListener l)
	{
		protocolListeners.add(l);
	}

	public void removeProtocolListener(ProtocolEngineListener l)
	{
		protocolListeners.remove(l);
	}

	/**
	 * Obtiene el protocolo que se está aplicando actualmente al paciente. Añadir o eliminar acciones del protocolo
	 * devuelto no tendrá efectos sobre el protocolo real aplicado, debiéndose utilizar los métodos proporcionados para
	 * tales fines. Sin embargo, las modificaciones realizadas a las acciones de protocolo contenidas si tendrán efecto
	 * en la planificación, por lo que se deberán manejar con cuidado. El resto de propiedades del protocolo, excepto el
	 * paciente, también serán copias del protocolo real, por lo que pueden ser modificadas sin ningún efecto en la
	 * planificación.
	 * 
	 * @return the protocol
	 */
	public MedicalProtocol getProtocol()
	{
		if (!started)
		{
			throw new IllegalStateException("El motor de ejecución del protocolo no ha sido inicializado aún");
		}

		MedicalProtocol protocolCopy = new MedicalProtocol();
		protocolCopy.setDescription(protocol.getDescription());
		protocolCopy.setName(protocol.getName());
		protocolCopy.setStartDate(protocolCopy.getStartDate());
		protocolCopy.getActions().addAll(protocol.getActions());

		// TODO Add Patient reference here

		return protocolCopy;
	}

	/**
	 * Obtiene la fecha de inicio de aplicación del protocolo actual.
	 * 
	 * @return The protocol start date.
	 */
	public Calendar getProtocolStartDate()
	{
		return protocol.getStartDate();
	}

	/**
	 *
	 */
	public MedicalActionExecutionList getFinishedActions()
	{
		return new MedicalActionExecutionList(finishedActions.getExecutions());
	}

	/**
	 *
	 */
	public MedicalActionExecutionList getUncompletedActions()
	{
		return new MedicalActionExecutionList(uncompletedActions.getExecutions());
	}

	/**
	 *
	 */
	public MedicalActionExecutionList getAdvisedActions()
	{
		synchronized (engineLock)
		{
			ArrayList<MedicalActionExecution> all = new ArrayList<MedicalActionExecution>();
			all.addAll(pendingActions);
			all.addAll(executingActions);

			ArrayList<MedicalActionExecution> toRemove = new ArrayList<MedicalActionExecution>();
			for (MedicalActionExecution exec : all)
			{
				if (exec.getAction().getProvider().getId().equals("SERVANDO"))
				{
					toRemove.add(exec);
				}
			}

			all.removeAll(toRemove);

			return new MedicalActionExecutionList(all);
		}
	}

	public List<MedicalActionExecution> getFilteredDayActions(Calendar jdkCal)
	{

		synchronized (engineLock)
		{
			ArrayList<MedicalActionExecution> all = new ArrayList<MedicalActionExecution>();
			all.addAll(pendingActions);
			all.addAll(executingActions);

			for (MedicalActionExecution m : timerMappings.keySet())
			{
				if (timerMappings.get(m) instanceof MedicalActionStartTimerInvokedTask)
				{
					all.add(m);
				}
			}

			ArrayList<MedicalActionExecution> toRemove = new ArrayList<MedicalActionExecution>();

			for (MedicalActionExecution exec : all)
			{
				if (exec.getAction().getProvider().getId().equals("SERVANDO"))
				{
					toRemove.add(exec);
				}
			}

			for (MedicalActionExecution me : all)
			{
				if (new DateTime(me.getStartDate()).plusSeconds((int) me.getTimeWindow()).isBeforeNow())
				{
					toRemove.add(me);
				}
			}

			all.removeAll(toRemove);

			return all;
		}

		// List<MedicalActionExecution> dayActions = getDayActions(jdkCal);
		// dayActions.removeAll(finishedActions.getExecutions());
		// // Eliminamos aquellas que ya han expirado
		// List<MedicalActionExecution> toRemove = new ArrayList<MedicalActionExecution>();
		// for (MedicalActionExecution me : dayActions)
		// {
		// if (new DateTime(me.getStartDate()).plusSeconds((int) me.getTimeWindow()).isBeforeNow())
		// {
		// toRemove.add(me);
		// }
		// }
		// dayActions.removeAll(toRemove);
		// return dayActions;
	}

	private class StartMedicalActionExecutionTask extends TimerTask {


		private MedicalActionExecution execution;

		public StartMedicalActionExecutionTask(MedicalActionExecution mae)
		{
			execution = mae;
		}

		@Override
		public void run()
		{
			Thread.currentThread().setName("StartAction-" + execution.getAction().getId());

			long millis = 0;
			millis = System.currentTimeMillis();

			log.debug("Executing acion " + execution.getAction().getId() + "( " + millis + ") ...");
			// Añadimos la actuación al conjunto de actuaciones sin finalizar, si no se encontraba ya.
			if (!uncompletedActions.getExecutions().contains(execution))
			{
				synchronized (uncompletedActions)
				{
					uncompletedActions.getExecutions().add(execution);
					engineHelper.saveUncompletedActions(uncompletedActions);
				}
			}

			// Lanzamos la actuación.
			execution.start(ProtocolEngine.this);
			getInfo(execution).setScheduledTime(GregorianCalendar.getInstance());
			getInfo(execution).setStatus(ExecutionInfo.EXEC_RUNNING);

			// if (execution.getResources().conflictWith(PlatformResources.with(Available.BLUETOOTH)))
			// {
			// enableBluetoothIfDisabled();
			// }
		}

	}

	private class MedicalActionStartTimerInvokedTask extends TimerTask {

		private MedicalActionExecution execution;

		public MedicalActionStartTimerInvokedTask(MedicalActionExecution mae)
		{
			execution = mae;
		}

		@Override
		public void run()
		{
			synchronized (engineLock)
			{

				// Reprogramamos el temporizador, para que nos avise al finalizar la ventana de ejecución.
				cancelTask(timerMappings.get(execution));
				// Controlamos que el tiempo asociado a los temporizadores no exceda del máximo soportado por la clase
				long dueTime = new Duration(DateTime.now(), new DateTime(execution.getStartDate()).plusSeconds((int) execution.getTimeWindow())).getStandardSeconds();
				// Creamos una nueva tarea que nos avise al finalizar
				TimerTask task = new MedicalActionExpiredTimerInvokedTask(execution);
				// executor.schedule(task, dueTime, TimeUnit.SECONDS);
				scheduleTask(task, dueTime, TimeUnit.SECONDS);
				timerMappings.put(execution, task);

				// TODO Test
				// ============================================================ //
				ExecutionInfo info = getInfo(execution);
				info.setAbortScheduledTime(new DateTime(execution.getStartDate()).plusSeconds((int) execution.getTimeWindow()).toGregorianCalendar());
				info.setStatus(ExecutionInfo.EXEC_PENDING);
				// ============================================================ //

				log.debug("Action " + execution.getAction().getId() + " expiration sucssesfully scheduled to execute in " + dueTime + " seconds");

				// Añadimos la actuación a la lista de actuaciones pendientes, y la guardamos en el registro de
				// actuaciones no completadas
				pendingActions.add(execution);

				// if (scheduledActions.contains(execution))
				// {
				// scheduledActions.remove(execution);
				// }
			}

			schedule();
		}

	}

	private class MedicalActionAutoFinishTask extends TimerTask {

		private MedicalActionExecution execution;

		public MedicalActionAutoFinishTask(MedicalActionExecution mae)
		{
			execution = mae;
		}

		@Override
		public void run()
		{
			log.debug("Autofinishing " + execution.getAction().getId() + "...");
			execution.finish(ProtocolEngine.this);
			getInfo(execution).setFinishTime(GregorianCalendar.getInstance());
			getInfo(execution).setStatus(ExecutionInfo.EXEC_FINISHED);

		}

	}

	private class MedicalActionExpiredTimerInvokedTask extends TimerTask {

		private MedicalActionExecution execution;

		public MedicalActionExpiredTimerInvokedTask(MedicalActionExecution mae)
		{
			execution = mae;
		}

		@Override
		public void run()
		{
			synchronized (engineLock)
			{
				log.debug("Executing expiration task for " + execution.getAction().getId());

				DateTime startDate = new DateTime(execution.getStartDate());
				// Si la ventana de ejecución de la actuación ha expirado. Liberamos los recursos, y si la actuación
				// está en
				// ejecución,
				// la abortamos.
				if (startDate.plusSeconds((int) execution.getTimeWindow() - 5).isBeforeNow())
				{
					cancelTask(timerMappings.get(execution));
					// Si está en ejecución, la abortamos y ejecutamos el planificador.
					if (executingActions.contains(execution))
					{
						log.debug("Aborting execution " + execution.getAction().getId());
						execution.abort(ProtocolEngine.this);
						getInfo(execution).setFinishTime(GregorianCalendar.getInstance());
						getInfo(execution).setStatus(ExecutionInfo.EXEC_ABORTED);
						// if (scheduledActions.contains(execution))
						// {
						// scheduledActions.remove(execution);
						// }

					} else if (pendingActions.contains(execution))
					{
						deleteAction(execution);
					}
				}
				// En otro caso, modificamos el tiempo del temporizador para seguir acercándonos al momento de
				// expiración
				else
				{
					long dueTime = new Duration(DateTime.now(), new DateTime(execution.getStartDate()).plusSeconds((int) execution.getTimeWindow())).getStandardSeconds();
					log.debug("Reescheduling expiration " + dueTime + " seconds");
					// executor.remove(timerMappings.get(execution));
					// executor.schedule(this, dueTime >= 0 ? dueTime : 0, TimeUnit.SECONDS);
					scheduleTask(this, dueTime >= 0 ? dueTime : 0, TimeUnit.SECONDS);
				}
			}
		}

	}

	private class BeginDayTimerInvokedTask extends TimerTask {

		@Override
		public void run()
		{
			// Invocamos al método de carga de actuaciones
			loadDayActions();
			updateBeginDayTimerTask(false);
		}
	}

	private void updateBeginDayTimerTask(boolean doItNow)
	{

		log.debug("Updating day actions...");

		if (beginDayTimerTask != null)
		{
			beginDayTimerTask.cancel();
			// executor.remove(beginDayTimerTask);
		}

		// Y programamos el temporizador para las 00:00 del día siguiente.
		Duration timeToMidnight = new Duration(DateTime.now(), new DateMidnight().plusDays(1).toDateTime().plusSeconds(10));

		// Duration timeToMidnight = new Duration(DateTime.now(), DateTime.now().plusSeconds(30));
		beginDayTimerTask = new BeginDayTimerInvokedTask();


		// executor.schedule(beginDayTimerTask, doItNow ? 1000 : (timeToMidnight.getMillis()), TimeUnit.MILLISECONDS);
		scheduleTask(beginDayTimerTask, doItNow ? 1 : (timeToMidnight.getStandardSeconds()), TimeUnit.SECONDS);

		log.debug("Next update scheduled to within " + timeToMidnight.toStandardMinutes().getMinutes() + " minutes");
	}

	/**
	 * Añade una ejecución de actuación médica puntual al protocolo del paciente. Añadir una ejecución puntual generará
	 * una {@link ProtocolAction} completa que encapsula unicamente a una actuación, por lo que es recomendable añadir
	 * siempre que sea posible una {@link ProtocolAction} completa que permita la definición de más de una
	 * {@link MedicalActionExecution}
	 * 
	 * @param execution The execuion to add
	 */

	public void addToProtocol(MedicalActionExecution execution)
	{
		// Creamos una acción de protocolo que encapsule a la actuación solicitada, y configuramos correctamente el
		// StartGap.
		ProtocolAction wrap = new ProtocolAction(execution);
		wrap.setStartGap(new Duration(new DateTime(protocol.getStartDate()), new DateTime(execution.getStartDate())).getStandardSeconds());
		// Y añadimos la acción de protocolo correspondiente.
		addToProtocol(wrap);
	}

	/**
	 * Añade una nueva acción de protocolo al protocolo actual. La acción deberá tener todos los atributos inicializados
	 * correctamente, y no encontrarse ya en el protocolo.
	 * 
	 * @param protocolAction The action to add
	 */
	public void addToProtocol(ProtocolAction protocolAction)
	{
		if (protocolAction.getAction() == null || protocolAction.getAction().getProvider() == null)
		{
			throw new IllegalArgumentException("La acción deberá tener una actuación válida, y con un servicio proveedor");
		}
		if (protocol.getActions().contains(protocolAction))
		{
			throw new IllegalArgumentException("La acción ya se encuentra en el protocolo");
		}
		if (protocolAction.getStartGap() < 0)
		{
			throw new IllegalArgumentException("La acción no puede tener un momento de inicio anterior al inicio de aplicación del protocolo.");
		}
		// Añadimos la actuación al protocolo, y lo guardamos.
		protocol.getActions().add(protocolAction);
		engineHelper.saveProtocol(protocol);
		// Volvemos a cargar las actuaciones del día de hoy, por si hay modificaciones
		loadDayActions();
		// Lanzamos el evento de modificación del protocolo.
		raiseOnProtocolChangeEvent();
	}

	private List<MedicalActionExecution> getDayActions(Calendar c)
	{
		return engineHelper.getDayActions(c);
	}

	public Collection<ExecutionInfo> getActionsInfo()
	{
		return executionsInfo.values();
	}

	// Unused
	public MedicalActionExecution getExecutingAction(int id)
	{
		for (MedicalActionExecution e : executingActions)
		{
			if (e.getUniqueId() == id)
			{
				return e;
			}
		}
		return null;
	}

	@Override
	public ProtocolEngine getProtocolEngine()
	{
		return this;
	}

	private ExecutionInfo getInfo(MedicalActionExecution e)
	{
		ExecutionInfo info = executionsInfo.get(e);

		if (info == null)
		{
			info = new ExecutionInfo(e);
			info.setInfoListener(this);
			executionsInfo.put(e, info);

		}

		return info;

	}

	@Override
	public void onStatusChange(ExecutionInfo self)
	{

		Calendar abort = self.getAbortScheduledTime();
		Calendar start = self.getScheduledTime();
		Calendar finish = self.getFinishTime();

		Log.d("EXECUTION_INFO",
				self.getExecution().getAction().getId() + " [status: " + self.getStatus() + ", " + "start: "
						+ (start != null ? sdf.format(start.getTime()) : "null") + ", " + "abort: "
						+ (abort != null ? sdf.format(abort.getTime()) : "null") + ", " + "finish: "
						+ (finish != null ? sdf.format(finish.getTime()) : "null"));
	}
}
