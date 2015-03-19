/**weibotest
 * 
 *
 *TODO
 */
package com.weibo.activity;
import com.weibo.adapters.MorePicPagerAdapter;
import com.weibo.utils.ImageArrayList;

import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.WindowManager;
import android.widget.TextView;
import com.weibo.R;
/**
 * @作者:陈华清
 * 
 * @版本:1.0
 * @生成时期:2014年7月25日 下午12:14:14
 * @com.api.example.app
 */
public class ShowWeiBoPic extends BaseActivity {
	ViewPager mViewPager;
	MorePicPagerAdapter mAdapter;
	TextView curr, count;
	private int window_width, window_height;
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.show_weibo_pic);
		WindowManager manager = getWindowManager();
		window_width = manager.getDefaultDisplay().getWidth();
		window_height = manager.getDefaultDisplay().getHeight();
		mViewPager = (ViewPager) findViewById(R.id.pager);
		curr = (TextView) findViewById(R.id.curr);
		curr.setText(String.valueOf(ImageArrayList.PICCURRENT + 1));
		count = (TextView) findViewById(R.id.count);
		count.setText(String.valueOf(ImageArrayList.getList().size()));
		mAdapter = new MorePicPagerAdapter(this, ImageArrayList.getList(),window_width,window_height);
		mViewPager.setAdapter(mAdapter);
		mViewPager.setCurrentItem(ImageArrayList.PICCURRENT);
		mViewPager.setOnPageChangeListener(new OnPageChangeListener() {
			@Override
			public void onPageSelected(int arg0) {
				// TODO Auto-generated method stub
				curr.setText(String.valueOf(arg0+1));
			}

			@Override
			public void onPageScrolled(int arg0, float arg1, int arg2) {
				// TODO Auto-generated method stub
			}

			@Override
			public void onPageScrollStateChanged(int arg0) {
				// TODO Auto-generated method stub

			}
		});
	}
}
