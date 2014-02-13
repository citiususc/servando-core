/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package es.usc.citius.servando.android.communications.model;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.Root;

/**
 * Tipo de dato que permite el envío de un simple string
 * 
 * @author tomas
 */
@Root(name = "stringWrapper")
public class StringWrapper {

	@Element(name = "value")
	private String value;

	public StringWrapper()
	{
		this("");
	}

	public StringWrapper(String value)
	{
		this.value = value;
	}

	/**
	 * @return the value
	 */
	public String getValue()
	{
		return value;
	}

	/**
	 * @param value the value to set
	 */
	public void setValue(String value)
	{
		this.value = value;
	}
}
