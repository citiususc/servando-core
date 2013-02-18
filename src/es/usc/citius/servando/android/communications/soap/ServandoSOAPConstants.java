/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package es.usc.citius.servando.android.communications.soap;

/**
 * Interfaz que contiene las constantes necesarias para realizar correctamente las peticiones SOAP del módulo de
 * comunicaciones, en cuanto a namespaces de los elementos.
 * 
 * @author Tomás Teijeiro Campo
 */
public interface ServandoSOAPConstants {

	public static final String SOAP_ENVELOPE_NAMESPACE = "http://www.w3.org/2003/05/soap-envelope";
	public static final String WS_ADDRESSING_NAMESPACE = "http://www.w3.org/2005/08/addressing";
	public static final String OBJECT_TRANSPORTER_NAMESPACE = "http://gsi.dec.usc.es/servando/objectTransporterWSDL";

}
