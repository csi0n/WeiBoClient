package com.weibo.activity;
import com.weibo.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONTokener;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.animation.Animation;
import android.widget.EditText;
import android.widget.ImageView;

import com.google.gson.Gson;
import com.loopj.android.image.SmartImageView;
import com.weibo.adapters.Tab3Adapter;
import com.weibo.application.*;
import com.weibo.card.MyMedals;
import com.weibo.card.User;
import com.weibo.card.WeiBoCard;
import com.weibo.lib.zrclist.ZrcListView;
import com.weibo.lib.zrclist.ZrcListView.OnStartListener;
import com.weibo.utils.ACache;
import com.weibo.utils.ClientUtils;
import com.weibo.utils.Common;
import com.weibo.utils.FaceConversionUtil;
import com.weibo.utils.Utils;
import com.weibo.view.widget.LoadingAlertAnim;
public class Tab3Activity extends BaseActivity implements OnClickListener {
	SmartImageView head;
	EditText edittext1, edittext2, edittext3, edittext4, edittext5, edittext6;
	ZrcListView xlistview;
	LoadingAlertAnim loading, loading2;
	int number = 1;
	ImageView close;
	User user_card;
	List<WeiBoCard> weibo_list_card;
	Animation mShowAction, mHiddenAction;
	ACache mcache;
	protected static final int showpic = 0;
	protected static final int show_name = 1;
	protected static final int show_bac = 2;
	protected static final int success = 3;
	protected static final int error = 4;
	protected static final int change = 5;
	protected static final int end = 6;
	protected static final int colse_loadmore = 7;
	protected static final int finduser = 8;
	private static final String TAG = "Tab3Activity.java";
	View allview = null;
	Tab3Adapter adapter;
	int count = 0;
	String file = null;
	List<MyMedals> mlist;
	MyMedals m;
	private Handler handler = new Handler() {
		public void handleMessage(Message msg) {
			switch (msg.what) {
			case success:
				if (((Mykey) getApplication()).getHead_ic() != null) {
					((Mykey) getApplication()).getHead_ic().setImageBitmap(FaceConversionUtil.getLoacalBitmap(file));
				}
				FaceConversionUtil.dispToast(Tab3Activity.this, "头像修改成功！");
				break;
			case error:
				FaceConversionUtil.dispToast(Tab3Activity.this, "头像修改失败！");
				break;
			case change:
				break;
			case end:
				xlistview.setLoadMoreSuccess();
				break;
			}
		}
	};

	public void getuser() {
		try {
			List<BasicNameValuePair> params = new ArrayList<BasicNameValuePair>();
			params.add(new BasicNameValuePair("oauth_token", ((Mykey) getApplication()).getOauth_token()));
			params.add(new BasicNameValuePair("oauth_token_secret", ((Mykey) getApplication()).getOauth_token_secret()));
			params.add(new BasicNameValuePair("user_id", ((Mykey) getApplication()).getUid()));
			params.add(new BasicNameValuePair("fid", ((Mykey) getApplication()).getUid()));
			String result = ClientUtils.post_str(ClientUtils.BASE_URL + Tab3Activity.this.getString(ClientUtils.User_show), params, Tab3Activity.this);
			Gson gson = new Gson();
			user_card = gson.fromJson(result, User.class);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void getdata(final int page) {
		try {
			// TODO Auto-generated method stub
			List<BasicNameValuePair> params = new ArrayList<BasicNameValuePair>();
			params.add(new BasicNameValuePair("oauth_token", ((Mykey) getApplication()).getOauth_token()));
			params.add(new BasicNameValuePair("oauth_token_secret", ((Mykey) getApplication()).getOauth_token_secret()));
			params.add(new BasicNameValuePair("user_id", ((Mykey) getApplication()).getUid()));
			params.add(new BasicNameValuePair("page", Integer.toString(page)));
			String weibo = ClientUtils.post_str(ClientUtils.BASE_URL + Tab3Activity.this.getString(ClientUtils.WeiboStatuses_get_user_timeline), params, Tab3Activity.this);
			JSONArray jArray = new JSONArray(weibo);
			Gson gson = new Gson();
			for (int i = 0; i < jArray.length(); i++) {
				weibo_list_card.add(gson.fromJson(jArray.getString(i), WeiBoCard.class));
			}
			count = jArray.length();
		} catch (Exception e) {
			Log.d(TAG, e.toString());
		}
	}

	@SuppressLint("NewApi")
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.userpanl2);
		loading2 = new LoadingAlertAnim(Tab3Activity.this, R.style.MyDialogStyle, "正在获取数据,请稍后...");
		loading2.setCanceledOnTouchOutside(false);
		loading2.show();
		mcache = ACache.get(this);
		findview();
		FaceConversionUtil.StartListView(this, xlistview);
		initView();
		listen();
		xlistview.refresh();
		Utils.getInstance().addActivity(this);
	}
	public void findview() {
		close = (ImageView) findViewById(R.id.close);
		close.setOnClickListener(this);
		xlistview = (ZrcListView) findViewById(R.id.zListView);
	}
	private void initView() {
		weibo_list_card = new ArrayList<WeiBoCard>();
		adapter = new Tab3Adapter(this, weibo_list_card, Common.USER_INFOR);
		xlistview.setAdapter(adapter);
	}

	public void listen() {
		xlistview.setOnRefreshStartListener(new OnStartListener() {
			@Override
			public void onStart() {
				weibo_list_card.clear();
				new AsyncTask<Void, Void, Void>() {
					@Override
					protected Void doInBackground(Void... params) {
						// TODO Auto-generated method stub
						getdata(1);
						return null;
					}

					@Override
					protected void onPostExecute(Void result) {
						if (loading2 != null) {
							loading2.dismiss();
						}
						if (count >= 19) {
							xlistview.startLoadMore();
						} else {
							xlistview.stopLoadMore();
						}
						xlistview.setRefreshSuccess("加载成功"); // 通知加载成功
						adapter.notifyDataSetChanged();
						Log.d(TAG, "jieshu");
					}
				}.execute(null, null, null);
			}
		});
		// 加载更多事件回调
		xlistview.setOnLoadMoreStartListener(new OnStartListener() {
			@Override
			public void onStart() {
				number++;
				new AsyncTask<Void, Void, Void>() {
					@Override
					protected Void doInBackground(Void... params) {
						// TODO Auto-generated method stub
						getdata(number);
						return null;
					}

					@Override
					protected void onPostExecute(Void result) {
						if (loading2 != null) {
							loading2.dismiss();
						}
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
		xlistview.setOnItemClickListener(new ZrcListView.OnItemClickListener() {
			@Override
			public void onItemClick(ZrcListView parent, View view, int position, long id) {
				// TODO Auto-generated method stub
				if (position != 0) {
					((Mykey) getApplication()).setMymap(weibo_list_card.get(position-1));
					Intent intent = new Intent(Tab3Activity.this, WeiBoDetail.class);
					Tab3Activity.this.startActivity(intent);
				}
			}
		});
	}
	@Override
	protected void onResume() {
		if (((Mykey) getApplication()).getIsdel().equals("1")) {
			xlistview.setSelection(0);
			xlistview.refresh();
			((Mykey) getApplication()).setIsdel("0");
		}
		if (((Mykey) getApplication()).getWeibo_id() != null && ((Mykey) getApplication()).getWeibo_id().length() > 0) {
			xlistview.setSelection(0);
			xlistview.refresh();
		}
		super.onResume();
	}

	public void upload() {
		new Thread(new Runnable() {
			@Override
			public void run() {
				// TODO Auto-generated method stub
				try {
					String result = ClientUtils.post_with_pic(ClientUtils.BASE_URL + Tab3Activity.this.getString(ClientUtils.User_upload_face) + "&user_id=" + ((Mykey) getApplication()).getUid(), file, Tab3Activity.this);
					if (result.equals("1")) {
						Message message = new Message();
						message.what = success;
						handler.sendMessage(message);
					} else {
						Message message = new Message();
						message.what = error;
						handler.sendMessage(message);
					}
				} catch (Exception e) {
					Log.d(TAG, e.toString());
					Message message = new Message();
					message.what = error;
					handler.sendMessage(message);
				}
			}
		}).start();
	}

	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		switch (resultCode) {
		case 2:
			Bundle extras = data.getExtras();
			if (extras != null) {
				Bitmap i = extras.getParcelable("data");
				if (i != null) {
					try {
						FaceConversionUtil.saveBitmap("head_ic.png", i);
						file = "/sdcard/thinksns/head_ic.png";
						Log.d(TAG, "有图片！" + file);
						upload();
					} catch (Exception e) {
						Log.d(TAG, e.toString());
					}
				}
			}
			break;
		}
		super.onActivityResult(requestCode, resultCode, data);
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		if (v == close) {
			finish();
		}
	}
}
