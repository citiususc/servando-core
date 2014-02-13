/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package es.usc.citius.servando.android.models.patients;

import java.util.ArrayList;

import org.simpleframework.xml.Default;
import org.simpleframework.xml.DefaultType;
import org.simpleframework.xml.Element;
import org.simpleframework.xml.Root;

/**
 * Esta clase es la raíz para acceder al grupo de pacientes en seguimiento. Encapsula una lista de pacientes, que se
 * puede guardar en un archivo XML.
 * 
 * @author tomas
 */
@Root(name = "patientGroup")
@Default(DefaultType.FIELD)
// @XmlType(name = "patientGroup", propOrder = {"patients"})
public class PatientGroup {

	/**
	 * Lista de pacientes que representa este grupo.
	 */
	@Element(name = "patient")
	private ArrayList<Patient> patients;

	public ArrayList<Patient> getPatients()
	{
		return patients;
	}

	public void setPatients(ArrayList<Patient> patients)
	{
		this.patients = patients;
	}

	public PatientGroup()
	{
		patients = new ArrayList<Patient>();
	}
}
