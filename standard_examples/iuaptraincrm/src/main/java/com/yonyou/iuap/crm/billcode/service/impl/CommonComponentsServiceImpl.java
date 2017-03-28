package com.yonyou.iuap.crm.billcode.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.yonyou.iuap.persistence.bs.dao.DAOException;
import com.yonyou.uap.billcode.BillCodeException;
import com.yonyou.uap.billcode.model.BillCodeRuleVO;
import com.yonyou.uap.billcode.service.IBillCodeProvider;
import com.yonyou.iuap.crm.billcode.repository.itf.ICommonComponentsDao;
import com.yonyou.iuap.crm.billcode.service.itf.ICommonComponentsService;
import com.yonyou.iuap.crm.common.exception.AppBusinessException;

/**
 * 公共组件服务
* <p>description：</p>
* @author 
* @created 2016年12月12日 下午2:43:59
* @version 1.0
 */
@Service
public class CommonComponentsServiceImpl implements ICommonComponentsService {

	@Autowired
	private IBillCodeProvider billCodeProviderService;
	@Autowired
	private ICommonComponentsDao dao;
	
	private static CommonComponentsServiceImpl service;
	
	/**
	 * 根据编码号获取单据号
	* @author 
	* @date 2016年12月9日
	* @param ruleCode
	* @return
	* @throws AppBusinessException
	* (non-Javadoc)
	* @see com.yonyou.iuap.crm.currtype.service.itf.IBdCurrtypeService#getBillNoWithRuleCode(java.lang.String)
	 */
	public String generateOrderNo(String ruleCode) throws AppBusinessException{
		String billNo = null;
		try{

			BillCodeRuleVO codeRuleVO =  dao.getBillCodeRuleVO(ruleCode);
			if(null != codeRuleVO){
				billNo = billCodeProviderService.getBillCode(codeRuleVO, null, null, null);
			}
		}catch(DAOException e){
			throw new AppBusinessException(e.getMessage());
		} catch (BillCodeException e) {
			throw new AppBusinessException(e.getMessage());
		}
		return billNo;
	}

}
