package com.yonyou.iuap.example.entity;

import java.math.BigInteger;
import java.sql.Timestamp;
import java.util.Date;

import com.yonyou.iuap.persistence.jdbc.framework.annotation.Column;
import com.yonyou.iuap.persistence.jdbc.framework.annotation.Entity;
import com.yonyou.iuap.persistence.jdbc.framework.annotation.GeneratedValue;
import com.yonyou.iuap.persistence.jdbc.framework.annotation.Id;
import com.yonyou.iuap.persistence.jdbc.framework.annotation.Stragegy;
import com.yonyou.iuap.persistence.jdbc.framework.annotation.Table;
import com.yonyou.iuap.persistence.vo.BaseEntity;

/**
 * 商品示例实体,iuap-jdbc方式
 */
@Entity
@Table(name="good_demo")
public class GoodJdbcDemo extends BaseEntity{

	private static final long serialVersionUID = -3773469861533388458L;

	@Id
    @Column(name = "productid")
    @GeneratedValue(strategy=Stragegy.UUID,moudle="example_demo")
    private String productid;
    
    @Column(name = "productName")
    private String productName;
    
    @Column(name = "productNum")
    private BigInteger productNum;
    
    @Column(name = "price")
    private Double price;
    
    @Column(name = "supplier")
    private String supplier;
    
    @Column(name = "proDate")
    private Date proDate;

    @Column(name = "orgin")
    private String orgin;
    
    @Column(name = "ts")
    private Timestamp ts;

	public String getProductid() {
		return productid;
	}

	public void setProductid(String productid) {
		this.productid = productid;
	}

	public String getProductName() {
		return productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}

	public BigInteger getProductNum() {
		return productNum;
	}

	public void setProductNum(BigInteger productNum) {
		this.productNum = productNum;
	}

	public Double getPrice() {
		return price;
	}

	public void setPrice(Double price) {
		this.price = price;
	}

	public String getSupplier() {
		return supplier;
	}

	public void setSupplier(String supplier) {
		this.supplier = supplier;
	}

	public Date getProDate() {
		return proDate;
	}

	public void setProDate(Date proDate) {
		this.proDate = proDate;
	}

	public String getOrgin() {
		return orgin;
	}

	public void setOrgin(String orgin) {
		this.orgin = orgin;
	}

	public Timestamp getTs() {
		return ts;
	}

	public void setTs(Timestamp ts) {
		this.ts = ts;
	}

	@Override
	public String getMetaDefinedName() {
		return "GoodJdbcDemo";
	}

	@Override
	public String getNamespace() {
		//应该工具的元数据设计器上指定
		return "com.yonyou.iuap.example";
	}
}
