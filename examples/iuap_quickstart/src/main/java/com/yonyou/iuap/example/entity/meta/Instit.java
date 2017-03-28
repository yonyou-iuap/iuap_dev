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
 *  创建日期:2016-12-13
 * @author 
 * @version 
 */
@JsonIgnoreProperties(ignoreUnknown = true)
@Entity(namespace = "metadata",name = "instit")
@Table(name = "instit")
public class Instit extends BaseEntity{
	
    @Id
    @GeneratedValue(strategy = Stragegy.UUID, moudle = "")  
    @Column(name = "institid")
    private java.lang.String institid;
      
    @Column(name = "instit_code")
    private java.lang.String institcode;
      
    @Column(name = "instit_name")
    private java.lang.String institname;
      
    @Column(name = "short_name")
    private java.lang.String shortname;
      
    @Column(name = "instit_type")
    private java.lang.String instittype;
      
    @Column(name = "email")
    private java.lang.String email;
      
    @Column(name = "parent_id")
    private java.lang.String parentid;
      
    @Column(name = "creator")
    private java.lang.String creator;
      
    @Column(name = "creationtime")
    private java.util.Date creationtime;
      
    @Column(name = "dr")
    private java.lang.Integer dr = 0;
      
    @Column(name = "ts")
    private java.util.Date ts;
        
	
    @OneToMany(targetEntity = com.yonyou.iuap.example.entity.meta.Telbook.class)
    private java.util.List<com.yonyou.iuap.example.entity.meta.Telbook> fkTelbook;

    
    
	public java.lang.String getInstitid() {
		return institid;
	}

	public void setInstitid(java.lang.String institid) {
		this.institid = institid;
	}

	public java.lang.String getInstitcode() {
		return institcode;
	}

	public void setInstitcode(java.lang.String institcode) {
		this.institcode = institcode;
	}

	public java.lang.String getInstitname() {
		return institname;
	}

	public void setInstitname(java.lang.String institname) {
		this.institname = institname;
	}

	public java.lang.String getShortname() {
		return shortname;
	}

	public void setShortname(java.lang.String shortname) {
		this.shortname = shortname;
	}

	public java.lang.String getInstittype() {
		return instittype;
	}

	public void setInstittype(java.lang.String instittype) {
		this.instittype = instittype;
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

	public java.lang.Integer getDr() {
		return dr;
	}

	public void setDr(java.lang.Integer dr) {
		this.dr = dr;
	}

	public java.util.Date getTs() {
		return ts;
	}

	public void setTs(java.util.Date ts) {
		this.ts = ts;
	}

	public java.util.List<com.yonyou.iuap.example.entity.meta.Telbook> getFkTelbook() {
		return fkTelbook;
	}

	public void setFkTelbook(
			java.util.List<com.yonyou.iuap.example.entity.meta.Telbook> fkTelbook) {
		this.fkTelbook = fkTelbook;
	}

	@Override
    public String getMetaDefinedName() {
        return "instit";
    }

    @Override
    public String getNamespace() {
        return "metadata";
    }
}