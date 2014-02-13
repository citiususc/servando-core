package es.usc.citius.servando.android.communications.model;

import java.util.ArrayList;
import java.util.List;

public class EventList {

	private List<Object> events;

	public EventList()
	{
		setEvents(new ArrayList<Object>());
	}

	/**
	 * @return the events
	 */
	public List<Object> getEvents()
	{
		return events;
	}

	/**
	 * @param events the events to set
	 */
	public void setEvents(List<Object> events)
	{
		this.events = events;
	}

}
