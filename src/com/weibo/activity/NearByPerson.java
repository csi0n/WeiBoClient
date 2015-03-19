package com.weibo.activity;
import java.text.DecimalFormat;

import com.weibo.R;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONObject;

import com.weibo.adapters.NearByPersonAdapter;
import com.weibo.application.Mykey;
import com.weibo.card.PersonCard;
import com.weibo.lib.zrclist.ZrcListView;
import com.weibo.lib.zrclist.ZrcListView.OnStartListener;
import com.weibo.utils.ClientUtils;
import com.weibo.utils.DialogUtils;
import com.weibo.utils.DialogUtils.DialogCallBack;
import com.weibo.utils.FaceConversionUtil;
import com.weibo.view.widget.LoadingAlertAnim;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.location.Criteria;
import android.location.GpsSatellite;
import android.location.GpsStatus;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.location.LocationProvider;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.provider.Settings;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
/**
 * @作者:陈华清
 * 
 * @版本:1.0
 * @生成时期:2014年7月20日 下午1:31:32 @
 */
public class NearByPerson extends BaseActivity {
	private static final String TAG = "NearByPeople.JAVA";
	private ProgressDialog dialog;
	ImageView close;
	ZrcListView xlistview;
	private LocationManager lm;
	Location tlocation;
	double Longitude;
	double Latitude;
	ArrayList<PersonCard> cardlist = null;
	PersonCard person;
	NearByPersonAdapter adapter;
	private static final int change = 0;
	int number = 1;
	private static final int end = 1;
	private static final int ref = 2;
	private static final int error = 3;
	int listcount = 0;
	String sendmylocation = "0";
	LoadingAlertAnim loading;
	private Handler handler = new Handler() {
		public void handleMessage(Message msg) {
			switch (msg.what) {
				case change :
					if (loading != null) {
						loading.dismiss();
					}
					if (listcount >= 19) {
						xlistview.startLoadMore();
					} else {
						xlistview.stopLoadMore();
					}
					xlistview.setRefreshSuccess();
					adapter.notifyDataSetChanged();
					break;
				case end :
					xlistview.setLoadMoreSuccess();
					break;
				case ref :
					xlistview.refresh();
					break;
				case error :
					if (loading != null) {
						loading.dismiss();
					}
					break;
			}
		}
	};

	public void get(int page) {
		try {
			Longitude = tlocation.getLongitude();
			Latitude = tlocation.getLatitude();
			DecimalFormat df = new DecimalFormat("###.0000");
			Longitude = Double.parseDouble(df.format(Longitude));
			Latitude = Double.parseDouble(df.format(Latitude));
			Log.d(TAG, "----------------------------------");
			Log.d(TAG, String.valueOf(Longitude));
			Log.d(TAG, String.valueOf(Latitude));
			Log.d(TAG, "----------------------------------");
			if (sendmylocation.equals("1")) {
				insterlocation();
			}
			List<BasicNameValuePair> params = new ArrayList<BasicNameValuePair>();
			params.add(new BasicNameValuePair("user_id", ((Mykey) getApplication()).getUid()));
			params.add(new BasicNameValuePair("oauth_token", ((Mykey) getApplication()).getOauth_token()));
			params.add(new BasicNameValuePair("oauth_token_secret", ((Mykey) getApplication()).getOauth_token_secret()));
			params.add(new BasicNameValuePair("latitude", String.valueOf(Latitude)));
			params.add(new BasicNameValuePair("longitude", String.valueOf(Longitude)));
			String result = ClientUtils.post_str(ClientUtils.BASE_URL + NearByPerson.this.getString(ClientUtils.User_get_nearby_person), params, NearByPerson.this);
			JSONArray jArray = new JSONArray(result);
			for (int i = 0; i < jArray.length(); i++) {
				JSONObject temp = (JSONObject) jArray.get(i);
				person = new PersonCard();
				person.setUid(temp.getString("uid"));
				person.setAvatar_middle(temp.getString("avatar_middle"));
				person.setIntro(temp.getString("intro"));
				person.setUname(temp.getString("uname"));
				cardlist.add(person);
			}
			listcount = jArray.length();
			Message message = new Message();
			message.what = change;
			handler.sendMessage(message);
		} catch (Exception e) {
			Log.d(TAG, e.toString());
			Message me = new Message();
			me.what = error;
			handler.sendMessage(me);
		}
	}

	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
		lm.removeUpdates(locationListener);
	}
	public void insterlocation() {
		List<BasicNameValuePair> params = new ArrayList<BasicNameValuePair>();
		params.add(new BasicNameValuePair("user_id", ((Mykey) getApplication()).getUid()));
		params.add(new BasicNameValuePair("oauth_token", ((Mykey) getApplication()).getOauth_token()));
		params.add(new BasicNameValuePair("oauth_token_secret", ((Mykey) getApplication()).getOauth_token_secret()));
		params.add(new BasicNameValuePair("latitude", String.valueOf(Latitude)));
		params.add(new BasicNameValuePair("longitude", String.valueOf(Longitude)));
		String result = ClientUtils.post_str(ClientUtils.BASE_URL + NearByPerson.this.getString(ClientUtils.User_insert_location), params, NearByPerson.this);
		Log.d(TAG, "插入位置信息" + result == "1" ? "成功" : "失败");
	}
	public void listener() {
		close.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				NearByPerson.this.finish();
			}
		});
		xlistview.setOnLoadMoreStartListener(new OnStartListener() {
			@Override
			public void onStart() {
				// TODO Auto-generated method stub
				new Thread(new Runnable() {
					@Override
					public void run() {
						number++;
						// TODO Auto-generated method stub
						get(number);
						Message message = new Message();
						message.what = end;
						handler.sendMessage(message);
					}
				}).start();
			}
		});
		xlistview.setOnRefreshStartListener(new OnStartListener() {
			@Override
			public void onStart() {
				new Thread(new Runnable() {
					@Override
					public void run() {
						// TODO Auto-generated method stub
						cardlist.clear();
						get(1);
					}
				}).start();
			}
		});
	}

	public void inint() {
		cardlist = new ArrayList<PersonCard>();
		adapter = new NearByPersonAdapter(this, cardlist);
		xlistview.setAdapter(adapter);
	}

	public void findview() {
		close = (ImageView) findViewById(R.id.example_right);
		xlistview = (ZrcListView) findViewById(R.id.zrcListView1);
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.near_by_person);
		findview();
		FaceConversionUtil.StartListView(NearByPerson.this, xlistview);
		inint();
		listener();
		DialogUtils.dialogBuilderclose(NearByPerson.this, "提示", "是否发送您的位置到服务器？", new DialogCallBack() {
			@Override
			public void callBack() {
				loading = new LoadingAlertAnim(NearByPerson.this, R.style.MyDialogStyle, "正在定位...");
				loading.setCanceledOnTouchOutside(false);
				loading.show();
				insterlocation();
				lm = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
				// 判断GPS是否正常启动
				if (!lm.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
					FaceConversionUtil.dispToast(NearByPerson.this, "请开启GPS");
					// 返回开启GPS导航设置界面
					Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
					startActivityForResult(intent, 0);
					return;
				}
				String bestProvider = lm.getBestProvider(getCriteria(), true);
				Location location = lm.getLastKnownLocation(bestProvider);
				updateView(location);
				lm.addGpsStatusListener(listener);
				lm.requestLocationUpdates(LocationManager.GPS_PROVIDER, 1000, 1, locationListener);
			}
		});
	}

	// 位置监听
	private LocationListener locationListener = new LocationListener() {
		/**
		 * 位置信息变化时触发
		 */
		public void onLocationChanged(Location location) {
			updateView(location);
			Log.i(TAG, "时间：" + location.getTime());
			Log.i(TAG, "经度：" + location.getLongitude());
			Log.i(TAG, "纬度：" + location.getLatitude());
			Log.i(TAG, "海拔：" + location.getAltitude());
		}
		/**
		 * GPS状态变化时触发
		 */
		public void onStatusChanged(String provider, int status, Bundle extras) {
			switch (status) {
			// GPS状态为可见时
				case LocationProvider.AVAILABLE :
					Log.i(TAG, "当前GPS状态为可见状态");
					break;
				// GPS状态为服务区外时
				case LocationProvider.OUT_OF_SERVICE :
					Log.i(TAG, "当前GPS状态为服务区外状态");
					break;
				// GPS状态为暂停服务时
				case LocationProvider.TEMPORARILY_UNAVAILABLE :
					Log.i(TAG, "当前GPS状态为暂停服务状态");
					break;
			}
		}

		/**
		 * GPS开启时触发
		 */
		public void onProviderEnabled(String provider) {
			Location location = lm.getLastKnownLocation(provider);
			updateView(location);
		}

		/**
		 * GPS禁用时触发
		 */
		public void onProviderDisabled(String provider) {
			updateView(null);
		}

	};

	// 状态监听
	GpsStatus.Listener listener = new GpsStatus.Listener() {
		public void onGpsStatusChanged(int event) {
			switch (event) {
			// 第一次定位
				case GpsStatus.GPS_EVENT_FIRST_FIX :
					Log.i(TAG, "第一次定位");
					break;
				// 卫星状态改变
				case GpsStatus.GPS_EVENT_SATELLITE_STATUS :
					Log.i(TAG, "卫星状态改变");
					// 获取当前状态
					GpsStatus gpsStatus = lm.getGpsStatus(null);
					// 获取卫星颗数的默认最大值
					int maxSatellites = gpsStatus.getMaxSatellites();
					// 创建一个迭代器保存所有卫星
					Iterator<GpsSatellite> iters = gpsStatus.getSatellites().iterator();
					int count = 0;
					while (iters.hasNext() && count <= maxSatellites) {
						GpsSatellite s = iters.next();
						count++;
					}
					System.out.println("搜索到：" + count + "颗卫星");
					break;
				// 定位启动
				case GpsStatus.GPS_EVENT_STARTED :
					Log.i(TAG, "定位启动");
					break;
				// 定位结束
				case GpsStatus.GPS_EVENT_STOPPED :
					Log.i(TAG, "定位结束");
					break;
			}
		};
	};

	/**
	 * 实时更新文本内容
	 * 
	 * @param location
	 */
	private void updateView(Location location) {
		if (location != null) {
			tlocation = location;
			xlistview.refresh();
			lm.removeUpdates(locationListener);
		} else {
			// 清空EditText对象

		}
	}

	/**
	 * 返回查询条件
	 * 
	 * @return
	 */
	private Criteria getCriteria() {
		Criteria criteria = new Criteria();
		// 设置定位精确度 Criteria.ACCURACY_COARSE比较粗略，Criteria.ACCURACY_FINE则比较精细
		criteria.setAccuracy(Criteria.ACCURACY_FINE);
		// 设置是否要求速度
		criteria.setSpeedRequired(false);
		// 设置是否允许运营商收费
		criteria.setCostAllowed(false);
		// 设置是否需要方位信息
		criteria.setBearingRequired(false);
		// 设置是否需要海拔信息
		criteria.setAltitudeRequired(false);
		// 设置对电源的需求
		criteria.setPowerRequirement(Criteria.POWER_LOW);
		return criteria;
	}
}
