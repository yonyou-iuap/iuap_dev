
/** 
*Copyright 2016 Yonyou Corporation Ltd. All Rights Reserved.
* This software is published under the terms of the Yonyou Software
* License version 1.0, a copy of which has been included with this
* distribution in the LICENSE.txt file.
*
* @Project Name : iuaptraincrm
*
* @File name : TmCustomerinfoExtVO.java
*
* @author 
*
* @Date : 2016年12月9日
*
----------------------------------------------------------------------------------
*     Date       Who       Version     Comments
* 1. 2016年12月9日    name    1.0
*
*
*
*
----------------------------------------------------------------------------------
*/
	
package com.yonyou.iuap.crm.basedata.entity;

import javax.persistence.Transient;

import com.yonyou.iuap.persistence.jdbc.framework.annotation.Column;


/**
 * 客户信息扩展VO
 * @author 
 * @date 2016年12月9日
 */

public class TmCustomerinfoExtVO extends TmCustomerinfoVO{
	
	@Column(name = "provincename")
	@Transient
	private String provinceName;
	
	@Column(name = "cityname")
	@Transient
	private String cityName;

	public String getProvinceName() {
		return provinceName;
	}

	public void setProvinceName(String provinceName) {
		this.provinceName = provinceName;
	}

	public String getCityName() {
		return cityName;
	}

	public void setCityName(String cityName) {
		this.cityName = cityName;
	}

	
	
}
