/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package es.usc.citius.servando.android.communications;

import java.net.URI;

import es.usc.citius.servando.android.models.services.IPlatformService;

/**
 * Interfaz que deberán implementar los servicios que deseen atender las peticiones remotas realizadas desde los
 * dispositivos móviles.
 * 
 * @author Tomás Teijeiro campo
 */
public interface CommunicableService {

	/**
	 * Obtiene el identificador único del servicio
	 * 
	 * @return el id del servicio
	 */
	public String getId();

	/**
	 * Obtiente la URI del servidor remoto donte se encuentra desplegado el servicio que va a recibir las peticiones
	 * 
	 * @return La URI del servidor
	 */
	public URI getServiceRemoteUri();

	/**
	 * Procesa una petición de tipo "Send" enviada por un cliente remoto.
	 * 
	 * @param obj Objeto enviado
	 * @param client Dirección IP del cliente.
	 * @return true si la entrega ha sido correcta, false en caso contrario.
	 */
	public boolean processSend(Object obj, String client);

	/**
	 * Procesa una petición de tipo "ISend" enviada por un cliente remoto.
	 * 
	 * @param obj Objeto enviado
	 * @param client Dirección IP del cliente.
	 */
	public void processISend(Object obj, String client);

	/**
	 * Procesa una petición de tipo "SendReceive" enviada por un cliente remoto.
	 * 
	 * @param obj Objeto enviado
	 * @param client Dirección IP del cliente.
	 * @return Objeto respuesta.
	 */
	public Object processSendReceive(Object obj, String client);

}
