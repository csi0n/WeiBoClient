package com.weibo.utils;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

import android.app.ActivityManager;
import android.content.Context;

/**
 * @作者:陈华清
 *
 * @版本:1.0
 * @生成时期:2014年8月10日 下午11:04:32
 * @com.api
 */
public class memInfo {
	public static long getmem_UNUSED(Context mContext) {
		long MEM_UNUSED;
		ActivityManager am = (ActivityManager) mContext
				.getSystemService(Context.ACTIVITY_SERVICE);
		ActivityManager.MemoryInfo mi = new ActivityManager.MemoryInfo();
		am.getMemoryInfo(mi);
		MEM_UNUSED = mi.availMem / 1024;
		return MEM_UNUSED;
	}
	public static long getmem_TOLAL() {
		long mTotal;
		String path = "/proc/meminfo";
		String content = null;
		BufferedReader br = null;
		try {
			br = new BufferedReader(new FileReader(path), 8);
			String line;
			if ((line = br.readLine()) != null) {
				content = line;
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (br != null) {
				try {
					br.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		int begin = content.indexOf(':');
		int end = content.indexOf('k');
		content = content.substring(begin + 1, end).trim();
		mTotal = Integer.parseInt(content);
		return mTotal;
	}
}
