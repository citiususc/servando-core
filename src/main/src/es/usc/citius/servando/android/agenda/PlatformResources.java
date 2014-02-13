package es.usc.citius.servando.android.agenda;

public class PlatformResources {

	public static enum Available
	{

		/**
		 * Indica que la actuación médica no necesita acceso exclusivo a ningún recurso. Es el valor por defecto.
		 */
		NONE(0),
		/**
		 * Indica que la actuación médica necesita acceso exclusivo a la pantalla del dispositivo.
		 */
		SCREEN(1 << 0),
		/**
		 * Indica que le actuación médica necesita acceso exclusivo al sistema de bluetooth.
		 */
		BLUETOOTH(1 << 1),
		/**
		 * Indica que le actuación médica necesita acceso exclusivo al sistema de text-to-speech.
		 */
		TTS(1 << 2);

		private int value;

		private Available(int value)
		{
			this.value = value;
		}

		public int getValue()
		{
			return value;
		}

	}

	/**
	 *
	 */
	private int value = Available.NONE.getValue();

	public PlatformResources()
	{
		setValue(Available.NONE);
	}

	/**
	 * @return the value
	 */
	public int getValue()
	{
		return value;
	}

	/**
	 * @param value the value to set
	 */
	public void setValue(Available... flags)
	{

		value = Available.NONE.getValue();

		for (Available r : flags)
		{
			value |= r.getValue();
		}
	}

	/**
	 * @param value the value to set
	 */
	public void add(Available... flags)
	{

		for (Available r : flags)
		{
			value |= r.getValue();
		}
	}

	/**
	 * @param value the value to set
	 */
	public void remove(Available... flags)
	{

		for (Available r : flags)
		{
			value &= ~r.getValue();
		}
	}

	public boolean conflictWith(PlatformResources other)
	{
		return (value & other.value) != 0;
	}

	@Override
	public String toString()
	{
		return Integer.toBinaryString(value);
	}

	public static PlatformResources with(Available... flags)
	{
		PlatformResources r = new PlatformResources();
		r.setValue(flags);
		return r;
	}

}
