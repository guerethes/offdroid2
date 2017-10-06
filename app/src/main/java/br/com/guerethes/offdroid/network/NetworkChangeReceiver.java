package br.com.guerethes.offdroid.network;

import android.app.ActivityManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

public class NetworkChangeReceiver extends BroadcastReceiver {

	@Override
	public void onReceive(final Context context, final Intent intent) {
		if ( !isServiceRunning(context) ) {
			if (NetWorkUtils.isOnline(context)) {
				NetWorkUtils.setOnline(true);
			} else {
				NetWorkUtils.setOnline(false);
			}
		}
	}

	private boolean isServiceRunning(Context context) {
		ActivityManager manager = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
		for (ActivityManager.RunningServiceInfo service : manager.getRunningServices(Integer.MAX_VALUE)) {
			if (NetworkChangeReceiver.class.getName().equals(service.service.getClassName())) {
				return true;
			}
		}
		return false;
	}

}