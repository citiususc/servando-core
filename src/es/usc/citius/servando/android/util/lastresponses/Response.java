package es.usc.citius.servando.android.util.lastresponses;

import java.text.SimpleDateFormat;
import java.util.GregorianCalendar;

import org.simpleframework.xml.Default;
import org.simpleframework.xml.DefaultType;
import org.simpleframework.xml.Element;
import org.simpleframework.xml.convert.Convert;

import es.usc.citius.servando.android.xml.converters.GregorianCalendarConverter;

@Default(DefaultType.FIELD)
public class Response {

	@Element(name = "value", required = true)
	private String value;

	@Element(name = "timeStamp", required = true)
	@Convert(value = GregorianCalendarConverter.class)
	private GregorianCalendar timeStamp;

	public Response()
	{
	}

	/**
	 * By default timeStamp will be set to 'now' value
	 * 
	 * @param value
	 */
	public Response(String value)
	{
		this.value = value;
		this.timeStamp = new GregorianCalendar();
	}

	public Response(String value, GregorianCalendar timeStamp)
	{
		this.value = value;
		this.timeStamp = timeStamp;
	}

	public String getValue()
	{
		return value;
	}

	public void setValue(String value)
	{
		this.value = value;
	}

	public GregorianCalendar getTimeStamp()
	{
		return timeStamp;
	}

	public void setTimeStamp(GregorianCalendar timeStamp)
	{
		this.timeStamp = timeStamp;
	}

	@Override
	public String toString()
	{
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		return "Response: [value=" + this.value + ", timeStamp= " + df.format(timeStamp.getTime()) + "]";
	}
}
