package com.yonyou.iuap.example.entity.meta;

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
@Entity(namespace = "metadata",name = "sysUser")
@Table(name = "sys_user")
public class SysUser extends BaseEntity{
	
    @Id
    @GeneratedValue(strategy = Stragegy.UUID, moudle = "")  
    @Column(name = "id")
    private java.lang.String id ;
      
    @Column(name = "org_id")
    private java.lang.String orgid ;
      
    @Column(name = "org_name")
    private java.lang.String orgname ;
      
    @Column(name = "login_name")
    private java.lang.String loginname ;
      
    @Column(name = "user_name")
    private java.lang.String username ;
      
    @Column(name = "user_code")
    private java.lang.String usercode ;
      
    @Column(name = "sex_id")
    private java.lang.String sexid ;
      
    @Column(name = "user_password")
    private java.lang.String userpassword ;
      
    @Column(name = "user_email")
    private java.lang.String useremail ;
      
    @Column(name = "user_mobile")
    private java.lang.String usermobile ;
      
    @Column(name = "certificate_id")
    private java.lang.String certificateid ;
      
    @Column(name = "certificate_name")
    private java.lang.String certificatename ;
      
    @Column(name = "certificate_code")
    private java.lang.String certificatecode ;
      
    @Column(name = "birthdate")
    private java.util.Date birthdate ;
      
    @Column(name = "register_date")
    private java.util.Date registerdate ;
      
    @Column(name = "platform_id")
    private java.lang.String platformid ;
      
    @Column(name = "platform_name")
    private java.lang.String platformname ;
      
    @Column(name = "level_id")
    private java.lang.String levelid ;
      
    @Column(name = "level_name")
    private java.lang.String levelname ;
      
    @Column(name = "image_url")
    private java.lang.String imageurl ;
      
    @Column(name = "status_id")
    private java.lang.String statusid ;
      
    @Column(name = "status_name")
    private java.lang.String statusname ;
      
    @Column(name = "remark")
    private java.lang.String remark ;
      
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
        
	
    @OneToMany(targetEntity = com.yonyou.iuap.example.entity.meta.SysUserJob.class)
    private java.util.List<com.yonyou.iuap.example.entity.meta.SysUserJob> idSysUserJob;
	

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
	
	  
	
	public java.lang.String getOrgid() {
		return orgid;
	}

	public void setOrgid(java.lang.String orgid) {
		this.orgid = orgid;
	}

	public java.lang.String getOrgname() {
		return orgname;
	}

	public void setOrgname(java.lang.String orgname) {
		this.orgname = orgname;
	}

	public java.lang.String getLoginname() {
		return loginname;
	}

	public void setLoginname(java.lang.String loginname) {
		this.loginname = loginname;
	}

	public java.lang.String getUsername() {
		return username;
	}

	public void setUsername(java.lang.String username) {
		this.username = username;
	}

	public java.lang.String getUsercode() {
		return usercode;
	}

	public void setUsercode(java.lang.String usercode) {
		this.usercode = usercode;
	}

	public java.lang.String getSexid() {
		return sexid;
	}

	public void setSexid(java.lang.String sexid) {
		this.sexid = sexid;
	}

	public java.lang.String getUserpassword() {
		return userpassword;
	}

	public void setUserpassword(java.lang.String userpassword) {
		this.userpassword = userpassword;
	}

	public java.lang.String getUseremail() {
		return useremail;
	}

	public void setUseremail(java.lang.String useremail) {
		this.useremail = useremail;
	}

	public java.lang.String getUsermobile() {
		return usermobile;
	}

	public void setUsermobile(java.lang.String usermobile) {
		this.usermobile = usermobile;
	}

	public java.lang.String getCertificateid() {
		return certificateid;
	}

	public void setCertificateid(java.lang.String certificateid) {
		this.certificateid = certificateid;
	}

	public java.lang.String getCertificatename() {
		return certificatename;
	}

	public void setCertificatename(java.lang.String certificatename) {
		this.certificatename = certificatename;
	}

	public java.lang.String getCertificatecode() {
		return certificatecode;
	}

	public void setCertificatecode(java.lang.String certificatecode) {
		this.certificatecode = certificatecode;
	}

	public java.util.Date getBirthdate() {
		return birthdate;
	}

	public void setBirthdate(java.util.Date birthdate) {
		this.birthdate = birthdate;
	}

	public java.util.Date getRegisterdate() {
		return registerdate;
	}

	public void setRegisterdate(java.util.Date registerdate) {
		this.registerdate = registerdate;
	}

	public java.lang.String getPlatformid() {
		return platformid;
	}

	public void setPlatformid(java.lang.String platformid) {
		this.platformid = platformid;
	}

	public java.lang.String getPlatformname() {
		return platformname;
	}

	public void setPlatformname(java.lang.String platformname) {
		this.platformname = platformname;
	}

	public java.lang.String getLevelid() {
		return levelid;
	}

	public void setLevelid(java.lang.String levelid) {
		this.levelid = levelid;
	}

	public java.lang.String getLevelname() {
		return levelname;
	}

	public void setLevelname(java.lang.String levelname) {
		this.levelname = levelname;
	}

	public java.lang.String getImageurl() {
		return imageurl;
	}

	public void setImageurl(java.lang.String imageurl) {
		this.imageurl = imageurl;
	}

	public java.lang.String getStatusid() {
		return statusid;
	}

	public void setStatusid(java.lang.String statusid) {
		this.statusid = statusid;
	}

	public java.lang.String getStatusname() {
		return statusname;
	}

	public void setStatusname(java.lang.String statusname) {
		this.statusname = statusname;
	}

	public java.lang.String getRemark() {
		return remark;
	}

	public void setRemark(java.lang.String remark) {
		this.remark = remark;
	}

	public java.lang.String getCreatorid() {
		return creatorid;
	}

	public void setCreatorid(java.lang.String creatorid) {
		this.creatorid = creatorid;
	}

	public java.lang.String getCreator() {
		return creator;
	}

	public void setCreator(java.lang.String creator) {
		this.creator = creator;
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

	public java.lang.String getModifier() {
		return modifier;
	}

	public void setModifier(java.lang.String modifier) {
		this.modifier = modifier;
	}

	public java.util.Date getModifytime() {
		return modifytime;
	}

	public void setModifytime(java.util.Date modifytime) {
		this.modifytime = modifytime;
	}

	/**
	 * 属性 idSysUserJob的Getter方法.属性名：idSysUserJob
	 *  创建日期:2016-11-17
	 * @return java.util.List<com.yonyou.iuap.example.entity.SysUserJob>
	 */
	public java.util.List<com.yonyou.iuap.example.entity.meta.SysUserJob> getIdSysUserJob () {
		return idSysUserJob;
	}
	   
	/**
	 * 属性idSysUserJob的Setter方法.属性名：idSysUserJob
	 * 创建日期:2016-11-17
	 * @param newIdSysUserJob java.util.List<com.yonyou.iuap.example.entity.SysUserJob>
	 */
	public void setIdSysUserJob (java.util.List<com.yonyou.iuap.example.entity.meta.SysUserJob> newIdSysUserJob ) {
	 	this.idSysUserJob = newIdSysUserJob;
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
        return "sysUser";
    }

    @Override
    public String getNamespace() {
        return "metadata";
    }
}