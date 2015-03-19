/**weibotest
 * 
 *
 *TODO
 */
package com.weibo.fragment;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONObject;

import com.weibo.activity.MyWeiBaSetting;
import com.weibo.R;
import com.weibo.adapters.MyFollowWeiBa;
import com.weibo.application.Mykey;
import com.weibo.lib.zrclist.ZrcListView;
import com.weibo.lib.zrclist.ZrcListView.OnStartListener;
import com.weibo.utils.ClientUtils;
import com.weibo.utils.FaceConversionUtil;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

/**
 * @作者:陈华清
 * 
 * @版本:1.0
 * @生成时期:2014年7月28日 上午8:52:23
 * @com.frame
 */
public class ShowSearchNote extends Fragment {
	MyWeiBaSetting mainActivity;
	ZrcListView xlistview;
	private static final String TAG = "FollowWeiBa.java";
	HashMap<String, Object> map;
	ArrayList<HashMap<String, Object>> listitem;
	MyFollowWeiBa adapter;
	int count = 0;
	int number = 1;
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.show_search_note_layout,
				container, false);
		xlistview = (ZrcListView) view.findViewById(R.id.zrcListView1);
		mainActivity = (MyWeiBaSetting) getActivity();
		FaceConversionUtil.StartListView(mainActivity, xlistview);
		initView();
		listener();
		xlistview.refresh();
		return view;
	}
	public void listener() {
		xlistview.setOnRefreshStartListener(new OnStartListener() {
			@Override
			public void onStart() {
				// TODO Auto-generated method stub
				if (listitem != null || listitem.size() > 0) {
					listitem.clear();
				}
				new AsyncTask<Void, Void, Void>() {
					protected Void doInBackground(Void... params) {
						getdata(1);
						return null;
					}

					@Override
					protected void onPostExecute(Void result) {
						if (count >= 19) {
							xlistview.startLoadMore();
						} else {
							xlistview.stopLoadMore();
						}
						xlistview.setRefreshSuccess();
						adapter.notifyDataSetChanged();
						Log.d(TAG, "jieshu");
					}
				}.execute(null, null, null);
			}
		});
		xlistview.setOnLoadMoreStartListener(new OnStartListener() {
			@Override
			public void onStart() {
				Log.d(TAG, "点击了读取更多 ");
				number++;
				// TODO Auto-generated method stub
				new AsyncTask<Void, Void, Void>() {
					@Override
					protected Void doInBackground(Void... params) {
						// TODO Auto-generated method stub
						getdata(number);
						return null;
					}

					@Override
					protected void onPostExecute(Void result) {
						xlistview.setLoadMoreSuccess();
						adapter.notifyDataSetChanged();
						Log.d(TAG, "jieshu");
					}
				}.execute(null, null, null);
			}
		});
	}
	private void initView() {
		listitem = new ArrayList<HashMap<String, Object>>();
		adapter = new MyFollowWeiBa((Context) mainActivity, listitem);
		xlistview.setAdapter(adapter);
	}
	public void getdata(int page) {
		try {
			List<BasicNameValuePair> params = new ArrayList<BasicNameValuePair>();
			params.add(new BasicNameValuePair("oauth_token", ((Mykey)mainActivity.getApplication()).getOauth_token()));
			params.add(new BasicNameValuePair("oauth_token_secret",
					((Mykey)mainActivity.getApplication()).getOauth_token_secret()));
			params.add(new BasicNameValuePair("user_id", ((Mykey)mainActivity.getApplication()).getUid()));
			params.add(new BasicNameValuePair("page", String.valueOf(page)));
			params.add(new BasicNameValuePair("key", ((Mykey) mainActivity.getApplication())
					.getWeibasearch_key_note()));
			String result=ClientUtils.post_str(ClientUtils.BASE_URL+mainActivity.getString(ClientUtils.Weiba_search_post), params, mainActivity);
			JSONArray jArray = new JSONArray(result);
			for (int i = 0; i < jArray.length(); i++) {
				JSONObject temp = (JSONObject) jArray.get(i);
				map = new HashMap<String, Object>();
				map.put("post_id", temp.getString("post_id"));
				map.put("weiba_id", temp.getString("weiba_id"));
				map.put("title", temp.getString("title"));
				map.put("content", temp.getString("content"));
				map.put("author_info", temp.getString("author_info"));
				map.put("read_count", temp.getString("read_count"));
				map.put("reply_count", temp.getString("reply_count"));
				map.put("post_time", temp.getString("post_time"));
				listitem.add(map);
			}
			count = jArray.length();
		} catch (Exception e) {
			Log.d(TAG, e.toString());
		}
	}
}
