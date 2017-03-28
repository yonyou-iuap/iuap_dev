package com.yonyou.iuap.example.entity.meta;

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
@Entity(namespace = "metadata",name = "sysUserJob")
@Table(name = "sys_user_job")
public class SysUserJob extends BaseEntity{
	 
    @FK(name = "fk_id_sys_user_job", referenceTableName = "sys_user", referencedColumnName = "id") 
    @Column(name = "fk_id_sys_user_job")
    private java.lang.String fk_id_sys_user_job;
    
    @Id
    @GeneratedValue(strategy = Stragegy.UUID, moudle = "")  
    @Column(name = "id")
    private java.lang.String id ;
      
    @Column(name = "org_id")
    private java.lang.String orgid ;
    
    private String orgname ;  //参照需要显示的名字
      
    @Column(name = "dept_id")
    private java.lang.String deptid ;
      
    private String deptname ;//参照需要显示的名字
    
    @Column(name = "user_id")
    private java.lang.String userid ;
      
    @Column(name = "user_name")
    private java.lang.String username ;
      
    @Column(name = "is_active")
    private java.lang.String isactive ;
      
    @Column(name = "is_default")
    private java.lang.String isdefault ;
      
    @Column(name = "user_job_note")
    private java.lang.String userjobnote ;
      
    @Column(name = "creator_id")
    private java.lang.String creatorid ;
      
    @Column(name = "creator")
    private java.lang.String creator ;
      
    @Column(name = "create_time")
    private java.util.Date createtime ;
      
    @Column(name = "modifier_id")
    private java.lang.String modifierid ;
      
    @Column(name = "modifier")
    private java.lang.String modifier ;
      
    @Column(name = "modify_time")
    private java.util.Date modifytime ;
      
    @Column(name = "dr")
    private java.lang.Integer dr = 0 ;
      
    @Column(name = "ts")
    private java.util.Date ts ;
        
	
	

	/**
	 * 属性 fk_id_sys_user_job的Getter方法.属性名：parentPK
	 *  创建日期:2016-11-17
	 * @return java.lang.String
	 */
	public java.lang.String getFk_id_sys_user_job () {
		return fk_id_sys_user_job;
	}
	   
	/**
	 * 属性fk_id_sys_user_job的Setter方法.属性名：parentPK
	 * 创建日期:2016-11-17
	 * @param newFk_id_sys_user_job java.lang.String
	 */
	public void setFk_id_sys_user_job (java.lang.String newFk_id_sys_user_job ) {
	 	this.fk_id_sys_user_job = newFk_id_sys_user_job;
	} 	 
	
	/**
	 * 属性 id的Getter方法.属性名：主键
	 *  创建日期:2016-11-17
	 * @return java.lang.String
	 */
	public java.lang.String getId () {
		return id;
	}
	   
	/**
	 * 属性id的Setter方法.属性名：主键
	 * 创建日期:2016-11-17
	 * @param newId java.lang.String
	 */
	public void setId (java.lang.String newId ) {
	 	this.id = newId;
	} 	 
	
	 
	 
	    
	
	   
	/**
	 * 属性 creator的Getter方法.属性名：创建人
	 *  创建日期:2016-11-17
	 * @return java.lang.String
	 */
	public java.lang.String getCreator () {
		return creator;
	}
	   
	/**
	 * 属性creator的Setter方法.属性名：创建人
	 * 创建日期:2016-11-17
	 * @param newCreator java.lang.String
	 */
	public void setCreator (java.lang.String newCreator ) {
	 	this.creator = newCreator;
	} 	 
	
	
	/**
	 * 属性 modifier的Getter方法.属性名：修改人
	 *  创建日期:2016-11-17
	 * @return java.lang.String
	 */
	public java.lang.String getModifier () {
		return modifier;
	}
	   
	/**
	 * 属性modifier的Setter方法.属性名：修改人
	 * 创建日期:2016-11-17
	 * @param newModifier java.lang.String
	 */
	public void setModifier (java.lang.String newModifier ) {
	 	this.modifier = newModifier;
	} 	 
	
	  	 
	public java.lang.String getOrgid() {
		return orgid;
	}

	public void setOrgid(java.lang.String orgid) {
		this.orgid = orgid;
	}

	public String getOrgname() {
		return orgname;
	}

	public void setOrgname(String orgname) {
		this.orgname = orgname;
	}

	public java.lang.String getDeptid() {
		return deptid;
	}

	public void setDeptid(java.lang.String deptid) {
		this.deptid = deptid;
	}

	public String getDeptname() {
		return deptname;
	}

	public void setDeptname(String deptname) {
		this.deptname = deptname;
	}

	public java.lang.String getUserid() {
		return userid;
	}

	public void setUserid(java.lang.String userid) {
		this.userid = userid;
	}

	public java.lang.String getUsername() {
		return username;
	}

	public void setUsername(java.lang.String username) {
		this.username = username;
	}

	public java.lang.String getIsactive() {
		return isactive;
	}

	public void setIsactive(java.lang.String isactive) {
		this.isactive = isactive;
	}

	public java.lang.String getIsdefault() {
		return isdefault;
	}

	public void setIsdefault(java.lang.String isdefault) {
		this.isdefault = isdefault;
	}

	public java.lang.String getUserjobnote() {
		return userjobnote;
	}

	public void setUserjobnote(java.lang.String userjobnote) {
		this.userjobnote = userjobnote;
	}

	public java.lang.String getCreatorid() {
		return creatorid;
	}

	public void setCreatorid(java.lang.String creatorid) {
		this.creatorid = creatorid;
	}

	public java.util.Date getCreatetime() {
		return createtime;
	}

	public void setCreatetime(java.util.Date createtime) {
		this.createtime = createtime;
	}

	public java.lang.String getModifierid() {
		return modifierid;
	}

	public void setModifierid(java.lang.String modifierid) {
		this.modifierid = modifierid;
	}

	public java.util.Date getModifytime() {
		return modifytime;
	}

	public void setModifytime(java.util.Date modifytime) {
		this.modifytime = modifytime;
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
        return "sysUserJob";
    }

    @Override
    public String getNamespace() {
        return "metadata";
    }
    
    
}