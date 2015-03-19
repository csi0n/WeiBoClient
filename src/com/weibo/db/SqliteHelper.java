/**weibotest
 * 
 *
 *TODO
 */
package com.weibo.db;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * @作者:陈华清
 * 
 * @版本:1.0
 * @生成时期:2014年7月28日 下午4:50:25
 * @com.db
 */
public class SqliteHelper extends SQLiteOpenHelper {
	public SqliteHelper(Context context, String name, CursorFactory factory, int version) {
		super(context, name, factory, version);
		// TODO Auto-generated constructor stub
	}

	private static final String TAG = "SqliteHelper.JAVA";
	public static final String TBL_NAME = "userinfor";
	public static final String WEIBO_SEARCH_CACHE_KEY = "weibosearchkeycache";
	public static final String USER_NAME_SEARCH_CACHE_KEY = "usernamesearchkeycache";
	public static final String WEIBA_SEARCH_CACHE_KEY = "weibasearchkeycache";
	public static final String WEIBA_TIE_SEARCH_CACHE_KEY = "weibatiesearchkeycache";
	private static final String CREATE_TBL = "CREATE TABLE IF NOT EXISTS " + TBL_NAME + "(_id integer primary key autoincrement,uid text,uname text, head_ic text,sex text,ctime,text,intro text,medals text)";
	private static final String CREATE_WEIBO_SEARCH_KEY_TABLE = "CREATE TABLE IF NOT EXISTS " + WEIBO_SEARCH_CACHE_KEY + "(_id integer primary key autoincrement,key text)";
	private static final String CREATE_USER_NAME_SEARCH_KEY_TABALE = "CREATE TABLE IF NOT EXISTS " + USER_NAME_SEARCH_CACHE_KEY + "(_id integer primary key autoincrement,key text)";
	private static final String CREATE_WEBA_SEARCH_CACHE_TABLE = "CREATE TABLE IF NOT EXISTS " + WEIBA_SEARCH_CACHE_KEY + "(_id integer primary key autoincrement,key text)";
	private static final String CRTEATE_WEIBA_TIE_SEARCH_CACHE_KEY_TABLE = "CREATE TABLE IF NOT EXISTS " + WEIBA_TIE_SEARCH_CACHE_KEY + "(_id integer primary key autoincrement,key text)";

	@Override
	public void onCreate(SQLiteDatabase db) {
		// TODO Auto-generated method stub
		db.execSQL(CREATE_TBL);
		db.execSQL(CREATE_WEIBO_SEARCH_KEY_TABLE);
		db.execSQL(CREATE_USER_NAME_SEARCH_KEY_TABALE);
		db.execSQL(CREATE_WEBA_SEARCH_CACHE_TABLE);
		db.execSQL(CRTEATE_WEIBA_TIE_SEARCH_CACHE_KEY_TABLE);
		Log.d(TAG, "创建了数据库");
	}

	// 更新表
	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		db.execSQL("DROP TABLE IF EXISTS " + TBL_NAME);
		db.execSQL("DROP TABLE IF EXISTS " + WEIBO_SEARCH_CACHE_KEY);
		db.execSQL("DROP TABLE IF EXISTS " + USER_NAME_SEARCH_CACHE_KEY);
		db.execSQL("DROP TABLE IF EXISTS " + CREATE_WEBA_SEARCH_CACHE_TABLE);
		db.execSQL("DROP TABLE IF EXISTS " + CRTEATE_WEIBA_TIE_SEARCH_CACHE_KEY_TABLE);
		onCreate(db);
		Log.e(TAG, "更新了数据库表");
	}

	// 更新列
	public void updateColumn(SQLiteDatabase db, String oldColumn, String newColumn, String typeColumn) {
		try {
			db.execSQL("ALTER TABLE " + TBL_NAME + " CHANGE " + oldColumn + " " + newColumn + " " + typeColumn);
			db.execSQL("ALTER TABLE " + WEIBO_SEARCH_CACHE_KEY + " CHANGE " + oldColumn + " " + newColumn + " " + typeColumn);
			db.execSQL("ALTER TABLE " + USER_NAME_SEARCH_CACHE_KEY + " CHANGE " + oldColumn + " " + newColumn + " " + typeColumn);
			db.execSQL("ALTER TABLE " + CREATE_WEBA_SEARCH_CACHE_TABLE + " CHANGE " + oldColumn + " " + newColumn + " " + typeColumn);
			db.execSQL("ALTER TABLE " + CRTEATE_WEIBA_TIE_SEARCH_CACHE_KEY_TABLE + " CHANGE " + oldColumn + " " + newColumn + " " + typeColumn);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
