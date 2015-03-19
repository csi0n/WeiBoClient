/**weibotest
 * 
 *
 *TODO
 */
package com.weibo.activity.selectpop;
import org.json.JSONObject;
import com.weibo.R;
import com.weibo.activity.BaseActivity;
import com.weibo.activity.ReplayWeiBaComment;
import com.weibo.application.*;
import com.weibo.utils.DialogUtils;
import com.weibo.utils.DialogUtils.DialogCallBack;
import com.weibo.utils.FaceConversionUtil;
import com.weibo.utils.Utils;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
/**
 * @作者:陈华清
 * 
 * @版本:1.0
 * @生成时期:2014年8月4日 下午5:50:04
 * @com.ch
 */
public class WeiBaMainSelectCommentOrDel extends BaseActivity implements OnClickListener {
	private static final String TAG = "WeiBaMainSelectCommentOrDel.java";
	Button btn_comment, btn_del, btn_cancel;

	public void findview() {
		btn_comment = (Button) findViewById(R.id.btn_comment);
		btn_del = (Button) findViewById(R.id.btn_del);
		btn_cancel = (Button) findViewById(R.id.btn_cancel);
		btn_comment.setOnClickListener(this);
		btn_del.setOnClickListener(this);
		btn_cancel.setOnClickListener(this);
		try {
			JSONObject o = new JSONObject(getIntent().getStringExtra("author_info"));
			if (o.getString("uid").equals(((Mykey) getApplication()).getUid())) {
				if (btn_del.getVisibility() == View.GONE) {
					btn_del.setVisibility(View.VISIBLE);
				}
			} else {
				if (btn_del.getVisibility() == View.VISIBLE) {
					btn_del.setVisibility(View.GONE);
				}
			}
		} catch (Exception e) {
			Log.d(TAG, e.toString());
		}
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.weibamain_select_comment_or_del_layout);
		findview();
		Utils.getInstance().addActivity(this);
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		if (v == btn_cancel) {
			finish();
		} else if (v == btn_comment) {
			Intent i = new Intent(WeiBaMainSelectCommentOrDel.this, ReplayWeiBaComment.class);
			i.putExtra("post_id", getIntent().getStringExtra("post_id"));
			WeiBaMainSelectCommentOrDel.this.startActivity(i);
		} else if (v == btn_del) {
			DialogUtils.dialogBuilder(WeiBaMainSelectCommentOrDel.this, "是否删除?", "删除?", new DialogCallBack() {
				@Override
				public void callBack() {
					// TODO Auto-generated method stub
					FaceConversionUtil.dispToast(WeiBaMainSelectCommentOrDel.this, "没提供接口怎么删?");
				}
			});
		}
	}
}
