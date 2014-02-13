/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package es.usc.citius.servando.android.models.protocol.medicalActionResults.monitorizacion;

import org.simpleframework.xml.Default;
import org.simpleframework.xml.DefaultType;
import org.simpleframework.xml.Element;

/**
 * Esta clase agrupa los resultados de las pruebas de monitorización realizadas.
 * 
 * @author tomas
 */
@Default(DefaultType.FIELD)
public class MonitorizacionResult {

	@Element(name = "spirometryResult")
	private SpirometryResult spirometryResult;
	@Element(name = "ECGCompleted")
	private boolean ECGCompleted;
	@Element(name = "PulseCompleted")
	private boolean PulseCompleted;

	/**
	 * Resultado de la prueba de espirometría.
	 * 
	 * @return the spirometryResult
	 */
	public SpirometryResult getSpirometryResult()
	{
		return spirometryResult;
	}

	/**
	 * Resultado de la prueba de espirometría.
	 * 
	 * @param spirometryResult the spirometryResult to set
	 */
	public void setSpirometryResult(SpirometryResult spirometryResult)
	{
		this.spirometryResult = spirometryResult;
	}

	/**
	 * Indica si se ha realizado la prueba de ECG
	 * 
	 * @return the ECGCompleted
	 */
	public boolean isECGCompleted()
	{
		return ECGCompleted;
	}

	/**
	 * Indica si se ha realizado la prueba de ECG
	 * 
	 * @param ECGCompleted the ECGCompleted to set
	 */
	public void setECGCompleted(boolean ECGCompleted)
	{
		this.ECGCompleted = ECGCompleted;
	}

	/**
	 * Indica si se ha realizado la prueba de Pulsioximetría
	 * 
	 * @return the PulseCompleted
	 */
	public boolean isPulseCompleted()
	{
		return PulseCompleted;
	}

	/**
	 * Indica si se ha realizado la prueba de Pulsioximetría
	 * 
	 * @param PulseCompleted the PulseCompleted to set
	 */
	public void setPulseCompleted(boolean PulseCompleted)
	{
		this.PulseCompleted = PulseCompleted;
	}

	@Override
	public boolean equals(Object o)
	{
		return o instanceof MonitorizacionResult && o.hashCode() == this.hashCode();
	}

	@Override
	public int hashCode()
	{
		int hash = 5;
		hash = 41 * hash + (this.spirometryResult != null ? this.spirometryResult.hashCode() : 0);
		hash = 41 * hash + (this.ECGCompleted ? 1 : 0);
		hash = 41 * hash + (this.PulseCompleted ? 1 : 0);
		return hash;
	}
}
