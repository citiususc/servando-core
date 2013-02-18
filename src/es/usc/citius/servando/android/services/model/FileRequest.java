/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package es.usc.citius.servando.android.services.model;

import org.simpleframework.xml.Default;
import org.simpleframework.xml.DefaultType;
import org.simpleframework.xml.Element;
import org.simpleframework.xml.Root;

/**
 * Clase que representa una solicitud de transmisión de archivo remoto. Ante la recepción de un objeto de esta clase en
 * una petición CommunicableService.SendReceive, se devolverá un objeto BinaryData" con el archivo concreto.
 * 
 * @author tomas
 */
@Root(name = "FileRequest")
@Default(value = DefaultType.FIELD)
public class FileRequest {

	/**
	 * Ruta al archivo solicitado
	 */
	@Element(name = "path")
	private String path;

	public FileRequest()
	{

	}

	public FileRequest(String path)
	{
		this.path = path;
	}

	/**
	 * @return the path
	 */
	public String getPath()
	{
		return path;
	}

	/**
	 * @param path the path to set
	 */
	public void setPath(String path)
	{
		this.path = path;
	}
}
