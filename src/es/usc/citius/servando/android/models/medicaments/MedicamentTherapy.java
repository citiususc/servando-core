/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package es.usc.citius.servando.android.models.medicaments;

import org.simpleframework.xml.Default;
import org.simpleframework.xml.DefaultType;
import org.simpleframework.xml.Element;
import org.simpleframework.xml.Transient;

import es.usc.citius.servando.android.models.patients.Patient;

/**
 * Clase que representa la administración de un medicamento a un paciente.
 * 
 * @author tomas
 */
@Default(DefaultType.FIELD)
public class MedicamentTherapy {

	/**
	 * Medicamento que se administra
	 */
	@Element(name = "medicament")
	private Medicament medicament;

	/**
	 * Dosis administrada, expresada como una dosis y una frecuencia de administración del medicamento @see
	 * MedicamentAdministration
	 */
	@Element(name = "dose")
	private MedicamentAdministration dose;

	/**
	 * Paciente al que se le aplica esta terapia
	 */
	@Transient
	private Patient paciente;

	/**
	 * Medicamento que se administra
	 * 
	 * @return the medicament
	 */
	public Medicament getMedicament()
	{
		return medicament;
	}

	/**
	 * @param medicament the medicament to set
	 */
	public void setMedicament(Medicament medicament)
	{
		this.medicament = medicament;
	}

	/**
	 * Dosis administrada, expresada como una dosis y una frecuencia de administración del medicamento @see
	 * MedicamentAdministration
	 * 
	 * @return
	 */
	public MedicamentAdministration getDose()
	{
		return dose;
	}

	/**
	 * @param dose the dose to set
	 */
	public void setDose(MedicamentAdministration dose)
	{
		this.dose = dose;
	}

	/**
	 * Paciente al que se le aplica esta terapia
	 * 
	 * @return the paciente
	 */
	public Patient getPaciente()
	{
		return paciente;
	}

	/**
	 * @param paciente the paciente to set
	 */
	public void setPaciente(Patient paciente)
	{
		this.paciente = paciente;
	}
}