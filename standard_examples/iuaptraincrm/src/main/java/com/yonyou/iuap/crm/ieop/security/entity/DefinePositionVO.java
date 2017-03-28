package com.yonyou.iuap.crm.ieop.security.entity;

import java.util.List;

import com.yonyou.iuap.persistence.jdbc.framework.annotation.Column;
import com.yonyou.iuap.persistence.jdbc.framework.annotation.Entity;
import com.yonyou.iuap.persistence.jdbc.framework.annotation.GeneratedValue;
import com.yonyou.iuap.persistence.jdbc.framework.annotation.Id;
import com.yonyou.iuap.persistence.jdbc.framework.annotation.Stragegy;
import com.yonyou.iuap.persistence.jdbc.framework.annotation.Table;
import com.yonyou.iuap.persistence.vo.BaseEntity;


/**
 * The persistent class for the ieop_position database table.
 * 
 */
@Entity
@Table(name="ieop_position")
public class DefinePositionVO extends BaseEntity {
	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy=Stragegy.UUID,moudle="ieop_position")
	@Column(name = "id")
	private String id;
	@Column(name = "position_name")
	private String position_name;
	@Column(name = "position_code")
	private String position_code;
	@Column(name = "pk_org")
	private String pk_org;
	@Column(name = "pk_dept")
	private String pk_dept;
	@Column(name = "position_status")
	private String position_status;
	@Column(name = "remark")
	private String remark;
	@Column(name = "def1")
	private String def1;
	@Column(name = "def2")
	private String def2;
	@Column(name = "def3")
	private String def3;
	@Column(name = "def4")
	private String def4;
	@Column(name = "def5")
	private String def5;
	@Column(name = "def6")
	private String def6;
	@Column(name = "def7")
	private String def7;
	@Column(name = "def8")
	private String def8;
	@Column(name = "def9")
	private String def9;
	@Column(name = "def10")
	private String def10;
	@Column(name = "creator")
	private String creator;
	@Column(name = "creationtime")
	private String creationtime;
	@Column(name = "modifier")
	private String modifier;
	@Column(name = "modifiedtime")
	private String modifiedtime;
	@Column(name = "ts")
	private String ts;
	@Column(name = "dr")
	private int dr;
	
	//存在角色
	private List<DefineRoleSaveVO> roleItems = null;

	public List<DefineRoleSaveVO> getRoleItems() {
		return roleItems;
	}


	public void setRoleItems(List<DefineRoleSaveVO> roleItems) {
		this.roleItems = roleItems;
	}


	public DefinePositionVO() {
	}


	public String getId() {
		return this.id;
	}

	public void setId(String id) {
		this.id = id;
	}


	public String getPosition_name() {
		return this.position_name;
	}

	public void setPosition_name(String position_name) {
		this.position_name = position_name;
	}


	public String getPosition_code() {
		return this.position_code;
	}

	public void setPosition_code(String position_code) {
		this.position_code = position_code;
	}


	public String getPk_org() {
		return this.pk_org;
	}

	public void setPk_org(String pk_org) {
		this.pk_org = pk_org;
	}


	public String getPk_dept() {
		return this.pk_dept;
	}

	public void setPk_dept(String pk_dept) {
		this.pk_dept = pk_dept;
	}


	public String getPosition_status() {
		return this.position_status;
	}

	public void setPosition_status(String position_status) {
		this.position_status = position_status;
	}


	public String getRemark() {
		return this.remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}


	public String getDef1() {
		return this.def1;
	}

	public void setDef1(String def1) {
		this.def1 = def1;
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


	public String getDef10() {
		return this.def10;
	}

	public void setDef10(String def10) {
		this.def10 = def10;
	}


	public String getCreator() {
		return this.creator;
	}

	public void setCreator(String creator) {
		this.creator = creator;
	}


	public String getCreationtime() {
		return this.creationtime;
	}

	public void setCreationtime(String creationtime) {
		this.creationtime = creationtime;
	}


	public String getModifier() {
		return this.modifier;
	}

	public void setModifier(String modifier) {
		this.modifier = modifier;
	}


	public String getModifiedtime() {
		return this.modifiedtime;
	}

	public void setModifiedtime(String modifiedtime) {
		this.modifiedtime = modifiedtime;
	}


	public String getTs() {
		return this.ts;
	}

	public void setTs(String ts) {
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
        return "ieop_position";
    }

    @Override
    public String getNamespace() {
		return "com.yonyou.iuap.crm.ieop.security.entity";
    }
}