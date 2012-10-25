package com.snowdream.bean;

import java.util.Date;

/**
 * Communicate
 * 
 * @author LUOWEN
 * 
 */
public class Discuss {
	// message index
	private long id;
	// the user who sent the msg
	private User fromUser;
	// content
	private String content;
	// create ip
	private String ip;
	// create time
	private Date dttm;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public User getFromUser() {
		return fromUser;
	}

	public void setFromUser(User fromUser) {
		this.fromUser = fromUser;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public Date getDttm() {
		return dttm;
	}

	public void setDttm(Date dttm) {
		this.dttm = dttm;
	}

	public String getIp() {
		return ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}

}
