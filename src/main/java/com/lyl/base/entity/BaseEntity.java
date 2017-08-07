package com.lyl.base.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import javax.persistence.Version;

import org.apache.commons.lang3.StringUtils;
import org.hibernate.annotations.GenericGenerator;


/**
 * 基础实体
 * @author Liuyl
 *
 */

@MappedSuperclass
public abstract class BaseEntity {


	
	@Id
	@GenericGenerator(name="idGenerator", strategy="uuid")
	@GeneratedValue(generator="idGenerator")
	@Column(name="id",length=32)
	private String id;
	
	/**
	 * 创建用户
	 */
	private String createBy; 

	/**
	 * 最后更新用户
	 */
	private String lastUpdateBy; 

	/**
	 * 创建时间
	 */
	private Date createDttm; 

	/**
	 * 最后更新时间
	 */
	private Date updateDttm;
	
	/**
	 * 有效标识
	 */             
	private boolean enableFlag=true; 
	
	@Version
	private Long	version;
	
	public String getCreateBy() {
		return createBy;
	}

	public void setCreateBy(String createBy) {
		this.createBy = createBy;
	}

	public String getLastUpdateBy() {
		return lastUpdateBy;
	}

	public void setLastUpdateBy(String lastUpdateBy) {
		this.lastUpdateBy = lastUpdateBy;
	}

	public Long getVersion() {
		return version;
	}

	public void setVersion(Long version) {
		this.version = version;
	}

	public boolean isEnableFlag() {
		return enableFlag;
	}

	public void setEnableFlag(boolean enableFlag) {
		this.enableFlag = enableFlag;
	}


	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}
	

	public Date getCreateDttm() {
		return createDttm;
	}

	public void setCreateDttm(Date createDttm) {
		this.createDttm = createDttm;
	}

	public Date getUpdateDttm() {
		return updateDttm;
	}

	public void setUpdateDttm(Date updateDttm) {
		this.updateDttm = updateDttm;
	}

	@Override
	public boolean equals(Object obj) {
		BaseEntity another=(BaseEntity)obj;
		if(StringUtils.isNotBlank(this.getId()) && StringUtils.isNotBlank(another.getId())){
			return this.getId().equals(another.getId());
		}
		return super.equals(obj);
		
	}

	
	


}
