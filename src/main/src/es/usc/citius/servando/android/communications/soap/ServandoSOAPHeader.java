/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package es.usc.citius.servando.android.communications.soap;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.Namespace;

/**
 * Clase que representa la cabecera de un mensaje SOAP, con los atributos específicos utilizados en las peticiones con
 * el módulo de comunicaciones de Servando.
 * 
 * @author Tomás Teijeiro Campo
 */
public class ServandoSOAPHeader {
	/**
	 * Contiene el patrón de intercambio a utilizar en la transmisión (Send, ISend, o SendReceive).
	 */
	@Element(name = "vpnClient", required = false)
	@Namespace(reference = ServandoSOAPConstants.WS_ADDRESSING_NAMESPACE)
	private String vpnClient;

	public String getVpnClient()
	{
		return vpnClient;
	}

	public void setVpnClient(String user)
	{
		this.vpnClient = user;
	}

	/**
	 * Contiene el nombre del servicio destinatario del mensaje
	 */
	/**
	 * Contiene el patrón de intercambio a utilizar en la transmisión (Send, ISend, o SendReceive).
	 */
	@Element(name = "Action", required = false)
	@Namespace(reference = ServandoSOAPConstants.WS_ADDRESSING_NAMESPACE)
	private String action;
	/**
	 * Contiene el nombre del servicio destinatario del mensaje
	 */
	@Element(name = "service", required = true)
	@Namespace(reference = ServandoSOAPConstants.OBJECT_TRANSPORTER_NAMESPACE)
	private String service;
	/**
	 * Contiene la URL del destinatario del mensaje.
	 */
	@Element(name = "To", required = false)
	@Namespace(reference = ServandoSOAPConstants.WS_ADDRESSING_NAMESPACE)
	private String destinyURL;

	/**
	 * Contiene el patrón de intercambio a utilizar en la transmisión (Send, ISend, o SendReceive).
	 * 
	 * @return the action
	 */
	public String getAction()
	{
		return action;
	}

	/**
	 * Contiene el patrón de intercambio a utilizar en la transmisión (Send, ISend, o SendReceive).
	 * 
	 * @param action the action to set
	 */
	public void setAction(String action)
	{
		this.action = action;
	}

	/**
	 * Contiene el nombre del servicio destinatario del mensaje
	 * 
	 * @return the service
	 */
	public String getService()
	{
		return service;
	}

	/**
	 * Contiene el nombre del servicio destinatario del mensaje
	 * 
	 * @param service the service to set
	 */
	public void setService(String service)
	{
		this.service = service;
	}

	/**
	 * Contiene la URL del destinatario del mensaje.
	 * 
	 * @return the destinyURL
	 */
	public String getDestinyURL()
	{
		return destinyURL;
	}

	/**
	 * Contiene la URL del destinatario del mensaje.
	 * 
	 * @param destinyURL the destinyURL to set
	 */
	public void setDestinyURL(String destinyURL)
	{
		this.destinyURL = destinyURL;
	}

}
