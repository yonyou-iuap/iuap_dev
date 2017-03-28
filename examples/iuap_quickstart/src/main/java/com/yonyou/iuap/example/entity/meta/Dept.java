package com.yonyou.iuap.example.entity.meta;

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
 *  创建日期:2016-12-13
 * @author 
 * @version 
 */
@Entity(namespace = "metadata",name = "dept")
@Table(name = "dept")
public class Dept extends BaseEntity{
	
    @Id
    @GeneratedValue(strategy = Stragegy.UUID, moudle = "")  
    @Column(name = "pk_dept")
    private java.lang.String pk_dept;
      
    @Column(name = "org_id")
    private java.lang.String orgid;
      
    @Column(name = "dept_code")
    private java.lang.String deptcode;
      
    @Column(name = "dept_name")
    private java.lang.String deptname;
      
    @Column(name = "parent_id")
    private java.lang.String parentid;
      
    @Column(name = "dept_type")
    private java.lang.String depttype;
      
    @Column(name = "createdate")
    private java.util.Date createdate;
      
    @Column(name = "short_name")
    private java.lang.String shortname;
      
    @Column(name = "creator")
    private java.lang.String creator;
      
    @Column(name = "creationtime")
    private java.util.Date creationtime;
      
    @Column(name = "deptcanceldate")
    private java.util.Date deptcanceldate;
      
    @Column(name = "dr")
    private java.lang.Integer dr = 0;
      
    @Column(name = "ts")
    private java.util.Date ts;
        
	
	

	/**
	 * 属性 pk_dept的Getter方法.属性名：部门主键
	 *  创建日期:2016-12-13
	 * @return java.lang.String
	 */
	public java.lang.String getPk_dept () {
		return pk_dept;
	}
	   
	/**
	 * 属性pk_dept的Setter方法.属性名：部门主键
	 * 创建日期:2016-12-13
	 * @param newPk_dept java.lang.String
	 */
	public void setPk_dept (java.lang.String newPk_dept ) {
	 	this.pk_dept = newPk_dept;
	} 	 
	
	 
	public java.lang.String getOrgid() {
		return orgid;
	}

	public void setOrgid(java.lang.String orgid) {
		this.orgid = orgid;
	}

	public java.lang.String getDeptcode() {
		return deptcode;
	}

	public void setDeptcode(java.lang.String deptcode) {
		this.deptcode = deptcode;
	}

	public java.lang.String getDeptname() {
		return deptname;
	}

	public void setDeptname(java.lang.String deptname) {
		this.deptname = deptname;
	}

	public java.lang.String getParentid() {
		return parentid;
	}

	public void setParentid(java.lang.String parentid) {
		this.parentid = parentid;
	}

	public java.lang.String getDepttype() {
		return depttype;
	}

	public void setDepttype(java.lang.String depttype) {
		this.depttype = depttype;
	}

	public java.util.Date getCreatedate() {
		return createdate;
	}

	public void setCreatedate(java.util.Date createdate) {
		this.createdate = createdate;
	}

	public java.lang.String getShortname() {
		return shortname;
	}

	public void setShortname(java.lang.String shortname) {
		this.shortname = shortname;
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

	public java.util.Date getDeptcanceldate() {
		return deptcanceldate;
	}

	public void setDeptcanceldate(java.util.Date deptcanceldate) {
		this.deptcanceldate = deptcanceldate;
	}

	/**
	 * 属性 dr的Getter方法.属性名：dr
	 *  创建日期:2016-12-13
	 * @return java.lang.Integer
	 */
	public java.lang.Integer getDr () {
		return dr;
	}
	   
	/**
	 * 属性dr的Setter方法.属性名：dr
	 * 创建日期:2016-12-13
	 * @param newDr java.lang.Integer
	 */
	public void setDr (java.lang.Integer newDr ) {
	 	this.dr = newDr;
	} 	 
	
	/**
	 * 属性 ts的Getter方法.属性名：ts
	 *  创建日期:2016-12-13
	 * @return java.util.Date
	 */
	public java.util.Date getTs () {
		return ts;
	}
	   
	/**
	 * 属性ts的Setter方法.属性名：ts
	 * 创建日期:2016-12-13
	 * @param newTs java.util.Date
	 */
	public void setTs (java.util.Date newTs ) {
	 	this.ts = newTs;
	} 	 
	
    @Override
    public String getMetaDefinedName() {
        return "dept";
    }

    @Override
    public String getNamespace() {
        return "metadata";
    }
}