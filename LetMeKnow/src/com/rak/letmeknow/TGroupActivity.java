package com.rak.letmeknow;

import android.content.Intent;
import android.os.Bundle;

public class TGroupActivity extends TabGroupActivity {
	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        startChildActivity("MyEvents", new Intent(this,MyEvents.class));
    }
	
}
