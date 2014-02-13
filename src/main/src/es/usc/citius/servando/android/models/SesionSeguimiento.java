/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package es.usc.citius.servando.android.models;

import java.util.GregorianCalendar;

import org.simpleframework.xml.Default;
import org.simpleframework.xml.DefaultType;
import org.simpleframework.xml.Element;
import org.simpleframework.xml.Root;

import es.usc.citius.servando.android.models.patients.Patient;
import es.usc.citius.servando.android.models.protocol.medicalActionResults.ReagudizacionResult;
import es.usc.citius.servando.android.models.protocol.medicalActionResults.monitorizacion.MonitorizacionResult;
import es.usc.citius.servando.android.models.protocol.medicalActionResults.tests.TestsResult;
import es.usc.citius.servando.android.models.protocol.medicalActionResults.valoracionHistoriaClinica.ValoracionHistoriaClinicaResult;

/**
 * Esta clase representa una sesión de seguimiento realizada a un determinado paciente. Agrupa los datos del paciente y
 * los resultados de todas las pruebas realizadas.
 */
@Root(name = "SesionSeguimiento")
@Default(DefaultType.FIELD)
public class SesionSeguimiento {

	@Element(name = "timeStamp")
	private GregorianCalendar timeStamp;
	@Element(name = "paciente")
	private Patient paciente;
	@Element(name = "reagudizaciones")
	private ReagudizacionResult reagudizaciones;
	@Element(name = "valoracionHistoriaClinica")
	private ValoracionHistoriaClinicaResult valoracionHistoriaClinica;
	@Element(name = "monitorizaciones")
	private MonitorizacionResult monitorizaciones;
	@Element(name = "tests")
	private TestsResult tests;

	SesionSeguimiento()
	{
		this.timeStamp = new GregorianCalendar();
	}

	public SesionSeguimiento(Patient paciente)
	{
		this();
		this.paciente = paciente;
	}

	/**
	 * timeStamp que define el momento de creación de la sesión.
	 * 
	 * @return the timeStamp
	 */
	public GregorianCalendar getTimeStamp()
	{
		return timeStamp;
	}

	/**
	 * paciente al que se le ha realizado la sesión de seguimiento
	 * 
	 * @return the paciente
	 */
	public Patient getPaciente()
	{
		return paciente;
	}

	/**
	 * paciente al que se le ha realizado la sesión de seguimiento
	 * 
	 * @param paciente the paciente to set
	 */
	public void setPaciente(Patient paciente)
	{
		this.paciente = paciente;
	}

	/**
	 * Resultado del cuestionario de reagudizaciones
	 * 
	 * @return the reagudizaciones
	 */
	public ReagudizacionResult getReagudizaciones()
	{
		return reagudizaciones;
	}

	/**
	 * Resultado del cuestionario de reagudizaciones
	 * 
	 * @param reagudizaciones the reagudizaciones to set
	 */
	public void setReagudizaciones(ReagudizacionResult reagudizaciones)
	{
		this.reagudizaciones = reagudizaciones;
	}

	/**
	 * Resultados de las pruebas de valoración de historia clínica
	 * 
	 * @return the valoracionHistoriaClinica
	 */
	public ValoracionHistoriaClinicaResult getValoracionHistoriaClinica()
	{
		return valoracionHistoriaClinica;
	}

	/**
	 * Resultados de las pruebas de valoración de historia clínica
	 * 
	 * @param valoracionHistoriaClinica the valoracionHistoriaClinica to set
	 */
	public void setValoracionHistoriaClinica(ValoracionHistoriaClinicaResult valoracionHistoriaClinica)
	{
		this.valoracionHistoriaClinica = valoracionHistoriaClinica;
	}

	/**
	 * Resultados de las monitorizaciones
	 * 
	 * @return the monitorizaciones
	 */
	public MonitorizacionResult getMonitorizaciones()
	{
		return monitorizaciones;
	}

	/**
	 * Resultados de las monitorizaciones
	 * 
	 * @param monitorizaciones the monitorizaciones to set
	 */
	public void setMonitorizaciones(MonitorizacionResult monitorizaciones)
	{
		this.monitorizaciones = monitorizaciones;
	}

	/**
	 * Resultados de los test de calidad de vida
	 * 
	 * @return the tests
	 */
	public TestsResult getTests()
	{
		return tests;
	}

	/**
	 * Resultados de los test de calidad de vida
	 * 
	 * @param tests the tests to set
	 */
	public void setTests(TestsResult tests)
	{
		this.tests = tests;
	}

	@Override
	public boolean equals(Object obj)
	{
		if (obj instanceof SesionSeguimiento)
		{
			SesionSeguimiento s = (SesionSeguimiento) obj;
			if (s.timeStamp == null && this.timeStamp == null)
			{
				return this.hashCode() == s.hashCode();
			} else if (this.timeStamp != null)
			{
				return this.hashCode() == s.hashCode() && this.timeStamp.compareTo(s.timeStamp) == 0;
			}
		}
		return false;
	}

	@Override
	public int hashCode()
	{
		int hash = 7;
		hash = 37 * hash + (this.paciente != null ? this.paciente.hashCode() : 0);
		hash = 37 * hash + (this.reagudizaciones != null ? this.reagudizaciones.hashCode() : 0);
		hash = 37 * hash + (this.valoracionHistoriaClinica != null ? this.valoracionHistoriaClinica.hashCode() : 0);
		hash = 37 * hash + (this.monitorizaciones != null ? this.monitorizaciones.hashCode() : 0);
		hash = 37 * hash + (this.tests != null ? this.tests.hashCode() : 0);
		return hash;
	}
}
