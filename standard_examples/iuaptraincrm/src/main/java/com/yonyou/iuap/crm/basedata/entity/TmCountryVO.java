package com.yonyou.iuap.crm.basedata.entity;

import com.yonyou.iuap.persistence.jdbc.framework.annotation.Column;
import com.yonyou.iuap.persistence.jdbc.framework.annotation.Entity;
import com.yonyou.iuap.persistence.jdbc.framework.annotation.GeneratedValue;
import com.yonyou.iuap.persistence.jdbc.framework.annotation.Id;
import com.yonyou.iuap.persistence.jdbc.framework.annotation.Stragegy;
import com.yonyou.iuap.persistence.jdbc.framework.annotation.Table;
import com.yonyou.iuap.persistence.vo.BaseEntity;


/**
 * The persistent class for the tm_country database table.
 * 
 */
@Entity
@Table(name="tm_country")
public class TmCountryVO extends BaseEntity {
	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy=Stragegy.UAPOID,moudle="tm_country")
	@Column(name = "pk_country")
	private String pk_country;
	@Column(name = "vcountrycode")
	private String vcountrycode;
	@Column(name = "vcountryname")
	private String vcountryname;
	@Column(name = "ts")
	private String ts;
	@Column(name = "dr")
	private int dr;

	public TmCountryVO() {
	}


	public String getPk_country() {
		return this.pk_country;
	}

	public void setPk_country(String pk_country) {
		this.pk_country = pk_country;
	}


	public String getVcountrycode() {
		return this.vcountrycode;
	}

	public void setVcountrycode(String vcountrycode) {
		this.vcountrycode = vcountrycode;
	}


	public String getVcountryname() {
		return this.vcountryname;
	}

	public void setVcountryname(String vcountryname) {
		this.vcountryname = vcountryname;
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
        return "tm_country";
    }

    @Override
    public String getNamespace() {
		return "com.yonyou.iuap.crm.basedata.entity";
    }
}