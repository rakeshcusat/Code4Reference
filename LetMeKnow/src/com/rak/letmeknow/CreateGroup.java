package com.rak.letmeknow;

import org.json.JSONObject;

import com.rak.letmeknow.R;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class CreateGroup extends Activity implements ActivityInterface {

	@Override
	public void onCreate(Bundle icicle)
	{
		super.onCreate(icicle);
		setContentView(R.layout.create_group);

	}

	public void sendMessage(View v){

		EditText text = (EditText)this.findViewById(R.id.entry);
		Editable editText = text.getText();

		if(editText != null && editText.length() > 0) {
			try{
				JSONObject jObj = new JSONObject();
				
				jObj.put("groupName", editText.toString());
				jObj.put("email",this.getSharedPreferences("c2dmPref", 
						Context.MODE_PRIVATE).getString("prefemail",null));  
				
				new MyHttpClient(this).execute(URLReader.getHost()+"CreateGroupServ",jObj.toString());
			}catch(Exception e){
				
				Toast.makeText(this,"Error Occured", Toast.LENGTH_LONG).show();
				e.printStackTrace();
			}
		}
		else{
			Toast.makeText(this,"Please enter group name", Toast.LENGTH_LONG).show();
		}

	}

	public void onData(String data) {
		
		try{
			JSONObject jObj = new JSONObject(data);
			String status = jObj.getString("status");
			if(status!= null && status.equals("true")) {
				resetIt(new View(this));
				Toast.makeText(this,"Group Created Successfully", Toast.LENGTH_LONG).show();
			}
			else if(status!= null && status.equals("isExist")){
				System.out.println(status);
				Toast.makeText(this,"Group already exist", Toast.LENGTH_LONG).show();

			}
			else{
				Toast.makeText(this,"Server Error", Toast.LENGTH_LONG).show();
			}
		}
		catch(Exception e) {
			Toast.makeText(this,"Server Error", Toast.LENGTH_LONG).show();
			e.printStackTrace();
		}
	}
	
	public void resetIt(View v){
		EditText text = (EditText)this.findViewById(R.id.entry);
		text.setText("");		
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
