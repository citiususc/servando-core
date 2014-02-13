/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package es.usc.citius.servando.android.xml.converters;

import java.util.Calendar;
import java.util.GregorianCalendar;

import org.simpleframework.xml.convert.Converter;
import org.simpleframework.xml.stream.InputNode;
import org.simpleframework.xml.stream.OutputNode;

/**
 * Converter que permite la serialización a XML de GregorianCalendars compatibles con el binding realizado por JAXB.
 * NOTA: Esta implementación no recoge los cambios de zona horaria, por lo que se interpretan todas las fechas como
 * absolutas.
 * 
 * @author Tomás Teijeiro Campo
 */
public class GregorianCalendarConverter implements Converter<GregorianCalendar> {

	public GregorianCalendar read(InputNode in) throws Exception
	{
		String v = in.getValue();
		int year = Integer.parseInt(v.substring(0, 4));
		int month = Integer.parseInt(v.substring(5, 7)) - 1;
		int dayOfMonth = Integer.parseInt(v.substring(8, 10));
		int hourOfDay = Integer.parseInt(v.substring(11, 13));
		int minute = Integer.parseInt(v.substring(14, 16));
		int second = Integer.parseInt(v.substring(17, 19));
		int millisecond = Integer.parseInt(v.substring(20, 23));
		GregorianCalendar gc = new GregorianCalendar(year, month, dayOfMonth, hourOfDay, minute, second);
		gc.set(Calendar.MILLISECOND, millisecond);

		// TODO cambiar esto!
		// gc.setTimeZone(TimeZone.getTimeZone("GMT+1"));

		return gc;
	}

	public void write(OutputNode on, GregorianCalendar t) throws Exception
	{
		on.setValue(String.format("%04d-%02d-%02dT%02d:%02d:%02d.%03d", t.get(Calendar.YEAR), t.get(Calendar.MONTH) + 1,
				t.get(Calendar.DAY_OF_MONTH), t.get(Calendar.HOUR_OF_DAY), t.get(Calendar.MINUTE), t.get(Calendar.SECOND),
				t.get(Calendar.MILLISECOND)));
	}

}
