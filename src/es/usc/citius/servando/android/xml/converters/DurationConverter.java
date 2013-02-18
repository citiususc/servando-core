/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package es.usc.citius.servando.android.xml.converters;

import org.joda.time.Period;
import org.joda.time.format.PeriodFormatter;
import org.joda.time.format.PeriodFormatterBuilder;
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
public class DurationConverter implements Converter<Long> {

	static PeriodFormatter fmt = new PeriodFormatterBuilder().appendWeeks()
																.appendSuffix("w ")
																.appendDays()
																.appendSuffix("d ")
																.appendHours()
																.appendSuffix("h ")
																.appendMinutes()
																.appendSuffix("m ")
																.appendSeconds()
																.appendSuffix("s ")
																.toFormatter();

	@Override
	public Long read(InputNode in) throws Exception
	{
		long result = 0;
		String nodeValue = in.getValue();

		try
		{
			result = fmt.parsePeriod(nodeValue + " ").toStandardSeconds().getSeconds();
		} catch (Exception e)
		{
			result = Long.parseLong(nodeValue);
		}

		return result;
	}

	@Override
	public void write(OutputNode on, Long t) throws Exception
	{

		StringBuffer sb = new StringBuffer(20);
		fmt.printTo(sb, Period.seconds(t.intValue()));
		on.setValue(sb.toString());
	}

}
