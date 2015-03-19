/**weibotest
 * 
 *
 *TODO
 */
package com.weibo.adapters;
import com.weibo.R;

import android.content.Context;
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
 * @生成时期:2014年8月18日 上午6:53:38
 * @com.api
 */
public class WeiBaSendBtnAdapter extends BaseAdapter {
	private Context mContext;
	private String titlename[];
	private LayoutInflater inflater;
	private int picture[];
	public WeiBaSendBtnAdapter(Context cc, String titlename[],int pic[]) {
		this.mContext = cc;
		this.picture=pic;
		this.titlename = titlename;
		this.inflater = LayoutInflater.from(mContext);
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return titlename.length;
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
		ViewHolder holder=null;
		if(convertView==null)
		{
			convertView=inflater.inflate(R.layout.weiba_sendbtn_item, parent, false);
			holder=new ViewHolder();
			holder.pic=(ImageView)convertView.findViewById(R.id.pic);
			holder.text=(TextView)convertView.findViewById(R.id.text);
			convertView.setTag(holder);
		}else
		{
			holder=(ViewHolder)convertView.getTag();
		}
		holder.pic.setImageDrawable(mContext.getResources().getDrawable(picture[position]));
		holder.text.setText(titlename[position]);
		return convertView;
	}
	class ViewHolder
	{
		ImageView pic;
		TextView text;
	}

}
