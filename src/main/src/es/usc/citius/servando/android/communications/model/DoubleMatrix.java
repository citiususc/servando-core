/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package es.usc.citius.servando.android.communications.model;

import java.util.ArrayList;

import org.simpleframework.xml.ElementList;
import org.simpleframework.xml.Root;
import org.simpleframework.xml.convert.Convert;

import es.usc.citius.servando.android.xml.converters.numberListConverters.DoubleListConverter;

/**
 * Esta clase sirve como clase de utilidad para enviar y recibir "matrices" de números double.
 * 
 * @author Tomás Teijeiro Campo
 */
@Root(name = "doubleMatrix")
public class DoubleMatrix {

	/**
	 * Conjunto de cadenas de caracteres para transportar metadatos.
	 */
	@ElementList(entry = "metaData", inline = true, required = false)
	private ArrayList<String> metaData;
	/**
	 * Conjunto de filas de la matriz que transporta este objeto
	 */
	@ElementList(entry = "row", inline = true)
	@Convert(DoubleListConverter.class)
	private ArrayList<DoubleList> rows;

	/**
	 * Crea una nueva instancia de DoubleMatrix de tamaño 0x0
	 */
	public DoubleMatrix()
	{
	}

	/**
	 * Crea una nueva instancia de DoubleMatrix del tamaño especificado
	 * 
	 * @param rows
	 * @param columns
	 */
	public DoubleMatrix(int rows, int columns)
	{
		metaData = new ArrayList<String>();
		this.rows = new ArrayList<DoubleList>(rows);
		for (int i = 0; i < rows; i++)
		{
			this.rows.add(i, new DoubleList(columns));
		}
	}

	/**
	 * @return Número de filas de la matriz
	 */
	public int getRowCount()
	{
		return rows.size();
	}

	/**
	 * 
	 * @return Número de columnas de la matriz
	 */
	public int getColumnCount()
	{
		if (rows != null && rows.size() > 0)
		{
			return rows.get(0).getValues().length;
		}
		return 0;
	}

	/**
	 * Obtiene el elemento situado en la coordenada especificada.
	 * 
	 * @param row Fila
	 * @param column columna
	 * @return Valor en la coordenada (fila, columna)
	 */
	public double getElementAt(int row, int column)
	{
		return rows.get(row).getValues()[column];
	}

	/**
	 * Establece el elemento situado en la coordenada especificada
	 * 
	 * @param row fila
	 * @param column columna
	 * @param value Valor a establecer en la coordenada (fila,columna)
	 */
	public void setElementAt(int row, int column, double value)
	{
		rows.get(row).getValues()[column] = value;
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
	 * Conjunto de filas de la matriz que transporta este objeto
	 * 
	 * @return the rows
	 */
	public ArrayList<DoubleList> getRows()
	{
		return rows;
	}

	/**
	 * Conjunto de filas de la matriz que transporta este objeto
	 * 
	 * @param rows the rows to set
	 */
	public void setRows(ArrayList<DoubleList> rows)
	{
		this.rows = rows;
	}
}
