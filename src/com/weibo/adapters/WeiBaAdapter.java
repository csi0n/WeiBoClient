package com.weibo.adapters;

import java.util.ArrayList;
import java.util.HashMap;

import com.loopj.android.image.SmartImageView;
import com.weibo.R;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

//微吧列表的BaseAdapter
public class WeiBaAdapter extends BaseAdapter {
	LayoutInflater inflater;
	ArrayList<HashMap<String, Object>> listitem;
	private Context mContext;
	public WeiBaAdapter(Context ctx, ArrayList<HashMap<String, Object>> listitem) {
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

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		ViewHolder holder = null;
		holder = new ViewHolder();
		convertView = inflater.inflate(R.layout.weibaitem, parent, false);
		holder.textview1 = (TextView) convertView.findViewById(R.id.textView1);
		holder.textview2 = (TextView) convertView.findViewById(R.id.textView2);
		holder.imageview1 = (SmartImageView) convertView
				.findViewById(R.id.imageView1);
		holder.textview3 = (TextView) convertView.findViewById(R.id.textView3);
		holder.textview1.setText(listitem.get(position).get("weiba_name")
				.toString());
		holder.textview2
				.setText(listitem.get(position).get("intro").toString());
		if (listitem.get(position).get("followstate").equals("1")) {
			holder.textview3.setText("已关注");
		} else {
			holder.textview3.setText("未关注");
		}
		holder.imageview1.setImageUrl(listitem.get(position).get("logo_url")
				.toString());
		return convertView;
	}
	private class ViewHolder {
		TextView textview1, textview2, textview3;
		SmartImageView imageview1;
	}
}
