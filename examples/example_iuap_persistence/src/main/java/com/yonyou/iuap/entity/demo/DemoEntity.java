package com.yonyou.iuap.entity.demo;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

import org.hibernate.validator.constraints.NotBlank;

@Entity
@Table(name="example_demo")
@NamedQuery(name="DemoEntity.findAll", query="SELECT d FROM DemoEntity d")
public class DemoEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="id")
	private String id;
	
	@NotBlank(message="测试编码不能为空!")
	private String code;

	private String memo;

	private String name;
	
	private String isdefault;

	public DemoEntity() {
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getCode() {
		return this.code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getMemo() {
		return this.memo;
	}

	public void setMemo(String memo) {
		this.memo = memo;
	}

	public String getName() {
		return this.name;
	}

	public String getIsdefault() {
		return isdefault;
	}

	public void setIsdefault(String isdefault) {
		this.isdefault = isdefault;
	}

	public void setName(String name) {
		this.name = name;
	}

}