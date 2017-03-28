package com.yonyou.iuap.crm.saleorder.service;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.yonyou.iuap.crm.saleorder.dao.SaleReceiveinfoDao;
import com.yonyou.iuap.crm.saleorder.entity.Receiveinfo;
import com.yonyou.iuap.persistence.bs.jdbc.meta.access.DASFacade;

@Service
public class OrderReceiveinfoService {

	@Autowired
	private SaleReceiveinfoDao childDao;

	/**
	 * 需要处理searchParams
	 * @param searchParams
	 * @param pageRequest
	 * @return
	 */
	public Page<Receiveinfo> selectAllByPage(Map<String, Object> searchParams,
			PageRequest pageRequest) {		
		Page<Receiveinfo> pageResult = childDao.selectAllByPage(pageRequest,
				searchParams);
		this.setRefName(pageResult);
		return pageResult;
	}
	
    /**参照id和显示字段 这里进行转换 */
	private Page<Receiveinfo> setRefName(Page<Receiveinfo> pageResult){
		if(pageResult!=null && pageResult.getContent()!=null &&pageResult.getContent().size()>0){
			/**
			 * 下面的 xx.xxx, xx表示参照对应的外键属性名， xxx是参照实体对应的属性名，
			 * */	 
			Map<String, Map<String, Object>> refMap =
                    DASFacade.getAttributeValueAsPKMap(new String[] {
//                    		"csendstockorgvid.unitname", 
                    		}, pageResult
                            .getContent().toArray(new Receiveinfo[] {}));
            for (Receiveinfo item : pageResult.getContent()) {
                String id = item.getPk_receiveinfo();
                Map<String, Object> itemRefMap = refMap.get(id);
                if (itemRefMap != null) {
//                    item.setPk_org_name((String) itemRefMap.get("csendstordocid.unitname"));
                }
            }
		}
		return pageResult ;
	}

	@Transactional
	public void deleteEntity(Receiveinfo entity) {
		childDao.delete(entity);
	}

}
