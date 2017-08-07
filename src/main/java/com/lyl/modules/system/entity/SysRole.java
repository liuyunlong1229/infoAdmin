package com.lyl.modules.system.entity;



import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.persistence.UniqueConstraint;

import com.lyl.base.entity.BaseEntity;

/**
 * 角色表
 * @author Liu YunLong
 *
 */
@Entity
@Table(name = "T_SYS_ROLE")
public class SysRole extends BaseEntity  {

	private String		name;

	private String		label;
	
	private String description;

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	@Transient
	private String	resourceIds;

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

	public String getResourceIds() {
		return resourceIds;
	}

	public void setResourceIds(String resourceIds) {
		this.resourceIds = resourceIds;
	}


}
