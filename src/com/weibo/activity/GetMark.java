/**weibotest
 * 
 *
 *TODO
 */
package com.weibo.activity;
import com.weibo.R;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.message.BasicNameValuePair;
import org.json.JSONObject;

import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.Window;
import android.view.WindowManager;
import android.view.WindowManager.LayoutParams;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.weibo.application.Mykey;
import com.weibo.card.CheckInfo;
import com.weibo.utils.ClientUtils;
import com.weibo.utils.FaceConversionUtil;
import com.weibo.utils.Utils;

/**
 * @作者:陈华清
 * 
 * @版本:1.0
 * @生成时期:2014年8月20日 上午2:15:52
 * @com.example.weibotest
 */
public class GetMark extends BaseActivity {
	private static int screenHeight;
	TextView marktext;
	private static final String TAG = "GetMark";
	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		this.requestWindowFeature(Window.FEATURE_NO_TITLE); // 设置Activity标题不显示
		this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);// 设置全屏显示
		setContentView(R.layout.getmark_layout);
		init();
		getdata();
		Utils.getInstance().addActivity(this);
	}
	private void init() {
		marktext = (TextView) findViewById(R.id.marktext);
		screenHeight = getWindow().getWindowManager().getDefaultDisplay().getHeight();// 获取屏幕高度
		WindowManager.LayoutParams lp = getWindow().getAttributes();// //lp包含了布局的很多信息，通过lp来设置对话框的布局
		lp.width = LayoutParams.FILL_PARENT;
		lp.gravity = Gravity.BOTTOM;
		lp.height = screenHeight / 3;// lp高度设置为屏幕的一半
		getWindow().setAttributes(lp);// 将设置好属性的lp应用到对话框
	}
	String resultw;
	private void getdata() {
		new AsyncTask<Void, Void, Void>() {
			protected Void doInBackground(Void... param) {
				List<BasicNameValuePair> params = new ArrayList<BasicNameValuePair>();
				params.add(new BasicNameValuePair("oauth_token", ((Mykey) getApplication()).getOauth_token()));
				params.add(new BasicNameValuePair("oauth_token_secret", ((Mykey) getApplication()).getOauth_token_secret()));
				params.add(new BasicNameValuePair("user_id", ((Mykey) getApplication()).getUid()));
				resultw = ClientUtils.post_str(ClientUtils.BASE_URL + GetMark.this.getString(ClientUtils.Checkin_get_check_info), params, GetMark.this);
				return null;
			}
			@Override
			protected void onPostExecute(Void result) {
				try {
					Gson gson = new Gson();
					String s = "";
					CheckInfo info = gson.fromJson(resultw, CheckInfo.class);
					if (info.isIscheck()) {
						s = "您今天已经签过到了!\r\n你最近一次签到日期是:" + FaceConversionUtil.TimeStamp2Date(info.getData()) + "\r\n您已经连续签到:" + info.getCon_num() + "\r\n总共签到:" + info.getTotal_num() + "天";
					} else {
						s = "您今天还没有签到!\r\n温馨小提示，签到按钮在主页右上方.";
					}
					marktext.setText(s);
				} catch (Exception e) {
					Log.d(TAG, e.toString());
				}
			}
		}.execute(null, null, null);
	}

}
