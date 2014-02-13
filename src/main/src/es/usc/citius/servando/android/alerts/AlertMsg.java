package es.usc.citius.servando.android.alerts;

import java.text.SimpleDateFormat;
import java.util.GregorianCalendar;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.Root;
import org.simpleframework.xml.convert.Convert;

import es.usc.citius.servando.android.models.util.ParameterList;
import es.usc.citius.servando.android.xml.converters.GregorianCalendarConverter;

@Root(name = "alert")
public class AlertMsg {

	public AlertMsg()
	{
	}

	public AlertMsg(AlertType type, String displayName, String description)
	{
		super();
		this.type = type;
		this.displayName = displayName;
		this.description = description;
		this.timeStamp = new GregorianCalendar();
	}

	public AlertMsg(AlertType type, String displayName, String description, ParameterList parameters)
	{
		this(type, displayName, description);
		this.parameters = parameters;
	}

	public AlertMsg(AlertType type, String displayName, String description, String patientMsg)
	{
		this(type, displayName, description);
		this.patientMsg = patientMsg;
	}

	public AlertMsg(AlertType type, String displayName, String description, ParameterList parameters, String patientMsg)
	{
		this(type, displayName, description, parameters);
		this.patientMsg = patientMsg;
	}

	public AlertMsg(AlertType type, String displayName, String description, GregorianCalendar timestamp)
	{
		this(type, displayName, description);
		this.timeStamp = timestamp;
	}

	public AlertMsg(AlertType type, String displayName, String description, ParameterList parameters, GregorianCalendar timestamp)
	{
		this(type, displayName, description, parameters);
		this.timeStamp = timestamp;
	}

	public AlertMsg(AlertType type, String displayName, String description, String patientMsg, GregorianCalendar timeStamp)
	{
		this(type, displayName, description, patientMsg);
		this.timeStamp = timeStamp;
	}

	public AlertMsg(AlertType type, String displayName, String description, ParameterList parameters, String patientMsg, GregorianCalendar timeStamp)
	{
		this(type, displayName, description, parameters, patientMsg);
		this.timeStamp = timeStamp;
	}

	@Element(name = "type")
	private AlertType type;

	@Element(name = "displayName", required = false)
	private String displayName;

	@Element(name = "patientMsg", required = false)
	private String patientMsg;

	@Element(name = "description", required = false)
	private String description;

	@Element(name = "parameters", required = false)
	private ParameterList parameters;

	@Element(name = "timeStamp")
	@Convert(value = GregorianCalendarConverter.class)
	private GregorianCalendar timeStamp;

	public AlertType getType()
	{
		return type;
	}

	public void setType(AlertType type)
	{
		this.type = type;
	}

	public String getDisplayName()
	{
		return displayName;
	}

	public void setDisplayName(String displayName)
	{
		this.displayName = displayName;
	}

	public String getDescription()
	{
		return description;
	}

	public void setDescription(String description)
	{
		this.description = description;
	}

	public ParameterList getParameters()
	{
		return parameters;
	}

	public void setParameters(ParameterList parameters)
	{
		this.parameters = parameters;
	}

	@Override
	public String toString()
	{
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

		return "AlertMsg [type=" + type + ", displayName=" + displayName + ", patientMsg= " + patientMsg + ", description=" + description
				+ " , timeStamp= " + df.format(timeStamp.getTime()) + "]";
	}

	public void addParameter(String k, String value)
	{
		if (parameters == null)
			parameters = new ParameterList();

		parameters.put(k, value);
	}

	public String getPatientMsg()
	{
		return patientMsg;
	}

	public void setPatientMsg(String patientMsg)
	{
		this.patientMsg = patientMsg;
	}

	public GregorianCalendar getTimeStamp()
	{
		return timeStamp;
	}

	public void setTimeStamp(GregorianCalendar timeStamp)
	{
		this.timeStamp = timeStamp;
	}

	public static class Builder {

		private AlertMsg msg;

		public Builder()
		{
			msg = new AlertMsg();
		}

		public AlertMsg create()
		{
			if (msg.timeStamp == null)
			{
				msg.timeStamp = new GregorianCalendar();
			}
			return msg;
		}

		public Builder setType(AlertType type)
		{
			msg.type = type;
			return this;
		}

		public Builder setDisplayName(String displayName)
		{
			msg.displayName = displayName;
			return this;
		}

		public Builder setDescription(String description)
		{
			msg.description = description;
			return this;
		}

		public Builder addParameter(String k, String value)
		{
			if (msg.parameters == null)
				msg.parameters = new ParameterList();

			msg.parameters.put(k, value);
			return this;
		}

		public Builder setPatientMsg(String patientMsg)
		{
			msg.patientMsg = patientMsg;
			return this;
		}

		public Builder setTimeStamp(GregorianCalendar timeStamp)
		{
			msg.timeStamp = timeStamp;
			return this;
		}

	}

}
