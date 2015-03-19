package com.weibo.lib.photodeal;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.message.BasicNameValuePair;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.ColorDrawable;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.provider.MediaStore;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.iflytek.ui.RecognizerDialog;
import com.weibo.activity.BaseActivity;
import com.weibo.activity.Find;
import com.weibo.R;
import com.weibo.activity.Tab1Activity;
import com.weibo.application.*;
import com.weibo.utils.ClientUtils;
import com.weibo.utils.Common;
import com.weibo.utils.FaceConversionUtil;
import com.weibo.utils.FileUtils;
import com.weibo.utils.Utils;
import com.weibo.view.widget.LoadingAlertAnim;

public class PublishedActivity extends BaseActivity implements OnClickListener {
	private GridView noScrollgridview;
	private GridAdapter adapter;
	private TextView activity_selectimg_send, close, wordCounterView;
	private ImageButton imgChooseBtn, huatibtn, find_me = null;
	private EditText weiboContentText = null;
	private static final String TAG = "PublishedActivity";
	InputMethodManager imm;
	SensorManager sensorManager;
	ImageView voice;
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_selectimg);
		sensorManager = (SensorManager) this.getBaseContext().getSystemService(Context.SENSOR_SERVICE);
		imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
		Init();
		Common.CLOSE = true;
		Tab1Activity.i = 0;
		if (Common.change) {
			adapter.notifyDataSetChanged();
			Common.change = false;
		}
		Utils.getInstance().addActivity(this);
	}

	private boolean showSettings = false;// /SensorManager的监听程序
	private final SensorEventListener shakeListener = new SensorEventListener() {
		@Override
		public void onSensorChanged(SensorEvent se) {
			float x = se.values[0];
			float y = se.values[1];
			float z = se.values[2];
			float shake = x * x + y * y + z * z;
			if ((!showSettings) && (shake > 1100)) {
				showSettings = true;
				onClick(activity_selectimg_send);
				// 开始处理摇晃事件
				Log.d("Sharking", "检测到摇晃");
			}
		}

		public void onAccuracyChanged(Sensor sensor, int accuracy) {
		}
	};

	protected void onResume() {
		super.onResume();
		sensorManager.registerListener(shakeListener, sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER), SensorManager.SENSOR_DELAY_FASTEST);
		showSettings = false;
		// /返回true表示注册成功，flase则反之
		if (((Mykey) getApplication()).getFind_me() != null && ((Mykey) getApplication()).getFind_me().length() > 0) {
			weiboContentText.append("@" + ((Mykey) getApplication()).getFind_me() + " ");
			((Mykey) getApplication()).setFind_me(null);
		} else {
			Log.d(TAG, "没有检测到@我的好友!");
		}
		Log.d(TAG, "Back");
	}

	String result = null;

	public void upload(final String content, final List<String> list) {
		new AsyncTask<Void, Void, Void>() {
			protected Void doInBackground(Void... param) {
				try {
					if (list != null && list.size() > 0) {
						result = ClientUtils.post_with_pic(ClientUtils.BASE_URL + PublishedActivity.this.getString(ClientUtils.WeiboStatuses_upload) + "&user_id=" + ((Mykey) getApplication()).getUid() + "&content=" + content + "&from=3", list, PublishedActivity.this);
						Log.d(TAG, ClientUtils.BASE_URL + PublishedActivity.this.getString(ClientUtils.WeiboStatuses_upload) + "&user_id=" + ((Mykey) getApplication()).getUid() + "&content=" + content + "&from=3");
					} else {
						Log.d(TAG, "不带图片的微博！");
						List<BasicNameValuePair> params = new ArrayList<BasicNameValuePair>();
						params.add(new BasicNameValuePair("oauth_token", ((Mykey) getApplication()).getOauth_token()));
						params.add(new BasicNameValuePair("oauth_token_secret", ((Mykey) getApplication()).getOauth_token_secret()));
						params.add(new BasicNameValuePair("user_id", ((Mykey) getApplication()).getUid()));
						params.add(new BasicNameValuePair("content", content));
						params.add(new BasicNameValuePair("from", "3"));
						result = ClientUtils.post_str(ClientUtils.BASE_URL + PublishedActivity.this.getString(ClientUtils.WeiboStatuses_update), params, PublishedActivity.this);
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
				return null;
			}

			@Override
			protected void onPostExecute(Void resut) {
				if (!result.equals("0")) {
					if (Bimp.bmp != null && Bimp.bmp.size() > 0) {
						Bimp.bmp.clear();
						Bimp.drr.clear();
						Bimp.max = 0;
						FileUtils.deleteDir();
					}
					if (loading != null) {
						loading.dismiss();
					}
					FaceConversionUtil.dispToast(PublishedActivity.this, "发送成功");
					adapter.notifyDataSetChanged();
					((Mykey) getApplication()).setIsdel("1");
					finish();
				} else {
					FaceConversionUtil.dispToast(PublishedActivity.this, "发送失败!");
				}
			}
		}.execute(null, null, null);
	}

	private void textCountSet() {
		String textContent = weiboContentText.getText().toString();
		int currentLength = textContent.length();
		if (currentLength > 0) {
			FaceConversionUtil.MygetColor(PublishedActivity.this, activity_selectimg_send, R.color.yellow);
			FaceConversionUtil.MygetColor(PublishedActivity.this, close, R.color.yellow);
		} else {
			FaceConversionUtil.MygetColor(PublishedActivity.this, activity_selectimg_send, R.color.white);
			FaceConversionUtil.MygetColor(PublishedActivity.this, close, R.color.white);
		}
		if (currentLength <= 140) {
			wordCounterView.setTextColor(Color.BLACK);
			wordCounterView.setText(String.valueOf(140 - textContent.length()));
		} else {
			wordCounterView.setTextColor(Color.RED);
			wordCounterView.setText(String.valueOf(140 - currentLength));
		}

	}

	TextWatcher watcher = new TextWatcher() {
		public void onTextChanged(CharSequence s, int start, int before, int count) {
			textCountSet();
		}

		public void beforeTextChanged(CharSequence s, int start, int count, int after) {
			textCountSet();
		}

		@Override
		public void afterTextChanged(Editable s) {
			textCountSet();
		}
	};

	public void Init() {
		rd = new RecognizerDialog(this, "appid=50e1b967");
		voice = (ImageView) findViewById(R.id.voice);
		voice.setOnClickListener(this);
		weiboContentText = (EditText) findViewById(R.id.share_content);
		close = (TextView) findViewById(R.id.close);
		close.setOnClickListener(this);
		wordCounterView = (TextView) findViewById(R.id.share_word_counter);
		huatibtn = (ImageButton) findViewById(R.id.huatibtn);
		huatibtn.setOnClickListener(this);
		find_me = (ImageButton) findViewById(R.id.findmebtn);
		find_me.setOnClickListener(this);
		imgChooseBtn = (ImageButton) findViewById(R.id.share_imagechoose);
		imgChooseBtn.setOnClickListener(this);
		weiboContentText.addTextChangedListener(watcher);
		noScrollgridview = (GridView) findViewById(R.id.noScrollgridview);
		noScrollgridview.setSelector(new ColorDrawable(Color.TRANSPARENT));
		adapter = new GridAdapter(this);
		adapter.update();
		noScrollgridview.setAdapter(adapter);
		noScrollgridview.setOnItemClickListener(new OnItemClickListener() {
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
				if (arg2 == Bimp.bmp.size()) {
					imm.hideSoftInputFromWindow(arg0.getWindowToken(), 0);
					new PopupWindows(PublishedActivity.this, noScrollgridview);
				} else {
					Intent intent = new Intent(PublishedActivity.this, PhotoActivity.class);
					intent.putExtra("ID", arg2);
					startActivity(intent);
				}
			}
		});
		activity_selectimg_send = (TextView) findViewById(R.id.activity_selectimg_send);
		activity_selectimg_send.setOnClickListener(this);
	}

	@SuppressLint("HandlerLeak")
	public class GridAdapter extends BaseAdapter {
		private LayoutInflater inflater; // 视图容器
		private int selectedPosition = -1;// 选中的位置
		private boolean shape;

		public boolean isShape() {
			return shape;
		}

		public void setShape(boolean shape) {
			this.shape = shape;
		}

		public GridAdapter(Context context) {
			inflater = LayoutInflater.from(context);
		}

		public void update() {
			loading();
		}

		public int getCount() {
			return (Bimp.bmp.size() + 1);
		}

		public Object getItem(int arg0) {

			return null;
		}

		public long getItemId(int arg0) {

			return 0;
		}

		public void setSelectedPosition(int position) {
			selectedPosition = position;
		}

		public int getSelectedPosition() {
			return selectedPosition;
		}

		/**
		 * ListView Item设置
		 */
		public View getView(int position, View convertView, ViewGroup parent) {
			final int coord = position;
			ViewHolder holder = null;
			if (convertView == null) {
				convertView = inflater.inflate(R.layout.item_published_grida, parent, false);
				holder = new ViewHolder();
				holder.image = (ImageView) convertView.findViewById(R.id.item_grida_image);
				convertView.setTag(holder);
			} else {
				holder = (ViewHolder) convertView.getTag();
			}

			if (position == Bimp.bmp.size()) {
				holder.image.setImageBitmap(BitmapFactory.decodeResource(getResources(), R.drawable.icon_addpic_unfocused));
				if (position == 9) {
					holder.image.setVisibility(View.GONE);
				}
			} else {
				holder.image.setImageBitmap(Bimp.bmp.get(position));
			}

			return convertView;
		}

		public class ViewHolder {
			public ImageView image;
		}

		Handler handler = new Handler() {
			public void handleMessage(Message msg) {
				switch (msg.what) {
					case 1 :
						adapter.notifyDataSetChanged();
						break;
				}
				super.handleMessage(msg);
			}
		};

		public void loading() {
			new Thread(new Runnable() {
				public void run() {
					while (true) {
						if (Bimp.max == Bimp.drr.size()) {
							Message message = new Message();
							message.what = 1;
							handler.sendMessage(message);
							break;
						} else {
							try {
								String path = Bimp.drr.get(Bimp.max);
								System.out.println(path);
								Bitmap bm = Bimp.revitionImageSize(path);
								Bimp.bmp.add(bm);
								String newStr = path.substring(path.lastIndexOf("/") + 1, path.lastIndexOf("."));
								FileUtils.saveBitmap(bm, "" + newStr);
								Bimp.max += 1;
								Message message = new Message();
								message.what = 1;
								handler.sendMessage(message);
							} catch (IOException e) {

								e.printStackTrace();
							}
						}
					}
				}
			}).start();
		}
	}

	public String getString(String s) {
		String path = null;
		if (s == null)
			return "";
		for (int i = s.length() - 1; i > 0; i++) {
			s.charAt(i);
		}
		return path;
	}

	protected void onRestart() {
		adapter.update();
		super.onRestart();
	}

	protected void onStop() {
		super.onStop();
		// /注消
		sensorManager.unregisterListener(shakeListener);
	}

	protected void onPause() {
		super.onPause();
		// /注消
		sensorManager.unregisterListener(shakeListener);
	}

	public class PopupWindows extends PopupWindow {
		public PopupWindows(Context mContext, View parent) {
			View view = View.inflate(mContext, R.layout.item_popupwindows, null);
			view.startAnimation(AnimationUtils.loadAnimation(mContext, R.anim.fade_ins));
			LinearLayout ll_popup = (LinearLayout) view.findViewById(R.id.ll_popup);
			ll_popup.startAnimation(AnimationUtils.loadAnimation(mContext, R.anim.push_bottom_in_2));
			setWidth(LayoutParams.FILL_PARENT);
			setHeight(LayoutParams.FILL_PARENT);
			setBackgroundDrawable(new BitmapDrawable());
			setFocusable(true);
			setOutsideTouchable(true);
			setContentView(view);
			showAtLocation(parent, Gravity.BOTTOM, 0, 0);
			update();
			Button bt1 = (Button) view.findViewById(R.id.item_popupwindows_camera);
			Button bt2 = (Button) view.findViewById(R.id.item_popupwindows_Photo);
			Button bt3 = (Button) view.findViewById(R.id.item_popupwindows_cancel);
			bt1.setOnClickListener(new OnClickListener() {
				public void onClick(View v) {
					photo();
					dismiss();
				}
			});
			bt2.setOnClickListener(new OnClickListener() {
				public void onClick(View v) {
					Intent intent = new Intent(PublishedActivity.this, TestPicActivity.class);
					startActivity(intent);
					dismiss();
				}
			});
			bt3.setOnClickListener(new OnClickListener() {
				public void onClick(View v) {
					dismiss();
				}
			});

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
		switch (requestCode) {
			case TAKE_PICTURE :
				if (Bimp.drr.size() < 9 && resultCode == -1) {
					Bimp.drr.add(path);
				}
				adapter.notifyDataSetChanged();
				break;
		}
	}

	LoadingAlertAnim loading;
	private RecognizerDialog rd;

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		if (v == close) {
			if (Bimp.bmp != null && Bimp.bmp.size() > 0) {
				Bimp.bmp.clear();
				Bimp.drr.clear();
				Bimp.max = 0;
				FileUtils.deleteDir();
			}
			finish();
		} else if (v == imgChooseBtn) {
			imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
			new PopupWindows(PublishedActivity.this, noScrollgridview);
		} else if (v == activity_selectimg_send) {
			List<String> list = new ArrayList<String>();
			for (int i = 0; i < Bimp.drr.size(); i++) {
				String Str = Bimp.drr.get(i).substring(Bimp.drr.get(i).lastIndexOf("/") + 1, Bimp.drr.get(i).lastIndexOf("."));
				list.add(FileUtils.SDPATH + Str + ".JPEG");
			}
			if (weiboContentText.getText().length() > 0 || list.size() > 0) {
				if (list.size() > 9) {
					FaceConversionUtil.dispToast(PublishedActivity.this, "由于服务器限制暂时只支持九张图上传!请重新选择图片!");
				} else if (list.size() > 0) {
					loading = new LoadingAlertAnim(PublishedActivity.this, R.style.MyDialogStyle, "发送中...");
					loading.setCanceledOnTouchOutside(false);
					loading.show();
					upload(weiboContentText.getText().toString(), list);
				} else {
					loading = new LoadingAlertAnim(PublishedActivity.this, R.style.MyDialogStyle, "发送中...");
					loading.setCanceledOnTouchOutside(false);
					loading.show();
					upload(weiboContentText.getText().toString(), null);
				}
			} else {
				Animation animation = AnimationUtils.loadAnimation(this, R.anim.shake);
				weiboContentText.setAnimation(animation);
				weiboContentText.setError("请选择图片，或输入文字!");
			}

		} else if (v == huatibtn) {
			int index = weiboContentText.getSelectionStart();// 获取光标所在位置
			String text = "#请输入一个话题吧#";
			Editable edit = weiboContentText.getEditableText();// 获取EditText的文字
			if (index < 0 || index >= edit.length()) {
				edit.append(text);
			} else {
				edit.insert(index, text);// 光标所在位置插入文字
			}
			int j = weiboContentText.getText().toString().indexOf("#");
			weiboContentText.setSelection(j + 1, j + 9);
			Utils.ShowKeyboard(v);
		} else if (v == find_me) {
			Intent inten = new Intent(PublishedActivity.this, Find.class);
			PublishedActivity.this.startActivity(inten);
		} else if (v == voice) {
			FaceConversionUtil.showReconigizerDialog(rd, weiboContentText);
		}
	}

}
