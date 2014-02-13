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
 * Clase que representa un fragmento de un archivo. Se enviarán objetos de esta clase como respuestas asíncronas a la
 * recepción de mensajes del tipo FileRequest cuando el archivo no quepa en un único mensaje.
 * 
 * @author Tomás Teijeiro Campo
 */
@Root(name = "FileFragment")
@Default(value = DefaultType.FIELD)
public class FileFragment {

	/**
	 * Ruta en el origen del archivo del cual se está enviando una parte.
	 */
	@Element(name = "path")
	private String path;
	/**
	 * Índice del fragmento enviado, comenzando en 0.
	 */
	@Element(name = "fragmentIndex")
	private int fragmentIndex;
	/**
	 * Información del fragmento del archivo enviado.
	 */
	@Element(name = "data")
	private byte[] data;
	/**
	 * Bandera que indica que este fragmento es el último del archivo.
	 */
	@Element(name = "isLast")
	private boolean isLast;

	public FileFragment()
	{
		fragmentIndex = 0;
		isLast = false;
	}

	/**
	 * Ruta en el origen del archivo del cual se está enviando una parte.
	 * 
	 * @return the path
	 */
	public String getPath()
	{
		return path;
	}

	/**
	 * Ruta en el origen del archivo del cual se está enviando una parte.
	 * 
	 * @param path the path to set
	 */
	public void setPath(String path)
	{
		this.path = path;
	}

	/**
	 * Índice del fragmento enviado, comenzando en 0.
	 * 
	 * @return the fragmentIndex
	 */
	public int getFragmentIndex()
	{
		return fragmentIndex;
	}

	/**
	 * Índice del fragmento enviado, comenzando en 0.
	 * 
	 * @param fragmentIndex the fragmentIndex to set
	 */
	public void setFragmentIndex(int fragmentIndex)
	{
		this.fragmentIndex = fragmentIndex;
	}

	/**
	 * Información del fragmento del archivo enviado.
	 * 
	 * @return the data
	 */
	public byte[] getData()
	{
		return data;
	}

	/**
	 * Información del fragmento del archivo enviado.
	 * 
	 * @param data the data to set
	 */
	public void setData(byte[] data)
	{
		this.data = data;
	}

	/**
	 * Bandera que indica que este fragmento es el último del archivo.
	 * 
	 * @return the isLast
	 */
	public boolean isLast()
	{
		return isLast;
	}

	/**
	 * Bandera que indica que este fragmento es el último del archivo.
	 * 
	 * @param isLast the isLast to set
	 */
	public void setLast(boolean isLast)
	{
		this.isLast = isLast;
	}

}
