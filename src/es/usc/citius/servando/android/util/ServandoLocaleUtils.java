package es.usc.citius.servando.android.util;

import java.util.Locale;

public class ServandoLocaleUtils {

	public static String getDeviceLocale()
	{
		return Locale.getDefault().getLanguage();
	}

	public static String getValidLocale(String locale)
	{

		if ("en".equalsIgnoreCase(locale))
		{
			return "en";
		} else if ("es".equalsIgnoreCase("es"))
		{
			return "es";
		} else
		{
			return getDeviceLocale();
		}
	}

}
