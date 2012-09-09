package com.code4reference.androidlayout;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class XMLLayoutActivity extends Activity {

	Button btOtherActivity;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		// Attaching the layout file with the current Activity.
		setContentView(R.layout.activity_xmllayout);
		// Associate the Button object with the Button defined in the layout
		// file. Notice that R.id.btOtherActivity is associated with the 
        //button in activity_xmllayout.xml layoutfile.
		btOtherActivity = (Button) findViewById(R.id.btOtherActivity);
		//Attaching the OnclickListener with the button.
		btOtherActivity.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View view) {
				//Creating an Intent which will invoke
				//the other Activity (DynamicLayoutActivity).
				Intent intent = new Intent(getApplicationContext(),
						DynamicLayoutActivity.class);
				//This method will start the other activity.
				startActivity(intent);
			}
		});
	}
}
