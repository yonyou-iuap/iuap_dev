package com.yonyou.iuap.crm.billcode.entity;

import java.util.Date;

import com.yonyou.iuap.persistence.jdbc.framework.annotation.Column;
import com.yonyou.iuap.persistence.jdbc.framework.annotation.Entity;
import com.yonyou.iuap.persistence.jdbc.framework.annotation.GeneratedValue;
import com.yonyou.iuap.persistence.jdbc.framework.annotation.Id;
import com.yonyou.iuap.persistence.jdbc.framework.annotation.Stragegy;
import com.yonyou.iuap.persistence.jdbc.framework.annotation.Table;
import com.yonyou.iuap.persistence.vo.BaseEntity;
import com.yonyou.uap.billcode.model.IBillCodeBaseVO;
	

/**
 * The persistent class for the pub_bcr_rulebase database table.
 * 
 */
@Entity
@Table(name="pub_bcr_rulebase")
public class ExtRuleBaseVO extends BaseEntity implements IBillCodeBaseVO{
	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy=Stragegy.UUID,moudle="pub_bcr_rulebase")
	@Column(name = "pk_billcodebase")
	private String pk_billcodebase;
	@Column(name = "rulecode")
	private String rulecode;
	@Column(name = "rulename")
	private String rulename;
	@Column(name = "codemode")
	private String codemode;
	@Column(name = "iseditable")
	private String iseditable;
	@Column(name = "isautofill")
	private String isautofill;
	@Column(name = "format")
	private String format;
	@Column(name = "isdefault")
	private String isdefault;
	@Column(name = "isused")
	private String isused;
	@Column(name = "islenvar")
	private String islenvar;
	@Column(name = "isgetpk")
	private String isgetpk;
	@Column(name = "renterid")
	private String renterid;
	@Column(name = "sysid")
	private String sysid;
	@Column(name = "createdate")
	private Date createdate;

	public ExtRuleBaseVO() {
	}


	public String getPk_billcodebase() {
		return this.pk_billcodebase;
	}

	public void setPk_billcodebase(String pk_billcodebase) {
		this.pk_billcodebase = pk_billcodebase;
	}


	public String getRulecode() {
		return this.rulecode;
	}

	public void setRulecode(String rulecode) {
		this.rulecode = rulecode;
	}


	public String getRulename() {
		return this.rulename;
	}

	public void setRulename(String rulename) {
		this.rulename = rulename;
	}


	public String getCodemode() {
		return this.codemode;
	}

	public void setCodemode(String codemode) {
		this.codemode = codemode;
	}


	public String getIseditable() {
		return this.iseditable;
	}

	public void setIseditable(String iseditable) {
		this.iseditable = iseditable;
	}


	public String getIsautofill() {
		return this.isautofill;
	}

	public void setIsautofill(String isautofill) {
		this.isautofill = isautofill;
	}


	public String getFormat() {
		return this.format;
	}

	public void setFormat(String format) {
		this.format = format;
	}


	public String getIsdefault() {
		return this.isdefault;
	}

	public void setIsdefault(String isdefault) {
		this.isdefault = isdefault;
	}


	public String getIsused() {
		return this.isused;
	}

	public void setIsused(String isused) {
		this.isused = isused;
	}


	public String getIslenvar() {
		return this.islenvar;
	}

	public void setIslenvar(String islenvar) {
		this.islenvar = islenvar;
	}


	public String getIsgetpk() {
		return this.isgetpk;
	}

	public void setIsgetpk(String isgetpk) {
		this.isgetpk = isgetpk;
	}


	public String getRenterid() {
		return this.renterid;
	}

	public void setRenterid(String renterid) {
		this.renterid = renterid;
	}


	public String getSysid() {
		return this.sysid;
	}

	public void setSysid(String sysid) {
		this.sysid = sysid;
	}


	public Date getCreatedate() {
		return this.createdate;
	}

	public void setCreatedate(Date createdate) {
		this.createdate = createdate;
	}

	@Override
    public String getMetaDefinedName() {
        return "pub_bcr_rulebase";
    }

    @Override
    public String getNamespace() {
		return "com.yonyou.iuap.crm.billcode.entity";
    }


	@Override
	public String getStrPk_billcodebase() {
		return getPk_billcodebase();
	}


	@Override
	public String getStrRulecode() {
		// TODO 自动生成的方法存根
		return getRulecode();
	}


	@Override
	public String getStrCodemode() {
		return getCodemode();
	}


	@Override
	public boolean getBolIsEditable() {
		return getBoolean(getIseditable());
	}


	@Override
	public boolean isBolAutofill() {
		return getBoolean(getIsautofill());
	}


	@Override
	public boolean isBolGetRandomCode() {
		return getBoolean(getIsgetpk());
	}


	@Override
	public boolean isBolSNAppendZero() {
		return getBoolean(getIslenvar());
	}
	
	private boolean getBoolean(String YorN) {
		return YorN.trim().equals("Y");
	}
}