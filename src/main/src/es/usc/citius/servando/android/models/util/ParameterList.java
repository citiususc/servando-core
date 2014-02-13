/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package es.usc.citius.servando.android.models.util;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;
import java.util.concurrent.CopyOnWriteArraySet;

import org.simpleframework.xml.Default;
import org.simpleframework.xml.DefaultType;
import org.simpleframework.xml.ElementList;
import org.simpleframework.xml.Root;

/**
 * Clase de utilidad para trabajar comodamente con los parámetros de una ejecución de actuación médica, implementando la
 * interfaz Map.
 * 
 * @author Tomás Teijeiro Campo
 */
@Root(name = "ParameterList")
@Default(DefaultType.FIELD)
public class ParameterList implements Map<String, String> {

	/**
	 * Lista de parámetros encapsulados, para facilitar la interoperabilidad en la serialización XML.
	 */
	@ElementList(name = "parameter", type = Parameter.class, inline = true, required = false)
	private List<Parameter> parameters;

	public ParameterList()
	{
		parameters = new ArrayList<Parameter>();
	}

	/**
	 * Obtiene la lista completa de parámetros. Las modificaciones hechas a esta lista se verán reflejadas en el objeto,
	 * pues devuelve la lista interna de parámetros.
	 * 
	 * @return
	 */
	public List<Parameter> getParameters()
	{
		return parameters;
	}

	@Override
	public int size()
	{
		return parameters.size();
	}

	@Override
	public boolean isEmpty()
	{
		return parameters.isEmpty();
	}

	@Override
	public boolean containsKey(Object key)
	{
		return get(key) != null;
	}

	@Override
	public boolean containsValue(Object value)
	{
		for (Parameter p : parameters)
		{
			if (p.getValue().equals(value))
			{
				return true;
			}
		}
		return false;
	}

	@Override
	public String get(Object key)
	{
		for (Parameter p : parameters)
		{
			if (p.getName().equals(key))
			{
				return p.getValue();
			}
		}
		return null;
	}

	public Parameter getParameter(String key)
	{
		for (Parameter p : parameters)
		{
			if (p.getName().equals(key))
			{
				return p;
			}
		}
		return null;
	}

	@Override
	public String put(String key, String value)
	{
		// Buscamos a ver si ya está ese parámetro.
		for (Parameter p : parameters)
		{
			if (p.getName().equals(key))
			{
				String oldValue = p.getValue();
				p.setValue(value);
				return oldValue;
			}
		}
		// No estaba, lo añadimos
		Parameter p = new Parameter();
		p.setName(key);
		p.setValue(value);
		parameters.add(p);
		return null;
	}

	@Override
	public String remove(Object key)
	{
		for (int i = 0; i < parameters.size(); i++)
		{
			Parameter p = parameters.get(i);
			if (p.getName().equals(key))
			{
				parameters.remove(p);
				return p.getValue();
			}
		}
		return null;
	}

	@Override
	public void putAll(Map<? extends String, ? extends String> m)
	{
		for (Entry<? extends String, ? extends String> e : m.entrySet())
		{
			put(e.getKey(), e.getValue());
		}
	}

	@Override
	public void clear()
	{
		parameters.clear();
	}

	@Override
	public Set<String> keySet()
	{
		ArrayList<String> keys = new ArrayList<String>(parameters.size());
		for (Parameter p : parameters)
		{
			keys.add(p.getName());
		}
		return new CopyOnWriteArraySet<String>(keys);
	}

	@Override
	public Collection<String> values()
	{
		ArrayList<String> values = new ArrayList<String>(parameters.size());
		for (Parameter p : parameters)
		{
			values.add(p.getValue());
		}
		return values;
	}

	@Override
	public Set<Entry<String, String>> entrySet()
	{
		return new TreeSet<Entry<String, String>>(parameters);
	}

	/**
	 * Returns an {@link ArrayList} whith a list strings, where each pair consecutive items represents a parameter.
	 * 
	 * @return
	 */
	public ArrayList<String> asList()
	{
		ArrayList<String> list = new ArrayList<String>();

		for (String k : this.keySet())
		{
			list.add(k);
			list.add(this.get(k));
		}

		return list;

	}

	public static ParameterList fromList(ArrayList<String> params)
	{
		if (params.size() % 2 != 0)
		{
			throw new IllegalArgumentException("Param list size must be even");
		}

		ParameterList list = new ParameterList();
		for (int i = 0; i < params.size(); i += 2)
		{
			list.put(params.get(i), params.get(i + 1));
		}
		return list;

	}

}
