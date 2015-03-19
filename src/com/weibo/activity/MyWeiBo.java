/**weibotest
 * 
 *
 *TODO
 */
package com.weibo.activity;
import com.weibo.R;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.weibo.adapters.WeiBoMainAdapter;
import com.weibo.application.Mykey;
import com.weibo.card.WeiBoCard;
import com.weibo.lib.zrclist.ZrcListView;
import com.weibo.lib.zrclist.ZrcListView.OnItemClickListener;
import com.weibo.lib.zrclist.ZrcListView.OnStartListener;
import com.weibo.utils.ClientUtils;
import com.weibo.utils.FaceConversionUtil;
import com.weibo.utils.Utils;
/**
 * @作者:陈华清
 * 
 * @版本:1.0
 * @生成时期:2014年8月16日 下午9:38:59
 * @com.api.example.app
 */
public class MyWeiBo extends BaseActivity implements OnClickListener {
	ZrcListView xlistview;
	ImageView imageview1;
	List<WeiBoCard> weibolist_card;
	WeiBoMainAdapter adapter;
	int count = 0;
	private static final String TAG = "MyWeiBo.java";
	int number = 1;
	TextView title;

	public void findview() {
		xlistview = (ZrcListView) findViewById(R.id.xListView1);
		imageview1 = (ImageView) findViewById(R.id.example_right);
		imageview1.setOnClickListener(this);
		title = (TextView) findViewById(R.id.titleText);
		title.setText("我的微博");
	}

	private void initView() {
		weibolist_card = new ArrayList<WeiBoCard>();
		adapter = new WeiBoMainAdapter(this, weibolist_card);
		xlistview.setAdapter(adapter);
	}

	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.collection_list);
		findview();
		FaceConversionUtil.StartListView(this, xlistview);
		initView();
		listener();
		xlistview.refresh();
		Utils.getInstance().addActivity(this);
	}

	public void listener() {
		xlistview.setOnRefreshStartListener(new OnStartListener() {
			@Override
			public void onStart() {
				// TODO Auto-generated method stub
				weibolist_card.clear();
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
				number++;
				new AsyncTask<Void, Void, Void>() {
					protected Void doInBackground(Void... params) {
						getdata(number);
						return null;
					}

					@Override
					protected void onPostExecute(Void result) {
						if (count >= 19) {
							xlistview.startLoadMore();
						} else {
							xlistview.stopLoadMore();
						}
						xlistview.setLoadMoreSuccess();
						adapter.notifyDataSetChanged();
						Log.d(TAG, "jieshu");
					}
				}.execute(null, null, null);
			}
		});
		xlistview.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(ZrcListView parent, View view, int position, long id) {
				// TODO Auto-generated method stub
				((Mykey) getApplication()).setMymap(weibolist_card.get(position));
				Intent intent = new Intent(MyWeiBo.this, WeiBoDetail.class);
				MyWeiBo.this.startActivity(intent);
			}
		});
	}

	public void getdata(final int page) {
		try {
			// TODO Auto-generated method stub
			List<BasicNameValuePair> params = new ArrayList<BasicNameValuePair>();
			params.add(new BasicNameValuePair("oauth_token", ((Mykey) getApplication()).getOauth_token()));
			params.add(new BasicNameValuePair("oauth_token_secret", ((Mykey) getApplication()).getOauth_token_secret()));
			params.add(new BasicNameValuePair("user_id", ((Mykey) getApplication()).getUid()));
			params.add(new BasicNameValuePair("page", Integer.toString(page)));
			String weibo = ClientUtils.post_str(ClientUtils.BASE_URL + MyWeiBo.this.getString(ClientUtils.WeiboStatuses_get_user_timeline), params, MyWeiBo.this);
			JSONArray jArray = new JSONArray(weibo);
			Gson gson = new Gson();
			for (int i = 0; i < jArray.length(); i++) {
				weibolist_card.add(gson.fromJson(jArray.getString(i), WeiBoCard.class));
			}
			count = jArray.length();
		} catch (Exception e) {
			Log.d(TAG, e.toString());
		}
	}
	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		if (v == imageview1) {
			MyWeiBo.this.finish();
		}
	}
}
