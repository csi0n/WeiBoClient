package com.weibo.activity;
import java.util.ArrayList;
import com.weibo.R;

import java.util.HashMap;
import java.util.List;

import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONObject;

import com.weibo.adapters.WeiBaAdapter;
import com.weibo.application.*;
import com.weibo.lib.zrclist.ZrcListView;
import com.weibo.lib.zrclist.ZrcListView.OnStartListener;
import com.weibo.utils.ClientUtils;
import com.weibo.utils.FaceConversionUtil;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.*;
import android.widget.AdapterView.OnItemClickListener;
/**
 * 
 * 微吧列表
 * 
 */
public class WeiBaList extends BaseActivity {
	WeiBaAdapter adapter;
	ImageView imageview1;
	HashMap<String, Object> map;
	ArrayList<HashMap<String, Object>> listitem;
	ZrcListView xlistview;
	private static final String TAG = "WeiBaList.java";
	protected static final int change = 0;
	private Handler handler = new Handler() {
		public void handleMessage(Message msg) {
			switch (msg.what) {
			case change:
				xlistview.setRefreshSuccess();
				adapter.notifyDataSetChanged();
				break;
			}
		}
	};
	public void inint() {
		listitem = new ArrayList<HashMap<String, Object>>();
		adapter = new WeiBaAdapter(this, listitem);
		xlistview.setAdapter(adapter);
	}
	public void findview() {
		xlistview = (ZrcListView) findViewById(R.id.xListView1);
		imageview1 = (ImageView) findViewById(R.id.example_right);
	}

	public void get(int page) {
		try {
			List<BasicNameValuePair> params = new ArrayList<BasicNameValuePair>();
			params.add(new BasicNameValuePair("oauth_token", ((Mykey)getApplication()).getOauth_token()));
			params.add(new BasicNameValuePair("oauth_token_secret",
					((Mykey)getApplication()).getOauth_token_secret()));
			params.add(new BasicNameValuePair("user_id", ((Mykey)getApplication()).getUid()));
			params.add(new BasicNameValuePair("page", Integer.toString(page)));
			String result=ClientUtils.post_str(ClientUtils.BASE_URL+WeiBaList.this.getString(ClientUtils.Weiba_get_weibas), params,WeiBaList.this);
			JSONArray jArray = new JSONArray(result);
			for (int i = 0; i < jArray.length(); i++) {
				JSONObject temp = (JSONObject) jArray.get(i);
				map = new HashMap<String, Object>();
				map.put("weiba_id", temp.getString("weiba_id"));
				map.put("weiba_name", temp.getString("weiba_name"));
				map.put("intro", temp.getString("intro"));
				map.put("logo_url", temp.getString("logo_url"));
				map.put("followstate", temp.getString("followstate"));
				map.put("follower_count", temp.getString("follower_count"));
				map.put("thread_count", temp.getString("thread_count"));
				listitem.add(map);
			}
			Message message = new Message();
			message.what = change;
			handler.sendMessage(message);
		} catch (Exception e) {
			Log.d(TAG, e.toString());
		}
	}
	public void getdata(int page) {
		final int avg = page;
		new Thread(new Runnable() {
			@Override
			public void run() {
				// TODO Auto-generated method stub
				get(avg);
			}
		}).start();
	}
	public void listen() {
		xlistview.setOnRefreshStartListener(new OnStartListener() {
			
			@Override
			public void onStart() {
				// TODO Auto-generated method stub
				listitem.clear();
				getdata(1);
			}
		});
		xlistview.setOnItemClickListener(new ZrcListView.OnItemClickListener() {
			@Override
			public void onItemClick(ZrcListView parent, View view,
					int position, long id) {
				// TODO Auto-generated method stub
				Intent intent = new Intent(WeiBaList.this, GetWeiBaList.class);
				Bundle mbundle = new Bundle();
				mbundle.putString("logo_url",listitem.get(position).get("logo_url").toString());
				mbundle.putString("id", listitem.get(position).get("weiba_id")
						.toString());
				mbundle.putString("follower_count",
						listitem.get(position).get("follower_count").toString());
				mbundle.putString("thread_count",
						listitem.get(position).get("thread_count").toString());
				mbundle.putString("followstate",
						listitem.get(position).get("followstate").toString());
				mbundle.putString("weiba_name",listitem.get(position).get("weiba_name").toString());
				mbundle.putString("intro", listitem.get(position).get("intro").toString());
				intent.putExtras(mbundle);
				WeiBaList.this.startActivity(intent);
			}
		});
		imageview1.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				WeiBaList.this.finish();
			}
		});
	}
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.weibalist);
		findview();
		FaceConversionUtil.StartListView(this, xlistview);
		listen();
		inint();
		xlistview.refresh();
	}
}
