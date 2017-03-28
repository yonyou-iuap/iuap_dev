package com.yonyou.iuaptraincrm.test_orgref;

import com.yonyou.iuap.persistence.jdbc.framework.annotation.Column;
import com.yonyou.iuap.persistence.jdbc.framework.annotation.Entity;
import com.yonyou.iuap.persistence.jdbc.framework.annotation.GeneratedValue;
import com.yonyou.iuap.persistence.jdbc.framework.annotation.Id;
import com.yonyou.iuap.persistence.jdbc.framework.annotation.FK;
import com.yonyou.iuap.persistence.jdbc.framework.annotation.OneToMany;
import com.yonyou.iuap.persistence.jdbc.framework.annotation.Stragegy;
import com.yonyou.iuap.persistence.jdbc.framework.annotation.Table;
import com.yonyou.iuap.persistence.vo.BaseEntity;


/**
 * <b> 此处简要描述此类功能 </b>
 * <p>
 *   此处添加类的描述信息
 * </p>
 *  创建日期:2017-3-17
 * @author 
 * @version 
 */
@Entity(namespace = "iuaptraincrm",name = "testorgref")
@Table(name = "iuaptraincrm_testorgref")
public class Testorgref extends BaseEntity{
	
    @Id
    @GeneratedValue(strategy = Stragegy.UUID, moudle = "")  
    @Column(name = "pk_test")
    private java.lang.String pk_test;
      
    @Column(name = "name")
    private java.lang.String name;
      
    @Column(name = "city")
    private java.lang.String city;
      
    @Column(name = "creator")
    private java.lang.String creator;
      
    @Column(name = "org")
    private java.lang.String org;
      
    @Column(name = "pk_user")
    private java.lang.String pk_user;
      
    @Column(name = "pk_psn")
    private java.lang.String pk_psn;
      
    @Column(name = "dr")
    private java.lang.Integer dr = 0;
      
    @Column(name = "ts")
    private java.util.Date ts;
        
	
	

	/**
	 * 属性 pk_test的Getter方法.属性名：主键
	 *  创建日期:2017-3-17
	 * @return java.lang.String
	 */
	public java.lang.String getPk_test () {
		return pk_test;
	}
	   
	/**
	 * 属性pk_test的Setter方法.属性名：主键
	 * 创建日期:2017-3-17
	 * @param newPk_test java.lang.String
	 */
	public void setPk_test (java.lang.String newPk_test ) {
	 	this.pk_test = newPk_test;
	} 	 
	
	/**
	 * 属性 name的Getter方法.属性名：名称
	 *  创建日期:2017-3-17
	 * @return java.lang.String
	 */
	public java.lang.String getName () {
		return name;
	}
	   
	/**
	 * 属性name的Setter方法.属性名：名称
	 * 创建日期:2017-3-17
	 * @param newName java.lang.String
	 */
	public void setName (java.lang.String newName ) {
	 	this.name = newName;
	} 	 
	
	/**
	 * 属性 city的Getter方法.属性名：城市
	 *  创建日期:2017-3-17
	 * @return java.lang.String
	 */
	public java.lang.String getCity () {
		return city;
	}
	   
	/**
	 * 属性city的Setter方法.属性名：城市
	 * 创建日期:2017-3-17
	 * @param newCity java.lang.String
	 */
	public void setCity (java.lang.String newCity ) {
	 	this.city = newCity;
	} 	 
	
	/**
	 * 属性 creator的Getter方法.属性名：修改人
	 *  创建日期:2017-3-17
	 * @return java.lang.String
	 */
	public java.lang.String getCreator () {
		return creator;
	}
	   
	/**
	 * 属性creator的Setter方法.属性名：修改人
	 * 创建日期:2017-3-17
	 * @param newCreator java.lang.String
	 */
	public void setCreator (java.lang.String newCreator ) {
	 	this.creator = newCreator;
	} 	 
	
	/**
	 * 属性 org的Getter方法.属性名：组织
	 *  创建日期:2017-3-17
	 * @return java.lang.String
	 */
	public java.lang.String getOrg () {
		return org;
	}
	   
	/**
	 * 属性org的Setter方法.属性名：组织
	 * 创建日期:2017-3-17
	 * @param newOrg java.lang.String
	 */
	public void setOrg (java.lang.String newOrg ) {
	 	this.org = newOrg;
	} 	 
	
	/**
	 * 属性 pk_user的Getter方法.属性名：用户
	 *  创建日期:2017-3-17
	 * @return java.lang.String
	 */
	public java.lang.String getPk_user () {
		return pk_user;
	}
	   
	/**
	 * 属性pk_user的Setter方法.属性名：用户
	 * 创建日期:2017-3-17
	 * @param newPk_user java.lang.String
	 */
	public void setPk_user (java.lang.String newPk_user ) {
	 	this.pk_user = newPk_user;
	} 	 
	
	/**
	 * 属性 pk_psn的Getter方法.属性名：人员
	 *  创建日期:2017-3-17
	 * @return java.lang.String
	 */
	public java.lang.String getPk_psn () {
		return pk_psn;
	}
	   
	/**
	 * 属性pk_psn的Setter方法.属性名：人员
	 * 创建日期:2017-3-17
	 * @param newPk_psn java.lang.String
	 */
	public void setPk_psn (java.lang.String newPk_psn ) {
	 	this.pk_psn = newPk_psn;
	} 	 
	
	/**
	 * 属性 dr的Getter方法.属性名：dr
	 *  创建日期:2017-3-17
	 * @return java.lang.Integer
	 */
	public java.lang.Integer getDr () {
		return dr;
	}
	   
	/**
	 * 属性dr的Setter方法.属性名：dr
	 * 创建日期:2017-3-17
	 * @param newDr java.lang.Integer
	 */
	public void setDr (java.lang.Integer newDr ) {
	 	this.dr = newDr;
	} 	 
	
	/**
	 * 属性 ts的Getter方法.属性名：ts
	 *  创建日期:2017-3-17
	 * @return java.util.Date
	 */
	public java.util.Date getTs () {
		return ts;
	}
	   
	/**
	 * 属性ts的Setter方法.属性名：ts
	 * 创建日期:2017-3-17
	 * @param newTs java.util.Date
	 */
	public void setTs (java.util.Date newTs ) {
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