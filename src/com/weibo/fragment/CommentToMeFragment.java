/**weibotest
 * 
 *
 *TODO
 */
package com.weibo.fragment;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import com.google.gson.Gson;
import com.weibo.R;
import com.weibo.adapters.MytoOtherCommentAdapter;
import com.weibo.application.Mykey;
import com.weibo.card.CommentInfor;
import com.weibo.lib.zrclist.ZrcListView;
import com.weibo.lib.zrclist.ZrcListView.OnStartListener;
import com.weibo.utils.ClientUtils;
import com.weibo.utils.FaceConversionUtil;

/**
 * @作者:陈华清
 * 
 * @版本:1.0
 * @生成时期:2014年8月16日 下午1:12:37
 * @com.tab.newactivity
 */
public class CommentToMeFragment extends Fragment {
	MytoOtherCommentAdapter adapter;
	ArrayList<CommentInfor> commen_list;
	ZrcListView xlistview;
	MessageFragmentActivity mainActivity;
	int count = 0;
	int number = 1;
	private static final String TAG = "CommentToMeFragment";

	private void initView() {
		commen_list = new ArrayList<CommentInfor>();
		adapter = new MytoOtherCommentAdapter(mainActivity, commen_list);
		xlistview.setAdapter(adapter);
	}
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.follow_layout, container, false);
		xlistview = (ZrcListView) view.findViewById(R.id.xListView1);
		RelativeLayout top = (RelativeLayout) view.findViewById(R.id.top_relative);
		top.setVisibility(View.GONE);
		mainActivity = (MessageFragmentActivity) getActivity();
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
				commen_list.clear();
				new AsyncTask<Void, Void, Void>() {
					protected Void doInBackground(Void... params) {
						pinglunwode(1);
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
						pinglunwode(number);
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
	public void pinglunwode(int page) {
		try {
			List<BasicNameValuePair> params = new ArrayList<BasicNameValuePair>();
			params.add(new BasicNameValuePair("oauth_token", ((Mykey) mainActivity.getApplication()).getOauth_token()));
			params.add(new BasicNameValuePair("oauth_token_secret", ((Mykey) mainActivity.getApplication()).getOauth_token_secret()));
			params.add(new BasicNameValuePair("user_id", ((Mykey) mainActivity.getApplication()).getUid()));
			params.add(new BasicNameValuePair("page", Integer.toString(page)));
			String result = ClientUtils.post_str(ClientUtils.BASE_URL + mainActivity.getString(ClientUtils.WeiboStatuses_comments_to_me), params, mainActivity);
			Log.d(TAG, "result-------------->" + result);
			JSONArray jArray = new JSONArray(result);
			Gson gson=new Gson();
			for (int i = 0; i < jArray.length(); i++) {
				commen_list.add(gson.fromJson(jArray.getString(i), CommentInfor.class));
			}
			count = jArray.length();
		} catch (Exception e) {
			Log.d(TAG, e.toString());
		}
	}
}
