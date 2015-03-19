package com.weibo.adapters;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

import org.apache.http.message.BasicNameValuePair;
import org.json.JSONObject;

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
import com.weibo.view.widget.LoadingAlertAnim;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.Html;
import android.text.SpannableString;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.TextView;

/**
 * 
 * 微吧首页Adapter
 * 
 */
public class WeiBaMainAdapter extends BaseAdapter {
	LayoutInflater inflater;
	ArrayList<HashMap<String, Object>> listitem;
	private Context mContext;
	Bundle b;
	LoadingAlertAnim loading;
	final int type1 = 0;
	final int type2 = 1;
	private static final String TAG = "WeiBaMainAdapter.java";
	protected static final int change = 0;
	protected static final int find = 5;
	protected static final int success = 1;
	protected static final int error = 2;
	protected static final int success1 = 3;
	protected static final int error1 = 4;
	protected static final int end = 6;
	private Handler handler = new Handler() {
		public void handleMessage(Message msg) {
			switch (msg.what) {
			case success:
				if(loading!=null)
				{
					loading.dismiss();
				}
				Button button1 = (Button) msg.obj;
				FaceConversionUtil.dispToast((Activity) mContext, "关注成功");
				button1.setText("取消关注");
				break;
			case error:
				if(loading!=null)
				{
					loading.dismiss();
				}
				FaceConversionUtil.dispToast((Activity) mContext, "关注失败");
				break;
			case success1:
				if(loading!=null)
				{
					loading.dismiss();
				}
				Button button2 = (Button) msg.obj;
				FaceConversionUtil.dispToast((Activity) mContext, "取消关注成功");
				button2.setText("关注");
				break;
			case error1:
				if(loading!=null)
				{
					loading.dismiss();
				}
				FaceConversionUtil.dispToast((Activity) mContext, "取消关注失败");
				break;
			}
		}
	};

	public WeiBaMainAdapter(Context ctx,
			ArrayList<HashMap<String, Object>> listitem, Bundle b) {
		this.mContext = ctx;
		this.listitem = listitem;
		this.b = b;
		inflater = LayoutInflater.from(mContext);
		initPopWindow();
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return listitem.size() + 1;
	}

	@Override
	public HashMap<String, Object> getItem(int position) {
		// TODO Auto-generated method stub
		return listitem.get(position - 1);
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int getItemViewType(int position) {
		int p = position;
		if (p == 0) {
			return type1;
		} else {
			return type2;
		}
	}

	@Override
	public int getViewTypeCount() {
		return 2;
	}

	ViewHolder holder;

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		ViewHolder holder = null;
		WeiBaInforHolder weibainfor = null;
		int type = getItemViewType(position);
		if (convertView == null) {
			switch (type) {
			case type1:
				convertView = inflater.inflate(R.layout.weiba_footview, parent,
						false);
				weibainfor = new WeiBaInforHolder();
				weibainfor.head_ic = (SmartImageView) convertView
						.findViewById(R.id.image);
				weibainfor.follow_btn = (Button) convertView
						.findViewById(R.id.follow);
				weibainfor.followcount_Text = (TextView) convertView
						.findViewById(R.id.textView3);
				weibainfor.Thread_Text = (TextView) convertView
						.findViewById(R.id.textView5);
				weibainfor.title_text = (TextView) convertView
						.findViewById(R.id.textView1);
				break;
			case type2:
				convertView = inflater.inflate(R.layout.weibamainitem, parent,
						false);
				holder = new ViewHolder();
				holder.head = (SmartImageView) convertView
						.findViewById(R.id.imageView1);
				holder.uname_Text = (TextView) convertView
						.findViewById(R.id.textView1);
				holder.time_Text = (TextView) convertView
						.findViewById(R.id.textView5);
				holder.content_Text = (TextView) convertView
						.findViewById(R.id.textView2);
				holder.comm_Text = (TextView) convertView
						.findViewById(R.id.textView4);
				holder.title_Text = (TextView) convertView
						.findViewById(R.id.textView3);
				break;
			}
		} else {
			switch (type) {
			case type1:
				convertView = inflater.inflate(R.layout.weiba_footview, parent,
						false);
				weibainfor = new WeiBaInforHolder();
				weibainfor.head_ic = (SmartImageView) convertView
						.findViewById(R.id.image);
				weibainfor.follow_btn = (Button) convertView
						.findViewById(R.id.follow);
				weibainfor.followcount_Text = (TextView) convertView
						.findViewById(R.id.textView3);
				weibainfor.Thread_Text = (TextView) convertView
						.findViewById(R.id.textView5);
				weibainfor.title_text = (TextView) convertView
						.findViewById(R.id.textView1);
				break;
			case type2:
				convertView = inflater.inflate(R.layout.weibamainitem, parent,
						false);
				holder = new ViewHolder();
				holder.head = (SmartImageView) convertView
						.findViewById(R.id.imageView1);
				holder.uname_Text = (TextView) convertView
						.findViewById(R.id.textView1);
				holder.time_Text = (TextView) convertView
						.findViewById(R.id.textView5);
				holder.content_Text = (TextView) convertView
						.findViewById(R.id.textView2);
				holder.comm_Text = (TextView) convertView
						.findViewById(R.id.textView4);
				holder.title_Text = (TextView) convertView
						.findViewById(R.id.textView3);
				break;
			}
		}
		switch (type) {
		case type1:
			weibainfor.head_ic.setImageUrl(b.getString("logo_url"));
			weibainfor.title_text.setText(b.getString("weiba_name"));
			weibainfor.followcount_Text.setText(b.getString("follower_count"));
			weibainfor.Thread_Text.setText(b.getString("thread_count"));
			if (b.getString("followstate").equals("0")) {
				weibainfor.follow_btn.setText("关注");
			} else if (b.getString("followstate").equals("1")) {
				weibainfor.follow_btn.setText("取消关注");
			}
			break;
		case type2:
			if(listitem!=null&&listitem.size()>0)
			{
			holder.title_Text
					.setText(getItem(position).get("title").toString());
			SpannableString spannableString = FaceConversionUtil.getInstace()
					.getExpressionString(mContext,
							getItem(position).get("content").toString());
			holder.content_Text.setText(spannableString);
			FaceConversionUtil.extractMention2Link(holder.content_Text);
			holder.comm_Text.setOnClickListener(new popAction(position));
			String time = FaceConversionUtil.TimeStamp2Date(getItem(position)
					.get("post_time").toString());
			time = FaceConversionUtil.getInterval(time);
			if (time.indexOf("刚") > 0 || time.indexOf("前") > 0
					|| time.indexOf("天") > 0) {
				FaceConversionUtil.MygetColor(mContext, holder.time_Text,
						R.color.yellow);
				holder.time_Text.setText(time);
			} else {
				holder.time_Text.setText(time);
			}
			try {
				JSONObject ob = new JSONObject(getItem(position).get(
						"author_info").toString());
				holder.head.setImageUrl(ob.getString("avatar_middle"));
				holder.uname_Text.setText(ob.getString("uname"));
			} catch (Exception e) {
				Log.d(TAG, e.toString());
			}}
			break;
		}
		addListener(convertView, type, position);
		return convertView;
	}

	public void addListener(View convertView, int type, int position) {
		final int arg2 = position;
		switch (type) {
		case type1:
			final Button button1 = ((Button) convertView
					.findViewById(R.id.follow));
			button1.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					if (button1.getText().equals("关注")) {
						loading = new LoadingAlertAnim(mContext,
								R.style.MyDialogStyle, "正在关注");
						loading.setCanceledOnTouchOutside(false);
						loading.show();
						DialogUtils.dialogBuilder((Activity) mContext, "关注",
								"确定要关注？", new DialogCallBack() {
									@Override
									public void callBack() {
										new Thread(new Runnable() {
											@Override
											public void run() {
												List<BasicNameValuePair> params = new ArrayList<BasicNameValuePair>();
												params.add(new BasicNameValuePair("oauth_token", ((Mykey)mContext.getApplicationContext()).getOauth_token()));
												params.add(new BasicNameValuePair("oauth_token_secret",
														((Mykey)mContext.getApplicationContext()).getOauth_token_secret()));
												params.add(new BasicNameValuePair("id", b.getString("id")));
												params.add(new BasicNameValuePair("user_id",((Mykey)mContext.getApplicationContext()).getUid()));
												String result=ClientUtils.post_str(ClientUtils.BASE_URL+mContext.getString(ClientUtils.Weiba_create), params,(Activity)mContext);
												if (result.equals("1")) {
													Message message = new Message();
													message.what = success;
													message.obj = button1;
													handler.sendMessage(message);
												} else {
													Message message = new Message();
													message.what = error;
													handler.sendMessage(message);
												}
											}
										}).start();
									}
								});
					}
					if (button1.getText().equals("取消关注")) {
						DialogUtils.dialogBuilder((Activity) mContext, "取消关注",
								"是否要取消关注？", new DialogCallBack() {
									@Override
									public void callBack() {
										new Thread(new Runnable() {
											@Override
											public void run() {
												// TODO Auto-generated method
												// stub
												List<BasicNameValuePair> params = new ArrayList<BasicNameValuePair>();
												params.add(new BasicNameValuePair("oauth_token", ((Mykey)mContext.getApplicationContext()).getOauth_token()));
												params.add(new BasicNameValuePair("oauth_token_secret",
														((Mykey)mContext.getApplicationContext()).getOauth_token_secret()));
												params.add(new BasicNameValuePair("id", b.getString("id")));
												params.add(new BasicNameValuePair("user_id",((Mykey)mContext.getApplicationContext()).getUid()));
												String result=ClientUtils.post_str(ClientUtils.BASE_URL+mContext.getString(ClientUtils.Weiba_destroy), params,(Activity)mContext);
												if (result.equals("1")) {
													Button button2 = button1;
													Message message = new Message();
													message.what = success1;
													message.obj = button2;
													handler.sendMessage(message);
												} else {
													Message message = new Message();
													message.what = error1;
													handler.sendMessage(message);
												}
											}
										}).start();
									}
								});
					}
				}
			});
			break;
		case type2:
			((SmartImageView) convertView.findViewById(R.id.imageView1))
					.setOnClickListener(new View.OnClickListener() {
						public void onClick(View v) {
							try {
								JSONObject ob = new JSONObject(getItem(arg2)
										.get("author_info").toString());
								Mykey app = (Mykey) mContext
										.getApplicationContext();
								app.setOtheruid(ob.getString("uid"));
								Intent intent = new Intent(mContext,
										OtherUserPanl.class);
								mContext.startActivity(intent);
							} catch (Exception e) {
								Log.d(TAG, e.toString());
							}
						}
					});
			convertView.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					Bundle b = new Bundle();
					b.putString("id", getItem(arg2).get("post_id").toString());
					b.putString("author_info", getItem(arg2).get("author_info")
							.toString());
					Intent intent = new Intent(mContext, WeiBaDetail.class);
					intent.putExtras(b);
					mContext.startActivity(intent);
				}
			});
			break;
		}
	}

	TextView close, favorite, comment, del;
	private PopupWindow popupWindow;

	private void initPopWindow() {
		View popView = inflater.inflate(R.layout.weibapopwindow, null);
		popupWindow = new PopupWindow(popView, LayoutParams.WRAP_CONTENT,
				LayoutParams.WRAP_CONTENT);
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

			}
		});
		comment.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent i = new Intent(mContext, ReplayWeiBaComment.class);
				i.putExtra("post_id", getItem(postion).get("post_id")
						.toString());
				mContext.startActivity(i);
			}
		});
		del.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				DialogUtils.dialogBuilder((Activity) mContext, "删除帖子",
						"是否删除帖子？", new DialogCallBack() {
							@Override
							public void callBack() {
								// TODO Auto-generated method stub
								FaceConversionUtil.dispToast(
										(Activity) mContext, "没有找到API");
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

	private class ViewHolder {
		SmartImageView head;
		TextView uname_Text, content_Text, time_Text, favo_Text, comm_Text,
				title_Text;
		ImageView more;
	}

	private class WeiBaInforHolder {
		SmartImageView head_ic;
		TextView followcount_Text, Thread_Text, title_text;
		Button follow_btn;
	}
}
