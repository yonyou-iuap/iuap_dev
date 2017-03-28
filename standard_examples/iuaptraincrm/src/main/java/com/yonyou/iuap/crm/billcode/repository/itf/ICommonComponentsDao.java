package com.yonyou.iuap.crm.billcode.repository.itf;

import com.yonyou.iuap.persistence.bs.dao.DAOException;
import com.yonyou.uap.billcode.model.BillCodeRuleVO;

public interface ICommonComponentsDao {
	/**
	 * 查询编码规则
	* TODO description
	* @author 
	* @date 2016年12月9日
	* @param ruleCode
	* @return
	* @throws DAOException
	 */
	public BillCodeRuleVO getBillCodeRuleVO(String ruleCode) throws DAOException;
}
