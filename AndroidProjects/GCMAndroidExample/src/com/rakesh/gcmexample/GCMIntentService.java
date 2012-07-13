package com.rakesh.gcmexample;

import static com.rakesh.gcmexample.Utility.SENDER_ID;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.google.android.gcm.GCMBaseIntentService;

public class GCMIntentService extends GCMBaseIntentService {
	final String TAG = this.getClass().getSimpleName();
	
	public GCMIntentService() {
        super(SENDER_ID);
    }
	@Override
	protected void onError(Context contecxt, String errorId) {
		 Log.i(TAG, "Received error: " + errorId);
	}

	@Override
	protected void onMessage(Context context, Intent intent) {
		Log.i(TAG, "onMessage(): you have received message.");
	}

	@Override
	protected void onRegistered(Context context, String regId) {
		 Log.i(TAG, "Device registered: regId = " + regId);
	}

	@Override
	protected void onUnregistered(Context context, String regId) {
		Log.i(TAG,"Device unregistered regId = " + regId);
	}

}
