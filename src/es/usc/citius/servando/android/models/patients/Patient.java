/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package es.usc.citius.servando.android.models.patients;

import org.simpleframework.xml.Default;
import org.simpleframework.xml.DefaultType;
import org.simpleframework.xml.Element;
import org.simpleframework.xml.Order;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

/**
 * Clase que representa a un paciente.
 * 
 * @author tomas
 */
@Default(DefaultType.FIELD)
@Order(elements = { "name", "surnames", "phonenumber", "address", "clinicalHistoryNumber" })
@DatabaseTable(tableName = "patients")
public class Patient implements Comparable<Patient> {

	@DatabaseField(generatedId = true)
	@Element(name = "id")
	private Long id;

	@Element(name = "clinicalHistoryNumber")
	@DatabaseField
	private String clinicalHistoryNumber;

	@Element(name = "name")
	@DatabaseField
	private String name;

	@Element(name = "surnames")
	@DatabaseField
	private String surnames;

	@Element(name = "address")
	@DatabaseField
	private String address;

	@Element(name = "phonenumber")
	@DatabaseField
	private String phoneNumber;

	public Patient()
	{

	}

	public Patient(String name, String surnames, String clinicalHistoryNumber, String address, String phoneNumber)
	{
		this();
		this.name = name;
		this.surnames = surnames;
		this.clinicalHistoryNumber = clinicalHistoryNumber;
		this.address = address;
		this.phoneNumber = phoneNumber;
	}

	/**
	 * Obtiene las iniciales del paciente.
	 * 
	 * @return
	 */
	public String getInitials()
	{
		String initials = "";
		for (String word : getName().split("[\\s]+"))
		{
			initials += word.toUpperCase().substring(0, 1);
		}
		for (String word : getSurnames().split("[\\s]+"))
		{
			initials += word.toUpperCase().substring(0, 1);
		}
		return initials;
	}

	@Override
	public String toString()
	{
		return getName() + " " + getSurnames();
	}

	@Override
	public boolean equals(Object o)
	{
		if (o instanceof Patient)
		{
			return o.hashCode() == this.hashCode();
		}
		return false;
	}

	@Override
	public int hashCode()
	{
		int hash = 5;
		hash = 59 * hash + (name != null ? name.hashCode() : 0);
		hash = 59 * hash + (surnames != null ? surnames.hashCode() : 0);
		hash = 59 * hash + (clinicalHistoryNumber != null ? clinicalHistoryNumber.hashCode() : 0);
		hash = 59 * hash + (address != null ? address.hashCode() : 0);
		hash = 59 * hash + (phoneNumber != null ? phoneNumber.hashCode() : 0);
		return hash;
	}

	@Override
	public int compareTo(Patient o)
	{
		if (o instanceof Patient)
		{
			return surnames.compareTo(o.surnames);
		} else
		{
			throw new ClassCastException("Please compare only with other objects of Paciente class");
		}
	}

	public String getName()
	{
		return name;
	}

	public void setName(String name)
	{
		this.name = name;
	}

	public String getSurnames()
	{
		return surnames;
	}

	public void setSurnames(String surnames)
	{
		this.surnames = surnames;
	}

	public String getClinicalHistoryNumber()
	{
		return clinicalHistoryNumber;
	}

	public void setClinicalHistoryNumber(String clinicalHistoryNumber)
	{
		this.clinicalHistoryNumber = clinicalHistoryNumber;
	}

	public String getAddress()
	{
		return address;
	}

	public void setAddress(String address)
	{
		this.address = address;
	}

	public String getPhoneNumber()
	{
		return phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber)
	{
		this.phoneNumber = phoneNumber;
	}
}
