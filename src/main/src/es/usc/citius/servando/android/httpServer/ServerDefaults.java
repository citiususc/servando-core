package es.usc.citius.servando.android.httpServer;

import java.util.HashMap;
import java.util.Map;

/**
 * Server default params
 * 
 * @author Ángel Piñeiro
 */
public class ServerDefaults {

	public static Map<String, String> contentTypes;

	public static final int PORT = 8080;

	public static final String CONTEXT_PATH = "files";
	public static final boolean ENABLE_DOWNLOADS = true;
	public static final boolean AUNTHENTICATION_REQUIRED = true;
	public static final boolean SHOW_HIDDEN_FILES = true;

	static
	{
		contentTypes = getContentTypes();
	}

	private static Map<String, String> getContentTypes()
	{

		Map<String, String> cTypes = new HashMap<String, String>();
		cTypes.put(".xml", "text/plain");
		cTypes.put(".test", "text/plain");
		cTypes.put(".log", "text/plain");
		cTypes.put(".deviceinfo", "text/plain");
		cTypes.put(".hea", "text/plain");
		cTypes.put(".dat", "application/octet-stream");
		cTypes.put(".jar", "application/java-archive");
		cTypes.put(".pdf", "application/pdf");
		cTypes.put(".gif", "image/gif");
		cTypes.put(".jpeg", "image/jpeg");
		cTypes.put(".jpg", "image/jpeg");
		cTypes.put(".png", "image/png");
		cTypes.put(".wav", "audio/x-wav");
		cTypes.put(".mp3", "audio/mp3");
		cTypes.put(".ogg", "video/ogg");
		cTypes.put(".avi", "video/x-msvideo");
		cTypes.put(".wmv", "video/x-ms-wmv");
		return cTypes;
	}

}