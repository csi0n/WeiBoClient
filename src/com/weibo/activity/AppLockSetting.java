/**weibotest
 * 
 *
 *TODO
 */
package com.weibo.activity;
import com.weibo.R;
import com.weibo.lib.lock.AppLock;
import com.weibo.lib.lock.AppLockActivity;
import com.weibo.lib.lock.LockManager;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

/**
 * @作者:陈华清
 * 
 * @版本:1.0
 * @生成时期:2014年8月6日 下午9:33:43
 * @com.api.example.app
 */
public class AppLockSetting extends BaseActivity implements OnClickListener {
	ImageView close;
	Button enable_btn, change_btn;

	public void findview() {
		close = (ImageView) findViewById(R.id.example_right);
		close.setOnClickListener(this);
		enable_btn = (Button) findViewById(R.id.disable_btn);
		enable_btn.setOnClickListener(this);
		change_btn = (Button) findViewById(R.id.change_btn);
		change_btn.setOnClickListener(this);
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.applock_setting_main_layout);
		findview();
		updateUI();
	}

	private void updateUI() {
		if (LockManager.getInstance().getAppLock().isPasscodeSet()) {
			enable_btn.setText("关闭密码");
			change_btn.setEnabled(true);
		} else {
			enable_btn.setText("打开密码");
			change_btn.setEnabled(false);
		}
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.example_right:
			finish();
			break;
		case R.id.disable_btn:
			int type = LockManager.getInstance().getAppLock().isPasscodeSet() ? AppLock.DISABLE_PASSLOCK
					: AppLock.ENABLE_PASSLOCK;
			Intent intent = new Intent(this, AppLockActivity.class);
			intent.putExtra(AppLock.TYPE, type);
			startActivityForResult(intent, type);
			break;
		case R.id.change_btn:
			Intent intent1 = new Intent(this, AppLockActivity.class);
			intent1.putExtra(AppLock.TYPE, AppLock.CHANGE_PASSWORD);
			intent1.putExtra(AppLock.MESSAGE, "输入旧密码");
			startActivityForResult(intent1, AppLock.CHANGE_PASSWORD);
			break;

		}
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		switch (requestCode) {
		case AppLock.DISABLE_PASSLOCK:
			break;
		case AppLock.ENABLE_PASSLOCK:
		case AppLock.CHANGE_PASSWORD:
			if (resultCode == RESULT_OK) {
				Toast.makeText(this, "设置密码", Toast.LENGTH_SHORT).show();
			}
			break;
		default:
			break;
		}
		updateUI();
	}

}
