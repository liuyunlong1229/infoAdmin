package com.lyl.modules.info.entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import javax.persistence.Entity;
import javax.persistence.Table;

import com.lyl.base.entity.BaseEntity;


/**
 * 訪問規則表
 * @author Administrator
 *
 */
@Entity
@Table(name = "T_INFO_VISIT_RULE")
@ApiModel
public class VisitRule  extends BaseEntity {
	
	//生成唯一的編碼
	@ApiModelProperty(value="访问规则编码")
	private String code;
	
	//访问的时长
	@ApiModelProperty(value="有效时长（分钟）")
	private Integer visitTime;
	
	//是否划到了页底
	@ApiModelProperty(value="阅读置地（1-是 0-否）")
	private Boolean isEndofPage;
	
	//划页的次数
	@ApiModelProperty(value="翻阅次数")
	private Integer crossPageCount; 
	
	
	public Integer getVisitTime() {
		return visitTime;
	}

	public void setVisitTime(Integer visitTime) {
		this.visitTime = visitTime;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public Integer getCrossPageCount() {
		return crossPageCount;
	}

	public void setCrossPageCount(Integer crossPageCount) {
		this.crossPageCount = crossPageCount;
	}


	public Boolean getIsEndofPage() {
		return isEndofPage;
	}

	public void setIsEndofPage(Boolean isEndofPage) {
		this.isEndofPage = isEndofPage;
	}

}
