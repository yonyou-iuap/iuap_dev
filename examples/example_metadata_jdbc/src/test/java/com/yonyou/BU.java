package com.yonyou;

import com.yonyou.iuap.persistence.jdbc.framework.annotation.Column;
import com.yonyou.iuap.persistence.jdbc.framework.annotation.Entity;
import com.yonyou.iuap.persistence.jdbc.framework.annotation.FK;
import com.yonyou.iuap.persistence.jdbc.framework.annotation.GeneratedValue;
import com.yonyou.iuap.persistence.jdbc.framework.annotation.Id;
import com.yonyou.iuap.persistence.jdbc.framework.annotation.Stragegy;
import com.yonyou.iuap.persistence.jdbc.framework.annotation.Table;
import com.yonyou.iuap.persistence.vo.BaseEntity;


/**
 * <b> 此处简要描述此类功能 </b>
 * <p>
 * 此处添加类的描述信息
 * </p>
 * 创建日期:2016-3-31
 * 
 * @author
 * @version NCPrj
 */
@Entity(name = "BU", namespace = "com.iuap.org")
@Table(name = "bu")
public class BU extends BaseEntity {

    @FK(name = "fk_b_oid", referenceTableName = "org", referencedColumnName = "o_id")
    @Column(name = "fk_b_oid")
    private java.lang.String fk_b_oid;

    @Id
    @GeneratedValue(strategy = Stragegy.UUID, moudle = "b_id")
    @Column(name = "b_id")
    private java.lang.String b_id;

    @Column(name = "b_name")
    private java.lang.String b_name;

    @Column(name = "b_code")
    private java.lang.String b_code;

    @Column(name = "dr")
    private java.lang.Integer dr = 0;

    @Column(name = "ts")
    private java.util.Date ts;

    @Column(name = "id_employee")
    private java.lang.String id_employee;


    public String getId_employee() {
        return id_employee;
    }

    public void setId_employee(String id_employee) {
        this.id_employee = id_employee;
    }

    /**
     * 属性 fk_b_oid的Getter方法.属性名：parentPK 创建日期:2016-3-31
     * 
     * @return java.lang.String
     */
    public java.lang.String getFk_b_oid() {
        return fk_b_oid;
    }

    /**
     * 属性fk_b_oid的Setter方法.属性名：parentPK 创建日期:2016-3-31
     * 
     * @param newFk_b_oid java.lang.String
     */
    public void setFk_b_oid(java.lang.String newFk_b_oid) {
        this.fk_b_oid = newFk_b_oid;
    }

    /**
     * 属性 b_id的Getter方法.属性名：b_id 创建日期:2016-3-31
     * 
     * @return java.lang.String
     */
    public java.lang.String getB_id() {
        return b_id;
    }

    /**
     * 属性b_id的Setter方法.属性名：b_id 创建日期:2016-3-31
     * 
     * @param newB_id java.lang.String
     */
    public void setB_id(java.lang.String newB_id) {
        this.b_id = newB_id;
    }

    /**
     * 属性 b_name的Getter方法.属性名：b_name 创建日期:2016-3-31
     * 
     * @return java.lang.String
     */
    public java.lang.String getB_name() {
        return b_name;
    }

    /**
     * 属性b_name的Setter方法.属性名：b_name 创建日期:2016-3-31
     * 
     * @param newB_name java.lang.String
     */
    public void setB_name(java.lang.String newB_name) {
        this.b_name = newB_name;
    }

    /**
     * 属性 b_code的Getter方法.属性名：b_code 创建日期:2016-3-31
     * 
     * @return java.lang.String
     */
    public java.lang.String getB_code() {
        return b_code;
    }

    /**
     * 属性b_code的Setter方法.属性名：b_code 创建日期:2016-3-31
     * 
     * @param newB_code java.lang.String
     */
    public void setB_code(java.lang.String newB_code) {
        this.b_code = newB_code;
    }

    /**
     * 属性 dr的Getter方法.属性名：dr 创建日期:2016-3-31
     * 
     * @return java.lang.Integer
     */
    public java.lang.Integer getDr() {
        return dr;
    }

    /**
     * 属性dr的Setter方法.属性名：dr 创建日期:2016-3-31
     * 
     * @param newDr java.lang.Integer
     */
    public void setDr(java.lang.Integer newDr) {
        this.dr = newDr;
    }

    /**
     * 属性 ts的Getter方法.属性名：ts 创建日期:2016-3-31
     * 
     * @return java.util.Date
     */
    public java.util.Date getTs() {
        return ts;
    }

    /**
     * 属性ts的Setter方法.属性名：ts 创建日期:2016-3-31
     * 
     * @param newTs java.util.Date
     */
    public void setTs(java.util.Date newTs) {
        this.ts = newTs;
    }

    @Override
    public String getMetaDefinedName() {
        return "BU";
    }

    @Override
    public String getNamespace() {
        return "com.iuap.org";
    }
}
