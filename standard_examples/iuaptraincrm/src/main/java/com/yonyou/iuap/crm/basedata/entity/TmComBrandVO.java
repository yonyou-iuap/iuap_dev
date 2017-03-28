package com.yonyou.iuap.crm.basedata.entity;

import com.yonyou.iuap.persistence.jdbc.framework.annotation.*;
import com.yonyou.iuap.persistence.vo.BaseEntity;


/**
 * The persistent class for the tm_combrand database table.
 * 
 */
@Entity
@Table(name="tm_combrand")
//@NotDuplicateValid(claz=TmComBrandVO.class,field={"vcbrandcode","vcbrandname"},message="编码或名称不能重复")
public class TmComBrandVO extends BaseEntity {
	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy=Stragegy.UAPOID,moudle="tm_combrand")
	@Column(name = "pk_combrand")
	private String pk_combrand;
	
	@Column(name = "vcbrandcode")
	private String vcbrandcode;
	@Column(name = "vcbrandname")
	private String vcbrandname;
	@Column(name = "pk_org")
	private String pk_org;
	@Column(name = "vfactory")
	private String vfactory;
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

	public TmComBrandVO() {
	}


	public String getPk_combrand() {
		return this.pk_combrand;
	}

	public void setPk_combrand(String pk_combrand) {
		this.pk_combrand = pk_combrand;
	}


	public String getVcbrandcode() {
		return this.vcbrandcode;
	}

	public void setVcbrandcode(String vcbrandcode) {
		this.vcbrandcode = vcbrandcode;
	}


	public String getVcbrandname() {
		return this.vcbrandname;
	}

	public void setVcbrandname(String vcbrandname) {
		this.vcbrandname = vcbrandname;
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

	public String getVfactory() {
		return vfactory;
	}

	public void setVfactory(String vfactory) {
		this.vfactory = vfactory;
	}

	@Override
    public String getMetaDefinedName() {
        return "tm_combrand";
    }

    @Override
    public String getNamespace() {
		return "com.yonyou.iuap.crm.basedata.entity";
    }
}