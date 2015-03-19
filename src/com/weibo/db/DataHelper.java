/**weibotest
 * 
 *
 *TODO
 */
package com.weibo.db;

import com.weibo.card.UserInfor;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;
/**
 * @作者:陈华清
 * 
 * @版本:1.0
 * @生成时期:2014年7月28日 下午6:01:19
 * @com.db
 */
public class DataHelper {
	private static final String DB_NAME = "thinksns.db";
	private SQLiteDatabase db;
	private SqliteHelper dbHelper;
	// 数据库版本
	private static int DB_VERSION = 2;
	private static final String TAG = "DataHelper.java";

	public DataHelper(Context context) {
		dbHelper = new SqliteHelper(context, DB_NAME, null, DB_VERSION);
		db = dbHelper.getWritableDatabase();
	}

	public void Close() {
		db.close();
		dbHelper.close();
	}

	public boolean HaveWeiBoSearchKey(String key) {
		boolean b = false;
		Cursor cursor = db.query(SqliteHelper.WEIBO_SEARCH_CACHE_KEY, null,
				"key=?", new String[] { key }, null, null, null);
		b = cursor.moveToFirst();
		return b;
	}

	public boolean HaveUserNameSearchKey(String key) {
		boolean b = false;
		Cursor cursor = db.query(SqliteHelper.USER_NAME_SEARCH_CACHE_KEY, null,
				"key=?", new String[] { key }, null, null, null);
		b = cursor.moveToFirst();
		return b;
	}

	public boolean HaveWeiBaTieSearchKey(String key) {
		boolean b = false;
		Cursor cursor = db.query(SqliteHelper.WEIBA_TIE_SEARCH_CACHE_KEY, null,
				"key=?", new String[] { key }, null, null, null);
		b = cursor.moveToFirst();
		return b;
	}

	public boolean HavaweibaSearchKey(String key) {
		boolean b = false;
		Cursor cursor = db.query(SqliteHelper.WEIBA_SEARCH_CACHE_KEY, null,
				"key=?", new String[] { key }, null, null, null);
		b = cursor.moveToFirst();
		return b;
	}

	public void saveWeiBaSearchTieKey(String key) {
		ContentValues values = new ContentValues();
		values.put("key", key);
		if (HaveWeiBaTieSearchKey(key)) {
			db.update(SqliteHelper.WEIBA_TIE_SEARCH_CACHE_KEY, values, "key=?",
					new String[] { key });
			Log.d(TAG, "更新了搜索微吧关键字");
		} else {
			db.insert(SqliteHelper.WEIBA_TIE_SEARCH_CACHE_KEY, "key", values);
			Log.d(TAG, "插入了微吧搜索关键字");
		}
	}

	public void saveWeiBaSearchKey(String key) {
		ContentValues values = new ContentValues();
		values.put("key", key);
		if (HavaweibaSearchKey(key)) {
			db.update(SqliteHelper.WEIBA_SEARCH_CACHE_KEY, values, "key=?",
					new String[] { key });
			Log.d(TAG, "更新了微吧搜索关键字");
		} else {
			db.insert(SqliteHelper.WEIBA_SEARCH_CACHE_KEY, "key", values);
			Log.d(TAG, "插入了微吧搜索关键字");
		}
	}

	public void saveWeiBoSearchKey(String key) {
		ContentValues values = new ContentValues();
		values.put("key", key);
		if (HaveWeiBoSearchKey(key)) {
			db.update(SqliteHelper.WEIBO_SEARCH_CACHE_KEY, values, "key=?",
					new String[] { key });
			Log.d(TAG, "更新了微博搜索关键字");
		} else {
			db.insert(SqliteHelper.WEIBO_SEARCH_CACHE_KEY, "key", values);
			Log.d(TAG, "插入了微博搜索关键字！");
		}
	}

	public void saveUserSearchKey(String key) {
		ContentValues values = new ContentValues();
		values.put("key", key);
		if (HaveUserNameSearchKey(key)) {
			db.update(SqliteHelper.USER_NAME_SEARCH_CACHE_KEY, values, "key=?",
					new String[] { key });
			Log.d(TAG, "更新了用户搜索关键字");
		} else {
			db.insert(SqliteHelper.USER_NAME_SEARCH_CACHE_KEY, "key", values);
			Log.d(TAG, "插入了用户搜索关键字");
		}
	}
	public boolean HavaUserInfo(String uid) {
		boolean b = false;
		Cursor cursor = db.query(SqliteHelper.TBL_NAME, null, "uid=?",
				new String[] { uid }, null, null, null);
		b = cursor.moveToFirst();
		return b;
	}
	public int getweibasearchkey() {
		Cursor cursor = db.query(SqliteHelper.WEIBA_SEARCH_CACHE_KEY, null,
				null, null, null, null, "_id DESC");
		cursor.moveToFirst();
		return cursor.getCount();
	}

	public String[] getweibasearchlist() {
		String[] key = null;
		Cursor cursor = db.query(SqliteHelper.WEIBA_SEARCH_CACHE_KEY, null,
				null, null, null, null, "_id DESC");
		cursor.moveToFirst();
		key = new String[cursor.getCount()];
		if (!cursor.isAfterLast() && cursor.getString(1) != null) {
			for (int i = 0; i < cursor.getCount(); i++) {
				key[i] = new String(cursor.getString(1));
				Log.d(TAG, "当前是第" + String.valueOf(i) + "条" + key[i]);
				cursor.moveToNext();
			}
		}
		cursor.close();
		if (key.length > 0) {
			Log.d(TAG, "用户搜索缓存大小" + key.length);
			return key;

		} else {
			Log.d(TAG, "没有用户搜索记录");
			return null;
		}
	}

	public int weibatiesearchcount() {
		Cursor cursor = db.query(SqliteHelper.WEIBA_TIE_SEARCH_CACHE_KEY, null,
				null, null, null, null, "_id DESC");
		cursor.moveToFirst();
		return cursor.getCount();
	}

	public String[] getweibatiesearchlist() {
		String[] key = null;
		Cursor cursor = db.query(SqliteHelper.WEIBA_TIE_SEARCH_CACHE_KEY, null,
				null, null, null, null, "_id DESC");
		cursor.moveToFirst();
		key = new String[cursor.getCount()];
		if (!cursor.isAfterLast() && cursor.getString(1) != null) {
			for (int i = 0; i < cursor.getCount(); i++) {
				key[i] = new String(cursor.getString(1));
				Log.d(TAG, "当前是第" + String.valueOf(i) + "条" + key[i]);
				cursor.moveToNext();
			}
		}
		cursor.close();
		if (key.length > 0) {
			Log.d(TAG, "用户搜索缓存大小" + key.length);
			return key;

		} else {
			Log.d(TAG, "没有用户搜索记录");
			return null;
		}
	}

	public int getusersearchcount() {
		Cursor cursor = db.query(SqliteHelper.USER_NAME_SEARCH_CACHE_KEY, null,
				null, null, null, null, "_id DESC");
		cursor.moveToFirst();
		return cursor.getCount();
	}

	public String[] getusersearchlist() {
		String[] key = null;
		Cursor cursor = db.query(SqliteHelper.USER_NAME_SEARCH_CACHE_KEY, null,
				null, null, null, null, "_id DESC");
		cursor.moveToFirst();
		key = new String[cursor.getCount()];
		if (!cursor.isAfterLast() && cursor.getString(1) != null) {
			for (int i = 0; i < cursor.getCount(); i++) {
				key[i] = new String(cursor.getString(1));
				Log.d(TAG, "当前是第" + String.valueOf(i) + "条" + key[i]);
				cursor.moveToNext();
			}
		}
		cursor.close();
		if (key.length > 0) {
			Log.d(TAG, "用户搜索缓存大小" + key.length);
			return key;

		} else {
			Log.d(TAG, "没有用户搜索记录");
			return null;
		}
	}

	public int getweibosearchcount() {
		Cursor cursor = db.query(SqliteHelper.WEIBO_SEARCH_CACHE_KEY, null,
				null, null, null, null, "_id DESC");
		cursor.moveToFirst();
		return cursor.getCount();
	}

	public String[] getweibokeylist() {
		String[] key = null;
		Cursor cursor = db.query(SqliteHelper.WEIBO_SEARCH_CACHE_KEY, null,
				null, null, null, null, "_id DESC");
		cursor.moveToFirst();
		key = new String[cursor.getCount()];
		if (!cursor.isAfterLast() && cursor.getString(1) != null) {
			for (int i = 0; i < cursor.getCount(); i++) {
				key[i] = new String(cursor.getString(1));
				Log.d(TAG, "当前是第" + String.valueOf(i) + "条" + key[i]);
				cursor.moveToNext();
			}
		}
		cursor.close();
		if (key.length > 0) {
			Log.d(TAG, "微博搜索缓存大小" + key.length);
			return key;
		} else {
			Log.d(TAG, "没找到微博搜索缓存");
			return null;
		}
	}
	public void saveUserinfor(UserInfor user) {
		ContentValues values = new ContentValues();
		values.put("uid", user.getUid());
		values.put("uname", user.getUname());
		values.put("head_ic", user.getAvatar_middle());
		values.put("sex", user.getSex());
		values.put("ctime", user.getCtime());
		values.put("intro", user.getIntro());
		if (HavaUserInfo(user.getUid())) {
			db.update(SqliteHelper.TBL_NAME, values, "uid=?",
					new String[] { user.getUid() });
			Log.d(TAG, "更新了数据库");
		} else {
			db.insert(SqliteHelper.TBL_NAME, "uid", values);
			Log.d(TAG, "插入了数据");
		}
		db.close();
	}

	public UserInfor getUserInfor(String uid) {
		UserInfor user = null;
		if (HavaUserInfo(uid)) {
			Cursor cursor = db.query(SqliteHelper.TBL_NAME, null, "uid=?",
					new String[] { uid }, null, null, null);
			user = new UserInfor();
			user.setUid(cursor.getString(1));
			user.setUname(cursor.getString(2));
			user.setAvatar_middle(cursor.getString(3));
			user.setSex(cursor.getString(4));
			user.setCtime(cursor.getString(5));
			user.setIntro(cursor.getString(7));
			user.setMedals(cursor.getString(8));
			return user;

		} else {
			return null;
		}
	}
}
