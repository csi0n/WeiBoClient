/**weibotest
 * 
 *
 *TODO
 */
package com.weibo.utils;
import java.util.ArrayList;
import java.util.List;
import com.weibo.card.Attach;

/**
 * @作者:陈华清
 * 
 * @版本:1.0
 * @生成时期:2014年7月31日 上午10:58:00
 * @com.api.card
 */
public class ImageArrayList {
	public static List<Attach> ATTACH = null;
	public static int PICCURRENT = 0;
	public static ArrayList<String> getList() {
		ArrayList<String> a = new ArrayList<String>();
		try {
			for (int i = 0; i <ATTACH.size(); i++) {
				a.add(ATTACH.get(i).getAttach_big());
			}
		} catch (Exception e) {

		}
		return a;
	}
}
