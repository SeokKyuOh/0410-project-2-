package com.paris.db;

public class SubCategory {
	private int sub_category_id;
	private String sub_name;
	private int top_category_id;
	
	public int getSub_category_id() {
		return sub_category_id;
	}
	public void setSub_category_id(int sub_category_id) {
		this.sub_category_id = sub_category_id;
	}
	public String getSub_name() {
		return sub_name;
	}
	public void setSub_name(String sub_name) {
		this.sub_name = sub_name;
	}
	public int getTop_category_id() {
		return top_category_id;
	}
	public void setTop_category_id(int top_category_id) {
		this.top_category_id = top_category_id;
	}

	
}
