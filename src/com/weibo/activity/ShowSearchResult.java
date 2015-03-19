/**weibotest
 * 
 *
 *TODO
 */
package com.weibo.activity;
import com.weibo.R;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;

import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONObject;

import com.google.gson.Gson;
import com.loopj.android.image.SmartImageView;
import com.weibo.adapters.WeiBoMainAdapter;
import com.weibo.application.*;
import com.weibo.card.User;
import com.weibo.card.UserInfor;
import com.weibo.card.WeiBoCard;
import com.weibo.lib.zrclist.ZrcListView;
import com.weibo.lib.zrclist.ZrcListView.OnStartListener;
import com.weibo.utils.ClientUtils;
import com.weibo.utils.DialogUtils;
import com.weibo.utils.DialogUtils.DialogCallBack;
import com.weibo.utils.FaceConversionUtil;
import com.weibo.view.widget.LoadingAlertAnim;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
public class ShowSearchResult extends BaseActivity {
	private static final String TAG = "ShowSearchResult";
	ZrcListView xlistview;
	ImageView image_close;
	Bundle b = null;
	List<WeiBoCard> weibolist_card;
	List<User> userlist_card;
	private MyAdapter adapter;
	WeiBoMainAdapter adapter1;
	LoadingAlertAnim loading;
	int count = 1;
	public void findview() {
		xlistview = (ZrcListView) findViewById(R.id.xListView1);
		image_close = (ImageView) findViewById(R.id.example_right);
	}
	public void listen() {
		image_close.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				ShowSearchResult.this.finish();
			}
		});
		xlistview.setOnItemClickListener(new ZrcListView.OnItemClickListener() {
			@Override
			public void onItemClick(ZrcListView parent, View view, int position, long id) {
				// TODO Auto-generated method stub
				((Mykey) getApplication()).setMymap(weibolist_card.get(position));
				Intent intent = new Intent(ShowSearchResult.this, WeiBoDetail.class);
				ShowSearchResult.this.startActivity(intent);
			}
		});
		xlistview.setOnRefreshStartListener(new OnStartListener() {
			@Override
			public void onStart() {
				if (b.getString("id").equals("0")) {
					userlist_card.clear();
					new AsyncTask<Void, Void, Void>() {
						protected Void doInBackground(Void... param) {
							try {
								getweibo_search_user(1);
							} catch (Exception e) {
								Log.d(TAG, e.toString());
							}
							return null;
						}

						@Override
						protected void onPostExecute(Void result) {
							if (count >= 19) {
								xlistview.startLoadMore();
							} else {
								xlistview.stopLoadMore();
							}
							if (count == 0) {
								FaceConversionUtil.dispToast(ShowSearchResult.this, "对不起没有搜索到您想要的内容！");
							}
							xlistview.setRefreshSuccess();
							adapter.notifyDataSetChanged();
							if (loading != null) {
								loading.dismiss();
							}
						}
					}.execute(null, null, null);
				} else if (b.getString("id").equals("1")) {
					weibolist_card.clear();
					new AsyncTask<Void, Void, Void>() {
						protected Void doInBackground(Void... param) {
							get_search_weibo(1);
							return null;
						}
						@Override
						protected void onPostExecute(Void result) {
							if (count >= 19) {
								xlistview.startLoadMore();
							} else {
								xlistview.stopLoadMore();
							}
							if (count == 0) {
								FaceConversionUtil.dispToast(ShowSearchResult.this, "对不起没有搜索到您想要的内容！");
							}
							xlistview.setRefreshSuccess();
							adapter1.notifyDataSetChanged();
							if (loading != null) {
								loading.dismiss();
							}
						}
					}.execute(null, null, null);
				}
			}
		});
		// 加载更多事件回调
		xlistview.setOnLoadMoreStartListener(new OnStartListener() {
			@Override
			public void onStart() {
				count++;
				// TODO Auto-generated method stub
				if (b.getString("id").equals("0")) {
					new AsyncTask<Void, Void, Void>() {
						protected Void doInBackground(Void... params) {
							getweibo_search_user(count);
							return null;
						}

						@Override
						protected void onPostExecute(Void result) {
							xlistview.setLoadMoreSuccess();
						}
					}.execute(null, null, null);
				} else if (b.getString("id").equals("1")) {
					new AsyncTask<Void, Void, Void>() {
						protected Void doInBackground(Void... params) {
							get_search_weibo(count);
							return null;
						}

						@Override
						protected void onPostExecute(Void result) {
							xlistview.setLoadMoreSuccess();
						}
					}.execute(null, null, null);
				}
			}
		});
	}

	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.showsearchresult);
		b = getIntent().getExtras();
		findview();
		FaceConversionUtil.StartListView(this, xlistview);
		listen();
		loading = new LoadingAlertAnim(ShowSearchResult.this, R.style.MyDialogStyle,"正在获取数据,请稍后...");
		loading.setCanceledOnTouchOutside(false);
		loading.show();
		if (b.getString("id").equals("0")) {
			initView();
		} else if (b.getString("id").equals("1")) {
			initView1();
		}
		xlistview.refresh();
	}

	String result = "";

	void getweibo_search_user(final int page) {
		// TODO Auto-generated method stub
		try {
			List<BasicNameValuePair> params = new ArrayList<BasicNameValuePair>();
			params.add(new BasicNameValuePair("oauth_token", ((Mykey) getApplication()).getOauth_token()));
			params.add(new BasicNameValuePair("oauth_token_secret", ((Mykey) getApplication()).getOauth_token_secret()));
			params.add(new BasicNameValuePair("user_id", ((Mykey) getApplication()).getUid()));
			params.add(new BasicNameValuePair("page", Integer.toString(page)));
			params.add(new BasicNameValuePair("key", b.getString("word_key")));
			result = ClientUtils.post_str(ClientUtils.BASE_URL + ShowSearchResult.this.getString(ClientUtils.WeiboStatuses_weibo_search_user), params, ShowSearchResult.this);
			JSONArray jArray = new JSONArray(result);
			Gson gson = new Gson();
			for (int i = 0; i < jArray.length(); i++) {
				userlist_card.add(gson.fromJson(jArray.getString(i), User.class));
			}
			count = jArray.length();
		} catch (Exception e) {
			Log.d(TAG, e.toString());
		}
	}

	void get_search_weibo(final int page) {
		try {
			List<BasicNameValuePair> params = new ArrayList<BasicNameValuePair>();
			params.add(new BasicNameValuePair("oauth_token", ((Mykey) getApplication()).getOauth_token()));
			params.add(new BasicNameValuePair("oauth_token_secret", ((Mykey) getApplication()).getOauth_token_secret()));
			params.add(new BasicNameValuePair("user_id", ((Mykey) getApplication()).getUid()));
			params.add(new BasicNameValuePair("page", Integer.toString(page)));
			params.add(new BasicNameValuePair("key", b.getString("word_key")));
			result = ClientUtils.post_str(ClientUtils.BASE_URL + ShowSearchResult.this.getString(ClientUtils.WeiboStatuses_weibo_search_weibo), params, ShowSearchResult.this);
			JSONArray jArray = new JSONArray(result);
			Gson gson = new Gson();
			for (int i = 0; i < jArray.length(); i++) {
				weibolist_card.add(gson.fromJson(jArray.getString(i), WeiBoCard.class));
			}
			count = jArray.length();
		} catch (Exception e) {
			Log.d(TAG, e.toString());
		}
	}
	private void initView() {
		userlist_card = new ArrayList<User>();
		adapter = new MyAdapter(this);
		xlistview.setAdapter(adapter);
	}
	private class MyAdapter extends BaseAdapter {
		private Context mContext;
		private LayoutInflater inflater;
		public MyAdapter(Context ctx) {
			mContext = ctx;
			inflater = LayoutInflater.from(mContext);
		}
		@Override
		public int getCount() {
			return userlist_card.size();
		}
		@Override
		public User getItem(int position) {
			return userlist_card.get(position);
		}
		@Override
		public long getItemId(int position) {
			// TODO Auto-generated method stub
			return position;
		}
		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			// TODO Auto-generated method stub
			ViewHolder holder = null;
			if (convertView == null) {
				holder = new ViewHolder();
				convertView = inflater.inflate(R.layout.follow_person_item, null);
				holder.textview1 = (TextView) convertView.findViewById(R.id.textView1);
				holder.textview2 = (TextView) convertView.findViewById(R.id.textView2);
				holder.imageview1 = (SmartImageView) convertView.findViewById(R.id.imageView1);
				holder.foll = (TextView) convertView.findViewById(R.id.follow);
			} else {
				holder = new ViewHolder();
				convertView = inflater.inflate(R.layout.follow_person_item, null);
				holder.textview1 = (TextView) convertView.findViewById(R.id.textView1);
				holder.textview2 = (TextView) convertView.findViewById(R.id.textView2);
				holder.imageview1 = (SmartImageView) convertView.findViewById(R.id.imageView1);
				holder.foll = (TextView) convertView.findViewById(R.id.follow);
			}
			if (userlist_card != null && userlist_card.size() > 0) {
				holder.textview1.setText(getItem(position).getUname());
				holder.textview2.setText(getItem(position).getLocation());
				holder.imageview1.setImageUrl(getItem(position).getAvatar_middle());
				if (getItem(position).getFollow_status().getFollowing().equals("1")) {
					holder.foll.setCompoundDrawablesWithIntrinsicBounds(0, R.drawable.card_icon_attention, 0, 0);
					holder.foll.setText("已关注");
				} else {
					holder.foll.setCompoundDrawablesWithIntrinsicBounds(0, R.drawable.card_icon_addattention, 0, 0);
					holder.foll.setText("未关注");
				}
				addListener(convertView, position);
			}
			return convertView;
		}
		String kkk;
		public void addListener(final View convertView, final int arg) {
			convertView.setOnClickListener(new View.OnClickListener() {
				public void onClick(View v) {
					((Mykey) getApplication()).setOtheruid(getItem(arg).getUid());
					System.out.println("点击UID=" + getItem(arg).getUid());
					startActivity(new Intent(ShowSearchResult.this, OtherUserPanl.class));
				}
			});
			((TextView) convertView.findViewById(R.id.follow)).setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					if (getItem(arg).getUname().equals(UserInfor.UNAME)) {
						FaceConversionUtil.dispToast(ShowSearchResult.this, "不能关注自己！");
					} else {
						if (((TextView) convertView.findViewById(R.id.follow)).getText().toString().equals("已关注")) {
							DialogUtils.dialogBuilder(ShowSearchResult.this, "取消关注", "是否取消关注?", new DialogCallBack() {
								@Override
								public void callBack() {
									new AsyncTask<Void, Void, Void>() {
										protected Void doInBackground(Void... param) {
											List<BasicNameValuePair> params = new ArrayList<BasicNameValuePair>();
											params.add(new BasicNameValuePair("oauth_token", ((Mykey) getApplication()).getOauth_token()));
											params.add(new BasicNameValuePair("oauth_token_secret", ((Mykey) getApplication()).getOauth_token_secret()));
											params.add(new BasicNameValuePair("user_id", ((Mykey) getApplication()).getUid()));
											params.add(new BasicNameValuePair("fid", getItem(arg).getUid()));
											kkk = ClientUtils.post_str(ClientUtils.BASE_URL + ShowSearchResult.this.getString(ClientUtils.User_follow_destroy), params, ShowSearchResult.this);
											Log.d(TAG, "关注了UID:" + getItem(arg).getUid() + "服务器返回的结果:" + kkk);
											return null;
										}
										@Override
										protected void onPostExecute(Void result) {
											if (!kkk.equals("0")) {
												FaceConversionUtil.dispToast(ShowSearchResult.this, "取消关注成功");
												((TextView) convertView.findViewById(R.id.follow)).setCompoundDrawablesWithIntrinsicBounds(0, R.drawable.card_icon_addattention, 0, 0);
												((TextView) convertView.findViewById(R.id.follow)).setText("未关注");
											} else {
												FaceConversionUtil.dispToast(ShowSearchResult.this, "取消关注失败");
											}
										}
									}.execute(null, null, null);
								}
							});
						} else {
							DialogUtils.dialogBuilder(ShowSearchResult.this, "关注", "是否关注?", new DialogCallBack() {
								@Override
								public void callBack() {
									new AsyncTask<Void, Void, Void>() {
										protected Void doInBackground(Void... param) {
											List<BasicNameValuePair> params = new ArrayList<BasicNameValuePair>();
											params.add(new BasicNameValuePair("oauth_token", ((Mykey) getApplication()).getOauth_token()));
											params.add(new BasicNameValuePair("oauth_token_secret", ((Mykey) getApplication()).getOauth_token_secret()));
											params.add(new BasicNameValuePair("user_id", ((Mykey) getApplication()).getUid()));
											params.add(new BasicNameValuePair("fid", getItem(arg).getUid()));
											kkk = ClientUtils.post_str(ClientUtils.BASE_URL + ShowSearchResult.this.getString(ClientUtils.User_follow_create), params, ShowSearchResult.this);
											Log.d(TAG, "关注了UID：" + getItem(arg).getUid() + "服务器返回的结果:" + kkk);
											return null;
										}
										@Override
										protected void onPostExecute(Void result) {
											if (!kkk.equals("0")) {
												((TextView) convertView.findViewById(R.id.follow)).setCompoundDrawablesWithIntrinsicBounds(0, R.drawable.card_icon_attention, 0, 0);
												FaceConversionUtil.dispToast(ShowSearchResult.this, "关注成功");
												((TextView) convertView.findViewById(R.id.follow)).setText("已关注");
											} else {
												FaceConversionUtil.dispToast(ShowSearchResult.this, "关注失败");
											}
										}
									}.execute(null, null, null);
								}
							});
						}
					}
				}
			});
		}

		private class ViewHolder {
			TextView textview1, textview2, textview3, textview4, textview5;
			SmartImageView imageview1;
			TextView foll;
		}
	}

	private void initView1() {
		weibolist_card = new ArrayList<WeiBoCard>();
		adapter1 = new WeiBoMainAdapter(this, weibolist_card);
		xlistview.setAdapter(adapter1);
	}
}