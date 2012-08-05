package com.example.jellybeananimationexample;

import android.app.Activity;
import android.app.ActivityOptions;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends Activity {

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
	}

	public void scaleupAnimation(View view) {
		// Create a scale-up animation that originates at the button
		// being pressed.
		ActivityOptions opts = ActivityOptions.makeScaleUpAnimation(view, 0, 0,
				view.getWidth(), view.getHeight());
		// Request the activity be started, using the custom animation options.
		startActivity(new Intent(MainActivity.this, AnimationActivity.class),
				opts.toBundle());
	}

	public void thumbNailScaleAnimation(View view) {
		view.setDrawingCacheEnabled(true);
		view.setPressed(false);
		view.refreshDrawableState();
		Bitmap bitmap = view.getDrawingCache();
		ActivityOptions opts = ActivityOptions.makeThumbnailScaleUpAnimation(
				view, bitmap, 0, 0);
		// Request the activity be started, using the custom animation options.
		startActivity(new Intent(MainActivity.this, AnimationActivity.class),
				opts.toBundle());
		view.setDrawingCacheEnabled(false);
	}

	public void fadeAnimation(View view) {
		ActivityOptions opts = ActivityOptions.makeCustomAnimation(
				MainActivity.this, R.anim.fade_in, R.anim.fade_out);
		// Request the activity be started, using the custom animation options.
		startActivity(new Intent(MainActivity.this, AnimationActivity.class),
				opts.toBundle());
	}

}
