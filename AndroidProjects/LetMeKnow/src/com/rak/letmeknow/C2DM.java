package com.rak.letmeknow;

import java.util.List;

import com.rak.letmeknow.R;

import android.accounts.Account;
import android.accounts.AccountManager;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.PendingIntent;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.provider.Settings;

public class C2DM extends Activity {
	/** Called when the activity is first created. */


	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
	}

	@Override
	public void onResume(){
		super.onResume();
		verifyStuff();
	}

	private void verifyStuff(){
		LocationManager lm = (LocationManager) getSystemService(Context.LOCATION_SERVICE);	
		Location lastKnownLocation = null;
		List<String> providers = lm.getProviders(true);

		/* Loop over the array backwards, and if you get an accurate location, then break out the loop*/

		for (int i = providers.size()-1; i >= 0; i--) {
			lastKnownLocation = lm.getLastKnownLocation(providers.get(i));
			if (lastKnownLocation != null)
			{  
				double lat = lastKnownLocation.getLatitude();
				double lng = lastKnownLocation.getLongitude();
				Editor editor = this.getSharedPreferences("c2dmPref", Context.MODE_PRIVATE).edit();
				editor.putString("lat", new Double(lat).toString());
				editor.putString("lng", new Double(lng).toString());
				editor.commit();
				break;
			}
		}

		AccountManager accountManager = AccountManager.get(getApplicationContext());
		Account[] accounts = accountManager.getAccountsByType("com.google");

		if(accounts.length == 0) {
			launchAlertAccountOptions();
		}
		else if (lastKnownLocation == null && !lm.isProviderEnabled( LocationManager.GPS_PROVIDER ) ) {
			buildAlertMessageNoGps();
		}  
		else{
			launchActivity();
		}
	}

	public void launchActivity() {

		SharedPreferences pref = this.getSharedPreferences("c2dmPref",Context.MODE_PRIVATE);
		String regKey = pref.getString("registrationKey", null);
		String email = pref.getString("prefemail",null);

		LocationFinder find = new LocationFinder(this);
		LocationManager lm = (LocationManager) getSystemService(Context.LOCATION_SERVICE);		

		lm.requestLocationUpdates(LocationManager.GPS_PROVIDER, 100000 , 0 , find);
		Location lastKnownLocation = lm.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);

		if (lastKnownLocation != null) {
			double lat = lastKnownLocation.getLatitude();
			double lng = lastKnownLocation.getLongitude();
		}


		if(regKey == null) {
			Intent registrationIntent = new Intent("com.google.android.c2dm.intent.REGISTER");
			registrationIntent.putExtra("app", PendingIntent.getBroadcast(this, 0, new Intent(), 0));
			registrationIntent.putExtra("sender","anjain9@gmail.com");
			this.startService(registrationIntent);
		}
		else if(email == null){
			System.out.println("inside account thingy");
			Intent accountIntent = new Intent(this,AccountList.class);
			accountIntent.putExtra("regKey",regKey) ;
			this.startActivity(accountIntent);
		}
		else {
			Intent intent = new Intent(this, LMKTabWidget.class);
			intent.putExtra("email",email);
			intent.putExtra("tabToLoad",0);
			startActivity(intent);
		}
	}

	private void launchGPSOptions() {
		final Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
		intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		startActivityForResult(intent, 0);
	}  

	protected void onActivityResult (int requestCode, int resultCode, Intent data){
		if(requestCode == 1)	
			verifyStuff();
	}

	private void buildAlertMessageNoGps() {

		final AlertDialog.Builder builder = new AlertDialog.Builder(this);
		builder.setMessage("Yout GPS seems to be disabled, do you want to enable it?")
		.setCancelable(false)
		.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
			public void onClick(@SuppressWarnings("unused") final DialogInterface dialog, @SuppressWarnings("unused") final int id) {
				launchGPSOptions(); 
			}
		})
		.setNegativeButton("No", new DialogInterface.OnClickListener() {
			public void onClick(final DialogInterface dialog, @SuppressWarnings("unused") final int id) {
				dialog.cancel();
				launchActivity();
			}
		});
		final AlertDialog alert = builder.create();
		alert.show();
	}



	private void launchAlertAccountOptions() {

		final AlertDialog.Builder builder = new AlertDialog.Builder(this);
		builder.setMessage("No Google Account Configured. Please configure one")
		.setIcon(R.drawable.icon)
		.setTitle("Alert")
		.setCancelable(false)
		.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
			public void onClick(@SuppressWarnings("unused") final DialogInterface dialog, @SuppressWarnings("unused") final int id) {
				launchAccountOptions(); 
			}
		})
		.setNegativeButton("No", new DialogInterface.OnClickListener() {
			public void onClick(final DialogInterface dialog, @SuppressWarnings("unused") final int id) {
				dialog.cancel();
				System.exit(0);
			}
		});


		final AlertDialog alert = builder.create();
		alert.show();
	}

	private void launchAccountOptions(){
		final Intent intent = new Intent(Settings.ACTION_SYNC_SETTINGS);
		intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		startActivityForResult(intent, 1);
	}
}