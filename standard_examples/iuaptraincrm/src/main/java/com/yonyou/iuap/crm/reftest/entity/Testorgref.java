package com.yonyou.iuap.crm.reftest.entity;

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
 * @author 
 * @version 
 */
@Entity(namespace = "iuaptraincrm",name = "testorgref")
@Table(name="iuaptraincrm_testorgref")
public class Testorgref extends BaseEntity {
	  
	@Id
	@GeneratedValue(strategy=Stragegy.UUID,moudle="")
	@Column(name = "pk_test")
	private String pk_test;
	
	@Column(name = "name")
	private String name;
	
	@Column(name = "city")
	private String city;
	
	@Column(name = "creator")
	private String creator;
	
	@Column(name = "org")
	private String org;
	
	private String org_name;  //参照需要显示的名字
	
	@Column(name = "id")
	private String pk_user;
	
	private String pk_user_name;  //参照需要显示的名字
	
	@Column(name = "pk_psn")
	private String pk_psn;
	
	private String pk_psn_name;  //参照需要显示的名字
	

	@Column(name = "dr")
    private java.lang.Integer dr = 0 ;
      
    @Column(name = "ts")
    private java.sql.Timestamp ts ;    	

	public String getPk_test() {
		return this.pk_test;
	}

	public void setPk_test(String pk_test) {
		this.pk_test = pk_test;
	}
	

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}
	

	public String getCity() {
		return this.city;
	}

	public void setCity(String city) {
		this.city = city;
	}
	

	public String getCreator() {
		return this.creator;
	}

	public void setCreator(String creator) {
		this.creator = creator;
	}
	

	public String getOrg() {
		return this.org;
	}

	public void setOrg(String org) {
		this.org = org;
	}
	
	public String getOrg_name() {
		return this.org_name;
	}

	public void setOrg_name(String name) {
		this.org_name = name;
	}
	

	public String getPk_user() {
		return this.pk_user;
	}

	public void setPk_user(String pk_user) {
		this.pk_user = pk_user;
	}
	
	public String getPk_user_name() {
		return this.pk_user_name;
	}

	public void setPk_user_name(String name) {
		this.pk_user_name = name;
	}
	

	public String getPk_psn() {
		return this.pk_psn;
	}

	public void setPk_psn(String pk_psn) {
		this.pk_psn = pk_psn;
	}
	
	public String getPk_psn_name() {
		return this.pk_psn_name;
	}

	public void setPk_psn_name(String name) {
		this.pk_psn_name = name;
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
	
	@Override
    public String getMetaDefinedName() {
	        return "testorgref";
	    }

	    @Override
	    public String getNamespace() {
	        return "iuaptraincrm";
	    }
}