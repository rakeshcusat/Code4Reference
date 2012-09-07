package com.code4reference.androidlayout;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.TextView;
import android.widget.Toast;

public class DynamicLayoutActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		LinearLayout ll = new LinearLayout(this);
		ll.setOrientation(LinearLayout.VERTICAL);
		TextView tv = new TextView(this);
		LayoutParams lp = new LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
		
		tv.setLayoutParams(lp);
		tv.setText(R.string.c4r);
		tv.setPadding(8, 8, 8, 8);
		ll.addView(tv);
		EditText et = new EditText(this);
		et.setLayoutParams(lp);
		et.setHint(R.string.c4r);
		et.setPadding(8, 8, 8, 8);
		ll.addView(et);
		Button bt = new Button(this);
		bt.setText(R.string.OtherActivity);
		bt.setPadding(8, 8, 8, 8);
		ll.addView(bt);
		setContentView(ll);
		
		bt.setOnClickListener(new OnClickListener(){
			@Override
			public void onClick(View view){
				Toast.makeText(getApplicationContext(), "This is dynamic activity", Toast.LENGTH_LONG).show();
			}
		});
	}
}
