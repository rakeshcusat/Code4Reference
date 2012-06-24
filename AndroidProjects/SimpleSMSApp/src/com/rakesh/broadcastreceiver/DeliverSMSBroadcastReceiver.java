package com.rakesh.broadcastreceiver;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

public class DeliverSMSBroadcastReceiver extends BroadcastReceiver {

	@Override
	public void onReceive(Context context, Intent intent) {
	
		switch (getResultCode())
        {
            case Activity.RESULT_OK:
                Toast.makeText(context, "SMS delivered", 
                        Toast.LENGTH_SHORT).show();
                Log.d("RK","RESULT_OK=> DELIVER");
                break;
            case Activity.RESULT_CANCELED:
                Toast.makeText(context, "SMS not delivered", 
                        Toast.LENGTH_SHORT).show();
                Log.d("RK","RESULT_CANCELED");
                break;                        
        }
	}

}
