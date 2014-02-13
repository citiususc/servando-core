/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package es.usc.citius.servando.android.models.protocol.medicalActionResults.valoracionHistoriaClinica;

import org.simpleframework.xml.Default;
import org.simpleframework.xml.DefaultType;
import org.simpleframework.xml.Element;

/**
 * Esta clase guardará el resultado del cuestionario de constantes vitales <see
 * cref="ServandoPlatformPreliminary.Servando.Forms.MedicalActions.ValoracionHistoriaClinica.ConstantesVitales"/>
 */
@Default(DefaultType.FIELD)
public class ConstantesVitalesResult {

	@Element(name = "tensionSistolica")
	private int tensionSistolica;
	@Element(name = "tensionDiastolica")
	private int tensionDiastolica;
	@Element(name = "temperatura")
	private float temperatura;
	@Element(name = "frecuenciaRespiratoria")
	private int frecuenciaRespiratoria;

	/**
	 * Tensión sistólica medida.
	 * 
	 * @return the tensionSistolica
	 */
	public int getTensionSistolica()
	{
		return tensionSistolica;
	}

	/**
	 * Tensión sistólica medida.
	 * 
	 * @param tensionSistolica the tensionSistolica to set
	 */
	public void setTensionSistolica(int tensionSistolica)
	{
		this.tensionSistolica = tensionSistolica;
	}

	/**
	 * Tensión diastólica medida.
	 * 
	 * @return the tensionDiastolica
	 */
	public int getTensionDiastolica()
	{
		return tensionDiastolica;
	}

	/**
	 * Tensión diastólica medida.
	 * 
	 * @param tensionDiastolica the tensionDiastolica to set
	 */
	public void setTensionDiastolica(int tensionDiastolica)
	{
		this.tensionDiastolica = tensionDiastolica;
	}

	/**
	 * Temperatura medida.
	 * 
	 * @return the temperatura
	 */
	public float getTemperatura()
	{
		return temperatura;
	}

	/**
	 * Temperatura medida.
	 * 
	 * @param temperatura the temperatura to set
	 */
	public void setTemperatura(float temperatura)
	{
		this.temperatura = temperatura;
	}

	/**
	 * Frecuencia respiratoria medida.
	 * 
	 * @return the frecuenciaRespiratoria
	 */
	public int getFrecuenciaRespiratoria()
	{
		return frecuenciaRespiratoria;
	}

	/**
	 * Frecuencia respiratoria medida.
	 * 
	 * @param frecuenciaRespiratoria the frecuenciaRespiratoria to set
	 */
	public void setFrecuenciaRespiratoria(int frecuenciaRespiratoria)
	{
		this.frecuenciaRespiratoria = frecuenciaRespiratoria;
	}

	@Override
	public boolean equals(Object o)
	{
		return o instanceof ConstantesVitalesResult && o.hashCode() == this.hashCode();
	}

	@Override
	public int hashCode()
	{
		int hash = 5;
		hash = 83 * hash + this.tensionSistolica;
		hash = 83 * hash + this.tensionDiastolica;
		hash = 83 * hash + Float.floatToIntBits(this.temperatura);
		hash = 83 * hash + this.frecuenciaRespiratoria;
		return hash;
	}
}
