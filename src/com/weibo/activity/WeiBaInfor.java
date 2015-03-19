/**weibotest
 * 
 *
 *TODO
 */
package com.weibo.activity;
import com.weibo.utils.Utils;import com.weibo.R;
import com.weibo.view.widget.RoundCornerImageView;

import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.TextView;
/**
 * @作者:陈华清
 * 
 * @版本:1.0
 * @生成时期:2014年8月18日 上午9:04:32
 * @com.api.example.app
 */
public class WeiBaInfor extends BaseActivity implements OnClickListener{
	TextView intro;
	RoundCornerImageView head;
	ImageView close;
	public void findview() {
		intro = (TextView) findViewById(R.id.text);
		intro.setText(b.getString("intro"));
		head = (RoundCornerImageView) findViewById(R.id.head);
		head.setImageUrl(b.getString("logo_url"));
		close=(ImageView)findViewById(R.id.example_right);
		close.setOnClickListener(this);
	}

	Bundle b;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.weiba_infor_layout);
		b = getIntent().getExtras();
		findview();
		Utils.getInstance().addActivity(this);
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		if(v==close)
		{
			finish();
		}
	}
}
