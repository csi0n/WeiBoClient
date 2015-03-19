/**
 * weibotest
 * 
 * 
 * TODO
 */

package com.weibo.adapters;

import java.util.ArrayList;
import java.util.List;

import com.weibo.utils.*;

import org.apache.http.message.BasicNameValuePair;

import com.weibo.activity.*;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.SpannableString;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.loopj.android.image.SmartImageView;
import com.weibo.R;
import com.weibo.activity.selectpop.SelectPopupWindow;
import com.weibo.application.*;
import com.weibo.card.User;
import com.weibo.card.WeiBoCard;
import com.weibo.utils.ClientUtils;
import com.weibo.utils.DialogUtils.DialogCallBack;
import com.weibo.utils.FaceConversionUtil;
/**
 * @作者:陈华清
 * 
 * @版本:1.0
 * @生成时期:2014年7月19日 下午12:15:18
 * @com.api
 */
public class OtherUserPanlAdapter extends BaseAdapter {
	LayoutInflater mInflater;
	private Context xcontext;
	private static final String TAG = "OtherUserPanl";
	final int type1 = 0;
	final int type2 = 1;
	Button btn;
	List<WeiBoCard> weibolist_card;
	User user_card;
	protected static final int follow = 0;
	protected static final int follow_fail = 1;
	protected static final int intent = 2;
	private Handler handler = new Handler() {
		@SuppressLint("ResourceAsColor")
		public void handleMessage(Message msg) {
			switch (msg.what) {
			case follow:
				String k_key = (String) msg.obj;
				if (k_key.equals("follow_cancel")) {
					FaceConversionUtil.MygetColor(xcontext, btn, R.color.red, null);
					btn.setText("取消关注");
				} else if (k_key.equals("follow")) {
					FaceConversionUtil.MygetColor(xcontext, btn, R.color.lanse, null);
					btn.setText("关注");
				}
				break;
			case follow_fail:
				String key = (String) msg.obj;
				if (key.equals("follow")) {
					FaceConversionUtil.dispToast((Activity) xcontext, "不能关注自己！");
				} else if (key.equals("follow_cancel")) {
					FaceConversionUtil.dispToast((Activity) xcontext, "不能取消关注自己！");
				}
				break;
			}
		}
	};
	private Context mContext;

	public OtherUserPanlAdapter(Context ctx, List<WeiBoCard> weibolist, User user) {
		xcontext = ctx;
		this.mContext = ctx;
		this.weibolist_card = weibolist;
		this.user_card = user;
		this.mInflater = LayoutInflater.from(xcontext);
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return weibolist_card.size() + 1;
	}

	@Override
	public WeiBoCard getItem(int position) {
		// TODO Auto-generated method stub
		return weibolist_card.get(position - 1);
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

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		ViewHolder viewHolder = null;
		ViewHolderWeiBoDetail weiboviewHolder = null;
		int type = getItemViewType(position);
		if (convertView == null) {
			Log.e(TAG, "covertView NULL");
			switch (type) {
			case type1:
				viewHolder = new ViewHolder();
				convertView = mInflater.inflate(R.layout.otheruserpanl, null);
				viewHolder.intro = (TextView) convertView.findViewById(R.id.intro);
				viewHolder.head_ic = (SmartImageView) convertView.findViewById(R.id.image);
				viewHolder.user_Name = (TextView) convertView.findViewById(R.id.textView1);
				viewHolder.follow_btn = (Button) convertView.findViewById(R.id.button1);
				viewHolder.person_btn = (Button) convertView.findViewById(R.id.button2);
				viewHolder.weibo_Show = (Button) convertView.findViewById(R.id.button3);
				viewHolder.following_Show = (Button) convertView.findViewById(R.id.button4);
				viewHolder.follows_Show = (Button) convertView.findViewById(R.id.button5);
				break;
			case type2:
				weiboviewHolder = new ViewHolderWeiBoDetail();
				convertView = mInflater.inflate(R.layout.main_weibo_listitem, null);
				weiboviewHolder.head_pic = (SmartImageView) convertView.findViewById(R.id.imageView1);
				weiboviewHolder.more = (ImageView) convertView.findViewById(R.id.imageView2);
				weiboviewHolder.userName_Text = (TextView) convertView.findViewById(R.id.textView1);
				weiboviewHolder.content_Text = (TextView) convertView.findViewById(R.id.textView2);
				weiboviewHolder.From_device = (TextView) convertView.findViewById(R.id.textView6);
				weiboviewHolder.Date_Text = (TextView) convertView.findViewById(R.id.textView5);
				weiboviewHolder.item_pic1 = (SmartImageView) convertView.findViewById(R.id.item_image_1);
				weiboviewHolder.item_pic2 = (SmartImageView) convertView.findViewById(R.id.item_image_2);
				weiboviewHolder.item_pic3 = (SmartImageView) convertView.findViewById(R.id.item_image_3);
				weiboviewHolder.item_pic4 = (SmartImageView) convertView.findViewById(R.id.item_image_4);
				weiboviewHolder.item_pic5 = (SmartImageView) convertView.findViewById(R.id.item_image_5);
				weiboviewHolder.item_pic6 = (SmartImageView) convertView.findViewById(R.id.item_image_6);
				weiboviewHolder.item_pic7 = (SmartImageView) convertView.findViewById(R.id.item_image_7);
				weiboviewHolder.item_pic8 = (SmartImageView) convertView.findViewById(R.id.item_image_8);
				weiboviewHolder.item_pic9 = (SmartImageView) convertView.findViewById(R.id.item_image_9);
				weiboviewHolder.reitem_pic1 = (SmartImageView) convertView.findViewById(R.id.reitem_image_1);
				weiboviewHolder.reitem_pic2 = (SmartImageView) convertView.findViewById(R.id.reitem_image_2);
				weiboviewHolder.reitem_pic3 = (SmartImageView) convertView.findViewById(R.id.reitem_image_3);
				weiboviewHolder.reitem_pic4 = (SmartImageView) convertView.findViewById(R.id.reitem_image_4);
				weiboviewHolder.reitem_pic5 = (SmartImageView) convertView.findViewById(R.id.reitem_image_5);
				weiboviewHolder.reitem_pic6 = (SmartImageView) convertView.findViewById(R.id.reitem_image_6);
				weiboviewHolder.reitem_pic7 = (SmartImageView) convertView.findViewById(R.id.reitem_image_7);
				weiboviewHolder.reitem_pic8 = (SmartImageView) convertView.findViewById(R.id.reitem_image_8);
				weiboviewHolder.reitem_pic9 = (SmartImageView) convertView.findViewById(R.id.reitem_image_9);
				weiboviewHolder.reuserName_Text = (TextView) convertView.findViewById(R.id.reporttextView1);
				weiboviewHolder.recontent_Text = (TextView) convertView.findViewById(R.id.reporttextView2);
				weiboviewHolder.pic_Layout_1 = (LinearLayout) convertView.findViewById(R.id.linearLayout1);
				weiboviewHolder.pic_Layout_2 = (LinearLayout) convertView.findViewById(R.id.linearLayout2);
				weiboviewHolder.pic_Layout_3 = (LinearLayout) convertView.findViewById(R.id.linearLayout3);
				weiboviewHolder.repic_Layout_1 = (LinearLayout) convertView.findViewById(R.id.reportlinearLayout1);
				weiboviewHolder.repic_Layout_2 = (LinearLayout) convertView.findViewById(R.id.reportlinearLayout2);
				weiboviewHolder.repic_Layout_3 = (LinearLayout) convertView.findViewById(R.id.reportlinearLayout3);
				weiboviewHolder.comment_Text = (TextView) convertView.findViewById(R.id.textView3);
				weiboviewHolder.replay_Text = (TextView) convertView.findViewById(R.id.textView4);
				weiboviewHolder.more_Text = (TextView) convertView.findViewById(R.id.morepop);
				weiboviewHolder.report = (RelativeLayout) convertView.findViewById(R.id.report);
				break;
			}
		} else {
			Log.d(TAG, "covertView不是空的!");
			switch (type) {
			case type1:
				viewHolder = new ViewHolder();
				convertView = mInflater.inflate(R.layout.otheruserpanl, null);
				viewHolder.intro = (TextView) convertView.findViewById(R.id.intro);
				viewHolder.head_ic = (SmartImageView) convertView.findViewById(R.id.image);
				viewHolder.user_Name = (TextView) convertView.findViewById(R.id.textView1);
				viewHolder.follow_btn = (Button) convertView.findViewById(R.id.button1);
				viewHolder.person_btn = (Button) convertView.findViewById(R.id.button2);
				viewHolder.weibo_Show = (Button) convertView.findViewById(R.id.button3);
				viewHolder.following_Show = (Button) convertView.findViewById(R.id.button4);
				viewHolder.follows_Show = (Button) convertView.findViewById(R.id.button5);
				break;
			case type2:
				weiboviewHolder = new ViewHolderWeiBoDetail();
				convertView = mInflater.inflate(R.layout.main_weibo_listitem, null);
				weiboviewHolder.head_pic = (SmartImageView) convertView.findViewById(R.id.imageView1);
				weiboviewHolder.userName_Text = (TextView) convertView.findViewById(R.id.textView1);
				weiboviewHolder.content_Text = (TextView) convertView.findViewById(R.id.textView2);
				weiboviewHolder.From_device = (TextView) convertView.findViewById(R.id.textView6);
				weiboviewHolder.Date_Text = (TextView) convertView.findViewById(R.id.textView5);
				weiboviewHolder.item_pic1 = (SmartImageView) convertView.findViewById(R.id.item_image_1);
				weiboviewHolder.item_pic2 = (SmartImageView) convertView.findViewById(R.id.item_image_2);
				weiboviewHolder.item_pic3 = (SmartImageView) convertView.findViewById(R.id.item_image_3);
				weiboviewHolder.item_pic4 = (SmartImageView) convertView.findViewById(R.id.item_image_4);
				weiboviewHolder.item_pic5 = (SmartImageView) convertView.findViewById(R.id.item_image_5);
				weiboviewHolder.item_pic6 = (SmartImageView) convertView.findViewById(R.id.item_image_6);
				weiboviewHolder.item_pic7 = (SmartImageView) convertView.findViewById(R.id.item_image_7);
				weiboviewHolder.item_pic8 = (SmartImageView) convertView.findViewById(R.id.item_image_8);
				weiboviewHolder.item_pic9 = (SmartImageView) convertView.findViewById(R.id.item_image_9);
				weiboviewHolder.reitem_pic1 = (SmartImageView) convertView.findViewById(R.id.reitem_image_1);
				weiboviewHolder.reitem_pic2 = (SmartImageView) convertView.findViewById(R.id.reitem_image_2);
				weiboviewHolder.reitem_pic3 = (SmartImageView) convertView.findViewById(R.id.reitem_image_3);
				weiboviewHolder.reitem_pic4 = (SmartImageView) convertView.findViewById(R.id.reitem_image_4);
				weiboviewHolder.reitem_pic5 = (SmartImageView) convertView.findViewById(R.id.reitem_image_5);
				weiboviewHolder.reitem_pic6 = (SmartImageView) convertView.findViewById(R.id.reitem_image_6);
				weiboviewHolder.reitem_pic7 = (SmartImageView) convertView.findViewById(R.id.reitem_image_7);
				weiboviewHolder.reitem_pic8 = (SmartImageView) convertView.findViewById(R.id.reitem_image_8);
				weiboviewHolder.reitem_pic9 = (SmartImageView) convertView.findViewById(R.id.reitem_image_9);
				weiboviewHolder.reuserName_Text = (TextView) convertView.findViewById(R.id.reporttextView1);
				weiboviewHolder.recontent_Text = (TextView) convertView.findViewById(R.id.reporttextView2);
				weiboviewHolder.pic_Layout_1 = (LinearLayout) convertView.findViewById(R.id.linearLayout1);
				weiboviewHolder.pic_Layout_2 = (LinearLayout) convertView.findViewById(R.id.linearLayout2);
				weiboviewHolder.pic_Layout_3 = (LinearLayout) convertView.findViewById(R.id.linearLayout3);
				weiboviewHolder.repic_Layout_1 = (LinearLayout) convertView.findViewById(R.id.reportlinearLayout1);
				weiboviewHolder.repic_Layout_2 = (LinearLayout) convertView.findViewById(R.id.reportlinearLayout2);
				weiboviewHolder.repic_Layout_3 = (LinearLayout) convertView.findViewById(R.id.reportlinearLayout3);
				weiboviewHolder.comment_Text = (TextView) convertView.findViewById(R.id.textView3);
				weiboviewHolder.replay_Text = (TextView) convertView.findViewById(R.id.textView4);
				weiboviewHolder.more_Text = (TextView) convertView.findViewById(R.id.morepop);
				weiboviewHolder.report = (RelativeLayout) convertView.findViewById(R.id.report);
				break;
			}
		}
		switch (type) {
		case type1:
			if (user_card.getIntro() != null) {
				viewHolder.intro.setText(user_card.getIntro());
			}
			if (user_card.getUname() != null) {
				viewHolder.user_Name.setText(user_card.getUname());
			}
			if (user_card.getAvatar_middle() != null) {
				viewHolder.head_ic.setImageUrl(user_card.getAvatar_middle());
			}
			if (user_card.getCount_info() != null) {
				if (user_card.getCount_info().getWeibo_count() != null) {
					viewHolder.weibo_Show.setText("微博:" + user_card.getCount_info().getWeibo_count());
				}
				if (user_card.getCount_info().getFollowing_count() != null) {
					viewHolder.following_Show.setText("关注:" + user_card.getCount_info().getFollowing_count());
				}
				if (user_card.getCount_info().getFollower_count() != null) {
					viewHolder.follows_Show.setText("粉丝:" + user_card.getCount_info().getFollower_count());
				}
			}
			if (user_card.getUid() != null) {
				if (user_card.getUid().equals(((Mykey) xcontext.getApplicationContext()).getUid())) {
					if (viewHolder.person_btn.getVisibility() == View.VISIBLE) {
						viewHolder.person_btn.setVisibility(View.GONE);
					}
				} else {
					if (viewHolder.person_btn.getVisibility() == View.GONE) {
						viewHolder.person_btn.setVisibility(View.VISIBLE);
					}
				}
			}
			break;
		case type2:
			if (getItem(position).getComment_count() != null) {
				weiboviewHolder.comment_Text.setText("评论:" + getItem(position).getComment_count());
			}
			if (getItem(position).getRepost_count() != null) {
				weiboviewHolder.replay_Text.setText("转发:" + getItem(position).getRepost_count());
			}
			if (getItem(position).getAvatar_middle() != null) {
				weiboviewHolder.head_pic.setImageUrl(getItem(position).getAvatar_middle());
			}
			if (getItem(position).getUname() != null) {
				weiboviewHolder.userName_Text.setText(getItem(position).getUname());
			}
			if (getItem(position).getContent() != null) {
				SpannableString spannableString = FaceConversionUtil.getInstace().getExpressionString(xcontext, getItem(position).getContent());
				weiboviewHolder.content_Text.setText(spannableString);
				FaceConversionUtil.extractMention2Link(weiboviewHolder.content_Text);
			}
			if (getItem(position).getCtime() != null) {
				weiboviewHolder.Date_Text.setText(FaceConversionUtil.TimeStamp2Date(getItem(position).getCtime()));
			} else {
				weiboviewHolder.Date_Text.setVisibility(View.GONE);
			}
			if (getItem(position).getFrom() != null) {
				String key = getItem(position).getFrom();
				if (key.equals("0")) {
					weiboviewHolder.From_device.setText("来自网页");
				} else if (key.equals("1")) {
					weiboviewHolder.From_device.setText("来自手机网页");
				} else if (key.equals("2")) {
					weiboviewHolder.From_device.setText("来自iphone");
				} else if (key.equals("3")) {
					weiboviewHolder.From_device.setText("来自Android");
				} else if (key.equals("4")) {
					weiboviewHolder.From_device.setText("来自ipad");
				} else {
					weiboviewHolder.From_device.setText("来自火星");
				}
			}
			if (getItem(position).getAttach() != null) {
				try {
					for (int i = 0; i < getItem(position).getAttach().size(); i++) {
						if (i == 0) {
							weiboviewHolder.item_pic1.setImageUrl(getItem(position).getAttach().get(i).getAttach_middle());
							weiboviewHolder.item_pic1.setVisibility(View.VISIBLE);
						}
						if (i == 1) {
							weiboviewHolder.item_pic2.setImageUrl(getItem(position).getAttach().get(i).getAttach_middle());
							weiboviewHolder.item_pic2.setVisibility(View.VISIBLE);
						}
						if (i == 2) {
							weiboviewHolder.item_pic3.setImageUrl(getItem(position).getAttach().get(i).getAttach_middle());
							weiboviewHolder.item_pic3.setVisibility(View.VISIBLE);
						}
						if (i == 3) {
							weiboviewHolder.item_pic4.setImageUrl(getItem(position).getAttach().get(i).getAttach_middle());
							weiboviewHolder.item_pic4.setVisibility(View.VISIBLE);
						}
						if (i == 4) {
							weiboviewHolder.item_pic5.setImageUrl(getItem(position).getAttach().get(i).getAttach_middle());
							weiboviewHolder.item_pic5.setVisibility(View.VISIBLE);
						}
						if (i == 5) {
							weiboviewHolder.item_pic6.setImageUrl(getItem(position).getAttach().get(i).getAttach_middle());
							weiboviewHolder.item_pic6.setVisibility(View.VISIBLE);
						}
						if (i == 6) {
							weiboviewHolder.item_pic7.setImageUrl(getItem(position).getAttach().get(i).getAttach_middle());
							weiboviewHolder.item_pic7.setVisibility(View.VISIBLE);
						}
						if (i == 7) {
							weiboviewHolder.item_pic8.setImageUrl(getItem(position).getAttach().get(i).getAttach_middle());
							weiboviewHolder.item_pic8.setVisibility(View.VISIBLE);
						}
						if (i == 8) {
							weiboviewHolder.item_pic9.setImageUrl(getItem(position).getAttach().get(i).getAttach_middle());
							weiboviewHolder.item_pic9.setVisibility(View.VISIBLE);
						}
					}
				} catch (Exception e) {
					Log.d(TAG, e.toString());
				}
			}
			if (getItem(position).getTranspond_data() != null) {
				try {
					if (getItem(position).getTranspond_data().getUname() != null) {
						weiboviewHolder.reuserName_Text.setText(getItem(position).getTranspond_data().getUname());
						weiboviewHolder.reuserName_Text.setVisibility(View.VISIBLE);
					}
					if (getItem(position).getTranspond_data().getContent() != null) {
						SpannableString spannableString = FaceConversionUtil.getInstace().getExpressionString(xcontext, getItem(position).getTranspond_data().getContent());
						weiboviewHolder.recontent_Text.setText(spannableString);
						FaceConversionUtil.extractMention2Link(weiboviewHolder.content_Text);
						weiboviewHolder.recontent_Text.setVisibility(View.VISIBLE);
					}
					if (getItem(position).getTranspond_data().getAttach() != null) {
						for (int i = 0; i < getItem(position).getTranspond_data().getAttach().size(); i++) {
							if (i == 0) {
								weiboviewHolder.reitem_pic1.setImageUrl(getItem(position).getTranspond_data().getAttach().get(i).getAttach_middle());
								weiboviewHolder.reitem_pic1.setVisibility(View.VISIBLE);
							}
							if (i == 1) {
								weiboviewHolder.reitem_pic2.setImageUrl(getItem(position).getTranspond_data().getAttach().get(i).getAttach_middle());
								weiboviewHolder.reitem_pic2.setVisibility(View.VISIBLE);
							}
							if (i == 2) {
								weiboviewHolder.reitem_pic3.setImageUrl(getItem(position).getTranspond_data().getAttach().get(i).getAttach_middle());
								weiboviewHolder.reitem_pic3.setVisibility(View.VISIBLE);
							}
							if (i == 3) {
								weiboviewHolder.reitem_pic4.setImageUrl(getItem(position).getTranspond_data().getAttach().get(i).getAttach_middle());
								weiboviewHolder.reitem_pic4.setVisibility(View.VISIBLE);
							}
							if (i == 4) {
								weiboviewHolder.reitem_pic5.setImageUrl(getItem(position).getTranspond_data().getAttach().get(i).getAttach_middle());
								weiboviewHolder.reitem_pic5.setVisibility(View.VISIBLE);
							}
							if (i == 5) {
								weiboviewHolder.reitem_pic6.setImageUrl(getItem(position).getTranspond_data().getAttach().get(i).getAttach_middle());
								weiboviewHolder.reitem_pic6.setVisibility(View.VISIBLE);
							}
							if (i == 6) {
								weiboviewHolder.reitem_pic7.setImageUrl(getItem(position).getTranspond_data().getAttach().get(i).getAttach_middle());
								weiboviewHolder.reitem_pic7.setVisibility(View.VISIBLE);
							}
							if (i == 7) {
								weiboviewHolder.reitem_pic8.setImageUrl(getItem(position).getTranspond_data().getAttach().get(i).getAttach_middle());
								weiboviewHolder.reitem_pic8.setVisibility(View.VISIBLE);
							}
							if (i == 8) {
								weiboviewHolder.reitem_pic9.setImageUrl(getItem(position).getTranspond_data().getAttach().get(i).getAttach_middle());
								weiboviewHolder.reitem_pic9.setVisibility(View.VISIBLE);
							}
						}
					}
				} catch (Exception e) {
					Log.d(TAG, e.toString());
				}
			} else {
				weiboviewHolder.report.setVisibility(View.GONE);
			}
			break;
		}
		addListener(convertView, type, position);
		return convertView;
	}

	class ViewHolder {
		SmartImageView head_ic;
		TextView user_Name, intro;
		Button person_btn, follow_btn, weibo_Show, following_Show, follows_Show;

	}

	public void addListener(final View convert, final int type, final int position) {
		switch (type) {
		case type1:
			((TextView) convert.findViewById(R.id.intro)).setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					Common.USER_INFOR = user_card;
					Intent i = new Intent(xcontext, UserPanl.class);
					xcontext.startActivity(i);
				}
			});
			((Button) convert.findViewById(R.id.button2)).setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					Intent y = new Intent(xcontext, ChatWithFriend.class);
					Bundle q = new Bundle();
					q.putString("from_uname", user_card.getUname());
					q.putString("to_uid", user_card.getUid());
					y.putExtras(q);
					xcontext.startActivity(y);
				}
			});
			((Button) convert.findViewById(R.id.button1)).setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					btn = (Button) convert.findViewById(R.id.button1);
					if (btn.getText().equals("关注")) {
						DialogUtils.dialogBuilder((Activity) xcontext, "提示", "确定要关注吗？", new DialogCallBack() {
							@Override
							public void callBack() {
								new Thread(new Runnable() {
									@Override
									public void run() {
										// TODO Auto-generated
										// method stub
										Mykey app = (Mykey) xcontext.getApplicationContext();
										if (app.getOtheruid().equals(app.getUid())) {
											String key = "follow";
											Message message = new Message();
											message.what = follow_fail;
											message.obj = key;
											handler.sendMessage(message);
										} else {
											List<BasicNameValuePair> params = new ArrayList<BasicNameValuePair>();
											params.add(new BasicNameValuePair("oauth_token", app.getOauth_token()));
											params.add(new BasicNameValuePair("oauth_token_secret", app.getOauth_token_secret()));
											params.add(new BasicNameValuePair("user_id", app.getUid()));
											params.add(new BasicNameValuePair("fid", app.getOtheruid()));
											ClientUtils.post_str(ClientUtils.BASE_URL + xcontext.getString(ClientUtils.User_follow_create), params, (Activity) xcontext);
											String k_key = "follow_cancel";
											Message message = new Message();
											message.what = follow;
											message.obj = k_key;
											handler.sendMessage(message);
										}
										Log.d(TAG, "关注");
									}
								}).start();
							}
						});

					} else if (btn.getText().equals("取消关注")) {
						DialogUtils.dialogBuilder((Activity) xcontext, "提示", "确定要取消关注吗？", new DialogCallBack() {

							@Override
							public void callBack() {

								new Thread(new Runnable() {

									@Override
									public void run() {

										// TODO Auto-generated
										// method stub
										Mykey app = (Mykey) xcontext.getApplicationContext();
										if (app.getOtheruid().equals(app.getUid())) {
											String key = "follow_cancel";
											Message message = new Message();
											message.what = follow_fail;
											message.obj = key;
											handler.sendMessage(message);
										} else {
											List<BasicNameValuePair> params = new ArrayList<BasicNameValuePair>();
											params.add(new BasicNameValuePair("oauth_token", app.getOauth_token()));
											params.add(new BasicNameValuePair("oauth_token_secret", app.getOauth_token_secret()));
											params.add(new BasicNameValuePair("user_id", app.getUid()));
											params.add(new BasicNameValuePair("fid", app.getOtheruid()));
											ClientUtils.post_str(ClientUtils.BASE_URL + xcontext.getString(ClientUtils.User_follow_destroy), params, (Activity) xcontext);
											String k_key = "follow";
											Message message = new Message();
											message.what = follow;
											message.obj = k_key;
											handler.sendMessage(message);
										}
									}
								}).start();
							}
						});

					}
				}
			});
			((Button) convert.findViewById(R.id.button4)).setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					Intent i = new Intent(xcontext, FollowList.class);
					i.putExtra("a", "2");
					xcontext.startActivity(i);
				}
			});
			((Button) convert.findViewById(R.id.button5)).setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					Intent i = new Intent(xcontext, FollowList.class);
					i.putExtra("a", "1");
					xcontext.startActivity(i);
				}
			});
			break;
		case type2:
			((ImageView) convert.findViewById(R.id.imageView2)).setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					Mykey app = (Mykey) xcontext.getApplicationContext();
					app.setFeed_id(getItem(position).getFeed_id());
					app.setMymap(getItem(position));
					app.setDel_weibo_id(String.valueOf(position));
					Intent intent = new Intent(xcontext, SelectPopupWindow.class);
					xcontext.startActivity(intent);
				}
			});
			convert.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					((Mykey) xcontext.getApplicationContext()).setMymap(getItem(position));
					Intent intent = new Intent(xcontext, WeiBoDetail.class);
					xcontext.startActivity(intent);
				}
			});
			((SmartImageView) convert.findViewById(R.id.item_image_1)).setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					ImageArrayList.ATTACH = getItem(position).getAttach();
					ImageArrayList.PICCURRENT = 0;
					Intent intent = new Intent(mContext, ShowWeiBoPic.class);
					mContext.startActivity(intent);
				}
			});
			((SmartImageView) convert.findViewById(R.id.item_image_2)).setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					ImageArrayList.ATTACH = getItem(position).getAttach();
					ImageArrayList.PICCURRENT = 1;
					Intent intent = new Intent(mContext, ShowWeiBoPic.class);
					mContext.startActivity(intent);
				}
			});
			((SmartImageView) convert.findViewById(R.id.item_image_3)).setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					ImageArrayList.ATTACH = getItem(position).getAttach();
					ImageArrayList.PICCURRENT = 2;
					Intent intent = new Intent(mContext, ShowWeiBoPic.class);
					mContext.startActivity(intent);
				}
			});
			((SmartImageView) convert.findViewById(R.id.item_image_4)).setOnClickListener(new View.OnClickListener() {

				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub

					ImageArrayList.ATTACH = getItem(position).getAttach();
					ImageArrayList.PICCURRENT = 3;
					Intent intent = new Intent(mContext, ShowWeiBoPic.class);
					mContext.startActivity(intent);
				}
			});
			((SmartImageView) convert.findViewById(R.id.item_image_5)).setOnClickListener(new View.OnClickListener() {

				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					ImageArrayList.ATTACH = getItem(position).getAttach();

					ImageArrayList.PICCURRENT = 4;
					Intent intent = new Intent(mContext, ShowWeiBoPic.class);
					mContext.startActivity(intent);
				}
			});
			((SmartImageView) convert.findViewById(R.id.item_image_6)).setOnClickListener(new View.OnClickListener() {

				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					ImageArrayList.ATTACH = getItem(position).getAttach();

					ImageArrayList.PICCURRENT = 5;
					Intent intent = new Intent(mContext, ShowWeiBoPic.class);
					mContext.startActivity(intent);
				}
			});
			((SmartImageView) convert.findViewById(R.id.item_image_7)).setOnClickListener(new View.OnClickListener() {

				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub

					ImageArrayList.ATTACH = getItem(position).getAttach();
					ImageArrayList.PICCURRENT = 6;
					Intent intent = new Intent(mContext, ShowWeiBoPic.class);
					mContext.startActivity(intent);
				}
			});
			((SmartImageView) convert.findViewById(R.id.item_image_8)).setOnClickListener(new View.OnClickListener() {

				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					ImageArrayList.ATTACH = getItem(position).getAttach();

					ImageArrayList.PICCURRENT = 7;
					Intent intent = new Intent(mContext, ShowWeiBoPic.class);
					mContext.startActivity(intent);
				}
			});
			((SmartImageView) convert.findViewById(R.id.item_image_9)).setOnClickListener(new View.OnClickListener() {

				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub

					ImageArrayList.ATTACH = getItem(position).getAttach();
					ImageArrayList.PICCURRENT = 8;
					Intent intent = new Intent(mContext, ShowWeiBoPic.class);
					mContext.startActivity(intent);
				}
			});
			((SmartImageView) convert.findViewById(R.id.reitem_image_1)).setOnClickListener(new View.OnClickListener() {

				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub

					ImageArrayList.ATTACH = getItem(position).getTranspond_data().getAttach();
					ImageArrayList.PICCURRENT = 0;
					Intent intent = new Intent(mContext, ShowWeiBoPic.class);
					mContext.startActivity(intent);

				}
			});
			((SmartImageView) convert.findViewById(R.id.reitem_image_2)).setOnClickListener(new View.OnClickListener() {

				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					ImageArrayList.ATTACH = getItem(position).getTranspond_data().getAttach();
					ImageArrayList.PICCURRENT = 1;
					Intent intent = new Intent(mContext, ShowWeiBoPic.class);
					mContext.startActivity(intent);
				}
			});
			((SmartImageView) convert.findViewById(R.id.reitem_image_3)).setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					ImageArrayList.ATTACH = getItem(position).getTranspond_data().getAttach();
					ImageArrayList.PICCURRENT = 2;
					Intent intent = new Intent(mContext, ShowWeiBoPic.class);
					mContext.startActivity(intent);
				}
			});
			((SmartImageView) convert.findViewById(R.id.reitem_image_4)).setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					ImageArrayList.ATTACH = getItem(position).getTranspond_data().getAttach();
					ImageArrayList.PICCURRENT = 3;
					Intent intent = new Intent(mContext, ShowWeiBoPic.class);
					mContext.startActivity(intent);
				}
			});
			((SmartImageView) convert.findViewById(R.id.reitem_image_5)).setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					ImageArrayList.ATTACH = getItem(position).getTranspond_data().getAttach();
					ImageArrayList.PICCURRENT = 4;
					Intent intent = new Intent(mContext, ShowWeiBoPic.class);
					mContext.startActivity(intent);
				}
			});
			((SmartImageView) convert.findViewById(R.id.reitem_image_6)).setOnClickListener(new View.OnClickListener() {

				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					ImageArrayList.ATTACH = getItem(position).getTranspond_data().getAttach();
					ImageArrayList.PICCURRENT = 5;
					Intent intent = new Intent(mContext, ShowWeiBoPic.class);
					mContext.startActivity(intent);
				}
			});
			((SmartImageView) convert.findViewById(R.id.reitem_image_7)).setOnClickListener(new View.OnClickListener() {

				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					ImageArrayList.ATTACH = getItem(position).getTranspond_data().getAttach();
					ImageArrayList.PICCURRENT = 6;
					Intent intent = new Intent(mContext, ShowWeiBoPic.class);
					mContext.startActivity(intent);
				}
			});
			((SmartImageView) convert.findViewById(R.id.reitem_image_8)).setOnClickListener(new View.OnClickListener() {

				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub

					ImageArrayList.ATTACH = getItem(position).getTranspond_data().getAttach();
					ImageArrayList.PICCURRENT = 7;
					Intent intent = new Intent(mContext, ShowWeiBoPic.class);
					mContext.startActivity(intent);
				}
			});
			((SmartImageView) convert.findViewById(R.id.reitem_image_9)).setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					ImageArrayList.ATTACH = getItem(position).getTranspond_data().getAttach();
					ImageArrayList.PICCURRENT = 8;
					Intent intent = new Intent(mContext, ShowWeiBoPic.class);
					mContext.startActivity(intent);
				}
			});
			break;
		}
	}

	class ViewHolderWeiBoDetail {
		RelativeLayout Nothing;
		SmartImageView head_pic, item_pic1, item_pic2, item_pic3, item_pic4, item_pic5, item_pic6, item_pic7, item_pic8, item_pic9, reitem_pic1, reitem_pic2, reitem_pic3, reitem_pic4, reitem_pic5, reitem_pic6, reitem_pic7, reitem_pic8, reitem_pic9;
		TextView userName_Text, content_Text, From_device, Date_Text, reuserName_Text, recontent_Text;
		TextView comment_Text, replay_Text, more_Text;
		LinearLayout pic_Layout_1, pic_Layout_2, pic_Layout_3, repic_Layout_1, repic_Layout_2, repic_Layout_3;
		SmartImageView c_head_pic;
		ImageView more;
		TextView c_uName_Text, c_content_Text, c_Date_Text;
		RelativeLayout report;
	}

}
