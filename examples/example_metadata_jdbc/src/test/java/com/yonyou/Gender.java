package com.yonyou;


import com.yonyou.iuap.persistence.bs.jdbc.meta.model.MDEnum;
import com.yonyou.iuap.persistence.jdbc.framework.annotation.Entity;
import com.yonyou.metadata.spi.EnumItem;

/**
 * <b>此处简要描述此枚举的功能 </b>
 * <p>
 * 此处添加该枚举的描述信息
 * </p>
 * 创建日期:2016-3-31
 * 
 * @author
 * @version NCPrj
 */
@Entity(namespace = "com.iuap.org",name = "Gender")
public class Gender extends MDEnum {
    public Gender(EnumItem enumvalue) {
        super(enumvalue);
    }



    public static final Gender 男 = MDEnum.valueOf(Gender.class, String.valueOf("1"));


    public static final Gender 女 = MDEnum.valueOf(Gender.class, String.valueOf("2"));


}
