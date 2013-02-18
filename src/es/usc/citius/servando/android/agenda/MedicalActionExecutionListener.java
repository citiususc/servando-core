package es.usc.citius.servando.android.agenda;

import es.usc.citius.servando.android.models.protocol.MedicalActionExecution;

public interface MedicalActionExecutionListener {

	public void onStart(MedicalActionExecution target);

	public void onAbort(MedicalActionExecution target);

	public void onFinish(MedicalActionExecution target);

	public void onResourcesChange(MedicalActionExecution target);

}
