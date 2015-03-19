package com.weibo.activity;

import com.weibo.card.SomeViewCard;import com.weibo.R;
import com.weibo.fragment.MessageFragmentActivity;
import com.weibo.notification.client.ServiceManager;
import com.weibo.utils.PublicTimeWeiBoAuthorize;
import com.weibo.utils.SwitchAnimationUtil;
import com.weibo.utils.Utils;
import com.weibo.view.widget.BadgeView;

import android.annotation.SuppressLint;
import android.app.TabActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TabHost;
/**
 * @作者:陈华清
 * 
 * @版本:1.0
 * @生成时期:2014年7月18日 下午10:33:56
 * @com.example.weibotest
 */
@SuppressLint("NewApi")
public class WeiBoMainTab extends TabActivity implements View.OnClickListener {
	TabHost tabHost;
	private Button main_tab_home, main_tab_message, main_tab_self,
			main_tab_search;
	private ImageView send;
	@SuppressWarnings("deprecation")
	public void onCreate(Bundle savedInstanceState) {
		Utils.onActivityCreateSetTheme(this);
		super.onCreate(savedInstanceState);
		setContentView(R.layout.tab_main_layout);
		new SwitchAnimationUtil().startAnimation(getWindow().getDecorView(),
				Utils.getAnimationType(WeiBoMainTab.this));
		PublicTimeWeiBoAuthorize.isLogin = true;
		initTab();
		init();
		Utils.getInstance().addActivity(this);
	}
	public void init() {
		send=(ImageView)findViewById(R.id.send);
		send.setOnClickListener(this);
		main_tab_home = (Button) findViewById(R.id.main_tab_home);
		BadgeView bv = new BadgeView(this, main_tab_home);
		bv.setTextSize(10);
		bv.setBadgeBackgroundColor(0xfff45f5f);
		SomeViewCard.bv = bv;
		main_tab_message = (Button) findViewById(R.id.main_tab_message);
		main_tab_self = (Button) findViewById(R.id.main_tab_self);
		main_tab_search = (Button) findViewById(R.id.main_tab_search);
		main_tab_home.setOnClickListener(this);
		main_tab_message.setOnClickListener(this);
		main_tab_self.setOnClickListener(this);
		main_tab_search.setOnClickListener(this);
	
	}

	public void initTab() {
		tabHost = getTabHost();
		tabHost.addTab(tabHost.newTabSpec("home").setIndicator("home")
				.setContent(new Intent(this, Tab1Activity.class)));
		//tabHost.addTab(tabHost.newTabSpec("message").setIndicator("message")
				//.setContent(new Intent(this, Tab2Activity.class)));
		tabHost.addTab(tabHost.newTabSpec("message").setIndicator("message")
				.setContent(new Intent(this, MessageFragmentActivity.class)));
		// tabHost.addTab(tabHost.newTabSpec("self").setIndicator("self")
		// .setContent(new Intent(this, Tab3Activity.class)));
		tabHost.addTab(tabHost.newTabSpec("self").setIndicator("self")
				.setContent(new Intent(this, UserTabActivity.class)));
		// tabHost.addTab(tabHost.newTabSpec("search").setIndicator("search")
		// .setContent(new Intent(this, Tab4Activity.class)));
		tabHost.addTab(tabHost.newTabSpec("search").setIndicator("search")
				.setContent(new Intent(this, FindTabActivity.class)));
		tabHost.addTab(tabHost.newTabSpec("more").setIndicator("more")
				.setContent(new Intent(this, Tab5Activity.class)));
	}
	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.send:
			Intent intent=new Intent();
			intent.setClass(this, DialogSendNewWeiBo.class);
			startActivity(intent);
			overridePendingTransition(R.anim.dialog_enter, 0);
			break;
		case R.id.main_tab_home:
			main_tab_home.setCompoundDrawablesWithIntrinsicBounds(0,
					R.drawable.icon_home_d, 0, 0);
			main_tab_message.setCompoundDrawablesWithIntrinsicBounds(0,
					R.drawable.icon_meassage, 0, 0);
			main_tab_self.setCompoundDrawablesWithIntrinsicBounds(0,
					R.drawable.icon_selfinfo, 0, 0);
			main_tab_search.setCompoundDrawablesWithIntrinsicBounds(0,
					R.drawable.icon_square, 0, 0);
			tabHost.setCurrentTabByTag("home");
			break;
		case R.id.main_tab_message:
			main_tab_home.setCompoundDrawablesWithIntrinsicBounds(0,
					R.drawable.icon_home, 0, 0);
			main_tab_message.setCompoundDrawablesWithIntrinsicBounds(0,
					R.drawable.icon_message_d, 0, 0);
			main_tab_self.setCompoundDrawablesWithIntrinsicBounds(0,
					R.drawable.icon_selfinfo, 0, 0);
			main_tab_search.setCompoundDrawablesWithIntrinsicBounds(0,
					R.drawable.icon_square, 0, 0);
			tabHost.setCurrentTabByTag("message");
			break;
		case R.id.main_tab_self:
			main_tab_home.setCompoundDrawablesWithIntrinsicBounds(0,
					R.drawable.icon_home, 0, 0);
			main_tab_message.setCompoundDrawablesWithIntrinsicBounds(0,
					R.drawable.icon_meassage, 0, 0);
			main_tab_self.setCompoundDrawablesWithIntrinsicBounds(0,
					R.drawable.icon_selfinfo_d, 0, 0);
			main_tab_search.setCompoundDrawablesWithIntrinsicBounds(0,
					R.drawable.icon_square, 0, 0);
			tabHost.setCurrentTabByTag("self");
			break;
		case R.id.main_tab_search:
			main_tab_home.setCompoundDrawablesWithIntrinsicBounds(0,
					R.drawable.icon_home, 0, 0);
			main_tab_message.setCompoundDrawablesWithIntrinsicBounds(0,
					R.drawable.icon_meassage, 0, 0);
			main_tab_self.setCompoundDrawablesWithIntrinsicBounds(0,
					R.drawable.icon_selfinfo, 0, 0);
			main_tab_search.setCompoundDrawablesWithIntrinsicBounds(0,
					R.drawable.icon_square_d, 0, 0);
			tabHost.setCurrentTabByTag("search");
			break;
		}
	}
}
