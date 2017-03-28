package com.yonyou.iuap.crm.basedata.entity;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.yonyou.iuap.persistence.jdbc.framework.annotation.Column;
import com.yonyou.iuap.persistence.jdbc.framework.annotation.Entity;
import com.yonyou.iuap.persistence.jdbc.framework.annotation.GeneratedValue;
import com.yonyou.iuap.persistence.jdbc.framework.annotation.Id;
import com.yonyou.iuap.persistence.jdbc.framework.annotation.Stragegy;
import com.yonyou.iuap.persistence.jdbc.framework.annotation.Table;
import com.yonyou.iuap.persistence.vo.BaseEntity;


/**
 * The persistent class for the tm_seriesmargin database table.
 * 
 */
@Entity
@Table(name="tm_seriesmargin")
@JsonIgnoreProperties(ignoreUnknown = true)
public class SeriesMarginVO extends BaseEntity {
	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy=Stragegy.UUID,moudle="tm_seriesmargin")
	/**
	 * 车系毛利主键
	 */
	@Column(name = "pk_seriesmargin")
	private String pk_seriesmargin;
	/**
	 * 年度
	 */
	@NotNull(message="{vyear.not.empty}")
	@Pattern(regexp="[0-9]+",message="{vyear.input.valid}")
	@Column(name = "vyear")
	private String vyear;        
	/**
	 * 季度
	 */
	@NotNull(message="{vmonth.not.empty}")
	@Pattern(regexp="[0-9]+",message="{vmonth.input.valid}")
	@Column(name = "vmonth")
	private String vmonth;  
	/**
	 * 毛利
	 */
	@NotNull(message="{nmargin.not.empty}")
	@Column(name = "nmargin")
	private double nmargin; 
	/**
	 * 车系信息参照
	 */
	@NotNull(message="{code.not.empty}")
	@Column(name = "pk_series")
	private String pk_series;  
	/**
	 * 创建者
	 */
	@Column(name = "creator")
	private String creator;   
	/**
	 * 创建时间
	 */
	@Column(name = "creationtime")
	private String creationtime;   
	/**
	 * 修改者
	 */
	@Column(name = "modifier")
	private String modifier;       
	@Column(name = "modifiedtime")
	/**
	 * 修改时间
	 */
	private String modifiedtime;   
	@Column(name = "ts")
	private String ts;
	@Column(name = "dr")
	private int dr;

	public SeriesMarginVO() {
	}


	public String getPk_seriesmargin() {
		return this.pk_seriesmargin;
	}

	public void setPk_seriesmargin(String pk_seriesmargin) {
		this.pk_seriesmargin = pk_seriesmargin;
	}


	public String getVyear() {
		return this.vyear;
	}

	public void setVyear(String vyear) {
		this.vyear = vyear;
	}


	public String getVmonth() {
		return this.vmonth;
	}

	public void setVmonth(String vmonth) {
		this.vmonth = vmonth;
	}


	public double getNmargin() {
		return this.nmargin;
	}

	public void setNmargin(double nmargin) {
		this.nmargin = nmargin;
	}


	public String getPk_series() {
		return this.pk_series;
	}

	public void setPk_series(String pk_series) {
		this.pk_series = pk_series;
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
        return "tm_seriesmargin";
    }

    @Override
    public String getNamespace() {
		return "com.yonyou.iuap.crm.basedata.entity";
    }
}