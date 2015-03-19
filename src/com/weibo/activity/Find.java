/**weibotest
 * 
 *
 *TODO
 */
package com.weibo.activity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONObject;

import com.google.gson.Gson;
import com.weibo.adapters.FindMeAdapter;
import com.weibo.application.Mykey;
import com.weibo.card.UserInfor;
import com.weibo.lib.zrclist.ZrcListView;
import com.weibo.lib.zrclist.ZrcListView.OnStartListener;
import com.weibo.utils.ClientUtils;
import com.weibo.utils.FaceConversionUtil;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.weibo.R;
/**
 * @作者:陈华清
 * 
 * @版本:1.0
 * @生成时期:2014年7月20日 上午10:40:03
 * @com.api.example.app
 */
public class Find extends BaseActivity {
	ImageView close_btn;
	EditText findtext;
	ZrcListView list;
	private static final String TAG = "Find.java";
	List<UserInfor> user;
	UserInfor u;
	FindMeAdapter adapter;
	int number = 1;
	private static final int find = 1;
	private static final int more = 2;
	int count = 0;
	private Handler handler = new Handler() {
		@SuppressLint("ResourceAsColor")
		public void handleMessage(Message msg) {
			switch (msg.what) {
				case find :
					if (count >= 19) {
						list.startLoadMore();
					} else {
						list.stopLoadMore();
					}
					list.setRefreshSuccess();
					adapter.notifyDataSetChanged();
					break;
				case more :
					list.setLoadMoreSuccess();
					break;
			}
		}
	};

	public void findview() {
		close_btn = (ImageView) findViewById(R.id.example_right);
		list = (ZrcListView) findViewById(R.id.zrcListView1);
		findtext = (EditText) findViewById(R.id.editText1);
		TextWatcher watcher = new TextWatcher() {
			public void onTextChanged(CharSequence s, int start, int before, int count) {
				getdata();
			}

			public void beforeTextChanged(CharSequence s, int start, int count, int after) {
				getdata();
			}

			@Override
			public void afterTextChanged(Editable s) {
				getdata();
			}

		};
		findtext.addTextChangedListener(watcher);
	}
	public void getdata() {
		new Thread(new Runnable() {
			@Override
			public void run() {
				try {
					List<BasicNameValuePair> params = new ArrayList<BasicNameValuePair>();
					params.add(new BasicNameValuePair("oauth_token", ((Mykey) getApplication()).getOauth_token()));
					params.add(new BasicNameValuePair("oauth_token_secret", ((Mykey) getApplication()).getOauth_token_secret()));
					params.add(new BasicNameValuePair("user_id", ((Mykey) getApplication()).getUid()));
					params.add(new BasicNameValuePair("page", String.valueOf(1)));
					params.add(new BasicNameValuePair("key", findtext.getText().toString()));
					String result = ClientUtils.post_str(ClientUtils.BASE_URL + Find.this.getString(ClientUtils.WeiboStatuses_weibo_search_user), params, Find.this);
					JSONArray jArray = new JSONArray(result);
					user.clear();
					Gson gson = new Gson();
					for (int i = 0; i < jArray.length(); i++) {
						user.add(gson.fromJson(jArray.getString(i), UserInfor.class));
					}
					Log.d(TAG, Integer.toString(user.size()));
					Message mes = new Message();
					mes.what = find;
					handler.sendMessage(mes);
				} catch (Exception e) {
					Log.d(TAG, e.toString());
				}
			}
		}).start();
	}
	public void listener() {
		close_btn.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Find.this.finish();
			}
		});
		list.setOnItemClickListener(new ZrcListView.OnItemClickListener() {

			@Override
			public void onItemClick(ZrcListView parent, View view, int position, long id) {
				// TODO Auto-generated method stub
				Mykey app = (Mykey) getApplication();
				TextView t = (TextView) view.findViewById(R.id.textView1);
				app.setFind_me(t.getText().toString());
				Log.d(TAG, t.getText().toString());
				Find.this.finish();
			}
		});
		list.setOnRefreshStartListener(new OnStartListener() {

			@Override
			public void onStart() {
				// TODO Auto-generated method stub
				new Thread(new Runnable() {

					@Override
					public void run() {
						// TODO Auto-generated method stub
						getuserfollowing(1);
					}
				}).start();
			}
		});

		list.setOnLoadMoreStartListener(new OnStartListener() {

			@Override
			public void onStart() {
				// TODO Auto-generated method stub
				number++;
				new Thread(new Runnable() {

					@Override
					public void run() {
						// TODO Auto-generated method stub
						getuserfollowing(number);
						Message message = new Message();
						message.what = more;
						handler.sendMessage(message);
					}
				}).start();
			}
		});
	}
	public void getuserfollowing(int page) {
		try {
			List<BasicNameValuePair> params = new ArrayList<BasicNameValuePair>();
			params.add(new BasicNameValuePair("oauth_token", ((Mykey) getApplication()).getOauth_token()));
			params.add(new BasicNameValuePair("oauth_token_secret", ((Mykey) getApplication()).getOauth_token_secret()));
			params.add(new BasicNameValuePair("user_id", ((Mykey) getApplication()).getUid()));
			params.add(new BasicNameValuePair("page", String.valueOf(1)));
			String result = ClientUtils.post_str(ClientUtils.BASE_URL + Find.this.getString(ClientUtils.User_user_following), params, Find.this);
			JSONArray jArray = new JSONArray(result);
			user.clear();
			Gson gson = new Gson();
			for (int i = 0; i < jArray.length(); i++) {
				user.add(gson.fromJson(jArray.getString(i), UserInfor.class));
			}
			Message message = new Message();
			message.what = find;
			handler.sendMessage(message);
		} catch (Exception e) {
			Log.d(TAG, e.toString());
		}
	}
	public void inint() {
		user = new ArrayList<UserInfor>();
		adapter = new FindMeAdapter(Find.this, user);
		list.setAdapter(adapter);
	}
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.at_me_layout);
		Log.d(TAG, "Login in Find.java");
		findview();
		FaceConversionUtil.StartListView(Find.this, list);
		inint();
		listener();
		list.refresh();
	}
}
