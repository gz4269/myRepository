package com.example.utils;

import cn.bmob.v3.BmobObject;

@SuppressWarnings("serial")
public class Lost extends BmobObject {

	private String title;// 标题
	private String describe;// 描述
	private String phone;// 联系手机
	private String name;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getDescribe() {
		return describe;
	}

	public void setDescribe(String describe) {
		this.describe = describe;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

}