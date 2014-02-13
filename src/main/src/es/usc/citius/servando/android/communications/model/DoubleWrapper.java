/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package es.usc.citius.servando.android.communications.model;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.Root;

/**
 * Tipo de dato que permite el envío de un double
 * 
 * @author tomas
 */
@Root(name = "doubleWrapper")
public class DoubleWrapper {

	@Element(name = "value")
	private double value;

	public DoubleWrapper()
	{
		this(0.0);
	}

	public DoubleWrapper(double value)
	{
		this.value = value;
	}

	/**
	 * @return the value
	 */
	public double getValue()
	{
		return value;
	}

	/**
	 * @param value the value to set
	 */
	public void setValue(double value)
	{
		this.value = value;
	}
}
