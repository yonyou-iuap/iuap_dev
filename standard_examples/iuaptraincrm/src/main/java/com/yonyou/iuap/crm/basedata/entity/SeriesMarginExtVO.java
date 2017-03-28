package com.yonyou.iuap.crm.basedata.entity;


public class SeriesMarginExtVO extends SeriesMarginVO {
	private static final long serialVersionUID = 2336668822311207486L;
	/**
	 * 车系编码
	 */
	private String vseriescode;
	/**
	 * 车系名称
	 */
	private String vseriesname;
	/**
	 * 所属类别名称
	 */
	private String classname;
	/**
	 * 所属品牌名称
	 */
	private String brandname;
	
	public String getVseriescode() {
		return vseriescode;
	}
	public void setVseriescode(String vseriescode) {
		this.vseriescode = vseriescode;
	}
	public String getVseriesname() {
		return vseriesname;
	}
	public void setVseriesname(String vseriesname) {
		this.vseriesname = vseriesname;
	}
	public String getClassname() {
		return classname;
	}
	public void setClassname(String classname) {
		this.classname = classname;
	}
	public String getBrandname() {
		return brandname;
	}
	public void setBrandname(String brandname) {
		this.brandname = brandname;
	}
	
}
