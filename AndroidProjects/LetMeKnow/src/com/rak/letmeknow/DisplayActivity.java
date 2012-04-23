package com.rak.letmeknow;

import java.util.Calendar;

import org.json.JSONObject;

import com.rak.letmeknow.R;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

public class DisplayActivity extends Activity implements ActivityInterface{

	Event event;
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.event_details);

		Bundle b = getIntent().getExtras();
		Event e = (Event)b.getSerializable("item");
     
		this.event = e;
		
		TextView v = (TextView)findViewById(R.id.subject);
		v.setText("Subject: "+e.getSubject());

		TextView v1 = (TextView)findViewById(R.id.eventdetails);
		v1.setText("Message: "+e.getEventDetails());  

		TextView v2 = (TextView)findViewById(R.id.SenderName);
		v2.setText("Sender: "+e.getSenderEmail()); 

		TextView v3 = (TextView)findViewById(R.id.date);
		v3.setText("Date: "+e.getDate()); 

		TextView v4 = (TextView)findViewById(R.id.time);
		v4.setText("Time: "+e.getTime()); 

		if(!dateTimeRule(e.getDate(), e.getTime())){
			TextView v6 = (TextView)findViewById(R.id.expired);
			v6.setText("This event is expired");
		}
		
	String [] gpsLocArray = e.getGpsLoc().split(" ");
		Double evlat = Double.parseDouble(gpsLocArray[0]);
		Double evlng = Double.parseDouble(gpsLocArray[1]);

		SharedPreferences pref = this.getSharedPreferences("c2dmPref", Context.MODE_PRIVATE);
		double lat = Double.parseDouble(pref.getString("lat", "0000"));
		double lng = Double.parseDouble(pref.getString("lng", "0000"));
		
		if(GetDistanceBetweenPoints(evlat,evlng, lat, lng) > 15 ) {
			TextView v7 = (TextView)findViewById(R.id.outofreach);
			v7.setText("This event is out of reach");
		}

	}

	public static double GetDistanceBetweenPoints(double sourceLatitude, double sourceLongitude, 
			double destLatitude, double destLongitude) {
		
		double theta = sourceLongitude - destLongitude;
		double distance =
			Math.sin(DegToRad(sourceLatitude))
			* Math.sin(DegToRad(destLatitude))
			+ Math.cos(DegToRad(sourceLatitude))
			* Math.cos(DegToRad(destLatitude))
			* Math.cos(DegToRad(theta));
		distance = Math.acos(distance);
		distance = RadToDeg(distance);
		distance = distance * 60 * 1.1515;
		System.out.println("Distance" + distance);
		return (distance);
	}


	public static double DegToRad(double degrees)
	{
		return (degrees * Math.PI / 180.0);
	}

	public static double RadToDeg(double radians)
	{
		return (radians / Math.PI * 180.0);
	}
	
	public boolean dateTimeRule(String date, String time){

		boolean dateCompare = true;
		boolean timeCompare = true;
		String[] dateArray = date.split("/");
		Calendar currentCalendar = Calendar.getInstance();

		Integer [] currentDateArray = new Integer[3];
		currentDateArray[0] = currentCalendar.get(Calendar.YEAR);
		currentDateArray[1] = currentCalendar.get(Calendar.MONTH)+1;
		currentDateArray[2] = currentCalendar.get(Calendar.DAY_OF_MONTH);

		for(int i = 0 ; i < dateArray.length ; i ++){
			int toCompare = Integer.parseInt(dateArray[i]);
			if(currentDateArray[i] > toCompare){
				dateCompare = false;
				break;
			}
			else if(currentDateArray[i] == toCompare)
				continue;
			else if(currentDateArray[i] < toCompare)
				return true;
		}

		if (dateCompare == false){
			return dateCompare;
		}
		boolean hour24 = true;

		String AM_PM = "PM";

		if(time.contains("AM") || time.contains("PM")){
			hour24 = false;
			if(time.contains("AM"))
				AM_PM = "AM";
		}


		if(hour24) {
			Integer [] currentTimeArray = new Integer[2];
			currentTimeArray[0] = currentCalendar.get(Calendar.HOUR_OF_DAY);
			currentTimeArray[1] = currentCalendar.get(Calendar.MINUTE);

			String[] timeArray = time.split(":");

			for(int i = 0 ; i < timeArray.length ; i++) {
				int toCompare = Integer.parseInt(timeArray[i]);
				if(currentTimeArray[i] > toCompare) {
					timeCompare = false;
					break;
				}
				else if(currentTimeArray[i] == toCompare)
					continue;
				else if(currentTimeArray[i] < toCompare)
					return true;
			}
			return timeCompare;
		}

		Integer [] currentTimeArray = new Integer[2];

		currentTimeArray[0] = currentCalendar.get(Calendar.HOUR);
		currentTimeArray[1] = currentCalendar.get(Calendar.MINUTE);

		String current_AM_PM = "PM";
		if(currentCalendar.get(Calendar.AM_PM) == Calendar.AM)
			current_AM_PM = "AM";

		if(current_AM_PM.equals("PM") && AM_PM.equals("AM")){
			return false;
		}
		else if(current_AM_PM.equals("AM") && AM_PM.equals("PM")){
			return true;
		}

		String [] timeArray = time.split(" ");
		String [] realTimeArray = timeArray[0].split(":");

		for(int i = 0 ; i < realTimeArray.length ; i++) {
			int toCompare = Integer.parseInt(realTimeArray[i]);
			if(toCompare == 12)
				toCompare = 0 ;
			if(currentTimeArray[i] > toCompare) {
				timeCompare = false;
				break;
			}
			else if(currentTimeArray[i] == toCompare)
				continue;
			else if(currentTimeArray[i] < toCompare)
				return true;
		}
		return timeCompare;
	}
	
	public void onDelete(View v) {
		
		
		try{
			SharedPreferences pref = this.getSharedPreferences("c2dmPref",Context.MODE_PRIVATE);
			String email = pref.getString("prefemail",null);

			JSONObject jObj = new JSONObject();
			jObj.put("email",email);
			jObj.put("id",event.getId());
			new MyHttpClient(this).execute(URLReader.getHost()+"DeleteEvent",jObj.toString());

		}catch(Exception e){
			Toast.makeText(this,"Error Occurred in Deletion", Toast.LENGTH_LONG).show();
			e.printStackTrace();
		}	
	}

	public void onData(String data) {
		// TODO Auto-generated method stub
		try{
			JSONObject obj = new JSONObject(data);
			boolean status = obj.getBoolean("status");
		
			if(status)
				super.onBackPressed();
			else
				Toast.makeText(this,"Error Occurred in Deletion", Toast.LENGTH_LONG).show();
		}
		catch(Exception e){
			Toast.makeText(this,"Server Error", Toast.LENGTH_LONG).show();
			e.printStackTrace();
		}
	}
}
