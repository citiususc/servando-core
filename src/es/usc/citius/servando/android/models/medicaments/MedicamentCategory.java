/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package es.usc.citius.servando.android.models.medicaments;

import java.util.ArrayList;

import org.simpleframework.xml.Default;
import org.simpleframework.xml.DefaultType;
import org.simpleframework.xml.Element;
import org.simpleframework.xml.Transient;

/**
 * Esta clase representa la categoría de un medicamento. Puede contener una lista de medicamentos o una lista de
 * subcategorías
 * 
 * @author Tomás Teijeiro Campo
 */
@Default(DefaultType.FIELD)
public class MedicamentCategory {

	/**
	 * Nombre de la categoría
	 */
	@Element(name = "name")
	private String name;

	/**
	 * Medicamentos que pertenecen a esta categoría.
	 */
	@Element(name = "medicament")
	private ArrayList<Medicament> medicaments;

	/**
	 * Subcategorías de esta categoría
	 */
	@Element(name = "subcategory")
	private ArrayList<MedicamentCategory> subcategories;

	/**
	 * Categoría padre de esta categoría (sólo si esta categoría es una subcategoría)
	 */
	@Transient
	private MedicamentCategory parentCategory;

	/**
	 * Crea unha nova categoría de medicamentos
	 */
	public MedicamentCategory()
	{
		medicaments = new ArrayList<Medicament>();
		subcategories = new ArrayList<MedicamentCategory>();
	}

	/**
	 * @return Nombre de la categoría
	 */
	public String getName()
	{
		return name;
	}

	/**
	 * Nombre de la categoría
	 * 
	 * @param name the name to set
	 */
	public void setName(String name)
	{
		this.name = name;
	}

	/**
	 * @return the parentCategory
	 */
	public MedicamentCategory getParentCategory()
	{
		return parentCategory;
	}

	/**
	 * @param parent the parentCategory to set
	 */
	public void setParentCategory(MedicamentCategory parent)
	{
		this.parentCategory = parent;
		if (!parentCategory.getSubcategories().contains(this))
		{
			getParentCategory().getSubcategories().add(this);
		}
	}

	/**
	 * @return the medicaments
	 */
	public ArrayList<Medicament> getMedicaments()
	{
		return medicaments;
	}

	/**
	 * @return the subcategories
	 */
	public ArrayList<MedicamentCategory> getSubcategories()
	{
		return subcategories;
	}
}
