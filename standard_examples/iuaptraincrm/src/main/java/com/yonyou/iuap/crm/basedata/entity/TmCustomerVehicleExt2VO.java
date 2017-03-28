
/** 
*Copyright 2017 Yonyou Corporation Ltd. All Rights Reserved.
* This software is published under the terms of the Yonyou Software
* License version 1.0, a copy of which has been included with this
* distribution in the LICENSE.txt file.
*
* @Project Name : iuaptraincrm
*
* @File name : TmCustomerVehicleExt2VO.java
*
* @author 
*
* @Date : 2017年1月20日
*
----------------------------------------------------------------------------------
*     Date       Who       Version     Comments
* 1. 2017年1月20日    name    1.0
*
*
*
*
----------------------------------------------------------------------------------
*/
	
package com.yonyou.iuap.crm.basedata.entity;

import org.springframework.data.annotation.Transient;

import com.yonyou.iuap.persistence.jdbc.framework.annotation.Column;


/**
 * 客户信息-车辆扩展信息
 * @author 
 * @date 2017年1月20日
 */

public class TmCustomerVehicleExt2VO extends TmCustomerVehicleVO {
	
	@Column(name="vmodelname")
	@Transient
	private String vmodelname;
	@Column(name="vseriesname")
	@Transient
	private String vseriesname;
	
	public String getVmodelname() {
		return vmodelname;
	}
	public void setVmodelname(String vmodelname) {
		this.vmodelname = vmodelname;
	}
	public String getVseriesname() {
		return vseriesname;
	}
	public void setVseriesname(String vseriesname) {
		this.vseriesname = vseriesname;
	}
	

}
