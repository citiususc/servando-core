/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package es.usc.citius.servando.android.models.protocol;

/**
 * Estados en los que se puede encontrar una actuación médica.
 * 
 * @author tomas
 */
public enum MedicalActionState
{
	/**
	 * Indica que la actuación médica aún no ha sido iniciada
	 */
	NotStarted,
	/**
	 * Indica que la actuación médica se ha completado correctamente
	 */
	Completed,
	/**
	 * Indica que la actuación médica está en ejecución, pero incompleta
	 */
	Uncompleted,
	/**
	 * Indica que la actuación médica ha fallado
	 */
	Failed
}
