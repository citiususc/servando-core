package es.usc.citius.servando.android.communications;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URL;

public class HttpConnectionFactory {

	public static HttpURLConnection createSoapHttpURLConnection(String url) throws MalformedURLException, IOException
	{

		HttpURLConnection connection = (HttpURLConnection) new URL(url).openConnection();
		connection.setRequestMethod("POST");
		connection.addRequestProperty("Connection", "close");
		connection.addRequestProperty("Content-Type", "application/soap+xml; charset=utf-8");
		connection.setDoOutput(true);
		connection.setDoInput(true);
		return connection;
	}

	public static HttpURLConnection createSoapHttpURLConnection(URI uri) throws MalformedURLException, IOException
	{
		return createSoapHttpURLConnection(uri.toString());
	}

}
