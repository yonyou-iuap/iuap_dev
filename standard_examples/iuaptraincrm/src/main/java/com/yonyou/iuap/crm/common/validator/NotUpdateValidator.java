/** 
 *Copyright 2016 Yonyou Corporation Ltd. All Rights Reserved.
 * This software is published under the terms of the Yonyou Software
 * License version 1.0, a copy of which has been included with this
 * distribution in the LICENSE.txt file.
 *
 * @Project Name : iuaptraincrm
 *
 * @File name : NotUpdateValidator.java
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

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.apache.commons.beanutils.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import com.yonyou.iuap.persistence.bs.dao.BaseDAO;
import com.yonyou.iuap.persistence.jdbc.framework.util.FastBeanHelper;

/**
 * 判断某个实体类中的某个字段不可修改
 * 
 * @author 
 * @date 2016年12月12日
 */

public class NotUpdateValidator implements
		ConstraintValidator<NotUpdateValid, Object> {

	private String[] fields; // 不可修改的字段

	@Autowired
	@Qualifier("baseDAO") private  BaseDAO dao;

	/**
	 * @author 
	 * @date 2016年12月12日
	 * @param constraintAnnotation
	 *            (non-Javadoc)
	 * @see javax.validation.ConstraintValidator#initialize(java.lang.annotation.Annotation)
	 */

	@Override
	public void initialize(NotUpdateValid paramA) {
		this.fields = paramA.fields();
	}

	/**
	 * @author 
	 * @date 2016年12月12日
	 * @param value
	 * @param context
	 * @return (non-Javadoc)
	 * @see javax.validation.ConstraintValidator#isValid(java.lang.Object,
	 *      javax.validation.ConstraintValidatorContext)
	 */

	@Override
	public boolean isValid(Object value, ConstraintValidatorContext context) {
		try {

			// 获取该实体类@Id的主键字段并获取该实体补课修改的字段的值
			String idField = FastBeanHelper.getPKFieldName(value.getClass());
			String idFieldValue = BeanUtils.getProperty(value, idField); 		
			
			// 新增实体时， 不用校验
			if(idFieldValue == null) {
				return true;
			}
			
			// 获取旧值
			Object oldEntity = dao.queryByPK(value.getClass(), idFieldValue);
			String newValue, oldValue;
			boolean updated = false;

			for (int i = 0; i < fields.length; i++) {
				newValue = BeanUtils.getProperty(value, fields[i]);
				oldValue = BeanUtils.getProperty(oldEntity, fields[i]);
				updated = !((oldValue != null) && (oldValue.equals(newValue)) || ((oldValue == null) && (newValue == null)));
				if (updated) {
					break;
				}
			}

			if (updated) {
				String messageTemplate = context
						.getDefaultConstraintMessageTemplate();
				context.disableDefaultConstraintViolation();
				context.buildConstraintViolationWithTemplate(messageTemplate)
						.addNode(fields.toString()).addConstraintViolation();
				return false;
			}
			return true;

		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}

}
