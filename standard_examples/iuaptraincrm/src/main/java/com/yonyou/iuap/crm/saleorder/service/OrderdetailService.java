package com.yonyou.iuap.crm.saleorder.service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.yonyou.iuap.crm.saleorder.dao.OrderdetailDao;
import com.yonyou.iuap.crm.saleorder.dao.SaleorderDao;
import com.yonyou.iuap.crm.saleorder.entity.Orderdetail;
import com.yonyou.iuap.persistence.bs.jdbc.meta.access.DASFacade;


@Service
public class OrderdetailService {

    @Autowired
    private OrderdetailDao childDao;

    @Autowired
    private SaleorderDao dao;

    /**参照id和显示字段 这里进行转换 */
	private Page<Orderdetail> setRefName(Page<Orderdetail> pageResult){
		if(pageResult!=null && pageResult.getContent()!=null &&pageResult.getContent().size()>0){
			/**
			 * 下面的 xx.xxx, xx表示参照对应的外键属性名， xxx是参照实体对应的属性名，
			 * */	 
			Map<String, Map<String, Object>> refMap =
                    DASFacade.getAttributeValueAsPKMap(new String[] {
                    		"pk_org.unitname", 
                    		}, pageResult
                            .getContent().toArray(new Orderdetail[] {}));
            for (Orderdetail item : pageResult.getContent()) {
                String id = item.getPk_orderdetail();
                Map<String, Object> itemRefMap = refMap.get(id);
                if (itemRefMap != null) {
                    item.setPk_org_name((String) itemRefMap.get("pk_org.unitname"));
                }
            }
		}
		return pageResult ;
	}
    
    public Page<Orderdetail> selectAllByPage(Map<String, Object> searchParams, PageRequest pageRequest) {
		Page<Orderdetail> pageResult = childDao.selectAllByPage(pageRequest, searchParams) ;
		this.setRefName(pageResult);
		return pageResult;
	}

    @Transactional
    public void deleteEntity(Orderdetail entity) {
        childDao.delete(entity);
    }

	public List<com.yonyou.iuap.crm.corp.entity.BdCorpVO> findAllBdCorpVO() {
        return childDao.findAllBdCorpVO();
    }
    
}
