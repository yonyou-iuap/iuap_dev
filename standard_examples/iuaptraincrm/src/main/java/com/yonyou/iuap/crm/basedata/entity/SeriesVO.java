package com.yonyou.iuap.crm.basedata.entity;

import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.yonyou.iuap.persistence.jdbc.framework.annotation.Column;
import com.yonyou.iuap.persistence.jdbc.framework.annotation.Entity;
import com.yonyou.iuap.persistence.jdbc.framework.annotation.GeneratedValue;
import com.yonyou.iuap.persistence.jdbc.framework.annotation.Id;
import com.yonyou.iuap.persistence.jdbc.framework.annotation.Stragegy;
import com.yonyou.iuap.persistence.jdbc.framework.annotation.Table;
import com.yonyou.iuap.persistence.vo.BaseEntity;


/**
 * The persistent class for the tm_series database table.
 * 
 */
@Entity
@Table(name="tm_series")
public class SeriesVO extends BaseEntity {
	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy=Stragegy.UUID,moudle="tm_series")
	@Column(name = "pk_series")
	private String pk_series;//改为pkseries
	@Column(name = "pk_vehicleclassify")
	private String pk_vehicleclassify;//改为pkvehicleclassify
	@Column(name = "vseriescode")
	private String vseriescode;
	@Column(name = "vseriesname")
	private String vseriesname;
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
	@Column(name = "vstatus")
	private String vstatus;
	@Column(name = "vcomments")
	private String vcomments;

	public SeriesVO() {
	}


	public String getPk_series() {
		return this.pk_series;
	}

	public void setPk_series(String pk_series) {
		this.pk_series = pk_series;
	}


	public String getPk_vehicleclassify() {
		return this.pk_vehicleclassify;
	}

	public void setPk_vehicleclassify(String pk_vehicleclassify) {
		this.pk_vehicleclassify = pk_vehicleclassify;
	}

	@NotNull(message="{code.not.empty}")
	//@Pattern(regexp="[a-zA-Z0-9]+",message="{code.input.valid}")
	public String getVseriescode() {
		return this.vseriescode;
	}

	public void setVseriescode(String vseriescode) {
		this.vseriescode = vseriescode;
	}


	public String getVseriesname() {
		return this.vseriesname;
	}

	public void setVseriesname(String vseriesname) {
		this.vseriesname = vseriesname;
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

	// 设定JSON序列化时的日期格式
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
	
	public String getVstatus() {
		return this.vstatus;
	}

	public void setVstatus(String vstatus) {
		this.vstatus = vstatus;
	}

	@Override
    public String getMetaDefinedName() {
        return "tm_series";
    }

    @Override
    public String getNamespace() {
		return "com.yonyou.iuap.crm.basedata.entity";
    }


	public String getVcomments() {
		return vcomments;
	}


	public void setVcomments(String vcomments) {
		this.vcomments = vcomments;
	}
}