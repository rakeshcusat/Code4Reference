package com.rakesh.widgetalarmmanagerexample;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;

public class WidgetAlarmManagerActivity extends Activity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_widget_alarm_manager);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_widget_alarm_manager, menu);
        return true;
    }

    
}
