package com.lyl.base.vo;

public class ResourceVO {

	private String	id;

	private String	name;

	private String	label;

	private String	resourceType;

	private String	resourcePath;

	private String	icon;

	private String	_parentId;		//treegrid中识别父节点的必须字段，不能改名字

	private Boolean	isLeaf;		//是否是叶子节点
	
	

	private Integer orderNo; //序号

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

	public String getResourceType() {
		return resourceType;
	}

	public void setResourceType(String resourceType) {
		this.resourceType = resourceType;
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

	public String get_parentId() {
		return _parentId;
	}

	public void set_parentId(String _parentId) {
		this._parentId = _parentId;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	
	public ResourceVO() {
		super();
	}
	

	public Integer getOrderNo() {
		return orderNo;
	}

	public void setOrderNo(Integer orderNo) {
		this.orderNo = orderNo;
	}
	public Boolean getIsLeaf() {
		return isLeaf;
	}

	public void setIsLeaf(Boolean isLeaf) {
		this.isLeaf = isLeaf;
	}
	
	

	public ResourceVO(String id, String name, String label,
			String resourceType, String resourcePath, String icon,
			String _parentId, Boolean isLeaf, Integer orderNo) {
		super();
		this.id = id;
		this.name = name;
		this.label = label;
		this.resourceType = resourceType;
		this.resourcePath = resourcePath;
		this.icon = icon;
		this._parentId = _parentId;
		this.isLeaf = isLeaf;
		this.orderNo = orderNo;
	}


}
