package com.yonyou.iuap.crm.basedata.entity;

import com.yonyou.iuap.persistence.jdbc.framework.annotation.Column;
import com.yonyou.iuap.persistence.jdbc.framework.annotation.Entity;
import com.yonyou.iuap.persistence.jdbc.framework.annotation.GeneratedValue;
import com.yonyou.iuap.persistence.jdbc.framework.annotation.Id;
import com.yonyou.iuap.persistence.jdbc.framework.annotation.Stragegy;
import com.yonyou.iuap.persistence.jdbc.framework.annotation.Table;
import com.yonyou.iuap.persistence.vo.BaseEntity;


/**
 * The persistent class for the tm_customervehicle database table.
 * 
 */
@Entity
@Table(name="tm_customervehicle")
public class TmCustomerVehicleVO extends BaseEntity {
	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy=Stragegy.UAPOID,moudle="tm_customervehicle")
	@Column(name = "pk_customervehicle")
	private String pk_customervehicle;
	@Column(name = "pk_customerinfo")
	private String pk_customerinfo;
	@Column(name = "vvin")
	private String vvin;
	@Column(name = "pk_model")
	private String pk_model;
	@Column(name = "vvehiclelicense")
	private String vvehiclelicense;
	@Column(name = "vcertificate")
	private String vcertificate;
	@Column(name = "vinvoiceno")
	private String vinvoiceno;
	@Column(name = "dofflinedate")
	private String dofflinedate;
	@Column(name = "ddeliverydate")
	private String ddeliverydate;
	@Column(name = "dinvoicedate")
	private String dinvoicedate;
	@Column(name = "dreceivedate")
	private String dreceivedate;
	@Column(name = "dlicensedate")
	private String dlicensedate;
	@Column(name = "visoperation")
	private String visoperation;
	@Column(name = "doperstartdate")
	private String doperstartdate;
	@Column(name = "doperenddate")
	private String doperenddate;
	@Column(name = "vlocation")
	private String vlocation;
	@Column(name = "vcarstatus")
	private String vcarstatus;
	@Column(name = "dwarrantydate")
	private String dwarrantydate;
	@Column(name = "nwarratymile")
	private double nwarratymile;
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
	@Column(name = "visls")
	private String visls;
	@Column(name = "viscs")
	private String viscs;
	@Column(name = "vislye")
	private String vislye;
	@Column(name = "viscye")
	private String viscye;

	public TmCustomerVehicleVO() {
	}


	public String getPk_customervehicle() {
		return this.pk_customervehicle;
	}

	public void setPk_customervehicle(String pk_customervehicle) {
		this.pk_customervehicle = pk_customervehicle;
	}


	public String getPk_customerinfo() {
		return this.pk_customerinfo;
	}

	public void setPk_customerinfo(String pk_customerinfo) {
		this.pk_customerinfo = pk_customerinfo;
	}


	public String getVvin() {
		return this.vvin;
	}

	public void setVvin(String vvin) {
		this.vvin = vvin;
	}


	public String getPk_model() {
		return this.pk_model;
	}

	public void setPk_model(String pk_model) {
		this.pk_model = pk_model;
	}


	public String getVvehiclelicense() {
		return this.vvehiclelicense;
	}

	public void setVvehiclelicense(String vvehiclelicense) {
		this.vvehiclelicense = vvehiclelicense;
	}


	public String getVcertificate() {
		return this.vcertificate;
	}

	public void setVcertificate(String vcertificate) {
		this.vcertificate = vcertificate;
	}


	public String getVinvoiceno() {
		return this.vinvoiceno;
	}

	public void setVinvoiceno(String vinvoiceno) {
		this.vinvoiceno = vinvoiceno;
	}


	public String getDofflinedate() {
		return this.dofflinedate;
	}

	public void setDofflinedate(String dofflinedate) {
		this.dofflinedate = dofflinedate;
	}


	public String getDdeliverydate() {
		return this.ddeliverydate;
	}

	public void setDdeliverydate(String ddeliverydate) {
		this.ddeliverydate = ddeliverydate;
	}


	public String getDinvoicedate() {
		return this.dinvoicedate;
	}

	public void setDinvoicedate(String dinvoicedate) {
		this.dinvoicedate = dinvoicedate;
	}


	public String getDreceivedate() {
		return this.dreceivedate;
	}

	public void setDreceivedate(String dreceivedate) {
		this.dreceivedate = dreceivedate;
	}


	public String getDlicensedate() {
		return this.dlicensedate;
	}

	public void setDlicensedate(String dlicensedate) {
		this.dlicensedate = dlicensedate;
	}


	public String getVisoperation() {
		return this.visoperation;
	}

	public void setVisoperation(String visoperation) {
		this.visoperation = visoperation;
	}


	public String getDoperstartdate() {
		return this.doperstartdate;
	}

	public void setDoperstartdate(String doperstartdate) {
		this.doperstartdate = doperstartdate;
	}


	public String getDoperenddate() {
		return this.doperenddate;
	}

	public void setDoperenddate(String doperenddate) {
		this.doperenddate = doperenddate;
	}


	public String getVlocation() {
		return this.vlocation;
	}

	public void setVlocation(String vlocation) {
		this.vlocation = vlocation;
	}


	public String getVcarstatus() {
		return this.vcarstatus;
	}

	public void setVcarstatus(String vcarstatus) {
		this.vcarstatus = vcarstatus;
	}


	public String getDwarrantydate() {
		return this.dwarrantydate;
	}

	public void setDwarrantydate(String dwarrantydate) {
		this.dwarrantydate = dwarrantydate;
	}


	public double getNwarratymile() {
		return this.nwarratymile;
	}

	public void setNwarratymile(double nwarratymile) {
		this.nwarratymile = nwarratymile;
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


	public String getVisls() {
		return this.visls;
	}

	public void setVisls(String visls) {
		this.visls = visls;
	}


	public String getViscs() {
		return this.viscs;
	}

	public void setViscs(String viscs) {
		this.viscs = viscs;
	}


	public String getVislye() {
		return this.vislye;
	}

	public void setVislye(String vislye) {
		this.vislye = vislye;
	}


	public String getViscye() {
		return this.viscye;
	}

	public void setViscye(String viscye) {
		this.viscye = viscye;
	}

	@Override
    public String getMetaDefinedName() {
        return "tm_customervehicle";
    }

    @Override
    public String getNamespace() {
		return "com.yonyou.iuap.crm.basedata.entity";
    }
}