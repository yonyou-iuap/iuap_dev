package com.yonyou.iuap.crm.basedata.entity;

import com.yonyou.iuap.persistence.jdbc.framework.annotation.Column;

public class TmCustomerVehicleExtVO extends TmCustomerVehicleVO {

	/**
	 * 
	 */
	private static final long serialVersionUID = -4047242385564708328L;
	@Column(name = "vprojectno")
	private String vprojectno;
	@Column(name = "vcontractno")
	private String vcontractno;
	@Column(name = "poorderno")
	private String poorderno;
	@Column(name = "transno")
	private String transno;
	@Column(name = "vcertificateplace")
	private String vcertificateplace;
	@Column(name = "vcustomername")
	private String vcustomername;
	@Column(name = "deptname")
	private String deptname;

	public String getVprojectno() {
		return vprojectno;
	}

	public void setVprojectno(String vprojectno) {
		this.vprojectno = vprojectno;
	}

	public String getVcontractno() {
		return vcontractno;
	}

	public void setVcontractno(String vcontractno) {
		this.vcontractno = vcontractno;
	}

	public String getPoorderno() {
		return poorderno;
	}

	public void setPoorderno(String poorderno) {
		this.poorderno = poorderno;
	}

	public String getTransno() {
		return transno;
	}

	public void setTransno(String transno) {
		this.transno = transno;
	}

	public String getVcertificateplace() {
		return vcertificateplace;
	}

	public void setVcertificateplace(String vcertificateplace) {
		this.vcertificateplace = vcertificateplace;
	}

	public String getVcustomername() {
		return vcustomername;
	}

	public void setVcustomername(String vcustomername) {
		this.vcustomername = vcustomername;
	}

	public String getDeptname() {
		return deptname;
	}

	public void setDeptname(String deptname) {
		this.deptname = deptname;
	}

}
