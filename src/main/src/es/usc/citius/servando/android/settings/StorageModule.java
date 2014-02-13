/**
 *
 */
package es.usc.citius.servando.android.settings;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.Reader;
import java.security.InvalidParameterException;
import java.util.ArrayList;
import java.util.HashMap;

import org.simpleframework.xml.Serializer;
import org.simpleframework.xml.core.Persister;

import android.os.Environment;
import android.util.Log;
import es.usc.citius.servando.android.models.services.IPlatformService;
import es.usc.citius.servando.android.models.services.ServiceReflectionInfo;
import es.usc.citius.servando.android.models.services.StorageAvailableService;
import es.usc.citius.servando.android.services.ServiceStorageHelper;
import es.usc.citius.servando.android.xml.helpers.SimpleXMLSerializator;

/**
 * 
 * Storage Module. Centralizes the access to the device SDCard, and handles the creation of working directories and
 * subdirectories of services that implements the {@link StorageAvailableService} interface.
 * 
 * @author angel
 * 
 */
public class StorageModule {

	private static final String DEBUG_TAG = StorageModule.class.getSimpleName();

	/**
	 * Application path relative to the storage base path.
	 * 
	 * Android standard directory nomenclature: If the application is uninstalled, this directory and all its contents
	 * will be deleted
	 */
	private static String PLATFORM_PATH = "";
	/**
	 * Platform folder under the platform path
	 */
	private static String PLATFORM_FOLDER = "";
	/**
	 * Platform logs folder under the platform path
	 */
	private String platformLogsPath = "";
	/**
	 * Platform services folder under the platform path
	 */
	private String platformServicesPath = "";
	/**
	 *
	 */
	private final HashMap<String, StorageAvailableService> registeredServices;
	/**
	 *
	 */
	private static HashMap<String, ServiceStorageHelper> storageHelpers = new HashMap<String, ServiceStorageHelper>();
	/**
	 * DataSource static instance for Singleton
	 */
	private static StorageModule instance = null;
	/**
	 * Storage base path
	 */
	private static String basePath = "";
	/**
	 * Settings Path
	 */
	private static String settingsPath = "";

	/**
	 * Platform settings
	 */
	private ServandoSettings settings;

	/**
	 * Private constructor for Singleton
	 */
	private StorageModule()
	{
		registeredServices = new HashMap<String, StorageAvailableService>();
		updatePaths();

	}

	/**
	 * Public method to get the unique instance
	 */
	public static StorageModule getInstance()
	{
		if (instance == null)
		{
			instance = new StorageModule();
		}
		return instance;
	}

	public void registerService(StorageAvailableService service)
	{

		Log.i(DEBUG_TAG, "Service " + service.getId() + "registered in the Storage Module");

		registeredServices.put(service.getId(), service);
		storageHelpers.put(service.getId(), createServiceStoreageHelper(service));
	}

	public void unregisterService(StorageAvailableService service)
	{

		if (registeredServices.containsKey(service.getId()))
		{
			registeredServices.remove(service.getId());
			storageHelpers.remove(service.getId());
			Log.i(DEBUG_TAG, "Service " + service.getId() + "unregistered from the Storage Module");
		}
	}

	/**
	 * Gets the {@link ServiceStorageHelper} of an concrete service
	 * 
	 * @param serviceId The id of the service
	 * @return
	 */
	public ServiceStorageHelper getServiceStorageHelper(StorageAvailableService service)
	{

		if (!registeredServices.containsKey(service.getId()))
		{
			throw new InvalidParameterException("Service " + service.getId() + " not registered in the Storage Module");
		}

		return storageHelpers.get(service.getId());
	}

	/**
	 * Creates a {@link ServiceStorageHelper} for a concrete service. If the service folder are no present on the
	 * SDCard, it is created.
	 * 
	 * @param serviceId
	 * @return
	 */
	private ServiceStorageHelper createServiceStoreageHelper(IPlatformService service)
	{

		String serviceId = service.getId();

		Log.i(DEBUG_TAG, "Creating Storage Helper for service " + serviceId);
		ServiceStorageHelper helper = new ServiceStorageHelper();
		// String base = StorageModule.getInstance().getBasePath();

		String servicePath = platformServicesPath + "/" + serviceId;
		String serviceLogPath = platformServicesPath + "/" + serviceId + "/" + ServiceStorageHelper.LOGS;
		String serviceFilesPath = platformServicesPath + "/" + serviceId + "/" + ServiceStorageHelper.DATA;
		String serviceAssetsPath = platformServicesPath + "/" + serviceId + "/" + ServiceStorageHelper.ASSETS;

		Log.i(DEBUG_TAG, "Creating service folders on path " + servicePath);

		File f = null;
		f = new File(serviceLogPath);
		if (!f.exists())
		{
			f.mkdirs();
		}
		f = new File(serviceFilesPath);
		if (!f.exists())
		{
			f.mkdirs();
		}
		f = new File(serviceAssetsPath);
		if (!f.exists())
		{
			f.mkdirs();
		}

		helper.setServicePath(servicePath);
		helper.setServiceLogsPath(serviceLogPath);
		helper.setServiceFilesPath(serviceFilesPath);
		helper.setServiceAssetsPath(serviceAssetsPath);

		helper.addWorkingDirectory(ServiceStorageHelper.SERVICE, servicePath);
		helper.addWorkingDirectory(ServiceStorageHelper.LOGS, serviceLogPath);
		helper.addWorkingDirectory(ServiceStorageHelper.DATA, serviceFilesPath);
		helper.addWorkingDirectory(ServiceStorageHelper.ASSETS, serviceAssetsPath);

		return helper;
	}

	/**
	 * Check whether the external storage is available
	 */
	public boolean checkExternalStorageAvailability()
	{

		boolean externalStorageWriteable = false;

		// get external media state
		String state = Environment.getExternalStorageState();

		if (Environment.MEDIA_MOUNTED.equals(state))
		{
			// We can read and write the media
			externalStorageWriteable = true;
			Log.i(DEBUG_TAG, "external storage available and writeable");
		} else if (Environment.MEDIA_MOUNTED_READ_ONLY.equals(state))
		{
			// We can only read the media
			externalStorageWriteable = false;
			Log.i(DEBUG_TAG, "external storage available but not writeable");
		} else if (Environment.MEDIA_CHECKING.equals(state))
		{
			externalStorageWriteable = false;
			Log.i(DEBUG_TAG, "external storage unavailable");

			synchronized (this)
			{
				try
				{
					Thread.currentThread().sleep(1000);
					externalStorageWriteable = checkExternalStorageAvailability();
				} catch (InterruptedException e)
				{
					externalStorageWriteable = false;
				}
			}

		} else
		{
			externalStorageWriteable = false;
			Log.i(DEBUG_TAG, "external storage unavailable");
		}

		return externalStorageWriteable;
	}

	/**
	 *
	 */
	private void updatePaths()
	{

		if (basePath == "")
		{

			PLATFORM_FOLDER = ServandoStartConfig.getInstance().get(ServandoStartConfig.DIRECTORY);
			PLATFORM_PATH = ServandoStartConfig.getInstance().get(ServandoStartConfig.EXTERNAL_PATH) + "/" + PLATFORM_FOLDER;

			// find SD cards
			File platformDataPath = null;

			// if external storage is available
			if (checkExternalStorageAvailability())
			{
				String externalStorageRoot = Environment.getExternalStorageDirectory().getAbsolutePath();
				platformDataPath = new File(externalStorageRoot.concat(PLATFORM_PATH));
				// else select internal storage
			} else
			{
				String dataPath = Environment.getDataDirectory().getAbsolutePath();
				platformDataPath = new File(dataPath + "/" + PLATFORM_FOLDER);
			}

			// Ensure that path exist
			if (platformDataPath != null && !platformDataPath.exists())
			{
				platformDataPath.mkdirs();
			}

			basePath = platformDataPath.getAbsolutePath();

			// create logs path if not exist
			File logsPath = new File(basePath + "/logs");
			if (!logsPath.exists())
			{
				logsPath.mkdirs();
			}

			// create services path if not exist
			File servicesPath = new File(basePath + "/services");
			if (!servicesPath.exists())
			{
				servicesPath.mkdirs();
			}

			settingsPath = basePath + "/" + ServandoStartConfig.getInstance().get(ServandoStartConfig.SETTINGS);
			platformLogsPath = logsPath.getAbsolutePath();
			platformServicesPath = servicesPath.getAbsolutePath();
		}
	}

	/**
	 * Loads the platform settings from config file
	 * 
	 * @throws IOException
	 */
	public ServandoSettings getSettings() throws IOException
	{

		if (settings == null)
		{

			File settingsFile = new File(getSettingsPath());
			if (!settingsFile.exists())
			{
				throw new FileNotFoundException("Settings file not found");
				// copyDefaultSettings(false);
			}

			Log.i(DEBUG_TAG, "Reading settings from " + settingsPath);

			try
			{
				FileInputStream fis;
				fis = new FileInputStream(getSettingsPath());
				Serializer s = new Persister();
				Reader reader = new InputStreamReader(fis);
				settings = s.read(ServandoSettings.class, reader, false);

				if (settings.getAvailableServices() == null)
				{
					settings.setAvailableServices(new ArrayList<ServiceReflectionInfo>());
				}

			} catch (FileNotFoundException e)
			{
				Log.e(DEBUG_TAG, "Settings file not found at " + basePath + ". ", e);
				settings = null;
			} catch (Exception e)
			{
				Log.e(DEBUG_TAG, "Error reading settings " + basePath + ". ", e);
				settings = null;
			}
			// si houbo erros devolvemos a configuración por defecto
			// return getDefault ? ServandoSettings.getDefaultSettings(true) : this.settings;
		}

		return settings;
	}

	/**
	 * Copy the default settings to settings.xml
	 * 
	 * @param overrideIfAlreadyExist
	 * @throws IOException
	 */
	private void copyDefaultSettings(boolean overrideIfAlreadyExist) throws IOException
	{

		File settingsFile = new File(settingsPath);

		if (settingsFile.exists())
		{
			throw new IOException("Setting file already exist");
		}

		InputStream in = StorageModule.class.getResourceAsStream("default_settings.xml");
		OutputStream out = new FileOutputStream(settingsFile);
		copyFile(in, out);
	}

	/**
	 * Copy a file
	 * 
	 * @param in InputStream to the file to copy
	 * @param out OutputStream to the resulting file
	 * @throws IOException
	 */
	public void copyFile(InputStream in, OutputStream out) throws IOException
	{
		byte[] buffer = new byte[1024];
		int read;
		while ((read = in.read(buffer)) != -1)
		{
			out.write(buffer, 0, read);
		}
	}

	/**
	 * Gets the platform settings file path
	 * 
	 * @return the settingsPath
	 */
	public String getSettingsPath()
	{
		return settingsPath;
	}

	/**
	 * Gets the platform base path
	 * 
	 * @return platform base path
	 */
	public String getBasePath()
	{
		return basePath;
	}

	/**
	 * Gets the platform logs folder path
	 * 
	 * @return
	 */
	public String getPlatformLogsPath()
	{
		return platformLogsPath;
	}

	/**
	 * Gets the platform services folder path
	 * 
	 * @return
	 */
	public String getPlatformServicesPath()
	{
		return platformServicesPath;
	}

	public void saveSettings(ServandoSettings s) throws Exception
	{

		File settingsFile = new File(getSettingsPath());
		SimpleXMLSerializator serializator = new SimpleXMLSerializator();
		serializator.serialize(s, settingsFile);

	}

}
