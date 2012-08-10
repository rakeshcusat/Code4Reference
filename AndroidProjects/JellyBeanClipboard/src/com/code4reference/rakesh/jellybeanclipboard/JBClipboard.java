package com.code4reference.rakesh.jellybeanclipboard;

import android.app.Activity;
import android.content.ClipData;
import android.content.ClipDescription;
import android.content.ClipboardManager;
import android.os.Bundle;
import android.text.Html;
import android.text.Spannable;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Toast;

import com.example.jellybeanclipboard.R;

public class JBClipboard extends Activity {

	EditText etCopy;
	EditText etPaste;
	EditText etPasteCoerceText;
	RadioButton rbText;
	RadioButton rbHtml;
	ClipboardManager mClipboard;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_jbclipboard);
		etCopy = (EditText) findViewById(R.id.etCopy);
		etPaste = (EditText) findViewById(R.id.etPaste);
		etPasteCoerceText = (EditText) findViewById(R.id.etPasteCoerceText);
		etCopy.setText(Html.fromHtml(getString(R.string.tvHtml)));
		rbText = (RadioButton) findViewById(R.id.rbText);
		rbHtml = (RadioButton) findViewById(R.id.rbHtml);
		
		mClipboard = (ClipboardManager) getSystemService(this.CLIPBOARD_SERVICE);

	}

	public void copyHtml(View view) {
		Spannable spannable = (Spannable) etCopy.getText();
		String htmlText = Html.toHtml(spannable);
		String plainText = etCopy.getText().toString();
		
		mClipboard.setPrimaryClip(ClipData.newHtmlText("HTML Text", plainText,
				htmlText));
		showToast("Copied html text", Toast.LENGTH_SHORT);
	}

	public void pasteHtml(View view) {
		// Check if there is primary clip exsiting.
		// If it does then echeck the mime type to make sure
		// it has HTML content.
		if (mClipboard.hasPrimaryClip()
				&& mClipboard.getPrimaryClipDescription().hasMimeType(
						ClipDescription.MIMETYPE_TEXT_HTML)) {
			// Get the very first item from the clip.
			ClipData.Item item = mClipboard.getPrimaryClip().getItemAt(0);
		
			//If "Paste HTML" radio button is selected then paste 
			//HTML in the Textview.
			if(rbHtml.isChecked()){
				etPaste.setText(item.getHtmlText());
				showToast("HTML text pasted", Toast.LENGTH_SHORT);
			}else{
				//Paste the only text version.
				etPaste.setText(item.getText());
				showToast("Text pasted", Toast.LENGTH_SHORT);
			}
			//Paste the CoerceText .
			etPasteCoerceText.setText(item.coerceToText(this));
		}
	}

	public void showToast(String message, int duration) {
		Toast.makeText(this, message, duration).show();
	}
	
}
