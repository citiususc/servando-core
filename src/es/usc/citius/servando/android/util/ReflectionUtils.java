/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package es.usc.citius.servando.android.util;

import java.util.StringTokenizer;

/**
 * Esta clase define diversas utilidades para resolver más facilmente algunos problemas relacionados con la reflexión, y
 * la carga dinámica de clases.
 * 
 * @author Tomás Teijeiro Campo
 */
public class ReflectionUtils {

	/**
	 * Este método intenta obtener una clase a partir de su nombre, con cierta insensibilidad a mayúsculas y minúsculas.
	 * Concretamente, sigue los siguientes pasos: - Intenta cargar la clase cuyo nombre coincida con el suministrado -
	 * Si no lo consigue, intenta cargar la clase cambiando la primera letra del nombre de la clase (no del paquete) (si
	 * estaba en mayúscula, la pasa a minúscula, y si estaba en minúscula, la pasa a mayúscula) - Si aún así falla,
	 * cambia la primera letra del nombre de cada paquete y subpaquete que incluya a la clase. - Si aún no logra cargar
	 * la clase, combina los dos anteriores pasos. - Finalmente, intenta cargar la clase siguiendo la convención de
	 * nombres de Java (los paquetes en letra minúscula, y las clases en letra mayúscula)
	 * 
	 * @param name
	 * @return
	 * @throws ClassNotFoundException Si finalmente no somos capaces de cargar la clase con ese nombre
	 */
	public static Class<?> classForNameIgnoreCase(String name) throws ClassNotFoundException
	{
		try
		{
			// En primer lugar intentamos cargar la clase cuyo nombre coincida con el suministrado
			return Class.forName(name);
		} catch (ClassNotFoundException ex)
		{
			String newName = "";
			// Hemos fallado, intentamos el segundo criterio
			int nameIndex = name.lastIndexOf('.') + 1;
			newName = name.substring(0, nameIndex) + changeFirstChar(name.substring(nameIndex));
			try
			{
				// Intentamos cargar la clase con esta modificación
				return Class.forName(newName);
			} catch (ClassNotFoundException ex1)
			{
				newName = "";
				// Hemos fallado. Intentamos el tercer criterio.
				// Separamos la cadena por los caracteres de delimitación de paquetes '.'
				StringTokenizer tokenizer = new StringTokenizer(name, ".");
				// Cambiamos el nombre de cada paquete (pero no de la clase)
				while (tokenizer.countTokens() > 1)
				{
					newName += changeFirstChar(tokenizer.nextToken()) + ".";
				}
				// Añadimos el último token sin modificar
				newName += tokenizer.nextToken();
				try
				{
					// Volvemos a intentar cargar la clase
					return Class.forName(newName);
				} catch (ClassNotFoundException ex2)
				{
					// Hemos fallado de nuevo. Intentamos el cuarto criterio
					newName = "";
					// Separamos la cadena por los caracteres de delimitación de paquetes '.'
					tokenizer = new StringTokenizer(name, ".");
					// Cambiamos el nombre de cada paquete y de la clase
					while (tokenizer.hasMoreTokens())
					{
						newName += changeFirstChar(tokenizer.nextToken()) + ".";
					}
					// Eliminamos el último punto
					newName = newName.substring(0, newName.length() - 1);
					try
					{
						// Volvemos a intentar cargar la clase
						return Class.forName(newName);
					} catch (ClassNotFoundException ex3)
					{
						// Intentamos el último criterio (convención de nombres de Java)
						newName = "";
						// Separamos la cadena por los caracteres de delimitación de paquetes '.'
						tokenizer = new StringTokenizer(name, ".");
						// Cambiamos el nombre de cada paquete y de la clase
						while (tokenizer.hasMoreTokens())
						{
							String token = tokenizer.nextToken();
							// Si no es el último token, estamos ante el nombre de un paquete
							if (tokenizer.hasMoreTokens())
							{
								newName += Character.toLowerCase(token.charAt(0)) + token.substring(1) + '.';
							} else
							{
								newName += Character.toUpperCase(token.charAt(0)) + token.substring(1);
							}
						}
						// Si no lo conseguimos ahora, la excepción será lanzada
						return Class.forName(newName);
					}
				}
			}
		}
	}

	/**
	 * Cambia la primera letra de la cadena suministrada. Si es mayúscula, la pasa a minúscula, y si es minúscula, la
	 * pasa a mayúscula.
	 * 
	 * @param string cadena a modificar
	 * @return cadena modificada
	 */
	private static String changeFirstChar(String string)
	{
		if (Character.isLowerCase(string.charAt(0)))
		{
			return Character.toUpperCase(string.charAt(0)) + string.substring(1);
		} else
		{
			return Character.toLowerCase(string.charAt(0)) + string.substring(1);
		}
	}
}
