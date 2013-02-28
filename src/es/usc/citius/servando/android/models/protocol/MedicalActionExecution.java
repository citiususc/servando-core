/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package es.usc.citius.servando.android.models.protocol;

import java.util.GregorianCalendar;

import org.simpleframework.xml.Default;
import org.simpleframework.xml.DefaultType;
import org.simpleframework.xml.Element;
import org.simpleframework.xml.Root;
import org.simpleframework.xml.Transient;
import org.simpleframework.xml.convert.Convert;

import android.content.Context;
import android.graphics.Color;
import es.usc.citius.servando.R;
import es.usc.citius.servando.android.agenda.AgendaEvent;
import es.usc.citius.servando.android.agenda.MedicalActionExecutionListener;
import es.usc.citius.servando.android.agenda.MedicalActionExecutor;
import es.usc.citius.servando.android.agenda.PlatformResources;
import es.usc.citius.servando.android.logging.ILog;
import es.usc.citius.servando.android.logging.ServandoLoggerFactory;
import es.usc.citius.servando.android.models.util.Parameter;
import es.usc.citius.servando.android.models.util.ParameterList;
import es.usc.citius.servando.android.xml.converters.GregorianCalendarConverter;

/**
 * Esta clase representa la ejecución de una actuación médica. Agrupa una actuación médica junto con una fecha de inicio
 * de la misma, y una ventana de tiempo durante la cual se puede realizar la actuación.
 */
@Root(name = "MedicalActionExecution")
@Default(DefaultType.FIELD)
public class MedicalActionExecution implements AgendaEvent {

	/**
	 * Servando paltform logger for this class
	 */
	private static final ILog log = ServandoLoggerFactory.getLogger(MedicalActionExecution.class);

	@Transient
	private int uniqueId = 0;

	@Element(name = "action")
	protected MedicalAction action;

	@Element(name = "start")
	@Convert(value = GregorianCalendarConverter.class)
	protected GregorianCalendar startDate;

	@Element(name = "timeWindow")
	protected long timeWindow;

	@Element(name = "state")
	protected MedicalActionState state;

	@Element(name = "priority")
	protected MedicalActionPriority priority;
	/**
	 * Parámetros adicionales de la actuación. Los servicios pueden valerse de este campo o, si lo prefieren, definir
	 * sus propios atributos en clases derivadas. Estos parámetros serán los que se mostrarán al usuario en la interfaz
	 * principal. Se recomienda utilizar este campo para la configuración de actuaciones siempre que sea posible, pues
	 * esta información será guardada en todo momento por el motor de persistencia de la plataforma.
	 */
	@Element(name = "parameters")
	protected ParameterList parameters;

	@Transient
	protected PlatformResources resources;

	@Transient
	private int color = -1;

	@Transient
	private int icon = -1;

	@Transient
	protected MedicalActionExecutionListener listener;

	/**
	 * Máxima ventana de tiempo a considerar para una actuación médica
	 */
	public static final long MaxTimeWindow = Long.MAX_VALUE;

	public MedicalActionExecution()
	{
		startDate = new GregorianCalendar();
		timeWindow = MaxTimeWindow;
		state = MedicalActionState.NotStarted;
		priority = MedicalActionPriority.Normal;
		resources = new PlatformResources();
	}

	public MedicalActionExecution(MedicalAction action, ParameterList parameters, MedicalActionPriority priority, GregorianCalendar start, long timeWindow)
	{
		startDate = start;
		this.timeWindow = timeWindow;
		state = MedicalActionState.NotStarted;
		this.priority = priority;
		this.action = action;
		this.parameters = parameters;
		resources = new PlatformResources();
	}

	public MedicalActionExecution(MedicalAction action, ParameterList parameters, MedicalActionPriority priority, GregorianCalendar start, long timeWindow, MedicalActionState state)
	{
		this(action, parameters, priority, start, timeWindow);
		this.state = state;
	}

	/**
	 * Actuación médica a ejectuar.
	 * 
	 * @return the Action
	 */
	public MedicalAction getAction()
	{
		return action;
	}

	/**
	 * Actuación médica a ejectuar.
	 * 
	 * @param Action the Action to set
	 */
	public void setAction(MedicalAction Action)
	{
		action = Action;
	}

	/**
	 * Momento en el que debe comenzar la actuación.
	 * 
	 * @return the StartDate
	 */
	public GregorianCalendar getStartDate()
	{
		return startDate;
	}

	/**
	 * Momento en el que debe comenzar la actuación.
	 * 
	 * @param StartDate the StartDate to set
	 */
	public void setStartDate(GregorianCalendar StartDate)
	{
		startDate = StartDate;
	}

	/**
	 * Ventana de tiempo (en segundos) durante la cual se puede ejecutar la actuación.
	 * 
	 * @return the TimeWindow
	 */
	public long getTimeWindow()
	{
		return timeWindow;
	}

	/**
	 * Ventana de tiempo (segundos) durante la cual se puede ejecutar la actuación.
	 * 
	 * @param TimeWindow the TimeWindow to set, con el valor máximo @see MedicalActionExecution.MaxTimeWindow
	 */
	public void setTimeWindow(long TimeWindow)
	{
		if (TimeWindow > MaxTimeWindow)
		{
			throw new IllegalArgumentException("TimeWindow must be <= " + MaxTimeWindow);
		}
		timeWindow = TimeWindow;
	}

	/**
	 * Estado de la actuación médica.
	 * 
	 * @return the State
	 */
	public MedicalActionState getState()
	{
		return state;
	}

	/**
	 * Estado de la actuación médica.
	 * 
	 * @param State the State to set
	 */
	public void setState(MedicalActionState State)
	{
		state = State;
	}

	/**
	 * @return the priority
	 */
	public MedicalActionPriority getPriority()
	{
		return priority;
	}

	/**
	 * @param priority the priority to set
	 */
	public void setPriority(MedicalActionPriority priority)
	{
		this.priority = priority;
	}

	/**
	 * Parámetros adicionales de la actuación. Los servicios pueden valerse de este campo o, si lo prefieren, definir
	 * sus propios atributos en clases derivadas. Estos parámetros serán los que se mostrarán al usuario en la interfaz
	 * principal. Se recomienda utilizar este campo para la configuración de actuaciones siempre que sea posible, pues
	 * esta información será guardada en todo momento por el motor de persistencia de la plataforma.
	 * 
	 * @return the parameters
	 */
	public ParameterList getParameters()
	{
		return parameters;
	}

	/**
	 * Parámetros adicionales de la actuación. Los servicios pueden valerse de este campo o, si lo prefieren, definir
	 * sus propios atributos en clases derivadas. Estos parámetros serán los que se mostrarán al usuario en la interfaz
	 * principal. Se recomienda utilizar este campo para la configuración de actuaciones siempre que sea posible, pues
	 * esta información será guardada en todo momento por el motor de persistencia de la plataforma.
	 * 
	 * @param parameters the parameters to set
	 */
	public void setParameters(ParameterList parameters)
	{
		this.parameters = parameters;
	}

	/**
	 * @param listener the listener to set
	 */
	public void setExecutionListener(MedicalActionExecutionListener listener)
	{
		this.listener = listener;
	}

	/**
	 * @param listener the listener to set
	 */
	public void removeExecutionListener()
	{
		listener = null;
	}

	public void start(Context ctx)
	{

		log.debug("Starting  " + getAction().getId() + ", state: " + state);
		// Si el estado era no comenzado, lo cambiamos a no completado (en otro caso, no lo modificamos)
		if (state == MedicalActionState.NotStarted)
		{
			state = MedicalActionState.Uncompleted;
			MedicalActionExecutor.startExecution(this, ctx);
		}

		if (listener != null)
		{
			listener.onStart(this);
		}

	}

	public void abort(Context ctx)
	{
		// Si el estado era no comenzado, lo cambiamos a no completado (en otro caso, no lo modificamos)
		state = MedicalActionState.Failed;

		log.debug("Execution " + getAction().getId() + " aborted! Listener is " + (listener != null ? " not" : "") + " null");
		MedicalActionExecutor.abortOrFinish(this, ctx);

		if (listener != null)
		{
			listener.onAbort(this);
		}

	}

	public void finish(Context ctx)
	{
		state = MedicalActionState.Completed;
		MedicalActionExecutor.abortOrFinish(this, ctx);

		if (listener != null)
		{
			listener.onFinish(this);
		}
	}

	/**
	 * @return the resources
	 */
	public PlatformResources getResources()
	{
		return resources;
	}

	/**
	 * @param resources the resources to set
	 */
	public void setResources(PlatformResources resources)
	{
		this.resources = resources;
		if (listener != null)
		{
			listener.onResourcesChange(this);
		}
	}

	@Override
	public String toString()
	{
		StringBuilder builder = new StringBuilder();
		builder.append("MedicalActionExecution[\n");
		if (action != null)
		{
			builder.append("action=");
			builder.append(action.getId());
			builder.append(",\n ");
		}

		builder.append("timeWindow=");
		builder.append(timeWindow);
		builder.append(",\n ");
		if (state != null)
		{
			builder.append("state=");
			builder.append(state);
			builder.append(",\n ");
		}
		if (priority != null)
		{
			builder.append("priority=");
			builder.append(priority);
			builder.append(",\n ");
		}
		if (resources != null)
		{
			builder.append("resources=");
			builder.append(Integer.toBinaryString(resources.getValue()));
			builder.append(",\n ");
		}
		if (parameters != null)
		{

			builder.append("parameters=[");
			for (Parameter p : parameters.getParameters())
			{
				builder.append("{" + p.getName() + ":" + p.getValue() + "},");
			}
			builder.append("]");
		}
		builder.append("]");
		return builder.toString();
	}

	@Override
	public int getIcon()
	{
		return icon != -1 ? icon : R.drawable.ic_servando_notification;
	}

	@Override
	public String getTitle()
	{

		return action.getDisplayName();
	}

	@Override
	public int getColor()
	{
		return color != -1 ? color : Color.CYAN;
	}

	/**
	 * @param color the color to set
	 */
	public void setColor(int color)
	{
		this.color = color;
	}

	/**
	 * @param icon the icon to set
	 */
	public void setIcon(int icon)
	{
		this.icon = icon;
	}

	/**
	 * @return the uniqueId
	 */
	public int getUniqueId()
	{
		return uniqueId;
	}

	/**
	 * @param uniqueId the uniqueId to set
	 */
	public void setUniqueId(int uniqueId)
	{
		this.uniqueId = uniqueId;
	}
	
	
	/// <summary>
    /// Indicates whether the current object is equal to another object of the same type.
    /// </summary>
    /// <param name="other">An object to compare with this object.</param>
    /// <returns>
    /// true if the current object is equal to the <paramref name="other"/> parameter; otherwise, false. Para evaluar
    /// la igualdad, se comprobará que la actuación médica tenga el mismo identificador de actuación, la misma prioridad,
    /// la misma fecha de inicio, y la misma ventana temporal.
    /// </returns>
	@Override
    public boolean equals(Object o)
    {
		if(!(o instanceof MedicalActionExecution)){
			return false;
		}
		
		MedicalActionExecution other = (MedicalActionExecution)o;
        boolean equals = other.getPriority().equals(priority) && other.getStartDate().equals(startDate) && other.getTimeWindow() == timeWindow;
        if (other.getAction()!=null && action!=null)
        {
            equals &= other.getAction().getId().equals(action.getId());
        }
        // Si al final se decide incluír también los parámetros en la igualdad, descomentar esta sección.
        //comprobamos los parámetros.
        if (equals && getParameters()!=null && other.getParameters()!=null && getParameters().size()==other.getParameters().size())
        {
        	for(Parameter p : parameters.getParameters()){
        		String k = p.getName();
        		String v = p.getValue();
        		String op = other.getParameters().get(k);
        		if(!(op != null && op.equals(v))){
        			equals = false;
        			break;
        		}
        	}
        	
        	if(equals){
        		for(Parameter p : other.getParameters().getParameters()){
        			String k = p.getName();
            		String v = p.getValue();
            		String op = parameters.get(k);
            		if(!(op != null && op.equals(v))){
            			equals = false;
            			break;
            		}
        		}
        	}
        }
        else
        {
            equals = false;
        }
         
        return equals;
    }

}
