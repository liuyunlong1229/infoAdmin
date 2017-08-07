package com.lyl.modules.info.entity;

import javax.persistence.Entity;
import javax.persistence.Table;

import com.lyl.base.entity.BaseEntity;

@Entity
@Table(name = "T_INFO_QRCODE")
public class QrCode  extends BaseEntity {
	
	//二维码的编码，根据时间自动生成
	private String code;
	
	//二维码的名称
	private String name;
	//二维码的描述
	private String remark;
	
	//对应的网页路径
	private String webUrl;
	
	//二维码生成的图片保存地址
	private String picUrl;
	
	//生成二维码的拓展参数1
	private String param1;
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public String getWebUrl() {
		return webUrl;
	}
	public void setWebUrl(String webUrl) {
		this.webUrl = webUrl;
	}
	public String getPicUrl() {
		return picUrl;
	}
	public void setPicUrl(String picUrl) {
		this.picUrl = picUrl;
	}
	public String getParam1() {
		return param1;
	}
	public void setParam1(String param1) {
		this.param1 = param1;
	}
	public String getParam2() {
		return param2;
	}
	public void setParam2(String param2) {
		this.param2 = param2;
	}
	public String getParam3() {
		return param3;
	}
	public void setParam3(String param3) {
		this.param3 = param3;
	}
	public String getParam4() {
		return param4;
	}
	public void setParam4(String param4) {
		this.param4 = param4;
	}
	//生成二维码的拓展参数2
	private String param2;
	//生成二维码的拓展参数3
	private String param3;
	//生成二维码的拓展参数4
	private String param4;
	
	
	
}
