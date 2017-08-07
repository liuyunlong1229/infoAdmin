package com.lyl.modules.system.entity;

import javax.persistence.Entity;
import javax.persistence.Table;

import com.lyl.base.entity.BaseEntity;

/**
 * 角色和资源关联表
 * @author Liu YunLong
 *
 */
@Entity
@Table(name = "T_SYS_ROLE_RESOURCE")
public class RoleResource extends BaseEntity {

	private String rid;
	
	private String rsid;

	public String getRid() {
		return rid;
	}


	public void setRid(String rid) {
		this.rid = rid;
	}


	public String getRsid() {
		return rsid;
	}


	public void setRsid(String rsid) {
		this.rsid = rsid;
	}


}
