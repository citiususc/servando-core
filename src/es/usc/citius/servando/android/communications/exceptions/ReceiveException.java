/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package es.usc.citius.servando.android.communications.exceptions;

/**
 * 
 * @author tomas
 */
public class ReceiveException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Creates a new instance of <code>ReceiveException</code> without detail message.
	 */
	public ReceiveException()
	{
	}

	/**
	 * Constructs an instance of <code>ReceiveException</code> with the specified detail message.
	 * 
	 * @param msg the detail message.
	 */
	public ReceiveException(String msg)
	{
		super(msg);
	}

	public ReceiveException(String msg, Throwable cause)
	{
		super(msg, cause);
	}

	public ReceiveException(Throwable cause)
	{
		super(cause);
	}
}
