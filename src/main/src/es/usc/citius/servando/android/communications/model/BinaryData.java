/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package es.usc.citius.servando.android.communications.model;

import java.util.ArrayList;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.ElementList;
import org.simpleframework.xml.Root;
import org.simpleframework.xml.convert.Convert;

import es.usc.citius.servando.android.xml.converters.Base64Converter;

/**
 * Esta clase sirve como clase de utilidad para enviar datos binarios. Puede ser utilizada por los servicios para enviar
 * mensajes binarios.
 * 
 * @author Tomás Teijeiro Campo
 */
@Root(name = "binaryData")
public class BinaryData {

	/**
	 * Conjunto de cadenas de caracteres para transportar metadatos acerca de los datos binarios.
	 */
	@ElementList(entry = "metaData", inline = true, required = false)
	private ArrayList<String> metaData;
	/**
	 * Datos binarios que transporta este objeto.
	 */
	@Element(name = "data")
	@Convert(Base64Converter.class)
	private ByteArrayWrapper data;

	public BinaryData()
	{
		metaData = new ArrayList<String>();
		data = new ByteArrayWrapper();
	}

	/**
	 * Conjunto de cadenas de caracteres para transportar metadatos acerca de los datos binarios.
	 * 
	 * @return the metaData
	 */
	public ArrayList<String> getMetaData()
	{
		return metaData;
	}

	/**
	 * Conjunto de cadenas de caracteres para transportar metadatos acerca de los datos binarios.
	 * 
	 * @param metaData the metaData to set
	 */
	public void setMetaData(ArrayList<String> metaData)
	{
		this.metaData = metaData;
	}

	/**
	 * Datos binarios que transporta este objeto.
	 * 
	 * @return the data
	 */
	public byte[] getData()
	{
		return data.data;
	}

	/**
	 * Datos binarios que transporta este objeto.
	 * 
	 * @param data the data to set
	 */
	public void setData(byte[] data)
	{
		this.data.data = data;
	}

}
