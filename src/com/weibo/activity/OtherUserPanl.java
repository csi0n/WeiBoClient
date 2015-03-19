package com.weibo.activity;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;

import android.annotation.SuppressLint;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.widget.ImageView;

import com.google.gson.Gson;
import com.weibo.adapters.OtherUserPanlAdapter;
import com.weibo.application.*;
import com.weibo.card.User;
import com.weibo.card.WeiBoCard;
import com.weibo.lib.Defs;
import com.weibo.lib.zrclist.ZrcListView;
import com.weibo.lib.zrclist.ZrcListView.OnStartListener;
import com.weibo.utils.ClientUtils;
import com.weibo.utils.FaceConversionUtil;
import com.weibo.utils.Utils;
import com.weibo.view.widget.LoadingAlertAnim;
import com.weibo.R;
public class OtherUserPanl extends BaseActivity {
	ImageView imageview1;
	List<WeiBoCard> weibolist_card;
	User user_card=new User();
	protected static final int find = 0;
	protected static final int quxiaoguanzhu1 = 1;
	protected static final int guanzhu = 2;
	protected static final int initdata=6;
	protected static final int change = 3;
	protected static final int end = 4;
	protected static final int notfinduser = 5;
	private String username;
	LoadingAlertAnim loading2;
	private static final Uri PROFILE_URI = Uri.parse(Defs.MENTIONS_SCHEMA);
	int number = 1;
	Animation mShowAction, mHiddenAction;
	private static final String TAG = "OtherUserPanl.java";
	int count = 0;
	private ZrcListView mListView;
	OtherUserPanlAdapter adapter;
	private Handler handler = new Handler() {
		@SuppressLint("ResourceAsColor")
		public void handleMessage(Message msg) {
			switch (msg.what) {
			case notfinduser:
				FaceConversionUtil.dispToast(OtherUserPanl.this, "没找到该用户思密达！");
				OtherUserPanl.this.finish();
				break;
			case change:
				if (loading2 != null) {
					loading2.dismiss();
				}
				mListView.setRefreshSuccess("加载成功");
				if (count >= 19) {
					mListView.startLoadMore();
				} else {
					mListView.stopLoadMore();
				}
				adapter.notifyDataSetChanged();
				break;
			case end:
				mListView.setLoadMoreSuccess();
				break;
			case initdata:
				initView();
				break;
			}
		}
	};

	public void findview() {
		imageview1 = (ImageView) findViewById(R.id.example_right);
		mListView = (ZrcListView) findViewById(R.id.zListView);
	}
	public void listener() {
		imageview1.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				OtherUserPanl.this.finish();
			}
		});
		mListView.setOnRefreshStartListener(new OnStartListener() {

			@Override
			public void onStart() {
				// TODO Auto-generated method stub
				onRefresh();
			}
		});
		mListView.setOnLoadMoreStartListener(new OnStartListener() {

			@Override
			public void onStart() {
				// TODO Auto-generated method stub
				onLoadMore();
			}
		});
	}

	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.otheruser);
		loading2 = new LoadingAlertAnim(OtherUserPanl.this, R.style.MyDialogStyle, "正在获取数据,请稍后...");
		loading2.setCanceledOnTouchOutside(false);
		loading2.show();
		findview();
		FaceConversionUtil.StartListView(this, mListView);
		extractUidFromUri();
		listener();
		Utils.getInstance().addActivity(this);
	}

	public void getweibo(final int page, String uid) {
		try {
			List<BasicNameValuePair> params = new ArrayList<BasicNameValuePair>();
			params.add(new BasicNameValuePair("oauth_token", ((Mykey) getApplication()).getOauth_token()));
			params.add(new BasicNameValuePair("oauth_token_secret", ((Mykey) getApplication()).getOauth_token_secret()));
			params.add(new BasicNameValuePair("user_id", ((Mykey) getApplication()).getOtheruid()));
			params.add(new BasicNameValuePair("page", Integer.toString(page)));
			String weibo = ClientUtils.post_str(ClientUtils.BASE_URL + OtherUserPanl.this.getString(ClientUtils.WeiboStatuses_get_user_timeline), params, OtherUserPanl.this);
			JSONArray jArray = new JSONArray(weibo);
			Log.d(TAG, "用户微博数据:"+weibo);
			Gson gson = new Gson();
			for (int i = 0; i < jArray.length(); i++) {
				weibolist_card.add(gson.fromJson(jArray.getString(i), WeiBoCard.class));
			}
			count = jArray.length();
			Message message = new Message();
			message.what = change;
			handler.sendMessage(message);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	private void initView() {
		weibolist_card = new ArrayList<WeiBoCard>();
		adapter = new OtherUserPanlAdapter(this, weibolist_card, user_card);
		mListView.setAdapter(adapter);
	}
	public void onRefresh() {
		// TODO Auto-generated method stub
		new Thread(new Runnable() {
			@Override
			public void run() {
				// TODO Auto-generated method stub
				weibolist_card.clear();
				getweibo(1, result);
			}
		}).start();
	}

	public void onLoadMore() {
		// TODO Auto-generated method stub
		new Thread(new Runnable() {
			@Override
			public void run() {
				// TODO Auto-generated method stub
				number++;
				getweibo(number, result);
				Message message = new Message();
				message.what = end;
				handler.sendMessage(message);
			}
		}).start();
	}
	String result = "";
	public void FindUser(int page) {
		try {
			List<BasicNameValuePair> params = new ArrayList<BasicNameValuePair>();
			params.add(new BasicNameValuePair("oauth_token", ((Mykey) getApplication()).getOauth_token()));
			params.add(new BasicNameValuePair("oauth_token_secret", ((Mykey) getApplication()).getOauth_token_secret()));
			params.add(new BasicNameValuePair("user_id", ((Mykey) getApplication()).getUid()));
			params.add(new BasicNameValuePair("key", username));
			result = ClientUtils.post_str(ClientUtils.BASE_URL + OtherUserPanl.this.getString(ClientUtils.WeiboStatuses_get_uid), params, OtherUserPanl.this);
			Log.d(TAG, "获取到的用户UID:" + result);
			Message a=new Message();
			a.what=initdata;
			handler.sendMessage(a);
			if (!result.equals("0")) {
				getweibo(1, result);
			}
		} catch (Exception e) {
			Log.d(TAG, e.toString());
		}
	}
	public void ShowUser() {
		try {
			List<BasicNameValuePair> params = new ArrayList<BasicNameValuePair>();
			params.add(new BasicNameValuePair("oauth_token", ((Mykey) getApplication()).getOauth_token()));
			params.add(new BasicNameValuePair("oauth_token_secret", ((Mykey) getApplication()).getOauth_token_secret()));
			params.add(new BasicNameValuePair("user_id", ((Mykey) getApplication()).getOtheruid()));
			String result = ClientUtils.post_str(ClientUtils.BASE_URL + OtherUserPanl.this.getString(ClientUtils.User_show), params, OtherUserPanl.this);
			Log.d(TAG, "获取到得用户数据UID="+ ((Mykey) getApplication()).getOtheruid()+":"+result);
			Gson gson = new Gson();
			user_card = gson.fromJson(result, User.class);
			Message a=new Message();
			a.what=initdata;
			handler.sendMessage(a);
			getweibo(1, ((Mykey) getApplication()).getOtheruid());
		} catch (Exception e) {
			Log.d(TAG, e.toString());
		}
	}
	private void extractUidFromUri() {
		Uri uri = getIntent().getData();
		if (uri != null && PROFILE_URI.getScheme().equals(uri.getScheme())) {
			username = uri.getQueryParameter(Defs.PARAM_UID);
			new Thread(new Runnable() {
				@Override
				public void run() {
					// TODO Auto-generated method stub
					FindUser(1);
					if (result != null && !result.equals("0")) {
						((Mykey) getApplication()).setOtheruid(result);
					} else {
						Log.d(TAG, "没有找到用户！");
						Message message = new Message();
						message.what = notfinduser;
						handler.sendMessage(message);
					}
					ShowUser();
					Log.d(TAG, "当前UID为：" + ((Mykey) getApplication()).getOtheruid());
				}
			}).start();
		} else if (((Mykey) getApplication()).getOtheruid() != null) {
			new Thread(new Runnable() {
				@Override
				public void run() {
					// TODO Auto-generated method stub
					ShowUser();
				}
			}).start();
		}
	}
}