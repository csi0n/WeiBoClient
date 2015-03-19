/**weibotest
 * 
 *
 *TODO
 */
package com.weibo.activity.selectpop;
import com.weibo.R;

import org.json.JSONObject;

import com.weibo.activity.BaseActivity;
import com.weibo.activity.ReplayWeiBaComment;
import com.weibo.application.*;
import com.weibo.utils.DialogUtils;
import com.weibo.utils.DialogUtils.DialogCallBack;
import com.weibo.utils.FaceConversionUtil;
import com.weibo.utils.Utils;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.WindowManager;
import android.widget.Button;
/**
 * @作者:陈华清
 * 
 * @版本:1.0
 * @生成时期:2014年8月3日 下午5:04:28
 * @com.ch
 */
public class WeiBaSelectCommentAndDel extends BaseActivity implements
		OnClickListener {
	Button comment_replay_btn, cancel_btn, del_btn;
	private static final String TAG = "WeiBaSelectCommentAndDel.java";
	protected static final int del_success = 0;
	protected static final int del_error = 1;
	private Handler handler = new Handler() {
		public void handleMessage(Message msg) {
			switch (msg.what) {
			case del_success:
				FaceConversionUtil.dispToast(WeiBaSelectCommentAndDel.this,
						"删除成功!");
				((Mykey) getApplication()).setRefresh("1");
				finish();
				break;
			case del_error:
				FaceConversionUtil.dispToast(WeiBaSelectCommentAndDel.this,
						"删除失败!");
				finish();
				break;
			}
		}
	};
	public void findview() {
		comment_replay_btn = (Button) findViewById(R.id.btn_comment);
		comment_replay_btn.setOnClickListener(this);
		cancel_btn = (Button) findViewById(R.id.btn_cancel);
		cancel_btn.setOnClickListener(this);
		del_btn = (Button) findViewById(R.id.btn_del);
		del_btn.setOnClickListener(this);
		try {
			if (o.has("uid")) {
				if (o.getString("uid").equals(
						((Mykey) getApplication()).getUid())) {
					if (del_btn.getVisibility() == View.GONE) {
						del_btn.setVisibility(View.VISIBLE);
					}
				} else {
					if (del_btn.getVisibility() == View.VISIBLE) {
						del_btn.setVisibility(View.GONE);
					}
				}
			}
		} catch (Exception e) {
			Log.d(TAG, e.toString());
		}
	}
	JSONObject o;
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.weiba_select_comment_and_del_layout);
		try {
			o = new JSONObject(getIntent().getStringExtra("author_info"));
		} catch (Exception e) {
			Log.d(TAG, e.toString());
		}
		findview();
		Utils.getInstance().addActivity(this);
	}

	// reply_id
	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.btn_comment:
			new Thread(new Runnable() {
				@Override
				public void run() {
					try {
						Intent i = new Intent(WeiBaSelectCommentAndDel.this,
								ReplayWeiBaComment.class);
						i.putExtra("reply_id",
								getIntent().getStringExtra("reply_id"));
						i.putExtra("uname", o.getString("uname"));
						WeiBaSelectCommentAndDel.this.startActivity(i);
					} catch (Exception e) {
						Log.d(TAG, e.toString());
					}
				}
			}).start();
			break;
		case R.id.btn_cancel:
			finish();
			break;
		case R.id.btn_del:
			DialogUtils.dialogBuilder(WeiBaSelectCommentAndDel.this, "提示",
					"确定要删除该条评论？", new DialogCallBack() {
						@Override
						public void callBack() {
							new Thread(new Runnable() {
								@Override
								public void run() {
									// TODO Auto-generated method stub
									/*int DELETE_COMMENT = R.string.delete_comment;
									String result = GetInfor.delcomment(
											WeiBaSelectCommentAndDel.this
													.getString(DELETE_COMMENT),
											((Mykey) getApplication())
													.getOauth_token(),
											((Mykey) getApplication())
													.getOauth_token_secret(),
											((Mykey) getApplication()).getUid(),
											getIntent().getStringExtra(
													"reply_id"));
									if (result.equals("1")) {
										Message me = new Message();
										me.what = del_success;
										handler.sendMessage(me);
									} else {
										Message m = new Message();
										m.what = del_error;
										handler.sendMessage(m);
									}*/
								}
							}).start();
						}
					});
			break;
		}
	}
}
