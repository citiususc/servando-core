package es.usc.citius.servando.android.logging;

import org.apache.log4j.Level;

import android.util.Log;
import de.mindpipe.android.logging.log4j.LogConfigurator;

/**
 * Configuración de logs
 */
public class Log4JConfig {

	private static final String DEBUG_TAG = Log4JConfig.class.getSimpleName();
	/**
	 * Patrón de escritura de logs:
	 * 
	 * data - [NIVEL::nome_logger::a.b.ClaseLog] - mensaxe separador_de_liña
	 */
	private static final String logFilePattern = "%d - [%p::%c{1}::%C{3}] - %m%n";
	private static final String logCatPattern = "[%C{1}] %m%n";

	private static LogConfigurator logConfigurator = null;

	public static void initialize(String logFilename, Level logLevel, boolean logToFile, boolean useLogCat)
	{

		if (logConfigurator == null)
		{

			logConfigurator = new LogConfigurator();
			logConfigurator.setFileName(logFilename);
			logConfigurator.setRootLevel(logLevel);
			logConfigurator.setImmediateFlush(true);
			logConfigurator.setUseLogCatAppender(useLogCat);
			logConfigurator.setUseFileAppender(true);
			logConfigurator.setFilePattern(logFilePattern);
			logConfigurator.setLogCatPattern(logCatPattern);
			// Set log level of a specific logger
			logConfigurator.setLevel("org.apache", Level.ERROR);
			logConfigurator.configure();
			Log.d(DEBUG_TAG, "Initializing logs (" + logConfigurator.getFilePattern() + ")");
		}

	}
}
