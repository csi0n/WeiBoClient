/**weibotest
 * 
 *
 *TODO
 */
package com.weibo.view.widget;

import java.util.Timer;
import java.util.TimerTask;
import com.weibo.R;

import android.app.AlertDialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * @作者:陈华清
 * 
 * @版本:1.0
 * @生成时期:2014年8月1日 上午7:35:18
 * @com.example.weibotest
 */
public class CheckAlertAnim extends AlertDialog {
	TextView text;
	public String s = "";
	ImageView pic;
	int res;
	int time;
	Timer timer;
	private Context mContext;

	protected CheckAlertAnim(Context context) {
		super(context);
		this.mContext = context;
		// TODO Auto-generated constructor stub
	}

	public CheckAlertAnim(Context context, int theme, String s, int res,
			int time) {
		// TODO Auto-generated constructor stub
		super(context, theme);
		this.mContext = context;
		this.s = s;
		this.res = res;
		this.time = time;
	}

	TimerTask task = new TimerTask() {
		public void run() {
			dismiss();
			timer.cancel();
		}
	};

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.check_success_mark);
		text = (TextView) findViewById(R.id.text);
		pic = (ImageView) findViewById(R.id.pic);
		pic.setImageDrawable(mContext.getResources().getDrawable(res));
		text.setText(s);
		timer = new Timer();
		timer.schedule(task, time);
	}
}
