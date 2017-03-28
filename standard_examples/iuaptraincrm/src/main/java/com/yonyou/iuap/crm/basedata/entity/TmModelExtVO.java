package com.yonyou.iuap.crm.basedata.entity;

import javax.persistence.Transient;

import com.yonyou.iuap.persistence.jdbc.framework.annotation.Column;


/**
* TODO description
* @author 
* @date 2016年11月29日
*/
	
public class TmModelExtVO extends TmModelVO {
	/**
	 * 
	 */
	private static final long serialVersionUID = -8337930022911207486L;
	
	/**
	 * 所属车系
	 */
	@Transient
	@Column(name="seriesname")
	private String seriesname;
	
	/**
	 * 所属类别
	 */
	@Transient
	@Column(name="vehicelname")
	private String vehicelname;
	
	/**
	 * 所属品牌
	 */
	@Transient
	@Column(name="brandname")
	private String brandname;

	public String getVehicelname() {
		return vehicelname;
	}

	public void setVehicelname(String vehicelname) {
		this.vehicelname = vehicelname;
	}

	

	public String getBrandname() {
		return brandname;
	}

	public void setBrandname(String brandname) {
		this.brandname = brandname;
	}

	public String getSeriesname() {
		return seriesname;
	}

	public void setSeriesname(String seriesname) {
		this.seriesname = seriesname;
	}
	
	

	

}
