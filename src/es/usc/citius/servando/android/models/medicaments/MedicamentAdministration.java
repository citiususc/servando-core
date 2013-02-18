/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package es.usc.citius.servando.android.models.medicaments;

import org.simpleframework.xml.Default;
import org.simpleframework.xml.DefaultType;
import org.simpleframework.xml.Element;

/**
 * Clase que encapsula los datos relativos a cómo se administra un medicamento. Incluye la dosis, y con qué frecuencia
 * se debe administrar esa dosis.
 * 
 * @author tomas
 */
@Default(DefaultType.FIELD)
public class MedicamentAdministration {

	/**
	 * Frecuencia a la cual se administra el medicamento. Por ejemplo, 2 comprimidos/día.
	 */
	@Element(name = "frequency")
	private float frequency;

	/**
	 * Nombre de la frecuencia del medicamento, por ejemplo "comprimidos/día".
	 */
	@Element(name = "frequencyUnit")
	private String frequencyUnit;

	/**
	 * Dosis en la que se está administrando este medicamento. Típicamente será uno de los valores de @see
	 * Medicament.getDoses()
	 */
	@Element(name = "dose")
	private String dose;

	/**
	 * Formato de la frecuencia de administración del medicamento, que indica el número de dígitos enteros y decimales
	 * que puede tener. Esta cadena deberá cumplir la expresión regular "x+(,y+)?", donde el número de 'x' indica la
	 * cantidad de dígitos enteros, y el número de 'y' indica la cantidad de dígitos decimales. Ejemplo, frecuencia de 3
	 * cifras enteras y 2 decimales: 'xxx,yy'
	 */
	@Element(name = "frequencyFormat")
	private String frequencyFormat = "x";

	/**
	 * @return the frequency
	 */
	public float getFrequency()
	{
		return frequency;
	}

	/**
	 * @param frequency the frequency to set
	 */
	public void setFrequency(float frequency)
	{
		this.frequency = frequency;
	}

	/**
	 * @return the frequencyUnit
	 */
	public String getFrequencyUnit()
	{
		return frequencyUnit;
	}

	/**
	 * @param frequencyUnit the frequencyUnit to set
	 */
	public void setFrequencyUnit(String frequencyUnit)
	{
		this.frequencyUnit = frequencyUnit;
	}

	/**
	 * @return the dose
	 */
	public String getDose()
	{
		return dose;
	}

	/**
	 * @param dose the dose to set
	 */
	public void setDose(String dose)
	{
		this.dose = dose;
	}

	/**
	 * @return the frequencyFormat
	 */
	public String getFrequencyFormat()
	{
		return frequencyFormat;
	}

	/**
	 * @param frequencyFormat the frequencyFormat to set
	 */
	public void setFrequencyFormat(String frequencyFormat)
	{
		this.frequencyFormat = frequencyFormat;
	}
}
