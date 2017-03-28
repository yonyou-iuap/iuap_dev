package com.yonyou.iuap.example.service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import com.yonyou.iuap.example.entity.meta.Instit;
import com.yonyou.iuap.example.repository.meta.InstitDao;
import com.yonyou.iuap.mvc.type.SearchParams;
import org.springframework.transaction.annotation.Transactional;

/**
 * <p>Title: InstitService</p>
 * <p>Description: </p>
 * @author zhukai
 */

@Service
public class InstitService {
	
    @Autowired
    private InstitDao institdao;
    

    public List<Instit> findByFid(String pk) {
        return institdao.findByFid(pk);
    }

    public List<Instit> findByCode(String code) {
        return institdao.findByCode(code);
    }

    /**
     * 查询组织分页数据
     * 
     * @param pageRequest
     * @param searchParams
     * @return
     */
    public Page<Instit> selectAllByPage(PageRequest pageRequest, SearchParams searchParams) {
        return institdao.selectAllByPage(pageRequest, searchParams.getSearchMap());
    }

    /**
     * 查询所有组织
     * 
     * @return
     */
    public List<Instit> selectAll() {
        return institdao.findAll();
    }

    /**
     * 保存组织
     * 
     * @param recordList
     */
    @Transactional
    public void save(List<Instit> recordList) {
        List<Instit> addList = new ArrayList<>(recordList.size());
        List<Instit> updateList = new ArrayList<>(recordList.size());
        for (Instit instit : recordList) {

            if (instit.getInstitid() == null) {
            	instit.setInstitid(UUID.randomUUID().toString());
            	instit.setDr(0);
                addList.add(instit);
            } else {
                updateList.add(instit);
            }
        }
        if (CollectionUtils.isNotEmpty(addList)) {
        	institdao.batchInsert(addList);
        }
        if (CollectionUtils.isNotEmpty(updateList)) {
        	institdao.batchUpdate(updateList);
        }
    }

    /**
     * 删除组织
     * 
     * @param list
     */
    public void batchDeleteByPrimaryKey(List<Instit> list) {
    	institdao.batchDelete(list);
    }

}
