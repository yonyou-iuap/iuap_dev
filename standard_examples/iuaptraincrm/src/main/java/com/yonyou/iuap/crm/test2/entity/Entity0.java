package com.yonyou.iuap.crm.test2.entity;

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
@Entity(namespace = "iuaptraincrm",name = "Entity0")
@Table(name="iuaptraincrm_Entity0")
public class Entity0 extends BaseEntity {
	  
	@Id
	@GeneratedValue(strategy=Stragegy.UUID,moudle="")
	@Column(name = "id")
	private String id;
	
	@Column(name = "city")
	private String city;
	
	@Column(name = "name")
	private String name;
	

	@Column(name = "dr")
    private java.lang.Integer dr = 0 ;
      
    @Column(name = "ts")
    private java.sql.Timestamp ts ;    	

	public String getId() {
		return this.id;
	}

	public void setId(String id) {
		this.id = id;
	}
	

	public String getCity() {
		return this.city;
	}

	public void setCity(String city) {
		this.city = city;
	}
	

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
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
	        return "Entity0";
	    }

	    @Override
	    public String getNamespace() {
	        return "iuaptraincrm";
	    }
}