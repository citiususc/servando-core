package es.usc.citius.servando.android.util;

import android.content.Context;
import android.widget.Toast;

public class UiUtils {

	public static void showToast(String message, Context ctx)
	{
		Toast.makeText(ctx, message, Toast.LENGTH_SHORT).show();
	}

}
