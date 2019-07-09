package com.ianeiu.demo.fastjson;

import java.util.List;

public class UserGroup {
	private String id;
	private List<User> list;
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public List<User> getList() {
		return list;
	}
	public void setList(List<User> list) {
		this.list = list;
	}
	@Override
	public String toString() {
		return "UserGroup [id=" + id + ", list=" + list + "]";
	}
	
	
	
	
}
