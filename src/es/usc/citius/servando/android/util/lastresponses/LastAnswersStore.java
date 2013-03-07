package es.usc.citius.servando.android.util.lastresponses;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import es.usc.citius.servando.android.logging.ILog;
import es.usc.citius.servando.android.logging.ServandoLoggerFactory;
import es.usc.citius.servando.android.settings.StorageModule;
import es.usc.citius.servando.android.xml.helpers.SimpleXMLSerializator;

public class LastAnswersStore {

	ILog logger = ServandoLoggerFactory.getLogger(LastAnswersStore.class);

	// This file is located in the logs directory
	private static final String DEFAULT_FILE = "lastanswers.xml";

	private static LastAnswersStore instance = new LastAnswersStore();

	private File file;

	private ResponsesContainer responsesContainer;

	private LastAnswersStore()
	{
		responsesContainer = new ResponsesContainer();
		file = new File(StorageModule.getInstance().getBasePath() + "/" + DEFAULT_FILE);
		readFromFile();
		logger.debug("CALLING CONSTRUCTOR...");
	}

	public static LastAnswersStore getInstance()
	{
		return instance;
	}

	public synchronized void addResponses(String itemId, List<Response> responses)
	{
		if (responsesContainer == null)
		{
			return;
		}
		for (Response r : responses)
		{
			responsesContainer.addResponse(itemId, r);
		}
		writeToFile();
	}

	public synchronized void addResponses(Map<String, Response> responses)
	{
		if (responsesContainer == null)
		{
			return;
		}
		for (String key : responses.keySet())
		{
			responsesContainer.addResponse(key, responses.get(key));
		}
		writeToFile();
	}

	public synchronized void addResponse(String itemId, Response response)
	{
		if (responsesContainer == null)
		{
			return;
		}
		responsesContainer.addResponse(itemId, response);
		writeToFile();
	}

	/**
	 * This method return the last response for this item
	 * 
	 * @param itemId
	 * @return
	 */
	public synchronized Response getLastResponse(String itemId)
	{

		if (responsesContainer != null)
		{
			return responsesContainer.getLastResponse(itemId);
		}
		return null;
	}

	/**
	 * This method return the last 'x' responses for a itemId. If in this moment there are less than this 'x' responses
	 * the method return all responses. If there are not responses returns an empty list.
	 * 
	 * @param itemId
	 * @param lasts Number os responses. Should be more than 1.
	 * @return
	 */
	public synchronized List<Response> getLastResponses(String itemId, int lasts)
	{
		if (responsesContainer != null)
		{
			return responsesContainer.getLastResponses(itemId, lasts);
		}
		return new ArrayList<Response>();
	}

	private boolean readFromFile()
	{
		SimpleXMLSerializator serializator = new SimpleXMLSerializator();

		try
		{
			// logger.debug("FILE: " + (file == null ? "Null" : "NOT NULL"));

			responsesContainer = (ResponsesContainer) serializator.deserialize(file, ResponsesContainer.class);
		} catch (Exception e)
		{
			logger.error("It was not possible read LastAnswers from file. Exception: ", e);

		}
		if (responsesContainer == null)
		{
			this.responsesContainer = new ResponsesContainer();
			return true;
		}
		return false;
	}

	private synchronized boolean writeToFile()
	{
		SimpleXMLSerializator serializator = new SimpleXMLSerializator();

		try
		{
			serializator.serialize(this.responsesContainer, file);
		} catch (Exception e)
		{
			e.printStackTrace();
			logger.error("It was not posibble write LastAnswers to file. Exception: " + e.getMessage());
			return false;
		}

		return true;

	}
}
