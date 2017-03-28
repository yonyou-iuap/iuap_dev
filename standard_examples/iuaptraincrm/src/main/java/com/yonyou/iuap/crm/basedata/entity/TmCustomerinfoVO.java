package com.yonyou.iuap.crm.basedata.entity;

import java.sql.Timestamp;
import java.util.List;

import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.yonyou.iuap.crm.common.validator.NotUpdateValid;
import com.yonyou.iuap.persistence.jdbc.framework.annotation.Column;
import com.yonyou.iuap.persistence.jdbc.framework.annotation.Entity;
import com.yonyou.iuap.persistence.jdbc.framework.annotation.GeneratedValue;
import com.yonyou.iuap.persistence.jdbc.framework.annotation.Id;
import com.yonyou.iuap.persistence.jdbc.framework.annotation.Stragegy;
import com.yonyou.iuap.persistence.jdbc.framework.annotation.Table;
import com.yonyou.iuap.persistence.vo.BaseEntity;


/**
 * The persistent class for the tm_customerinfo database table.
 * 
 */
@Entity(namespace = "iuaptraincrm",name = "customerinfo")
@Table(name="tm_customerinfo")
@JsonIgnoreProperties(ignoreUnknown = true)
@NotUpdateValid(fields={"vcustomerno"},  message="{客户编码不可被修改}")
public class TmCustomerinfoVO extends BaseEntity {
	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy=Stragegy.UAPOID,moudle="tm_customerinfo")
	@Column(name = "pk_customerinfo")
	private String pk_customerinfo;           //  客户信息主键
	@Column(name = "vcustomerno", updatable=false)
	@NotNull(message = "{客户编码不可为空}")     
	private String vcustomerno;   			  //  客户信息编码 
	@Column(name = "vcustomername")
	@NotNull(message = "{客户名称不可为空}")
	private String vcustomername;			  //  客户名称
	@Column(name = "pk_province")
	@NotNull(message = "{省份不可为空}")
	private String pk_province;				  //  省份pk
	@Column(name = "pk_city")
	@NotNull(message = "{城市不可为空}")
	private String pk_city;					  //  城市pk  
	@Column(name = "vaddress")	
	private String vaddress; 				 //  客户地址  
	@Column(name = "vzipcode")
	private String vzipcode;				 //   邮编
	@Column(name = "vtaxnumber")
	private String vtaxnumber;				 //   纳税人识别号
	@Column(name = "vbizlicense")
	private String vbizlicense;				 //   营业执照地址	
	@Column(name = "vbizphone")			
	private String vbizphone;				 //  营业执照固定电话
	@Column(name = "vaccountbank")	
	private String vaccountbank;			 //  开户银行
	@Column(name = "vaccountno")
	private String vaccountno;				 //  开户行账号
	@Column(name = "vcusorgcode")
	private String vcusorgcode;				 //  组织机构代码
	@Column(name = "vcompanysize")
	private String vcompanysize;			 //  企业规模
	@Column(name = "vbuyinterest")
	private String vbuyinterest;			 //  购买意向强烈程度
	@Column(name = "vsubpower")
	private String vsubpower;				 //  当地政策补贴力度
	@Column(name = "creator")
	private String creator;
	@Column(name = "creationtime")
	private String creationtime;
	@Column(name = "modifier")
	private String modifier;
	@Column(name = "modifiedtime")
	private String modifiedtime;
	@Column(name = "ts")
	private Timestamp ts;
	@Column(name = "dr")
	private int dr;
	@Column(name = "vstatus")
	private String vstatus;
	@Column(name = "pk_trackinfo")			// 线索维护pk
	private String pk_trackinfo;

	public String getPk_trackinfo() {
		return pk_trackinfo;
	}

	public void setPk_trackinfo(String pk_trackinfo) {
		this.pk_trackinfo = pk_trackinfo;
	}

	private List<TmCustomercontactorVO> items;
	
	public TmCustomerinfoVO() {
	}

	public List<TmCustomercontactorVO> getItems() {
		return items;
	}


	public void setItems(List<TmCustomercontactorVO> items) {
		this.items = items;
	}



	public String getPk_customerinfo() {
		return this.pk_customerinfo;
	}

	public void setPk_customerinfo(String pk_customerinfo) {
		this.pk_customerinfo = pk_customerinfo;
	}


	public String getVcustomerno() {
		return this.vcustomerno;
	}

	public void setVcustomerno(String vcustomerno) {
		this.vcustomerno = vcustomerno;
	}


	public String getVcustomername() {
		return this.vcustomername;
	}

	public void setVcustomername(String vcustomername) {
		this.vcustomername = vcustomername;
	}


	public String getPk_province() {
		return this.pk_province;
	}

	public void setPk_province(String pk_province) {
		this.pk_province = pk_province;
	}


	public String getPk_city() {
		return this.pk_city;
	}

	public void setPk_city(String pk_city) {
		this.pk_city = pk_city;
	}


	public String getVaddress() {
		return this.vaddress;
	}

	public void setVaddress(String vaddress) {
		this.vaddress = vaddress;
	}


	public String getVzipcode() {
		return this.vzipcode;
	}

	public void setVzipcode(String vzipcode) {
		this.vzipcode = vzipcode;
	}


	public String getVtaxnumber() {
		return this.vtaxnumber;
	}

	public void setVtaxnumber(String vtaxnumber) {
		this.vtaxnumber = vtaxnumber;
	}


	public String getVbizlicense() {
		return this.vbizlicense;
	}

	public void setVbizlicense(String vbizlicense) {
		this.vbizlicense = vbizlicense;
	}


	public String getVbizphone() {
		return this.vbizphone;
	}

	public void setVbizphone(String vbizphone) {
		this.vbizphone = vbizphone;
	}


	public String getVaccountbank() {
		return this.vaccountbank;
	}

	public void setVaccountbank(String vaccountbank) {
		this.vaccountbank = vaccountbank;
	}


	public String getVaccountno() {
		return this.vaccountno;
	}

	public void setVaccountno(String vaccountno) {
		this.vaccountno = vaccountno;
	}


	public String getVcusorgcode() {
		return this.vcusorgcode;
	}

	public void setVcusorgcode(String vcusorgcode) {
		this.vcusorgcode = vcusorgcode;
	}


	public String getVcompanysize() {
		return vcompanysize;
	}


	public void setVcompanysize(String vcompanysize) {
		this.vcompanysize = vcompanysize;
	}


	public String getVbuyinterest() {
		return vbuyinterest;
	}


	public void setVbuyinterest(String vbuyinterest) {
		this.vbuyinterest = vbuyinterest;
	}


	public String getVsubpower() {
		return vsubpower;
	}


	public void setVsubpower(String vsubpower) {
		this.vsubpower = vsubpower;
	}


	public String getCreator() {
		return this.creator;
	}

	public void setCreator(String creator) {
		this.creator = creator;
	}

	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+08:00")
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

	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+08:00")
	public String getModifiedtime() {
		return this.modifiedtime;
	}

	public void setModifiedtime(String modifiedtime) {
		this.modifiedtime = modifiedtime;
	}

	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+08:00")
	public Timestamp getTs() {
		return this.ts;
	}

	public void setTs(Timestamp ts) {
		this.ts = ts;
	}


	public int getDr() {
		return this.dr;
	}

	public void setDr(int dr) {
		this.dr = dr;
	}


	public String getVstatus() {
		return this.vstatus;
	}

	public void setVstatus(String vstatus) {
		this.vstatus = vstatus;
	}

	@Override
    public String getMetaDefinedName() {
        return "customerinfo";
    }

    @Override
    public String getNamespace() {
		return "iuaptraincrm";
    }
}