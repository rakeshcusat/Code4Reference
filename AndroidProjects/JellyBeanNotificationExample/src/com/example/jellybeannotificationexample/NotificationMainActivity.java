package com.example.jellybeannotificationexample;

import android.R;
import android.os.Bundle;
import android.app.Activity;
import android.app.Notification;
import android.app.Notification.Builder;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;
import android.support.v4.app.NavUtils;

public class NotificationMainActivity extends Activity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_main, menu);
        return true;
    }

    public void sendNotification(View view){
    	Context context = getApplicationContext();
    	Toast.makeText(context, "Just a test", Toast.LENGTH_SHORT).show();
    	createNotificationforBigText();
    }
    
   public void createNotificationforBigText(){
	   String msgText = "Jeally Bean Notification example!! "+
	               "where you will see three different kind of notification. " +
	               "you can even put the very long string here.";
	  
	   NotificationManager notificationManager = getNotificationManager();
	   PendingIntent pi = getPendingIntent();
	   Builder builder = new Notification.Builder(this);
	   builder.setContentTitle("JB Notofication")
	   .setContentText("Jelly Bean Notification")
	   .setSmallIcon(R.drawable.ic_launcher)
	   .addAction(R.drawable.ic_action_search, "show activity", pi);
	  Notification noti = new Notification.BigTextStyle(builder).bigText(msgText).build();
	  noti.flags |= Notification.FLAG_AUTO_CANCEL; //Put the auto cancel notification.
	  //noti.contentIntent = pi;  //Associate the intent here.
	  notificationManager.notify(0, noti);
   }
   
   public void createBigpictureNotification(){
	   PendingIntent pi = getPendingIntent();
	   Builder builder = new Notification.Builder(this);
	   builder.setContentTitle("BP notification") //Notification title
	   .setContentText("BigPicutre notification") //you can put subject line.
	   .setSmallIcon(R.drawable.ic_launcher) //Set your notification icon here.
	   .addAction(R.drawable.ic_action_search, "show activity", pi);
	   
	   //Now create the Big picture notification.
	   Notification notification = new Notification.BigPictureStyle(builder)
	   .bigPicture(BitmapFactory.decodeResource(getResources(), R.drawable.android_jelly_bean)).build();
	   NotificationManager notificationManager = getNotificationManager();
	   notificationManager.notify(0, notification);
   }
   public void createInboxStyleNotification(){
	   Builder builder =  new Notification.Builder()
       .setContentTitle("IS Notification")
       .setContentText("Inbox Style notification!!")
       .setSmallIcon(R.drawable.ic_launcher);
	   Notification noti = new Notification.InboxStyle(builder)
			      .addLine("IS notification 1st message")
			      .addLine("IS notification 2st message")
			      .setContentTitle("")
			      .setSummaryText("+2 more")
			      .build();
   }
   public PendingIntent getPendingIntent(){
	   return PendingIntent.getActivity(this, 0, new Intent(this, NotificationMainActivity.class), 0);
   }
   public NotificationManager getNotificationManager(){
	   return (NotificationManager)getSystemService(NOTIFICATION_SERVICE); 
   }
}
