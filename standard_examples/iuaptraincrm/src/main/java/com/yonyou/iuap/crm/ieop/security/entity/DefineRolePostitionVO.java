package com.yonyou.iuap.crm.ieop.security.entity;

import com.yonyou.iuap.persistence.jdbc.framework.annotation.Column;
import com.yonyou.iuap.persistence.jdbc.framework.annotation.Entity;
import com.yonyou.iuap.persistence.jdbc.framework.annotation.GeneratedValue;
import com.yonyou.iuap.persistence.jdbc.framework.annotation.Id;
import com.yonyou.iuap.persistence.jdbc.framework.annotation.Stragegy;
import com.yonyou.iuap.persistence.jdbc.framework.annotation.Table;
import com.yonyou.iuap.persistence.vo.BaseEntity;


/**
 * The persistent class for the ieop_role_position database table.
 * 
 */
@Entity
@Table(name="ieop_role_position")
public class DefineRolePostitionVO extends BaseEntity {
	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy=Stragegy.UUID,moudle="ieop_role_position")
	@Column(name = "id")
	private String id;
	@Column(name = "role_id")
	private String role_id;
	@Column(name = "role_code")
	private String role_code;
	@Column(name = "position_id")
	private String position_id;
	@Column(name = "position_code")
	private String position_code;
	@Column(name = "ts")
	private String ts;

	public DefineRolePostitionVO() {
	}


	public String getId() {
		return this.id;
	}

	public void setId(String id) {
		this.id = id;
	}


	public String getRole_id() {
		return this.role_id;
	}

	public void setRole_id(String role_id) {
		this.role_id = role_id;
	}


	public String getRole_code() {
		return this.role_code;
	}

	public void setRole_code(String role_code) {
		this.role_code = role_code;
	}


	public String getPosition_id() {
		return this.position_id;
	}

	public void setPosition_id(String position_id) {
		this.position_id = position_id;
	}


	public String getPosition_code() {
		return this.position_code;
	}

	public void setPosition_code(String position_code) {
		this.position_code = position_code;
	}


	public String getTs() {
		return this.ts;
	}

	public void setTs(String ts) {
		this.ts = ts;
	}

	@Override
    public String getMetaDefinedName() {
        return "ieop_role_position";
    }

    @Override
    public String getNamespace() {
		return "com.yonyou.iuap.crm.ieop.security.entity";
    }
}