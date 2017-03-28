package com.yonyou.iuap.crm.subsidy.entity;

import java.util.List;

import com.yonyou.iuap.persistence.jdbc.framework.annotation.Column;
import com.yonyou.iuap.persistence.jdbc.framework.annotation.Entity;
import com.yonyou.iuap.persistence.jdbc.framework.annotation.GeneratedValue;
import com.yonyou.iuap.persistence.jdbc.framework.annotation.Id;
import com.yonyou.iuap.persistence.jdbc.framework.annotation.Stragegy;
import com.yonyou.iuap.persistence.jdbc.framework.annotation.Table;
import com.yonyou.iuap.persistence.vo.BaseEntity;


/**
 * The persistent class for the SS_CONTRYSUBSIDY database table.
 * 
 */
@Entity
@Table(name="SS_CONTRYSUBSIDY")
public class CountrysubsidyVO extends BaseEntity {
	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy=Stragegy.UUID,moudle="SS_CONTRYSUBSIDY")
	@Column(name = "pk_countrysubsidy")
	private String pk_countrysubsidy;
	@Column(name = "vbillno")
	private String vbillno;
	@Column(name = "countrysubsidyno")
	private String countrysubsidyno;
	@Column(name = "vdeclareno")
	private String vdeclareno;
	@Column(name = "dentrydate")
	private String dentrydate;
	@Column(name = "vdeclaredept")
	private String vdeclaredept;
	@Column(name = "vdeclarelegal")
	private String vdeclarelegal;
	@Column(name = "vgovernmentappno")
	private String vgovernmentappno;
	@Column(name = "vdeclarecity")
	private String vdeclarecity;
	@Column(name = "visdatacomplete")
	private String visdatacomplete;
	@Column(name = "visdataapprove")
	private String visdataapprove;
	@Column(name = "pk_org")
	private String pk_org;
	@Column(name = "vstatus")
	private String vstatus;
	@Column(name = "creator")
	private String creator;
	@Column(name = "creationtime")
	private String creationtime;
	@Column(name = "modifier")
	private String modifier;
	@Column(name = "modifiedtime")
	private String modifiedtime;
	@Column(name = "ts")
	private String ts;
	@Column(name = "dr")
	private int dr;
	
	//存在车辆子表信息
	private List<CountrysubsidyItemsVO> countrysubsidyItems = null;
	//存在车辆回款子表信息
	private List<CountrysubsidyFundbackVO> countrysubsidyFundback = null;

	public CountrysubsidyVO() {
	}


	public String getPk_countrysubsidy() {
		return this.pk_countrysubsidy;
	}

	public void setPk_countrysubsidy(String pk_countrysubsidy) {
		this.pk_countrysubsidy = pk_countrysubsidy;
	}


	public String getVbillno() {
		return this.vbillno;
	}

	public void setVbillno(String vbillno) {
		this.vbillno = vbillno;
	}


	public String getCountrysubsidyno() {
		return this.countrysubsidyno;
	}

	public void setCountrysubsidyno(String countrysubsidyno) {
		this.countrysubsidyno = countrysubsidyno;
	}


	public String getVdeclareno() {
		return this.vdeclareno;
	}

	public void setVdeclareno(String vdeclareno) {
		this.vdeclareno = vdeclareno;
	}


	public String getDentrydate() {
		return this.dentrydate;
	}

	public void setDentrydate(String dentrydate) {
		this.dentrydate = dentrydate;
	}


	public String getVdeclaredept() {
		return this.vdeclaredept;
	}

	public void setVdeclaredept(String vdeclaredept) {
		this.vdeclaredept = vdeclaredept;
	}


	public String getVdeclarelegal() {
		return this.vdeclarelegal;
	}

	public void setVdeclarelegal(String vdeclarelegal) {
		this.vdeclarelegal = vdeclarelegal;
	}


	public String getVgovernmentappno() {
		return this.vgovernmentappno;
	}

	public void setVgovernmentappno(String vgovernmentappno) {
		this.vgovernmentappno = vgovernmentappno;
	}


	public String getVdeclarecity() {
		return this.vdeclarecity;
	}

	public void setVdeclarecity(String vdeclarecity) {
		this.vdeclarecity = vdeclarecity;
	}


	public String getVisdatacomplete() {
		return this.visdatacomplete;
	}

	public void setVisdatacomplete(String visdatacomplete) {
		this.visdatacomplete = visdatacomplete;
	}


	public String getVisdataapprove() {
		return this.visdataapprove;
	}

	public void setVisdataapprove(String visdataapprove) {
		this.visdataapprove = visdataapprove;
	}


	public String getPk_org() {
		return this.pk_org;
	}

	public void setPk_org(String pk_org) {
		this.pk_org = pk_org;
	}


	public String getVstatus() {
		return this.vstatus;
	}

	public void setVstatus(String vstatus) {
		this.vstatus = vstatus;
	}


	public String getCreator() {
		return this.creator;
	}

	public void setCreator(String creator) {
		this.creator = creator;
	}


	public String getCreationtime() {
		return this.creationtime;
	}

	public void setCreationtime(String creationtime) {
		this.creationtime = creationtime;
	}


	public String getModifier() {
		return this.modifier;
	}

	public void setModifier(String modifier) {
		this.modifier = modifier;
	}


	public String getModifiedtime() {
		return this.modifiedtime;
	}

	public void setModifiedtime(String modifiedtime) {
		this.modifiedtime = modifiedtime;
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
        return "SS_CONTRYSUBSIDY";
    }

    @Override
    public String getNamespace() {
		return "com.yonyou.iuap.crm.subsidy.entity";
    }


	public List<CountrysubsidyItemsVO> getCountrysubsidyItems() {
		return countrysubsidyItems;
	}


	public void setCountrysubsidyItems(
			List<CountrysubsidyItemsVO> countrysubsidyItems) {
		this.countrysubsidyItems = countrysubsidyItems;
	}


	public List<CountrysubsidyFundbackVO> getCountrysubsidyFundback() {
		return countrysubsidyFundback;
	}


	public void setCountrysubsidyFundback(
			List<CountrysubsidyFundbackVO> countrysubsidyFundback) {
		this.countrysubsidyFundback = countrysubsidyFundback;
	}
    
}