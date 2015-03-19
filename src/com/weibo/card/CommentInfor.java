package com.weibo.card;

public class CommentInfor {
private String comment_id;
private String app;
private String table;
private String row_id;
private String app_uid;
private String uid;
private String content;
private String to_comment_id;
private String to_uid;
private String data;
private String ctime;
private String is_del;
private String client_type;
private String is_audit;
private String storey;
private String app_detail_url;
private String app_detail_summary;
private User user_info;
private WeiBoCard sourceInfo;
public WeiBoCard getSourceInfo() {
	return sourceInfo;
}
public void setSourceInfo(WeiBoCard sourceInfo) {
	this.sourceInfo = sourceInfo;
}
public String getApp_detail_url() {
	return app_detail_url;
}
public void setApp_detail_url(String app_detail_url) {
	this.app_detail_url = app_detail_url;
}
public String getApp_detail_summary() {
	return app_detail_summary;
}
public void setApp_detail_summary(String app_detail_summary) {
	this.app_detail_summary = app_detail_summary;
}
public String getComment_id() {
	return comment_id;
}
public void setComment_id(String comment_id) {
	this.comment_id = comment_id;
}
public String getApp() {
	return app;
}
public void setApp(String app) {
	this.app = app;
}
public String getTable() {
	return table;
}
public void setTable(String table) {
	this.table = table;
}
public String getRow_id() {
	return row_id;
}
public void setRow_id(String row_id) {
	this.row_id = row_id;
}
public String getApp_uid() {
	return app_uid;
}
public void setApp_uid(String app_uid) {
	this.app_uid = app_uid;
}
public String getUid() {
	return uid;
}
public void setUid(String uid) {
	this.uid = uid;
}
public String getContent() {
	return content;
}
public void setContent(String content) {
	this.content = content;
}
public String getTo_comment_id() {
	return to_comment_id;
}
public void setTo_comment_id(String to_comment_id) {
	this.to_comment_id = to_comment_id;
}
public String getTo_uid() {
	return to_uid;
}
public void setTo_uid(String to_uid) {
	this.to_uid = to_uid;
}
public String getData() {
	return data;
}
public void setData(String data) {
	this.data = data;
}
public String getCtime() {
	return ctime;
}
public void setCtime(String ctime) {
	this.ctime = ctime;
}
public String getIs_del() {
	return is_del;
}
public void setIs_del(String is_del) {
	this.is_del = is_del;
}
public String getClient_type() {
	return client_type;
}
public void setClient_type(String client_type) {
	this.client_type = client_type;
}
public String getIs_audit() {
	return is_audit;
}
public void setIs_audit(String is_audit) {
	this.is_audit = is_audit;
}
public String getStorey() {
	return storey;
}
public void setStorey(String storey) {
	this.storey = storey;
}
public User getUser_info() {
	return user_info;
}
public void setUser_info(User user_info) {
	this.user_info = user_info;
}
}
