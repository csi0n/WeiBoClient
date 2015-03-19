package com.weibo.activity;
import java.util.Hashtable;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;
import com.weibo.application.Mykey;
import com.weibo.card.UserInfor;
import com.weibo.utils.Utils;
import com.weibo.view.widget.CircleImageView;

import android.annotation.SuppressLint;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.TextView;

import com.weibo.R;
/**
 * @作者:陈华清
 * 
 * @版本:1.0
 * @生成时期:2014年8月6日 下午7:29:31
 * @com.api.example.app
 */
public class MyQrActivity extends BaseActivity implements OnClickListener {
	ImageView myQr, close;
	CircleImageView head;
	TextView uname;
	private static final String TAG = "MyQrActivity.JAVA";
	public void findview() {
		myQr = (ImageView) findViewById(R.id.myqr);
		close = (ImageView) findViewById(R.id.example_right);
		close.setOnClickListener(this);
		head = (CircleImageView) findViewById(R.id.circleImageView1);
		uname = (TextView) findViewById(R.id.uname);
		if (UserInfor.HEAD_IC != null) {
			head.setImageUrl(UserInfor.HEAD_IC);
		}
		if (UserInfor.UNAME != null) {
			uname.setText(UserInfor.UNAME);
		}
	}
	@SuppressLint("NewApi")
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.myqr_main_layout);
		findview();
		myQr.setImageBitmap(Utils.createImage(getWindowManager().getDefaultDisplay().getWidth(),
				getWindowManager().getDefaultDisplay().getWidth(),((Mykey) getApplication()).getUname()));
	}
	private void createImage(int QR_WIDTH, int QR_HEIGHT) {
		try {
			// 需要引入core包
			QRCodeWriter writer = new QRCodeWriter();
			String text = ((Mykey) getApplication()).getUname();
			Log.i(TAG, "生成的文本：" + text);
			if (text == null || "".equals(text) || text.length() < 1) {
				return;
			}
			// 把输入的文本转为二维码
			BitMatrix martix = writer.encode(text, BarcodeFormat.QR_CODE,
					QR_WIDTH, QR_HEIGHT);
			Log.d(TAG, "w:" + martix.getWidth() + "h:" + martix.getHeight());
			Hashtable<EncodeHintType, String> hints = new Hashtable<EncodeHintType, String>();
			hints.put(EncodeHintType.CHARACTER_SET, "utf-8");
			BitMatrix bitMatrix = new QRCodeWriter().encode(text,
					BarcodeFormat.QR_CODE, QR_WIDTH, QR_HEIGHT, hints);
			int[] pixels = new int[QR_WIDTH * QR_HEIGHT];
			for (int y = 0; y < QR_HEIGHT; y++) {
				for (int x = 0; x < QR_WIDTH; x++) {
					if (bitMatrix.get(x, y)) {
						pixels[y * QR_WIDTH + x] = 0xff000000;
					} else {
						pixels[y * QR_WIDTH + x] = 0xffffffff;
					}

				}
			}

			Bitmap bitmap = Bitmap.createBitmap(QR_WIDTH, QR_HEIGHT,
					Bitmap.Config.ARGB_8888);

			bitmap.setPixels(pixels, 0, QR_WIDTH, 0, 0, QR_WIDTH, QR_HEIGHT);
			myQr.setImageBitmap(bitmap);

		} catch (WriterException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		if (v == close) {
			MyQrActivity.this.finish();
		}
	}
}
