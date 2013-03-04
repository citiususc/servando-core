package es.usc.citius.servando.android.alerts;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.Root;

import es.usc.citius.servando.android.models.util.ParameterList;

@Root(name = "alert")
public class AlertMsg {
	
	@Element(name = "type")
	private AlertType type;
	
	@Element(name = "displayName", required = false)
	private String displayName;

	@Element(name = "description", required = false)
	private String description;

	@Element(name = "parameters", required = false)
	private ParameterList parameters;

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
		return "AlertMsg [type=" + type + ", displayName=" + displayName + ", description=" + description + "]";
	}

}
