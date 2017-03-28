package com.yonyou;


import com.yonyou.iuap.persistence.jdbc.framework.annotation.Column;
import com.yonyou.iuap.persistence.jdbc.framework.annotation.Entity;
import com.yonyou.iuap.persistence.jdbc.framework.annotation.GeneratedValue;
import com.yonyou.iuap.persistence.jdbc.framework.annotation.Id;
import com.yonyou.iuap.persistence.jdbc.framework.annotation.OneToMany;
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
@Entity(namespace = "com.iuap.org", name = "Org")
@Table(name = "org")
public class Org extends BaseEntity {

    @Id
    @GeneratedValue(strategy = Stragegy.UUID, moudle = "o_id")
    @Column(name = "o_id")
    private String o_id;

    @Column(name = "o_name")
    private String o_name;

    @Column(name = "o_code")
    private String o_code;

    @Column(name = "dr")
    private Integer dr = 0;

    @Column(name = "ts")
    private java.util.Date ts;

    @OneToMany(targetEntity = Employee.class)
    private java.util.List<Employee> id_employee;

    @OneToMany(targetEntity = BU.class)
    private java.util.List<BU> id_entity2;

    /**
     * 属性 o_id的Getter方法.属性名：o_id 创建日期:2016-3-31
     * 
     * @return java.lang.String
     */
    public String getO_id() {
        return o_id;
    }

    /**
     * 属性o_id的Setter方法.属性名：o_id 创建日期:2016-3-31
     * 
     * @param newO_id java.lang.String
     */
    public void setO_id(String newO_id) {
        this.o_id = newO_id;
    }

    /**
     * 属性 o_name的Getter方法.属性名：o_name 创建日期:2016-3-31
     * 
     * @return java.lang.String
     */
    public String getO_name() {
        return o_name;
    }

    /**
     * 属性o_name的Setter方法.属性名：o_name 创建日期:2016-3-31
     * 
     * @param newO_name java.lang.String
     */
    public void setO_name(String newO_name) {
        this.o_name = newO_name;
    }

    /**
     * 属性 o_code的Getter方法.属性名：o_code 创建日期:2016-3-31
     * 
     * @return java.lang.String
     */
    public String getO_code() {
        return o_code;
    }

    /**
     * 属性o_code的Setter方法.属性名：o_code 创建日期:2016-3-31
     * 
     * @param newO_code java.lang.String
     */
    public void setO_code(String newO_code) {
        this.o_code = newO_code;
    }

    /**
     * 属性 id_employee的Getter方法.属性名：employeeList 创建日期:2016-3-31
     * 
     * @return java.util.List<com.yonyou.Employee>
     */
    public java.util.List<Employee> getId_employee() {
        return id_employee;
    }

    /**
     * 属性id_employee的Setter方法.属性名：employeeList 创建日期:2016-3-31
     * 
     * @param newId_employee java.util.List<com.yonyou.Employee>
     */
    public void setId_employee(java.util.List<Employee> newId_employee) {
        this.id_employee = newId_employee;
    }

    /**
     * 属性 id_entity2的Getter方法.属性名：buList 创建日期:2016-3-31
     * 
     * @return java.util.List<com.yonyou.BU>
     */
    public java.util.List<BU> getId_entity2() {
        return id_entity2;
    }

    /**
     * 属性id_entity2的Setter方法.属性名：buList 创建日期:2016-3-31
     * 
     * @param newId_entity2 java.util.List<com.yonyou.BU>
     */
    public void setId_entity2(java.util.List<BU> newId_entity2) {
        this.id_entity2 = newId_entity2;
    }

    /**
     * 属性 dr的Getter方法.属性名：dr 创建日期:2016-3-31
     * 
     * @return java.lang.Integer
     */
    public Integer getDr() {
        return dr;
    }

    /**
     * 属性dr的Setter方法.属性名：dr 创建日期:2016-3-31
     * 
     * @param newDr java.lang.Integer
     */
    public void setDr(Integer newDr) {
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
        return "Org";
    }

    @Override
    public String getNamespace() {
        return "com.iuap.org";
    }


}
