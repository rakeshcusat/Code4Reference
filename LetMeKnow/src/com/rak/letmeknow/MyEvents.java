package com.rak.letmeknow;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONObject;

import com.rak.letmeknow.R;

import android.app.AlertDialog;
import android.app.ListActivity;
import android.app.NotificationManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

public class MyEvents extends ListActivity implements ActivityInterface {


	private ArrayList<Event> eventList;
	private MyListAdapter listAdapter;

	@Override
	public void onCreate(Bundle icicle)
	{
		super.onCreate(icicle);
		View viewToLoad = LayoutInflater.from(this.getParent()).inflate(R.layout.list_item, null);
		setContentView(viewToLoad);
	}

	@Override
	public void onResume(){
		super.onResume();

		try{
			String ns = Context.NOTIFICATION_SERVICE;
			NotificationManager mNotificationManager = (NotificationManager) this.getSystemService(ns);
			mNotificationManager.cancelAll();
			JSONObject jObj = new JSONObject();
			jObj.put("email",this.getSharedPreferences("c2dmPref", Context.MODE_PRIVATE).getString("prefemail",null));  
			new MyHttpClient(this).execute(URLReader.getHost()+"FetchEvent",jObj.toString());
		}catch(Exception e){
			Toast.makeText(this,"Error Occurred", Toast.LENGTH_LONG).show();
			e.printStackTrace();
		}
	}

	public void onData(String data) {
		// TODO Auto-generated method stub
		try{
			if(data.trim().equals("{}"))
				Toast.makeText(this,"No Events Found", Toast.LENGTH_LONG).show();

			else{	

				JSONObject jObj = new JSONObject(data);

				eventList = new ArrayList<Event>();

				JSONArray idArray = jObj.getJSONArray("id");
				JSONArray eventDetailArray = jObj.getJSONArray("eventDetail");
				JSONArray eventTimeArray = jObj.getJSONArray("time");
				JSONArray eventDateArray = jObj.getJSONArray("date");
				JSONArray sendEmailArray = jObj.getJSONArray("email");
				JSONArray subjectArray   = jObj.getJSONArray("subject");
				JSONArray gpsLocArray    = jObj.getJSONArray("gpsLoc");

				for(int i = 0 ; i < idArray.length(); i++){

					int id = idArray.getInt(i);
					String eventDetails = eventDetailArray.getString(i);
					String time = eventTimeArray.getString(i);
					String date = eventDateArray.getString(i);
					String senderEmail = sendEmailArray.getString(i);
					String subject = subjectArray.getString(i);
					String gpsLoc = gpsLocArray.getString(i);
					Event e = new Event(id, senderEmail, eventDetails, time, date, subject,gpsLoc);
					eventList.add(e);

				}

				listAdapter = new MyListAdapter(this, eventList);
				setListAdapter(listAdapter);

				ListView lv = getListView();
				lv.setTextFilterEnabled(true);
				lv.setOnItemClickListener(new OnItemClickListener() {

					public void onItemClick(AdapterView<?> parent, View view,
							int position, long id) {
						Event e = eventList.get(position);
						Intent previewMessage = new Intent(getParent(), DisplayActivity.class);
						previewMessage.putExtra("item",e);
						TabGroupActivity parentActivity = (TabGroupActivity)getParent();
						parentActivity.startChildActivity("Display", previewMessage);

					}
				});

			}

		}
		catch(Exception e){
			Toast.makeText(this,"Server Error", Toast.LENGTH_LONG).show();
			e.printStackTrace();
		}
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		System.out.println("myevents bkey down");
		if (keyCode == KeyEvent.KEYCODE_BACK) {
			return true;
		}
		return super.onKeyDown(keyCode, event);
	}



	/**
	 * Overrides the default implementation for KeyEvent.KEYCODE_BACK 
	 * so that all systems call onBackPressed().
	 */
	@Override
	public boolean onKeyUp(int keyCode, KeyEvent event) {

		System.out.println("my events key up");
		if (keyCode == KeyEvent.KEYCODE_BACK) {
			TabGroupActivity parentActivity = (TabGroupActivity)getParent();
			onBackPressed(parentActivity);
			return true;
		}
		return super.onKeyUp(keyCode, event);
	}

	//	@Override
	public void onBackPressed(Context activity) {

		new AlertDialog.Builder(activity)
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
