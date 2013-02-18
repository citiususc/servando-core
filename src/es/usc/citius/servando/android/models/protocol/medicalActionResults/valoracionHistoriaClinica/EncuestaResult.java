/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package es.usc.citius.servando.android.models.protocol.medicalActionResults.valoracionHistoriaClinica;

import java.util.ArrayList;
import java.util.GregorianCalendar;

import org.simpleframework.xml.Default;
import org.simpleframework.xml.DefaultType;
import org.simpleframework.xml.Element;

/**
 * Esta clase almacena el resultado de la encuesta diaria a los pacientes.
 */
@Default(DefaultType.FIELD)
public class EncuestaResult {

	/**
	 * Mes al que corresponde la encuesta
	 */
	@Element(name = "mes")
	private GregorianCalendar mes;
	@Element(name = "diasAumentoDisnea")
	private ArrayList<Integer> diasAumentoDisnea;
	@Element(name = "diasAumentoEsputo")
	private ArrayList<Integer> diasAumentoEsputo;
	@Element(name = "diasEsputoPurulento")
	private ArrayList<Integer> diasEsputoPurulento;
	@Element(name = "diasFiebre")
	private ArrayList<Integer> diasFiebre;
	@Element(name = "diasAumentoTos")
	private ArrayList<Integer> diasAumentoTos;
	@Element(name = "diasSilbidosRespirar")
	private ArrayList<Integer> diasSilbidosRespirar;
	@Element(name = "diasOpresionToracica")
	private ArrayList<Integer> diasOpresionToracica;
	@Element(name = "inhalacionesDia")
	private int[] inhalacionesDia;

	public EncuestaResult()
	{
		setMes(new GregorianCalendar());
		diasAumentoDisnea = new ArrayList<Integer>();
		diasAumentoEsputo = new ArrayList<Integer>();
		diasEsputoPurulento = new ArrayList<Integer>();
		diasFiebre = new ArrayList<Integer>();
		diasAumentoTos = new ArrayList<Integer>();
		diasOpresionToracica = new ArrayList<Integer>();
		diasSilbidosRespirar = new ArrayList<Integer>();

	}

	/**
	 * Mes al que corresponde la encuesta
	 * 
	 * @return the mes
	 */
	public GregorianCalendar getMes()
	{
		return mes;
	}

	/**
	 * Mes al que corresponde la encuesta
	 * 
	 * @param mes the mes to set
	 */
	public void setMes(GregorianCalendar mes)
	{
		this.mes = mes;
		int[] newarray = new int[mes.getMaximum(GregorianCalendar.DAY_OF_MONTH)];
		if (inhalacionesDia != null)
		{
			System.arraycopy(inhalacionesDia, 0, newarray, 0, inhalacionesDia.length);
		}
		inhalacionesDia = newarray;
	}

	/**
	 * ArrayLista que guardará los números de los días del mes en el cual el paciente sintió un aumento de la disnea.
	 * 
	 * @return the diasAumentoDisnea
	 */
	public ArrayList<Integer> getDiasAumentoDisnea()
	{
		return diasAumentoDisnea;
	}

	/**
	 * ArrayLista que guardará los números de los días del mes en el cual el paciente sintió un aumento del volumen de
	 * esputo.
	 * 
	 * @return the diasAumentoEsputo
	 */
	public ArrayList<Integer> getDiasAumentoEsputo()
	{
		return diasAumentoEsputo;
	}

	/**
	 * ArrayLista que guardará los números de los días del mes en el cual el paciente tuvo esputo purulento
	 * 
	 * @return the diasEsputoPurulento
	 */
	public ArrayList<Integer> getDiasEsputoPurulento()
	{
		return diasEsputoPurulento;
	}

	/**
	 * ArrayLista que guardará los números de los días del mes en el cual el paciente tuvo fiebre
	 * 
	 * @return the diasFiebre
	 */
	public ArrayList<Integer> getDiasFiebre()
	{
		return diasFiebre;
	}

	/**
	 * ArrayLista que guardará los números de los días del mes en el cual el paciente sintió un aumento de la tos
	 * 
	 * @return the diasAumentoTos
	 */
	public ArrayList<Integer> getDiasAumentoTos()
	{
		return diasAumentoTos;
	}

	/**
	 * ArrayLista que guardará los números de los días del mes en el cual el paciente sintió silbidos al respirar.
	 * 
	 * @return the diasSilbidosRespirar
	 */
	public ArrayList<Integer> getDiasSilbidosRespirar()
	{
		return diasSilbidosRespirar;
	}

	/**
	 * ArrayLista que guardará los números de los días del mes en el cual el paciente sintió opresión torácica.
	 * 
	 * @return the diasOpresionToracica
	 */
	public ArrayList<Integer> getDiasOpresionToracica()
	{
		return diasOpresionToracica;
	}

	/**
	 * ArrayLista que guardará el número de veces que el paciente utilizó el inhalador cada día
	 * 
	 * @return the inhalacionesDia
	 */
	public int[] getInhalacionesDia()
	{
		return inhalacionesDia;
	}

	@Override
	public boolean equals(Object o)
	{
		if (o instanceof EncuestaResult)
		{
			EncuestaResult e = (EncuestaResult) o;
			boolean result = true;
			result &= this.mes != null && e.mes != null ? this.mes.get(GregorianCalendar.MONTH) == e.mes.get(GregorianCalendar.MONTH)
					&& this.mes.get(GregorianCalendar.YEAR) == e.mes.get(GregorianCalendar.YEAR) : this.mes == e.mes;
			result &= this.diasAumentoDisnea == null ? e.diasAumentoDisnea == null : this.diasAumentoDisnea.equals(e.diasAumentoDisnea);
			result &= this.diasAumentoEsputo == null ? e.diasAumentoEsputo == null : this.diasAumentoEsputo.equals(e.diasAumentoEsputo);
			result &= this.diasEsputoPurulento == null ? e.diasEsputoPurulento == null : this.diasEsputoPurulento.equals(e.diasEsputoPurulento);
			result &= this.diasFiebre == null ? e.diasFiebre == null : this.diasFiebre.equals(e.diasFiebre);
			result &= this.diasOpresionToracica == null ? e.diasOpresionToracica == null : this.diasOpresionToracica.equals(e.diasOpresionToracica);
			result &= this.diasSilbidosRespirar == null ? e.diasSilbidosRespirar == null : this.diasSilbidosRespirar.equals(e.diasSilbidosRespirar);
			result &= this.inhalacionesDia.length == e.inhalacionesDia.length;
			if (result)
			{
				for (int i = 0; i < this.inhalacionesDia.length; i++)
				{
					result &= this.inhalacionesDia[i] == e.inhalacionesDia[i];
				}
			}
			return result;
		}
		return false;
	}

	@Override
	public int hashCode()
	{
		int hash = 7;
		return hash;
	}

}
