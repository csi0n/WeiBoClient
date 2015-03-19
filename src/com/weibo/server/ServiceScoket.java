package com.weibo.server;

import java.io.File;
import java.io.IOException;
import java.net.*;

import com.weibo.R;
import com.weibo.activity.WeiBoMainTab;
import com.weibo.utils.FileUtils;

import android.annotation.SuppressLint;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Environment;
import android.os.IBinder;
import android.util.Log;

/**
 * @作者:陈华清
 * 
 * @版本:1.0
 * @生成时期:2014年8月4日 下午7:55:20
 * @com.server
 */
public class ServiceScoket extends Service {
	private static final String TAG = "ServiceScoket.java";
	private static Context c;
	@Override
	public IBinder onBind(Intent intent) {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public void onCreate() {
		Log.v(TAG, "服务启动");
	}
	@Override
	public void onDestroy() {
	}
	int port = 7000;
	ServerSocket server = null;
	Socket client = null;
	static NotificationManager manager;
	@Override
	public void onStart(Intent intent, int startId) {
		manager = (NotificationManager) ServiceScoket.this.getSystemService(NOTIFICATION_SERVICE);
		c = ServiceScoket.this;
		if (server == null) {
			try {
				server = new ServerSocket(port);
				Log.d(TAG, "Scoket监听开始");
				new Thread(new Runnable() {
					@Override
					public void run() {
						// TODO Auto-generated method stub
						while (true) {
							try {
								client = server.accept();
							} catch (IOException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
							new WebThread(client, FileUtils.SDPATH).start();
						}
					}
				}).start();
			} catch (Exception e) {
				Log.d(TAG, e.toString());
				Log.d(TAG, "端口已经打开");
			}
		}
	}
	public static void noti(String title, String context) {
		Notification notification = new Notification(R.drawable.logo, "tickerText", System.currentTimeMillis());
		notification.flags = Notification.FLAG_AUTO_CANCEL;
		notification.flags = Notification.FLAG_NO_CLEAR;
		notification.vibrate = new long[]{1000, 200, 1000};
		Intent intent = new Intent(c, WeiBoMainTab.class);
		PendingIntent contentIntent = PendingIntent.getActivity(c, 0, intent, 0);
		notification.setLatestEventInfo(c, title, context, contentIntent);
		manager.notify(0, notification);
	}
}
