package com.yonyou.iuap.entity.uapjdbc;


import java.sql.Timestamp;
import java.util.Date;

import com.yonyou.iuap.persistence.jdbc.framework.annotation.Column;
import com.yonyou.iuap.persistence.jdbc.framework.annotation.Entity;
import com.yonyou.iuap.persistence.jdbc.framework.annotation.Id;
import com.yonyou.iuap.persistence.jdbc.framework.annotation.Table;
import com.yonyou.iuap.persistence.vo.BaseEntity;

/**
 * <b> 此处简要描述此类功能 </b>
 * <p>
 * 此处添加类的描述信息
 * </p>
 * 创建日期:2015-12-17
 *
 * @author YONYOU NC
 * @version NCPrj ??
 */
@Entity(name = "nc.vo.uap.ipuquotation.IpuQuotation")
@Table(name = "ipuquotation")
public class UapIpuQuotation extends BaseEntity {

    @Id
    @Column(name = "ipuquotaionid")
    private String ipuquotaionid;

    @Column(name = "buyoffertime")
    private Date buyoffertime;

    @Column(name = "contact")
    private String contact;

    @Column(name = "createtime")
    private Date createtime;

    @Column(name = "description")
    private String description;

    @Column(name = "phone")
    private String phone;

    @Column(name = "processtime")
    private Date processtime;

    @Column(name = "processor")
    private String processor;

    @Column(name = "qtexpiredate")
    private Date qtexpiredate;

    @Column(name = "subject")
    private String subject;

    @Column(name = "ts")
    private Timestamp ts;

    @Column(name = "dr")
    private Short dr;


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

    public Timestamp getTs() {
        return ts;
    }

    public void setTs(Timestamp ts) {
        this.ts = ts;
    }

    public Short getDr() {
        return dr;
    }

    public void setDr(Short dr) {
        this.dr = dr;
    }



}
