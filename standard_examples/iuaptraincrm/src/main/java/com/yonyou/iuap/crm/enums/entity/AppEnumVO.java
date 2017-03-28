package com.yonyou.iuap.crm.enums.entity;

import com.yonyou.iuap.persistence.jdbc.framework.annotation.Column;
import com.yonyou.iuap.persistence.jdbc.framework.annotation.Entity;
import com.yonyou.iuap.persistence.jdbc.framework.annotation.GeneratedValue;
import com.yonyou.iuap.persistence.jdbc.framework.annotation.Id;
import com.yonyou.iuap.persistence.jdbc.framework.annotation.Stragegy;
import com.yonyou.iuap.persistence.jdbc.framework.annotation.Table;
import com.yonyou.iuap.persistence.vo.BaseEntity;
import java.util.Date;
	

/**
 * The persistent class for the tc_enumcode database table.
 * 
 */
@Entity
@Table(name="tc_enumcode")
public class AppEnumVO extends BaseEntity {
	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy=Stragegy.UAPOID,moudle="tc_enumcode")
	@Column(name = "pk_enumcode")
	private String pk_enumcode;
	@Column(name = "vtype")
	private String vtype;
	@Column(name = "vtypename")
	private String vtypename;
	@Column(name = "vcode")
	private String vcode;
	@Column(name = "vname")
	private String vname;
	@Column(name = "ts")
	private Date ts;
	@Column(name = "dr")
	private int dr;

	public AppEnumVO() {
	}


	public String getPk_enumcode() {
		return this.pk_enumcode;
	}

	public void setPk_enumcode(String pk_enumcode) {
		this.pk_enumcode = pk_enumcode;
	}


	public String getVtype() {
		return this.vtype;
	}

	public void setVtype(String vtype) {
		this.vtype = vtype;
	}


	public String getVtypename() {
		return this.vtypename;
	}

	public void setVtypename(String vtypename) {
		this.vtypename = vtypename;
	}


	public String getVcode() {
		return this.vcode;
	}

	public void setVcode(String vcode) {
		this.vcode = vcode;
	}


	public String getVname() {
		return this.vname;
	}

	public void setVname(String vname) {
		this.vname = vname;
	}


	public Date getTs() {
		return this.ts;
	}

	public void setTs(Date ts) {
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
        return "tc_enumcode";
    }

    @Override
    public String getNamespace() {
		return "com.yonyou.iuap.crm.enums.entity";
    }
}