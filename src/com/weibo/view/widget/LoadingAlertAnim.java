package com.weibo.view.widget;
import com.weibo.R;

import android.app.AlertDialog;
import android.content.Context;
import android.os.Bundle;
import android.widget.TextView;
public class LoadingAlertAnim extends AlertDialog {
	TextView textloading;
	private String t = "";
	protected LoadingAlertAnim(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
	}
	public LoadingAlertAnim(Context context, int theme, String t) {
		// TODO Auto-generated constructor stub
		super(context, theme);
		this.t = t;
	}
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.loading_data_layout);
		textloading = (TextView) findViewById(R.id.text);
		textloading.setText(t);
	}
}
