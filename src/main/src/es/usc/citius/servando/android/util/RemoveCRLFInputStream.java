/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package es.usc.citius.servando.android.util;

import java.io.IOException;
import java.io.InputStream;

/**
 * Decorador que elimina de la entrada de otro InputStream los caracteres \r y \n. Implementa un patrón decorador.
 * 
 * @author Tomás Teijeiro Campo
 */
public class RemoveCRLFInputStream extends InputStream {

	/**
	 * InputStream decorado.
	 */
	private InputStream subject;

	/**
	 * Byte que leeremos
	 */
	private int b;

	/**
	 * Crea una nueva instancia de este InputStream decorador, utilizando el argumento como objeto decorado.
	 * 
	 * @param subject InputStream que decoraremos eliminando los caracteres \r y \n.
	 */
	public RemoveCRLFInputStream(InputStream subject)
	{
		if (subject == null)
		{
			throw new IllegalArgumentException("Subject must not be null");
		}
		this.subject = subject;
	}

	@Override
	public int read() throws IOException
	{
		do
		{
			b = subject.read();
		} while (b == 10 || b == 13);
		return b;

	}
}
