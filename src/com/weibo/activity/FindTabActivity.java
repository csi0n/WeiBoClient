/**weibotest
 * 
 *
 *TODO
 */
package com.weibo.activity;

import java.util.ArrayList;
import com.weibo.R;

import java.util.HashMap;
import java.util.List;

import org.json.JSONObject;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Message;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.loopj.android.image.SmartImageView;
import com.weibo.adapters.ChannelAdapter;
import com.weibo.adapters.WeiComePagerAdapter;
import com.weibo.db.DataHelper;
import com.weibo.utils.DialogUtils;
import com.weibo.utils.DialogUtils.DialogCallBack;
import com.weibo.utils.Utils;
import com.zbar.lib.CaptureActivity;
/**
 * @作者:陈华清
 * 
 * @版本:1.0
 * @生成时期:2014年8月15日 下午7:19:31
 * @com.tab.newactivity
 */
public class FindTabActivity extends BaseActivity implements OnPageChangeListener, OnClickListener {
	private static final String TAG = "FindTabActivity";
	View view1, view2, view3, view4;
	ViewPager viewPager;
	ViewGroup group;
	ArrayList<View> views;
	WeiComePagerAdapter adapter;
	ChannelAdapter cadapter;
	ImageView[] image;
	ImageView imageview;
	ListView list;
	TextView weibosearchcachecount, usersearchcachecount;
	DataHelper helper;
	RelativeLayout weibosearch, usersearch;
	LinearLayout nearperson, saoyisao, weiba, channel, weather;
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK) {
			DialogUtils.dialogBuilder(FindTabActivity.this, "提示", "确定要退出？", new DialogCallBack() {
				@Override
				public void callBack() {
					Utils.getInstance().exit(FindTabActivity.this);
				}
			});
		}
		return super.onKeyDown(keyCode, event);
	}
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.findtabactivity_layout);
		findView();
		initData();
		adapter.notifyDataSetChanged();
		Utils.getInstance().addActivity(this);
	}
	private void findView() {
		weather = (LinearLayout) findViewById(R.id.weather);
		weather.setOnClickListener(this);
		helper = new DataHelper(FindTabActivity.this.getApplicationContext());
		weibosearchcachecount = (TextView) findViewById(R.id.weibosearchcachecount);
		weibosearchcachecount.setText(String.valueOf("共有" + helper.getweibosearchcount() + "条记录"));
		usersearchcachecount = (TextView) findViewById(R.id.usersearchcachecount);
		usersearchcachecount.setText(String.valueOf("共有" + helper.getusersearchcount() + "条记录"));
		nearperson = (LinearLayout) findViewById(R.id.nearperson);
		nearperson.setOnClickListener(this);
		saoyisao = (LinearLayout) findViewById(R.id.saoyisao);
		saoyisao.setOnClickListener(this);
		weiba = (LinearLayout) findViewById(R.id.weiba);
		weiba.setOnClickListener(this);
		channel = (LinearLayout) findViewById(R.id.channel);
		channel.setOnClickListener(this);
		weibosearch = (RelativeLayout) findViewById(R.id.weibosearch);
		weibosearch.setOnClickListener(this);
		usersearch = (RelativeLayout) findViewById(R.id.usersearch);
		usersearch.setOnClickListener(this);
		list = (ListView) findViewById(R.id.list);
		LayoutInflater mLi = LayoutInflater.from(this);
		view1 = mLi.inflate(R.layout.findtab_v4_item_layout, null);
		SmartImageView i1 = (SmartImageView) view1.findViewById(R.id.pic);
		i1.setImageDrawable(this.getResources().getDrawable(R.drawable.r1));
		view2 = mLi.inflate(R.layout.findtab_v4_item_layout, null);
		SmartImageView i2 = (SmartImageView) view2.findViewById(R.id.pic);
		i2.setImageDrawable(this.getResources().getDrawable(R.drawable.r2));
		view3 = mLi.inflate(R.layout.findtab_v4_item_layout, null);
		SmartImageView i3 = (SmartImageView) view3.findViewById(R.id.pic);
		i3.setImageDrawable(this.getResources().getDrawable(R.drawable.r3));
		view4 = mLi.inflate(R.layout.findtab_v4_item_layout, null);
		SmartImageView i4 = (SmartImageView) view4.findViewById(R.id.pic);
		i4.setImageDrawable(this.getResources().getDrawable(R.drawable.r4));
		viewPager = (ViewPager) findViewById(R.id.viewpager);
		group = (ViewGroup) findViewById(R.id.viewGroup);
		views = new ArrayList<View>();
		adapter = new WeiComePagerAdapter(views);
	}
	private void initData() {
		viewPager.setOnPageChangeListener(this);
		viewPager.setAdapter(adapter);
		views.add(view1);
		views.add(view2);
		views.add(view3);
		views.add(view4);
		image = new ImageView[views.size()];
		for (int i = 0; i < views.size(); i++) {
			imageview = new ImageView(this);
			imageview.setLayoutParams(new LayoutParams(15, 15));
			imageview.setPadding(90, 0, 90, 0);
			image[i] = imageview;
			if (i == 0) {
				image[i].setBackgroundResource(R.drawable.point01);
			} else {
				image[i].setBackgroundResource(R.drawable.point02);
			}
			group.addView(image[i]);
		}
	}

	@Override
	public void onPageScrollStateChanged(int arg0) {
		// TODO Auto-generated method stub
	}

	@Override
	public void onPageScrolled(int arg0, float arg1, int arg2) {
		// TODO Auto-generated method stub
	}

	@Override
	public void onPageSelected(int arg0) {
		// TODO Auto-generated method stub
		for (int i = 0; i < image.length; i++) {
			image[arg0].setBackgroundResource(R.drawable.point01);
			if (arg0 != i) {
				image[i].setBackgroundResource(R.drawable.point02);
			}
		}
	}
	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		if (v == weibosearch) {
			Intent inten = new Intent(FindTabActivity.this, Search.class);
			inten.putExtra("a", "2");
			FindTabActivity.this.startActivity(inten);
		} else if (v == usersearch) {
			Intent inten = new Intent(FindTabActivity.this, Search.class);
			inten.putExtra("a", "1");
			FindTabActivity.this.startActivity(inten);
		} else if (v == nearperson) {
			Intent i = new Intent(FindTabActivity.this, NearByPerson.class);
			FindTabActivity.this.startActivity(i);
		} else if (v == saoyisao) {
			Intent i = new Intent(FindTabActivity.this, CaptureActivity.class);
			FindTabActivity.this.startActivity(i);
		} else if (v == weiba) {
			Intent ineten = new Intent(FindTabActivity.this, WeiBaList.class);
			FindTabActivity.this.startActivity(ineten);
		} else if (v == channel) {
			Intent intent = new Intent(FindTabActivity.this, ChannelList.class);
			FindTabActivity.this.startActivity(intent);
		} else if (v == weather) {
			Bundle b = Utils.getweather(FindTabActivity.this);
			if (b.getString("city").equals("0")) {
				Intent i = new Intent(FindTabActivity.this, WeatherListMain.class);
				FindTabActivity.this.startActivity(i);
			} else {
				Intent i = new Intent(FindTabActivity.this, WeatherDetail.class);
				i.putExtra("city", b.getString("city"));
				i.putExtra("code", b.getString("code"));
				FindTabActivity.this.startActivity(i);
			}
		}
	}
}
