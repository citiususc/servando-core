package es.usc.citius.servando.android.communications;

import es.usc.citius.servando.android.communications.soap.ServandoSOAPEnvelope;

/**
 * 
 * @author Ángel Piñeiro
 * 
 */
public interface SendResult {

	/**
	 * 
	 * @return
	 */
	public boolean getResult();

	/**
	 * 
	 */
	public class SendSuccess implements SendResult {

		private ServandoSOAPEnvelope response;

		public SendSuccess(ServandoSOAPEnvelope response)
		{
			this.setResponse(response);
		}

		@Override
		public boolean getResult()
		{
			return true;
		}

		public ServandoSOAPEnvelope getResponse()
		{
			return response;
		}

		public void setResponse(ServandoSOAPEnvelope response)
		{
			this.response = response;
		}

	}

	/**
	 *
	 */
	public class SendError implements SendResult {

		private String cause;

		public SendError(String cause)
		{
			this.cause = cause;
		}

		@Override
		public boolean getResult()
		{
			return false;
		}

		public String getCause()
		{
			return cause;
		}

		public void setCause(String cause)
		{
			this.cause = cause;
		}

	}

}
