package com.weibo.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.net.URL;
import java.net.URLConnection;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.http.conn.util.InetAddressUtils;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Application;
import android.content.ContentUris;
import android.content.Context;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.content.res.Resources;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.telephony.TelephonyManager;
import android.text.ClipboardManager;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.TextUtils;
import android.text.style.ImageSpan;
import android.text.util.Linkify;
import android.text.util.Linkify.MatchFilter;
import android.text.util.Linkify.TransformFilter;
import android.util.Log;
import android.view.Gravity;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.TranslateAnimation;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.iflytek.speech.RecognizerResult;
import com.iflytek.speech.SpeechConfig.RATE;
import com.iflytek.speech.SpeechError;
import com.iflytek.ui.RecognizerDialog;
import com.iflytek.ui.RecognizerDialogListener;
import com.weibo.R;
import com.weibo.card.ChatEmoji;
import com.weibo.card.WeatherCard;
import com.weibo.lib.Defs;
import com.weibo.lib.zrclist.SimpleFooter;
import com.weibo.lib.zrclist.SimpleHeader;
import com.weibo.lib.zrclist.ZrcListView;

/**
 * @作者:陈华清
 * 
 * @版本:1.0
 * @生成时期:2014年7月19日 上午7:17:51
 * @com.api
 */
@SuppressLint("SdCardPath")
public class FaceConversionUtil {
	/** 每一页表情的个数 */
	private int pageSize = 20;
	private static FaceConversionUtil mFaceConversionUtil;
	/** 保存于内存中的表情HashMap */
	private HashMap<String, String> emojiMap = new HashMap<String, String>();
	/** 保存于内存中的表情集合 */
	private List<ChatEmoji> emojis = new ArrayList<ChatEmoji>();
	/** 表情分页的结果集合 */
	public List<List<ChatEmoji>> emojiLists = new ArrayList<List<ChatEmoji>>();
	private static final String TAG = "FaceConversionUyil.java";
	private static final int CONNECT_TIMEOUT = 5000;
	private static final int READ_TIMEOUT = 10000;
	public static List<WeatherCard> weather = new ArrayList<WeatherCard>();

	private FaceConversionUtil() {
	}

	public static FaceConversionUtil getInstace() {
		if (mFaceConversionUtil == null) {
			mFaceConversionUtil = new FaceConversionUtil();
		}
		return mFaceConversionUtil;
	}

	/**
	 * 得到一个SpanableString对象，通过传入的字符串,并进行正则判断
	 * 
	 */
	public SpannableString getExpressionString(Context context, String str) {
		Pattern p_script = Pattern.compile("<.*?>", Pattern.CASE_INSENSITIVE);
		Matcher m_script = p_script.matcher(str);
		str = m_script.replaceAll("");
		SpannableString spannableString = new SpannableString(str);
		// 正则表达式比配字符串里是否含有表情，如： 我好[开心]啊
		String zhengze = "\\[[^\\]]+\\]";
		// 通过传入的正则表达式来生成一个pattern
		Pattern sinaPatten = Pattern.compile(zhengze, Pattern.CASE_INSENSITIVE);
		try {
			dealExpression(context, spannableString, sinaPatten, 0);
		} catch (Exception e) {
			Log.e("dealExpression", e.getMessage());
		}
		return spannableString;
	}

	/**
	 * 添加表情
	 * 
	 */
	public SpannableString addFace(Context context, int imgId,
			String spannableString) {
		if (TextUtils.isEmpty(spannableString)) {
			return null;
		}
		Bitmap bitmap = BitmapFactory.decodeResource(context.getResources(),
				imgId);
		bitmap = Bitmap.createScaledBitmap(bitmap, 35, 35, true);
		ImageSpan imageSpan = new ImageSpan(context, bitmap);
		SpannableString spannable = new SpannableString(spannableString);
		spannable.setSpan(imageSpan, 0, spannableString.length(),
				Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
		return spannable;
	}

	/**
	 * 对spanableString进行正则判断，如果符合要求，则以表情图片代替
	 *
	 */
	private void dealExpression(Context context,
			SpannableString spannableString, Pattern patten, int start)
			throws Exception {

		Matcher matcher = patten.matcher(spannableString);

		while (matcher.find()) {
			String key = matcher.group();
			// 返回第一个字符的索引的文本匹配整个正则表达式,ture 则继续递归
			if (matcher.start() < start) {
				continue;
			}
			String value = emojiMap.get(key);
			if (TextUtils.isEmpty(value)) {
				continue;
			}
			int resId = context.getResources().getIdentifier(value, "drawable",
					context.getPackageName());
			// 通过上面匹配得到的字符串来生成图片资源id
			// Field field=R.drawable.class.getDeclaredField(value);
			// int resId=Integer.parseInt(field.get(null).toString());
			if (resId != 0) {
				Bitmap bitmap = BitmapFactory.decodeResource(
						context.getResources(), resId);
				bitmap = Bitmap.createScaledBitmap(bitmap, 70,70, true);
				// 通过图片资源id来得到bitmap，用一个ImageSpan来包装
				ImageSpan imageSpan = new ImageSpan(bitmap);
				// 计算该图片名字的长度，也就是要替换的字符串的长度
				int end = matcher.start() + key.length();
				// 将该图片替换字符串中规定的位置中
				spannableString.setSpan(imageSpan, matcher.start(), end,
						Spannable.SPAN_INCLUSIVE_EXCLUSIVE);
				if (end < spannableString.length()) {
					// 如果整个字符串还未验证完，则继续。。
					dealExpression(context, spannableString, patten, end);
				}
				break;
			}
		}
	}

	public void getFileText(Context context) {
		ParseData(FileUtils.getEmojiFile(context), context);
	}

	public void getWeatherText(Context context) {
		ParseWeather(FileUtils.getWheatherFile(context), context);
	}

	private void ParseWeather(List<String> data, Context context) {
		if (data == null) {
			return;
		}
		try {
			for (String str : data) {
				String[] text = str.split("=");
				String code = text[0];
				String city = text[1];
				WeatherCard weathe = new WeatherCard();
				weathe.setCity(city);
				weathe.setCode(code);
				weathe.setSortLetters(city);
				weather.add(weathe);
			}
			Log.d(TAG, "找到" + weather.size() + "个城市");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 解析字符
	 * 
	 * @param data
	 */
	private void ParseData(List<String> data, Context context) {
		if (data == null) {
			return;
		}
		ChatEmoji emojEentry;
		try {
			for (String str : data) {
				String[] text = str.split(",");
				String fileName = text[0]
						.substring(0, text[0].lastIndexOf("."));
				emojiMap.put(text[1], fileName);
				int resID = context.getResources().getIdentifier(fileName,
						"drawable", context.getPackageName());
				if (resID != 0) {
					emojEentry = new ChatEmoji();
					emojEentry.setId(resID);
					emojEentry.setCharacter(text[1]);
					emojEentry.setFaceName(fileName);
					emojis.add(emojEentry);
				}
			}
			int pageCount = (int) Math.ceil(emojis.size() / 20 + 0.1);
			Log.d(TAG, "找到的表情个数为:" + emojis.size());
			for (int i = 0; i < pageCount; i++) {
				emojiLists.add(getData(i));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 获取分页数据
	 * 
	 * @param page
	 * @return
	 */
	private List<ChatEmoji> getData(int page) {
		int startIndex = page * pageSize;
		int endIndex = startIndex + pageSize;

		if (endIndex > emojis.size()) {
			endIndex = emojis.size();
		}
		// 不这么写，会在viewpager加载中报集合操作异常，我也不知道为什么
		List<ChatEmoji> list = new ArrayList<ChatEmoji>();
		list.addAll(emojis.subList(startIndex, endIndex));
		if (list.size() < pageSize) {
			for (int i = list.size(); i < pageSize; i++) {
				ChatEmoji object = new ChatEmoji();
				list.add(object);
			}
		}
		if (list.size() == pageSize) {
			ChatEmoji object = new ChatEmoji();
			object.setId(R.drawable.face_del_icon);
			list.add(object);
		}
		return list;
	}
	public static void extractMention2Link(TextView v) {
		v.setAutoLinkMask(0);
		v.setLinkTextColor(0xff6CA6CD);
		Pattern mentionsPattern = Pattern.compile("@(\\w+?)(?=\\W|$)(.)");
		String mentionsScheme = String.format("%s/?%s=", Defs.MENTIONS_SCHEMA,
				Defs.PARAM_UID);
		Linkify.addLinks(v, mentionsPattern, mentionsScheme, new MatchFilter() {
			@Override
			public boolean acceptMatch(CharSequence s, int start, int end) {
				return s.charAt(end - 1) != '.';
			}
		}, new TransformFilter() {
			@Override
			public String transformUrl(Matcher match, String url) {
				Log.d(TAG, match.group(1));
				return match.group(1);
			}
		});

		Pattern trendsPattern = Pattern.compile("#(\\w+?)#");
		String trendsScheme = String.format("%s/?%s=", Defs.TRENDS_SCHEMA,
				Defs.PARAM_UID);
		Linkify.addLinks(v, trendsPattern, trendsScheme, null,
				new TransformFilter() {
					@Override
					public String transformUrl(Matcher match, String url) {
						Log.d(TAG, match.group(1));
						return match.group(1);
					}
				});
		Pattern phonePattern = Pattern.compile("1[0-9]{10}");
		String phoneScheme = String.format("%s/?%s=", Defs.PHONE_SCHEMA,
				Defs.PARAM_UID);
		Linkify.addLinks(v, phonePattern, phoneScheme);
	}

	@SuppressLint("ResourceAsColor")
	public static void StartListView(Activity act, ZrcListView listview) {
		/* float density = act.getResources().getDisplayMetrics().density;
		 listview.setFirstTopOffset((int) (50 * density));*/
		// 设置下拉刷新的样式
		SimpleHeader header = new SimpleHeader(act);
		header.setTextColor(0xff6CA6CD);
		header.setCircleColor(0xff33bbee);
		listview.setHeadable(header);
		// 设置加载更多的样式
		SimpleFooter footer = new SimpleFooter(act);
		footer.setCircleColor(0xff6CA6CD);
		listview.setFootable(footer);
		// 设置列表项出现动画
		listview.setItemAnimForTopIn(R.anim.topitem_in);
		listview.setItemAnimForBottomIn(R.anim.bottomitem_in);
	}

	// 将UNIX时间戳转换为dd/MM/yyyy HH:mm:ss格式输出
	public static String TimeStamp2Date(String timestampString) {
		Long timestamp = Long.parseLong(timestampString) * 1000;
		String date = new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm")
				.format(new java.util.Date(timestamp));
		return date;
	}

	// 获取时间
	public static String getDate() {
		Calendar c = Calendar.getInstance();
		String year = String.valueOf(c.get(Calendar.YEAR));
		String month = String.valueOf(c.get(Calendar.MONTH));
		String day = String.valueOf(c.get(Calendar.DAY_OF_MONTH) + 1);
		String hour = String.valueOf(c.get(Calendar.HOUR_OF_DAY));
		String mins = String.valueOf(c.get(Calendar.MINUTE));
		String sec = String.valueOf(c.get(Calendar.SECOND));
		StringBuffer sbBuffer = new StringBuffer();
		sbBuffer.append(day + "/" + month + "/" + year + "/" + " " + hour + ":"
				+ mins + ":" + sec);
		return sbBuffer.toString();
	}

	// 将当前的Button改变颜色
	public static Button MygetColor(Activity a, Button b, int color) {
		Resources resource = a.getBaseContext().getResources();
		ColorStateList csl = (ColorStateList) resource.getColorStateList(color);
		b.setTextColor(csl);
		return b;
	}

	/**
	 * @param context
	 * @param follow_btn
	 * @param red
	 */
	public static Button MygetColor(Context context, Button follow_btn, int red) {
		// TODO Auto-generated method stub
		Resources resource = context.getResources();
		ColorStateList csl = (ColorStateList) resource.getColorStateList(red);
		follow_btn.setTextColor(csl);
		return follow_btn;

	}

	public static TextView MygetColor(Context context, TextView follow_btn,
			int red) {
		// TODO Auto-generated method stub
		Resources resource = context.getResources();
		ColorStateList csl = (ColorStateList) resource.getColorStateList(red);
		follow_btn.setTextColor(csl);
		return follow_btn;

	}

	public static Button MygetColor(Context context, Button follow_btn,
			int red, String s) {
		// TODO Auto-generated method stub
		follow_btn.setBackgroundColor(Color.parseColor(context.getString(red)));
		return follow_btn;

	}

	/** * ������Ҳ����Ķ���Ч�� * @return */
	public static Animation inFromRightAnimation() {
		Animation inFromRight = new TranslateAnimation(
				Animation.RELATIVE_TO_PARENT, +1.0f,
				Animation.RELATIVE_TO_PARENT, 0.0f,
				Animation.RELATIVE_TO_PARENT, 0.0f,
				Animation.RELATIVE_TO_PARENT, 0.0f);
		inFromRight.setDuration(20000);
		inFromRight.setInterpolator(new AccelerateInterpolator());
		return inFromRight;
	}

	/** * ���������˳��Ķ���Ч�� * @return */
	public static Animation outToLeftAnimation() {
		Animation outtoLeft = new TranslateAnimation(
				Animation.RELATIVE_TO_PARENT, 0.0f,
				Animation.RELATIVE_TO_PARENT, -1.0f,
				Animation.RELATIVE_TO_PARENT, 0.0f,
				Animation.RELATIVE_TO_PARENT, 0.0f);
		outtoLeft.setDuration(500);
		outtoLeft.setInterpolator(new AccelerateInterpolator());
		return outtoLeft;
	}

	/** * �����������Ķ���Ч�� * @return */
	public static Animation inFromLeftAnimation() {
		Animation inFromLeft = new TranslateAnimation(
				Animation.RELATIVE_TO_PARENT, -1.0f,
				Animation.RELATIVE_TO_PARENT, 0.0f,
				Animation.RELATIVE_TO_PARENT, 0.0f,
				Animation.RELATIVE_TO_PARENT, 0.0f);
		inFromLeft.setDuration(500);
		inFromLeft.setInterpolator(new AccelerateInterpolator());
		return inFromLeft;
	}

	/** * ������Ҳ��˳�ʱ�Ķ���Ч�� * @return */
	public static Animation outToRightAnimation() {
		Animation outtoRight = new TranslateAnimation(
				Animation.RELATIVE_TO_PARENT, 0.0f,
				Animation.RELATIVE_TO_PARENT, +1.0f,
				Animation.RELATIVE_TO_PARENT, 0.0f,
				Animation.RELATIVE_TO_PARENT, 0.0f);
		outtoRight.setDuration(500);
		outtoRight.setInterpolator(new AccelerateInterpolator());
		return outtoRight;
	}

	public static void dispToast(Activity a, String str) {
		Toast toast;
		toast=Toast.makeText(a, str, Toast.LENGTH_SHORT);
		toast.setGravity(Gravity.CENTER, 0, 0);
		LinearLayout toastView = (LinearLayout) toast.getView();
		ImageView imageCodeProject = new ImageView(a.getApplicationContext());
		imageCodeProject.setImageResource(R.drawable.img_musiccircle_comment);
		toastView.addView(imageCodeProject, 0);
		toast.show();
	}
	public static void dispToast(Application a, String str) {
		Toast toast;
		toast=Toast.makeText(a, str, Toast.LENGTH_SHORT);
		toast.setGravity(Gravity.CENTER, 0, 0);
		LinearLayout toastView = (LinearLayout) toast.getView();
		ImageView imageCodeProject = new ImageView(a.getApplicationContext());
		imageCodeProject.setImageResource(R.drawable.img_musiccircle_comment);
		toastView.addView(imageCodeProject, 0);
		toast.show();
	}


	public static Bitmap getLoacalBitmap(String url) {
		try {
			FileInputStream fis = new FileInputStream(url);
			return BitmapFactory.decodeStream(fis); // /把流转化为Bitmap图片
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			return null;
		}
	}

	public static Bitmap getBitmapFromUrl(String url) {
		Bitmap bitmap = null;
		try {
			URLConnection conn = new URL(url).openConnection();
			conn.setConnectTimeout(CONNECT_TIMEOUT);
			conn.setReadTimeout(READ_TIMEOUT);
			bitmap = BitmapFactory
					.decodeStream((InputStream) conn.getContent());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return bitmap;
	}

	/** 保存方法 */
	public static void saveBitmap(String picName, Bitmap bm) throws IOException {
		Log.e(TAG, "保存图片");
		String name = picName;
		File g = new File(Environment.getExternalStorageDirectory().toString()
				+ File.separator + "Thinksns");
		File f = new File(g, name);
		if (!g.exists()) {
			g.mkdir();
		}
		if (f.exists()) {
			f.delete();
		}
		try {
			FileOutputStream out = new FileOutputStream(f);
			bm.compress(Bitmap.CompressFormat.JPEG, 90, out);
			out.flush();
			out.close();
			Log.i(TAG, "已经保存");
			Log.d(TAG, f.toString());
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public static void saveBitmapcompress(String picName, String filename)
			throws IOException {
		Log.e(TAG, "保存图片");
		String name = picName;
		File g = new File(Environment.getExternalStorageDirectory().toString()
				+ File.separator + "Thinksns");
		File f = new File(g, name);
		if (!g.exists()) {
			g.mkdir();
		}
		if (f.exists()) {
			f.delete();
		}
		try {
			BitmapFactory.Options opts = new BitmapFactory.Options();
			opts.inJustDecodeBounds = true;
			BitmapFactory.decodeFile(filename, opts);
			opts.inSampleSize = computeSampleSize(opts, -1, 1200 * 1200);
			opts.inJustDecodeBounds = false;
			Bitmap bm = null;
			try {
				bm = BitmapFactory.decodeFile(filename, opts);
			} catch (OutOfMemoryError e) {
				Log.e("Exception", e.getMessage(), e);
			}
			FileOutputStream out = new FileOutputStream(f);
			bm.compress(Bitmap.CompressFormat.JPEG, 100, out);
			out.flush();
			out.close();
			Log.i(TAG, "已经保存");
			Log.d(TAG, f.toString());
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public static int computeSampleSize(BitmapFactory.Options options,
			int minSideLength, int maxNumOfPixels) {
		int initialSize = computeInitialSampleSize(options, minSideLength,
				maxNumOfPixels);
		int roundedSize;
		if (initialSize <= 8) {
			roundedSize = 1;
			while (roundedSize < initialSize) {
				roundedSize <<= 1;
			}
		} else {
			roundedSize = (initialSize + 7) / 8 * 8;
		}
		return roundedSize;
	}

	private static int computeInitialSampleSize(BitmapFactory.Options options,
			int minSideLength, int maxNumOfPixels) {
		double w = options.outWidth;
		double h = options.outHeight;
		int lowerBound = (maxNumOfPixels == -1) ? 1 : (int) Math.ceil(Math
				.sqrt(w * h / maxNumOfPixels));
		int upperBound = (minSideLength == -1) ? 128 : (int) Math.min(
				Math.floor(w / minSideLength), Math.floor(h / minSideLength));
		if (upperBound < lowerBound) {
			return lowerBound;
		}
		if ((maxNumOfPixels == -1) && (minSideLength == -1)) {
			return 1;
		} else if (minSideLength == -1) {
			return lowerBound;
		} else {
			return upperBound;
		}
	}

	public static EditText shake(Activity a, EditText b) {
		Animation shake = AnimationUtils.loadAnimation(a, R.anim.shake);
		b.setAnimation(shake);
		return b;
	}

	@SuppressWarnings("deprecation")
	public static void copy(String content, Context context) {
		// 得到剪贴板管理器
		ClipboardManager cmb = (ClipboardManager) context
				.getSystemService(Context.CLIPBOARD_SERVICE);
		cmb.setText(content.trim());
	}

	public static String paste(Context context) {
		// 得到剪贴板管理器
		ClipboardManager cmb = (ClipboardManager) context
				.getSystemService(Context.CLIPBOARD_SERVICE);
		return cmb.getText().toString().trim();
	}

	public static Animation HiddenAction() {
		Animation mHiddenAction = new TranslateAnimation(
				Animation.RELATIVE_TO_SELF, 0.0f, Animation.RELATIVE_TO_SELF,
				0.0f, Animation.RELATIVE_TO_SELF, 0.0f,
				Animation.RELATIVE_TO_SELF, -1.0f);
		mHiddenAction.setDuration(1000);
		return mHiddenAction;
	}

	public static Animation ShowAction() {
		Animation mShowAction = new TranslateAnimation(
				Animation.RELATIVE_TO_SELF, 0.0f, Animation.RELATIVE_TO_SELF,
				0.0f, Animation.RELATIVE_TO_SELF, -1.0f,
				Animation.RELATIVE_TO_SELF, 0.0f);
		mShowAction.setDuration(1000);
		return mShowAction;
	}

	public static String getiemi(Context context) {
		TelephonyManager telephonemanage = (TelephonyManager) context
				.getSystemService(Context.TELEPHONY_SERVICE);
		return telephonemanage.getDeviceId();
	}

	public static String getInterval(String createtime) {
		String interval = null;
		SimpleDateFormat sd = new SimpleDateFormat("yyyy-MM-dd HH:mm");
		ParsePosition pos = new ParsePosition(0);
		Date d1 = (Date) sd.parse(createtime, pos);
		long time = new Date().getTime() - d1.getTime();
		System.out.println(time);
		if (time / 1000 < 10 && time / 1000 >= 0) {
			// 如果时间间隔小于10秒则显示“刚刚”time/10得出的时间间隔的单位是秒
			interval = "刚刚";
		} else if (time / 1000 < 60 && time / 1000 > 0) {
			// 如果时间间隔小于60秒则显示多少秒前
			int se = (int) ((time % 60000) / 1000);
			interval = se + "秒前";
		} else if (time / 60000 < 60 && time / 60000 > 0) {
			// 如果时间间隔小于60分钟则显示多少分钟前
			int m = (int) ((time % 3600000) / 60000);// 得出的时间间隔的单位是分钟
			interval = m + "分钟前";
		} else if (time / 3600000 < 24 && time / 3600000 >= 0) {
			// 如果时间间隔小于24小时则显示多少小时前
			int h = (int) (time / 3600000);// 得出的时间间隔的单位是小时
			interval = h + "小时前";
		} else if (time / 3600000 > 24 && time / 3600000 < 48
				&& time / 3600000 > 0) {
			interval = "昨天";
		} else if (time / 3600000 > 48 && time / 3600000 < 72
				&& time / 3600000 > 0) {
			interval = "前天";
		} else if (time / 86400000 > 3 && time / 86400000 < 31) {
			long day = (long) time / 86400000;
			interval = day + "天前";
		} else if (time / 86400000 > 31 && time / 86400000 < 365) {
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			ParsePosition pos2 = new ParsePosition(0);
			Date d2 = (Date) sdf.parse(createtime, pos2);
			interval = sdf.format(d2);
		} else {
			// 大于24小时，则显示正常的时间，但是不显示秒
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
			ParsePosition pos2 = new ParsePosition(0);
			Date d2 = (Date) sdf.parse(createtime, pos2);
			interval = sdf.format(d2);
		}
		return interval;
	}

	@SuppressLint("NewApi")
	public static String getPath(final Context context, final Uri uri) {
		final boolean isKitKat = Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT;
		// DocumentProvider
		if (isKitKat && DocumentsContract.isDocumentUri(context, uri)) {
			// ExternalStorageProvider
			if (isExternalStorageDocument(uri)) {
				final String docId = DocumentsContract.getDocumentId(uri);
				final String[] split = docId.split(":");
				final String type = split[0];

				if ("primary".equalsIgnoreCase(type)) {
					return Environment.getExternalStorageDirectory() + "/"
							+ split[1];
				}

				// TODO handle non-primary volumes
			}
			// DownloadsProvider
			else if (isDownloadsDocument(uri)) {
				final String id = DocumentsContract.getDocumentId(uri);
				final Uri contentUri = ContentUris.withAppendedId(
						Uri.parse("content://downloads/public_downloads"),
						Long.valueOf(id));

				return getDataColumn(context, contentUri, null, null);
			}
			// MediaProvider
			else if (isMediaDocument(uri)) {
				final String docId = DocumentsContract.getDocumentId(uri);
				final String[] split = docId.split(":");
				final String type = split[0];

				Uri contentUri = null;
				if ("image".equals(type)) {
					contentUri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
				} else if ("video".equals(type)) {
					contentUri = MediaStore.Video.Media.EXTERNAL_CONTENT_URI;
				} else if ("audio".equals(type)) {
					contentUri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;
				}

				final String selection = "_id=?";
				final String[] selectionArgs = new String[] { split[1] };

				return getDataColumn(context, contentUri, selection,
						selectionArgs);
			}
		} else if ("content".equalsIgnoreCase(uri.getScheme())) {

			// Return the remote address
			if (isGooglePhotosUri(uri))
				return uri.getLastPathSegment();

			return getDataColumn(context, uri, null, null);
		}
		// File
		else if ("file".equalsIgnoreCase(uri.getScheme())) {
			return uri.getPath();
		}

		return null;
	}

	public static String getDataColumn(Context context, Uri uri,
			String selection, String[] selectionArgs) {

		Cursor cursor = null;
		final String column = "_data";
		final String[] projection = { column };

		try {
			cursor = context.getContentResolver().query(uri, projection,
					selection, selectionArgs, null);
			if (cursor != null && cursor.moveToFirst()) {
				final int index = cursor.getColumnIndexOrThrow(column);
				return cursor.getString(index);
			}
		} finally {
			if (cursor != null)
				cursor.close();
		}
		return null;
	}

	public static boolean isExternalStorageDocument(Uri uri) {
		return "com.android.externalstorage.documents".equals(uri
				.getAuthority());
	}

	/**
	 * @param uri
	 *            The Uri to check.
	 * @return Whether the Uri authority is DownloadsProvider.
	 */
	public static boolean isDownloadsDocument(Uri uri) {
		return "com.android.providers.downloads.documents".equals(uri
				.getAuthority());
	}

	public static boolean isMediaDocument(Uri uri) {
		return "com.android.providers.media.documents".equals(uri
				.getAuthority());
	}

	public static boolean isGooglePhotosUri(Uri uri) {
		return "com.google.android.apps.photos.content".equals(uri
				.getAuthority());
	}

	public static String selectImage(Context context, Intent data) {
		Uri selectedImage = data.getData();
		// Log.e(TAG, selectedImage.toString());
		if (selectedImage != null) {
			String uriStr = selectedImage.toString();
			String path = uriStr.substring(10, uriStr.length());
			if (path.startsWith("com.sec.android.gallery3d")) {
				Log.e(TAG,
						"It's auto backup pic path:" + selectedImage.toString());
				return null;
			}
		}
		String[] filePathColumn = { MediaStore.Images.Media.DATA };
		Cursor cursor = context.getContentResolver().query(selectedImage,
				filePathColumn, null, null, null);
		cursor.moveToFirst();
		int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
		String picturePath = cursor.getString(columnIndex);
		cursor.close();
		return picturePath;
	}

	public static void showReconigizerDialog(RecognizerDialog rd,
			final EditText txt_result) {
		rd.setEngine("sms", null, null);
		rd.setSampleRate(RATE.rate16k);
		final StringBuilder sb = new StringBuilder();
		rd.setListener(new RecognizerDialogListener() {
			@Override
			public void onResults(ArrayList<RecognizerResult> result,
					boolean isLast) {
				for (RecognizerResult recognizerResult : result) {
					recognizerResult.text = recognizerResult.text.replace("。",
							"");
					recognizerResult.text = recognizerResult.text.replace("！",
							"");
					recognizerResult.text = recognizerResult.text.replace("？",
							"");
					sb.append(recognizerResult.text);
				}
			}

			@Override
			public void onEnd(SpeechError error) {
				txt_result.setText(sb.toString());
			}
		});
		txt_result.setText("");
		rd.show();
	}

	public static String getLocalHostIp() {
		String ipaddress = "";
		try {
			Enumeration<NetworkInterface> en = NetworkInterface
					.getNetworkInterfaces();
			// 遍历所用的网络接口
			while (en.hasMoreElements()) {
				NetworkInterface nif = en.nextElement();// 得到每一个网络接口绑定的所有ip
				Enumeration<InetAddress> inet = nif.getInetAddresses();
				// 遍历每一个接口绑定的所有ip
				while (inet.hasMoreElements()) {
					InetAddress ip = inet.nextElement();
					if (!ip.isLoopbackAddress()
							&& InetAddressUtils.isIPv4Address(ip
									.getHostAddress())) {
						return ipaddress = ip.getHostAddress();
					}
				}

			}
		} catch (SocketException e) {
			Log.e("feige", "获取本地ip地址失败");
			e.printStackTrace();
		}
		return ipaddress;

	}
}