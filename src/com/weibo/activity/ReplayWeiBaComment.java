/**weibotest
 * 
 *
 *TODO
 */
package com.weibo.activity;
import com.weibo.application.*;
import com.weibo.utils.FaceConversionUtil;
import com.weibo.R;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
/**
 * @作者:陈华清
 * 
 * @版本:1.0
 * @生成时期:2014年8月3日 下午5:46:02
 * @com.api.example.app
 */
public class ReplayWeiBaComment extends BaseActivity implements OnClickListener {
	TextView title, close, send, count, commentweibatext;
	EditText content_Text;
	ImageButton pick_photo, atme, topic, face;
	CheckBox commentweibo;
	private static final String TAG = "ReplayComment.java";
	private static final int success = 0;
	private static final int error = 1;
	private Handler handler = new Handler() {
		@SuppressLint("NewApi")
		public void handleMessage(Message msg) {
			switch (msg.what) {
				case success :
					FaceConversionUtil.dispToast(ReplayWeiBaComment.this, "发送成功！");
					if (getIntent().hasExtra("reply_id")) {
						((Mykey) getApplication()).setRefresh("1");
					}
					ReplayWeiBaComment.this.finish();
					break;
				case error :
					FaceConversionUtil.dispToast(ReplayWeiBaComment.this, "发送失败！");
					break;
			}
		}
	};

	public void findview() {
		title = (TextView) findViewById(R.id.titleText);
		if (getIntent().hasExtra("uname")) {
			title.setText("回复" + getIntent().getStringExtra("uname"));
		}
		close = (TextView) findViewById(R.id.textView1);
		close.setOnClickListener(this);
		send = (TextView) findViewById(R.id.textView2);
		send.setOnClickListener(this);
		count = (TextView) findViewById(R.id.share_word_counter);
		count.setVisibility(View.GONE);
		content_Text = (EditText) findViewById(R.id.share_content);
		pick_photo = (ImageButton) findViewById(R.id.share_imagechoose);
		pick_photo.setVisibility(View.GONE);
		topic = (ImageButton) findViewById(R.id.huatibtn);
		topic.setVisibility(View.GONE);
		commentweibo = (CheckBox) findViewById(R.id.checkBox1);
		commentweibo.setVisibility(View.GONE);
		atme = (ImageButton) findViewById(R.id.findmebtn);
		atme.setOnClickListener(this);
		commentweibatext = (TextView) findViewById(R.id.textView3);
		commentweibatext.setVisibility(View.GONE);
	}

	public void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
		setContentView(R.layout.comment_layout);
		findview();
	}

	protected void onResume() {
		super.onResume();
		if (((Mykey) getApplication()).getFind_me() != null && ((Mykey) getApplication()).getFind_me().length() > 0) {
			content_Text.append("@" + ((Mykey) getApplication()).getFind_me() + " ");
			((Mykey) getApplication()).setFind_me(null);
		} else {
			Log.d(TAG, "没有检测到@我的好友!");
		}
		Log.d(TAG, "Back");
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
			case R.id.textView1 :
				finish();
				break;
			case R.id.textView2 :
				if (content_Text.getText().length() > 0) {
					if (getIntent().hasExtra("reply_id")) {
						new Thread(new Runnable() {
							@Override
							public void run() {
								/*
								 * String result = GetInfor.commentweiba(
								 * ReplayWeiBaComment.this
								 * .getString(REPLY_COMMENT), ((Mykey)
								 * getApplication()).getOauth_token(), ((Mykey)
								 * getApplication()) .getOauth_token_secret(),
								 * ((Mykey) getApplication()).getUid(),
								 * content_Text.getText().toString(),
								 * getIntent().getStringExtra("reply_id")); if
								 * (result.equals("1")) { Message me = new
								 * Message(); me.what = success;
								 * handler.sendMessage(me); } else { Message me
								 * = new Message(); me.what = error;
								 * handler.sendMessage(me); } Log.d(TAG,
								 * result);
								 */
							}
						}).start();
					} else if (getIntent().hasExtra("post_id")) {
						new Thread(new Runnable() {
							@Override
							public void run() {
								// TODO Auto-generated method stub
								/*
								 * int COMMENT_POST = R.string.comment_post;
								 * String result = GetInfor.commentweiba(
								 * ReplayWeiBaComment.this
								 * .getString(COMMENT_POST), ((Mykey)
								 * getApplication()).getOauth_token(), ((Mykey)
								 * getApplication()) .getOauth_token_secret(),
								 * ((Mykey) getApplication()).getUid(),
								 * content_Text.getText().toString(),
								 * getIntent().getStringExtra("post_id")); if
								 * (result.equals("1")) { Message me = new
								 * Message(); me.what = success;
								 * handler.sendMessage(me); } else { Message me
								 * = new Message(); me.what = error;
								 * handler.sendMessage(me); } Log.d(TAG,
								 * result);
								 */
							}
						}).start();
					}
				} else {
					content_Text.setError("请输入内容！");
				}
				break;
			case R.id.findmebtn :
				Intent inten = new Intent(ReplayWeiBaComment.this, Find.class);
				ReplayWeiBaComment.this.startActivity(inten);
				break;
		}
	}
}
