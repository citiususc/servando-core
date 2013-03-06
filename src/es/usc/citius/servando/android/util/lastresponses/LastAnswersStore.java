package es.usc.citius.servando.android.util.lastresponses;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.simpleframework.xml.ElementMap;
import org.simpleframework.xml.Root;

import es.usc.citius.servando.android.logging.ILog;
import es.usc.citius.servando.android.logging.ServandoLoggerFactory;
import es.usc.citius.servando.android.xml.helpers.SimpleXMLSerializator;

@Root(name = "LastAnswers")
public class LastAnswersStore {

	ILog logger = ServandoLoggerFactory.getLogger(LastAnswersStore.class);

	// This file is located in the logs directory
	public static final String DEFAULT_FILE = "alarmsresults.log";

	@ElementMap(name = "responses", keyType = String.class, valueType = ResponsesWrapper.class, inline = true)
	private HashMap<String, ResponsesWrapper> responses;

	public LastAnswersStore()
	{
		responses = new HashMap<String, ResponsesWrapper>();
	}

	public HashMap<String, ResponsesWrapper> getResponses()
	{
		return responses;
	}

	public void setResponses(HashMap<String, ResponsesWrapper> responses)
	{
		this.responses = responses;
	}

	public void addResponse(String itemId, Response response)
	{
		ResponsesWrapper last = this.responses.get(itemId);
		// Check for nulls
		if (last == null)
		{
			last = new ResponsesWrapper();
			this.responses.put(itemId, last);
		}

		if (response != null && last != null && last.getResponses() != null)
		{
			// If the list is not full, insert the response
			if (last.getResponses().size() < ResponsesWrapper.MAX_RESPONSES)
			{
				last.getResponses().add(response);
			} else
			// if list is full, delete the first response (the oldest) and insert at the end
			{
				last.getResponses().remove(0);
				last.getResponses().add(response);
			}
		}
	}

	/**
	 * This method return the last response for this item
	 * 
	 * @param itemId
	 * @return
	 */
	public Response getLastResponse(String itemId)
	{

		if (this.responses != null)
		{
			ResponsesWrapper wrapper = this.responses.get(itemId);
			if (wrapper != null)
			{
				List<Response> list = wrapper.getResponses();
				if (list != null && list.size() > 0)
				{
					return list.get(list.size() - 1);
				}
			}
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
	public List<Response> getLastResponses(String itemId, int lasts)
	{
		List<Response> toReturn = new ArrayList<Response>();

		ResponsesWrapper lastResponses = this.responses.get(itemId);
		if (lastResponses != null)
		{
			if (lastResponses.getResponses().size() <= lasts)
			{
				toReturn = new ArrayList<Response>(lastResponses.getResponses());
			} else
			// If have enough response return only the requested
			{
				int size = lastResponses.getResponses().size();
				int start = size - lasts;
				toReturn = new ArrayList<Response>(lastResponses.getResponses().subList(start, size));
			}

		}
		return toReturn;
	}

	public boolean readFromFile(File file)
	{
		SimpleXMLSerializator serializator = new SimpleXMLSerializator();
		LastAnswersStore readed = null;
		try
		{
			readed = (LastAnswersStore) serializator.deserialize(file, LastAnswersStore.class);
			this.setResponses(readed.getResponses());
		} catch (Exception e)
		{
			e.printStackTrace();
			logger.debug("It was not possible read LastAnswers from file. Exception: " + e.getMessage());

		}
		if (readed != null)
		{
			this.responses = new HashMap<String, ResponsesWrapper>(readed.getResponses());
			return true;
		}
		return false;
	}

	public boolean writeToFile(File file)
	{
		SimpleXMLSerializator serializator = new SimpleXMLSerializator();

		try
		{
			serializator.serialize(this, file);
		} catch (Exception e)
		{
			e.printStackTrace();
			logger.error("It was not posibble write LastAnswers to file. Exception: " + e.getMessage());
			return false;
		}

		return true;

	}
}
