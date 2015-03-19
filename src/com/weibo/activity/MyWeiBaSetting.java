/**weibotest
 * 
 *
 *TODO
 */
package com.weibo.activity;
import com.weibo.fragment.FollowWeiBa;
import com.weibo.fragment.MenuFragment;
import com.weibo.lib.slidingmenu.SlidingFragmentActivity;
import com.weibo.lib.slidingmenu.SlidingMenu;
import com.weibo.utils.Utils;

import android.app.Activity;
import android.content.Context;
import android.graphics.Canvas;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageButton;
import android.widget.ImageView;

import com.weibo.R;
/**
 * @作者:陈华清
 * 
 * @版本:1.0
 * @生成时期:2014年7月27日 下午6:12:03
 * @com.api.example.app
 */
public class MyWeiBaSetting extends SlidingFragmentActivity implements OnClickListener {
	SlidingMenu menu;
	Fragment mContent;
	ImageButton leftlist;
	ImageView close;
	protected SlidingMenu leftRightSlidingMenu;
	@Override
	public void onCreate(Bundle savedInstanceState) {
		Utils.onActivityCreateSetTheme(this);
		super.onCreate(savedInstanceState);
		initLeftRightSlidingMenu();
		setContentView(R.layout.my_weiba_setting_layout);
		init();
	}
	public void init() {
		close = (ImageView) findViewById(R.id.close);
		close.setOnClickListener(this);
		leftlist = (ImageButton) findViewById(R.id.leftlist_btn);
		leftlist.setOnClickListener(this);
	}
	private void initLeftRightSlidingMenu() {
		mContent = new FollowWeiBa();
		getSupportFragmentManager().beginTransaction().replace(R.id.frame, mContent).commit();
		setBehindContentView(R.layout.main_left_layout);
		FragmentTransaction leftFragementTransaction = getSupportFragmentManager().beginTransaction();
		Fragment leftFrag = new MenuFragment();
		leftFragementTransaction.replace(R.id.main_left_fragment, leftFrag);
		leftFragementTransaction.commit();
		// customize the SlidingMenu
		leftRightSlidingMenu = getSlidingMenu();
		// leftRightSlidingMenu.setMode(SlidingMenu.LEFT_RIGHT);//
		// 设置是左滑还是右滑，还是左右都可以滑，我这里只做了左滑
		leftRightSlidingMenu.setMode(SlidingMenu.LEFT);
		leftRightSlidingMenu.setBehindOffsetRes(R.dimen.slidingmenu_offset);// 设置菜单宽度
		leftRightSlidingMenu.setFadeDegree(0.25f);// 设置淡入淡出的比例
		leftRightSlidingMenu.setTouchModeAbove(SlidingMenu.TOUCHMODE_FULLSCREEN);// 设置手势模式
		leftRightSlidingMenu.setShadowDrawable(R.drawable.shadow);// 设置左菜单阴影图片
		leftRightSlidingMenu.setFadeEnabled(true);// 设置滑动时菜单的是否淡入淡出
		leftRightSlidingMenu.setBehindScrollScale(0.333f);// 设置滑动时拖拽效果
		leftRightSlidingMenu.setBackgroundImage(R.drawable.back1);
		leftRightSlidingMenu.setBehindCanvasTransformer(new SlidingMenu.CanvasTransformer() {
			@Override
			public void transformCanvas(Canvas canvas, float percentOpen) {
				float scale = (float) (percentOpen * 0.25 + 0.75);
				canvas.scale(scale, scale, -canvas.getWidth() / 2, canvas.getHeight() / 2);
			}
		});
		leftRightSlidingMenu.setAboveCanvasTransformer(new SlidingMenu.CanvasTransformer() {
			@Override
			public void transformCanvas(Canvas canvas, float percentOpen) {
				float scale = (float) (1 - percentOpen * 0.25);
				canvas.scale(scale, scale, 0, canvas.getHeight() / 2);
			}
		});
	}

	/**
	 * 左侧菜单点击切换首页的内容
	 */
	public void switchContent(Fragment fragment) {
		mContent = fragment;
		getSupportFragmentManager().beginTransaction().replace(R.id.frame, fragment).commit();
		getSlidingMenu().showContent();
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
			case R.id.leftlist_btn :
				leftRightSlidingMenu.showMenu();
				break;
			case R.id.close :
				finish();
				break;
			default :
				break;
		}
	}

}
