package com.yonyou.iuap.example.service;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.yonyou.iuap.context.InvocationInfoProxy;
import com.yonyou.iuap.example.entity.mybatis.SysDictType;
import com.yonyou.iuap.example.repository.mybatis.SysDictTypeMapper;
import com.yonyou.iuap.iweb.exception.WebRuntimeException;
import com.yonyou.iuap.persistence.vo.pub.util.StringUtil;

@Service
public class SysDictTypeService {

    @Autowired
    private SysDictTypeMapper sysDictTypeMapper;

    /**
     * 分页查询方法
     * 
     * @param pageRequest
     * @param searchParams
     * @return
     */
    public Page<SysDictType> selectAllByPage(PageRequest pageRequest, Map<String, Object> searchParams) {
        return sysDictTypeMapper.selectAllByPage(pageRequest, searchParams).getPage();
    }

    /**
     * 批量保存，更新，删除方法
     * 
     * @param addList
     * @param updateList
     * @param removeList
     */
    @Transactional
    public void save(List<SysDictType> addList, List<SysDictType> updateList, List<SysDictType> removeList) {
        if (CollectionUtils.isNotEmpty(addList)) {
            for (SysDictType dictType : addList) {
                dictType.setDictid(UUID.randomUUID().toString());
                if (dictType.getCreatetime() == null) {
                    dictType.setCreatetime(new Date());
                }
                dictType.setModifytime(dictType.getCreatetime());
                // 从InvocationInfoProxy获取值
                if (StringUtil.isEmpty(dictType.getCreator())) {
                    dictType.setCreator(InvocationInfoProxy.getUsername());
                }
                dictType.setCreatorid(InvocationInfoProxy.getUserid());
                dictType.setDr("0");
            }
            sysDictTypeMapper.batchInsert(addList);
        }
        if (CollectionUtils.isNotEmpty(updateList)) {
            for (SysDictType dictType : addList) {

                // 从InvocationInfoProxy获取值
                dictType.setModifier(InvocationInfoProxy.getUsername());
                dictType.setModifierid(InvocationInfoProxy.getUserid());
                dictType.setModifytime(new Date());
            }
            sysDictTypeMapper.batchUpdate(updateList);
        }
        if (CollectionUtils.isNotEmpty(removeList)) {
            sysDictTypeMapper.batchDeleteByPrimaryKey(removeList);
        }
    }

    /**
     * 批量删除
     * 
     * @param list
     */
    @Transactional
    public void batchDeleteByPrimaryKey(List<SysDictType> list) {
        if (CollectionUtils.isEmpty(list)) {
            throw new WebRuntimeException("当前没有选中数据!");
        }
        sysDictTypeMapper.batchDeleteByPrimaryKey(list);
    }

    /**
     * 用于保存时候的编码重复校验
     * 
     * @param dictTypeCode
     * @return
     */
    public List<SysDictType> findByCode(String dictTypeCode) {
        return sysDictTypeMapper.findByCode(dictTypeCode);
    }

}
