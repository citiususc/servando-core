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

import es.usc.citius.servando.android.xml.converters.numberListConverters.DoubleListConverter;

/**
 * Esta clase sirve como clase de utilidad para enviar y recibir grandes listas de números double.
 * 
 * @author Tomás Teijeiro Campo
 */
@Root(name = "doubleList", strict = false)
@Convert(value = DoubleListConverter.class)
public class DoubleList {

	/**
	 * Conjunto de cadenas de caracteres para transportar metadatos.
	 */
	private ArrayList<String> metaData;
	/**
	 * Conjunto de valores de tipo double que transporta este objeto
	 */
	private double[] values;

	public DoubleList()
	{
		this(0);
	}

	/**
	 * Crea una nueva lista de double del tamaño especificado.
	 * 
	 * @param size
	 */
	public DoubleList(int size)
	{
		metaData = new ArrayList<String>();
		values = new double[size];
	}

	/**
	 * Crea una lista de double a partir de su representación en binario, con 8 bytes en formato little endian por cada
	 * número.
	 * 
	 * @param binaryList
	 */
	public DoubleList(byte[] binaryList)
	{
		this(binaryList, ByteOrder.LITTLE_ENDIAN);
	}

	/**
	 * Crea una lista de double a partir de su representación en binario, con 8 bytes por cada número en el formato
	 * indicado.
	 * 
	 * @param binaryList
	 * @param byteOrder Formato utilizado en la codificación binaria (little-endian o big-endian)
	 */
	public DoubleList(byte[] binaryList, ByteOrder byteOrder)
	{
		metaData = new ArrayList<String>();
		values = new double[binaryList.length / 8];
		ByteBuffer b = ByteBuffer.wrap(binaryList);
		b.order(byteOrder);
		b.asDoubleBuffer().get(values);
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
	 * Muestra esta lista de números double en una representación binaria en el formato elegido. Los metadatos se
	 * ignorarán.
	 * 
	 * @param order Orden de los bytes (little-endian o big-endian)
	 * @return Array de bytes con la lista de números en el formato elegido.
	 */
	public byte[] toByteArray(ByteOrder order)
	{
		byte[] buf = new byte[8 * values.length];
		int off = 0;
		long v = 0;
		if (order == ByteOrder.LITTLE_ENDIAN)
		{
			for (double d : values)
			{
				v = Double.doubleToLongBits(d);
				buf[off++] = (byte) v;
				buf[off++] = (byte) (v >> 8);
				buf[off++] = (byte) (v >> 16);
				buf[off++] = (byte) (v >> 24);
				buf[off++] = (byte) (v >> 32);
				buf[off++] = (byte) (v >> 40);
				buf[off++] = (byte) (v >> 48);
				buf[off++] = (byte) (v >> 56);
			}
		} else
		{
			for (double d : values)
			{
				v = Double.doubleToLongBits(d);
				buf[off++] = (byte) (v >> 56);
				buf[off++] = (byte) (v >> 48);
				buf[off++] = (byte) (v >> 40);
				buf[off++] = (byte) (v >> 32);
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
	 * Conjunto de valores de tipo short que transporta este objeto
	 * 
	 * @return the values
	 */
	public double[] getValues()
	{
		return values;
	}

	/**
	 * Conjunto de valores de tipo short que transporta este objeto
	 * 
	 * @param values the values to set
	 */
	public void setValues(double[] values)
	{
		this.values = values;
	}
}
