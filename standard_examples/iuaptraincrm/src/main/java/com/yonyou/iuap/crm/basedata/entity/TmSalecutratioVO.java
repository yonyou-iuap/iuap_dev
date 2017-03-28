package com.yonyou.iuap.crm.basedata.entity;

import com.yonyou.iuap.persistence.jdbc.framework.annotation.Column;
import com.yonyou.iuap.persistence.jdbc.framework.annotation.Entity;
import com.yonyou.iuap.persistence.jdbc.framework.annotation.GeneratedValue;
import com.yonyou.iuap.persistence.jdbc.framework.annotation.Id;
import com.yonyou.iuap.persistence.jdbc.framework.annotation.Stragegy;
import com.yonyou.iuap.persistence.jdbc.framework.annotation.Table;
import com.yonyou.iuap.persistence.vo.BaseEntity;


/**
 * The persistent class for the tm_salecutratio database table.
 * 
 */
@Entity
@Table(name="tm_salecutratio")
public class TmSalecutratioVO extends BaseEntity {
	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy=Stragegy.UUID,moudle="tm_salecutratio")
	@Column(name = "pk_salecutratio")
	private String pk_salecutratio;
	@Column(name = "nyear")
	private int nyear;
	@Column(name = "nmonth")
	private int nmonth;
	@Column(name = "ncutratio")
	private double ncutratio;
	@Column(name = "ncontract")
	private double ncontract;
	@Column(name = "ncountry")
	private double ncountry;
	@Column(name = "nlocation")
	private double nlocation;
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
	@Column(name = "vinvoice")
	private String vinvoice;
	@Column(name = "visbyd")
	private String visbyd;
	
	public String getVinvoice() {
		return vinvoice;
	}


	public void setVinvoice(String vinvoice) {
		this.vinvoice = vinvoice;
	}


	public String getVisbyd() {
		return visbyd;
	}


	public void setVisbyd(String visbyd) {
		this.visbyd = visbyd;
	}


	public TmSalecutratioVO() {
	}


	public String getPk_salecutratio() {
		return this.pk_salecutratio;
	}

	public void setPk_salecutratio(String pk_salecutratio) {
		this.pk_salecutratio = pk_salecutratio;
	}


	public int getNyear() {
		return this.nyear;
	}

	public void setNyear(int nyear) {
		this.nyear = nyear;
	}


	public int getNmonth() {
		return this.nmonth;
	}

	public void setNmonth(int nmonth) {
		this.nmonth = nmonth;
	}


	public double getNcutratio() {
		return this.ncutratio;
	}

	public void setNcutratio(double ncutratio) {
		this.ncutratio = ncutratio;
	}


	public double getNcontract() {
		return this.ncontract;
	}

	public void setNcontract(double ncontract) {
		this.ncontract = ncontract;
	}


	public double getNcountry() {
		return this.ncountry;
	}

	public void setNcountry(double ncountry) {
		this.ncountry = ncountry;
	}


	public double getNlocation() {
		return this.nlocation;
	}

	public void setNlocation(double nlocation) {
		this.nlocation = nlocation;
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
        return "tm_salecutratio";
    }

    @Override
    public String getNamespace() {
		return "com.yonyou.iuap.crm.basedata.entity";
    }
}