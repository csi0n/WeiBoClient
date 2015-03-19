package com.weibo.activity;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;

import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONObject;










import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.WindowManager;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.weibo.adapters.ChatMsgAdapter;
import com.weibo.application.*;
import com.weibo.card.ChatMsgEntity;
import com.weibo.lib.zrclist.ZrcListView;
import com.weibo.lib.zrclist.ZrcListView.OnStartListener;
import com.weibo.utils.ClientUtils;
import com.weibo.utils.FaceConversionUtil;
import com.weibo.utils.Utils;
import com.weibo.view.widget.ChatFaceRelativeLayout;
import com.weibo.R;
/**
 * @作者:陈华清
 * 
 * @版本:1.0
 * @生成时期:2014年7月18日 下午10:24:00
 * @com.api.example.app
 */
public class ChatWithFriend extends BaseActivity implements OnClickListener {
	ZrcListView listview;
	TextView title;
	Bundle b = null;
	private Button mBtnSend;
	private EditText mEditTextContent;
	private ChatMsgAdapter mAdapter;
	HashMap<String, Object> map;
	ImageView colse;
	ArrayList<HashMap<String, Object>> listitem;
	private final static String TAG = "ChatWithFriend";
	protected static final int change = 1;
	protected static final int senderror = 0;
	private List<ChatMsgEntity> mDataArrays = new ArrayList<ChatMsgEntity>();

	public void findview() {
		listview = (ZrcListView) findViewById(R.id.listview);
		title = (TextView) findViewById(R.id.titleText);
		mBtnSend = (Button) findViewById(R.id.btn_send);
		mBtnSend.setOnClickListener(this);
		mEditTextContent = (EditText) findViewById(R.id.et_sendmessage);
		colse = (ImageView) findViewById(R.id.example_right);
	}

	private void do_send(String s) {
		List<BasicNameValuePair> params = new ArrayList<BasicNameValuePair>();
		params.add(new BasicNameValuePair("oauth_token", ((Mykey) getApplication()).getOauth_token()));
		params.add(new BasicNameValuePair("oauth_token_secret", ((Mykey) getApplication()).getOauth_token_secret()));
		params.add(new BasicNameValuePair("user_id", ((Mykey) getApplication()).getUid()));
		params.add(new BasicNameValuePair("to_uid", b.getString("to_uid")));
		params.add(new BasicNameValuePair("content", s));
		String result = ClientUtils.post_str(ClientUtils.BASE_URL + ChatWithFriend.this.getString(ClientUtils.Message_create), params, ChatWithFriend.this);
		if (result.equals("0")) {
			Log.d(TAG, "回复失败！");
		} else {
			Log.d(TAG, "回复成功！");
		}
	}

	private void send() {
		String contString = mEditTextContent.getText().toString();
		if (contString.length() > 0) {
			ChatMsgEntity entity = new ChatMsgEntity();
			entity.setDate(FaceConversionUtil.getDate());
			entity.setMsgType(false);
			entity.setText(contString);
			mDataArrays.add(entity);
			mAdapter.notifyDataSetChanged();
			new AsyncTask<Void, Void, Void>() {
				protected Void doInBackground(Void... params) {
					do_send(mEditTextContent.getText().toString());
					return null;
				}

				@Override
				protected void onPostExecute(Void result) {
					mEditTextContent.setText("");
					listview.setSelection(listview.getCount() - 1);
				}
			}.execute(null, null, null);
		} else {
			mEditTextContent.setError("请输入发送的内容！");
		}
	}

	private void initView() {
		listitem = new ArrayList<HashMap<String, Object>>();
		mAdapter = new ChatMsgAdapter(ChatWithFriend.this, mDataArrays);
		listview.setAdapter(mAdapter);
	}

	int count;

	public String get_list_id() {
		List<BasicNameValuePair> params = new ArrayList<BasicNameValuePair>();
		params.add(new BasicNameValuePair("to_uid", b.getString("to_uid")));
		params.add(new BasicNameValuePair("user_id", ((Mykey) getApplication()).getUid()));
		params.add(new BasicNameValuePair("oauth_token", ((Mykey) getApplication()).getOauth_token()));
		params.add(new BasicNameValuePair("oauth_token_secret", ((Mykey) getApplication()).getOauth_token_secret()));
		return list_id = ClientUtils.post_str(ClientUtils.BASE_URL + ChatWithFriend.this.getString(ClientUtils.Message_get_list_id), params, ChatWithFriend.this);
	}

	public void finddata(final int page) {
		new AsyncTask<Void, Void, Void>() {
			protected Void doInBackground(Void... param) {
				try {
					List<BasicNameValuePair> params = new ArrayList<BasicNameValuePair>();
					params.add(new BasicNameValuePair("id", get_list_id()));
					params.add(new BasicNameValuePair("user_id", ((Mykey) getApplication()).getUid()));
					params.add(new BasicNameValuePair("oauth_token", ((Mykey) getApplication()).getOauth_token()));
					params.add(new BasicNameValuePair("oauth_token_secret", ((Mykey) getApplication()).getOauth_token_secret()));
					params.add(new BasicNameValuePair("page", String.valueOf(page)));
					String result = ClientUtils.post_str(ClientUtils.BASE_URL + ChatWithFriend.this.getString(ClientUtils.Message_get_message_detail), params, ChatWithFriend.this);
					JSONArray jArray = new JSONArray(result);
					count = jArray.length();
					for (int i = 0; i < jArray.length(); i++) {
						JSONObject temp = (JSONObject) jArray.get(i);
						ChatMsgEntity entity = new ChatMsgEntity();
						if (temp.getString("from_uname").equals(((Mykey) getApplication()).getUname())) {
							entity.setName(temp.getString("from_uname"));
							entity.setDate(temp.getString("from_ctime"));
							entity.setText(temp.getString("content"));
							entity.setFaceurl(temp.getString("from_face"));
							entity.setFrom_uid(temp.getString("from_uid"));
							entity.setMsgType(false);
						} else {
							entity.setName(temp.getString("from_uname"));
							entity.setDate(temp.getString("from_ctime"));
							entity.setText(temp.getString("content"));
							entity.setFaceurl(temp.getString("from_face"));
							entity.setFrom_uid(temp.getString("from_uid"));
							entity.setMsgType(true);
						}
						mDataArrays.add(entity);
					}
				} catch (Exception e) {
					Log.d(TAG, e.toString());
				}
				return null;
			}

			@Override
			protected void onPostExecute(Void result) {
				if (count >= 19) {
					listview.startLoadMore();
				} else {
					listview.stopLoadMore();
				}
				mAdapter.notifyDataSetChanged();
				listview.setRefreshSuccess();
				listview.setSelection(listview.getCount() - 1);
			}
		}.execute(null, null, null);
	}

	public void listener() {
		colse.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				ChatWithFriend.this.finish();
			}
		});
		listview.setOnRefreshStartListener(new OnStartListener() {
			@Override
			public void onStart() {
				mDataArrays.clear();
				listitem.clear();
				finddata(1);
			}
		});
		// 加载更多事件回调
		listview.setOnLoadMoreStartListener(new OnStartListener() {
			@Override
			public void onStart() {
				finddata(number++);
			}
		});
	}

	int number = 1;
	String list_id = null;
	String to_uid = null;

	public void onCreate(Bundle savedInstanceState) {
		Utils.onActivityCreateSetTheme(this);
		super.onCreate(savedInstanceState);
		setContentView(R.layout.chatwithfriend);
		getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
		b = getIntent().getExtras();
		findview();
		title.setText(b.getString("from_uname"));
		mEditTextContent.setHint("和" + b.getString("from_uname") + "聊天");
		FaceConversionUtil.StartListView(this, listview);
		initView();
		listener();
		listview.refresh();
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.btn_send:
			send();
			break;
		}
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK && ((ChatFaceRelativeLayout) findViewById(R.id.FaceRelativeLayout)).hideFaceView()) {
			return true;
		}
		return super.onKeyDown(keyCode, event);
	}

}
