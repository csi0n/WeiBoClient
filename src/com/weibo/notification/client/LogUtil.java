package com.weibo.notification.client;
public class LogUtil {
    @SuppressWarnings("unchecked")
    public static String makeLogTag(Class cls) {
        return "Androidpn_" + cls.getSimpleName();
    }

}
