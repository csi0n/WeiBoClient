/**weibotest
 * 
 *
 *TODO
 */
package com.weibo.activity;

import java.util.ArrayList;
import java.util.List;

import com.weibo.R;

import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import com.google.gson.Gson;
import com.weibo.adapters.WeiBoMainAdapter;
import com.weibo.application.Mykey;
import com.weibo.card.WeiBoCard;
import com.weibo.lib.Defs;
import com.weibo.lib.zrclist.ZrcListView;
import com.weibo.lib.zrclist.ZrcListView.OnStartListener;
import com.weibo.utils.ClientUtils;
import com.weibo.utils.FaceConversionUtil;
import com.weibo.view.widget.LoadingAlertAnim;

/**
 * @作者:陈华清
 *
 * @版本:1.0
 * @生成时期:2014年7月20日 下午11:13:52
 * @com.api.example.app
 */
public class Topic extends BaseActivity {
	private static final String TAG = "huati";
	private static final Uri PROFILE_URI = Uri.parse(Defs.MENTIONS_SCHEMA);
	private String key;
	ImageView imageview;
	ZrcListView xlistview;
	WeiBoMainAdapter adapter;
	int number = 1;
	int count = 0;
	LoadingAlertAnim loading;
	String topic_id = null;
	String topic_name = null;
	List<WeiBoCard> weibo_list_card;
	protected static final int change = 1;
	protected static final int end = 0;
	protected static final int colse = 2;
	private Handler handler = new Handler() {
		public void handleMessage(Message msg) {
			switch (msg.what) {
			case change:
				if (loading != null) {
					loading.dismiss();
				}
				if (count >= 19) {
					xlistview.startLoadMore();
				} else {
					xlistview.stopLoadMore();
				}
				xlistview.setRefreshSuccess("刷新成功本次共刷新" + count + "条信息!");
				adapter.notifyDataSetChanged();
				break;
			case end:
				xlistview.setLoadMoreSuccess();
				break;
			case colse:
				FaceConversionUtil.dispToast(Topic.this, "不存在该话题！");
				Topic.this.finish();
				break;
			}
		}
	};
	public void findview() {
		imageview = (ImageView) findViewById(R.id.example_right);
		xlistview = (ZrcListView) findViewById(R.id.xListView1);
		imageview.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Topic.this.finish();
			}
		});
		xlistview.setOnRefreshStartListener(new OnStartListener() {
			@Override
			public void onStart() {
				new Thread(new Runnable() {
					@Override
					public void run() {
						// TODO Auto-generated method stub
						if (weibo_list_card != null || weibo_list_card.size() > 0) {
							weibo_list_card.clear();
						}
						getdata(1,key);
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
						getdata(number,key);
						Message m = new Message();
						m.what = end;
						handler.sendMessage(m);
					}
				}).start();
			}
		});
		xlistview.setOnItemClickListener(new ZrcListView.OnItemClickListener() {
			@Override
			public void onItemClick(ZrcListView parent, View view, int position, long id) {
				// TODO Auto-generated method stub
				Intent i = new Intent(Topic.this, WeiBoDetail.class);
				((Mykey) getApplication()).setMymap(weibo_list_card.get(position));
				Topic.this.startActivity(i);
			}
		});
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.topic_layout);
		findview();
		FaceConversionUtil.StartListView(Topic.this, xlistview);
		loading = new LoadingAlertAnim(Topic.this, R.style.MyDialogStyle,"正在获取数据,请稍后...");
		loading.setCanceledOnTouchOutside(false);
		loading.show();
		initView();
		extractUidFromUri();
	}

	private void initView() {
		weibo_list_card = new ArrayList<WeiBoCard>();
		adapter = new WeiBoMainAdapter(this, weibo_list_card);
		xlistview.setAdapter(adapter);
	}

	// 话题搜索，API上说，用topic_id搜索，为毛我返回的是空数组，话题明明存在，（APi有问题）
	public void getdata(final int page, final String key) {
		try {
			List<BasicNameValuePair> params = new ArrayList<BasicNameValuePair>();
			params.add(new BasicNameValuePair("oauth_token", ((Mykey) getApplication()).getOauth_token()));
			params.add(new BasicNameValuePair("oauth_token_secret", ((Mykey) getApplication()).getOauth_token_secret()));
			params.add(new BasicNameValuePair("key", key));
			params.add(new BasicNameValuePair("page", Integer.toString(page)));
			String result = ClientUtils.post_str(ClientUtils.BASE_URL + Topic.this.getString(ClientUtils.WeiboStatuses_search_topic), params, Topic.this);
			JSONArray jArray = new JSONArray(result);
			Gson gson = new Gson();
			for (int i = 0; i < jArray.length(); i++) {
				weibo_list_card.add(gson.fromJson(jArray.getString(i), WeiBoCard.class));
			}
			count = jArray.length();
			if (count != 0) {
				Message message = new Message();
				message.what = colse;
				handler.sendMessage(message);
			}
			Message message = new Message();
			message.what = change;
			handler.sendMessage(message);
		} catch (Exception e) {
			Message message = new Message();
			message.what = colse;
			handler.sendMessage(message);
		}
	}

	private void extractUidFromUri() {
		Uri uri = getIntent().getData();
		if (uri != null && PROFILE_URI.getScheme().equals(uri.getScheme())) {
			key = uri.getQueryParameter(Defs.PARAM_UID);
			new Thread(new Runnable() {
				@Override
				public void run() {
					// TODO Auto-generated method stub
					try {
						getdata(1, key);
					} catch (Exception e) {
						Log.d(TAG, e.toString());
					}
				}
			}).start();
		}
	}
}
