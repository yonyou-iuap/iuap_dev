
/** 
*Copyright 2016 Yonyou Corporation Ltd. All Rights Reserved.
* This software is published under the terms of the Yonyou Software
* License version 1.0, a copy of which has been included with this
* distribution in the LICENSE.txt file.
*
* @Project Name : iuaptraincrm
*
* @File name : NotUpdateValid.java
*
* @author 
*
* @Date : 2016年12月12日
*
----------------------------------------------------------------------------------
*     Date       Who       Version     Comments
* 1. 2016年12月12日    name    1.0
*
*
*
*
----------------------------------------------------------------------------------
*/
	
package com.yonyou.iuap.crm.common.validator;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import javax.validation.Constraint;
import javax.validation.Payload;


/**
 *判断某个实体类中的某个字段不可修改
 * @author 
 * @date 2016年12月12日
 */
@Target({ElementType.TYPE,ElementType.ANNOTATION_TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = {NotUpdateValidator.class})
@Documented
public @interface NotUpdateValid {
	public String message() default "field can't be updated";
	
	public Class<?>[] groups() default {};
	
	Class<? extends Payload>[] payload() default {};
	
	String[] fields();
}
 