package es.usc.citius.servando.android.logging;

/**
 * Null logger implementation of {@link ILog} that dont log anything
 * 
 * @author Ángel Piñeiro
 * 
 */
public class NullLogger implements ILog {

	@Override
	public void info(Object message)
	{
	}

	@Override
	public void info(Object message, Throwable e)
	{
	}

	@Override
	public void info(Object message, Object... params)
	{
	}

	@Override
	public void debug(Object message)
	{
	}

	@Override
	public void debug(Object message, Object... params)
	{
	}

	@Override
	public void debug(Object message, Throwable e)
	{
	}

	@Override
	public void warn(Object message)
	{
	}

	@Override
	public void warn(Object message, Object... params)
	{
	}

	@Override
	public void warn(Object message, Throwable e)
	{
	}

	@Override
	public void error(Object message)
	{
	}

	@Override
	public void error(Object message, Object... params)
	{
	}

	@Override
	public void error(Object message, Throwable e)
	{
	}

	@Override
	public void fatal(Object message)
	{
	}

	@Override
	public void fatal(Object message, Object... params)
	{
	}

	@Override
	public void fatal(Object message, Throwable e)
	{
	}

}
