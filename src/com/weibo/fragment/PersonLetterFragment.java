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

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.weibo.R;
import com.weibo.adapters.PersonLetterAdapter;
import com.weibo.application.Mykey;
import com.weibo.lib.zrclist.ZrcListView;
import com.weibo.lib.zrclist.ZrcListView.OnStartListener;
import com.weibo.utils.ClientUtils;
import com.weibo.utils.FaceConversionUtil;

/**
 * @作者:陈华清
 * 
 * @版本:1.0
 * @生成时期:2014年8月16日 下午1:23:06
 * @com.tab.newactivity
 */
public class PersonLetterFragment extends Fragment {
	ZrcListView xListView;
	MessageFragmentActivity mainActivity;
	PersonLetterAdapter adapter;
	ArrayList<HashMap<String, Object>> listitem;
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.only_have_zrclistview, container, false);
		xListView = (ZrcListView) view.findViewById(R.id.xListView1);
		mainActivity = (MessageFragmentActivity) getActivity();
		FaceConversionUtil.StartListView(mainActivity, xListView);
		initView();
		listener();
		xListView.refresh();
		return view;
	}

	private void initView() {
		listitem = new ArrayList<HashMap<String, Object>>();
		adapter = new PersonLetterAdapter(mainActivity, listitem);
		xListView.setAdapter(adapter);
	}

	public void listener() {
		xListView.setOnRefreshStartListener(new OnStartListener() {
			@Override
			public void onStart() {
				// TODO Auto-generated method stub
				listitem.clear();
				new AsyncTask<Void, Void, Void>() {
					protected Void doInBackground(Void... params) {
						getmessage_list(1);
						return null;
					}

					@Override
					protected void onPostExecute(Void result) {
						if (count >= 19) {
							xListView.startLoadMore();
						} else {
							xListView.stopLoadMore();
						}
						xListView.setRefreshSuccess();
						adapter.notifyDataSetChanged();
						Log.d(TAG, "jieshu");
					}
				}.execute(null, null, null);
			}
		});
		xListView.setOnLoadMoreStartListener(new OnStartListener() {
			@Override
			public void onStart() {
				Log.d(TAG, "点击了读取更多 ");
				number++;
				// TODO Auto-generated method stub
				new AsyncTask<Void, Void, Void>() {
					@Override
					protected Void doInBackground(Void... params) {
						// TODO Auto-generated method stub
						getmessage_list(number);
						return null;
					}
					@Override
					protected void onPostExecute(Void result) {
						xListView.setLoadMoreSuccess();
						adapter.notifyDataSetChanged();
						Log.d(TAG, "jieshu");
					}
				}.execute(null, null, null);
			}
		});
	}

	int count = 0;
	int number = 1;
	private static final String TAG = "PersonLetterFragment";
	HashMap<String, Object> map1;

	public void getmessage_list(int page) {
		try {
			List<BasicNameValuePair> params = new ArrayList<BasicNameValuePair>();
			params.add(new BasicNameValuePair("oauth_token", ((Mykey) mainActivity.getApplication()).getOauth_token()));
			params.add(new BasicNameValuePair("oauth_token_secret", ((Mykey) mainActivity.getApplication()).getOauth_token_secret()));
			params.add(new BasicNameValuePair("user_id", ((Mykey) mainActivity.getApplication()).getUid()));
			params.add(new BasicNameValuePair("page", Integer.toString(page)));
			String result = ClientUtils.post_str(ClientUtils.BASE_URL + mainActivity.getString(ClientUtils.Message_get_message_list), params, mainActivity);
			JSONArray jArray = new JSONArray(result);
			for (int i = 0; i < jArray.length(); i++) {
				JSONObject temp = (JSONObject) jArray.get(i);
				map1 = new HashMap<String, Object>();
				map1.put("list_id", temp.getString("list_id"));
				map1.put("to_user_info", temp.getString("to_user_info"));
				map1.put("from_uname", temp.getString("from_uname"));
				map1.put("from_face", temp.getString("from_face"));
				map1.put("ctime", temp.getString("ctime"));
				map1.put("content", temp.getString("content"));
				listitem.add(map1);
			}
			count = jArray.length();
		} catch (Exception e) {
			Log.d(TAG, e.toString());
		}
	}
}
