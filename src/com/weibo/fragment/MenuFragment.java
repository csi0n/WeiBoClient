/**weibotest
 * 
 *
 *TODO
 */
package com.weibo.fragment;
import com.loopj.android.image.SmartImageView;
import com.weibo.activity.MyWeiBaSetting;
import com.weibo.application.Mykey;
import com.weibo.R;
import com.weibo.card.UserInfor;
import com.weibo.view.widget.RoundCornerImageView;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

/**
 * @作者:陈华清
 * 
 * @版本:1.0
 * @生成时期:2014年7月27日 下午8:18:39
 * @com.frame
 */

public class MenuFragment extends Fragment implements View.OnClickListener {
	RoundCornerImageView head;
	Button myviewweiba_btn, send_note_ago, comment_btn, search_weiba_btn, search_note_btn, favorite_btn;
	MyWeiBaSetting mainActivity;
	TextView Myname;
	private static final String TAG = "MenuFragment.java";
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.weibamenufragment_layout, container, false);
		myviewweiba_btn = (Button) view.findViewById(R.id.myviewweiba_btn);
		myviewweiba_btn.setOnClickListener(this);
		send_note_ago = (Button) view.findViewById(R.id.send_note_ago);
		send_note_ago.setOnClickListener(this);
		comment_btn = (Button) view.findViewById(R.id.comment_note);
		comment_btn.setOnClickListener(this);
		search_weiba_btn = (Button) view.findViewById(R.id.search_weiba);
		search_weiba_btn.setOnClickListener(this);
		search_note_btn = (Button) view.findViewById(R.id.search_note);
		search_note_btn.setOnClickListener(this);
		favorite_btn = (Button) view.findViewById(R.id.favorite_list);
		favorite_btn.setOnClickListener(this);
		head = (RoundCornerImageView) view.findViewById(R.id.head_ic);
		Myname = (TextView) view.findViewById(R.id.myname);
		mainActivity = (MyWeiBaSetting) getActivity();
		head.setImageUrl(((Mykey) mainActivity.getApplication()).getMy_head());
		Myname.setText(UserInfor.UNAME);
		return view;
	}
	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
			case R.id.myviewweiba_btn :
				switchFragment(new FollowWeiBa());
				break;
			case R.id.send_note_ago :
				switchFragment(new SendNoteAgo());
				break;
			case R.id.comment_note :
				switchFragment(new Comment());
				break;
			case R.id.search_weiba :
				switchFragment(new SearchWeiBa());
				break;
			case R.id.search_note :
				switchFragment(new SearchNote());
				break;
			case R.id.favorite_list :
				switchFragment(new FavoriteList());
				break;
		}
	}
	private void switchFragment(Fragment fragment) {
		if (getActivity() == null)
			return;
		MyWeiBaSetting ra = (MyWeiBaSetting) getActivity();
		ra.switchContent(fragment);
	}

}
