package es.usc.citius.servando.android.models.util;

import org.simpleframework.xml.Root;

@Root(name = "Ping")
public class Ping {

	public String getPing()
	{
		return ping;
	}

	public void setPing(String ping)
	{
		this.ping = ping;
	}

	public String ping = "ping";
}
