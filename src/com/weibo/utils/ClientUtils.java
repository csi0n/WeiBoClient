package com.weibo.utils;
import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.mime.MultipartEntity;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.entity.mime.content.StringBody;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import com.weibo.R;

import android.app.Activity;
import android.util.Log;
/**
 * @作者:陈华清
 * 
 * @版本:1.0
 * @生成时期:2014-11-10 下午6:35:16
 * @com.weibo.utils
 */
public class ClientUtils {
	public static final String BASE_URL = "http://192.168.1.100:8080";
	public static final int GetResquestKey_resquest_key = R.string.GetResquestKey_resquest_key;
	public static final int UserLogin_login = R.string.UserLogin_login;
	public static final int RegisterNewUser_register = R.string.RegisterNewUser_register;
	public static final int WeiboStatuses_get_public_timeline = R.string.WeiboStatuses_get_public_timeline;
	public static final int WeiboStatuses_get_friends_timeline = R.string.WeiboStatuses_get_friends_timeline;
	public static final int WeiboStatuses_get_user_timeline = R.string.WeiboStatuses_get_user_timeline;
	public static final int WeiboStatuses_mentions_feed = R.string.WeiboStatuses_mentions_feed;
	public static final int WeiboStatuses_show = R.string.WeiboStatuses_show;
	public static final int WeiboStatuses_update = R.string.WeiboStatuses_update;
	public static final int WeiboStatuses_upload = R.string.WeiboStatuses_upload;
	public static final int WeiboStatuses_destroy = R.string.WeiboStatuses_destroy;
	public static final int WeiboStatuses_repost = R.string.WeiboStatuses_repost;
	public static final int WeiboStatuses_comments = R.string.WeiboStatuses_comments;
	public static final int WeiboStatuses_comments_to_me = R.string.WeiboStatuses_comments_to_me;
	public static final int WeiboStatuses_comments_by_me = R.string.WeiboStatuses_comments_by_me;
	public static final int WeiboStatuses_comment = R.string.WeiboStatuses_comment;
	public static final int WeiboStatuses_weibo_search_weibo = R.string.WeiboStatuses_weibo_search_weibo;
	public static final int WeiboStatuses_weibo_search_user = R.string.WeiboStatuses_weibo_search_user;
	public static final int WeiboStatuses_favorite_create = R.string.WeiboStatuses_favorite_create;
	public static final int WeiboStatuses_favorite_destroy = R.string.WeiboStatuses_favorite_destroy;
	public static final int WeiboStatuses_add_digg = R.string.WeiboStatuses_add_digg;
	public static final int WeiboStatuses_delete_digg = R.string.WeiboStatuses_delete_digg;
	public static final int WeiboStatuses_favorite_feed = R.string.WeiboStatuses_favorite_feed;
	public static final int WeiboStatuses_search_topic = R.string.WeiboStatuses_search_topic;
	public static final int WeiboStatuses_get_uid = R.string.WeiboStatuses_get_uid;
	public static final int Channel_get_all_channel = R.string.Channel_get_all_channel;
	public static final int Channel_get_channel_feed = R.string.Channel_get_channel_feed;
	public static final int Message_get_message_list = R.string.Message_get_message_list;
	public static final int Message_get_message_detail = R.string.Message_get_message_detail;
	public static final int Message_create = R.string.Message_create;
	public static final int Message_reply = R.string.Message_reply;
	public static final int Message_get_list_id = R.string.Message_get_list_id;
	public static final int User_show = R.string.User_show;
	public static final int User_upload_face = R.string.User_upload_face;
	public static final int User_user_followers = R.string.User_user_followers;
	public static final int User_user_following = R.string.User_user_following;
	public static final int User_user_friends = R.string.User_user_friends;
	public static final int User_follow_create = R.string.User_follow_create;
	public static final int User_follow_destroy = R.string.User_follow_destroy;
	public static final int User_get_nearby_person = R.string.User_get_nearby_person;
	public static final int User_insert_location = R.string.User_insert_location;
	public static final int Weiba_get_weibas = R.string.Weiba_get_weibas;
	public static final int Weiba_create = R.string.Weiba_create;
	public static final int Weiba_destroy = R.string.Weiba_destroy;
	public static final int Weiba_get_posts = R.string.Weiba_get_posts;
	public static final int Weiba_create_post = R.string.Weiba_create_post;
	public static final int Weiba_post_detail = R.string.Weiba_post_detail;
	public static final int Weiba_comment_list = R.string.Weiba_comment_list;
	public static final int Weiba_comment_post = R.string.Weiba_comment_post;
	public static final int Weiba_reply_comment = R.string.Weiba_reply_comment;
	public static final int Weiba_delete_comment = R.string.Weiba_delete_comment;
	public static final int Weiba_following_posts = R.string.Weiba_following_posts;
	public static final int Weiba_posteds = R.string.Weiba_posteds;
	public static final int Weiba_search_weiba = R.string.Weiba_search_weiba;
	public static final int Weiba_search_post = R.string.Weiba_search_post;
	public static final int Weiba_post_favorite = R.string.Weiba_post_favorite;
	public static final int Weiba_post_unfavorite = R.string.Weiba_post_unfavorite;
	public static final int Weiba_favorite_list = R.string.Weiba_favorite_list;
	public static final int Checkin_checkin = R.string.Checkin_checkin;
	public static final int Checkin_get_check_info = R.string.Checkin_get_check_info;
	public static final int Weiba_commenteds = R.string.Weiba_commenteds;
	public static final String TAG = "";

	public static String post_str(String url, List<BasicNameValuePair> params, Activity a) {
		String result = "";
		try {
			DefaultHttpClient httpClient = new DefaultHttpClient();
			HttpPost request = new HttpPost(url);
			HttpEntity httpentity = new UrlEncodedFormEntity(params, "UTF-8");
			request.setEntity(httpentity);
			HttpResponse httpresponse = httpClient.execute(request);
			result = EntityUtils.toString(httpresponse.getEntity(), "UTF-8");
		} catch (Exception e) {
		}
		return result;
	}

	public static String post_with_pic(String url, String file, Activity a) {
		String result = "";
		try {
			File file1 = new File(file);
			DefaultHttpClient httpClient = new DefaultHttpClient();
			HttpPost request = new HttpPost(url);
			MultipartEntity reqEntity = new MultipartEntity();
			FileBody filebody = new FileBody(file1, "image/jpeg");
			reqEntity.addPart("File", filebody);
			request.setEntity(reqEntity);
			HttpResponse httpresponse = httpClient.execute(request);
			result = EntityUtils.toString(httpresponse.getEntity(), "UTF-8");
		} catch (Exception e) {
			Log.d(TAG, e.toString());
		}
		return result;
	}
	public static String post_with_pic(String url, List<String> filelist, Activity a) {
		String result = "";
		try {
			DefaultHttpClient httpClient = new DefaultHttpClient();
			HttpPost request = new HttpPost(url);
			MultipartEntity reqEntity = new MultipartEntity();
			request.setEntity(reqEntity);
			for (int i = 0; i < filelist.size(); i++) {
				reqEntity.addPart("File", new FileBody(new File(filelist.get(i))));
				Log.d(TAG, "当前上传图片:" + filelist.get(i));
			}
			StringBody userId = new StringBody("1");
			reqEntity.addPart("test", userId);
			HttpResponse httpresponse = httpClient.execute(request);
			HttpEntity responseEntity = httpresponse.getEntity();
			System.out.println("----------------------------------------");
			System.out.println(httpresponse.getStatusLine());
			if (responseEntity != null) {
				return inputStream2String(responseEntity.getContent());
			}
			httpClient.getConnectionManager().shutdown();
			// result = EntityUtils.toString(httpresponse.getEntity(), "UTF-8");

		} catch (Exception e) {
			Log.d(TAG, e.toString());
		}
		return result;
	}

	public static String post_face_id(String url, String file, String user_id, Activity a) {
		try {
			HttpClient httpClient = new DefaultHttpClient();
			HttpPost httpPost = new HttpPost(url);
			MultipartEntity reqEntity = new MultipartEntity();
			httpPost.setEntity(reqEntity);
			/** file param name */
			FileBody bin = new FileBody(new File(file));
			reqEntity.addPart("File", bin);
			/** String param name */
			StringBody userId = new StringBody(user_id);
			reqEntity.addPart("user_id", userId);
			HttpResponse response = httpClient.execute(httpPost);
			HttpEntity responseEntity = response.getEntity();
			System.out.println("----------------------------------------");
			System.out.println(response.getStatusLine());
			if (responseEntity != null) {
				return inputStream2String(responseEntity.getContent());
			}
			httpClient.getConnectionManager().shutdown();
		} catch (Exception e) {
			Log.d(TAG, e.toString());
		}
		return "0";
	}
	public static String inputStream2String(InputStream is) throws IOException {
		byte[] buff = new byte[1024 * 1024 * 5];
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		int i = -1;
		while ((i = is.read(buff)) != -1) {
			baos.write(buff, 0, i);
		}
		return baos.toString();
	}
	public static String getweatherdetail(String url1) {
		String result = "";
		try {
			URL url = new URL(url1);
			HttpURLConnection con = (HttpURLConnection) url.openConnection();
			con.connect();
			java.io.InputStream is = con.getInputStream();
			BufferedReader br = new BufferedReader(new InputStreamReader(is));
			String line;
			StringBuilder builder = new StringBuilder();
			while ((line = br.readLine()) != null) {
				builder.append(line);
			}
			result = builder.toString();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}
}
