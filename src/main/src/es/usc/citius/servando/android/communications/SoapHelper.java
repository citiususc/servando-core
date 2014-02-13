package es.usc.citius.servando.android.communications;

import java.net.URI;

import es.usc.citius.servando.android.communications.model.BooleanWrapper;
import es.usc.citius.servando.android.communications.model.DoubleWrapper;
import es.usc.citius.servando.android.communications.model.LongWrapper;
import es.usc.citius.servando.android.communications.model.StringWrapper;
import es.usc.citius.servando.android.communications.soap.ServandoSOAPEnvelope;

public class SoapHelper {

	/**
	 * Wraps an object in a {@link ServandoSOAPEnvelope} to be sent to a remote service
	 * 
	 * @param obj The object to send
	 * @param operation The operation to call
	 * @param remoteService the name of the receiver service
	 * @param remoteServiceUri the receiver URI
	 * @return The wrapped message
	 */
	public static ServandoSOAPEnvelope prepareMessage(Object obj, String operation, String remoteService, URI remoteServiceUri)
	{
		// Si es un tipo de dato primitivo, lo convertimos a su objeto encapsulador antes de transmitirlo
		if (obj instanceof Boolean)
		{
			obj = new BooleanWrapper((Boolean) obj);
		} else if (obj instanceof String)
		{
			obj = new StringWrapper((String) obj);
		} else if (obj instanceof Long)
		{
			obj = new LongWrapper((Long) obj);
		} else if (obj instanceof Integer)
		{
			obj = new LongWrapper((Integer) obj);
		} else if (obj instanceof Double)
		{
			obj = new DoubleWrapper((Double) obj);
		} else if (obj instanceof Float)
		{
			obj = new DoubleWrapper((Float) obj);
		}
		// Creamos el mensaje con el objeto a transmitir y la operación a ejecutar
		ServandoSOAPEnvelope msg = new ServandoSOAPEnvelope();
		msg.getBody().setObject(obj);
		// Añadimos al mensaje la cabecera que indica la acción a ejecutar
		msg.getHeader().setAction(operation);
		// Indicamos el id del servicio remoto
		msg.getHeader().setService(remoteService);
		// Establecemos la URL de destino del mensaje
		msg.getHeader().setDestinyURL(remoteServiceUri.toString());
		return msg;
	}

	/**
	 * Wraps an object in a {@link ServandoSOAPEnvelope} to be sent to a remote service
	 * 
	 * @param obj The object to send
	 * @param operation The operation to call
	 * @param remoteService the name of the receiver service
	 * @param remoteServiceUri the receiver URI
	 * @return The wrapped message
	 */
	public static ServandoSOAPEnvelope prepareMessage(Object obj, String operation, String remoteService)
	{
		// Si es un tipo de dato primitivo, lo convertimos a su objeto encapsulador antes de transmitirlo
		if (obj instanceof Boolean)
		{
			obj = new BooleanWrapper((Boolean) obj);
		} else if (obj instanceof String)
		{
			obj = new StringWrapper((String) obj);
		} else if (obj instanceof Long)
		{
			obj = new LongWrapper((Long) obj);
		} else if (obj instanceof Integer)
		{
			obj = new LongWrapper((Integer) obj);
		} else if (obj instanceof Double)
		{
			obj = new DoubleWrapper((Double) obj);
		} else if (obj instanceof Float)
		{
			obj = new DoubleWrapper((Float) obj);
		}
		// Creamos el mensaje con el objeto a transmitir y la operación a ejecutar
		ServandoSOAPEnvelope msg = new ServandoSOAPEnvelope();
		msg.getBody().setObject(obj);
		// Añadimos al mensaje la cabecera que indica la acción a ejecutar
		msg.getHeader().setAction(operation);
		// Indicamos el id del servicio remoto
		msg.getHeader().setService(remoteService);

		return msg;
	}

}
