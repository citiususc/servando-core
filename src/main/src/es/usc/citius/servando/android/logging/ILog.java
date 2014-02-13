package es.usc.citius.servando.android.logging;

public interface ILog {

	public void info(Object message);

	public void info(Object message, Throwable e);

	public void info(Object message, Object... params);

	public void debug(Object message);

	public void debug(Object message, Object... params);

	public void debug(Object message, Throwable e);

	public void warn(Object message);

	public void warn(Object message, Object... params);

	public void warn(Object message, Throwable e);

	public void error(Object message);

	public void error(Object message, Object... params);

	public void error(Object message, Throwable e);

	public void fatal(Object message);

	public void fatal(Object message, Object... params);

	public void fatal(Object message, Throwable e);

}
