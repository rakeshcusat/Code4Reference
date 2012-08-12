package com.code4reference.rakesh.jellybeanclipboard;

import android.app.Activity;
import android.content.ClipboardManager;
import android.content.Intent;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.Toast;
import android.content.ClipData;
import android.content.ClipDescription;

import com.example.jellybeanclipboard.R;

public class ClipdataIntentActivity extends Activity {
	private EditText etHtml;
	private EditText etText;
	ClipboardManager mClipboard;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_clipdataintent);
		etHtml = (EditText) findViewById(R.id.etClipBoardHtml);
		etText = (EditText) findViewById(R.id.etClipBoardText);

		//Get the intent that started this activity
		Intent intent = getIntent();
		if (intent != null) {
			ClipData clipdata = intent.getClipData();

			if (clipdata != null
					&& clipdata.getDescription().hasMimeType(
							ClipDescription.MIMETYPE_TEXT_HTML)) {

				ClipData.Item item = clipdata.getItemAt(0);

				etHtml.setText(item.getHtmlText());
				etText.setText(item.getText());
			} else {
				Utility.showToastMessage(this,
						"Intent clipdata doesn't have HTML", Toast.LENGTH_SHORT);
			}

		}
	}

}
