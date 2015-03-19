/**weibotest
 * 
 *
 *TODO
 */
package com.weibo.adapters;
import java.util.List;

import com.loopj.android.image.SmartImageView;
import com.weibo.R;
import com.weibo.card.UserInfor;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
/**
 * @作者:陈华清
 * 
 * @版本:1.0
 * @生成时期:2014年7月20日 上午11:46:17
 * @com.api
 */
public class FindMeAdapter extends BaseAdapter {
	private List<UserInfor> coll;
	private LayoutInflater mInflater;
	private Context context;
	public FindMeAdapter(Context context, List<UserInfor> coll) {
		this.coll = coll;
		mInflater = LayoutInflater.from(context);
		this.context = context;
	}
	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return coll.size();
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
		UserInfor u = coll.get(position);
		ViewHolder viewHolder = null;
		viewHolder = new ViewHolder();
		convertView = mInflater.inflate(R.layout.at_me_item, null);
		viewHolder.user_Name = (TextView) convertView
				.findViewById(R.id.textView1);
		viewHolder.head_ic = (SmartImageView) convertView
				.findViewById(R.id.image);
		viewHolder.head_ic.setImageUrl(u.getAvatar_middle());
		viewHolder.user_Name.setText(u.getUname());
		//addListener(convertView,position);
		return convertView;
	}
	/*public void addListener(View convert,int position)
	{
		final TextView t=(TextView)convert.findViewById(R.id.image);
		convert.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Mykey app = (Mykey) context.getApplicationContext();
				app.setFind_me(t.getText().toString());
				finish;
			}
		});
	}*/
	class ViewHolder {
		public TextView user_Name;
		public SmartImageView head_ic;
	}

}
