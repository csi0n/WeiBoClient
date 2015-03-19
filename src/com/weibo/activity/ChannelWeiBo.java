package com.weibo.activity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONObject;

import com.weibo.adapters.WeiBoMainAdapter;
import com.weibo.application.*;
import com.weibo.card.CommentInfor;
import com.weibo.card.WeiBoCard;
import com.weibo.lib.zrclist.ZrcListView;
import com.weibo.lib.zrclist.ZrcListView.OnStartListener;
import com.weibo.utils.ClientUtils;
import com.weibo.utils.FaceConversionUtil;
import com.google.gson.Gson;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.*;

import com.weibo.R;
/**
 * 
 * 获取频道的微博（此处API是否存在问题，为何返回空数组？）
 * 
 */
/**
 * @作者:陈华清
 *
 * @版本:1.0
 * @生成时期:2014年7月24日 下午5:19:55
 * @com.api.example.app
 */
public class ChannelWeiBo extends BaseActivity {
	ZrcListView xlistview;
	WeiBoMainAdapter adapter;
	WeiBoCard weibo_card;
	ArrayList<WeiBoCard> weibolist_card;
	ImageView imageview1;
	TextView textview1;
	protected static final int change = 1;
	protected static final int end = 2;
	private static final String TAG = "ChannelWeiBo.java";
	int count = 0;
	int number = 1;
	private Handler handler = new Handler() {
		public void handleMessage(Message msg) {
			switch (msg.what) {
			case change:
				if (count >= 19) {
					xlistview.startLoadMore();
				} else {
					xlistview.stopLoadMore();
				}
				xlistview.setRefreshSuccess("本次刷新" + count + "条信息！");
				adapter.notifyDataSetChanged();
				break;
			case end:
				xlistview.setLoadMoreSuccess();
				break;
			}
		}
	};

	public void findview() {
		xlistview = (ZrcListView) findViewById(R.id.xListView1);
		imageview1 = (ImageView) findViewById(R.id.example_right);
		textview1 = (TextView) findViewById(R.id.titleText);
	}

	public void inint() {
		weibolist_card = new ArrayList<WeiBoCard>();
		adapter = new WeiBoMainAdapter(this, weibolist_card);
		xlistview.setAdapter(adapter);
	}

	public void get(int page) {
		try {
			List<BasicNameValuePair> params = new ArrayList<BasicNameValuePair>();
			params.add(new BasicNameValuePair("oauth_token", ((Mykey) getApplication()).getOauth_token()));
			params.add(new BasicNameValuePair("oauth_token_secret", ((Mykey) getApplication()).getOauth_token_secret()));
			params.add(new BasicNameValuePair("id", getIntent().getStringExtra("channel_category_id")));
			params.add(new BasicNameValuePair("page", Integer.toString(page)));
			params.add(new BasicNameValuePair("user_id", ((Mykey) getApplication()).getUid()));
			String result = ClientUtils.post_str(ClientUtils.BASE_URL + ChannelWeiBo.this.getString(ClientUtils.Channel_get_channel_feed), params, ChannelWeiBo.this);
			JSONArray jArray = new JSONArray(result);
			Gson gson = new Gson();
			for (int i = 0; i < jArray.length(); i++) {
				weibolist_card.add(gson.fromJson(jArray.getString(i), WeiBoCard.class));
			}
			count = jArray.length();
			Message message = new Message();
			message.what = change;
			handler.sendMessage(message);
		} catch (Exception e) {
			Log.d(TAG, e.toString());
		}
	}

	public void listener() {
		imageview1.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				ChannelWeiBo.this.finish();
			}
		});
		xlistview.setOnRefreshStartListener(new OnStartListener() {
			@Override
			public void onStart() {
				new Thread(new Runnable() {

					@Override
					public void run() {
						// TODO Auto-generated method stub
						get(1);
					}
				}).start();
			}
		});
		xlistview.setOnLoadMoreStartListener(new OnStartListener() {
			@Override
			public void onStart() {
				// TODO Auto-generated method stub
				new Thread(new Runnable() {

					@Override
					public void run() {
						// TODO Auto-generated method stub
						number++;
						get(number);
						Message me = new Message();
						me.what = end;
						handler.sendMessage(me);
					}
				}).start();
			}
		});
	}

	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.channel_weibo_list);
		findview();
		FaceConversionUtil.StartListView(ChannelWeiBo.this, xlistview);
		textview1.setText(getIntent().getStringExtra("title"));
		inint();
		listener();
		xlistview.refresh();
	}
}
