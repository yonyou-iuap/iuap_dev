package com.yonyou.iuap.crm.test.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.yonyou.iuap.persistence.bs.dao.DAOException;
import com.yonyou.iuap.persistence.bs.jdbc.meta.model.MDEnum;
import com.yonyou.iuap.persistence.bs.jdbc.meta.access.DASFacade;

import com.yonyou.iuap.crm.test.entity.Test;
import com.yonyou.iuap.crm.test.dao.TestDao;
import com.yonyou.iuap.persistence.bs.dao.DAOException;
import com.yonyou.iuap.persistence.bs.dao.MetadataDAO;
import com.yonyou.iuap.persistence.jdbc.framework.SQLParameter;
import com.yonyou.iuap.persistence.jdbc.framework.util.FastBeanHelper;
import com.yonyou.iuap.persistence.vo.pub.util.StringUtil;

@Service
public class TestService {
    private Logger logger = LoggerFactory.getLogger(TestService.class);

    @Autowired
    private TestDao dao;

	@Autowired
    private TestBService childService;
    
    @Transactional
    public Test save(Test entity) {
    	logger.debug("execute  Test save .");
        return	dao.save(entity) ;
    }

    /**
     * 批量删除数据
     * 
     * @param list
     */
    @Transactional
    public void batchDeleteEntity(List<Test> list) {
        this.batchDelChild(list);
        dao.batchDelete(list);
    }

    /**
     * 删除主表下面的子表数据
     * 
     * @param list
     * @throws DAOException
     */
    private void batchDelChild(List<Test> list) throws DAOException {
        dao.batchDelChild(list);
    }

    /**参照id和显示字段 这里进行转换 */
	private Page<Test> setRefName(Page<Test> pageResult){
		if(pageResult!=null && pageResult.getContent()!=null &&pageResult.getContent().size()>0){
			/**
			 * 下面的 xx.xxx, xx表示参照对应的外键属性名， xxx是参照实体对应的属性名，
			 * */	 
			Map<String, Map<String, Object>> refMap =
                    DASFacade.getAttributeValueAsPKMap(new String[] {
                    		"currtype.name", 
                    		}, pageResult
                            .getContent().toArray(new Test[] {}));
            for (Test item : pageResult.getContent()) {
                String id = item.getId();
                Map<String, Object> itemRefMap = refMap.get(id);
                if (itemRefMap != null) {
                    item.setCurrtype_name((String) itemRefMap.get("currtype.name"));
                }
            }
		}
		return pageResult ;
	}
    
    /**
     * 根据传递的参数，进行分页查询
     */
    public Page<Test> selectAllByPage(Map<String, Object> searchParams, PageRequest pageRequest) {
      	Page<Test> pageResult = dao.selectAllByPage(pageRequest, searchParams) ;
		this.setRefName(pageResult);
		return pageResult;
    }


     
 
	public List<com.yonyou.iuap.crm.currtype.entity.Currtype> findAllCurrtype() {
        return dao.findAllCurrtype();
    }
    
    
}
