/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package es.usc.citius.servando.android.models.medicaments;

import java.util.ArrayList;

import org.simpleframework.xml.Default;
import org.simpleframework.xml.DefaultType;
import org.simpleframework.xml.Element;
import org.simpleframework.xml.Root;

/**
 * 
 * Esta es la clase raíz para acceder a los medicamentos disponibles en la plataforma. Agrupa todas las categorías raíz
 * de medicamentos.
 * 
 * @author tomas
 */
@Root(name = "AvailableMedicaments")
@Default(DefaultType.FIELD)
// @XmlType(name = "AvailableMedicaments")
public class AvailableMedicaments {

	/**
	 * Categorías raíz de los medicamentos agregados en la plataforma
	 */
	@Element(name = "category")
	private ArrayList<MedicamentCategory> categories;

	public AvailableMedicaments()
	{
		categories = new ArrayList<MedicamentCategory>();
	}

	/**
	 * Busca el medicamento con el nombre dado en todas las categorías y subcategorías. Se devuelve el primer
	 * medicamento encontrado cuyo nombre coincida en mayúsculas, minúsculas con el suministrado
	 * 
	 * @param name
	 * @return
	 */
	public Medicament findMedicament(String name)
	{
		for (MedicamentCategory cat : getCategories())
		{
			Medicament searched = findMedicamentInCategory(name, cat);
			if (searched != null)
			{
				return searched;
			}
		}
		return null;
	}

	/**
	 * Busca el medicamento con el nombre dado en todas las categorías y subcategorías. Se devuelve el primer
	 * medicamento encontrado cuyo nombre coincida en mayúsculas, minúsculas con el suministrado
	 * 
	 * @param name
	 * @param category
	 * @return
	 */
	private static Medicament findMedicamentInCategory(String name, MedicamentCategory category)
	{
		for (Medicament m : category.getMedicaments())
		{
			if (m.getName().equalsIgnoreCase(name))
			{
				return m;
			}
		}
		for (MedicamentCategory subCategory : category.getSubcategories())
		{
			Medicament searched = findMedicamentInCategory(name, subCategory);
			if (searched != null)
			{
				return searched;
			}
		}
		return null;
	}

	/**
	 * Categorías raíz de los medicamentos agregados en la plataforma
	 * 
	 * @return the categories
	 */
	public ArrayList<MedicamentCategory> getCategories()
	{
		return categories;
	}

	/**
	 * Categorías raíz de los medicamentos agregados en la plataforma
	 * 
	 * @param categories the categories to set
	 */
	public void setCategories(ArrayList<MedicamentCategory> categories)
	{
		this.categories = categories;
	}
}
