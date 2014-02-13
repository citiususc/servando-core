/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package es.usc.citius.servando.android.communications.model;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.Root;

/**
 * Tipo de dato que permite el envío de un long
 * 
 * @author tomas
 */
@Root(name = "longWrapper")
public class LongWrapper {

	@Element(name = "value")
	private long value;

	public LongWrapper()
	{
		this(0);
	}

	public LongWrapper(long value)
	{
		this.value = value;
	}

	/**
	 * @return the value
	 */
	public long getValue()
	{
		return value;
	}

	/**
	 * @param value the value to set
	 */
	public void setValue(long value)
	{
		this.value = value;
	}
}
