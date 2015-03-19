/**weibotest
 * 
 *
 *TODO
 */
package com.weibo.adapters;
import java.util.List;

import android.support.v4.view.ViewPager;
import android.app.Activity;

import com.loopj.android.image.SmartImageView;
import com.weibo.R;
import com.weibo.view.widget.DragImageView;

import android.content.Context;
import android.graphics.Rect;
import android.support.v4.view.PagerAdapter;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.view.ViewTreeObserver.OnGlobalLayoutListener;

/**
 * @作者:陈华清
 * 
 * @版本:1.0
 * @生成时期:2014年7月25日 下午12:08:24
 * @com.api
 */
public class MorePicPagerAdapter extends PagerAdapter {
	private List<String> mPaths;
	private Context mContext;
	private static final String TAG = "MorePicPagerAdapter";
	LayoutInflater inflater;
	int window_width;
	int window_height;

	public MorePicPagerAdapter(Context context, List<String> mPaths,
			int window_width, int window_height) {
		this.mPaths = mPaths;
		this.mContext = context;
		this.window_height =window_height;
		this.window_width=window_width;
		inflater = LayoutInflater.from(mContext);
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return mPaths.size();
	}

	@Override
	public boolean isViewFromObject(View arg0, Object arg1) {
		// TODO Auto-generated method stub
		return arg0 == (View) arg1;
	}

	@Override
	public Object instantiateItem(ViewGroup container, int position) {
		ViewHolder viewholder = null;
		viewholder = new ViewHolder();
		View view = inflater.inflate(R.layout.more_pic_adapter_item, null);
		viewholder.iv = (DragImageView) view.findViewById(R.id.image);
		try {
			viewholder.iv.setImageUrl(mPaths.get(position));
			Log.d(TAG, mPaths.get(position));
		} catch (OutOfMemoryError e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		addListener(view, position);
		((ViewPager) container).addView(view, 0);
		return view;
	}
	private int state_height;
	private ViewTreeObserver viewTreeObserver;
	public void addListener(View v, int postion) {
		final DragImageView image = (DragImageView) v.findViewById(R.id.image);
		image.setmActivity((Activity)mContext);
		viewTreeObserver = image.getViewTreeObserver();
		viewTreeObserver
				.addOnGlobalLayoutListener(new OnGlobalLayoutListener() {
					@Override
					public void onGlobalLayout() {
						if (state_height == 0) {
							Rect frame = new Rect();
							((Activity) mContext).getWindow().getDecorView()
									.getWindowVisibleDisplayFrame(frame);
							state_height = frame.top;
							image.setScreen_H(window_height - state_height);
							image.setScreen_W(window_width);
						}
					}
				});
	}

	@Override
	public void destroyItem(ViewGroup container, int position, Object object) {
		container.removeView((View) object);
	}

	public class ViewHolder {
		DragImageView iv;
	}

}
