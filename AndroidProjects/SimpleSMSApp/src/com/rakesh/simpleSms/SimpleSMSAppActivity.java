package com.rakesh.simpleSms;

import android.app.Activity;
import android.app.PendingIntent;
import android.content.Intent;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class SimpleSMSAppActivity extends Activity {
    /** Called when the activity is first created. */
	private Button btSendMessage;
	private EditText etNumber;
	private EditText etMessage;
	private SmsManager manager;
	public static final int  MAX_MESSAGE_SIZE = 160;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        btSendMessage = (Button)findViewById(R.id.btSendMessage);
        etNumber = (EditText)findViewById(R.id.etNumber);
        etMessage = (EditText)findViewById(R.id.etMessage);
        manager =  SmsManager.getDefault();
        
    }
    
    public void sendMessage(View view){
    	String number = etNumber.getText().toString();
    	String message = etMessage.getText().toString();
    	Log.d("RK","number : "+number+", message : " + message  );
    	
    	if(isNullOrEmpty(number) && isNullOrEmpty(message)){
    		if(message.length() > MAX_MESSAGE_SIZE){
    			
    			Toast.makeText(this,"Message is longer then allowed in SMS",Toast.LENGTH_LONG).show();
    		} else{
    			PendingIntent piSend = PendingIntent.getActivity(this, 0, new Intent(this, SimpleSMSAppActivity.class), 0);
    		    PendingIntent piDelivered = PendingIntent.getBroadcast(this, 0, new Intent("SMS_DELIVERED"), 0);
    		    manager.sendTextMessage(number, null, message, null, null);
    		}
    	}
    }
    
    private boolean isNullOrEmpty(String string){
    	return string == null || string.isEmpty();
    }
}