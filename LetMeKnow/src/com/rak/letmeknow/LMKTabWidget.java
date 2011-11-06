/**
 * 
 */
package com.rak.letmeknow;

import com.rak.letmeknow.R;

import android.app.TabActivity;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.widget.TabHost;

/**
 * @author user
 *
 */
public class LMKTabWidget extends TabActivity {

	public void onCreate(Bundle savedInstanceState) {
	    super.onCreate(savedInstanceState);
	    setContentView(R.layout.main);

	    Resources res = getResources(); // Resource object to get Drawables
	    TabHost tabHost = getTabHost();  // The activity TabHost
	    TabHost.TabSpec spec;  // Resusable TabSpec for each tab
	    Intent intent;  // Reusable Intent for each tab
        Intent prevIntent = getIntent();
        Bundle b = prevIntent.getExtras();

        intent = new Intent().setClass(this, MyGroups.class);

        spec = tabHost.newTabSpec("mygroups").setIndicator("",
	                      res.getDrawable(R.drawable.mygroups_tab))
	                  .setContent(intent);
	    tabHost.addTab(spec);
	    
	    intent = new Intent().setClass(this, CreateGroup.class);

	    spec = tabHost.newTabSpec("creategroup").setIndicator("",
	                      res.getDrawable(R.drawable.add_group_button))
	                  .setContent(intent);
	    tabHost.addTab(spec);

	    intent = new Intent().setClass(this, AllGroups.class);
	    spec = tabHost.newTabSpec("allgroups").setIndicator("",
	                      res.getDrawable(R.drawable.allgroups_button))
	                  .setContent(intent);
	    tabHost.addTab(spec);

	    intent = new Intent().setClass(this, TGroupActivity.class);
	    spec = tabHost.newTabSpec("myevents").setIndicator("",
	                      res.getDrawable(R.drawable.myevents_button))
	                  .setContent(intent);
	    tabHost.addTab(spec); 

	    int currentHost = b.getInt("tabToLoad");
	    tabHost.setCurrentTab(currentHost);
	}
	
	
}
