package com.yonyou.iuap.example.entity.mybatis;

import java.util.Date;

public class SysDictType {
    private String dictid;

    private String dicttypecode;

    private String dicttypename;

    private String isfixed = "N";

    private String remark;

    private String creatorid;

    private String creator;

    private Date createtime;

    private String modifierid;

    private String modifier;

    private Date modifytime;

    private String dr = "0";

    public String getDictid() {
        return dictid;
    }

    public void setDictid(String dictid) {
        this.dictid = dictid;
    }

    public String getDicttypecode() {
        return dicttypecode;
    }

    public void setDicttypecode(String dicttypecode) {
        this.dicttypecode = dicttypecode;
    }

    public String getDicttypename() {
        return dicttypename;
    }

    public void setDicttypename(String dicttypename) {
        this.dicttypename = dicttypename;
    }

    public String getIsfixed() {
        return isfixed;
    }

    public void setIsfixed(String isfixed) {
        this.isfixed = isfixed;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getCreatorid() {
        return creatorid;
    }

    public void setCreatorid(String creatorid) {
        this.creatorid = creatorid;
    }

    public String getCreator() {
        return creator;
    }

    public void setCreator(String creator) {
        this.creator = creator;
    }

    public Date getCreatetime() {
        return createtime;
    }

    public void setCreatetime(Date createtime) {
        this.createtime = createtime;
    }

    public String getModifierid() {
        return modifierid;
    }

    public void setModifierid(String modifierid) {
        this.modifierid = modifierid;
    }

    public String getModifier() {
        return modifier;
    }

    public void setModifier(String modifier) {
        this.modifier = modifier;
    }

    public Date getModifytime() {
        return modifytime;
    }

    public void setModifytime(Date modifytime) {
        this.modifytime = modifytime;
    }

    public String getDr() {
        return dr;
    }

    public void setDr(String dr) {
        this.dr = dr;
    }
}
