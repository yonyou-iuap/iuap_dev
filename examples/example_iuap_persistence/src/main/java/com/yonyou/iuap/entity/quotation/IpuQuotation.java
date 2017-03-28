package com.yonyou.iuap.entity.quotation;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

public class IpuQuotation implements Serializable {

	private static final long serialVersionUID = -9169987729255268660L;

	private String ipuquotaionid;

    private Date buyoffertime;

    private String contact;

    private Date createtime;

    private String description;

    private String phone;

    private Date processtime;

    private String processor;

    private Date qtexpiredate;

    private String subject;

    private Date ts;

    private Short dr;

    private List<IpuQuotationDetail> datailentitylist;

    public String getIpuquotaionid() {
        return ipuquotaionid;
    }

    public void setIpuquotaionid(String ipuquotaionid) {
        this.ipuquotaionid = ipuquotaionid == null ? null : ipuquotaionid.trim();
    }


    public Date getBuyoffertime() {
        return buyoffertime;
    }

    public void setBuyoffertime(Date buyoffertime) {
        this.buyoffertime = buyoffertime;
    }


    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact == null ? null : contact.trim();
    }

    public Date getCreatetime() {
        return createtime;
    }

    public void setCreatetime(Date createtime) {
        this.createtime = createtime;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description == null ? null : description.trim();
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone == null ? null : phone.trim();
    }

    public Date getProcesstime() {
        return processtime;
    }

    public void setProcesstime(Date processtime) {
        this.processtime = processtime;
    }

    public String getProcessor() {
        return processor;
    }

    public void setProcessor(String processor) {
        this.processor = processor == null ? null : processor.trim();
    }

    public Date getQtexpiredate() {
        return qtexpiredate;
    }

    public void setQtexpiredate(Date qtexpiredate) {
        this.qtexpiredate = qtexpiredate;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject == null ? null : subject.trim();
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


    public List<IpuQuotationDetail> getDatailentitylist() {
        return datailentitylist;
    }

    public void setDatailentitylist(List<IpuQuotationDetail> datailentitylist) {
        this.datailentitylist = datailentitylist;
    }

    @Override
    public String toString() {
        return "IpuQuotation{" +
                "ipuquotaionid='" + ipuquotaionid + '\'' +
                ", buyoffertime=" + buyoffertime +
                ", contact='" + contact + '\'' +
                ", createtime=" + createtime +
                ", description='" + description + '\'' +
                ", phone='" + phone + '\'' +
                ", processtime=" + processtime +
                ", processor='" + processor + '\'' +
                ", qtexpiredate=" + qtexpiredate +
                ", subject='" + subject + '\'' +
                ", ts=" + ts +
                ", dr=" + dr +
                ", datailentitylist=" + datailentitylist +
                '}';
    }
}
