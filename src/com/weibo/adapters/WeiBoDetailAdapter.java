/**
 * weibotest
 * 
 * 
 * TODO
 */

package com.weibo.adapters;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;

import com.loopj.android.image.SmartImageView;
import com.weibo.activity.OtherUserPanl;
import com.weibo.R;
import com.weibo.activity.ReplayWeiBoComment;
import com.weibo.activity.ShowWeiBoPic;
import com.weibo.activity.WeiBoDetail;
import com.weibo.application.Mykey;
import com.weibo.card.CommentInfor;
import com.weibo.card.Transpond_Data;
import com.weibo.card.WeiBoCard;
import com.weibo.utils.FaceConversionUtil;
import com.weibo.utils.ImageArrayList;
import com.weibo.view.widget.RoundCornerImageView;

import android.content.Context;
import android.content.Intent;
import android.text.SpannableString;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

/**
 * @作者:陈华清
 * 
 * @版本:1.0
 * @生成时期:2014年7月19日 上午7:47:33
 * @com.api
 */
public class WeiBoDetailAdapter extends BaseAdapter {
	Transpond_Data transpond;
	LayoutInflater inflater;
	List<CommentInfor> commen_list;
	WeiBoCard weibo;
	private Context mContext;
	final int type1 = 0;
	final int type2 = 1;
	final int type3 = 2;
	private static final String TAG = "WeiBoActivity";

	public WeiBoDetailAdapter(Context ctx, List<CommentInfor> commen_list, WeiBoCard weibo) {
		mContext = ctx;
		this.commen_list = commen_list;
		this.weibo = weibo;
		inflater = LayoutInflater.from(mContext);
	}

	@Override
	public int getCount() {
		if (commen_list != null && commen_list.size() > 0) {
			return commen_list.size() + 1;
		} else {
			return commen_list.size() + 2;
		}
	}

	@Override
	public long getItemId(int position) {
		return 0;
	}

	@Override
	public CommentInfor getItem(int position) {
		// TODO Auto-generated method stub
		if (commen_list != null && commen_list.size() > 0) {
			return commen_list.get(position - 1);
		} else {
			return commen_list.get(position - 2);
		}
	}

	@Override
	public int getItemViewType(int position) {
		int p = position;
		if (p == 0) {
			return type1;
		} else if (p == 1 && commen_list.size() == 0) {
			return type2;
		} else {
			return type3;
		}
	}

	@Override
	public int getViewTypeCount() {
		return 3;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		ViewHolderWeiBoDetail viewHolder = null;
		ViewHolderWeiBoComment viewHoldercomment = null;
		int type = getItemViewType(position);
		if (convertView == null) {
			switch (type) {
			case type1:
				viewHolder = new ViewHolderWeiBoDetail();
				convertView = inflater.inflate(R.layout.weibo_detail, null);
				viewHolder.head_pic = (RoundCornerImageView) convertView.findViewById(R.id.imageView1);
				viewHolder.userName_Text = (TextView) convertView.findViewById(R.id.textView1);
				viewHolder.content_Text = (TextView) convertView.findViewById(R.id.textView2);
				viewHolder.From_device = (TextView) convertView.findViewById(R.id.textView5);
				viewHolder.Date_Text = (TextView) convertView.findViewById(R.id.textView3);
				viewHolder.item_pic1 = (SmartImageView) convertView.findViewById(R.id.item_image_1);
				viewHolder.item_pic2 = (SmartImageView) convertView.findViewById(R.id.item_image_2);
				viewHolder.item_pic3 = (SmartImageView) convertView.findViewById(R.id.item_image_3);
				viewHolder.item_pic4 = (SmartImageView) convertView.findViewById(R.id.item_image_4);
				viewHolder.item_pic5 = (SmartImageView) convertView.findViewById(R.id.item_image_5);
				viewHolder.item_pic6 = (SmartImageView) convertView.findViewById(R.id.item_image_6);
				viewHolder.item_pic7 = (SmartImageView) convertView.findViewById(R.id.item_image_7);
				viewHolder.item_pic8 = (SmartImageView) convertView.findViewById(R.id.item_image_8);
				viewHolder.item_pic9 = (SmartImageView) convertView.findViewById(R.id.item_image_9);
				viewHolder.reitem_pic1 = (SmartImageView) convertView.findViewById(R.id.reitem_image_1);
				viewHolder.reitem_pic2 = (SmartImageView) convertView.findViewById(R.id.reitem_image_2);
				viewHolder.reitem_pic3 = (SmartImageView) convertView.findViewById(R.id.reitem_image_3);
				viewHolder.reitem_pic4 = (SmartImageView) convertView.findViewById(R.id.reitem_image_4);
				viewHolder.reitem_pic5 = (SmartImageView) convertView.findViewById(R.id.reitem_image_5);
				viewHolder.reitem_pic6 = (SmartImageView) convertView.findViewById(R.id.reitem_image_6);
				viewHolder.reitem_pic7 = (SmartImageView) convertView.findViewById(R.id.reitem_image_7);
				viewHolder.reitem_pic8 = (SmartImageView) convertView.findViewById(R.id.reitem_image_8);
				viewHolder.reitem_pic9 = (SmartImageView) convertView.findViewById(R.id.reitem_image_9);
				viewHolder.reuserName_Text = (TextView) convertView.findViewById(R.id.reporttextView1);
				viewHolder.recontent_Text = (TextView) convertView.findViewById(R.id.reporttextView2);
				viewHolder.pic_Layout_1 = (LinearLayout) convertView.findViewById(R.id.linearLayout1);
				viewHolder.pic_Layout_2 = (LinearLayout) convertView.findViewById(R.id.linearLayout2);
				viewHolder.pic_Layout_3 = (LinearLayout) convertView.findViewById(R.id.linearLayout3);
				viewHolder.repic_Layout_1 = (LinearLayout) convertView.findViewById(R.id.reportlinearLayout1);
				viewHolder.repic_Layout_2 = (LinearLayout) convertView.findViewById(R.id.reportlinearLayout2);
				viewHolder.repic_Layout_3 = (LinearLayout) convertView.findViewById(R.id.reportlinearLayout3);
				viewHolder.report = (RelativeLayout) convertView.findViewById(R.id.report);
				convertView.setTag(viewHolder);
				break;
			case type2:
				convertView = inflater.inflate(R.layout.nothing, null);
				break;
			case type3:
				viewHoldercomment = new ViewHolderWeiBoComment();
				convertView = inflater.inflate(R.layout.comment_weibo, null);
				viewHoldercomment.c_head_pic = (SmartImageView) convertView.findViewById(R.id.head);
				viewHoldercomment.c_uName_Text = (TextView) convertView.findViewById(R.id.textView1);
				viewHoldercomment.c_Date_Text = (TextView) convertView.findViewById(R.id.textView2);
				viewHoldercomment.c_content_Text = (TextView) convertView.findViewById(R.id.textView3);
				viewHoldercomment.arrow = (ImageView) convertView.findViewById(R.id.arrow);
				break;
			}
		} else {
			switch (type) {
			case type1:
				viewHolder = (ViewHolderWeiBoDetail) convertView.getTag();
				break;
			case type2:
				convertView = inflater.inflate(R.layout.nothing, null);
				break;
			case type3:
				viewHoldercomment = new ViewHolderWeiBoComment();
				convertView = inflater.inflate(R.layout.comment_weibo, null);
				viewHoldercomment.c_head_pic = (SmartImageView) convertView.findViewById(R.id.head);
				viewHoldercomment.c_uName_Text = (TextView) convertView.findViewById(R.id.textView1);
				viewHoldercomment.c_Date_Text = (TextView) convertView.findViewById(R.id.textView2);
				viewHoldercomment.c_content_Text = (TextView) convertView.findViewById(R.id.textView3);
				viewHoldercomment.arrow = (ImageView) convertView.findViewById(R.id.arrow);
				break;
			}
		}
		switch (type) {
		case type1:
			if (weibo.getAvatar_middle() != null) {
				viewHolder.head_pic.setImageUrl(weibo.getAvatar_middle());
			}
			if (weibo.getUname() != null)
				viewHolder.userName_Text.setText(weibo.getUname());
			{
			}
			if (weibo.getContent() != null) {
				SpannableString spannableString = FaceConversionUtil.getInstace().getExpressionString(mContext, weibo.getContent());
				viewHolder.content_Text.setText(spannableString);
				FaceConversionUtil.extractMention2Link(viewHolder.content_Text);
			}
			if (weibo.getCtime() != null) {
				viewHolder.Date_Text.setText(FaceConversionUtil.getInterval(FaceConversionUtil.TimeStamp2Date(weibo.getCtime())));
			} else {
				viewHolder.Date_Text.setVisibility(View.GONE);
			}
			if (weibo.getFrom() != null) {
				String key = weibo.getFrom();
				if (key.equals("0")) {
					viewHolder.From_device.setText("来自网页");
				} else if (key.equals("1")) {
					viewHolder.From_device.setText("来自手机网页");
				} else if (key.equals("2")) {
					viewHolder.From_device.setText("来自iphone");
				} else if (key.equals("3")) {
					viewHolder.From_device.setText("来自Android");
				} else if (key.equals("4")) {
					viewHolder.From_device.setText("来自ipad");
				} else {
					viewHolder.From_device.setText("来自火星");
				}
			}
			if (weibo.getAttach() != null) {
				try {
					for (int i = 0; i < weibo.getAttach().size(); i++) {
						if (i == 0) {
							viewHolder.item_pic1.setImageUrl(weibo.getAttach().get(i).getAttach_middle());
							viewHolder.item_pic1.setVisibility(View.VISIBLE);
						}
						if (i == 1) {

							viewHolder.item_pic2.setImageUrl(weibo.getAttach().get(i).getAttach_middle());
							viewHolder.item_pic2.setVisibility(View.VISIBLE);
						}
						if (i == 2) {

							viewHolder.item_pic3.setImageUrl(weibo.getAttach().get(i).getAttach_middle());
							viewHolder.item_pic3.setVisibility(View.VISIBLE);
						}
						if (i == 3) {

							viewHolder.item_pic4.setImageUrl(weibo.getAttach().get(i).getAttach_middle());
							viewHolder.item_pic4.setVisibility(View.VISIBLE);
						}
						if (i == 4) {

							viewHolder.item_pic5.setImageUrl(weibo.getAttach().get(i).getAttach_middle());
							viewHolder.item_pic5.setVisibility(View.VISIBLE);
						}
						if (i == 5) {

							viewHolder.item_pic6.setImageUrl(weibo.getAttach().get(i).getAttach_middle());
							viewHolder.item_pic6.setVisibility(View.VISIBLE);
						}
						if (i == 6) {

							viewHolder.item_pic7.setImageUrl(weibo.getAttach().get(i).getAttach_middle());
							viewHolder.item_pic7.setVisibility(View.VISIBLE);
						}
						if (i == 7) {

							viewHolder.item_pic8.setImageUrl(weibo.getAttach().get(i).getAttach_middle());
							viewHolder.item_pic8.setVisibility(View.VISIBLE);
						}
						if (i == 8) {

							viewHolder.item_pic9.setImageUrl(weibo.getAttach().get(i).getAttach_middle());
							viewHolder.item_pic9.setVisibility(View.VISIBLE);
						}
					}
				} catch (Exception e) {
					Log.d(TAG, e.toString());
				}
			}
			if (weibo.getTranspond_data() != null) {
				try {
					transpond = weibo.getTranspond_data();
					if (transpond.getUname() != null) {
						viewHolder.reuserName_Text.setText(transpond.getUname());
						viewHolder.reuserName_Text.setVisibility(View.VISIBLE);
					}
					if (transpond.getContent() != null) {
						SpannableString spannableString = FaceConversionUtil.getInstace().getExpressionString(mContext, transpond.getContent());
						viewHolder.recontent_Text.setText(spannableString);
						FaceConversionUtil.extractMention2Link(viewHolder.content_Text);
						viewHolder.recontent_Text.setVisibility(View.VISIBLE);
					}
					if (transpond.getAttach() != null) {
						Log.d(TAG, "发现了" + transpond.getAttach().size() + "张图片");
						for (int i = 0; i < transpond.getAttach().size(); i++) {
							if (i == 0) {
								viewHolder.reitem_pic1.setImageUrl(transpond.getAttach().get(i).getAttach_middle());
								viewHolder.reitem_pic1.setVisibility(View.VISIBLE);

							}
							if (i == 1) {
								viewHolder.reitem_pic2.setImageUrl(transpond.getAttach().get(i).getAttach_middle());
								viewHolder.reitem_pic2.setVisibility(View.VISIBLE);
							}
							if (i == 2) {
								viewHolder.reitem_pic3.setImageUrl(transpond.getAttach().get(i).getAttach_middle());
								viewHolder.reitem_pic3.setVisibility(View.VISIBLE);
							}
							if (i == 3) {
								viewHolder.reitem_pic4.setImageUrl(transpond.getAttach().get(i).getAttach_middle());
								viewHolder.reitem_pic4.setVisibility(View.VISIBLE);
							}
							if (i == 4) {
								viewHolder.reitem_pic5.setImageUrl(transpond.getAttach().get(i).getAttach_middle());
								viewHolder.reitem_pic5.setVisibility(View.VISIBLE);
							}
							if (i == 5) {
								viewHolder.reitem_pic6.setImageUrl(transpond.getAttach().get(i).getAttach_middle());
								viewHolder.reitem_pic6.setVisibility(View.VISIBLE);
							}
							if (i == 6) {
								viewHolder.reitem_pic7.setImageUrl(transpond.getAttach().get(i).getAttach_middle());
								viewHolder.reitem_pic7.setVisibility(View.VISIBLE);
							}
							if (i == 7) {
								viewHolder.reitem_pic8.setImageUrl(transpond.getAttach().get(i).getAttach_middle());
								viewHolder.reitem_pic8.setVisibility(View.VISIBLE);
							}
							if (i == 8) {
								viewHolder.reitem_pic9.setImageUrl(transpond.getAttach().get(i).getAttach_middle());
								viewHolder.reitem_pic9.setVisibility(View.VISIBLE);
							}
						}
					}
				} catch (Exception e) {
					Log.d(TAG, e.toString());
				}
			} else {
				viewHolder.report.setVisibility(View.GONE);
			}
			break;
		case type3:
			try {
				viewHoldercomment.c_uName_Text.setText(getItem(position).getUser_info().getUname());
				viewHoldercomment.c_head_pic.setImageUrl(getItem(position).getUser_info().getAvatar_middle());
				viewHoldercomment.c_Date_Text.setText(FaceConversionUtil.getInterval(FaceConversionUtil.TimeStamp2Date(getItem(position).getCtime())));
				SpannableString spannableString = FaceConversionUtil.getInstace().getExpressionString(mContext, getItem(position).getContent());
				viewHoldercomment.c_content_Text.setText(spannableString);
				FaceConversionUtil.extractMention2Link(viewHoldercomment.c_content_Text);
				if (position == 1) {
					if (viewHoldercomment.arrow.getVisibility() == View.GONE) {
						viewHoldercomment.arrow.setVisibility(View.VISIBLE);
					} else {
						if (viewHoldercomment.arrow.getVisibility() == View.VISIBLE) {
							viewHoldercomment.arrow.setVisibility(View.GONE);
						}
					}
				}
			} catch (NullPointerException e) {
				e.printStackTrace();
			}
			break;
		}
		addListener(convertView, type, position);
		return convertView;
	}
	private void addListener(View convertView, int type, int position) {
		final int arg = position;
		try {
			switch (type) {
			case type1:
				((RoundCornerImageView) convertView.findViewById(R.id.imageView1)).setOnClickListener(new View.OnClickListener() {
					@Override
					public void onClick(View v) {
						// TODO Auto-generated method stub
						Mykey app = (Mykey) mContext.getApplicationContext();
						app.setOtheruid(weibo.getUid());
						Log.d(TAG, weibo.getUid());
						Intent intent = new Intent(mContext, OtherUserPanl.class);
						mContext.startActivity(intent);
					}
				});
				((RelativeLayout) convertView.findViewById(R.id.report)).setOnClickListener(new View.OnClickListener() {
					@Override
					public void onClick(View v) {
						// TODO Auto-generated method stub
						//像微博详情界面传送微博转发内容
						((Mykey) mContext.getApplicationContext()).setTreanspond(weibo.getTranspond_data());
						Intent in = new Intent(mContext, WeiBoDetail.class);
						mContext.startActivity(in);
					}
				});
				((SmartImageView) convertView.findViewById(R.id.item_image_1)).setOnClickListener(new View.OnClickListener() {
					@Override
					public void onClick(View v) {
						// TODO Auto-generated method stub
						ImageArrayList.ATTACH = weibo.getAttach();
						ImageArrayList.PICCURRENT = 0;
						Intent intent = new Intent(mContext, ShowWeiBoPic.class);
						mContext.startActivity(intent);
					}
				});
				((SmartImageView) convertView.findViewById(R.id.item_image_2)).setOnClickListener(new View.OnClickListener() {
					@Override
					public void onClick(View v) {
						// TODO Auto-generated method stub
						ImageArrayList.ATTACH = weibo.getAttach();
						ImageArrayList.PICCURRENT = 1;
						Intent intent = new Intent(mContext, ShowWeiBoPic.class);
						mContext.startActivity(intent);
					}
				});
				((SmartImageView) convertView.findViewById(R.id.item_image_3)).setOnClickListener(new View.OnClickListener() {

					@Override
					public void onClick(View v) {
						// TODO Auto-generated method stub
						ImageArrayList.ATTACH = weibo.getAttach();
						ImageArrayList.PICCURRENT = 2;
						Intent intent = new Intent(mContext, ShowWeiBoPic.class);
						mContext.startActivity(intent);
					}
				});
				((SmartImageView) convertView.findViewById(R.id.item_image_4)).setOnClickListener(new View.OnClickListener() {
					@Override
					public void onClick(View v) {
						// TODO Auto-generated method stub
						ImageArrayList.ATTACH = weibo.getAttach();
						ImageArrayList.PICCURRENT = 3;
						Intent intent = new Intent(mContext, ShowWeiBoPic.class);
						mContext.startActivity(intent);
					}
				});
				((SmartImageView) convertView.findViewById(R.id.item_image_5)).setOnClickListener(new View.OnClickListener() {

					@Override
					public void onClick(View v) {
						// TODO Auto-generated method stub
						ImageArrayList.ATTACH = weibo.getAttach();
						ImageArrayList.PICCURRENT = 4;
						Intent intent = new Intent(mContext, ShowWeiBoPic.class);
						mContext.startActivity(intent);
					}
				});
				((SmartImageView) convertView.findViewById(R.id.item_image_6)).setOnClickListener(new View.OnClickListener() {

					@Override
					public void onClick(View v) {
						// TODO Auto-generated method stub
						ImageArrayList.ATTACH = weibo.getAttach();
						ImageArrayList.PICCURRENT = 5;
						Intent intent = new Intent(mContext, ShowWeiBoPic.class);
						mContext.startActivity(intent);
					}
				});
				((SmartImageView) convertView.findViewById(R.id.item_image_7)).setOnClickListener(new View.OnClickListener() {

					@Override
					public void onClick(View v) {
						// TODO Auto-generated method stub
						ImageArrayList.ATTACH = weibo.getAttach();
						ImageArrayList.PICCURRENT = 6;
						Intent intent = new Intent(mContext, ShowWeiBoPic.class);
						mContext.startActivity(intent);
					}
				});
				((SmartImageView) convertView.findViewById(R.id.item_image_8)).setOnClickListener(new View.OnClickListener() {

					@Override
					public void onClick(View v) {
						// TODO Auto-generated method stub
						ImageArrayList.ATTACH = weibo.getAttach();
						ImageArrayList.PICCURRENT = 7;
						Intent intent = new Intent(mContext, ShowWeiBoPic.class);
						mContext.startActivity(intent);
					}
				});
				((SmartImageView) convertView.findViewById(R.id.item_image_9)).setOnClickListener(new View.OnClickListener() {

					@Override
					public void onClick(View v) {
						// TODO Auto-generated method stub
						ImageArrayList.ATTACH = weibo.getAttach();
						ImageArrayList.PICCURRENT = 8;
						Intent intent = new Intent(mContext, ShowWeiBoPic.class);
						mContext.startActivity(intent);
					}
				});
				((SmartImageView) convertView.findViewById(R.id.reitem_image_1)).setOnClickListener(new View.OnClickListener() {

					@Override
					public void onClick(View v) {
						// TODO Auto-generated method stub
						ImageArrayList.ATTACH = weibo.getTranspond_data().getAttach();
						ImageArrayList.PICCURRENT = 0;
						Intent intent = new Intent(mContext, ShowWeiBoPic.class);
						mContext.startActivity(intent);

					}
				});
				((SmartImageView) convertView.findViewById(R.id.reitem_image_2)).setOnClickListener(new View.OnClickListener() {

					@Override
					public void onClick(View v) {
						// TODO Auto-generated method stub
						
						ImageArrayList.ATTACH = weibo.getTranspond_data().getAttach();
						ImageArrayList.PICCURRENT = 1;
						Intent intent = new Intent(mContext, ShowWeiBoPic.class);
						mContext.startActivity(intent);
					}
				});
				((SmartImageView) convertView.findViewById(R.id.reitem_image_3)).setOnClickListener(new View.OnClickListener() {

					@Override
					public void onClick(View v) {
						// TODO Auto-generated method stub
						ImageArrayList.ATTACH = weibo.getTranspond_data().getAttach();
						ImageArrayList.PICCURRENT = 2;
						Intent intent = new Intent(mContext, ShowWeiBoPic.class);
						mContext.startActivity(intent);
					}
				});
				((SmartImageView) convertView.findViewById(R.id.reitem_image_4)).setOnClickListener(new View.OnClickListener() {

					@Override
					public void onClick(View v) {
						// TODO Auto-generated method stub
						ImageArrayList.ATTACH = weibo.getTranspond_data().getAttach();
						ImageArrayList.PICCURRENT = 3;
						Intent intent = new Intent(mContext, ShowWeiBoPic.class);
						mContext.startActivity(intent);
					}
				});
				((SmartImageView) convertView.findViewById(R.id.reitem_image_5)).setOnClickListener(new View.OnClickListener() {

					@Override
					public void onClick(View v) {
						// TODO Auto-generated method stub
						ImageArrayList.ATTACH = weibo.getTranspond_data().getAttach();
						ImageArrayList.PICCURRENT = 4;
						Intent intent = new Intent(mContext, ShowWeiBoPic.class);
						mContext.startActivity(intent);
					}
				});
				((SmartImageView) convertView.findViewById(R.id.reitem_image_6)).setOnClickListener(new View.OnClickListener() {

					@Override
					public void onClick(View v) {
						// TODO Auto-generated method stub
						ImageArrayList.ATTACH = weibo.getTranspond_data().getAttach();
						ImageArrayList.PICCURRENT = 5;
						Intent intent = new Intent(mContext, ShowWeiBoPic.class);
						mContext.startActivity(intent);
					}
				});
				((SmartImageView) convertView.findViewById(R.id.reitem_image_7)).setOnClickListener(new View.OnClickListener() {

					@Override
					public void onClick(View v) {
						// TODO Auto-generated method stub
						ImageArrayList.ATTACH = weibo.getTranspond_data().getAttach();
						ImageArrayList.PICCURRENT = 6;
						Intent intent = new Intent(mContext, ShowWeiBoPic.class);
						mContext.startActivity(intent);
					}
				});
				((SmartImageView) convertView.findViewById(R.id.reitem_image_8)).setOnClickListener(new View.OnClickListener() {

					@Override
					public void onClick(View v) {
						// TODO Auto-generated method stub
						ImageArrayList.ATTACH = weibo.getTranspond_data().getAttach();
						ImageArrayList.PICCURRENT = 7;
						Intent intent = new Intent(mContext, ShowWeiBoPic.class);
						mContext.startActivity(intent);
					}
				});
				((SmartImageView) convertView.findViewById(R.id.reitem_image_9)).setOnClickListener(new View.OnClickListener() {

					@Override
					public void onClick(View v) {
						// TODO Auto-generated method stub
						ImageArrayList.ATTACH = weibo.getTranspond_data().getAttach();
						ImageArrayList.PICCURRENT = 8;
						Intent intent = new Intent(mContext, ShowWeiBoPic.class);
						mContext.startActivity(intent);
					}
				});
				break;
			case type3:
				((SmartImageView) convertView.findViewById(R.id.head)).setOnClickListener(new View.OnClickListener() {
					@Override
					public void onClick(View v) {
						// TODO Auto-generated method stub
						((Mykey) mContext.getApplicationContext()).setOtheruid(getItem(arg).getUid());
						Intent intent = new Intent(mContext, OtherUserPanl.class);
						mContext.startActivity(intent);
						Log.d(TAG,getItem(arg).getUid());
					}
				});
				convertView.setOnClickListener(new View.OnClickListener() {
					@Override
					public void onClick(View v) {
						// TODO Auto-generated method stub
						Intent i = new Intent(mContext, ReplayWeiBoComment.class);
						i.putExtra("comment_id", getItem(arg).getComment_id());
						i.putExtra("uname", getItem(arg).getUser_info().getUname());
						i.putExtra("row_id", getItem(arg).getRow_id());
						mContext.startActivity(i);
					}
				});
				break;
			}
		} catch (NullPointerException e) {
			e.printStackTrace();
		}
	}
	class ViewHolderWeiBoDetail {
		RoundCornerImageView head_pic;
		SmartImageView item_pic1, item_pic2, item_pic3, item_pic4, item_pic5, item_pic6, item_pic7, item_pic8, item_pic9, reitem_pic1, reitem_pic2, reitem_pic3, reitem_pic4, reitem_pic5, reitem_pic6, reitem_pic7, reitem_pic8, reitem_pic9;
		TextView userName_Text, content_Text, From_device, Date_Text, reuserName_Text, recontent_Text;
		LinearLayout pic_Layout_1, pic_Layout_2, pic_Layout_3, repic_Layout_1, repic_Layout_2, repic_Layout_3;
		RelativeLayout report;
	}
	class ViewHolderWeiBoComment {
		SmartImageView c_head_pic;
		ImageView arrow;
		TextView c_uName_Text, c_content_Text, c_Date_Text;
	}
}
