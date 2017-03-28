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
@Entity(namespace = "metadata", name = "sysDictTypeMeta")
@Table(name = "sys_dict_type_meta")
public class SysDictTypeMeta extends BaseEntity {

    @Id
    @GeneratedValue(strategy = Stragegy.UUID, moudle = "")
    @Column(name = "dict_id")
    private java.lang.String dictid;

    @Column(name = "dict_type_code")
    private java.lang.String dicttypecode;

    @Column(name = "dict_type_name")
    private java.lang.String dicttypename;

    @Column(name = "is_fixed")
    private java.lang.String isfixed;

    @Column(name = "remark")
    private java.lang.String remark;

    @Column(name = "creator_id")
    private java.lang.String creatorid;

    @Column(name = "creator")
    private java.lang.String creator;

    @Column(name = "create_time")
    private java.util.Date createtime;

    @Column(name = "modifier_id")
    private java.lang.String modifierid;

    @Column(name = "modifier")
    private java.lang.String modifier;

    @Column(name = "modify_time")
    private java.util.Date modifytime;

    @Column(name = "dr")
    private java.lang.Integer dr = 0;

    @Column(name = "ts")
    private java.util.Date ts;

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
        return "sysDictTypeMeta";
    }

    @Override
    public String getNamespace() {
        return "metadata";
    }
}
