package com.weibo.activity;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.message.BasicNameValuePair;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.View.OnFocusChangeListener;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import com.weibo.application.*;
import com.weibo.utils.ClientUtils;
import com.weibo.utils.FaceConversionUtil;
import com.weibo.view.widget.LoadingAlertAnim;
import com.weibo.R;
/**
 * 
 * 发表一条微吧
 * 
 */
public class ReportNewWeiBa extends BaseActivity implements View.OnClickListener {
	EditText edittext1, edittext2;
	TextView textview1, textview2, textcount;
	ImageButton imagchoose, topic, face, atme;
	protected static final int success = 0;
	LoadingAlertAnim loading;
	protected static final int error = 1;
	protected static final int enable = 2;
	protected static final int disenable = 3;
	private static final String TAG = "ReportNewWeiba.java";
	private Handler handler = new Handler() {
		@SuppressLint("ResourceAsColor")
		public void handleMessage(Message msg) {
			switch (msg.what) {
				case success :
					((Mykey) getApplication()).setRefresh("1");
					if (loading != null) {
						loading.dismiss();
					}
					FaceConversionUtil.dispToast(ReportNewWeiBa.this, "发送成功!");
					ReportNewWeiBa.this.finish();
					break;
				case error :
					FaceConversionUtil.dispToast(ReportNewWeiBa.this, "发送失败!");
					if (loading != null) {
						loading.dismiss();
					}
					break;
			}
		}
	};

	public void findview() {
		edittext1 = (EditText) findViewById(R.id.editText1);
		edittext2 = (EditText) findViewById(R.id.editText2);
		textview1 = (TextView) findViewById(R.id.textView1);
		textview2 = (TextView) findViewById(R.id.textView2);
		textcount = (TextView) findViewById(R.id.share_word_counter);
		textcount.setVisibility(View.GONE);
		topic = (ImageButton) findViewById(R.id.huatibtn);
		topic.setVisibility(View.GONE);
		imagchoose = (ImageButton) findViewById(R.id.share_imagechoose);
		imagchoose.setVisibility(View.GONE);
		face = (ImageButton) findViewById(R.id.biaoqingbtn);
		atme = (ImageButton) findViewById(R.id.findmebtn);
		edittext1.setOnFocusChangeListener(new OnFocusChangeListener() {
			@Override
			public void onFocusChange(View v, boolean hasFocus) {
				// TODO Auto-generated method stub
				if (edittext1.hasFocus()) {
					atme.setEnabled(false);
					face.setEnabled(false);
				} else {
					face.setEnabled(true);
					atme.setEnabled(true);
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
		edittext1.addTextChangedListener(watcher);
		edittext2.addTextChangedListener(watcher);
	}

	private void textCountSet() {
		String textContent = edittext2.getText().toString();
		int currentLength = textContent.length();
		if (currentLength > 0) {
			FaceConversionUtil.MygetColor(ReportNewWeiBa.this, textview2, R.color.yellow);
			FaceConversionUtil.MygetColor(ReportNewWeiBa.this, textview1, R.color.yellow);
		} else {
			FaceConversionUtil.MygetColor(ReportNewWeiBa.this, textview2, R.color.white);
			FaceConversionUtil.MygetColor(ReportNewWeiBa.this, textview1, R.color.white);
		}
	}

	public void listen() {
		textview1.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				ReportNewWeiBa.this.finish();
			}
		});
		textview2.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				loading = new LoadingAlertAnim(ReportNewWeiBa.this, R.style.MyDialogStyle, "发送中...");
				// loading.s = "发送中。。。";
				loading.setCanceledOnTouchOutside(false);
				loading.show();
				// TODO Auto-generated method stub
				new Thread(new Runnable() {
					@Override
					public void run() {
						// TODO Auto-generated method stub
						List<BasicNameValuePair> params = new ArrayList<BasicNameValuePair>();
						params.add(new BasicNameValuePair("oauth_token", ((Mykey) getApplication()).getOauth_token()));
						params.add(new BasicNameValuePair("oauth_token_secret", ((Mykey) getApplication()).getOauth_token_secret()));
						params.add(new BasicNameValuePair("id", getIntent().getStringExtra("id")));
						params.add(new BasicNameValuePair("user_id", ((Mykey) getApplication()).getUid()));
						params.add(new BasicNameValuePair("title", edittext1.getText().toString()));
						params.add(new BasicNameValuePair("content", edittext2.getText().toString()));
						String result = ClientUtils.post_str(ClientUtils.BASE_URL + ReportNewWeiBa.this.getString(ClientUtils.Weiba_create_post), params, ReportNewWeiBa.this);
						if (!result.equals("0")) {
							Message message = new Message();
							message.what = success;
							handler.sendMessage(message);
						} else {
							Message message = new Message();
							message.what = error;
							handler.sendMessage(message);
						}
					}
				}).start();
			}
		});
	}

	protected void onResume() {
		super.onResume();
		// /返回true表示注册成功，flase则反之
		if (((Mykey) getApplication()).getFind_me() != null && ((Mykey) getApplication()).getFind_me().length() > 0) {
			textview2.append("@" + ((Mykey) getApplication()).getFind_me() + " ");
			((Mykey) getApplication()).setFind_me(null);
		} else {
			Log.d(TAG, "没有检测到@我的好友!");
		}
		Log.d(TAG, "Back");
	}

	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.send_new_weiba_layout);
		findview();
		listen();
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
			case R.id.findmebtn :
				Intent a = new Intent(ReportNewWeiBa.this, Find.class);
				ReportNewWeiBa.this.startActivity(a);
				break;
		}
	}
}
