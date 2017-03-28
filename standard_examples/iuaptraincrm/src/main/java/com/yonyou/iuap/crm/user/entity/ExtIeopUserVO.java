package com.yonyou.iuap.crm.user.entity;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Date;

import javax.persistence.Temporal;
import javax.persistence.TemporalType;

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
@Entity(namespace = "iuaptraincrm",name = "ieop_user")
@Table(name="ieop_user")
public class ExtIeopUserVO extends com.yonyou.iuap.persistence.vo.BaseEntity implements Serializable {
	                            
	private static final long serialVersionUID = 1L;
	@Id
	@Column(name="id")
	@GeneratedValue(strategy = Stragegy.UUID, moudle = "id")
	private String id;	
	
	@Override
	public String getMetaDefinedName() {
		return "IeopUserVO";
	}

	@Override
	public String getNamespace() {
		return "com.yonyou.iuap.crm.user.entity";
	}		


	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="creationtime")

	private Date creationtime;
	@Column(name="creator")

	private String creator;
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
	@Column(name="email")

	private String email;
	@Column(name="locked")

	private int locked;

	@Column(name="login_name")
	private String loginName;
	@Column(name="modifiedtime")

	@Temporal(TemporalType.TIMESTAMP)
	private Date modifiedtime;
	@Column(name="modifier")

	private String modifier;
	@Column(name="name")

	private String name;
	@Column(name="passrank")

	private String passrank;
	@Column(name="password")

	private String password;

	@Column(name="pk_corp")
	private String pkCorp;

	@Column(name="pk_dept")
	private String pkDept;

	@Column(name="pk_psndoc")
	private String pkPsndoc;

	@Column(name="pk_usertype")
	private String pkUsertype;
	@Column(name="psnseal")

	private int psnseal;
	@Column(name="psntel")

	private String psntel;

	@Column(name="register_date")
	private Date registerDate;
	@Column(name="roles")

	private String roles;
	@Column(name="salt")

	private String salt;
	@Column(name="states")

	private String states;
	@Column(name="pk_propt")

	private String pk_propt;
	@Column(name="tenant_userid")
	private String tenantUserid;
	@Column(name="lockedT")

	private String lockedT;
	@Column(name="userattr")

	private String userattr;
	@Temporal(TemporalType.TIMESTAMP)
	private Timestamp ts;

	public ExtIeopUserVO() {
	}

	public String getId() {
		return this.id;
	}

	public void setId(String id) {
		this.id = id;
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
	public String getPk_propt() {
		return this.pk_propt;
	}

	public void setPk_propt(String pk_propt) {
		this.pk_propt = pk_propt;
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

	public String getEmail() {
		return this.email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public int getLocked() {
		return this.locked;
	}

	public void setLocked(int locked) {
		this.locked = locked;
	}

	public String getLoginName() {
		return this.loginName;
	}

	public void setLoginName(String loginName) {
		this.loginName = loginName;
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

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPassrank() {
		return this.passrank;
	}

	public void setPassrank(String passrank) {
		this.passrank = passrank;
	}

	public String getPassword() {
		return this.password;
	}

	public void setPassword(String password) {
		this.password = password;
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

	public String getPkPsndoc() {
		return this.pkPsndoc;
	}

	public void setPkPsndoc(String pkPsndoc) {
		this.pkPsndoc = pkPsndoc;
	}

	public String getPkUsertype() {
		return this.pkUsertype;
	}

	public void setPkUsertype(String pkUsertype) {
		this.pkUsertype = pkUsertype;
	}

	public int getPsnseal() {
		return this.psnseal;
	}

	public void setPsnseal(int psnseal) {
		this.psnseal = psnseal;
	}

	public String getPsntel() {
		return this.psntel;
	}

	public void setPsntel(String psntel) {
		this.psntel = psntel;
	}

	public Date getRegisterDate() {
		return this.registerDate;
	}

	public void setRegisterDate(Date registerDate) {
		this.registerDate = registerDate;
	}

	public String getRoles() {
		return this.roles;
	}

	public void setRoles(String roles) {
		this.roles = roles;
	}
	
	public String getSalt() {
		return this.salt;
	}

	public void setSalt(String salt) {
		this.salt = salt;
	}

	public String getStates() {
		return this.states;
	}

	public void setStates(String states) {
		this.states = states;
	}

	public String getTenantUserid() {
		return this.tenantUserid;
	}

	public void setTenantUserid(String tenantUserid) {
		this.tenantUserid = tenantUserid;
	}

	public Timestamp getTs() {
		return this.ts;
	}

	public void setTs(Timestamp ts) {
		this.ts = ts;
	}

	
	public String getLockedT() {
		//return this.lockedT;
//		return "正常d";
		int locked= getLocked();
		int psntel= getPsnseal();
		if(locked==1 &&psntel==1){
			return "正常";
		}else{
			if(psntel==0){
				return "停用";

			}else{
				if(locked==0){
					return "锁定";

				}
			}
		}
			return "正常";
	}

	public void setLockedT(String lockedT) {
		this.lockedT = lockedT;
	}
	public String getUserattr() {
		return this.userattr;
	}

	public void setUserattr(String userattr) {
		this.userattr = userattr;
	}
}