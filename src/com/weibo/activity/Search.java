package com.weibo.activity;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

import com.iflytek.ui.RecognizerDialog;
import com.weibo.adapters.SearchAche_itemAdapter;
import com.weibo.adapters.WeiBoMainAdapter;
import com.weibo.db.DataHelper;
import com.weibo.utils.ACache;
import com.weibo.utils.FaceConversionUtil;
import com.weibo.view.widget.KeywordsFlow;
import com.weibo.view.widget.LoadingAlertAnim;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import com.weibo.R;
public class Search extends BaseActivity implements View.OnClickListener {
	ImageView imageview1;
	TextView textview1;
	EditText edittext1;
	ListView listview;
	Button button1, button2;
	ACache mcache;
	SearchAche_itemAdapter adapter;
	LoadingAlertAnim loading;
	protected static final int find = 0;
	protected static final int find1 = 1;
	protected static final int error = 2;
	protected static final int error1 = 3;
	int number = 1;
	String word = null;
	String word2 = null;
	WeiBoMainAdapter adapter1;
	ArrayList<HashMap<String, Object>> listitem, listitem1;
	HashMap<String, Object> map, map1;
	String s;
	private KeywordsFlow keywordsFlow;
	String[] keywords;
	private static final String TAG = "Search.java";
	DataHelper helper;
	private RecognizerDialog rd;
	public void FindSearchCache(String key) {
		try {
			if (key.equals("word_key")) {
				helper = new DataHelper(getApplicationContext());
				keywords = helper.getusersearchlist();
				Log.d(TAG, "从数据库中获取的keywords大小" + keywords.length);
			} else if (key.equals("word_key2")) {
				helper = new DataHelper(getApplicationContext());
				keywords = helper.getweibokeylist();
				Log.d(TAG, "从数据库中获取的keywords大小" + keywords.length);
			}
		} catch (NullPointerException e) {
			e.printStackTrace();
		}
	}
	private boolean FirstSearch;
	public void iffirst() {
		try {
			SharedPreferences preferences = getSharedPreferences("FirstSearch",
					MODE_WORLD_READABLE);
			FirstSearch = preferences.getBoolean("FirstSearch", true);
			if (FirstSearch) {
				final Dialog dialog = new Dialog(this,
						R.style.Dialog_Fullscreen);
				dialog.setContentView(R.layout.first_login_tab1_pic);
				ImageView close = (ImageView) dialog.findViewById(R.id.close);
				close.setImageResource(R.drawable.first_search);
				close.setOnClickListener(new View.OnClickListener() {
					@Override
					public void onClick(View v) {
						// TODO Auto-generated method stub
						dialog.dismiss();
					}
				});
				dialog.show();
			} else {
				Log.d(TAG, "不是第一次进入Search.java");
			}
			Editor editor = preferences.edit();
			editor.putBoolean("FirstSearch", false);
			editor.commit();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private boolean showSettings = false;
	private SensorManager sensorManager;
	protected void onResume() {
		super.onResume();
		// /返回true表示注册成功，flase则反之
		sensorManager.registerListener(shakeListener,
				sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER),
				SensorManager.SENSOR_DELAY_FASTEST);
		showSettings = false;
		if (s.equals("1")) {
			FindSearchCache("word_key");
			feedKeywordsFlow(keywordsFlow, keywords);
			keywordsFlow.go2Show(KeywordsFlow.ANIMATION_OUT);
		} else if (s.equals("2")) {
			FindSearchCache("word_key2");
			feedKeywordsFlow(keywordsFlow, keywords);
			keywordsFlow.go2Show(KeywordsFlow.ANIMATION_OUT);
		}
	}
	protected void onStop() {
		super.onStop();
		sensorManager.unregisterListener(shakeListener);
	}
	protected void onPause() {
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
				if (s.equals("1")) {
					FindSearchCache("word_key");
					feedKeywordsFlow(keywordsFlow, keywords);
					keywordsFlow.go2Show(KeywordsFlow.ANIMATION_OUT);
				} else if (s.equals("2")) {
					FindSearchCache("word_key2");
					feedKeywordsFlow(keywordsFlow, keywords);
					keywordsFlow.go2Show(KeywordsFlow.ANIMATION_OUT);
				}
				showSettings = false;
			}
		}
		public void onAccuracyChanged(Sensor sensor, int accuracy) {
		}
	};
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.do_search);
		findview();
		iffirst();
		sensorManager = (SensorManager) this.getBaseContext().getSystemService(
				Context.SENSOR_SERVICE);
		s = getIntent().getStringExtra("a");
		if (s.equals("1")) {
			textview1.setText("搜索用户");
			edittext1.setHint("请输入用户关键字");
			FindSearchCache("word_key");
			feedKeywordsFlow(keywordsFlow, keywords);
			keywordsFlow.go2Show(KeywordsFlow.ANIMATION_IN);
		} else if (s.equals("2")) {
			textview1.setText("搜索微博");
			edittext1.setHint("请输入微博关键字");
			FindSearchCache("word_key2");
			feedKeywordsFlow(keywordsFlow, keywords);
			keywordsFlow.go2Show(KeywordsFlow.ANIMATION_IN);
		}
	}
	public void findview() {
		imageview1 = (ImageView) findViewById(R.id.example_right);
		imageview1.setOnClickListener(this);
		textview1 = (TextView) findViewById(R.id.titleText);
		edittext1 = (EditText) findViewById(R.id.editText1);
		button1 = (Button) findViewById(R.id.button1);
		button1.setOnClickListener(this);
		button2 = (Button) findViewById(R.id.button2);
		button2.setOnClickListener(this);
		keywordsFlow = (KeywordsFlow) findViewById(R.id.keywordsFlow1);
		keywordsFlow.setOnItemClickListener(this);
		keywordsFlow.setDuration(1000);
		rd = new RecognizerDialog(this, "appid=50e1b967");
	}
	private static void feedKeywordsFlow(KeywordsFlow keywordsFlow, String[] arr) {
		if (arr != null && arr.length > 0) {
			Random random = new Random();
			for (int i = 0; i < KeywordsFlow.MAX; i++) {
				int ran = random.nextInt(arr.length);
				String tmp = arr[ran];
				keywordsFlow.feedKeyword(tmp);
			}
		}
	}
	public void start(String word_key, String id) {
		Intent i = new Intent(Search.this, ShowSearchResult.class);
		Bundle b = new Bundle();
		b.putString("word_key", word_key);
		b.putString("id", id);
		i.putExtras(b);
		Search.this.startActivity(i);
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		if (v == imageview1) {
			finish();
		} else if (v == button1) {
			if (s.equals("1")) {
				helper = new DataHelper(getApplicationContext());
				helper.saveUserSearchKey(edittext1.getText().toString());
				start(edittext1.getText().toString(), "0");
			} else if (s.equals("2")) {
				helper = new DataHelper(getApplicationContext());
				helper.saveWeiBoSearchKey(edittext1.getText().toString());
				start(edittext1.getText().toString(), "1");
			}
		} else if (v == button2) {
			FaceConversionUtil.showReconigizerDialog(rd, edittext1);
		} else if (v instanceof TextView) {
			if (s.equals("1")) {
				String keyword = ((TextView) v).getText().toString();
				start(keyword, "0");
			} else if (s.equals("2")) {
				String keyword = ((TextView) v).getText().toString();
				start(keyword, "1");
			}
		}
	}
}
