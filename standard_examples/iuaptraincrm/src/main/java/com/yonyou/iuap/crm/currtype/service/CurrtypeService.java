package com.yonyou.iuap.crm.currtype.service;

import java.util.Collection;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.yonyou.iuap.crm.currtype.entity.Currtype;
import com.yonyou.iuap.crm.currtype.dao.CurrtypeDao;

@Service
public class CurrtypeService {

	
	@Autowired
    private CurrtypeDao dao;
    

	  /**
     * 分页查询方法
     * @param pageRequest
     * @param searchParams
     * @return
     */
    public Page<Currtype> selectAllByPage(PageRequest pageRequest, Map<String, Object> searchParams) {
        Page<Currtype> pageResult = dao.selectAllByPage(pageRequest, searchParams) ;
		return pageResult;
    }

    /**
     * 批量保存，更新，删除方法
     * @param addList
     * @param updateList
     * @param removeList
     */
    @Transactional
    public void save(List<Currtype> addList, List<Currtype> updateList, List<Currtype> removeList) {
    	dao.save(addList, updateList, removeList);
    }

    /**
     * 批量删除
     * @param list
     */
    public void batchDeleteByPrimaryKey(List<Currtype> list) {
    	dao.batchDeleteByPrimaryKey(list);
    }


}
