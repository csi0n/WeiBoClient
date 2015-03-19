/**weibotest
 * 
 *
 *TODO
 */
package com.weibo.fragment;

import java.util.ArrayList;
import java.util.List;

import com.weibo.R;
import com.weibo.utils.DialogUtils;
import com.weibo.utils.DialogUtils.DialogCallBack;
import com.weibo.utils.Utils;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.KeyEvent;

/**
 * @作者:陈华清
 * 
 * @版本:1.0
 * @生成时期:2014年8月16日 下午12:33:47
 * @com.tab.newactivity
 */
public class MessageFragmentActivity extends FragmentActivity {
	/** 页面list **/
	List<Fragment> fragmentList = new ArrayList<Fragment>();
	/** 页面title list **/
	List<String> titleList = new ArrayList<String>();
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK) {
			DialogUtils.dialogBuilder(MessageFragmentActivity.this, "提示", "确定要退出？",
					new DialogCallBack() {
						@Override
						public void callBack() {
							Utils.getInstance().exit(MessageFragmentActivity.this);
						}
					});
		}
		return super.onKeyDown(keyCode, event);
	}
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		Utils.onActivityCreateSetTheme(this);
		super.onCreate(savedInstanceState);
		setContentView(R.layout.messagefragmentactivity_layout);
		ViewPager vp = (ViewPager) findViewById(R.id.viewPager);
		fragmentList.add(new AtMeFramgent());
		fragmentList.add(new MeToCommentFragment());
		fragmentList.add(new CommentToMeFragment());
		fragmentList.add(new PersonLetterFragment());
		titleList.add("@我");
		titleList.add("我评论的");
		titleList.add("评论我的");
		titleList.add("私信");
		vp.setAdapter(new myPagerAdapter(this.getSupportFragmentManager(),
				fragmentList, titleList));
	}
	/**
	 * 定义适配器
	 * 
	 * @author 陈华清
	 */
	class myPagerAdapter extends FragmentPagerAdapter {
		private List<Fragment> fragmentList;
		private List<String> titleList;
		public myPagerAdapter(FragmentManager fm, List<Fragment> fragmentList,
				List<String> titleList) {
			super(fm);
			this.fragmentList = fragmentList;
			this.titleList = titleList;
		}
		/**
		 * 得到每个页面
		 */
		@Override
		public Fragment getItem(int arg0) {
			return (fragmentList == null || fragmentList.size() == 0) ? null
					: fragmentList.get(arg0);
		}

		/**
		 * 每个页面的title
		 */
		@Override
		public CharSequence getPageTitle(int position) {
			return (titleList.size() > position) ? titleList.get(position) : "";
		}
		/**
		 * 页面的总个数
		 */
		@Override
		public int getCount() {
			return fragmentList == null ? 0 : fragmentList.size();
		}
	}
}
