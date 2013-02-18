package es.usc.citius.servando.android.httpServer;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

import org.apache.http.HttpException;
import org.apache.http.impl.DefaultConnectionReuseStrategy;
import org.apache.http.impl.DefaultHttpResponseFactory;
import org.apache.http.impl.DefaultHttpServerConnection;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.protocol.BasicHttpContext;
import org.apache.http.protocol.BasicHttpProcessor;
import org.apache.http.protocol.HttpRequestHandlerRegistry;
import org.apache.http.protocol.HttpService;
import org.apache.http.protocol.ResponseConnControl;
import org.apache.http.protocol.ResponseContent;
import org.apache.http.protocol.ResponseDate;
import org.apache.http.protocol.ResponseServer;

import android.util.Log;
import es.usc.citius.servando.android.util.NetworkUtils;

public class WebServer extends Thread {

	private static final String SERVER_NAME = "ServandoHTTPServer";
	private static String HOME_PATTERN = "/*";
	private static String FILE_PATTERN = "/files*";
	private static String SOAP_PATTERN = "/soap*";

	private static final String TAG = WebServer.class.getSimpleName();

	private boolean isRunning = false;
	private BasicHttpProcessor httpproc = null;
	private BasicHttpContext httpContext = null;
	private HttpService httpService = null;
	private HttpRequestHandlerRegistry registry = null;
	private WebServerConfig config;
	private ServerSocket serverSocket;

	public WebServer(WebServerConfig cfg)
	{

		super(SERVER_NAME);

		config = cfg;

		httpContext = new BasicHttpContext();
		httpproc = new BasicHttpProcessor();
		httpproc.addInterceptor(new ResponseDate());
		httpproc.addInterceptor(new ResponseServer());
		httpproc.addInterceptor(new ResponseContent());
		httpproc.addInterceptor(new ResponseConnControl());

		FILE_PATTERN = "/" + config.getFileServerPath() + "*";

		registry = new HttpRequestHandlerRegistry();
		registry.register(FILE_PATTERN, new FileServerHandler(config));
		registry.register(SOAP_PATTERN, new SoapMessageHandler(config));
		registry.register(HOME_PATTERN, new RootMessageHandler());

		httpService = new HttpService(httpproc, new DefaultConnectionReuseStrategy(), new DefaultHttpResponseFactory());
		httpService.setHandlerResolver(registry);
	}

	@Override
	public void run()
	{

		super.run();

		try
		{
			serverSocket = new ServerSocket(config.getServerPort());
			serverSocket.setReuseAddress(true);

			while (isRunning)
			{
				try
				{
					Log.d("WEB_SERVER", "Web server waiting for connections at " + NetworkUtils.getLocalIpAddress() + ":" + config.getServerPort());
					final Socket socket = serverSocket.accept();
					String remoteAddr = socket.getInetAddress().getHostAddress();
					Log.d("WEB_SERVER", "Connection accepted from " + remoteAddr);
					DefaultHttpServerConnection serverConnection = new DefaultHttpServerConnection();
					serverConnection.bind(socket, new BasicHttpParams());
					// pass remote address as httpcontext atribute, for use to authentication
					httpContext.setAttribute("remoteAddress", remoteAddr);
					httpService.handleRequest(serverConnection, httpContext);
					serverConnection.shutdown();
				} catch (IOException e)
				{
					Log.e(TAG, "IOException in server socket", e);
				} catch (HttpException e)
				{
					Log.e(TAG, "HttpException in server socket", e);
				} catch (Exception e)
				{
					// interrupted exception is thrown on server stop when
					// serversocket is stopped
					if (!(e instanceof InterruptedException))
					{
						Log.e(TAG, "Exception in server socket", e);
					}
				}
			}
			serverSocket.close();

		} catch (IOException e)
		{
			e.printStackTrace();
		}
	}

	public synchronized void startServer()
	{
		Log.d(TAG, "Starting HTTPServer...");
		isRunning = true;
		super.start();
	}

	public synchronized void stopServer()
	{
		Log.d(TAG, "Stopping HTTPServer...");
		if (serverSocket != null)
		{
			try
			{
				serverSocket.close();
			} catch (IOException e)
			{
			}
		}
		isRunning = false;
	}

}