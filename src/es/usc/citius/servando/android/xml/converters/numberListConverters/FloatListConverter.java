/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package es.usc.citius.servando.android.xml.converters.numberListConverters;

import org.simpleframework.xml.convert.Converter;
import org.simpleframework.xml.stream.InputNode;
import org.simpleframework.xml.stream.OutputNode;

import es.usc.citius.servando.android.communications.model.FloatList;

/**
 * Converter para serializar como atributos las listas de números.
 * 
 * @author Tomás Teijeiro Campo
 */
public class FloatListConverter implements Converter<FloatList> {

	/**
	 * Factor que consideraremos como número de caracteres que ocupará cada entero.
	 */
	private static final int VALUE_FACTOR = 10;

	@Override
	public FloatList read(InputNode in) throws Exception
	{
		FloatList fl = new FloatList();
		// Leemos los valores
		String[] values = in.getAttribute("values").getValue().trim().split("\\s");
		float[] result = new float[values.length];
		for (int i = 0; i < result.length; i++)
		{
			result[i] = Float.parseFloat(values[i]);
		}
		fl.setValues(result);
		InputNode metadata;
		// Y los metadatos
		while ((metadata = in.getNext("metaData")) != null)
		{
			fl.getMetaData().add(metadata.getValue());
		}
		return fl;
	}

	@Override
	public void write(OutputNode on, FloatList t) throws Exception
	{
		// Escribimos los valores
		StringBuilder builder = new StringBuilder(t.getValues().length * VALUE_FACTOR);
		for (int i = 0; i < t.getValues().length; i++)
		{
			if (i != 0)
			{
				builder.append(' ');
			}
			builder.append(t.getValues()[i]);
		}
		builder.trimToSize();
		on.setAttribute("values", builder.toString());
		// Escribimos los metadatos
		for (String str : t.getMetaData())
		{
			on.getChild("metaData").setValue(str);
		}
	}

}
