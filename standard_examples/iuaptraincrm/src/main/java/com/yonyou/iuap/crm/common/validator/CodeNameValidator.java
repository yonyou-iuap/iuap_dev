package com.yonyou.iuap.crm.common.validator;

import java.lang.reflect.InvocationTargetException;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.apache.commons.beanutils.BeanUtils;

/**
 * 用来校验币种的编码格式，只能输入字母、数字、字母和数字的组合
 * @author 
 *
 */
public class CodeNameValidator implements ConstraintValidator<CodeNameValid, Object> {

	private String field;
	private String verifyField;
	
	@Override
	public void initialize(CodeNameValid paramA) {
		this.field = paramA.field();
		this.verifyField = paramA.verifyField();
	}

	@Override
	public boolean isValid(Object value,
			ConstraintValidatorContext context) {
		try {
			String fieldValue = BeanUtils.getProperty(value, field);
			String verityFieldValue = BeanUtils.getProperty(value, verifyField);

			boolean match = (fieldValue != null) && (!fieldValue.equals(verityFieldValue));
			if(!match){
				String messageTemplate = context.getDefaultConstraintMessageTemplate();
				context.disableDefaultConstraintViolation();
				context.buildConstraintViolationWithTemplate(messageTemplate)
						.addNode(verifyField)
						.addConstraintViolation();
			}
			return match;
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			e.printStackTrace();
		} catch (NoSuchMethodException e) {
			e.printStackTrace();
		}
		
		return true;
	}


}
