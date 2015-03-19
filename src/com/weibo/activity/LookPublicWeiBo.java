/**weibotest
 * 
 *
 *TODO
 */
package com.weibo.activity;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;

import com.google.gson.Gson;
import com.weibo.adapters.WeiBoMainAdapter;
import com.weibo.application.*;
import com.weibo.card.Oauth_authorize;
import com.weibo.card.WeiBoCard;
import com.weibo.lib.zrclist.ZrcListView;
import com.weibo.lib.zrclist.ZrcListView.OnItemClickListener;
import com.weibo.lib.zrclist.ZrcListView.OnStartListener;
import com.weibo.utils.ClientUtils;
import com.weibo.utils.FaceConversionUtil;
import com.weibo.utils.PublicTimeWeiBoAuthorize;
import com.weibo.utils.Utils;
import com.weibo.R;
/**
 * @作者:陈华清
 * 
 * @版本:1.0
 * @生成时期:2014年8月2日 下午7:00:25
 * @com.example.weibotest
 */
public class LookPublicWeiBo extends BaseActivity implements OnClickListener {
	private ZrcListView xlistview;
	private WeiBoMainAdapter adapter;
	private ImageView close;
	private static final String TAG = "LookPublicWeiBo.java";
	int count = 0;
	List<WeiBoCard> weibolist_card;
	protected static final int find = 0;
	protected static final int more = 1;
	int number = 1;
	private Handler handler = new Handler() {
		public void handleMessage(Message msg) {
			switch (msg.what) {
				case find :
					if (count >= 19) {
						xlistview.startLoadMore();
					} else {
						xlistview.stopLoadMore();
					}
					xlistview.setRefreshSuccess();
					adapter.notifyDataSetChanged();
					break;
				case more :
					xlistview.setLoadMoreSuccess();
					break;
			}
		}
	};
	ImageView logon;

	public void findview() {
		logon = (ImageView) findViewById(R.id.logon);
		logon.setOnClickListener(this);
		xlistview = (ZrcListView) findViewById(R.id.zrcListView1);
		close = (ImageView) findViewById(R.id.example_right);
		close.setOnClickListener(this);
		xlistview.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(ZrcListView parent, View view, int position, long id) {
				// TODO Auto-generated method stub
				((Mykey) getApplication()).setMymap(weibolist_card.get(position));
				Intent intent = new Intent(LookPublicWeiBo.this, WeiBoDetail.class);
				LookPublicWeiBo.this.startActivity(intent);
			}
		});
		xlistview.setOnRefreshStartListener(new OnStartListener() {
			@Override
			public void onStart() {
				// TODO Auto-generated method stub
				new Thread(new Runnable() {

					@Override
					public void run() {
						// TODO Auto-generated method stub
						getpublicweibo(1);
					}
				}).start();

			}
		});
		xlistview.setOnLoadMoreStartListener(new OnStartListener() {

			@Override
			public void onStart() {
				// TODO Auto-generated method stub
				number++;
				new Thread(new Runnable() {
					@Override
					public void run() {
						// TODO Auto-generated method stub
						getpublicweibo(number);
						Message me = new Message();
						me.what = more;
						handler.sendMessage(me);
					}
				}).start();
			}
		});
	}

	public void init() {
		weibolist_card = new ArrayList<WeiBoCard>();
		adapter = new WeiBoMainAdapter(this, weibolist_card);
		xlistview.setAdapter(adapter);
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.publictime_welcome_layout);
		PublicTimeWeiBoAuthorize.isLogin = false;
		findview();
		FaceConversionUtil.StartListView(LookPublicWeiBo.this, xlistview);
		init();
		login.start();
		Utils.getInstance().addActivity(this);
	}
	Thread login = new Thread(new Runnable() {
		@Override
		public void run() {
			// TODO Auto-generated method stub
			try {
				List<BasicNameValuePair> params = new ArrayList<BasicNameValuePair>();
				params.add(new BasicNameValuePair("username", "754455838@qq.com"));
				params.add(new BasicNameValuePair("password", "123123"));
				String result = ClientUtils.post_str(ClientUtils.BASE_URL + LookPublicWeiBo.this.getString(ClientUtils.UserLogin_login), params, LookPublicWeiBo.this);
				if (result.indexOf("oauth_token") > 0) {
					Gson gson = new Gson();
					PublicTimeWeiBoAuthorize.OAUTH_TOKEN = gson.fromJson(result, Oauth_authorize.class).getOauth_token();
					PublicTimeWeiBoAuthorize.OAUTH_TOKEN_SECRET = gson.fromJson(result, Oauth_authorize.class).getOauth_token_secret();
					PublicTimeWeiBoAuthorize.UID = gson.fromJson(result, Oauth_authorize.class).getUid();
					((Mykey) getApplication()).setOauth_token(gson.fromJson(result, Oauth_authorize.class).getOauth_token());
					((Mykey) getApplication()).setOauth_token_secret(gson.fromJson(result, Oauth_authorize.class).getOauth_token_secret());
					((Mykey) getApplication()).setUid(gson.fromJson(result, Oauth_authorize.class).getUid());
					xlistview.refresh();
				}
			} catch (Exception e) {
				Log.d(TAG, e.toString());
			}
		}
	});

	public void getpublicweibo(int page) {
		try {
			List<BasicNameValuePair> params = new ArrayList<BasicNameValuePair>();
			params.add(new BasicNameValuePair("oauth_token", PublicTimeWeiBoAuthorize.OAUTH_TOKEN));
			params.add(new BasicNameValuePair("oauth_token_secret", PublicTimeWeiBoAuthorize.OAUTH_TOKEN_SECRET));
			params.add(new BasicNameValuePair("user_id", PublicTimeWeiBoAuthorize.UID));
			params.add(new BasicNameValuePair("page", Integer.toString(page)));
			String reuslt = ClientUtils.post_str(ClientUtils.BASE_URL + LookPublicWeiBo.this.getString(ClientUtils.WeiboStatuses_get_public_timeline), params, LookPublicWeiBo.this);
			JSONArray jArray = new JSONArray(reuslt);
			Gson gson = new Gson();
			for (int i = 0; i < jArray.length(); i++) {
				weibolist_card.add(gson.fromJson(jArray.getString(i), WeiBoCard.class));
			}
			count = jArray.length();
			Message me = new Message();
			me.what = find;
			handler.sendMessage(me);
		} catch (Exception e) {
			Log.d(TAG, e.toString());
			xlistview.setRefreshFail();
		}
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
			case R.id.example_right :
				finish();
				break;
			case R.id.logon :
				Intent i = new Intent(LookPublicWeiBo.this, MainActivity.class);
				startActivity(i);
				break;
		}
	}
}
