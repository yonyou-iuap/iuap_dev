package com.yonyou.iuap.crm.dept.entity;

import java.io.Serializable;
import java.util.Date;

import com.yonyou.iuap.persistence.jdbc.framework.annotation.Column;
import com.yonyou.iuap.persistence.jdbc.framework.annotation.Entity;
import com.yonyou.iuap.persistence.jdbc.framework.annotation.GeneratedValue;
import com.yonyou.iuap.persistence.jdbc.framework.annotation.Id;
import com.yonyou.iuap.persistence.jdbc.framework.annotation.Stragegy;
import com.yonyou.iuap.persistence.jdbc.framework.annotation.Table;


/**
 * The persistent class for the bd_dept database table.
 * 
 */
@Entity
@Table(name="bd_dept")
public class BdDeptVO extends com.yonyou.iuap.persistence.vo.BaseEntity implements Serializable {
	                            
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="pk_dept")
	@GeneratedValue(strategy = Stragegy.UUID, moudle = "pk_dept")
	private String pk_dept;


	@Column(name="creationtime")
	private Date creationtime;
	@Column(name="canceled")
	private int canceled;

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

	@Column(name="pk_corp")
	private String pk_corp;

	@Column(name="deptlevel")
	private int deptlevel;	

	@Column(name="modifiedtime")
	private Date modifiedtime;

	@Column(name="modifier")
	private String modifier;

	@Column(name="pk_user")
	private String pk_user;

	@Column(name="ts")
	private Date ts;
	@Column(name="pk_fathedept")
	private String pkFathedept;

	@Column(name="fathedept")
	private String fathedept;
	
	@Column(name="pk_leader")
	private String pkLeader;

	@Column(name="deptcode")
	private String deptcode;
	@Column(name="deptname")

	private String deptname;
	
	@Column(name="iscbzx")
	private String iscbzx;
	
	@Column(name="cbzx")
	private String cbzx;
	
	public String getCbzx() {
		return cbzx;
	}

	public void setCbzx(String cbzx) {
		this.cbzx = cbzx;
	}

	public String getIscbzx() {
		return iscbzx;
	}

	public void setIscbzx(String iscbzx) {
		this.iscbzx = iscbzx;
	}

	public String getIsbudept() {
		return isbudept;
	}

	public void setIsbudept(String isbudept) {
		this.isbudept = isbudept;
	}

	@Column(name="isbudept")
	private String isbudept;
	@Override
	public String getMetaDefinedName() {
		return "BdDeptVO";
	}

	@Override
	public String getNamespace() {
		return "com.yonyou.iuap.crm.dept.entity";
	}	

	public BdDeptVO() {
	}


	public String getPk_dept() {
		return pk_dept;
	}

	public void setPk_dept(String pk_dept) {
		this.pk_dept = pk_dept;
	}

	public Date getCreationtime() {
		return this.creationtime;
	}

	public void setCreationtime(Date creationtime) {
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

	public String getPk_corp() {
		return this.pk_corp;
	}
	
	public int getDeptlevel() {
		return deptlevel;
	}

	public void setDeptlevel(int deptlevel) {
		this.deptlevel = deptlevel;
	}

	public void setPk_corp(String pkCorp) {
		this.pk_corp = pkCorp;
	}


	public Date getModifiedtime() {
		return this.modifiedtime;
	}

	public void setModifiedtime(Date modifiedtime) {
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

	public Date getTs() {
		return this.ts;
	}

	public void setTs(Date ts) {
		this.ts = ts;
	}

	public String getPkFathedept() {
		return this.pkFathedept;
	}

	public String getFathedept() {
		return fathedept;
	}

	public void setFathedept(String fathedept) {
		this.fathedept = fathedept;
	}

	public void setPkFathedept(String pkFathedept) {
		this.pkFathedept = pkFathedept;
	}

	public String getPkLeader() {
		return this.pkLeader;
	}

	public void setPkLeader(String pkLeader) {
		this.pkLeader = pkLeader;
	}

	public int getCanceled() {
		return this.canceled;
	}

	public void setCanceled(int canceled) {
		this.canceled = canceled;
	}
	public String getDeptcode() {
		return this.deptcode;
	}

	public void setDeptcode(String deptcode) {
		this.deptcode = deptcode;
	}

	public String getDeptname() {
		return this.deptname;
	}

	public void setDeptname(String deptname) {
		this.deptname = deptname;
	}

}