/**weibotest
 * 
 *
 *TODO
 */
package com.weibo.activity;
import com.weibo.R;
import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.io.PushbackInputStream;
import java.io.RandomAccessFile;
import java.net.ServerSocket;
import java.net.Socket;

import com.weibo.utils.CommonUtils;
import com.weibo.utils.FaceConversionUtil;
import com.weibo.utils.StringUtils;
import com.weibo.view.widget.LoadingAlertAnim;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.DocumentsContract;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

/**
 * @作者:陈华清
 * 
 * @版本:1.0
 * @生成时期:2014年8月7日 下午7:56:58
 * @com.api.example.app
 */
public class NearSendMain extends BaseActivity implements OnClickListener {
	ImageView close;
	Button chose;
	EditText geturl;
	private boolean start = true;
	private static final String TAG = "NearSendMain";
	String file;

	public void findview() {
		chose = (Button) findViewById(R.id.chose);
		chose.setOnClickListener(this);
		geturl = (EditText) findViewById(R.id.getdata);
		close = (ImageView) findViewById(R.id.example_right);
		close.setOnClickListener(this);
		geturl.append(FaceConversionUtil.getLocalHostIp());
	}

	static DataInputStream din = null;
	static DataOutputStream dout = null;
	static Socket s = null;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.near_send_main_layout);
		findview();
		WifiManager wifiManager = (WifiManager) getSystemService(Context.WIFI_SERVICE);
		if (!wifiManager.isWifiEnabled()) {
			wifiManager.setWifiEnabled(true);
		} 
		WifiInfo wifiInfo = wifiManager.getConnectionInfo();
		int ipAddress = wifiInfo.getIpAddress();
		String ip = intToIp(ipAddress);
		geturl.setText(ip);
		Intent i = new Intent("com.server.getdate");
		startService(i);
	}
	private String intToIp(int i) {
		return (i & 0xFF) + "." + ((i >> 8) & 0xFF) + "." + ((i >> 16) & 0xFF)
				+ "." + (i >> 24 & 0xFF);
	}
	public void senddata(String r) {
		try {
			s = new Socket(geturl.getText().toString(), 7000);
			File file = new File(r);
			String fileName = file.getName();
			OutputStream out = s.getOutputStream();
			out.write(fileName.getBytes());
			BufferedInputStream input = new BufferedInputStream(
					new FileInputStream(file));
			byte[] buff = new byte[1024 * 1024];
			int len;
			while ((len = input.read(buff)) != -1) {
				out.write(buff, 0, len);
			}
			s.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		if (v == close) {
			finish();
		} else if (v == chose) {
			Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
			intent.setType("*/*");
			intent.addCategory(Intent.CATEGORY_OPENABLE);
			try {
				startActivityForResult(intent, 1);
			} catch (android.content.ActivityNotFoundException ex) {

			}
		}
	}

	Uri uri;
	LoadingAlertAnim loading;
	@SuppressLint("NewApi")
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		switch (requestCode) {
		case 1:
			if (data == null) {
				return;
			} else {
				uri = data.getData();
				try {
					if (DocumentsContract.isDocumentUri(
							getApplicationContext(), uri)) {
						String url = FaceConversionUtil.getPath(
								getApplicationContext(), uri);
						file = url;
						return;
					}
				} catch (NoClassDefFoundError e) {
					e.printStackTrace();
				}
			}
			try {
				String thePath = CommonUtils
						.getAbsolutePathFromNoStandardUri(uri);
				if (StringUtils.isBlank(thePath)) {
					file = CommonUtils.getAbsoluteImagePath(NearSendMain.this,
							uri);
					Log.d(TAG, file);
				} else {
					file = thePath;
					Log.d(TAG, file);
				}
				loading = new LoadingAlertAnim(NearSendMain.this, R.style.MyDialogStyle,"正在发送，请稍后");
				loading.setCanceledOnTouchOutside(false);
				loading.show();
				new AsyncTask<Void, Void, Void>() {
					protected Void doInBackground(Void... params) {
						senddata(file);
						return null;
					}

					@Override
					protected void onPostExecute(Void result) {
						Log.d(TAG, file);
						FaceConversionUtil
								.dispToast(NearSendMain.this, "发送成功!");
						loading.dismiss();
					}
				}.execute(null, null, null);
			} catch (Exception e) {
				Log.d(TAG, e.toString());
			}
			break;
		}
		super.onActivityResult(requestCode, resultCode, data);
	}
}
