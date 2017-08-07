package com.lyl.base.vo;

import java.util.HashSet;
import java.util.Set;


public class MenuVO {

		String Id;
		String name;
		Integer sortNo;
		String resourcePath;
		String icon; //图标
		
		Set<MenuVO> subList=new HashSet<MenuVO>();
		
		
		public String getId() {
			return Id;
		}
		public void setId(String id) {
			Id = id;
		}
		public String getName() {
			return name;
		}
		public void setName(String name) {
			this.name = name;
		}
		public Integer getSortNo() {
			return sortNo;
		}
		public void setSortNo(Integer sortNo) {
			this.sortNo = sortNo;
		}
	
		public String getResourcePath() {
			return resourcePath;
		}
		public void setResourcePath(String resourcePath) {
			this.resourcePath = resourcePath;
		}
		
		public Set<MenuVO> getSubList() {
			return subList;
		}
		public void setSubList(Set<MenuVO> subList) {
			this.subList = subList;
		}

		public MenuVO() {
			
		}
		
		public MenuVO(String id, String name, Integer sortNo,
				String resourcePath, Set<MenuVO> subList) {
			super();
			Id = id;
			this.name = name;
			this.sortNo = sortNo;
			this.resourcePath = resourcePath;
			this.subList = subList;
		}
		
		@Override
		public String toString() {
			return "MenuVO [Id=" + Id + ", name=" + name + ", sortNo=" + sortNo
					+ ", resourcePath=" + resourcePath + ", icon=" + icon
					+ ", subList=" + subList + "]";
		}
		public String getIcon() {
			return icon;
		}
		public void setIcon(String icon) {
			this.icon = icon;
		}
	
}
