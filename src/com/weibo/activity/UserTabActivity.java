/**weibotest
 * 
 *
 *TODO
 */
package com.weibo.activity;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.message.BasicNameValuePair;
import org.json.JSONObject;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.weibo.application.Mykey;
import com.weibo.card.CheckInfo;
import com.weibo.card.UserInfor;
import com.weibo.utils.ClientUtils;
import com.weibo.utils.DialogUtils;
import com.weibo.utils.DialogUtils.DialogCallBack;
import com.weibo.utils.Utils;
import com.weibo.view.widget.RoundCornerImageView;
import com.weibo.R;
/**
 * @作者:陈华清
 * 
 * @版本:1.0
 * @生成时期:2014年8月16日 上午7:47:42
 * @com.tab.newactivity
 */
public class UserTabActivity extends BaseActivity implements OnClickListener {
	RoundCornerImageView head;
	TextView uname, intro, weibocount, followingcount, followercount;
	ImageView sex;
	LinearLayout following, follower, myweibo, mycoll, myfriend, myqr, myweiba, ssss, nearsend, markl;
	RelativeLayout user;
	ImageView setting;
	TextView ischeck;
	private static final String TAG = "UserTabActivity";
	public void findview() {
		markl = (LinearLayout) findViewById(R.id.markl);
		markl.setOnClickListener(this);
		nearsend = (LinearLayout) findViewById(R.id.sendfil);
		nearsend.setOnClickListener(this);
		ischeck = (TextView) findViewById(R.id.ischeck);
		setting = (ImageView) findViewById(R.id.setting);
		setting.setOnClickListener(this);
		ssss = (LinearLayout) findViewById(R.id.ssss);
		ssss.setOnClickListener(this);
		user = (RelativeLayout) findViewById(R.id.user);
		user.setOnClickListener(this);
		head = (RoundCornerImageView) findViewById(R.id.roundCornerImageView1);
		if (UserInfor.HEAD_IC != null) {
			head.setImageUrl(UserInfor.HEAD_IC);
		}
		uname = (TextView) findViewById(R.id.textView2);
		if (UserInfor.UNAME != null) {
			uname.setText(UserInfor.UNAME);
		}
		intro = (TextView) findViewById(R.id.textView3);
		if (UserInfor.INTRO != null) {
			intro.setText(UserInfor.INTRO);
		}
		weibocount = (TextView) findViewById(R.id.weibocount);
		weibocount.setText(UserInfor.getweibo_count());
		followingcount = (TextView) findViewById(R.id.followingcount);
		followingcount.setText(UserInfor.getfollowing_count());
		followercount = (TextView) findViewById(R.id.followercount);
		followercount.setText(UserInfor.getfollower_count());
		sex = (ImageView) findViewById(R.id.imageView1);
		if (UserInfor.SEX.equals("男")) {
			sex.setImageDrawable(this.getResources().getDrawable(R.drawable.find_man));
		} else {
			sex.setImageDrawable(this.getResources().getDrawable(R.drawable.find_woman));
		}
		following = (LinearLayout) findViewById(R.id.following);
		following.setOnClickListener(this);
		follower = (LinearLayout) findViewById(R.id.follower);
		follower.setOnClickListener(this);
		myweibo = (LinearLayout) findViewById(R.id.myweibo);
		myweibo.setOnClickListener(this);
		mycoll = (LinearLayout) findViewById(R.id.mycoll);
		mycoll.setOnClickListener(this);
		myfriend = (LinearLayout) findViewById(R.id.myfriend);
		myfriend.setOnClickListener(this);
		myqr = (LinearLayout) findViewById(R.id.myqr);
		myqr.setOnClickListener(this);
		myweiba = (LinearLayout) findViewById(R.id.myweiba);
		myweiba.setOnClickListener(this);
	}
	String checkinfor;
	public void getdata() {
		new AsyncTask<Void, Void, Void>() {
			protected Void doInBackground(Void... param) {
				List<BasicNameValuePair> params = new ArrayList<BasicNameValuePair>();
				params.add(new BasicNameValuePair("oauth_token", ((Mykey) getApplication()).getOauth_token()));
				params.add(new BasicNameValuePair("oauth_token_secret", ((Mykey) getApplication()).getOauth_token_secret()));
				params.add(new BasicNameValuePair("user_id", ((Mykey) getApplication()).getUid()));
				checkinfor = ClientUtils.post_str(ClientUtils.BASE_URL + UserTabActivity.this.getString(ClientUtils.Checkin_get_check_info), params, UserTabActivity.this);
				return null;
			}
			@Override
			protected void onPostExecute(Void result) {
				try {
					Gson gson = new Gson();
					CheckInfo ck = gson.fromJson(checkinfor, CheckInfo.class);
					if (ck.isIscheck()) {
						ischeck.setText("今天已经签到过了，你已经签到了!");
					} else {
						ischeck.setText("您今天还沒有签到哦");
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
				Log.d(TAG, "jieshu");
			}
		}.execute(null, null, null);
	}
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.usertab_activity_layout);
		findview();
		getdata();
		Utils.getInstance().addActivity(this);
	}
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK) {
			DialogUtils.dialogBuilder(UserTabActivity.this, "提示", "确定要退出？", new DialogCallBack() {
				@Override
				public void callBack() {
					Utils.getInstance().exit(UserTabActivity.this);
				}
			});
		}
		return super.onKeyDown(keyCode, event);
	}
	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		if (v == following) {
			((Mykey) getApplication()).setOtheruid(((Mykey) getApplication()).getUid());
			Intent i = new Intent(UserTabActivity.this, FollowList.class);
			i.putExtra("a", "2");
			UserTabActivity.this.startActivity(i);
		} else if (v == follower) {
			((Mykey) getApplication()).setOtheruid(((Mykey) getApplication()).getUid());
			Intent i = new Intent(UserTabActivity.this, FollowList.class);
			i.putExtra("a", "1");
			UserTabActivity.this.startActivity(i);
		} else if (v == myweibo) {
			Intent i = new Intent(UserTabActivity.this, MyWeiBo.class);
			startActivity(i);
		} else if (v == mycoll) {
			Intent i = new Intent(UserTabActivity.this, Collection.class);
			startActivity(i);
		} else if (v == myfriend) {
			((Mykey) getApplication()).setOtheruid(((Mykey) getApplication()).getUid());
			Intent i = new Intent(UserTabActivity.this, FollowList.class);
			i.putExtra("a", "3");
			UserTabActivity.this.startActivity(i);
		} else if (v == myqr) {
			Intent i = new Intent(UserTabActivity.this, MyQrActivity.class);
			startActivity(i);
		} else if (v == myweiba) {
			Intent i = new Intent(UserTabActivity.this, MyWeiBaSetting.class);
			startActivity(i);
		} else if (v == user) {
			Intent i = new Intent(UserTabActivity.this, Tab3Activity.class);
			startActivity(i);
		} else if (v == ssss) {
			Intent i = new Intent(UserTabActivity.this, ThemeMain.class);
			startActivity(i);
		} else if (v == setting) {
			Intent i = new Intent(UserTabActivity.this, MySetting.class);
			startActivity(i);
		} else if (v == nearsend) {
			Intent i = new Intent(UserTabActivity.this, NearSendMain.class);
			startActivity(i);
		} else if (v == markl) {
			Intent i = new Intent(UserTabActivity.this, GetMark.class);
			startActivity(i);
		}
	}
}
