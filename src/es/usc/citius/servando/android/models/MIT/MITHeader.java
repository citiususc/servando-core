/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package es.usc.citius.servando.android.models.MIT;

import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.GregorianCalendar;
import java.util.List;

import org.simpleframework.xml.Default;
import org.simpleframework.xml.DefaultType;
import org.simpleframework.xml.Element;
import org.simpleframework.xml.ElementList;
import org.simpleframework.xml.Root;

import android.util.Log;

/**
 * Esta clase representa la cabecera de un registro MIT, según el formato WFDB. Para más información:
 * http://www.physionet.org/physiotools/wag/header-5.htm
 * 
 * @author Tomás Teijeiro Campo
 */
@Root(name = "MITHeader")
@Default(DefaultType.FIELD)
public class MITHeader {

	/**
	 * Nombre del registro al que se refiere esta cabecera.
	 */
	@Element(name = "recordName", required = false)
	private String recordName;
	/**
	 * Número de segmentos de esta cabecera
	 */
	@Element(name = "segments", required = false)
	private Integer segments;
	/**
	 * Frecuencia base de muestreo de las señales, en muestras por segundo. Si no existe, se toma el valor 250.
	 */
	@Element(name = "samplingFrequency", required = false)
	private Float samplingFrequency;
	/**
	 * Frecuencia del contador (en ticks por segundo). Actualmente este campo es ignorado.
	 */
	@Element(name = "counterFrequency", required = false)
	private Float counterFrequency;
	/**
	 * Valor del contador para la primera muestra. Actualmente este campo es ignorado.
	 */
	@Element(name = "baseCounterValue", required = false)
	private Float baseCounterValue;
	/**
	 * Número de muestras almacenadas por cada señal.
	 */
	@Element(name = "samplesPerSignal", required = false)
	private Long samplesPerSignal;
	/**
	 * Fecha y hora de comienzo del registro
	 */
	@Element(name = "baseTime", required = false)
	private GregorianCalendar baseTime;
	/**
	 * Especificaciones de las señales incluídas en el registro al que corresponde esta cabecera.
	 */
	@ElementList(name = "signalSpecifications", inline = true, type = MITSignalSpecification.class, required = false)
	private ArrayList<MITSignalSpecification> signalSpecifications;
	/**
	 * Cadenas con información sobre la señal.
	 */
	@ElementList(name = "infoStrings", type = String.class, inline = true, required = false)
	private List<String> infoStrings;

	/**
	 * Inicializa una nueva instancia de MITHeader.
	 */
	public MITHeader()
	{
		segments = null;
		recordName = null;
		signalSpecifications = new ArrayList<MITSignalSpecification>();
		infoStrings = new ArrayList<String>();
	}

	/**
	 * Nombre del registro al que se refiere esta cabecera.
	 * 
	 * @return the recordName
	 */
	public String getRecordName()
	{
		return recordName;
	}

	/**
	 * Nombre del registro al que se refiere esta cabecera.
	 * 
	 * @param recordName the recordName to set
	 */
	public void setRecordName(String recordName)
	{
		this.recordName = recordName;
	}

	/**
	 * Número de segmentos de esta cabecera
	 * 
	 * @return the segments
	 */
	public Integer getSegments()
	{
		return segments;
	}

	/**
	 * Número de segmentos de esta cabecera
	 * 
	 * @param segments the segments to set
	 */
	public void setSegments(Integer segments)
	{
		this.segments = segments;
	}

	/**
	 * @return Número de señales de la cabecera
	 */
	public int getSignals()
	{
		return signalSpecifications == null ? 0 : signalSpecifications.size();
	}

	/**
	 * Frecuencia base de muestreo de las señales, en muestras por segundo. Si no existe, se toma el valor 250.
	 * 
	 * @return the samplingFrequency
	 */
	public Float getSamplingFrequency()
	{
		return samplingFrequency;
	}

	/**
	 * Frecuencia base de muestreo de las señales, en muestras por segundo. Si no existe, se toma el valor 250.
	 * 
	 * @param samplingFrequency the samplingFrequency to set
	 */
	public void setSamplingFrequency(Float samplingFrequency)
	{
		this.samplingFrequency = samplingFrequency;
	}

	/**
	 * Frecuencia del contador (en ticks por segundo). Actualmente este campo es ignorado.
	 * 
	 * @return the counterFrequency
	 */
	public Float getCounterFrequency()
	{
		return counterFrequency;
	}

	/**
	 * Frecuencia del contador (en ticks por segundo). Actualmente este campo es ignorado.
	 * 
	 * @param counterFrequency the counterFrequency to set
	 */
	public void setCounterFrequency(Float counterFrequency)
	{
		this.counterFrequency = counterFrequency;
	}

	/**
	 * Valor del contador para la primera muestra. Actualmente este campo es ignorado.
	 * 
	 * @return the baseCounterValue
	 */
	public Float getBaseCounterValue()
	{
		return baseCounterValue;
	}

	/**
	 * Valor del contador para la primera muestra. Actualmente este campo es ignorado.
	 * 
	 * @param baseCounterValue the baseCounterValue to set
	 */
	public void setBaseCounterValue(Float baseCounterValue)
	{
		this.baseCounterValue = baseCounterValue;
	}

	/**
	 * Número de muestras almacenadas por cada señal.
	 * 
	 * @return the samplesPerSignal
	 */
	public Long getSamplesPerSignal()
	{
		return samplesPerSignal;
	}

	/**
	 * Número de muestras almacenadas por cada señal.
	 * 
	 * @param samplesPerSignal the samplesPerSignal to set
	 */
	public void setSamplesPerSignal(Long samplesPerSignal)
	{
		this.samplesPerSignal = samplesPerSignal;
	}

	/**
	 * Fecha y hora de comienzo del registro
	 * 
	 * @return the baseTime
	 */
	public GregorianCalendar getBaseTime()
	{
		return baseTime;
	}

	/**
	 * Fecha y hora de comienzo del registro
	 * 
	 * @param baseTime the baseTime to set
	 */
	public void setBaseTime(GregorianCalendar baseTime)
	{
		this.baseTime = baseTime;
	}

	/**
	 * Especificaciones de las señales incluídas en el registro al que corresponde esta cabecera.
	 * 
	 * @return the signalSpecifications
	 */
	public ArrayList<MITSignalSpecification> getSignalSpecifications()
	{
		return signalSpecifications;
	}

	/**
	 * Cadenas con información sobre la señal.
	 * 
	 * @return the infoStrings
	 */
	public List<String> getInfoStrings()
	{
		return infoStrings;
	}

	/**
	 * Cadenas con información sobre la señal.
	 * 
	 * @param infoStrings the infoStrings to set
	 */
	public void setInfoStrings(List<String> infoStrings)
	{
		this.infoStrings = infoStrings;
	}

	@Override
	public String toString()
	{
		String result = "";
		// Record Line
		result += recordName;
		if (segments != null)
		{
			result += "/" + segments;
		}
		result += " " + getSignals() + " ";
		if (samplingFrequency != null)
		{
			result += samplingFrequency;
			if (counterFrequency != null)
			{
				result += "/" + counterFrequency;

				if (baseCounterValue != null)
				{
					result += "(" + baseCounterValue + ")";
				}
			}
			if (samplesPerSignal != null)
			{
				SimpleDateFormat fmt = new SimpleDateFormat("HH:mm:ss dd/MM/yyyy");
				result += " " + samplesPerSignal;
				if (baseTime != null)
				{
					result += " " + fmt.format(baseTime.getTime());
				}
				result += "\r\n";
			}
		}

		// Especificación de las señales
		for (MITSignalSpecification s : signalSpecifications)
		{
			if (s.getFileName() != null)
			{
				result += s.getFileName();
			}
			result += " " + s.getFormat();
			if (s.getSamplesPerFrame() != null && s.getSamplesPerFrame() != 1)
			{
				result += "x" + s.getSamplesPerFrame();
			}
			if (s.getSkew() != null)
			{
				result += ":" + s.getSkew();
			}
			if (s.getByteOffset() != null)
			{
				result += "+" + s.getByteOffset();
			}
			if (s.getADCGain() != null)
			{
				result += " " + s.getADCGain();
				if (s.getBaseLine() != null)
				{
					result += "(" + s.getBaseLine() + ")";
				}
				if (s.getUnits() != null)
				{
					result += "/" + s.getUnits();
				}

				if (s.getADCResolution() != null)
				{

					result += " " + s.getADCResolution();

					result += " " + (s.getADCZero() != null ? s.getADCZero() : 0);

					result += " " + (s.getInitialValue() != null ? s.getInitialValue() : 0);

					result += " " + (s.getCheckSum() != null ? s.getCheckSum() : 0);

					result += " " + (s.getBlockSize() != null ? s.getBlockSize() : 0);

					if (s.getDescription() != null)
					{
						result += " " + s.getDescription();
					}

				}
			}
			result += "\r\n";
		}

		return result;
	}

	/**
	 * Analiza la cadena de texto pasada como argumento, considerándola como el texto completo separado en líneas de un
	 * archivo de cabecera ".hea". En base al contenido, se establecen las diferentes propiedades de esta instancia.
	 * <b>La cadena puede contener líneas de comentarios, que serán convenientemente ignoradas.</b>
	 * 
	 * @param HeaderString Cabecera como cadena de caracteres
	 */
	public void Parse(String HeaderString)
	{
		// Variables de control
		String s;
		boolean recordLineFound = false;
		boolean signalsParsed = false;
		int numberOfSignals = 0;
		// Separamos la cadena en líneas
		String[] lineas = HeaderString.split("\\r\\n");
		// Parseamos cada una de las líneas
		for (int i = 0; i < lineas.length; i++)
		{
			s = lineas[i];
			// Si es una línea vacía, la ignoramos
			if (s.length() == 0)
			{
				continue;
			}

			// Si aún no hemos encontrado la Record Line
			if (!recordLineFound)
			{
				if (s.startsWith("#"))
				{
					continue;
				}
				// Ya hemos encontrado la primera línea de interés, la
				// analizamos
				// La separamos por espacios en blanco
				String[] fields = s.split("\\s");
				// Comprobamos si contiene un registro multisegmento
				if (fields[0].contains("/"))
				{
					int barIndex = s.indexOf('/');
					recordName = fields[0].substring(0, barIndex - 1);
					segments = Integer.parseInt(fields[0].substring(barIndex + 1));
				} else
				{
					recordName = fields[0];
				}
				// Obtenemos el número de señales
				numberOfSignals = Integer.parseInt(fields[1]);
				// Si está especificada la frecuencia de muestreo, de contador,
				// y la base del contador, la obtenemos
				if (fields.length > 2)
				{
					s = fields[2];
					if (s.contains("/"))
					{
						int barIndex = s.indexOf('/');

						samplingFrequency = Float.parseFloat(s.substring(0, barIndex - 1));

						if (s.contains("("))
						{
							counterFrequency = Float.parseFloat(s.substring(barIndex + 1, s.indexOf('(')));
							baseCounterValue = Float.parseFloat(s.substring(s.indexOf('(') + 1, s.indexOf(')')));
						} else
						{
							counterFrequency = Float.parseFloat(s.substring(barIndex + 1));
						}
					} else
					{
						samplingFrequency = Float.parseFloat(s);
					}

					Log.d("MITHeader", "SamplingFrecuency: " + samplingFrequency);
					// Si está presente el número de muestras por señal, lo
					// obtenemos
					if (fields.length > 3)
					{
						samplesPerSignal = Long.parseLong(fields[3]);
						// Si está la fecha, la obtenemos
						if (fields.length > 4)
						{
							SimpleDateFormat fmt = new SimpleDateFormat("HH:mm:ss dd/MM/yyyy");
							baseTime = new GregorianCalendar();
							baseTime.setTime(fmt.parse(fields[4] + " " + fields[5], new ParsePosition(0)));
						}
					}
				}
				recordLineFound = true;
			}
			// Parseo de señales
			else if (!signalsParsed)
			{
				signalSpecifications = new ArrayList<MITSignalSpecification>(numberOfSignals);
				for (int j = 0; j < numberOfSignals; j++)
				{
					s = lineas[i + j];
					String[] fields = s.split("\\s");
					MITSignalSpecification spec = new MITSignalSpecification();
					spec.setFileName(fields[0]);
					if (fields[1].contains("x"))
					{
						int xIndex = fields[1].indexOf("x");
						spec.setFormat(Integer.parseInt(fields[1].substring(0, xIndex)));
						if (fields[1].contains(":"))
						{
							int pIndex = fields[1].indexOf(":");
							spec.setSamplesPerFrame(Integer.parseInt(fields[1].substring(xIndex + 1, pIndex)));
							if (fields[1].contains("+"))
							{
								int mIndex = fields[1].indexOf("+");
								spec.setSkew(Integer.parseInt(fields[1].substring(pIndex + 1, mIndex)));
								spec.setByteOffset(Long.parseLong(fields[1].substring(mIndex + 1)));
							} else
							{
								spec.setSkew(Integer.parseInt(fields[1].substring(pIndex + 1)));
							}
						} else
						{
							spec.setSamplesPerFrame(Integer.parseInt(fields[1].substring(xIndex + 1)));
						}
					} else
					{
						spec.setFormat(Integer.parseInt(fields[1]));
					}
					if (fields.length > 2)
					{
						if (fields[2].contains("("))
						{
							spec.setADCGain(Float.parseFloat(fields[2].substring(0, fields[2].indexOf("("))));
							spec.setBaseLine(Integer.parseInt(fields[2].substring(fields[2].indexOf("(") + 1, fields[2].indexOf(")"))));
							if (fields[2].contains("/"))
							{
								spec.setUnits(fields[2].substring(fields[2].indexOf("/") + 1));
							} else
							{
								spec.setUnits("mv");
							}
						} else if (fields[2].contains("/"))
						{
							spec.setADCGain(Float.parseFloat(fields[2].substring(0, fields[2].indexOf("/"))));
							spec.setUnits(fields[2].substring(fields[2].indexOf("/") + 1));
						} else
						{
							spec.setADCGain(Float.parseFloat(fields[2]));
							spec.setUnits("mv");
						}
						if (fields.length > 3)
						{
							spec.setADCResolution(Integer.parseInt(fields[3]));
							if (fields.length > 4)
							{
								spec.setADCZero(Integer.parseInt(fields[4]));
								if (fields.length > 5)
								{
									spec.setInitialValue(Integer.parseInt(fields[5]));
									if (fields.length > 6)
									{
										spec.setCheckSum(Short.parseShort(fields[6]));
										if (fields.length > 7)
										{
											spec.setBlockSize(Integer.parseInt(fields[7]));
											if (fields.length > 8)
											{
												String desc = "";
												for (int k = 8; k < fields.length; k++)
												{
													desc += fields[k] + " ";
												}
												spec.setDescription(desc);
											}
										}
									}
								}
							}
						}
					} else
					{
						spec.setADCGain(200f);
						spec.setUnits("mv");
					}
					signalSpecifications.add(spec);
				}
				signalsParsed = true;
				i += numberOfSignals;
			} // InfoStrings
			else
			{
				infoStrings = new ArrayList<String>();
				if (s.startsWith("#"))
				{
					infoStrings.add(s.substring(1));
				}
			}

		}
	}

	public void setSignalSpecifications(ArrayList<MITSignalSpecification> signalSpecifications)
	{
		this.signalSpecifications = signalSpecifications;
	}
}
