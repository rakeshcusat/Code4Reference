/**
 * 
 */
package com.rak.letmeknow;

import java.util.Calendar;

import org.json.JSONException;
import org.json.JSONObject;

import com.rak.letmeknow.R;

import android.app.AlertDialog;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;


/**
 * @author user
 *
 */
public class C2DMReceiver extends BroadcastReceiver {

	private final String TAG=getClass().getSimpleName(); 
	private static String KEY = "c2dmPref";
	private static String REGISTRATION_KEY = "registrationKey";
	private static int number = 0;


	private Context context;

	@Override
	public void onReceive(Context context, Intent intent) {
		Log.v(TAG,"inside onReceive");
		this.context = context;
		if (intent.getAction().equals("com.google.android.c2dm.intent.REGISTRATION")) {
			handleRegistration(context, intent);
		} else if (intent.getAction().equals("com.google.android.c2dm.intent.RECEIVE")) {
			handleMessage(context, intent);
		}
	}

	private void handleRegistration(Context context, Intent intent) {

		Log.v(TAG,"handleRegistration()");
		String registration = intent.getStringExtra("registration_id");

		if (intent.getStringExtra("error") != null) {
			Log.d("c2dm", "registration failed");
			String error = intent.getStringExtra("error");
			System.out.println(error);
			if(error == "SERVICE_NOT_AVAILABLE"){
				Log.d("c2dm", "SERVICE_NOT_AVAILABLE");
			}else if(error == "ACCOUNT_MISSING"){
				Log.d("c2dm", "ACCOUNT_MISSING");
			}else if(error == "AUTHENTICATION_FAILED"){
				Log.d("c2dm", "AUTHENTICATION_FAILED");
			}else if(error == "TOO_MANY_REGISTRATIONS"){
				Log.d("c2dm", "TOO_MANY_REGISTRATIONS");
			}else if(error == "INVALID_SENDER"){
				Log.d("c2dm", "INVALID_SENDER");
			}else if(error == "PHONE_REGISTRATION_ERROR"){
				Log.d("c2dm", "PHONE_REGISTRATION_ERROR");
			}

			callRegistrationError(context);

		} else if (intent.getStringExtra("unregistered") != null) {
			Log.d("c2dm", "unregistered");

		} else if (registration != null) {

			Editor editor = context.getSharedPreferences(KEY, Context.MODE_PRIVATE).edit();
			editor.putString(REGISTRATION_KEY, registration);
			System.out.println(registration);
			editor.commit();

			Intent accountIntent = new Intent(context,AccountList.class);
			accountIntent.putExtra("regKey",registration) ;
			accountIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK); 
			context.startActivity(accountIntent);
		}
		Toast.makeText(context, registration, Toast.LENGTH_LONG);
	}

	private void handleMessage(Context context, Intent intent)
	{
		Log.v(TAG,"handleMessage");
		Log.e("C2DM", "Message: Fantastic!!!");
		try{

			Bundle extras = intent.getExtras();
			if (extras != null) {
				String data = (String)extras.get("payload");
				JSONObject jobj = new JSONObject(data);
				String email = jobj.getString("email");

				if(decide(jobj))
					sendNotification(context, intent, email);
			}	
		}
		catch(JSONException e){
			e.printStackTrace();
		}
	}

	private boolean decide(JSONObject jobj) throws JSONException {

		boolean sendNotification = false;

		String gpsLoc = jobj.getString("gpsLoc");
		String time = jobj.getString("time");
		String date = jobj.getString("date");

		String [] gpsLocArray = gpsLoc.split(" ");
		Double evlat = Double.parseDouble(gpsLocArray[0]);
		Double evlng = Double.parseDouble(gpsLocArray[1]);

		SharedPreferences pref = context.getSharedPreferences("c2dmPref", Context.MODE_PRIVATE);
		double lat = Double.parseDouble(pref.getString("lat", "0000"));
		double lng = Double.parseDouble(pref.getString("lng", "0000"));

		if( dateTimeRule(date,time) &&  GetDistanceBetweenPoints(evlat,evlng, lat, lng) < 15 )
			sendNotification = true;
		return sendNotification;
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

	private void sendNotification(Context context,Intent intent, String email) {

		String ns = Context.NOTIFICATION_SERVICE;
		NotificationManager mNotificationManager = (NotificationManager) context.getSystemService(ns);
		int icon = R.drawable.icon;
		CharSequence tickerText = "New LMK Event";
		long when = System.currentTimeMillis();

		Notification notification = new Notification(icon, tickerText, when);
		CharSequence contentTitle = "New Event Received";
		CharSequence contentText = "From :"+email;
		Intent notificationIntent = new Intent(context, LMKTabWidget.class);
		notificationIntent.putExtra("tabToLoad",3);
		PendingIntent contentIntent = PendingIntent.getActivity(context, 0, notificationIntent, 0);

		notification.setLatestEventInfo(context, contentTitle, contentText, contentIntent);
		notification.defaults |= Notification.DEFAULT_SOUND;
		number++;
		System.out.println("Number" +number);
		mNotificationManager.notify(number, notification);

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

	private void callRegistrationError(Context context) {
		Toast.makeText(context, "Registration Error, Try again", Toast.LENGTH_LONG).show();

		System.exit(0);
	}
/*		final AlertDialog.Builder builder = new AlertDialog.Builder(context);
		builder.setMessage("Registration Failed with C2DM. Please restart applicaiton")
		.setCancelable(false)
		.setPositiveButton("OK", new DialogInterface.OnClickListener() {
			public void onClick(@SuppressWarnings("unused") final DialogInterface dialog, @SuppressWarnings("unused") final int id) {
			}
		});
		final AlertDialog alert = builder.create();
		alert.show();
	}*/
}
