/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package es.usc.citius.servando.android.models.protocol.medicalActionResults.tests;

import org.simpleframework.xml.Default;
import org.simpleframework.xml.DefaultType;
import org.simpleframework.xml.Element;

/**
 * Esta clase guarda los resultados de la cumplimentación del cuestionario Saint George
 */
@Default(DefaultType.FIELD)
public class STGeorgeResult {
	@Element(name = "tosUltimoAno")
	private int tosUltimoAno;
	@Element(name = "expectoracionUltimoAno")
	private int expectoracionUltimoAno;
	@Element(name = "fatigaUltimoAno")
	private int fatigaUltimoAno;
	@Element(name = "silbidosUltimoAno")
	private int silbidosUltimoAno;
	@Element(name = "ataquesDesagradables")
	private int ataquesDesagradables;
	@Element(name = "duracionPeorAtaque")
	private int duracionPeorAtaque;
	@Element(name = "diasBuenosSemana")
	private int diasBuenosSemana;
	@Element(name = "silbidosPeoresManana")
	private boolean silbidosPeoresManana;
	@Element(name = "situacionPulmones")
	private int situacionPulmones;
	@Element(name = "afectacionPulmonesTrabajo")
	private int afectacionPulmonesTrabajo;
	@Element(name = "faltaAireSentado")
	private boolean faltaAireSentado;
	@Element(name = "faltaAireVestirse")
	private boolean faltaAireVestirse;
	@Element(name = "faltaAireCaminandoCasa")
	private boolean faltaAireCaminandoCasa;
	@Element(name = "faltaAireCaminandoFuera")
	private boolean faltaAireCaminandoFuera;
	@Element(name = "faltaAireSubirEscaleras")
	private boolean faltaAireSubirEscaleras;
	@Element(name = "faltaAireSubirCuesta")
	private boolean faltaAireSubirCuesta;
	@Element(name = "faltaAirePracticarDeporte")
	private boolean faltaAirePracticarDeporte;
	@Element(name = "dolorToser")
	private boolean dolorToser;
	@Element(name = "agotaToser")
	private boolean agotaToser;
	@Element(name = "faltaAireHablar")
	private boolean faltaAireHablar;
	@Element(name = "faltaAireAgacharse")
	private boolean faltaAireAgacharse;
	@Element(name = "molestaTosDormir")
	private boolean molestaTosDormir;
	@Element(name = "agotarseRapido")
	private boolean agotarseRapido;
	@Element(name = "verguenzaTos")
	private boolean verguenzaTos;
	@Element(name = "molestiasFamilia")
	private boolean molestiasFamilia;
	@Element(name = "asustaNoRespirar")
	private boolean asustaNoRespirar;
	@Element(name = "problemasRespiratoriosIncontrolables")
	private boolean problemasRespiratoriosIncontrolables;
	@Element(name = "problemasRespiratoriosNoMejoran")
	private boolean problemasRespiratoriosNoMejoran;
	@Element(name = "personaDebil")
	private boolean personaDebil;
	@Element(name = "ejercicioPeligroso")
	private boolean ejercicioPeligroso;
	@Element(name = "cualquierEsfuerzoExcesivo")
	private boolean cualquierEsfuerzoExcesivo;
	@Element(name = "medicacionInutil")
	private boolean medicacionInutil;
	@Element(name = "verguenzaMedicarsePublico")
	private boolean verguenzaMedicarsePublico;
	@Element(name = "medicacionDesagradable")
	private boolean medicacionDesagradable;
	@Element(name = "medicacionAlteraVida")
	private boolean medicacionAlteraVida;
	@Element(name = "tardaMuchoVestirse")
	private boolean tardaMuchoVestirse;
	@Element(name = "noConsigueDucharse")
	private boolean noConsigueDucharse;
	@Element(name = "caminaDespacio")
	private boolean caminaDespacio;
	@Element(name = "tardaRealizarTareas")
	private boolean tardaRealizarTareas;
	@Element(name = "descansosSubirEscaleras")
	private boolean descansosSubirEscaleras;
	@Element(name = "noPuedeApurarPaso")
	private boolean noPuedeApurarPaso;
	@Element(name = "cuestaBailar")
	private boolean cuestaBailar;
	@Element(name = "cuestaCargarCosasPesadas")
	private boolean cuestaCargarCosasPesadas;
	@Element(name = "cuestaCorrer")
	private boolean cuestaCorrer;
	@Element(name = "noPuedePracticarDeporte")
	private boolean noPuedePracticarDeporte;
	@Element(name = "noPuedeDivertirse")
	private boolean noPuedeDivertirse;
	@Element(name = "noPuedeIrCompra")
	private boolean noPuedeIrCompra;
	@Element(name = "noPuedeHacerTareasHogar")
	private boolean noPuedeHacerTareasHogar;
	@Element(name = "noPuedeAlejarseSilla")
	private boolean noPuedeAlejarseSilla;
	@Element(name = "impideHacerRespiracion")
	private int impideHacerRespiracion;

	/**
	 * Indica cuando tuvo tos el paciente en el último año (0 = Casi todos los días, 1 = varios días a la semana, 2 =
	 * algunos días al mes, 3 = sólo cuando tuvo infección pulmonar, o 4 = nada en absoluto).
	 * 
	 * @return the tosUltimoAno
	 */
	public int getTosUltimoAno()
	{
		return tosUltimoAno;
	}

	/**
	 * Indica cuando tuvo tos el paciente en el último año (0 = Casi todos los días, 1 = varios días a la semana, 2 =
	 * algunos días al mes, 3 = sólo cuando tuvo infección pulmonar, o 4 = nada en absoluto).
	 * 
	 * @param tosUltimoAno the tosUltimoAno to set
	 */
	public void setTosUltimoAno(int tosUltimoAno)
	{
		this.tosUltimoAno = tosUltimoAno;
	}

	/**
	 * Indica cuando tuvo expectoración el paciente en el último año (0 = Casi todos los días, 1 = varios días a la
	 * semana, 2 = algunos días al mes, 3 = sólo cuando tuvo infección pulmonar, o 4 = nada en absoluto).
	 * 
	 * @return the expectoracionUltimoAno
	 */
	public int getExpectoracionUltimoAno()
	{
		return expectoracionUltimoAno;
	}

	/**
	 * Indica cuando tuvo expectoración el paciente en el último año (0 = Casi todos los días, 1 = varios días a la
	 * semana, 2 = algunos días al mes, 3 = sólo cuando tuvo infección pulmonar, o 4 = nada en absoluto).
	 * 
	 * @param expectoracionUltimoAno the expectoracionUltimoAno to set
	 */
	public void setExpectoracionUltimoAno(int expectoracionUltimoAno)
	{
		this.expectoracionUltimoAno = expectoracionUltimoAno;
	}

	/**
	 * Indica cuando sintió fatiga el paciente en el último año (0 = Casi todos los días, 1 = varios días a la semana, 2
	 * = algunos días al mes, 3 = sólo cuando tuvo infección pulmonar, o 4 = nada en absoluto).
	 * 
	 * @return the fatigaUltimoAno
	 */
	public int getFatigaUltimoAno()
	{
		return fatigaUltimoAno;
	}

	/**
	 * Indica cuando sintió fatiga el paciente en el último año (0 = Casi todos los días, 1 = varios días a la semana, 2
	 * = algunos días al mes, 3 = sólo cuando tuvo infección pulmonar, o 4 = nada en absoluto).
	 * 
	 * @param fatigaUltimoAno the fatigaUltimoAno to set
	 */
	public void setFatigaUltimoAno(int fatigaUltimoAno)
	{
		this.fatigaUltimoAno = fatigaUltimoAno;
	}

	/**
	 * Indica cuando tuvo ataques de silbidos en el pulmón el paciente en el último año (0 = Casi todos los días, 1 =
	 * varios días a la semana, 2 = algunos días al mes, 3 = sólo cuando tuvo infección pulmonar, o 4 = nada en
	 * absoluto).
	 * 
	 * @return the silbidosUltimoAno
	 */
	public int getSilbidosUltimoAno()
	{
		return silbidosUltimoAno;
	}

	/**
	 * Indica cuando tuvo ataques de silbidos en el pulmón el paciente en el último año (0 = Casi todos los días, 1 =
	 * varios días a la semana, 2 = algunos días al mes, 3 = sólo cuando tuvo infección pulmonar, o 4 = nada en
	 * absoluto).
	 * 
	 * @param silbidosUltimoAno the silbidosUltimoAno to set
	 */
	public void setSilbidosUltimoAno(int silbidosUltimoAno)
	{
		this.silbidosUltimoAno = silbidosUltimoAno;
	}

	/**
	 * Indica cuantos ataques tuvo el paciente por problemas respiratorios en el último año (0 = Más de tres, 1 = tres,
	 * 2 = dos, 3 = uno, 4 = ninguno)
	 * 
	 * @return the ataquesDesagradables
	 */
	public int getAtaquesDesagradables()
	{
		return ataquesDesagradables;
	}

	/**
	 * Indica cuantos ataques tuvo el paciente por problemas respiratorios en el último año (0 = Más de tres, 1 = tres,
	 * 2 = dos, 3 = uno, 4 = ninguno)
	 * 
	 * @param ataquesDesagradables the ataquesDesagradables to set
	 */
	public void setAtaquesDesagradables(int ataquesDesagradables)
	{
		this.ataquesDesagradables = ataquesDesagradables;
	}

	/**
	 * Indica cuanto ataque (de los referidos anteriormente) (0 = Una semana o más, 1 = de tres a seis días, 2 = uno o
	 * dos días, 3 = menos de un día)
	 * 
	 * @return the duracionPeorAtaque
	 */
	public int getDuracionPeorAtaque()
	{
		return duracionPeorAtaque;
	}

	/**
	 * Indica cuanto ataque (de los referidos anteriormente) (0 = Una semana o más, 1 = de tres a seis días, 2 = uno o
	 * dos días, 3 = menos de un día)
	 * 
	 * @param duracionPeorAtaque the duracionPeorAtaque to set
	 */
	public void setDuracionPeorAtaque(int duracionPeorAtaque)
	{
		this.duracionPeorAtaque = duracionPeorAtaque;
	}

	/**
	 * Indica cuantos días buenos tuvo el paciente en una semana normal del último año (0 = ninguno, 1 = uno o dos días,
	 * 2 = tres o cuatro días, 3 = casi todos los días, 4 = todos los días)
	 * 
	 * @return the diasBuenosSemana
	 */
	public int getDiasBuenosSemana()
	{
		return diasBuenosSemana;
	}

	/**
	 * Indica cuantos días buenos tuvo el paciente en una semana normal del último año (0 = ninguno, 1 = uno o dos días,
	 * 2 = tres o cuatro días, 3 = casi todos los días, 4 = todos los días)
	 * 
	 * @param diasBuenosSemana the diasBuenosSemana to set
	 */
	public void setDiasBuenosSemana(int diasBuenosSemana)
	{
		this.diasBuenosSemana = diasBuenosSemana;
	}

	/**
	 * Indica si los silbidos en el pecho son peores por la mañana.
	 * 
	 * @return the silbidosPeoresManana
	 */
	public boolean isSilbidosPeoresManana()
	{
		return silbidosPeoresManana;
	}

	/**
	 * Indica si los silbidos en el pecho son peores por la mañana.
	 * 
	 * @param silbidosPeoresManana the silbidosPeoresManana to set
	 */
	public void setSilbidosPeoresManana(boolean silbidosPeoresManana)
	{
		this.silbidosPeoresManana = silbidosPeoresManana;
	}

	/**
	 * Como considera el paciente que está de los pulmones (0 = Es mi problema más importante, 1 = me causa bastantes
	 * problemas, 2 = me causa algún problema, 3 = no me causa problemas)
	 * 
	 * @return the situacionPulmones
	 */
	public int getSituacionPulmones()
	{
		return situacionPulmones;
	}

	/**
	 * Como considera el paciente que está de los pulmones (0 = Es mi problema más importante, 1 = me causa bastantes
	 * problemas, 2 = me causa algún problema, 3 = no me causa problemas)
	 * 
	 * @param situacionPulmones the situacionPulmones to set
	 */
	public void setSituacionPulmones(int situacionPulmones)
	{
		this.situacionPulmones = situacionPulmones;
	}

	/**
	 * Indica cómo le afectan los problemas respiratorios al paciente en su trabajo (0 = Tuve que dejar de trabajar, 1 =
	 * tuve que cambiar de trabajo, 2 = dificultan mi trabajo, 3 = no me afectan).
	 * 
	 * @return the afectacionPulmonesTrabajo
	 */
	public int getAfectacionPulmonesTrabajo()
	{
		return afectacionPulmonesTrabajo;
	}

	/**
	 * Indica cómo le afectan los problemas respiratorios al paciente en su trabajo (0 = Tuve que dejar de trabajar, 1 =
	 * tuve que cambiar de trabajo, 2 = dificultan mi trabajo, 3 = no me afectan).
	 * 
	 * @param afectacionPulmonesTrabajo the afectacionPulmonesTrabajo to set
	 */
	public void setAfectacionPulmonesTrabajo(int afectacionPulmonesTrabajo)
	{
		this.afectacionPulmonesTrabajo = afectacionPulmonesTrabajo;
	}

	/**
	 * Indica si ultimamente al paciente le falta el aire estando sentado o incluso acostado.
	 * 
	 * @return the faltaAireSentado
	 */
	public boolean isFaltaAireSentado()
	{
		return faltaAireSentado;
	}

	/**
	 * Indica si ultimamente al paciente le falta el aire estando sentado o incluso acostado.
	 * 
	 * @param faltaAireSentado the faltaAireSentado to set
	 */
	public void setFaltaAireSentado(boolean faltaAireSentado)
	{
		this.faltaAireSentado = faltaAireSentado;
	}

	/**
	 * Indica si ultimamente al paciente le falta el aire al lavarse o vestirse.
	 * 
	 * @return the faltaAireVestirse
	 */
	public boolean isFaltaAireVestirse()
	{
		return faltaAireVestirse;
	}

	/**
	 * Indica si ultimamente al paciente le falta el aire al lavarse o vestirse.
	 * 
	 * @param faltaAireVestirse the faltaAireVestirse to set
	 */
	public void setFaltaAireVestirse(boolean faltaAireVestirse)
	{
		this.faltaAireVestirse = faltaAireVestirse;
	}

	/**
	 * Indica si ultimamente al paciente le falta el aire caminando por su casa.
	 * 
	 * @return the faltaAireCaminandoCasa
	 */
	public boolean isFaltaAireCaminandoCasa()
	{
		return faltaAireCaminandoCasa;
	}

	/**
	 * Indica si ultimamente al paciente le falta el aire caminando por su casa.
	 * 
	 * @param faltaAireCaminandoCasa the faltaAireCaminandoCasa to set
	 */
	public void setFaltaAireCaminandoCasa(boolean faltaAireCaminandoCasa)
	{
		this.faltaAireCaminandoCasa = faltaAireCaminandoCasa;
	}

	/**
	 * Indica si ultimamente al paciente le falta el aire caminando por fuera en terreno llano.
	 * 
	 * @return the faltaAireCaminandoFuera
	 */
	public boolean isFaltaAireCaminandoFuera()
	{
		return faltaAireCaminandoFuera;
	}

	/**
	 * Indica si ultimamente al paciente le falta el aire caminando por fuera en terreno llano.
	 * 
	 * @param faltaAireCaminandoFuera the faltaAireCaminandoFuera to set
	 */
	public void setFaltaAireCaminandoFuera(boolean faltaAireCaminandoFuera)
	{
		this.faltaAireCaminandoFuera = faltaAireCaminandoFuera;
	}

	/**
	 * Indica si ultimamente al paciente le falta el aire al subir un piso por las escaleras.
	 * 
	 * @return the faltaAireSubirEscaleras
	 */
	public boolean isFaltaAireSubirEscaleras()
	{
		return faltaAireSubirEscaleras;
	}

	/**
	 * Indica si ultimamente al paciente le falta el aire al subir un piso por las escaleras.
	 * 
	 * @param faltaAireSubirEscaleras the faltaAireSubirEscaleras to set
	 */
	public void setFaltaAireSubirEscaleras(boolean faltaAireSubirEscaleras)
	{
		this.faltaAireSubirEscaleras = faltaAireSubirEscaleras;
	}

	/**
	 * Indica si ultimamente al paciente le falta el aire al subir una cuesta.
	 * 
	 * @return the faltaAireSubirCuesta
	 */
	public boolean isFaltaAireSubirCuesta()
	{
		return faltaAireSubirCuesta;
	}

	/**
	 * Indica si ultimamente al paciente le falta el aire al subir una cuesta.
	 * 
	 * @param faltaAireSubirCuesta the faltaAireSubirCuesta to set
	 */
	public void setFaltaAireSubirCuesta(boolean faltaAireSubirCuesta)
	{
		this.faltaAireSubirCuesta = faltaAireSubirCuesta;
	}

	/**
	 * Indica si ultimamente al paciente le falta el aire al practicar algún deporte o jugar.
	 * 
	 * @return the faltaAirePracticarDeporte
	 */
	public boolean isFaltaAirePracticarDeporte()
	{
		return faltaAirePracticarDeporte;
	}

	/**
	 * Indica si ultimamente al paciente le falta el aire al practicar algún deporte o jugar.
	 * 
	 * @param faltaAirePracticarDeporte the faltaAirePracticarDeporte to set
	 */
	public void setFaltaAirePracticarDeporte(boolean faltaAirePracticarDeporte)
	{
		this.faltaAirePracticarDeporte = faltaAirePracticarDeporte;
	}

	/**
	 * Indica si ultimamente el paciente siente dolor al toser.
	 * 
	 * @return the dolorToser
	 */
	public boolean isDolorToser()
	{
		return dolorToser;
	}

	/**
	 * Indica si ultimamente el paciente siente dolor al toser.
	 * 
	 * @param dolorToser the dolorToser to set
	 */
	public void setDolorToser(boolean dolorToser)
	{
		this.dolorToser = dolorToser;
	}

	/**
	 * Indica si ultimamente el paciente se agota al toser.
	 * 
	 * @return the agotaToser
	 */
	public boolean isAgotaToser()
	{
		return agotaToser;
	}

	/**
	 * Indica si ultimamente el paciente se agota al toser.
	 * 
	 * @param agotaToser the agotaToser to set
	 */
	public void setAgotaToser(boolean agotaToser)
	{
		this.agotaToser = agotaToser;
	}

	/**
	 * Indica si ultimamente al paciente le falta el aire al hablar.
	 * 
	 * @return the faltaAireHablar
	 */
	public boolean isFaltaAireHablar()
	{
		return faltaAireHablar;
	}

	/**
	 * Indica si ultimamente al paciente le falta el aire al hablar.
	 * 
	 * @param faltaAireHablar the faltaAireHablar to set
	 */
	public void setFaltaAireHablar(boolean faltaAireHablar)
	{
		this.faltaAireHablar = faltaAireHablar;
	}

	/**
	 * Indica si ultimamente al paciente le falta el aire al agacharse.
	 * 
	 * @return the faltaAireAgacharse
	 */
	public boolean isFaltaAireAgacharse()
	{
		return faltaAireAgacharse;
	}

	/**
	 * Indica si ultimamente al paciente le falta el aire al agacharse.
	 * 
	 * @param faltaAireAgacharse the faltaAireAgacharse to set
	 */
	public void setFaltaAireAgacharse(boolean faltaAireAgacharse)
	{
		this.faltaAireAgacharse = faltaAireAgacharse;
	}

	/**
	 * Indica si ultimamente al paciente le molesta la tos o la respiración al dormir.
	 * 
	 * @return the molestaTosDormir
	 */
	public boolean isMolestaTosDormir()
	{
		return molestaTosDormir;
	}

	/**
	 * Indica si ultimamente al paciente le molesta la tos o la respiración al dormir.
	 * 
	 * @param molestaTosDormir the molestaTosDormir to set
	 */
	public void setMolestaTosDormir(boolean molestaTosDormir)
	{
		this.molestaTosDormir = molestaTosDormir;
	}

	/**
	 * Indica si ultimamente el paciente se agota rápido
	 * 
	 * @return the agotarseRapido
	 */
	public boolean isAgotarseRapido()
	{
		return agotarseRapido;
	}

	/**
	 * Indica si ultimamente el paciente se agota rápido
	 * 
	 * @param agotarseRapido the agotarseRapido to set
	 */
	public void setAgotarseRapido(boolean agotarseRapido)
	{
		this.agotarseRapido = agotarseRapido;
	}

	/**
	 * Indica si el paciente se avergüenza de toser en público.
	 * 
	 * @return the verguenzaTos
	 */
	public boolean isVerguenzaTos()
	{
		return verguenzaTos;
	}

	/**
	 * Indica si el paciente se avergüenza de toser en público.
	 * 
	 * @param verguenzaTos the verguenzaTos to set
	 */
	public void setVerguenzaTos(boolean verguenzaTos)
	{
		this.verguenzaTos = verguenzaTos;
	}

	/**
	 * Indica si los problemas respiratorios del paciente suponen una molestia para su familia, amigos, etc.
	 * 
	 * @return the molestiasFamilia
	 */
	public boolean isMolestiasFamilia()
	{
		return molestiasFamilia;
	}

	/**
	 * Indica si los problemas respiratorios del paciente suponen una molestia para su familia, amigos, etc.
	 * 
	 * @param molestiasFamilia the molestiasFamilia to set
	 */
	public void setMolestiasFamilia(boolean molestiasFamilia)
	{
		this.molestiasFamilia = molestiasFamilia;
	}

	/**
	 * Indica si el paciente se asusta cuando no es capaz de respirar con normalidad.
	 * 
	 * @return the asustaNoRespirar
	 */
	public boolean isAsustaNoRespirar()
	{
		return asustaNoRespirar;
	}

	/**
	 * Indica si el paciente se asusta cuando no es capaz de respirar con normalidad.
	 * 
	 * @param asustaNoRespirar the asustaNoRespirar to set
	 */
	public void setAsustaNoRespirar(boolean asustaNoRespirar)
	{
		this.asustaNoRespirar = asustaNoRespirar;
	}

	/**
	 * Indica que el paciente siente que no es capaz de controlar sus problemas respiratorios
	 * 
	 * @return the problemasRespiratoriosIncontrolables
	 */
	public boolean isProblemasRespiratoriosIncontrolables()
	{
		return problemasRespiratoriosIncontrolables;
	}

	/**
	 * Indica que el paciente siente que no es capaz de controlar sus problemas respiratorios
	 * 
	 * @param problemasRespiratoriosIncontrolables the problemasRespiratoriosIncontrolables to set
	 */
	public void setProblemasRespiratoriosIncontrolables(boolean problemasRespiratoriosIncontrolables)
	{
		this.problemasRespiratoriosIncontrolables = problemasRespiratoriosIncontrolables;
	}

	/**
	 * Indica si el paciente cree que sus problemas respiratorios no van a mejorar.
	 * 
	 * @return the problemasRespiratoriosNoMejoran
	 */
	public boolean isProblemasRespiratoriosNoMejoran()
	{
		return problemasRespiratoriosNoMejoran;
	}

	/**
	 * Indica si el paciente cree que sus problemas respiratorios no van a mejorar.
	 * 
	 * @param problemasRespiratoriosNoMejoran the problemasRespiratoriosNoMejoran to set
	 */
	public void setProblemasRespiratoriosNoMejoran(boolean problemasRespiratoriosNoMejoran)
	{
		this.problemasRespiratoriosNoMejoran = problemasRespiratoriosNoMejoran;
	}

	/**
	 * Indica si el paciente cree que sus problemas respiratorios le hacen una persona débil.
	 * 
	 * @return the personaDebil
	 */
	public boolean isPersonaDebil()
	{
		return personaDebil;
	}

	/**
	 * Indica si el paciente cree que sus problemas respiratorios le hacen una persona débil.
	 * 
	 * @param personaDebil the personaDebil to set
	 */
	public void setPersonaDebil(boolean personaDebil)
	{
		this.personaDebil = personaDebil;
	}

	/**
	 * Indica si el paciente cree que hacer ejercicio es peligroso para el.
	 * 
	 * @return the ejercicioPeligroso
	 */
	public boolean isEjercicioPeligroso()
	{
		return ejercicioPeligroso;
	}

	/**
	 * Indica si el paciente cree que hacer ejercicio es peligroso para el.
	 * 
	 * @param ejercicioPeligroso the ejercicioPeligroso to set
	 */
	public void setEjercicioPeligroso(boolean ejercicioPeligroso)
	{
		this.ejercicioPeligroso = ejercicioPeligroso;
	}

	/**
	 * Indica si el paciente cree que cualquier esfuerzo que haga es excesivo.
	 * 
	 * @return the cualquierEsfuerzoExcesivo
	 */
	public boolean isCualquierEsfuerzoExcesivo()
	{
		return cualquierEsfuerzoExcesivo;
	}

	/**
	 * Indica si el paciente cree que cualquier esfuerzo que haga es excesivo.
	 * 
	 * @param cualquierEsfuerzoExcesivo the cualquierEsfuerzoExcesivo to set
	 */
	public void setCualquierEsfuerzoExcesivo(boolean cualquierEsfuerzoExcesivo)
	{
		this.cualquierEsfuerzoExcesivo = cualquierEsfuerzoExcesivo;
	}

	/**
	 * Indica si el paciente cree que la medicación le sirve de poco.
	 * 
	 * @return the medicacionInutil
	 */
	public boolean isMedicacionInutil()
	{
		return medicacionInutil;
	}

	/**
	 * Indica si el paciente cree que la medicación le sirve de poco.
	 * 
	 * @param medicacionInutil the medicacionInutil to set
	 */
	public void setMedicacionInutil(boolean medicacionInutil)
	{
		this.medicacionInutil = medicacionInutil;
	}

	/**
	 * Indica si al paciente le da vergüenza tomar la medicación en público.
	 * 
	 * @return the verguenzaMedicarsePublico
	 */
	public boolean isVerguenzaMedicarsePublico()
	{
		return verguenzaMedicarsePublico;
	}

	/**
	 * Indica si al paciente le da vergüenza tomar la medicación en público.
	 * 
	 * @param verguenzaMedicarsePublico the verguenzaMedicarsePublico to set
	 */
	public void setVerguenzaMedicarsePublico(boolean verguenzaMedicarsePublico)
	{
		this.verguenzaMedicarsePublico = verguenzaMedicarsePublico;
	}

	/**
	 * Indica si al paciente le produce efectos desagradables la medicación.
	 * 
	 * @return the medicacionDesagradable
	 */
	public boolean isMedicacionDesagradable()
	{
		return medicacionDesagradable;
	}

	/**
	 * Indica si al paciente le produce efectos desagradables la medicación.
	 * 
	 * @param medicacionDesagradable the medicacionDesagradable to set
	 */
	public void setMedicacionDesagradable(boolean medicacionDesagradable)
	{
		this.medicacionDesagradable = medicacionDesagradable;
	}

	/**
	 * Indica si el paciente cree que la medicación le altera mucho la vida.
	 * 
	 * @return the medicacionAlteraVida
	 */
	public boolean isMedicacionAlteraVida()
	{
		return medicacionAlteraVida;
	}

	/**
	 * Indica si el paciente cree que la medicación le altera mucho la vida.
	 * 
	 * @param medicacionAlteraVida the medicacionAlteraVida to set
	 */
	public void setMedicacionAlteraVida(boolean medicacionAlteraVida)
	{
		this.medicacionAlteraVida = medicacionAlteraVida;
	}

	/**
	 * Indica si el paciente tarda mucho en lavarse o vestirse debido a sus problemas respiratorios.
	 * 
	 * @return the tardaMuchoVestirse
	 */
	public boolean isTardaMuchoVestirse()
	{
		return tardaMuchoVestirse;
	}

	/**
	 * Indica si el paciente tarda mucho en lavarse o vestirse debido a sus problemas respiratorios.
	 * 
	 * @param tardaMuchoVestirse the tardaMuchoVestirse to set
	 */
	public void setTardaMuchoVestirse(boolean tardaMuchoVestirse)
	{
		this.tardaMuchoVestirse = tardaMuchoVestirse;
	}

	/**
	 * Indica si el paciente cree no consigue ducharse o bañarse, o le lleva mucho tiempo.
	 * 
	 * @return the noConsigueDucharse
	 */
	public boolean isNoConsigueDucharse()
	{
		return noConsigueDucharse;
	}

	/**
	 * Indica si el paciente cree no consigue ducharse o bañarse, o le lleva mucho tiempo.
	 * 
	 * @param noConsigueDucharse the noConsigueDucharse to set
	 */
	public void setNoConsigueDucharse(boolean noConsigueDucharse)
	{
		this.noConsigueDucharse = noConsigueDucharse;
	}

	/**
	 * Indica si el paciente camina más despacio que los demás, o tiene que parar a descansar.
	 * 
	 * @return the caminaDespacio
	 */
	public boolean isCaminaDespacio()
	{
		return caminaDespacio;
	}

	/**
	 * Indica si el paciente camina más despacio que los demás, o tiene que parar a descansar.
	 * 
	 * @param caminaDespacio the caminaDespacio to set
	 */
	public void setCaminaDespacio(boolean caminaDespacio)
	{
		this.caminaDespacio = caminaDespacio;
	}

	/**
	 * Indica si el paciente tarda mucho en hacer las tareas domésticas, o tiene que parar a descansar.
	 * 
	 * @return the tardaRealizarTareas
	 */
	public boolean isTardaRealizarTareas()
	{
		return tardaRealizarTareas;
	}

	/**
	 * Indica si el paciente tarda mucho en hacer las tareas domésticas, o tiene que parar a descansar.
	 * 
	 * @param tardaRealizarTareas the tardaRealizarTareas to set
	 */
	public void setTardaRealizarTareas(boolean tardaRealizarTareas)
	{
		this.tardaRealizarTareas = tardaRealizarTareas;
	}

	/**
	 * Indica si el paciente tiene que ir muy despacio o parar al subir un piso por las escaleras.
	 * 
	 * @return the descansosSubirEscaleras
	 */
	public boolean isDescansosSubirEscaleras()
	{
		return descansosSubirEscaleras;
	}

	/**
	 * Indica si el paciente tiene que ir muy despacio o parar al subir un piso por las escaleras.
	 * 
	 * @param descansosSubirEscaleras the descansosSubirEscaleras to set
	 */
	public void setDescansosSubirEscaleras(boolean descansosSubirEscaleras)
	{
		this.descansosSubirEscaleras = descansosSubirEscaleras;
	}

	/**
	 * Indica si el paciente tiene que detenerse o ir más despacio si intenta apurar el paso.
	 * 
	 * @return the noPuedeApurarPaso
	 */
	public boolean isNoPuedeApurarPaso()
	{
		return noPuedeApurarPaso;
	}

	/**
	 * Indica si el paciente tiene que detenerse o ir más despacio si intenta apurar el paso.
	 * 
	 * @param noPuedeApurarPaso the noPuedeApurarPaso to set
	 */
	public void setNoPuedeApurarPaso(boolean noPuedeApurarPaso)
	{
		this.noPuedeApurarPaso = noPuedeApurarPaso;
	}

	/**
	 * Indica si los problemas respiratorios dificultan al paciente tareas como subir una cuesta o arreglar el jardín o
	 * bailar.
	 * 
	 * @return the cuestaBailar
	 */
	public boolean isCuestaBailar()
	{
		return cuestaBailar;
	}

	/**
	 * Indica si los problemas respiratorios dificultan al paciente tareas como subir una cuesta o arreglar el jardín o
	 * bailar.
	 * 
	 * @param cuestaBailar the cuestaBailar to set
	 */
	public void setCuestaBailar(boolean cuestaBailar)
	{
		this.cuestaBailar = cuestaBailar;
	}

	/**
	 * Indica si al paciente le cuesta cargar cosas pesadas, trotar, jugar al tenis...
	 * 
	 * @return the cuestaCargarCosasPesadas
	 */
	public boolean isCuestaCargarCosasPesadas()
	{
		return cuestaCargarCosasPesadas;
	}

	/**
	 * Indica si al paciente le cuesta cargar cosas pesadas, trotar, jugar al tenis...
	 * 
	 * @param cuestaCargarCosasPesadas the cuestaCargarCosasPesadas to set
	 */
	public void setCuestaCargarCosasPesadas(boolean cuestaCargarCosasPesadas)
	{
		this.cuestaCargarCosasPesadas = cuestaCargarCosasPesadas;
	}

	/**
	 * Indica si al paciente le cuesta correr, realizar trabajos pesados, competir...
	 * 
	 * @return the cuestaCorrer
	 */
	public boolean isCuestaCorrer()
	{
		return cuestaCorrer;
	}

	/**
	 * Indica si al paciente le cuesta correr, realizar trabajos pesados, competir...
	 * 
	 * @param cuestaCorrer the cuestaCorrer to set
	 */
	public void setCuestaCorrer(boolean cuestaCorrer)
	{
		this.cuestaCorrer = cuestaCorrer;
	}

	/**
	 * Indica si por culpa de los problemas respiratorios el paciente no puede practicar deporte o jugar
	 * 
	 * @return the noPuedePracticarDeporte
	 */
	public boolean isNoPuedePracticarDeporte()
	{
		return noPuedePracticarDeporte;
	}

	/**
	 * Indica si por culpa de los problemas respiratorios el paciente no puede practicar deporte o jugar
	 * 
	 * @param noPuedePracticarDeporte the noPuedePracticarDeporte to set
	 */
	public void setNoPuedePracticarDeporte(boolean noPuedePracticarDeporte)
	{
		this.noPuedePracticarDeporte = noPuedePracticarDeporte;
	}

	/**
	 * Indica si por culpa de los problemas respiratorios el paciente no puede salir a divertirse.
	 * 
	 * @return the noPuedeDivertirse
	 */
	public boolean isNoPuedeDivertirse()
	{
		return noPuedeDivertirse;
	}

	/**
	 * Indica si por culpa de los problemas respiratorios el paciente no puede salir a divertirse.
	 * 
	 * @param noPuedeDivertirse the noPuedeDivertirse to set
	 */
	public void setNoPuedeDivertirse(boolean noPuedeDivertirse)
	{
		this.noPuedeDivertirse = noPuedeDivertirse;
	}

	/**
	 * Indica si por culpa de los problemas respiratorios el paciente no puede salir de casa para ir a comprar algo.
	 * 
	 * @return the noPuedeIrCompra
	 */
	public boolean isNoPuedeIrCompra()
	{
		return noPuedeIrCompra;
	}

	/**
	 * Indica si por culpa de los problemas respiratorios el paciente no puede salir de casa para ir a comprar algo.
	 * 
	 * @param noPuedeIrCompra the noPuedeIrCompra to set
	 */
	public void setNoPuedeIrCompra(boolean noPuedeIrCompra)
	{
		this.noPuedeIrCompra = noPuedeIrCompra;
	}

	/**
	 * Indica si por culpa de los problemas respiratorios el paciente no puede hacer las tareas del hogar
	 * 
	 * @return the noPuedeHacerTareasHogar
	 */
	public boolean isNoPuedeHacerTareasHogar()
	{
		return noPuedeHacerTareasHogar;
	}

	/**
	 * Indica si por culpa de los problemas respiratorios el paciente no puede hacer las tareas del hogar
	 * 
	 * @param noPuedeHacerTareasHogar the noPuedeHacerTareasHogar to set
	 */
	public void setNoPuedeHacerTareasHogar(boolean noPuedeHacerTareasHogar)
	{
		this.noPuedeHacerTareasHogar = noPuedeHacerTareasHogar;
	}

	/**
	 * Indica si por culpa de los problemas respiratorios el paciente no puede alejarse de la cama o una silla
	 * 
	 * @return the noPuedeAlejarseSilla
	 */
	public boolean isNoPuedeAlejarseSilla()
	{
		return noPuedeAlejarseSilla;
	}

	/**
	 * Indica si por culpa de los problemas respiratorios el paciente no puede alejarse de la cama o una silla
	 * 
	 * @param noPuedeAlejarseSilla the noPuedeAlejarseSilla to set
	 */
	public void setNoPuedeAlejarseSilla(boolean noPuedeAlejarseSilla)
	{
		this.noPuedeAlejarseSilla = noPuedeAlejarseSilla;
	}

	/**
	 * Indica qué cosas le impide realizar al paciente sus problemas respiratorios (0 = Todo lo que quisiera hacer, 1 =
	 * la mayoría de cosas, 2 = una o dos cosas, 3 = no me impiden hacer nada)
	 * 
	 * @return the impideHacerRespiracion
	 */
	public int getImpideHacerRespiracion()
	{
		return impideHacerRespiracion;
	}

	/**
	 * Indica qué cosas le impide realizar al paciente sus problemas respiratorios (0 = Todo lo que quisiera hacer, 1 =
	 * la mayoría de cosas, 2 = una o dos cosas, 3 = no me impiden hacer nada)
	 * 
	 * @param impideHacerRespiracion the impideHacerRespiracion to set
	 */
	public void setImpideHacerRespiracion(int impideHacerRespiracion)
	{
		this.impideHacerRespiracion = impideHacerRespiracion;
	}

	@Override
	public boolean equals(Object o)
	{
		return o instanceof STGeorgeResult && o.hashCode() == this.hashCode();
	}

	@Override
	public int hashCode()
	{
		int hash = 7;
		hash = 61 * hash + this.tosUltimoAno;
		hash = 61 * hash + this.expectoracionUltimoAno;
		hash = 61 * hash + this.fatigaUltimoAno;
		hash = 61 * hash + this.silbidosUltimoAno;
		hash = 61 * hash + this.ataquesDesagradables;
		hash = 61 * hash + this.duracionPeorAtaque;
		hash = 61 * hash + this.diasBuenosSemana;
		hash = 61 * hash + (this.silbidosPeoresManana ? 1 : 0);
		hash = 61 * hash + this.situacionPulmones;
		hash = 61 * hash + this.afectacionPulmonesTrabajo;
		hash = 61 * hash + (this.faltaAireSentado ? 1 : 0);
		hash = 61 * hash + (this.faltaAireVestirse ? 1 : 0);
		hash = 61 * hash + (this.faltaAireCaminandoCasa ? 1 : 0);
		hash = 61 * hash + (this.faltaAireCaminandoFuera ? 1 : 0);
		hash = 61 * hash + (this.faltaAireSubirEscaleras ? 1 : 0);
		hash = 61 * hash + (this.faltaAireSubirCuesta ? 1 : 0);
		hash = 61 * hash + (this.faltaAirePracticarDeporte ? 1 : 0);
		hash = 61 * hash + (this.dolorToser ? 1 : 0);
		hash = 61 * hash + (this.agotaToser ? 1 : 0);
		hash = 61 * hash + (this.faltaAireHablar ? 1 : 0);
		hash = 61 * hash + (this.faltaAireAgacharse ? 1 : 0);
		hash = 61 * hash + (this.molestaTosDormir ? 1 : 0);
		hash = 61 * hash + (this.agotarseRapido ? 1 : 0);
		hash = 61 * hash + (this.verguenzaTos ? 1 : 0);
		hash = 61 * hash + (this.molestiasFamilia ? 1 : 0);
		hash = 61 * hash + (this.asustaNoRespirar ? 1 : 0);
		hash = 61 * hash + (this.problemasRespiratoriosIncontrolables ? 1 : 0);
		hash = 61 * hash + (this.problemasRespiratoriosNoMejoran ? 1 : 0);
		hash = 61 * hash + (this.personaDebil ? 1 : 0);
		hash = 61 * hash + (this.ejercicioPeligroso ? 1 : 0);
		hash = 61 * hash + (this.cualquierEsfuerzoExcesivo ? 1 : 0);
		hash = 61 * hash + (this.medicacionInutil ? 1 : 0);
		hash = 61 * hash + (this.verguenzaMedicarsePublico ? 1 : 0);
		hash = 61 * hash + (this.medicacionDesagradable ? 1 : 0);
		hash = 61 * hash + (this.medicacionAlteraVida ? 1 : 0);
		hash = 61 * hash + (this.tardaMuchoVestirse ? 1 : 0);
		hash = 61 * hash + (this.noConsigueDucharse ? 1 : 0);
		hash = 61 * hash + (this.caminaDespacio ? 1 : 0);
		hash = 61 * hash + (this.tardaRealizarTareas ? 1 : 0);
		hash = 61 * hash + (this.descansosSubirEscaleras ? 1 : 0);
		hash = 61 * hash + (this.noPuedeApurarPaso ? 1 : 0);
		hash = 61 * hash + (this.cuestaBailar ? 1 : 0);
		hash = 61 * hash + (this.cuestaCargarCosasPesadas ? 1 : 0);
		hash = 61 * hash + (this.cuestaCorrer ? 1 : 0);
		hash = 61 * hash + (this.noPuedePracticarDeporte ? 1 : 0);
		hash = 61 * hash + (this.noPuedeDivertirse ? 1 : 0);
		hash = 61 * hash + (this.noPuedeIrCompra ? 1 : 0);
		hash = 61 * hash + (this.noPuedeHacerTareasHogar ? 1 : 0);
		hash = 61 * hash + (this.noPuedeAlejarseSilla ? 1 : 0);
		hash = 61 * hash + this.impideHacerRespiracion;
		return hash;
	}
}
