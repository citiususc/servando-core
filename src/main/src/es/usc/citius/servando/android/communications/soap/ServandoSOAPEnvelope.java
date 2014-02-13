/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package es.usc.citius.servando.android.communications.soap;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.Namespace;
import org.simpleframework.xml.NamespaceList;
import org.simpleframework.xml.Order;
import org.simpleframework.xml.Root;
import org.simpleframework.xml.Transient;
import org.simpleframework.xml.convert.Convert;

import es.usc.citius.servando.android.communications.soap.converters.SOAPBodyConverter;

/**
 * Clase que representa un Envelope SOAP, para invocar y recibir resultados del servicio Web que ofrece el módulo de
 * comunicaciones de Servando. Esta clase soporta un subconjunto del estándar SOAP, el necesario para que funcione el
 * API de comunicaciones.
 * 
 * @author Tomás Teijeiro Campo
 */
// Nombre del elemento raíz
@Root(name = "Envelope")
// Namespaces incluídos
@NamespaceList({ @Namespace(reference = ServandoSOAPConstants.SOAP_ENVELOPE_NAMESPACE, prefix = "s"),
		@Namespace(reference = ServandoSOAPConstants.WS_ADDRESSING_NAMESPACE, prefix = "a") })
// Namespace en el que se sitúa el elemento raíz
@Namespace(reference = ServandoSOAPConstants.SOAP_ENVELOPE_NAMESPACE, prefix = "s")
// Debemos preservar el orden de los elementos, para que no haya fallos en el otro extremo.
@Order(elements = { "Header", "Body" })
public class ServandoSOAPEnvelope {
	/**
	 * Cabecera del mensaje SOAP, donde se encuentra el identificador del servicio destino del mensaje, el patrón de
	 * intercambio a utilizar, y la URL del destinatario.
	 */
	@Element(name = "Header", required = true)
	@Namespace(reference = ServandoSOAPConstants.SOAP_ENVELOPE_NAMESPACE)
	private ServandoSOAPHeader header;

	/**
	 * Cuerpo del mensaje SOAP, donde se encuentra codificado en XML el objeto a transmitir.
	 */
	@Element(name = "Body", required = true)
	@Namespace(reference = ServandoSOAPConstants.SOAP_ENVELOPE_NAMESPACE)
	@Convert(value = SOAPBodyConverter.class)
	private ServandoSOAPBody body;

	/**
	 * Indica si ha ocurrido un error en el envío
	 */
	@Transient
	private boolean hasFault = false;

	public ServandoSOAPEnvelope()
	{
		header = new ServandoSOAPHeader();
		body = new ServandoSOAPBody();
	}

	/**
	 * Cabecera del mensaje SOAP, donde se encuentra el identificador del servicio destino del mensaje, el patrón de
	 * intercambio a utilizar, y la URL del destinatario.
	 * 
	 * @return the header
	 */
	public ServandoSOAPHeader getHeader()
	{
		return header;
	}

	/**
	 * Cabecera del mensaje SOAP, donde se encuentra el identificador del servicio destino del mensaje, el patrón de
	 * intercambio a utilizar, y la URL del destinatario.
	 * 
	 * @param header the header to set
	 */
	public void setHeader(ServandoSOAPHeader header)
	{
		this.header = header;
	}

	/**
	 * Cuerpo del mensaje SOAP, donde se encuentra codificado en XML el objeto a transmitir.
	 * 
	 * @return the body
	 */
	public ServandoSOAPBody getBody()
	{
		return body;
	}

	/**
	 * Cuerpo del mensaje SOAP, donde se encuentra codificado en XML el objeto a transmitir.
	 * 
	 * @param body the body to set
	 */
	public void setBody(ServandoSOAPBody body)
	{
		this.body = body;
	}

	/**
	 * Devuelve un valor indicando si el mensaje esta vacío
	 */
	@Transient
	public boolean isEmpty()
	{
		return this.body.getObject() == null;
	}

	/**
	 * Devuelve un valor indicando si ha ocurrido un error en el envío. Si el mensaje todavía no ha sido enviado
	 * devuelve #<code>false</code>
	 */
	public boolean hasFault()
	{
		return this.hasFault;
	}

	public void setHasFault(boolean hasFault)
	{
		this.hasFault = hasFault;
	}

	/**
	 * Método estático que permite obtener un mensaje vacío. Simplemente devuelve una nueva instancia de
	 * {@link ServandoSOAPEnvelope}. Es útil cuando se desea indicar de forma explícita que se está devolviendo un
	 * mensaje vacío, añadiendo un significado a mayores.
	 * 
	 * @return un mensaje vacío
	 */
	public static ServandoSOAPEnvelope emptyMessage()
	{
		return new ServandoSOAPEnvelope();
	}

	/**
	 * Método estático que permite obtener un mensaje marcado como erróneo {@link #hasFault()} = true
	 * 
	 * @return un mensaje marcado como erróneo
	 */
	public static ServandoSOAPEnvelope errorMessage()
	{
		ServandoSOAPEnvelope errorMsg = new ServandoSOAPEnvelope();
		errorMsg.hasFault = true;
		return errorMsg;
	}

	/**
	 * Método estático que permite obtener un mensaje marcado como erróneo {@link #hasFault()} = true, que contiene una
	 * descripción del error en el cuerpodel mensaje
	 * 
	 * @return un mensaje marcado como erróneo
	 */
	public static ServandoSOAPEnvelope errorMessage(String description)
	{
		ServandoSOAPEnvelope errorMsg = new ServandoSOAPEnvelope();
		errorMsg.getBody().setObject(description);
		errorMsg.hasFault = true;
		return errorMsg;
	}

}
