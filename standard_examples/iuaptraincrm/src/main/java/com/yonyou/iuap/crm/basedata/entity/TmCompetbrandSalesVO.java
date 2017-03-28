/** 
 *Copyright 2016 Yonyou Corporation Ltd. All Rights Reserved.
 * This software is published under the terms of the Yonyou Software
 * License version 1.0, a copy of which has been included with this
 * distribution in the LICENSE.txt file.
 *
 * @Project Name : iuaptraincrm
 *
 * @File name : TmCompetbrandSalesVO.java
 *
 * @author 
 *
 * @Date : Dec 27, 2016
 *
 ----------------------------------------------------------------------------------
 *     Date       Who       Version     Comments
 * 1. Dec 27, 2016    name    1.0
 *
 *
 *
 *
 ----------------------------------------------------------------------------------
 */

package com.yonyou.iuap.crm.basedata.entity;

import com.yonyou.iuap.persistence.jdbc.framework.annotation.Column;
import com.yonyou.iuap.persistence.jdbc.framework.annotation.Entity;
import com.yonyou.iuap.persistence.vo.BaseEntity;

/**
 * 竞品年度销量信息数据展示VO
 * 
 * @author 
 * @date Dec 27, 2016
 */
@Entity
public class TmCompetbrandSalesVO extends BaseEntity {

	private static final long serialVersionUID = 3447043927226314739L;
	@Column(name = "vyear")
	private String vyear;// 年度
	@Column(name = "sumup")
	private double sumup;// 销量

	public String getVyear() {
		return vyear;
	}

	public void setVyear(String vyear) {
		this.vyear = vyear;
	}
	
	public double getSumup() {
		return sumup;
	}

	public void setSumup(double sumup) {
		this.sumup = sumup;
	}

	//必须加
	@Override
	public String getMetaDefinedName() {
		return "TmCompetbrandSalesVO";
	}

	@Override
	public String getNamespace() {
		return "com.yonyou.iuap.crm.basedata.entity";
	}
}
