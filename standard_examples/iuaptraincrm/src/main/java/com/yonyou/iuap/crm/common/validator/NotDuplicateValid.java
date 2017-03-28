
/** 
*Copyright 2016 Yonyou Corporation Ltd. All Rights Reserved.
* This software is published under the terms of the Yonyou Software
* License version 1.0, a copy of which has been included with this
* distribution in the LICENSE.txt file.
*
* @Project Name : iuaptraincrm
*
* @File name : NotDuplicateValid.java
*
* @Author : name
*
* @Date : 2016年12月21日
*
----------------------------------------------------------------------------------
*     Date       Who       Version     Comments
* 1. 2016年12月21日    name    1.0
*
*
*
*
----------------------------------------------------------------------------------
*/
	
package com.yonyou.iuap.crm.common.validator;

import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import javax.validation.Constraint;
import javax.validation.Payload;

import com.yonyou.iuap.persistence.vo.BaseEntity;


/**判断field是否重复（idfield为空或在数据库中不存在）
 * TODO description
 * @author 
 * @date 2016年12月21日
 */
@Target({ TYPE})
@Retention(RUNTIME)
@Documented
@Constraint(validatedBy = { NotDuplicateValidator.class})
public @interface NotDuplicateValid {
	public String message() default "field.duplicate";
	
	Class<?>[] groups() default { };
	
	Class<? extends Payload>[] payload() default {};
	
	Class<? extends BaseEntity> claz();
	String[] field();
}
