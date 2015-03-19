package com.weibo.activity;

//这边执行的是查看详细微博
import java.text.SimpleDateFormat;
import com.weibo.R;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;

import com.weibo.utils.*;
import com.weibo.utils.DialogUtils.DialogCallBack;

import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONTokener;

import com.google.gson.Gson;
import com.loopj.android.image.SmartImageView;
import com.weibo.adapters.WeiBoDetailAdapter;
import com.weibo.application.*;
import com.weibo.card.CommentInfor;
import com.weibo.card.WeiBoCard;
import com.weibo.lib.umeng.ShareAPI;
import com.weibo.lib.zrclist.ZrcListView;
import com.weibo.lib.zrclist.ZrcListView.OnStartListener;
import com.weibo.utils.ClientUtils;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.animation.Animation;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
public class WeiBoDetail extends BaseActivity {
	ZrcListView xlistview1;
	String result = "";
	Animation mShowAction, mHiddenAction;
	ImageView imageview_2, updown;
	Button button1, button2, button3, share, del;
	SmartImageView image;
	RelativeLayout report, iii;
	WeiBoDetailAdapter adapter;
	List<CommentInfor> commentcard_list;
	CommentInfor comment_card;
	WeiBoCard weibo;
	private static final String TAG = "WeiBoDetail.java";
	private void initView() {
		commentcard_list = new ArrayList<CommentInfor>();
		adapter = new WeiBoDetailAdapter(this, commentcard_list, weibo);
		xlistview1.setAdapter(adapter);
	}
	String fare;
	public void findview() {
		share = (Button) findViewById(R.id.button4);
		xlistview1 = (ZrcListView) findViewById(R.id.zListView);
		button1 = (Button) findViewById(R.id.button1);
		button2 = (Button) findViewById(R.id.button2);
		button3 = (Button) findViewById(R.id.button3);
		imageview_2 = (ImageView) findViewById(R.id.example_right);
		del = (Button) findViewById(R.id.del);
		if (((Mykey) getApplication()).getMymap().getUid() != null) {
			if (((Mykey) getApplication()).getMymap().getUid().equals(((Mykey) getApplication()).getUid())) {
				if (del.getVisibility() == View.GONE) {
					del.setVisibility(View.VISIBLE);
				}
			} else {
				if (del.getVisibility() == View.VISIBLE) {
					del.setVisibility(View.GONE);
				}
			}
		}
	}

	String pinglun = "";

	public void getpinglun(final int page) {
		// TODO Auto-generated method stub
		new AsyncTask<Void, Void, Void>() {
			protected Void doInBackground(Void... param) {
				List<BasicNameValuePair> params = new ArrayList<BasicNameValuePair>();
				params.add(new BasicNameValuePair("oauth_token", ((Mykey) getApplication()).getOauth_token()));
				params.add(new BasicNameValuePair("oauth_token_secret", ((Mykey) getApplication()).getOauth_token_secret()));
				params.add(new BasicNameValuePair("feed_id", ((Mykey) getApplication()).getMymap().getFeed_id()));
				params.add(new BasicNameValuePair("user_id", ((Mykey) getApplication()).getUid()));
				params.add(new BasicNameValuePair("page", String.valueOf(page)));
				pinglun = ClientUtils.post_str(ClientUtils.BASE_URL + WeiBoDetail.this.getString(ClientUtils.WeiboStatuses_comments), params, WeiBoDetail.this);
				Log.d(TAG, "评论:" + pinglun);
				return null;
			}
			@SuppressLint("NewApi")
			@Override
			protected void onPostExecute(Void result) {
				try {
					Log.d(TAG, pinglun);
					Gson gson = new Gson();
					JSONArray jArray = new JSONArray(pinglun);
					for (int i = 0; i < jArray.length(); i++) {
						commentcard_list.add(gson.fromJson(jArray.getString(i), CommentInfor.class));
					}
					if (jArray.length() >= 19) {
						xlistview1.startLoadMore();
					} else {
						xlistview1.stopLoadMore();
					}
					xlistview1.setRefreshSuccess();
					adapter.notifyDataSetChanged();
				} catch (Exception e) {
					Log.d(TAG, e.toString());
				}
			}
		}.execute(null, null, null);
	}
	int number = 1;
	public void listener() {
		xlistview1.setOnRefreshStartListener(new OnStartListener() {
			@Override
			public void onStart() {
				commentcard_list.clear();
				getpinglun(1);
			}
		});
		xlistview1.setOnLoadMoreStartListener(new OnStartListener() {
			@Override
			public void onStart() {
				// TODO Auto-generated method stub
				getpinglun(number++);
			}
		});
		button1.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if (PublicTimeWeiBoAuthorize.isLogin) {
					Intent intent = new Intent(WeiBoDetail.this, RelayWeiBo.class);
					intent.putExtra("feed_id", ((Mykey)getApplication()).getMymap().getFeed_id());
					WeiBoDetail.this.startActivity(intent);
				} else {
					DialogUtils.dialogBuilder(WeiBoDetail.this, "提示", "请先登录！", new DialogCallBack() {
						@Override
						public void callBack() {
							Intent i = new Intent(WeiBoDetail.this, MainActivity.class);
							WeiBoDetail.this.startActivity(i);
						}
					});
				}
			}
		});
		button2.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if (PublicTimeWeiBoAuthorize.isLogin) {
					Intent intent = new Intent(WeiBoDetail.this, CommentWeiBo.class);
					intent.putExtra("feed_id", ((Mykey) getApplication()).getMymap().getFeed_id());
					WeiBoDetail.this.startActivity(intent);
				} else {
					DialogUtils.dialogBuilder(WeiBoDetail.this, "提示", "请先登录！", new DialogCallBack() {
						@Override
						public void callBack() {
							Intent i = new Intent(WeiBoDetail.this, MainActivity.class);
							WeiBoDetail.this.startActivity(i);
						}
					});
				}
			}
		});
		imageview_2.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				WeiBoDetail.this.finish();
			}
		});
		button3.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if (PublicTimeWeiBoAuthorize.isLogin) {
					DialogUtils.dialogBuilder(WeiBoDetail.this, "添加收藏？", "是否添加收藏！", new DialogCallBack() {
						@Override
						public void callBack() {
							// TODO Auto-generated method stub
							new AsyncTask<Void, Void, Void>() {
								protected Void doInBackground(Void... paras) {
									List<BasicNameValuePair> params = new ArrayList<BasicNameValuePair>();
									params.add(new BasicNameValuePair("oauth_token", ((Mykey) getApplication()).getOauth_token()));
									params.add(new BasicNameValuePair("oauth_token_secret", ((Mykey) getApplication()).getOauth_token_secret()));
									params.add(new BasicNameValuePair("feed_id",weibo.getFeed_id()));
									params.add(new BasicNameValuePair("source_table_name", "feed"));
									params.add(new BasicNameValuePair("source_app", "public"));
									params.add(new BasicNameValuePair("user_id", ((Mykey) getApplication()).getUid()));
									fare = ClientUtils.post_str(ClientUtils.BASE_URL + WeiBoDetail.this.getString(ClientUtils.WeiboStatuses_favorite_create), params, WeiBoDetail.this);
									return null;
								}
								@Override
								protected void onPostExecute(Void result) {
									if (!fare.equals("0")) {
										FaceConversionUtil.dispToast(WeiBoDetail.this, "添加收藏成功!");
									} else {
										DialogUtils.dialogBuilder(WeiBoDetail.this, "您已经收藏的该微博", "是否取消收藏？", new DialogCallBack() {
											@Override
											public void callBack() {
												// TODO
												// Auto-generated
												// method stub
												new AsyncTask<Void, Void, Void>() {
													protected Void doInBackground(Void... param) {
														List<BasicNameValuePair> params = new ArrayList<BasicNameValuePair>();
														params.add(new BasicNameValuePair("oauth_token", ((Mykey) getApplication()).getOauth_token()));
														params.add(new BasicNameValuePair("oauth_token_secret", ((Mykey) getApplication()).getOauth_token_secret()));
														params.add(new BasicNameValuePair("feed_id",weibo.getFeed_id()));
														params.add(new BasicNameValuePair("source_table_name", "feed"));
														params.add(new BasicNameValuePair("source_app", "public"));
														params.add(new BasicNameValuePair("user_id", ((Mykey) getApplication()).getUid()));
														fare = ClientUtils.post_str(ClientUtils.BASE_URL + WeiBoDetail.this.getString(ClientUtils.WeiboStatuses_favorite_destroy), params, WeiBoDetail.this);
														return null;
													}
													@Override
													protected void onPostExecute(Void result) {
														if (!fare.equals("0")) {
															FaceConversionUtil.dispToast(WeiBoDetail.this, "取消收藏成功!");
														} else {
															FaceConversionUtil.dispToast(WeiBoDetail.this, "取消收藏失败!");
														}
														Log.d(TAG, "jieshu");
													}
												}.execute(null, null, null);
											}
										});
									}
									Log.d(TAG, "jieshu");
								}
							}.execute(null, null, null);
						}
					});
				} else {
					DialogUtils.dialogBuilder(WeiBoDetail.this, "提示", "请先登录！", new DialogCallBack() {
						@Override
						public void callBack() {
							Intent i = new Intent(WeiBoDetail.this, MainActivity.class);
							WeiBoDetail.this.startActivity(i);
						}
					});
				}
			}
		});
		share.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				ShareAPI.start(weibo.getContent(), WeiBoDetail.this).openShare(WeiBoDetail.this, false);
			}
		});
		del.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if (PublicTimeWeiBoAuthorize.isLogin) {
					DialogUtils.dialogBuilder(WeiBoDetail.this, "提示", "确定要删除微博吗？", new DialogCallBack() {
						@Override
						public void callBack() {
							new AsyncTask<Void, Void, Void>() {
								protected Void doInBackground(Void... param) {
									List<BasicNameValuePair> params = new ArrayList<BasicNameValuePair>();
									params.add(new BasicNameValuePair("oauth_token", ((Mykey) getApplication()).getOauth_token()));
									params.add(new BasicNameValuePair("oauth_token_secret", ((Mykey) getApplication()).getOauth_token_secret()));
									params.add(new BasicNameValuePair("feed_id", weibo.getFeed_id()));
									params.add(new BasicNameValuePair("user_id", ((Mykey) getApplication()).getUid()));
									result = ClientUtils.post_str(ClientUtils.BASE_URL + WeiBoDetail.this.getString(ClientUtils.WeiboStatuses_destroy), params, WeiBoDetail.this);
									return null;
								}
								@Override
								protected void onPostExecute(Void resut) {
									if (!result.equals("0")) {
										FaceConversionUtil.dispToast(WeiBoDetail.this, "删除成功！");
										((Mykey)getApplication()).setIsdel("1");
										finish();
									} else {
										FaceConversionUtil.dispToast(WeiBoDetail.this, "删除失敗！");
									}

									Log.d(TAG, "jieshu");
								}
							}.execute(null, null, null);
						}
					});
				} else {
					DialogUtils.dialogBuilder(WeiBoDetail.this, "提示", "请先登录！", new DialogCallBack() {
						@Override
						public void callBack() {
							Intent i = new Intent(WeiBoDetail.this, MainActivity.class);
							WeiBoDetail.this.startActivity(i);
						}
					});
				}
			}
		});
	}
	Button favoti;
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.xiangxiweibo);
		weibo = ((Mykey) getApplication()).getMymap();
		findview();
		FaceConversionUtil.StartListView(this, xlistview1);
		initView();
		listener();
		xlistview1.refresh();
	}
	@Override
	protected void onResume() {
		super.onResume();
		if (((Mykey) getApplication()).getRefresh().equals("1")) {
			xlistview1.setSelection(0);
			xlistview1.refresh();
			((Mykey) getApplication()).setRefresh("0");
		}
	}
}
