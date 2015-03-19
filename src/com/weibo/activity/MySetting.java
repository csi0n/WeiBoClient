/**weibotest
 * 
 *
 *TODO
 */
package com.weibo.activity;
import com.weibo.notification.client.NotificationSettingsActivity2;
import com.weibo.utils.ACache;
import com.weibo.utils.DialogUtils;
import com.weibo.utils.DialogUtils.DialogCallBack;
import com.weibo.utils.Utils;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.LinearLayout;
import com.weibo.R;
/**
 * @作者:陈华清
 * 
 * @版本:1.0
 * @生成时期:2014年8月17日 上午9:05:00
 * @com.api.example.app
 */
public class MySetting extends BaseActivity implements OnClickListener {
	LinearLayout tongyong, lock, about, exit;
	ACache mcache;
	ImageView close;

	public void findview() {
		close = (ImageView) findViewById(R.id.close);
		close.setOnClickListener(this);
		tongyong = (LinearLayout) findViewById(R.id.tongyong);
		tongyong.setOnClickListener(this);
		lock = (LinearLayout) findViewById(R.id.lock);
		lock.setOnClickListener(this);
		about = (LinearLayout) findViewById(R.id.about);
		about.setOnClickListener(this);
		exit = (LinearLayout) findViewById(R.id.exit);
		exit.setOnClickListener(this);
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.mysetting);
		mcache = ACache.get(this);
		findview();
		Utils.getInstance().addActivity(this);
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		if (v == tongyong) {
			Intent i = new Intent(MySetting.this, NotificationSettingsActivity2.class);
			startActivity(i);
			// ServiceManager.viewNotificationSettings(MySetting.this);
		}
		if (v == lock) {
			Intent i = new Intent(MySetting.this, AppLockSetting.class);
			startActivity(i);
		} else if (v == about) {
			Intent intent = new Intent(MySetting.this, About.class);
			startActivity(intent);
		} else if (v == exit) {
			DialogUtils.dialogBuilder(MySetting.this, "提示", "是否注销当前登陆的账号?", new DialogCallBack() {
				@SuppressWarnings("deprecation")
				@Override
				public void callBack() {
					if (mcache.FindAsString("password")) {
						mcache.remove("password");
					}
					Intent i = new Intent(MySetting.this, MainActivity.class);
					MySetting.this.startActivity(i);
				}
			});
		} else if (v == close) {
			finish();
		}
	}
}
