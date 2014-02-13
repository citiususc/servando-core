/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package es.usc.citius.servando.android.models.protocol.medicalActionResults.tests;

import org.simpleframework.xml.Default;
import org.simpleframework.xml.DefaultType;
import org.simpleframework.xml.Element;

/**
 * Esta clase guarda los resultados de la cumplimentación del cuestionario SF36
 */
@Default(DefaultType.FIELD)
public class SF36Result {
	@Element(name = "salud")
	private int salud;
	@Element(name = "comparacionSalud")
	private int comparacionSalud;
	@Element(name = "limitacionEsfuerzosIntensos")
	private int limitacionEsfuerzosIntensos;
	@Element(name = "limitacionEsfuerzosModerados")
	private int limitacionEsfuerzosModerados;
	@Element(name = "limitacionLlevarBolsaCompra")
	private int limitacionLlevarBolsaCompra;
	@Element(name = "limitacionSubirVariosPisos")
	private int limitacionSubirVariosPisos;
	@Element(name = "limitacionSubirUnPiso")
	private int limitacionSubirUnPiso;
	@Element(name = "limitacionAgacharse")
	private int limitacionAgacharse;
	@Element(name = "limitacionCaminarKm")
	private int limitacionCaminarKm;
	@Element(name = "limitacionCaminarCientosMetros")
	private int limitacionCaminarCientosMetros;
	@Element(name = "limitacionCaminar100m")
	private int limitacionCaminar100m;
	@Element(name = "limitacionVestirse")
	private int limitacionVestirse;
	@Element(name = "reducirTiempoTrabajoProblemasSalud")
	private boolean reducirTiempoTrabajoProblemasSalud;
	@Element(name = "pocaProductividadProblemasSalud")
	private boolean pocaProductividadProblemasSalud;
	@Element(name = "abandonoTareasProblemasSalud")
	private boolean abandonoTareasProblemasSalud;
	@Element(name = "dificulatadesTrabajoProblemasSalud")
	private boolean dificultadesTrabajoProblemasSalud;
	@Element(name = "reducirTiempoTrabajoProblemasEmocionales")
	private boolean reducirTiempoTrabajoProblemasEmocionales;
	@Element(name = "pocaProductividadProblemasEmocionales")
	private boolean pocaProductividadProblemasEmocionales;
	@Element(name = "trabajoPocoCuidadosoProblemasEmocionales")
	private boolean trabajoPocoCuidadosoProblemasEmocionales;
	@Element(name = "dificultadesSociales")
	private int dificultadesSociales;
	@Element(name = "sufrirDolores")
	private int sufrirDolores;
	@Element(name = "dificultadesTareas")
	private int dificultadesTareas;
	@Element(name = "tiempoLlenoVitalidad")
	private int tiempoLlenoVitalidad;
	@Element(name = "tiempoNervioso")
	private int tiempoNervioso;
	@Element(name = "tiempoTranquilo")
	private int tiempoTranquilo;
	@Element(name = "tiempoBajoMoral")
	private int tiempoBajoMoral;
	@Element(name = "tiempoLlenoEnergia")
	private int tiempoLlenoEnergia;
	@Element(name = "tiempoDesanimado")
	private int tiempoDesanimado;
	@Element(name = "tiempoAgotado")
	private int tiempoAgotado;
	@Element(name = "tiempoFeliz")
	private int tiempoFeliz;
	@Element(name = "tiempoCansado")
	private int tiempoCansado;
	@Element(name = "dificultadesRelacionesSociales")
	private int dificultadesRelacionesSociales;
	@Element(name = "facilidadPonerseEnfermo")
	private int facilidadPonerseEnfermo;
	@Element(name = "saludNormal")
	private int saludNormal;
	@Element(name = "empeorarSalud")
	private int empeorarSalud;
	@Element(name = "saludExcelente")
	private int saludExcelente;

	/**
	 * Cómo considera el enfermo su salud (0 = excelente,1 = muy buena, 2 = buena, 3 = regular o 4 = mala)
	 * 
	 * @return the Salud
	 */
	public int getSalud()
	{
		return salud;
	}

	/**
	 * Cómo considera el enfermo su salud (0 = excelente,1 = muy buena, 2 = buena, 3 = regular o 4 = mala)
	 * 
	 * @param Salud the Salud to set
	 */
	public void setSalud(int Salud)
	{
		this.salud = Salud;
	}

	/**
	 * Cómo considera el enfermo su salud comparada con un año anterior (0 = mucho mejor, 1 = algo mejor, 2 = igual, 3 =
	 * algo peor o 4 = mucho peor)
	 * 
	 * @return the ComparacionSalud
	 */
	public int getComparacionSalud()
	{
		return comparacionSalud;
	}

	/**
	 * Cómo considera el enfermo su salud comparada con un año anterior (0 = mucho mejor, 1 = algo mejor, 2 = igual, 3 =
	 * algo peor o 4 = mucho peor)
	 * 
	 * @param ComparacionSalud the ComparacionSalud to set
	 */
	public void setComparacionSalud(int ComparacionSalud)
	{
		this.comparacionSalud = ComparacionSalud;
	}

	/**
	 * Cuanto limita la salud del paciente para realizar esfuerzos intensos (0 = mucho, 1 = poco o 2 = nada)
	 * 
	 * @return the LimitacionEsfuerzosIntensos
	 */
	public int getLimitacionEsfuerzosIntensos()
	{
		return limitacionEsfuerzosIntensos;
	}

	/**
	 * Cuanto limita la salud del paciente para realizar esfuerzos intensos (0 = mucho, 1 = poco o 2 = nada)
	 * 
	 * @param LimitacionEsfuerzosIntensos the LimitacionEsfuerzosIntensos to set
	 */
	public void setLimitacionEsfuerzosIntensos(int LimitacionEsfuerzosIntensos)
	{
		this.limitacionEsfuerzosIntensos = LimitacionEsfuerzosIntensos;
	}

	/**
	 * Cuanto limita la salud del paciente para realizar esfuerzos moderados (0 = mucho, 1 = poco o 2 = nada)
	 * 
	 * @return the LimitacionEsfuerzosModerados
	 */
	public int getLimitacionEsfuerzosModerados()
	{
		return limitacionEsfuerzosModerados;
	}

	/**
	 * Cuanto limita la salud del paciente para realizar esfuerzos moderados (0 = mucho, 1 = poco o 2 = nada)
	 * 
	 * @param LimitacionEsfuerzosModerados the LimitacionEsfuerzosModerados to set
	 */
	public void setLimitacionEsfuerzosModerados(int LimitacionEsfuerzosModerados)
	{
		this.limitacionEsfuerzosModerados = LimitacionEsfuerzosModerados;
	}

	/**
	 * Cuanto limita la salud del paciente para llevar la bolsa de la compra (0 = mucho, 1 = poco o 2 = nada)
	 * 
	 * @return the LimitacionLlevarBolsaCompra
	 */
	public int getLimitacionLlevarBolsaCompra()
	{
		return limitacionLlevarBolsaCompra;
	}

	/**
	 * Cuanto limita la salud del paciente para llevar la bolsa de la compra (0 = mucho, 1 = poco o 2 = nada)
	 * 
	 * @param LimitacionLlevarBolsaCompra the LimitacionLlevarBolsaCompra to set
	 */
	public void setLimitacionLlevarBolsaCompra(int LimitacionLlevarBolsaCompra)
	{
		this.limitacionLlevarBolsaCompra = LimitacionLlevarBolsaCompra;
	}

	/**
	 * Cuanto limita la salud del paciente para subir varios pisos por las escaleras (0 = mucho, 1 = poco o 2 = nada)
	 * 
	 * @return the LimitacionSubirVariosPisos
	 */
	public int getLimitacionSubirVariosPisos()
	{
		return limitacionSubirVariosPisos;
	}

	/**
	 * Cuanto limita la salud del paciente para subir varios pisos por las escaleras (0 = mucho, 1 = poco o 2 = nada)
	 * 
	 * @param LimitacionSubirVariosPisos the LimitacionSubirVariosPisos to set
	 */
	public void setLimitacionSubirVariosPisos(int LimitacionSubirVariosPisos)
	{
		this.limitacionSubirVariosPisos = LimitacionSubirVariosPisos;
	}

	/**
	 * Cuanto limita la salud del paciente para subir un piso por las escaleras (0 = mucho, 1 = poco o 2 = nada)
	 * 
	 * @return the LimitacionSubirUnPiso
	 */
	public int getLimitacionSubirUnPiso()
	{
		return limitacionSubirUnPiso;
	}

	/**
	 * Cuanto limita la salud del paciente para subir un piso por las escaleras (0 = mucho, 1 = poco o 2 = nada)
	 * 
	 * @param LimitacionSubirUnPiso the LimitacionSubirUnPiso to set
	 */
	public void setLimitacionSubirUnPiso(int LimitacionSubirUnPiso)
	{
		this.limitacionSubirUnPiso = LimitacionSubirUnPiso;
	}

	/**
	 * Cuanto limita la salud del paciente para agacharse o arrodillarse (0 = mucho, 1 = poco o 2 = nada)
	 * 
	 * @return the LimitacionAgacharse
	 */
	public int getLimitacionAgacharse()
	{
		return limitacionAgacharse;
	}

	/**
	 * Cuanto limita la salud del paciente para agacharse o arrodillarse (0 = mucho, 1 = poco o 2 = nada)
	 * 
	 * @param LimitacionAgacharse the LimitacionAgacharse to set
	 */
	public void setLimitacionAgacharse(int LimitacionAgacharse)
	{
		this.limitacionAgacharse = LimitacionAgacharse;
	}

	/**
	 * Cuanto limita la salud del paciente para caminar 1 Km o más (0 = mucho, 1 = poco o 2 = nada)
	 * 
	 * @return the LimitacionCaminarKm
	 */
	public int getLimitacionCaminarKm()
	{
		return limitacionCaminarKm;
	}

	/**
	 * Cuanto limita la salud del paciente para caminar 1 Km o más (0 = mucho, 1 = poco o 2 = nada)
	 * 
	 * @param LimitacionCaminarKm the LimitacionCaminarKm to set
	 */
	public void setLimitacionCaminarKm(int LimitacionCaminarKm)
	{
		this.limitacionCaminarKm = LimitacionCaminarKm;
	}

	/**
	 * Cuanto limita la salud del paciente para caminar varios cientos de metros (0 = mucho, 1 = poco o 2 = nada)
	 * 
	 * @return the LimitacionCaminarCientosMetros
	 */
	public int getLimitacionCaminarCientosMetros()
	{
		return limitacionCaminarCientosMetros;
	}

	/**
	 * Cuanto limita la salud del paciente para caminar varios cientos de metros (0 = mucho, 1 = poco o 2 = nada)
	 * 
	 * @param LimitacionCaminarCientosMetros the LimitacionCaminarCientosMetros to set
	 */
	public void setLimitacionCaminarCientosMetros(int LimitacionCaminarCientosMetros)
	{
		this.limitacionCaminarCientosMetros = LimitacionCaminarCientosMetros;
	}

	/**
	 * Cuanto limita la salud del paciente para caminar 100 metros (0 = mucho, 1 = poco o 2 = nada)
	 * 
	 * @return the LimitacionCaminar100m
	 */
	public int getLimitacionCaminar100m()
	{
		return limitacionCaminar100m;
	}

	/**
	 * Cuanto limita la salud del paciente para caminar 100 metros (0 = mucho, 1 = poco o 2 = nada)
	 * 
	 * @param LimitacionCaminar100m the LimitacionCaminar100m to set
	 */
	public void setLimitacionCaminar100m(int LimitacionCaminar100m)
	{
		this.limitacionCaminar100m = LimitacionCaminar100m;
	}

	/**
	 * Cuanto limita la salud del paciente para lavarse o vestirse por sí mismo (0 = mucho, 1 = poco o 2 = nada)
	 * 
	 * @return the LimitacionVestirse
	 */
	public int getLimitacionVestirse()
	{
		return limitacionVestirse;
	}

	/**
	 * Cuanto limita la salud del paciente para lavarse o vestirse por sí mismo (0 = mucho, 1 = poco o 2 = nada)
	 * 
	 * @param LimitacionVestirse the LimitacionVestirse to set
	 */
	public void setLimitacionVestirse(int LimitacionVestirse)
	{
		this.limitacionVestirse = LimitacionVestirse;
	}

	/**
	 * Indica si el paciente tuvo que reducir el tiempo de trabajo debido a problemas de salud
	 * 
	 * @return the ReducirTiempoTrabajoProblemasSalud
	 */
	public boolean isReducirTiempoTrabajoProblemasSalud()
	{
		return reducirTiempoTrabajoProblemasSalud;
	}

	/**
	 * Indica si el paciente tuvo que reducir el tiempo de trabajo debido a problemas de salud
	 * 
	 * @param ReducirTiempoTrabajoProblemasSalud the ReducirTiempoTrabajoProblemasSalud to set
	 */
	public void setReducirTiempoTrabajoProblemasSalud(boolean ReducirTiempoTrabajoProblemasSalud)
	{
		this.reducirTiempoTrabajoProblemasSalud = ReducirTiempoTrabajoProblemasSalud;
	}

	/**
	 * Indica si el paciente no pudo trabajar con una buena productividad debido a problemas de salud
	 * 
	 * @return the PocaProductividadProblemasSalud
	 */
	public boolean isPocaProductividadProblemasSalud()
	{
		return pocaProductividadProblemasSalud;
	}

	/**
	 * Indica si el paciente no pudo trabajar con una buena productividad debido a problemas de salud
	 * 
	 * @param PocaProductividadProblemasSalud the PocaProductividadProblemasSalud to set
	 */
	public void setPocaProductividadProblemasSalud(boolean PocaProductividadProblemasSalud)
	{
		this.pocaProductividadProblemasSalud = PocaProductividadProblemasSalud;
	}

	/**
	 * Indica si el paciente tuvo que dejar de realizar algunas tareas debido a problemas de salud
	 * 
	 * @return the AbandonoTareasProblemasSalud
	 */
	public boolean isAbandonoTareasProblemasSalud()
	{
		return abandonoTareasProblemasSalud;
	}

	/**
	 * Indica si el paciente tuvo que dejar de realizar algunas tareas debido a problemas de salud
	 * 
	 * @param AbandonoTareasProblemasSalud the AbandonoTareasProblemasSalud to set
	 */
	public void setAbandonoTareasProblemasSalud(boolean AbandonoTareasProblemasSalud)
	{
		this.abandonoTareasProblemasSalud = AbandonoTareasProblemasSalud;
	}

	/**
	 * Indica si el paciente se encontró con dificultades a la hora de realizar su trabajo debido a problemas de salud
	 * 
	 * @return the DificultadesTrabajoProblemasSalud
	 */
	public boolean isDificultadesTrabajoProblemasSalud()
	{
		return dificultadesTrabajoProblemasSalud;
	}

	/**
	 * Indica si el paciente se encontró con dificultades a la hora de realizar su trabajo debido a problemas de salud
	 * 
	 * @param DificultadesTrabajoProblemasSalud the DificultadesTrabajoProblemasSalud to set
	 */
	public void setDificultadesTrabajoProblemasSalud(boolean DificultadesTrabajoProblemasSalud)
	{
		this.dificultadesTrabajoProblemasSalud = DificultadesTrabajoProblemasSalud;
	}

	/**
	 * Indica si el paciente tuvo que reducir el tiempo de trabajo debido a problemas emocionales
	 * 
	 * @return the ReducirTiempoTrabajoProblemasEmocionales
	 */
	public boolean isReducirTiempoTrabajoProblemasEmocionales()
	{
		return reducirTiempoTrabajoProblemasEmocionales;
	}

	/**
	 * Indica si el paciente tuvo que reducir el tiempo de trabajo debido a problemas emocionales
	 * 
	 * @param ReducirTiempoTrabajoProblemasEmocionales the ReducirTiempoTrabajoProblemasEmocionales to set
	 */
	public void setReducirTiempoTrabajoProblemasEmocionales(boolean ReducirTiempoTrabajoProblemasEmocionales)
	{
		this.reducirTiempoTrabajoProblemasEmocionales = ReducirTiempoTrabajoProblemasEmocionales;
	}

	/**
	 * Indica si el paciente no pudo trabajar con una buena productividad debido a problemas emocionales
	 * 
	 * @return the PocaProductividadProblemasEmocionales
	 */
	public boolean isPocaProductividadProblemasEmocionales()
	{
		return pocaProductividadProblemasEmocionales;
	}

	/**
	 * Indica si el paciente no pudo trabajar con una buena productividad debido a problemas emocionales
	 * 
	 * @param PocaProductividadProblemasEmocionales the PocaProductividadProblemasEmocionales to set
	 */
	public void setPocaProductividadProblemasEmocionales(boolean PocaProductividadProblemasEmocionales)
	{
		this.pocaProductividadProblemasEmocionales = PocaProductividadProblemasEmocionales;
	}

	/**
	 * Indica si el paciente trabajó poco cuidadosamente debido a problemas emocionales
	 * 
	 * @return the TrabajoPocoCuidadosoProblemasEmocionales
	 */
	public boolean isTrabajoPocoCuidadosoProblemasEmocionales()
	{
		return trabajoPocoCuidadosoProblemasEmocionales;
	}

	/**
	 * Indica si el paciente trabajó poco cuidadosamente debido a problemas emocionales
	 * 
	 * @param TrabajoPocoCuidadosoProblemasEmocionales the TrabajoPocoCuidadosoProblemasEmocionales to set
	 */
	public void setTrabajoPocoCuidadosoProblemasEmocionales(boolean TrabajoPocoCuidadosoProblemasEmocionales)
	{
		this.trabajoPocoCuidadosoProblemasEmocionales = TrabajoPocoCuidadosoProblemasEmocionales;
	}

	/**
	 * Cuanto ha dificultado las relaciones sociales del paciente su enfermedad (0 = nada,1 = un poco,2 = regular, 3 =
	 * bastante o 4 = mucho)
	 * 
	 * @return the DificultadesSociales
	 */
	public int getDificultadesSociales()
	{
		return dificultadesSociales;
	}

	/**
	 * Cuanto ha dificultado las relaciones sociales del paciente su enfermedad (0 = nada,1 = un poco,2 = regular, 3 =
	 * bastante o 4 = mucho)
	 * 
	 * @param DificultadesSociales the DificultadesSociales to set
	 */
	public void setDificultadesSociales(int DificultadesSociales)
	{
		this.dificultadesSociales = DificultadesSociales;
	}

	/**
	 * Indica si el paciente sufrió dolor en alguna parte de su cuerpo (0 = ninguno, 1 = muy poco, 2 = poco, 3 =
	 * moderado, 4 = mucho o 5 = muchísimo)
	 * 
	 * @return the SufrirDolores
	 */
	public int getSufrirDolores()
	{
		return sufrirDolores;
	}

	/**
	 * Indica si el paciente sufrió dolor en alguna parte de su cuerpo (0 = ninguno, 1 = muy poco, 2 = poco, 3 =
	 * moderado, 4 = mucho o 5 = muchísimo)
	 * 
	 * @param SufrirDolores the SufrirDolores to set
	 */
	public void setSufrirDolores(int SufrirDolores)
	{
		this.sufrirDolores = SufrirDolores;
	}

	/**
	 * Cuanto ha dificultado el dolor las tareas habituales del paciente (0 = nada, 1 = un poco, 2 = regular, 3 =
	 * bastante o 4 = mucho)
	 * 
	 * @return the DificultadesTareas
	 */
	public int getDificultadesTareas()
	{
		return dificultadesTareas;
	}

	/**
	 * Cuanto ha dificultado el dolor las tareas habituales del paciente (0 = nada, 1 = un poco, 2 = regular, 3 =
	 * bastante o 4 = mucho)
	 * 
	 * @param DificultadesTareas the DificultadesTareas to set
	 */
	public void setDificultadesTareas(int DificultadesTareas)
	{
		this.dificultadesTareas = DificultadesTareas;
	}

	/**
	 * Cuanto tiempo se ha sentido el paciente lleno de vitalidad (0 = siempre, 1 = casi siempre, 2 = muchas veces, 3 =
	 * alguna vez, 4 = casi nunca, 5 = nunca)
	 * 
	 * @return the TiempoLlenoVitalidad
	 */
	public int getTiempoLlenoVitalidad()
	{
		return tiempoLlenoVitalidad;
	}

	/**
	 * Cuanto tiempo se ha sentido el paciente lleno de vitalidad (0 = siempre, 1 = casi siempre, 2 = muchas veces, 3 =
	 * alguna vez, 4 = casi nunca, 5 = nunca)
	 * 
	 * @param TiempoLlenoVitalidad the TiempoLlenoVitalidad to set
	 */
	public void setTiempoLlenoVitalidad(int TiempoLlenoVitalidad)
	{
		this.tiempoLlenoVitalidad = TiempoLlenoVitalidad;
	}

	/**
	 * Cuanto tiempo se ha sentido nervioso el paciente (0 = siempre, 1 = casi siempre, 2 = muchas veces, 3 = alguna
	 * vez, 4 = casi nunca, 5 = nunca)
	 * 
	 * @return the TiempoNervioso
	 */
	public int getTiempoNervioso()
	{
		return tiempoNervioso;
	}

	/**
	 * Cuanto tiempo se ha sentido nervioso el paciente (0 = siempre, 1 = casi siempre, 2 = muchas veces, 3 = alguna
	 * vez, 4 = casi nunca, 5 = nunca)
	 * 
	 * @param TiempoNervioso the TiempoNervioso to set
	 */
	public void setTiempoNervioso(int TiempoNervioso)
	{
		this.tiempoNervioso = TiempoNervioso;
	}

	/**
	 * Cuanto tiempo se ha sentido tranquilo el paciente (0 = siempre, 1 = casi siempre, 2 = muchas veces, 3 = alguna
	 * vez, 4 = casi nunca, 5 = nunca)
	 * 
	 * @return the TiempoTranquilo
	 */
	public int getTiempoTranquilo()
	{
		return tiempoTranquilo;
	}

	/**
	 * Cuanto tiempo se ha sentido tranquilo el paciente (0 = siempre, 1 = casi siempre, 2 = muchas veces, 3 = alguna
	 * vez, 4 = casi nunca, 5 = nunca)
	 * 
	 * @param TiempoTranquilo the TiempoTranquilo to set
	 */
	public void setTiempoTranquilo(int TiempoTranquilo)
	{
		this.tiempoTranquilo = TiempoTranquilo;
	}

	/**
	 * Cuanto tiempo se ha sentido el paciente tan bajo de moral que nada podía animarlo (0 = siempre, 1 = casi siempre,
	 * 2 = muchas veces, 3 = alguna vez, 4 = casi nunca, 5 = nunca)
	 * 
	 * @return the TiempoBajoMoral
	 */
	public int getTiempoBajoMoral()
	{
		return tiempoBajoMoral;
	}

	/**
	 * Cuanto tiempo se ha sentido el paciente tan bajo de moral que nada podía animarlo (0 = siempre, 1 = casi siempre,
	 * 2 = muchas veces, 3 = alguna vez, 4 = casi nunca, 5 = nunca)
	 * 
	 * @param TiempoBajoMoral the TiempoBajoMoral to set
	 */
	public void setTiempoBajoMoral(int TiempoBajoMoral)
	{
		this.tiempoBajoMoral = TiempoBajoMoral;
	}

	/**
	 * Cuanto tiempo se ha sentido el paciente lleno de energía (0 = siempre, 1 = casi siempre, 2 = muchas veces, 3 =
	 * alguna vez, 4 = casi nunca, 5 = nunca)
	 * 
	 * @return the TiempoLlenoEnergia
	 */
	public int getTiempoLlenoEnergia()
	{
		return tiempoLlenoEnergia;
	}

	/**
	 * Cuanto tiempo se ha sentido el paciente lleno de energía (0 = siempre, 1 = casi siempre, 2 = muchas veces, 3 =
	 * alguna vez, 4 = casi nunca, 5 = nunca)
	 * 
	 * @param TiempoLlenoEnergia the TiempoLlenoEnergia to set
	 */
	public void setTiempoLlenoEnergia(int TiempoLlenoEnergia)
	{
		this.tiempoLlenoEnergia = TiempoLlenoEnergia;
	}

	/**
	 * Cuanto tiempo se ha sentido desanimado el paciente (0 = siempre, 1 = casi siempre, 2 = muchas veces, 3 = alguna
	 * vez, 4 = casi nunca, 5 = nunca)
	 * 
	 * @return the TiempoDesanimado
	 */
	public int getTiempoDesanimado()
	{
		return tiempoDesanimado;
	}

	/**
	 * Cuanto tiempo se ha sentido desanimado el paciente (0 = siempre, 1 = casi siempre, 2 = muchas veces, 3 = alguna
	 * vez, 4 = casi nunca, 5 = nunca)
	 * 
	 * @param TiempoDesanimado the TiempoDesanimado to set
	 */
	public void setTiempoDesanimado(int TiempoDesanimado)
	{
		this.tiempoDesanimado = TiempoDesanimado;
	}

	/**
	 * Cuanto tiempo se ha sentido agotado el paciente (0 = siempre, 1 = casi siempre, 2 = muchas veces, 3 = alguna vez,
	 * 4 = casi nunca, 5 = nunca)
	 * 
	 * @return the TiempoAgotado
	 */
	public int getTiempoAgotado()
	{
		return tiempoAgotado;
	}

	/**
	 * Cuanto tiempo se ha sentido agotado el paciente (0 = siempre, 1 = casi siempre, 2 = muchas veces, 3 = alguna vez,
	 * 4 = casi nunca, 5 = nunca)
	 * 
	 * @param TiempoAgotado the TiempoAgotado to set
	 */
	public void setTiempoAgotado(int TiempoAgotado)
	{
		this.tiempoAgotado = TiempoAgotado;
	}

	/**
	 * Cuanto tiempo se ha sentido feliz el paciente (0 = siempre, 1 = casi siempre, 2 = muchas veces, 3 = alguna vez, 4
	 * = casi nunca, 5 = nunca)
	 * 
	 * @return the TiempoFeliz
	 */
	public int getTiempoFeliz()
	{
		return tiempoFeliz;
	}

	/**
	 * Cuanto tiempo se ha sentido feliz el paciente (0 = siempre, 1 = casi siempre, 2 = muchas veces, 3 = alguna vez, 4
	 * = casi nunca, 5 = nunca)
	 * 
	 * @param TiempoFeliz the TiempoFeliz to set
	 */
	public void setTiempoFeliz(int TiempoFeliz)
	{
		this.tiempoFeliz = TiempoFeliz;
	}

	/**
	 * Cuanto tiempo se ha sentido cansado el paciente (0 = siempre, 1 = casi siempre, 2 = muchas veces, 3 = alguna vez,
	 * 4 = casi nunca, 5 = nunca)
	 * 
	 * @return the TiempoCansado
	 */
	public int getTiempoCansado()
	{
		return tiempoCansado;
	}

	/**
	 * Cuanto tiempo se ha sentido cansado el paciente (0 = siempre, 1 = casi siempre, 2 = muchas veces, 3 = alguna vez,
	 * 4 = casi nunca, 5 = nunca)
	 * 
	 * @param TiempoCansado the TiempoCansado to set
	 */
	public void setTiempoCansado(int TiempoCansado)
	{
		this.tiempoCansado = TiempoCansado;
	}

	/**
	 * Con qué frecuencia la salud física del paciente ha dificultado sus relaciones sociales (0 = siempre, 1 = casi
	 * siempre, 2 = muchas veces, 3 = alguna vez, 4 = pocas veces, 5 = casi nunca, 6 = nunca)
	 * 
	 * @return the DificultadesRelacionesSociales
	 */
	public int getDificultadesRelacionesSociales()
	{
		return dificultadesRelacionesSociales;
	}

	/**
	 * Con qué frecuencia la salud física del paciente ha dificultado sus relaciones sociales (0 = siempre, 1 = casi
	 * siempre, 2 = muchas veces, 3 = alguna vez, 4 = pocas veces, 5 = casi nunca, 6 = nunca)
	 * 
	 * @param DificultadesRelacionesSociales the DificultadesRelacionesSociales to set
	 */
	public void setDificultadesRelacionesSociales(int DificultadesRelacionesSociales)
	{
		this.dificultadesRelacionesSociales = DificultadesRelacionesSociales;
	}

	/**
	 * Indica si el paciente cree que se pone enfermo más fácilmente que el resto de la gente (0 = totalmente cierto, 1
	 * = bastante cierto, 2 = no sabe, 3 = bastante falso, 4 = totalmente falso)
	 * 
	 * @return the FacilidadPonerseEnfermo
	 */
	public int getFacilidadPonerseEnfermo()
	{
		return facilidadPonerseEnfermo;
	}

	/**
	 * Indica si el paciente cree que se pone enfermo más fácilmente que el resto de la gente (0 = totalmente cierto, 1
	 * = bastante cierto, 2 = no sabe, 3 = bastante falso, 4 = totalmente falso)
	 * 
	 * @param FacilidadPonerseEnfermo the FacilidadPonerseEnfermo to set
	 */
	public void setFacilidadPonerseEnfermo(int FacilidadPonerseEnfermo)
	{
		this.facilidadPonerseEnfermo = FacilidadPonerseEnfermo;
	}

	/**
	 * Indica si el paciente cree que está tan sano como cualquier otra persona (0 = totalmente cierto, 1 = bastante
	 * cierto, 2 = no sabe, 3 = bastante falso, 4 = totalmente falso)
	 * 
	 * @return the SaludNormal
	 */
	public int getSaludNormal()
	{
		return saludNormal;
	}

	/**
	 * Indica si el paciente cree que está tan sano como cualquier otra persona (0 = totalmente cierto, 1 = bastante
	 * cierto, 2 = no sabe, 3 = bastante falso, 4 = totalmente falso)
	 * 
	 * @param SaludNormal the SaludNormal to set
	 */
	public void setSaludNormal(int SaludNormal)
	{
		this.saludNormal = SaludNormal;
	}

	/**
	 * Indica si el paciente cree que su salud va a empeorar (0 = totalmente cierto, 1 = bastante cierto, 2 = no sabe, 3
	 * = bastante falso, 4 = totalmente falso)
	 * 
	 * @return the EmpeorarSalud
	 */
	public int getEmpeorarSalud()
	{
		return empeorarSalud;
	}

	/**
	 * Indica si el paciente cree que su salud va a empeorar (0 = totalmente cierto, 1 = bastante cierto, 2 = no sabe, 3
	 * = bastante falso, 4 = totalmente falso)
	 * 
	 * @param EmpeorarSalud the EmpeorarSalud to set
	 */
	public void setEmpeorarSalud(int EmpeorarSalud)
	{
		this.empeorarSalud = EmpeorarSalud;
	}

	/**
	 * Indica si el paciente cree que su salud es excelente (0 = totalmente cierto, 1 = bastante cierto, 2 = no sabe, 3
	 * = bastante falso, 4 = totalmente falso)
	 * 
	 * @return the SaludExcelente
	 */
	public int getSaludExcelente()
	{
		return saludExcelente;
	}

	/**
	 * Indica si el paciente cree que su salud es excelente (0 = totalmente cierto, 1 = bastante cierto, 2 = no sabe, 3
	 * = bastante falso, 4 = totalmente falso)
	 * 
	 * @param SaludExcelente the SaludExcelente to set
	 */
	public void setSaludExcelente(int SaludExcelente)
	{
		this.saludExcelente = SaludExcelente;
	}

	@Override
	public boolean equals(Object o)
	{
		return o instanceof SF36Result && o.hashCode() == this.hashCode();
	}

	@Override
	public int hashCode()
	{
		int hash = 7;
		hash = 29 * hash + this.salud;
		hash = 29 * hash + this.comparacionSalud;
		hash = 29 * hash + this.limitacionEsfuerzosIntensos;
		hash = 29 * hash + this.limitacionEsfuerzosModerados;
		hash = 29 * hash + this.limitacionLlevarBolsaCompra;
		hash = 29 * hash + this.limitacionSubirVariosPisos;
		hash = 29 * hash + this.limitacionSubirUnPiso;
		hash = 29 * hash + this.limitacionAgacharse;
		hash = 29 * hash + this.limitacionCaminarKm;
		hash = 29 * hash + this.limitacionCaminarCientosMetros;
		hash = 29 * hash + this.limitacionCaminar100m;
		hash = 29 * hash + this.limitacionVestirse;
		hash = 29 * hash + (this.reducirTiempoTrabajoProblemasSalud ? 1 : 0);
		hash = 29 * hash + (this.pocaProductividadProblemasSalud ? 1 : 0);
		hash = 29 * hash + (this.abandonoTareasProblemasSalud ? 1 : 0);
		hash = 29 * hash + (this.dificultadesTrabajoProblemasSalud ? 1 : 0);
		hash = 29 * hash + (this.reducirTiempoTrabajoProblemasEmocionales ? 1 : 0);
		hash = 29 * hash + (this.pocaProductividadProblemasEmocionales ? 1 : 0);
		hash = 29 * hash + (this.trabajoPocoCuidadosoProblemasEmocionales ? 1 : 0);
		hash = 29 * hash + this.dificultadesSociales;
		hash = 29 * hash + this.sufrirDolores;
		hash = 29 * hash + this.dificultadesTareas;
		hash = 29 * hash + this.tiempoLlenoVitalidad;
		hash = 29 * hash + this.tiempoNervioso;
		hash = 29 * hash + this.tiempoTranquilo;
		hash = 29 * hash + this.tiempoBajoMoral;
		hash = 29 * hash + this.tiempoLlenoEnergia;
		hash = 29 * hash + this.tiempoDesanimado;
		hash = 29 * hash + this.tiempoAgotado;
		hash = 29 * hash + this.tiempoFeliz;
		hash = 29 * hash + this.tiempoCansado;
		hash = 29 * hash + this.dificultadesRelacionesSociales;
		hash = 29 * hash + this.facilidadPonerseEnfermo;
		hash = 29 * hash + this.saludNormal;
		hash = 29 * hash + this.empeorarSalud;
		hash = 29 * hash + this.saludExcelente;
		return hash;
	}
}
