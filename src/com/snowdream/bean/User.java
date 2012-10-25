package com.snowdream.bean;

public class User {
	private long uid;
	private String name;
	private short age;
	private String createIp;
	private String lastLogonIp;

	public long getUid() {
		return uid;
	}

	public void setUid(long uid) {
		this.uid = uid;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public short getAge() {
		return age;
	}

	public void setAge(short age) {
		this.age = age;
	}

	public String getCreateIp() {
		return createIp;
	}

	public void setCreateIp(String createIp) {
		this.createIp = createIp;
	}

	public String getLastLogonIp() {
		return lastLogonIp;
	}

	public void setLastLogonIp(String lastLogonIp) {
		this.lastLogonIp = lastLogonIp;
	}

}
