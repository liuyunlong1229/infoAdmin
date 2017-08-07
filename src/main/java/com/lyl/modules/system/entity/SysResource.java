package com.lyl.modules.system.entity;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.persistence.UniqueConstraint;

import com.lyl.base.entity.BaseEntity;

/**
 * 资源表
 * @author Liu YunLong
 *
 */

@Entity
@Table(name = "T_SYS_RESOURCE", uniqueConstraints = { @UniqueConstraint(columnNames = { "name", "label" }) })
public class SysResource extends BaseEntity{

	private String				name;

	private String				label;

	//EnumType.ORDINAL 将值保持到数据库，默认的模式 ;EnumType.STRING将名称保存到数据库
	@Enumerated(EnumType.STRING)
	private ResourceType		resourceType;

	private String				resourcePath;

	private String				icon;
	
	private String			    pid;

	private Boolean				isLeaf;
	
	private Integer orderNo;
	
	@Transient
	private String resType;//资源类型
	
	public Boolean getIsLeaf() {
		return isLeaf;
	}

	public void setIsLeaf(Boolean isLeaf) {
		this.isLeaf = isLeaf;
	}

	public Integer getOrderNo() {
		return orderNo;
	}

	public void setOrderNo(Integer orderNo) {
		this.orderNo = orderNo;
	}

	@Transient
	private List<SysResource> subList=new ArrayList<SysResource>();
	

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List<SysResource> getSubList() {
		return subList;
	}

	public void setSubList(List<SysResource> subList) {
		this.subList = subList;
	}

	public String getLabel() {
		return label;
	}

	public void setLabel(String label) {
		this.label = label;
	}

	public String getResourcePath() {
		return resourcePath;
	}

	public void setResourcePath(String resourcePath) {
		this.resourcePath = resourcePath;
	}

	public String getIcon() {
		return icon;
	}

	public void setIcon(String icon) {
		this.icon = icon;
	}

	public String getPid() {
		return pid;
	}

	public void setPid(String pid) {
		this.pid = pid;
	}

	public ResourceType getResourceType() {
		return resourceType;
	}
	
	public String getResType() {
		return resType;
	}

	public void setResType(String resType) {
		this.resType = resType;
	}

	public void setResourceType(ResourceType resourceType) {
		this.resType=resourceType.getText();
		this.resourceType = resourceType;
	}

	public enum ResourceType {
		button("按钮"), menu("菜单"), module("模块");
		String	text;

		ResourceType(String text) {
			this.text = text;
		}

		public String getText() {
			return text;
		}
	}

}
