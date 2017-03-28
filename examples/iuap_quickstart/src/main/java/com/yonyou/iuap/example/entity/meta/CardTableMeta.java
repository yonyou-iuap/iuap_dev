package com.yonyou.iuap.example.entity.meta;

import com.yonyou.iuap.persistence.jdbc.framework.annotation.Column;
import com.yonyou.iuap.persistence.jdbc.framework.annotation.Entity;
import com.yonyou.iuap.persistence.jdbc.framework.annotation.GeneratedValue;
import com.yonyou.iuap.persistence.jdbc.framework.annotation.Id;
import com.yonyou.iuap.persistence.jdbc.framework.annotation.Stragegy;
import com.yonyou.iuap.persistence.jdbc.framework.annotation.Table;
import com.yonyou.iuap.persistence.vo.BaseEntity;

import java.util.Date;


/**
 * <b> 此处简要描述此类功能 </b>
 * <p>
 * 此处添加类的描述信息
 * </p>
 * 创建日期:2016-12-13
 * 
 * @author
 * @version
 */
@Entity(namespace = "metadata", name = "cardTableMeta")
@Table(name = "cardTableMeta")
public class CardTableMeta extends BaseEntity {

    @Column(name = "name")
    private java.lang.String name;

    @Column(name = "code")
    private java.lang.String code;

    @Id
    @GeneratedValue(strategy = Stragegy.UUID, moudle = "")
    @Column(name = "pk_system")
    private java.lang.String pksystem;

    @Column(name = "gateway")
    private java.lang.String gateway;

    @Column(name = "radiodatacontroller")
    private java.lang.String radiodatacontroller;

    @Column(name = "radiodataassociate")
    private java.lang.String radiodataassociate;

    @Column(name = "combodatamodel")
    private java.lang.String combodatamodel;

    @Column(name = "tag")
    private java.lang.String tag;

    @Column(name = "createtime")
    private java.util.Date createtime;

    @Column(name = "dr")
    private java.lang.Integer dr = 0;

    @Column(name = "ts")
    private java.util.Date ts;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getPksystem() {
        return pksystem;
    }

    public void setPksystem(String pksystem) {
        this.pksystem = pksystem;
    }

    public String getGateway() {
        return gateway;
    }

    public void setGateway(String gateway) {
        this.gateway = gateway;
    }

    public String getRadiodatacontroller() {
        return radiodatacontroller;
    }

    public void setRadiodatacontroller(String radiodatacontroller) {
        this.radiodatacontroller = radiodatacontroller;
    }

    public String getRadiodataassociate() {
        return radiodataassociate;
    }

    public void setRadiodataassociate(String radiodataassociate) {
        this.radiodataassociate = radiodataassociate;
    }

    public String getCombodatamodel() {
        return combodatamodel;
    }

    public void setCombodatamodel(String combodatamodel) {
        this.combodatamodel = combodatamodel;
    }

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    public Date getCreatetime() {
        return createtime;
    }

    public void setCreatetime(Date createtime) {
        this.createtime = createtime;
    }

    public Integer getDr() {
        return dr;
    }

    public void setDr(Integer dr) {
        this.dr = dr;
    }

    public Date getTs() {
        return ts;
    }

    public void setTs(Date ts) {
        this.ts = ts;
    }

    @Override
    public String getMetaDefinedName() {
        return "cardTableMeta";
    }

    @Override
    public String getNamespace() {
        return "metadata";
    }
}
