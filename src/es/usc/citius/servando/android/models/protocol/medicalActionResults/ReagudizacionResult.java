/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package es.usc.citius.servando.android.models.protocol.medicalActionResults;

import org.simpleframework.xml.Default;
import org.simpleframework.xml.DefaultType;
import org.simpleframework.xml.Element;

/**
 * Clase que gardará os datos do test de reagudización, obtidos a partires do formulario de reagudización.
 */
@Default(DefaultType.FIELD)
public class ReagudizacionResult {

	/**
	 * Indica si se ha producido reagudizaciones en la enfermedad del paciente
	 */
	@Element(name = "reagudizacionesProducidas")
	private boolean reagudizacionesProducidas;

	/**
	 * Indica si se ha producido reagudizaciones en la enfermedad del paciente
	 * 
	 * @return the reagudizacionesProducidas
	 */
	public boolean getReagudizacionesProducidas()
	{
		return reagudizacionesProducidas;
	}

	/**
	 * Indica si se ha producido reagudizaciones en la enfermedad del paciente
	 * 
	 * @param reagudizacionesProducidas the reagudizacionesProducidas to set
	 */
	public void setReagudizacionesProducidas(boolean reagudizacionesProducidas)
	{
		this.reagudizacionesProducidas = reagudizacionesProducidas;
	}

	@Override
	public boolean equals(Object o)
	{
		if (o instanceof ReagudizacionResult)
		{
			return o.hashCode() == this.hashCode();
		}
		return false;
	}

	@Override
	public int hashCode()
	{
		int hash = 3;
		hash = 37 * hash + (this.reagudizacionesProducidas ? 1 : 0);
		return hash;
	}
}
