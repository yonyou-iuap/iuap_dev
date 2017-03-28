package com.yonyou.iuap.crm.billcode.repository.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import com.yonyou.iuap.crm.billcode.entity.ExtRuleBaseVO;
import com.yonyou.iuap.crm.billcode.entity.ExtRuleEleVO;
import com.yonyou.iuap.crm.billcode.repository.itf.ICommonComponentsDao;
import com.yonyou.iuap.persistence.bs.dao.BaseDAO;
import com.yonyou.iuap.persistence.bs.dao.DAOException;
import com.yonyou.iuap.persistence.jdbc.framework.SQLParameter;
import com.yonyou.iuap.persistence.jdbc.framework.processor.BeanListProcessor;
import com.yonyou.uap.billcode.model.BillCodeRuleVO;

//用于标注数据访问组件，即DAO组件
@Repository
public class CommonComponentsDaoImpl implements ICommonComponentsDao {

	@Autowired
	@Qualifier("baseDAO") private BaseDAO baseDao;

	/**
	 * 根据编码查询VO
	* @author 
	* @date 2016年12月9日
	* @param ruleCode
	* @return
	* @throws DAOException
	* (non-Javadoc)
	* @see com.yonyou.iuap.crm.currtype.repository.itf.IBdCurrtypeDao#getBillCodeRuleVO(java.lang.String)
	 */
	@Override
	public BillCodeRuleVO getBillCodeRuleVO(String ruleCode)
			throws DAOException {
		BillCodeRuleVO codeRuleVO = null;
		try{
			String sql = "select * from pub_bcr_rulebase where rulecode=?";
			SQLParameter sqlParameter = new SQLParameter();
			sqlParameter.addParam(ruleCode);
			List<ExtRuleBaseVO> ruleBaseList = baseDao.queryForObject(sql, sqlParameter, new BeanListProcessor(ExtRuleBaseVO.class));
			if(null != ruleBaseList && ruleBaseList.size()>0){
				ExtRuleBaseVO ruleBaseVo = ruleBaseList.get(0);
				codeRuleVO = new BillCodeRuleVO();
				
				codeRuleVO.setBaseVO(ruleBaseVo);
				String pk = ruleBaseVo.getPk_billcodebase();
				
				String sqlEle = "select * from pub_bcr_elem where pk_billcodebase=?";
				SQLParameter sqlEleParameter = new SQLParameter();
				sqlEleParameter.addParam(pk);
				List<ExtRuleEleVO> ruleEleList = baseDao.queryForObject(sqlEle, sqlEleParameter, new BeanListProcessor(ExtRuleEleVO.class));
				codeRuleVO.setElems(ruleEleList.toArray(new ExtRuleEleVO[0]));
			}
		}catch(DAOException e){
			throw e;
		}
		return codeRuleVO;
	}
	
}
