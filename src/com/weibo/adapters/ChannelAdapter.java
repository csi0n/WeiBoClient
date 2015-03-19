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
/**
 * 
 * 频道的Adapter
 *
 *//**
 * @作者:陈华清
*
* @版本:1.0
* @生成时期:2014年7月24日 下午10:49:27
* @com.server
*/
public class ChannelAdapter extends BaseAdapter {
	LayoutInflater inflater;
	ArrayList<HashMap<String, Object>> listitem;
	private Context mContext;

	public ChannelAdapter(Context ctx, ArrayList<HashMap<String, Object>> listitem) {
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
		return null;
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return 0;
	}

	ViewHolder holder;

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		ViewHolder holder = null;
		if (convertView == null) {
			convertView = inflater.inflate(R.layout.channel_item, parent, false);
			holder = new ViewHolder();
			holder.textview1 = (TextView) convertView
					.findViewById(R.id.textView1);
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}
		holder.textview1
				.setText(listitem.get(position).get("title").toString());
		return convertView;
	}

	private class ViewHolder {
		TextView textview1;
	}

}
