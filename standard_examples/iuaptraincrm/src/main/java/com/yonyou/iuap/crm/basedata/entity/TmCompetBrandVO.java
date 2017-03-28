package com.yonyou.iuap.crm.basedata.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.yonyou.iuap.persistence.jdbc.framework.annotation.*;
import com.yonyou.iuap.persistence.vo.BaseEntity;

import javax.validation.constraints.NotNull;

/**
 * The persistent class for the tm_competbrand database table.
 * 
 */
@Entity
@Table(name = "tm_competbrand")
//@NotDuplicateValid(claz=TmCompetBrandVO.class,field={"vcompetmodel","vmodelname"},message="车型编码和车型名称不能重复")
public class TmCompetBrandVO extends BaseEntity {
	private static final long serialVersionUID = -8776016874554199080L;
	
	@Id
	@GeneratedValue(strategy = Stragegy.UAPOID, moudle = "tm_competbrand")
	@Column(name = "pk_competbrand")
	private String pk_competbrand;
	@NotNull(message = "{vcompetbrand.not.empty}")
	@Column(name = "vcompetbrand")
	private String vcompetbrand;//品牌
//	@NotNull(message = "{vcompetmodel.not.empty}")
	@Column(name = "vcompetmodel")
	private String vcompetmodel;//车型编码
	@NotNull(message = "{vmodelname.not.empty}")
	@Column(name = "vmodelname")
	private String vmodelname;//车型名称
	@Column(name = "vnoticenum")
	private String vnoticenum;//公告批次
//	@NotNull(message = "{nguideprice.not.empty}")
	@Column(name = "nguideprice")
	private double nguideprice;
	@Column(name = "vstatus")
	private String vstatus;
	@NotNull(message = "{vlength.not.empty}")
	@Column(name = "vlength")
	private int vlength;//长
//	@NotNull(message = "{vwidth.not.empty}")
	@Column(name = "vwidth")
	private int vwidth;//宽
//	@NotNull(message = "{vheight.not.empty}")
	@Column(name = "vheight")
	private int vheight;//高
	@NotNull(message = "{ncurbweight.not.empty}")
	@Column(name = "ncurbweight")
	private double ncurbweight;//整备质量
	@NotNull(message = "{nmaxweight.not.empty}")
	@Column(name = "nmaxweight")
	private double nmaxweight;//最大质量
//	@NotNull(message = "{nwheelbase.not.empty}")
	@Column(name = "nwheelbase")
	private int nwheelbase;
//	@NotNull(message = "{nsuspensionbase.not.empty}")
	@Column(name = "nsuspensionbase")
	private String nsuspensionbase;
//	@NotNull(message = "{ngroundclearanc.not.empty}")
	@Column(name = "ngroundclearanc")
	private int ngroundclearanc;
//	@NotNull(message = "{vdriveangle.not.empty}")
	@Column(name = "vdriveangle")
	private String vdriveangle;
//	@NotNull(message = "{ninheight.not.empty}")
	@Column(name = "ninheight")
	private int ninheight;
//	@NotNull(message = "{nfloorheight.not.empty}")
	@Column(name = "nfloorheight")
	private int nfloorheight;
//	@NotNull(message = "{nminheight.not.empty}")
	@Column(name = "nminheight")
	private int nminheight;
//	@NotNull(message = "{vhubcapwidth.not.empty}")
	@Column(name = "vhubcapwidth")
	private String vhubcapwidth;
//	@NotNull(message = "{nseat.not.empty}")
	@Column(name = "nseat")
	private int nseat;
	@NotNull(message = "{npasenger.not.empty}")
	@Column(name = "npasenger")
	private String npasenger;//额定载客数
//	@NotNull(message = "{nstandarea.not.empty}")
	@Column(name = "nstandarea")
	private double nstandarea;
//	@NotNull(message = "{ncardoor.not.empty}")
	@Column(name = "ncardoor")
	private int ncardoor;
//	@NotNull(message = "{ndoorwidth.not.empty}")
	@Column(name = "ndoorwidth")
	private String ndoorwidth;
//	@NotNull(message = "{vtyreform.not.empty}")
	@Column(name = "vtyreform")
	private String vtyreform;
//	@NotNull(message = "{vdriveform.not.empty}")
	@Column(name = "vdriveform")
	private String vdriveform;
//	@NotNull(message = "{nmaxspeed.not.empty}")
	@Column(name = "nmaxspeed")
	private double nmaxspeed;
//	@NotNull(message = "{nspeeduptime.not.empty}")
	@Column(name = "nspeeduptime")
	private double nspeeduptime;
//	@NotNull(message = "{nrollangle.not.empty}")
	@Column(name = "nrollangle")
	private double nrollangle;
//	@NotNull(message = "{nradii.not.empty}")
	@Column(name = "nradii")
	private double nradii;
//	@NotNull(message = "{ndrivingcourse.not.empty}")
	@Column(name = "ndrivingcourse")
	private double ndrivingcourse;
//	@NotNull(message = "{ndrivingcourse2.not.empty}")
	@Column(name = "ndrivingcourse2")
	private double ndrivingcourse2;
	@NotNull(message = "{vmotorpower.not.empty}")
	@Column(name = "vmotorpower")
	private String vmotorpower;//电机功率
//	@NotNull(message = "{vmotortorque.not.empty}")
	@Column(name = "vmotortorque")
	private String vmotortorque;
	@NotNull(message = "{vbatteryclass.not.empty}")
	@Column(name = "vbatteryclass")
	private String vbatteryclass;//电池形式
//	@NotNull(message = "{nvbatterypower.not.empty}")
	@Column(name = "nvbatterypower")
	private double nvbatterypower;
//	@NotNull(message = "{nchargepower.not.empty}")
	@Column(name = "nchargepower")
	private double nchargepower;
//	@NotNull(message = "{nchargetime.not.empty}")
	@Column(name = "nchargetime")
	private double nchargetime;
//	@NotNull(message = "{vsellpoint.not.empty}")
	@Column(name = "vsellpoint")
	private String vsellpoint;
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

	public TmCompetBrandVO() {
	}

	public String getPk_competbrand() {
		return this.pk_competbrand;
	}

	public void setPk_competbrand(String pk_competbrand) {
		this.pk_competbrand = pk_competbrand;
	}

	public String getVcompetbrand() {
		return this.vcompetbrand;
	}

	public void setVcompetbrand(String vcompetbrand) {
		this.vcompetbrand = vcompetbrand;
	}

	public String getVcompetmodel() {
		return this.vcompetmodel;
	}

	public void setVcompetmodel(String vcompetmodel) {
		this.vcompetmodel = vcompetmodel;
	}

	public double getNguideprice() {
		return this.nguideprice;
	}

	public void setNguideprice(double nguideprice) {
		this.nguideprice = nguideprice;
	}

	public String getVstatus() {
		return this.vstatus;
	}

	public void setVstatus(String vstatus) {
		this.vstatus = vstatus;
	}

	public int getVlength() {
		return vlength;
	}

	public void setVlength(int vlength) {
		this.vlength = vlength;
	}

	public int getVwidth() {
		return vwidth;
	}

	public void setVwidth(int vwidth) {
		this.vwidth = vwidth;
	}

	public int getVheight() {
		return vheight;
	}

	public void setVheight(int vheight) {
		this.vheight = vheight;
	}

	public double getNcurbweight() {
		return this.ncurbweight;
	}

	public void setNcurbweight(double ncurbweight) {
		this.ncurbweight = ncurbweight;
	}

	public double getNmaxweight() {
		return this.nmaxweight;
	}

	public void setNmaxweight(double nmaxweight) {
		this.nmaxweight = nmaxweight;
	}

	public int getNwheelbase() {
		return this.nwheelbase;
	}

	public void setNwheelbase(int nwheelbase) {
		this.nwheelbase = nwheelbase;
	}

	public String getNsuspensionbase() {
		return this.nsuspensionbase;
	}

	public void setNsuspensionbase(String nsuspensionbase) {
		this.nsuspensionbase = nsuspensionbase;
	}

	public int getNgroundclearanc() {
		return this.ngroundclearanc;
	}

	public void setNgroundclearanc(int ngroundclearanc) {
		this.ngroundclearanc = ngroundclearanc;
	}

	public String getVdriveangle() {
		return this.vdriveangle;
	}

	public void setVdriveangle(String vdriveangle) {
		this.vdriveangle = vdriveangle;
	}

	public int getNinheight() {
		return this.ninheight;
	}

	public void setNinheight(int ninheight) {
		this.ninheight = ninheight;
	}

	public int getNfloorheight() {
		return this.nfloorheight;
	}

	public void setNfloorheight(int nfloorheight) {
		this.nfloorheight = nfloorheight;
	}

	public int getNminheight() {
		return this.nminheight;
	}

	public void setNminheight(int nminheight) {
		this.nminheight = nminheight;
	}

	public String getVhubcapwidth() {
		return this.vhubcapwidth;
	}

	public void setVhubcapwidth(String vhubcapwidth) {
		this.vhubcapwidth = vhubcapwidth;
	}

	public int getNseat() {
		return this.nseat;
	}

	public void setNseat(int nseat) {
		this.nseat = nseat;
	}

	public String getNpasenger() {
		return this.npasenger;
	}

	public void setNpasenger(String npasenger) {
		this.npasenger = npasenger;
	}

	public double getNstandarea() {
		return this.nstandarea;
	}

	public void setNstandarea(double nstandarea) {
		this.nstandarea = nstandarea;
	}

	public int getNcardoor() {
		return this.ncardoor;
	}

	public void setNcardoor(int ncardoor) {
		this.ncardoor = ncardoor;
	}

	public String getNdoorwidth() {
		return this.ndoorwidth;
	}

	public void setNdoorwidth(String ndoorwidth) {
		this.ndoorwidth = ndoorwidth;
	}

	public String getVtyreform() {
		return this.vtyreform;
	}

	public void setVtyreform(String vtyreform) {
		this.vtyreform = vtyreform;
	}

	public String getVdriveform() {
		return this.vdriveform;
	}

	public void setVdriveform(String vdriveform) {
		this.vdriveform = vdriveform;
	}

	public double getNmaxspeed() {
		return this.nmaxspeed;
	}

	public void setNmaxspeed(double nmaxspeed) {
		this.nmaxspeed = nmaxspeed;
	}

	public double getNspeeduptime() {
		return this.nspeeduptime;
	}

	public void setNspeeduptime(double nspeeduptime) {
		this.nspeeduptime = nspeeduptime;
	}

	public double getNrollangle() {
		return this.nrollangle;
	}

	public void setNrollangle(double nrollangle) {
		this.nrollangle = nrollangle;
	}

	public double getNradii() {
		return this.nradii;
	}

	public void setNradii(double nradii) {
		this.nradii = nradii;
	}

	public double getNdrivingcourse() {
		return this.ndrivingcourse;
	}

	public void setNdrivingcourse(double ndrivingcourse) {
		this.ndrivingcourse = ndrivingcourse;
	}

	public double getNdrivingcourse2() {
		return this.ndrivingcourse2;
	}

	public void setNdrivingcourse2(double ndrivingcourse2) {
		this.ndrivingcourse2 = ndrivingcourse2;
	}

	public String getVmotorpower() {
		return this.vmotorpower;
	}

	public void setVmotorpower(String vmotorpower) {
		this.vmotorpower = vmotorpower;
	}

	public String getVmotortorque() {
		return this.vmotortorque;
	}

	public void setVmotortorque(String vmotortorque) {
		this.vmotortorque = vmotortorque;
	}

	public String getVbatteryclass() {
		return this.vbatteryclass;
	}

	public void setVbatteryclass(String vbatteryclass) {
		this.vbatteryclass = vbatteryclass;
	}

	public double getNvbatterypower() {
		return this.nvbatterypower;
	}

	public void setNvbatterypower(double nvbatterypower) {
		this.nvbatterypower = nvbatterypower;
	}

	public double getNchargepower() {
		return this.nchargepower;
	}

	public void setNchargepower(double nchargepower) {
		this.nchargepower = nchargepower;
	}

	public double getNchargetime() {
		return this.nchargetime;
	}

	public void setNchargetime(double nchargetime) {
		this.nchargetime = nchargetime;
	}

	public String getVsellpoint() {
		return this.vsellpoint;
	}

	public void setVsellpoint(String vsellpoint) {
		this.vsellpoint = vsellpoint;
	}

	public String getCreator() {
		return this.creator;
	}

	public void setCreator(String creator) {
		this.creator = creator;
	}

	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+08:00")
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

	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+08:00")
	public String getModifiedtime() {
		return this.modifiedtime;
	}

	public void setModifiedtime(String modifiedtime) {
		this.modifiedtime = modifiedtime;
	}

	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+08:00")
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
		return "tm_competbrand";
	}

	@Override
	public String getNamespace() {
		return "com.yonyou.iuap.crm.basedata.entity";
	}


	public String getVnoticenum() {
		return vnoticenum;
	}

	public void setVnoticenum(String vnoticenum) {
		this.vnoticenum = vnoticenum;
	}

	public String getVmodelname() {
		return vmodelname;
	}

	public void setVmodelname(String vmodelname) {
		this.vmodelname = vmodelname;
	}
	

}