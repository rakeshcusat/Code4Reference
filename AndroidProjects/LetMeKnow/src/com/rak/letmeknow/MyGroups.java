package com.rak.letmeknow;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Iterator;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.rak.letmeknow.R;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ExpandableListActivity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.provider.Settings;
import android.text.Editable;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ExpandableListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

public class MyGroups extends ExpandableListActivity implements ActivityInterface,OnClickListener {
	
	private ArrayList<String> groupNames;
	private ArrayList<ArrayList<Group>> groups;
	private MyGroupsAdapter expListAdapter;
	private String time;
	private String date;

	@Override
	public void onCreate(Bundle icicle)
	{
		super.onCreate(icicle);
		setContentView(R.layout.mygroups);
		findViewById(R.id.DateTime).setOnClickListener(this);
	}

	@Override
	public void onResume(){
		super.onResume();
		try{
			JSONObject jObj = new JSONObject();
			jObj.put("email",this.getSharedPreferences("c2dmPref", Context.MODE_PRIVATE).getString("prefemail",null));  
			new MyHttpClient(this).execute(URLReader.getHost()+"ListMyGroups",jObj.toString());
		}catch(Exception e){
			Toast.makeText(this,"Error Occured", Toast.LENGTH_LONG).show();
			e.printStackTrace();
		}
	}
	
	
	public void onData(String data) {

		groupNames = new ArrayList<String>();
		try{
			JSONObject jObj = new JSONObject(data);

			Iterator<String> it = jObj.keys();
			while(it.hasNext()){
				String key = it.next();
				if(!key.equals("allevents") && !key.equals("status") &&!key.equals("type")){
					groupNames.add(key);
				}
			}
			JSONArray eventsArray = jObj.getJSONArray("allevents");

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
			if(jObj.getBoolean("status") && jObj.getString("type").equals("sendevent")){
				resetIt(new View(this));
				Toast.makeText(this,"Event Successfuly sent",Toast.LENGTH_LONG).show();
			}
			else if(jObj.getString("type").equals("sendevent"))
				Toast.makeText(this,"There was a error in sending the event",Toast.LENGTH_LONG).show();

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
	
	public void changeClickStatus(View v) {
    	
    }

	private boolean calculate (JSONObject jObj, String groupName, String eventName) throws JSONException {
		boolean isExist = false;

		JSONArray eventArray = jObj.getJSONArray(groupName);

		for (int i=0 ; i< eventArray.length(); i++){
			String event = eventArray.getString(i);
			if(event != null && event.equals(eventName)) {
				isExist = true;
				break;
			}
		}

		return isExist;
	}
	
	
	public void resetIt(View v){
		
		EditText text = (EditText)this.findViewById(R.id.entry);
		text.setText("");
		EditText textsub = (EditText)this.findViewById(R.id.subject);
		textsub.setText("");
		((TextView) findViewById(R.id.Time)).setText("Time");
		time = null;
		((TextView) findViewById(R.id.Date)).setText("Date");
		date = null;		
	}
	
	public void sendNotification(View v){

		EditText text = (EditText)this.findViewById(R.id.entry);
		Editable editText = text.getText();
		EditText textsub = (EditText)this.findViewById(R.id.subject);
		Editable editTextSub = textsub.getText();
		
		Toast.makeText(this,editText.toString(), Toast.LENGTH_LONG);
		
		if((editText != null && editText.length() > 0) && (editTextSub != null && editTextSub.length()>0) 
				&& time != null && date != null) {
			
			try{
				SharedPreferences pref = this.getSharedPreferences("c2dmPref",Context.MODE_PRIVATE);
				String email = pref.getString("prefemail",null);
				String lat = pref.getString("lat", null);
				String lng = pref.getString("lng", null);

				JSONObject jObj = new JSONObject();
				jObj.put("email",email);
				jObj.put("time",time);
				jObj.put("date", date );
				jObj.put("eventDetail", editText.toString());
				jObj.put("subject", editTextSub.toString());
				jObj.put("gpsLoc", lat+" "+lng);
				System.out.println("gpsLoc");
				if(putGroupsAndEvents(jObj))
					new MyHttpClient(this).execute(URLReader.getHost()+"SendNotification",jObj.toString());
				else
					Toast.makeText(this,"Please select at least one event", Toast.LENGTH_LONG).show();

			}catch(Exception e){
				Toast.makeText(this,"Error Occured", Toast.LENGTH_LONG).show();
				e.printStackTrace();
			}
		}
		else{
			Toast.makeText(this,"Please enter a value for Subject,Message,Time", Toast.LENGTH_LONG).show();
			System.out.println("Enter a value");
		}

	}
	
	private boolean putGroupsAndEvents(JSONObject jObject) throws JSONException {
		
		boolean value = false;
		
		for(int i = 0 ; i < groupNames.size() ; i ++) {
			for(int j = 0 ; j < groups.get(i).size() ; j++) {
				if(groups.get(i).get(j).getState()){
					jObject.accumulate(groupNames.get(i), groups.get(i).get(j).getEvent());
					value = true;
				}
			}
		}
		System.out.println(jObject.toString());
		return value;
	}
	
	public void onClick(View view) {
		// TODO Auto-generated method stub
		if (view.getId() == R.id.DateTime)
			showDateTimeDialog();
	}
	
	private void showDateTimeDialog() {
		// Create the dialog
		final Dialog mDateTimeDialog = new Dialog(this);
		// Inflate the root layout
		final RelativeLayout mDateTimeDialogView = (RelativeLayout) getLayoutInflater().inflate(R.layout.date_time_dialog, null);
		// Grab widget instance
		final DateTimePicker mDateTimePicker = (DateTimePicker) mDateTimeDialogView.findViewById(R.id.DateTimePicker);
		// Check is system is set to use 24h time (this doesn't seem to work as expected though)
		final String timeS = android.provider.Settings.System.getString(getContentResolver(), android.provider.Settings.System.TIME_12_24);
		final boolean is24h = !(timeS == null || timeS.equals("12"));
		
		// Update demo TextViews when the "OK" button is clicked 
		((Button) mDateTimeDialogView.findViewById(R.id.SetDateTime)).setOnClickListener(new OnClickListener() {

			public void onClick(View v) {
				// TODO Auto-generated method stub
				((TextView) findViewById(R.id.Date)).setText(mDateTimePicker.get(Calendar.YEAR) + "/" + (mDateTimePicker.get(Calendar.MONTH)+1) + "/"
						+ mDateTimePicker.get(Calendar.DAY_OF_MONTH));
				
				date = mDateTimePicker.get(Calendar.YEAR) + "/" + (mDateTimePicker.get(Calendar.MONTH)+1) + "/"
				+ mDateTimePicker.get(Calendar.DAY_OF_MONTH);
				
				if (mDateTimePicker.is24HourView()) {
					((TextView) findViewById(R.id.Time)).setText(mDateTimePicker.get(Calendar.HOUR_OF_DAY) + 
							":" + mDateTimePicker.get(Calendar.MINUTE));
					time = mDateTimePicker.get(Calendar.HOUR_OF_DAY) + 
							":" + mDateTimePicker.get(Calendar.MINUTE);
				} else {
					((TextView) findViewById(R.id.Time)).setText(mDateTimePicker.get(Calendar.HOUR) + 
							":" + mDateTimePicker.get(Calendar.MINUTE) + " "
							+ (mDateTimePicker.get(Calendar.AM_PM) == Calendar.AM ? "AM" : "PM"));
					int hour = mDateTimePicker.get(Calendar.HOUR);
					
					if (hour == 0)
						hour = 12;
					
					time =  hour +":" + mDateTimePicker.get(Calendar.MINUTE) + " "
					+ (mDateTimePicker.get(Calendar.AM_PM) == Calendar.AM ? "AM" : "PM");
				}
				mDateTimeDialog.dismiss();
			}
		});

		// Cancel the dialog when the "Cancel" button is clicked
		((Button) mDateTimeDialogView.findViewById(R.id.CancelDialog)).setOnClickListener(new OnClickListener() {

			public void onClick(View v) {
				// TODO Auto-generated method stub
				mDateTimeDialog.cancel();
			}
		});

		// Reset Date and Time pickers when the "Reset" button is clicked
		((Button) mDateTimeDialogView.findViewById(R.id.ResetDateTime)).setOnClickListener(new OnClickListener() {

			public void onClick(View v) {
				// TODO Auto-generated method stub
				mDateTimePicker.reset();
			}
		});
		
		// Setup TimePicker
		mDateTimePicker.setIs24HourView(is24h);
		// No title on the dialog window
		mDateTimeDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
		// Set the dialog content view
		mDateTimeDialog.setContentView(mDateTimeDialogView);
		// Display the dialog
		mDateTimeDialog.show();
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
        /*        	Intent intent = new Intent(MyGroups.this, LMKTabWidget.class);
        			intent.putExtra("tabToLoad",0);
        			startActivity(intent);*/
                       
                }
            })
            .show();

            return ;
    }
}
