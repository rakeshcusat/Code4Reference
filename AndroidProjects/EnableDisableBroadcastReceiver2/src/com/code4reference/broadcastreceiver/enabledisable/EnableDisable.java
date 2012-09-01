package com.code4reference.broadcastreceiver.enabledisable;

import android.app.Activity;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.Toast;

public class EnableDisable extends Activity {

	UserDefinedBroadcastReceiver broadCastReceiver = new UserDefinedBroadcastReceiver();
	static final String SOME_ACTION = "com.code4reference.action.SOME_ACTION";
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_enable_disable);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_enable_disable, menu);
        return true;
    }
    /**
     * This method enables the Broadcast receiver registered in the AndroidManifest file.
     * @param view
     */
   public void enableBroadcastReceiver(View view){
	   
	   this.registerReceiver(broadCastReceiver, new IntentFilter("android.intent.action.TIME_TICK"));
	   Toast.makeText(this, "Enabled broadcast receiver", Toast.LENGTH_SHORT).show();
   }
   /**
    * This method disables the Broadcast receiver registered in the AndroidManifest file.
    * @param view
    */
   public void disableBroadcastReceiver(View view){
	   this.unregisterReceiver(broadCastReceiver);
	   Toast.makeText(this, "Disabled broadcst receiver", Toast.LENGTH_SHORT).show();
   }
}
