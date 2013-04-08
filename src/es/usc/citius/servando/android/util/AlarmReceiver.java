package es.usc.citius.servando.android.util;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import es.usc.citius.servando.android.ServandoPlatformFacade;
import es.usc.citius.servando.android.agenda.ProtocolEngine;
import es.usc.citius.servando.android.logging.ILog;
import es.usc.citius.servando.android.logging.ServandoLoggerFactory;

public class AlarmReceiver extends BroadcastReceiver {

	private ILog log = ServandoLoggerFactory.getLogger(AlarmReceiver.class);

	@Override
	public void onReceive(Context context, Intent intent)
	{
		if (ServandoPlatformFacade.isStarted())
		{
			int taskId = intent.getIntExtra("taskid", -1);
			if (taskId != -1)
			{
				log.debug("Alarm broadcast received (task: " + taskId + ")");
				ProtocolEngine.getInstance().runTask(taskId);
			}
		}
	}
}