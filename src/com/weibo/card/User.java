package com.weibo.card;

public class User {
	private String uid;
	private String login;
	private String login_salt;
	private String uname;
	private String email;
	private String sex;
	private String location;
	private String is_audit;
	private String is_activte;
	private String is_init;
	private String ctime;
	private String identity;
	private String lang;
	private String reg_ip;
	private String is_del;
	private String intro;
	private String avatar_original;
	private String avatar_middle;
	private String avatar_small;
	private String avatar_big;
	private String search_key;
	private CountInfor count_info;
	private FollowState follow_state;
	public String getLogin_salt() {
		return login_salt;
	}

	public void setLogin_salt(String login_salt) {
		this.login_salt = login_salt;
	}

	public String getIs_audit() {
		return is_audit;
	}

	public void setIs_audit(String is_audit) {
		this.is_audit = is_audit;
	}

	public String getIs_activte() {
		return is_activte;
	}

	public void setIs_activte(String is_activte) {
		this.is_activte = is_activte;
	}

	public String getIs_init() {
		return is_init;
	}

	public void setIs_init(String is_init) {
		this.is_init = is_init;
	}

	public String getCtime() {
		return ctime;
	}

	public void setCtime(String ctime) {
		this.ctime = ctime;
	}

	public String getIdentity() {
		return identity;
	}

	public void setIdentity(String identity) {
		this.identity = identity;
	}

	public String getIs_del() {
		return is_del;
	}

	public void setIs_del(String is_del) {
		this.is_del = is_del;
	}

	public String getIntro() {
		return intro;
	}

	public void setIntro(String intro) {
		this.intro = intro;
	}

	public CountInfor getCount_info() {
		return count_info;
	}

	public void setCount_info(CountInfor count_info) {
		this.count_info = count_info;
	}

	public FollowState getFollow_status() {
		return follow_state;
	}

	public void setFollow_status(FollowState follow_status) {
		this.follow_state = follow_status;
	}

	public String getAvatar_original() {
		return avatar_original;
	}

	public void setAvatar_original(String avatar_original) {
		this.avatar_original = avatar_original;
	}

	public String getAvatar_big() {
		return avatar_big;
	}

	public void setAvatar_big(String avatar_big) {
		this.avatar_big = avatar_big;
	}

	public String getAvatar_middle() {
		return avatar_middle;
	}

	public void setAvatar_middle(String avatar_middle) {
		this.avatar_middle = avatar_middle;
	}

	public String getAvatar_small() {
		return avatar_small;
	}

	public void setAvatar_small(String avatar_small) {
		this.avatar_small = avatar_small;
	}

	public String getUid() {
		return uid;
	}

	public void setUid(String uid) {
		this.uid = uid;
	}

	public String getLogin() {
		return login;
	}

	public void setLogin(String login) {
		this.login = login;
	}

	public String getUname() {
		return uname;
	}

	public void setUname(String uname) {
		this.uname = uname;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getSex() {
		return sex;
	}

	public void setSex(String sex) {
		this.sex = sex;
	}

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public String getReg_ip() {
		return reg_ip;
	}

	public void setReg_ip(String reg_ip) {
		this.reg_ip = reg_ip;
	}

	public String getLang() {
		return lang;
	}

	public void setLang(String lang) {
		this.lang = lang;
	}

	public String getSearch_key() {
		return search_key;
	}

	public void setSearch_key(String search_key) {
		this.search_key = search_key;
	}
}
