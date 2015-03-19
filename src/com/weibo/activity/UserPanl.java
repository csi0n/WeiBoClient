package com.weibo.activity;

import com.weibo.card.UserInfor;import com.weibo.R;
import com.weibo.utils.Common;
import com.weibo.utils.Utils;
import com.weibo.view.widget.RoundCornerImageView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
/**
 * 
 * 用户点击更多资料时调用
 * 
 */
public class UserPanl extends BaseActivity implements OnClickListener{
	LinearLayout headl, unamel, introl, emaill, sexl, languagel, searchkeyl,qrl;
	TextView uname, intro, email, sex, language, searchkey;
	RoundCornerImageView head;
	ImageView close,myqr;
	private static final String TAG = "UserPanl.java";
	public void findview() {
		myqr=(ImageView)findViewById(R.id.myqr);
		myqr.setImageBitmap(Utils.createImage(40, 40, Common.USER_INFOR.getUname()));
		qrl=(LinearLayout)findViewById(R.id.qrl);
		qrl.setOnClickListener(this);
		close=(ImageView)findViewById(R.id.example_right);
		close.setOnClickListener(this);
		headl = (LinearLayout) findViewById(R.id.headl);
		unamel = (LinearLayout) findViewById(R.id.unamel);
		introl = (LinearLayout) findViewById(R.id.introl);
		emaill = (LinearLayout) findViewById(R.id.emaill);
		sexl = (LinearLayout) findViewById(R.id.sexl);
		languagel = (LinearLayout) findViewById(R.id.languagel);
		searchkeyl = (LinearLayout) findViewById(R.id.searchkeyl);
		head = (RoundCornerImageView) findViewById(R.id.head);
		uname = (TextView) findViewById(R.id.uname);
		intro = (TextView) findViewById(R.id.intro);
		email = (TextView) findViewById(R.id.email);
		sex = (TextView) findViewById(R.id.sex);
		language = (TextView) findViewById(R.id.language);
		searchkey = (TextView) findViewById(R.id.searchkey);
	}

	public void getdata() {
		if (Common.USER_INFOR.getUname()!=null) {
			uname.setText(Common.USER_INFOR.getUname());
			unamel.setVisibility(View.VISIBLE);
		}
		if (Common.USER_INFOR.getEmail()!=null) {
			email.setText(Common.USER_INFOR.getEmail());
			emaill.setVisibility(View.VISIBLE);
		}
		if (Common.USER_INFOR.getAvatar_middle()!=null) {
			head.setImageUrl(Common.USER_INFOR.getAvatar_middle());
			headl.setVisibility(View.VISIBLE);
		}

		if (Common.USER_INFOR.getSex()!=null) {
			sex.setText(Common.USER_INFOR.getSex());
			sexl.setVisibility(View.VISIBLE);
		}
		if (Common.USER_INFOR.getIntro()!=null) {
			intro.setText(Common.USER_INFOR.getIntro());
			introl.setVisibility(View.VISIBLE);
		}
		if (Common.USER_INFOR.getLang()!=null) {
			language.setText(Common.USER_INFOR.getLang());
			languagel.setVisibility(View.VISIBLE);
		}
		if (Common.USER_INFOR.getSearch_key()!=null) {
			searchkey.setText(Common.USER_INFOR.getSearch_key());
			searchkeyl.setVisibility(View.VISIBLE);
		}
	}

	public void hideview() {
		headl.setVisibility(View.GONE);
		unamel.setVisibility(View.GONE);
		introl.setVisibility(View.GONE);
		sexl.setVisibility(View.GONE);
		emaill.setVisibility(View.GONE);
		searchkeyl.setVisibility(View.GONE);
		languagel.setVisibility(View.GONE);
	}
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.userinfor_layout);
		if(Common.USER_INFOR!=null)
		{
			findview();
			hideview();
			getdata();
		}
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		if(v==close)
		{
			finish();
		}if(v==qrl)
		{
			UserInfor.HEAD_IC=Common.USER_INFOR.getAvatar_middle();
			UserInfor.UNAME=Common.USER_INFOR.getUname();
			Intent i=new Intent(UserPanl.this,MyQrActivity.class);
			startActivity(i);
		}
	}
}
