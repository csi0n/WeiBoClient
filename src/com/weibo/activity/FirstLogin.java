package com.weibo.activity;
import java.util.ArrayList;

import com.weibo.adapters.WeiComePagerAdapter;
import com.weibo.utils.Utils;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.Button;
import android.widget.ImageView;
import com.weibo.R;
/**
 * @作者:陈华清
 * 
 * @版本:1.0
 * @生成时期:2014年7月28日 下午6:56:27
 * @com.example.weibotest
 */
public class FirstLogin extends BaseActivity implements OnPageChangeListener,
		View.OnClickListener {
	WeiComePagerAdapter adapter;
	private View view1, view2, view3, view4;
	private ViewPager viewPager;
	Button login, public_weibo;
	ArrayList<View> views;
	ImageView[] image;
	ImageView imageview;
	ViewGroup group;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.welcome);
		findView();
		initData();
		adapter.notifyDataSetChanged();
		Utils.getInstance().addActivity(this);
	}

	private void findView() {
		LayoutInflater mLi = LayoutInflater.from(this);
		view1 = mLi.inflate(R.layout.welcome_pic1, null);
		ImageView i = (ImageView) view1.findViewById(R.id.imageView1);
		i.setImageDrawable(FirstLogin.this.getResources().getDrawable(
				R.drawable.intro01));
		view2 = mLi.inflate(R.layout.welcome_pic2, null);
		ImageView ii = (ImageView) view2.findViewById(R.id.imageView1);
		ii.setImageDrawable(FirstLogin.this.getResources().getDrawable(
				R.drawable.intro02));
		view3 = mLi.inflate(R.layout.welcome_pic3, null);
		login = (Button) view3.findViewById(R.id.login);
		login.setOnClickListener(this);
		public_weibo = (Button) view3.findViewById(R.id.public_weibo);
		public_weibo.setOnClickListener(this);
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
		image = new ImageView[views.size()];
		for (int i = 0; i < views.size(); i++) {
			imageview = new ImageView(this);
			imageview.setLayoutParams(new LayoutParams(20, 20));
			imageview.setPadding(0, 0, 12, 0);
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
		switch (v.getId()) {
		case R.id.login:
			Intent q = new Intent(this, MainActivity.class);
			FirstLogin.this.startActivity(q);
			break;
		case R.id.public_weibo:
			Intent i = new Intent(FirstLogin.this, LookPublicWeiBo.class);
			startActivity(i);
			break;
		}
	}
}
