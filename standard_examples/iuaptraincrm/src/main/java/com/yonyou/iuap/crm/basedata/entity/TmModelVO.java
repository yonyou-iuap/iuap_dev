package com.yonyou.iuap.crm.basedata.entity;

import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.yonyou.iuap.crm.common.validator.NotDuplicateValid;
import com.yonyou.iuap.persistence.jdbc.framework.annotation.Column;
import com.yonyou.iuap.persistence.jdbc.framework.annotation.Entity;
import com.yonyou.iuap.persistence.jdbc.framework.annotation.GeneratedValue;
import com.yonyou.iuap.persistence.jdbc.framework.annotation.Id;
import com.yonyou.iuap.persistence.jdbc.framework.annotation.Stragegy;
import com.yonyou.iuap.persistence.jdbc.framework.annotation.Table;
import com.yonyou.iuap.persistence.vo.BaseEntity;


/**
 * The persistent class for the tm_model database table.
 * 
 */
@Entity
@Table(name="tm_model")
@NotDuplicateValid(claz=TmModelVO.class,field={"vannouncenum"},message="车辆型号不能重复")
public class TmModelVO extends BaseEntity {
	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy=Stragegy.UAPOID,moudle="tm_model")
	/**
	 * 主键id
	 */
	@Column(name = "pk_model")
	private String pk_model;
	/**
	 * 车型编码
	 */
	@Column(name = "vmodelcode")
	private String vmodelcode;
	/**
	 * 车型名称
	 */
	@NotNull(message="{username.not.empty}")
	@Column(name = "vmodelname")
	private String vmodelname;
	/**
	 * 所属车系
	 */
	@Column(name = "pk_series")
	private String pk_series;
	/**
	 * 车辆型号
	 */
	@Column(name = "vannouncenum")
	private String vannouncenum;
	/**
	 * 公告有效日期
	 */
	@Column(name = "vannouncevaliddate")
	private String vannouncevaliddate;
	/**
	 * 新能源推广目录
	 */
	@Column(name = "vmarketcatalog")
	private String vmarketcatalog;
	/**
	 * 整备质量
	 */
	@Column(name = "ncurbweight")
	private String ncurbweight;
	/**
	 * 最大质量
	 */
	@Column(name = "nmaxweight")
	private String nmaxweight;
	/**
	 * 轴距
	 */
	@Column(name = "nwheelbase")
	private String nwheelbase;
	/**
	 * 前悬/后悬
	 */
	@Column(name = "nsuspensionbase")
	private String nsuspensionbase;
	/**
	 * 离地间隙
	 */
	@Column(name = "ngroundclearanc")
	private String ngroundclearanc;
	/**
	 * 接近角/离去角
	 */
	@Column(name = "vdriveangle")
	private String vdriveangle;
	/**
	 * 入口高度
	 */
	@Column(name = "ninheight")
	private String ninheight;
	/**
	 * 地板高度
	 */
	@Column(name = "nfloorheight")
	private String nfloorheight;
	/**
	 * 车内最低高度
	 */
	@Column(name = "nminheight")
	private String nminheight;
	/**
	 * 轮包通道宽
	 */
	@Column(name = "vhubcapwidth")
	private String vhubcapwidth;
	/**
	 * 座位数
	 */
	@Column(name = "nseat")
	private String nseat;
	/**
	 * 载客数
	 */
	@Column(name = "npasenger")
	private String npasenger;
	/**
	 * 站立面积
	 */
	@Column(name = "nstandarea")
	private String nstandarea;
	/**
	 * 车门
	 */
	@Column(name = "ncardoor")
	private String ncardoor;
	/**
	 * 车门净宽
	 */
	@Column(name = "ndoorwidth")
	private String ndoorwidth;
	/**
	 * 轮胎形式
	 */
	@Column(name = "vtyreform")
	private String vtyreform;
	/**
	 * 驱动形式
	 */
	@Column(name = "vdriveform")
	private String vdriveform;
	/**
	 * 最高车速
	 */
	@Column(name = "nmaxspeed")
	private String nmaxspeed;
	/**
	 * 0-50km加速时间
	 */
	@Column(name = "nspeeduptime")
	private String nspeeduptime;
	/**
	 * 静态侧倾角
	 */
	@Column(name = "nrollangle")
	private String nrollangle;
	/**
	 * 通道圆半径
	 */
	@Column(name = "nradii")
	private String nradii;
	/**
	 * 继续行驶里程(40km/h 等速不开空调)
	 */
	@Column(name = "ndrivingcourse")
	private String ndrivingcourse;
	/**
	 * 继续行驶里程（摆渡车运营工况）
	 */
	@Column(name = "ndrivingcourse2")
	private String ndrivingcourse2;
	/**
	 * 电机额定最大功率(
	 */
	@Column(name = "vmotorpower")
	private String vmotorpower;
	/**
	 * 电机额定/最大扭矩
	 */
	@Column(name = "vmotortorque")
	private String vmotortorque;
	/**
	 * 电池形式
	 */
	@Column(name = "vbatteryclass")
	private String vbatteryclass;
	/**
	 * 电池电量（kwh）
	 */
	@Column(name = "nvbatterypower")
	private String nvbatterypower;
	/**
	 * 充电功率
	 */
	@Column(name = "nchargepower")
	private String nchargepower;
	/**
	 * 充电时间
	 */
	@Column(name = "nchargetime")
	private String nchargetime;
	/**
	 * 工信部车辆公告
	 */
	@Column(name = "vvehicleannouncement")
	private String vvehicleannouncement;
	/**
	 * 车辆状态
	 */
	@Column(name = "vstatus")
	private String vstatus;
	
	@Column(name="vusestatus")
	private String vusestatus;
	
	/**
	 * 备注
	 */
	@Column(name = "vremark")
	private String vremark;
	@Column(name ="vlength")
	private String vlength;
	@Column(name ="vwidth")
	private String vwidth;
	@Column(name ="vheight")
	private String vheight;
	
	@Column(name="vwarranty")
	private String vwarranty;
	@Column(name="vmilewarranty")
	private String vmilewarranty;
	@Column(name="vtirewarranty")
	private String vtirewarranty;
	
	/**
	 * 最新公告批次号
	 */
	@Column(name="vnewnoticenum")
	private String vnewnoticenum;
	/**
	 * 充电方式
	 */
	@Column(name="vchargetype")
	private String vchargetype;
	/**
	 * 免购置税目录
	 */
	@Column(name="vistaxfree")
	private String vistaxfree;
	/**
	 * 最大爬坡度
	 */
	@Column(name="vmaxclaim")
	private String vmaxclaim;
	/**
	 * 转弯半径
	 */
	@Column(name="vturnradii")
	private String vturnradii;
	/**
	 * 充电枪个数
	 */
	@Column(name="nchargegun")
	private String nchargegun;
	
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
	
	
	

	public TmModelVO() {
	}


	public String getPk_model() {
		return this.pk_model;
	}

	public void setPk_model(String pk_model) {
		this.pk_model = pk_model;
	}


	public String getVmodelcode() {
		return this.vmodelcode;
	}

	public void setVmodelcode(String vmodelcode) {
		this.vmodelcode = vmodelcode;
	}


	public String getVmodelname() {
		return this.vmodelname;
	}

	public void setVmodelname(String vmodelname) {
		this.vmodelname = vmodelname;
	}


	public String getPk_series() {
		return this.pk_series;
	}

	public void setPk_series(String pk_series) {
		this.pk_series = pk_series;
	}


	public String getVannouncenum() {
		return this.vannouncenum;
	}

	public void setVannouncenum(String vannouncenum) {
		this.vannouncenum = vannouncenum;
	}


	public String getVannouncevaliddate() {
		return this.vannouncevaliddate;
	}

	public void setVannouncevaliddate(String vannouncevaliddate) {
		this.vannouncevaliddate = vannouncevaliddate;
	}


	public String getVmarketcatalog() {
		return this.vmarketcatalog;
	}

	public void setVmarketcatalog(String vmarketcatalog) {
		this.vmarketcatalog = vmarketcatalog;
	}


	public String getNcurbweight() {
		return this.ncurbweight;
	}

	public void setNcurbweight(String ncurbweight) {
		this.ncurbweight = ncurbweight;
	}


	public String getNmaxweight() {
		return this.nmaxweight;
	}

	public void setNmaxweight(String nmaxweight) {
		this.nmaxweight = nmaxweight;
	}


	public String getNwheelbase() {
		return this.nwheelbase;
	}

	public void setNwheelbase(String nwheelbase) {
		this.nwheelbase = nwheelbase;
	}


	public String getNsuspensionbase() {
		return this.nsuspensionbase;
	}

	public void setNsuspensionbase(String nsuspensionbase) {
		this.nsuspensionbase = nsuspensionbase;
	}


	public String getNgroundclearanc() {
		return this.ngroundclearanc;
	}

	public void setNgroundclearanc(String ngroundclearanc) {
		this.ngroundclearanc = ngroundclearanc;
	}


	public String getVdriveangle() {
		return this.vdriveangle;
	}

	public void setVdriveangle(String vdriveangle) {
		this.vdriveangle = vdriveangle;
	}


	public String getNinheight() {
		return this.ninheight;
	}

	public void setNinheight(String ninheight) {
		this.ninheight = ninheight;
	}


	public String getNfloorheight() {
		return this.nfloorheight;
	}

	public void setNfloorheight(String nfloorheight) {
		this.nfloorheight = nfloorheight;
	}


	public String getNminheight() {
		return this.nminheight;
	}

	public void setNminheight(String nminheight) {
		this.nminheight = nminheight;
	}


	public String getVhubcapwidth() {
		return this.vhubcapwidth;
	}

	public void setVhubcapwidth(String vhubcapwidth) {
		this.vhubcapwidth = vhubcapwidth;
	}


	public String getNseat() {
		return this.nseat;
	}

	public void setNseat(String nseat) {
		this.nseat = nseat;
	}


	public String getNpasenger() {
		return this.npasenger;
	}

	public void setNpasenger(String npasenger) {
		this.npasenger = npasenger;
	}


	public String getNstandarea() {
		return this.nstandarea;
	}

	public void setNstandarea(String nstandarea) {
		this.nstandarea = nstandarea;
	}
	
	public String getNcardoor() {
		return ncardoor;
	}


	public void setNcardoor(String ncardoor) {
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


	public String getNmaxspeed() {
		return this.nmaxspeed;
	}

	public void setNmaxspeed(String nmaxspeed) {
		this.nmaxspeed = nmaxspeed;
	}


	public String getNspeeduptime() {
		return this.nspeeduptime;
	}

	public void setNspeeduptime(String nspeeduptime) {
		this.nspeeduptime = nspeeduptime;
	}


	public String getNrollangle() {
		return this.nrollangle;
	}

	public void setNrollangle(String nrollangle) {
		this.nrollangle = nrollangle;
	}


	public String getNradii() {
		return this.nradii;
	}

	public void setNradii(String nradii) {
		this.nradii = nradii;
	}


	public String getNdrivingcourse() {
		return this.ndrivingcourse;
	}

	public void setNdrivingcourse(String ndrivingcourse) {
		this.ndrivingcourse = ndrivingcourse;
	}


	public String getNdrivingcourse2() {
		return this.ndrivingcourse2;
	}

	public void setNdrivingcourse2(String ndrivingcourse2) {
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


	public String getNchargepower() {
		return this.nchargepower;
	}

	public void setNchargepower(String nchargepower) {
		this.nchargepower = nchargepower;
	}


	public String getNchargetime() {
		return this.nchargetime;
	}

	public void setNchargetime(String nchargetime) {
		this.nchargetime = nchargetime;
	}


	public String getVvehicleannouncement() {
		return this.vvehicleannouncement;
	}

	public void setVvehicleannouncement(String vvehicleannouncement) {
		this.vvehicleannouncement = vvehicleannouncement;
	}


	public String getVstatus() {
		return this.vstatus;
	}

	public void setVstatus(String vstatus) {
		this.vstatus = vstatus;
	}


	public String getVremark() {
		return this.vremark;
	}

	public void setVremark(String vremark) {
		this.vremark = vremark;
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
        return "tm_model";
    }

    @Override
    public String getNamespace() {
		return "com.yonyou.iuap.crm.basedata.entity";
    }


	public String getVlength() {
		return vlength;
	}


	public void setVlength(String vlength) {
		this.vlength = vlength;
	}


	public String getVwidth() {
		return vwidth;
	}


	public void setVwidth(String vwidth) {
		this.vwidth = vwidth;
	}


	public String getVheight() {
		return vheight;
	}


	public void setVheight(String vheight) {
		this.vheight = vheight;
	}


	public String getVusestatus() {
		return vusestatus;
	}


	public void setVusestatus(String vusestatus) {
		this.vusestatus = vusestatus;
	}


	public String getNvbatterypower() {
		return nvbatterypower;
	}


	public void setNvbatterypower(String nvbatterypower) {
		this.nvbatterypower = nvbatterypower;
	}


	public String getVwarranty() {
		return vwarranty;
	}


	public void setVwarranty(String vwarranty) {
		this.vwarranty = vwarranty;
	}


	public String getVmilewarranty() {
		return vmilewarranty;
	}


	public void setVmilewarranty(String vmilewarranty) {
		this.vmilewarranty = vmilewarranty;
	}


	public String getVtirewarranty() {
		return vtirewarranty;
	}


	public void setVtirewarranty(String vtirewarranty) {
		this.vtirewarranty = vtirewarranty;
	}


	public String getVnewnoticenum() {
		return vnewnoticenum;
	}


	public void setVnewnoticenum(String vnewnoticenum) {
		this.vnewnoticenum = vnewnoticenum;
	}


	public String getVchargetype() {
		return vchargetype;
	}


	public void setVchargetype(String vchargetype) {
		this.vchargetype = vchargetype;
	}


	public String getVistaxfree() {
		return vistaxfree;
	}


	public void setVistaxfree(String vistaxfree) {
		this.vistaxfree = vistaxfree;
	}


	public String getVmaxclaim() {
		return vmaxclaim;
	}


	public void setVmaxclaim(String vmaxclaim) {
		this.vmaxclaim = vmaxclaim;
	}


	public String getVturnradii() {
		return vturnradii;
	}


	public void setVturnradii(String vturnradii) {
		this.vturnradii = vturnradii;
	}


	public String getNchargegun() {
		return nchargegun;
	}


	public void setNchargegun(String nchargegun) {
		this.nchargegun = nchargegun;
	}
	
	

}