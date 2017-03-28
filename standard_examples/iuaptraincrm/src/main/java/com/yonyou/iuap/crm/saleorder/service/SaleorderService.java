package com.yonyou.iuap.crm.saleorder.service;

import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.yonyou.iuap.crm.saleorder.dao.SaleorderDao;
import com.yonyou.iuap.crm.saleorder.entity.Saleorder;
import com.yonyou.iuap.persistence.bs.dao.DAOException;
import com.yonyou.iuap.persistence.bs.jdbc.meta.access.DASFacade;

@Service
public class SaleorderService {
    private Logger logger = LoggerFactory.getLogger(SaleorderService.class);

    @Autowired
    private SaleorderDao dao;

	@Autowired
    private OrderdetailService childService;
	
	@Autowired
	private SaleOrderValidator validator;
    
    @Transactional
    public Saleorder save(Saleorder entity) {
    	logger.debug("execute  Saleorder save .");
    	validator.validate(entity);
        return	dao.save(entity) ;
    }

    /**
     * 批量删除数据
     * 
     * @param list
     */
    @Transactional
    public void batchDeleteEntity(List<Saleorder> list) {
        this.batchDelChild(list);
        dao.batchDelete(list);
    }

    /**
     * 删除主表下面的子表数据
     * 
     * @param list
     * @throws DAOException
     */
    private void batchDelChild(List<Saleorder> list) throws DAOException {
        dao.batchDelChild(list);
    }

    /**参照id和显示字段 这里进行转换 */
	private Page<Saleorder> setRefName(Page<Saleorder> pageResult){
		if(pageResult!=null && pageResult.getContent()!=null &&pageResult.getContent().size()>0){
			/**
			 * 下面的 xx.xxx, xx表示参照对应的外键属性名， xxx是参照实体对应的属性名，
			 * */	 
			Map<String, Map<String, Object>> refMap =
                    DASFacade.getAttributeValueAsPKMap(new String[] {
                    		"pk_org.unitname", 
                    		"ccustomerid.vcustomername", 
                    		"cemployeeid.psnname", 
                    		"corigcurrencyid.name", 
                    		}, pageResult
                            .getContent().toArray(new Saleorder[] {}));
            for (Saleorder item : pageResult.getContent()) {
                String id = item.getPk_projectapp();
                Map<String, Object> itemRefMap = refMap.get(id);
                if (itemRefMap != null) {
                    item.setPk_org_name((String) itemRefMap.get("pk_org.unitname"));
                    item.setCcustomerid_name((String) itemRefMap.get("ccustomerid.vcustomername"));
                    item.setCemployeeid_name((String) itemRefMap.get("cemployeeid.psnname"));
                    item.setCorigcurrencyid_name((String) itemRefMap.get("corigcurrencyid.name"));
                }
            }
		}
		return pageResult ;
	}
    
    /**
     * 根据传递的参数，进行分页查询
     */
    public Page<Saleorder> selectAllByPage(Map<String, Object> searchParams, PageRequest pageRequest) {
      	Page<Saleorder> pageResult = dao.selectAllByPage(pageRequest, searchParams) ;
		this.setRefName(pageResult);
		return pageResult;
    }


     /**
     * 根据编码查询
     * 
     * @param code
     * @return
     */
    public List<Saleorder> findByProjectcode(String projectcode) {
        return dao.findByProjectcode(projectcode);
    }
    /**
     * 查询除某个id外，是否存在相同编码的 实体 ，用于更新实体时验证重复
     * 
     * @param code
     * @param id
     * @return
     */
    public List<Saleorder> findByProjectcode(String projectcode, String pk_projectapp) {
        return dao.findByProjectcode(projectcode, pk_projectapp);
    }
                                  
 
	public List<com.yonyou.iuap.crm.corp.entity.BdCorpVO> findAllBdCorpVO() {
        return dao.findAllBdCorpVO();
    }
    
	public List<com.yonyou.iuap.crm.basedata.entity.TmCustomerinfoVO> findAllTmCustomerinfoVO() {
        return dao.findAllTmCustomerinfoVO();
    }
    
	public List<com.yonyou.iuap.crm.psn.entity.BdPsndocVO> findAllBdPsndocVO() {
        return dao.findAllBdPsndocVO();
    }
    
	public List<com.yonyou.iuap.crm.currtype.entity.Currtype> findAllCurrtype() {
        return dao.findAllCurrtype();
    }
    
    
}
