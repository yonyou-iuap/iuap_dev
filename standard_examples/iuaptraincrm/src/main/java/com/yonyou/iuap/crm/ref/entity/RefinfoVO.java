package com.yonyou.iuap.crm.ref.entity;

import com.yonyou.iuap.persistence.jdbc.framework.annotation.Column;
import com.yonyou.iuap.persistence.jdbc.framework.annotation.Entity;
import com.yonyou.iuap.persistence.jdbc.framework.annotation.GeneratedValue;
import com.yonyou.iuap.persistence.jdbc.framework.annotation.Id;
import com.yonyou.iuap.persistence.jdbc.framework.annotation.Stragegy;
import com.yonyou.iuap.persistence.jdbc.framework.annotation.Table;
import com.yonyou.iuap.persistence.vo.BaseEntity;


/**
 * The persistent class for the ref_refinfo database table.
 * 
 */
@Entity
@Table(name="ref_refinfo")
public class RefinfoVO extends BaseEntity {
	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy=Stragegy.UUID,moudle="ref_refinfo")
	@Column(name = "pk_ref")
	private String pk_ref;
	@Column(name = "refname")
	private String refname;
	@Column(name = "refcode")
	private String refcode;
	@Column(name = "refclass")
	private String refclass;
	@Column(name = "refurl")
	private String refurl;
	@Column(name = "md_entitypk")
	private String md_entitypk;
	@Column(name = "productType")
	private String productType;

	public RefinfoVO() {
	}


	public String getPk_ref() {
		return this.pk_ref;
	}

	public void setPk_ref(String pk_ref) {
		this.pk_ref = pk_ref;
	}


	public String getRefname() {
		return this.refname;
	}

	public void setRefname(String refname) {
		this.refname = refname;
	}


	public String getRefcode() {
		return this.refcode;
	}

	public void setRefcode(String refcode) {
		this.refcode = refcode;
	}


	public String getRefclass() {
		return this.refclass;
	}

	public void setRefclass(String refclass) {
		this.refclass = refclass;
	}


	public String getRefurl() {
		return this.refurl;
	}

	public void setRefurl(String refurl) {
		this.refurl = refurl;
	}


	public String getMd_entitypk() {
		return this.md_entitypk;
	}

	public void setMd_entitypk(String md_entitypk) {
		this.md_entitypk = md_entitypk;
	}


	public String getProductType() {
		return this.productType;
	}

	public void setProductType(String productType) {
		this.productType = productType;
	}

	@Override
    public String getMetaDefinedName() {
        return "RefinfoVO";
    }

    @Override
    public String getNamespace() {
		return "com.yonyou.iuap.crm.ref.entity";
    }
}