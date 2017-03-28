
/** 
*Copyright 2016 Yonyou Corporation Ltd. All Rights Reserved.
* This software is published under the terms of the Yonyou Software
* License version 1.0, a copy of which has been included with this
* distribution in the LICENSE.txt file.
*
* @Project Name : iuaptraincrm
*
* @File name : TmVehicleClassifyExtVO.java
*
* @author 
*
* @Date : 2016年11月28日
*
----------------------------------------------------------------------------------
*     Date       Who       Version     Comments
* 1. 2016年11月28日    name    1.0
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
 * 车辆类别扩展信息
 * @author 
 * @date 2016年11月28日
 */

public class TmVehicleClassifyExtVO extends TmVehicleClassifyVO {
	
	@Column(name = "vbrandname")
	@Transient
	private String vbrandname;

	public String getVbrandname() {
		return vbrandname;
	}

	public void setVbrandname(String vbrandname) {
		this.vbrandname = vbrandname;
	}
	
	
}
