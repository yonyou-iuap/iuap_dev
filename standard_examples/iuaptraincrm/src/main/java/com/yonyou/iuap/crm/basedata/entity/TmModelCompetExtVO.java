package com.yonyou.iuap.crm.basedata.entity;

import com.yonyou.iuap.persistence.jdbc.framework.annotation.Column;

/**
 * 竞品用车型扩展类
 * 
 * @author 
 * @date 2016年11月29日
 */

public class TmModelCompetExtVO extends TmModelVO {
	private static final long serialVersionUID = 5316370744989996935L;
	//竞品主键，用于标志删除
	@Column(name = "pk_competbrand")
	private String pk_competbrand;
	//映射到列上，查询时需要
	@Column(name = "nvpower")
	private String nvpower;//电池电量，隶属对比车型

	public String getPk_competbrand() {
		return pk_competbrand;
	}

	public void setPk_competbrand(String pk_competbrand) {
		this.pk_competbrand = pk_competbrand;
	}

	public String getNvpower() {
		return nvpower;
	}

	public void setNvpower(String nvpower) {
		this.nvpower = nvpower;
	}
	
}
