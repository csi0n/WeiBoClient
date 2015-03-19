/**weibotest
 * 
 *
 *TODO
 */
package com.weibo.activity;
import com.weibo.R;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.message.BasicNameValuePair;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import com.weibo.application.Mykey;
import com.weibo.utils.ClientUtils;
import com.weibo.utils.FaceConversionUtil;
import com.weibo.view.widget.LoadingAlertAnim;

/**
 * @作者:陈华清
 * 
 * @版本:1.0
 * @生成时期:2014年7月31日 上午11:47:39
 * @com.api.example.app
 */
public class ReplayWeiBoComment extends BaseActivity implements OnClickListener {
	TextView title, close, send, count;
	EditText content_Text;
	String result = "";
	ImageButton pick_photo, atme, topic, face;
	CheckBox commentweibo;
	private static final String TAG = "ReplayComment.java";
	TextView share_word_counter;
	LoadingAlertAnim loading;

	public void findview() {
		share_word_counter = (TextView) findViewById(R.id.share_word_counter);
		share_word_counter.setVisibility(View.GONE);
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
		content_Text.addTextChangedListener(watcher);
		pick_photo = (ImageButton) findViewById(R.id.share_imagechoose);
		pick_photo.setVisibility(View.GONE);
		topic = (ImageButton) findViewById(R.id.huatibtn);
		topic.setVisibility(View.GONE);
		commentweibo = (CheckBox) findViewById(R.id.checkBox1);
		atme = (ImageButton) findViewById(R.id.findmebtn);
		atme.setOnClickListener(this);
	}

	private void textCountSet() {
		String textContent = content_Text.getText().toString();
		int currentLength = textContent.length();
		if (currentLength > 0) {
			FaceConversionUtil.MygetColor(ReplayWeiBoComment.this, close, R.color.yellow);
			FaceConversionUtil.MygetColor(ReplayWeiBoComment.this, send, R.color.yellow);
		} else {
			FaceConversionUtil.MygetColor(ReplayWeiBoComment.this, send, R.color.white);
			FaceConversionUtil.MygetColor(ReplayWeiBoComment.this, close, R.color.white);
		}
	}

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

	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.comment_layout);
		findview();
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
					loading = new LoadingAlertAnim(ReplayWeiBoComment.this, R.style.MyDialogStyle, "发送中...");
					loading.setCanceledOnTouchOutside(false);
					loading.show();
					new AsyncTask<Void, Void, Void>() {
						protected Void doInBackground(Void... param) {
							List<BasicNameValuePair> params = new ArrayList<BasicNameValuePair>();
							params.add(new BasicNameValuePair("oauth_token", ((Mykey) getApplication()).getOauth_token()));
							params.add(new BasicNameValuePair("oauth_token_secret", ((Mykey) getApplication()).getOauth_token_secret()));
							params.add(new BasicNameValuePair("row_id", getIntent().getStringExtra("row_id")));
							params.add(new BasicNameValuePair("content", "回复: @" + getIntent().getStringExtra("uname") + " " + content_Text.getText().toString()));
							params.add(new BasicNameValuePair("user_id", ((Mykey) getApplication()).getUid()));
							result = ClientUtils.post_str(ClientUtils.BASE_URL + ReplayWeiBoComment.this.getString(ClientUtils.WeiboStatuses_comment), params, ReplayWeiBoComment.this);
							return null;
						}
						@Override
						protected void onPostExecute(Void reslt) {
							Log.d(TAG, result);
							if (!result.equals("0")) {
								if (loading != null) {
									loading.dismiss();
								}
								FaceConversionUtil.dispToast(ReplayWeiBoComment.this, "回复成功");
								((Mykey) getApplication()).setRefresh("1");
								ReplayWeiBoComment.this.finish();
							} else {
								if (loading != null) {
									loading.dismiss();
								}
								FaceConversionUtil.dispToast(ReplayWeiBoComment.this, "回复失败");
							}
						}
					}.execute(null, null, null);

				} else {
					content_Text.setError("请输入内容！");
				}
				break;
			case R.id.findmebtn :
				Intent inten = new Intent(ReplayWeiBoComment.this, Find.class);
				ReplayWeiBoComment.this.startActivity(inten);
				break;
		}
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

}
