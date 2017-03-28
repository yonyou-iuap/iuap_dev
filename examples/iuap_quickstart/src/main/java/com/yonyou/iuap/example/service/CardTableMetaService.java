package com.yonyou.iuap.example.service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import com.yonyou.iuap.example.entity.meta.CardTableMeta;
import com.yonyou.iuap.example.repository.meta.CardTableMetaDao;
import com.yonyou.iuap.mvc.type.SearchParams;
import org.springframework.transaction.annotation.Transactional;

/**
 * <p>Title: CardTableMetaService</p>
 * <p>Description: </p>
 * @author zhukai
 */
@Service
public class CardTableMetaService {
	
    @Autowired
    CardTableMetaDao cardTableMetaDao;
    
	/**
	 * 根据某一非主键字段查询实体
	 */
    public List<CardTableMeta> findByCode(String code) {
        return cardTableMetaDao.findByCode(code);
    }
    
	/**
	 *分页相关
	 */
    public Page<CardTableMeta> selectAllByPage(PageRequest pageRequest, SearchParams searchParams) {
        return cardTableMetaDao.selectAllByPage(pageRequest, searchParams.getSearchMap());
    }
    
	/**
	 *保存（插入和更新）
	 */
	@Transactional
    public void save(List<CardTableMeta> recordList) {
        List<CardTableMeta> addList = new ArrayList<>(recordList.size());
        List<CardTableMeta> updateList = new ArrayList<>(recordList.size());
        for (CardTableMeta cardTableMeta : recordList) {

            if (cardTableMeta.getPksystem() == null) {
            	cardTableMeta.setPksystem(UUID.randomUUID().toString());
            	cardTableMeta.setDr(0);
                addList.add(cardTableMeta);
            } else {
                updateList.add(cardTableMeta);
            }
        }
        if (CollectionUtils.isNotEmpty(addList)) {
        	cardTableMetaDao.batchInsert(addList);
        }
        if (CollectionUtils.isNotEmpty(updateList)) {
        	cardTableMetaDao.batchUpdate(updateList);
        }
    }
    
	/**
	 *批量删除
	 */
    public void batchDeleteByPrimaryKey(List<CardTableMeta> list) {
    	cardTableMetaDao.batchDelete(list);
    }

    

}
