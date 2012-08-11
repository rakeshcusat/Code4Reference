package com.code4reference.rakesh.jellybeanclipboard;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

public class HTMLTextBroadcastReceiver extends BroadcastReceiver {

	@Override
	public void onReceive(Context context, Intent intent) {
		Utility.showToastMessage(context, "Intent message", Toast.LENGTH_SHORT);
	}
}
