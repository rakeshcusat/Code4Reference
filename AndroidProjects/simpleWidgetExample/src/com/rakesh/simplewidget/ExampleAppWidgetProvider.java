package com.rakesh.simplewidget;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Log;
import android.widget.RemoteViews;
import android.widget.Toast;

public class ExampleAppWidgetProvider extends AppWidgetProvider {

	int[] mAppWidgetIds;

	public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {

		final int N = appWidgetIds.length;
		this.mAppWidgetIds = appWidgetIds;
		/*
    Log.i("ExampleWidget",  "Updating widgets " + Arrays.asList(appWidgetIds));
    // Perform this loop procedure for each App Widget that belongs to this
    // provider
    for (int i = 0; i < N; i++) {
      int appWidgetId = appWidgetIds[i];
      // Create an Intent to launch ExampleActivity
      Intent intent = new Intent(context, SimpleWidgetExampleActivity.class);
      PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, intent, 0);
      // Get the layout for the App Widget and attach an on-click listener
      // to the button
      RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.widget1);
      views.setOnClickPendingIntent(R.id.BtEnableDisable, pendingIntent);
      // To update a label
     // views.setTextViewText(R.id.widget1label, df.format(new Date()));
      // Tell the AppWidgetManager to perform an update on the current app
      // widget
      appWidgetManager.updateAppWidget(appWidgetId, views);
    }
		 */

		// Get all ids
		ComponentName thisWidget = new ComponentName(context,
				ExampleAppWidgetProvider.class);
		int[] allWidgetIds = appWidgetManager.getAppWidgetIds(thisWidget);

		// Build the intent to call the service
		Intent intent = new Intent(context.getApplicationContext(),
				UpdateWidgetService.class);
		intent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_IDS, allWidgetIds);

		// Update the widgets via the service
		context.startService(intent);
	}

//	@Override
//	public void onReceive(Context context, Intent intent) {
//		super.onReceive(context, intent);
//		NetworkInfo info = (NetworkInfo)intent.getParcelableExtra(ConnectivityManager.EXTRA_NETWORK_INFO);
//		if(info.getType() == ConnectivityManager.TYPE_MOBILE){ 
//			updateAppWidget(context, mAppWidgetIds, info.isConnectedOrConnecting());
//		}
//	}

	public static void updateAppWidget(Context context,
			int[] appWidgetIds, boolean enable){
		
		if(appWidgetIds.length > 0){
			
			AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(context);
			RemoteViews updateViews = new RemoteViews(context.getPackageName(), R.layout.widget1);
			if(enable){
				updateViews.setTextColor(R.id.BtEnableDisable, Color.GREEN);
				updateViews.setTextViewText(R.id.BtEnableDisable, "Enabled");

			}else{
				updateViews.setTextColor(R.id.BtEnableDisable, Color.GRAY);
				updateViews.setTextViewText(R.id.BtEnableDisable, "Disabled");
			}
			appWidgetManager.updateAppWidget(appWidgetIds, updateViews);
			Toast.makeText(context, "updateAppWidget() ", Toast.LENGTH_SHORT).show();

		}
	}
}