package com.weibo.activity;

import java.util.List;

import com.weibo.lib.lock.PageListener;
import com.weibo.utils.DialogUtils;
import com.weibo.utils.DialogUtils.DialogCallBack;
import com.weibo.utils.Utils;

import android.app.Activity;
import android.app.ActivityManager;
import android.app.ActivityManager.RunningAppProcessInfo;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

public class BaseActivity extends Activity {
	private static final String TAG = "BaseActivity.java";
	private static PageListener pageListener;
	public static void setListener(PageListener listener) {
		pageListener = listener;
	}
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		Utils.onActivityCreateSetTheme(this);
		super.onCreate(savedInstanceState);
		if (pageListener != null) {
			pageListener.onActivityCreated(this);
		}
		Log.e(TAG, "start onCreate~~~");
		Utils.getInstance().addActivity(this);
		if (!Utils.isNetworkConnected(BaseActivity.this)) {
			DialogUtils.dialogBuilderclose(BaseActivity.this, "未联网", "请链接互联网？", new DialogCallBack() {
				@SuppressWarnings("deprecation")
				@Override
				public void callBack() {
					Intent i = new Intent(android.provider.Settings.ACTION_SETTINGS);
					startActivity(i);
				}
			});
		}
	}
	@Override
	protected void onStart() {
		// TODO Auto-generated method stub
		super.onStart();
		if (pageListener != null) {
			pageListener.onActivityStarted(this);
		}
		if (!Utils.isNetworkConnected(BaseActivity.this)) {
			DialogUtils.dialogBuilderclose(BaseActivity.this, "未联网", "请链接互联网？", new DialogCallBack() {
				@SuppressWarnings("deprecation")
				@Override
				public void callBack() {
					Intent i = new Intent(android.provider.Settings.ACTION_SETTINGS);
					startActivity(i);
				}
			});
		}
		Log.e(TAG, "start onStart~~~");
	}
	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		if (Utils.isActive) {
			if (pageListener != null) {
				pageListener.onActivityResumed(this);
			}
			Log.d(TAG, "激活后台");
		}
		Log.e(TAG, "start onResume~~~");
	}
	@Override
	protected void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
		if (pageListener != null) {
			pageListener.onActivityPaused(this);
		}
		Log.e(TAG, "start onPause~~~");
	}
	@Override
	protected void onStop() {
		// TODO Auto-generated method stub
		super.onStop();
		if (!isAppOnForeground()) {
			Utils.isActive = true;
			// app 进入后台
			// 全局变量isActive = false 记录当前已经进入后台
		}
		if (pageListener != null) {
			pageListener.onActivityStopped(this);
		}
		Log.e(TAG, "start onStop~~~");
	}
	@Override
	protected void onRestart() {
		// TODO Auto-generated method stub
		super.onRestart();
	}
	@Override
	protected void onSaveInstanceState(Bundle outState) {
		super.onSaveInstanceState(outState);
		if (pageListener != null) {
			pageListener.onActivitySaveInstanceState(this);
		}
	}
	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
		if (pageListener != null) {
			pageListener.onActivityDestroyed(this);
		}
		Log.e(TAG, "start onDestroy~~~");
	}
	public boolean isAppOnForeground() {
		ActivityManager activityManager = (ActivityManager) getApplicationContext().getSystemService(Context.ACTIVITY_SERVICE);
		String packageName = getApplicationContext().getPackageName();
		List<RunningAppProcessInfo> appProcesses = activityManager.getRunningAppProcesses();
		if (appProcesses == null)
			return false;
		for (RunningAppProcessInfo appProcess : appProcesses) {
			if (appProcess.processName.equals(packageName) && appProcess.importance == RunningAppProcessInfo.IMPORTANCE_FOREGROUND) {
				return true;
			}
		}
		return false;
	}
}
