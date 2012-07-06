package com.rakesh.simplewidget;


import android.app.PendingIntent;
import android.app.Service;
import android.appwidget.AppWidgetManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;
import android.telephony.TelephonyManager;
import android.widget.RemoteViews;

public class UpdateWidgetService extends Service {

	@Override
	public void onStart(Intent intent, int startId) {

		AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(this
				.getApplicationContext());

		int[] allWidgetIds = intent.getIntArrayExtra(AppWidgetManager.EXTRA_APPWIDGET_IDS);

		ComponentName thisWidget = new ComponentName(getApplicationContext(),
				SimpleWidgetAppWidgetProvider.class);

		for (int widgetId : allWidgetIds) {
			RemoteViews remoteViews = new RemoteViews(this
					.getApplicationContext().getPackageName(),
					R.layout.widget_layout);

			EnableDisableConnectivity edConn = new EnableDisableConnectivity(this.getApplicationContext());
			edConn.enableDisableDataPacketConnection(!checkConnectivityState(this.getApplicationContext()));

			// Register an onClickListener
			Intent clickIntent = new Intent(this.getApplicationContext(),
					SimpleWidgetAppWidgetProvider.class);

			clickIntent.setAction(AppWidgetManager.ACTION_APPWIDGET_UPDATE);
			clickIntent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_IDS,
					allWidgetIds);

			PendingIntent pendingIntent = PendingIntent.getBroadcast(getApplicationContext(), 0, clickIntent,
					PendingIntent.FLAG_UPDATE_CURRENT);
			remoteViews.setOnClickPendingIntent(R.id.BtEnableDisable, pendingIntent);
			appWidgetManager.updateAppWidget(widgetId, remoteViews);
		}
		stopSelf();

		super.onStart(intent, startId);
	}

	@Override
	public IBinder onBind(Intent intent) {
		return null;
	}

	private boolean checkConnectivityState(Context context){
		final TelephonyManager telephonyManager = (TelephonyManager) context
				.getSystemService(Context.TELEPHONY_SERVICE);
		return telephonyManager.getDataState() == TelephonyManager.DATA_CONNECTED;

	}
}