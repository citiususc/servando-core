package es.usc.citius.servando.android.httpServer;

import java.io.File;
import java.io.FilenameFilter;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;

import org.apache.http.HttpEntity;
import org.apache.http.HttpEntityEnclosingRequest;
import org.apache.http.HttpException;
import org.apache.http.HttpRequest;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.utils.URLEncodedUtils;
import org.apache.http.entity.ContentProducer;
import org.apache.http.entity.EntityTemplate;
import org.apache.http.entity.FileEntity;
import org.apache.http.protocol.HttpContext;
import org.apache.http.protocol.HttpRequestHandler;

import android.content.Context;
import android.util.Log;
import es.usc.citius.servando.R;
import es.usc.citius.servando.android.util.CryptoUtils;
import es.usc.citius.servando.android.util.NetworkUtils;
import es.usc.citius.servando.android.util.StringUtils;

public class FileServerHandler implements HttpRequestHandler {

	private enum AuthCode
	{
		NO_LOGIN_ATTEMP, LOGOUT, LOGIN_CORRECT, INVALID_PASSWORD, MAX_ATTEMPS_EXCEEDED
	}

	private static final String TAG = FileServerHandler.class.getSimpleName();
	private static LinkedHashSet<String> authClients;

	private WebServerConfig config;
	private File rootFolder;
	private String template;
	private int fileCount;

	/**
	 * 
	 * @param cfg
	 */
	public FileServerHandler(WebServerConfig cfg)
	{
		rootFolder = cfg.getRootDirectory();
		authClients = new LinkedHashSet<String>();
		config = cfg;
	}

	@Override
	public void handle(HttpRequest request, HttpResponse response, HttpContext httpContext) throws HttpException, IOException
	{

		synchronized (this)
		{

			HttpEntity entity = null;
			AuthCode authCode = null;
			boolean authenticated = false;

			String remoteAddress = (String) httpContext.getAttribute("remoteAddress");

			Log.d(TAG, "Handling request from: " + httpContext.getAttribute("remoteAddress"));

			if (!config.isAuthenticationRequired())
			{
				authenticated = true;
			} else
			{
				authCode = authenticateRequest(request);
				if (authCode == AuthCode.LOGOUT)
				{
					authenticated = false;
					authClients.remove(remoteAddress);
				} else if (authCode == AuthCode.LOGIN_CORRECT || authClients.contains(remoteAddress))
				{
					authClients.add(remoteAddress);
					authenticated = true;
				}
			}

			Log.d(TAG, "Handling request. Client" + (authenticated ? "" : " not") + " authenticated");

			if (authenticated)
			{

				entity = onValidRequest(request);
			} else
			{
				entity = onInvalidRequest(authCode);
			}

			response.setEntity(entity);

		}
	}

	/**
	 * 
	 * @param request
	 * @param errorMessage
	 * @return
	 */
	private AuthCode authenticateRequest(HttpRequest request)
	{

		String password = null;

		if (request instanceof HttpEntityEnclosingRequest)
		{

			List<NameValuePair> params;
			try
			{
				params = URLEncodedUtils.parse(((HttpEntityEnclosingRequest) request).getEntity());
				for (NameValuePair pair : params)
				{
					if ("password".equals(pair.getName()))
					{
						password = pair.getValue();
					} else if ("exit".equals(pair.getName()))
					{
						return AuthCode.LOGOUT;
					}
				}
			} catch (IOException e)
			{
				// do nothing
			}
		}

		if (password != null && validatePassword(password))
		{
			return AuthCode.LOGIN_CORRECT;
		} else if (password == null)
		{
			return AuthCode.NO_LOGIN_ATTEMP;
		} else
		{
			return AuthCode.INVALID_PASSWORD;

		}

	}

	/**
	 * 
	 * @param request
	 * @return
	 */
	private HttpEntity onValidRequest(HttpRequest request)
	{

		HttpEntity entity;

		template = openHTMLString(config.getContext(), R.raw.tpl_file_server);

		String urFileName;
		try
		{
			urFileName = URLDecoder.decode(request.getRequestLine().getUri(), "UTF-8");
		} catch (Exception e)
		{
			urFileName = "/" + config.getFileServerPath();
		}

		final String requestedFileName = urFileName;
		final File requestedPath = new File(requestedFileName.replaceFirst("/" + config.getFileServerPath(), rootFolder.getAbsolutePath()));
		final File requestedFile = requestedPath.exists() ? requestedPath : rootFolder;

		Log.d(TAG, "Requested file: " + requestedPath.getAbsolutePath());

		// if requested file is a directory, list it
		if (requestedFile.isDirectory())
		{
			entity = new EntityTemplate(new ContentProducer()
			{
				@Override
				public void writeTo(final OutputStream outstream) throws IOException
				{
					template = template.replace("%file_list%", listDirectoryAsTable(requestedFile));
					template = template.replace("%local_ip_address%", NetworkUtils.getLocalIpAddress());
					template = template.replace("%listed_directory%", requestedFileName);
					template = template.replace("%file_server_path%", config.getFileServerPath());
					template = template.replace("%file_count%", String.valueOf(fileCount));
					PrintWriter writer = new PrintWriter(outstream);
					writer.write(template);
					writer.flush();
				}
			});
			((EntityTemplate) entity).setContentType("text/html");

		} else
		{
			// else send file
			entity = new FileEntity(requestedFile, getContentType(requestedFileName));
		}
		return entity;
	}

	/**
	 * 
	 * @param cause
	 * @return
	 */
	private HttpEntity onInvalidRequest(AuthCode cause)
	{

		String message = "";

		if (cause == AuthCode.INVALID_PASSWORD)
		{
			message = htmlErrorMessage("Invalid password");
		} else if (cause == AuthCode.MAX_ATTEMPS_EXCEEDED)
		{
			message = htmlErrorMessage("Maximum number of failed login attempts exceeded. <br> Please try again later");
		} else if (cause == AuthCode.NO_LOGIN_ATTEMP)
		{
			message = "Authentication required";
		} else if (cause == AuthCode.LOGOUT)
		{
			message = "Logged out";
		}

		Log.d(TAG, "Invalid request: " + cause);

		HttpEntity entity;
		int tpl = cause == AuthCode.MAX_ATTEMPS_EXCEEDED ? R.raw.tpl_file_server_wait : R.raw.tpl_file_server_login;
		template = openHTMLString(config.getContext(), tpl);
		template = template.replace("%local_ip_address%", NetworkUtils.getLocalIpAddress());
		template = template.replace("%file_server_path%", config.getFileServerPath());
		template = template.replace("%error_message%", message);
		entity = new EntityTemplate(new ContentProducer()
		{
			@Override
			public void writeTo(final OutputStream outstream) throws IOException
			{
				PrintWriter writer = new PrintWriter(outstream);
				writer.write(template);
				writer.flush();
			}
		});
		return entity;
	}

	/**
	 * List directory
	 * 
	 * @param dir
	 * @return
	 */
	private String listDirectoryAsTable(File dir)
	{

		SimpleDateFormat df = new SimpleDateFormat("HH:mm:ss dd/MM/yy");

		String[] fileList = config.showHiddenFiles() ? dir.list() : dir.list(new FilenameFilter()
		{
			@Override
			public boolean accept(File dir, String filename)
			{
				return !filename.startsWith(".");
			}
		});

		fileCount = fileList.length;

		String html = openFilesTable(dir);

		for (String fileName : fileList)
		{

			File file = new File(dir.getAbsolutePath() + "/" + fileName);

			String contentType = getContentType(fileName);
			String cssCls = dir.isDirectory() ? "dir" : "file";
			String fileSize = !dir.isDirectory() ? ("" + getFileSize(dir)) : "0";
			String permissions = "" + (dir.canRead() ? "r" : "") + (dir.canWrite() ? "w" : "") + (fileName.startsWith(".") ? " (hidden)" : "");
			Calendar lastModified = new GregorianCalendar();
			lastModified.setTime(new Date(dir.lastModified()));
			boolean needLink = (config.isDownloadEnabled() && contentType != null) || dir.isDirectory();

			// write table
			html += htmlOpenTr();
			html += htmlTd(needLink ? htmlLink("http://" + NetworkUtils.getLocalIpAddress() + ":" + "8080/" + mapPathToSdCard(file), fileName, cssCls)
					: fileName);
			html += htmlTd(df.format(lastModified.getTime()));
			html += htmlTd(contentType != null ? contentType : (dir.isDirectory() ? "DIR" : "unknown"));
			html += htmlTd(fileSize + " bytes");
			html += htmlTd(permissions);
			html += htmlCloseTr();
		}

		html += closeFilesTable();

		return html;
	}

	/**
	 * 
	 * @param requestedFileName
	 * @return
	 */
	private String getContentType(String requestedFileName)
	{
		Map<String, String> cts = ServerDefaults.contentTypes;
		for (String ct : cts.keySet())
		{
			if (requestedFileName.endsWith(ct))
			{
				return cts.get(ct);
			}
		}
		return null;
	}

	/**
	 * 
	 * @param context
	 * @param id
	 * @return
	 */
	public static String openHTMLString(Context context, int id)
	{
		InputStream is = context.getResources().openRawResource(id);
		return StringUtils.convertStreamToString(is);
	}

	/**
	 * 
	 * @param pass
	 * @return
	 */
	private boolean validatePassword(String pass)
	{
		try
		{
			if (getEncryptedPassword().equals(CryptoUtils.MD5(pass)))
			{
				Log.d(TAG, "Valid password");
				return true;
			}
		} catch (UnsupportedEncodingException e)
		{
			e.printStackTrace();
		}
		Log.d(TAG, "Incorrect password");
		return false;
	}

	/**
	 * 
	 * @return
	 */
	private String getEncryptedPassword()
	{
		// password = sadmin
		return "c5edac1b8c1d58bad90a246d8f08f53b";
	}

	/**
	 * 
	 * @param f
	 * @return
	 */
	public long getFileSize(File f)
	{
		long size = 0;
		InputStream stream = null;
		try
		{
			stream = f.toURL().openStream();
			size = stream.available();
		} catch (Exception e)
		{

		} finally
		{
			try
			{
				stream.close();
			} catch (IOException e)
			{
			}
		}
		return size;
	}

	/**
	 * 
	 * @param f
	 * @return
	 */
	private String mapPathToSdCard(File f)
	{
		return f.getAbsolutePath().replace(rootFolder.getAbsolutePath(), config.getFileServerPath());
	}

	/**
	 * 
	 * @param url
	 * @param title
	 * @return
	 */
	public String htmlLink(String url, String title, String cls)
	{
		return "<a cls='" + (cls != null ? cls : "") + "' href='" + url + "' title='" + title + "'>" + title + "</a>";
	}

	public String htmlErrorMessage(String message)
	{
		return "<span class='error'> " + message + "</span>";
	}

	/**
	 * 
	 * @param dir
	 * @return
	 */
	public String openFilesTable(File dir)
	{
		String html = "<table cellspacing='10'>";
		html += "<tr><td>Name</td><td>Last modified</td><td>Type</td><td>Size</td><td>Attributes</td></tr>";
		html += "<tr><td></td><td></td><td></td><td></td><td></td></tr>";

		if (!dir.equals(rootFolder))
		{
			html += "<tr>";
			html += "<td><a href='http://" + NetworkUtils.getLocalIpAddress() + ":" + "8080/" + mapPathToSdCard(dir.getParentFile())
					+ "'> .. </a></td><td></td><td></td><td><td></td></td>";
			html += "</tr>";
		}
		return html;
	}

	/**
	 * 
	 * @return
	 */
	public String closeFilesTable()
	{
		return "</table>";
	}

	/**
	 * 
	 * @param content
	 * @return
	 */
	public String htmlTd(String content)
	{
		return "<td>" + content + "</td>";
	}

	/**
	 * 
	 * @return
	 */
	public String htmlOpenTr()
	{
		return "<tr>";
	}

	/**
	 * 
	 * @return
	 */
	public String htmlCloseTr()
	{
		return "</tr>";
	}

}