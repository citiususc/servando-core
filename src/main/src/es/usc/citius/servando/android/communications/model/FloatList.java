/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package es.usc.citius.servando.android.communications.model;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.util.ArrayList;

import org.simpleframework.xml.Root;
import org.simpleframework.xml.convert.Convert;

import es.usc.citius.servando.android.xml.converters.numberListConverters.FloatListConverter;

/**
 * Esta clase sirve como clase de utilidad para enviar y recibir grandes listas de números en punto flotante.
 * 
 * @author Tomás Teijeiro Campo
 */
@Root(name = "floatList", strict = false)
@Convert(value = FloatListConverter.class)
public class FloatList {

	/**
	 * Conjunto de cadenas de caracteres para transportar metadatos.
	 */
	private ArrayList<String> metaData;
	/**
	 * Conjunto de valores de tipo float que transporta este objeto
	 */
	private float[] values;

	/**
	 * Crea una nueva instancia de FloatList de tamaño 0
	 */
	public FloatList()
	{
		this(0);
	}

	/**
	 * Crea una nueva instancia de FloatList del tamaño especificado.
	 * 
	 * @param size Tamaño de la lista
	 */
	public FloatList(int size)
	{
		metaData = new ArrayList<String>();
		values = new float[size];
	}

	/**
	 * Crea una lista de números en punto flotante a partir de su representación en binario, con 4 bytes en formato
	 * little endian por cada número.
	 * 
	 * @param binaryList
	 */
	public FloatList(byte[] binaryList)
	{
		this(binaryList, ByteOrder.LITTLE_ENDIAN);
	}

	/**
	 * Crea una lista de números en punto flotante a partir de su representación en binario, con 4 bytes por cada número
	 * en el formato indicado.
	 * 
	 * @param binaryList
	 * @param byteOrder Orden de los bytes (little-endian o big-endian)
	 */
	public FloatList(byte[] binaryList, ByteOrder byteOrder)
	{
		metaData = new ArrayList<String>();
		values = new float[binaryList.length / 4];
		ByteBuffer b = ByteBuffer.wrap(binaryList);
		b.order(byteOrder);
		b.asFloatBuffer().get(values);
	}

	/**
	 * Muestra esta lista de números enteros en una representación binaria en formato little-endian. Los metadatos se
	 * ignorarán.
	 * 
	 * @return Array de bytes con la lista de números en formato little endian.
	 */
	public byte[] toByteArray()
	{
		return toByteArray(ByteOrder.LITTLE_ENDIAN);
	}

	/**
	 * Muestra esta lista de números enteros en una representación binaria en el formato elegido. Los metadatos se
	 * ignorarán.
	 * 
	 * @param order Orden de los bytes (little-endian o big-endian)
	 * @return Array de bytes con la lista de números en el formato elegido.
	 */
	public byte[] toByteArray(ByteOrder order)
	{
		byte[] buf = new byte[4 * values.length];
		int off = 0;
		int v = 0;
		if (order == ByteOrder.LITTLE_ENDIAN)
		{
			for (float f : values)
			{
				v = Float.floatToIntBits(f);
				buf[off++] = (byte) v;
				buf[off++] = (byte) (v >> 8);
				buf[off++] = (byte) (v >> 16);
				buf[off++] = (byte) (v >> 24);
			}
		} else
		{
			for (float f : values)
			{
				v = Float.floatToIntBits(f);
				buf[off++] = (byte) (v >> 24);
				buf[off++] = (byte) (v >> 16);
				buf[off++] = (byte) (v >> 8);
				buf[off++] = (byte) v;
			}
		}
		return buf;
	}

	/**
	 * Conjunto de cadenas de caracteres para transportar metadatos.
	 * 
	 * @return the metaData
	 */
	public ArrayList<String> getMetaData()
	{
		return metaData;
	}

	/**
	 * Conjunto de cadenas de caracteres para transportar.
	 * 
	 * @param metaData the metaData to set
	 */
	public void setMetaData(ArrayList<String> metaData)
	{
		this.metaData = metaData;
	}

	/**
	 * Conjunto de valores de tipo float que transporta este objeto
	 * 
	 * @return the values
	 */
	public float[] getValues()
	{
		return values;
	}

	/**
	 * Conjunto de valores de tipo float que transporta este objeto
	 * 
	 * @param values the values to set
	 */
	public void setValues(float[] values)
	{
		this.values = values;
	}

}
