package com.weibo.activity;
import com.weibo.activity.About;
import com.weibo.notification.client.ServiceManager;
import com.weibo.utils.ACache;
import com.weibo.utils.DialogUtils;
import com.weibo.utils.DialogUtils.DialogCallBack;
import com.weibo.utils.Utils;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import com.weibo.R;
/**
 * 
 * 更多选项卡的处理
 * 
 */
public class Tab5Activity extends BaseActivity implements OnClickListener {
	ImageView imageview1;
	Button button1, button2, setting, myweiba, theme, myqr, lock;
	TextView textview1;
	ACache mcache;
	private static final String TAG = "Tab5Activity.java";
	public void findview() {
		button1 = (Button) findViewById(R.id.button1);
		button1.setOnClickListener(this);
		button2 = (Button) findViewById(R.id.button2);
		button2.setOnClickListener(this);
		setting = (Button) findViewById(R.id.button3);
		setting.setOnClickListener(this);
		myweiba = (Button) findViewById(R.id.myweiba);
		myweiba.setOnClickListener(this);
		theme = (Button) findViewById(R.id.theme);
		theme.setOnClickListener(this);
		myqr = (Button) findViewById(R.id.myqr);
		myqr.setOnClickListener(this);
		lock = (Button) findViewById(R.id.lock);
		lock.setOnClickListener(this);
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK) {
			DialogUtils.dialogBuilder(Tab5Activity.this, "提示", "确定要退出？", new DialogCallBack() {
				@Override
				public void callBack() {
					Utils.getInstance().exit(Tab5Activity.this);
				}
			});
		}
		return super.onKeyDown(keyCode, event);
	}
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.setting);
		mcache = ACache.get(this);
		findview();
		Utils.getInstance().addActivity(this);
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		if (v == button1) {
			Intent intent = new Intent(Tab5Activity.this, About.class);
			Tab5Activity.this.startActivity(intent);
		} else if (v == button2) {
			DialogUtils.dialogBuilder(Tab5Activity.this, "提示", "是否注销当前登陆的账号?", new DialogCallBack() {
				@SuppressWarnings("deprecation")
				@Override
				public void callBack() {
					if (mcache.FindAsString("password")) {
						mcache.remove("password");
					}
					Intent i = new Intent(Tab5Activity.this, MainActivity.class);
					Tab5Activity.this.startActivity(i);
				}
			});

		} else if (v == setting) {
			ServiceManager.viewNotificationSettings(Tab5Activity.this);
		} else if (v == myweiba) {
			Intent i = new Intent(Tab5Activity.this, MyWeiBaSetting.class);
			Tab5Activity.this.startActivity(i);
		} else if (v == theme) {
			Intent i = new Intent(Tab5Activity.this, ThemeMain.class);
			Tab5Activity.this.startActivity(i);
		} else if (v == myqr) {
			Intent i = new Intent(Tab5Activity.this, MyQrActivity.class);
			Tab5Activity.this.startActivity(i);
		} else if (v == lock) {
			Intent i = new Intent(Tab5Activity.this, AppLockSetting.class);
			Tab5Activity.this.startActivity(i);
		}
	}
}
