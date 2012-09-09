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
		// This will create the LinearLayout
		LinearLayout ll = new LinearLayout(this);
		ll.setOrientation(LinearLayout.VERTICAL);
		
		// Configuring the width and height of the linear layout.
		LayoutParams llLP = new LayoutParams(
				//android:layout_width="match_parent" an in xml
				LinearLayout.LayoutParams.MATCH_PARENT,
				//android:layout_height="wrap_content"
				LinearLayout.LayoutParams.MATCH_PARENT);

		ll.setLayoutParams(llLP);

		TextView tv = new TextView(this);

		LayoutParams lp = new LayoutParams(
				LinearLayout.LayoutParams.MATCH_PARENT,
				LinearLayout.LayoutParams.WRAP_CONTENT);

		tv.setLayoutParams(lp);
		//android:text="@string/c4r"
		tv.setText(R.string.c4r);
		//android:padding="@dimen/padding_medium"
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
		//Now finally attach the Linear layout to the current Activity.
		setContentView(ll);

		//Attach OnClickListener to the button.
		bt.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View view) {
				Toast.makeText(getApplicationContext(),
						"This is dynamic activity", Toast.LENGTH_LONG).show();
			}
		});
	}
}
