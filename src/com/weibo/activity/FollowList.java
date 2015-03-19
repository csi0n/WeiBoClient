package com.weibo.activity;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.loopj.android.image.SmartImageView;
import com.weibo.application.*;
import com.weibo.card.User;
import com.weibo.lib.zrclist.ZrcListView;
import com.weibo.lib.zrclist.ZrcListView.OnItemClickListener;
import com.weibo.lib.zrclist.ZrcListView.OnStartListener;
import com.weibo.utils.ClientUtils;
import com.weibo.utils.DialogUtils;
import com.weibo.utils.DialogUtils.DialogCallBack;
import com.weibo.utils.FaceConversionUtil;
import com.weibo.utils.Utils;
import com.weibo.R;
/**
 * 
 * 关注列表已经粉丝列表
 * 
 */
public class FollowList extends BaseActivity {
	List<User> user_list;
	private ZrcListView mListView;
	private MyAdapter adapter;
	private static final String TAG = "FollowList.java";
	TextView textview1;
	int number = 1;
	ImageView imageview1;
	String s;
	int count = 0;
	protected static final int find = 2;
	protected static final int change = 0;
	protected static final int end = 1;
	private Handler handler = new Handler() {
		public void handleMessage(Message msg) {
			switch (msg.what) {
				case change :
					if (count >= 19) {
						mListView.startLoadMore();
						Log.d(TAG, "获取人数大于19条!");
					} else {
						mListView.stopLoadMore();
						Log.d(TAG, "获取人数小于19条!");
					}
					adapter.notifyDataSetChanged();
					mListView.setRefreshSuccess("本次刷新成功！共有" + count + "个用户!");
					break;
				case end :
					mListView.setLoadMoreSuccess();
					break;
			}
		}
	};
	String kkk;
	public void listener() {
		mListView.setOnRefreshStartListener(new OnStartListener() {
			@Override
			public void onStart() {
				// TODO Auto-generated method stub
				new Thread(new Runnable() {
					public void run() {
						if (s.equals("2")) {
							user_list.clear();
							getuserfollowing(1);
						} else if (s.equals("1")) {
							user_list.clear();
							getuserfollows(1);
						} else if (s.equals("3")) {
							user_list.clear();
							getmyfriend(1);
						}
					}
				}).start();
			}
		});
		mListView.setOnLoadMoreStartListener(new OnStartListener() {
			@Override
			public void onStart() {
				// TODO Auto-generated method stub
				new Thread(new Runnable() {
					@Override
					public void run() {
						// TODO Auto-generated method stub
						if (s.equals("2")) {
							number++;
							getuserfollowing(number);
							Message message = new Message();
							message.what = end;
							handler.sendMessage(message);
						} else if (s.equals("1")) {
							number++;
							getuserfollows(number);
							Message message = new Message();
							message.what = end;
							handler.sendMessage(message);
						} else if (s.equals("3")) {
							number++;
							getmyfriend(number);
							Message message = new Message();
							message.what = end;
							handler.sendMessage(message);
						}
					}
				}).start();
			}
		});
		imageview1.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				FollowList.this.finish();
			}
		});
		mListView.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(ZrcListView parent, final View view, final int position, long id) {
				// TODO Auto-generated method stub
				view.setOnClickListener(new View.OnClickListener() {
					@Override
					public void onClick(View v) {
						// TODO Auto-generated method stub
						Intent inten = new Intent(FollowList.this, OtherUserPanl.class);
						((Mykey) getApplication()).setOtheruid(user_list.get(position).getUid());
						FollowList.this.startActivity(inten);
					}
				});
				((TextView) view.findViewById(R.id.follow)).setOnClickListener(new OnClickListener() {
					@Override
					public void onClick(View v) {
						// TODO Auto-generated method stub
						if (((TextView) view.findViewById(R.id.follow)).getText().toString().equals("已关注")) {
							DialogUtils.dialogBuilder(FollowList.this, "取消关注", "是否取消关注?", new DialogCallBack() {
								@Override
								public void callBack() {
									// TODO Auto-generated
									// method
									// stub
									new AsyncTask<Void, Void, Void>() {
										protected Void doInBackground(Void... param) {
											List<BasicNameValuePair> params = new ArrayList<BasicNameValuePair>();
											params.add(new BasicNameValuePair("oauth_token", ((Mykey) getApplication()).getOauth_token()));
											params.add(new BasicNameValuePair("oauth_token_secret", ((Mykey) getApplication()).getOauth_token_secret()));
											params.add(new BasicNameValuePair("user_id", ((Mykey) getApplication()).getUid()));
											params.add(new BasicNameValuePair("fid", ((Mykey) getApplication()).getUid()));
											kkk = ClientUtils.post_str(ClientUtils.BASE_URL + FollowList.this.getString(ClientUtils.User_follow_create), params, FollowList.this);
											return null;
										}
										@Override
										protected void onPostExecute(Void result) {
											if (!kkk.equals("0")) {
												FaceConversionUtil.dispToast(FollowList.this, "取消关注成功");
												((TextView) view.findViewById(R.id.follow)).setCompoundDrawablesWithIntrinsicBounds(0, R.drawable.card_icon_addattention, 0, 0);
												((TextView) view.findViewById(R.id.follow)).setText("未关注");
											} else {
												FaceConversionUtil.dispToast(FollowList.this, "取消关注失败");
											}
										}
									}.execute(null, null, null);
								}
							});
						} else {
							DialogUtils.dialogBuilder(FollowList.this, "关注", "是否关注?", new DialogCallBack() {
								@Override
								public void callBack() {
									// TODO Auto-generated
									// method
									// stub
									new AsyncTask<Void, Void, Void>() {
										protected Void doInBackground(Void... param) {
											List<BasicNameValuePair> params = new ArrayList<BasicNameValuePair>();
											params.add(new BasicNameValuePair("oauth_token", ((Mykey) getApplication()).getOauth_token()));
											params.add(new BasicNameValuePair("oauth_token_secret", ((Mykey) getApplication()).getOauth_token_secret()));
											params.add(new BasicNameValuePair("user_id", ((Mykey) getApplication()).getUid()));
											params.add(new BasicNameValuePair("fid", user_list.get(position).getUid()));
											kkk = ClientUtils.post_str(ClientUtils.BASE_URL + FollowList.this.getString(ClientUtils.User_follow_create), params, FollowList.this);
											return null;
										}

										@Override
										protected void onPostExecute(Void result) {
											if (!kkk.equals("0")) {
												((TextView) view.findViewById(R.id.follow)).setCompoundDrawablesWithIntrinsicBounds(0, R.drawable.card_icon_attention, 0, 0);
												FaceConversionUtil.dispToast(FollowList.this, "关注成功");
											} else {
												FaceConversionUtil.dispToast(FollowList.this, "关注失败");
											}
										}
									}.execute(null, null, null);
								}
							});
						}
					}
				});
			}
		});
	}

	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		LayoutInflater inflater = (LayoutInflater) getSystemService(LAYOUT_INFLATER_SERVICE);
		LinearLayout parent = (LinearLayout) inflater.inflate(R.layout.follow_layout, null);
		setContentView(parent);
		s = getIntent().getStringExtra("a");
		findView();
		FaceConversionUtil.StartListView(this, mListView);
		initView();
		listener();
		mListView.refresh();
		Utils.getInstance().addActivity(this);
	}

	private void initView() {
		user_list = new ArrayList<User>();
		adapter = new MyAdapter(this);
		mListView.setAdapter(adapter);
	}

	private void findView() {
		mListView = (ZrcListView) findViewById(R.id.xListView1);
		imageview1 = (ImageView) findViewById(R.id.example_right);
		textview1 = (TextView) findViewById(R.id.titleText);
		if (s.equals("1")) {
			textview1.setText("粉丝");
		} else if (s.equals("2")) {
			textview1.setText("关注");
		} else if (s.equals("3")) {
			textview1.setText("我的好友");
		}
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
			return user_list.size();
		}

		@Override
		public User getItem(int position) {
			return user_list.get(position);
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
				holder.follow = (TextView) convertView.findViewById(R.id.follow);
				convertView.setTag(holder);
			} else {
				holder = (ViewHolder) convertView.getTag();
			}
			if (user_list != null && user_list.size() > 0) {
				if (getItem(position).getUname() != null) {
					holder.textview1.setText(getItem(position).getUname());
				}
				if (getItem(position).getIntro() != null) {
					holder.textview2.setText(getItem(position).getIntro());
				}
				holder.imageview1.setImageUrl(getItem(position).getAvatar_middle());
				if (s.equals("2")) {
					if (getItem(position).getFollow_status().getFollowing().equals("1")) {
						holder.follow.setCompoundDrawablesWithIntrinsicBounds(0, R.drawable.card_icon_attention, 0, 0);
						holder.follow.setText("已关注");

					} else {
						holder.follow.setCompoundDrawablesWithIntrinsicBounds(0, R.drawable.card_icon_addattention, 0, 0);
						holder.follow.setText("未关注");
					}
				} else if (s.equals("1")) {
					if (getItem(position).getFollow_status().getFollowing().equals("1")) {
						holder.follow.setCompoundDrawablesWithIntrinsicBounds(0, R.drawable.card_icon_attention, 0, 0);
						holder.follow.setText("已关注");

					} else {
						holder.follow.setCompoundDrawablesWithIntrinsicBounds(0, R.drawable.card_icon_addattention, 0, 0);
						holder.follow.setText("未关注");
					}
				} else if (s.equals("3")) {
					if (getItem(position).getFollow_status().getFollowing().equals("1")) {
						holder.follow.setCompoundDrawablesWithIntrinsicBounds(0, R.drawable.card_icon_attention, 0, 0);
						holder.follow.setText("已关注");

					} else {
						holder.follow.setCompoundDrawablesWithIntrinsicBounds(0, R.drawable.card_icon_addattention, 0, 0);
						holder.follow.setText("未关注");
					}
				}
			}
			return convertView;
		}

		private class ViewHolder {
			TextView textview1, textview2;
			SmartImageView imageview1;
			TextView follow;
		}
	}

	public void getmyfriend(int page) {
		try {
			List<BasicNameValuePair> params = new ArrayList<BasicNameValuePair>();
			params.add(new BasicNameValuePair("oauth_token", ((Mykey) getApplication()).getOauth_token()));
			params.add(new BasicNameValuePair("oauth_token_secret", ((Mykey) getApplication()).getOauth_token_secret()));
			params.add(new BasicNameValuePair("user_id", ((Mykey) getApplication()).getUid()));
			params.add(new BasicNameValuePair("fid", ((Mykey) getApplication()).getUid()));
			params.add(new BasicNameValuePair("page", Integer.toString(page)));
			String result = ClientUtils.post_str(ClientUtils.BASE_URL + FollowList.this.getString(ClientUtils.User_user_friends), params, FollowList.this);
			JSONArray jArray = new JSONArray(result);
			Gson gson = new Gson();
			for (int i = 0; i < jArray.length(); i++) {
				user_list.add(gson.fromJson(jArray.getString(i), User.class));
			}
			count = jArray.length();
			Message message = new Message();
			message.what = change;
			handler.sendMessage(message);
		} catch (Exception e) {
			Log.d(TAG, e.toString());
		}
	}

	public void getuserfollowing(final int page) {
		try {
			List<BasicNameValuePair> params = new ArrayList<BasicNameValuePair>();
			params.add(new BasicNameValuePair("oauth_token", ((Mykey) getApplication()).getOauth_token()));
			params.add(new BasicNameValuePair("oauth_token_secret", ((Mykey) getApplication()).getOauth_token_secret()));
			params.add(new BasicNameValuePair("user_id", ((Mykey) getApplication()).getUid()));
			params.add(new BasicNameValuePair("fid", ((Mykey) getApplication()).getUid()));
			params.add(new BasicNameValuePair("page", Integer.toString(page)));
			String result = ClientUtils.post_str(ClientUtils.BASE_URL + FollowList.this.getString(ClientUtils.User_user_following), params, FollowList.this);
			JSONArray jArray = new JSONArray(result);
			Gson gson = new Gson();
			for (int i = 0; i < jArray.length(); i++) {
				user_list.add(gson.fromJson(jArray.getString(i), User.class));
			}
			count = jArray.length();
			Message message = new Message();
			message.what = change;
			handler.sendMessage(message);
		} catch (Exception e) {
			Log.d(TAG, e.toString());
		}
	}

	public void getuserfollows(int page) {
		try {
			List<BasicNameValuePair> params = new ArrayList<BasicNameValuePair>();
			params.add(new BasicNameValuePair("oauth_token", ((Mykey) getApplication()).getOauth_token()));
			params.add(new BasicNameValuePair("oauth_token_secret", ((Mykey) getApplication()).getOauth_token_secret()));
			params.add(new BasicNameValuePair("user_id", ((Mykey) getApplication()).getUid()));
			params.add(new BasicNameValuePair("fid", ((Mykey) getApplication()).getUid()));
			params.add(new BasicNameValuePair("page", Integer.toString(page)));
			String result = ClientUtils.post_str(ClientUtils.BASE_URL + FollowList.this.getString(ClientUtils.User_user_followers), params, FollowList.this);
			JSONArray jArray = new JSONArray(result);
			Gson gson = new Gson();
			for (int i = 0; i < jArray.length(); i++) {
				user_list.add(gson.fromJson(jArray.getString(i), User.class));
			}
			count = jArray.length();
			Message message = new Message();
			message.what = change;
			handler.sendMessage(message);
		} catch (Exception e) {
			Log.d(TAG, e.toString());
		}
	}

}
