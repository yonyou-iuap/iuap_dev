package com.yonyou.iuap.example.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.yonyou.iuap.example.entity.GoodJdbcDemo;
import com.yonyou.iuap.example.repository.GoodJdbcDemoDao;
import com.yonyou.iuap.persistence.bs.dao.DAOException;

@Service
public class GoodJdbcDemoService {

	@Autowired
	private GoodJdbcDemoDao dao;

	public GoodJdbcDemo getGoodById(String id) throws DAOException {
		return dao.queryByPK(id);
	}

	@Transactional
	public void deleteById(String id) throws DAOException {
		GoodJdbcDemo goodDemo = new GoodJdbcDemo();
		goodDemo.setProductid(id);
		dao.remove(goodDemo);
	}
	
	@Transactional
	public void batchDelete(List<String> ids) throws DAOException {
		List<GoodJdbcDemo> deleteVos = new ArrayList<GoodJdbcDemo>();
		for (int i = 0; i < ids.size(); i++) {
			GoodJdbcDemo goodDemo = new GoodJdbcDemo();
			goodDemo.setProductid(ids.get(i));
			deleteVos.add(goodDemo);
		}
		if (deleteVos.size() > 0) {
			dao.remove(deleteVos);
		}
	}
	
	@Transactional
	public GoodJdbcDemo saveEntity(GoodJdbcDemo entity) throws DAOException {
		dao.save(entity);
		return entity;
	}

	public Page<GoodJdbcDemo> getDemoPage(Map<String, Object> searchParams, PageRequest pageRequest) throws DAOException {
		return dao.queryPage(searchParams, pageRequest);
	}

}
