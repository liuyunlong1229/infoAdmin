package com.lyl.modules.system.entity;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import com.lyl.base.entity.BaseEntity;

/**
 * 组织表
 * @author Liu YunLong
 *
 */
@Entity
@Table(name = "T_SYS_ORGANIZATION", uniqueConstraints = { @UniqueConstraint(columnNames = { "name", "label" }) })
public class Organization extends BaseEntity {

	private String	name;

	private String	label;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getLabel() {
		return label;
	}

	public void setLabel(String label) {
		this.label = label;
	}

}
