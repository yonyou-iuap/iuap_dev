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
 * The persistent class for the tm_competrelation database table.
 * 
 */
@Entity
@Table(name = "tm_competrelation")
public class TmCompetRelationVO extends BaseEntity {
	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy = Stragegy.UAPOID, moudle = "tm_competrelation")
	@Column(name = "pk_competrelation")
	private String pk_competrelation;
	@Column(name = "pk_competbrand")
	private String pk_competbrand;
	@Column(name = "pk_model")
	private String pk_model;
	@Column(name = "nvbatterypower")
	private String nvbatterypower;
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

	public TmCompetRelationVO() {
	}

	public String getPk_competbrand() {
		return this.pk_competbrand;
	}

	public void setPk_competbrand(String pk_competbrand) {
		this.pk_competbrand = pk_competbrand;
	}

	public String getPk_model() {
		return this.pk_model;
	}

	public void setPk_model(String pk_model) {
		this.pk_model = pk_model;
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
		return "tm_competrelation";
	}

	@Override
	public String getNamespace() {
		return "com.yonyou.iuap.crm.basedata.entity";
	}

	public String getPk_competrelation() {
		return pk_competrelation;
	}

	public void setPk_competrelation(String pk_competrelation) {
		this.pk_competrelation = pk_competrelation;
	}

	public String getNvbatterypower() {
		return nvbatterypower;
	}

	public void setNvbatterypower(String nvbatterypower) {
		this.nvbatterypower = nvbatterypower;
	}
	
}