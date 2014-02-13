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
		maxWaterLitersPerDay = PROPERTY_INVALID_VALUE;
		// Propiedades de peso
		weightVariationInterval = PROPERTY_INVALID_VALUE;
		weightVariation = PROPERTY_INVALID_VALUE;
		maxWeight = PROPERTY_INVALID_VALUE;

		// Propiedades de tensión
		minDiastolic = PROPERTY_INVALID_VALUE;
		maxDiastolic = PROPERTY_INVALID_VALUE;
		minSystolic = PROPERTY_INVALID_VALUE;
		maxSystolic = PROPERTY_INVALID_VALUE;
		minHeartRate = PROPERTY_INVALID_VALUE;
		maxHeartRate = PROPERTY_INVALID_VALUE;

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

	@Element(name = "maxWaterLitersPerDay", required = false)
	private float maxWaterLitersPerDay;

	/**
	 * This variable represents days
	 */
	@Element(name = "weightVariationInterval", required = false)
	private int weightVariationInterval;

	@Element(name = "weightVariation", required = false)
	private float weightVariation;

	@Element(name = "maxWeight", required = false)
	private float maxWeight;

	@Element(name = "minDiastolic", required = false)
	private int minDiastolic;

	@Element(name = "maxDiastolic", required = false)
	private int maxDiastolic;

	@Element(name = "minSystolic", required = false)
	private int minSystolic;

	@Element(name = "maxSystolic", required = false)
	private int maxSystolic;

	@Element(name = "minHeartRate", required = false)
	private int minHeartRate;

	@Element(name = "maxHeartRate", required = false)
	private int maxHeartRate;

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

	public float getMaxWaterLitersPerDay()
	{
		return maxWaterLitersPerDay;
	}

	public void setMaxWaterLitersPerDay(float maxWaterLitersPerDay)
	{
		this.maxWaterLitersPerDay = maxWaterLitersPerDay;
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

	public int getMinDiastolic()
	{
		return minDiastolic;
	}

	public void setMinDiastolic(int minDiastolic)
	{
		this.minDiastolic = minDiastolic;
	}

	public int getMaxDiastolic()
	{
		return maxDiastolic;
	}

	public void setMaxDiastolic(int maxDiastolic)
	{
		this.maxDiastolic = maxDiastolic;
	}

	public int getMinSystolic()
	{
		return minSystolic;
	}

	public void setMinSystolic(int minSystolic)
	{
		this.minSystolic = minSystolic;
	}

	public int getMaxSystolic()
	{
		return maxSystolic;
	}

	public void setMaxSystolic(int maxSystolic)
	{
		this.maxSystolic = maxSystolic;
	}

	public int getMinHeartRate()
	{
		return minHeartRate;
	}

	public void setMinHeartRate(int minHeartRate)
	{
		this.minHeartRate = minHeartRate;
	}

	public int getMaxHeartRate()
	{
		return maxHeartRate;
	}

	public void setMaxHeartRate(int maxHeartRate)
	{
		this.maxHeartRate = maxHeartRate;
	}

}
