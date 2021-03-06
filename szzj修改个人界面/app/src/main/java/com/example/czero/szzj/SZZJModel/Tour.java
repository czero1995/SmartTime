package com.example.czero.szzj.SZZJModel;

import cn.bmob.v3.BmobObject;

/**
 * 二手交易实体类
 * @date 2014-9-15
 * @author Stone
 */
@SuppressWarnings("serial")
public class Tour extends BmobObject {


	private String name = ""; // 商品名称
	private String type = ""; // 类型
	private String content = ""; // 描述
	private String phone = "";
	private String tourtime="";

	public void setTourtime(String tourtime) {
		this.tourtime = tourtime;
	}

	public String getTourtime() {

		return tourtime;
	}

	public void setTime(String time) {
		this.time = time;
	}

	public String getTime() {

		return time;
	}

	private String time;// 联系电话

	public String getContent() {
		return content;
	}



	public void setContent(String content) {
		this.content = content;
	}

	public String getType() {
		return type;
	}


	public String getPhone() {
		return phone;
	}


	public void setName(String name) {
		this.name = name;
	}

	public String getName() {
		return name;

	}

	public void setType(String type) {
		this.type = type;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

}
