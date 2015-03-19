/**weibotest
 * 
 *
 *TODO
 */
package com.weibo.activity;
import com.weibo.R;
import java.util.Collections;
import java.util.HashMap;

import org.json.JSONObject;

import com.weibo.utils.ClientUtils;
import com.weibo.utils.FaceConversionUtil;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * @作者:陈华清
 * 
 * @版本:1.0
 * @生成时期:2014年8月7日 下午1:29:04
 * @com.api.example.app
 */
public class WeatherDetail extends BaseActivity {
	TextView city, en_city, date_yandweek, temp1, weather1, wind1;
	Button chose;
	ImageView close, change;
	private static final String TAG = "WeatherDetail.java";
	HashMap<String, Object> map;
	void get_data() {
		new AsyncTask<Void, Void, Void>() {
			protected Void doInBackground(Void... params) {
				getdata(getIntent().getStringExtra("code"));
				return null;
			}
			@Override
			protected void onPostExecute(Void result) {
				if (map != null && map.containsKey("city")) {
					city.setText(map.get("city").toString());
					en_city.setText(map.get("city_en").toString());
					date_yandweek.setText(map.get("date_y").toString() + " " + map.get("week").toString());
					temp1.setText(map.get("temp1").toString());
					wind1.setText(map.get("wind1").toString());
					weather1.setText(map.get("weather1").toString());
					FaceConversionUtil.dispToast(WeatherDetail.this, map.get("index_d").toString());
				} else {
					FaceConversionUtil.dispToast(WeatherDetail.this, "没有获得代号:" + getIntent().getStringExtra("code") + "天气");
					startActivity(new Intent(WeatherDetail.this, WeatherListMain.class));
				}
			}
		}.execute(null, null, null);
	}
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.weather_datail_main_layout);
		findview();
		get_data();
	}
	public void getdata(String code) {
		try {
			String result = ClientUtils.getweatherdetail("http://m.weather.com.cn/data/" + code + ".html");
			JSONObject o = new JSONObject(result);
			Log.d(TAG, result);
			String weatherinfo = o.getString("weatherinfo");
			JSONObject q = new JSONObject(weatherinfo);
			map = new HashMap<String, Object>();
			map.put("city", q.getString("city"));
			map.put("city_en", q.getString("city_en"));
			map.put("date_y", q.getString("date_y"));
			map.put("week", q.getString("week"));
			map.put("temp1", q.getString("temp1"));
			map.put("weather1", q.getString("weather1"));
			map.put("wind1", q.getString("wind1"));
			map.put("index_d", q.getString("index_d"));
		} catch (Exception e) {
			Log.d(TAG, e.toString());
		}
	}
	public void findview() {
		city = (TextView) findViewById(R.id.city);
		en_city = (TextView) findViewById(R.id.en_city);
		date_yandweek = (TextView) findViewById(R.id.date_yandweek);
		temp1 = (TextView) findViewById(R.id.temp1);
		weather1 = (TextView) findViewById(R.id.weather1);
		wind1 = (TextView) findViewById(R.id.wind1);
		close = (ImageView) findViewById(R.id.example_right);
		change = (ImageView) findViewById(R.id.change);
		change.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent i = new Intent(WeatherDetail.this, WeatherListMain.class);
				WeatherDetail.this.startActivity(i);
			}
		});
		close.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				finish();
			}
		});
	}
}
