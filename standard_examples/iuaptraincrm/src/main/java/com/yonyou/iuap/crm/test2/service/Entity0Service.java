package com.yonyou.iuap.crm.test2.service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import com.yonyou.iuap.crm.test2.entity.Entity0;
import com.yonyou.iuap.crm.test2.dao.Entity0Dao;
import com.yonyou.iuap.mvc.type.SearchParams;
import com.yonyou.iuap.persistence.bs.jdbc.meta.access.DASFacade;

/**
 * <p>Title: CardTableMetaService</p>
 * <p>Description: </p>
 */
@Service
public class Entity0Service {
	
    @Autowired
    private Entity0Dao dao;
    
    /**
     * Description:通过非主键字段查询
     * List<CardTable>
     * @param str
     */
    
    
    public Page<Entity0> selectAllByPage(PageRequest pageRequest, SearchParams searchParams) {
        Page<Entity0> pageResult = dao.selectAllByPage(pageRequest, searchParams.getSearchMap()) ;
		return pageResult;
    }
    
    public void save(List<Entity0> recordList) {
        List<Entity0> addList = new ArrayList<>(recordList.size());
        List<Entity0> updateList = new ArrayList<>(recordList.size());
        for (Entity0 meta : recordList) {
        	if (meta.getId() == null) {
            	meta.setId(UUID.randomUUID().toString());
            	meta.setDr(0);
                addList.add(meta);
            } else {
                updateList.add(meta);
            }
        }
        if (CollectionUtils.isNotEmpty(addList)) {
        	dao.batchInsert(addList);
        }
        if (CollectionUtils.isNotEmpty(updateList)) {
        	dao.batchUpdate(updateList);
        }
    }
    
    public void batchDeleteByPrimaryKey(List<Entity0> list) {
    	dao.batchDelete(list);
    }
    
}
