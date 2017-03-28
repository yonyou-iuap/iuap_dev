package com.yonyou.iuap.crm.enums.repository.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Repository;

import com.yonyou.iuap.persistence.bs.dao.DAOException;
import com.yonyou.iuap.persistence.jdbc.framework.SQLParameter;
import com.yonyou.iuap.crm.common.base.repository.AppBaseDao;
import com.yonyou.iuap.crm.enums.entity.AppEnumVO;
import com.yonyou.iuap.crm.enums.repository.itf.IAppEnumDao;

@Repository
public class AppEnumDaoImpl implements IAppEnumDao{

	@Autowired
	private AppBaseDao appbasedao;
	
	@Override
	public void save(AppEnumVO enumvo) throws DAOException {
		appbasedao.save(enumvo);
	}

	@Override
	public void update(AppEnumVO enumvo) throws DAOException {
		appbasedao.update(enumvo);
	}

	@Override
	public void delete(AppEnumVO enumvo) throws DAOException {
		appbasedao.delete(enumvo);
	}

	@Override
	public List<AppEnumVO> queryAll() throws DAOException {
		List<AppEnumVO> results = appbasedao.findAll(AppEnumVO.class);
		return results;
	}
	
	public Page<AppEnumVO> queryPage(String condition, SQLParameter sqlParameter,
			PageRequest pageRequest) throws DAOException{
		return appbasedao.findBypage(AppEnumVO.class, condition, sqlParameter, pageRequest);
	}

	
}
