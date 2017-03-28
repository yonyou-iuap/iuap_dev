package com.yonyou.iuap.crm.test.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.yonyou.iuap.persistence.jdbc.framework.annotation.Column;
import com.yonyou.iuap.persistence.jdbc.framework.annotation.Entity;
import com.yonyou.iuap.persistence.jdbc.framework.annotation.FK;
import com.yonyou.iuap.persistence.jdbc.framework.annotation.GeneratedValue;
import com.yonyou.iuap.persistence.jdbc.framework.annotation.Id;
import com.yonyou.iuap.persistence.jdbc.framework.annotation.Stragegy;
import com.yonyou.iuap.persistence.jdbc.framework.annotation.Table;
import com.yonyou.iuap.persistence.vo.BaseEntity;

import java.util.List;
	


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
@Entity(namespace = "iuaptraincrm",name = "TestB")
@Table(name = "iuaptraincrm_TestB")
public class TestB extends BaseEntity{
	 
    @FK(name = "fk_id_testb", referenceTableName = "iuaptraincrm_Test", referencedColumnName = "id") 
    @Column(name = "fk_id_testb")
    private java.lang.String fk_id_testb;
    
	@Id
	@GeneratedValue(strategy=Stragegy.UUID,moudle="")
	@Column(name = "id")
	private String id;
	
	@Column(name = "memo")
	private String memo;
	
	@Column(name = "dr")
    private java.lang.Integer dr = 0 ;
      
    @Column(name = "ts")
    private java.util.Date ts ;
	

	/**
	 * 属性 fk的Getter方法.属性名：parentPK
	 *  创建日期:2016-11-17
	 * @return java.lang.String
	 */
	public java.lang.String getFk_id_testb() {
		return fk_id_testb;
	}
	   
	/**
	 * 属性fk的Setter方法.属性名：parentPK
	 * 创建日期:2016-11-17
	 * @param newFk java.lang.String
	 */
	public void setFk_id_testb (java.lang.String fk_id_testb ) {
	 	this.fk_id_testb = fk_id_testb;
	} 	 
	

	public String getId() {
		return this.id;
	}

	public void setId(String id) {
		this.id = id;
	}


	public String getMemo() {
		return this.memo;
	}

	public void setMemo(String memo) {
		this.memo = memo;
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
        return "TestB";
    }

    @Override
    public String getNamespace() {
        return "iuaptraincrm";
    }
}