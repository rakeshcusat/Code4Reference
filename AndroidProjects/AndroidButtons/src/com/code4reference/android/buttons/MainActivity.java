package com.code4reference.android.buttons;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.RadioButton;
import android.widget.Switch;
import android.widget.Toast;
import android.widget.ToggleButton;

public class MainActivity extends Activity {
	private Switch mSwitch;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		mSwitch = (Switch) findViewById(R.id.switchbutton);
		
		mSwitch.setOnCheckedChangeListener(new OnCheckedChangeListener() {
        @Override
        public void onCheckedChanged(CompoundButton buttonView, boolean desiredState) {
        	if (desiredState){
                showMessage("Switch is turned ON");
        	} else {
        		showMessage("Switch is turned OFF");
        	}
        }
    });

	}

	/**
	 * Basic Button onClick method handler
	 * @param view
	 */
	public void basicButtonOnClickHandler(View view){
		showMessage("Basic Button is clicked");
	}
	/**
	 * ImageButton onClick method handler
	 * @param view
	 */
	public void imageButtonOnClickHandler(View view){
		showMessage("ImageButton is clicked");
	}
	/**
	 * Radio Button onClick method handler
	 * @param view
	 */
	public void onRadioButtonClicked(View view){
		 boolean checked = ((RadioButton) view).isChecked();
		    
		    // Check which radio button was clicked
		    switch(view.getId()) {
		        case R.id.rbYes:
		            if (checked){
		            	showMessage("Yes Radio button is selected");
		            }
		                
		            break;
		        case R.id.rbNo:
		            if (checked){
		            	showMessage("No Radio button is selected");
		            }
		                
		            break;
		    }

	}
	/**
	 * ToggleButton onClick method handler
	 * @param view
	 */
	public void onToggleClicked(View view){
	    boolean on = ((ToggleButton) view).isChecked();
	    
	    if (on) {
	    	showMessage("Toggle Button is in ON state");
	    } else {
	    	showMessage("Toggle Button is in OFF state");
	    }
	}
	/**
	 * Switch Onclick method handler
	 * @param view
	 */
	public void onSwitchClicked(View view){
		 boolean on = ((Switch) view).isChecked();
		    
		    if (on) {
		    	showMessage("Switch Button is in ON state");
		    } else {
		    	showMessage("Switch Button is in OFF state");
		    }
	}
	/**
	 * This method toast(display) a message.
	 * @param message
	 */
	private void showMessage(String message){
		
		Toast.makeText(getApplicationContext(), message, Toast.LENGTH_LONG).show();
	}
}
