package com.weibo.notification.client;
import com.weibo.R;
import com.weibo.utils.Utils;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
public class NotificationDetailsActivity extends Activity {
	private static final String LOGTAG = LogUtil.makeLogTag(NotificationDetailsActivity.class);
	private String callbackActivityPackageName;
	private String callbackActivityClassName;
	public NotificationDetailsActivity() {
	}
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.notificationdetailsactivity_layout);
		SharedPreferences sharedPrefs = this.getSharedPreferences(Constants.SHARED_PREFERENCE_NAME, Context.MODE_PRIVATE);
		callbackActivityPackageName = sharedPrefs.getString(Constants.CALLBACK_ACTIVITY_PACKAGE_NAME, "");
		callbackActivityClassName = sharedPrefs.getString(Constants.CALLBACK_ACTIVITY_CLASS_NAME, "");
		Intent intent = getIntent();
		String notificationId = intent.getStringExtra(Constants.NOTIFICATION_ID);
		String notificationApiKey = intent.getStringExtra(Constants.NOTIFICATION_API_KEY);
		String notificationTitle = intent.getStringExtra(Constants.NOTIFICATION_TITLE);
		String notificationMessage = intent.getStringExtra(Constants.NOTIFICATION_MESSAGE);
		String notificationUri = intent.getStringExtra(Constants.NOTIFICATION_URI);
		Log.d(LOGTAG, "notificationId=" + notificationId);
		Log.d(LOGTAG, "notificationApiKey=" + notificationApiKey);
		Log.d(LOGTAG, "notificationTitle=" + notificationTitle);
		Log.d(LOGTAG, "notificationMessage=" + notificationMessage);
		Log.d(LOGTAG, "notificationUri=" + notificationUri);
		Utils.getInstance().addActivity(this);
	}
}
