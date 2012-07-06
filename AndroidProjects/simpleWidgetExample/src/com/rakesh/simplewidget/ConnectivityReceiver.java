package com.rakesh.simplewidget;
import android.appwidget.AppWidgetManager;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Log;
import android.widget.RemoteViews;
import android.widget.Toast;


public class ConnectivityReceiver extends BroadcastReceiver {

	@Override
	public void onReceive(Context context, Intent intent) {
		NetworkInfo info = (NetworkInfo)intent.getExtras().get(ConnectivityManager.EXTRA_NETWORK_INFO);
		
		if(info.getType() == ConnectivityManager.TYPE_MOBILE){
			
			RemoteViews remoteViews = new RemoteViews(context.getPackageName(),
					R.layout.widget_layout);
			
			if(info.isConnectedOrConnecting()){
				Toast.makeText(context, "Data packet enabled", Toast.LENGTH_SHORT).show();
				Log.d("RK","Mobile data is enabled");
				remoteViews.setTextColor(R.id.BtEnableDisable, Color.GREEN);
				remoteViews.setTextViewText(R.id.BtEnableDisable, "Enabled");
			}else{
				Toast.makeText(context, "Data packet disabled", Toast.LENGTH_SHORT).show();
				Log.e("RK","Mobile data is disconnected");
				remoteViews.setTextColor(R.id.BtEnableDisable, Color.BLACK);
				remoteViews.setTextViewText(R.id.BtEnableDisable,"Disabled");
			}
			
			ComponentName thiswidget = new ComponentName(context, SimpleWidgetAppWidgetProvider.class);
			AppWidgetManager manager = AppWidgetManager.getInstance(context);
			manager.updateAppWidget(thiswidget, remoteViews);
			
		}
	}

}
