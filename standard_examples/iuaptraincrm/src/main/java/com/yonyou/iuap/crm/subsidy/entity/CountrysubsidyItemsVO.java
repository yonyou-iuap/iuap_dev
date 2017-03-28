package com.yonyou.iuap.crm.subsidy.entity;

import com.yonyou.iuap.persistence.jdbc.framework.annotation.Column;
import com.yonyou.iuap.persistence.jdbc.framework.annotation.Entity;
import com.yonyou.iuap.persistence.jdbc.framework.annotation.FK;
import com.yonyou.iuap.persistence.jdbc.framework.annotation.GeneratedValue;
import com.yonyou.iuap.persistence.jdbc.framework.annotation.Id;
import com.yonyou.iuap.persistence.jdbc.framework.annotation.Stragegy;
import com.yonyou.iuap.persistence.jdbc.framework.annotation.Table;
import com.yonyou.iuap.persistence.vo.BaseEntity;


/**
 * The persistent class for the SS_COUNTRYSUBSIDY_ITEMS database table.
 * 
 */
@Entity
@Table(name="SS_COUNTRYSUBSIDY_ITEMS")
public class CountrysubsidyItemsVO extends BaseEntity {
	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy=Stragegy.UUID,moudle="SS_COUNTRYSUBSIDY_ITEMS")
	@Column(name = "pk_countrysubsidy_items")
	private String pk_countrysubsidy_items;
	@FK(name="pk_countrysubsidy",referencedColumnName="pk_countrysubsidy",referenceTableName="SS_CONTRYSUBSIDY" )
	@Column(name = "pk_countrysubsidy")
	private String pk_countrysubsidy;
	@Column(name = "viscommercial")
	private String viscommercial;
	@Column(name = "vbuydomain")
	private String vbuydomain;
	@Column(name = "vbuycity")
	private String vbuycity;
	@Column(name = "vrundept")
	private String vrundept;
	@Column(name = "vvehiclekind")
	private String vvehiclekind;
	@Column(name = "vvehiclepurpose")
	private String vvehiclepurpose;
	@Column(name = "vvehiclemodel")
	private String vvehiclemodel;
	@Column(name = "vvin")
	private String vvin;
	@Column(name = "vlicense")
	private String vlicense;
	@Column(name = "nsubsidystandard")
	private double nsubsidystandard;
	@Column(name = "ntotalback")
	private double ntotalback;
	@Column(name = "nnotback")
	private double nnotback;
	@Column(name = "npurchaseprice")
	private double npurchaseprice;
	@Column(name = "vinvoiceno")
	private String vinvoiceno;
	@Column(name = "ninvoiceyear")
	private int ninvoiceyear;
	@Column(name = "ninvoicemonth")
	private int ninvoicemonth;
	@Column(name = "ninvoiceday")
	private int ninvoiceday;
	@Column(name = "ndlicenseyear")
	private int ndlicenseyear;
	@Column(name = "ndlicensemonth")
	private int ndlicensemonth;
	@Column(name = "ndlicenseday")
	private int ndlicenseday;
	@Column(name = "dmustbackdate")
	private String dmustbackdate;
	@Column(name = "ddeclaredate")
	private String ddeclaredate;
	@Column(name = "vapprovepoint")
	private String vapprovepoint;
	@Column(name = "vnotdeclarestatus")
	private String vnotdeclarestatus;
	@Column(name = "vnotdeclarecomments")
	private String vnotdeclarecomments;
	@Column(name = "doverduedate")
	private String doverduedate;
	@Column(name = "vdeclarestatus")
	private String vdeclarestatus;
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

	public CountrysubsidyItemsVO() {
	}


	public String getPk_countrysubsidy_items() {
		return this.pk_countrysubsidy_items;
	}

	public void setPk_countrysubsidy_items(String pk_countrysubsidy_items) {
		this.pk_countrysubsidy_items = pk_countrysubsidy_items;
	}


	public String getPk_countrysubsidy() {
		return this.pk_countrysubsidy;
	}

	public void setPk_countrysubsidy(String pk_countrysubsidy) {
		this.pk_countrysubsidy = pk_countrysubsidy;
	}


	public String getViscommercial() {
		return this.viscommercial;
	}

	public void setViscommercial(String viscommercial) {
		this.viscommercial = viscommercial;
	}


	public String getVbuydomain() {
		return this.vbuydomain;
	}

	public void setVbuydomain(String vbuydomain) {
		this.vbuydomain = vbuydomain;
	}


	public String getVbuycity() {
		return this.vbuycity;
	}

	public void setVbuycity(String vbuycity) {
		this.vbuycity = vbuycity;
	}


	public String getVrundept() {
		return this.vrundept;
	}

	public void setVrundept(String vrundept) {
		this.vrundept = vrundept;
	}


	public String getVvehiclekind() {
		return this.vvehiclekind;
	}

	public void setVvehiclekind(String vvehiclekind) {
		this.vvehiclekind = vvehiclekind;
	}


	public String getVvehiclepurpose() {
		return this.vvehiclepurpose;
	}

	public void setVvehiclepurpose(String vvehiclepurpose) {
		this.vvehiclepurpose = vvehiclepurpose;
	}


	public String getVvehiclemodel() {
		return this.vvehiclemodel;
	}

	public void setVvehiclemodel(String vvehiclemodel) {
		this.vvehiclemodel = vvehiclemodel;
	}


	public String getVvin() {
		return this.vvin;
	}

	public void setVvin(String vvin) {
		this.vvin = vvin;
	}


	public String getVlicense() {
		return this.vlicense;
	}

	public void setVlicense(String vlicense) {
		this.vlicense = vlicense;
	}


	public double getNsubsidystandard() {
		return this.nsubsidystandard;
	}

	public void setNsubsidystandard(double nsubsidystandard) {
		this.nsubsidystandard = nsubsidystandard;
	}


	public double getNtotalback() {
		return this.ntotalback;
	}

	public void setNtotalback(double ntotalback) {
		this.ntotalback = ntotalback;
	}


	public double getNnotback() {
		return this.nnotback;
	}

	public void setNnotback(double nnotback) {
		this.nnotback = nnotback;
	}


	public double getNpurchaseprice() {
		return this.npurchaseprice;
	}

	public void setNpurchaseprice(double npurchaseprice) {
		this.npurchaseprice = npurchaseprice;
	}


	public String getVinvoiceno() {
		return this.vinvoiceno;
	}

	public void setVinvoiceno(String vinvoiceno) {
		this.vinvoiceno = vinvoiceno;
	}


	public int getNinvoiceyear() {
		return this.ninvoiceyear;
	}

	public void setNinvoiceyear(int ninvoiceyear) {
		this.ninvoiceyear = ninvoiceyear;
	}


	public int getNinvoicemonth() {
		return this.ninvoicemonth;
	}

	public void setNinvoicemonth(int ninvoicemonth) {
		this.ninvoicemonth = ninvoicemonth;
	}


	public int getNinvoiceday() {
		return this.ninvoiceday;
	}

	public void setNinvoiceday(int ninvoiceday) {
		this.ninvoiceday = ninvoiceday;
	}


	public int getNdlicenseyear() {
		return this.ndlicenseyear;
	}

	public void setNdlicenseyear(int ndlicenseyear) {
		this.ndlicenseyear = ndlicenseyear;
	}


	public int getNdlicensemonth() {
		return this.ndlicensemonth;
	}

	public void setNdlicensemonth(int ndlicensemonth) {
		this.ndlicensemonth = ndlicensemonth;
	}


	public int getNdlicenseday() {
		return this.ndlicenseday;
	}

	public void setNdlicenseday(int ndlicenseday) {
		this.ndlicenseday = ndlicenseday;
	}


	public String getDmustbackdate() {
		return this.dmustbackdate;
	}

	public void setDmustbackdate(String dmustbackdate) {
		this.dmustbackdate = dmustbackdate;
	}


	public String getDdeclaredate() {
		return this.ddeclaredate;
	}

	public void setDdeclaredate(String ddeclaredate) {
		this.ddeclaredate = ddeclaredate;
	}


	public String getVapprovepoint() {
		return this.vapprovepoint;
	}

	public void setVapprovepoint(String vapprovepoint) {
		this.vapprovepoint = vapprovepoint;
	}


	public String getVnotdeclarestatus() {
		return this.vnotdeclarestatus;
	}

	public void setVnotdeclarestatus(String vnotdeclarestatus) {
		this.vnotdeclarestatus = vnotdeclarestatus;
	}


	public String getVnotdeclarecomments() {
		return this.vnotdeclarecomments;
	}

	public void setVnotdeclarecomments(String vnotdeclarecomments) {
		this.vnotdeclarecomments = vnotdeclarecomments;
	}


	public String getDoverduedate() {
		return this.doverduedate;
	}

	public void setDoverduedate(String doverduedate) {
		this.doverduedate = doverduedate;
	}


	public String getVdeclarestatus() {
		return this.vdeclarestatus;
	}

	public void setVdeclarestatus(String vdeclarestatus) {
		this.vdeclarestatus = vdeclarestatus;
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
        return "SS_COUNTRYSUBSIDY_ITEMS";
    }

    @Override
    public String getNamespace() {
		return "com.yonyou.iuap.crm.subsidy.entity";
    }
}