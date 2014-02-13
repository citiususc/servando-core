/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package es.usc.citius.servando.android.services.model;

import java.util.GregorianCalendar;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.Root;
import org.simpleframework.xml.convert.Convert;

import es.usc.citius.servando.android.xml.converters.GregorianCalendarConverter;

/**
 * Clase de utilidad para realizar pruebas de conectividad y rendimiento de la red.
 * 
 * @author Tomás Teijeiro Campo
 */
@Root(name = "ping")
public class Ping {

	@Element(name = "timeStamp")
	@Convert(value = GregorianCalendarConverter.class)
	private GregorianCalendar timeStamp;

	public Ping()
	{
		timeStamp = new GregorianCalendar();
	}

	public Ping(GregorianCalendar timeStamp)
	{
		this.timeStamp = timeStamp;
	}

	/**
	 * @return Marca de tiempo con el momento en el que se envió este paquete.
	 */
	public GregorianCalendar getTimeStamp()
	{
		return timeStamp;
	}

	/**
	 * @param timeStamp Marca de tiempo con el momento en el que se envió este paquete.
	 */
	public void setTimeStamp(GregorianCalendar timeStamp)
	{
		this.timeStamp = timeStamp;
	}

}
