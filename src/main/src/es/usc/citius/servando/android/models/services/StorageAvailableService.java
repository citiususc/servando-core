package es.usc.citius.servando.android.models.services;

import es.usc.citius.servando.android.services.ServiceStorageHelper;

/**
 * Services ath implement this interface will be able to get an {@link ServiceStorageHelper} to access its own working
 * directory on de device SDCard. This directory will be located under the services folder, in the main platform folder.
 * 
 * @author Ángel Piñeiro
 * 
 */
public interface StorageAvailableService extends IPlatformService {
}
