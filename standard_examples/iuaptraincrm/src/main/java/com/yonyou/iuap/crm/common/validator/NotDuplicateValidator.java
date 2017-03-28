
/** 
*Copyright 2016 Yonyou Corporation Ltd. All Rights Reserved.
* This software is published under the terms of the Yonyou Software
* License version 1.0, a copy of which has been included with this
* distribution in the LICENSE.txt file.
*
* @Project Name : iuaptraincrm
*
* @File name : NotDuplicateValidator.java
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

import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.apache.commons.beanutils.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;

import com.yonyou.iuap.persistence.bs.dao.DAOException;
import com.yonyou.iuap.persistence.jdbc.framework.util.FastBeanHelper;
import com.yonyou.iuap.persistence.vo.BaseEntity;
import com.yonyou.iuap.crm.common.base.repository.AppBaseDao;


/**验证属性是否重复
 * TODO description
 * @author 
 * @date 2016年12月21日
 */

public class NotDuplicateValidator implements ConstraintValidator<NotDuplicateValid, Object> {

	Class<? extends BaseEntity> claz;
	String[] fieldColums;
	
	@Autowired
	private AppBaseDao baseDao;
	
	/**
	* @author 
	* @date 2016年12月21日
	* @param constraintAnnotation
	* (non-Javadoc)
	* @see javax.validation.ConstraintValidator#initialize(java.lang.annotation.Annotation)
	*/
		
	@Override
	public void initialize(NotDuplicateValid constraintAnnotation) {
		claz = constraintAnnotation.claz();
		fieldColums=constraintAnnotation.field();
	}

	
	/**
	* @author 
	* @date 2016年12月21日
	* @param value
	* @param context
	* @return
	* (non-Javadoc)
	* @see javax.validation.ConstraintValidator#isValid(java.lang.Object, javax.validation.ConstraintValidatorContext)
	*/
		
	@Override
	public boolean isValid(Object obj, ConstraintValidatorContext context) {
		
		try{
			StringBuffer condition = new StringBuffer("");
			String PkColumn = FastBeanHelper.getPkColumn(claz);
			String idValue = BeanUtils.getProperty(obj,PkColumn.toLowerCase());
			for(int i=0;i<fieldColums.length;i++){
				String fieldVale = BeanUtils.getProperty(obj, fieldColums[i]);
				condition.append("OR "+fieldColums[i]+" ='"+fieldVale+"' " );
			}

			List<? extends BaseEntity> entityList = baseDao.findListByClauseWithDR(claz, PkColumn +" !='"+idValue+"' AND ("+condition.toString().substring(2)+") ");
			if(entityList.size()>0){
				String messageTemplate = context
						.getDefaultConstraintMessageTemplate();
				context.disableDefaultConstraintViolation();
				context.buildConstraintViolationWithTemplate(messageTemplate)
						.addNode(claz.toString()).addConstraintViolation();
				return false;
			}else{
				return true;
			}
		}catch(DAOException e){
			e.printStackTrace();
		}catch(IllegalAccessException e){
			e.printStackTrace();
		}catch(InvocationTargetException e){
			e.printStackTrace();
		}catch(NoSuchMethodException e){
			e.printStackTrace();
		}
		return false;
	}

}
