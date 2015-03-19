/**weibotest
 * 
 *
 *TODO
 */
package com.weibo.activity;
import com.weibo.utils.Utils;

import android.app.Activity;
import android.app.ActivityManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import com.weibo.R;
/**
 * @作者:陈华清
 * 
 * @版本:1.0
 * @生成时期:2014年8月2日 下午3:53:24
 * @com.api.example.app
 */
public class ThemeMain extends BaseActivity implements OnClickListener {
	ImageButton theme1, theme2, theme3, theme4;
	Button anim;
	ImageView close;

	public void findview() {
		theme1 = (ImageButton) findViewById(R.id.theme1);
		theme1.setOnClickListener(this);
		theme2 = (ImageButton) findViewById(R.id.theme2);
		theme2.setOnClickListener(this);
		theme3 = (ImageButton) findViewById(R.id.theme3);
		theme3.setOnClickListener(this);
		theme4 = (ImageButton) findViewById(R.id.theme4);
		theme4.setOnClickListener(this);
		close = (ImageView) findViewById(R.id.example_right);
		close.setOnClickListener(this);
		anim = (Button) findViewById(R.id.anim);
		anim.setOnClickListener(this);
	}
	ActivityManager manager;
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.theme_main_layout);
		findview();
		Utils.getInstance().addActivity(this);
		manager = (ActivityManager) getSystemService(Context.ACTIVITY_SERVICE);      
		
	}
	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.theme1:
			Utils.changeToTheme(ThemeMain.this, 1);
			manager.restartPackage(getPackageName());
			break;
		case R.id.theme2:
			Utils.changeToTheme(ThemeMain.this, 2);
			manager.restartPackage(getPackageName());
			break;
		case R.id.theme3:
			Utils.changeToTheme(ThemeMain.this, 3);
			manager.restartPackage(getPackageName());
			break;
		case R.id.theme4:
			Utils.changeToTheme(ThemeMain.this, 4);
			manager.restartPackage(getPackageName());
			break;
		case R.id.example_right:
			ThemeMain.this.finish();
			break;
		case R.id.anim:
			Intent i = new Intent(ThemeMain.this, AnimSettingActivity.class);
			startActivity(i);
			break;
		}
	}
}
