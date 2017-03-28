
package com.yonyou.iuap.crm.user.entity;

import java.io.Serializable;
import java.sql.Timestamp;

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
@Table(name="ieop_role")
public class ExtIeopRoleVO extends com.yonyou.iuap.persistence.vo.BaseEntity implements Serializable {
	                            
	private static final long serialVersionUID = 1L;
	
	@Override
	public String getMetaDefinedName() {
		return "IeopRoleVO";
	}

	@Override
	public String getNamespace() {
		return "com.yonyou.iuap.crm.user.entity";
	}		

	@Id
	@Column(name="id")
	@GeneratedValue(strategy = Stragegy.UUID, moudle = "id")
	private String id;	
	@Column(name="role_name")
	private String roleName;

	@Column(name="role_code")
	private String roleCode;

	@Column(name="role_type")
	private String roleType;

	@Column(name="isactive")
	private String isActive;

	@Column(name="create_date")
	private Timestamp createDate;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getRoleName() {
		return roleName;
	}

	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}

	public String getRoleCode() {
		return roleCode;
	}

	public void setRoleCode(String roleCode) {
		this.roleCode = roleCode;
	}

	public String getRoleType() {
		return roleType;
	}

	public void setRoleType(String roleType) {
		this.roleType = roleType;
	}

	public String getIsActive() {
		return isActive;
	}

	public void setIsActive(String isActive) {
		this.isActive = isActive;
	}

	public Timestamp getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Timestamp createDate) {
		this.createDate = createDate;
	}
}