/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package es.usc.citius.servando.android.models.MIT;

import org.simpleframework.xml.Default;
import org.simpleframework.xml.DefaultType;
import org.simpleframework.xml.Element;
import org.simpleframework.xml.Root;

/**
 * Esta clase representa una anotación en un registro MIT, según el format WFDB. Actualmente sólo se soportan
 * anotaciones estándar de 2 bytes. Para más información, consultar:
 * http://www.physionet.org/physiotools/wag/annot-5.htm
 * 
 * @author tomas
 */
@Root(name = "MITAnnotation")
@Default(DefaultType.FIELD)
public class MITAnnotation {

	/**
	 * Obtiene o establece el código de la anotación (campo A)
	 */
	@Element(name = "code")
	private short code;
	/**
	 * Número de muestra en el que comienza la anotación, a partir de la anotación anterior o del comienzo del registro.
	 */
	@Element(name = "time")
	private short time;
	/**
	 * Subtipo de código.
	 */
	@Element(name = "subtype")
	private byte subType;
	/**
	 * Indica la señal a la cual se asocia la anotación
	 */
	@Element(name = "chan")
	private byte chan;
	/**
	 * Gets or sets the num field.
	 */
	@Element(name = "num")
	private byte num;
	/**
	 * Cadena auxiliar, para ser usada a discreción del usuario
	 */
	@Element(name = "aux")
	private String aux;

	/**
	 * Obtiene o establece el código de la anotación (campo A)
	 * 
	 * @return the code
	 */
	public short getCode()
	{
		return code;
	}

	/**
	 * Obtiene o establece el código de la anotación (campo A)
	 * 
	 * @param code the code to set
	 */
	public void setCode(short code)
	{
		this.code = code;
	}

	/**
	 * Número de muestra en el que comienza la anotación, a partir de la anotación anterior o del comienzo del registro.
	 * 
	 * @return the time
	 */
	public short getTime()
	{
		return time;
	}

	/**
	 * Número de muestra en el que comienza la anotación, a partir de la anotación anterior o del comienzo del registro.
	 * 
	 * @param time the time to set
	 */
	public void setTime(short time)
	{
		this.time = time;
	}

	/**
	 * Subtipo de código.
	 * 
	 * @return the subType
	 */
	public byte getSubType()
	{
		return subType;
	}

	/**
	 * Subtipo de código.
	 * 
	 * @param subType the subType to set
	 */
	public void setSubType(byte subType)
	{
		this.subType = subType;
	}

	/**
	 * Indica la señal a la cual se asocia la anotación
	 * 
	 * @return the chan
	 */
	public byte getChan()
	{
		return chan;
	}

	/**
	 * Indica la señal a la cual se asocia la anotación
	 * 
	 * @param chan the chan to set
	 */
	public void setChan(byte chan)
	{
		this.chan = chan;
	}

	/**
	 * Gets or sets the num field.
	 * 
	 * @return the num
	 */
	public byte getNum()
	{
		return num;
	}

	/**
	 * Gets or sets the num field.
	 * 
	 * @param num the num to set
	 */
	public void setNum(byte num)
	{
		this.num = num;
	}

	/**
	 * Cadena auxiliar, para ser usada a discreción del usuario
	 * 
	 * @return the aux
	 */
	public String getAux()
	{
		return aux;
	}

	/**
	 * Cadena auxiliar, para ser usada a discreción del usuario
	 * 
	 * @param aux the aux to set
	 */
	public void setAux(String aux)
	{
		this.aux = aux;
	}
}
