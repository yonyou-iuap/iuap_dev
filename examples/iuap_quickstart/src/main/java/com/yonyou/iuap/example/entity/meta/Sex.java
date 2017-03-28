package com.yonyou.iuap.example.entity.meta;

import com.yonyou.iuap.persistence.jdbc.framework.annotation.Entity;
import com.yonyou.iuap.persistence.bs.jdbc.meta.model.MDEnum;
import com.yonyou.metadata.spi.EnumItem;
/**
 * <b>此处简要描述此枚举的功能 </b>
 * <p>
 *   此处添加该枚举的描述信息
 * </p>
 *  创建日期:2016-12-13
 * @author 
 * @version 
 */
@Entity(namespace = "metadata",name = "Sex" )
public class Sex extends MDEnum{
	public Sex(EnumItem enumvalue){
		super(enumvalue);
	}

	
	
	public static final Sex 男 = MDEnum.valueOf(Sex.class, String.valueOf("0"));
	
	
	public static final Sex 女 = MDEnum.valueOf(Sex.class, String.valueOf("1"));
	

}