/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package es.usc.citius.servando.android.models.protocol.medicalActionResults.valoracionHistoriaClinica;

import org.simpleframework.xml.Default;
import org.simpleframework.xml.DefaultType;
import org.simpleframework.xml.Element;

/**
 * Esta clase representa los resultados del test de tos
 */
@Default(DefaultType.FIELD)
public class TosResult {
	/**
	 * Si la tos es productiva, características de la misma (0 = amarillo-verdosa, 1 = hemóptica o 2 = mucosa)
	 */
	@Element(name = "tos")
	private boolean tos;
	@Element(name = "tipoTos")
	private int tipoTos;
	@Element(name = "caracteristicasProduccion")
	private int caracteristicasProduccion;

	/**
	 * Indica si el paciente tose o no
	 * 
	 * @return the tos
	 */
	public boolean getTos()
	{
		return tos;
	}

	/**
	 * Indica si el paciente tose o no
	 * 
	 * @param tos the tos to set
	 */
	public void setTos(boolean tos)
	{
		this.tos = tos;
	}

	/**
	 * Tipo de tos (0 = seca o 1 = productiva)
	 * 
	 * @return the tipoTos
	 */
	public int getTipoTos()
	{
		return tipoTos;
	}

	/**
	 * Tipo de tos (0 = seca o 1 = productiva)
	 * 
	 * @param tipoTos the tipoTos to set
	 */
	public void setTipoTos(int tipoTos)
	{
		this.tipoTos = tipoTos;
	}

	/**
	 * Si la tos es productiva, características de la misma (0 = amarillo-verdosa, 1 = hemóptica o 2 = mucosa)
	 * 
	 * @return the caracteristicasProduccion
	 */
	public int getCaracteristicasProduccion()
	{
		return caracteristicasProduccion;
	}

	/**
	 * Si la tos es productiva, características de la misma (0 = amarillo-verdosa, 1 = hemóptica o 2 = mucosa)
	 * 
	 * @param caracteristicasProduccion the caracteristicasProduccion to set
	 */
	public void setCaracteristicasProduccion(int caracteristicasProduccion)
	{
		this.caracteristicasProduccion = caracteristicasProduccion;
	}

	@Override
	public boolean equals(Object o)
	{
		return o instanceof TosResult && o.hashCode() == this.hashCode();
	}

	@Override
	public int hashCode()
	{
		int hash = 7;
		hash = 89 * hash + (this.tos ? 1 : 0);
		hash = 89 * hash + this.tipoTos;
		hash = 89 * hash + this.caracteristicasProduccion;
		return hash;
	}
}
