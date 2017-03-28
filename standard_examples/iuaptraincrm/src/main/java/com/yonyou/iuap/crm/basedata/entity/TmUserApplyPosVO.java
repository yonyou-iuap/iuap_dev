package com.yonyou.iuap.crm.basedata.entity;

import com.yonyou.iuap.persistence.jdbc.framework.annotation.Column;
import com.yonyou.iuap.persistence.jdbc.framework.annotation.Entity;
import com.yonyou.iuap.persistence.jdbc.framework.annotation.FK;
import com.yonyou.iuap.persistence.jdbc.framework.annotation.GeneratedValue;
import com.yonyou.iuap.persistence.jdbc.framework.annotation.Id;
import com.yonyou.iuap.persistence.jdbc.framework.annotation.Stragegy;
import com.yonyou.iuap.persistence.jdbc.framework.annotation.Table;
import com.yonyou.iuap.persistence.vo.BaseEntity;


/**
 * The persistent class for the tm_userapply_pos database table.
 * 
 */
@Entity
@Table(name="tm_userapply_pos")
public class TmUserApplyPosVO extends BaseEntity {
	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy=Stragegy.UAPOID,moudle="tm_userapply_pos")
	@Column(name = "pk_userapplypos")
	private String pk_userapplypos;
	@Column(name = "pk_userapply")
	@FK(name = "pk_userapply", referenceTableName = "tm_userapply", referencedColumnName = "pk_userapply")
	private String pk_userapply;
	@Column(name = "pk_position")
	private String pk_position;
	@Column(name = "position_code")
	private String position_code;
	@Column(name = "position_name")
	private String position_name;
	@Column(name = "ts")
	private String ts;
	@Column(name = "dr")
	private int dr;
	@Column(name = "creator")
	private String creator;
	@Column(name = "creationtime")
	private String creationtime;
	@Column(name = "modifier")
	private String modifier;
	@Column(name = "modifiedtime")
	private String modifiedtime;

	public TmUserApplyPosVO() {
	}


	public String getPk_userapplypos() {
		return this.pk_userapplypos;
	}

	public void setPk_userapplypos(String pk_userapplypos) {
		this.pk_userapplypos = pk_userapplypos;
	}


	public String getPk_userapply() {
		return this.pk_userapply;
	}

	public void setPk_userapply(String pk_userapply) {
		this.pk_userapply = pk_userapply;
	}


	public String getPk_position() {
		return this.pk_position;
	}

	public void setPk_position(String pk_position) {
		this.pk_position = pk_position;
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

	@Override
    public String getMetaDefinedName() {
        return "tm_userapply_pos";
    }

    @Override
    public String getNamespace() {
		return "com.yonyou.iuap.crm.basedata.entity";
    }


	public String getPosition_code() {
		return position_code;
	}


	public void setPosition_code(String position_code) {
		this.position_code = position_code;
	}


	public String getPosition_name() {
		return position_name;
	}


	public void setPosition_name(String position_name) {
		this.position_name = position_name;
	}
}