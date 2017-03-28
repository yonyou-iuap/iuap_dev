package com.yonyou.iuap.example.entity.meta;

/**将枚举内容转换为前端 js框架可以解析的 数据格式*/
public class  EnumVo{
	public String name;

	public Object value;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Object getValue() {
		return value;
	}

	public void setValue(Object value) {
		this.value = value;
	}

}