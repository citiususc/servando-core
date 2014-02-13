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
 * Esta clase agrupa la información relativa a una línea dentro de un archivo de calibración en el formato MIT-BIH.
 * 
 * @author Tomás Teijeiro Campo
 */
@Root(name = "MITCalibrationLine")
@Default(DefaultType.FIELD)
public class MITCalibrationLine {

	/**
	 * Specifies the shape of the calibration pulse ('sine', 'square', or 'undefined')
	 */
	@Element(name = "type")
	private String type;
	/**
	 * A string, possibly containing embedded spaces but not tabs, taken from the signal description field of the header
	 * file entry for signals of the desired type.
	 */
	@Element(name = "description")
	private String description;
	/**
	 * Physical measurement that correspond to the low-amplitude phase of the calibration pulse
	 */
	@Element(name = "low")
	private float low;
	/**
	 * Physical measurement that correspond to the high-amplitude phase of the calibration pulse
	 */
	@Element(name = "high")
	private float high;

	/**
	 * Specifies the customary scale in physical units per centimeter
	 */
	@Element(name = "scale")
	private float scale;
	/**
	 * A string (without embedded whitespace) that specifies the physical units of the signal (e.g., 'mV', 'mmHg',
	 * 'degrees_Celsius')
	 */
	@Element(name = "units")
	private String units;

	/**
	 * Specifies the shape of the calibration pulse ('sine', 'square', or 'undefined')
	 * 
	 * @return the type
	 */
	public String getType()
	{
		return type;
	}

	/**
	 * Specifies the shape of the calibration pulse ('sine', 'square', or 'undefined')
	 * 
	 * @param type the type to set
	 */
	public void setType(String type)
	{
		if (!type.equals("sine") && !type.equals("square") && !type.equals("undefined"))
		{
			throw new IllegalArgumentException("Value should be 'sine' or 'square' or 'undefined'");
		}
		this.type = type;
	}

	/**
	 * A string, possibly containing embedded spaces but not tabs, taken from the signal description field of the header
	 * file entry for signals of the desired type.
	 * 
	 * @return the description
	 */
	public String getDescription()
	{
		return description;
	}

	/**
	 * A string, possibly containing embedded spaces but not tabs, taken from the signal description field of the header
	 * file entry for signals of the desired type.
	 * 
	 * @param description the description to set
	 */
	public void setDescription(String description)
	{
		this.description = description;
	}

	/**
	 * Physical measurement that correspond to the low-amplitude phase of the calibration pulse
	 * 
	 * @return the low
	 */
	public float getLow()
	{
		return low;
	}

	/**
	 * Physical measurement that correspond to the low-amplitude phase of the calibration pulse
	 * 
	 * @param low the low to set
	 */
	public void setLow(float low)
	{
		this.low = low;
	}

	/**
	 * Physical measurement that correspond to the high-amplitude phase of the calibration pulse
	 * 
	 * @return the high
	 */
	public float getHigh()
	{
		return high;
	}

	/**
	 * Physical measurement that correspond to the high-amplitude phase of the calibration pulse
	 * 
	 * @param high the high to set
	 */
	public void setHigh(float high)
	{
		this.high = high;
	}

	/**
	 * Specifies the customary scale in physical units per centimeter
	 * 
	 * @return the scale
	 */
	public float getScale()
	{
		return scale;
	}

	/**
	 * Specifies the customary scale in physical units per centimeter
	 * 
	 * @param scale the scale to set
	 */
	public void setScale(float scale)
	{
		this.scale = scale;
	}

	/**
	 * A string (without embedded whitespace) that specifies the physical units of the signal (e.g., 'mV', 'mmHg',
	 * 'degrees_Celsius')
	 * 
	 * @return the units
	 */
	public String getUnits()
	{
		return units;
	}

	/**
	 * A string (without embedded whitespace) that specifies the physical units of the signal (e.g., 'mV', 'mmHg',
	 * 'degrees_Celsius')
	 * 
	 * @param units the units to set
	 */
	public void setUnits(String units)
	{
		this.units = units;
	}
}
