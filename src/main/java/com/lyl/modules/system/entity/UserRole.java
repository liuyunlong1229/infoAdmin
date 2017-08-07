package com.lyl.modules.system.entity;

import javax.persistence.Entity;
import javax.persistence.Table;

import com.lyl.base.entity.BaseEntity;

/**
 * 用户和角色关联表
 * @author Liu YunLong
 *
 */
@Entity
@Table(name = "T_SYS_USER_ROLE")
public class UserRole extends BaseEntity {


	private String uid;
	
	private String rid;

	public String getUid() {
		return uid;
	}

	public void setUid(String uid) {
		this.uid = uid;
	}

	public String getRid() {
		return rid;
	}

	public void setRid(String rid) {
		this.rid = rid;
	}
}
