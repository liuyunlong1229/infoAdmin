package com.lyl.modules.info.entity;

import javax.persistence.Entity;
import javax.persistence.Table;

import com.lyl.base.entity.BaseEntity;

/**
 * 会员表
 * @author Liu YunLong
 *
 */
@Entity
@Table(name = "T_INFO_MEMBER")
public class Member extends BaseEntity {

	//用户账号
	private String userId;
	
	//姓名
	private String		name;

	//性别
	private String		gender;
	
	//年龄
	private Integer     age;
	
	//爱好，多个用“，”分开
	private String     hobbies;
	
	//手机号码
	private String		mobile;

	//类型，高级-1 普通为 0
	private Integer     type;
	
	//头像地址
	private String     portrait;
	
	//支付宝号
	private String  alipayNum;
	
	//微信号
	private String weChatNum;
	
	//邮箱
	private String		email;

	//地址
	private String		address;
	
	//发放的金额
	private Double sendMoney;


	public Double getSendMoney() {
		return sendMoney;
	}

	public void setSendMoney(Double sendMoney) {
		this.sendMoney = sendMoney;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public Integer getAge() {
		return age;
	}

	public void setAge(Integer age) {
		this.age = age;
	}

	public String getHobbies() {
		return hobbies;
	}

	public void setHobbies(String hobbies) {
		this.hobbies = hobbies;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	public String getPortrait() {
		return portrait;
	}

	public void setPortrait(String portrait) {
		this.portrait = portrait;
	}

	public String getAlipayNum() {
		return alipayNum;
	}

	public void setAlipayNum(String alipayNum) {
		this.alipayNum = alipayNum;
	}

	public String getWeChatNum() {
		return weChatNum;
	}

	public void setWeChatNum(String weChatNum) {
		this.weChatNum = weChatNum;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}
   
	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}



}
