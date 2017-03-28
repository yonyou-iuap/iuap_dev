package com.yonyou.iuap.crm.corp.entity;

import java.io.Serializable;


import java.sql.Timestamp;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.yonyou.iuap.persistence.jdbc.framework.annotation.Column;
import com.yonyou.iuap.persistence.jdbc.framework.annotation.Entity;
import com.yonyou.iuap.persistence.jdbc.framework.annotation.GeneratedValue;
import com.yonyou.iuap.persistence.jdbc.framework.annotation.Id;
import com.yonyou.iuap.persistence.jdbc.framework.annotation.Stragegy;
import com.yonyou.iuap.persistence.jdbc.framework.annotation.Table;


/**
 * The persistent class for the corp database table.
 * 
 */

@Entity(namespace = "iuaptraincrm",name = "org_test")
@Table(name="bd_corp")
public class BdCorpVO extends com.yonyou.iuap.persistence.vo.BaseEntity implements Serializable {
	                            
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="pk_corp")
	@GeneratedValue(strategy = Stragegy.UUID, moudle = "pk_corp")
	private String pk_corp;


	@Column(name="creationtime")
	private String creationtime;
	
	@Column(name="creator")
	private String creator;

	@Column(name="def1")
	private String def1;

	@Column(name="def10")
	private String def10;

	@Column(name="def2")
	private String def2;

	@Column(name="def3")
	private String def3;

	@Column(name="def4")
	private String def4;

	@Column(name="def5")
	private String def5;

	@Column(name="def6")
	private String def6;

	@Column(name="def7")
	private String def7;

	@Column(name="def8")
	private String def8;

	@Column(name="def9")
	private String def9;

	@Column(name="dr")
	private int dr;

	@Column(name="fathercorp")
	private String fathercorp;

	@Column(name="isseal")
	private int isseal;

	@Column(name="memo")
	private String memo;

	@Column(name="modifiedtime")
	private String modifiedtime;

	@Column(name="modifier")
	private String modifier;

	@Column(name="pk_user")
	private String pk_user;

	@Column(name="ts")
	private Timestamp ts;
	
	@Column(name="unitcode")
	private String unitcode;
	
	
	@Column(name="unitname")
	private String unitname;
	
	@Column(name="corptype")
	private int corptype;	
	
	@Column(name="corplevel")
	private int corplevel;		
	
	@Override
	public String getMetaDefinedName() {
		return "BdCorpVO";
	}

	@Override
	public String getNamespace() {
		return "com.yonyou.iuap.crm.corp.entity";
	}	

	public BdCorpVO() {
	}

	public String getPk_corp() {
		return this.pk_corp;
	}

	public void setPk_corp(String pkCorp) {
		this.pk_corp = pkCorp;
	}
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+08:00")
	public String getCreationtime() {
		return this.creationtime;
	}

	public void setCreationtime(String creationtime) {
		this.creationtime = creationtime;
	}

	public String getCreator() {
		return this.creator;
	}

	public void setCreator(String creator) {
		this.creator = creator;
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

	public String getDef6() {
		return this.def6;
	}

	public void setDef6(String def6) {
		this.def6 = def6;
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

	public String getFathercorp() {
		return this.fathercorp;
	}

	public void setFathercorp(String fathercorp) {
		this.fathercorp = fathercorp;
	}

	public int getIsseal() {
		return this.isseal;
	}

	public void setIsseal(int isseal) {
		this.isseal = isseal;
	}

	public String getMemo() {
		return this.memo;
	}

	public void setMemo(String memo) {
		this.memo = memo;
	}
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+08:00")
	public String getModifiedtime() {
		return this.modifiedtime;
	}

	public void setModifiedtime(String modifiedtime) {
		this.modifiedtime = modifiedtime;
	}

	public String getModifier() {
		return this.modifier;
	}

	public void setModifier(String modifier) {
		this.modifier = modifier;
	}

	public String getPk_user() {
		return this.pk_user;
	}

	public void setPk_user(String pkUser) {
		this.pk_user = pkUser;
	}
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+08:00")
	public Timestamp getTs() {
		return this.ts;
	}

	public void setTs(Timestamp ts) {
		this.ts = ts;
	}

	public String getUnitcode() {
		return this.unitcode;
	}

	public void setUnitcode(String unitcode) {
		this.unitcode = unitcode;
	}

	public String getUnitname() {
		return this.unitname;
	}

	public void setUnitname(String unitname) {
		this.unitname = unitname;
	}

	public int getCorptype() {
		return corptype;
	}

	public void setCorptype(int corptype) {
		this.corptype = corptype;
	}

	public int getCorplevel() {
		return corplevel;
	}

	public void setCorplevel(int corplevel) {
		this.corplevel = corplevel;
	}
	

}