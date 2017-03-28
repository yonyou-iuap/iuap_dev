package com.yonyou.iuap.crm.basedata.entity;

import java.util.List;

import org.springframework.data.annotation.Transient;

import com.yonyou.iuap.persistence.jdbc.framework.annotation.Column;
import com.yonyou.iuap.persistence.jdbc.framework.annotation.Entity;
import com.yonyou.iuap.persistence.jdbc.framework.annotation.GeneratedValue;
import com.yonyou.iuap.persistence.jdbc.framework.annotation.Id;
import com.yonyou.iuap.persistence.jdbc.framework.annotation.Stragegy;
import com.yonyou.iuap.persistence.jdbc.framework.annotation.Table;
import com.yonyou.iuap.persistence.vo.BaseEntity;


/**
 * The persistent class for the tm_userapply database table.
 * 
 */
@Entity
@Table(name="tm_userapply")
public class TmUserApplyVO extends BaseEntity {
	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy=Stragegy.UAPOID,moudle="tm_userapply")
	@Column(name = "pk_userapply")
	private String pk_userapply;
	@Column(name = "vusercode")
	private String vusercode;
	@Column(name = "vpsncode")
	private String vpsncode;
	@Column(name = "vusername")
	private String vusername;
	@Column(name = "vid")
	private String vid;
	@Column(name = "vworkid")
	private String vworkid;
	@Column(name = "vphone")
	private String vphone;
	@Column(name = "vemail")
	private String vemail;
	@Column(name = "pk_org")
	private String pk_org;
	@Column(name = "pk_dept")
	private String pk_dept;
	@Column(name = "ts")
	private String ts;
	@Column(name = "dr")
	private int dr;
	@Column(name = "vstatus")
	private String vstatus;
	@Column(name = "creator")
	private String creator;
	@Column(name = "creationtime")
	private String creationtime;
	@Column(name = "modifier")
	private String modifier;
	@Column(name = "modifiedtime")
	private String modifiedtime;
	@Transient
	@Column(name = "orgname")
	private String orgname;
	@Transient
	@Column(name = "deptname")
	private String deptname;
	@Transient
	@Column(name = "stsname")
	private String stsname;
	
	private List<TmUserApplyPosVO> positions;

	public TmUserApplyVO() {
	}


	public String getPk_userapply() {
		return this.pk_userapply;
	}

	public void setPk_userapply(String pk_userapply) {
		this.pk_userapply = pk_userapply;
	}


	public String getVusercode() {
		return this.vusercode;
	}

	public void setVusercode(String vusercode) {
		this.vusercode = vusercode;
	}


	public String getVpsncode() {
		return this.vpsncode;
	}

	public void setVpsncode(String vpsncode) {
		this.vpsncode = vpsncode;
	}


	public String getVusername() {
		return this.vusername;
	}

	public void setVusername(String vusername) {
		this.vusername = vusername;
	}


	public String getVid() {
		return this.vid;
	}

	public void setVid(String vid) {
		this.vid = vid;
	}


	public String getVworkid() {
		return this.vworkid;
	}

	public void setVworkid(String vworkid) {
		this.vworkid = vworkid;
	}


	public String getVphone() {
		return this.vphone;
	}

	public void setVphone(String vphone) {
		this.vphone = vphone;
	}


	public String getVemail() {
		return this.vemail;
	}

	public void setVemail(String vemail) {
		this.vemail = vemail;
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


	public String getVstatus() {
		return this.vstatus;
	}

	public void setVstatus(String vstatus) {
		this.vstatus = vstatus;
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
        return "tm_userapply";
    }

    @Override
    public String getNamespace() {
		return "com.yonyou.iuap.crm.basedata.entity";
    }


	public List<TmUserApplyPosVO> getPositions() {
		return positions;
	}


	public void setPositions(List<TmUserApplyPosVO> positions) {
		this.positions = positions;
	}


	public String getOrgname() {
		return orgname;
	}


	public void setOrgname(String orgname) {
		this.orgname = orgname;
	}


	public String getDeptname() {
		return deptname;
	}


	public void setDeptname(String deptname) {
		this.deptname = deptname;
	}


	public String getStsname() {
		return stsname;
	}


	public void setStsname(String stsname) {
		this.stsname = stsname;
	}
}