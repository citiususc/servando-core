/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package es.usc.citius.servando.android.communications.soap.converters;

import javax.xml.XMLConstants;
import org.simpleframework.xml.convert.AnnotationStrategy;
import org.simpleframework.xml.convert.Converter;
import org.simpleframework.xml.core.Persister;
import org.simpleframework.xml.stream.Format;
import org.simpleframework.xml.stream.InputNode;
import org.simpleframework.xml.stream.OutputNode;

import android.util.Log;

import es.usc.citius.servando.android.communications.soap.ServandoSOAPBody;
import es.usc.citius.servando.android.util.ReflectionUtils;

/**
 * Converter utilizado para la codificación y decodificación del body SOAP.
 * 
 * @author Tomás Teijeiro Campo
 */
public class SOAPBodyConverter implements Converter<ServandoSOAPBody> {

	private static final String DEBUG_TAG = SOAPBodyConverter.class.getSimpleName();

	private Persister objectPersister;

	public SOAPBodyConverter()
	{
		// Creamos una nueva instancia de persister, que nos servirá para
		// serializar y deserializar el objeto embebido.
		objectPersister = new Persister(new AnnotationStrategy(), new Format(0));
	}

	@Override
	public ServandoSOAPBody read(InputNode in) throws Exception
	{
		ServandoSOAPBody b = new ServandoSOAPBody();
		// Intentamos obtener el nombre de la clase
		InputNode className = in.getAttribute("type");
		// Si no viene el nombre de la clase, el objeto lo leeremos como un string,
		// y no estableceremos el nombre de la clase.
		if (className == null)
		{
			b.setObject(in.getValue());
			b.setClassName("");
		} else
		{
			b.setClassName(className.getValue());
			// Y la instancia de la clase
			Class<?> c = ReflectionUtils.classForNameIgnoreCase(b.getClassName());
			// Ahora obtenemos el objeto, utilizando el deserializador.
			b.setObject(objectPersister.read(c, in.getNext()));
		}
		return b;
	}

	@Override
	public void write(OutputNode on, ServandoSOAPBody t) throws Exception
	{
		// Incluímos el namespace para indicar el tipo de objeto.
		on.getNamespaces().setReference(XMLConstants.W3C_XML_SCHEMA_INSTANCE_NS_URI, "xsi");
		// Escribimos el nombre de la clase, como atributo con el namespace correcto.
		OutputNode type = on.setAttribute("type", t.getObject().getClass().getName());
		type.setReference(XMLConstants.W3C_XML_SCHEMA_INSTANCE_NS_URI);
		// Ahora escribimos el objeto con el persister adecuado
		objectPersister.write(t.getObject(), on);
	}
}
