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
 * 创建日期:2016-4-2
 * 
 * @author
 * @version NCPrj
 */
@Entity(name = "Employee", namespace = "com.iuap.org")
@Table(name = "employee")
public class Employee extends BaseEntity {

    @FK(name = "fk_e_oid", referenceTableName = "org", referencedColumnName = "o_id")
    @Column(name = "fk_e_oid")
    private String fk_e_oid;

    @Id
    @GeneratedValue(strategy = Stragegy.UUID, moudle = "e_id")
    @Column(name = "e_id")
    private String e_id;

    @Column(name = "e_name")
    private String e_name;

    @Column(name = "e_code")
    private String e_code;

    @Column(name = "gender")
    private String gender;

    @Column(name = "dr")
    private Integer dr = 0;

    @Column(name = "ts")
    private java.util.Date ts;



    /**
     * 属性 fk_e_oid的Getter方法.属性名：parentPK 创建日期:2016-4-2
     * 
     * @return java.lang.String
     */
    public String getFk_e_oid() {
        return fk_e_oid;
    }

    /**
     * 属性fk_e_oid的Setter方法.属性名：parentPK 创建日期:2016-4-2
     * 
     * @param newFk_e_oid java.lang.String
     */
    public void setFk_e_oid(String newFk_e_oid) {
        this.fk_e_oid = newFk_e_oid;
    }

    /**
     * 属性 e_id的Getter方法.属性名：id 创建日期:2016-4-2
     * 
     * @return java.lang.String
     */
    public String getE_id() {
        return e_id;
    }

    /**
     * 属性e_id的Setter方法.属性名：id 创建日期:2016-4-2
     * 
     * @param newE_id java.lang.String
     */
    public void setE_id(String newE_id) {
        this.e_id = newE_id;
    }

    /**
     * 属性 e_name的Getter方法.属性名：e_name 创建日期:2016-4-2
     * 
     * @return java.lang.String
     */
    public String getE_name() {
        return e_name;
    }

    /**
     * 属性e_name的Setter方法.属性名：e_name 创建日期:2016-4-2
     * 
     * @param newE_name java.lang.String
     */
    public void setE_name(String newE_name) {
        this.e_name = newE_name;
    }

    /**
     * 属性 e_code的Getter方法.属性名：e_code 创建日期:2016-4-2
     * 
     * @return java.lang.String
     */
    public String getE_code() {
        return e_code;
    }

    /**
     * 属性e_code的Setter方法.属性名：e_code 创建日期:2016-4-2
     * 
     * @param newE_code java.lang.String
     */
    public void setE_code(String newE_code) {
        this.e_code = newE_code;
    }

    /**
     * 属性 gender的Getter方法.属性名：e_gender 创建日期:2016-4-2
     * 
     * @return java.lang.String
     */
    public String getGender() {
        return gender;
    }

    /**
     * 属性gender的Setter方法.属性名：e_gender 创建日期:2016-4-2
     * 
     * @param newGender java.lang.String
     */
    public void setGender(String newGender) {
        this.gender = newGender;
    }

    /**
     * 属性 dr的Getter方法.属性名：dr 创建日期:2016-4-2
     * 
     * @return java.lang.Integer
     */
    public Integer getDr() {
        return dr;
    }

    /**
     * 属性dr的Setter方法.属性名：dr 创建日期:2016-4-2
     * 
     * @param newDr java.lang.Integer
     */
    public void setDr(Integer newDr) {
        this.dr = newDr;
    }

    /**
     * 属性 ts的Getter方法.属性名：ts 创建日期:2016-4-2
     * 
     * @return java.util.Date
     */
    public java.util.Date getTs() {
        return ts;
    }

    /**
     * 属性ts的Setter方法.属性名：ts 创建日期:2016-4-2
     * 
     * @param newTs java.util.Date
     */
    public void setTs(java.util.Date newTs) {
        this.ts = newTs;
    }

    @Override
    public String getMetaDefinedName() {
        return "Employee";
    }

    @Override
    public String getNamespace() {
        return "com.iuap.org";
    }

}
