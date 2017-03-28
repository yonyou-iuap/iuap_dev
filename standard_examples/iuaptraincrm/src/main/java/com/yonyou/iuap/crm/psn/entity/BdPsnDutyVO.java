package com.yonyou.iuap.crm.psn.entity;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.yonyou.iuap.persistence.jdbc.framework.annotation.Column;
import com.yonyou.iuap.persistence.jdbc.framework.annotation.Entity;
import com.yonyou.iuap.persistence.jdbc.framework.annotation.GeneratedValue;
import com.yonyou.iuap.persistence.jdbc.framework.annotation.Id;
import com.yonyou.iuap.persistence.jdbc.framework.annotation.Stragegy;
import com.yonyou.iuap.persistence.jdbc.framework.annotation.Table;


/**
 * The persistent class for the bd_psnduty database table.
 * 
 */
@Entity
@Table(name="bd_psnduty")
public class BdPsnDutyVO extends com.yonyou.iuap.persistence.vo.BaseEntity implements Serializable {
	private static final long serialVersionUID = 1L;


	
	@Id
	@Column(name="pk_psnduty")
	@GeneratedValue(strategy = Stragegy.UAPOID, moudle = "pk_psnduty")
	private String pk_psnduty;

	@Override
	public String getMetaDefinedName() {
		return "BdPsnDutyVO";
	}

	@Override
	public String getNamespace() {
		return "com.yonyou.iuap.crm.psn.entity";
	}		
	@Column(name="def1")
	private String def1;

	@Column(name="def10")
	private String def10;

	@Column(name="def11")
	private String def11;

	@Column(name="def12")
	private String def12;

	@Column(name="def13")
	private String def13;

	@Column(name="def14")
	private String def14;

	@Column(name="def15")
	private String def15;

	@Column(name="def2")
	private String def2;

	@Column(name="def3")
	private String def3;

	@Column(name="def4")
	private String def4;

	@Column(name="def5")
	private String def5;

	@Column(name="def7")
	private String def7;

	@Column(name="def8")
	private String def8;

	@Column(name="def9")
	private String def9;

	@Column(name="dr")
	private int dr;

	@Column(name="dutystate")
	private int dutystate;

	@Column(name="pk_corp")
	private String pkCorp;

	@Column(name="pk_dept")
	private String pkDept;

	@Column(name="pk_psndoc")
	private String pk_psndoc;

	private String ts;

	public BdPsnDutyVO() {
	}

	public String getPk_psnduty() {
		return this.pk_psnduty;
	}

	public void setPk_psnduty(String pkPsnduty) {
		this.pk_psnduty = pkPsnduty;
	}

	public String getDef1() {
		return this.def1;
	}

	public void setDef1(String def1) {
		this.def1 = def1;
	}

	public String getDef10() {
		return this.def10;
	}

	public void setDef10(String def10) {
		this.def10 = def10;
	}

	public String getDef11() {
		return this.def11;
	}

	public void setDef11(String def11) {
		this.def11 = def11;
	}

	public String getDef12() {
		return this.def12;
	}

	public void setDef12(String def12) {
		this.def12 = def12;
	}

	public String getDef13() {
		return this.def13;
	}

	public void setDef13(String def13) {
		this.def13 = def13;
	}

	public String getDef14() {
		return this.def14;
	}

	public void setDef14(String def14) {
		this.def14 = def14;
	}

	public String getDef15() {
		return this.def15;
	}

	public void setDef15(String def15) {
		this.def15 = def15;
	}

	public String getDef2() {
		return this.def2;
	}

	public void setDef2(String def2) {
		this.def2 = def2;
	}

	public String getDef3() {
		return this.def3;
	}

	public void setDef3(String def3) {
		this.def3 = def3;
	}

	public String getDef4() {
		return this.def4;
	}

	public void setDef4(String def4) {
		this.def4 = def4;
	}

	public String getDef5() {
		return this.def5;
	}

	public void setDef5(String def5) {
		this.def5 = def5;
	}

	public String getDef7() {
		return this.def7;
	}

	public void setDef7(String def7) {
		this.def7 = def7;
	}

	public String getDef8() {
		return this.def8;
	}

	public void setDef8(String def8) {
		this.def8 = def8;
	}

	public String getDef9() {
		return this.def9;
	}

	public void setDef9(String def9) {
		this.def9 = def9;
	}

	public int getDr() {
		return this.dr;
	}

	public void setDr(int dr) {
		this.dr = dr;
	}

	public int getDutystate() {
		return this.dutystate;
	}

	public void setDutystate(int dutystate) {
		this.dutystate = dutystate;
	}

	public String getPkCorp() {
		return this.pkCorp;
	}

	public void setPkCorp(String pkCorp) {
		this.pkCorp = pkCorp;
	}

	public String getPkDept() {
		return this.pkDept;
	}

	public void setPkDept(String pkDept) {
		this.pkDept = pkDept;
	}

	public String getPk_psndoc() {
		return this.pk_psndoc;
	}

	public void setPk_psndoc(String pk_psndoc) {
		this.pk_psndoc = pk_psndoc;
	}

	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+08:00")
	public String getTs() {
		return this.ts;
	}

	public void setTs(String ts) {
		this.ts = ts;
	}

}