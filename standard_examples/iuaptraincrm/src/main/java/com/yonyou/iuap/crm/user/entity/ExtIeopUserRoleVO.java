package com.yonyou.iuap.crm.user.entity;

import java.io.Serializable;

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
@Table(name="ieop_role_user")
public class ExtIeopUserRoleVO extends com.yonyou.iuap.persistence.vo.BaseEntity implements Serializable {
	                            
	private static final long serialVersionUID = 1L;
	@Id
	@Column(name="id")
	@GeneratedValue(strategy = Stragegy.UUID, moudle = "id")
	private String id;	
	
	@Override
	public String getMetaDefinedName() {
		return "IeopUserRoleVO";
	}

	@Override
	public String getNamespace() {
		return "com.yonyou.iuap.crm.user.entity";
	}		


	@Column(name="role_id")
	private String roleID;

	@Column(name="role_code")
	private String roleCode;
	@Column(name="role_name")
	private String roleName;
	@Column(name="user_id")
	private String userID;

	@Column(name="user_code")
	private String userCode;
	

	public String getRoleID() {
		return roleID;
	}

	public void setRoleID(String roleID) {
		this.roleID = roleID;
	}

	public String getRoleCode() {
		return roleCode;
	}


	public void setRoleCode(String roleCode) {
		this.roleCode = roleCode;
	}
	
	public String getRoleName() {
		return roleName;
	}
	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}
	public String getUserID() {
		return userID;
	}

	public void setUserID(String userID) {
		this.userID = userID;
	}

	public String getUserCode() {
		return userCode;
	}

	public void setUserCode(String userCode) {
		this.userCode = userCode;
	}

}