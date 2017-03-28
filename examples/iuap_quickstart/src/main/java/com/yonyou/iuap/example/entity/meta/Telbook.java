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
 *  创建日期:2016-12-13
 * @author 
 * @version 
 */
@JsonIgnoreProperties(ignoreUnknown = true)
@Entity(namespace = "metadata",name = "telbook")
@Table(name = "telbook")
public class Telbook extends BaseEntity{
	 
    @FK(name = "institid", referenceTableName = "instit", referencedColumnName = "institid") 
    @Column(name = "institid")
    private java.lang.String institid;
    
    @Id
    @GeneratedValue(strategy = Stragegy.UUID, moudle = "")  
    @Column(name = "id")
    private java.lang.String id;
      
    @Column(name = "peoname")
    private java.lang.String peoname;
      
    @Column(name = "peocode")
    private java.lang.String peocode;
      
    
    private java.lang.String institname;
      
      
    @Column(name = "worktel")
    private java.lang.String worktel;
      
    @Column(name = "email")
    private java.lang.String email;
      
    @Column(name = "sex")
    private java.lang.String sex;
      
    @Column(name = "tel")
    private java.lang.String tel;
      
    @Column(name = "office")
    private java.lang.String office;
      
    @Column(name = "countryzone")
    private java.lang.String countryzone;
      
    @Column(name = "dr")
    private java.lang.Integer dr = 0;
      
    @Column(name = "ts")
    private java.util.Date ts;
        
	
    
    public java.lang.String getInstitid() {
		return institid;
	}

	public void setInstitid(java.lang.String institid) {
		this.institid = institid;
	}

	public java.lang.String getId() {
		return id;
	}

	public void setId(java.lang.String id) {
		this.id = id;
	}

	public java.lang.String getPeoname() {
		return peoname;
	}

	public void setPeoname(java.lang.String peoname) {
		this.peoname = peoname;
	}

	public java.lang.String getPeocode() {
		return peocode;
	}

	public void setPeocode(java.lang.String peocode) {
		this.peocode = peocode;
	}

	public java.lang.String getInstitname() {
		return institname;
	}

	public void setInstitname(java.lang.String institname) {
		this.institname = institname;
	}

	public java.lang.String getWorktel() {
		return worktel;
	}

	public void setWorktel(java.lang.String worktel) {
		this.worktel = worktel;
	}

	public java.lang.String getEmail() {
		return email;
	}

	public void setEmail(java.lang.String email) {
		this.email = email;
	}

	public java.lang.String getSex() {
		return sex;
	}

	public void setSex(java.lang.String sex) {
		this.sex = sex;
	}

	public java.lang.String getTel() {
		return tel;
	}

	public void setTel(java.lang.String tel) {
		this.tel = tel;
	}

	public java.lang.String getOffice() {
		return office;
	}

	public void setOffice(java.lang.String office) {
		this.office = office;
	}

	public java.lang.String getCountryzone() {
		return countryzone;
	}

	public void setCountryzone(java.lang.String countryzone) {
		this.countryzone = countryzone;
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

	@Override
    public String getMetaDefinedName() {
        return "telbook";
    }

    @Override
    public String getNamespace() {
        return "metadata";
    }
}