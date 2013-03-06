package es.usc.citius.servando.android.util.lastresponses;

import org.simpleframework.xml.Default;
import org.simpleframework.xml.DefaultType;
import org.simpleframework.xml.Element;

@Default(DefaultType.FIELD)
public class Response {

	@Element(name = "value", required = true)
	private String value;

	public Response()
	{

	}

	public Response(String value)
	{
		this.value = value;
	}

	public String getValue()
	{
		return value;
	}

	public void setValue(String value)
	{
		this.value = value;
	}
}
