package com.weibo.adapters;
import java.util.ArrayList;
import java.util.HashMap;

import com.loopj.android.image.SmartImageView;
import com.weibo.R;
import com.weibo.activity.ShowSearchResult;
import com.weibo.utils.ACache;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * @作者:陈华清
 *
 * @版本:1.0
 * @生成时期:2014年8月4日 下午11:07:27
 * @com.api
 */
public class SearchAche_itemAdapter extends BaseAdapter {
	LayoutInflater inflater;
	ArrayList list;
	private Context mContext;
	String o;
	ACache mcache;

	public SearchAche_itemAdapter(Context ctx, ArrayList list, String o) {
		mContext = ctx;
		this.list = list;
		this.o = o;
		mcache = ACache.get(mContext);
		inflater = LayoutInflater.from(mContext);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see android.widget.Adapter#getCount()
	 */
	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return list.size();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see android.widget.Adapter#getItem(int)
	 */
	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return list.get(position);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see android.widget.Adapter#getItemId(int)
	 */
	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return 0;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see android.widget.Adapter#getView(int, android.view.View,
	 * android.view.ViewGroup)
	 */
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		ViewHolder holder = null;
		if (convertView == null) {
			convertView = inflater.inflate(R.layout.searchache_iem, parent,
					false);
			holder = new ViewHolder();
			holder.textview1 = (TextView) convertView
					.findViewById(R.id.textView1);
			holder.close = (ImageView) convertView
					.findViewById(R.id.imageView1);
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}
		holder.textview1.setText(list.get(position).toString());
		AddListener(convertView, position);
		return convertView;
	}

	private class ViewHolder {
		TextView textview1;
		ImageView close;
	}

	public void DelSearchKey(int key) {
		for (int i = 0; i < list.size(); i++) {
			mcache.put(o, list.get(i).toString() + ";");
		}
	}

	public void AddListener(View convertView, int position) {
		final int arg2 = position;
		TextView t1 = (TextView) convertView.findViewById(R.id.textView1);
		final String s = t1.getText().toString();
		((ImageView) convertView.findViewById(R.id.imageView1))
				.setOnClickListener(new View.OnClickListener() {
					@Override
					public void onClick(View v) {
						// TODO Auto-generated method stub
						list.remove(arg2);
						DelSearchKey(arg2);
						SearchAche_itemAdapter.this.notifyDataSetChanged();
					}
				});
		convertView.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if (o.equals("word_key")) {
					Intent i = new Intent(mContext, ShowSearchResult.class);
					Bundle b = new Bundle();
					b.putString("word_key", s);
					b.putString("id", "0");
					i.putExtras(b);
					mContext.startActivity(i);
				} else if (o.equals("word_key2")) {
					Intent i = new Intent(mContext, ShowSearchResult.class);
					Bundle b = new Bundle();
					b.putString("word_key", s);
					b.putString("id", "1");
					i.putExtras(b);
					mContext.startActivity(i);
				}
			}
		});
	}

}
