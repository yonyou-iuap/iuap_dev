package com.yonyou.iuap.crm.test.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.yonyou.iuap.persistence.vo.pub.util.StringUtil;
import com.yonyou.iuap.persistence.bs.jdbc.meta.access.DASFacade;

import com.yonyou.iuap.crm.test.entity.Test;
import com.yonyou.iuap.crm.test.entity.TestB;
import com.yonyou.iuap.crm.test.dao.TestDao;
import com.yonyou.iuap.crm.test.dao.TestBDao;


@Service
public class TestBService {

    @Autowired
    private TestBDao childDao;

    @Autowired
    private TestDao dao;

    
    public Page<TestB> selectAllByPage(Map<String, Object> searchParams, PageRequest pageRequest) {
		Page<TestB> pageResult = childDao.selectAllByPage(pageRequest, searchParams) ;
		return pageResult;
	}

    @Transactional
    public void deleteEntity(TestB entity) {
        childDao.delete(entity);
    }

}
