package com.code4reference.orientationspecificlayout;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends Activity {
    
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }
    
    /**
     *Starts Automatic Orientation detection Activity.  
     * @param view
     */
    public void autoOrientationDetection(View view){
    	Intent intent = new Intent(this, AutomaticOrientation.class);
    	startActivity(intent);
    }
    /**
     * Starts the dynamic orientation detection Activity.
     * @param view
     */
    public void dynamicOrientationDetection(View view){
    	Intent intent = new Intent(this, DynamicOrientationDetection.class);
    	startActivity(intent);
    }
    /**
     * Starts the activity which has restricted layout.
     * @param view
     */
    public void restrictedOrientation(View view){
    	Intent intent = new Intent(this, RestrictedOrientation.class);
    	startActivity(intent);
    }
    
}
