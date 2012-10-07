package com.code4reference.orientationspecificlayout;

import android.app.Activity;
import android.content.res.Configuration;
import android.os.Bundle;

public class DynamicOrientationDetection extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		//The below code provide the current orientation of the screen.
		setSpecificLayout(this.getResources().getConfiguration().orientation);
	}
    
	@Override
	public void onConfigurationChanged(Configuration newConfig) {
		super.onConfigurationChanged(newConfig);
		setSpecificLayout(newConfig.orientation);
	}
	
	/**
	 * Set the specific layout based on the orientation.
	 * @param orientation
	 */
	public void setSpecificLayout(int orientation){
		//Based on the current orientation, the specific 
		//layout is set.
    	if (orientation == Configuration.ORIENTATION_LANDSCAPE){
    		setContentView(R.layout.activity_dynamic_land);
    	}
    	else if (orientation == Configuration.ORIENTATION_PORTRAIT){
    		setContentView(R.layout.activity_dynamic_port);
    	}
    }
}
