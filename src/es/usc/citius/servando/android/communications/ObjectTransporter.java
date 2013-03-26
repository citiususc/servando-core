/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package es.usc.citius.servando.android.communications;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.HashMap;
import java.util.Random;
import java.util.UUID;

import org.simpleframework.xml.convert.AnnotationStrategy;
import org.simpleframework.xml.core.Persister;
import org.simpleframework.xml.stream.Format;

import android.util.Log;
import es.usc.citius.servando.android.ServandoPlatformFacade;
import es.usc.citius.servando.android.communications.ISendTicket.TicketStatus;
import es.usc.citius.servando.android.communications.callbacks.TransmissionFinishedCallback;
import es.usc.citius.servando.android.communications.callbacks.TransmissionFinishedCallback.NullTransmissionFinishedCallback;
import es.usc.citius.servando.android.communications.exceptions.ReceiveException;
import es.usc.citius.servando.android.communications.exceptions.SendException;
import es.usc.citius.servando.android.communications.model.BooleanWrapper;
import es.usc.citius.servando.android.communications.model.DoubleWrapper;
import es.usc.citius.servando.android.communications.model.LongWrapper;
import es.usc.citius.servando.android.communications.model.StringWrapper;
import es.usc.citius.servando.android.communications.soap.ServandoSOAPEnvelope;

/**
 * Clase encargada de realizar peticiones al servicio Web situado en los DSR. Proporciona la funcionalidad de tipo
 * cliente de la librería.
 * 
 * @todo por ahora esta clase es un prototipo para garantizar la compatibilidad con android. Necesita numerosas pruebas
 *       y refactorizaciones.
 * @author Tomás Teijeiro Campo
 */
public class ObjectTransporter {

	private static final String DEBUG_TAG = ObjectTransporter.class.getSimpleName();
	/**
	 * URI relativa del servicio web de ObjectTransporter en los DSR. El uso de este valor está recomendado para
	 * establecer la propiedad @see setRemoteEndpoint, por ejemplo de la forma:
	 * objectTransporter.setRemoteEndpoint("http://remote_addr:remote_port"+ObjectTransporter.DSRWebServiceRelativeURI);
	 */
	public static final String DSRWebServiceRelativeURI = "/OTWSServer/ObjectTransporterService";
	/**
	 * URI relativa del servicio web de ObjectTransporter en los SXDS. El uso de este valor está recomendado para
	 * establecer la propiedad @see setRemoteEndpoint, por ejemplo de la forma:
	 * objectTransporter.setRemoteEndpoint("http://remote_addr:remote_port"
	 * +ObjectTransporter.SXDSWebServiceRelativeURI);
	 */
	public static final String SXDSWebServiceRelativeURI = "ServandoViewAndroid/objectTransporterWSDLService"; // "/OTWSServer/objectTransporterWSDLService"
	/**
	 * Tabla en la que guardaremos las respuestas a las peticiones remotas
	 */
	private final HashMap<UUID, Boolean> ticketResponses;
	/**
	 * Serializador utilizado para leer y escribir los mensajes
	 */
	private Persister persister;
	/**
	 * Servicio al que se le realizará la petición.
	 */
	private String remoteService;
	/**
	 * URI del servicio
	 */
	private URI serviceUri;

	/**
	 * Crea una nueva instancia de ObjectTransporter, que debe ser configurada correctamente para que se conecte al
	 * servicio de forma apropiada. Véase {@link #setRemoteService(String)} y {@link #setServiceUri(URI)}
	 */
	public ObjectTransporter()
	{
		persister = new Persister(new AnnotationStrategy(), new Format(0));
		ticketResponses = new HashMap<UUID, Boolean>();
	}

	/**
	 * Crea una nueva instancia de {@link ObjectTransporter}, con los parametros de servicio especificados
	 * 
	 * @param serviceId Id del servicio remoto al que se enviarán los mensajes
	 * @param remoteIp IP del servidor en el que se encuentra desplegado el servicio
	 * @param remotePort Puerto en el que escucha el servidor
	 * @param remoteEndPoint ruta relativa al servidor en la que se encuentra desplegado el servicio
	 */
	ObjectTransporter(String serviceId, String remoteIp, String remotePort, String remoteEndPoint)
	{
		this();
		serviceUri = URI.create("http://" + remoteIp + ":" + remotePort + (remoteEndPoint.startsWith("/") ? "" : "/") + remoteEndPoint);
		remoteService = serviceId;
	}

	/**
	 * Crea una nueva instancia de {@link ObjectTransporter}, lista para enviar mensajes al servicio desplegado en la
	 * URI especificada
	 * 
	 * @param serviceId Id del servicio remoto al que se enviarán los mensajes
	 * @param serviceUri {@link URI} del servicio
	 */
	ObjectTransporter(URI serviceUri, String serviceId)
	{
		this();
		remoteService = serviceId;
		this.serviceUri = serviceUri;
	}

	/**
	 * Crea una nueva instancia de {@link ObjectTransporter}, lista para enviar mensajes al servicio desplegado en la
	 * URI especificada
	 * 
	 * @param serviceId Id del servicio remoto al que se enviarán los mensajes
	 * @param serviceUri {@link URI} del servicio
	 * @throws {@link URISyntaxException} si la URL especificada no es válida
	 */
	ObjectTransporter(URL serviceUrl, String serviceId) throws URISyntaxException
	{
		this();
		remoteService = serviceId;
		serviceUri = serviceUrl.toURI();
	}

	/**
	 * Envía el objeto obj al otro extremo del servicio, y espera la respuesta que nos indique si ese objeto se ha
	 * transmitido o no de forma correcta. Es una llamada síncrona, pues el invocador deberá esperar a que se reciba la
	 * respuesta.
	 * 
	 * @param obj El objeto a transmitir
	 * @return Resultado de la llamada, del tipo SendResult
	 */
	public boolean send(Object obj)
	{

		ServandoSOAPEnvelope response = null;
		boolean sendResult = false;

		try
		{
			// Respuesta que obtendremos
			response = invokeForResponse(obj, "Send");
		} catch (Exception e)
		{
			Log.d("OT", "Erro ó enviar unha mensaxe síncrona mediante Send (clase " + obj.getClass().getName() + ")", e);
		}

		if (response != null && !response.hasFault())
		{
			try
			{
				// Intentamos leer un booleano
				sendResult = Boolean.parseBoolean((String) response.getBody().getObject());
			} catch (Exception ignoreException)
			{
			}
		}
		return sendResult;
	}

	/**
	 * Envía de forma asíncrona el objeto especificado al otro extremo del servicio invocador. Es una comunicación no
	 * fiable, y no se asegura la entrega correcta, pero permite enviar mensajes no críticos de una forma rápida.
	 * 
	 * @param obj
	 */
	public void iSend(Object obj)
	{
		// Creamos un nuevo hilo que llame a la función ISend de forma asíncrona, sin posibilidad
		// de comprobar el correcto envío del mensaje.
		new Thread(new DoIsend(obj)).start();
	}

	/**
	 * Envía un objeto de forma asíncrona al otro extremo del servicio invocador. Se devuelve un objeto del tipo
	 * ISendTicket, que nos permitirá comprobar posteriormente si el mensaje ha sido correctamente entregado.
	 * 
	 * @param obj
	 * @return Ticket que nos permitirá comprobar si la entrega del objeto al otro extremo del servicio se ha realizado
	 *         correctamente.
	 */
	public ISendTicket checkableISend(Object obj)
	{
		// Creamos el ticket que devolveremos
		ISendTicket ticket = new ISendTicket();
		// Realizamos el envío de forma asíncrona, y devolvemos inmediatamente el ticket
		new Thread(new DoCheckableIsend(new CheckableIsendData(ticket.getId(), obj))).start();
		return ticket;
	}

	/**
	 * Envía el objeto indicado en modo <b>asíncrono</b>. Si se produce un error en el envío, automaticamente se
	 * reintenta. Delega en {@link #reliableSend(Object, long, TransmissionFinishedCallback)} pasando como callback la
	 * instancia única de {@link NullTransmissionFinishedCallback}, de modo que el invocador non obtiene ningún tipo de
	 * respuesta
	 * 
	 * @param obj El objeto a transmitir
	 * @param timeout Timeout en <b>segundos</b> durante el cual se intentará el envío.
	 */
	public void reliableSend(Object obj, long timeout)
	{
		// Creamos un nuevo hilo para que la llamada sea asíncrona.
		new Thread(new DoReliableIsend(new ReliableSendData(obj, timeout, NullTransmissionFinishedCallback.getInstance(), new ISendTicket()))).start();
	}

	/**
	 * Envía el objeto indicado en modo <b>asíncrono</b>. Si se produce un error en el envío, automaticamente se
	 * reintenta
	 * 
	 * @param obj El objeto a transmitir
	 * @param timeout Timeout en <b>segundos</b> durante el cual se intentará el envío.
	 * @param callback Callback que se ejecutará cuando se envíe el mensaje o expire el timeout
	 * @return ticket Ticket con el cual se identificará el mensaje cuando se ejecute el método de callback
	 */
	public ISendTicket reliableSend(Object obj, long timeout, TransmissionFinishedCallback callback)
	{
		// Creamos el ticket que identificará a la llamada
		ISendTicket ticket = new ISendTicket();
		// Creamos un nuevo hilo para que la llamada sea asíncrona.
		new Thread(new DoReliableIsend(new ReliableSendData(obj, timeout, callback, ticket))).start();
		// Devolvemos el ticket
		return ticket;
	}

	/**
	 * Envía un objeto de forma asíncrona al otro extremo del servicio invocador, recibiendo otro objeto como respuesta.
	 * El objeto será del mismo tipo enviado, por lo que ambos tipos deberán estar definidos en ambos lados del
	 * servicio.
	 * 
	 * @param obj Objeto a enviar.
	 * @return Respuesta
	 * @throws SendException Si se produce un error en el envío.
	 * @throws ReceiveException Si se produce un error en la recepción.
	 */
	public Object sendReceive(Object obj) throws Exception
	{
		// Invocamos a la operación SendReceive
		ServandoSOAPEnvelope response = invokeForResponse(obj, "SendReceive");
		// Obtenemos el nombre del tipo de objeto que se nos ha transmitido
		String classname = response.getBody().getClassName();
		// Si no se encuentra el nombre de la clase, devolvemos null, suponiendo que el cuerpo no contendrá ningún
		// objeto
		if (classname.length() == 0)
		{
			return null;
		}
		Object resp = response.getBody().getObject();
		// Si es un objeto de tipo envoltorio de los tipos básicos, devolvemos el tipo básico
		if (resp instanceof BooleanWrapper)
		{
			return ((BooleanWrapper) resp).getValue();
		}
		if (resp instanceof StringWrapper)
		{
			return ((StringWrapper) resp).getValue();
		}
		if (resp instanceof LongWrapper)
		{
			return ((LongWrapper) resp).getValue();
		}
		if (resp instanceof DoubleWrapper)
		{
			return ((DoubleWrapper) resp).getValue();
		}
		return resp;
	}

	/**
	 * Permite comprobar si un envío asíncrono se ha realizado correctamente, a partir del ticket obtenido al realizar
	 * la llamada.
	 * 
	 * @param ticket Ticket obtenido con la llamada CheckableISend
	 * @return El estado del envío correspondiente al ticket suministrado. Cuando se reciba
	 *         TicketStatus.CorrectTransmision o TicketStatus.IncorrectTransmision el ticket quedará invalidado, no
	 *         pudiendo volver a consultar. Si se devuelve TicketStatus.Unknown, entonces aún no se ha recibido ninguna
	 *         respuesta del otro extremo.
	 */
	public TicketStatus checkTicket(ISendTicket ticket)
	{
		// Comprobamos el resultado de la invocación con el id del ticket
		// Si aún no está en el diccionario, entonces no se conoce la respuesta
		if (!ticketResponses.containsKey(ticket.getId()))
		{
			return TicketStatus.Unknown;
		}
		// Comprobamos el resultado de la llamada almacenado en el diccionario
		Boolean res = ticketResponses.get(ticket.getId());
		// Eliminamos esa entrada del diccionario (no se podrá volver a consultar)
		ticketResponses.remove(ticket.getId());
		// Devolvemos el estado en función de si la llamada fue correcta o no
		if (res)
		{
			return TicketStatus.CorrectTransmision;
		}
		return TicketStatus.IncorrectTransmision;
	}

	/**
	 * LLama a la operación remota con el objeto especificado, y devuelve la respuesta obtenida
	 * 
	 * @param obj
	 * @param operation
	 * @return
	 * @throws javax.xml.soap.SOAPException
	 * @throws javax.xml.parsers.ParserConfigurationException
	 * @throws javax.xml.bind.JAXBException
	 * @throws java.net.MalformedURLException
	 * @throws java.net.ProtocolException
	 * @throws java.io.IOException
	 */
	private ServandoSOAPEnvelope invokeForResponse(Object obj, String operation) throws Exception
	{
		return getReply(prepareMessage(obj, operation));
	}

	/**
	 * LLama a la operación remota con el objeto especificado, sin devolver ningún tipo de respuesta
	 * 
	 * @param obj
	 * @param operation
	 * @throws javax.xml.soap.SOAPException
	 * @throws javax.xml.parsers.ParserConfigurationException
	 * @throws javax.xml.bind.JAXBException
	 * @throws java.net.MalformedURLException
	 * @throws java.net.ProtocolException
	 * @throws java.io.IOException
	 */
	private void invoke(Object obj, String operation) throws Exception
	{
		noReply(prepareMessage(obj, operation));
	}

	/**
	 * Wraps an object in a {@link ServandoSOAPEnvelope} to be sent to the remote service
	 * 
	 * @param obj The object to send
	 * @param operation The operation to call
	 * @return The wrapped message
	 */
	public ServandoSOAPEnvelope prepareMessage(Object obj, String operation)
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
		msg.getHeader().setDestinyURL(serviceUri.toString());

		msg.getHeader().setVpnClient(ServandoPlatformFacade.getInstance().getSettings().getVpnClient());

		return msg;
	}

	/**
	 * Obtiene el mensaje de respuesta a un mensaje determinado, utilizando un MEP Request/Response
	 * 
	 * @param msg Mensaje que enviaremos
	 * @return Mensaje de respuesta
	 * @throws Exception
	 */
	private ServandoSOAPEnvelope getReply(ServandoSOAPEnvelope msg) throws Exception
	{

		ServandoSOAPEnvelope response = null;
		HttpURLConnection connection = null;
		OutputStream out = null;
		InputStream in = null;

		try
		{
			// Obtenemos una conexión HTTP configurada a través de la fábrica
			connection = HttpConnectionFactory.createSoapHttpURLConnection(serviceUri);
			// escribimos el mensaje
			out = connection.getOutputStream();
			persister.write(msg, out);
			// leemos la respuesta
			in = connection.getInputStream();
			response = persister.read(ServandoSOAPEnvelope.class, in);

		} finally
		{
			if (out != null)
			{
				try
				{
					// intentamos cerrar el stream
					out.close();
				} catch (IOException logOrIgnore)
				{
				}
			}
			if (in != null)
			{
				try
				{
					// intentamos cerrar el stream
					in.close();
				} catch (IOException logOrIgnore)
				{
				}
			}
			if (connection != null)
			{
				connection.disconnect();
			}
		}

		/*
		 * //Si se envía con chunking, leeremos directamente del stream de entrada String encoding =
		 * conexion.getHeaderField("Transfer-Encoding"); if (encoding != null && encoding.equalsIgnoreCase("chunked")) {
		 * in = conexion.getInputStream(); } //Si no, leeremos los bytes indicados en el content-length else { byte[]
		 * respuesta = new byte[Integer.parseInt(conexion.getHeaderField("Content-Length"))]; int read = 0; do { read +=
		 * conexion.getInputStream().read(respuesta, read, respuesta.length - read); } while (read < respuesta.length);
		 * //Creamos un nuevo InputStream del que se leerá el mensaje (para evitar bloqueos con el inputstream de la
		 * //conexión, pues al ser persistente, no finalizaría la lectura del mensaje. Además, ese inputStream será
		 * decorado //para que se ignoren los caracteres \r y \n, pues ralentizan enormemente el parseo del documento.
		 * in = new RemoveCRLFInputStream(new ByteArrayInputStream(respuesta)); }
		 */

		return response;
	}

	/**
	 * Envía un mensaje determinado, sin intentar leer ningún tipo de respuesta
	 * 
	 * @param msg Mensaje que enviaremos
	 * @return Mensaje de respuesta
	 * @throws Exception
	 */
	private void noReply(ServandoSOAPEnvelope msg) throws Exception
	{

		HttpURLConnection connection = null;
		OutputStream out = null;

		try
		{
			// Obtenemos una conexión HTTP configurada a través de la fábrica
			connection = HttpConnectionFactory.createSoapHttpURLConnection(serviceUri);
			// escribimos el mensaje
			out = connection.getOutputStream();
			persister.write(msg, out);
			connection.getInputStream().close(); // importante para que se realize el envío
		} finally
		{
			if (out != null)
			{
				try
				{
					// intentamos cerrar el stream
					out.close();
				} catch (IOException logOrIgnore)
				{
				}
			}
			if (connection != null)
			{
				connection.disconnect();
			}
		}
	}

	/**
	 * Este método será el invocado al crear un nuevo Thread que realice las llamadas asíncronas ISend. No lanzará
	 * excepciones de ningún modo, por lo que no se podrá conocer el estado del envío. Las pruebas realizadas indican
	 * que este tipo de llamadas es soportada para volúmenes de 50KB de datos cada 250 ms. Si disminuimos el volumen de
	 * datos, la frecuencia de envío puede llegar hasta los 10HZ, y si aumentamos dicho volumen, la frecuencia deberá
	 * disminuirse de modo proporcional.
	 */
	private class DoIsend implements Runnable {

		private Object obj;

		public DoIsend(Object obj)
		{
			this.obj = obj;
		}

		@Override
		public void run()
		{
			try
			{
				invoke(obj, "ISend");
			} // No lanzaremos ninguna excepción, lo que significa que no aceptaremos ningún error, como corresponde
				// a las transmisiones asíncronas de tipo datagrama.
			catch (Exception ex)
			{
				ex.printStackTrace();
			}
		}
	}

	/**
	 * Este método será el invocado al crear un nuevo Thread que realice las llamadas asíncronas CheckableISend. Este
	 * método ejecutará la llamada e introducirá el resultado de la misma en el diccionario ticketResponses, para que
	 * posteriormente se pueda conocer el estado de la llamada
	 */
	private class DoCheckableIsend implements Runnable {

		private CheckableIsendData data;

		public DoCheckableIsend(CheckableIsendData data)
		{
			this.data = data;
		}

		@Override
		public void run()
		{
			// Si se produce una excepción en el envío, entenderemos que este no se ha realizado correctamente
			boolean result;
			try
			{
				result = send(data.getObjectToSend());
			} catch (Exception ex)
			{
				result = false;
			}
			// Introducimos el resultado en el diccionario (Si se ha producido una excepción, valdrá false, el valor
			// inicial)
			synchronized (ticketResponses)
			{
				ticketResponses.put(data.getTicketId(), result);
			}
		}
	}

	/**
	 * Esta estructura encapsula los datos que son necesarios para hacer una llamada CheckableISend
	 */
	private class CheckableIsendData {

		private UUID ticketId;
		private Object objectToSend;

		public CheckableIsendData(UUID ticketId, Object objectToSend)
		{
			this.ticketId = ticketId;
			this.objectToSend = objectToSend;
		}

		public Object getObjectToSend()
		{
			return objectToSend;
		}

		public UUID getTicketId()
		{
			return ticketId;
		}
	}

	/**
	 * Realiza un envío asíncrono fiable, reintentando las transmisiones cada cierto tiempo hasta que se consigue o se
	 * desiste.
	 */
	private class DoReliableIsend implements Runnable {

		private ReliableSendData data;

		public DoReliableIsend(ReliableSendData data)
		{
			this.data = data;
		}

		@Override
		public void run()
		{
			// Cambiamos la prioridad del Thread al mínimo, para disminuir la ralentización del dispositivo
			Thread.currentThread().setPriority(Thread.MIN_PRIORITY);
			// Necesitaremos un generador de números aleatorios, pues entre reintentos esperaremos un periodo aleatorio
			// entre 1 y data.Timeout segundos.
			Random rn = new Random();
			// Para ahorrar recursos, no llamaremos directamente al método Send, sino que crearemos el mensaje a
			// transmitir, y lo retransmitiremos hasta que se envíe o se supere el timeout.
			ServandoSOAPEnvelope msg = prepareMessage(data.getObj(), "Send");

			// Marcamos el tiempo inicial.
			double elapsed = 0;
			long tinic = System.nanoTime();
			do
			{
				try
				{
					ServandoSOAPEnvelope response = getReply(msg);
					// Si obtenemos una respuesta afirmativa...
					if (Boolean.parseBoolean((String) response.getBody().getObject()))
					{
						// ... ejecutamos el callback de finalización correcta
						data.getCallback().onSuccess(data.getTicket());
						return;
					}

					Log.d(DEBUG_TAG, "attemp failed " + ((System.nanoTime() - tinic) / 1e9));
					// Esperamos entre uno y data.Timeout/10 segundos para volverlo a intentar
					if (data.getTimeout() > 1)
					{
						Thread.sleep(1000 + rn.nextInt((int) data.getTimeout() * 100));
					}
				} catch (Exception doNothingException)
				{
					// ignoramos cualquier tipo de excepción
				}

				elapsed = (System.nanoTime() - tinic) / 1e9;
				Log.d(DEBUG_TAG, "attemp failed (" + elapsed + "sec)");
			} while (elapsed < data.getTimeout());
			// Ha expirado el timeout, por lo que ejecutamos el callback que lo indica
			data.getCallback().onTimeout(data.getTicket());
		}
	}

	/**
	 * Encapsula los parámetros necesarios para la creación de un nuevo thread durante una ejecución de
	 * {@link ObjectTransporter#reliableSend(Object, long, TransmissionFinishedCallback)}
	 */
	private class ReliableSendData {

		private final long timeout;
		private final Object obj;
		private final TransmissionFinishedCallback callback;
		private final ISendTicket ticket;

		public ReliableSendData(Object obj, long timeout, TransmissionFinishedCallback callback, ISendTicket ticket)
		{
			this.obj = obj;
			this.timeout = timeout;
			this.callback = callback;
			this.ticket = ticket;
		}

		public long getTimeout()
		{
			return timeout;
		}

		public Object getObj()
		{
			return obj;
		}

		public TransmissionFinishedCallback getCallback()
		{
			return callback;
		}

		public ISendTicket getTicket()
		{
			return ticket;
		}
	}

	/**
	 * @return the remoteService
	 */
	public String getRemoteService()
	{
		return remoteService;
	}

	/**
	 * @param remoteService the remoteService to set
	 */
	public void setRemoteService(String remoteService)
	{
		this.remoteService = remoteService;
	}

	/**
	 * @return the service URI
	 */
	public URI getServiceUri()
	{
		return serviceUri;
	}

	/**
	 * @param serviceUri the service URI to set
	 */
	public void setServiceUri(URI serviceUri)
	{
		this.serviceUri = serviceUri;
	}
}
