package es.usc.citius.servando.android.settings;

import java.io.IOException;
import java.util.Properties;

import android.util.Log;

/**
 * Provides access to the platform default configuration parameters, such as paths or settings files names obtained from
 * 'servando.properties' file and allows to change it.
 * 
 * This is useful for testing, because it allows to establish different settings for each test.
 * 
 * @author angel
 * 
 */
public class ServandoStartConfig {

	private static final String DEBUG_TAG = "PlatformConfig";

	/**
	 * Private instance
	 */
	private static ServandoStartConfig cfg = null;

	private static final String CONFIG_FILE = "servando.properties";

	// Set of properties keys
	public static final String DEFAULT_SETTINGS = "platform.defaultsettings";
	public static final String SETTINGS = "platform.settings";
	public static final String EXTERNAL_PATH = "platform.externalpath";
	public static final String DIRECTORY = "platform.directory";

	/**
	 * Properties
	 */
	private Properties configuration = null;

	private ServandoStartConfig()
	{
		configuration = new Properties();
		try
		{
			configuration.load(ServandoStartConfig.class.getResourceAsStream(CONFIG_FILE));
		} catch (IOException e)
		{
			Log.e(DEBUG_TAG, "Error reading platform config", e);
			// configuration.put(SETTINGS, "settings.xml");
			// configuration.put(DIRECTORY,"ServandoPlatformData");
			// configuration.put(EXTERNAL_PATH,"/Android/data/es.usc.citius.servando/files");
		}
	}

	public static ServandoStartConfig getInstance()
	{
		if (cfg == null)
			cfg = new ServandoStartConfig();
		return cfg;
	}

	public String get(String key)
	{
		return (String) configuration.get(key);
	}

	public void set(String key, String value)
	{
		configuration.put(key, value);
	}

}
