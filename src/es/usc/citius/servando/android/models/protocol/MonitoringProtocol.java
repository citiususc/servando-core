/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package es.usc.citius.servando.android.models.protocol;

import java.util.ArrayList;

import org.simpleframework.xml.Default;
import org.simpleframework.xml.DefaultType;
import org.simpleframework.xml.Element;
import org.simpleframework.xml.Root;

import es.usc.citius.servando.android.models.patients.Patient;

/**
 * Esta clase es la clase central del paquete Servando.Models.Protocol. Corresponde a la definición del protocolo de
 * seguimiento de un paciente. Este protocolo simplemente consiste en un conjunto de ejecuciones de actuaciones médicas
 * organizadas en el tiempo sobre un paciente.
 * 
 * @see Patient
 * @see MedicalActionExecution
 * @author Tomás Teijeiro Campo
 */
@Root(name = "protocol")
@Default(DefaultType.FIELD)
@Deprecated
public class MonitoringProtocol {

	/**
	 * Paciente sobre el que se ejecuta el protocolo
	 */
	@Element(name = "paciente")
	private Patient Paciente;
	/**
	 * Actuaciones médicas que se ejecutarán sobre el paciente.
	 */
	@Element(name = "medicalAction")
	private ArrayList<MedicalActionExecution> MedicalActions;

	public MonitoringProtocol()
	{
		MedicalActions = new ArrayList<MedicalActionExecution>();
	}

	/**
	 * Paciente sobre el que se ejecuta el
	 * 
	 * @return the Paciente
	 */
	public Patient getPaciente()
	{
		return Paciente;
	}

	/**
	 * Paciente sobre el que se ejecuta el
	 * 
	 * @param Paciente the Paciente to set
	 */
	public void setPaciente(Patient Paciente)
	{
		this.Paciente = Paciente;
	}

	/**
	 * Actuaciones médicas que se ejecutarán sobre el paciente.
	 * 
	 * @return the MedicalActions
	 */
	public ArrayList<MedicalActionExecution> getMedicalActions()
	{
		return MedicalActions;
	}

	/**
	 * Actuaciones médicas que se ejecutarán sobre el paciente.
	 * 
	 * @param MedicalActions the MedicalActions to set
	 */
	public void setMedicalActions(ArrayList<MedicalActionExecution> MedicalActions)
	{
		this.MedicalActions = MedicalActions;
	}
}
