package com.rakesh.simplewidget;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

import android.content.Context;
import android.net.ConnectivityManager;
import android.telephony.TelephonyManager;
import android.util.Log;

public class EnableDisableConnectivity {
	private Context mContext;
	public EnableDisableConnectivity(Context context){
		this.mContext = context;
	}
	public boolean enableDataPacketConnection(){
		return enableDisableDataPacketConnection(true);
	}
	public boolean disableDataPacketConnection(){
		return enableDisableDataPacketConnection(false);
	}

	public boolean enableDisableDataPacketConnection(boolean enable){
		boolean result = false;
		Method dataConnSwitchmethod;
		Class telephonyManagerClass;
		Object ITelephonyStub;
		Class ITelephonyClass;
		final ConnectivityManager conman = (ConnectivityManager) mContext.getSystemService(Context.CONNECTIVITY_SERVICE);


		try{
			final Class conmanClass = Class.forName(conman.getClass().getName());
			final Field iConnectivityManagerField = conmanClass.getDeclaredField("mService");
			iConnectivityManagerField.setAccessible(true);
			final Object iConnectivityManager = iConnectivityManagerField.get(conman);
			final Class iConnectivityManagerClass = Class.forName(iConnectivityManager.getClass().getName());
			final Method setMobileDataEnabledMethod = iConnectivityManagerClass.getDeclaredMethod("setMobileDataEnabled", Boolean.TYPE);
			setMobileDataEnabledMethod.setAccessible(true);
			setMobileDataEnabledMethod.invoke(iConnectivityManager, enable);
			result = true;
		}catch(Exception e){
			Log.e("Error", "here is an exception "+e.getMessage());
			result =false;
		}
		return result;
	}

	private boolean checkConnectivityState(){
		final TelephonyManager telephonyManager = (TelephonyManager) mContext
				.getSystemService(Context.TELEPHONY_SERVICE);
		return telephonyManager.getDataState() == TelephonyManager.DATA_CONNECTED;

	}
}
