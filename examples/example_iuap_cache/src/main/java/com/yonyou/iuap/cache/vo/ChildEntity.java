package com.yonyou.iuap.cache.vo;

import java.io.Serializable;

public class ChildEntity implements Serializable {

	private static final long serialVersionUID = -3125850288889685892L;

	private String id;
	
	private String name;
	
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
}
