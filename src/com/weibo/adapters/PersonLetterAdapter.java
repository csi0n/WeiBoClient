package com.weibo.adapters;

import java.util.ArrayList;
import java.util.HashMap;

import org.json.JSONException;
import org.json.JSONObject;

import com.loopj.android.image.SmartImageView;
import com.weibo.activity.ChatWithFriend;
import com.weibo.R;
import com.weibo.utils.DialogUtils;
import com.weibo.utils.DialogUtils.DialogCallBack;
import com.weibo.utils.FaceConversionUtil;
import com.weibo.view.widget.RoundCornerImageView;

import android.util.Log;
import android.view.MotionEvent;
import android.animation.ObjectAnimator;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.Html;
import android.text.SpannableString;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnLongClickListener;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

/**
 * 
 * 私信的Adapter
 * 
 */
@SuppressLint("NewApi")
public class PersonLetterAdapter extends BaseAdapter {
	LayoutInflater inflater;
	ArrayList<HashMap<String, Object>> listitem;
	private Context mContext;
	private static final String TAG = "sixinAdapter.java";

	public PersonLetterAdapter(Context ctx, ArrayList<HashMap<String, Object>> listitem) {
		mContext = ctx;

		this.listitem = listitem;
		inflater = LayoutInflater.from(mContext);
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return listitem.size();
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return listitem.get(position);
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return 0;
	}

	ViewHolder holder;

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		if (convertView == null) {
			holder = new ViewHolder();
			convertView = inflater.inflate(R.layout.person_letter_listitem, null);
			holder.imageview1 = (RoundCornerImageView) convertView.findViewById(R.id.imageView1);
			holder.textview1 = (TextView) convertView.findViewById(R.id.textView1);
			holder.textview2 = (TextView) convertView.findViewById(R.id.textView2);
			holder.textview4 = (TextView) convertView.findViewById(R.id.textView4);
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}
		try {
			JSONObject o = new JSONObject(listitem.get(position).get("to_user_info").toString());
			holder.textview1.setText(o.getString("uname"));
			holder.imageview1.setImageUrl(o.getString("avatar_middle"));
		} catch (Exception e) {
			Log.d(TAG, e.toString());
		}
		SpannableString spannableString = FaceConversionUtil.getInstace().getExpressionString(mContext, listitem.get(position).get("content").toString());
		holder.textview2.setText(spannableString);
		FaceConversionUtil.extractMention2Link(holder.textview2);
		holder.textview4.setText(FaceConversionUtil.getInterval(FaceConversionUtil.TimeStamp2Date(listitem.get(position).get("ctime").toString())));
		addListener(convertView, position);
		return convertView;
	}

	static class ViewHolder {
		TextView textview1, textview2, textview4;
		RoundCornerImageView imageview1;
	}

	public void addListener(View convertView, int arg) {
		final int position = arg;
		convertView.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				try {
					Intent i = new Intent(mContext, ChatWithFriend.class);
					Bundle b = new Bundle();
					b.putString("from_uname", listitem.get(position).get("from_uname").toString());
					JSONObject t = new JSONObject(listitem.get(position).get("to_user_info").toString());
					b.putString("to_uid", t.getString("uid"));
					i.putExtras(b);
					mContext.startActivity(i);
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

			}
		});
		convertView.setOnLongClickListener(new OnLongClickListener() {
			@Override
			public boolean onLongClick(View v) {
				// TODO Auto-generated method stub
				DialogUtils.dialogBuilder((Activity) mContext, "删除内容？", "确定要删除内容？", new DialogCallBack() {
					@Override
					public void callBack() {
						Log.d(TAG, "检测到删除任务！删除list_id为：" + listitem.get(position).get("list_id").toString());
						listitem.remove(position);
						notifyDataSetChanged();
					}
				});
				return true;
			}
		});
	}
}
