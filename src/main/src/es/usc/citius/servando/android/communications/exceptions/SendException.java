/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package es.usc.citius.servando.android.communications.exceptions;

/**
 * Excepción que indica que se ha producido un error en el envío de una petición de tipo SendReceive.
 * 
 * @author Tomás Teijeiro Campo
 */
public class SendException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public SendException()
	{
		super();
	}

	public SendException(String message)
	{
		super(message);
	}

	public SendException(String message, Throwable cause)
	{
		super(message, cause);
	}

	public SendException(Throwable cause)
	{
		super(cause);
	}

}
