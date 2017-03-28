package com.yonyou.iuap.example.entity.meta;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.yonyou.iuap.persistence.jdbc.framework.annotation.Column;
import com.yonyou.iuap.persistence.jdbc.framework.annotation.Entity;
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
@Entity(namespace = "metadata",name = "org")
@Table(name = "org")
public class Org extends BaseEntity{
	
    @Id
    @GeneratedValue(strategy = Stragegy.UUID, moudle = "")  
    @Column(name = "org_id")
   // @JsonProperty(value = "id")
    private java.lang.String orgid ;
      
    @Column(name = "org_code")
    private java.lang.String orgcode ;
      
    @Column(name = "org_name")
    private java.lang.String orgname ;
      
    @Column(name = "short_name")
    private java.lang.String shortname ;
      
    @Column(name = "org_type")
    private java.lang.String orgtype ;
      
    @Column(name = "email")
    private java.lang.String email ;
      
    @Column(name = "parent_id")
    private java.lang.String parentid ;
      
    @Column(name = "creator")
    private java.lang.String creator ;
      
    @Column(name = "creationtime")
    private java.util.Date creationtime ;
      
    @Column(name = "dr")
    private java.lang.Integer dr = 0 ;
      
    @Column(name = "ts")
    private java.util.Date ts ;
        
	
 
	public java.lang.String getOrgid() {
		return orgid;
	}

	public void setOrgid(java.lang.String orgid) {
		this.orgid = orgid;
	}

	public java.lang.String getOrgcode() {
		return orgcode;
	}

	public void setOrgcode(java.lang.String orgcode) {
		this.orgcode = orgcode;
	}

	public java.lang.String getOrgname() {
		return orgname;
	}

	public void setOrgname(java.lang.String orgname) {
		this.orgname = orgname;
	}

	public java.lang.String getShortname() {
		return shortname;
	}

	public void setShortname(java.lang.String shortname) {
		this.shortname = shortname;
	}

	public java.lang.String getOrgtype() {
		return orgtype;
	}

	public void setOrgtype(java.lang.String orgtype) {
		this.orgtype = orgtype;
	}

	public java.lang.String getEmail() {
		return email;
	}

	public void setEmail(java.lang.String email) {
		this.email = email;
	}

	public java.lang.String getParentid() {
		return parentid;
	}

	public void setParentid(java.lang.String parentid) {
		this.parentid = parentid;
	}

	public java.lang.String getCreator() {
		return creator;
	}

	public void setCreator(java.lang.String creator) {
		this.creator = creator;
	}

	public java.util.Date getCreationtime() {
		return creationtime;
	}

	public void setCreationtime(java.util.Date creationtime) {
		this.creationtime = creationtime;
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
        return "org";
    }

    @Override
    public String getNamespace() {
        return "metadata";
    }
}