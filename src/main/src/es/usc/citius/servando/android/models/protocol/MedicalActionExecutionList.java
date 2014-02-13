package es.usc.citius.servando.android.models.protocol;

import java.util.ArrayList;
import java.util.List;

import org.simpleframework.xml.Default;
import org.simpleframework.xml.DefaultType;
import org.simpleframework.xml.ElementList;

import android.util.Log;

/**
 * Esta clase agrupa un conjunto de {@link MedicalActionExecution}, permitiendo su persistencia o transmisión a través
 * del módulo de comunicaciones.
 * 
 * @author Ángel Piñeiro
 */
@Default(DefaultType.FIELD)
public class MedicalActionExecutionList {

	@ElementList(type = MedicalActionExecution.class, inline = true)
	private List<MedicalActionExecution> executions;

	public MedicalActionExecutionList()
	{
		executions = new ArrayList<MedicalActionExecution>();
	}

	public MedicalActionExecutionList(List<MedicalActionExecution> defaultActions)
	{
		executions = new ArrayList<MedicalActionExecution>();
		executions.addAll(defaultActions);
	}

	public List<MedicalActionExecution> getExecutions()
	{
		return executions;
	}

	public void setExecutions(List<MedicalActionExecution> executions)
	{
		this.executions = executions;
	}

	public boolean contains(MedicalActionExecution c)
	{
		boolean contains = false;
		for (MedicalActionExecution e : executions)
		{
			if (MedicalActionExecutionComparator.getInstance().compare(c, e) == 0)
			{
				Log.d("Comparator", "Compare true");
				contains = true;
				break;
			}
		}
		return contains;
	}

}
