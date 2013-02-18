package es.usc.citius.servando.android.device;

import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.BatteryManager;

public class BatteryMonitor {

	private int level;
	private boolean charging;
	private String status;

	private long lastUpdated;
	private long updatePeriod;

	public void update(Context ctx)
	{
		IntentFilter filter = new IntentFilter(Intent.ACTION_BATTERY_CHANGED);
		Intent batteryStatus = ctx.registerReceiver(null, filter);
		int maxLevel = batteryStatus.getIntExtra(BatteryManager.EXTRA_SCALE, -1);
		int currLevel = batteryStatus.getIntExtra(BatteryManager.EXTRA_LEVEL, -1);
		int stat = batteryStatus.getIntExtra(BatteryManager.EXTRA_STATUS, -1);
		charging = stat == BatteryManager.BATTERY_STATUS_CHARGING;
		level = currLevel / maxLevel;
		status = getStatus(stat);
	}

	private String getStatus(int status)
	{
		String strStatus = "UNKNOWN";

		switch (status) {
		case BatteryManager.BATTERY_STATUS_CHARGING:
			strStatus = "BATTERY_STATUS_CHARGING";
			break;
		case BatteryManager.BATTERY_STATUS_DISCHARGING:
			strStatus = "BATTERY_STATUS_DISCHARGING";
			break;
		case BatteryManager.BATTERY_STATUS_FULL:
			strStatus = "BATTERY_STATUS_FULL";
			break;
		case BatteryManager.BATTERY_STATUS_NOT_CHARGING:
			strStatus = "BATTERY_STATUS_NOT_CHARGING";
			break;
		default:
			break;
		}
		return strStatus;
	}

	private void updateIfNeeded(Context ctx)
	{
		long currentTime = System.currentTimeMillis();
		if (currentTime > lastUpdated + updatePeriod)
		{
			update(ctx);
		}
	}

	public int getLevel(Context ctx)
	{
		updateIfNeeded(ctx);
		return level;
	}

	public boolean isCharging(Context ctx)
	{
		updateIfNeeded(ctx);
		return charging;
	}

	@Override
	public String toString()
	{

		StringBuilder builder = new StringBuilder();
		builder.append("BatteryMonitor[\nlevel=");
		builder.append(level);
		builder.append(",\n charging=");
		builder.append(charging);
		builder.append(",\n lastUpdated=");
		builder.append(lastUpdated);
		builder.append("]");
		return builder.toString();
	}
}
