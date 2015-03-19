/**weibotest
 * 
 *
 *TODO
 */
package com.weibo.fragment;
import com.weibo.R;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

/**
 * @作者:陈华清
 * 
 * @版本:1.0
 * @生成时期:2014年7月28日 上午10:03:52
 * @com.frame
 */
public class RightFragment extends Fragment {
	ImageView qq_link_us;
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.right_fragment_layout, container,
				false);
		qq_link_us = (ImageView) view.findViewById(R.id.qq_link_us);
		qq_link_us.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {

				// TODO Auto-generated method stub
				Uri uri = Uri
						.parse("http://wpa.qq.com/msgrd?v=3&uin=841506740&site=qq&menu=yes");
				Intent it = new Intent(Intent.ACTION_VIEW, uri);
				getActivity().startActivity(it);
			}
		});
		return view;
	}

}
