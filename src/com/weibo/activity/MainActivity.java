package com.weibo.activity;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.message.BasicNameValuePair;
import org.json.JSONObject;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnFocusChangeListener;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.weibo.application.*;
import com.weibo.utils.ACache;
import com.weibo.utils.ClientUtils;
import com.weibo.utils.DialogUtils;
import com.weibo.utils.DialogUtils.DialogCallBack;
import com.weibo.utils.FaceConversionUtil;
import com.weibo.utils.RegInfor;
import com.weibo.utils.Utils;
import com.weibo.view.widget.LoadingAlertAnim;
import com.weibo.view.widget.RoundCornerImageView;
import com.weibo.R;
/**
 * 
 * 主登陆类
 * 
 */
@SuppressLint("NewApi")
public class MainActivity extends BaseActivity implements OnClickListener {
	EditText edittext1, edittext2, edittext3, edittext4, edittext5;
	Button button1, button2, button3, button4;
	CheckBox checkbox1, checkbox2;
	LoadingAlertAnim loading;
	LinearLayout user;
	RadioGroup group;
	RoundCornerImageView image;
	ACache mcache;
	LayoutInflater li;
	View view;
	AlertDialog dialog0;
	TextView textview2;
	ImageView editclear1, editclear2, close;
	private static final String TAG = "MainActivity";
	String data = "";
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK) {
			DialogUtils.dialogBuilder(MainActivity.this, "提示", "确定要退出？", new DialogCallBack() {
				@Override
				public void callBack() {
					Utils.getInstance().exit(MainActivity.this);
				}
			});
		}
		return super.onKeyDown(keyCode, event);
	}
	public void findview() {
		textview2 = (TextView) findViewById(R.id.textView2);
		textview2.setOnClickListener(this);
		edittext1 = (EditText) findViewById(R.id.editText1);
		edittext2 = (EditText) findViewById(R.id.editText2);
		editclear1 = (ImageView) findViewById(R.id.editclear1);
		close = (ImageView) findViewById(R.id.close);
		close.setOnClickListener(this);
		editclear1.setOnClickListener(this);
		editclear2 = (ImageView) findViewById(R.id.editclear2);
		editclear2.setOnClickListener(this);
		button1 = (Button) findViewById(R.id.button1);
		button1.setOnClickListener(this);
		button2 = (Button) findViewById(R.id.button2);
		image = (RoundCornerImageView) findViewById(R.id.image);
		user = (LinearLayout) findViewById(R.id.user);
	}
	public void do_Login(final String username, final String password) {
		new AsyncTask<Void, Void, Void>() {
			@Override
			protected Void doInBackground(Void... arg0) {
				// TODO Auto-generated method stub
				List<BasicNameValuePair> params = new ArrayList<BasicNameValuePair>();
				params.add(new BasicNameValuePair("username", username));
				params.add(new BasicNameValuePair("password", password));
				data = ClientUtils.post_str(ClientUtils.BASE_URL + MainActivity.this.getString(ClientUtils.UserLogin_login), params, MainActivity.this);
				return null;
			}
			@Override
			protected void onPostExecute(Void result) {
				// TODO Auto-generated method stub
				try {
					if (data.indexOf("oauth_token") > 0) {
						mcache.put("username", username);
						mcache.put("password", password);
						JSONObject person = new JSONObject(data);
						String oauth_token = person.getString("oauth_token");
						String oauth_token_secret = person.getString("oauth_token_secret");
						String Uid = person.getString("uid");
						((Mykey) getApplication()).setOauth_token(oauth_token);
						((Mykey) getApplication()).setOauth_token_secret(oauth_token_secret);
						((Mykey) getApplication()).setUid(Uid);
						if (loading != null) {
							loading.dismiss();
						}
						Intent intent1 = new Intent(MainActivity.this, WeiBoMainTab.class);
						MainActivity.this.startActivity(intent1);
					} else {
						loading.dismiss();
						FaceConversionUtil.dispToast(MainActivity.this, "验证失败!");
					}
				} catch (Exception e) {
					// TODO Auto-generated catch block
					Log.d(TAG, e.toString());
					loading.dismiss();
					mcache.clear();
					FaceConversionUtil.dispToast(MainActivity.this, "发成未知错误!!");
				}
			}
		}.execute(null, null, null);
	}
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		findview();
		Animation anim = AnimationUtils.loadAnimation(MainActivity.this, R.anim.login_anim);
		anim.setFillAfter(true);
		user.startAnimation(anim);
		mcache = ACache.get(this);
		if (mcache.FindAsString("username")) {
			edittext1.setText(mcache.getAsString("username"));
			edittext1.setCompoundDrawablesWithIntrinsicBounds(R.drawable.login_user_hightlighted, 0, 0, 0);
		}
		if (mcache.FindAsString("password")) {
			edittext2.setText(mcache.getAsString("password"));
		}
		if (mcache.FindAsString("touxiang")) {
			image.setImageUrl(mcache.getAsString("touxiang"));
		}
		TextWatcher watcher = new TextWatcher() {
			public void onTextChanged(CharSequence s, int start, int before, int count) {
				textCountSet();
			}

			public void beforeTextChanged(CharSequence s, int start, int count, int after) {
				textCountSet();
			}
			@Override
			public void afterTextChanged(Editable s) {
				textCountSet();
			}
		};
		edittext1.addTextChangedListener(watcher);
		edittext2.addTextChangedListener(watcher);
		edittext1.setOnFocusChangeListener(new OnFocusChangeListener() {
			@Override
			public void onFocusChange(View v, boolean hasFocus) {
				// TODO Auto-generated method stub
				if (hasFocus) {
					if (edittext1.getText().length() > 0) {
						if (editclear1.getVisibility() == View.GONE) {
							editclear1.setVisibility(View.VISIBLE);
						}
					}
					if (editclear2.getVisibility() == View.VISIBLE) {
						editclear2.setVisibility(View.GONE);
					}
				}
			}
		});
		edittext2.setOnFocusChangeListener(new OnFocusChangeListener() {
			@Override
			public void onFocusChange(View v, boolean hasFocus) {
				// TODO Auto-generated method stub
				if (hasFocus) {
					if (edittext2.getText().length() > 0) {
						if (editclear2.getVisibility() == View.GONE) {
							editclear2.setVisibility(View.VISIBLE);
						}
					}
					if (editclear1.getVisibility() == View.VISIBLE) {
						editclear1.setVisibility(View.GONE);
					}
				}
			}
		});
	}
	private void textCountSet() {
		int currentLength1 = edittext1.getText().toString().length();
		int currentLength2 = edittext2.getText().toString().length();
		if (currentLength1 > 0) {
			edittext1.setCompoundDrawablesWithIntrinsicBounds(R.drawable.login_user_hightlighted, 0, 0, 0);
			if (edittext1.hasFocus()) {
				editclear1.setVisibility(View.VISIBLE);
			} else {
				editclear1.setVisibility(View.GONE);
			}
		} else {
			editclear1.setVisibility(View.GONE);
			edittext1.setCompoundDrawablesWithIntrinsicBounds(R.drawable.login_user, 0, 0, 0);
		}
		if (edittext1.hasFocus() && edittext1.getText().length() > 0) {
			if (editclear1.getVisibility() == View.GONE) {
				editclear1.setVisibility(View.VISIBLE);
			}
		} else {
			if (editclear1.getVisibility() == View.VISIBLE) {
				editclear1.setVisibility(View.GONE);
			}
		}
		if (edittext2.hasFocus() && edittext2.getText().length() > 0) {
			if (editclear2.getVisibility() == View.GONE) {
				editclear2.setVisibility(View.VISIBLE);
			}
		} else {
			if (editclear2.getVisibility() == View.VISIBLE) {
				editclear2.setVisibility(View.GONE);
			}
		}
		if (currentLength2 > 0) {
			edittext2.setCompoundDrawablesWithIntrinsicBounds(R.drawable.login_key_hightlighted, 0, 0, 0);
			if (edittext2.hasFocus()) {
				editclear2.setVisibility(View.VISIBLE);
			} else {
				editclear2.setVisibility(View.GONE);
			}
		} else {
			editclear2.setVisibility(View.GONE);
			edittext2.setCompoundDrawablesWithIntrinsicBounds(R.drawable.login_key, 0, 0, 0);
		}
		if (currentLength2 > 0 && currentLength1 > 0) {
			FaceConversionUtil.MygetColor(MainActivity.this, button1, R.color.white);
		} else {
			FaceConversionUtil.MygetColor(MainActivity.this, button1, R.color.logincolor_before);
		}
	}
	@Override
	protected void onResume() {
		super.onResume();
		if (!RegInfor.USERNAME.endsWith("thinksns")) {
			edittext1.setText(RegInfor.USERNAME);
			edittext2.setText(RegInfor.PASSWORD);
		}
	}
	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		if (v == close) {
			DialogUtils.dialogBuilder(MainActivity.this, "提示", "确定要退出？", new DialogCallBack() {
				@Override
				public void callBack() {
					Utils.getInstance().exit(MainActivity.this);
				}
			});
		} else if (v == button1) {
			if (edittext1.getText().length() > 0 && edittext2.getText().length() > 0) {
				loading = new LoadingAlertAnim(MainActivity.this, R.style.MyDialogStyle, "登陆中");
				loading.setCanceledOnTouchOutside(false);
				loading.show();
				Log.e(TAG, "用户名:" + edittext1.getText().toString() + "密码:" + edittext2.getText().toString());
				do_Login(edittext1.getText().toString(), edittext2.getText().toString());
			} else {
				FaceConversionUtil.dispToast(MainActivity.this, "账号密码啊不能为空！");
			}
		} else if (v == editclear1) {
			edittext1.setText("");
		} else if (v == editclear2) {
			edittext2.setText("");
		} else if (v == textview2) {
			Intent i = new Intent(MainActivity.this, RegNewUser.class);
			MainActivity.this.startActivity(i);
		}
	}
}
