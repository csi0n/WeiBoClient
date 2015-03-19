package com.weibo.adapters;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;

import com.loopj.android.image.SmartImageView;
import com.weibo.activity.MainActivity;
import com.weibo.activity.OtherUserPanl;
import com.weibo.activity.WeiBoDetail;
import com.weibo.R;
import com.weibo.activity.ShowWeiBoPic;
import com.weibo.activity.selectpop.SelectPopupWindow;
import com.weibo.application.Mykey;
import com.weibo.card.Transpond_Data;
import com.weibo.card.WeiBoCard;
import com.weibo.utils.ClientUtils;
import com.weibo.utils.DialogUtils;
import com.weibo.utils.DialogUtils.DialogCallBack;
import com.weibo.utils.FaceConversionUtil;
import com.weibo.utils.ImageArrayList;
import com.weibo.view.widget.CheckAlertAnim;
import com.weibo.view.widget.RoundCornerImageView;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.text.SpannableString;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

/**
 * 
 * 微博的Adapter
 * 
 */
public class WeiBoMainAdapter extends BaseAdapter {
	LayoutInflater inflater;
	private Context mContext;
	private static final String TAG = "WeiBoMainAdapter.java";
	String ss = "";
	CheckAlertAnim error;
	List<WeiBoCard> weibolist_card;
	public WeiBoMainAdapter(Context ctx, List<WeiBoCard> weibo_list) {
		this.mContext = ctx;
		this.weibolist_card = weibo_list;
		inflater = LayoutInflater.from(mContext);
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return weibolist_card.size();
	}

	@Override
	public WeiBoCard getItem(int position) {
		// TODO Auto-generated method stub
		return weibolist_card.get(position);
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
		holder = new ViewHolder();
		convertView = inflater.inflate(R.layout.main_weibo_listitem, parent, false);
		holder.more = (ImageView) convertView.findViewById(R.id.imageView2);
		holder.textview1 = (TextView) convertView.findViewById(R.id.textView1);
		holder.textview2 = (TextView) convertView.findViewById(R.id.textView2);
		holder.textview3 = (TextView) convertView.findViewById(R.id.textView3);
		holder.textview4 = (TextView) convertView.findViewById(R.id.textView4);
		holder.textview5 = (TextView) convertView.findViewById(R.id.textView5);
		holder.textview6 = (TextView) convertView.findViewById(R.id.textView6);
		holder.imageview0 = (RoundCornerImageView) convertView.findViewById(R.id.imageView1);
		holder.imagepop = (TextView) convertView.findViewById(R.id.morepop);
		holder.imageview1 = (SmartImageView) convertView.findViewById(R.id.item_image_1);
		holder.imageview2 = (SmartImageView) convertView.findViewById(R.id.item_image_2);
		holder.imageview3 = (SmartImageView) convertView.findViewById(R.id.item_image_3);
		holder.imageview4 = (SmartImageView) convertView.findViewById(R.id.item_image_4);
		holder.imageview5 = (SmartImageView) convertView.findViewById(R.id.item_image_5);
		holder.imageview6 = (SmartImageView) convertView.findViewById(R.id.item_image_6);
		holder.imageview7 = (SmartImageView) convertView.findViewById(R.id.item_image_7);
		holder.imageview8 = (SmartImageView) convertView.findViewById(R.id.item_image_8);
		holder.imageview9 = (SmartImageView) convertView.findViewById(R.id.item_image_9);
		holder.reimageview1 = (SmartImageView) convertView.findViewById(R.id.reitem_image_1);
		holder.reimageview2 = (SmartImageView) convertView.findViewById(R.id.reitem_image_2);
		holder.reimageview3 = (SmartImageView) convertView.findViewById(R.id.reitem_image_3);
		holder.reimageview4 = (SmartImageView) convertView.findViewById(R.id.reitem_image_4);
		holder.reimageview5 = (SmartImageView) convertView.findViewById(R.id.reitem_image_5);
		holder.reimageview6 = (SmartImageView) convertView.findViewById(R.id.reitem_image_6);
		holder.reimageview7 = (SmartImageView) convertView.findViewById(R.id.reitem_image_7);
		holder.reimageview8 = (SmartImageView) convertView.findViewById(R.id.reitem_image_8);
		holder.reimageview9 = (SmartImageView) convertView.findViewById(R.id.reitem_image_9);
		holder.reporttextview1 = (TextView) convertView.findViewById(R.id.reporttextView1);
		holder.reporttextview2 = (TextView) convertView.findViewById(R.id.reporttextView2);
		holder.report = (RelativeLayout) convertView.findViewById(R.id.report);
		if (weibolist_card != null && weibolist_card.size() > 0) {
			if (getItem(position).getUname() != null) {
				holder.textview1.setText(getItem(position).getUname());
			} else {
				holder.textview1.setVisibility(View.GONE);
			}
			if (getItem(position).getContent() != null) {
				SpannableString spannableString = FaceConversionUtil.getInstace().getExpressionString(mContext, getItem(position).getContent());
				holder.textview2.setText(spannableString);
				FaceConversionUtil.extractMention2Link(holder.textview2);
			} else {
				holder.textview2.setVisibility(View.GONE);
			}
			if (getItem(position).getComment_count() != null) {
				holder.textview3.setText("评论:" + getItem(position).getComment_count());
			} else {
				holder.textview3.setVisibility(View.GONE);
			}
			if (getItem(position).getRepost_count() != null) {
				holder.textview4.setText("转发:" + getItem(position).getRepost_count());
			} else {
				holder.textview4.setVisibility(View.GONE);
			}
			if (getItem(position).getCtime() != null) {
				String time = FaceConversionUtil.getInterval(FaceConversionUtil.TimeStamp2Date(getItem(position).getCtime()));
				if (time.indexOf("刚") > 0 || time.indexOf("前") > 0 || time.indexOf("天") > 0) {
					FaceConversionUtil.MygetColor(mContext, holder.textview5, R.color.yellow);
					holder.textview5.setText(time);
				} else {
					holder.textview5.setText(time);
				}
			} else {
				holder.textview4.setVisibility(View.GONE);
			}
			if (getItem(position).getAvatar_middle() != null) {
				holder.imageview0.setImageUrl(getItem(position).getAvatar_middle());
			} else {
				holder.imageview0.setVisibility(View.GONE);
			}
			if (getItem(position).getFrom() != null) {
				if (getItem(position).getFrom().equals("0")) {
					holder.textview6.setText("来自网页");
				} else if (getItem(position).getFrom().equals("1")) {
					holder.textview6.setText("来自手机网页");
				} else if (getItem(position).getFrom().equals("2")) {
					holder.textview6.setText("来自iphone");
				} else if (getItem(position).getFrom().equals("3")) {
					holder.textview6.setText("来自Android");
				} else if (getItem(position).getFrom().equals("4")) {
					holder.textview6.setText("来自Ipad");
				} else {
					holder.textview6.setText("来自火星");
				}
			} else {
				holder.textview6.setVisibility(View.GONE);
			}
			if (getItem(position).getAttach() != null) {
				try {
					for (int i = 0; i < getItem(position).getAttach().size(); i++) {
						if (i == 0) {
							holder.imageview1.setImageUrl(getItem(position).getAttach().get(i).getAttach_small());
							holder.imageview1.setVisibility(View.VISIBLE);
						}
						if (i == 1) {
							holder.imageview2.setImageUrl(getItem(position).getAttach().get(i).getAttach_small());
							holder.imageview2.setVisibility(View.VISIBLE);
						}
						if (i == 2) {
							holder.imageview3.setImageUrl(getItem(position).getAttach().get(i).getAttach_small());
							holder.imageview3.setVisibility(View.VISIBLE);
						}
						if (i == 3) {
							holder.imageview4.setImageUrl(getItem(position).getAttach().get(i).getAttach_small());
							holder.imageview4.setVisibility(View.VISIBLE);
						}
						if (i == 4) {
							holder.imageview5.setImageUrl(getItem(position).getAttach().get(i).getAttach_small());
							holder.imageview5.setVisibility(View.VISIBLE);

						}
						if (i == 5) {
							holder.imageview6.setImageUrl(getItem(position).getAttach().get(i).getAttach_small());
							holder.imageview6.setVisibility(View.VISIBLE);
						}
						if (i == 6) {
							holder.imageview7.setImageUrl(getItem(position).getAttach().get(i).getAttach_small());
							holder.imageview7.setVisibility(View.VISIBLE);
						}
						if (i == 7) {
							holder.imageview8.setImageUrl(getItem(position).getAttach().get(i).getAttach_small());
							holder.imageview8.setVisibility(View.VISIBLE);
						}
						if (i == 8) {
							holder.imageview9.setImageUrl(getItem(position).getAttach().get(i).getAttach_small());
							holder.imageview9.setVisibility(View.VISIBLE);
						}
					}
				} catch (Exception e) {
					Log.d(TAG, e.toString());
				}
			}
			if (getItem(position).getTranspond_data() != null) {
				try {
					Transpond_Data transpon = getItem(position).getTranspond_data();
					holder.reporttextview1.setText("@");
					holder.reporttextview1.append(transpon.getUname() + ":");
					SpannableString spannableString = FaceConversionUtil.getInstace().getExpressionString(mContext, transpon.getContent());
					holder.reporttextview2.setText(spannableString);
					FaceConversionUtil.extractMention2Link(holder.reporttextview2);
					if (transpon.getAttach() != null) {
						for (int i = 0; i < transpon.getAttach().size(); i++) {
							if (i == 0) {
								holder.reimageview1.setImageUrl(transpon.getAttach().get(i).getAttach_big());
								holder.reimageview1.setVisibility(View.VISIBLE);
							}
							if (i == 1) {
								holder.reimageview2.setImageUrl(transpon.getAttach().get(i).getAttach_big());
								holder.reimageview2.setVisibility(View.VISIBLE);
							}
							if (i == 2) {
								holder.reimageview3.setImageUrl(transpon.getAttach().get(i).getAttach_big());
								holder.reimageview3.setVisibility(View.VISIBLE);
							}
							if (i == 3) {
								holder.reimageview4.setImageUrl(transpon.getAttach().get(i).getAttach_big());
								holder.reimageview4.setVisibility(View.VISIBLE);
							}
							if (i == 4) {
								holder.reimageview5.setImageUrl(transpon.getAttach().get(i).getAttach_big());
								holder.reimageview5.setVisibility(View.VISIBLE);
							}
							if (i == 5) {
								holder.reimageview6.setImageUrl(transpon.getAttach().get(i).getAttach_big());
								holder.reimageview6.setVisibility(View.VISIBLE);
							}
							if (i == 6) {
								holder.reimageview7.setImageUrl(transpon.getAttach().get(i).getAttach_big());
								holder.reimageview7.setVisibility(View.VISIBLE);
							}
							if (i == 7) {
								holder.reimageview8.setImageUrl(transpon.getAttach().get(i).getAttach_big());
								holder.reimageview8.setVisibility(View.VISIBLE);
							}
							if (i == 8) {
								holder.reimageview9.setImageUrl(transpon.getAttach().get(i).getAttach_big());
								holder.reimageview9.setVisibility(View.VISIBLE);
							}
						}
					}
				} catch (Exception e) {
					Log.d(TAG, e.toString());
				}
			} else {
				holder.report.setVisibility(View.GONE);
			}
			addListener(convertView, position);
		}
		return convertView;
	}

	ViewHolder holder;
	TextView text;

	public void addListener(View convertView, int arg) {
		final int arg2 = arg;
		text = ((TextView) convertView.findViewById(R.id.morepop));
		text.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				new AsyncTask<Void, Void, Void>() {
					protected Void doInBackground(Void... paras) {
						List<BasicNameValuePair> params = new ArrayList<BasicNameValuePair>();
						params.add(new BasicNameValuePair("oauth_token", ((Mykey) mContext.getApplicationContext()).getOauth_token()));
						params.add(new BasicNameValuePair("oauth_token_secret", ((Mykey) mContext.getApplicationContext()).getOauth_token_secret()));
						params.add(new BasicNameValuePair("feed_id", weibolist_card.get(arg2).getFeed_id()));
						params.add(new BasicNameValuePair("user_id", ((Mykey) mContext.getApplicationContext()).getUid()));
						ss = ClientUtils.post_str(ClientUtils.BASE_URL + mContext.getString(ClientUtils.WeiboStatuses_add_digg), params, (Activity) mContext);
						return null;
					}
					@Override
					protected void onPostExecute(Void result) {
						if (ss.equals("1")) {
							text.setCompoundDrawablesWithIntrinsicBounds(R.drawable.timeline_icon_like, 0, 0, 0);
							FaceConversionUtil.dispToast((Activity) mContext, "点赞成功!");
						} else {
							DialogUtils.dialogBuilder((Activity) mContext, "提示", "您已经赞过是否取消赞？", new DialogCallBack() {
								@Override
								public void callBack() {
									new AsyncTask<Void, Void, Void>() {
										@Override
										protected Void doInBackground(Void... paras) {
											// TODO Auto-generated method stub
											List<BasicNameValuePair> params = new ArrayList<BasicNameValuePair>();
											params.add(new BasicNameValuePair("oauth_token", ((Mykey) mContext.getApplicationContext()).getOauth_token()));
											params.add(new BasicNameValuePair("oauth_token_secret", ((Mykey) mContext.getApplicationContext()).getOauth_token_secret()));
											params.add(new BasicNameValuePair("feed_id", weibolist_card.get(arg2).getFeed_id()));
											params.add(new BasicNameValuePair("user_id", ((Mykey) mContext.getApplicationContext()).getUid()));
											ss = ClientUtils.post_str(ClientUtils.BASE_URL + mContext.getString(ClientUtils.WeiboStatuses_delete_digg), params, (Activity) mContext);
											return null;
										}
										@Override
										protected void onPostExecute(Void result) {
											// TODO Auto-generated method stub
											if (!ss.equals("0")) {
												FaceConversionUtil.dispToast((Activity) mContext, "取消赞成功!");
											} else {
												FaceConversionUtil.dispToast((Activity) mContext, "发生未知错误!");
											}
										}
									}.execute(null, null, null);
								}
							});
						}
						Log.d(TAG, ss);
					}
				}.execute(null, null, null);
			}
		});
		((RoundCornerImageView) convertView.findViewById(R.id.imageView1)).setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				((Mykey) mContext.getApplicationContext()).setOtheruid(getItem(arg2).getUid());
				Intent intent = new Intent(mContext, OtherUserPanl.class);
				mContext.startActivity(intent);
			}
		});
		((SmartImageView) convertView.findViewById(R.id.item_image_1)).setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				ImageArrayList.ATTACH = getItem(arg2).getAttach();
				ImageArrayList.PICCURRENT = 0;
				Intent intent = new Intent(mContext, ShowWeiBoPic.class);
				mContext.startActivity(intent);
			}
		});
		((SmartImageView) convertView.findViewById(R.id.item_image_2)).setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				ImageArrayList.ATTACH = getItem(arg2).getAttach();
				ImageArrayList.PICCURRENT = 1;
				Intent intent = new Intent(mContext, ShowWeiBoPic.class);
				mContext.startActivity(intent);
			}
		});
		((SmartImageView) convertView.findViewById(R.id.item_image_3)).setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				ImageArrayList.ATTACH = getItem(arg2).getAttach();
				ImageArrayList.PICCURRENT = 2;
				Intent intent = new Intent(mContext, ShowWeiBoPic.class);
				mContext.startActivity(intent);
			}
		});
		((SmartImageView) convertView.findViewById(R.id.item_image_4)).setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				ImageArrayList.ATTACH = getItem(arg2).getAttach();
				ImageArrayList.PICCURRENT = 3;
				Intent intent = new Intent(mContext, ShowWeiBoPic.class);
				mContext.startActivity(intent);
			}
		});
		((SmartImageView) convertView.findViewById(R.id.item_image_5)).setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				ImageArrayList.ATTACH = getItem(arg2).getAttach();
				ImageArrayList.PICCURRENT = 4;
				Intent intent = new Intent(mContext, ShowWeiBoPic.class);
				mContext.startActivity(intent);
			}
		});
		((SmartImageView) convertView.findViewById(R.id.item_image_6)).setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				ImageArrayList.ATTACH = getItem(arg2).getAttach();
				ImageArrayList.PICCURRENT = 5;
				Intent intent = new Intent(mContext, ShowWeiBoPic.class);
				mContext.startActivity(intent);
			}
		});
		((SmartImageView) convertView.findViewById(R.id.item_image_7)).setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				ImageArrayList.ATTACH = getItem(arg2).getAttach();
				ImageArrayList.PICCURRENT = 6;
				Intent intent = new Intent(mContext, ShowWeiBoPic.class);
				mContext.startActivity(intent);
			}
		});
		((SmartImageView) convertView.findViewById(R.id.item_image_8)).setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				ImageArrayList.ATTACH = getItem(arg2).getAttach();
				ImageArrayList.PICCURRENT = 7;
				Intent intent = new Intent(mContext, ShowWeiBoPic.class);
				mContext.startActivity(intent);
			}
		});
		((SmartImageView) convertView.findViewById(R.id.item_image_9)).setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				ImageArrayList.ATTACH = getItem(arg2).getAttach();
				ImageArrayList.PICCURRENT = 8;
				Intent intent = new Intent(mContext, ShowWeiBoPic.class);
				mContext.startActivity(intent);
			}
		});
		((SmartImageView) convertView.findViewById(R.id.reitem_image_1)).setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				ImageArrayList.ATTACH = getItem(arg2).getTranspond_data().getAttach();
				ImageArrayList.PICCURRENT = 0;
				Intent intent = new Intent(mContext, ShowWeiBoPic.class);
				mContext.startActivity(intent);

			}
		});
		((SmartImageView) convertView.findViewById(R.id.reitem_image_2)).setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				ImageArrayList.ATTACH = getItem(arg2).getTranspond_data().getAttach();
				ImageArrayList.PICCURRENT = 1;
				Intent intent = new Intent(mContext, ShowWeiBoPic.class);
				mContext.startActivity(intent);
			}
		});
		((SmartImageView) convertView.findViewById(R.id.reitem_image_3)).setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				ImageArrayList.ATTACH = getItem(arg2).getTranspond_data().getAttach();
				ImageArrayList.PICCURRENT = 2;
				Intent intent = new Intent(mContext, ShowWeiBoPic.class);
				mContext.startActivity(intent);
			}
		});
		((SmartImageView) convertView.findViewById(R.id.reitem_image_4)).setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				ImageArrayList.ATTACH = getItem(arg2).getTranspond_data().getAttach();
				ImageArrayList.PICCURRENT = 3;
				Intent intent = new Intent(mContext, ShowWeiBoPic.class);
				mContext.startActivity(intent);
			}
		});
		((SmartImageView) convertView.findViewById(R.id.reitem_image_5)).setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				ImageArrayList.ATTACH = getItem(arg2).getTranspond_data().getAttach();
				ImageArrayList.PICCURRENT = 4;
				Intent intent = new Intent(mContext, ShowWeiBoPic.class);
				mContext.startActivity(intent);
			}
		});
		((SmartImageView) convertView.findViewById(R.id.reitem_image_6)).setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				ImageArrayList.ATTACH = getItem(arg2).getTranspond_data().getAttach();
				ImageArrayList.PICCURRENT = 5;
				Intent intent = new Intent(mContext, ShowWeiBoPic.class);
				mContext.startActivity(intent);
			}
		});
		((SmartImageView) convertView.findViewById(R.id.reitem_image_7)).setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				ImageArrayList.ATTACH = getItem(arg2).getTranspond_data().getAttach();
				ImageArrayList.PICCURRENT = 6;
				Intent intent = new Intent(mContext, ShowWeiBoPic.class);
				mContext.startActivity(intent);
			}
		});
		((SmartImageView) convertView.findViewById(R.id.reitem_image_8)).setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				ImageArrayList.ATTACH = getItem(arg2).getTranspond_data().getAttach();
				ImageArrayList.PICCURRENT = 7;
				Intent intent = new Intent(mContext, ShowWeiBoPic.class);
				mContext.startActivity(intent);
			}
		});
		((SmartImageView) convertView.findViewById(R.id.reitem_image_9)).setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				ImageArrayList.ATTACH = getItem(arg2).getTranspond_data().getAttach();
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
				app.setFeed_id(weibolist_card.get(arg2).getFeed_id());
				app.setMymap(weibolist_card.get(arg2));
				app.setDel_weibo_id(String.valueOf(arg2));
				Intent intent = new Intent(mContext, SelectPopupWindow.class);
				mContext.startActivity(intent);
			}
		});
	}
	private class ViewHolder {
		RoundCornerImageView imageview0;
		TextView textview1, textview2, textview3, textview4, textview5, textview6, reporttextview1, reporttextview2;
		SmartImageView imageview1, imageview2, imageview3, imageview4, imageview5, imageview6, imageview7, imageview8, imageview9, reimageview1, reimageview2, reimageview3, reimageview4, reimageview5, reimageview6, reimageview7, reimageview8, reimageview9;
		TextView imagepop;
		ImageView more;
		RelativeLayout report;
	}

}
