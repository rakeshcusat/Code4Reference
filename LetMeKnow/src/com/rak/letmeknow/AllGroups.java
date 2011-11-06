/**
 * 
 */
package com.rak.letmeknow;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.rak.letmeknow.R;

import android.app.AlertDialog;
import android.app.ExpandableListActivity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.ExpandableListView;
import android.widget.Toast;

/**
 * @author user
 *
 */
public class AllGroups extends ExpandableListActivity implements ActivityInterface {

	private ArrayList<String> groupNames;
	private ArrayList<ArrayList<Group>> groups;
	private MyGroupsAdapter expListAdapter;


	@Override
	public void onCreate(Bundle icicle)
	{
		super.onCreate(icicle);
		setContentView(R.layout.allgroups);

	}

	@Override
	public void onResume(){
		super.onResume();
		try{
			JSONObject jObj = new JSONObject();
			jObj.put("email",this.getSharedPreferences("c2dmPref", Context.MODE_PRIVATE).getString("prefemail",null));  
			new MyHttpClient(this).execute(URLReader.getHost()+"GetAllgroups",jObj.toString());

		}catch(Exception e){
			Toast.makeText(this,"Error Occurred", Toast.LENGTH_LONG).show();
			e.printStackTrace();
		}
	}

	public void onData(String data) {

		groupNames = new ArrayList<String>();
		try{
			JSONObject jObj = new JSONObject(data);

			JSONArray groupsArray = jObj.getJSONArray("allGroups");
			JSONArray eventsArray = jObj.getJSONArray("allevents");

			for( int i = 0 ; i < groupsArray.length() ; i++) {
				groupNames.add(groupsArray.getString(i));
			}

			groups = new ArrayList<ArrayList<Group>>(); 

			for(int i = 0 ; i < groupNames.size() ; i++) {
				ArrayList<Group> events = new ArrayList<Group>();
				for(int j = 0 ; j < eventsArray.length() ; j++)
					events.add( new Group( eventsArray.getString(j), 
							calculate(jObj, groupNames.get(i),eventsArray.getString(j)))); 
				groups.add( events );
			}

			expListAdapter = new MyGroupsAdapter(this,groupNames, groups );

			setListAdapter( expListAdapter );

			if(jObj.getBoolean("status") && jObj.getString("type").equals("register"))
				Toast.makeText(this,"Successfully registered",Toast.LENGTH_LONG).show();
			else if(jObj.getString("type").equals("register"))
				Toast.makeText(this,"There was a error in registration",Toast.LENGTH_LONG).show();

		}
		catch(Exception e){
			Toast.makeText(this,"Server Error", Toast.LENGTH_LONG).show();
			e.printStackTrace();
		}
	}

	public boolean onChildClick(ExpandableListView parent, View v,
			int groupPosition, int childPosition, long id) {

		CheckBox cb = (CheckBox)v.findViewById( R.id.check1 );
		if( cb != null ) {
			cb.toggle();
			boolean status = cb.isChecked();
			Group c = (Group) expListAdapter.getChild( groupPosition, childPosition );
			c.setState(status);
		}
		return false;
	}

	private boolean calculate (JSONObject jObj, String groupName, String eventName) throws JSONException {
		boolean isExist = false;
		boolean contains = false;
		JSONArray registeredGroups = jObj.names();

		for(int i = 0 ;i < registeredGroups.length(); i++){
			if(registeredGroups.getString(i).equals(groupName)){
				contains = true;
				break;
			}		
		}

		if(contains) {
			JSONArray eventArray = jObj.getJSONArray(groupName);
			for (int i = 0 ; i< eventArray.length(); i++){
				String event = eventArray.getString(i);
				if(event != null && event.equals(eventName)) {
					isExist = true;
					break;
				}
			}
		}
		return isExist;
	}

	public void registerGroup(View v){

		try{
			SharedPreferences pref = this.getSharedPreferences("c2dmPref",Context.MODE_PRIVATE);
			String email = pref.getString("prefemail",null);

			JSONObject jObj = new JSONObject();
			jObj.put("email",email);
			putGroupsAndEvents(jObj);
			new MyHttpClient(this).execute(URLReader.getHost()+"RegisterGroups",jObj.toString());

		}catch(Exception e){
			Toast.makeText(this,"Error Occurred", Toast.LENGTH_LONG).show();
			e.printStackTrace();
		}
	}

	private void putGroupsAndEvents(JSONObject jObject) throws JSONException {

		for(int i = 0 ; i < groupNames.size() ; i ++) {
			for(int j = 0 ; j < groups.get(i).size() ; j++) {
				if(groups.get(i).get(j).getState())
					jObject.accumulate(groupNames.get(i), groups.get(i).get(j).getEvent());
			}
		}
	}

	public void refreshData(View v){
		try{
			SharedPreferences pref = this.getSharedPreferences("c2dmPref",Context.MODE_PRIVATE);
			String email = pref.getString("prefemail",null);

			JSONObject jObj = new JSONObject();
			jObj.put("email",email);
			new MyHttpClient(this).execute(URLReader.getHost()+"GetAllgroups",jObj.toString());

		}catch(Exception e){
			Toast.makeText(this,"Error Occurred", Toast.LENGTH_LONG).show();
			e.printStackTrace();
		}
	}

	@Override
	public void onBackPressed() {

		new AlertDialog.Builder(this)
		.setIcon(R.drawable.icon)
		.setTitle("Quit")
		.setMessage("Are you sure you want to exit")
		.setPositiveButton("Yes", new DialogInterface.OnClickListener() {

			public void onClick(DialogInterface dialog, int which) {

				Intent i = new Intent(Intent.ACTION_MAIN);
				i.addCategory(Intent.CATEGORY_HOME);
				startActivity(i);

			}

		})
		.setNegativeButton("No",new DialogInterface.OnClickListener() {

			public void onClick(DialogInterface dialog, int which) {

				dialog.cancel();

			}
		})
		.show();

		return ;
	}
}
