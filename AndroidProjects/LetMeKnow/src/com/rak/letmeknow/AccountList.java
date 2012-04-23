package com.rak.letmeknow;

import org.json.JSONObject;

import com.rak.letmeknow.R;

import android.accounts.Account;
import android.accounts.AccountManager;
import android.app.ListActivity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

public class AccountList extends ListActivity implements ActivityInterface {
	
	protected AccountManager accountManager;
	protected Intent intent;

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		launchActivity();       
	}

	@Override
	protected void onListItemClick(ListView l, View v, int position, long id) {

		Account account = (Account)getListView().getItemAtPosition(position);
		Editor editor = this.getSharedPreferences("c2dmPref", Context.MODE_PRIVATE).edit();
        editor.putString("prefemail",account.name );
		editor.commit();
		
		reportData(account.name,getIntent().getExtras().getString("regKey"));
		
		Intent intent = new Intent(this, LMKTabWidget.class);
		intent.putExtra("tabToLoad",0);
		intent.putExtra("account", account);
		startActivity(intent);
	}
	
	private void launchActivity(){
		
		accountManager = AccountManager.get(getApplicationContext());
		Account[] accounts = accountManager.getAccountsByType("com.google");
		this.setListAdapter(new ArrayAdapter(this, R.layout.accounts, accounts)); 
	}
	
	private void reportData(String email,String regKey) {
		
		try{
			JSONObject jobj = new JSONObject();
			jobj.put("email",email);
			jobj.put("c2dmid", regKey);
			new MyHttpClient(this).execute(URLReader.getHost()+"Register",jobj.toString());
		}catch(Exception e){
			e.printStackTrace();
		}
	}

	public void onData(String data) {
		System.out.println("In on data of AccountList "+ data );	
		
	}
		
}
