/**weibotest
 * 
 *
 *TODO
 */
package com.weibo.card;


/**
 * @作者:陈华清
 * 
 * @版本:1.0
 * @生成时期:2014年7月20日 上午11:39:54
 * @com.api.card
 */
public class UserInfor {
	public static final String TAG = "UserInfor";
	public static String UID = null;
	public static String UNAME = null;
	public static String HEAD_IC = null;
	public static CountInfor COUNT_INFOR = null;
	public static String SEX = null;
	public static String INTRO = null;
	private String uid;
	private String uname;
	private String avatar_middle;
	private String sex;
	private String ctime;
	private String intro;

	public String getSex() {
		return sex;
	}

	public void setSex(String sex) {
		this.sex = sex;
	}

	public String getCtime() {
		return ctime;
	}

	public void setCtime(String ctime) {
		this.ctime = ctime;
	}

	public String getIntro() {
		return intro;
	}

	public void setIntro(String intro) {
		this.intro = intro;
	}

	public String getMedals() {
		return medals;
	}

	public void setMedals(String medals) {
		this.medals = medals;
	}
	public static String getUID() {
		return UID;
	}

	public static void setUID(String uID) {
		UID = uID;
	}

	public static String getUNAME() {
		return UNAME;
	}

	public static void setUNAME(String uNAME) {
		UNAME = uNAME;
	}

	public static String getHEAD_IC() {
		return HEAD_IC;
	}

	public static void setHEAD_IC(String hEAD_IC) {
		HEAD_IC = hEAD_IC;
	}

	public static CountInfor getCOUNT_INFOR() {
		return COUNT_INFOR;
	}

	public static void setCOUNT_INFOR(CountInfor cOUNT_INFOR) {
		COUNT_INFOR = cOUNT_INFOR;
	}

	public static String getSEX() {
		return SEX;
	}

	public static void setSEX(String sEX) {
		SEX = sEX;
	}

	public static String getINTRO() {
		return INTRO;
	}

	public static void setINTRO(String iNTRO) {
		INTRO = iNTRO;
	}

	public String getAvatar_middle() {
		return avatar_middle;
	}

	public void setAvatar_middle(String avatar_middle) {
		this.avatar_middle = avatar_middle;
	}

	public static String getTag() {
		return TAG;
	}
	private String medals;

	public String getUid() {
		return uid;
	}

	public void setUid(String uid) {
		this.uid = uid;
	}

	public String getUname() {
		return uname;
	}

	public void setUname(String uname) {
		this.uname = uname;
	}

	public static String getweibo_count() {
		if (COUNT_INFOR != null) {
			return COUNT_INFOR.getWeibo_count();
		} else {
			return "0";
		}
	}

	public static String getfollowing_count() {
		if (COUNT_INFOR != null) {
			return COUNT_INFOR.getFollowing_count();
		} else {
			return "0";
		}
	}

	public static String getfollower_count() {
		if (COUNT_INFOR != null) {
			return COUNT_INFOR.getFollower_count();
		} else {
			return "0";
		}
	}
}
