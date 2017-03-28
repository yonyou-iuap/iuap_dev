package com.yonyou.iuap.crm.saleorder.dao;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.yonyou.iuap.crm.common.base.repository.AppBaseDao;
import com.yonyou.iuap.crm.saleorder.entity.Orderdetail;
import com.yonyou.iuap.crm.saleorder.entity.Receiveinfo;
import com.yonyou.iuap.crm.saleorder.entity.Saleorder;
import com.yonyou.iuap.persistence.bs.dao.DAOException;
import com.yonyou.iuap.persistence.bs.dao.MetadataDAO;
import com.yonyou.iuap.persistence.jdbc.framework.SQLParameter;
import com.yonyou.iuap.persistence.jdbc.framework.util.FastBeanHelper;
import com.yonyou.iuap.persistence.jdbc.framework.util.SQLHelper;
import com.yonyou.iuap.persistence.vo.pub.VOStatus;


@Repository
public class SaleorderDao {

    @Autowired
    private MetadataDAO dao;
    
    @Autowired
	private AppBaseDao baseDao;

	@Transactional
    public Saleorder save(Saleorder entity) {
    	if(entity.getPk_projectapp() ==null ){
    		 entity.setStatus(VOStatus.NEW);
             entity.setDr(0);// 未删除标识         
    	}else{
    		entity.setStatus(VOStatus.UPDATED);
    	}
    	
    	List<Orderdetail> children = new ArrayList<Orderdetail>();
		if(entity.getId_orderdetail()!=null && entity.getId_orderdetail().size()>0){
    		for(Orderdetail child : entity.getId_orderdetail() ){
    			if(child.getPk_orderdetail() == null ){
    				child.setStatus(VOStatus.NEW);
    				child.setDr(entity.getDr());
    			}else{
    				child.setStatus(VOStatus.UPDATED);
    			}
    			
    			children.add(child);
    		}   		
    	}
		List<Receiveinfo> infochildren = new ArrayList<Receiveinfo>();
		if(entity.getId_receiveinfo()!=null && entity.getId_receiveinfo().size()>0){
    		for(Receiveinfo child : entity.getId_receiveinfo() ){
    			if(child.getPk_receiveinfo() == null ){
    				child.setStatus(VOStatus.NEW);
    				child.setDr(entity.getDr());
    			}else{
    				child.setStatus(VOStatus.UPDATED);
    			}
    			infochildren.add(child);
    		}
    	}			
		baseDao.mergeWithChild(entity, children,infochildren);
		return entity ;
    }


    public int delete(Saleorder entity) throws Exception {

        if (null == entity) {
            return 0;
        }
        dao.remove(entity);
        return 1;
    }

    public Page<Saleorder> selectAllByPage(PageRequest pageRequest, Map<String, Object> searchParams) throws DAOException {

        String sql = " select * from train_saleorder"; // user_name = ?
        SQLParameter sqlparam = new SQLParameter();

     	if (null != searchParams && !searchParams.isEmpty()) {
            sql = sql + " where ";
            for (String key : searchParams.keySet()) {
                sql = sql + FastBeanHelper.getColumn(Saleorder.class, key) + " like ? AND ";
                sqlparam.addParam("%" + searchParams.get(key) + "%");
            }
            sql = sql.substring(0, sql.length() - 4);
        }
        return dao.queryPage(sql, sqlparam, pageRequest, Saleorder.class);
    }

    public void batchDelete(List<Saleorder> list) throws DAOException {

        dao.remove(list);
    }

    public void batchDelChild(List<Saleorder> list) throws DAOException {
        SQLParameter sqlparam = new SQLParameter();
		String deleteSQL = SQLHelper.createDeleteIn("train_orderdetail", "fk_id_orderdetail", list.size());
        for (Saleorder item : list) {
            sqlparam.addParam(item.getPk_projectapp());
        }
        dao.update(deleteSQL, sqlparam);
    }     
  
	public List<Saleorder> findByProjectcode(String projectcode) throws DAOException {

        String sql = "SELECT * FROM train_saleorder where projectcode=?";
        SQLParameter sqlparam = new SQLParameter();
        sqlparam.addParam(projectcode);
        return  dao.queryByClause(Saleorder.class, sql, sqlparam);
    }
    
    public List<Saleorder> findByProjectcode(String projectcode, String id) {
        String sql = " SELECT * FROM train_saleorder where projectcode=? AND pk_projectapp !=?";
        SQLParameter sqlparam = new SQLParameter();
        sqlparam.addParam(projectcode);
        sqlparam.addParam(id);
        return  dao.queryByClause(Saleorder.class, sql, sqlparam);
    }

	public List<com.yonyou.iuap.crm.corp.entity.BdCorpVO> findAllBdCorpVO() {
        return dao.queryAll(com.yonyou.iuap.crm.corp.entity.BdCorpVO.class);
    }
    
	public List<com.yonyou.iuap.crm.basedata.entity.TmCustomerinfoVO> findAllTmCustomerinfoVO() {
        return dao.queryAll(com.yonyou.iuap.crm.basedata.entity.TmCustomerinfoVO.class);
    }
    
	public List<com.yonyou.iuap.crm.psn.entity.BdPsndocVO> findAllBdPsndocVO() {
        return dao.queryAll(com.yonyou.iuap.crm.psn.entity.BdPsndocVO.class);
    }
    
	public List<com.yonyou.iuap.crm.currtype.entity.Currtype> findAllCurrtype() {
        return dao.queryAll(com.yonyou.iuap.crm.currtype.entity.Currtype.class);
    }
    
}
