package com.yonyou.iuap.crm.currtype.entity;



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
@Entity(namespace = "train",name = "currtype")
@Table(name="train_currtype")
public class Currtype extends BaseEntity {
	  
	@Id
	@GeneratedValue(strategy=Stragegy.UUID,moudle="")
	@Column(name = "pk_currtype")
	private String pk_currtype;

	@Column(name = "code")
	private String code;

	@Column(name = "name")
	private String name;

	@Column(name = "currdigit")
	private String currdigit;

	@Column(name = "creator")
	private String creator;

	@Column(name = "creationtime")
	private String creationtime;

	@Column(name = "modifier")
	private String modifier;

	@Column(name = "modifiedtime")
	private String modifiedtime;


	@Column(name = "dr")
    private java.lang.Integer dr = 0 ;
      
    @Column(name = "ts")
    private java.sql.Timestamp ts ;    	

	public String getPk_currtype() {
		return this.pk_currtype;
	}

	public void setPk_currtype(String pk_currtype) {
		this.pk_currtype = pk_currtype;
	}
	

	public String getCode() {
		return this.code;
	}

	public void setCode(String code) {
		this.code = code;
	}
	

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}
	

	public String getCurrdigit() {
		return this.currdigit;
	}

	public void setCurrdigit(String currdigit) {
		this.currdigit = currdigit;
	}
	

	public String getCreator() {
		return this.creator;
	}

	public void setCreator(String creator) {
		this.creator = creator;
	}
	

	public String getCreationtime() {
		return this.creationtime;
	}

	public void setCreationtime(String creationtime) {
		this.creationtime = creationtime;
	}
	

	public String getModifier() {
		return this.modifier;
	}

	public void setModifier(String modifier) {
		this.modifier = modifier;
	}
	

	public String getModifiedtime() {
		return this.modifiedtime;
	}

	public void setModifiedtime(String modifiedtime) {
		this.modifiedtime = modifiedtime;
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
	        return "currtype";
	    }

	    @Override
	    public String getNamespace() {
	        return "train";
	    }
}