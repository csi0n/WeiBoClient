/**weibotest
 * 
 *
 *TODO
 */
package com.weibo.activity;
import com.weibo.R;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.http.message.BasicNameValuePair;
import org.json.JSONObject;
import org.json.JSONTokener;

import com.weibo.utils.ClientUtils;
import com.weibo.utils.FaceConversionUtil;
import com.weibo.utils.RegInfor;
import com.weibo.utils.Utils;
import com.weibo.view.widget.LoadingAlertAnim;

import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.View.OnFocusChangeListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;

/**
 * @作者:陈华清
 * 
 * @版本:1.0
 * @生成时期:2014年7月29日 下午1:35:13
 * @com.example.weibotest
 */
public class RegNewUser extends BaseActivity {
	Button button3, button4;
	EditText edittext3, edittext4, edittext5, reedittext4;
	ImageView clear1, clear2, clear3, clear4;
	RadioGroup group;
	RadioButton radiobutton1, radiobutton2;
	private String sex = "男";
	String result = "";
	LoadingAlertAnim loading;
	private static final String TAG = "RegNewUser.java";
	private static final int regmsg = 0;
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.reg);
		viewfind();
		listener();
	}
	OnCheckedChangeListener mOnCheckedChangeListener = new OnCheckedChangeListener() {
		@Override
		public void onCheckedChanged(RadioGroup group, int checkedId) {
			if (checkedId == radiobutton1.getId()) {
				sex = "男";
			} else if (checkedId == radiobutton2.getId()) {
				sex = "女";
			}
		}
	};

	public boolean emailFormat(String email) {
		boolean tag = true;
		final String pattern1 = "^([a-z0-9A-Z]+[-|\\.]?)+[a-z0-9A-Z]@([a-z0-9A-Z]+(-[a-z0-9A-Z]+)?\\.)+[a-zA-Z]{2,}$";
		final Pattern pattern = Pattern.compile(pattern1);
		final Matcher mat = pattern.matcher(email);
		if (!mat.find()) {
			tag = false;
		}
		return tag;
	}

	void do_Red(final String uname, final String password, final String sex, final String email) {
		new AsyncTask<Void, Void, Void>() {
			protected Void doInBackground(Void... params) {
				try {
					List<BasicNameValuePair> paramss = new ArrayList<BasicNameValuePair>();
					paramss.add(new BasicNameValuePair("uname", uname));
					paramss.add(new BasicNameValuePair("password", password));
					paramss.add(new BasicNameValuePair("sex", sex));
					paramss.add(new BasicNameValuePair("email", email));
					result = ClientUtils.post_str(ClientUtils.BASE_URL + RegNewUser.this.getString(ClientUtils.RegisterNewUser_register), paramss, RegNewUser.this);
				} catch (Exception e) {
					Log.d(TAG, e.toString());
				}
				return null;
			}

			@Override
			protected void onPostExecute(Void qq) {
				try {
					JSONObject person = new JSONObject(result);
					if (person.getString("status").equals("1")) {
						loading.dismiss();
					}
					String mmessage = person.getString("msg");
					if (loading != null) {
						loading.dismiss();
					}
					if (mmessage.equals("注册成功")) {
						RegInfor.USERNAME = email;
						RegInfor.PASSWORD = password;
						finish();
					} else {
						FaceConversionUtil.dispToast(RegNewUser.this, person.getString("msg"));
					}
				} catch (Exception e) {

				}
				Log.d(TAG, "注册任务完成！");
			}
		}.execute(null, null, null);
	}

	public void listener() {
		button3.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				final String uname = edittext3.getText().toString();
				final String passwd = edittext4.getText().toString();
				final String repasswd = reedittext4.getText().toString();
				final String email = edittext5.getText().toString();
				Log.d(TAG, uname + passwd + email);
				if (uname.length() == 0 || passwd.length() == 0 || email.length() == 0 || emailFormat(email) == false) {
					if (uname.length() == 0) {
						edittext3.setError("请输入用户名！");
					}
					if (passwd.length() == 0) {
						edittext4.setError("请输入密码！");
					}
					if (email.length() == 0) {
						edittext5.setError("请输入邮箱！");
					}
					if (emailFormat(email) == false) {
						edittext5.setError("邮箱格式不合法！");
					}
					if (passwd != repasswd) {
						reedittext4.setError("对不起你两次输入的密码不一致!");
					}
				} else {
					loading = new LoadingAlertAnim(RegNewUser.this, R.style.MyDialogStyle, "注册中");
					loading.setCanceledOnTouchOutside(true);
					loading.show();
					// TODO Auto-generated method stub
					do_Red(uname, passwd, sex, email);
				}
			}
		});
		button4.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				finish();
			}
		});
		Utils.getInstance().addActivity(this);
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

	private void textCountSet() {
		int t1 = edittext3.getText().length();
		int t2 = edittext4.getText().length();
		int t3 = reedittext4.getText().length();
		int t4 = edittext5.getText().length();
		if (t1 > 0) {
			if (edittext3.hasFocus()) {
				clear1.setVisibility(View.VISIBLE);
			} else {
				clear1.setVisibility(View.GONE);
			}
		} else {
			clear1.setVisibility(View.GONE);
		}
		if (t2 > 0) {
			if (edittext4.hasFocus()) {
				clear2.setVisibility(View.VISIBLE);
			} else {
				clear2.setVisibility(View.GONE);
			}
		} else {
			clear2.setVisibility(View.GONE);
		}
		if (t3 > 0) {
			if (reedittext4.hasFocus()) {
				clear3.setVisibility(View.VISIBLE);
			} else {
				clear3.setVisibility(View.GONE);
			}
		} else {
			clear3.setVisibility(View.GONE);
		}
		if (t4 > 0) {
			if (edittext5.hasFocus()) {
				clear4.setVisibility(View.VISIBLE);
			} else {
				clear4.setVisibility(View.GONE);
			}
		} else {
			clear4.setVisibility(View.GONE);
		}
		if (t1 > 0 && t2 > 0 && t3 > 0 && t4 > 0) {
			FaceConversionUtil.MygetColor(RegNewUser.this, button3, R.color.white);
		} else {
			FaceConversionUtil.MygetColor(RegNewUser.this, button3, R.color.logincolor_before);
		}
	}
	public void viewfind() {
		clear1 = (ImageView) findViewById(R.id.editclear1);
		clear2 = (ImageView) findViewById(R.id.editclear2);
		clear3 = (ImageView) findViewById(R.id.editclear3);
		clear4 = (ImageView) findViewById(R.id.editclear4);
		button3 = (Button) findViewById(R.id.button1);
		button4 = (Button) findViewById(R.id.button2);
		edittext3 = (EditText) findViewById(R.id.editText3);
		edittext3.addTextChangedListener(watcher);
		edittext3.setOnFocusChangeListener(new OnFocusChangeListener() {
			@Override
			public void onFocusChange(View v, boolean hasFocus) {
				// TODO Auto-generated method stub
				if (hasFocus) {
					if (edittext3.getText().length() > 0) {
						if (clear1.getVisibility() == View.GONE) {
							clear1.setVisibility(View.VISIBLE);
						}
					}
					if (clear2.getVisibility() == View.VISIBLE) {
						clear2.setVisibility(View.GONE);
					}
					if (clear3.getVisibility() == View.VISIBLE) {
						clear3.setVisibility(View.GONE);
					}
					if (clear4.getVisibility() == View.VISIBLE) {
						clear4.setVisibility(View.VISIBLE);
					}
				}

			}
		});
		edittext4 = (EditText) findViewById(R.id.editText4);
		edittext4.addTextChangedListener(watcher);
		edittext4.setOnFocusChangeListener(new OnFocusChangeListener() {
			@Override
			public void onFocusChange(View v, boolean hasFocus) {
				// TODO Auto-generated method stub
				if (edittext4.getText().length() > 0) {
					if (clear2.getVisibility() == View.GONE) {
						clear2.setVisibility(View.VISIBLE);
					}

				}
				if (clear1.getVisibility() == View.VISIBLE) {
					clear1.setVisibility(View.GONE);
				}
				if (clear3.getVisibility() == View.VISIBLE) {
					clear3.setVisibility(View.GONE);
				}
				if (clear4.getVisibility() == View.VISIBLE) {
					clear4.setVisibility(View.VISIBLE);
				}
			}
		});
		reedittext4 = (EditText) findViewById(R.id.reeditText4);
		reedittext4.addTextChangedListener(watcher);
		reedittext4.setOnFocusChangeListener(new OnFocusChangeListener() {
			@Override
			public void onFocusChange(View v, boolean hasFocus) {
				// TODO Auto-generated method stub
				if (reedittext4.getText().length() > 0) {
					if (clear3.getVisibility() == View.GONE) {
						clear3.setVisibility(View.VISIBLE);
					}
				}
				if (clear1.getVisibility() == View.VISIBLE) {
					clear1.setVisibility(View.GONE);
				}
				if (clear2.getVisibility() == View.VISIBLE) {
					clear2.setVisibility(View.GONE);
				}
				if (clear4.getVisibility() == View.VISIBLE) {
					clear4.setVisibility(View.VISIBLE);
				}
			}
		});
		edittext5 = (EditText) findViewById(R.id.editText5);
		edittext5.addTextChangedListener(watcher);
		edittext5.setOnFocusChangeListener(new OnFocusChangeListener() {

			@Override
			public void onFocusChange(View v, boolean hasFocus) {
				// TODO Auto-generated method stub
				if (edittext5.getText().length() > 0) {
					if (clear4.getVisibility() == View.GONE) {
						clear4.setVisibility(View.VISIBLE);
					}

				}
				if (clear1.getVisibility() == View.VISIBLE) {
					clear1.setVisibility(View.GONE);
				}
				if (clear2.getVisibility() == View.VISIBLE) {
					clear2.setVisibility(View.GONE);
				}
				if (clear3.getVisibility() == View.VISIBLE) {
					clear3.setVisibility(View.VISIBLE);
				}
			}
		});
		group = (RadioGroup) findViewById(R.id.radioGroup);
		radiobutton1 = (RadioButton) findViewById(R.id.radioButton1);
		radiobutton2 = (RadioButton) findViewById(R.id.radioButton2);
		group.setOnCheckedChangeListener(mOnCheckedChangeListener);
		clear1.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				edittext3.setText("");
			}
		});
		clear2.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				edittext4.setText("");
			}
		});
		clear3.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				reedittext4.setText("");
			}
		});
		clear4.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				edittext5.setText("");
			}
		});
	}
}
