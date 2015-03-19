/**weibotest
 * 
 *
 *TODO
 */
package com.weibo.activity.selectpop;
import com.weibo.R;
import com.weibo.activity.BaseActivity;
import com.weibo.activity.WeiBoDetail;
import com.weibo.application.*;
import com.weibo.utils.DialogUtils;
import com.weibo.utils.DialogUtils.DialogCallBack;
import com.weibo.utils.FaceConversionUtil;
import com.weibo.utils.Utils;
import com.weibo.view.widget.LoadingAlertAnim;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
/**
 * @作者:陈华清
 * 
 * @版本:1.0
 * @生成时期:2014年7月27日 上午10:31:57
 * @com.ch
 */
public class CommentWeiBoSelectPopWindow extends BaseActivity implements OnClickListener {
	Button btn_view_weibo, btn_del, btn_cancel;
	private static final String TAG = "CommentWeiBoSelectPopWindow";
	protected static final int success = 0;
	protected static final int error = 1;
	private Handler handler = new Handler() {
		@SuppressLint("ResourceAsColor")
		public void handleMessage(Message msg) {
			switch (msg.what) {
			case success:
				FaceConversionUtil.dispToast(CommentWeiBoSelectPopWindow.this, "删除成功!");
				((Mykey) getApplication()).setIsdel("1");
				finish();
				break;
			case error:
				FaceConversionUtil.dispToast(CommentWeiBoSelectPopWindow.this, "删除失败!");
				finish();
				break;
			}
		}
	};

	public void findview() {
		btn_view_weibo = (Button) findViewById(R.id.btn_view_weibo);
		btn_del = (Button) findViewById(R.id.btn_del);
		btn_cancel = (Button) findViewById(R.id.btn_cancel);
		btn_view_weibo.setOnClickListener(this);
		btn_del.setOnClickListener(this);
		btn_cancel.setOnClickListener(this);
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.comment_weibo_select_popwindow);
		findview();
		Utils.getInstance().addActivity(this);
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.btn_view_weibo:
			Intent i = new Intent(CommentWeiBoSelectPopWindow.this, WeiBoDetail.class);
			CommentWeiBoSelectPopWindow.this.startActivity(i);
			break;
		case R.id.btn_del:
			DialogUtils.dialogBuilder(CommentWeiBoSelectPopWindow.this, "提示", "确定要清除图片吗？", new DialogCallBack() {
				@Override
				public void callBack() {
					loading = new LoadingAlertAnim(CommentWeiBoSelectPopWindow.this, R.style.MyDialogStyle, "正在删除...");
					loading.setCanceledOnTouchOutside(false);
					loading.show();
					del.start();
				}
			});

			break;
		case R.id.btn_cancel:
			finish();
			break;
		}
	}
	LoadingAlertAnim loading;
	Thread del = new Thread(new Runnable() {
		@Override
		public void run() {
			// TODO Auto-generated method stub
			/*GetInfor g = new GetInfor();
			String result = g.getwb(CommentWeiBoSelectPopWindow.this.getString(DESTROY), ((Mykey) getApplication()).getOauth_token(), ((Mykey) getApplication()).getOauth_token_secret(), getIntent().getStringExtra("row_id"));
			Log.d(TAG, result);
			if (result.equals("1")) {
				Message message = new Message();
				message.what = success;
				handler.sendMessage(message);
			} else {
				Message message = new Message();
				message.what = error;
				handler.sendMessage(message);
			}*/

		}
	});

}
