package es.usc.citius.servando.android.models.services;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.content.Context;
import android.util.Log;
import es.usc.citius.servando.android.services.ServiceLoader;
import es.usc.citius.servando.android.settings.ServandoSettings;
import es.usc.citius.servando.android.settings.StorageModule;

/**
 * Singleton that manages the set of platform services (see {@link IPlatformService}). Allows other components to access
 * a particular service by id or the whole of them.
 * 
 * @author angel
 * 
 */
public class ServiceManager {

	private static final String DEBUG_TAG = "ServiceManager";

	/**
	 * Static instance for singleton
	 */
	private static ServiceManager instance = null;

	/**
	 * Private constructor for singleton. Use {@link #getInstance()} to obtain an instance of this class.
	 */
	private ServiceManager()
	{
		registeredServices = new HashMap<String, IPlatformService>();
	}

	/**
	 * Set of services currently registered with the platform, accessible from its identifier.
	 */
	private Map<String, IPlatformService> registeredServices;

	/**
	 * Gets all platform services
	 * 
	 * @return A {@link Map} with the registeredServices
	 */
	public Map<String, IPlatformService> getRegisteredServices()
	{
		return registeredServices;
	}

	/**
	 * Gets a list with all platform services
	 * 
	 * @return A {@link List} with the registeredServices
	 */
	public List<IPlatformService> getRegisteredServicesList()
	{
		List<IPlatformService> svcs = new ArrayList<IPlatformService>();
		svcs.addAll(registeredServices.values());
		return svcs;
	}

	/**
	 * Gets a platform service
	 * 
	 * @return The service (see {@link IPlatformService}) with the specified serviceID, or null if the service can not
	 *         be found.
	 */
	public IPlatformService getRegisteredService(String serviceID)
	{
		return registeredServices.get(serviceID);
	}

	/**
	 * @param registeredServices the registeredServices to set
	 */
	public void setRegisteredServices(Map<String, IPlatformService> registeredServices)
	{
		this.registeredServices = registeredServices;
	}

	/**
	 * Public method to get the {@link ServiceManager} unique instance.
	 */
	public static ServiceManager getInstance()
	{
		if (instance == null)
		{
			instance = new ServiceManager();
		}
		return instance;
	}

	/**
	 * Load the set of services specified in the platform configuration obtained from {@link ServandoSettings}. If a
	 * service can not be loaded, the other services would be properly registered on the platform. TODO Discuss
	 * 
	 * @throws IOException
	 */
	public Collection<IPlatformService> loadServices(Context ctx) throws IOException
	{

		ServandoSettings settings = StorageModule.getInstance().getSettings();
		ServiceLoader loader = new ServiceLoader();

		for (ServiceReflectionInfo sri : settings.getAvailableServices())
		{
			if (!registeredServices.containsKey(sri.serviceID))
			{
				IPlatformService service = null;
				try
				{
					service = loader.loadService(sri, ctx);
					registeredServices.put(sri.serviceID, service);
					Log.i(DEBUG_TAG, "Service " + sri.serviceID + " succesfully registered.");
				} catch (Exception e)
				{
					Log.e(DEBUG_TAG, "An error ocurred while loading " + sri.serviceID + " service.", e);
				}
			} else
			{
				Log.w(DEBUG_TAG, "Attempting to load a loaded service or another service with the same identifier. " + "last service with id '"
						+ sri.serviceID + "' will be ignored.");
			}
		}

		return registeredServices.values();
	}

	public void unregisterService(IPlatformService service)
	{
		if (registeredServices.containsKey(service.getId()))
		{
			registeredServices.remove(service.getId());
		}
	}

}
