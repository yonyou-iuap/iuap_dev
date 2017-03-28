package com.yonyou.iuap.crm.basedata.entity;

import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.yonyou.iuap.persistence.jdbc.framework.annotation.Column;
import com.yonyou.iuap.persistence.jdbc.framework.annotation.Entity;
import com.yonyou.iuap.persistence.jdbc.framework.annotation.GeneratedValue;
import com.yonyou.iuap.persistence.jdbc.framework.annotation.Id;
import com.yonyou.iuap.persistence.jdbc.framework.annotation.Stragegy;
import com.yonyou.iuap.persistence.jdbc.framework.annotation.Table;
import com.yonyou.iuap.persistence.vo.BaseEntity;


/**
 * The persistent class for the tm_vehicleclassify database table.
 * 
 */
@Entity
@JsonIgnoreProperties(ignoreUnknown=true)
@Table(name="tm_vehicleclassify")
public class TmVehicleClassifyVO extends BaseEntity {
	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy=Stragegy.UAPOID,moudle="tm_vehicleclassify")
	@Column(name = "pk_vehicleclassify")
	private String pk_vehicleclassify;			// 车辆类别主键
	@Column(name = "pk_brand")
	@NotNull(message = "{所属品牌不可为空}")
	private String pk_brand;					// 品牌外键
	@Column(name = "vclasscode")
	@NotNull(message = "{车辆类别编码不可为空}")
	private String vclasscode;					// 车辆类别编码
	@Column(name = "vclassname")
	@NotNull(message = "{车辆类别名称不可为空}")
	private String vclassname;					// 车辆类别名称
	@Column(name = "pk_org")
	private String pk_org;						// 所属组织
	@Column(name = "vstatus")
	private String vstatus;						// 状态：启用、停用
	@Column(name = "creator")
	private String creator;						// 创建者
	@Column(name = "creationtime")
	private String creationtime;				// 创建时间
	@Column(name = "modifier")
	private String modifier;					// 修改者
	@Column(name = "modifiedtime")
	private String modifiedtime;				// 修改时间
	@Column(name = "ts")
	private String ts;							// 时间戳
	@Column(name = "dr")
	private int dr;								// 脏位

	public TmVehicleClassifyVO() {
	}


	public String getPk_vehicleclassify() {
		return this.pk_vehicleclassify;
	}

	public void setPk_vehicleclassify(String pk_vehicleclassify) {
		this.pk_vehicleclassify = pk_vehicleclassify;
	}


	public String getPk_brand() {
		return this.pk_brand;
	}

	public void setPk_brand(String pk_brand) {
		this.pk_brand = pk_brand;
	}


	public String getVclasscode() {
		return this.vclasscode;
	}

	public void setVclasscode(String vclasscode) {
		this.vclasscode = vclasscode;
	}


	public String getVclassname() {
		return this.vclassname;
	}

	public void setVclassname(String vclassname) {
		this.vclassname = vclassname;
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
        return "tm_vehicleclassify";
    }

    @Override
    public String getNamespace() {
		return "com.yonyou.iuap.crm.basedata.entity";
    }
}