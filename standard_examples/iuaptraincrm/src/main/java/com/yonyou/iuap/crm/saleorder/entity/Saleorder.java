package com.yonyou.iuap.crm.saleorder.entity;

import java.util.Date;
	import java.util.List;
	

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.yonyou.iuap.persistence.jdbc.framework.annotation.Column;
import com.yonyou.iuap.persistence.jdbc.framework.annotation.Entity;
import com.yonyou.iuap.persistence.jdbc.framework.annotation.GeneratedValue;
import com.yonyou.iuap.persistence.jdbc.framework.annotation.Id;
import com.yonyou.iuap.persistence.jdbc.framework.annotation.OneToMany;
import com.yonyou.iuap.persistence.jdbc.framework.annotation.Stragegy;
import com.yonyou.iuap.persistence.jdbc.framework.annotation.Table;
import com.yonyou.iuap.persistence.vo.BaseEntity;


/**
 * <b> 此处简要描述此类功能 </b>
 * <p>
 *   此处添加类的描述信息
 * </p>
 *  创建日期:2016-11-17
 * @author 
 * @version 
 */
@JsonIgnoreProperties(ignoreUnknown = true)
@Entity(namespace = "iuaptraincrm",name = "saleorder")
@Table(name = "train_saleorder")
public class Saleorder extends BaseEntity{
	@Id
	@GeneratedValue(strategy=Stragegy.UUID,moudle="")
	@Column(name = "pk_projectapp")
	private String pk_projectapp;

	@Column(name = "projectcode")
	private String projectcode;

	@Column(name = "pk_org")
	private String pk_org;

	private String pk_org_name;  //参照需要显示的名字
	
	@Column(name = "vbillcode")
	private String vbillcode;

	@Column(name = "dbilldate")
	private Date dbilldate;

	@Column(name = "ccustomerid")
	private String ccustomerid;

	private String ccustomerid_name;  //参照需要显示的名字
	
	@Column(name = "cchanneltypeid")
	private String cchanneltypeid;

	@Column(name = "cemployeeid")
	private String cemployeeid;

	private String cemployeeid_name;  //参照需要显示的名字
	
	@Column(name = "cdeptvid")
	private String cdeptvid;

	@Column(name = "cpaytermid")
	private String cpaytermid;

	@Column(name = "ndiscountrate")
	private Double ndiscountrate;

	@Column(name = "corigcurrencyid")
	private String corigcurrencyid;

	private String corigcurrencyid_name;  //参照需要显示的名字
	
	@Column(name = "ctransporttypeid")
	private String ctransporttypeid;

	@Column(name = "ntotalnum")
	private Integer ntotalnum;

	@Column(name = "ntotalorigmny")
	private Double ntotalorigmny;

	@Column(name = "fstatusflag")
	private Integer fstatusflag;

	@Column(name = "vnote")
	private String vnote;

	@Column(name = "billmaker")
	private String billmaker;

	@Column(name = "creationtime")
	private Date creationtime;

	@Column(name = "modifier")
	private String modifier;

	@Column(name = "modifiedtime")
	private Date modifiedtime;

	@Column(name = "vdef10")
	private String vdef10;

	@Column(name = "vdef9")
	private String vdef9;

	@Column(name = "vdef8")
	private String vdef8;

	@Column(name = "vdef7")
	private String vdef7;

	@Column(name = "vdef6")
	private String vdef6;

	@Column(name = "vdef5")
	private String vdef5;

	@Column(name = "vdef4")
	private String vdef4;

	@Column(name = "vdef3")
	private String vdef3;

	@Column(name = "vdef2")
	private String vdef2;

	@Column(name = "vdef1")
	private String vdef1;

	@OneToMany(targetEntity = Orderdetail.class)
	private List<Orderdetail> id_orderdetail;
	
	@OneToMany(targetEntity = Receiveinfo.class)
	private List<Receiveinfo> id_receiveinfo;

	@Column(name = "billtype")
	private String billtype;

	@Column(name = "dr")
    private java.lang.Integer dr = 0 ;
      
    @Column(name = "ts")
    private java.sql.Timestamp ts ;


	public String getPk_projectapp() {
		return this.pk_projectapp;
	}

	public void setPk_projectapp(String pk_projectapp) {
		this.pk_projectapp = pk_projectapp;
	}


	public String getProjectcode() {
		return this.projectcode;
	}

	public void setProjectcode(String projectcode) {
		this.projectcode = projectcode;
	}


	public String getPk_org() {
		return this.pk_org;
	}

	public void setPk_org(String pk_org) {
		this.pk_org = pk_org;
	}

	public String getPk_org_name() {
		return this.pk_org_name;
	}

	public void setPk_org_name(String name) {
		this.pk_org_name = name;
	}
	

	public String getVbillcode() {
		return this.vbillcode;
	}

	public void setVbillcode(String vbillcode) {
		this.vbillcode = vbillcode;
	}


	public Date getDbilldate() {
		return this.dbilldate;
	}

	public void setDbilldate(Date dbilldate) {
		this.dbilldate = dbilldate;
	}


	public String getCcustomerid() {
		return this.ccustomerid;
	}

	public void setCcustomerid(String ccustomerid) {
		this.ccustomerid = ccustomerid;
	}

	public String getCcustomerid_name() {
		return this.ccustomerid_name;
	}

	public void setCcustomerid_name(String name) {
		this.ccustomerid_name = name;
	}
	

	public String getCchanneltypeid() {
		return this.cchanneltypeid;
	}

	public void setCchanneltypeid(String cchanneltypeid) {
		this.cchanneltypeid = cchanneltypeid;
	}


	public String getCemployeeid() {
		return this.cemployeeid;
	}

	public void setCemployeeid(String cemployeeid) {
		this.cemployeeid = cemployeeid;
	}

	public String getCemployeeid_name() {
		return this.cemployeeid_name;
	}

	public void setCemployeeid_name(String name) {
		this.cemployeeid_name = name;
	}
	

	public String getCdeptvid() {
		return this.cdeptvid;
	}

	public void setCdeptvid(String cdeptvid) {
		this.cdeptvid = cdeptvid;
	}


	public String getCpaytermid() {
		return this.cpaytermid;
	}

	public void setCpaytermid(String cpaytermid) {
		this.cpaytermid = cpaytermid;
	}


	public Double getNdiscountrate() {
		return this.ndiscountrate;
	}

	public void setNdiscountrate(Double ndiscountrate) {
		this.ndiscountrate = ndiscountrate;
	}


	public String getCorigcurrencyid() {
		return this.corigcurrencyid;
	}

	public void setCorigcurrencyid(String corigcurrencyid) {
		this.corigcurrencyid = corigcurrencyid;
	}

	public String getCorigcurrencyid_name() {
		return this.corigcurrencyid_name;
	}

	public void setCorigcurrencyid_name(String name) {
		this.corigcurrencyid_name = name;
	}
	

	public String getCtransporttypeid() {
		return this.ctransporttypeid;
	}

	public void setCtransporttypeid(String ctransporttypeid) {
		this.ctransporttypeid = ctransporttypeid;
	}


	public Integer getNtotalnum() {
		return this.ntotalnum;
	}

	public void setNtotalnum(Integer ntotalnum) {
		this.ntotalnum = ntotalnum;
	}


	public Double getNtotalorigmny() {
		return this.ntotalorigmny;
	}

	public void setNtotalorigmny(Double ntotalorigmny) {
		this.ntotalorigmny = ntotalorigmny;
	}


	public Integer getFstatusflag() {
		return this.fstatusflag;
	}

	public void setFstatusflag(Integer fstatusflag) {
		this.fstatusflag = fstatusflag;
	}


	public String getVnote() {
		return this.vnote;
	}

	public void setVnote(String vnote) {
		this.vnote = vnote;
	}


	public String getBillmaker() {
		return this.billmaker;
	}

	public void setBillmaker(String billmaker) {
		this.billmaker = billmaker;
	}


	public Date getCreationtime() {
		return this.creationtime;
	}

	public void setCreationtime(Date creationtime) {
		this.creationtime = creationtime;
	}


	public String getModifier() {
		return this.modifier;
	}

	public void setModifier(String modifier) {
		this.modifier = modifier;
	}


	public Date getModifiedtime() {
		return this.modifiedtime;
	}

	public void setModifiedtime(Date modifiedtime) {
		this.modifiedtime = modifiedtime;
	}


	public String getVdef10() {
		return this.vdef10;
	}

	public void setVdef10(String vdef10) {
		this.vdef10 = vdef10;
	}


	public String getVdef9() {
		return this.vdef9;
	}

	public void setVdef9(String vdef9) {
		this.vdef9 = vdef9;
	}


	public String getVdef8() {
		return this.vdef8;
	}

	public void setVdef8(String vdef8) {
		this.vdef8 = vdef8;
	}


	public String getVdef7() {
		return this.vdef7;
	}

	public void setVdef7(String vdef7) {
		this.vdef7 = vdef7;
	}


	public String getVdef6() {
		return this.vdef6;
	}

	public void setVdef6(String vdef6) {
		this.vdef6 = vdef6;
	}


	public String getVdef5() {
		return this.vdef5;
	}

	public void setVdef5(String vdef5) {
		this.vdef5 = vdef5;
	}


	public String getVdef4() {
		return this.vdef4;
	}

	public void setVdef4(String vdef4) {
		this.vdef4 = vdef4;
	}


	public String getVdef3() {
		return this.vdef3;
	}

	public void setVdef3(String vdef3) {
		this.vdef3 = vdef3;
	}


	public String getVdef2() {
		return this.vdef2;
	}

	public void setVdef2(String vdef2) {
		this.vdef2 = vdef2;
	}


	public String getVdef1() {
		return this.vdef1;
	}

	public void setVdef1(String vdef1) {
		this.vdef1 = vdef1;
	}


	public List<Orderdetail> getId_orderdetail() {
		return this.id_orderdetail;
	}

	public void setId_orderdetail(List<Orderdetail> id_orderdetail) {
		this.id_orderdetail = id_orderdetail;
	}


	public String getBilltype() {
		return this.billtype;
	}

	public void setBilltype(String billtype) {
		this.billtype = billtype;
	}

	public java.lang.Integer getDr () {
		return dr;
	}
	
	public void setDr (java.lang.Integer newDr ) {
	 	this.dr = newDr;
	} 	
	
	public java.sql.Timestamp getTs () {
		return ts;
	}
	
	public void setTs (java.sql.Timestamp newTs ) {
	 	this.ts = newTs;
	} 
	
	public List<Receiveinfo> getId_receiveinfo() {
		return id_receiveinfo;
	}

	public void setId_receiveinfo(List<Receiveinfo> id_receiveinfo) {
		this.id_receiveinfo = id_receiveinfo;
	}

	@Override
    public String getMetaDefinedName() {
        return "saleorder";
    }

    @Override
    public String getNamespace() {
        return "iuaptraincrm";
    }
}