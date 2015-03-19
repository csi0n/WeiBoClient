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

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.weibo.activity.GetWeiBaList;
import com.weibo.activity.MyWeiBaSetting;
import com.weibo.R;
import com.weibo.adapters.WeiBaAdapter;
import com.weibo.application.Mykey;
import com.weibo.lib.zrclist.ZrcListView;
import com.weibo.lib.zrclist.ZrcListView.OnItemClickListener;
import com.weibo.lib.zrclist.ZrcListView.OnStartListener;
import com.weibo.utils.ClientUtils;
import com.weibo.utils.FaceConversionUtil;

/**
 * @作者:陈华清
 * 
 * @版本:1.0
 * @生成时期:2014年7月28日 上午7:56:10
 * @com.frame
 */
public class ShowSearchResult extends Fragment {
	ZrcListView xlistview;
	MyWeiBaSetting mainActivity;
	private static final String TAG = "ShowSearchResult.java";
	HashMap<String, Object> map;
	ArrayList<HashMap<String, Object>> listitem;
	WeiBaAdapter adapter;
	int count = 0;
	int number = 1;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.show_search_result_list_layout,
				container, false);
		xlistview = (ZrcListView) view.findViewById(R.id.zrcListView1);
		mainActivity = (MyWeiBaSetting) getActivity();
		Log.d(TAG, ((Mykey) mainActivity.getApplication()).getWeibasearch_key());
		FaceConversionUtil.StartListView(mainActivity, xlistview);
		initView();
		listener();
		xlistview.refresh();
		return view;
	}

	private void initView() {
		listitem = new ArrayList<HashMap<String, Object>>();
		adapter = new WeiBaAdapter((Context) mainActivity, listitem);
		xlistview.setAdapter(adapter);
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
				// TODO Auto-generated method stub
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
		xlistview.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(ZrcListView parent, View view,
					int position, long id) {
				// TODO Auto-generated method stub
				Intent intent = new Intent(mainActivity, GetWeiBaList.class);
				Bundle mbundle = new Bundle();
				mbundle.putString("logo_url",
						listitem.get(position).get("logo_url").toString());
				mbundle.putString("id", listitem.get(position).get("weiba_id")
						.toString());
				mbundle.putString("follower_count",
						listitem.get(position).get("follower_count").toString());
				mbundle.putString("thread_count",
						listitem.get(position).get("thread_count").toString());
				mbundle.putString("followstate",
						listitem.get(position).get("followstate").toString());
				mbundle.putString("weiba_name",
						listitem.get(position).get("weiba_name").toString());
				mbundle.putString("intro", listitem.get(position).get("intro")
						.toString());
				intent.putExtras(mbundle);
				mainActivity.startActivity(intent);
			}
		});
	}
	public void getdata(int page) {
		try {
			List<BasicNameValuePair> params = new ArrayList<BasicNameValuePair>();
			params.add(new BasicNameValuePair("oauth_token", ((Mykey) mainActivity.getApplication()).getOauth_token()));
			params.add(new BasicNameValuePair("oauth_token_secret", ((Mykey) mainActivity.getApplication()) .getOauth_token_secret()));
			params.add(new BasicNameValuePair("user_id", ((Mykey) mainActivity .getApplication()).getUid()));
			params.add(new BasicNameValuePair("page", String.valueOf(page)));
			params.add(new BasicNameValuePair("key", ((Mykey) mainActivity .getApplication()).getWeibasearch_key()));
			String result = ClientUtils.post_str(ClientUtils.BASE_URL
					+ mainActivity.getString(ClientUtils.Weiba_search_weiba),
					params, mainActivity);
			Log.d(TAG, result);
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
			count = jArray.length();
		} catch (Exception e) {
			Log.d(TAG, e.toString());
		}
	}
}
