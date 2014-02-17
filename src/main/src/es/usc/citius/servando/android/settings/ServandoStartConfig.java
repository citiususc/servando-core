package es.usc.citius.servando.android.settings;

import android.content.Context;
import android.os.Environment;
import android.util.Log;

import java.io.File;
import java.io.InputStream;
import java.net.URL;
import java.util.Properties;
import java.util.PropertyResourceBundle;
import java.util.ResourceBundle;

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

	private static final String CONFIG_FILE = "/servando.properties";

	// Set of properties keys
	public static final String DEFAULT_SETTINGS = "platform.defaultsettings";
	public static final String SETTINGS = "platform.settings";
	public static final String EXTERNAL_PATH = "platform.externalpath";
	public static final String DIRECTORY = "platform.directory";

	public static final String CHECK_UPDATES_URL = "updates.chekupdatesurl";
	public static final String APK_URL = "updates.apkurl";
	public static final String SDCARD_DATA_URL = "updates.sdfilesurl";
	public static final String SDCARD_PATIENT_DATA_URL = "updates.patientsdfilesurl";
	public static final String PROTOCOL_UPDATE_URL = "updates.protocolupdateurl";

    public static final String ENABLE_DYNAMIC_SETUP = "platform.enabledynamicsetup";

	/**
	 * Properties
	 */
	private Properties configuration = null;

	private ServandoStartConfig()
	{
        configuration = new Properties();
	}


    public void load(Context ctx){
        try
        {
            InputStream stream = ctx.getAssets().open("servando.properties");
            //InputStream stream = this.getClass().getResourceAsStream(CONFIG_FILE);
            configuration.load(stream);
            Log.d(DEBUG_TAG, "Loading start config done!");
        } catch (Exception e)
        {
            Log.e(DEBUG_TAG, "Error reading platform config", e);
            configuration.put(SETTINGS, "settings.xml");
            configuration.put(DIRECTORY,"ServandoPlatformData");
            configuration.put(EXTERNAL_PATH,"/Android/data/es.usc.citius.servando/");
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

	public String getPlatformInstallationPath()
	{
		return configuration.getProperty(EXTERNAL_PATH);
	}

	public boolean isPlatformSetupOnSDCard(){

		String settingsPath = Environment.getExternalStorageDirectory().getPath() + configuration.getProperty(EXTERNAL_PATH)
				+ configuration.getProperty(DIRECTORY) + "/"
				+ configuration.getProperty(SETTINGS);
		return new File(settingsPath).exists();
 	}

    public boolean dynamicSetupEnabled(){
            String enable = configuration.getProperty(ENABLE_DYNAMIC_SETUP);
            return enable!=null && enable.equalsIgnoreCase("true");
    }



}
