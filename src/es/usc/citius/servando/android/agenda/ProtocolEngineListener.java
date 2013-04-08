package es.usc.citius.servando.android.agenda;

import es.usc.citius.servando.android.models.protocol.MedicalActionExecution;

public interface ProtocolEngineListener {

	public void onExecutionStart(MedicalActionExecution target);

	public void onExecutionAbort(MedicalActionExecution target);

	public void onExecutionFinish(MedicalActionExecution target);

	public void onLoadDayActions();

	public void onProtocolChanged();

	public void onProtocolEngineStart();

	public void onReminder(long minutes);
}

