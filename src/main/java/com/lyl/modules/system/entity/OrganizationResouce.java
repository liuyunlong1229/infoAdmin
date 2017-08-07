package com.lyl.modules.system.entity;

import javax.persistence.Entity;
import javax.persistence.Table;

import com.lyl.base.entity.BaseEntity;

/**
 * 组织和资源关联表
 * @author Liu YunLong
 *
 */
@Entity
@Table(name = "T_SYS_ORG_RESOURCE")
public class OrganizationResouce extends BaseEntity {

	public String getOrgid() {
		return orgid;
	}

	public void setOrgid(String orgid) {
		this.orgid = orgid;
	}

	private String orgid;

	private String	rsid;

	public String getRsid() {
		return rsid;
	}

	public void setRsid(String rsid) {
		this.rsid = rsid;
	}

}
