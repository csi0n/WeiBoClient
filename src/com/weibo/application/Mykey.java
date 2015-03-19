package com.weibo.application;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.weibo.R;
import com.weibo.card.MyMedals;
import com.weibo.card.Transpond_Data;
import com.weibo.card.WeiBoCard;
import com.weibo.lib.lock.LockManager;
import com.weibo.notification.client.Constants;
import com.weibo.notification.client.NotificationSettingsActivity2;
import com.weibo.notification.client.ServiceManager;
import com.weibo.utils.FaceConversionUtil;
import com.weibo.utils.FileUtils;
import com.weibo.utils.Utils;
import com.weibo.view.widget.MyHeadImgeView;

import android.app.Application;
import android.content.Intent;
import android.os.AsyncTask;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
public class Mykey extends Application {
	private static final String TAG = "Mykey";
	private String oauth_token;
	private String oauth_token_secret;
	private String uid;
	private String otheruid;
	private String feed_id;
	// @我的好友（回传）
	private String find_me = null;
	private String uploadimageurl = null;
	// 经纬度
	private String latitude;
	private String longitude;
	// 返回页面是否刷新0不刷新1为刷新
	private String shouldrefr = null;
	private String shoulddissmiss = "0";
	// 是否删除
	private String isdel = "0";
	// 删除ID
	private String del_weibo_id = null;
	private View view;
	private MyHeadImgeView head_ic;
	private String head_icurl;
	private String avatar_middle;
	private List<MyMedals> medals;
	private ArrayList<String> picturelist;
	private int picCurrentItem = 0;
	private String refresh = "0";
	private String weibasearch_key;
	private String weibasearch_key_note;
	private String my_head = null;
	private List<WeiBoCard> data;
	private WeiBoCard mymap;
	private String weibo_id = null;
	private String uname = null;
	private Transpond_Data treanspond = null;
	public Transpond_Data getTreanspond() {
		return treanspond;
	}
	public void setTreanspond(Transpond_Data treanspond) {
		this.treanspond = treanspond;
	}
	private WindowManager.LayoutParams wmParams = new WindowManager.LayoutParams();
	public List<WeiBoCard> getData() {
		return data;
	}
	public void setData(List<WeiBoCard> data) {
		this.data = data;
	}
	public WeiBoCard getMymap() {
		return mymap;
	}
	public void setMymap(WeiBoCard mymap) {
		this.mymap = mymap;
	}
	public WindowManager.LayoutParams getWmParams() {
		return wmParams;
	}
	public void setWmParams(WindowManager.LayoutParams wmParams) {
		this.wmParams = wmParams;
	}
	public static String getTag() {
		return TAG;
	}
	public WindowManager.LayoutParams getMywmParams() {
		return wmParams;
	}
	public String getMy_head() {
		return my_head;
	}

	public void setMy_head(String my_head) {
		this.my_head = my_head;
	}

	public String getWeibasearch_key_note() {
		return weibasearch_key_note;
	}

	public void setWeibasearch_key_note(String weibasearch_key_note) {
		this.weibasearch_key_note = weibasearch_key_note;
	}

	public String getWeibasearch_key() {
		return weibasearch_key;
	}

	public void setWeibasearch_key(String weibasearch_key) {
		this.weibasearch_key = weibasearch_key;
	}

	public String getRefresh() {
		return refresh;
	}

	public void setRefresh(String refresh) {
		this.refresh = refresh;
	}

	public int getPicCurrentItem() {
		return picCurrentItem;
	}

	public void setPicCurrentItem(int picCurrentItem) {
		this.picCurrentItem = picCurrentItem;
	}

	public ArrayList<String> getPicturelist() {
		return picturelist;
	}

	public void setPicturelist(ArrayList<String> picturelist) {
		this.picturelist = picturelist;
	}

	public List<MyMedals> getMedals() {
		return medals;
	}

	public void setMedals(List<MyMedals> medals) {
		this.medals = medals;
	}

	public String getAvatar_middle() {
		return avatar_middle;
	}

	public void setAvatar_middle(String avatar_middle) {
		this.avatar_middle = avatar_middle;
	}

	public String getHead_icurl() {
		return head_icurl;
	}

	public void setHead_icurl(String head_icurl) {
		this.head_icurl = head_icurl;
	}

	public MyHeadImgeView getHead_ic() {
		return head_ic;
	}

	public void setHead_ic(MyHeadImgeView head_ic) {
		this.head_ic = head_ic;
	}

	public View getView() {
		return view;
	}

	public void setView(View view) {
		this.view = view;
	}

	public String getShoulddissmiss() {
		return shoulddissmiss;
	}

	public void setShoulddissmiss(String shoulddissmiss) {

		this.shoulddissmiss = shoulddissmiss;
	}

	public String getShouldrefr() {

		return shouldrefr;
	}

	public void setShouldrefr(String shouldrefr) {

		this.shouldrefr = shouldrefr;
	}
	public String getUploadimageurl() {
		return uploadimageurl;
	}
	public void setUploadimageurl(String uploadimageurl) {
		this.uploadimageurl = uploadimageurl;
	}

	public String getDel_weibo_id() {
		return del_weibo_id;
	}

	public void setDel_weibo_id(String del_weibo_id) {
		this.del_weibo_id = del_weibo_id;
	}

	public String getIsdel() {
		return isdel;
	}

	public void setIsdel(String isdel) {
		this.isdel = isdel;
	}

	public String getLatitude() {
		return latitude;
	}

	public void setLatitude(String latitude) {
		this.latitude = latitude;
	}

	public String getLongitude() {
		return longitude;
	}

	public void setLongitude(String longitude) {
		this.longitude = longitude;
	}

	public String getFind_me() {
		return find_me;
	}

	public void setFind_me(String find_me) {
		this.find_me = find_me;
	}
	public String getUname() {
		return uname;
	}
	public void setUname(String uname) {
		this.uname = uname;
	}

	public String getWeibo_id() {
		return weibo_id;
	}

	public void setWeibo_id(String weibo_id) {
		this.weibo_id = weibo_id;
	}

	public String getFeed_id() {
		return feed_id;
	}

	public void setFeed_id(String feed_id) {
		this.feed_id = feed_id;
	}
	public String getOtheruid() {
		return otheruid;
	}

	public void setOtheruid(String otheruid) {
		this.otheruid = otheruid;
	}

	public String getUid() {
		return uid;
	}

	public void setUid(String uid) {
		this.uid = uid;
	}

	public String getOauth_token() {
		return oauth_token;
	}

	public void setOauth_token(String oauth_token) {
		this.oauth_token = oauth_token;
	}

	public String getOauth_token_secret() {
		return oauth_token_secret;
	}
	public void setOauth_token_secret(String oauth_token_secret) {
		this.oauth_token_secret = oauth_token_secret;
	}
	@Override
	public void onCreate() {
		super.onCreate();
		LockManager.getInstance().enableAppLock(this);
		if(FileUtils.SDCardStatus())
		{
			System.out.println("SD卡已经插入!");
		}else
		{
			FaceConversionUtil.dispToast(Mykey.this, "请插入SD卡！");
		}
		new AsyncTask<Void, Void, Void>() {
			@Override
			protected Void doInBackground(Void... arg0) {
				// TODO Auto-generated method stub
				FileUtils.isExist();
				FaceConversionUtil.getInstace().getFileText(Mykey.this);
				FaceConversionUtil.getInstace().getWeatherText(Mykey.this);
				ServiceManager serviceManager = new ServiceManager(Mykey.this);
				serviceManager.setNotificationIcon(R.drawable.logo);
				serviceManager.startService();
				Utils.PutSetting(Constants.SETTINGS_NOTIFICATION_ENABLED, true, Mykey.this);
				return null;
			}
			@Override
			protected void onPostExecute(Void result) {
				// TODO Auto-generated method stub
				Log.e(TAG, "资源初始化完成!");
			}
		}.execute(null, null, null);

	}
}
