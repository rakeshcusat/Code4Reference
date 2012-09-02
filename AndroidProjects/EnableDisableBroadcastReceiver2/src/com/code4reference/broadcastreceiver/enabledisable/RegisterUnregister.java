package com.code4reference.broadcastreceiver.enabledisable;

import android.app.Activity;
import android.content.IntentFilter;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.Toast;

public class RegisterUnregister extends Activity {

	UserDefinedBroadcastReceiver broadCastReceiver = new UserDefinedBroadcastReceiver();
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_register_unregister);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.activity_enable_disable, menu);
		return true;
	}

	/**
	 * This method enables the Broadcast receiver for 
	 * "android.intent.action.TIME_TICK" intent. This intent get 
	 * broadcasted every minute.
	 * 
	 * @param view
	 */
	public void registerBroadcastReceiver(View view) {

		this.registerReceiver(broadCastReceiver, new IntentFilter(
				"android.intent.action.TIME_TICK"));
		Toast.makeText(this, "Enabled broadcast receiver", Toast.LENGTH_SHORT)
				.show();
	}

	/**
	 * This method disables the Broadcast receiver
	 * 
	 * @param view
	 */
	public void unregisterBroadcastReceiver(View view) {
		
		this.unregisterReceiver(broadCastReceiver);
		
		Toast.makeText(this, "Disabled broadcst receiver", Toast.LENGTH_SHORT)
				.show();
	}
}
