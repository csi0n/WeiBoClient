package com.weibo.activity;

/**
 * 用户点击收藏时调用
 */
import java.util.ArrayList;
import com.weibo.R;

import java.util.List;

import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.ImageView;

import com.google.gson.Gson;
import com.weibo.adapters.WeiBoMainAdapter;
import com.weibo.application.*;
import com.weibo.card.WeiBoCard;
import com.weibo.lib.zrclist.ZrcListView;
import com.weibo.lib.zrclist.ZrcListView.OnStartListener;
import com.weibo.utils.ClientUtils;
import com.weibo.utils.FaceConversionUtil;
import com.weibo.utils.Utils;
public class Collection extends BaseActivity {
	ZrcListView xlistview;
	ImageView imageview1;
	int number = 1;
	WeiBoCard weibo_card;
	List<WeiBoCard> weibolist_card;
	WeiBoMainAdapter adapter;
	protected static final int change = 1;
	protected static final int end = 0;
	protected static final int colse_loadmore = 2;
	private Handler handler = new Handler() {
		public void handleMessage(Message msg) {
			switch (msg.what) {
				case change :
					if (count >= 19) {
						xlistview.startLoadMore();
					} else {
						xlistview.stopLoadMore();
					}
					xlistview.setRefreshSuccess();
					adapter.notifyDataSetChanged();
					break;
				case end :
					xlistview.setLoadMoreSuccess();
					break;
			}
		}
	};
	public void findview() {
		xlistview = (ZrcListView) findViewById(R.id.xListView1);
		imageview1 = (ImageView) findViewById(R.id.example_right);
	}
	private void initView() {
		weibolist_card = new ArrayList<WeiBoCard>();
		adapter = new WeiBoMainAdapter(this, weibolist_card);
		xlistview.setAdapter(adapter);
	}
	int count = 0;
	public void getinfor(int page) {
		try {
			List<BasicNameValuePair> params = new ArrayList<BasicNameValuePair>();
			params.add(new BasicNameValuePair("oauth_token", ((Mykey) getApplication()).getOauth_token()));
			params.add(new BasicNameValuePair("oauth_token_secret", ((Mykey) getApplication()).getOauth_token_secret()));
			params.add(new BasicNameValuePair("user_id", ((Mykey) getApplication()).getUid()));
			params.add(new BasicNameValuePair("page", Integer.toString(page)));
			String result = ClientUtils.post_str(ClientUtils.BASE_URL + Collection.this.getString(ClientUtils.WeiboStatuses_favorite_feed), params, Collection.this);
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
			e.printStackTrace();
		}
	}
	public void listen() {
		imageview1.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Collection.this.finish();
			}
		});
		xlistview.setOnItemClickListener(new ZrcListView.OnItemClickListener() {
			@Override
			public void onItemClick(ZrcListView parent, View view, int position, long id) {
				// TODO Auto-generated method stub
				((Mykey) getApplication()).setMymap(weibolist_card.get(position));
				Intent intent = new Intent(Collection.this, WeiBoDetail.class);
				Collection.this.startActivity(intent);
			}
		});
		xlistview.setOnRefreshStartListener(new OnStartListener() {
			@Override
			public void onStart() {
				onRefresh();
			}
		});

		// 加载更多事件回调
		xlistview.setOnLoadMoreStartListener(new OnStartListener() {
			@Override
			public void onStart() {
				onLoadMore();
			}
		});

	}

	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.collection_list);
		findview();
		FaceConversionUtil.StartListView(this, xlistview);
		initView();
		listen();
		new Thread(new Runnable() {
			@Override
			public void run() {
				// TODO Auto-generated method stub
				getinfor(1);
			}
		}).start();
		Utils.getInstance().addActivity(this);
	}

	public void onRefresh() {
		// TODO Auto-generated method stub
		new Thread(new Runnable() {
			@Override
			public void run() {
				// TODO Auto-generated method stub
				weibolist_card.clear();
				getinfor(1);
			}
		}).start();
	}

	public void onLoadMore() {
		// TODO Auto-generated method stub
		number++;
		new Thread(new Runnable() {

			@Override
			public void run() {
				// TODO Auto-generated method stub
				getinfor(number);
				Message message = new Message();
				message.what = end;
				handler.sendMessage(message);
			}
		}).start();
	}
}
