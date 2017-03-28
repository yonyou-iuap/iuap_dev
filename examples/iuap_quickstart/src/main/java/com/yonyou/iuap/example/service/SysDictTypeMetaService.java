/**
 */
package com.yonyou.iuap.example.service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.yonyou.iuap.example.entity.meta.SysDictTypeMeta;
import com.yonyou.iuap.example.repository.meta.SysDictTypeMetaDao;

@Service
public class SysDictTypeMetaService {

	
	@Autowired
	private SysDictTypeMetaDao sysDictMetaDao;

	  /**
     * 分页查询方法
     * @param pageRequest
     * @param searchParams
     * @return
     */
    public Page<SysDictTypeMeta> selectAllByPage(PageRequest pageRequest, Map<String, Object> searchParams) {
    	return sysDictMetaDao.selectAllByPage(pageRequest, searchParams) ;
    }

    /**
     * 批量保存，更新，删除方法
     * @param addList
     * @param updateList
     * @param removeList
     */
    @Transactional
    public void save(List<SysDictTypeMeta> addList, List<SysDictTypeMeta> updateList, List<SysDictTypeMeta> removeList) {
    	sysDictMetaDao.save(addList, updateList, removeList);
    }

    /**
     * 批量删除
     * @param list
     */
    public void batchDeleteByPrimaryKey(List<SysDictTypeMeta> list) {
    	sysDictMetaDao.batchDeleteByPrimaryKey(list);
    }

    /**
     * 用于保存时候的编码重复校验
     * @param dictTypeCode
     * @return
     */
    public List<SysDictTypeMeta> findByCode(String dictTypeCode) {
        return	sysDictMetaDao.findByCode(dictTypeCode) ;
    }

}
