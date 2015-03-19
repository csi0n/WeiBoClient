package com.weibo.activity.selectpop;
import java.io.File;
import com.weibo.activity.BaseActivity;
import com.weibo.utils.FaceConversionUtil;
import com.weibo.utils.Utils;

import android.annotation.SuppressLint;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import com.weibo.R;
public class SelectPicPopup extends BaseActivity implements OnClickListener {
	private Button btn_take_photo, btn_pick_photo, btn_cancel;
	private Intent intent;
	private static final String TAG = "SelectPicPopup";
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.send_new_weibo_select_pic);
		intent = getIntent();
		btn_take_photo = (Button) this.findViewById(R.id.btn_take_photo);
		btn_pick_photo = (Button) this.findViewById(R.id.btn_pick_photo);
		btn_cancel = (Button) this.findViewById(R.id.btn_cancel);
		btn_cancel.setOnClickListener(this);
		btn_pick_photo.setOnClickListener(this);
		btn_take_photo.setOnClickListener(this);
		Utils.getInstance().addActivity(this);
	}

	@Override
	public boolean onTouchEvent(MotionEvent event) {
		finish();
		return true;
	}

	@SuppressLint("NewApi")
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		if (data == null) {
			return;
		} else {
			switch (requestCode) {
				case 1 :
					File temp = new File("/sdcard/camera_raw.jpg");
					startPhotoZoom(Uri.fromFile(temp));
					break;
				case SELECT_PIC_KITKAT :
					Uri uri = data.getData();
					try {
						if (DocumentsContract.isDocumentUri(SelectPicPopup.this, uri)) {
							String url = FaceConversionUtil.getPath(getApplicationContext(), uri);
							Log.d(TAG, url);
							startPhotoZoom(Uri.parse(url));
						} else {
							Log.d(TAG, "222" + uri);
							startPhotoZoom(uri);
						}
					} catch (NoClassDefFoundError e) {
						startPhotoZoom(uri);
					}
					break;
				case 200 :
					setResult(2, data);
					finish();
					break;
			}
		}
	}

	private static final int SELECT_PIC_KITKAT = 0x00000;
	public void onClick(View v) {
		switch (v.getId()) {
			case R.id.btn_take_photo :
				try {
					Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
					intent.setAction(MediaStore.ACTION_IMAGE_CAPTURE);
					intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(new File("/sdcard/camera_raw.jpg")));
					startActivityForResult(intent, 1);
				} catch (Exception e) {
					e.printStackTrace();
				}
				break;
			case R.id.btn_pick_photo :
				try {
					Intent intent = new Intent(Intent.ACTION_GET_CONTENT);// ACTION_OPEN_DOCUMENT
					intent.addCategory(Intent.CATEGORY_OPENABLE);
					intent.setType("image/jpeg");
					startActivityForResult(intent, SELECT_PIC_KITKAT);
				} catch (ActivityNotFoundException e) {
				}
				break;
			case R.id.btn_cancel :
				finish();
				break;
			default :
				break;
		}
	}
	public void startPhotoZoom(Uri uri) {
		Intent intent = new Intent("com.android.camera.action.CROP");
		intent.setDataAndType(uri, "image/jpeg");
		intent.putExtra("crop", "true");
		intent.putExtra("aspectX", 1);
		intent.putExtra("aspectY", 1);
		intent.putExtra("outputX", 100);
		intent.putExtra("outputY", 100);
		File tempFile = new File("/sdcard/camera.jpg");
		intent.putExtra("output", Uri.fromFile(tempFile));
		intent.putExtra("outputFormat", "JPEG");
		intent.putExtra("return-data", true);
		startActivityForResult(intent, 200);
	}

}
