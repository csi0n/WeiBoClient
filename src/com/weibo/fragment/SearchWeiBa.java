/**weibotest
 * 
 *
 *TODO
 */
package com.weibo.fragment;
import java.util.Random;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.iflytek.ui.RecognizerDialog;
import com.weibo.activity.MyWeiBaSetting;
import com.weibo.application.Mykey;
import com.weibo.R;
import com.weibo.db.DataHelper;
import com.weibo.utils.FaceConversionUtil;
import com.weibo.view.widget.KeywordsFlow;

/**
 * @作者:陈华清
 * 
 * @版本:1.0
 * @生成时期:2014年7月28日 上午7:44:20
 * @com.frame
 */
public class SearchWeiBa extends Fragment implements View.OnClickListener {
	Button search, voice;
	EditText key;
	MyWeiBaSetting mainActivity;
	DataHelper helper;
	private KeywordsFlow keywordsFlow;
	String[] keywords;
	private RecognizerDialog rd;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.search_weiba_layout, container,
				false);
		search = (Button) view.findViewById(R.id.button1);
		search.setOnClickListener(this);
		voice = (Button) view.findViewById(R.id.button2);
		voice.setOnClickListener(this);
		key = (EditText) view.findViewById(R.id.editText1);
		keywordsFlow = (KeywordsFlow) view.findViewById(R.id.keywordsFlow1);
		keywordsFlow.setOnItemClickListener(this);
		mainActivity = (MyWeiBaSetting) getActivity();
		sensorManager = (SensorManager) mainActivity.getBaseContext().getSystemService(
				Context.SENSOR_SERVICE);
		helper = new DataHelper(mainActivity.getApplicationContext());
		keywords = helper.getweibasearchlist();
		feedKeywordsFlow(keywordsFlow, keywords);
		keywordsFlow.go2Show(KeywordsFlow.ANIMATION_IN);
		rd = new RecognizerDialog(mainActivity, "appid=50e1b967");
		return view;
	}

	public static void feedKeywordsFlow(KeywordsFlow keywordsFlow, String[] arr) {
		if (arr != null && arr.length > 0) {
			Random random = new Random();
			for (int i = 0; i < KeywordsFlow.MAX; i++) {
				int ran = random.nextInt(arr.length);
				String tmp = arr[ran];
				keywordsFlow.feedKeyword(tmp);
			}
		}
	}

	private void switchFragment(Fragment fragment) {
		if (getActivity() == null)
			return;
		MyWeiBaSetting ra = (MyWeiBaSetting) getActivity();
		ra.switchContent(fragment);
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		if (v == search) {
			if (key.getText().length() == 0) {
				key.setError("请输入搜索内容！");
			} else if (key.getText().equals(" ")) {
				key.setError("不能输入空白!");
			} else {
				((Mykey) mainActivity.getApplication()).setWeibasearch_key(key
						.getText().toString());
				helper = new DataHelper(mainActivity.getApplicationContext());
				helper.saveWeiBaSearchKey(key.getText().toString());
				switchFragment(new ShowSearchResult());
			}
		} else if (v == voice) {
			FaceConversionUtil.showReconigizerDialog(rd, key);
		} else if (v instanceof TextView) {
			String keyword = ((TextView) v).getText().toString();
			((Mykey) mainActivity.getApplication()).setWeibasearch_key(keyword);
			switchFragment(new ShowSearchResult());
		}
	}
	private boolean showSettings = false;
	private SensorManager sensorManager;
	public void onResume() {
		super.onResume();
		// /返回true表示注册成功，flase则反之
		sensorManager.registerListener(shakeListener,
				sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER),
				SensorManager.SENSOR_DELAY_FASTEST);
		showSettings = false;
	}
	public void onStop() {
		super.onStop();
		sensorManager.unregisterListener(shakeListener);
	}
	public void onPause() {
		super.onPause();
		sensorManager.unregisterListener(shakeListener);
	}
	private final SensorEventListener shakeListener = new SensorEventListener() {
		@Override
		public void onSensorChanged(SensorEvent se) {
			float x = se.values[0];
			float y = se.values[1];
			float z = se.values[2];
			float shake = x * x + y * y + z * z;
			if ((!showSettings) && (shake > 800)) {
				showSettings = true;
				// 开始处理摇晃事件
				Log.d("Sharking", "检测到摇晃");
				helper = new DataHelper(mainActivity.getApplicationContext());
				keywords = helper.getweibatiesearchlist();
				feedKeywordsFlow(keywordsFlow, keywords);
				keywordsFlow.go2Show(KeywordsFlow.ANIMATION_IN);
				showSettings = false;
			}
		}

		public void onAccuracyChanged(Sensor sensor, int accuracy) {
		}
	};
}
