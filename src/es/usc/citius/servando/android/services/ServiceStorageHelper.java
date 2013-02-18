package es.usc.citius.servando.android.services;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.HashMap;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;

/**
 * This class is a helper that allows services to create logs and data files under its own folder on the device SDCard.
 * 
 * @author Ángel Piñeiro
 * 
 */
public class ServiceStorageHelper {

	/**
	 * Constant for access service folder vía {@link #getWorkingDirectory(String)}
	 */
	public static final String SERVICE = "service";
	/**
	 * Constant for access service log folder vía {@link #getWorkingDirectory(String)}
	 */
	public static final String LOGS = "logs";
	/**
	 * Constant for access service data folder vía {@link #getWorkingDirectory(String)}
	 */
	public static final String DATA = "data";
	/**
	 * Constant for access service data folder vía {@link #getWorkingDirectory(String)}
	 */
	public static final String ASSETS = "assets";
	/**
	 * Stores a set pairs [key, path] that can be accesed using {@link #getWorkingDirectory(String)}
	 */
	private final HashMap<String, String> servicePaths = new HashMap<String, String>();

	/**
	 * Service folder path on the device SDCard
	 */
	private String servicePath;
	/**
	 * Service logs path on the device SDCard
	 */
	private String serviceLogsPath;
	/**
	 * Service assets path on the device SDCard
	 */
	private String serviceAssetsPath;

	/**
	 * Service data path on the device SDCard
	 */
	private String serviceFilesPath;

	public ServiceStorageHelper()
	{
	}

	/**
	 * Creates a directory under the service folder, and returns its full path
	 * 
	 * @param name The directory name
	 * @return The full path to the directory
	 */
	public String createWorkingDirectory(String name)
	{
		File f = new File(serviceFilesPath + "/" + name);
		if (!f.exists())
		{
			f.mkdirs();
		}
		servicePaths.put(name, f.getAbsolutePath());

		return servicePaths.get(name);
	}

	/**
	 * Gets the full path of a directory under the service folder by its name
	 * 
	 * @param name the directory name
	 * @return The directory full path
	 */
	public String getWorkingDirectory(String name)
	{
		return servicePaths.get(name);
	}

	/**
	 * Adds an already created directory to the service folders list, so that it can be accessed vía
	 * {@link #getWorkingDirectory(String)}
	 * 
	 * @param name the directory name
	 * @param path the path where it is located
	 * @return
	 */
	public String addWorkingDirectory(String name, String path)
	{
		return servicePaths.put(name, path);
	}

	/**
	 * Gets a {@link FileOutputStream} to the specified file, in the specified folder.
	 * 
	 * @param fileName The name of the file to open
	 * @param where Where to open the file. Posible values are {@link ServiceStorageHelper#SERVICE},
	 *            {@link ServiceStorageHelper#LOGS}, {@link ServiceStorageHelper#DATA} or other existing folder name. If
	 *            this parameter is null, the file its created in the {@link ServiceStorageHelper#SERVICE} directory.
	 * 
	 * @return A {@link FileOutputStream} to the specified file
	 * @throws IOException
	 */
	public FileOutputStream getFileOutputStream(String fileName, String where) throws IOException
	{

		String path = getWorkingDirectory(where);
		path = path != null ? path : serviceFilesPath;

		File f = new File(path + "/" + fileName);
		try
		{
			return new FileOutputStream(f);
		} catch (FileNotFoundException e)
		{
			Log.e("ServiceConfig", "Error creating output stream", e);
			throw new IOException("Error creating output stream '" + path + "/" + fileName + "'");
		}
	}

	/**
	 * Gets a {@link FileInputStream} to the specified file, in the specified folder.
	 * 
	 * @param fileName The name of the file to open
	 * @param where Where to open the file. Posible values are {@link ServiceStorageHelper#SERVICE},
	 *            {@link ServiceStorageHelper#LOGS}, {@link ServiceStorageHelper#DATA} or other existing folder name. If
	 *            this parameter is null, the file its created in the {@link ServiceStorageHelper#SERVICE} directory.
	 * 
	 * @return A {@link FileOutputStream} to the specified file
	 * @throws IOException
	 */
	public FileInputStream getFileInputStream(String fileName, String where) throws IOException
	{

		String path = getWorkingDirectory(where);
		path = path != null ? path : serviceFilesPath;

		try
		{
			File f = new File(path + "/" + fileName);
			if (f.exists())
			{
				FileInputStream in = new FileInputStream(path + "/" + fileName);
				return in;
			}
		} catch (FileNotFoundException e)
		{
			Log.e("ServiceConfig", "Error creating output stream", e);
			throw new FileNotFoundException("Error creating input stream '" + path + "/" + fileName + "'");
		}

		throw new FileNotFoundException("Error creating input stream '" + path + "/" + fileName + "'");
	}

	/**
	 * Creates a file in the service logs directory {@link ServiceStorageHelper#LOGS} with the specified name and the
	 * extension '.log'.
	 * 
	 * @param fileName the filename to set
	 * @return The created file
	 * @throws IOException
	 */
	public File createLogFile(String fileName) throws IOException
	{
		File f = new File(serviceLogsPath + "/" + fileName + ".log");
		try
		{
			f.createNewFile();
			return f;
		} catch (IOException e)
		{
			Log.e("ServiceConfig", "Error creating log file", e);
			throw new IOException("Error creating log file '" + serviceLogsPath + "/" + fileName + ".log'");
		}
	}

	/**
	 * Creates a file in the service data directory {@link ServiceStorageHelper#DATA} with the specified name.
	 * 
	 * @param fileName the filename to set
	 * @return The created file
	 * @throws IOException
	 */
	public File createDataFile(String fileName) throws IOException
	{
		File f = new File(serviceFilesPath + "/" + fileName);
		try
		{
			f.createNewFile();
			return f;
		} catch (IOException e)
		{
			Log.e("ServiceConfig", "Error creating data file", e);
			throw new IOException("Error creating data file '" + serviceFilesPath + "/" + fileName + "'");
		}
	}

	/**
	 * Opens a file in the service data directory {@link ServiceStorageHelper#DATA} with the specified name.
	 * 
	 * @param fileName the filename to set
	 * @return The created file
	 * @throws IOException
	 */
	public File openDataFile(String fileName) throws IOException
	{

		File f = new File(serviceFilesPath + "/" + fileName);

		if (!f.exists())
		{
			Log.e("ServiceConfig", "Error opening data file. File doesnt exist");
			throw new IOException("Error opening data file. File doesnt exist: '" + serviceFilesPath + "/" + fileName + "'");
		}

		return f;

	}

	/**
	 * Returns a bitmap object from an image file in the service assets directory {@link ServiceStorageHelper#ASSETS}
	 * with the specified name.
	 * 
	 * @param fileName the image filename
	 * @return An Bitmap object
	 * @throws IOException
	 */
	public Bitmap getBitmapAsset(String imgFileName)
	{
		String imageFullPath = serviceAssetsPath + "/" + imgFileName;
		return BitmapFactory.decodeFile(imageFullPath);
	}

	/**
	 * Gets the service folder path on the device SDCard
	 * 
	 * @return the service path
	 */
	public String getServicePath()
	{
		return servicePath;
	}

	/**
	 * Sets the service folder path on the device SDCard
	 * 
	 * @param servicePath
	 */
	public void setServicePath(String servicePath)
	{
		this.servicePath = servicePath;
	}

	/**
	 * Gets the service logs folder path on the device SDCard
	 * 
	 * @return the service logs path
	 */
	public String getServiceLogsPath()
	{
		return serviceLogsPath;
	}

	/**
	 * Sets the service logs folder path on the device SDCard
	 * 
	 * @param servicePath
	 */
	public void setServiceLogsPath(String serviceLogPath)
	{
		serviceLogsPath = serviceLogPath;
	}

	/**
	 * Sets the service data path on the device SDCard
	 * 
	 * @return the service data path
	 */
	public String getServiceFilesPath()
	{
		return serviceFilesPath;
	}

	/**
	 * Gets the service data path on the device SDCard
	 * 
	 * @param servicePath
	 */
	public void setServiceFilesPath(String serviceFilesPath)
	{
		this.serviceFilesPath = serviceFilesPath;
	}

	/**
	 * @return the serviceAssetsPath
	 */
	public String getServiceAssetsPath()
	{
		return serviceAssetsPath;
	}

	/**
	 * @param serviceAssetsPath the serviceAssetsPath to set
	 */
	public void setServiceAssetsPath(String serviceAssetsPath)
	{
		this.serviceAssetsPath = serviceAssetsPath;
	}

}
