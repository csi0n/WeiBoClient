package com.weibo.activity;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.message.BasicNameValuePair;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.weibo.application.*;
import com.weibo.utils.ClientUtils;
import com.weibo.utils.DialogUtils;
import com.weibo.utils.DialogUtils.DialogCallBack;
import com.weibo.utils.FaceConversionUtil;
import com.weibo.view.widget.LoadingAlertAnim;
import com.weibo.R;
/**
 * 
 * 转发一条微博时调用
 * 
 */
public class RelayWeiBo extends BaseActivity {
	TextView close_btn, send_btn;
	EditText replayText;
	String result = "";
	ImageButton find_me_btn, pick_photo_btn, topic_btn, face_btn;
	LoadingAlertAnim loading;
	private static final String TAG = "RelayWeiBo.java";
	protected static final int success = 0;
	protected static final int error = 1;
	private Handler handler = new Handler() {
		@SuppressLint("ResourceAsColor")
		public void handleMessage(Message msg) {
			switch (msg.what) {
			case success:
				loading.dismiss();
				dispToast("发送成功！");
				((Mykey) getApplication()).setRefresh("1");
				RelayWeiBo.this.finish();
				break;
			case error:
				dispToast("发送失败！");
				if (loading != null) {
					loading.dismiss();
				}
				break;

			}
		}
	};

	private void textCountSet() {
		String textContent = replayText.getText().toString();
		int currentLength = textContent.length();
		if (currentLength > 0) {
			FaceConversionUtil.MygetColor(RelayWeiBo.this, close_btn, R.color.yellow);
			FaceConversionUtil.MygetColor(RelayWeiBo.this, send_btn, R.color.yellow);
		} else {
			FaceConversionUtil.MygetColor(RelayWeiBo.this, close_btn, R.color.white);
			FaceConversionUtil.MygetColor(RelayWeiBo.this, send_btn, R.color.white);
		}
		if (currentLength <= 140) {
			replayText.setTextColor(Color.BLACK);
		} else {
			replayText.setTextColor(Color.RED);
		}
	}

	OnClickListener listener = new OnClickListener() {
		@Override
		public void onClick(View view) {
			switch (view.getId()) {
			case R.id.findmebtn:
				Intent inten = new Intent(RelayWeiBo.this, Find.class);
				RelayWeiBo.this.startActivity(inten);
				break;
			case R.id.textView1:
				RelayWeiBo.this.finish();
				break;
			case R.id.textView2:
				if (replayText.getText().length() == 0) {
					replayText.setError("请输入内容！");
					FaceConversionUtil.shake(RelayWeiBo.this, replayText);
				} else if (replayText.getText().length() > 140) {
					FaceConversionUtil.shake(RelayWeiBo.this, replayText);
					replayText.setError("你转发的内容过长！");
				} else {
					loading = new LoadingAlertAnim(RelayWeiBo.this, R.style.MyDialogStyle, "发送中...");
					//loading.s = "发送中。。。";
					loading.setCanceledOnTouchOutside(false);
					loading.show();
					// TODO Auto-generated method stub
					new AsyncTask<Void, Void, Void>() {
						protected Void doInBackground(Void... param) {
							List<BasicNameValuePair> params = new ArrayList<BasicNameValuePair>();
							params.add(new BasicNameValuePair("oauth_token", ((Mykey) getApplication()).getOauth_token()));
							params.add(new BasicNameValuePair("oauth_token_secret", ((Mykey) getApplication()).getOauth_token_secret()));
							params.add(new BasicNameValuePair("user_id", ((Mykey) getApplication()).getUid()));
							params.add(new BasicNameValuePair("content", replayText.getText().toString()));
							params.add(new BasicNameValuePair("feed_id", getIntent().getStringExtra("feed_id")));
							result = ClientUtils.post_str(ClientUtils.BASE_URL + RelayWeiBo.this.getString(ClientUtils.WeiboStatuses_repost), params, RelayWeiBo.this);
							return null;
						}

						@Override
						protected void onPostExecute(Void resul) {
							if (!result.equals("0")) {
								((Mykey) getApplication()).setIsdel("1");
								if (loading != null) {
									loading.dismiss();
								}
								((Mykey) getApplication()).setShoulddissmiss("1");
								FaceConversionUtil.dispToast(RelayWeiBo.this, "发送成功！");
								((Mykey) getApplication()).setRefresh("1");
								RelayWeiBo.this.finish();
							} else {
								if (loading != null) {
									loading.dismiss();
								}
								FaceConversionUtil.dispToast(RelayWeiBo.this, "发送失败！");
							}
							Log.d(TAG, "评论获得的服务器返回数据:" + result);
						}
					}.execute(null, null, null);
				}
				break;
			}
		}
	};

	public void findview() {
		find_me_btn = (ImageButton) findViewById(R.id.findmebtn);
		find_me_btn.setOnClickListener(listener);
		pick_photo_btn = (ImageButton) findViewById(R.id.share_imagechoose);
		topic_btn = (ImageButton) findViewById(R.id.huatibtn);
		face_btn = (ImageButton) findViewById(R.id.biaoqingbtn);
		replayText = (EditText) findViewById(R.id.share_content);
		replayText.setOnClickListener(listener);
		close_btn = (TextView) findViewById(R.id.textView1);
		close_btn.setOnClickListener(listener);
		send_btn = (TextView) findViewById(R.id.textView2);
		send_btn.setOnClickListener(listener);
		topic_btn.setVisibility(View.GONE);
		pick_photo_btn.setVisibility(View.GONE);
		TextWatcher watcher = new TextWatcher() {
			public void onTextChanged(CharSequence s, int start, int before, int count) {
				textCountSet();
			}

			public void beforeTextChanged(CharSequence s, int start, int count, int after) {
				textCountSet();
			}

			@Override
			public void afterTextChanged(Editable s) {
				textCountSet();
			}

		};
		replayText.addTextChangedListener(watcher);
	}

	public void dispToast(String str) {
		Toast.makeText(this, str, Toast.LENGTH_SHORT).show();
	}

	public void listen() {

	}

	protected void onStop() {
		super.onStop();
		// /注消
		sensorManager.unregisterListener(shakeListener);
	}

	protected void onPause() {
		super.onPause();
		// /注消
		sensorManager.unregisterListener(shakeListener);
	}

	// /SensorManager的监听程序
	private final SensorEventListener shakeListener = new SensorEventListener() {
		// /
		@Override
		public void onSensorChanged(SensorEvent se) {
			float x = se.values[0];
			float y = se.values[1];
			float z = se.values[2];
			float shake = x * x + y * y + z * z;
			if ((!showSettings) && (shake > 1100)) {
				showSettings = true;
				// 开始处理摇晃事件
				Log.d("Sharking", "检测到摇晃");
				DialogUtils.dialogBuilder(RelayWeiBo.this, "删除内容？", "确定要删除内容？", new DialogCallBack() {
					@Override
					public void callBack() {
						replayText.setText("");

					}
				});
				// 处理结束
				showSettings = false;
			}
		}

		public void onAccuracyChanged(Sensor sensor, int accuracy) {
		}
	};
	private boolean showSettings = false;

	protected void onResume() {
		super.onResume();
		sensorManager.registerListener(shakeListener, sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER), SensorManager.SENSOR_DELAY_FASTEST);
		showSettings = false;
		// /返回true表示注册成功，flase则反之
		if (((Mykey) getApplication()).getFind_me() != null && ((Mykey) getApplication()).getFind_me().length() > 0) {
			replayText.append("@" + ((Mykey) getApplication()).getFind_me() + " ");
			((Mykey) getApplication()).setFind_me(null);
		} else {
			Log.d(TAG, "没有检测到@我的好友!");
		}
		Log.d(TAG, "Back");
	}

	public void inint() {
		replayText.setText("//" + ((Mykey) getApplication()).getMymap().getContent());
	}

	private SensorManager sensorManager;

	public void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
		setContentView(R.layout.replayweibo);
		findview();
		sensorManager = (SensorManager) this.getBaseContext().getSystemService(Context.SENSOR_SERVICE);
		inint();
	}
}
