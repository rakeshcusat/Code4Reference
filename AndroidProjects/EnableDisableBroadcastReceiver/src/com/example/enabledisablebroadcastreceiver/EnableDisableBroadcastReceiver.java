package com.example.enabledisablebroadcastreceiver;

import android.app.Activity;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

public class EnableDisableBroadcastReceiver extends Activity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

   public void enableBroadcastReceiver(View view){
	   ComponentName receiver = new ComponentName(this, AlarmManagerBroadcastReceiver.class);
	   PackageManager pm = this.getPackageManager();

	   pm.setComponentEnabledSetting(receiver,
	           PackageManager.COMPONENT_ENABLED_STATE_ENABLED,
	           PackageManager.DONT_KILL_APP);
	   Toast.makeText(this, "Enabled broadcast receiver", Toast.LENGTH_SHORT).show();
   }
   
   public void disableBroadcastReceiver(View view){
	   ComponentName receiver = new ComponentName(this, AlarmManagerBroadcastReceiver.class);
	   PackageManager pm = this.getPackageManager();

	   pm.setComponentEnabledSetting(receiver,
	           PackageManager.COMPONENT_ENABLED_STATE_DISABLED,
	           PackageManager.DONT_KILL_APP);
	   Toast.makeText(this, "Disabled broadcst receiver", Toast.LENGTH_SHORT).show();
   }
   
   public void startRepeatingAlarm(View view)
   {
       AlarmManager am=(AlarmManager)this.getSystemService(Context.ALARM_SERVICE);
       Intent intent = new Intent(this, AlarmManagerBroadcastReceiver.class);
       PendingIntent pi = PendingIntent.getBroadcast(this, 0, intent, 0);
       //After after 2 seconds
       am.setRepeating(AlarmManager.RTC_WAKEUP, System.currentTimeMillis(), 1000 * 4 , pi); 
       Toast.makeText(this, "Started Repeating Alarm", Toast.LENGTH_SHORT).show();
   }

   public void cancelAlarm(View view)
   {
       Intent intent = new Intent(this, AlarmManagerBroadcastReceiver.class);
       PendingIntent sender = PendingIntent.getBroadcast(this, 0, intent, 0);
       AlarmManager alarmManager = (AlarmManager) this.getSystemService(Context.ALARM_SERVICE);
       alarmManager.cancel(sender);
       Toast.makeText(this, "Cancelled alarm", Toast.LENGTH_SHORT).show();
   }
    
}
