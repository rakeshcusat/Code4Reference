package com.code4reference.orientationspecificlayout;

import android.app.Activity;
import android.os.Bundle;

public class RestrictedOrientation extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_restricted);
	}
}
