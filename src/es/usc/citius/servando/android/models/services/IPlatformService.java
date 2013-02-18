/**
 *
 */
package es.usc.citius.servando.android.models.services;

import java.util.Calendar;
import java.util.List;

import es.usc.citius.servando.android.agenda.PlatformResources;
import es.usc.citius.servando.android.models.protocol.MedicalAction;
import es.usc.citius.servando.android.models.protocol.MedicalActionExecution;
import es.usc.citius.servando.android.models.protocol.MedicalActionPriority;
import es.usc.citius.servando.android.models.util.ParameterList;

/**
 * @author angel
 * 
 */
public interface IPlatformService {

	public String getId();

	public void onPlatformStarted();

	public void onPlatformStopping();

	public List<MedicalAction> getProvidedActions();

	public MedicalActionExecution newExecution(MedicalAction action, PlatformResources neededResources, ParameterList parameters,
			MedicalActionPriority priority, Calendar startDate, long timeWindow);

	public MedicalActionExecution restoreExecution(MedicalAction action, PlatformResources neededResources, ParameterList parameters,
			MedicalActionPriority priority, Calendar startDate, long timeWindow);

}
