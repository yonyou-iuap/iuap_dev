package com.yonyou.iuap.crm.billcode.service.itf;

import com.yonyou.iuap.crm.common.exception.AppBusinessException;
/**
 * 公共组件接口
* <p>description：</p>
* @author 
* @created 2016年12月12日 下午2:44:17
* @version 1.0
 */
public interface ICommonComponentsService {

	/**
	 * 根据编码号获取单据号
	* TODO description
	* @author 
	* @date 2016年12月9日
	* @param ruleCode
	* @return
	* @throws AppBusinessException
	 */
	public String generateOrderNo(String ruleCode) throws AppBusinessException;
}
