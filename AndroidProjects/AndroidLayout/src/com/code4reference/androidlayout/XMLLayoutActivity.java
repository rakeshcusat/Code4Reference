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
        setContentView(R.layout.activity_xmllayout);
        btOtherActivity = (Button)findViewById(R.id.btOtherActivity);
        btOtherActivity.setOnClickListener(new OnClickListener(){
        	@Override
        	public void onClick(View view){
        		Intent intent = new Intent(getApplicationContext(), DynamicLayoutActivity.class);
        		startActivity(intent);
        	}
        });
    }
}
