package es.usc.citius.servando.android.util.lastresponses;

import java.util.ArrayList;
import java.util.List;

import org.simpleframework.xml.Default;
import org.simpleframework.xml.DefaultType;
import org.simpleframework.xml.ElementList;

/**
 * Wrapper for {@link Response}
 * 
 * @author pablojose.viqueira
 * 
 */
@Default(DefaultType.FIELD)
public class ResponsesWrapper {

	public static final int MAX_RESPONSES = 10;

	@ElementList(name = "responses", type = Response.class, inline = true)
	private List<Response> responses;

	public ResponsesWrapper()
	{
		responses = new ArrayList<Response>();
	}

	public List<Response> getResponses()
	{
		return responses;
	}

	public void setResponses(List<Response> responses)
	{
		this.responses = responses;
	}
}
