package com.rakesh.simplewidget;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Log;
import android.widget.Toast;


public class ConnectivityReceiver extends BroadcastReceiver {

	@Override
	public void onReceive(Context context, Intent intent) {
		NetworkInfo info = (NetworkInfo)intent.getExtras().get(ConnectivityManager.EXTRA_NETWORK_INFO);
		if(info.getType() == ConnectivityManager.TYPE_MOBILE){ 
			if(info.isConnectedOrConnecting()){
				Toast.makeText(context, "Data packet enabled", Toast.LENGTH_SHORT).show();
				Log.e("RK","Mobile data is connected");
			}else{
				Toast.makeText(context, "Data packet disabled", Toast.LENGTH_SHORT).show();
				Log.e("RK","Mobile data is disconnected");
			}
		}
	}

}
