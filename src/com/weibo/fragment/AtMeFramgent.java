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

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.gson.Gson;
import com.weibo.R;
import com.weibo.activity.WeiBoDetail;
import com.weibo.adapters.WeiBoMainAdapter;
import com.weibo.application.Mykey;
import com.weibo.card.WeiBoCard;
import com.weibo.lib.zrclist.ZrcListView;
import com.weibo.lib.zrclist.ZrcListView.OnStartListener;
import com.weibo.utils.ClientUtils;
import com.weibo.utils.FaceConversionUtil;

/**
 * @作者:陈华清
 * 
 * @版本:1.0
 * @生成时期:2014年8月16日 下午12:38:30
 * @com.tab.newactivity
 */
public class AtMeFramgent extends Fragment {
	ZrcListView xlistview;
	View view;
	List<WeiBoCard> weibo_list;
	MessageFragmentActivity mainActivity;
	WeiBoMainAdapter adapter;
	private static final String TAG = "AtMeFragment";
	int number = 1;
	int count = 0;
	HashMap<String, Object> map;
	public void findview() {
		xlistview = (ZrcListView) view.findViewById(R.id.xListView1);
	}
	private void initView() {
		weibo_list = new ArrayList<WeiBoCard>();
		adapter = new WeiBoMainAdapter(mainActivity, weibo_list);
		xlistview.setAdapter(adapter);
	}
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		view = inflater.inflate(R.layout.only_have_zrclistview, container, false);
		findview();
		mainActivity = (MessageFragmentActivity) getActivity();
		FaceConversionUtil.StartListView(mainActivity, xlistview);
		initView();
		listener();
		xlistview.refresh();
		return view;
	}
	public void listener() {
		xlistview.setOnItemClickListener(new ZrcListView.OnItemClickListener() {
			@Override
			public void onItemClick(ZrcListView parent, View view, int position, long id) {
				// TODO Auto-generated method stub
				((Mykey) mainActivity.getApplication()).setMymap(weibo_list.get(position));
				Intent intent = new Intent(mainActivity, WeiBoDetail.class);
				mainActivity.startActivity(intent);
			}
		});
		xlistview.setOnRefreshStartListener(new OnStartListener() {
			@Override
			public void onStart() {
				// TODO Auto-generated method stub
				weibo_list.clear();
				new AsyncTask<Void, Void, Void>() {
					protected Void doInBackground(Void... params) {
						getmessagefeed(1);
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
						getmessagefeed(number);
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
	public void getmessagefeed(int page) {
		try {
			List<BasicNameValuePair> params = new ArrayList<BasicNameValuePair>();
			params.add(new BasicNameValuePair("oauth_token", ((Mykey) mainActivity.getApplication()).getOauth_token()));
			params.add(new BasicNameValuePair("oauth_token_secret", ((Mykey) mainActivity.getApplication()).getOauth_token_secret()));
			params.add(new BasicNameValuePair("user_id", ((Mykey) mainActivity.getApplication()).getUid()));
			params.add(new BasicNameValuePair("page", Integer.toString(page)));
			String result = ClientUtils.post_str(ClientUtils.BASE_URL + mainActivity.getString(ClientUtils.WeiboStatuses_mentions_feed), params, mainActivity);
			JSONArray jArray = new JSONArray(result);
			Gson gson = new Gson();
			for (int i = 0; i < jArray.length(); i++) {
				weibo_list.add(gson.fromJson(jArray.getString(i), WeiBoCard.class));
			}
			count = jArray.length();
		} catch (Exception e) {
			Log.d(TAG, e.toString());
		}
	}
}
