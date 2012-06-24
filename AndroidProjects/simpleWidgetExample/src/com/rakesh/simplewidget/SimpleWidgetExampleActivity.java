package com.rakesh.simplewidget;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class SimpleWidgetExampleActivity extends Activity {
	private Button btNetworkSetting;
	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		btNetworkSetting = (Button)findViewById(R.id.btNetworkSetting);
		if(checkConnectivityState(getApplicationContext())){
			btNetworkSetting.setBackgroundColor(Color.GREEN);
		}else{
			btNetworkSetting.setBackgroundColor(Color.GRAY);
		}
	}

	public void openNetworkSetting(View view){

		Method dataConnSwitchmethod;
		Class telephonyManagerClass;
		Object ITelephonyStub;
		Class ITelephonyClass;
		Context context = view.getContext();
		boolean enabled = !checkConnectivityState(context);
		final ConnectivityManager conman = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);


		try{
			final Class conmanClass = Class.forName(conman.getClass().getName());
			final Field iConnectivityManagerField = conmanClass.getDeclaredField("mService");
			iConnectivityManagerField.setAccessible(true);
			final Object iConnectivityManager = iConnectivityManagerField.get(conman);
			final Class iConnectivityManagerClass = Class.forName(iConnectivityManager.getClass().getName());
			final Method setMobileDataEnabledMethod = iConnectivityManagerClass.getDeclaredMethod("setMobileDataEnabled", Boolean.TYPE);
			setMobileDataEnabledMethod.setAccessible(true);

			setMobileDataEnabledMethod.invoke(iConnectivityManager, enabled);

			if(enabled){
				//Toast.makeText(view.getContext(), "Enabled Network Data", Toast.LENGTH_SHORT).show();
				view.setBackgroundColor(Color.GREEN);
			}
			else{
				//Toast.makeText(view.getContext(), "Disabled Network Data", Toast.LENGTH_SHORT).show();
				view.setBackgroundColor(Color.LTGRAY);
			}
		}catch(Exception e){
			Log.e("Error", "some error");
			Toast.makeText(view.getContext(), "It didn't work", Toast.LENGTH_LONG).show();
		}
	}

	private boolean checkConnectivityState(Context context){
		final TelephonyManager telephonyManager = (TelephonyManager) context
				.getSystemService(Context.TELEPHONY_SERVICE);
		ConnectivityManager af ;
		return telephonyManager.getDataState() == TelephonyManager.DATA_CONNECTED;

	}
}