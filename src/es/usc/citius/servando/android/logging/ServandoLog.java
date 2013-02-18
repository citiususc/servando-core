package es.usc.citius.servando.android.logging;

import org.apache.log4j.Logger;

public class ServandoLog implements ILog {

	private Logger log;

	public ServandoLog(Logger log)
	{
		this.log = log;
	}

	@Override
	public void info(Object message)
	{
		log.info(message);
	}

	@Override
	public void info(Object message, Object... params)
	{
		log.info(String.format((String) message, params));
	}

	@Override
	public void info(Object message, Throwable e)
	{
		log.info(message, e);

	}

	@Override
	public void debug(Object message)
	{
		log.debug(message);

	}

	@Override
	public void debug(Object message, Object... params)
	{
		log.debug(String.format((String) message, params));

	}

	@Override
	public void debug(Object message, Throwable e)
	{
		log.debug(message, e);

	}

	@Override
	public void error(Object message)
	{
		log.error(message);
	}

	@Override
	public void error(Object message, Throwable e)
	{
		log.error(message, e);
	}

	@Override
	public void fatal(Object message)
	{
		log.fatal(message);
	}

	@Override
	public void fatal(Object message, Throwable e)
	{
		log.fatal(message, e);
	}

	@Override
	public void error(Object message, Object... params)
	{
		log.error(String.format((String) message, params));

	}

	@Override
	public void fatal(Object message, Object... params)
	{
		log.fatal(String.format((String) message, params));

	}

	@Override
	public void warn(Object message)
	{
		log.warn(message);

	}

	@Override
	public void warn(Object message, Object... params)
	{
		log.warn(String.format((String) message, params));

	}

	@Override
	public void warn(Object message, Throwable e)
	{
		log.warn(message, e);

	}

}
