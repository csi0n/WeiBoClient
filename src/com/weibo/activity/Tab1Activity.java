package com.weibo.activity;
import java.util.ArrayList;
import com.weibo.R;

import java.util.List;

import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONObject;

import android.annotation.SuppressLint;
import android.app.ActivityManager;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.BitmapDrawable;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.media.AudioManager;
import android.media.SoundPool;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.google.gson.Gson;
import com.weibo.adapters.WeiBaSendBtnAdapter;
import com.weibo.adapters.WeiBoMainAdapter;
import com.weibo.application.*;
import com.weibo.card.SomeViewCard;
import com.weibo.card.User;
import com.weibo.card.UserInfor;
import com.weibo.card.WeiBoCard;
import com.weibo.db.DataHelper;
import com.weibo.lib.photodeal.PublishedActivity;
import com.weibo.lib.zrclist.ZrcListView;
import com.weibo.lib.zrclist.ZrcListView.OnStartListener;
import com.weibo.utils.ACache;
import com.weibo.utils.ClientUtils;
import com.weibo.utils.Common;
import com.weibo.utils.DialogUtils;
import com.weibo.utils.DialogUtils.DialogCallBack;
import com.weibo.utils.FaceConversionUtil;
import com.weibo.utils.Utils;
import com.weibo.view.widget.CheckAlertAnim;
import com.weibo.view.widget.LoadingAlertAnim;
@SuppressLint("NewApi")
public class Tab1Activity extends BaseActivity {
	private ZrcListView mListView;
	TextView title;
	ImageView more, addnewuser;
	private WeiBoMainAdapter adapter;
	protected static final int show_pic = 0;
	protected static final int change = 1;
	protected static final int notfind = 4;
	protected static final int end = 2;
	protected static final int weibocount = 3;
	ArrayList<String> news, username, Comment_countmain, Repost_countmain, ctimemain, userimgurl;
	WeiBoCard weibo_card;
	List<WeiBoCard> weibo_list;
	int number = 1;
	LoadingAlertAnim loading, loading2;
	ImageView imageview1;
	private static final String TAG = "Tab1Activity.JAVA";
	private SensorManager sensorManager;
	private boolean showSettings = false;
	int count = 0;
	private int show = 1;
	String code = "0";
	private SoundPool soundPool;
	String titlename[] = {"我关注的人", "公共微博", "我发布的微博"};
	int pic[] = {R.drawable.icon_onxin, R.drawable.icon_refresh_off_32, R.drawable.icon_myinfo_48};
	private PopupWindow popupWindow;
	private LinearLayout layout;
	TextView mark;
	private ListView listView;
	DataHelper helper;
	int weibo = 0;
	FrameLayout noti;
	String markresult = null;
	CheckAlertAnim success, error;
	private void findView() {
		mark = (TextView) findViewById(R.id.mark);
		mark.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				new AsyncTask<Void, Void, Void>() {
					protected Void doInBackground(Void... param) {
						List<BasicNameValuePair> params = new ArrayList<BasicNameValuePair>();
						params.add(new BasicNameValuePair("oauth_token", ((Mykey) getApplication()).getOauth_token()));
						params.add(new BasicNameValuePair("oauth_token_secret", ((Mykey) getApplication()).getOauth_token_secret()));
						params.add(new BasicNameValuePair("user_id", ((Mykey) getApplication()).getUid()));
						markresult = ClientUtils.post_str(ClientUtils.BASE_URL + Tab1Activity.this.getString(ClientUtils.Checkin_checkin), params, Tab1Activity.this);
						return null;
					}

					@Override
					protected void onPostExecute(Void result) {
						try {
							if (!markresult.equals("0")) {
								success = new CheckAlertAnim(Tab1Activity.this, R.style.MyDialogStyle, "签到成功", R.drawable.empty_check, 3000);
								success.setCanceledOnTouchOutside(true);
								success.show();
							} else {
								error = new CheckAlertAnim(Tab1Activity.this, R.style.MyDialogStyle, "请不要重复签到！", R.drawable.empty_failed, 3000);
								error.setCanceledOnTouchOutside(true);
								error.show();
							}
							Log.d(TAG, markresult);
						} catch (Exception e) {
							e.printStackTrace();
						}
					}
				}.execute(null, null, null);
			}
		});
		addnewuser = (ImageView) findViewById(R.id.imageView1);
		addnewuser.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent inten = new Intent(Tab1Activity.this, Search.class);
				inten.putExtra("a", "1");
				startActivity(inten);
			}
		});
		mListView = (ZrcListView) findViewById(R.id.zListView);
		// imageview1 = (ImageView) findViewById(R.id.example_right);
		title = (TextView) findViewById(R.id.titleText);
		noti = (FrameLayout) findViewById(R.id.noti);
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK) {
			DialogUtils.dialogBuilder(Tab1Activity.this, "提示", "确定要退出？", new DialogCallBack() {
				@SuppressWarnings("deprecation")
				@Override
				public void callBack() {
					Utils.getInstance().exit(Tab1Activity.this);
				}
			});
		}
		return super.onKeyDown(keyCode, event);
	}

	String result = "";
	String userinfor_result = "";
	public void find_loginuser() {
		loading2 = new LoadingAlertAnim(Tab1Activity.this, R.style.MyDialogStyle, "正在获取用户数据，请稍后");
		loading2.setCanceledOnTouchOutside(false);
		loading2.show();
		new AsyncTask<Void, Void, Void>() {
			@Override
			protected Void doInBackground(Void... param) {
				// TODO Auto-generated method stub
				List<BasicNameValuePair> params = new ArrayList<BasicNameValuePair>();
				params.add(new BasicNameValuePair("oauth_token", ((Mykey) getApplication()).getOauth_token()));
				params.add(new BasicNameValuePair("oauth_token_secret", ((Mykey) getApplication()).getOauth_token_secret()));
				params.add(new BasicNameValuePair("user_id", ((Mykey) getApplication()).getUid()));
				params.add(new BasicNameValuePair("fid", ((Mykey) getApplication()).getUid()));
				userinfor_result = ClientUtils.post_str(ClientUtils.BASE_URL + Tab1Activity.this.getString(ClientUtils.User_show), params, Tab1Activity.this);
				Gson gson = new Gson();
				Common.USER_INFOR = gson.fromJson(userinfor_result, User.class);
				Log.d(TAG, "获取到的个人信息:" + userinfor_result);
				((Mykey) getApplication()).setUname(Common.USER_INFOR.getUname());
				((Mykey) getApplication()).setMy_head(Common.USER_INFOR.getAvatar_middle());
				Log.e(TAG, "当前登录用户为:" + Common.USER_INFOR.getUname());
				UserInfor.UID = Common.USER_INFOR.getUid();
				UserInfor.UNAME = Common.USER_INFOR.getUname();
				UserInfor.HEAD_IC = Common.USER_INFOR.getAvatar_middle();
				UserInfor.COUNT_INFOR = Common.USER_INFOR.getCount_info();
				UserInfor.SEX = Common.USER_INFOR.getSex();
				UserInfor.INTRO = Common.USER_INFOR.getIntro();
				UserInfor user = new UserInfor();
				user.setCtime(Common.USER_INFOR.getCtime());
				user.setUid(Common.USER_INFOR.getUid());
				user.setIntro(Common.USER_INFOR.getIntro());
				user.setAvatar_middle(Common.USER_INFOR.getAvatar_middle());
				user.setSex(Common.USER_INFOR.getSex());
				user.setUname(Common.USER_INFOR.getUname());
				helper = new DataHelper(getApplicationContext());
				helper.saveUserinfor(user);
				return null;
			}
			@Override
			protected void onPostExecute(Void resulta) {
				try {
					if (loading2 != null) {
						loading2.dismiss();
					}
				} catch (Exception e) {
					if (loading2 != null) {
						loading2.dismiss();
					}
					FaceConversionUtil.dispToast(Tab1Activity.this, "获取用户信息时发生错误:" + e.toString());
					Log.d(TAG, e.toString());
				}
			}
		}.execute(null, null, null);
	}
	public void listener() {
		// 下拉刷新事件回调
		mListView.setOnRefreshStartListener(new OnStartListener() {
			@Override
			public void onStart() {
				if (code.equals("0")) {
					weibo_list.clear();
					getdata(1);
				} else if (code.equals("1")) {
					weibo_list.clear();
					getpublicweibo(1);
				} else if (code.equals("2")) {
					weibo_list.clear();
					getmyweibo(1);
				}
			}
		});
		// 加载更多事件回调
		mListView.setOnLoadMoreStartListener(new OnStartListener() {
			@Override
			public void onStart() {
				number++;
				if (code.equals("0")) {
					getdata(number);
				} else if (code.equals("1")) {
					getpublicweibo(number);
				} else if (code.equals("2")) {
					getmyweibo(number);
				}
			}
		});
		mListView.setOnItemClickListener(new ZrcListView.OnItemClickListener() {
			@Override
			public void onItemClick(ZrcListView parent, View view, int position, long id) {
				// TODO Auto-generated method stub
				((Mykey) getApplication()).setMymap(weibo_list.get(position));
				Intent intent = new Intent(Tab1Activity.this, WeiBoDetail.class);
				Tab1Activity.this.startActivity(intent);
			}
		});
		title.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				show++;
				int[] location = new int[2];
				title.getLocationInWindow(location);
				int x = location[0];
				int y = location[1] + title.getHeight();
				showPopupWindow(0, y);
			}
		});
		noti.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if (noti.getVisibility() == View.VISIBLE) {
					noti.setVisibility(View.GONE);
				}
			}
		});
	}

	public void iffirst() {
		try {
			if (Utils.iffirst("FirstLoginTab1", Tab1Activity.this)) {
				final Dialog dialog = new Dialog(this, R.style.Dialog_Fullscreen);
				dialog.setContentView(R.layout.first_login_tab1_pic);
				ImageView close = (ImageView) dialog.findViewById(R.id.close);
				close.setOnClickListener(new View.OnClickListener() {
					@Override
					public void onClick(View v) {
						// TODO Auto-generated method stub
						dialog.dismiss();
					}
				});
				dialog.show();
				if (noti.getVisibility() == View.GONE) {
					noti.setVisibility(View.VISIBLE);
				}
			} else {
				Log.d(TAG, "不是第一次进入Tab1Activity");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	ActivityManager am = null;
	ACache mcache;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.weibolist);
		findView();
		iffirst();
		mcache = ACache.get(this);
		soundPool = new SoundPool(10, AudioManager.STREAM_SYSTEM, 5);
		soundPool.load(this, R.raw.newblogtoast, 1);
		loading = new LoadingAlertAnim(Tab1Activity.this, R.style.MyDialogStyle, "正在获取数据,请稍后...");
		loading.setCanceledOnTouchOutside(false);
		loading.show();
		sensorManager = (SensorManager) this.getBaseContext().getSystemService(Context.SENSOR_SERVICE);
		Common.a = Tab1Activity.this;
		listener();
		// 设置默认偏移量，主要用于实现透明标题栏功能。
		FaceConversionUtil.StartListView(this, mListView);
		initView();
		find_loginuser();
		mListView.refresh();
		if (Utils.GetSetting("autodown", Tab1Activity.this)) {
			Intent i = new Intent("com.server.getdate");
			startService(i);
		}
		Utils.getInstance().addActivity(this);
	}

	public void getmyweibo(final int page) {
		new AsyncTask<Void, Void, Void>() {
			protected Void doInBackground(Void... param) {
				List<BasicNameValuePair> params = new ArrayList<BasicNameValuePair>();
				params.add(new BasicNameValuePair("oauth_token", ((Mykey) getApplication()).getOauth_token()));
				params.add(new BasicNameValuePair("oauth_token_secret", ((Mykey) getApplication()).getOauth_token_secret()));
				params.add(new BasicNameValuePair("page", Integer.toString(page)));
				params.add(new BasicNameValuePair("user_id", ((Mykey) getApplication()).getUid()));
				result = ClientUtils.post_str(ClientUtils.BASE_URL + Tab1Activity.this.getString(ClientUtils.WeiboStatuses_get_user_timeline), params, Tab1Activity.this);
				return null;
			}

			@Override
			protected void onPostExecute(Void reult) {
				try {
					Log.d(TAG, result);
					Gson gson = new Gson();
					JSONArray jArray = new JSONArray(result);
					for (int i = 0; i < jArray.length(); i++) {
						weibo_list.add(gson.fromJson(jArray.getString(i), WeiBoCard.class));
					}
					if (loading != null) {
						loading.dismiss();
					}
					mListView.setRefreshSuccess();
					if (jArray.length() >= 19) {
						mListView.startLoadMore();
					} else {
						mListView.stopLoadMore();
					}
					soundPool.play(1, 1, 1, 0, 0, 1);
					adapter.notifyDataSetChanged();
					if (weibo_list.size() > 99) {
						SomeViewCard.bv.setText("99+");
						if (SomeViewCard.bv.getVisibility() == View.GONE) {
							SomeViewCard.bv.show();
						}
					} else if (weibo_list.size() == 0) {
						if (SomeViewCard.bv.getVisibility() == View.VISIBLE) {
							SomeViewCard.bv.setVisibility(View.GONE);
						}
						Log.d(TAG, "数组空，角标不显示？");
					} else {
						SomeViewCard.bv.setText(String.valueOf(weibo_list.size()));
						if (SomeViewCard.bv.getVisibility() == View.GONE) {
							SomeViewCard.bv.show();
						}
					}
				} catch (Exception e) {
					Log.d(TAG, e.toString());
				}
				Log.d(TAG, "jieshu");
			}
		}.execute(null, null, null);
	}

	public void getpublicweibo(final int page) {
		new AsyncTask<Void, Void, Void>() {
			protected Void doInBackground(Void... param) {
				List<BasicNameValuePair> params = new ArrayList<BasicNameValuePair>();
				params.add(new BasicNameValuePair("oauth_token", ((Mykey) getApplication()).getOauth_token()));
				params.add(new BasicNameValuePair("oauth_token_secret", ((Mykey) getApplication()).getOauth_token_secret()));
				params.add(new BasicNameValuePair("user_id", ((Mykey) getApplication()).getUid()));
				params.add(new BasicNameValuePair("page", Integer.toString(page)));
				result = ClientUtils.post_str(ClientUtils.BASE_URL + Tab1Activity.this.getString(ClientUtils.WeiboStatuses_get_public_timeline), params, Tab1Activity.this);
				return null;
			}

			@Override
			protected void onPostExecute(Void resut) {
				try {
					Log.d(TAG, result);
					JSONArray jArray = new JSONArray(result);
					Gson gson = new Gson();
					for (int i = 0; i < jArray.length(); i++) {
						weibo_list.add(gson.fromJson(jArray.getString(i), WeiBoCard.class));
					}
					if (loading != null) {
						loading.dismiss();
					}
					mListView.setRefreshSuccess();
					if (jArray.length() >= 19) {
						mListView.startLoadMore();
					} else {
						mListView.stopLoadMore();
					}
					soundPool.play(1, 1, 1, 0, 0, 1);
					adapter.notifyDataSetChanged();
					if (weibo_list.size() > 99) {
						SomeViewCard.bv.setText("99+");
						if (SomeViewCard.bv.getVisibility() == View.GONE) {
							SomeViewCard.bv.show();
						}
					} else if (weibo_list.size() == 0) {
						if (SomeViewCard.bv.getVisibility() == View.VISIBLE) {
							SomeViewCard.bv.setVisibility(View.GONE);
						}
						Log.d(TAG, "数组空，角标不显示？");
					} else {
						SomeViewCard.bv.setText(String.valueOf(weibo_list.size()));
						if (SomeViewCard.bv.getVisibility() == View.GONE) {
							SomeViewCard.bv.show();
						}
					}
				} catch (Exception e) {
					Log.d(TAG, e.toString());
				}
				Log.d(TAG, "jieshu");
			}
		}.execute(null, null, null);
	}

	public void getdata(final int page) {
		new AsyncTask<Void, Void, Void>() {
			protected Void doInBackground(Void... param) {
				List<BasicNameValuePair> params = new ArrayList<BasicNameValuePair>();
				params.add(new BasicNameValuePair("oauth_token", ((Mykey) getApplication()).getOauth_token()));
				params.add(new BasicNameValuePair("oauth_token_secret", ((Mykey) getApplication()).getOauth_token_secret()));
				params.add(new BasicNameValuePair("page", Integer.toString(page)));
				params.add(new BasicNameValuePair("user_id", ((Mykey) getApplication()).getUid()));
				result = ClientUtils.post_str(ClientUtils.BASE_URL + Tab1Activity.this.getString(ClientUtils.WeiboStatuses_get_friends_timeline), params, Tab1Activity.this);
				return null;
			}

			@Override
			protected void onPostExecute(Void q) {
				try {
					Log.d(TAG, result);
					JSONArray jArray = new JSONArray(result);
					Gson gson = new Gson();
					for (int i = 0; i < jArray.length(); i++) {
						weibo_list.add(gson.fromJson(jArray.getString(i), WeiBoCard.class));
					}
					if (loading != null) {
						loading.dismiss();
					}
					mListView.setRefreshSuccess();
					if (jArray.length() >= 19) {
						mListView.startLoadMore();
					} else {
						mListView.stopLoadMore();
					}
					soundPool.play(1, 1, 1, 0, 0, 1);
					adapter.notifyDataSetChanged();
					if (weibo_list.size() > 99) {
						SomeViewCard.bv.setText("99+");
						if (SomeViewCard.bv.getVisibility() == View.GONE) {
							SomeViewCard.bv.show();
						}
					} else if (weibo_list.size() == 0) {
						if (SomeViewCard.bv.getVisibility() == View.VISIBLE) {
							SomeViewCard.bv.setVisibility(View.GONE);
						}
						Log.d(TAG, "数组空，角标不显示？");
					} else {
						SomeViewCard.bv.setText(String.valueOf(weibo_list.size()));
						if (SomeViewCard.bv.getVisibility() == View.GONE) {
							SomeViewCard.bv.show();
						}
					}
				} catch (Exception e) {
					Log.d(TAG, e.toString());
				}
			}
		}.execute(null, null, null);
	}

	WeiBaSendBtnAdapter sendadapter;

	private void initView() {
		sendadapter = new WeiBaSendBtnAdapter(Tab1Activity.this, titlename, pic);
		weibo_list = new ArrayList<WeiBoCard>();
		adapter = new WeiBoMainAdapter(this, weibo_list);
		mListView.setAdapter(adapter);
	}

	@SuppressLint("NewApi")
	protected void onResume() {
		super.onResume();
		if (((Mykey) getApplication()).getIsdel().equals("1")) {
			code = "0";
			title.setText("我关注的人");
			mListView.setSelection(0);
			mListView.refresh();
			((Mykey) getApplication()).setIsdel("0");
		}
		if (((Mykey) getApplication()).getWeibo_id() != null && ((Mykey) getApplication()).getWeibo_id().length() > 0) {
			code = "0";
			title.setText("我关注的人");
			mListView.setSelection(0);
			mListView.refresh();
			((Mykey) getApplication()).setWeibo_id(null);
		}
		// /返回true表示注册成功，flase则反之
		sensorManager.registerListener(shakeListener, sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER), SensorManager.SENSOR_DELAY_FASTEST);
		showSettings = false;
	}

	@SuppressLint("NewApi")
	protected void onStop() {
		super.onStop();
		// /注消
		sensorManager.unregisterListener(shakeListener);
	}

	@SuppressLint("NewApi")
	protected void onPause() {
		super.onPause();
		// /注消
		sensorManager.unregisterListener(shakeListener);
	}

	public static int i = 0;
	// /SensorManager的监听程序
	@SuppressLint("NewApi")
	private final SensorEventListener shakeListener = new SensorEventListener() {
		// /
		@Override
		public void onSensorChanged(SensorEvent se) {
			float x = se.values[0];
			float y = se.values[1];
			float z = se.values[2];
			float shake = x * x + y * y + z * z;
			if ((!showSettings) && (shake > 800)) {
				showSettings = true;
				i++;
				if (i == 1) {
					Intent i = new Intent(Tab1Activity.this, PublishedActivity.class);
					startActivity(i);
				}
				// 处理结束
				showSettings = false;
			}
		}

		public void onAccuracyChanged(Sensor sensor, int accuracy) {
		}
	};

	public void showPopupWindow(int x, int y) {
		layout = (LinearLayout) LayoutInflater.from(Tab1Activity.this).inflate(R.layout.tab1popwindow, null);
		listView = (ListView) layout.findViewById(R.id.lv_dialog);
		listView.setAdapter(sendadapter);
		listView.setSelection(0);
		popupWindow = new PopupWindow(Tab1Activity.this);
		popupWindow.setBackgroundDrawable(new BitmapDrawable());
		popupWindow.setWidth(getWindowManager().getDefaultDisplay().getWidth() / 2);
		popupWindow.setHeight(300);
		popupWindow.setOutsideTouchable(true);
		popupWindow.setFocusable(true);
		popupWindow.setContentView(layout);
		popupWindow.showAtLocation(findViewById(R.id.titleText), Gravity.TOP | Gravity.CENTER_HORIZONTAL | Gravity.CENTER_VERTICAL, x, y);
		listView.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
				title.setText(titlename[arg2]);
				popupWindow.dismiss();
				popupWindow = null;
				((TextView) arg0.findViewById(R.id.text)).setSelected(true);
				if (title.getText().equals("公共微博")) {
					code = "1";
					mListView.setSelection(0);
					mListView.refresh();
				} else if (title.getText().equals("我关注的人")) {
					code = "0";
					mListView.setSelection(0);
					mListView.refresh();
				} else if (title.getText().equals("我发布的微博")) {
					code = "2";
					mListView.setSelection(0);
					mListView.refresh();
				} else {
					Log.d(TAG, "不知道你选了什么玩意！");
				}
			}
		});
	}
}
