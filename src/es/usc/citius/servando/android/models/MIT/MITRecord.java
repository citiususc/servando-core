/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package es.usc.citius.servando.android.models.MIT;

import java.util.ArrayList;
import java.util.List;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.ElementList;
import org.simpleframework.xml.Root;
import org.simpleframework.xml.convert.Convert;

import es.usc.citius.servando.android.communications.model.ByteArrayWrapper;
import es.usc.citius.servando.android.xml.converters.Base64Converter;

/**
 * Clase que representa un registro MIT, agrupando todos los elementos que lo conforman.
 * 
 * @author Tomás Teijeiro Campo
 */
@Root(name = "MITRecord")
public class MITRecord {

	/**
	 * Conjunto de anotaciones de este registro.
	 */
	@ElementList(name = "annotations", type = MITAnnotation.class, inline = true, required = false, empty = true, entry = "annotation")
	private List<MITAnnotation> annotations;
	/**
	 * Cabecera del registro.
	 */
	@Element(name = "header", required = false, type = MITHeader.class)
	private MITHeader header;
	/**
	 * Valores binarios de señal de este registro.
	 */
	@Element(name = "signal", required = false)
	@Convert(Base64Converter.class)
	private ByteArrayWrapper signal;

	/**
	 * Información de calibración de las diferentes señales.
	 */
	@ElementList(name = "calibration", inline = true, type = MITCalibrationLine.class, required = false, empty = true)
	private List<MITCalibrationLine> calibration;

	public MITRecord()
	{
		annotations = new ArrayList<MITAnnotation>();
		calibration = new ArrayList<MITCalibrationLine>();
		signal = new ByteArrayWrapper();
		signal.data = new byte[0];
	}

	/**
	 * Conjunto de anotaciones de este registro.
	 * 
	 * @return the annotations
	 */

	public List<MITAnnotation> getAnnotations()
	{
		return annotations;
	}

	/**
	 * Conjunto de anotaciones de este registro.
	 * 
	 * @param annotations the annotations to set
	 */
	public void setAnnotations(ArrayList<MITAnnotation> annotations)
	{
		this.annotations = annotations;
	}

	/**
	 * Cabecera del registro.
	 * 
	 * @return the header
	 */

	public MITHeader getHeader()
	{
		return header;
	}

	/**
	 * Cabecera del registro.
	 * 
	 * @param header the header to set
	 */
	public void setHeader(MITHeader header)
	{
		this.header = header;
	}

	/**
	 * Valores binarios de señal de este registro.
	 * 
	 * @return the signal
	 */

	public byte[] getSignal()
	{
		return signal.data;
	}

	/**
	 * Valores binarios de señal de este registro.
	 * 
	 * @param signal the signal to set
	 */
	public void setSignal(byte[] signal)
	{
		this.signal.data = signal;
	}

	/**
	 * Información de calibración de las diferentes señales.
	 * 
	 * @return the calibration
	 */

	public List<MITCalibrationLine> getCalibration()
	{
		return calibration;
	}

	/**
	 * Información de calibración de las diferentes señales.
	 * 
	 * @param calibration the calibration to set
	 */
	public void setCalibration(ArrayList<MITCalibrationLine> calibration)
	{
		this.calibration = calibration;
	}
}
