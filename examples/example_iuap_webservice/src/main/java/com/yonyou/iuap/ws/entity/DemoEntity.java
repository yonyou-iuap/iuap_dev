package com.yonyou.iuap.ws.entity;

import java.io.Serializable;
import java.util.Date;

public class DemoEntity implements Serializable{

	private static final long serialVersionUID = 1L;

	private String id;
	
	private String name;
	
	private boolean isdefault;
	
	private int flag;
	
	private Date startDate;
	
	private long count;
	
	private double price;

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

	public boolean isIsdefault() {
		return isdefault;
	}

	public void setIsdefault(boolean isdefault) {
		this.isdefault = isdefault;
	}

	public int getFlag() {
		return flag;
	}

	public void setFlag(int flag) {
		this.flag = flag;
	}

	public Date getStartDate() {
		return startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	public long getCount() {
		return count;
	}

	public void setCount(long count) {
		this.count = count;
	}

	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
	}

	@Override
	public String toString() {
		return "DemoEntity [id=" + id + ", name=" + name + ", isdefault=" + isdefault + ", flag=" + flag + ", startDate=" + startDate + ", count=" + count + ", price=" + price + "]";
	}
	
}
