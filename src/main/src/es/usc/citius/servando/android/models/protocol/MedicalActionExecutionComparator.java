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
				comparison = Long.valueOf(x.getTimeWindow()).compareTo(Long.valueOf(y.getTimeWindow()));

				if (comparison == 0)
				{
					// TODO: Ollo, insertado para as accións que únicamente se diferencian por parámetros
					comparison = x.toString().equals(y.toString()) ? 0 : 1;
				}

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
