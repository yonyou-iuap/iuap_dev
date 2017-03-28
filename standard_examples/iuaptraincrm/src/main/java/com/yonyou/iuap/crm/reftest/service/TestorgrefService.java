package com.yonyou.iuap.crm.reftest.service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import com.yonyou.iuap.crm.reftest.entity.Testorgref;
import com.yonyou.iuap.crm.reftest.dao.TestorgrefDao;
import com.yonyou.iuap.mvc.type.SearchParams;
import com.yonyou.iuap.persistence.bs.jdbc.meta.access.DASFacade;

/**
 * <p>Title: CardTableMetaService</p>
 * <p>Description: </p>
 */
@Service
public class TestorgrefService {
	
    @Autowired
    private TestorgrefDao dao;
    
    /**
     * Description:通过非主键字段查询
     * List<CardTable>
     * @param str
     */
    public List<Testorgref> findByName(String code) {
        return dao.findByName(code);
    }
    public List<Testorgref> findByCity(String code) {
        return dao.findByCity(code);
    }
    
    /**参照id和显示字段 这里进行转换 */
	private Page<Testorgref> setRefName(Page<Testorgref> pageResult){
		if(pageResult!=null && pageResult.getContent()!=null &&pageResult.getContent().size()>0){
			/**
			 * 下面的 xx.xxx, xx表示参照对应的外键属性名， xxx是参照实体对应的属性名，
			 * */	 
			Map<String, Map<String, Object>> refMap =
                    DASFacade.getAttributeValueAsPKMap(new String[] {
                    		"org.unitname", 
                    		"pk_user.name", 
                    		"pk_psn.psnname", 
                    		}, pageResult
                            .getContent().toArray(new Testorgref[] {}));
            for (Testorgref item : pageResult.getContent()) {
                String id = item.getPk_test();
                Map<String, Object> itemRefMap = refMap.get(id);
                if (itemRefMap != null) {
                    item.setOrg_name((String) itemRefMap.get("org.unitname"));
                    item.setPk_user_name((String) itemRefMap.get("pk_user.name"));
                    item.setPk_psn_name((String) itemRefMap.get("pk_psn.psnname"));
                }
            }
		}
		return pageResult ;
	}
    
    public Page<Testorgref> selectAllByPage(PageRequest pageRequest, SearchParams searchParams) {
        Page<Testorgref> pageResult = dao.selectAllByPage(pageRequest, searchParams.getSearchMap()) ;
		this.setRefName(pageResult);
		return pageResult;
    }
    
    public void save(List<Testorgref> recordList) {
        List<Testorgref> addList = new ArrayList<>(recordList.size());
        List<Testorgref> updateList = new ArrayList<>(recordList.size());
        for (Testorgref meta : recordList) {
        	if (meta.getPk_test() == null) {
            	meta.setPk_test(UUID.randomUUID().toString());
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
    
    public void batchDeleteByPrimaryKey(List<Testorgref> list) {
    	dao.batchDelete(list);
    }
    
	public List<com.yonyou.iuap.crm.corp.entity.BdCorpVO> findAllBdCorpVO() {
        return dao.findAllBdCorpVO();
    }
    
	public List<com.yonyou.iuap.crm.user.entity.ExtIeopUserVO> findAllExtIeopUserVO() {
        return dao.findAllExtIeopUserVO();
    }
    
	public List<com.yonyou.iuap.crm.psn.entity.BdPsndocVO> findAllBdPsndocVO() {
        return dao.findAllBdPsndocVO();
    }
    
}
