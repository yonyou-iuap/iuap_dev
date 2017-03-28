package com.yonyou.iuap.crm.psn.repository.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Repository;

import com.yonyou.iuap.persistence.bs.dao.DAOException;
import com.yonyou.iuap.persistence.jdbc.framework.SQLParameter;
import com.yonyou.iuap.crm.common.base.repository.AppBaseDao;
import com.yonyou.iuap.crm.psn.entity.BdPsnDutyVO;
import com.yonyou.iuap.crm.psn.repository.itf.IBdPsnDutyDao;

/**
 * 
 * TODO description
 * 
 * @author 
 * @date 2016年12月8日
 */
@Repository
public class BdPsnDutyDaoImpl implements IBdPsnDutyDao {
	@Autowired
	private AppBaseDao baseDao;

	@Override
	public void updatePsnDuty(BdPsnDutyVO entity) throws DAOException {
		baseDao.update(entity);
	}

	@Override
	public String savePsnDuty(BdPsnDutyVO entity) throws DAOException {
		return baseDao.saveWithPK(entity);
	}

	@Override
	public Page<BdPsnDutyVO> getPsnDutysBypage(String condition,
			SQLParameter sqlParameter, PageRequest pageRequest, String pk_psndoc)
			throws DAOException {
		if (sqlParameter.getCountParams() == 0)
			sqlParameter = null;
		StringBuffer sqlBuffer = new StringBuffer(" and pk_psndoc='"
				+ pk_psndoc + "' ");
		SQLParameter sqlParameternum = new SQLParameter();
		sqlBuffer.append(condition);
		return baseDao.findBypageWithDR(BdPsnDutyVO.class,
				sqlBuffer.toString(), sqlParameter, pageRequest);
	}

	@Override
	public void deletePsnDutyById(String pk_psnduty) throws DAOException {
		baseDao.deleteWithDR(BdPsnDutyVO.class, pk_psnduty);;
	}

}
