package com.yonyou.iuap.crm.basedata.entity;

import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.yonyou.iuap.crm.common.validator.NotDuplicateValid;
import com.yonyou.iuap.persistence.jdbc.framework.annotation.Column;
import com.yonyou.iuap.persistence.jdbc.framework.annotation.Entity;
import com.yonyou.iuap.persistence.jdbc.framework.annotation.GeneratedValue;
import com.yonyou.iuap.persistence.jdbc.framework.annotation.Id;
import com.yonyou.iuap.persistence.jdbc.framework.annotation.Stragegy;
import com.yonyou.iuap.persistence.jdbc.framework.annotation.Table;
import com.yonyou.iuap.persistence.vo.BaseEntity;


/**
 * The persistent class for the tm_brand database table.
 * 
 */
@Entity
@Table(name="tm_brand")
@JsonIgnoreProperties(ignoreUnknown = true)
@NotDuplicateValid(claz=TmBrandVO.class,field={"vbrandcode","vbrandname"},message="品牌名称或编码不能重复")
public class TmBrandVO extends BaseEntity {
	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy=Stragegy.UAPOID,moudle="tm_brand")
	@Column(name = "pk_brand")
	private String pk_brand;				// 品牌主键
	@Column(name = "vbrandcode")
	@NotNull(message = "{品牌编码不可为空}")
	private String vbrandcode;				// 品牌编码
	@Column(name = "vbrandname")
	@NotNull(message = "{品牌名称不可为空}")
	private String vbrandname;				// 品牌名称
	@Column(name = "pk_org")
	private String pk_org;					// 所属组织
	@Column(name = "vstatus")
	private String vstatus;					// 状态： 启用、停用
	@Column(name = "creator")
	private String creator;					// 创建者
	@Column(name = "creationtime")
	private String creationtime;			// 创建时间
	@Column(name = "modifier")
	private String modifier;				// 修改者
	@Column(name = "modifiedtime")
	private String modifiedtime;			// 修改时间
	@Column(name = "ts")
	private String ts;						// 时间戳
	@Column(name = "dr")
	private int dr;							// 脏位： 逻辑删除

	public TmBrandVO() {
	}


	public String getPk_brand() {
		return this.pk_brand;
	}

	public void setPk_brand(String pk_brand) {
		this.pk_brand = pk_brand;
	}


	public String getVbrandcode() {
		return this.vbrandcode;
	}

	public void setVbrandcode(String vbrandcode) {
		this.vbrandcode = vbrandcode;
	}


	public String getVbrandname() {
		return this.vbrandname;
	}

	public void setVbrandname(String vbrandname) {
		this.vbrandname = vbrandname;
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
        return "tm_brand";
    }

    @Override
    public String getNamespace() {
		return "com.yonyou.iuap.crm.basedata.entity";
    }
}