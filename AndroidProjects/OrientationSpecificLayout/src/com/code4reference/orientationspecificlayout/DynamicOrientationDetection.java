package com.code4reference.orientationspecificlayout;

import android.app.Activity;
import android.content.res.Configuration;
import android.os.Bundle;

public class DynamicOrientationDetection extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setSpecificLayout(this.getResources().getConfiguration().orientation);
	}
    
	@Override
	public void onConfigurationChanged(Configuration newConfig) {
		super.onConfigurationChanged(newConfig);
		setSpecificLayout(newConfig.orientation);
	}
	
	public void setSpecificLayout(int orientation){
    	if (orientation == Configuration.ORIENTATION_LANDSCAPE){
    		setContentView(R.layout.activity_dynamic_land);
    	}
    	else if (orientation == Configuration.ORIENTATION_PORTRAIT){
    		setContentView(R.layout.activity_dynamic_port);
    	}
    }
}
