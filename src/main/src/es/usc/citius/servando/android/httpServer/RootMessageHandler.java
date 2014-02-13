package es.usc.citius.servando.android.httpServer;

import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;

import org.apache.http.HttpEntity;
import org.apache.http.HttpException;
import org.apache.http.HttpRequest;
import org.apache.http.HttpResponse;
import org.apache.http.entity.ContentProducer;
import org.apache.http.entity.EntityTemplate;
import org.apache.http.protocol.HttpContext;
import org.apache.http.protocol.HttpRequestHandler;

import es.usc.citius.servando.android.util.NetworkUtils;

public class RootMessageHandler implements HttpRequestHandler {

	@Override
	public void handle(HttpRequest request, HttpResponse response, HttpContext httpContext) throws HttpException, IOException
	{

		String contentType = "text/html";

		HttpEntity entity = new EntityTemplate(new ContentProducer()
		{
			@Override
			public void writeTo(final OutputStream outstream) throws IOException
			{
				PrintWriter writer = new PrintWriter(outstream);
				writer.write("<html><h1>Servando HTTP Server running at " + NetworkUtils.getLocalIpAddress() + "</h1></html>");
				writer.flush();
			}
		});

		((EntityTemplate) entity).setContentType(contentType);
		response.setEntity(entity);
	}
}