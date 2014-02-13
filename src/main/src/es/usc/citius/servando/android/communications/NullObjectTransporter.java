/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package es.usc.citius.servando.android.communications;



/**
 * Clase encargada de realizar peticiones al servicio Web situado en los DSR. Proporciona la funcionalidad de tipo
 * cliente de la librería.
 * 
 * @todo por ahora esta clase es un prototipo para garantizar la compatibilidad con android. Necesita numerosas pruebas
 *       y refactorizaciones.
 * @author Tomás Teijeiro Campo
 */
public class NullObjectTransporter extends ObjectTransporter {

	@Override
	public boolean send(Object obj)
	{
		// do nothing
		return true;
	}

	@Override
	public void iSend(Object obj)
	{
		// do nothing
	}

	@Override
	public Object sendReceive(Object obj) throws Exception
	{
		return null;
	}

	@Override
	public void reliableSend(Object obj, long timeout)
	{

	}

}
