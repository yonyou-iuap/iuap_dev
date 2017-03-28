package com.yonyou.iuap.crm.basedata.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.yonyou.iuap.persistence.jdbc.framework.annotation.Column;
import com.yonyou.iuap.persistence.jdbc.framework.annotation.Entity;
import com.yonyou.iuap.persistence.jdbc.framework.annotation.GeneratedValue;
import com.yonyou.iuap.persistence.jdbc.framework.annotation.Id;
import com.yonyou.iuap.persistence.jdbc.framework.annotation.Stragegy;
import com.yonyou.iuap.persistence.jdbc.framework.annotation.Table;
import com.yonyou.iuap.persistence.vo.BaseEntity;


/**
 * The persistent class for the tm_compet_price database table.
 * 
 */
@Entity
@Table(name="tm_compet_price")
public class TmCompetPriceVO extends BaseEntity {
	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy=Stragegy.UAPOID,moudle="tm_compet_price")
	@Column(name = "pk_compet_price")
	private String pk_compet_price;
	@Column(name = "pk_competbrand")
	private String pk_competbrand;
	@Column(name = "vcity")
	private String vcity;
	@Column(name = "ncountrysubsidy")
	private double ncountrysubsidy;
	@Column(name = "vbatpower")
	private String vbatpower;
	@Column(name = "vbatlife")
	private String vbatlife;
	@Column(name = "vchargepower")
	private String vchargepower;
	@Column(name = "vchargetime")
	private String vchargetime;
	@Column(name = "dsaledate")
	private String dsaledate;
	@Column(name = "nsubsidy")
	private double nsubsidy;
	@Column(name = "nprice")
	private double nprice;
	@Column(name = "vpolicy")
	private String vpolicy;
	@Column(name = "vsellpoint")
	private String vsellpoint;
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

	public TmCompetPriceVO() {
	}


	public String getPk_compet_price() {
		return this.pk_compet_price;
	}

	public void setPk_compet_price(String pk_compet_price) {
		this.pk_compet_price = pk_compet_price;
	}


	public String getPk_competbrand() {
		return this.pk_competbrand;
	}

	public void setPk_competbrand(String pk_competbrand) {
		this.pk_competbrand = pk_competbrand;
	}


	public String getVcity() {
		return this.vcity;
	}

	public void setVcity(String vcity) {
		this.vcity = vcity;
	}


	public double getNcountrysubsidy() {
		return this.ncountrysubsidy;
	}

	public void setNcountrysubsidy(double ncountrysubsidy) {
		this.ncountrysubsidy = ncountrysubsidy;
	}


	public String getVbatpower() {
		return this.vbatpower;
	}

	public void setVbatpower(String vbatpower) {
		this.vbatpower = vbatpower;
	}


	public String getVbatlife() {
		return this.vbatlife;
	}

	public void setVbatlife(String vbatlife) {
		this.vbatlife = vbatlife;
	}


	public String getVchargepower() {
		return this.vchargepower;
	}

	public void setVchargepower(String vchargepower) {
		this.vchargepower = vchargepower;
	}


	public String getVchargetime() {
		return this.vchargetime;
	}

	public void setVchargetime(String vchargetime) {
		this.vchargetime = vchargetime;
	}

	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+08:00")
	public String getDsaledate() {
		return this.dsaledate;
	}

	public void setDsaledate(String dsaledate) {
		this.dsaledate = dsaledate;
	}


	public double getNsubsidy() {
		return this.nsubsidy;
	}

	public void setNsubsidy(double nsubsidy) {
		this.nsubsidy = nsubsidy;
	}


	public double getNprice() {
		return this.nprice;
	}

	public void setNprice(double nprice) {
		this.nprice = nprice;
	}


	public String getVpolicy() {
		return this.vpolicy;
	}

	public void setVpolicy(String vpolicy) {
		this.vpolicy = vpolicy;
	}


	public String getVsellpoint() {
		return this.vsellpoint;
	}

	public void setVsellpoint(String vsellpoint) {
		this.vsellpoint = vsellpoint;
	}


	public String getCreator() {
		return this.creator;
	}

	public void setCreator(String creator) {
		this.creator = creator;
	}

	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+08:00")
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

	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+08:00")
	public String getModifiedtime() {
		return this.modifiedtime;
	}

	public void setModifiedtime(String modifiedtime) {
		this.modifiedtime = modifiedtime;
	}

	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+08:00")
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
        return "tm_compet_price";
    }

    @Override
    public String getNamespace() {
		return "com.yonyou.iuap.crm.basedata.entity";
    }
}