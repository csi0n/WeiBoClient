/**weibotest
 * 
 *
 *TODO
 */
package com.weibo.adapters;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;

import com.loopj.android.image.SmartImageView;
import com.weibo.activity.Collection;
import com.weibo.activity.FollowList;
import com.weibo.R;
import com.weibo.activity.ShowWeiBoPic;
import com.weibo.activity.UserPanl;
import com.weibo.activity.selectpop.SelectPicPopup;
import com.weibo.activity.selectpop.SelectPopupWindow;
import com.weibo.application.Mykey;
import com.weibo.card.User;
import com.weibo.card.WeiBoCard;
import com.weibo.utils.FaceConversionUtil;
import com.weibo.utils.ImageArrayList;
import com.weibo.view.widget.MyHeadImgeView;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
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
import android.widget.RelativeLayout;
import android.widget.TextView;

/**
 * @作者:陈华清
 * 
 * @版本:1.0
 * @生成时期:2014年7月21日 下午2:38:27
 * @com.api
 */
public class Tab3Adapter extends BaseAdapter {
	LayoutInflater inflater;
	List<WeiBoCard> weibo_list_card;
	private Context mContext;
	User user_card;
	private static final String TAG = "TabAdapter.java";
	final int type1 = 0;
	final int type2 = 1;
	MyHeadImgeView head;
	ArrayList<String> a;

	public Tab3Adapter(Context ctx, List<WeiBoCard> weibo_list_card, User user_card) {
		this.mContext = ctx;
		this.weibo_list_card = weibo_list_card;
		this.user_card = user_card;
		inflater = LayoutInflater.from(mContext);
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return weibo_list_card.size() + 1;
	}

	@Override
	public WeiBoCard getItem(int position) {
		// TODO Auto-generated method stub
		return weibo_list_card.get(position - 1);
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
		ViewHolder WeiBomain = null;
		ViewUserPanl userPan = null;
		int type = getItemViewType(position);
		if (convertView == null) {
			switch (type) {
			case type1:
				userPan = new ViewUserPanl();
				convertView = inflater.inflate(R.layout.tab3no1_item, null);
				userPan.head_ic = (MyHeadImgeView) convertView.findViewById(R.id.imageView1);
				userPan.uName_Text = (TextView) convertView.findViewById(R.id.textView1);
				userPan.intro_Text = (TextView) convertView.findViewById(R.id.textView2);
				userPan.weibo_btn = (Button) convertView.findViewById(R.id.button1);
				userPan.followingg_btn = (Button) convertView.findViewById(R.id.button2);
				userPan.follows_btn = (Button) convertView.findViewById(R.id.button3);
				userPan.collect_btn = (Button) convertView.findViewById(R.id.button4);
				break;
			case type2:
				WeiBomain = new ViewHolder();
				convertView = inflater.inflate(R.layout.main_weibo_listitem, parent, false);
				WeiBomain.more = (ImageView) convertView.findViewById(R.id.imageView2);
				WeiBomain.textview1 = (TextView) convertView.findViewById(R.id.textView1);
				WeiBomain.textview2 = (TextView) convertView.findViewById(R.id.textView2);
				WeiBomain.textview3 = (TextView) convertView.findViewById(R.id.textView3);
				WeiBomain.textview4 = (TextView) convertView.findViewById(R.id.textView4);
				WeiBomain.textview5 = (TextView) convertView.findViewById(R.id.textView5);
				WeiBomain.textview6 = (TextView) convertView.findViewById(R.id.textView6);
				WeiBomain.imageview0 = (SmartImageView) convertView.findViewById(R.id.imageView1);
				WeiBomain.imagepop = (TextView) convertView.findViewById(R.id.morepop);
				WeiBomain.imageview1 = (SmartImageView) convertView.findViewById(R.id.item_image_1);
				WeiBomain.imageview2 = (SmartImageView) convertView.findViewById(R.id.item_image_2);
				WeiBomain.imageview3 = (SmartImageView) convertView.findViewById(R.id.item_image_3);
				WeiBomain.imageview4 = (SmartImageView) convertView.findViewById(R.id.item_image_4);
				WeiBomain.imageview5 = (SmartImageView) convertView.findViewById(R.id.item_image_5);
				WeiBomain.imageview6 = (SmartImageView) convertView.findViewById(R.id.item_image_6);
				WeiBomain.imageview7 = (SmartImageView) convertView.findViewById(R.id.item_image_7);
				WeiBomain.imageview8 = (SmartImageView) convertView.findViewById(R.id.item_image_8);
				WeiBomain.imageview9 = (SmartImageView) convertView.findViewById(R.id.item_image_9);
				WeiBomain.reimageview1 = (SmartImageView) convertView.findViewById(R.id.reitem_image_1);
				WeiBomain.reimageview2 = (SmartImageView) convertView.findViewById(R.id.reitem_image_2);
				WeiBomain.reimageview3 = (SmartImageView) convertView.findViewById(R.id.reitem_image_3);
				WeiBomain.reimageview4 = (SmartImageView) convertView.findViewById(R.id.reitem_image_4);
				WeiBomain.reimageview5 = (SmartImageView) convertView.findViewById(R.id.reitem_image_5);
				WeiBomain.reimageview6 = (SmartImageView) convertView.findViewById(R.id.reitem_image_6);
				WeiBomain.reimageview7 = (SmartImageView) convertView.findViewById(R.id.reitem_image_7);
				WeiBomain.reimageview8 = (SmartImageView) convertView.findViewById(R.id.reitem_image_8);
				WeiBomain.reimageview9 = (SmartImageView) convertView.findViewById(R.id.reitem_image_9);
				WeiBomain.reporttextview1 = (TextView) convertView.findViewById(R.id.reporttextView1);
				WeiBomain.reporttextview2 = (TextView) convertView.findViewById(R.id.reporttextView2);
				WeiBomain.report = (RelativeLayout) convertView.findViewById(R.id.report);
				break;
			}
		} else {
			switch (type) {
			case type1:
				userPan = new ViewUserPanl();
				convertView = inflater.inflate(R.layout.tab3no1_item, null);
				userPan.head_ic = (MyHeadImgeView) convertView.findViewById(R.id.imageView1);
				userPan.uName_Text = (TextView) convertView.findViewById(R.id.textView1);
				userPan.intro_Text = (TextView) convertView.findViewById(R.id.textView2);
				userPan.weibo_btn = (Button) convertView.findViewById(R.id.button1);
				userPan.followingg_btn = (Button) convertView.findViewById(R.id.button2);
				userPan.follows_btn = (Button) convertView.findViewById(R.id.button3);
				userPan.collect_btn = (Button) convertView.findViewById(R.id.button4);
				break;
			case type2:
				WeiBomain = new ViewHolder();
				convertView = inflater.inflate(R.layout.main_weibo_listitem, parent, false);
				WeiBomain.more = (ImageView) convertView.findViewById(R.id.imageView2);
				WeiBomain.textview1 = (TextView) convertView.findViewById(R.id.textView1);
				WeiBomain.textview2 = (TextView) convertView.findViewById(R.id.textView2);
				WeiBomain.textview3 = (TextView) convertView.findViewById(R.id.textView3);
				WeiBomain.textview4 = (TextView) convertView.findViewById(R.id.textView4);
				WeiBomain.textview5 = (TextView) convertView.findViewById(R.id.textView5);
				WeiBomain.textview6 = (TextView) convertView.findViewById(R.id.textView6);
				WeiBomain.imageview0 = (SmartImageView) convertView.findViewById(R.id.imageView1);
				WeiBomain.imagepop = (TextView) convertView.findViewById(R.id.morepop);
				WeiBomain.imageview1 = (SmartImageView) convertView.findViewById(R.id.item_image_1);
				WeiBomain.imageview2 = (SmartImageView) convertView.findViewById(R.id.item_image_2);
				WeiBomain.imageview3 = (SmartImageView) convertView.findViewById(R.id.item_image_3);
				WeiBomain.imageview4 = (SmartImageView) convertView.findViewById(R.id.item_image_4);
				WeiBomain.imageview5 = (SmartImageView) convertView.findViewById(R.id.item_image_5);
				WeiBomain.imageview6 = (SmartImageView) convertView.findViewById(R.id.item_image_6);
				WeiBomain.imageview7 = (SmartImageView) convertView.findViewById(R.id.item_image_7);
				WeiBomain.imageview8 = (SmartImageView) convertView.findViewById(R.id.item_image_8);
				WeiBomain.imageview9 = (SmartImageView) convertView.findViewById(R.id.item_image_9);
				WeiBomain.reimageview1 = (SmartImageView) convertView.findViewById(R.id.reitem_image_1);
				WeiBomain.reimageview2 = (SmartImageView) convertView.findViewById(R.id.reitem_image_2);
				WeiBomain.reimageview3 = (SmartImageView) convertView.findViewById(R.id.reitem_image_3);
				WeiBomain.reimageview4 = (SmartImageView) convertView.findViewById(R.id.reitem_image_4);
				WeiBomain.reimageview5 = (SmartImageView) convertView.findViewById(R.id.reitem_image_5);
				WeiBomain.reimageview6 = (SmartImageView) convertView.findViewById(R.id.reitem_image_6);
				WeiBomain.reimageview7 = (SmartImageView) convertView.findViewById(R.id.reitem_image_7);
				WeiBomain.reimageview8 = (SmartImageView) convertView.findViewById(R.id.reitem_image_8);
				WeiBomain.reimageview9 = (SmartImageView) convertView.findViewById(R.id.reitem_image_9);
				WeiBomain.reporttextview1 = (TextView) convertView.findViewById(R.id.reporttextView1);
				WeiBomain.reporttextview2 = (TextView) convertView.findViewById(R.id.reporttextView2);
				WeiBomain.report = (RelativeLayout) convertView.findViewById(R.id.report);
				break;
			}
		}
		switch (type) {
		case type1:
			if (user_card != null) {
				userPan.head_ic.setImageUrl(user_card.getAvatar_middle());
				userPan.uName_Text.setText(user_card.getUname());
				if (user_card.getIntro() != null) {
					userPan.intro_Text.setText(user_card.getIntro());
				}
				userPan.weibo_btn.setText("微博:" + user_card.getCount_info().getWeibo_count());
				userPan.followingg_btn.setText("关注:" + user_card.getCount_info().getFollowing_count());
				userPan.follows_btn.setText("粉丝:" + user_card.getCount_info().getFollower_count());
				userPan.collect_btn.setText("收藏:" + user_card.getCount_info().getFavorite_count());
			} else {
				Log.d(TAG, "用户信息是空的");
			}
			break;
		case type2:
			if (getItem(position).getUname() != null) {
				WeiBomain.textview1.setText(getItem(position).getUname());
			} else {
				WeiBomain.textview1.setVisibility(View.GONE);
			}
			if (getItem(position).getContent() != null) {
				SpannableString spannableString = FaceConversionUtil.getInstace().getExpressionString(mContext, getItem(position).getContent());
				WeiBomain.textview2.setText(spannableString);
				FaceConversionUtil.extractMention2Link(WeiBomain.textview2);
			} else {
				WeiBomain.textview2.setVisibility(View.GONE);
			}
			if (getItem(position).getComment_count() != null) {
				WeiBomain.textview3.setText("评论:" + getItem(position).getComment_count());
			} else {
				WeiBomain.textview3.setVisibility(View.GONE);
			}
			if (getItem(position).getRepost_count() != null) {
				WeiBomain.textview4.setText("转发:" + getItem(position).getRepost_count());
			} else {
				WeiBomain.textview4.setVisibility(View.GONE);
			}
			if (getItem(position).getCtime() != null) {
				String time = FaceConversionUtil.getInterval(FaceConversionUtil.TimeStamp2Date(getItem(position).getCtime()));
				if (time.indexOf("刚") > 0 || time.indexOf("前") > 0 || time.indexOf("天") > 0) {
					FaceConversionUtil.MygetColor(mContext, WeiBomain.textview5, R.color.yellow);
					WeiBomain.textview5.setText(time);
				} else {
					WeiBomain.textview5.setText(time);
				}
			} else {
				WeiBomain.textview4.setVisibility(View.GONE);
			}
			if (getItem(position).getAvatar_middle() != null) {
				WeiBomain.imageview0.setImageUrl(getItem(position).getAvatar_middle());
			} else {
				WeiBomain.imageview0.setVisibility(View.GONE);
			}
			if (getItem(position).getFrom() != null) {
				if (getItem(position).getFrom().equals("0")) {
					WeiBomain.textview6.setText("来自网页");
				} else if (getItem(position).getFrom().equals("1")) {
					WeiBomain.textview6.setText("来自手机网页");
				} else if (getItem(position).getFrom().equals("2")) {
					WeiBomain.textview6.setText("来自iphone");
				} else if (getItem(position).getFrom().equals("3")) {
					WeiBomain.textview6.setText("来自Android");
				} else if (getItem(position).getFrom().equals("4")) {
					WeiBomain.textview6.setText("来自Ipad");
				} else {
					WeiBomain.textview6.setText("来自火星");
				}
			} else {
				WeiBomain.textview6.setVisibility(View.GONE);
			}
			if (getItem(position).getAttach() != null) {
				try {
					for (int i = 0; i < getItem(position).getAttach().size(); i++) {
						if (i == 0) {
							WeiBomain.imageview1.setImageUrl(getItem(position).getAttach().get(i).getAttach_middle());
							WeiBomain.imageview1.setVisibility(View.VISIBLE);

						}
						if (i == 1) {
							WeiBomain.imageview2.setImageUrl(getItem(position).getAttach().get(i).getAttach_middle());
							WeiBomain.imageview2.setVisibility(View.VISIBLE);

						}
						if (i == 2) {
							WeiBomain.imageview3.setImageUrl(getItem(position).getAttach().get(i).getAttach_middle());
							WeiBomain.imageview3.setVisibility(View.VISIBLE);

						}
						if (i == 3) {
							WeiBomain.imageview4.setImageUrl(getItem(position).getAttach().get(i).getAttach_middle());
							WeiBomain.imageview4.setVisibility(View.VISIBLE);

						}
						if (i == 4) {
							WeiBomain.imageview5.setImageUrl(getItem(position).getAttach().get(i).getAttach_middle());
							WeiBomain.imageview5.setVisibility(View.VISIBLE);

						}
						if (i == 5) {
							WeiBomain.imageview6.setImageUrl(getItem(position).getAttach().get(i).getAttach_middle());
							WeiBomain.imageview6.setVisibility(View.VISIBLE);

						}
						if (i == 6) {
							WeiBomain.imageview7.setImageUrl(getItem(position).getAttach().get(i).getAttach_middle());
							WeiBomain.imageview7.setVisibility(View.VISIBLE);

						}
						if (i == 7) {
							WeiBomain.imageview8.setImageUrl(getItem(position).getAttach().get(i).getAttach_middle());
							WeiBomain.imageview8.setVisibility(View.VISIBLE);

						}
						if (i == 8) {
							WeiBomain.imageview9.setImageUrl(getItem(position).getAttach().get(i).getAttach_middle());
							WeiBomain.imageview9.setVisibility(View.VISIBLE);

						}
					}

				} catch (Exception e) {
					Log.d(TAG, e.toString());
				}
			}
			if (getItem(position).getTranspond_data() != null) {
				try {
					WeiBomain.reporttextview1.setText(getItem(position).getTranspond_data().getUname());
					SpannableString spannableString = FaceConversionUtil.getInstace().getExpressionString(mContext, getItem(position).getTranspond_data().getContent());
					WeiBomain.reporttextview2.setText(spannableString);
					FaceConversionUtil.extractMention2Link(WeiBomain.reporttextview2);
					if (getItem(position).getTranspond_data().getAttach() != null) {
						for (int i = 0; i < getItem(position).getTranspond_data().getAttach().size(); i++) {
							if (i == 0) {
								WeiBomain.reimageview1.setImageUrl(getItem(position).getTranspond_data().getAttach().get(i).getAttach_middle());
								WeiBomain.reimageview1.setVisibility(View.VISIBLE);
							}
							if (i == 1) {
								WeiBomain.reimageview2.setImageUrl(getItem(position).getTranspond_data().getAttach().get(i).getAttach_middle());
								WeiBomain.reimageview2.setVisibility(View.VISIBLE);

							}
							if (i == 2) {
								WeiBomain.reimageview3.setImageUrl(getItem(position).getTranspond_data().getAttach().get(i).getAttach_middle());

								WeiBomain.reimageview3.setVisibility(View.VISIBLE);
							}
							if (i == 3) {
								WeiBomain.reimageview4.setImageUrl(getItem(position).getTranspond_data().getAttach().get(i).getAttach_middle());

								WeiBomain.reimageview4.setVisibility(View.VISIBLE);
							}
							if (i == 4) {
								WeiBomain.reimageview5.setImageUrl(getItem(position).getTranspond_data().getAttach().get(i).getAttach_middle());

								WeiBomain.reimageview5.setVisibility(View.VISIBLE);
							}
							if (i == 5) {
								WeiBomain.reimageview6.setImageUrl(getItem(position).getTranspond_data().getAttach().get(i).getAttach_middle());
								WeiBomain.reimageview6.setVisibility(View.VISIBLE);

							}
							if (i == 6) {
								WeiBomain.reimageview7.setImageUrl(getItem(position).getTranspond_data().getAttach().get(i).getAttach_middle());
								WeiBomain.reimageview7.setVisibility(View.VISIBLE);

							}
							if (i == 7) {
								WeiBomain.reimageview8.setImageUrl(getItem(position).getTranspond_data().getAttach().get(i).getAttach_middle());
								WeiBomain.reimageview8.setVisibility(View.VISIBLE);

							}
							if (i == 8) {
								WeiBomain.reimageview9.setImageUrl(getItem(position).getTranspond_data().getAttach().get(i).getAttach_middle());
								WeiBomain.reimageview9.setVisibility(View.VISIBLE);

							}
						}
					}
				} catch (Exception e) {
					Log.d(TAG, e.toString());
				}
			} else {
				WeiBomain.report.setVisibility(View.GONE);
			}
			break;
		}
		addListener(convertView, position, type);
		return convertView;
	}

	public void addListener(final View convertView, final int position, final int type) {
		switch (type) {
		case type1:
			((TextView) convertView.findViewById(R.id.textView2)).setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					Intent i = new Intent(mContext, UserPanl.class);
					mContext.startActivity(i);
				}
			});
			((Button) convertView.findViewById(R.id.button2)).setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					((Mykey) mContext.getApplicationContext()).setOtheruid(((Mykey) mContext.getApplicationContext()).getUid());
					Intent intent = new Intent(mContext, FollowList.class);
					intent.putExtra("a", "2");
					mContext.startActivity(intent);
				}
			});
			((Button) convertView.findViewById(R.id.button3)).setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					((Mykey) mContext.getApplicationContext()).setOtheruid(((Mykey) mContext.getApplicationContext()).getUid());
					Intent intent = new Intent(mContext, FollowList.class);
					intent.putExtra("a", "1");
					mContext.startActivity(intent);
				}
			});
			((Button) convertView.findViewById(R.id.button4)).setOnClickListener(new View.OnClickListener() {

				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					Intent intent = new Intent(mContext, Collection.class);
					mContext.startActivity(intent);
				}
			});
			head = (MyHeadImgeView) convertView.findViewById(R.id.imageView1);
			((Mykey) mContext.getApplicationContext()).setHead_ic(head);
			((MyHeadImgeView) convertView.findViewById(R.id.imageView1)).setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View v) {
					Intent i = new Intent(mContext, SelectPicPopup.class);
					((Activity) mContext).startActivityForResult(i, 1);
				}
			});
			break;
		case type2:
			((ImageView) convertView.findViewById(R.id.imageView2)).setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					Mykey app = (Mykey) mContext.getApplicationContext();
					app.setFeed_id(getItem(position).getFeed_id());
					app.setMymap(getItem(position));
					Intent intent = new Intent(mContext, SelectPopupWindow.class);
					mContext.startActivity(intent);
				}
			});
			((SmartImageView) convertView.findViewById(R.id.item_image_1)).setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					ImageArrayList.ATTACH = getItem(position).getAttach();
					ImageArrayList.PICCURRENT = 0;
					Intent intent = new Intent(mContext, ShowWeiBoPic.class);
					mContext.startActivity(intent);
				}
			});
			((SmartImageView) convertView.findViewById(R.id.item_image_2)).setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					ImageArrayList.ATTACH = getItem(position).getAttach();
					ImageArrayList.PICCURRENT = 1;
					Intent intent = new Intent(mContext, ShowWeiBoPic.class);
					mContext.startActivity(intent);
				}
			});
			((SmartImageView) convertView.findViewById(R.id.item_image_3)).setOnClickListener(new View.OnClickListener() {

				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					ImageArrayList.ATTACH = getItem(position).getAttach();
					ImageArrayList.PICCURRENT = 2;
					Intent intent = new Intent(mContext, ShowWeiBoPic.class);
					mContext.startActivity(intent);
				}
			});
			((SmartImageView) convertView.findViewById(R.id.item_image_4)).setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub

					ImageArrayList.ATTACH = getItem(position).getAttach();
					ImageArrayList.PICCURRENT = 3;
					Intent intent = new Intent(mContext, ShowWeiBoPic.class);
					mContext.startActivity(intent);
				}
			});
			((SmartImageView) convertView.findViewById(R.id.item_image_5)).setOnClickListener(new View.OnClickListener() {

				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					ImageArrayList.ATTACH = getItem(position).getAttach();

					ImageArrayList.PICCURRENT = 4;
					Intent intent = new Intent(mContext, ShowWeiBoPic.class);
					mContext.startActivity(intent);
				}
			});
			((SmartImageView) convertView.findViewById(R.id.item_image_6)).setOnClickListener(new View.OnClickListener() {

				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					ImageArrayList.ATTACH = getItem(position).getAttach();

					ImageArrayList.PICCURRENT = 5;
					Intent intent = new Intent(mContext, ShowWeiBoPic.class);
					mContext.startActivity(intent);
				}
			});
			((SmartImageView) convertView.findViewById(R.id.item_image_7)).setOnClickListener(new View.OnClickListener() {

				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub

					ImageArrayList.ATTACH = getItem(position).getAttach();
					ImageArrayList.PICCURRENT = 6;
					Intent intent = new Intent(mContext, ShowWeiBoPic.class);
					mContext.startActivity(intent);
				}
			});
			((SmartImageView) convertView.findViewById(R.id.item_image_8)).setOnClickListener(new View.OnClickListener() {

				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					ImageArrayList.ATTACH = getItem(position).getAttach();

					ImageArrayList.PICCURRENT = 7;
					Intent intent = new Intent(mContext, ShowWeiBoPic.class);
					mContext.startActivity(intent);
				}
			});
			((SmartImageView) convertView.findViewById(R.id.item_image_9)).setOnClickListener(new View.OnClickListener() {

				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub

					ImageArrayList.ATTACH = getItem(position).getAttach();
					ImageArrayList.PICCURRENT = 8;
					Intent intent = new Intent(mContext, ShowWeiBoPic.class);
					mContext.startActivity(intent);
				}
			});
			((SmartImageView) convertView.findViewById(R.id.reitem_image_1)).setOnClickListener(new View.OnClickListener() {

				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub

					ImageArrayList.ATTACH = getItem(position).getTranspond_data().getAttach();
					ImageArrayList.PICCURRENT = 0;
					Intent intent = new Intent(mContext, ShowWeiBoPic.class);
					mContext.startActivity(intent);

				}
			});
			((SmartImageView) convertView.findViewById(R.id.reitem_image_2)).setOnClickListener(new View.OnClickListener() {

				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					ImageArrayList.ATTACH = getItem(position).getTranspond_data().getAttach();
					ImageArrayList.PICCURRENT = 1;
					Intent intent = new Intent(mContext, ShowWeiBoPic.class);
					mContext.startActivity(intent);
				}
			});
			((SmartImageView) convertView.findViewById(R.id.reitem_image_3)).setOnClickListener(new View.OnClickListener() {

				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub

					ImageArrayList.ATTACH = getItem(position).getTranspond_data().getAttach();
					ImageArrayList.PICCURRENT = 2;
					Intent intent = new Intent(mContext, ShowWeiBoPic.class);
					mContext.startActivity(intent);
				}
			});
			((SmartImageView) convertView.findViewById(R.id.reitem_image_4)).setOnClickListener(new View.OnClickListener() {

				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub

					ImageArrayList.ATTACH = getItem(position).getTranspond_data().getAttach();
					ImageArrayList.PICCURRENT = 3;
					Intent intent = new Intent(mContext, ShowWeiBoPic.class);
					mContext.startActivity(intent);
				}
			});
			((SmartImageView) convertView.findViewById(R.id.reitem_image_5)).setOnClickListener(new View.OnClickListener() {

				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub

					ImageArrayList.ATTACH = getItem(position).getTranspond_data().getAttach();
					ImageArrayList.PICCURRENT = 4;
					Intent intent = new Intent(mContext, ShowWeiBoPic.class);
					mContext.startActivity(intent);
				}
			});
			((SmartImageView) convertView.findViewById(R.id.reitem_image_6)).setOnClickListener(new View.OnClickListener() {

				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					ImageArrayList.ATTACH = getItem(position).getTranspond_data().getAttach();

					ImageArrayList.PICCURRENT = 5;
					Intent intent = new Intent(mContext, ShowWeiBoPic.class);
					mContext.startActivity(intent);
				}
			});
			((SmartImageView) convertView.findViewById(R.id.reitem_image_7)).setOnClickListener(new View.OnClickListener() {

				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					ImageArrayList.ATTACH = getItem(position).getTranspond_data().getAttach();
					ImageArrayList.PICCURRENT = 6;
					Intent intent = new Intent(mContext, ShowWeiBoPic.class);
					mContext.startActivity(intent);
				}
			});
			((SmartImageView) convertView.findViewById(R.id.reitem_image_8)).setOnClickListener(new View.OnClickListener() {

				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					ImageArrayList.ATTACH = getItem(position).getTranspond_data().getAttach();
					ImageArrayList.PICCURRENT = 7;
					Intent intent = new Intent(mContext, ShowWeiBoPic.class);
					mContext.startActivity(intent);
				}
			});
			((SmartImageView) convertView.findViewById(R.id.reitem_image_9)).setOnClickListener(new View.OnClickListener() {

				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					ImageArrayList.ATTACH = getItem(position).getTranspond_data().getAttach();
					ImageArrayList.PICCURRENT = 8;
					Intent intent = new Intent(mContext, ShowWeiBoPic.class);
					mContext.startActivity(intent);
				}
			});
			((ImageView) convertView.findViewById(R.id.imageView2)).setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					Mykey app = (Mykey) mContext.getApplicationContext();
					app.setFeed_id(getItem(position).getFeed_id());
					app.setMymap(getItem(position));
					app.setDel_weibo_id(String.valueOf(position));
					Intent intent = new Intent(mContext, SelectPopupWindow.class);
					mContext.startActivity(intent);
				}
			});
			break;
		}
	}

	private class ViewHolder {
		TextView textview1, textview2, textview3, textview4, textview5, textview6, reporttextview1, reporttextview2;
		SmartImageView imageview0, imageview1, imageview2, imageview3, imageview4, imageview5, imageview6, imageview7, imageview8, imageview9, reimageview1, reimageview2, reimageview3, reimageview4, reimageview5, reimageview6, reimageview7, reimageview8, reimageview9;
		TextView imagepop;
		ImageView more;
		RelativeLayout report;
	}

	private class ViewUserPanl {
		MyHeadImgeView head_ic;
		TextView uName_Text, intro_Text;
		Button weibo_btn, followingg_btn, follows_btn, collect_btn;
	}
}
