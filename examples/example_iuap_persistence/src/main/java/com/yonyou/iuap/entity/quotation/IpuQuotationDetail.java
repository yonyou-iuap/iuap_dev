package com.yonyou.iuap.entity.quotation;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

public class IpuQuotationDetail implements Serializable {

	private static final long serialVersionUID = -1703801236486281083L;

	private String ipuquotationdetailid;

    private String productdesc;

    private String productname;

    private BigDecimal purchaseamount;

    private String unit;

    private String ipuquotaionid;

    private Date ts;

    private Short dr;

    public String getIpuquotationdetailid() {
        return ipuquotationdetailid;
    }

    public void setIpuquotationdetailid(String ipuquotationdetailid) {
        this.ipuquotationdetailid = ipuquotationdetailid == null ? null : ipuquotationdetailid.trim();
    }

    public String getProductdesc() {
        return productdesc;
    }

    public void setProductdesc(String productdesc) {
        this.productdesc = productdesc == null ? null : productdesc.trim();
    }

    public String getProductname() {
        return productname;
    }

    public void setProductname(String productname) {
        this.productname = productname == null ? null : productname.trim();
    }

    public BigDecimal getPurchaseamount() {
        return purchaseamount;
    }

    public void setPurchaseamount(BigDecimal purchaseamount) {
        this.purchaseamount = purchaseamount;
    }


    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit == null ? null : unit.trim();
    }

    public String getIpuquotaionid() {
        return ipuquotaionid;
    }

    public void setIpuquotaionid(String ipuquotaionid) {
        this.ipuquotaionid = ipuquotaionid == null ? null : ipuquotaionid.trim();
    }

    public Date getTs() {
        return ts;
    }

    public void setTs(Date ts) {
        this.ts = ts;
    }

    public Short getDr() {
        return dr;
    }

    public void setDr(Short dr) {
        this.dr = dr;
    }
}