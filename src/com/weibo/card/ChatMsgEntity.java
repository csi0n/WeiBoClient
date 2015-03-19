package com.weibo.card;
/**
 * @作者:陈华清
 *
 * @版本:1.0
 * @生成时期:2014年8月14日 下午11:47:27
 * @com.server
 */
public class ChatMsgEntity {

	private String name;

	private String date;

	private String text;
	private String from_uid;

	public String getFrom_uid() {
		return from_uid;
	}

	public void setFrom_uid(String from_uid) {
		this.from_uid = from_uid;
	}

	private boolean isComMeg = true;
	private String faceurl;

	public String getFaceurl() {
		return faceurl;
	}

	public void setFaceurl(String faceurl) {
		this.faceurl = faceurl;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public boolean getMsgType() {
		return isComMeg;
	}

	public void setMsgType(boolean isComMsg) {
		isComMeg = isComMsg;
	}

	public ChatMsgEntity() {
	}

	public ChatMsgEntity(String name, String date, String text,
			boolean isComMsg, String faceurl, String uid) {
		super();
		this.name = name;
		this.date = date;
		this.text = text;
		this.isComMeg = isComMsg;
		this.faceurl = faceurl;
		this.from_uid = uid;
	}

}
