package com.lyl.base.vo;

import java.util.ArrayList;
import java.util.List;

public class TreeVo {
	
	private String id;
	
	
	private String text;
	
	private String iconCls;
	
	
	private String  state="open" ;
	
	boolean checked;
	
	List<TreeVo> children=new ArrayList<TreeVo>();
	
	List<String> attributes;



	public boolean isChecked() {
		return checked;
	}


	public void setChecked(boolean checked) {
		this.checked = checked;
	}
	

	public String getId() {
		return id;
	}


	public void setId(String id) {
		this.id = id;
	}


	public TreeVo() {
		super();
	}

	public String getText() {
		return text;
	}


	public void setText(String text) {
		this.text = text;
	}


	public String getState() {
		return state;
	}


	public void setState(String state) {
		this.state = state;
	}


	public List<String> getAttributes() {
		return attributes;
	}


	public void setAttributes(List<String> attributes) {
		this.attributes = attributes;
	}


	@Override
	public String toString() {
		return "TreeVo [id=" + id + ", text=" + text + ", iconCls=" + iconCls
				+ ", state=" + state + ", checked=" + checked + ", children="
				+ children + ", attributes=" + attributes + "]";
	}


	public List<TreeVo> getChildren() {
		return children;
	}


	public void setChildren(List<TreeVo> children) {
		this.children = children;
	}


	public TreeVo(String id, String text, String state, boolean checked,
			List<TreeVo> children, List<String> attributes) {
		super();
		this.id = id;
		this.text = text;
		this.state = state;
		this.checked = checked;
		this.children = children;
		this.attributes = attributes;
	}


	public String getIconCls() {
		return iconCls;
	}


	public void setIconCls(String iconCls) {
		this.iconCls = iconCls;
	}




	

	
	
}
