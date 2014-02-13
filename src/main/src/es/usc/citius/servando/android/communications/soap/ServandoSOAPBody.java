/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package es.usc.citius.servando.android.communications.soap;

/**
 * Clase que representa el cuerpo de un mensaje SOAP, adaptado al modelo de comunicaciones de Servando.
 * 
 * @author Tomás Teijeiro Campo
 */
public class ServandoSOAPBody {
	/**
	 * Nombre de la clase del objeto que está incrustado en el mensaje.
	 */
	private String className;
	/**
	 * Objeto incrustado en el mensaje.
	 */
	private Object object;

	/**
	 * Nombre de la clase del objeto que está incrustado en el mensaje.
	 * 
	 * @return the className
	 */
	public String getClassName()
	{
		return className;
	}

	/**
	 * Nombre de la clase del objeto que está incrustado en el mensaje.
	 * 
	 * @param className the className to set
	 */
	public void setClassName(String className)
	{
		this.className = className;
	}

	/**
	 * Objeto incrustado en el mensaje.
	 * 
	 * @return the object
	 */
	public Object getObject()
	{
		return object;
	}

	/**
	 * Objeto incrustado en el mensaje. Se actualiza automáticamente el nombre de la clase al establecer este parámetro.
	 * 
	 * @param object the object to set
	 */
	public void setObject(Object object)
	{
		this.object = object;
		this.className = this.object.getClass().getName();
	}
}
