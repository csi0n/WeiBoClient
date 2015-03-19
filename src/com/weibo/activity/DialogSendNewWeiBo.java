package com.weibo.activity;

import java.io.File;import com.weibo.R;

import com.weibo.lib.photodeal.Bimp;
import com.weibo.lib.photodeal.PublishedActivity;
import com.weibo.lib.photodeal.TestPicActivity;
import com.weibo.utils.Common;
import com.weibo.utils.FileUtils;
import com.weibo.utils.Utils;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.Gravity;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.WindowManager;
import android.view.WindowManager.LayoutParams;
import android.widget.ImageView;
import android.widget.TextView;
/**
 * @作者:陈华清
 * 
 * @版本:1.0
 * @生成时期:2014年8月4日 下午01:49:27
 * @com.server
 */
public class DialogSendNewWeiBo extends BaseActivity implements OnClickListener {
	private ImageView button_cancle;// 取消按钮
	TextView send, album, take_photo;
	private static int screenHeight;
	private static final String TAG = "DialogSendNewWeiBo";
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.requestWindowFeature(Window.FEATURE_NO_TITLE); // 设置Activity标题不显示
		this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);// 设置全屏显示
		setContentView(R.layout.activity_dialog);
		init();
		Utils.getInstance().addActivity(this);
	}
	private void init() {
		screenHeight = getWindow().getWindowManager().getDefaultDisplay().getHeight();// 获取屏幕高度
		WindowManager.LayoutParams lp = getWindow().getAttributes();// //lp包含了布局的很多信息，通过lp来设置对话框的布局
		lp.width = LayoutParams.FILL_PARENT;
		lp.gravity = Gravity.BOTTOM;
		lp.height = screenHeight / 3;// lp高度设置为屏幕的一半
		getWindow().setAttributes(lp);// 将设置好属性的lp应用到对话框
		button_cancle = (ImageView) findViewById(R.id.button_cancle);
		button_cancle.setOnClickListener(this);// 取消按钮的点击事件监听
		send = (TextView) findViewById(R.id.newweibo);
		send.setOnClickListener(this);
		album = (TextView) findViewById(R.id.album);
		album.setOnClickListener(this);
		take_photo = (TextView) findViewById(R.id.take_camera);
		take_photo.setOnClickListener(this);
	}
	// 重写finish（）方法，加入关闭时的动画
	public void finish() {
		super.finish();
		DialogSendNewWeiBo.this.overridePendingTransition(0, R.anim.dialog_exit);
	}
	@Override
	protected void onResume() {
		super.onResume();
		if (Common.CLOSE) {
			Common.CLOSE = false;
			finish();
		}
	}
	private static final int TAKE_PICTURE = 0x000000;
	private String path = "";
	public void photo() {
		Intent openCameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
		if (FileUtils.isExist()) {
			File file = new File(FileUtils.SDPATH, String.valueOf(System.currentTimeMillis()) + ".jpg");
			path = file.getPath();
			Uri imageUri = Uri.fromFile(file);
			openCameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
			startActivityForResult(openCameraIntent, TAKE_PICTURE);
		}
	}
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		if (data == null) {
			return;
		} else {
			switch (requestCode) {
				case TAKE_PICTURE :
					if (Bimp.drr.size() < 9 && resultCode == -1) {
						Bimp.drr.add(path);
					}
					Common.change = true;
					Intent i = new Intent(DialogSendNewWeiBo.this, PublishedActivity.class);
					startActivity(i);
					break;
			}
		}
	}

	public void onClick(View v) {
		switch (v.getId()) {
		// 取消按钮的点击事件，关闭对话框
			case R.id.button_cancle :
				finish();
				break;
			case R.id.newweibo :
				Intent u = new Intent(DialogSendNewWeiBo.this, PublishedActivity.class);
				startActivity(u);
				break;
			case R.id.album :
				Bimp.act_bool = true;
				Intent i = new Intent(DialogSendNewWeiBo.this, TestPicActivity.class);
				startActivity(i);
				break;
			case R.id.take_camera :
				photo();
				break;
		}
	}

}
