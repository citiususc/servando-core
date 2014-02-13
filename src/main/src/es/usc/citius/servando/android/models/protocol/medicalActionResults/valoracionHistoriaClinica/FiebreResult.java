/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package es.usc.citius.servando.android.models.protocol.medicalActionResults.valoracionHistoriaClinica;

import org.simpleframework.xml.Default;
import org.simpleframework.xml.DefaultType;
import org.simpleframework.xml.Element;

/**
 * 
 * Esta clase almacena el resultado del test de fiebre
 */
@Default(DefaultType.FIELD)
public class FiebreResult {

	/**
	 * Número de episodios de fiebre sufridos por el paciente
	 */
	@Element(name = "episodiosFiebre")
	private int episodiosFiebre;

	/**
	 * Número de episodios de fiebre sufridos por el paciente
	 * 
	 * @return the episodiosFiebre
	 */
	public int getEpisodiosFiebre()
	{
		return episodiosFiebre;
	}

	/**
	 * Número de episodios de fiebre sufridos por el paciente
	 * 
	 * @param episodiosFiebre the episodiosFiebre to set
	 */
	public void setEpisodiosFiebre(int episodiosFiebre)
	{
		this.episodiosFiebre = episodiosFiebre;
	}

	@Override
	public boolean equals(Object o)
	{
		return o instanceof FiebreResult && o.hashCode() == this.hashCode();
	}

	@Override
	public int hashCode()
	{
		int hash = 7;
		hash = 11 * hash + this.episodiosFiebre;
		return hash;
	}
}
