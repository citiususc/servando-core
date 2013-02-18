package es.usc.citius.servando.android.communications;

import java.util.HashMap;

/**
 * Implemets the platform communications module.
 * 
 * @author Ángel Piñeiro
 * 
 */
public class ServandoCommunicationsModule {

	private final HashMap<String, CommunicableService> registeredServices;
	private final HashMap<CommunicableService, ObjectTransporter> objectTransporters;

	/**
	 * Singleton unique instance
	 */
	private static final ServandoCommunicationsModule instance = new ServandoCommunicationsModule();

	/**
	 * Static member to obtain the unique instance
	 */
	public static ServandoCommunicationsModule getInstance()
	{
		return instance;
	}

	/**
	 * Private constructor to avoid multiple instances
	 */
	private ServandoCommunicationsModule()
	{
		registeredServices = new HashMap<String, CommunicableService>();
		objectTransporters = new HashMap<CommunicableService, ObjectTransporter>();
	}

	/**
	 * Registers a service in the communications module, allowing it to obtain an {@link ObjectTransporter} ready for
	 * use
	 * 
	 * @param service The service to register
	 */
	public void registerService(CommunicableService service)
	{
		if (registeredServices.containsKey(service.getId()))
		{
			throw new IllegalArgumentException("Service with ID " + service.getId() + " already registered");
		}
		registeredServices.put(service.getId(), service);
		objectTransporters.put(service, new ObjectTransporter(service.getServiceRemoteUri(), service.getId()));
	}

	/**
	 * Unregisters a service from the communications module use
	 * 
	 * @param service The service to unregister
	 */
	public void unregisterService(CommunicableService service)
	{
		registeredServices.remove(service.getId());
		objectTransporters.remove(service);
	}

	/**
	 * Gets a register service by its id
	 * 
	 * @param id The id of the service to get
	 * @return The service with the specified id, or null
	 */
	public CommunicableService getRegisteredService(String id)
	{
		return registeredServices.get(id);
	}

	/**
	 * Gets the {@link ObjectTransporter} of an concrete service
	 * 
	 * @param service The service whose {@link ObjectTransporter} we want to get
	 * @return
	 */
	public ObjectTransporter getServiceObjectTransporter(CommunicableService service)
	{
		return objectTransporters.get(service);
	}

	/**
	 * Gets the {@link ObjectTransporter} of an concrete service by its id
	 * 
	 * @param id The id of the service whose {@link ObjectTransporter} we want to get
	 * @return
	 */
	public ObjectTransporter getServiceObjectTransporter(String id)
	{
		return objectTransporters.get(registeredServices.get(id));
	}

}
