/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package es.usc.citius.servando.android.models.protocol.medicalActionResults.tests;

import org.simpleframework.xml.Default;
import org.simpleframework.xml.DefaultType;
import org.simpleframework.xml.Element;

/**
 * Esta clase agrupa los resultados de los test de calidad de vida
 */
@Default(DefaultType.FIELD)
public class TestsResult {

	@Element(name = "SF36Result")
	private SF36Result SF36Result;
	@Element(name = "STGeorgeResult")
	private STGeorgeResult STGeorgeResult;

	/**
	 * Indica si este resultado está completo
	 * 
	 * @return
	 */
	public boolean Completed()
	{
		return getSF36Result() != null && getSTGeorgeResult() != null;
	}

	/**
	 * @return the SF36Result
	 */
	public SF36Result getSF36Result()
	{
		return SF36Result;
	}

	/**
	 * @param SF36Result the SF36Result to set
	 */
	public void setSF36Result(SF36Result SF36Result)
	{
		this.SF36Result = SF36Result;
	}

	/**
	 * @return the STGeorgeResult
	 */
	public STGeorgeResult getSTGeorgeResult()
	{
		return STGeorgeResult;
	}

	/**
	 * @param STGeorgeResult the STGeorgeResult to set
	 */
	public void setSTGeorgeResult(STGeorgeResult STGeorgeResult)
	{
		this.STGeorgeResult = STGeorgeResult;
	}

	@Override
	public boolean equals(Object o)
	{
		return o instanceof TestsResult && o.hashCode() == this.hashCode();
	}

	@Override
	public int hashCode()
	{
		int hash = 3;
		hash = 59 * hash + (this.SF36Result != null ? this.SF36Result.hashCode() : 0);
		hash = 59 * hash + (this.STGeorgeResult != null ? this.STGeorgeResult.hashCode() : 0);
		return hash;
	}
}
