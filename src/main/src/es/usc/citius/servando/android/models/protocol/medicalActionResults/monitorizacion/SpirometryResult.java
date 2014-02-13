/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package es.usc.citius.servando.android.models.protocol.medicalActionResults.monitorizacion;

import java.util.GregorianCalendar;

import org.simpleframework.xml.Default;
import org.simpleframework.xml.DefaultType;
import org.simpleframework.xml.Element;

/**
 * Clase que encapsula los datos de una medición realizada con el espirómetro.
 * 
 * @author tomas
 */
@Default(DefaultType.FIELD)
public class SpirometryResult {

	/**
	 * Identificador do paciente
	 */
	@Element(name = "PatientID")
	public String patientID;
	/**
	 * Parámetro FVC, dado en mL.
	 */
	@Element(name = "FVC")
	public int FVC;
	/**
	 * Parámetro PEF, dado en L/min
	 */
	@Element(name = "PEF")
	public int PEF;
	/**
	 * Parámetro FEV1, dado en mL
	 */
	@Element(name = "FEV1")
	public int FEV1;
	/**
	 * Parámetro FEF75, dado en mL/s
	 */
	@Element(name = "FEF75")
	public int FEF75;
	/**
	 * Parámetro FEF50, dado en mL/s
	 */
	@Element(name = "FEF50")
	public int FEF50;
	/**
	 * Parámetro FEF25, dado en mL/s
	 */
	@Element(name = "FEF25")
	public int FEF25;
	/**
	 * Parámetro FEF 75/25, dado en mL/s
	 */
	@Element(name = "FEF7525")
	public int FEF7525;
	/**
	 * Fecha en la que fue realizada la medición
	 */
	@Element(name = "Date")
	public GregorianCalendar date;

	@Override
	public boolean equals(Object o)
	{
		if (o instanceof SpirometryResult)
		{
			SpirometryResult r = (SpirometryResult) o;
			if (r.date == null && this.date == null)
			{
				return r.hashCode() == this.hashCode();
			} else if (this.date != null)
			{
				return r.hashCode() == this.hashCode() && this.date.equals(r.date);
			}
		}
		return false;
	}

	@Override
	public int hashCode()
	{
		int hash = 7;
		hash = 89 * hash + (this.patientID != null ? this.patientID.hashCode() : 0);
		hash = 89 * hash + this.FVC;
		hash = 89 * hash + this.PEF;
		hash = 89 * hash + this.FEV1;
		hash = 89 * hash + this.FEF75;
		hash = 89 * hash + this.FEF50;
		hash = 89 * hash + this.FEF25;
		hash = 89 * hash + this.FEF7525;
		return hash;
	}
}
