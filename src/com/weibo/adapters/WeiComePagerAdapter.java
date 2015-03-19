/**weibotest
 * 
 *
 *TODO
 */
package com.weibo.adapters;

import java.util.ArrayList;

import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;

/**
 * @作者:陈华清
 * 
 * @版本:1.0
 * @生成时期:2014年7月28日 下午6:52:20
 * @com.api
 */
public class WeiComePagerAdapter extends PagerAdapter {
	private ArrayList<View> views;
	public WeiComePagerAdapter(ArrayList<View> views) {
		this.views = views;
	}
	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		if (views != null) {
			return views.size();
		}
		return 0;
	}
	@Override
	public Object instantiateItem(View view, int position) {
		((ViewPager) view).addView(views.get(position), 0);
		return views.get(position);
	}

	@Override
	public boolean isViewFromObject(View view, Object arg1) {
		return (view == arg1);
	}
	@Override
	public void destroyItem(View view, int position, Object arg2) {
		((ViewPager) view).removeView(views.get(position));
	}

}
