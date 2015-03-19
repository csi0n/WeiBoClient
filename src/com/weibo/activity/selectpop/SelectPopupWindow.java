package com.weibo.activity.selectpop;
import com.weibo.R;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.message.BasicNameValuePair;

import android.app.Activity;
import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.LinearLayout;

import com.weibo.activity.BaseActivity;
import com.weibo.activity.CommentWeiBo;
import com.weibo.activity.MainActivity;
import com.weibo.activity.RelayWeiBo;
import com.weibo.application.*;
import com.weibo.utils.ClientUtils;
import com.weibo.utils.DialogUtils;
import com.weibo.utils.DialogUtils.DialogCallBack;
import com.weibo.utils.FaceConversionUtil;
import com.weibo.utils.PublicTimeWeiBoAuthorize;
import com.weibo.utils.Utils;
/**
 * 
 * 点击每条微博上面的更多textview时调用，弹出评论，转发，取消按钮
 * 
 */
public class SelectPopupWindow extends BaseActivity implements OnClickListener {
	private Button btn_comment, btn_reply, btn_cancel, btn_del;
	private LinearLayout layout;
	private static final String TAG = "SelectPopupWindow.java";
	private static final int success = 0;
	private static final int error = 1;
	private Handler handler = new Handler() {
		public void handleMessage(Message msg) {
			switch (msg.what) {
			case success:
				FaceConversionUtil.dispToast(SelectPopupWindow.this, "删除成功！");
				((Mykey) getApplication()).setIsdel("1");
				SelectPopupWindow.this.finish();
				break;
			case error:
				FaceConversionUtil.dispToast(SelectPopupWindow.this, "删除失败！");
				((Mykey) getApplication()).setIsdel("0");
				SelectPopupWindow.this.finish();
				break;
			}
		}
	};
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.popwin);
		btn_comment = (Button) this.findViewById(R.id.btn_comment);
		btn_reply = (Button) this.findViewById(R.id.btn_reply);
		btn_cancel = (Button) this.findViewById(R.id.btn_cancel);
		btn_del = (Button) this.findViewById(R.id.btn_del);
		layout = (LinearLayout) findViewById(R.id.pop_layout);
		btn_cancel.setOnClickListener(this);
		btn_comment.setOnClickListener(this);
		btn_reply.setOnClickListener(this);
		btn_del.setOnClickListener(this);
		if (((Mykey) getApplication()).getUid().equals(((Mykey) getApplication()).getMymap().getUid())) {
			if (btn_del.getVisibility() == View.GONE) {
				btn_del.setVisibility(View.VISIBLE);
			}
		} else {
			if (btn_del.getVisibility() == View.VISIBLE) {
				btn_del.setVisibility(View.GONE);
			}
		}
		Utils.getInstance().addActivity(this);
	}

	@Override
	public boolean onTouchEvent(MotionEvent event) {
		finish();
		return true;
	}

	public void do_del() {
		new Thread(new Runnable() {
			@Override
			public void run() {
				// TODO Auto-generated method stub
				List<BasicNameValuePair> params = new ArrayList<BasicNameValuePair>();
				params.add(new BasicNameValuePair("oauth_token", ((Mykey)getApplication()).getOauth_token()));
				params.add(new BasicNameValuePair("oauth_token_secret",
						 ((Mykey)getApplication()).getOauth_token_secret()));
				params.add(new BasicNameValuePair("user_id",  ((Mykey)getApplication()).getUid()));
				params.add(new BasicNameValuePair("feed_id",  ((Mykey)getApplication()).getFeed_id()));
				String result=ClientUtils.post_str(ClientUtils.BASE_URL+SelectPopupWindow.this.getString(ClientUtils.WeiboStatuses_destroy), params, SelectPopupWindow.this);
				Log.d(TAG, result);
				if (result.equals("1")) {
					Message message = new Message();
					message.what = success;
					handler.sendMessage(message);
					Log.d(TAG, "删除成功！");
				} else {
					Message message = new Message();
					message.what = error;
					handler.sendMessage(message);
					Log.d(TAG, "删除失败！");
				}
			}
		}).start();
	}

	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.btn_comment:
			if (PublicTimeWeiBoAuthorize.isLogin) {
				Intent intent = new Intent(SelectPopupWindow.this, CommentWeiBo.class);
				intent.putExtra("feed_id", ((Mykey) getApplication()).getFeed_id());
				SelectPopupWindow.this.startActivity(intent);
			} else {
				DialogUtils.dialogBuilder(SelectPopupWindow.this, "提示", "请先登录！", new DialogCallBack() {
					@Override
					public void callBack() {
						Intent i = new Intent(SelectPopupWindow.this, MainActivity.class);
						SelectPopupWindow.this.startActivity(i);
					}
				});
			}
			break;
		case R.id.btn_reply:
			if (PublicTimeWeiBoAuthorize.isLogin) {
				Intent intent1 = new Intent(SelectPopupWindow.this, RelayWeiBo.class);
				SelectPopupWindow.this.startActivity(intent1);
			} else {
				DialogUtils.dialogBuilder(SelectPopupWindow.this, "提示", "请先登录！", new DialogCallBack() {
					@Override
					public void callBack() {
						Intent i = new Intent(SelectPopupWindow.this, MainActivity.class);
						SelectPopupWindow.this.startActivity(i);
					}
				});
			}
			break;
		case R.id.btn_cancel:
			finish();
			break;
		case R.id.btn_del:
			if (PublicTimeWeiBoAuthorize.isLogin) {
				DialogUtils.dialogBuilder(SelectPopupWindow.this, "删除", "确定删除这条微博？", new DialogCallBack() {
					@Override
					public void callBack() {
						do_del();
					}
				});
			} else {
				DialogUtils.dialogBuilder(SelectPopupWindow.this, "提示", "请先登录！", new DialogCallBack() {
					@Override
					public void callBack() {
						Intent i = new Intent(SelectPopupWindow.this, MainActivity.class);
						SelectPopupWindow.this.startActivity(i);
					}
				});
			}
			break;
		default:
			break;
		}
	}

	protected void onStop() {
		super.onStop();
	}

	protected void onPause() {
		super.onPause();

	}

	protected void onResume() {
		super.onResume();
		if (((Mykey) getApplication()).getShoulddissmiss().equals("1")) {
			((Mykey) getApplication()).setShoulddissmiss("0");
			SelectPopupWindow.this.finish();
		}
	}

}