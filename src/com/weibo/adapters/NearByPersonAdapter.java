/**weibotest
 * 
 *
 *TODO
 */
package com.weibo.adapters;
import java.util.ArrayList;
import java.util.HashMap;

import com.loopj.android.image.SmartImageView;
import com.weibo.activity.OtherUserPanl;
import com.weibo.application.Mykey;
import com.weibo.R;
import com.weibo.card.PersonCard;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;

/**
 * @作者:陈华清
 * 
 * @版本:1.0
 * @生成时期:2014年7月26日 上午8:13:02
 * @com.api
 */
public class NearByPersonAdapter extends BaseAdapter {
	ArrayList<PersonCard> cardlist;
	PersonCard person;
	Context mContext;
	private LayoutInflater inflater;

	public NearByPersonAdapter(Context context,
			ArrayList<PersonCard> personcardlist) {
		this.mContext = context;
		inflater = LayoutInflater.from(mContext);
		this.cardlist = personcardlist;

	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return cardlist.size();
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		ViewHolder viewHolder = null;
		person = cardlist.get(position);
		if (convertView == null) {
			viewHolder = new ViewHolder();
			convertView = inflater.inflate(R.layout.follow_person_item, null);
			viewHolder.head_ic = (SmartImageView) convertView
					.findViewById(R.id.imageView1);
			viewHolder.uname_Text = (TextView) convertView
					.findViewById(R.id.textView1);
			viewHolder.intro_Text = (TextView) convertView
					.findViewById(R.id.textView2);
			viewHolder.more = (Button) convertView.findViewById(R.id.button1);
			viewHolder.follow = (TextView) convertView
					.findViewById(R.id.follow);
			viewHolder.follow.setVisibility(View.GONE);
			convertView.setTag(viewHolder);
		} else {
			viewHolder = (ViewHolder) convertView.getTag();
		}
		/*
		 * OtherUser.FOLLOW_STATE = person.getFollow_state(); if
		 * (OtherUser.isFollowing()) {
		 * viewHolder.follow.setCompoundDrawablesWithIntrinsicBounds(0,
		 * R.drawable.card_icon_attention, 0, 0);
		 * viewHolder.follow.setText("已关注");
		 * 
		 * } else { viewHolder.follow.setCompoundDrawablesWithIntrinsicBounds(0,
		 * R.drawable.card_icon_addattention, 0, 0);
		 * viewHolder.follow.setText("未关注"); }
		 */
		if (person.getUname() != null) {
			viewHolder.uname_Text.setText(person.getUname());
		}
		if (person.getIntro() != null) {
			viewHolder.intro_Text.setText(person.getIntro());
		}
		if (person.getAvatar_middle() != null) {
			viewHolder.head_ic.setImageUrl(person.getAvatar_middle());
		}
		addListener(convertView, position);
		return convertView;
	}

	String kkk;

	public void addListener(final View view, int postion) {
		/*
		 * ((TextView) view.findViewById(R.id.follow)) .setOnClickListener(new
		 * OnClickListener() {
		 * 
		 * @Override public void onClick(View v) { // TODO Auto-generated method
		 * stub if (((TextView) view.findViewById(R.id.follow))
		 * .getText().toString().equals("已关注")) {
		 * DialogUtils.dialogBuilder((Activity) mContext, "取消关注", "是否取消关注?", new
		 * DialogCallBack() {
		 * 
		 * @Override public void callBack() { // TODO Auto-generated // method
		 * // stub new AsyncTask<Void, Void, Void>() { protected Void
		 * doInBackground( Void... params) { GetInfor g = new GetInfor(); int
		 * FOLLOW_CREATE = R.string.follow_create; kkk = g.get(
		 * mContext.getString(FOLLOW_CREATE), ((Mykey) mContext
		 * .getApplicationContext()) .getOauth_token(), ((Mykey) mContext
		 * .getApplicationContext()) .getOauth_token_secret(), person.getUid());
		 * Log.d(kkk, person.getUid()); return null; }
		 * 
		 * @Override protected void onPostExecute( Void result) {
		 * OtherUser.FOLLOW_STATE = kkk; if (OtherUser.isFollowing()) {
		 * 
		 * FaceConversionUtil .dispToast( (Activity) mContext, "取消关注成功");
		 * ((TextView) view .findViewById(R.id.follow))
		 * .setCompoundDrawablesWithIntrinsicBounds( 0,
		 * R.drawable.card_icon_addattention, 0, 0); ((TextView) view
		 * .findViewById(R.id.follow)) .setText("未关注"); } else {
		 * FaceConversionUtil .dispToast( (Activity) mContext, "取消关注失败"); } }
		 * }.execute(null, null, null);
		 * 
		 * } }); } else { DialogUtils.dialogBuilder((Activity) mContext, "关注",
		 * "是否关注?", new DialogCallBack() {
		 * 
		 * @Override public void callBack() { // TODO Auto-generated // method
		 * // stub new AsyncTask<Void, Void, Void>() { protected Void
		 * doInBackground( Void... params) { GetInfor g = new GetInfor(); int
		 * FOLLOW_CREATE = R.string.follow_destroy; kkk = g.get(
		 * mContext.getString(FOLLOW_CREATE), ((Mykey) mContext
		 * .getApplicationContext()) .getOauth_token(), ((Mykey) mContext
		 * .getApplicationContext()) .getOauth_token_secret(), person.getUid());
		 * 
		 * return null; }
		 * 
		 * @Override protected void onPostExecute( Void result) {
		 * OtherUser.FOLLOW_STATE = kkk; if (!OtherUser .isFollowing()) {
		 * ((TextView) view .findViewById(R.id.follow))
		 * .setCompoundDrawablesWithIntrinsicBounds( 0,
		 * R.drawable.card_icon_attention, 0, 0); FaceConversionUtil .dispToast(
		 * (Activity) mContext, "关注成功");
		 * 
		 * } else { FaceConversionUtil .dispToast( (Activity) mContext, "关注失败");
		 * } } }.execute(null, null, null); } }); } } });
		 */
		view.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent i = new Intent(mContext, OtherUserPanl.class);
				((Mykey)mContext.getApplicationContext()).setOtheruid(person.getUid());
				mContext.startActivity(i);
			}
		});
	}

	private class ViewHolder {
		TextView follow;
		TextView uname_Text, intro_Text;
		Button more;
		SmartImageView head_ic;
	}

}
