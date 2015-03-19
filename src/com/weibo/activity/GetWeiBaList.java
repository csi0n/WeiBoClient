package com.weibo.activity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONObject;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.content.Intent.ShortcutIconResource;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.AdapterView.OnItemClickListener;

import com.weibo.adapters.WeiBaMainAdapter;
import com.weibo.adapters.WeiBaSendBtnAdapter;
import com.weibo.application.*;
import com.weibo.lib.zrclist.ZrcListView;
import com.weibo.lib.zrclist.ZrcListView.OnStartListener;
import com.weibo.utils.ClientUtils;
import com.weibo.utils.FaceConversionUtil;
import com.weibo.utils.Utils;
import com.weibo.view.widget.LoadingAlertAnim;
import com.weibo.R;
/**
 * 
 * 获取微吧列表
 * 
 */
@SuppressLint("ResourceAsColor")
public class GetWeiBaList extends BaseActivity {
	ZrcListView xlistview;
	ImageView imageview1, imageview3;
	Button button1, button2, button3;
	ImageView cutch;
	LinearLayout line;
	HashMap<String, Object> map;
	ArrayList<HashMap<String, Object>> listitem;
	WeiBaMainAdapter adapter;
	TextView title;
	int listitemdatasize = 0;
	int number = 1;
	LoadingAlertAnim loading, loading2;
	private static final String TAG = "GetWeiBaList.java";
	int count = 0;
	ImageButton more;
	protected static final int change = 0;
	protected static final int end = 1;
	private Handler handler = new Handler() {
		public void handleMessage(Message msg) {
			switch (msg.what) {
				case change :
					if (count > 19) {
						xlistview.startLoadMore();
					} else {
						xlistview.stopLoadMore();
					}
					if (loading2 != null) {
						loading2.dismiss();
					}
					xlistview.setRefreshSuccess("刷新成功，共刷新了" + count + "条信息!");
					adapter.notifyDataSetChanged();
					break;
				case end :

					xlistview.setLoadMoreSuccess();
					break;
			}
		}
	};

	protected void onResume() {
		super.onResume();
		// /返回true表示注册成功，flase则反之
		if (((Mykey) getApplication()).getRefresh().equals("1")) {
			xlistview.setSelection(0);
			xlistview.refresh();
			((Mykey) getApplication()).setRefresh("0");
		} else {
			Log.d(TAG, "没有检测到@我的好友!");
		}
		Log.d(TAG, "Back");
	}

	public void findview() {
		xlistview = (ZrcListView) findViewById(R.id.xListView1);
		imageview1 = (ImageView) findViewById(R.id.example_right);
		imageview3 = (ImageView) findViewById(R.id.imageView2);
		title = (TextView) findViewById(R.id.titleText);
		more = (ImageButton) findViewById(R.id.more);
	}

	public void listen() {
		imageview1.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				GetWeiBaList.this.finish();
			}
		});
		imageview3.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				int[] location = new int[2];
				imageview3.getLocationInWindow(location);
				int x = location[0];
				int y = location[1] + imageview3.getHeight();
				showPopupWindow(x, y);
			}
		});
		xlistview.setOnRefreshStartListener(new OnStartListener() {
			@Override
			public void onStart() {
				// TODO Auto-generated method stub
				new Thread(new Runnable() {
					@Override
					public void run() {
						// TODO Auto-generated method stub
						listitem.clear();
						get(1);
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
						get(number);
						Message message = new Message();
						message.what = end;
						handler.sendMessage(message);
					}
				}).start();
			}
		});
		more.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				xlistview.setSelection(0);
				xlistview.refresh();
			}
		});
	}

	private void initView() {
		sendadapter = new WeiBaSendBtnAdapter(GetWeiBaList.this, titlename, pic);
		listitem = new ArrayList<HashMap<String, Object>>();
		adapter = new WeiBaMainAdapter(this, listitem, b);
		xlistview.setAdapter(adapter);
		listitem.clear();
		adapter.notifyDataSetChanged();
	}

	public void get(int page) {
		try {
			List<BasicNameValuePair> params = new ArrayList<BasicNameValuePair>();
			params.add(new BasicNameValuePair("id", b.getString("id")));
			params.add(new BasicNameValuePair("oauth_token", ((Mykey) getApplication()).getOauth_token()));
			params.add(new BasicNameValuePair("oauth_token_secret", ((Mykey) getApplication()).getOauth_token_secret()));
			params.add(new BasicNameValuePair("page", Integer.toString(page)));
			params.add(new BasicNameValuePair("user_id", ((Mykey) getApplication()).getUid()));
			String result = ClientUtils.post_str(ClientUtils.BASE_URL + GetWeiBaList.this.getString(ClientUtils.Weiba_get_posts), params, GetWeiBaList.this);
			JSONArray jArray = new JSONArray(result);
			for (int i = 0; i < jArray.length(); i++) {
				JSONObject temp = (JSONObject) jArray.get(i);
				map = new HashMap<String, Object>();
				map.put("post_id", temp.getString("post_id"));
				Log.d(TAG, temp.getString("post_id"));
				map.put("weiba_id", temp.getString("weiba_id"));
				map.put("title", temp.getString("title"));
				map.put("content", temp.getString("content"));
				map.put("author_info", temp.getString("author_info"));
				map.put("read_count", temp.getString("read_count"));
				map.put("reply_count", temp.getString("reply_count"));
				map.put("post_time", temp.getString("post_time"));
				map.put("favorite", temp.getString("favorite"));
				listitem.add(map);
			}
			count = jArray.length();
			Message message = new Message();
			message.what = change;
			handler.sendMessage(message);
		} catch (Exception e) {
			Log.d(TAG, e.toString());
		}
	}

	Bundle b = null;
	LinearLayout head = null;

	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.weiba_list_layout);
		findview();
		b = getIntent().getExtras();
		title.setText(b.getString("weiba_name"));
		FaceConversionUtil.StartListView(this, xlistview);
		loading2 = new LoadingAlertAnim(GetWeiBaList.this, R.style.MyDialogStyle, "正在获取数据请稍后...");
		loading2.setCanceledOnTouchOutside(false);
		loading2.show();
		listen();
		initView();
		xlistview.refresh();
		Utils.getInstance().addActivity(this);
	}

	private void addShortCut(String tName) {
		// 安装的Intent
		Intent shortcut = new Intent("com.android.launcher.action.INSTALL_SHORTCUT");
		// 快捷名称
		shortcut.putExtra(Intent.EXTRA_SHORTCUT_NAME, tName);
		// 快捷图标是允许重复
		shortcut.putExtra("duplicate", false);
		Intent shortcutIntent = new Intent(Intent.ACTION_MAIN);
		shortcutIntent.putExtra("tName", tName);
		shortcutIntent.putExtras(b);
		shortcutIntent.setClassName("com.weibo", ".activity.GetWeiBaList");
		shortcutIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
		shortcut.putExtra(Intent.EXTRA_SHORTCUT_INTENT, shortcutIntent);
		// 快捷图标
		ShortcutIconResource iconRes = Intent.ShortcutIconResource.fromContext(this, R.drawable.logo);
		shortcut.putExtra(Intent.EXTRA_SHORTCUT_ICON_RESOURCE, iconRes);
		// 发送广播
		sendBroadcast(shortcut);
	}

	String titlename[] = {"发表", "添加至桌面", "本吧信息"};
	int pic[] = {R.drawable.img_edit_add, R.drawable.img_edit_send, R.drawable.img_musiccircle_comment};
	private PopupWindow popupWindow;
	private LinearLayout layout;
	private ListView listView;
	WeiBaSendBtnAdapter sendadapter;

	public void showPopupWindow(int x, int y) {
		ListAdapter a = new ArrayAdapter<String>(GetWeiBaList.this, R.layout.text, R.id.tv_text, titlename);
		layout = (LinearLayout) LayoutInflater.from(GetWeiBaList.this).inflate(R.layout.tab1popwindow, null);
		listView = (ListView) layout.findViewById(R.id.lv_dialog);
		listView.setAdapter(sendadapter);
		listView.setSelection(0);
		popupWindow = new PopupWindow(GetWeiBaList.this);
		popupWindow.setBackgroundDrawable(new BitmapDrawable());
		popupWindow.setWidth(getWindowManager().getDefaultDisplay().getWidth() / 2);
		popupWindow.setHeight(300);
		popupWindow.setOutsideTouchable(true);
		popupWindow.setFocusable(true);
		popupWindow.setContentView(layout);
		popupWindow.showAtLocation(findViewById(R.id.imageView2), Gravity.TOP, x, y);
		listView.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
				popupWindow.dismiss();
				popupWindow = null;
				if (titlename[arg2].equals("发表")) {
					Intent intent = new Intent(GetWeiBaList.this, ReportNewWeiBa.class);
					intent.putExtra("id", b.getString("id"));
					GetWeiBaList.this.startActivity(intent);
				} else if (titlename[arg2].equals("添加至桌面")) {
					addShortCut(b.getString("weiba_name"));
				} else if (titlename[arg2].equals("本吧信息")) {
					Intent a = new Intent(GetWeiBaList.this, WeiBaInfor.class);
					a.putExtras(b);
					startActivity(a);
				}
			}
		});
	}
}
