/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package es.usc.citius.servando.android.models.protocol.medicalActionResults.valoracionHistoriaClinica;

import org.simpleframework.xml.Default;
import org.simpleframework.xml.DefaultType;
import org.simpleframework.xml.Element;

/**
 * import javax.xml.bind.annotation.XmlElement;
 * 
 * Esta clase almacena los datos del test de disnea
 */
@Default(DefaultType.FIELD)
public class DisneaResult {

	@Element(name = "magnitudTarea")
	private int magnitudTarea;
	@Element(name = "afectacionFuncional")
	private int afectacionFuncional;
	@Element(name = "magnitudEsfuerzo")
	private int magnitudEsfuerzo;

	/**
	 * Magnitud de la tarea realizada para que aparezca disnea (0 = Extraordinaria, 1 = mayor, 2 = moderada, 3 = ligera
	 * o 4 = sin tarea)
	 * 
	 * @return the magnitudTarea
	 */
	public int getMagnitudTarea()
	{
		return magnitudTarea;
	}

	/**
	 * Magnitud de la tarea realizada para que aparezca disnea (0 = Extraordinaria, 1 = mayor, 2 = moderada, 3 = ligera
	 * o 4 = sin tarea)
	 * 
	 * @param magnitudTarea the magnitudTarea to set
	 */
	public void setMagnitudTarea(int magnitudTarea)
	{
		this.magnitudTarea = magnitudTarea;
	}

	/**
	 * Afectación funcional de la disnea (0 = muy severa,1 = severa, 2 = moderada, 3 = ligera, 4 = muy ligera o 5 =
	 * ninguna)
	 * 
	 * @return the afectacionFuncional
	 */
	public int getAfectacionFuncional()
	{
		return afectacionFuncional;
	}

	/**
	 * Afectación funcional de la disnea (0 = muy severa,1 = severa, 2 = moderada, 3 = ligera, 4 = muy ligera o 5 =
	 * ninguna)
	 * 
	 * @param afectacionFuncional the afectacionFuncional to set
	 */
	public void setAfectacionFuncional(int afectacionFuncional)
	{
		this.afectacionFuncional = afectacionFuncional;
	}

	/**
	 * Magnitud del esfuerzo realizado para que aparezca disnea (0 = Extraordinario, 1 = mayor, 2 = moderado, 3 = ligero
	 * o 4 = sin esfuerzo)
	 * 
	 * @return the magnitudEsfuerzo
	 */
	public int getMagnitudEsfuerzo()
	{
		return magnitudEsfuerzo;
	}

	/**
	 * Magnitud del esfuerzo realizado para que aparezca disnea (0 = Extraordinario, 1 = mayor, 2 = moderado, 3 = ligero
	 * o 4 = sin esfuerzo)
	 * 
	 * @param magnitudEsfuerzo the magnitudEsfuerzo to set
	 */
	public void setMagnitudEsfuerzo(int magnitudEsfuerzo)
	{
		this.magnitudEsfuerzo = magnitudEsfuerzo;
	}

	@Override
	public boolean equals(Object o)
	{
		return o instanceof DisneaResult && o.hashCode() == this.hashCode();
	}

	@Override
	public int hashCode()
	{
		int hash = 3;
		hash = 29 * hash + this.magnitudTarea;
		hash = 29 * hash + this.afectacionFuncional;
		hash = 29 * hash + this.magnitudEsfuerzo;
		return hash;
	}
}
