package com.weibo.activity;
import com.weibo.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONObject;

import com.loopj.android.image.SmartImageView;
import com.weibo.adapters.WeiBaDetailAdapter;
import com.weibo.application.*;
import com.weibo.lib.zrclist.ZrcListView;
import com.weibo.lib.zrclist.ZrcListView.OnStartListener;
import com.weibo.utils.ClientUtils;
import com.weibo.utils.FaceConversionUtil;
import com.weibo.view.widget.ChatFaceRelativeLayout;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.Html;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.WindowManager;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
/**
 * 
 * 获取详细的微博并进行相应的处理。
 * 
 */
public class WeiBaDetail extends BaseActivity implements OnClickListener {
	ImageView close;
	ZrcListView xlistview;
	WeiBaDetailAdapter adapter;
	HashMap<String, Object> map, map1;
	ArrayList<HashMap<String, Object>> listitem;
	protected static final int change = 0;
	protected static final int comment_success = 1;
	protected static final int comment_error = 2;
	int weibocount = 0;
	Bundle b = null;
	private static final String TAG = "GetWeiBaDetail.java";
	TextView title;
	private Button mBtnSend;
	private EditText mEditTextContent;
	private Handler handler = new Handler() {
		public void handleMessage(Message msg) {
			switch (msg.what) {
			case change:
				if (weibocount >= 19) {
					xlistview.startLoadMore();
				} else {
					xlistview.stopLoadMore();
				}
				xlistview.setRefreshSuccess();
				adapter.notifyDataSetChanged();
				break;
			case comment_success:
				FaceConversionUtil.dispToast(WeiBaDetail.this, "评论成功!");
				xlistview.setSelection(0);
				xlistview.refresh();
				break;
			case comment_error:
				FaceConversionUtil.dispToast(WeiBaDetail.this, "评论失败!");
				break;
			}
		}
	};
	public void findview() {
		close = (ImageView) findViewById(R.id.example_right);
		xlistview = (ZrcListView) findViewById(R.id.zrcListView1);
		title = (TextView) findViewById(R.id.titleText);
		mBtnSend = (Button) findViewById(R.id.btn_send);
		mBtnSend.setOnClickListener(this);
		mEditTextContent = (EditText) findViewById(R.id.et_sendmessage);
	}
	private void initView() {
		map = new HashMap<String, Object>();
		listitem = new ArrayList<HashMap<String, Object>>();
		adapter = new WeiBaDetailAdapter(this, listitem, map);
		xlistview.setAdapter(adapter);
	}
	public void getdata() {
		new Thread(new Runnable() {
			@Override
			public void run() {
				try {
					// TODO Auto-generated method stub
					List<BasicNameValuePair> params = new ArrayList<BasicNameValuePair>();
					params.add(new BasicNameValuePair("oauth_token", ((Mykey)getApplication()).getOauth_token()));
					params.add(new BasicNameValuePair("oauth_token_secret",
							((Mykey)getApplication()).getOauth_token_secret()));
					params.add(new BasicNameValuePair("id", b.getString("id")));
					params.add(new BasicNameValuePair("user_id",((Mykey)getApplication()).getUid()));
					String result=ClientUtils.post_str(ClientUtils.BASE_URL+WeiBaDetail.this.getString(ClientUtils.Weiba_post_detail), params, WeiBaDetail.this);
					JSONObject o = new JSONObject(result);
					map.put("title", o.getString("title"));
					map.put("content", o.getString("content"));
					map.put("reply_count", o.getString("reply_count"));
					map.put("read_count", o.getString("read_count"));
					map.put("post_time", o.getString("post_time"));
					map.put("author_info", b.getString("author_info"));
					getpinlun(1);
				} catch (Exception e) {
					Log.d(TAG, e.toString());
				}
			}
		}).start();
	}
	public void getpinlun(int page) {
		final int page1 = page;
		try {
			// TODO Auto-generated method stub
			List<BasicNameValuePair> params = new ArrayList<BasicNameValuePair>();
			params.add(new BasicNameValuePair("id", b.getString("id")));
			params.add(new BasicNameValuePair("oauth_token", ((Mykey)getApplication()).getOauth_token()));
			params.add(new BasicNameValuePair("oauth_token_secret", ((Mykey)getApplication()).getOauth_token_secret()));
			params.add(new BasicNameValuePair("page", Integer.toString(page)));
			String result=ClientUtils.post_str(ClientUtils.BASE_URL+WeiBaDetail.this.getString(ClientUtils.Weiba_comment_list), params, WeiBaDetail.this);
			JSONArray jArray = new JSONArray(result);
			for (int i = 0; i < jArray.length(); i++) {
				JSONObject temp = (JSONObject) jArray.get(i);
				map1 = new HashMap<String, Object>();
				map1.put("reply_id", temp.getString("reply_id"));
				map1.put("content", temp.getString("content"));
				map1.put("ctime", FaceConversionUtil.TimeStamp2Date(temp
						.getString("ctime")));
				map1.put("author_info", temp.getString("author_info"));
				JSONObject oo = new JSONObject(temp.getString("author_info"));
				map1.put("uname", oo.getString("uname"));
				map1.put("avatar_middle", oo.getString("avatar_middle"));
				listitem.add(map1);
			}
			weibocount = jArray.length();
			Message message = new Message();
			message.what = change;
			handler.sendMessage(message);
		} catch (Exception e) {
			Log.d(TAG, e.toString());
		}
	}
	public void listen() {
		close.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				WeiBaDetail.this.finish();
			}
		});
		xlistview.setOnRefreshStartListener(new OnStartListener() {
			@Override
			public void onStart() {
				// TODO Auto-generated method stub
				listitem.clear();
				getdata();
			}
		});
		xlistview.setOnLoadMoreStartListener(new OnStartListener() {
			@Override
			public void onStart() {
				// TODO Auto-generated method stub
				getpinlun(1);
			}
		});
	}
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK
				&& ((ChatFaceRelativeLayout) findViewById(R.id.FaceRelativeLayout))
						.hideFaceView()) {
			return true;
		}
		return super.onKeyDown(keyCode, event);
	}
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.weiba_detail_layout);
		b = getIntent().getExtras();
		Log.d(TAG, b.getString("id"));
		getWindow().setSoftInputMode(
				WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
		findview();
		FaceConversionUtil.StartListView(this, xlistview);
		initView();
		listen();
		xlistview.refresh();
	}
	@Override
	protected void onResume() {
		super.onResume();
		if (((Mykey) getApplication()).getRefresh().equals("1")) {
			xlistview.setSelection(0);
			xlistview.refresh();
			((Mykey) getApplication()).setRefresh("0");
		}
	}
	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.btn_send:
			if (mEditTextContent.getText().toString().length() > 0) {
				new Thread(new Runnable() {
					@Override
					public void run() {
						// TODO Auto-generated method stub
						List<BasicNameValuePair> params = new ArrayList<BasicNameValuePair>();
						params.add(new BasicNameValuePair("oauth_token", ((Mykey)getApplication()).getOauth_token()));
						params.add(new BasicNameValuePair("oauth_token_secret",
								((Mykey)getApplication()).getOauth_token_secret()));
						params.add(new BasicNameValuePair("user_id", ((Mykey)getApplication()).getUid()));
						params.add(new BasicNameValuePair("content", mEditTextContent.getText().toString()));
						params.add(new BasicNameValuePair("id", b.getString("id")));
						String result=ClientUtils.post_str(ClientUtils.BASE_URL+WeiBaDetail.this.getString(ClientUtils.Weiba_comment_post), params,WeiBaDetail.this);
						if (result.equals("1")) {
							Message m = new Message();
							m.what = comment_success;
							handler.sendMessage(m);
						} else {
							Message m = new Message();
							m.what = comment_error;
							handler.sendMessage(m);
						}
					}
				}).start();
			} else {
				mEditTextContent.setError("请输入回复内容！");
			}
			break;
		}
	}
}
