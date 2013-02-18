/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package es.usc.citius.servando.android.models.protocol;

import java.util.ArrayList;

import org.simpleframework.xml.Default;
import org.simpleframework.xml.DefaultType;
import org.simpleframework.xml.ElementList;
import org.simpleframework.xml.Root;

/**
 * Esta es la clase raíz para acceder al árbol de categorías de actuaciones médicas proporcionadas por los servicios de
 * la plataforma.
 * 
 * @author tomas
 * @author Ángel Piñeiro
 */
@Deprecated
@Root(name = "MedicalActionCategories")
@Default(DefaultType.FIELD)
public class ActionCategories {

	/**
	 * Lista de categorías raíz de actuaciones médicas disponibles en la plataforma.
	 */
	@ElementList(name = "category", type = MedicalActionCategory.class)
	private ArrayList<MedicalActionCategory> categories;

	public ActionCategories()
	{
		categories = new ArrayList<MedicalActionCategory>();
	}

	/**
	 * @return the categories
	 */
	public ArrayList<MedicalActionCategory> getCategories()
	{
		return categories;
	}

	/**
	 * Indica si el árbol de categorías de actuaciones médicas contiene una categoría cuyo nombre coincide (ignorando
	 * mayúsculas y minúsculas) con el nombre de la categoría parámetro.
	 * 
	 * @param category
	 * @return
	 */
	public boolean contains(MedicalActionCategory category)
	{
		for (MedicalActionCategory cat : getCategories())
		{
			if (cat.containsCategoryWithName(category.getName()))
			{
				return true;
			}
		}
		return false;
	}

	/**
	 * Obtiene la categoría de actuaciones médicas cuyo nombre coincida con el parámetro. Null si no se encuentra.
	 * 
	 * @param name
	 * @return
	 */
	public MedicalActionCategory getCategoryWithName(String name)
	{
		for (MedicalActionCategory cat : getCategories())
		{
			MedicalActionCategory c = cat.getCategoryWithName(name);
			if (c != null)
			{
				return c;
			}
		}
		return null;
	}

	/**
	 * Añade al árbol de categorías la nueva categoría, siempre que en el mismo no exista ninguna otra categoría con el
	 * mismo nombre (ignorando mayúsculas y minúsculas). Si se desea añadir un nuevo subárbol de categorías, se deberá
	 * hacer con el nodo raíz de dicho subárbol.
	 * 
	 * @param category
	 * @throws Exception si ya existe una categoría con ese nombre o si se intenta agregar un nuevo subárbol de
	 *             categorías desde un nodo que no es la raíz
	 */
	public synchronized void insertCategory(MedicalActionCategory category) throws Exception
	{
		for (MedicalActionCategory cat : getCategories())
		{
			if (cat.containsCategoryWithName(category.getName()))
			{
				throw new Exception("Ya existe una categoría con ese nombre");
			}
		}
		// Ya lo tenemos comprobado, la insertamos en los elementos hijo de su
		// categoría padre y guardamos las
		// categorías
		MedicalActionCategory parent = category.getParentCategory();
		// Comprobamos que el padre es nulo o ya existe en el árbol de
		// categorías.
		if (parent != null && this.getCategoryWithName(parent.getName()) == null)
		{
			throw new Exception("Si se desea añadir un nuevo subárbol de categorías, se debe añadir el nodo raíz");
		}
		// Ahora, si no tiene padre, agregamos la categoría como categoría raíz
		if (parent == null)
		{
			this.getCategories().add(category);
		} // Si tiene un padre, tomamos el verdadero padre del árbol (el que
			// estea en el árbol principal y el nombre
			// coincida
			// con el del padre del argumento.
		else
		{
			parent = this.getCategoryWithName(parent.getName());
			parent.getSubcategories().add(category);
			category.setParentCategory(parent);
		}
	}
}
