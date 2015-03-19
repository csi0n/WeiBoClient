/**
 * weibotest
 * 
 * 
 * TODO
 */

package com.weibo.adapters;

import java.util.ArrayList;
import java.util.HashMap;

import org.json.JSONObject;

import com.loopj.android.image.SmartImageView;
import com.weibo.activity.OtherUserPanl;
import com.weibo.R;
import com.weibo.activity.selectpop.WeiBaSelectCommentAndDel;
import com.weibo.application.Mykey;
import com.weibo.utils.FaceConversionUtil;

import android.content.Context;
import android.content.Intent;
import android.text.SpannableString;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

/**
 * @作者:陈华清
 * 
 * @版本:1.0
 * @生成时期:2014年7月20日 下午5:36:16
 * @com.api
 */
public class WeiBaDetailAdapter extends BaseAdapter {

	LayoutInflater inflater;

	ArrayList<HashMap<String, Object>> listitem;

	private Context mContext;

	HashMap<String, Object> map;

	final int type1 = 0;

	final int type2 = 1;

	final int type3 = 2;

	private static final String TAG = "WeiBaDetailAdapter.java";

	public WeiBaDetailAdapter(Context ctx, ArrayList<HashMap<String, Object>> listitem, HashMap<String, Object> map) {

		mContext = ctx;
		this.listitem = listitem;
		this.map = map;
		inflater = LayoutInflater.from(mContext);
	}

	@Override
	public int getCount() {
		if (listitem != null && listitem.size() > 0) {
			return listitem.size() + 1;
		} else {
			return listitem.size() + 2;
		}
	}

	@Override
	public HashMap<String, Object> getItem(int position) {
		if (listitem != null && listitem.size() > 0) {
			return listitem.get(position - 1);
		} else {
			return listitem.get(position - 2);
		}
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
		} else if (p == 1 && listitem.size() == 0) {
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
		ViewHolderWeiBaDetail viewHolderdetail = null;
		ViewHolderWeiBoComment viewHoldercomment = null;
		int type = getItemViewType(position);
		if (convertView == null) {
			switch (type) {
				case type1 :
					viewHolderdetail = new ViewHolderWeiBaDetail();
					convertView = inflater.inflate(R.layout.weiba_maindetail, null);
					viewHolderdetail.head_ic = (SmartImageView) convertView.findViewById(R.id.imageView2);
					viewHolderdetail.data_Text = (TextView) convertView.findViewById(R.id.textView3);
					viewHolderdetail.content_Text = (TextView) convertView.findViewById(R.id.textView2);
					viewHolderdetail.title_Text = (TextView) convertView.findViewById(R.id.textView1);
					viewHolderdetail.comment_Text = (TextView) convertView.findViewById(R.id.textView4);
					viewHolderdetail.author_Text = (TextView) convertView.findViewById(R.id.textView7);
					viewHolderdetail.read_Text = (TextView) convertView.findViewById(R.id.textView5);
					break;
				case type2 :
					convertView = inflater.inflate(R.layout.nothing, null);
					break;
				case type3 :
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
				case type1 :
					viewHolderdetail = new ViewHolderWeiBaDetail();
					convertView = inflater.inflate(R.layout.weiba_maindetail, null);
					viewHolderdetail.head_ic = (SmartImageView) convertView.findViewById(R.id.imageView2);
					viewHolderdetail.data_Text = (TextView) convertView.findViewById(R.id.textView3);
					viewHolderdetail.content_Text = (TextView) convertView.findViewById(R.id.textView2);
					viewHolderdetail.title_Text = (TextView) convertView.findViewById(R.id.textView1);
					viewHolderdetail.comment_Text = (TextView) convertView.findViewById(R.id.textView4);
					viewHolderdetail.author_Text = (TextView) convertView.findViewById(R.id.textView7);
					viewHolderdetail.read_Text = (TextView) convertView.findViewById(R.id.textView5);
					break;
				case type2 :
					convertView = inflater.inflate(R.layout.nothing, null);
					break;
				case type3 :

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
			case type1 :
				try {
					if (map != null && map.size() > 0) {
						JSONObject o = new JSONObject(map.get("author_info").toString());
						viewHolderdetail.head_ic.setImageUrl(o.getString("avatar_middle"));
						viewHolderdetail.author_Text.setText(o.getString("uname"));
						viewHolderdetail.title_Text.setText(map.get("title").toString());
						viewHolderdetail.data_Text.setText(FaceConversionUtil.TimeStamp2Date(map.get("post_time").toString()));
						viewHolderdetail.comment_Text.setText("评论:" + map.get("reply_count").toString());
						viewHolderdetail.read_Text.setText("阅读:" + map.get("read_count").toString());
						SpannableString spannableString = FaceConversionUtil.getInstace().getExpressionString(mContext, map.get("content").toString());
						viewHolderdetail.content_Text.setText(spannableString);
						FaceConversionUtil.extractMention2Link(viewHolderdetail.content_Text);
					}
				} catch (Exception e) {
					Log.d(TAG, e.toString());
				}
				break;
			case type3 :
				if (listitem != null || listitem.size() > 0) {
					viewHoldercomment.c_uName_Text.setText(getItem(position).get("uname").toString());
					viewHoldercomment.c_head_pic.setImageUrl(getItem(position).get("avatar_middle").toString());
					viewHoldercomment.c_Date_Text.setText(getItem(position).get("ctime").toString());
					SpannableString spannableString = FaceConversionUtil.getInstace().getExpressionString(mContext, getItem(position).get("content").toString());
					viewHoldercomment.c_content_Text.setText(spannableString);
					FaceConversionUtil.extractMention2Link(viewHoldercomment.c_content_Text);
					if (position == 1) {
						if (viewHoldercomment.arrow.getVisibility() == View.GONE) {
							viewHoldercomment.arrow.setVisibility(View.VISIBLE);
						}
					}
				}
				break;
		}
		addListener(convertView, type, position);
		return convertView;
	}

	public void addListener(View convertView, int type, int postion) {
		final int arg = postion;
		switch (type) {
			case type1 :
				((SmartImageView) convertView.findViewById(R.id.imageView2)).setOnClickListener(new View.OnClickListener() {
					@Override
					public void onClick(View v) {
						// TODO Auto-generated method stub
						try {
							Intent i = new Intent(mContext, OtherUserPanl.class);
							JSONObject o = new JSONObject(map.get("author_info").toString());
							((Mykey) mContext.getApplicationContext()).setOtheruid(o.getString("uid"));
							mContext.startActivity(i);
						} catch (Exception e) {
							Log.d(TAG, e.toString());
						}
					}
				});
				break;
			case type3 :
				((SmartImageView) convertView.findViewById(R.id.head)).setOnClickListener(new View.OnClickListener() {
					@Override
					public void onClick(View v) {
						// TODO Auto-generated method stub
						try {
							Intent i = new Intent(mContext, OtherUserPanl.class);
							JSONObject o = new JSONObject(getItem(arg).get("author_info").toString());
							((Mykey) mContext.getApplicationContext()).setOtheruid(o.getString("uid"));
							mContext.startActivity(i);
						} catch (Exception e) {
							Log.d(TAG, e.toString());
						}
					}
				});
				convertView.setOnClickListener(new View.OnClickListener() {
					@Override
					public void onClick(View v) {
						// TODO Auto-generated method stub
						Intent i = new Intent(mContext, WeiBaSelectCommentAndDel.class);
						i.putExtra("author_info", getItem(arg).get("author_info").toString());
						i.putExtra("reply_id", getItem(arg).get("reply_id").toString());
						mContext.startActivity(i);
					}
				});
				break;
		}
	}
	class ViewHolderWeiBaDetail {
		SmartImageView head_ic;
		TextView data_Text, content_Text, title_Text, comment_Text, read_Text, author_Text;
	}
	class ViewHolderWeiBoComment {
		SmartImageView c_head_pic;
		ImageView arrow;
		TextView c_uName_Text, c_content_Text, c_Date_Text;
	}

}
