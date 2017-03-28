package com.yonyou.iuap.crm.saleorder.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.yonyou.iuap.persistence.jdbc.framework.annotation.Column;
import com.yonyou.iuap.persistence.jdbc.framework.annotation.Entity;
import com.yonyou.iuap.persistence.jdbc.framework.annotation.FK;
import com.yonyou.iuap.persistence.jdbc.framework.annotation.GeneratedValue;
import com.yonyou.iuap.persistence.jdbc.framework.annotation.Id;
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
@Entity(namespace = "iuaptraincrm",name = "orderdetail")
@Table(name = "train_orderdetail")
public class Orderdetail extends BaseEntity{
	 
    @FK(name = "fk_id_orderdetail", referenceTableName = "train_saleorder", referencedColumnName = "pk_projectapp") 
    @Column(name = "fk_id_orderdetail")
    private java.lang.String fk_id_orderdetail;
    
	@Id
	@GeneratedValue(strategy=Stragegy.UUID,moudle="")
	@Column(name = "pk_orderdetail")
	private String pk_orderdetail;
	
	@Column(name = "cmaterialid")
	private String cmaterialid;
	
	@Column(name = "cmaterialvid")
	private String cmaterialvid;
	
	@Column(name = "cfactoryid")
	private String cfactoryid;
	
	@Column(name = "cunitid")
	private String cunitid;
	
	@Column(name = "castunitid")
	private String castunitid;
	
	@Column(name = "nnum")
	private Integer nnum;
	
	@Column(name = "nastnum")
	private Integer nastnum;
	
	@Column(name = "ndiscountrate")
	private Double ndiscountrate;
	
	@Column(name = "nqtorigprice")
	private Double nqtorigprice;
	
	@Column(name = "norigtaxmny")
	private Double norigtaxmny;
	
	@Column(name = "pk_org")
	private String pk_org;
	
	private String pk_org_name;  //参照需要显示的名字
	
	@Column(name = "dr")
    private java.lang.Integer dr = 0 ;
      
    @Column(name = "ts")
    private java.util.Date ts ;
	

	/**
	 * 属性 fk的Getter方法.属性名：parentPK
	 *  创建日期:2016-11-17
	 * @return java.lang.String
	 */
	public java.lang.String getFk_id_orderdetail() {
		return fk_id_orderdetail;
	}
	   
	/**
	 * 属性fk的Setter方法.属性名：parentPK
	 * 创建日期:2016-11-17
	 * @param newFk java.lang.String
	 */
	public void setFk_id_orderdetail (java.lang.String fk_id_orderdetail ) {
	 	this.fk_id_orderdetail = fk_id_orderdetail;
	} 	 
	

	public String getPk_orderdetail() {
		return this.pk_orderdetail;
	}

	public void setPk_orderdetail(String pk_orderdetail) {
		this.pk_orderdetail = pk_orderdetail;
	}


	public String getCmaterialid() {
		return this.cmaterialid;
	}

	public void setCmaterialid(String cmaterialid) {
		this.cmaterialid = cmaterialid;
	}


	public String getCmaterialvid() {
		return this.cmaterialvid;
	}

	public void setCmaterialvid(String cmaterialvid) {
		this.cmaterialvid = cmaterialvid;
	}


	public String getCfactoryid() {
		return this.cfactoryid;
	}

	public void setCfactoryid(String cfactoryid) {
		this.cfactoryid = cfactoryid;
	}


	public String getCunitid() {
		return this.cunitid;
	}

	public void setCunitid(String cunitid) {
		this.cunitid = cunitid;
	}


	public String getCastunitid() {
		return this.castunitid;
	}

	public void setCastunitid(String castunitid) {
		this.castunitid = castunitid;
	}


	public Integer getNnum() {
		return this.nnum;
	}

	public void setNnum(Integer nnum) {
		this.nnum = nnum;
	}


	public Integer getNastnum() {
		return this.nastnum;
	}

	public void setNastnum(Integer nastnum) {
		this.nastnum = nastnum;
	}


	public Double getNdiscountrate() {
		return this.ndiscountrate;
	}

	public void setNdiscountrate(Double ndiscountrate) {
		this.ndiscountrate = ndiscountrate;
	}


	public Double getNqtorigprice() {
		return this.nqtorigprice;
	}

	public void setNqtorigprice(Double nqtorigprice) {
		this.nqtorigprice = nqtorigprice;
	}


	public Double getNorigtaxmny() {
		return this.norigtaxmny;
	}

	public void setNorigtaxmny(Double norigtaxmny) {
		this.norigtaxmny = norigtaxmny;
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
	
	
	/**
	 * 属性 dr的Getter方法.属性名：dr
	 *  创建日期:2016-11-17
	 * @return java.lang.Integer
	 */
	public java.lang.Integer getDr () {
		return dr;
	}
	   
	/**
	 * 属性dr的Setter方法.属性名：dr
	 * 创建日期:2016-11-17
	 * @param newDr java.lang.Integer
	 */
	public void setDr (java.lang.Integer newDr ) {
	 	this.dr = newDr;
	} 	 
	
	/**
	 * 属性 ts的Getter方法.属性名：ts
	 *  创建日期:2016-11-17
	 * @return java.util.Date
	 */
	public java.util.Date getTs () {
		return ts;
	}
	   
	/**
	 * 属性ts的Setter方法.属性名：ts
	 * 创建日期:2016-11-17
	 * @param newTs java.util.Date
	 */
	public void setTs (java.util.Date newTs ) {
	 	this.ts = newTs;
	} 	 
	
	@Override
    public String getMetaDefinedName() {
        return "orderdetail";
    }

    @Override
    public String getNamespace() {
        return "iuaptraincrm";
    }
}