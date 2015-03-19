package com.weibo.notification.client;

import com.weibo.activity.BaseActivity;
import com.weibo.R;
import com.weibo.server.FloatService;
import com.weibo.utils.Utils;
import com.weibo.view.widget.UISwitchButton;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.ImageView;
import android.widget.LinearLayout;
/**
 * @作者:陈华清
 * 
 * @版本:1.0
 * @生成时期:2014年8月18日 下午5:36:55
 * @org.androidpn.client
 */
public class NotificationSettingsActivity2 extends BaseActivity implements OnClickListener{
	UISwitchButton noti, sound, zhendong, xuanfu, autodown;
	LinearLayout soundl;
	ImageView close;
	public void findview() {
		autodown = (UISwitchButton) findViewById(R.id.autodown);
		noti = (UISwitchButton) findViewById(R.id.noti);
		sound = (UISwitchButton) findViewById(R.id.sound);
		zhendong = (UISwitchButton) findViewById(R.id.zhendong);
		xuanfu = (UISwitchButton) findViewById(R.id.xuanfu);
		soundl = (LinearLayout) findViewById(R.id.soundl);
		close=(ImageView)findViewById(R.id.example_right);
		close.setOnClickListener(this);
	}

	public void inint() {
		if (Utils.GetSetting("autodown", NotificationSettingsActivity2.this)) {
			autodown.setChecked(true);
		} else {
			autodown.setChecked(false);
		}
		if (Utils.GetSetting(Constants.SETTINGS_NOTIFICATION_ENABLED,
				NotificationSettingsActivity2.this)) {
			noti.setChecked(true);
			if (soundl.getVisibility() == View.GONE) {
				soundl.setVisibility(View.VISIBLE);
			}
		} else {
			noti.setChecked(false);
			if (soundl.getVisibility() == View.VISIBLE) {
				soundl.setVisibility(View.GONE);
			}
		}
		if (Utils.GetSetting(Constants.SETTINGS_SOUND_ENABLED,
				NotificationSettingsActivity2.this)) {
			sound.setChecked(true);
		} else {
			sound.setChecked(false);
		}
		if (Utils.GetSetting(Constants.SETTINGS_VIBRATE_ENABLED,
				NotificationSettingsActivity2.this)) {
			zhendong.setChecked(true);
		} else {
			zhendong.setChecked(false);
		}
		if (Utils.GetSetting("xuanfu", NotificationSettingsActivity2.this)) {
			xuanfu.setChecked(true);
		} else {
			xuanfu.setChecked(false);
		}
		autodown.setOnCheckedChangeListener(new OnCheckedChangeListener() {
			@Override
			public void onCheckedChanged(CompoundButton buttonView,
					boolean isChecked) {
				// TODO Auto-generated method stub
				if (isChecked) {
					Utils.PutSetting("autodown", true,
							NotificationSettingsActivity2.this);
					Intent i = new Intent("com.server.getdate");
					startService(i);
				} else {
					Utils.PutSetting("autodown", false,
							NotificationSettingsActivity2.this);
					Intent i = new Intent("com.server.getdate");
					stopService(i);
				}
			}
		});
		noti.setOnCheckedChangeListener(new OnCheckedChangeListener() {
			@Override
			public void onCheckedChanged(CompoundButton buttonView,
					boolean isChecked) {
				// TODO Auto-generated method stub
				if (isChecked) {
					if (soundl.getVisibility() == View.GONE) {
						soundl.setVisibility(View.VISIBLE);
					}
					Utils.PutSetting(Constants.SETTINGS_NOTIFICATION_ENABLED,
							true, NotificationSettingsActivity2.this);
					Intent i=new Intent("org.androidpn.client.NotificationService");
					startService(i);
					
				} else {
					if (soundl.getVisibility() == View.VISIBLE) {
						soundl.setVisibility(View.GONE);
					}
					Utils.PutSetting(Constants.SETTINGS_NOTIFICATION_ENABLED,
							false, NotificationSettingsActivity2.this);
					Intent i=new Intent("org.androidpn.client.NotificationService");
					stopService(i);
				}
			}
		});
		sound.setOnCheckedChangeListener(new OnCheckedChangeListener() {
			@Override
			public void onCheckedChanged(CompoundButton buttonView,
					boolean isChecked) {
				// TODO Auto-generated method stub
				if (isChecked) {
					Utils.PutSetting(Constants.SETTINGS_SOUND_ENABLED, true,
							NotificationSettingsActivity2.this);
				} else {
					Utils.PutSetting(Constants.SETTINGS_SOUND_ENABLED, false,
							NotificationSettingsActivity2.this);
				}
			}
		});
		zhendong.setOnCheckedChangeListener(new OnCheckedChangeListener() {

			@Override
			public void onCheckedChanged(CompoundButton buttonView,
					boolean isChecked) {
				// TODO Auto-generated method stub
				if (isChecked) {
					Utils.PutSetting(Constants.SETTINGS_VIBRATE_ENABLED, true,
							NotificationSettingsActivity2.this);
				} else {
					Utils.PutSetting(Constants.SETTINGS_VIBRATE_ENABLED, false,
							NotificationSettingsActivity2.this);
				}
			}
		});
		xuanfu.setOnCheckedChangeListener(new OnCheckedChangeListener() {
			@Override
			public void onCheckedChanged(CompoundButton buttonView,
					boolean isChecked) {
				// TODO Auto-generated method stub
				if (isChecked) {
					Utils.PutSetting("xuanfu", true,
							NotificationSettingsActivity2.this);
					Intent service = new Intent();
					service.setClass(NotificationSettingsActivity2.this,
							FloatService.class);
					startService(service);

				} else {
					Utils.PutSetting("xuanfu", false,
							NotificationSettingsActivity2.this);
					Intent serviceStop = new Intent();
					serviceStop.setClass(NotificationSettingsActivity2.this,
							FloatService.class);
					stopService(serviceStop);
				}
			}
		});
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.notificationsettingactivity_layout);
		findview();
		inint();
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		if(v==close)
		{
			finish();
		}
	}

}
