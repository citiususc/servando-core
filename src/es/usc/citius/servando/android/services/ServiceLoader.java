package es.usc.citius.servando.android.services;

import android.content.Context;
import android.util.Log;
import dalvik.system.DexClassLoader;
import es.usc.citius.servando.android.models.services.IPlatformService;
import es.usc.citius.servando.android.models.services.ServiceReflectionInfo;
import es.usc.citius.servando.android.settings.StorageModule;

/**
 * Allows dynamic loading of platform services.
 * 
 * @author angel
 * 
 */
public class ServiceLoader {

	private static final String DEBUG_TAG = "ServiceLoader";

	public ServiceLoader()
	{
	}

	/**
	 * <p>
	 * Loads a service from an external assembly (apk or jar *(not implemented yet)) using the asociated
	 * {@link ServiceReflectionInfo}.
	 * </p>
	 * 
	 * @param sri The {@link ServiceReflectionInfo} object asociated to the service
	 * @return The {@link IPlatformService} object asociated to the service or null if a problem occurs.
	 */
	public IPlatformService loadService(ServiceReflectionInfo sri, Context ctx) throws ClassNotFoundException, InstantiationException,
			IllegalAccessException
	{
		return loadLocalService(sri, ctx);

	}

	@SuppressWarnings("unused")
	private IPlatformService loadRemoteService(ServiceReflectionInfo sri, Context ctx)
	{
		// TODO Load remote service
		return null;
	}

	private IPlatformService loadLocalService(ServiceReflectionInfo sri, Context ctx) throws ClassNotFoundException, InstantiationException,
			IllegalAccessException
	{
		// if the service is compiled
		if (ServiceReflectionInfo.COMPILED.equals(sri.serviceAssemblyType))
		{
			return loadCompiledService(sri);
		}
		// if the service is inside an APK file
		if (ServiceReflectionInfo.APK.equals(sri.serviceAssemblyType))
		{
			return loadAPKService(sri, ctx);
			// in other case it is in a JAR file
		} else
		{
			return loadJARService(sri, ctx);
		}
	}

	/**
	 * Loads a service compiled in the main application APK
	 * 
	 * @param sri The {@link ServiceReflectionInfo} object asociated to the service
	 * @return The {@link IPlatformService} object asociated to the service or null if a problem occurs.
	 * @throws
	 */
	private IPlatformService loadCompiledService(ServiceReflectionInfo sri) throws ClassNotFoundException, InstantiationException,
			IllegalAccessException
	{
		Log.i(DEBUG_TAG, "Loading compiled service ...");

		IPlatformService service = null;
		Class<?> clazz = null;

		try
		{

			clazz = Class.forName(sri.getCannonicalClassName());
			// try to instantiate the service class
			service = (IPlatformService) clazz.newInstance();
		} catch (ClassNotFoundException e)
		{
			Log.e(DEBUG_TAG, "Class " + sri.getCannonicalClassName() + " not found.", e);
			throw e;
		} catch (IllegalAccessException e)
		{
			Log.e(DEBUG_TAG, "Illegal acces to " + sri.serviceClassName + ".", e);
			throw e;
		} catch (InstantiationException e)
		{
			Log.e(DEBUG_TAG, "Could not instantiate service class for service" + sri.serviceID + ".", e);
			throw e;
		}

		return service;
	}

	/**
	 * Loads a service from an external jar file
	 * 
	 * @param sri The {@link ServiceReflectionInfo} object asociated to the service
	 * @return The {@link IPlatformService} object asociated to the service or null if a problem occurs.
	 * @throws
	 */
	public IPlatformService loadAPKService(ServiceReflectionInfo sri, Context ctx) throws ClassNotFoundException, InstantiationException,
			IllegalAccessException
	{
		Log.i(DEBUG_TAG, "Loading APK service ...");

		IPlatformService service = null;
		String apkPath = StorageModule.getInstance().getBasePath() + "/" + sri.getFullAssemblyName();
		String outputPath = ctx.getFilesDir().getAbsoluteFile().getAbsolutePath();
		// ClassLoader cl = ContextManager.getInstance().getApplicationContext().getClassLoader();
		ClassLoader cl = ctx.getClassLoader();
		// Load the assembly using dexclassloaderz
		DexClassLoader dcl = new DexClassLoader(apkPath, outputPath, null, cl);
		Class<?> clazz = null;
		// try to instantiate the service class
		try
		{
			clazz = dcl.loadClass(sri.getCannonicalClassName());
			service = (IPlatformService) clazz.newInstance();

		} catch (ClassNotFoundException e)
		{
			Log.e(DEBUG_TAG, "Class " + sri.getCannonicalClassName() + " not found in " + apkPath + ".", e);
			throw e;
		} catch (IllegalAccessException e)
		{
			Log.e(DEBUG_TAG, "Illegal acces to " + sri.serviceClassName + ".", e);
			throw e;
		} catch (InstantiationException e)
		{
			Log.e(DEBUG_TAG, "Could not instantiate service class for service" + sri.serviceID + ".", e);
			throw e;
		}
		return service;
	}

	/**
	 * Loads a service from an external apk file
	 * 
	 * @param sri The {@link ServiceReflectionInfo} object asociated to the service
	 * @return The {@link IPlatformService} object asociated to the service or null if a problem occurs.
	 */
	public IPlatformService loadJARService(ServiceReflectionInfo sri, Context ctx) throws ClassNotFoundException, InstantiationException,
			IllegalAccessException
	{

		throw new UnsupportedOperationException("Not yet implemented");
	}

}
