package com.lyl.modules.system.entity;

import javax.persistence.Entity;
import javax.persistence.Table;

import com.lyl.base.entity.BaseEntity;

/**
 * 用户和组织关联表
 * @author Liu YunLong
 *
 */
@Entity
@Table(name = "T_SYS_USER_ORGANIZATION")
public class UserOrganization extends BaseEntity {

	private  String orgid;

	private String uid;

	public String getOrgid() {
		return orgid;
	}

	public void setOrgid(String orgid) {
		this.orgid = orgid;
	}

	public String getUid() {
		return uid;
	}

	public void setUid(String uid) {
		this.uid = uid;
	}


}
