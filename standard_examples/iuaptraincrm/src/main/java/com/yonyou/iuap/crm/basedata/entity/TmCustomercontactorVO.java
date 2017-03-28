package com.yonyou.iuap.crm.basedata.entity;

import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.yonyou.iuap.persistence.jdbc.framework.annotation.Column;
import com.yonyou.iuap.persistence.jdbc.framework.annotation.Entity;
import com.yonyou.iuap.persistence.jdbc.framework.annotation.FK;
import com.yonyou.iuap.persistence.jdbc.framework.annotation.GeneratedValue;
import com.yonyou.iuap.persistence.jdbc.framework.annotation.Id;
import com.yonyou.iuap.persistence.jdbc.framework.annotation.Stragegy;
import com.yonyou.iuap.persistence.jdbc.framework.annotation.Table;
import com.yonyou.iuap.persistence.vo.BaseEntity;


/**
 * The persistent class for the tm_customercontactor database table.
 * 
 */
@Entity
@Table(name="tm_customercontactor")
@JsonIgnoreProperties(ignoreUnknown = true)
public class TmCustomercontactorVO extends BaseEntity {
	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy=Stragegy.UAPOID,moudle="tm_customercontactor")
	@Column(name = "pk_customercontactor")
	private String pk_customercontactor;
	@Column(name = "pk_customerinfo")
	@FK(name = "pk_customerinfo", referenceTableName = "tm_customerinfo", referencedColumnName = "pk_customerinfo")
	private String pk_customerinfo;
	@Column(name = "vname")
	@NotNull(message = "{vname.not.empty}")
	private String vname;
	@Column(name = "vphone")
	private String vphone;
	@Column(name = "vduty")
	private String vduty;
	@Column(name = "vcomments")
	private String vcomments;
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

	public TmCustomercontactorVO() {
	}


	public String getPk_customercontactor() {
		return this.pk_customercontactor;
	}

	public void setPk_customercontactor(String pk_customercontactor) {
		this.pk_customercontactor = pk_customercontactor;
	}


	public String getPk_customerinfo() {
		return this.pk_customerinfo;
	}

	public void setPk_customerinfo(String pk_customerinfo) {
		this.pk_customerinfo = pk_customerinfo;
	}


	public String getVname() {
		return this.vname;
	}

	public void setVname(String vname) {
		this.vname = vname;
	}


	public String getVphone() {
		return this.vphone;
	}

	public void setVphone(String vphone) {
		this.vphone = vphone;
	}


	public String getVduty() {
		return this.vduty;
	}

	public void setVduty(String vduty) {
		this.vduty = vduty;
	}


	public String getVcomments() {
		return this.vcomments;
	}

	public void setVcomments(String vcomments) {
		this.vcomments = vcomments;
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
        return "tm_customercontactor";
    }

    @Override
    public String getNamespace() {
		return "com.yonyou.iuap.crm.basedata.entity";
    }
}