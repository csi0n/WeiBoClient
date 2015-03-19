package com.weibo.utils;
import com.weibo.R;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
/**
 * @作者:陈华清
 *
 * @版本:1.0
 * @生成时期:2014年7月4日 下午11:49:27
 * @com.server
 */
/**
 * 类说明： 对话框弹出帮助类
 */
public class DialogUtils {
	/**
	 * 弹出询问窗口
	 */
	
	public static void dialogBuilder(Activity instance, String title,
			String message, final DialogCallBack callBack) {
		LayoutInflater inflater = instance.getLayoutInflater();
		View layout = inflater.inflate(R.layout.custdialog_layout,null);
		   final Dialog dialog=new AlertDialog.Builder(instance).create();
		   dialog.show();
		   dialog.getWindow().setContentView(layout);
		   TextView tit=(TextView)layout.findViewById(R.id.title);
		   tit.setText(title);
		   TextView con=(TextView)layout.findViewById(R.id.content);
		   con.setText(message);
		   Button sure=(Button)layout.findViewById(R.id.button1);
		   sure.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				dialog.dismiss();
				callBack.callBack();
			}
		});
		   Button cancel=(Button)layout.findViewById(R.id.button2);
		   cancel.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				dialog.dismiss();
			}
		});
	}
	public static void dialogBuilderclose(final Activity instance, String title,
			String message, final DialogCallBack callBack) {
		LayoutInflater inflater = instance.getLayoutInflater();
		View layout = inflater.inflate(R.layout.custdialog_layout,null);
		   final Dialog dialog=new AlertDialog.Builder(instance).create();
		   dialog.show();
		   dialog.getWindow().setContentView(layout);
		   TextView tit=(TextView)layout.findViewById(R.id.title);
		   tit.setText(title);
		   TextView con=(TextView)layout.findViewById(R.id.content);
		   con.setText(message);
		   Button sure=(Button)layout.findViewById(R.id.button1);
		   sure.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				dialog.dismiss();
				callBack.callBack();
			}
		});
		   Button cancel=(Button)layout.findViewById(R.id.button2);
		   cancel.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				dialog.dismiss();
				instance.finish();
			}
		});
	}
	public interface DialogCallBack {
		public void callBack();
	}
}