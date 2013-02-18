/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package es.usc.citius.servando.android.models.protocol.medicalActionResults.valoracionHistoriaClinica;

import org.simpleframework.xml.Default;
import org.simpleframework.xml.DefaultType;
import org.simpleframework.xml.Element;

/**
 * Esta clase guarda los resultados de una exploración física.
 */
@Default(DefaultType.FIELD)
public class ExploracionFisicaResult {

	@Element(name = "carotidasLatenSimetricas")
	private boolean carotidasLatenSimetricas;
	@Element(name = "ingurgitacionYugular")
	private boolean ingurgitacionYugular;
	@Element(name = "adenopatias")
	private boolean adenopatias;
	@Element(name = "inspeccionNormal")
	private boolean inspeccionNormal;
	@Element(name = "inspeccionAnormalDetalle")
	private String inspeccionAnormalDetalle;
	@Element(name = "palpacionNormal")
	private boolean palpacionNormal;
	@Element(name = "palpacionAnormalDetalle")
	private String palpacionAnormalDetalle;
	@Element(name = "percusionNormal")
	private boolean percusionNormal;
	@Element(name = "percusionAnormalDetalle")
	private String percusionAnormalDetalle;
	@Element(name = "auscultacionNormal")
	private boolean auscultacionNormal;
	@Element(name = "auscultacionAnormalDetalle")
	private String auscultacionAnormalDetalle;
	@Element(name = "auscultacionCardiacaNormal")
	private boolean auscultacionCardiacaNormal;
	@Element(name = "haySoplos")
	private boolean haySoplos;
	@Element(name = "localizacionSoplo")
	private int localizacionSoplo;
	@Element(name = "tipoSoplo")
	private int tipoSoplo;
	@Element(name = "hayEdemas")
	private boolean hayEdemas;
	@Element(name = "hayAcropaquias")
	private boolean hayAcropaquias;
	@Element(name = "hayPulsosConservados")
	private boolean hayPulsosConservados;

	/**
	 * Indica si las carótidas laten simétricas
	 * 
	 * @return the carotidasLatenSimetricas
	 */
	public boolean CarotidasLatenSimetricas()
	{
		return carotidasLatenSimetricas;
	}

	/**
	 * Indica si las carótidas laten simétricas
	 * 
	 * @param carotidasLatenSimetricas the carotidasLatenSimetricas to set
	 */
	public void setCarotidasLatenSimetricas(boolean carotidasLatenSimetricas)
	{
		this.carotidasLatenSimetricas = carotidasLatenSimetricas;
	}

	/**
	 * Indica si se detecta ingurgitación yugular a 45º
	 * 
	 * @return the ingurgitacionYugular
	 */
	public boolean IngurgitacionYugular()
	{
		return ingurgitacionYugular;
	}

	/**
	 * Indica si se detecta ingurgitación yugular a 45º
	 * 
	 * @param ingurgitacionYugular the ingurgitacionYugular to set
	 */
	public void setIngurgitacionYugular(boolean ingurgitacionYugular)
	{
		this.ingurgitacionYugular = ingurgitacionYugular;
	}

	/**
	 * Indica se hay adenopatías
	 * 
	 * @return the adenopatias
	 */
	public boolean Adenopatias()
	{
		return adenopatias;
	}

	/**
	 * Indica se hay adenopatías
	 * 
	 * @param adenopatias the adenopatias to set
	 */
	public void setAdenopatias(boolean adenopatias)
	{
		this.adenopatias = adenopatias;
	}

	/**
	 * Indica si la inspección física fue normal.
	 * 
	 * @return the inspeccionNormal
	 */
	public boolean InspeccionNormal()
	{
		return inspeccionNormal;
	}

	/**
	 * Indica si la inspección física fue normal.
	 * 
	 * @param inspeccionNormal the inspeccionNormal to set
	 */
	public void setInspeccionNormal(boolean inspeccionNormal)
	{
		this.inspeccionNormal = inspeccionNormal;
	}

	/**
	 * Explicación de por qué la inspección física no es normal.
	 * 
	 * @return the inspeccionAnormalDetalle
	 */
	public String getInspeccionAnormalDetalle()
	{
		return inspeccionAnormalDetalle;
	}

	/**
	 * Explicación de por qué la inspección física no es normal.
	 * 
	 * @param inspeccionAnormalDetalle the inspeccionAnormalDetalle to set
	 */
	public void setInspeccionAnormalDetalle(String inspeccionAnormalDetalle)
	{
		this.inspeccionAnormalDetalle = inspeccionAnormalDetalle;
	}

	/**
	 * Indica si la palpación fue normal o no
	 * 
	 * @return the palpacionNormal
	 */
	public boolean PalpacionNormal()
	{
		return palpacionNormal;
	}

	/**
	 * Indica si la palpación fue normal o no
	 * 
	 * @param palpacionNormal the palpacionNormal to set
	 */
	public void setPalpacionNormal(boolean palpacionNormal)
	{
		this.palpacionNormal = palpacionNormal;
	}

	/**
	 * Explicación de por qué la palpación no es normal.
	 * 
	 * @return the palpacionAnormalDetalle
	 */
	public String getPalpacionAnormalDetalle()
	{
		return palpacionAnormalDetalle;
	}

	/**
	 * Explicación de por qué la palpación no es normal.
	 * 
	 * @param palpacionAnormalDetalle the palpacionAnormalDetalle to set
	 */
	public void setPalpacionAnormalDetalle(String palpacionAnormalDetalle)
	{
		this.palpacionAnormalDetalle = palpacionAnormalDetalle;
	}

	/**
	 * Indica si la percusión es normal
	 * 
	 * @return the percusionNormal
	 */
	public boolean PercusionNormal()
	{
		return percusionNormal;
	}

	/**
	 * Indica si la percusión es normal
	 * 
	 * @param percusionNormal the percusionNormal to set
	 */
	public void setPercusionNormal(boolean percusionNormal)
	{
		this.percusionNormal = percusionNormal;
	}

	/**
	 * Explicación de por qué la percusión no es normal.
	 * 
	 * @return the percusionAnormalDetalle
	 */
	public String getPercusionAnormalDetalle()
	{
		return percusionAnormalDetalle;
	}

	/**
	 * Explicación de por qué la percusión no es normal.
	 * 
	 * @param percusionAnormalDetalle the percusionAnormalDetalle to set
	 */
	public void setPercusionAnormalDetalle(String percusionAnormalDetalle)
	{
		this.percusionAnormalDetalle = percusionAnormalDetalle;
	}

	/**
	 * Indica si la auscultación fue normal
	 * 
	 * @return the auscultacionNormal
	 */
	public boolean AuscultacionNormal()
	{
		return auscultacionNormal;
	}

	/**
	 * Indica si la auscultación fue normal
	 * 
	 * @param auscultacionNormal the auscultacionNormal to set
	 */
	public void setAuscultacionNormal(boolean auscultacionNormal)
	{
		this.auscultacionNormal = auscultacionNormal;
	}

	/**
	 * Explicación de por qué la auscultacion no es normal.
	 * 
	 * @return the auscultacionAnormalDetalle
	 */
	public String getAuscultacionAnormalDetalle()
	{
		return auscultacionAnormalDetalle;
	}

	/**
	 * Explicación de por qué la auscultacion no es normal.
	 * 
	 * @param auscultacionAnormalDetalle the auscultacionAnormalDetalle to set
	 */
	public void setAuscultacionAnormalDetalle(String auscultacionAnormalDetalle)
	{
		this.auscultacionAnormalDetalle = auscultacionAnormalDetalle;
	}

	/**
	 * Indica si la auscultación cardiaca fue normal
	 * 
	 * @return the auscultacionCardiacaNormal
	 */
	public boolean AuscultacionCardiacaNormal()
	{
		return auscultacionCardiacaNormal;
	}

	/**
	 * Indica si la auscultación cardiaca fue normal
	 * 
	 * @param auscultacionCardiacaNormal the auscultacionCardiacaNormal to set
	 */
	public void setAuscultacionCardiacaNormal(boolean auscultacionCardiacaNormal)
	{
		this.auscultacionCardiacaNormal = auscultacionCardiacaNormal;
	}

	/**
	 * Indica si se detectaron soplos
	 * 
	 * @return the haySoplos
	 */
	public boolean HaySoplos()
	{
		return haySoplos;
	}

	/**
	 * Indica si se detectaron soplos
	 * 
	 * @param haySoplos the haySoplos to set
	 */
	public void setHaySoplos(boolean haySoplos)
	{
		this.haySoplos = haySoplos;
	}

	/**
	 * Localización del soplo detectado (0 = mitral, 1 = aórteo, 2 = tricúspideo o 3 = pulmonar)
	 * 
	 * @return the localizacionSoplo
	 */
	public int getLocalizacionSoplo()
	{
		return localizacionSoplo;
	}

	/**
	 * Localización del soplo detectado (0 = mitral, 1 = aórteo, 2 = tricúspideo o 3 = pulmonar)
	 * 
	 * @param localizacionSoplo the localizacionSoplo to set
	 */
	public void setLocalizacionSoplo(int localizacionSoplo)
	{
		this.localizacionSoplo = localizacionSoplo;
	}

	/**
	 * Tipo del soplo detectado (0 = Sistólico o 1 = diastólico)
	 * 
	 * @return the tipoSoplo
	 */
	public int getTipoSoplo()
	{
		return tipoSoplo;
	}

	/**
	 * Tipo del soplo detectado (0 = Sistólico o 1 = diastólico)
	 * 
	 * @param tipoSoplo the tipoSoplo to set
	 */
	public void setTipoSoplo(int tipoSoplo)
	{
		this.tipoSoplo = tipoSoplo;
	}

	/**
	 * Indica si se detectaron edemas
	 * 
	 * @return the hayEdemas
	 */
	public boolean HayEdemas()
	{
		return hayEdemas;
	}

	/**
	 * Indica si se detectaron edemas
	 * 
	 * @param hayEdemas the hayEdemas to set
	 */
	public void setHayEdemas(boolean hayEdemas)
	{
		this.hayEdemas = hayEdemas;
	}

	/**
	 * Indica si se detectaron acropaquias
	 * 
	 * @return the hayAcropaquias
	 */
	public boolean HayAcropaquias()
	{
		return hayAcropaquias;
	}

	/**
	 * Indica si se detectaron acropaquias
	 * 
	 * @param hayAcropaquias the hayAcropaquias to set
	 */
	public void setHayAcropaquias(boolean hayAcropaquias)
	{
		this.hayAcropaquias = hayAcropaquias;
	}

	/**
	 * Indica si se detectaron pulsos conservados
	 * 
	 * @return the hayPulsosConservados
	 */
	public boolean HayPulsosConservados()
	{
		return hayPulsosConservados;
	}

	/**
	 * Indica si se detectaron pulsos conservados
	 * 
	 * @param hayPulsosConservados the hayPulsosConservados to set
	 */
	public void setHayPulsosConservados(boolean hayPulsosConservados)
	{
		this.hayPulsosConservados = hayPulsosConservados;
	}

	@Override
	public boolean equals(Object o)
	{
		return o instanceof ExploracionFisicaResult && o.hashCode() == this.hashCode();
	}

	@Override
	public int hashCode()
	{
		int hash = 7;
		hash = 41 * hash + (this.carotidasLatenSimetricas ? 1 : 0);
		hash = 41 * hash + (this.ingurgitacionYugular ? 1 : 0);
		hash = 41 * hash + (this.adenopatias ? 1 : 0);
		hash = 41 * hash + (this.inspeccionNormal ? 1 : 0);
		hash = 41 * hash + (this.inspeccionAnormalDetalle != null ? this.inspeccionAnormalDetalle.hashCode() : 0);
		hash = 41 * hash + (this.palpacionNormal ? 1 : 0);
		hash = 41 * hash + (this.palpacionAnormalDetalle != null ? this.palpacionAnormalDetalle.hashCode() : 0);
		hash = 41 * hash + (this.percusionNormal ? 1 : 0);
		hash = 41 * hash + (this.percusionAnormalDetalle != null ? this.percusionAnormalDetalle.hashCode() : 0);
		hash = 41 * hash + (this.auscultacionNormal ? 1 : 0);
		hash = 41 * hash + (this.auscultacionAnormalDetalle != null ? this.auscultacionAnormalDetalle.hashCode() : 0);
		hash = 41 * hash + (this.auscultacionCardiacaNormal ? 1 : 0);
		hash = 41 * hash + (this.haySoplos ? 1 : 0);
		hash = 41 * hash + this.localizacionSoplo;
		hash = 41 * hash + this.tipoSoplo;
		hash = 41 * hash + (this.hayEdemas ? 1 : 0);
		hash = 41 * hash + (this.hayAcropaquias ? 1 : 0);
		hash = 41 * hash + (this.hayPulsosConservados ? 1 : 0);
		return hash;
	}
}
