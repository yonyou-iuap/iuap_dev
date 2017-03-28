package com.yonyou.iuap.example.entity;

import java.io.Serializable;
import java.math.BigInteger;
import java.sql.Timestamp;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.validator.constraints.NotBlank;

/**
 * 商品示例实体，JPA方式
 */
@Entity
@Table(name="good_demo")
public class GoodJpaDemo implements Serializable {
	
	private static final long serialVersionUID = 6982552527937662598L;

	@Id
	@Column(name="productid")
	private String productid;
	
	@Column(name="productname")
	@NotBlank(message="名称不能为空!")
    private String productName;
	
	@Column(name="productnum")
    private BigInteger productNum;
    
    private Double price;
    
    private String supplier;
    
    @Column(name="proDate")
    private Date proDate;
    
    private String orgin;
    
    private Timestamp ts;
	
	public GoodJpaDemo() {
	}

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

}