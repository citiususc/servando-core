package es.usc.citius.servando.android.httpServer;

import java.io.IOException;
import java.io.OutputStream;
import java.util.Arrays;

import org.apache.http.HttpEntity;
import org.apache.http.HttpEntityEnclosingRequest;
import org.apache.http.HttpException;
import org.apache.http.HttpRequest;
import org.apache.http.HttpResponse;
import org.apache.http.entity.ContentProducer;
import org.apache.http.entity.EntityTemplate;
import org.apache.http.protocol.HttpContext;
import org.apache.http.protocol.HttpRequestHandler;
import org.apache.http.util.EntityUtils;

import android.util.Log;
import es.usc.citius.servando.android.ServandoPlatformFacade;
import es.usc.citius.servando.android.communications.CommunicableService;
import es.usc.citius.servando.android.communications.ServandoCommunicationsModule;
import es.usc.citius.servando.android.communications.SoapHelper;
import es.usc.citius.servando.android.communications.model.BooleanWrapper;
import es.usc.citius.servando.android.communications.soap.ServandoSOAPEnvelope;
import es.usc.citius.servando.android.xml.helpers.SimpleXMLSerializator;

/**
 * 
 * 
 * @author Ángel Piñeiro
 */
public class SoapMessageHandler implements HttpRequestHandler {

	private final String SEND = "Send";
	private final String I_SEND = "ISend";
	private final String SEND_RECEIVE = "SendReceive";

	private static final String TAG = SoapMessageHandler.class.getSimpleName();
	/**
	 * Serializator for parsing the SOAP envelope contained in the HTTP request
	 */
	private SimpleXMLSerializator s;

	/**
	 * Creates a new instance of {@link SoapMessageHandler}
	 * 
	 * @param transporter
	 */
	public SoapMessageHandler(WebServerConfig cfg)
	{
		s = new SimpleXMLSerializator();
	}

	/**
	 * Implements HTTP request handling.
	 * 
	 * Extracts the SOAP message from the HTTPRequest payload, and then redirects the message to its receiver (a service
	 * already registered in the communications module). Finally, sends the response of the receiver back to the sender.
	 * 
	 */
	@Override
	public void handle(HttpRequest request, HttpResponse response, HttpContext httpContext) throws HttpException, IOException
	{

		// objet to store the soap envelope contained in the HttpRequest
		ServandoSOAPEnvelope requestSoapEnvelope = null;
		String soapPayload = null;
		String client = null;

		if (request instanceof HttpEntityEnclosingRequest)
		{
			Log.d(TAG, Arrays.toString(request.getAllHeaders()));
			// obtain an HTTPEntity from post request
			HttpEntity entity = ((HttpEntityEnclosingRequest) request).getEntity();
			// get the soap message contained in the payload as a string
			soapPayload = EntityUtils.toString(entity);
			try
			{
				// try to deserialize the soap message
				requestSoapEnvelope = (ServandoSOAPEnvelope) s.deserializeString(soapPayload, ServandoSOAPEnvelope.class);
				ServandoPlatformFacade.getInstance().logSerializable(requestSoapEnvelope);

			} catch (Exception e)
			{
				Log.e(TAG, "Error reading SOAP message", e);
				return;
			}
		}

		// get receiver id from soap header
		String service = requestSoapEnvelope.getHeader().getService();
		// get action to invoke from soap header
		String action = requestSoapEnvelope.getHeader().getAction();
		// get request data from soap body
		Object data = requestSoapEnvelope.getBody().getObject();
		// invoke requested action on the receiver
		Object serviceResponse = getServiceResponse(service, action, data, client);

		// send the result to the sender
		if (!action.equals(I_SEND))
		{
			sendResponse(serviceResponse, service, action, request, response, httpContext);
		}
	}

	/**
	 * 
	 * @param service Receiver id
	 * @param action Action to invoke on the receiver
	 * @param obj Received object
	 * @param client Request client ip (for distinguishing clients)
	 * @return
	 */
	private Object getServiceResponse(String service, String action, Object obj, String client)
	{
		// service that will execute the request
		CommunicableService destinationService;
		// response object
		Object responseObj = null;
		// if a service with the specified id is registered on the communications module ...
		if ((destinationService = ServandoCommunicationsModule.getInstance().getRegisteredService(service)) != null)
		{
			// invoque send...
			if (SEND.equalsIgnoreCase(action))
			{
				responseObj = destinationService.processSend(obj, client);
			}
			// or invoque isend ...
			else if (I_SEND.equalsIgnoreCase(action))
			{
				destinationService.processISend(obj, client);
			}
			// or invoke sendReceive
			else if (SEND_RECEIVE.equalsIgnoreCase(action))
			{
				responseObj = destinationService.processSendReceive(obj, client);
			}
			// or log error
			else
			{
				Log.e(TAG, "Uknown action received (" + action + ")");
			}
		}

		Log.e(TAG, "Invoked service not registered (" + service + ")");

		return responseObj;
	}

	/**
	 * 
	 * @param responseObject Object to send as response
	 * @param request
	 * @param response
	 * @param httpContext
	 */
	private void sendResponse(final Object responseObject, final String remoteService, final String action, HttpRequest request,
			HttpResponse response, HttpContext httpContext)
	{

		// Content type of the response
		String contentType = "application/soap+xml; charset=utf-8";
		// HttpEntity to write the soap message
		HttpEntity entity = new EntityTemplate(new ContentProducer()
		{

			@Override
			public void writeTo(final OutputStream outstream) throws IOException
			{
				// Encapsulate the object to send in a soap messaje with the specified action
				ServandoSOAPEnvelope msg = responseObject != null ? SoapHelper.prepareMessage(responseObject, action, remoteService)
						: SoapHelper.prepareMessage(new BooleanWrapper(false), action, remoteService);
				try
				{
					// write the message to the connection stream
					s.getSerializer().write(msg, outstream);
				} catch (Exception e)
				{
					Log.d(TAG, "Error writting SOAP response", e);
				}
			}
		});

		// set the entity content type
		((EntityTemplate) entity).setContentType(contentType);
		// write the entity
		response.setEntity(entity);
	}
}