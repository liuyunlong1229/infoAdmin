package com.lyl.base.paging;

import com.lyl.base.util.SystemEnum.OrderDir;



public class PageParam {

	private int			start	= 0;
	private int			page	= 0;
	private int			rows;
	private String		sort;
	private OrderDir	order;


	public int getStart() {
		return (this.page - 1) * this.rows;
	}

	public void setStart(int start) {
		this.start = start;
	}

	public int getPage() {
		return page;
	}

	public void setPage(int page) {
		this.page = page;
		this.start = (this.page - 1) * this.rows;
	}

	public int getRows() {
		return rows;
	}

	public void setRows(int rows) {
		this.rows = rows;
	}

	public String getSort() {
		return sort;
	}

	public void setSort(String sort) {
		this.sort = sort;
	}

	public OrderDir getOrder() {
		return order;
	}

	public void setOrder(OrderDir order) {
		this.order = order;
	}
	
	public int getPageSize(){
		return rows;
	}

}