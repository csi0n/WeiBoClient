package com.weibo.activity;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONObject;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.AdapterView.OnItemClickListener;

import com.weibo.adapters.ChannelAdapter;
import com.weibo.application.*;
import com.weibo.lib.zrclist.ZrcListView;
import com.weibo.lib.zrclist.ZrcListView.OnStartListener;
import com.weibo.utils.ClientUtils;
import com.weibo.utils.FaceConversionUtil;
import com.weibo.utils.Utils;
import com.weibo.R;
/**
 * 
 * 此处处理频道列表
 * 
 */
public class ChannelList extends BaseActivity {
	ZrcListView xlistview;
	ImageView imageview1;
	HashMap<String, Object> map;
	ArrayList<HashMap<String, Object>> listitem;
	protected static final int change = 1;
	private static final String TAG = "ChannelList.java";
	ChannelAdapter adapter;
	int count = 0;
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
	public void findview() {
		xlistview = (ZrcListView) findViewById(R.id.xListView1);
		imageview1 = (ImageView) findViewById(R.id.example_right);
	}
	public void getdata() {
		new Thread(new Runnable() {
			@Override
			public void run() {
				// TODO Auto-generated method stub
				get(1);
			}
		}).start();
	}
	public void get(final int page) {
		try {
			List<BasicNameValuePair> params = new ArrayList<BasicNameValuePair>();
			params.add(new BasicNameValuePair("oauth_token",
					((Mykey) getApplication()).getOauth_token()));
			params.add(new BasicNameValuePair("oauth_token_secret",
					((Mykey) getApplication()).getOauth_token_secret()));
			params.add(new BasicNameValuePair("page", Integer.toString(page)));
			String result = ClientUtils .post_str( ClientUtils.BASE_URL + ChannelList.this .getString(ClientUtils.Channel_get_all_channel), params, ChannelList.this);
			JSONArray jArray = new JSONArray(result);
			for (int i = 0; i < jArray.length(); i++) {
				JSONObject temp = (JSONObject) jArray.get(i);
				map = new HashMap<String, Object>();
				map.put("title", temp.getString("title"));
				map.put("channel_category_id",
						temp.getString("channel_category_id"));
				listitem.add(map);
			}
			Message message = new Message();
			message.what = change;
			handler.sendMessage(message);
		} catch (Exception e) {
			Log.d(TAG, e.toString());
		}
	}
	public void inint() {
		listitem = new ArrayList<HashMap<String, Object>>();
		adapter = new ChannelAdapter(this, listitem);
		xlistview.setAdapter(adapter);
	}
	public void listen() {
		imageview1.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				ChannelList.this.finish();
			}
		});
		xlistview.setOnItemClickListener(new ZrcListView.OnItemClickListener() {
			@Override
			public void onItemClick(ZrcListView parent, View view,
					int position, long id) {
				// TODO Auto-generated method stub
				Intent intent = new Intent(ChannelList.this, ChannelWeiBo.class);
				intent.putExtra("title", listitem.get(position).get("title")
						.toString());
				intent.putExtra("channel_category_id", listitem.get(position)
						.get("channel_category_id").toString());
				ChannelList.this.startActivity(intent);
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
	}
	public void onCreate(Bundle savedInstanceState) {
		Utils.onActivityCreateSetTheme(this);
		super.onCreate(savedInstanceState);
		setContentView(R.layout.channel_list);
		findview();
		FaceConversionUtil.StartListView(ChannelList.this, xlistview);
		inint();
		listen();
		xlistview.refresh();
	}
}
