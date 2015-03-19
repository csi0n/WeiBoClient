package com.weibo.server;
import java.io.*;
import java.net.*;

import android.app.NotificationManager;
import android.util.Log;
public class WebThread extends Thread {
	private static final String TAG = "WebThread";
	Socket socket;
	String filePath;
	private NotificationManager mNotificationManager;
	public WebThread(Socket cl, String filePathVal) {
		socket = cl;
		filePath = filePathVal;
	}
	public void privideService() throws IOException {
		InputStream input = socket.getInputStream();
		byte[] fileNameByte = new byte[1024];
		int fileNameLenth;
		fileNameLenth = input.read(fileNameByte);
		String fileName = new String(fileNameByte, 0, fileNameLenth);
		Log.d(TAG, "接收到得文件名"+fileName);
		BufferedOutputStream output = new BufferedOutputStream(new FileOutputStream(new File(filePath,fileName)));
		byte[] buff = new byte[1024 * 1024 * 5];
		int len;
		while ((len = input.read(buff)) != -1) {
			output.write(buff, 0, len);
		}
		output.close();
		socket.close();
		ServiceScoket.noti("接收到了新文件", "文件" + fileName + "保存在/sdcard/thinksns目录下");
	}
	public void run() {
		try {
			privideService();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}
}
