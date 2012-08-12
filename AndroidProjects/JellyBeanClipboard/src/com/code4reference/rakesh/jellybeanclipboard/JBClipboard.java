package com.code4reference.rakesh.jellybeanclipboard;

import android.app.Activity;
import android.content.ClipData;
import android.content.ClipDescription;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
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

	ClipboardManager.OnPrimaryClipChangedListener mPrimaryChangeListener = new ClipboardManager.OnPrimaryClipChangedListener() {
		/**
		 * This method is a callback. It get called when the primary clip 
		 * on the clipboard changes. 
		 */
		public void onPrimaryClipChanged() {
			//Toast message will appear whenever the clipboad 
			//primary data changes.
			Utility.showToastMessage(getApplicationContext(),
					"Primary clipdata changed", Toast.LENGTH_SHORT);
		}
	};

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

		mClipboard = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
		mClipboard.addPrimaryClipChangedListener(mPrimaryChangeListener);
	}
	
	/**
	 * This method gets called when "Copy" button get pressed.
	 * @param view
	 */
	public void copyHtml(View view) {
		String htmlText = getHtmltxt(etCopy);
		String plainText = getOnlyText(etCopy);

		mClipboard.setPrimaryClip(ClipData.newHtmlText("HTML Text", plainText,
				htmlText));
		
	}
	/**
	 * This method gets called when "Paste" button get pressed.
	 * @param view
	 */
	public void pasteHtml(View view) {
		// Check if there is primary clip exsiting.
		// If it does then echeck the mime type to make sure
		// it has HTML content.
		if (mClipboard.hasPrimaryClip()
				&& mClipboard.getPrimaryClipDescription().hasMimeType(
						ClipDescription.MIMETYPE_TEXT_HTML)) {
			// Get the very first item from the clip.
			ClipData.Item item = mClipboard.getPrimaryClip().getItemAt(0);

			// If "Paste HTML" radio button is selected then paste
			// HTML in the Textview.
			if (rbHtml.isChecked()) {
				etPaste.setText(item.getHtmlText());
			} else {
				// Paste the only text version.
				etPaste.setText(item.getText());
			}
			// Paste the CoerceText .
			etPasteCoerceText.setText(item.coerceToText(this));
		}
	}
	/**
	 * This method gets called when "send Html Intent" button get pressed.
	 * @param view
	 */
	public void sendHtmlIntent(View view) {
		// This kind of intent can be handle by this application
		// Or other application which handle text/html type Intent
		Intent intent = new Intent(Intent.ACTION_SEND);

		String htmlText = getHtmltxt(etCopy);
		String text = getOnlyText(etCopy);
		intent.putExtra(Intent.EXTRA_HTML_TEXT, htmlText);
		intent.putExtra(Intent.EXTRA_TEXT, text);
		intent.setType("text/html");
		startActivity(Intent.createChooser(intent, null));
	}

	/**
	 * This method gets called when "send Clipdata Intent" button get pressed.
	 * 
	 * @param view
	 */
	public void sendClipdataIntent(View view) {
		String htmlText = getHtmltxt(etCopy);
		String plainText = getOnlyText(etCopy);
		Intent intent = new Intent(this, ClipdataIntentActivity.class);
		intent.setClipData(ClipData.newHtmlText(
				"HTML text in Intent's clipdata", plainText, htmlText));
		startActivity(intent);
	}
	@Override
    protected void onDestroy() {
        super.onDestroy();
        //Remove the ClipChanged Listener to save the resources.
        mClipboard.removePrimaryClipChangedListener(mPrimaryChangeListener);
    }
	/**
	 * This method get the EditText object and returns the HTML text. This
	 * method can only be run with those EditText which has spannable set and
	 * contains the HTML text.
	 * 
	 * @param editText
	 * @return
	 */
	private String getHtmltxt(EditText editText) {
		Spannable spannable = (Spannable) editText.getText();
		return Html.toHtml(spannable);
	}

	/**
	 * This method takes the EditText object which has spannable object with HTML
	 * text and returns the only text.
	 * 
	 * @param editText
	 * @return
	 */
	private String getOnlyText(EditText editText) {
		return editText.getText().toString();
	}
}
