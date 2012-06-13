package com.rakesh.simpleSms;

import com.rakesh.broadcastreceiver.DeliverSMSBroadcastReceiver;
import com.rakesh.broadcastreceiver.OutgoingSMSBroadcastReceiver;

import android.app.Activity;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class SimpleSMSAppActivity extends Activity {
	//This application sends two identical message. Issue can be fixed by method mention in following link.
	//http://stackoverflow.com/questions/9706039/android-sendtextmessage-sends-two-identical-messages-on-exceution-how-to-fix
	//private Button btSendMessage;
	private EditText etNumber;
	private EditText etMessage;
	private SmsManager manager;
	public static final int  MAX_MESSAGE_SIZE = 160;
	public static final String SMS_SENT = "SMS_SENT";
	public static final String SMS_DELIVERED = "SMS_DELIVERED";
	private final BroadcastReceiver outgoingSMSBR = new OutgoingSMSBroadcastReceiver();
	private final BroadcastReceiver deliverSMSBR = new DeliverSMSBroadcastReceiver();
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		//  btSendMessage = (Button)findViewById(R.id.btSendMessage);
		etNumber = (EditText)findViewById(R.id.etNumber);
		etMessage = (EditText)findViewById(R.id.etMessage);
		manager =  SmsManager.getDefault();
	}

	public void sendMessage(View view){
		String number = etNumber.getText().toString();
		String message = etMessage.getText().toString();
		Log.d("RK","number : "+number+", message : " + message  );

		if(!isNullOrEmpty(number) && !isNullOrEmpty(message)){
			if(message.length() > MAX_MESSAGE_SIZE){

				Toast.makeText(this,"Message is longer then allowed in SMS",Toast.LENGTH_LONG).show();
			} else{
				PendingIntent piSend = PendingIntent.getBroadcast(this, 0, new Intent(SMS_SENT),0);
				PendingIntent piDelivered = PendingIntent.getBroadcast(this, 0, new Intent(SMS_DELIVERED), 0);

				manager.sendTextMessage(number, null, message, piSend, piDelivered);
				etMessage.setText("");
			}
		}
	}

	@Override
	protected void onResume() {
		registerReceiver(outgoingSMSBR, new IntentFilter(SMS_SENT));

		registerReceiver(deliverSMSBR, new IntentFilter(SMS_DELIVERED));

		super.onResume();
	}

	@Override
	protected void onPause() {
		unregisterReceiver(outgoingSMSBR);
		unregisterReceiver(deliverSMSBR);
		super.onPause();
	}



	private boolean isNullOrEmpty(String string){
		return string == null || string.isEmpty();
	}
}