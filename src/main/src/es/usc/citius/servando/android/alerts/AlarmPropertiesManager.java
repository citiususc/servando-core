package es.usc.citius.servando.android.alerts;

import es.usc.citius.servando.android.logging.ILog;
import es.usc.citius.servando.android.logging.ServandoLoggerFactory;
import es.usc.citius.servando.android.settings.StorageModule;
import es.usc.citius.servando.android.xml.helpers.SimpleXMLSerializator;

public class AlarmPropertiesManager {

	ILog logger = ServandoLoggerFactory.getLogger(AlarmPropertiesManager.class);

	private static AlarmPropertiesManager instance;

	private LocalUserAlarmProperties properties;

	private final String FILE_NAME = "alarmconfiguration.xml";
	private String fileFullPath;

	private AlarmPropertiesManager()
	{
		fileFullPath = StorageModule.getInstance().getBasePath();
		fileFullPath = fileFullPath + "/" + FILE_NAME;
		readPropertiesFromFile();

	}

	public static AlarmPropertiesManager getInstance()
	{
		if (instance == null)
		{
			instance = new AlarmPropertiesManager();
		}
		return instance;
	}

	public LocalUserAlarmProperties getProperties()
	{
		return properties;
	}

	public void setProperties(LocalUserAlarmProperties properties)
	{
		this.properties = properties;
	}

	private void readPropertiesFromFile()
	{
		SimpleXMLSerializator serializator = new SimpleXMLSerializator();
		try
		{
			properties = (LocalUserAlarmProperties) serializator.deserialize(fileFullPath, LocalUserAlarmProperties.class);
		} catch (Exception e)
		{
			logger.error("No fue posible cargar el fichero de propiedades de las alertas.Causa excepción: " + e.getMessage()
					+ " Comprube que existe y tiene un formato válido en " + fileFullPath);
			e.printStackTrace();
		}
	}

}
