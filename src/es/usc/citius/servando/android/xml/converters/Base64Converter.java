/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package es.usc.citius.servando.android.xml.converters;

import org.simpleframework.xml.convert.Converter;
import org.simpleframework.xml.stream.InputNode;
import org.simpleframework.xml.stream.OutputNode;

import es.usc.citius.servando.android.communications.model.ByteArrayWrapper;

/**
 * Clase que nos permitirá guardar los arrays de bytes en base 64, para hacerlo compatible con JAXB, que codifica los
 * atributos byte[] de esta forma.
 * 
 * @author Tomás Teijeiro Campo
 */
public class Base64Converter implements Converter<ByteArrayWrapper> {

	public ByteArrayWrapper read(InputNode in) throws Exception
	{
		ByteArrayWrapper w = new ByteArrayWrapper();
		// Leemos el contenido en base64
		w.data = Base64Coder.decode(in.getValue());
		return w;
	}

	public void write(OutputNode on, ByteArrayWrapper t) throws Exception
	{
		on.setValue(new String(Base64Coder.encode(t.data)));
	}

}
