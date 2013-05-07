/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package es.usc.citius.servando.android.models.protocol;

import java.util.ArrayList;
import java.util.GregorianCalendar;
import java.util.List;

import org.simpleframework.xml.Default;
import org.simpleframework.xml.DefaultType;
import org.simpleframework.xml.Element;
import org.simpleframework.xml.ElementList;
import org.simpleframework.xml.Order;
import org.simpleframework.xml.Root;
import org.simpleframework.xml.convert.Convert;

import es.usc.citius.servando.android.xml.converters.GregorianCalendarConverter;

/**
 * Esta clase representa un protocolo médico, el cual consiste en un conjunto de actuaciones médicas programadas para el
 * tratamiento estándar de una enfermedad determinada. A partir de estos protocolos se creará el protocolo de
 * seguimiento concreto para el paciente actual, de la clase MonitoringProtocol.
 * 
 * @author Tomás Teijeiro Campo
 */
@Root(name = "MedicalProtocol")
@Default(DefaultType.FIELD)
@Order(elements = { "startDate", "name", "description" })
public class MedicalProtocol {

	@Element(name = "version", required = false)
	private String version = "0";

	/**
	 * Nombre del protocolo. Sirve como identificador del mismo.
	 */
	@Element(name = "name")
	private String name;
	/**
	 * Descripción del protocolo.
	 */
	@Element(name = "description")
	private String description;
	/**
	 * Acciones que conforman el protocolo médico.
	 */
	@ElementList(name = "actions", type = ProtocolAction.class, inline = true)
	private List<ProtocolAction> actions;
	/**
	 * Fecha de inicio de aplicación del protocolo médico. De esta propiedad solamente se obviará la información
	 * relativa a la hora, considerando que el momento de inicio de aplicación del protocolo serán siempre las 00:00
	 * horas del día representado por este atributo. Las representaciones de tiempos relativos, como la propiedad
	 * {@link servando#models #protocol#ProtocolAction#GetStartGap} harán referencia a este momento inicial.
	 */
	@Element(name = "startDate")
	@Convert(value = GregorianCalendarConverter.class)
	private GregorianCalendar startDate;

	public MedicalProtocol()
	{
		actions = new ArrayList<ProtocolAction>();
	}

	/**
	 * Nombre del protocolo. Sirve como identificador del mismo.
	 * 
	 * @return the name
	 */
	public String getName()
	{
		return name;
	}

	/**
	 * Nombre del protocolo. Sirve como identificador del mismo.
	 * 
	 * @param name the name to set
	 */
	public void setName(String name)
	{
		this.name = name;
	}

	/**
	 * Descripción del protocolo.
	 * 
	 * @return the description
	 */
	public String getDescription()
	{
		return description;
	}

	/**
	 * Descripción del protocolo.
	 * 
	 * @param description the description to set
	 */
	public void setDescription(String description)
	{
		this.description = description;
	}

	/**
	 * Acciones que conforman el protocolo médico.
	 * 
	 * @return the actions
	 */
	public List<ProtocolAction> getActions()
	{
		return actions;
	}

	/**
	 * Acciones que conforman el protocolo médico.
	 * 
	 * @param actions the actions to set
	 */
	public void setActions(ArrayList<ProtocolAction> actions)
	{
		this.actions = actions;
	}

	/**
	 * Fecha de inicio de aplicación del protocolo médico. De esta propiedad solamente se obviará la información
	 * relativa a la hora, considerando que el momento de inicio de aplicación del protocolo serán siempre las 00:00
	 * horas del día representado por este atributo. Las representaciones de tiempos relativos, como la propiedad
	 * {@link servando#models #protocol#ProtocolAction#GetStartGap} harán referencia a este momento inicial.
	 * 
	 * @return the startDate
	 */
	public GregorianCalendar getStartDate()
	{
		return startDate;
	}

	/**
	 * Fecha de inicio de aplicación del protocolo médico. De esta propiedad solamente se obviará la información
	 * relativa a la hora, considerando que el momento de inicio de aplicación del protocolo serán siempre las 00:00
	 * horas del día representado por este atributo. Las representaciones de tiempos relativos, como la propiedad
	 * {@link servando#models #protocol#ProtocolAction#GetStartGap} harán referencia a este momento inicial.
	 * 
	 * @param startDate the startDate to set
	 */
	public void setStartDate(GregorianCalendar startDate)
	{
		this.startDate = startDate;
	}

	public String getVersion()
	{
		return version;
	}

	public void setVersion(String version)
	{
		this.version = version;
	}

}
