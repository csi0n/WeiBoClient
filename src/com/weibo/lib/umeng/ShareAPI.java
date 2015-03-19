/**weibotest
 * 
 *
 *TODO
 */
package com.weibo.lib.umeng;
import android.app.Activity;

import com.umeng.socialize.controller.UMServiceFactory;
import com.umeng.socialize.controller.UMSocialService;
import com.umeng.socialize.sso.QZoneSsoHandler;

/**
 * @作者:陈华清
 * 
 * @版本:1.0
 * @生成时期:2014年7月25日 下午4:36:34
 * @com.api
 */
public class ShareAPI {
	public static UMSocialService start(String content, Activity a) {
		QZoneSsoHandler qZoneSsoHandler = new QZoneSsoHandler(a,
				"100424468", "c7394704798a158208a74ab60104f0ba");
		qZoneSsoHandler.addToSocialSDK();
		final UMSocialService mController = UMServiceFactory
				.getUMSocialService("com.umeng.share");
		// 设置分享内容
		mController.setShareContent(content);
		return mController;
	}

}
