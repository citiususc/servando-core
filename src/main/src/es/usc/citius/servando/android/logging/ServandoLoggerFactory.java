package es.usc.citius.servando.android.logging;

import java.util.HashMap;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;

import es.usc.citius.servando.android.models.services.IPlatformService;

/**
 * This class allows any component to obtain a {@link Logger} configured for writting to the platform logs file. Default
 * logging implementation is log4j.
 * 
 * Make sure {@link Log4JConfig#initialize(String, Level, boolean)} is called before
 * 
 * @author Ángel Piñeiro
 */
public class ServandoLoggerFactory {

	private static boolean loggingEnabled = true;

	private static ILog nullLogger;

	/**
	 * Map of loggers, one for each service
	 */
	private static HashMap<String, ILog> loggers = new HashMap<String, ILog>();

	/**
	 * Retrieve a logger for an especific service. The idea is that each service can get its logger at initialization
	 * 
	 * @param service The service who requests a logger
	 * @return An instance of {@link Logger}
	 */
	public static ILog getServiceLogger(IPlatformService service)
	{
		return getLogger(service.getClass());
	}

	/**
	 * Retrieve a logger for an especific class.
	 * 
	 * @return An instance of {@link Logger}
	 */
	public static ILog getLogger(Class<?> clazz)
	{
		String className = clazz.getName();
		if (!loggers.containsKey(className))
		{
			loggers.put(className, createLog4jLogger(clazz));
		}
		return loggers.get(className);
	}

	/**
	 * 
	 * @param clazz
	 * @return
	 */
	private static ILog createLog4jLogger(Class<?> clazz)
	{

		// if(loggingEnabled) {
		return new ServandoLog(Logger.getLogger(clazz));
		// }else {
		//
		//
		// }
	}

}
