package com.weibo.adapters;

import android.content.Context;
import android.content.Intent;
import android.text.SpannableString;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

import com.loopj.android.image.SmartImageView;
import com.weibo.activity.OtherUserPanl;
import com.weibo.application.Mykey;
import com.weibo.R;
import com.weibo.card.ChatMsgEntity;
import com.weibo.utils.FaceConversionUtil;
/**
 * @作者:陈华清
 * 
 * @版本:1.0
 * @生成时期:2014年8月3日 下午11:49:24
 * @com.server
 */
public class ChatMsgAdapter extends BaseAdapter {

	public static interface IMsgViewType {
		int IMVT_COM_MSG = 0;
		int IMVT_TO_MSG = 1;
	}
	private List<ChatMsgEntity> coll;
	private LayoutInflater mInflater;
	private Context context;

	public ChatMsgAdapter(Context context, List<ChatMsgEntity> coll) {
		this.coll = coll;
		mInflater = LayoutInflater.from(context);
		this.context = context;
	}
	public int getCount() {
		return coll.size();
	}

	public Object getItem(int position) {
		return coll.get(position);
	}

	public long getItemId(int position) {
		return position;
	}

	public int getItemViewType(int position) {
		try {
			ChatMsgEntity entity = coll.get(position);
			if (entity.getMsgType()) {
				return IMsgViewType.IMVT_COM_MSG;
			} else {
				return IMsgViewType.IMVT_TO_MSG;
			}
		} catch (ArrayIndexOutOfBoundsException e) {
			e.printStackTrace();
			return 0;
		}
	}
	public int getViewTypeCount() {
		return 2;
	}
	public View getView(int position, View convertView, ViewGroup parent) {
		ChatMsgEntity entity = coll.get(position);
		boolean isComMsg = entity.getMsgType();
		ViewHolder viewHolder = null;
		if (convertView == null) {
			if (isComMsg) {
				convertView = mInflater.inflate(R.layout.chatting_item_msg_text_left, null);
			} else {
				convertView = mInflater.inflate(R.layout.chatting_item_msg_text_right, null);
			}
			viewHolder = new ViewHolder();
			viewHolder.tvSendTime = (TextView) convertView.findViewById(R.id.tv_sendtime);
			viewHolder.tvContent = (TextView) convertView.findViewById(R.id.tv_chatcontent);
			viewHolder.image = (SmartImageView) convertView.findViewById(R.id.iv_userhead);
			viewHolder.isComMsg = isComMsg;

			convertView.setTag(viewHolder);
		} else {
			viewHolder = (ViewHolder) convertView.getTag();
		}
		viewHolder.tvSendTime.setText(entity.getDate());
		SpannableString spannableString = FaceConversionUtil.getInstace().getExpressionString(context, entity.getText());
		viewHolder.tvContent.setText(spannableString);
		viewHolder.image.setImageUrl(entity.getFaceurl());
		addListener(convertView, position, entity);
		return convertView;
	}

	public void addListener(View convertView, int position, ChatMsgEntity q) {
		final ChatMsgEntity entity = q;
		((SmartImageView) convertView.findViewById(R.id.iv_userhead)).setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				Mykey app = (Mykey) context.getApplicationContext();
				app.setOtheruid(entity.getFrom_uid());
				Intent intent = new Intent(context, OtherUserPanl.class);
				context.startActivity(intent);
			}
		});
	}

	class ViewHolder {
		public TextView tvSendTime;
		public TextView tvContent;
		public SmartImageView image;
		public boolean isComMsg = true;
	}

}
