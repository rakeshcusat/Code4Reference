package com.rakesh.gcmexample;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.view.MenuItem;
import android.support.v4.app.NavUtils;
import com.google.android.gcm.GCMRegistrar;
import static com.rakesh.gcmexample.Utility.SENDER_ID;
import android.util.Log;

public class GCMMainActivity extends Activity {
	final String TAG = this.getClass().getSimpleName();
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gcmmain);
        GCMRegistrar.checkDevice(this);
        GCMRegistrar.checkManifest(this);
        
        final String regId = GCMRegistrar.getRegistrationId(this);
        if (regId.equals("")) {
          GCMRegistrar.register(this, SENDER_ID);
        } else {
          Log.v(TAG, "Already registered");
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_gcmmain, menu);
        return true;
    }

    
}
