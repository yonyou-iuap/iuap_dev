package com.yonyou.iuap.crm.subsidy.entity;

import com.yonyou.iuap.persistence.jdbc.framework.annotation.Column;
import com.yonyou.iuap.persistence.jdbc.framework.annotation.Entity;
import com.yonyou.iuap.persistence.jdbc.framework.annotation.FK;
import com.yonyou.iuap.persistence.jdbc.framework.annotation.GeneratedValue;
import com.yonyou.iuap.persistence.jdbc.framework.annotation.Id;
import com.yonyou.iuap.persistence.jdbc.framework.annotation.Stragegy;
import com.yonyou.iuap.persistence.jdbc.framework.annotation.Table;
import com.yonyou.iuap.persistence.vo.BaseEntity;


/**
 * The persistent class for the SS_COUNTRYSUBSIDY_FUNDBACK database table.
 * 
 */
@Entity
@Table(name="SS_COUNTRYSUBSIDY_FUNDBACK")
public class CountrysubsidyFundbackVO extends BaseEntity {
	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy=Stragegy.UUID,moudle="SS_COUNTRYSUBSIDY_FUNDBACK")
	@Column(name = "pk_countrysubsidy_fundback")
	private String pk_countrysubsidy_fundback;
	@FK(name="pk_countrysubsidy",referencedColumnName="pk_countrysubsidy",referenceTableName="SS_CONTRYSUBSIDY" )
	@Column(name = "pk_countrysubsidy")
	private String pk_countrysubsidy;
	@Column(name = "vvin")
	private String vvin;
	@Column(name = "vvehicleseries")
	private String vvehicleseries;
	@Column(name = "vsaledept")
	private String vsaledept;
	@Column(name = "nsubsidystandard")
	private double nsubsidystandard;
	@Column(name = "dmustbackdate")
	private String dmustbackdate;
	@Column(name = "nfactback")
	private double nfactback;
	@Column(name = "dfactbackdate")
	private String dfactbackdate;
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

	public CountrysubsidyFundbackVO() {
	}


	public String getPk_countrysubsidy_fundback() {
		return this.pk_countrysubsidy_fundback;
	}

	public void setPk_countrysubsidy_fundback(String pk_countrysubsidy_fundback) {
		this.pk_countrysubsidy_fundback = pk_countrysubsidy_fundback;
	}


	public String getPk_countrysubsidy() {
		return this.pk_countrysubsidy;
	}

	public void setPk_countrysubsidy(String pk_countrysubsidy) {
		this.pk_countrysubsidy = pk_countrysubsidy;
	}


	public String getVvin() {
		return this.vvin;
	}

	public void setVvin(String vvin) {
		this.vvin = vvin;
	}


	public String getVvehicleseries() {
		return this.vvehicleseries;
	}

	public void setVvehicleseries(String vvehicleseries) {
		this.vvehicleseries = vvehicleseries;
	}


	public String getVsaledept() {
		return this.vsaledept;
	}

	public void setVsaledept(String vsaledept) {
		this.vsaledept = vsaledept;
	}


	public double getNsubsidystandard() {
		return this.nsubsidystandard;
	}

	public void setNsubsidystandard(double nsubsidystandard) {
		this.nsubsidystandard = nsubsidystandard;
	}


	public String getDmustbackdate() {
		return this.dmustbackdate;
	}

	public void setDmustbackdate(String dmustbackdate) {
		this.dmustbackdate = dmustbackdate;
	}


	public double getNfactback() {
		return this.nfactback;
	}

	public void setNfactback(double nfactback) {
		this.nfactback = nfactback;
	}


	public String getDfactbackdate() {
		return this.dfactbackdate;
	}

	public void setDfactbackdate(String dfactbackdate) {
		this.dfactbackdate = dfactbackdate;
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
        return "SS_COUNTRYSUBSIDY_FUNDBACK";
    }

    @Override
    public String getNamespace() {
		return "com.yonyou.iuap.crm.subsidy.entity";
    }
}