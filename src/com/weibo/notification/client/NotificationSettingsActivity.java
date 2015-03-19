package com.weibo.notification.client;
import com.weibo.R;
import com.weibo.server.FloatService;
import com.weibo.utils.Utils;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.preference.CheckBoxPreference;
import android.preference.Preference;
import android.preference.Preference.OnPreferenceChangeListener;
import android.preference.PreferenceActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
public class NotificationSettingsActivity extends PreferenceActivity {
	public NotificationSettingsActivity() {
	}
	@SuppressWarnings("deprecation")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		Utils.onActivityCreateSetTheme(this);
		super.onCreate(savedInstanceState);
		addPreferencesFromResource(R.xml.pushinforsetting);
		find();
		setPreferenceDependencies();
		notifyPref = (CheckBoxPreference) findPreference("SETTINGS_NOTIFICATION_ENABLED");
		if (notifyPref.isChecked()) {
			notifyPref.setTitle("通知中心开启");
		} else {
			notifyPref.setTitle("通知中心关闭");
		}
		Utils.getInstance().addActivity(this);
	}
	CheckBoxPreference notifyPref, soundPref, vibratePref, flag_settting;
	@SuppressWarnings("deprecation")
	private void find() {
		notifyPref = (CheckBoxPreference) findPreference("SETTINGS_NOTIFICATION_ENABLED");
		notifyPref.setTitle("通知中心开启");
		notifyPref.setSummaryOn("获取通知消息");
		notifyPref.setSummaryOff("不获取通知中心消息");
		notifyPref.setDefaultValue(Boolean.TRUE);
		notifyPref
				.setOnPreferenceChangeListener(new Preference.OnPreferenceChangeListener() {
					public boolean onPreferenceChange(Preference preference,
							Object newValue) {
						boolean checked = Boolean.valueOf(newValue.toString());
						if (checked) {
							preference.setTitle("通知中心开启");
						} else {
							preference.setTitle("通知中心关闭");
						}
						return true;
					}
				});
		soundPref = (CheckBoxPreference) findPreference("SETTINGS_SOUND_ENABLED");
		soundPref.setKey(Constants.SETTINGS_SOUND_ENABLED);
		soundPref.setTitle("声音");
		soundPref.setSummary("打开声音");
		soundPref.setDefaultValue(Boolean.TRUE);
		vibratePref = (CheckBoxPreference) findPreference("SETTINGS_VIBRATE_ENABLED");
		vibratePref.setKey(Constants.SETTINGS_VIBRATE_ENABLED);
		vibratePref.setTitle("震动");
		vibratePref.setSummary("通知震动");
		vibratePref.setDefaultValue(Boolean.TRUE);
		flag_settting = (CheckBoxPreference) findPreference("SETTINGS_Flag_ENABLED");
		flag_settting
				.setOnPreferenceChangeListener(new OnPreferenceChangeListener() {
					@Override
					public boolean onPreferenceChange(Preference preference,
							Object newValue) {
						// TODO Auto-generated method stub
						boolean checked = Boolean.valueOf(newValue.toString());
						if (checked) {
							Intent service = new Intent();
							service.setClass(NotificationSettingsActivity.this,
									FloatService.class);
							startService(service);
						} else {
							Intent serviceStop = new Intent();
							serviceStop.setClass(
									NotificationSettingsActivity.this,
									FloatService.class);
							stopService(serviceStop);
						}
						return true;
					}
				});
	}

	private void setPreferenceDependencies() {
		Preference soundPref = getPreferenceManager().findPreference(
				Constants.SETTINGS_SOUND_ENABLED);
		if (soundPref != null) {
			soundPref.setDependency(Constants.SETTINGS_NOTIFICATION_ENABLED);
		}
		Preference vibratePref = getPreferenceManager().findPreference(
				Constants.SETTINGS_VIBRATE_ENABLED);
		if (vibratePref != null) {
			vibratePref.setDependency(Constants.SETTINGS_NOTIFICATION_ENABLED);
		}
	}

}
