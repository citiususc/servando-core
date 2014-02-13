package es.usc.citius.servando.android.communications.callbacks;

import es.usc.citius.servando.android.communications.ISendTicket;

public interface TransmissionFinishedCallback {

	/**
	 * Este método será invocado cuando la transmisión se realice de forma correcta.
	 * 
	 * @param ticket
	 */
	public void onSuccess(ISendTicket ticket);

	/**
	 * Este método será invocado cuando la transmisión finalize de forma incorrecta porque se ha agotado el timeoout
	 * 
	 * @param ticket
	 */
	public void onTimeout(ISendTicket ticket);

	/**
	 * Este método será invocado cuando se produzca algún otro error durante la transmisión
	 * 
	 * @param ticket
	 */
	public void onError(ISendTicket ticket);

	/**
	 * Implementaión nula del manejador de finalización de transmissión. ütil si los invocadores no desean manejar las
	 * respuestas.
	 */
	public static class NullTransmissionFinishedCallback implements TransmissionFinishedCallback {

		/**
		 * Singleton unique instance
		 */
		private static final NullTransmissionFinishedCallback instance = new NullTransmissionFinishedCallback();

		/**
		 * Private constructor to avoid multiple instances
		 */
		private NullTransmissionFinishedCallback()
		{
		}

		/**
		 * Static member to obtain the unique instance
		 */
		public static NullTransmissionFinishedCallback getInstance()
		{
			return instance;
		}

		@Override
		public void onSuccess(ISendTicket ticket)
		{
		}

		@Override
		public void onError(ISendTicket ticket)
		{
		}

		@Override
		public void onTimeout(ISendTicket ticket)
		{
		}

	}

}
