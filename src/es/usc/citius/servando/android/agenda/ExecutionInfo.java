package es.usc.citius.servando.android.agenda;

import java.util.Calendar;

import es.usc.citius.servando.android.models.protocol.MedicalActionExecution;

public class ExecutionInfo {

	public static final String EXEC_NOT_SCHEDULED = "Execution scheduled";
	public static final String EXEC_SCHEDULED = "Execution scheduled";
	public static final String EXEC_BLOCKED = "Execution blocked";
	public static final String EXEC_PENDING = "Execution pending";
	public static final String EXEC_PENDING_CONFLICT = "Execution pending (Conflict)";
	public static final String EXEC_RUNNING = "Execution running";
	public static final String EXEC_FINISHED = "Execution finished";
	public static final String EXEC_ABORTED = "Execution aborted";

	private MedicalActionExecution execution;

	private Calendar scheduledTime;

	private Calendar abortScheduledTime;

	private Calendar finishTime;

	private boolean blocked = false;

	private String status = EXEC_NOT_SCHEDULED;

	private ExecutionInfoListener listener;

	public ExecutionInfo()
	{

	}

	public ExecutionInfo(MedicalActionExecution execution)
	{
		super();
		this.execution = execution;
	}

	public ExecutionInfo(MedicalActionExecution execution, Calendar scheduledTime)
	{
		this(execution);
		this.scheduledTime = scheduledTime;
	}

	public MedicalActionExecution getExecution()
	{
		return execution;
	}

	public void setExecution(MedicalActionExecution execution)
	{
		this.execution = execution;
	}

	public Calendar getScheduledTime()
	{
		return scheduledTime;
	}

	public void setScheduledTime(Calendar scheduledTime)
	{
		this.scheduledTime = scheduledTime;
	}

	public boolean isBlocked()
	{
		return blocked;
	}

	public void setBlocked(boolean blocked)
	{
		this.blocked = blocked;
	}

	/**
	 * @return the abortScheduledTime
	 */
	public Calendar getAbortScheduledTime()
	{
		return abortScheduledTime;
	}

	/**
	 * @param abortScheduledTime the abortScheduledTime to set
	 */
	public void setAbortScheduledTime(Calendar abortScheduledTime)
	{
		this.abortScheduledTime = abortScheduledTime;
	}

	/**
	 * @return the status
	 */
	public String getStatus()
	{
		return status;
	}

	/**
	 * @param status the status to set
	 */
	public void setStatus(String status)
	{

		this.status = status;
		if (listener != null)
		{
			listener.onStatusChange(this);
		}
	}

	/**
	 * @return the listener
	 */
	public void removeInfoListener()
	{
		listener = null;
	}

	/**
	 * @param listener the listener to set
	 */
	public void setInfoListener(ExecutionInfoListener listener)
	{
		this.listener = listener;
	}

	/**
	 * @return the finishTime
	 */
	public Calendar getFinishTime()
	{
		return finishTime;
	}

	/**
	 * @param finishTime the finishTime to set
	 */
	public void setFinishTime(Calendar finishTime)
	{
		this.finishTime = finishTime;
	}

	public interface ExecutionInfoListener {

		public void onStatusChange(ExecutionInfo self);

	}

}
