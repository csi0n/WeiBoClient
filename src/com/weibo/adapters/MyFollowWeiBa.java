/**weibotest
 * 
 *
 *TODO
 */
package com.weibo.adapters;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.apache.http.message.BasicNameValuePair;
import org.json.JSONObject;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.SpannableString;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.loopj.android.image.SmartImageView;
import com.weibo.activity.OtherUserPanl;
import com.weibo.R;
import com.weibo.activity.ReplayWeiBaComment;
import com.weibo.activity.WeiBaDetail;
import com.weibo.application.Mykey;
import com.weibo.utils.ClientUtils;
import com.weibo.utils.DialogUtils;
import com.weibo.utils.DialogUtils.DialogCallBack;
import com.weibo.utils.FaceConversionUtil;
/**
 * @作者:陈华清
 * 
 * @版本:1.0
 * @生成时期:2014年7月28日 上午6:20:21
 * @com.api
 */
public class MyFollowWeiBa extends BaseAdapter {
	LayoutInflater inflater;
	ArrayList<HashMap<String, Object>> listitem;
	private Context mContext;
	int IS_FAVORITE = R.drawable.toolbar_fav_icon_res;
	int FAVORITE = R.drawable.toolbar_favorite_middle;
	private static final String TAG = "MyFollowWeiBa.java";
	protected static final int favorite_success = 0;
	protected static final int favorite_error = 1;
	protected static final int unfavorite_success = 2;
	protected static final int unfavorite_error = 3;
	private Handler handler = new Handler() {
		public void handleMessage(Message msg) {
			switch (msg.what) {
				case favorite_success :
					FaceConversionUtil.dispToast((Activity) mContext, "取消收藏成功!");
					break;
				case favorite_error :
					FaceConversionUtil.dispToast((Activity) mContext, "取消收藏失败!");
					break;
				case unfavorite_success :
					FaceConversionUtil.dispToast((Activity) mContext, "收藏成功!");
					break;
				case unfavorite_error :
					FaceConversionUtil.dispToast((Activity) mContext, "收藏失败!");
					break;
			}
		}
	};
	TextView close, favorite, comment, del;
	private PopupWindow popupWindow;
	private void initPopWindow() {
		View popView = inflater.inflate(R.layout.weibapopwindow, null);
		popupWindow = new PopupWindow(popView, LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
		popupWindow.setBackgroundDrawable(new ColorDrawable(0));
		popupWindow.setAnimationStyle(R.style.PopMenuAnimation);
		close = (TextView) popView.findViewById(R.id.cancel);
		favorite = (TextView) popView.findViewById(R.id.favorite);
		comment = (TextView) popView.findViewById(R.id.comment);
		del = (TextView) popView.findViewById(R.id.del);
	}
	public void showPop(View parent, int x, int y, final int postion) {
		popupWindow.showAtLocation(parent, 0, x, y);
		popupWindow.setFocusable(true);
		popupWindow.setOutsideTouchable(true);
		popupWindow.update();
		if (popupWindow.isShowing()) {
		}
		close.setOnClickListener(new OnClickListener() {
			public void onClick(View paramView) {
				popupWindow.dismiss();
			}
		});
		favorite.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if (listitem != null && listitem.size() > 0) {
					if (getItem(postion).get("favorite").equals("1")) {
						DialogUtils.dialogBuilder((Activity) mContext, "提示", "确定要取消收藏？", new DialogCallBack() {
							@Override
							public void callBack() {
								new Thread(new Runnable() {
									@Override
									public void run() {
										List<BasicNameValuePair> params = new ArrayList<BasicNameValuePair>();
										params.add(new BasicNameValuePair("oauth_token", ((Mykey) mContext.getApplicationContext()).getOauth_token()));
										params.add(new BasicNameValuePair("oauth_token_secret", ((Mykey) mContext.getApplicationContext()).getOauth_token_secret()));
										params.add(new BasicNameValuePair("user_id", ((Mykey) mContext.getApplicationContext()).getUid()));
										params.add(new BasicNameValuePair("id", getItem(postion).get("post_id").toString()));
										String result = ClientUtils.post_str(ClientUtils.BASE_URL + mContext.getString(ClientUtils.Weiba_post_unfavorite), params, (Activity) mContext);
										if (!result.equals("0")) {
											Message me = new Message();
											me.what = favorite_success;
											handler.sendMessage(me);
										} else {
											Message me = new Message();
											me.what = favorite_error;
											handler.sendMessage(me);
										}
									}
								}).start();
							}
						});
					} else if (getItem(postion).get("favorite").equals("0")) {
						DialogUtils.dialogBuilder((Activity) mContext, "提示", "确定要收藏该条内容？", new DialogCallBack() {
							@Override
							public void callBack() {
								new Thread(new Runnable() {
									@Override
									public void run() {
										// TODO Auto-generated
										List<BasicNameValuePair> params = new ArrayList<BasicNameValuePair>();
										params.add(new BasicNameValuePair("oauth_token", ((Mykey) mContext.getApplicationContext()).getOauth_token()));
										params.add(new BasicNameValuePair("oauth_token_secret", ((Mykey) mContext.getApplicationContext()).getOauth_token_secret()));
										params.add(new BasicNameValuePair("user_id", ((Mykey) mContext.getApplicationContext()).getUid()));
										params.add(new BasicNameValuePair("id", getItem(postion).get("post_id").toString()));
										String result = ClientUtils.post_str(ClientUtils.BASE_URL + mContext.getString(ClientUtils.Weiba_post_favorite), params, (Activity) mContext);
										if (!result.equals("0")) {
											Message m = new Message();
											m.what = unfavorite_success;
											handler.sendMessage(m);
										} else {
											Message m = new Message();
											m.what = unfavorite_error;
											handler.sendMessage(m);
										}
									}
								}).start();
							}
						});
					} else {
						Log.d(TAG, "什么鬼？");
					}
				}
			}
		});
		comment.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent i = new Intent(mContext, ReplayWeiBaComment.class);
				i.putExtra("post_id", getItem(postion).get("post_id").toString());
				mContext.startActivity(i);
			}
		});
		del.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				DialogUtils.dialogBuilder((Activity) mContext, "删除帖子", "是否删除帖子？", new DialogCallBack() {
					@Override
					public void callBack() {
						// TODO Auto-generated method stub
						FaceConversionUtil.dispToast((Activity) mContext, "没有找到API");
					}
				});
			}
		});
	}
	public class popAction implements OnClickListener {
		int position;
		public popAction(int position) {
			this.position = position;
		}

		@Override
		public void onClick(View v) {
			int[] arrayOfInt = new int[2];
			v.getLocationOnScreen(arrayOfInt);
			int x = arrayOfInt[0];
			int y = arrayOfInt[1];
			showPop(v, x, y, position);
		}
	}

	public MyFollowWeiBa(Context ctx, ArrayList<HashMap<String, Object>> listitem) {
		mContext = ctx;
		this.listitem = listitem;
		inflater = LayoutInflater.from(mContext);
		initPopWindow();
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return listitem.size();
	}

	@Override
	public HashMap<String, Object> getItem(int position) {
		// TODO Auto-generated method stub
		return listitem.get(position);
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		ViewHolder holder = null;
		if (convertView == null) {
			convertView = inflater.inflate(R.layout.weibamainitem, parent, false);
			holder = new ViewHolder();
			holder.head = (SmartImageView) convertView.findViewById(R.id.imageView1);
			holder.uname_Text = (TextView) convertView.findViewById(R.id.textView1);
			holder.time_Text = (TextView) convertView.findViewById(R.id.textView5);
			holder.content_Text = (TextView) convertView.findViewById(R.id.textView2);
			holder.comm_Text = (TextView) convertView.findViewById(R.id.textView4);
			holder.title_Text = (TextView) convertView.findViewById(R.id.textView3);
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}
		holder.title_Text.setText(getItem(position).get("title").toString());
		SpannableString spannableString = FaceConversionUtil.getInstace().getExpressionString(mContext, getItem(position).get("content").toString());
		holder.content_Text.setText(spannableString);
		FaceConversionUtil.extractMention2Link(holder.content_Text);
		String time = FaceConversionUtil.TimeStamp2Date(getItem(position).get("post_time").toString());
		time = FaceConversionUtil.getInterval(time);
		if (time.indexOf("刚") > 0 || time.indexOf("前") > 0 || time.indexOf("天") > 0) {
			FaceConversionUtil.MygetColor(mContext, holder.time_Text, R.color.yellow);
			holder.time_Text.setText(time);
		} else {
			holder.time_Text.setText(time);
		}
		try {
			JSONObject ob = new JSONObject(getItem(position).get("author_info").toString());
			holder.head.setImageUrl(ob.getString("avatar_middle"));
			holder.uname_Text.setText(ob.getString("uname"));
		} catch (Exception e) {
			Log.d(TAG, e.toString());
		}
		holder.comm_Text.setOnClickListener(new popAction(position));
		addListener(convertView, position);
		return convertView;
	}
	private class ViewHolder {
		SmartImageView head;
		TextView uname_Text, content_Text, time_Text, favo_Text, comm_Text, title_Text, followstate_Text;
		ImageView more;
	}

	public void addListener(View convertView, int position) {
		final int arg2 = position;
		final View convertview = convertView;
		((SmartImageView) convertView.findViewById(R.id.imageView1)).setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				try {
					JSONObject ob = new JSONObject(getItem(arg2).get("author_info").toString());
					Mykey app = (Mykey) mContext.getApplicationContext();
					app.setOtheruid(ob.getString("uid"));
					Intent intent = new Intent(mContext, OtherUserPanl.class);
					mContext.startActivity(intent);
				} catch (Exception e) {
					Log.d(TAG, e.toString());
				}
			}
		});
		convertview.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Bundle b = new Bundle();
				b.putString("id", getItem(arg2).get("post_id").toString());
				b.putString("author_info", getItem(arg2).get("author_info").toString());
				Intent intent = new Intent(mContext, WeiBaDetail.class);
				intent.putExtras(b);
				mContext.startActivity(intent);
			}
		});

	}

}
