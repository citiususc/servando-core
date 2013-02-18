/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package es.usc.citius.servando.android.models.protocol;

import java.util.ArrayList;

import org.simpleframework.xml.Default;
import org.simpleframework.xml.DefaultType;
import org.simpleframework.xml.Element;
import org.simpleframework.xml.ElementList;
import org.simpleframework.xml.Transient;

/**
 * Clase para organizar las @see MedicalAction, que permite una construcción de categorías y subcategorías. Es una clase
 * equivalente a @see MedicamentCategory
 * 
 * @author tomas
 */
@Deprecated
@Default(DefaultType.FIELD)
public class MedicalActionCategory {

	/**
	 * Nombre de la categoría. También funciona como identificador de la misma, debiendo ser único no sólo en la
	 * categoría a la que pertenece esta (sub)categoría, sino dentro de todo el árbol de categorías y subcategorías.
	 */
	@Element(name = "name")
	private String name;
	/**
	 * Actuaciones médicas que pertenecen a esta categoría.
	 */
	@Transient
	private ArrayList<MedicalAction> actions;
	/**
	 * Categorías de actuaciones médicas que se enmarcan dentro de esta categoría.
	 */
	@ElementList(name = "subcategory", type = MedicalActionCategory.class)
	private ArrayList<MedicalActionCategory> subcategories;

	/**
	 * Icono de la categoría. Si es null, se le asignará uno genérico.
	 */
	// @Transient
	// private ImageIcon icon;
	/**
	 * Categoría padre de esta categoría (sólo si esta categoría es una subcategoría)
	 */
	@Transient
	private MedicalActionCategory parentCategory;

	public MedicalActionCategory()
	{
		actions = new ArrayList<MedicalAction>();
		subcategories = new ArrayList<MedicalActionCategory>();
	}

	/**
	 * Obtiene el nombre de la categoría normalizado, en minúsculas, sin tildes ni espacios en blanco. Útil para, por
	 * ejemplo, crear archivos o directorios con el nombre de la categoría.
	 * 
	 * @return
	 */
	public String getSafeName()
	{
		return name.toLowerCase().replace('á', 'á').replace('é', 'e').replace('í', 'i').replace('ó', 'o').replace('ú', 'u').replace(' ', '_');
	}

	/**
	 * Obtiene la categoría de actuación médica cuyo nombre coincida (ignorando mayúsculas y minúsculas) con el
	 * parámetro recibido. Si no se encuentra, se devuelve null.
	 * 
	 * @param name
	 * @return
	 */
	public MedicalActionCategory getCategoryWithName(String name)
	{
		// Si somos esa categoría, nos devolvemos
		if (this.name.equalsIgnoreCase(name))
		{
			return this;
		}
		// En otro caso, buscamos en las subcategorías de de modo recursivo
		for (MedicalActionCategory category : subcategories)
		{
			MedicalActionCategory cat = category.getCategoryWithName(name);
			if (cat != null)
			{
				return cat;
			}
		}
		// No la hemos encontrado, devolvemos null
		return null;
	}

	/**
	 * Comprueba si alguna subcategoría de esta categoría, o esta categoría, tienen un nombre coincidente (ignorando
	 * mayúsculas y minúsculas) con el parámetro recibido.
	 * 
	 * @param name
	 * @return
	 */
	public boolean containsCategoryWithName(String name)
	{
		return getCategoryWithName(name) != null;
	}

	/**
	 * @return the name
	 */
	public String getName()
	{
		return name;
	}

	/**
	 * @param name the name to set
	 */
	public void setName(String name)
	{
		this.name = name;
	}

	// /**
	// * @return the icon
	// */
	// public ImageIcon getIcon() {
	// return icon;
	// }
	//
	// /**
	// * @param icon the icon to set
	// */
	// public void setIcon(ImageIcon icon) {
	// this.icon = icon;
	// }

	/**
	 * @return the parentCategory
	 */
	public MedicalActionCategory getParentCategory()
	{
		return parentCategory;
	}

	/**
	 * @param parentCategory the parentCategory to set
	 */
	public void setParentCategory(MedicalActionCategory parentCategory)
	{
		this.parentCategory = parentCategory;
		if (!parentCategory.getSubcategories().contains(this))
		{
			getParentCategory().getSubcategories().add(this);
		}
	}

	/**
	 * @return the actions
	 */
	public ArrayList<MedicalAction> getActions()
	{
		return actions;
	}

	/**
	 * @return the subcategories
	 */
	public ArrayList<MedicalActionCategory> getSubcategories()
	{
		return subcategories;
	}
}
