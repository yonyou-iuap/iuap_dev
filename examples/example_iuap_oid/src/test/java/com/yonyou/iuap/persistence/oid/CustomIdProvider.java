package com.yonyou.iuap.persistence.oid;

public class CustomIdProvider implements IOidProvider {

	@Override
	public String generatorID(String module) {
		return String.valueOf(module) + "_" + System.currentTimeMillis();
	}

}
