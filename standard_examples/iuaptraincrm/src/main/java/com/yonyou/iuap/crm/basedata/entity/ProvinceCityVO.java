package com.yonyou.iuap.crm.basedata.entity;

import com.yonyou.iuap.persistence.jdbc.framework.annotation.Column;
import com.yonyou.iuap.persistence.jdbc.framework.annotation.Entity;
import com.yonyou.iuap.persistence.jdbc.framework.annotation.GeneratedValue;
import com.yonyou.iuap.persistence.jdbc.framework.annotation.Id;
import com.yonyou.iuap.persistence.jdbc.framework.annotation.Stragegy;
import com.yonyou.iuap.persistence.jdbc.framework.annotation.Table;
import com.yonyou.iuap.persistence.vo.BaseEntity;


/**
 * The persistent class for the tm_region database table.
 * 
 */
@Entity
@Table(name="tm_region")
public class ProvinceCityVO extends BaseEntity {
	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy=Stragegy.UUID,moudle="tm_region")
	@Column(name = "pk_region")
	private String pk_region;
	@Column(name = "code")
	private String code;
	@Column(name = "name")
	private String name;
	@Column(name = "pk_father")
	private String pk_father;
	@Column(name = "pk_country")
	private String pk_country;
	@Column(name = "ts")
	private String ts;
	@Column(name = "dr")
	private int dr;

	public ProvinceCityVO() {
	}


	public String getPk_region() {
		return this.pk_region;
	}

	public void setPk_region(String pk_region) {
		this.pk_region = pk_region;
	}


	public String getCode() {
		return this.code;
	}

	public void setCode(String code) {
		this.code = code;
	}


	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}


	public String getPk_father() {
		return this.pk_father;
	}

	public void setPk_father(String pk_father) {
		this.pk_father = pk_father;
	}


	public String getPk_country() {
		return this.pk_country;
	}

	public void setPk_country(String pk_country) {
		this.pk_country = pk_country;
	}


	public String getTs() {
		return this.ts;
	}

	public void setTs(String ts) {
		this.ts = ts;
	}


	public int getDr() {
		return this.dr;
	}

	public void setDr(int dr) {
		this.dr = dr;
	}

	@Override
    public String getMetaDefinedName() {
        return "tm_region";
    }

    @Override
    public String getNamespace() {
		return "com.yonyou.iuap.crm.basedata.entity";
    }
}