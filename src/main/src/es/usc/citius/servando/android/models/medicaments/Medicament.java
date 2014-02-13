/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package es.usc.citius.servando.android.models.medicaments;

import org.simpleframework.xml.Default;
import org.simpleframework.xml.DefaultType;
import org.simpleframework.xml.Element;

/**
 * Esta clase representa un medicamento
 * 
 * @author tomas
 */
@Default(DefaultType.FIELD)
public class Medicament {

	/**
	 * Nombre del medicamento
	 */
	@Element(name = "name")
	private String name;

	/**
	 * Dosis típica para este medicamento
	 */
	@Element(name = "typicalDose")
	private MedicamentAdministration typicalAdministration;

	/**
	 * Dosis en las que está disponible este medicamento. Por ejemplo: 500 mg, 150mg...
	 */
	@Element(name = "doses")
	private String[] doses;

	/**
	 * @return Nombre del medicamento
	 */
	public String getName()
	{
		return name;
	}

	/**
	 * Nombre del medicamento
	 * 
	 * @param name the name to set
	 */
	public void setName(String name)
	{
		this.name = name;
	}

	/**
	 * @return the Dosis típica para este medicamento
	 */
	public MedicamentAdministration getTypicalAdministration()
	{
		return typicalAdministration;
	}

	/**
	 * Dosis típica para este medicamento
	 * 
	 * @param typicalAdministration the typicalAdministration to set
	 */
	public void setTypicalAdministration(MedicamentAdministration typicalAdministration)
	{
		this.typicalAdministration = typicalAdministration;
	}

	/**
	 * @return Dosis en las que está disponible este medicamento. Por ejemplo: 500 mg, 150mg...
	 */
	public String[] getDoses()
	{
		return doses;
	}

	/**
	 * Dosis en las que está disponible este medicamento. Por ejemplo: 500 mg, 150mg...
	 * 
	 * @param doses the doses to set
	 */
	public void setDoses(String[] doses)
	{
		this.doses = doses;
	}
}
