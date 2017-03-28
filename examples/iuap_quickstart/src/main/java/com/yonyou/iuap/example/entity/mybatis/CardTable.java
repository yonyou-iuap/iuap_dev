package com.yonyou.iuap.example.entity.mybatis;

import java.util.Date;

/**
 * 卡片表示例实体，mybatis方式
 */
public class CardTable {
    private String pksystem;

    private String name;

    private String code;

    private String i18n;

    private String gateway;

    private String radiodatacontroller;

    private String radiodataassociate;

    private String combodatamodel;

    private String tag;

    private String secretkey;

    private String system;

    private String tenant;

    private Date ts;

    private Integer dr;

    public String getPksystem() {
        return pksystem;
    }

    public void setPksystem(String pksystem) {
        this.pksystem = pksystem;
    }

    public void setTs(Date ts) {
        this.ts = ts;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code == null ? null : code.trim();
    }

    public String getI18n() {
        return i18n;
    }

    public void setI18n(String i18n) {
        this.i18n = i18n == null ? null : i18n.trim();
    }

    public String getGateway() {
        return gateway;
    }

    public void setGateway(String gateway) {
        this.gateway = gateway == null ? null : gateway.trim();
    }

    public String getRadiodatacontroller() {
        return radiodatacontroller;
    }

    public void setRadiodatacontroller(String radiodatacontroller) {
        this.radiodatacontroller = radiodatacontroller == null ? null : radiodatacontroller.trim();
    }

    public String getRadiodataassociate() {
        return radiodataassociate;
    }

    public void setRadiodataassociate(String radiodataassociate) {
        this.radiodataassociate = radiodataassociate == null ? null : radiodataassociate.trim();
    }

    public String getCombodatamodel() {
        return combodatamodel;
    }

    public void setCombodatamodel(String combodatamodel) {
        this.combodatamodel = combodatamodel == null ? null : combodatamodel.trim();
    }

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag == null ? null : tag.trim();
    }

    public String getSecretkey() {
        return secretkey;
    }

    public void setSecretkey(String secretkey) {
        this.secretkey = secretkey;
    }

    public String getSystem() {
        return system;
    }

    public void setSystem(String system) {
        this.system = system == null ? null : system.trim();
    }

    public String getTenant() {
        return tenant;
    }

    public void setTenant(String tenant) {
        this.tenant = tenant == null ? null : tenant.trim();
    }

    public Date getTs() {
        return ts;
    }

    public Integer getDr() {
        return dr;
    }

    public void setDr(Integer dr) {
        this.dr = dr;
    }
}
