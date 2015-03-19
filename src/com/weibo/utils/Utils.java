package com.weibo.utils;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;
import com.weibo.R;
import com.weibo.application.Mykey;
import com.weibo.utils.SwitchAnimationUtil.AnimationType;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.graphics.Bitmap;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.ImageView;

/**
 * @作者:陈华清
 * 
 * @版本:1.0
 * @生成时期:2014年8月2日 下午3:45:06
 * @com.api.card
 */
public class Utils {
	public static AnimationType mType = AnimationType.HORIZION_RIGHT;
	public static int sTheme = -1;
	public static boolean isActive = false;
	private List<Activity> list = new ArrayList<Activity>();
	private static Utils ea;
	private static final String TAG = "Utils";
	private Utils() {
	}
	public static void HideKeyboard(View v) {
		InputMethodManager imm = (InputMethodManager) v.getContext()
				.getSystemService(Context.INPUT_METHOD_SERVICE);
		if (imm.isActive()) {
			imm.hideSoftInputFromWindow(v.getApplicationWindowToken(), 0);
		}
	}

	public static void ShowKeyboard(View v) {
		InputMethodManager imm = (InputMethodManager) v.getContext()
				.getSystemService(Context.INPUT_METHOD_SERVICE);
		imm.showSoftInput(v, InputMethodManager.SHOW_FORCED);
	}

	public static int getAnimationTypeNum(Activity a) {
		SharedPreferences preferences = a.getSharedPreferences("anim",
				Context.MODE_WORLD_READABLE);
		int anim = preferences.getInt("anim", 1);
		return anim;
	}

	public static AnimationType getAnimationType(Activity a) {
		AnimationType ty = null;
		SharedPreferences preferences = a.getSharedPreferences("anim",
				Context.MODE_WORLD_READABLE);
		int anim = preferences.getInt("anim", 1);
		switch (anim) {
		case 1:
			ty = AnimationType.ALPHA;
			break;
		case 2:
			ty = AnimationType.FLIP_HORIZON;
			break;
		case 3:
			ty = AnimationType.FLIP_VERTICAL;
			break;
		case 4:
			ty = AnimationType.HORIZION_LEFT;
			break;
		case 5:
			ty = AnimationType.HORIZION_RIGHT;
			break;
		case 6:
			ty = AnimationType.HORIZON_CROSS;
			break;
		case 7:
			ty = AnimationType.ROTATE;
			break;
		case 8:
			ty = AnimationType.SCALE;
			break;
		}
		return ty;
	}

	public static void saveAnimationType(Activity a, int i) {
		SharedPreferences preferences = a.getSharedPreferences("anim",
				Context.MODE_WORLD_READABLE);
		Editor editor = preferences.edit();
		editor.putInt("anim", i);
		editor.commit();
	}

	public static Utils getInstance() {
		if (null == ea) {
			ea = new Utils();
		}
		return ea;
	}

	public void addActivity(Activity activity) {
		list.add(activity);
	}

	public void exit(Context context) {
		for (Activity activity : list) {
			activity.finish();
		}
		System.exit(0);
	}

	public static void changeToTheme(Activity activity, int theme) {
		sTheme = activity.getSharedPreferences("cons", Context.MODE_PRIVATE)
				.getInt("theme", R.style.MainDefault);
		sTheme = theme;
		activity.finish();
		activity.startActivity(new Intent(activity, activity.getClass()));
	}
	public static void onActivityCreateSetTheme(Activity activity) {
		switch (sTheme) {
		default:
		case 1:
			activity.setTheme(R.style.MainDefault);
			activity.getSharedPreferences("cons", Activity.MODE_PRIVATE).edit()
					.putInt("theme", R.style.MainDefault).commit();
			break;
		case 2:
			activity.setTheme(R.style.MainWhite);
			activity.getSharedPreferences("cons", Activity.MODE_PRIVATE).edit()
					.putInt("theme", R.style.MainWhite).commit();
			break;
		case 3:
			activity.setTheme(R.style.MainBlack);
			activity.getSharedPreferences("cons", Activity.MODE_PRIVATE).edit()
					.putInt("theme", R.style.MainBlack).commit();
			break;
		case 4:
			activity.setTheme(R.style.MainRed);
			activity.getSharedPreferences("cons", Activity.MODE_PRIVATE).edit()
					.putInt("theme", R.style.MainRed).commit();
			break;
		}
	}

	public static boolean iffirst(String name, Activity a) {
		try {
			boolean isfirst;
			SharedPreferences preferences = a.getSharedPreferences(name,
					Context.MODE_WORLD_READABLE);
			isfirst = preferences.getBoolean(name, true);
			if (isfirst) {
				isfirst = true;
			} else {
				isfirst = false;
			}
			Editor editor = preferences.edit();
			editor.putBoolean(name, false);
			editor.commit();
			return isfirst;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}

	public static void PutSetting(String name, boolean r, Activity mykey) {
		try {
			boolean setting;
			SharedPreferences preferences = mykey.getSharedPreferences(name,
					Context.MODE_WORLD_READABLE);
			setting = preferences.getBoolean(name, false);
			Editor editor = preferences.edit();
			editor.putBoolean(name, r);
			editor.commit();

		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	public static void PutSetting(String name, boolean r, Mykey mykey) {
		try {
			boolean setting;
			SharedPreferences preferences = mykey.getSharedPreferences(name,
					Context.MODE_WORLD_READABLE);
			setting = preferences.getBoolean(name, false);
			Editor editor = preferences.edit();
			editor.putBoolean(name, r);
			editor.commit();

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static boolean GetSetting(String name, Activity a) {
		try {
			boolean setting;
			SharedPreferences preferences = a.getSharedPreferences(name,
					Context.MODE_WORLD_READABLE);
			setting = preferences.getBoolean(name, false);
			return setting;

		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}

	public static Bundle getweather(Activity a) {
		SharedPreferences preferences = a.getSharedPreferences("weather",
				Context.MODE_WORLD_READABLE);
		String city = preferences.getString("city", "0");
		String code = preferences.getString("code", "0");
		Bundle b = new Bundle();
		b.putString("city", city);
		b.putString("code", code);
		return b;
	}

	public static void putweathercityandcode(Activity a, String city,
			String code) {
		SharedPreferences preferences = a.getSharedPreferences("weather",
				Context.MODE_WORLD_READABLE);
		Editor editor = preferences.edit();
		editor.putString("city", city);
		editor.putString("code", code);
		editor.commit();
	}

	public static boolean isNetworkConnected(Context context) {
		if (context != null) {
			ConnectivityManager mConnectivityManager = (ConnectivityManager) context
					.getSystemService(Context.CONNECTIVITY_SERVICE);
			NetworkInfo mNetworkInfo = mConnectivityManager
					.getActiveNetworkInfo();
			if (mNetworkInfo != null) {
				return mNetworkInfo.isAvailable();
			}
		}
		return false;
	}
	public static Bitmap createImage(int QR_WIDTH, int QR_HEIGHT, String text) {
		try {
			// 需要引入core包
			QRCodeWriter writer = new QRCodeWriter();
			Log.i(TAG, "生成的文本：" + text);
			if (text == null || "".equals(text) || text.length() < 1) {
				return null;
			} else {
				// 把输入的文本转为二维码
				BitMatrix martix = writer.encode(text, BarcodeFormat.QR_CODE,
						QR_WIDTH, QR_HEIGHT);
				Log.d(TAG, "w:" + martix.getWidth() + "h:" + martix.getHeight());
				Hashtable<EncodeHintType, String> hints = new Hashtable<EncodeHintType, String>();
				hints.put(EncodeHintType.CHARACTER_SET, "utf-8");
				BitMatrix bitMatrix = new QRCodeWriter().encode(text,
						BarcodeFormat.QR_CODE, QR_WIDTH, QR_HEIGHT, hints);
				int[] pixels = new int[QR_WIDTH * QR_HEIGHT];
				for (int y = 0; y < QR_HEIGHT; y++) {
					for (int x = 0; x < QR_WIDTH; x++) {
						if (bitMatrix.get(x, y)) {
							pixels[y * QR_WIDTH + x] = 0xff000000;
						} else {
							pixels[y * QR_WIDTH + x] = 0xffffffff;
						}
					}
				}
				Bitmap bitmap = Bitmap.createBitmap(QR_WIDTH, QR_HEIGHT,
						Bitmap.Config.ARGB_8888);
				bitmap.setPixels(pixels, 0, QR_WIDTH, 0, 0, QR_WIDTH, QR_HEIGHT);
				return bitmap;
			}
		} catch (WriterException e) {
			e.printStackTrace();
			return null;
		}
	}
}
