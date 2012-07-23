package com.rakesh.voicerecognitionexample;

import java.util.ArrayList;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.speech.RecognizerIntent;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;

public class VoiceRecognitionActivity extends Activity {
 private static final int VOICE_RECOGNITION_REQUEST_CODE = 1001;
 private EditText metTextHint;
 private ListView mlvTextMatches;
 
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_voice_recognition);
        metTextHint = (EditText)findViewById(R.id.etTextHint);
        mlvTextMatches = (ListView)findViewById(R.id.lvTextMatches);
    }

    public void speak(View view){
    	Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);

        // Specify the calling package to identify your application
        intent.putExtra(RecognizerIntent.EXTRA_CALLING_PACKAGE, getClass().getPackage().getName());

        // Display an hint to the user about what he should say.
        intent.putExtra(RecognizerIntent.EXTRA_PROMPT, "Speech recognition demo");

        // Given an hint to the recognizer about what the user is going to say
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL,
                RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);

        // Specify how many results you want to receive. The results will be sorted
        // where the first result is the one with higher confidence.
        intent.putExtra(RecognizerIntent.EXTRA_MAX_RESULTS, 5);


        startActivityForResult(intent, VOICE_RECOGNITION_REQUEST_CODE);
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == VOICE_RECOGNITION_REQUEST_CODE && resultCode == RESULT_OK) {
            // Fill the list view with the strings the recognizer thought it could have heard
            ArrayList<String> textMatcheList = data.getStringArrayListExtra(
                    RecognizerIntent.EXTRA_RESULTS);
            mlvTextMatches.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1,
                    textMatcheList));
        }

        super.onActivityResult(requestCode, resultCode, data);
    }
    
}
