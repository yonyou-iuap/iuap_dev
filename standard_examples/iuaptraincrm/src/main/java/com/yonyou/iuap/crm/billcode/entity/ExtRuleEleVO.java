package com.yonyou.iuap.crm.billcode.entity;

import java.util.Date;

import com.yonyou.iuap.persistence.jdbc.framework.annotation.Column;
import com.yonyou.iuap.persistence.jdbc.framework.annotation.Entity;
import com.yonyou.iuap.persistence.jdbc.framework.annotation.GeneratedValue;
import com.yonyou.iuap.persistence.jdbc.framework.annotation.Id;
import com.yonyou.iuap.persistence.jdbc.framework.annotation.Stragegy;
import com.yonyou.iuap.persistence.jdbc.framework.annotation.Table;
import com.yonyou.iuap.persistence.vo.BaseEntity;
import com.yonyou.uap.billcode.model.IBillCodeElemVO;
	

/**
 * The persistent class for the pub_bcr_elem database table.
 * 
 */
@Entity
@Table(name="pub_bcr_elem")
public class ExtRuleEleVO extends BaseEntity implements IBillCodeElemVO{
	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy=Stragegy.UUID,moudle="pub_bcr_elem")
	@Column(name = "pk_billcodeelem")
	private String pk_billcodeelem;
	@Column(name = "pk_billcodebase")
	private String pk_billcodebase;
	@Column(name = "elemvalue")
	private String elemvalue;
	@Column(name = "datedisplayformat")
	private String datedisplayformat;
	@Column(name = "fillsign")
	private String fillsign;
	@Column(name = "elemtype")
	private int elemtype;
	@Column(name = "elemlenth")
	private int elemlenth;
	@Column(name = "isrefer")
	private int isrefer;
	@Column(name = "eorder")
	private int eorder;
	@Column(name = "fillstyle")
	private int fillstyle;
	
	public int getElemtype() {
		return elemtype;
	}


	public void setElemtype(int elemtype) {
		this.elemtype = elemtype;
	}


	public int getElemlenth() {
		return elemlenth;
	}


	public void setElemlenth(int elemlenth) {
		this.elemlenth = elemlenth;
	}


	public int getIsrefer() {
		return isrefer;
	}


	public void setIsrefer(int isrefer) {
		this.isrefer = isrefer;
	}


	public int getEorder() {
		return eorder;
	}


	public void setEorder(int eorder) {
		this.eorder = eorder;
	}


	public int getFillstyle() {
		return fillstyle;
	}


	public void setFillstyle(int fillstyle) {
		this.fillstyle = fillstyle;
	}


	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	@Column(name = "createdate")
	private Date createdate;

	public ExtRuleEleVO() {
	}


	public String getPk_billcodeelem() {
		return this.pk_billcodeelem;
	}

	public void setPk_billcodeelem(String pk_billcodeelem) {
		this.pk_billcodeelem = pk_billcodeelem;
	}


	public String getPk_billcodebase() {
		return this.pk_billcodebase;
	}

	public void setPk_billcodebase(String pk_billcodebase) {
		this.pk_billcodebase = pk_billcodebase;
	}


	public String getElemvalue() {
		return this.elemvalue;
	}

	public void setElemvalue(String elemvalue) {
		this.elemvalue = elemvalue;
	}


	public String getDatedisplayformat() {
		return this.datedisplayformat;
	}

	public void setDatedisplayformat(String datedisplayformat) {
		this.datedisplayformat = datedisplayformat;
	}


	public String getFillsign() {
		return this.fillsign;
	}

	public void setFillsign(String fillsign) {
		this.fillsign = fillsign;
	}


	public Date getCreatedate() {
		return this.createdate;
	}

	public void setCreatedate(Date createdate) {
		this.createdate = createdate;
	}

	@Override
    public String getMetaDefinedName() {
        return "pub_bcr_elem";
    }

    @Override
    public String getNamespace() {
		return "com.yonyou.iuap.crm.billcode.entity";
    }


	@Override
	public String getStrPk_billcodebase() {
		// TODO 自动生成的方法存根
		return getPk_billcodebase();
	}


	@Override
	public String getStrPk_billcodeelem() {
		// TODO 自动生成的方法存根
		return getPk_billcodeelem();
	}


	public int getIntElemtype() {
		/* 148 */     return getElemtype();
		/*     */   }
		/*     */   
		/*     */   public String getStrElemvalue() {
		/* 152 */     return getElemvalue();
		/*     */   }
		/*     */   
		/*     */   public int getIntEorder() {
		/* 156 */     return getEorder();
		/*     */   }
		/*     */   
		/*     */   public int getIntElemlenth() {
		/* 160 */     return getElemlenth();
		/*     */   }
		/*     */   
		/*     */   public int getIntIsrefer() {
		/* 164 */     return getIsrefer();
		/*     */   }
		/*     */   
		/*     */   public int getIntFillstyle() {
		/* 168 */     return getFillstyle();
		/*     */   }
		/*     */   
		/*     */   public String getStrFillsign() {
		/* 172 */     return getFillsign();
		/*     */   }
		/*     */   
		/*     */   public String getStrDateElemDisplayFormat() {
		/* 176 */     return getDatedisplayformat();
		/*     */   }
}