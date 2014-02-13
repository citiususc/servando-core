/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package es.usc.citius.servando.android.models.util;

import java.util.Map;

import org.simpleframework.xml.Default;
import org.simpleframework.xml.DefaultType;
import org.simpleframework.xml.Element;

/**
 * Esta clase representa un parámetro en formato de cadena de caracteres. Este parámetro simplemente estará compuesto
 * por un par nombre/valor. Es utilizado, por ejemplo, para la configuración de ejecuciones de actuaciones médicas.
 * 
 * @author Tomás Teijeiro Campo
 */
@Default(DefaultType.FIELD)
public class Parameter implements Map.Entry<String, String> {

	/**
	 * Nombre del parámetro
	 */
	@Element(name = "name")
	private String name;
	/**
	 * Valor del parámetro
	 */
	@Element(name = "value")
	private String value;
	/**
	 * Propiedad que indica si el valor del parámetro puede ser mostrado al usuario, o si por el contrario se trata de
	 * un parámetro interno.
	 */
	@Element(name = "visible")
	private boolean visible;

	/**
	 * Nombre del parámetro
	 * 
	 * @return the name
	 */
	public String getName()
	{
		return name;
	}

	/**
	 * Nombre del parámetro
	 * 
	 * @param name the name to set
	 */
	public void setName(String name)
	{
		this.name = name;
	}

	/**
	 * Valor del parámetro
	 * 
	 * @return the value
	 */
	public String getValue()
	{
		return value;
	}

	/**
	 * Valor del parámetro
	 * 
	 * @param value the value to set
	 */
	public String setValue(String value)
	{
		String oldValue = this.value;
		this.value = value;
		return oldValue;
	}

	/**
	 * Propiedad que indica si el valor del parámetro puede ser mostrado al usuario, o si por el contrario se trata de
	 * un parámetro interno.
	 * 
	 * @return the visible
	 */
	public boolean isVisible()
	{
		return visible;
	}

	/**
	 * Propiedad que indica si el valor del parámetro puede ser mostrado al usuario, o si por el contrario se trata de
	 * un parámetro interno.
	 * 
	 * @param visible the visible to set
	 */
	public void setVisible(boolean visible)
	{
		this.visible = visible;
	}

	public String getKey()
	{
		throw new UnsupportedOperationException("Not supported yet.");
	}

}
