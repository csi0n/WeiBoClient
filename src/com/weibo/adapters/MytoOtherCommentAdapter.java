/**weibotest
 * 
 *
 *TODO
 */
package com.weibo.adapters;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

import com.loopj.android.image.SmartImageView;
import com.weibo.activity.OtherUserPanl;
import com.weibo.R;
import com.weibo.activity.WeiBoDetail;
import com.weibo.application.Mykey;
import com.weibo.card.CommentInfor;
import com.weibo.card.WeiBoCard;
import com.weibo.utils.FaceConversionUtil;
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
import android.widget.TextView;

/**
 * @作者:陈华清
 * 
 * @版本:1.0
 * @生成时期:2014年7月27日 上午8:46:45
 * @com.api
 */
public class MytoOtherCommentAdapter extends BaseAdapter {
	Context mContext;
	List<CommentInfor> comment_list;
	private LayoutInflater inflater;
	private static final String TAG = "MytoOtherComment.java";

	public MytoOtherCommentAdapter(Context context, List<CommentInfor> comment_list) {
		mContext = context;
		this.comment_list = comment_list;
		inflater = LayoutInflater.from(mContext);
	}
	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return comment_list.size();
	}

	@Override
	public CommentInfor getItem(int position) {
		// TODO Auto-generated method stub
		return comment_list.get(position);
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
		convertView = inflater.inflate(R.layout.mycomment, null);
		holder.head_ic = (RoundCornerImageView) convertView.findViewById(R.id.myhead);
		holder.uname_Text = (TextView) convertView.findViewById(R.id.uname);
		holder.From_Text = (TextView) convertView.findViewById(R.id.from);
		holder.Ctime_Text = (TextView) convertView.findViewById(R.id.ctime);
		holder.Content_Text = (TextView) convertView.findViewById(R.id.content);
		holder.reuname_Text = (TextView) convertView.findViewById(R.id.reporttextView1);
		holder.recontent_Text = (TextView) convertView.findViewById(R.id.reporttextView2);
		holder.item_pic1 = (SmartImageView) convertView.findViewById(R.id.item_image_1);
		holder.item_pic2 = (SmartImageView) convertView.findViewById(R.id.item_image_2);
		holder.item_pic3 = (SmartImageView) convertView.findViewById(R.id.item_image_3);
		holder.item_pic4 = (SmartImageView) convertView.findViewById(R.id.item_image_4);
		holder.item_pic5 = (SmartImageView) convertView.findViewById(R.id.item_image_5);
		holder.item_pic6 = (SmartImageView) convertView.findViewById(R.id.item_image_6);
		holder.item_pic7 = (SmartImageView) convertView.findViewById(R.id.item_image_7);
		holder.item_pic8 = (SmartImageView) convertView.findViewById(R.id.item_image_8);
		holder.item_pic9 = (SmartImageView) convertView.findViewById(R.id.item_image_9);
		if (getItem(position).getUser_info().getAvatar_middle() != null) {
			holder.head_ic.setImageUrl(getItem(position).getUser_info().getAvatar_middle());
		} else {
			holder.head_ic.setVisibility(View.GONE);
		}
		if (getItem(position).getSourceInfo().getFrom() != null) {
			if (getItem(position).getSourceInfo().getFrom().equals("0")) {
				holder.From_Text.setText("来自网页");
			} else if (getItem(position).getSourceInfo().getFrom().equals("1")) {
				holder.From_Text.setText("来自手机网页");
			} else if (getItem(position).getSourceInfo().getFrom().equals("2")) {
				holder.From_Text.setText("来自iphone");
			} else if (getItem(position).getSourceInfo().getFrom().equals("3")) {
				holder.From_Text.setText("来自Android");
			} else if (getItem(position).getSourceInfo().getFrom().equals("4")) {
				holder.From_Text.setText("来自Ipad");
			} else {
				holder.From_Text.setText("来自火星");
			}
		} else {
			holder.From_Text.setVisibility(View.GONE);
		}
		if (getItem(position).getUser_info().getUname() != null) {
			holder.uname_Text.setText(getItem(position).getUser_info().getUname());
		} else {
			holder.uname_Text.setVisibility(View.GONE);
		}
		if (getItem(position).getContent()!= null) {
			SpannableString spannableString = FaceConversionUtil.getInstace().getExpressionString(mContext, getItem(position).getContent());
			holder.Content_Text.setText(spannableString);
			FaceConversionUtil.extractMention2Link(holder.Content_Text);
		} else {
			holder.Content_Text.setVisibility(View.GONE);
		}
		if (getItem(position).getCtime()!=null) {
				String time = FaceConversionUtil.getInterval(FaceConversionUtil.TimeStamp2Date(getItem(position).getCtime()));
				if (time.indexOf("刚") > 0 || time.indexOf("前") > 0 || time.indexOf("天") > 0) {
					FaceConversionUtil.MygetColor(mContext, holder.Ctime_Text, R.color.yellow);
					holder.Ctime_Text.setText(time);
				} else {
					holder.Ctime_Text.setText(time);
				}
		}
		if (getItem(position).getSourceInfo().getAttach()!=null) {
			try {
				for (int i = 0; i <getItem(position).getSourceInfo().getAttach().size(); i++) {
					if (i == 0) {
						holder.item_pic1.setImageUrl(getItem(position).getSourceInfo().getAttach().get(i).getAttach_middle());
						holder.item_pic1.setVisibility(View.VISIBLE);
					}
					if (i == 1) {
						holder.item_pic2.setImageUrl(getItem(position).getSourceInfo().getAttach().get(i).getAttach_middle());
						holder.item_pic2.setVisibility(View.VISIBLE);
					}
					if (i == 2) {
						holder.item_pic3.setImageUrl(getItem(position).getSourceInfo().getAttach().get(i).getAttach_middle());
						holder.item_pic3.setVisibility(View.VISIBLE);
					}
					if (i == 3) {
						holder.item_pic4.setImageUrl(getItem(position).getSourceInfo().getAttach().get(i).getAttach_middle());
						holder.item_pic4.setVisibility(View.VISIBLE);
					}
					if (i == 4) {
						holder.item_pic5.setImageUrl(getItem(position).getSourceInfo().getAttach().get(i).getAttach_middle());
						holder.item_pic5.setVisibility(View.VISIBLE);
					}
					if (i == 5) {
						holder.item_pic6.setImageUrl(getItem(position).getSourceInfo().getAttach().get(i).getAttach_middle());
						holder.item_pic6.setVisibility(View.VISIBLE);
					}
					if (i == 6) {
						holder.item_pic7.setImageUrl(getItem(position).getSourceInfo().getAttach().get(i).getAttach_middle());
						holder.item_pic7.setVisibility(View.VISIBLE);
					}
					if (i == 7) {
						holder.item_pic8.setImageUrl(getItem(position).getSourceInfo().getAttach().get(i).getAttach_middle());
						holder.item_pic8.setVisibility(View.VISIBLE);
					}
					if (i == 8) {
						holder.item_pic9.setImageUrl(getItem(position).getSourceInfo().getAttach().get(i).getAttach_middle());
						holder.item_pic9.setVisibility(View.VISIBLE);
					}
				}

			} catch (Exception e) {
				Log.d(TAG, e.toString());
			}
		}
		if (getItem(position).getUser_info().getUname()!=null) {
			holder.reuname_Text.setText(getItem(position).getUser_info().getUname());
		} else {
			holder.reuname_Text.setVisibility(View.GONE);
		}
		if (getItem(position).getSourceInfo().getContent()!=null) {
			SpannableString spannableString = FaceConversionUtil.getInstace().getExpressionString(mContext,getItem(position).getSourceInfo().getContent());
			holder.recontent_Text.setText(spannableString);
			FaceConversionUtil.extractMention2Link(holder.recontent_Text);
		} else {
			holder.recontent_Text.setVisibility(View.GONE);
		}
		addListener(convertView, position);
		return convertView;
	}

	public void addListener(View v, final int position) {
		((RoundCornerImageView) v.findViewById(R.id.myhead)).setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent i = new Intent(mContext, OtherUserPanl.class);
				((Mykey) mContext.getApplicationContext()).setOtheruid(getItem(position).getUid());
				mContext.startActivity(i);
			}
		});
		v.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				((Mykey) mContext.getApplicationContext()).setMymap(getItem(position).getSourceInfo());
				Intent intent = new Intent(mContext, WeiBoDetail.class);
				mContext.startActivity(intent);
			}
		});
	}
	private class ViewHolder {
		TextView uname_Text, From_Text, Ctime_Text, Content_Text, reuname_Text, recontent_Text;
		RoundCornerImageView head_ic;
		SmartImageView item_pic1, item_pic2, item_pic3, item_pic4, item_pic5, item_pic6, item_pic7, item_pic8, item_pic9;
	}

}
