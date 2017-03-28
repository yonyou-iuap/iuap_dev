package com.yonyou.iuap.crm.currtype.dao;

import java.util.List;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.yonyou.iuap.crm.currtype.entity.Currtype;
import com.yonyou.iuap.iweb.exception.WebRuntimeException;
import com.yonyou.iuap.persistence.bs.dao.BaseDAO;
import com.yonyou.iuap.persistence.jdbc.framework.SQLParameter;
import com.yonyou.iuap.persistence.jdbc.framework.util.FastBeanHelper;
import com.yonyou.iuap.persistence.vo.pub.VOStatus;

@Repository
public class CurrtypeDao {

	@Autowired
	@Qualifier("baseDAO") private  BaseDAO dao;
	

	  /**
     * 分页查询方法
     * @param pageRequest
     * @param searchParams
     * @return
     */
    public Page<Currtype> selectAllByPage(PageRequest pageRequest, Map<String, Object> searchParams) {
    	 String sql = " SELECT * FROM train_currtype ";   
         SQLParameter sqlparam = new SQLParameter();
         if (null != searchParams && !searchParams.isEmpty()) {
             sql = sql + " where ";
             for (String key : searchParams.keySet()) {
                 sql = sql + FastBeanHelper.getColumn(Currtype.class, key) + " like ? AND ";
                 sqlparam.addParam("%" + searchParams.get(key) + "%");
             }
             sql = sql.substring(0, sql.length() - 4);
         }
         return dao.queryPage(sql, sqlparam, pageRequest, Currtype.class);
    }

    /**
     * 批量保存，更新，删除方法
     * @param addList
     * @param updateList
     * @param removeList
     */
    @Transactional
    public void save(List<Currtype> addList, List<Currtype> updateList, List<Currtype> removeList) {
        if (CollectionUtils.isNotEmpty(addList)) {
            for (Currtype obj : addList) {
            	obj.setStatus(VOStatus.NEW);
            	obj.setDr(0); 
//            	if(obj.getCreateTime() == null ){
//            		obj.setCreateTime(new Date()); 
//            	}
//            	obj.setModifyTime(dictType.getCreateTime());
//                // 从InvocationInfoProxy获取值
//            	obj.setCreator(InvocationInfoProxy.getUsername());
//            	obj.setCreatorId(InvocationInfoProxy.getUserid());
            }
            dao.insert(addList) ;
        }
        if (CollectionUtils.isNotEmpty(updateList)) {
            for (Currtype obj : addList) {
            	obj.setStatus(VOStatus.UPDATED);
//                // 从InvocationInfoProxy获取值
//                dictType.setModifier(InvocationInfoProxy.getUsername());
//                dictType.setModifierId(InvocationInfoProxy.getUserid());
//                dictType.setModifyTime(new Date());
            }
            dao.update(updateList) ;
        }
        if (CollectionUtils.isNotEmpty(removeList)) {
        	 for (Currtype obj : addList) {
             	obj.setStatus(VOStatus.DELETED);
             }
        	 dao.remove(removeList);
        }
        
    }

    /**
     * 批量删除
     * @param list
     */
    public void batchDeleteByPrimaryKey(List<Currtype> list) {
        if (CollectionUtils.isEmpty(list)) {
            throw new WebRuntimeException("当前没有选中数据!");
        }
        dao.remove(list);
    }
    

}
