package com.yonyou.iuap.crm.common.utils;

import java.sql.Timestamp;

import net.sf.ezmorph.MorphException;
import net.sf.ezmorph.object.AbstractObjectMorpher;

public class TimestampMorpher extends AbstractObjectMorpher{

	@Override
	public Class morphsTo() {
		return Timestamp.class;
	}

	@Override
	public Object morph(Object value) {
		if(value == null){
			return null;
		}
		if(Timestamp.class.isAssignableFrom(value.getClass())){
			return (Timestamp)value;
		}
		if(!supports(value.getClass())){
			throw new MorphException(value.getClass() + " is not supported");
		}
		if(value instanceof String){
			String strValue = (String)value;
			return Timestamp.valueOf(strValue);			
		}
		long lvalue = (long) value;
		return new Timestamp(lvalue);
	}
	
	public boolean supports(Class clazz){
		boolean flag = String.class.isAssignableFrom(clazz) || Long.class.isAssignableFrom(clazz);
		return flag;
	}

}
