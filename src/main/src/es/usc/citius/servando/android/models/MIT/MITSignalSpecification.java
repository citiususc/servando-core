/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package es.usc.citius.servando.android.models.MIT;

import java.io.Serializable;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.Root;

/**
 * Especificación de una señal incluída en un registro. Esta información irá en la cabecera de los registros. Para más
 * información: http://www.physionet.org/physiotools/wag/header-5.htm
 * 
 * @author Tomás Teijeiro
 */
@Root(name = "MITSignalSpecification")
public class MITSignalSpecification implements Serializable {

	private static final long serialVersionUID = 1L;

	@Element(name = "fileName", required = false)
	private String fileName;
	@Element(name = "format", required = false)
	private int format;
	@Element(name = "samplesPerFrame", required = false)
	private Integer samplesPerFrame;
	@Element(name = "skew", required = false)
	private Integer skew;
	@Element(name = "byteOffset", required = false)
	private Long byteOffset;
	@Element(name = "ADCGain", required = false)
	private Float ADCGain;
	@Element(name = "baseLine", required = false)
	private Integer baseLine;
	@Element(name = "units", required = false)
	private String units;
	@Element(name = "ADCResolution", required = false)
	private Integer ADCResolution;
	@Element(name = "ADCZero", required = false)
	private Integer ADCZero;
	@Element(name = "initialValue", required = false)
	private Integer initialValue;
	@Element(name = "checkSum", required = false)
	private Short checkSum;
	@Element(name = "blockSize", required = false)
	private Integer blockSize;
	@Element(name = "description", required = false)
	private String description;
	@Element(name = "sampleFrequency", required = false)
	private short sampleFrequency = 250;

	private boolean isUsed = true;

	// new MITSignalSpecification(16, 250f, 95.057f, "mV", 12, "I"));
	public MITSignalSpecification(int format, short sampleFrequency, float gain, String units, int resolution, String description)
	{
		this.format = format;
		this.sampleFrequency = sampleFrequency;
		this.ADCGain = gain;
		this.units = units;
		this.ADCResolution = resolution;
		this.description = description;
		this.isUsed = true;
	}

	public MITSignalSpecification()
	{
		this.isUsed = true;
	}

	/**
	 * Nombre del archivo donde se guarda la señal.
	 * 
	 * @return the fileName
	 */
	public String getFileName()
	{
		return fileName;
	}

	/**
	 * Nombre del archivo donde se guarda la señal.
	 * 
	 * @param fileName the fileName to set
	 */
	public void setFileName(String fileName)
	{
		this.fileName = fileName;
	}

	/**
	 * Formato de los datos.
	 * 
	 * @return the format
	 */
	public int getFormat()
	{
		return format;
	}

	/**
	 * Formato de los datos.
	 * 
	 * @param format the format to set
	 */
	public void setFormat(int format)
	{
		this.format = format;
	}

	/**
	 * Número de muestras de esta señal que contiene cada Frame. Cada frame tiene la duración indicada por la frecuencia
	 * de muestreo en la cabecera correspondiente, por lo que este campo se utiliza para indicar frecuencias superiores
	 * a la básica. Por ejemplo, si la cabecera indica una frecuencia base de 250Hz y la señal tiene este campo con
	 * valor 2, la frecuencia real de muestreo de esta señal serían 500Hz.
	 * 
	 * @return the samplesPerFrame
	 */
	public Integer getSamplesPerFrame()
	{
		return samplesPerFrame;
	}

	/**
	 * Número de muestras de esta señal que contiene cada Frame. Cada frame tiene la duración indicada por la frecuencia
	 * de muestreo en la cabecera correspondiente, por lo que este campo se utiliza para indicar frecuencias superiores
	 * a la básica. Por ejemplo, si la cabecera indica una frecuencia base de 250Hz y la señal tiene este campo con
	 * valor 2, la frecuencia real de muestreo de esta señal serían 500Hz.
	 * 
	 * @param samplesPerFrame the samplesPerFrame to set
	 */
	public void setSamplesPerFrame(Integer samplesPerFrame)
	{
		this.samplesPerFrame = samplesPerFrame;
	}

	/**
	 * Este campo puede ser utilizado para corregir desviaciones entre muestras que deberían ser simultáneas de
	 * diferentes señales capturadas con la misma frecuencia.
	 * 
	 * @return the skew
	 */
	public Integer getSkew()
	{
		return skew;
	}

	/**
	 * Este campo puede ser utilizado para corregir desviaciones entre muestras que deberían ser simultáneas de
	 * diferentes señales capturadas con la misma frecuencia.
	 * 
	 * @param skew the skew to set
	 */
	public void setSkew(Integer skew)
	{
		this.skew = skew;
	}

	/**
	 * Offset en bytes que indica donde comienza realmente la señal dentro del archivo.
	 * 
	 * @return the byteOffset
	 */
	public Long getByteOffset()
	{
		return byteOffset;
	}

	/**
	 * Offset en bytes que indica donde comienza realmente la señal dentro del archivo.
	 * 
	 * @param byteOffset the byteOffset to set
	 */
	public void setByteOffset(Long byteOffset)
	{
		this.byteOffset = byteOffset;
	}

	/**
	 * Ganancia en unidades de la conversión analógico-digital. Unidades ADC por cada unidad física.
	 * 
	 * @return the ADCGain
	 */
	public Float getADCGain()
	{
		return ADCGain;
	}

	/**
	 * Ganancia en unidades de la conversión analógico-digital. Unidades ADC por cada unidad física.
	 * 
	 * @param ADCGain the ADCGain to set
	 */
	public void setADCGain(Float ADCGain)
	{
		this.ADCGain = ADCGain;
	}

	/**
	 * Este campo sólo estará presente si ADCGain está presente, e indica el valor ADC correspondiente al valor 0 de la
	 * magnitud física.
	 * 
	 * @return the baseLine
	 */
	public Integer getBaseLine()
	{
		return baseLine;
	}

	/**
	 * Este campo sólo estará presente si ADCGain está presente, e indica el valor ADC correspondiente al valor 0 de la
	 * magnitud física.
	 * 
	 * @param baseLine the baseLine to set
	 */
	public void setBaseLine(Integer baseLine)
	{
		this.baseLine = baseLine;
	}

	/**
	 * Unidades de la magnitud física.
	 * 
	 * @return the units
	 */
	public String getUnits()
	{
		return units;
	}

	/**
	 * Unidades de la magnitud física.
	 * 
	 * @param units the units to set
	 */
	public void setUnits(String units)
	{
		this.units = units;
	}

	/**
	 * Resolución del conversor AD, en número de bits. Si este campo no está presente, se considerarán 12 bits para los
	 * campos basados en amplitud y 10 bits para los campos basados en diferencia.
	 * 
	 * @return the ADCResolution
	 */
	public Integer getADCResolution()
	{
		return ADCResolution;
	}

	/**
	 * Resolución del conversor AD, en número de bits. Si este campo no está presente, se considerarán 12 bits para los
	 * campos basados en amplitud y 10 bits para los campos basados en diferencia.
	 * 
	 * @param ADCResolution the ADCResolution to set
	 */
	public void setADCResolution(Integer ADCResolution)
	{
		this.ADCResolution = ADCResolution;
	}

	/**
	 * Este campo indica el valor medio del conversor ADC. Si es un conversor bipolar, usualmente será 0, mientras que
	 * si es monopolar, corresponderá con el valor medio de la escala.
	 * 
	 * @return the ADCZero
	 */
	public Integer getADCZero()
	{
		return ADCZero;
	}

	/**
	 * Este campo indica el valor medio del conversor ADC. Si es un conversor bipolar, usualmente será 0, mientras que
	 * si es monopolar, corresponderá con el valor medio de la escala.
	 * 
	 * @param ADCZero the ADCZero to set
	 */
	public void setADCZero(Integer ADCZero)
	{
		this.ADCZero = ADCZero;
	}

	/**
	 * Indica el valor de la primera muestra, al trabajar con formatos basados en diferencias.
	 * 
	 * @return the initialValue
	 */
	public Integer getInitialValue()
	{
		return initialValue;
	}

	/**
	 * Indica el valor de la primera muestra, al trabajar con formatos basados en diferencias.
	 * 
	 * @param initialValue the initialValue to set
	 */
	public void setInitialValue(Integer initialValue)
	{
		this.initialValue = initialValue;
	}

	/**
	 * Checksum obtenido de la suma de todas las muestras de la señal.
	 * 
	 * @return the checkSum
	 */
	public Short getCheckSum()
	{
		return checkSum;
	}

	/**
	 * Checksum obtenido de la suma de todas las muestras de la señal.
	 * 
	 * @param checkSum the checkSum to set
	 */
	public void setCheckSum(Short checkSum)
	{
		this.checkSum = checkSum;
	}

	/**
	 * Tamaño de bloque utilizado para lectura. Normalmente este campo no se usa, y está pensado para operaciones tipo
	 * fseek.
	 * 
	 * @return the blockSize
	 */
	public Integer getBlockSize()
	{
		return blockSize;
	}

	/**
	 * Tamaño de bloque utilizado para lectura. Normalmente este campo no se usa, y está pensado para operaciones tipo
	 * fseek.
	 * 
	 * @param blockSize the blockSize to set
	 */
	public void setBlockSize(Integer blockSize)
	{
		this.blockSize = blockSize;
	}

	/**
	 * Descripción de la señal. Utilizado por ejemplo para representar el canal de datos utilizado.
	 * 
	 * @return the description
	 */
	public String getDescription()
	{
		return description;
	}

	/**
	 * Descripción de la señal. Utilizado por ejemplo para representar el canal de datos utilizado.
	 * 
	 * @param description the description to set
	 */
	public void setDescription(String description)
	{
		this.description = description;
	}

	public short getSampleFrequency()
	{
		return sampleFrequency;
	}

	public void setSampleFrequency(short sampleFrequency)
	{
		this.sampleFrequency = sampleFrequency;
	}

	public boolean isUsed()
	{
		return isUsed;
	}

	public void setUsed(boolean isUsed)
	{
		this.isUsed = isUsed;
	}

	@Override
	public String toString()
	{
		StringBuilder builder = new StringBuilder();
		builder.append("MITSignalSpecification [fileName=");
		builder.append(fileName);
		builder.append(", format=");
		builder.append(format);
		builder.append(", samplesPerFrame=");
		builder.append(samplesPerFrame);
		builder.append(", skew=");
		builder.append(skew);
		builder.append(", byteOffset=");
		builder.append(byteOffset);
		builder.append(", ADCGain=");
		builder.append(ADCGain);
		builder.append(", baseLine=");
		builder.append(baseLine);
		builder.append(", units=");
		builder.append(units);
		builder.append(", ADCResolution=");
		builder.append(ADCResolution);
		builder.append(", ADCZero=");
		builder.append(ADCZero);
		builder.append(", initialValue=");
		builder.append(initialValue);
		builder.append(", checkSum=");
		builder.append(checkSum);
		builder.append(", blockSize=");
		builder.append(blockSize);
		builder.append(", description=");
		builder.append(description);
		builder.append(", sampleFrequency=");
		builder.append(sampleFrequency);
		builder.append(", isUsed=");
		builder.append(isUsed);
		builder.append("]");
		return builder.toString();
	}

}
