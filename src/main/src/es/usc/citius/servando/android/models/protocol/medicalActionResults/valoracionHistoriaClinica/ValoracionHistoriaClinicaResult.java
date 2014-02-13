/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package es.usc.citius.servando.android.models.protocol.medicalActionResults.valoracionHistoriaClinica;

import org.simpleframework.xml.Default;
import org.simpleframework.xml.DefaultType;
import org.simpleframework.xml.Element;

/**
 * Esta clase agrupa los resultados de las pruebas de valoración de historia clínica del paciente.
 * 
 * @see ExploracionFisicaResult
 * @see ConstantesVitalesResult
 * @see TosResult
 * @see DisneaResult
 * @see FiebreResult
 * @see EncuestaResult
 */
@Default(DefaultType.FIELD)
public class ValoracionHistoriaClinicaResult {
	@Element(name = "exploracionFisicaResult")
	private ExploracionFisicaResult exploracionFisicaResult;
	@Element(name = "constantesVitalesResult")
	private ConstantesVitalesResult constantesVitalesResult;
	@Element(name = "tosResult")
	private TosResult tosResult;
	@Element(name = "disneaResult")
	private DisneaResult disneaResult;
	@Element(name = "fiebreResult")
	private FiebreResult fiebreResult;
	@Element(name = "encuestaResult")
	private EncuestaResult encuestaResult;

	/**
	 * Indica si este resultado está completo
	 * 
	 * @return
	 */
	public boolean Completed()
	{
		return getExploracionFisicaResult() != null && getConstantesVitalesResult() != null && getTosResult() != null && getDisneaResult() != null
				&& getFiebreResult() != null && getEncuestaResult() != null;
	}

	/**
	 * @return the exploracionFisicaResult
	 */
	public ExploracionFisicaResult getExploracionFisicaResult()
	{
		return exploracionFisicaResult;
	}

	/**
	 * @param exploracionFisicaResult the exploracionFisicaResult to set
	 */
	public void setExploracionFisicaResult(ExploracionFisicaResult exploracionFisicaResult)
	{
		this.exploracionFisicaResult = exploracionFisicaResult;
	}

	/**
	 * @return the constantesVitalesResult
	 */
	public ConstantesVitalesResult getConstantesVitalesResult()
	{
		return constantesVitalesResult;
	}

	/**
	 * @param constantesVitalesResult the constantesVitalesResult to set
	 */
	public void setConstantesVitalesResult(ConstantesVitalesResult constantesVitalesResult)
	{
		this.constantesVitalesResult = constantesVitalesResult;
	}

	/**
	 * @return the tosResult
	 */
	public TosResult getTosResult()
	{
		return tosResult;
	}

	/**
	 * @param tosResult the tosResult to set
	 */
	public void setTosResult(TosResult tosResult)
	{
		this.tosResult = tosResult;
	}

	/**
	 * @return the disneaResult
	 */
	public DisneaResult getDisneaResult()
	{
		return disneaResult;
	}

	/**
	 * @param disneaResult the disneaResult to set
	 */
	public void setDisneaResult(DisneaResult disneaResult)
	{
		this.disneaResult = disneaResult;
	}

	/**
	 * @return the fiebreResult
	 */
	public FiebreResult getFiebreResult()
	{
		return fiebreResult;
	}

	/**
	 * @param fiebreResult the fiebreResult to set
	 */
	public void setFiebreResult(FiebreResult fiebreResult)
	{
		this.fiebreResult = fiebreResult;
	}

	/**
	 * @return the encuestaResult
	 */
	public EncuestaResult getEncuestaResult()
	{
		return encuestaResult;
	}

	/**
	 * @param encuestaResult the encuestaResult to set
	 */
	public void setEncuestaResult(EncuestaResult encuestaResult)
	{
		this.encuestaResult = encuestaResult;
	}

	@Override
	public boolean equals(Object o)
	{
		if (o instanceof ValoracionHistoriaClinicaResult)
		{
			return o.hashCode() == this.hashCode();
		}
		return false;
	}

	@Override
	public int hashCode()
	{
		int hash = 3;
		hash = 71 * hash + (this.exploracionFisicaResult != null ? this.exploracionFisicaResult.hashCode() : 0);
		hash = 71 * hash + (this.constantesVitalesResult != null ? this.constantesVitalesResult.hashCode() : 0);
		hash = 71 * hash + (this.tosResult != null ? this.tosResult.hashCode() : 0);
		hash = 71 * hash + (this.disneaResult != null ? this.disneaResult.hashCode() : 0);
		hash = 71 * hash + (this.fiebreResult != null ? this.fiebreResult.hashCode() : 0);
		hash = 71 * hash + (this.encuestaResult != null ? this.encuestaResult.hashCode() : 0);
		return hash;
	}
}
