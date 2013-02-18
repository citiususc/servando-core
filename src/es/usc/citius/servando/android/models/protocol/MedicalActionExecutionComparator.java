package es.usc.citius.servando.android.models.protocol;

import java.util.Comparator;

public class MedicalActionExecutionComparator implements Comparator<MedicalActionExecution> {

	private static MedicalActionExecutionComparator instance = new MedicalActionExecutionComparator();

	private MedicalActionExecutionComparator()
	{
	}

	@Override
	public int compare(MedicalActionExecution x, MedicalActionExecution y)
	{
		System.out.println("Comparing " + x.getAction().getId() + ", " + y.getAction().getId());
		// Empezamos comparando las fechas de inicio.
		int comparison = x.getStartDate().compareTo(y.getStartDate());

		System.out.println(comparison);

		if (comparison == 0)
		{
			// Como las fechas de inicio son iguales, comparamos la prioridad (cambiando el signo, pues a mayor
			// prioridad, menor valor en la comparación)
			comparison = -x.getPriority().compareTo(y.getPriority());

			System.out.println(comparison);

			// Si la prioridad también es la misma, entonces comparamos la ventana temporal
			if (comparison == 0)
			{
				comparison = new Long(x.getTimeWindow()).compareTo(new Long(y.getTimeWindow()));

				System.out.println(comparison);
			}
		}

		System.out.println("Result: " + comparison);

		return comparison;
	}

	/**
	 * @return the instance
	 */
	public static MedicalActionExecutionComparator getInstance()
	{
		return instance;
	}

}
