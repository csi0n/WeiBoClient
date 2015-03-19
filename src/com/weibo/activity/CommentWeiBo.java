package com.weibo.activity;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.message.BasicNameValuePair;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import com.weibo.application.Mykey;
import com.weibo.utils.ClientUtils;
import com.weibo.utils.DialogUtils;
import com.weibo.utils.DialogUtils.DialogCallBack;
import com.weibo.utils.FaceConversionUtil;
import com.weibo.view.widget.LoadingAlertAnim;
import com.weibo.R;
/**
 * 
 * 发表一条评论
 * 
 */
public class CommentWeiBo extends BaseActivity {
	private boolean showSettings = false;
	TextView close_btn, send_btn, replay_weibo;
	EditText commentText;
	ImageButton find_me_btn, pick_photo_btn, topic_btn, face_btn;
	LoadingAlertAnim loading;
	TextView share_word_counter;
	CheckBox replay;
	private static final String TAG = "ReportComment.java";
	String result = "";

	private void textCountSet() {
		String textContent = commentText.getText().toString();
		int currentLength = textContent.length();
		if (currentLength > 0) {
			FaceConversionUtil.MygetColor(CommentWeiBo.this, send_btn, R.color.yellow);
			FaceConversionUtil.MygetColor(CommentWeiBo.this, close_btn, R.color.yellow);
		} else {
			FaceConversionUtil.MygetColor(CommentWeiBo.this, send_btn, R.color.white);
			FaceConversionUtil.MygetColor(CommentWeiBo.this, close_btn, R.color.white);
		}

		if (currentLength <= 140) {
			commentText.setTextColor(Color.BLACK);
		} else {
			commentText.setTextColor(Color.RED);
		}
	}

	boolean ischeck = false;

	public void findview() {
		share_word_counter = (TextView) findViewById(R.id.share_word_counter);
		share_word_counter.setVisibility(View.GONE);
		find_me_btn = (ImageButton) findViewById(R.id.findmebtn);
		find_me_btn.setOnClickListener(listener);
		pick_photo_btn = (ImageButton) findViewById(R.id.share_imagechoose);
		topic_btn = (ImageButton) findViewById(R.id.huatibtn);
		face_btn = (ImageButton) findViewById(R.id.biaoqingbtn);
		commentText = (EditText) findViewById(R.id.share_content);
		commentText.setOnClickListener(listener);
		close_btn = (TextView) findViewById(R.id.textView1);
		close_btn.setOnClickListener(listener);
		send_btn = (TextView) findViewById(R.id.textView2);
		send_btn.setOnClickListener(listener);
		topic_btn.setVisibility(View.GONE);
		pick_photo_btn.setVisibility(View.GONE);
		replay_weibo = (TextView) findViewById(R.id.textView3);
		replay_weibo.setText("同时分享微博");
		replay = (CheckBox) findViewById(R.id.checkBox1);
		replay.setOnCheckedChangeListener(new OnCheckedChangeListener() {
			@Override
			public void onCheckedChanged(CompoundButton arg0, boolean arg1) {
				// TODO Auto-generated method stub
				if (arg1) {
					ischeck = true;
				} else {
					ischeck = false;
				}
			}
		});
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
		commentText.addTextChangedListener(watcher);
	}

	OnClickListener listener = new OnClickListener() {
		@Override
		public void onClick(View view) {
			switch (view.getId()) {
			case R.id.findmebtn:
				Intent inten = new Intent(CommentWeiBo.this, Find.class);
				CommentWeiBo.this.startActivity(inten);
				break;
			case R.id.textView1:
				CommentWeiBo.this.finish();
				break;
			case R.id.textView2:
				if (commentText.getText().length() == 0) {
					FaceConversionUtil.shake(CommentWeiBo.this, commentText);
					commentText.setError("请输入评论！");
				} else if (commentText.getText().length() > 140) {
					FaceConversionUtil.shake(CommentWeiBo.this, commentText);
					commentText.setError("您输入的内容超出了字符限制,最多只能输入140个字！");
				} else {
					loading = new LoadingAlertAnim(CommentWeiBo.this, R.style.MyDialogStyle, "发送中...");
					// loading.s = "发送中。。。";
					loading.setCanceledOnTouchOutside(false);
					loading.show();
					new AsyncTask<Void, Void, Void>() {
						protected Void doInBackground(Void... param) {
							List<BasicNameValuePair> params = new ArrayList<BasicNameValuePair>();
							params.add(new BasicNameValuePair("oauth_token", ((Mykey) getApplication()).getOauth_token()));
							params.add(new BasicNameValuePair("oauth_token_secret", ((Mykey) getApplication()).getOauth_token_secret()));
							params.add(new BasicNameValuePair("content", commentText.getText().toString()));
							params.add(new BasicNameValuePair("row_id", getIntent().getStringExtra("feed_id")));
							params.add(new BasicNameValuePair("user_id", ((Mykey) getApplication()).getUid()));
							params.add(new BasicNameValuePair("ifShareFeed", ischeck ? "0" : "1"));
							result = ClientUtils.post_str(ClientUtils.BASE_URL + CommentWeiBo.this.getString(ClientUtils.WeiboStatuses_comment), params, CommentWeiBo.this);
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
								FaceConversionUtil.dispToast(CommentWeiBo.this, "发送成功！");
								((Mykey) getApplication()).setRefresh("1");
								CommentWeiBo.this.finish();
							} else {
								if (loading != null) {
									loading.dismiss();
								}
								FaceConversionUtil.dispToast(CommentWeiBo.this, "发送失败！");
							}
							Log.d(TAG, "评论获得的服务器返回数据:" + result);
						}
					}.execute(null, null, null);
				}
				break;
			}
		}
	};

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
				DialogUtils.dialogBuilder(CommentWeiBo.this, "删除内容？", "确定要删除内容？", new DialogCallBack() {
					@Override
					public void callBack() {
						commentText.setText("");
					}
				});
				// 处理结束
				showSettings = false;
			}
		}

		public void onAccuracyChanged(Sensor sensor, int accuracy) {
		}
	};

	protected void onResume() {
		super.onResume();
		sensorManager.registerListener(shakeListener, sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER), SensorManager.SENSOR_DELAY_FASTEST);
		showSettings = false;
		// /返回true表示注册成功，flase则反之
		if (((Mykey) getApplication()).getFind_me() != null && ((Mykey) getApplication()).getFind_me().length() > 0) {
			commentText.append("@" + ((Mykey) getApplication()).getFind_me() + " ");
			((Mykey) getApplication()).setFind_me(null);
		} else {
			Log.d(TAG, "没有检测到@我的好友!");
		}
		Log.d(TAG, "Back");
	}

	private SensorManager sensorManager;

	public void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
		setContentView(R.layout.comment_layout);
		findview();
		sensorManager = (SensorManager) this.getBaseContext().getSystemService(Context.SENSOR_SERVICE);
	}
}
