package com.code4reference.rakesh.jellybeanclipboard;

import com.example.jellybeanclipboard.R;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.EditText;

public class HtmlIntentActivity extends Activity {

	private EditText etHtml;
	private EditText etText;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_htmlintent);
		etHtml = (EditText) findViewById(R.id.etHtml);
		etText = (EditText) findViewById(R.id.etText);

		//Get the intent that started this activity
		Intent intent = getIntent();
		if (intent != null && intent.getType() != null
				&& intent.getType().equals("text/html")) {
			//This contition will full-fill when this application receive the 
			//intent who's type is "test/html". In this application sendHtmlIntent
			//method sends this type of Intent. 
			Bundle bundle = intent.getExtras();
			if(bundle != null){
				etHtml.setText(bundle.getCharSequence(Intent.EXTRA_HTML_TEXT));
				etText.setText(bundle.getCharSequence(Intent.EXTRA_TEXT));
			}
		}
	}
}

