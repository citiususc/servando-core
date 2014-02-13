/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package es.usc.citius.servando.android.communications.model;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.Root;

/**
 * Tipo de dato que permite el envío de un booleano
 * 
 * @author tomas
 */
@Root(name = "booleanWrapper")
public class BooleanWrapper {

	@Element(name = "value")
	private boolean value;

	public BooleanWrapper()
	{
		this(false);
	}

	public BooleanWrapper(boolean value)
	{
		this.value = value;
	}

	/**
	 * @return the value
	 */
	public boolean getValue()
	{
		return value;
	}

	/**
	 * @param value the value to set
	 */
	public void setValue(boolean value)
	{
		this.value = value;
	}
}
