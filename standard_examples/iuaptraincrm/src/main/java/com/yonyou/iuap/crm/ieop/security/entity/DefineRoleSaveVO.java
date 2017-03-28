package com.yonyou.iuap.crm.ieop.security.entity;
import java.sql.Timestamp;

import com.yonyou.iuap.persistence.jdbc.framework.annotation.Column;
import com.yonyou.iuap.persistence.jdbc.framework.annotation.Entity;
import com.yonyou.iuap.persistence.jdbc.framework.annotation.GeneratedValue;
import com.yonyou.iuap.persistence.jdbc.framework.annotation.Id;
import com.yonyou.iuap.persistence.jdbc.framework.annotation.Stragegy;
import com.yonyou.iuap.persistence.jdbc.framework.annotation.Table;
import com.yonyou.iuap.persistence.vo.BaseEntity;
	
@Entity
@Table(name="ieop_role")
public class DefineRoleSaveVO extends BaseEntity implements java.io.Serializable
{
  private static final long serialVersionUID = 1L;
  @Id
  @Column(name="id")
  @GeneratedValue(strategy=Stragegy.UAPOID,moudle="ieop_role")
  private String id;
  @Column(name="role_name")
  private String role_name;
  @Column(name="role_code")
  private String role_code;
  @Column(name="role_type")
  private String role_type;
  @Column(name="isactive")
  private String isactive;
  @Column(name="create_date")
  private Timestamp create_date;
  @Column(name="data_role")
  private String dataRole;
  @Column(name="ts")
  private String ts;
  @Column(name="dr")
  private int dr;

	public DefineRoleSaveVO() {
	}


	public String getId() {
		return this.id;
	}

	public void setId(String id) {
		this.id = id;
	}

	@Override
    public String getMetaDefinedName() {
        return "ieop_role";
    }

    @Override
    public String getNamespace() {
		return "com.yonyou.iuap.crm.ieop.security.entity";
    }


	public String getDataRole() {
		return dataRole;
	}


	public void setDataRole(String dataRole) {
		this.dataRole = dataRole;
	}


	public String getTs() {
		return ts;
	}


	public String getRole_name() {
		return role_name;
	}


	public void setRole_name(String role_name) {
		this.role_name = role_name;
	}


	public String getRole_code() {
		return role_code;
	}


	public void setRole_code(String role_code) {
		this.role_code = role_code;
	}


	public String getRole_type() {
		return role_type;
	}


	public void setRole_type(String role_type) {
		this.role_type = role_type;
	}


	public String getIsactive() {
		return isactive;
	}


	public void setIsactive(String isactive) {
		this.isactive = isactive;
	}


	public Timestamp getCreate_date() {
		return create_date;
	}


	public void setCreate_date(Timestamp create_date) {
		this.create_date = create_date;
	}


	public void setTs(String ts) {
		this.ts = ts;
	}


	public int getDr() {
		return dr;
	}


	public void setDr(int dr) {
		this.dr = dr;
	}
}