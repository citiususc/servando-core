package es.usc.citius.servando.android.communications;

import java.util.UUID;

public class ISendTicket {

	public enum TicketStatus
	{
		Unknown, CorrectTransmision, IncorrectTransmision
	}

	private UUID id;

	public ISendTicket()
	{
		id = UUID.randomUUID();
	}

	public UUID getId()
	{
		return id;
	}

}
