/**weibotest
 * 
 *
 *TODO
 */
package com.weibo.activity.selectpop;
import java.util.HashMap;

import org.json.JSONArray;
import org.json.JSONObject;

import com.weibo.R;
import com.weibo.activity.BaseActivity;
import com.weibo.lib.Defs;
import com.weibo.utils.FaceConversionUtil;
import com.weibo.utils.Utils;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Message;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

/**
 * @作者:陈华清
 * 
 * @版本:1.0
 * @生成时期:2014年7月22日 下午5:09:26
 * @com.ch
 */
public class PhonePopupWindow extends BaseActivity implements OnClickListener {
	Button colse_btn, dial_btn, copy_btn;
	private static final String TAG = "PhonePopupWindow.java";
	String key = null;
	private static final Uri PROFILE_URI = Uri.parse(Defs.PHONE_SCHEMA);
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.phone_number_pop);
		findview();
		extractUidFromUri();
		Utils.getInstance().addActivity(this);
	}
	public void findview() {
		colse_btn = (Button) findViewById(R.id.btn_cancel);
		colse_btn.setOnClickListener(this);
		dial_btn = (Button) findViewById(R.id.btn_dial);
		dial_btn.setOnClickListener(this);
		copy_btn = (Button) findViewById(R.id.btn_copy);
		copy_btn.setOnClickListener(this);
	}
	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.btn_cancel:
			PhonePopupWindow.this.finish();
			break;
		case R.id.btn_copy:
			FaceConversionUtil.copy(key,this);
			Log.d(TAG, FaceConversionUtil.paste(this));
			FaceConversionUtil.dispToast(PhonePopupWindow.this,"复制成功！找个地方粘贴去吧！");
			PhonePopupWindow.this.finish();
			break;
		case R.id.btn_dial:
			Intent phoneIntent = new Intent("android.intent.action.CALL",
			Uri.parse("tel:" + key));
			startActivity(phoneIntent);
			break;
		}
	}

	protected void onStop() {
		super.onStop();
	}

	protected void onPause() {
		super.onPause();

	}

	protected void onResume() {
		super.onResume();
	}

	@Override
	public boolean onTouchEvent(MotionEvent event) {
		finish();
		return true;
	}
	private void extractUidFromUri() {
		Uri uri = getIntent().getData();
		if (uri != null && PROFILE_URI.getScheme().equals(uri.getScheme())) {
			key = uri.getQueryParameter(Defs.PARAM_UID);
			Log.d(TAG,key);

		}
	}
}
