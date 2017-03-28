package com.yonyou.iuap.crm.test.entity;

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
@Entity(namespace = "iuaptraincrm",name = "Test")
@Table(name = "iuaptraincrm_Test")
public class Test extends BaseEntity{
	@Id
	@GeneratedValue(strategy=Stragegy.UUID,moudle="")
	@Column(name = "id")
	private String id;

	@Column(name = "city")
	private String city;

	@Column(name = "currtype")
	private String currtype;

	private String currtype_name;  //参照需要显示的名字
	
	@OneToMany(targetEntity = TestB.class)
	private List<TestB> id_testb;

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


	public String getCurrtype() {
		return this.currtype;
	}

	public void setCurrtype(String currtype) {
		this.currtype = currtype;
	}

	public String getCurrtype_name() {
		return this.currtype_name;
	}

	public void setCurrtype_name(String name) {
		this.currtype_name = name;
	}
	

	public List<TestB> getId_testb() {
		return this.id_testb;
	}

	public void setId_testb(List<TestB> id_testb) {
		this.id_testb = id_testb;
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
        return "Test";
    }

    @Override
    public String getNamespace() {
        return "iuaptraincrm";
    }
}