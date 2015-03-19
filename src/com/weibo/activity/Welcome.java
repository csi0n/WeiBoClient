package com.weibo.activity;
import java.util.ArrayList;
import com.weibo.R;

import java.util.List;

import org.apache.http.message.BasicNameValuePair;
import org.json.JSONObject;
import org.json.JSONTokener;

import com.weibo.application.Mykey;
import com.weibo.utils.ACache;
import com.weibo.utils.ClientUtils;
import com.weibo.utils.Utils;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
/**
 * @作者:陈华清
 *
 * @版本:1.0
 * @生成时期:2014年12月5日 下午8:05:39
 * @com.weibo.activity
 */
public class Welcome extends BaseActivity implements Runnable {
	View view;
	ACache mcache;
	private static final String TAG = "Welcome.java";
	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		view = View.inflate(this, R.layout.welcome_main, null);
		setContentView(view);
		mcache = ACache.get(this);
		new Thread(this).start();
		Utils.getInstance().addActivity(this);
	}
	@Override
	public void run() {
		try {
			Thread.sleep(2000);
		} catch (InterruptedException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		if (Utils.iffirst("isFirstUse", Welcome.this)) {
			startActivity(new Intent(Welcome.this, FirstLogin.class));
		} else {
			if (mcache.FindAsString("username")
					&& mcache.FindAsString("password")) {
				try {
					List<BasicNameValuePair> params = new ArrayList<BasicNameValuePair>();
					params.add(new BasicNameValuePair("username", mcache.getAsString("username")));
					params.add(new BasicNameValuePair("password", mcache
							.getAsString("password")));
					System.out.println("获取到得缓存username:"
							+ mcache.getAsString("username") + "缓存密码:"
							+ mcache.getAsString("password"));
					String result = ClientUtils.post_str(ClientUtils.BASE_URL+ Welcome.this.getString(ClientUtils.UserLogin_login),params, Welcome.this);
					Log.d(TAG, result);
					if (result.indexOf("oauth_token") > 0) {
						JSONTokener jsonparser = new JSONTokener(result);
						JSONObject person = (JSONObject) jsonparser.nextValue();
						String oauth_token = person.getString("oauth_token");
						String oauth_token_secret = person
								.getString("oauth_token_secret");
						String Uid = person.getString("uid");
						((Mykey) getApplication()).setOauth_token(oauth_token);
						((Mykey) getApplication())
								.setOauth_token_secret(oauth_token_secret);
						((Mykey) getApplication()).setUid(Uid);
						Intent intent = new Intent(Welcome.this,
								WeiBoMainTab.class);
						Welcome.this.startActivity(intent);
						overridePendingTransition(R.anim.rotate_left,
								R.anim.rotate_right);
					} else {
						startActivity(new Intent(Welcome.this,
								MainActivity.class));
					}
				} catch (Exception e) {
					Log.d(TAG, e.toString());
					startActivity(new Intent(Welcome.this, MainActivity.class));
				}
			} else {
				startActivity(new Intent(Welcome.this, MainActivity.class));
			}
		}
	}
}
