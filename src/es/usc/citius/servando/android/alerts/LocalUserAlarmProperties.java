package es.usc.citius.servando.android.alerts;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.Root;

/**
 * This class store the local user params configuration that allow define alarms for the patient.
 * 
 * @author pablojose.viqueira
 * 
 */
@Root(name = "LocalUserAlarmProperties")
public class LocalUserAlarmProperties {

	public static final int PROPERTY_INVALID_VALUE = -1;

	public LocalUserAlarmProperties()
	{
		/*
		 * Iniciamos todas las propiedades a un valor no válido, de esta forma si en el fichero no se encuentra esa
		 * propiedad cuando intentemos acceder a ella sabremos si el valor es válido o no comparándolo con el valor no
		 * válido
		 */
		// Propiedades del test de buenas prácticas
		maxCigarettesPerDay = PROPERTY_INVALID_VALUE;
		maxConsecutiveDaysHavingSalt = PROPERTY_INVALID_VALUE;
		maxConsecutiveDaysDrinkingAlcohol = PROPERTY_INVALID_VALUE;
		maxConsecutiveDaysWithoutPracticeExercise = PROPERTY_INVALID_VALUE;
		maxWaterGlassesPerDay = PROPERTY_INVALID_VALUE;
		// Propiedades de peso
		weightVariationInterval = PROPERTY_INVALID_VALUE;
		weightVariation = PROPERTY_INVALID_VALUE;
		maxWeight = PROPERTY_INVALID_VALUE;

	}

	/**
	 * Good Practice Test Properties
	 */
	/**
	 * Max number of cigarettes that the patient can smoke in a day
	 */
	@Element(name = "maxCigarettesPerDay", required = false)
	private int maxCigarettesPerDay;

	/**
	 * Max number of consecutive days that the patient can eat salt
	 */
	@Element(name = "maxConsecutiveDaysHavingSalt", required = false)
	private int maxConsecutiveDaysHavingSalt;

	/**
	 * Max number of consecutive days that the patient can drink alcohol
	 */
	@Element(name = "maxConsecutiveDaysDrinkingAlcohol", required = false)
	private int maxConsecutiveDaysDrinkingAlcohol;

	/**
	 * Max number of consecutive days that the patient can be without practice exercise
	 */
	@Element(name = "maxConsecutiveDaysWithoutPracticeExercise", required = false)
	private int maxConsecutiveDaysWithoutPracticeExercise;

	@Element(name = "maxWaterGlassesPerDay", required = false)
	private int maxWaterGlassesPerDay;

	/**
	 * This variable indicates the number of MEASUREMENTS used to evaluate the weight variation. So no dates are used to
	 * this function.
	 */
	@Element(name = "weightVariationInterval", required = false)
	private int weightVariationInterval;

	@Element(name = "weightVariation", required = false)
	private float weightVariation;

	@Element(name = "maxWeight", required = false)
	private float maxWeight;

	// setters and getters

	public int getMaxCigarettesPerDay()
	{
		return maxCigarettesPerDay;
	}

	public void setMaxCigarettesPerDay(int maxCigarettesPerDay)
	{
		this.maxCigarettesPerDay = maxCigarettesPerDay;
	}

	public int getMaxConsecutiveDaysHavingSalt()
	{
		return maxConsecutiveDaysHavingSalt;
	}

	public void setMaxConsecutiveDaysHavingSalt(int maxConsecutiveDaysHavingSalt)
	{
		this.maxConsecutiveDaysHavingSalt = maxConsecutiveDaysHavingSalt;
	}

	public int getMaxConsecutiveDaysDrinkingAlcohol()
	{
		return maxConsecutiveDaysDrinkingAlcohol;
	}

	public void setMaxConsecutiveDaysDrinkingAlcohol(int maxConsecutiveDaysDrinkingAlcohol)
	{
		this.maxConsecutiveDaysDrinkingAlcohol = maxConsecutiveDaysDrinkingAlcohol;
	}

	public int getMaxConsecutiveDaysWithoutPracticeExercise()
	{
		return maxConsecutiveDaysWithoutPracticeExercise;
	}

	public void setMaxConsecutiveDaysWithoutPracticeExercise(int maxConsecutiveDaysWithoutPracticeExercise)
	{
		this.maxConsecutiveDaysWithoutPracticeExercise = maxConsecutiveDaysWithoutPracticeExercise;
	}

	public int getMaxWaterGlassesPerDay()
	{
		return maxWaterGlassesPerDay;
	}

	public void setMaxWaterGlassesPerDay(int maxWaterGlassesPerDay)
	{
		this.maxWaterGlassesPerDay = maxWaterGlassesPerDay;
	}

	public int getWeightVariationInterval()
	{
		return weightVariationInterval;
	}

	public void setWeightVariationInterval(int weightVariationInterval)
	{
		this.weightVariationInterval = weightVariationInterval;
	}

	public float getWeightVariation()
	{
		return weightVariation;
	}

	public void setWeightVariation(float weightVariation)
	{
		this.weightVariation = weightVariation;
	}

	public float getMaxWeight()
	{
		return maxWeight;
	}

	public void setMaxWeight(float maxWeight)
	{
		this.maxWeight = maxWeight;
	}

}
