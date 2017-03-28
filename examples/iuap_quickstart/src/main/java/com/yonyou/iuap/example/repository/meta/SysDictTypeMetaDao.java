/**
 */
package com.yonyou.iuap.example.repository.meta;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.yonyou.iuap.context.InvocationInfoProxy;
import com.yonyou.iuap.example.entity.meta.SysDictTypeMeta;
import com.yonyou.iuap.iweb.exception.WebRuntimeException;
import com.yonyou.iuap.persistence.bs.dao.MetadataDAO;
import com.yonyou.iuap.persistence.jdbc.framework.SQLParameter;
import com.yonyou.iuap.persistence.jdbc.framework.util.FastBeanHelper;
import com.yonyou.iuap.persistence.vo.pub.VOStatus;

@Repository
public class SysDictTypeMetaDao {

	@Autowired
	private MetadataDAO metadataDAO;

	  /**
     * 分页查询方法
     * @param pageRequest
     * @param searchParams
     * @return
     */
    public Page<SysDictTypeMeta> selectAllByPage(PageRequest pageRequest, Map<String, Object> searchParams) {
    	 String sql = " SELECT * FROM sys_dict_type_meta ";   
         SQLParameter sqlparam = new SQLParameter();
         if (null != searchParams && !searchParams.isEmpty()) {
             sql = sql + " where ";
             for (String key : searchParams.keySet()) {
                 sql = sql + FastBeanHelper.getColumn(SysDictTypeMeta.class, key) + " like ? AND ";
                 sqlparam.addParam("%" + searchParams.get(key) + "%");
             }
             sql = sql.substring(0, sql.length() - 4);
         }
         return metadataDAO.queryPage(sql, sqlparam, pageRequest, SysDictTypeMeta.class);
    }

    /**
     * 批量保存，更新，删除方法
     * @param addList
     * @param updateList
     * @param removeList
     */
    @Transactional
    public void save(List<SysDictTypeMeta> addList, List<SysDictTypeMeta> updateList, List<SysDictTypeMeta> removeList) {
        if (CollectionUtils.isNotEmpty(addList)) {
            for (SysDictTypeMeta dictType : addList) {
            	dictType.setStatus(VOStatus.NEW);
            	if(dictType.getCreatetime() == null ){
            		dictType.setCreatetime(new Date());
            	}
                dictType.setModifytime(dictType.getCreatetime());
                // 从InvocationInfoProxy获取值
                dictType.setCreator(InvocationInfoProxy.getUsername());
                dictType.setCreatorid(InvocationInfoProxy.getUserid());
                dictType.setDr(0); 
            }
            metadataDAO.insert(addList) ;
        }
        if (CollectionUtils.isNotEmpty(updateList)) {
            for (SysDictTypeMeta dictType : addList) {
            	dictType.setStatus(VOStatus.UPDATED);
                // 从InvocationInfoProxy获取值
                dictType.setModifier(InvocationInfoProxy.getUsername());
                dictType.setModifierid(InvocationInfoProxy.getUserid());
                dictType.setModifytime(new Date());
            }
            metadataDAO.update(updateList) ;
        }
        if (CollectionUtils.isNotEmpty(removeList)) {
        	 for (SysDictTypeMeta dictType : addList) {
             	dictType.setStatus(VOStatus.DELETED);
             }
        	 metadataDAO.remove(removeList);
        }
        
    }

    /**
     * 批量删除
     * @param list
     */
    public void batchDeleteByPrimaryKey(List<SysDictTypeMeta> list) {
        if (CollectionUtils.isEmpty(list)) {
            throw new WebRuntimeException("当前没有选中数据!");
        }
        metadataDAO.remove(list);
    }

    /**
     * 用于保存时候的编码重复校验
     * @param dictTypeCode
     * @return
     */
    public List<SysDictTypeMeta> findByCode(String dictTypeCode) {
      String sql = " SELECT * FROM sys_dict_type_meta where dict_type_code=? ";
      SQLParameter sqlparam = new SQLParameter();
      sqlparam.addParam(dictTypeCode);
      return  metadataDAO.queryByClause(SysDictTypeMeta.class, sql, sqlparam);
    }

}
