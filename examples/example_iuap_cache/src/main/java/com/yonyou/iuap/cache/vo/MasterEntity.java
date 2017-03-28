package com.yonyou.iuap.cache.vo;

import java.io.Serializable;
import java.util.List;

public class MasterEntity implements Serializable {

	private static final long serialVersionUID = -3125850288889685892L;

	private String id;
	
	private String name;
	
	private boolean isDefault;
	
	private List<ChildEntity> children;

	
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public boolean isDefault() {
		return isDefault;
	}

	public void setDefault(boolean isDefault) {
		this.isDefault = isDefault;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List<ChildEntity> getChildren() {
		return children;
	}

	public void setChildren(List<ChildEntity> children) {
		this.children = children;
	}
	
}
