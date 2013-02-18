/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package es.usc.citius.servando.android.models.protocol;

import java.util.ArrayList;
import java.util.List;

import org.simpleframework.xml.Default;
import org.simpleframework.xml.DefaultType;
import org.simpleframework.xml.Element;
import org.simpleframework.xml.ElementList;
import org.simpleframework.xml.Order;
import org.simpleframework.xml.convert.Convert;

import es.usc.citius.servando.android.models.util.ParameterList;
import es.usc.citius.servando.android.xml.converters.DurationConverter;

/**
 * Clase que representa una acción de un protocolo médico. Consiste en una actuación médica que comienza su ejecución en
 * una fecha determinada a partir del comienzo de aplicación del protocolo, y que se repite periódicamente hasta una
 * fecha determinada.
 * 
 * @author Tomás Teijeiro Campo
 */
@Default(DefaultType.FIELD)
@Order(elements = { "action", "priority", "startGap", "interval", "duration", "timewindow", "parameters", "exceptions" })
public class ProtocolAction {

	/**
	 * Actuación médica que se ejecuta.
	 */
	@Element(name = "action")
	private MedicalAction action;
	/**
	 * Diferencia de tiempo en segundos entre el momento de inicio de aplicación del protocolo MedicalProtocol, y el
	 * comienzo de esta actuación.
	 */
	@Element(name = "startGap")
	@Convert(value = DurationConverter.class)
	private Long startGap;
	/**
	 * Intervalo de periodicidad en segundos para la actuación médica. Si vale 0 , significa que la actuación no se
	 * repetirá, y la propiedad Duration será ignorada.
	 */
	@Element(name = "interval")
	@Convert(value = DurationConverter.class)
	private Long interval;
	/**
	 * Duración de la acción en segundos, es decir, indica la finalización en la repetición de la actuación médica
	 * asociada. Normalmente este valor será un múltiplo de Interval. Si vale 0, significa que la actuación se repetirá
	 * durante toda la ejecución del protocolo.
	 */
	@Element(name = "duration")
	@Convert(value = DurationConverter.class)
	private Long duration;
	/**
	 * Ventana de tiempo (en segundos) durante la cual se puede ejecutar la actuación en cada una de sus repeticiones.
	 * Es equivalente a la propiedad MedicalActionExecution.timeWindow.
	 */
	@Element(name = "timewindow")
	@Convert(value = DurationConverter.class)
	private Long timeWindow;
	/**
	 * Prioridad en las ejecuciones de las actuaciones médicas. Equivalente a MedicalActionExecution.priority.
	 */
	@Element(name = "priority")
	private MedicalActionPriority priority;

	/**
	 * Lista de repeticiones de la actuación que no serán generadas. Esta propiedad permite añadir excepciones a la
	 * regla de repetición especificada en Interval. Cada uno de los elementos de esta lista se interpretará como el
	 * número de repetición de la actuación que no será generada, comenzando en 0. Así, si por ejemplo esta lista
	 * contiene el valor '3', la MedicalActionExecution con fecha de inicio StartGap + 3* Interval no sería generada.
	 * 
	 * Si no contiene ningún valor, esta propiedad deberá estar establecida a null. En otro caso, se producirán errores
	 * en la carga y transmisión de objetos de este tipo.
	 */
	@ElementList(name = "exceptions", type = Integer.class, inline = false, required = false)
	private List<Integer> exceptions;

	/**
	 * Parámetros de configuración que se aplicarán a las diferentes ejecuciones de la actuación correspondiente.
	 * Equivalente a MedicalActionExecution.parameters
	 */
	@Element(name = "parameters", required = false)
	private ParameterList parameters;

	public ProtocolAction()
	{
		parameters = new ParameterList();
		exceptions = new ArrayList<Integer>();
		priority = MedicalActionPriority.Normal;
	}

	/**
	 * 
	 Crea una nueva acción de protocolo, que encapsula a una única ejecución de actuación médica. <remarks>La fecha de
	 * inicio relativa de la acción creada <see cref="StartGap"/> será 0, ignorando la propiedad <see
	 * cref="MedicalActionExecution.StartDate"/> de <see cref="wrapped"/>. Una vez creada la acción, se debería
	 * establecer convenientemente esta propiedad a partir de la fecha de comienzo del <see cref="MedicalProtocol"/>
	 * asociado
	 * 
	 * <param name="wrapped">Instancia de actuación médica encapsulada.</param>
	 * 
	 */
	public ProtocolAction(MedicalActionExecution wrapped)
	{
		this();
		parameters = wrapped.getParameters();
		action = wrapped.getAction();
		timeWindow = (long) wrapped.getTimeWindow();
		priority = wrapped.getPriority();
		interval = (long) 0;
		duration = (long) 0;
		startGap = (long) 0;
	}

	/**
	 * Actuación médica que se ejecuta.
	 * 
	 * @return the action
	 */
	public MedicalAction getAction()
	{
		return action;
	}

	/**
	 * Actuación médica que se ejecuta.
	 * 
	 * @param action the action to set
	 */
	public void setAction(MedicalAction action)
	{
		this.action = action;
	}

	/**
	 * Diferencia de tiempo en segundos entre el momento de inicio de aplicación del protocolo MedicalProtocol, y el
	 * comienzo de esta actuación.
	 * 
	 * @return the startGap
	 */
	public long getStartGap()
	{
		return startGap;
	}

	/**
	 * Diferencia de tiempo en segundos entre el momento de inicio de aplicación del protocolo MedicalProtocol, y el
	 * comienzo de esta actuación.
	 * 
	 * @param startGap the startGap to set
	 */
	public void setStartGap(long startGap)
	{
		this.startGap = startGap;
	}

	/**
	 * Intervalo de periodicidad en segundos para la actuación médica. Si vale 0 , significa que la actuación no se
	 * repetirá, y la propiedad Duration será ignorada.
	 * 
	 * @return the interval
	 */
	public long getInterval()
	{
		return interval;
	}

	/**
	 * Intervalo de periodicidad en segundos para la actuación médica. Si vale 0 , significa que la actuación no se
	 * repetirá, y la propiedad Duration será ignorada.
	 * 
	 * @param interval the interval to set
	 */
	public void setInterval(long interval)
	{
		this.interval = interval;
	}

	/**
	 * Duración de la acción en segundos, es decir, indica la finalización en la repetición de la actuación médica
	 * asociada. Normalmente este valor será un múltiplo de Interval. Si vale 0, significa que la actuación se repetirá
	 * durante toda la ejecución del protocolo.
	 * 
	 * @return the duration
	 */
	public long getDuration()
	{
		return duration;
	}

	/**
	 * Duración de la acción en segundos, es decir, indica la finalización en la repetición de la actuación médica
	 * asociada. Normalmente este valor será un múltiplo de Interval. Si vale 0, significa que la actuación se repetirá
	 * durante toda la ejecución del protocolo.
	 * 
	 * @param duration the duration to set
	 */
	public void setDuration(long duration)
	{
		this.duration = duration;
	}

	/**
	 * Ventana de tiempo (en segundos) durante la cual se puede ejecutar la actuación en cada una de sus repeticiones.
	 * Es equivalente a la propiedad MedicalActionExecution.timeWindow.
	 * 
	 * @return the timeWindow
	 */
	public long getTimeWindow()
	{
		return timeWindow;
	}

	/**
	 * Ventana de tiempo (en segundos) durante la cual se puede ejecutar la actuación en cada una de sus repeticiones.
	 * Es equivalente a la propiedad MedicalActionExecution.timeWindow.
	 * 
	 * @param timeWindow the timeWindow to set
	 */
	public void setTimeWindow(long timeWindow)
	{
		this.timeWindow = timeWindow;
	}

	/**
	 * Prioridad en las ejecuciones de las actuaciones médicas. Equivalente a MedicalActionExecution.priority.
	 * 
	 * @return the priority
	 */
	public MedicalActionPriority getPriority()
	{
		return priority;
	}

	/**
	 * Prioridad en las ejecuciones de las actuaciones médicas. Equivalente a MedicalActionExecution.priority.
	 * 
	 * @param priority the priority to set
	 */
	public void setPriority(MedicalActionPriority priority)
	{
		this.priority = priority;
	}

	/**
	 * Parámetros de configuración que se aplicarán a las diferentes ejecuciones de la actuación correspondiente.
	 * Equivalente a MedicalActionExecution.parameters
	 * 
	 * @return the parameters
	 */
	public ParameterList getParameters()
	{
		return parameters;
	}

	/**
	 * Parámetros de configuración que se aplicarán a las diferentes ejecuciones de la actuación correspondiente.
	 * Equivalente a MedicalActionExecution.parameters
	 * 
	 * @param parameters the parameters to set
	 */
	public void setParameters(ParameterList parameters)
	{
		this.parameters = parameters;
	}

	/**
	 * Lista de repeticiones de la actuación que no serán generadas. Esta propiedad permite añadir excepciones a la
	 * regla de repetición especificada en Interval. Cada uno de los elementos de esta lista se interpretará como el
	 * número de repetición de la actuación que no será generada, comenzando en 0. Así, si por ejemplo esta lista
	 * contiene el valor '3', la MedicalActionExecution con fecha de inicio StartGap + 3* Interval no sería generada.
	 * 
	 * Si no contiene ningún valor, esta propiedad deberá estar establecida a null. En otro caso, se producirán errores
	 * en la carga y transmisión de objetos de este tipo.
	 * 
	 * @return the exceptions
	 */
	public List<Integer> getExceptions()
	{
		return exceptions;
	}

	/**
	 * Lista de repeticiones de la actuación que no serán generadas. Esta propiedad permite añadir excepciones a la
	 * regla de repetición especificada en Interval. Cada uno de los elementos de esta lista se interpretará como el
	 * número de repetición de la actuación que no será generada, comenzando en 0. Así, si por ejemplo esta lista
	 * contiene el valor '3', la MedicalActionExecution con fecha de inicio StartGap + 3* Interval no sería generada.
	 * 
	 * Si no contiene ningún valor, esta propiedad deberá estar establecida a null. En otro caso, se producirán errores
	 * en la carga y transmisión de objetos de este tipo.
	 * 
	 * @param exceptions the exceptions to set
	 */
	public void setExceptions(ArrayList<Integer> exceptions)
	{
		this.exceptions = exceptions;
	}

	@Override
	public String toString()
	{
		StringBuilder builder = new StringBuilder();
		builder.append("ProtocolAction[");
		if (action != null && action.getId() != null)
		{
			builder.append("actionId=");
			builder.append(action.getId());
			builder.append(", ");
		}
		if (startGap != null)
		{
			builder.append("startGap=");
			builder.append(startGap);
			builder.append(", ");
		}
		if (interval != null)
		{
			builder.append("interval=");
			builder.append(interval);
			builder.append(", ");
		}
		if (duration != null)
		{
			builder.append("duration=");
			builder.append(duration);
			builder.append(", ");
		}
		if (timeWindow != null)
		{
			builder.append("timeWindow=");
			builder.append(timeWindow);
			builder.append(", ");
		}
		if (priority != null)
		{
			builder.append("priority=");
			builder.append(priority);
		}
		builder.append("]");
		return builder.toString();
	}

}
