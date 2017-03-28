package com.yonyou.iuap.crm.corp.repository.impl;

import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Repository;

import com.yonyou.iuap.crm.corp.entity.BdCorpVO;
import com.yonyou.iuap.crm.corp.repository.itf.IBdCorpDao;
import com.yonyou.iuap.persistence.bs.dao.BaseDAO;
import com.yonyou.iuap.persistence.bs.dao.DAOException;
import com.yonyou.iuap.persistence.jdbc.framework.SQLParameter;
import com.yonyou.iuap.persistence.jdbc.framework.processor.BeanProcessor;
import com.yonyou.iuap.persistence.jdbc.framework.processor.MapListProcessor;
import com.yonyou.iuap.persistence.jdbc.framework.processor.MapProcessor;

//用于标注数据访问组件，即DAO组件
@Repository
public class BdCorpDaoImpl implements IBdCorpDao {

	@Autowired
	@Qualifier("baseDAO") private BaseDAO baseDao;

	@Override
	public BdCorpVO getBdCorp(String pk_corp) throws DAOException {
		String sql = "select * from bd_corp where pk_corp=? and dr=0";
		SQLParameter sqlParameter = new SQLParameter();
		sqlParameter.addParam(pk_corp);
		BdCorpVO vo = baseDao.queryForObject(sql, sqlParameter, new BeanProcessor(BdCorpVO.class));
		return vo;
	}

	@Override
	public List<BdCorpVO> getBdCorps(String wherestr) throws DAOException {
		String sql = "select * from bd_corp where dr=0 "+wherestr+" order by unitcode ";
		return baseDao.queryByClause(BdCorpVO.class, sql);
	}
		
	@Override
	public List<HashMap> getBdCorpsListMap(String wherestr)
			throws DAOException {
		String sql = "select * from bd_corp where dr=0 "+wherestr+" order by unitcode ";		
		List<HashMap> listmap = baseDao.queryForList(sql,new MapListProcessor());
		return listmap;
	}

	@Override
	public Page<BdCorpVO> getBdCorpsBypage(String condition, SQLParameter sqlParameter, PageRequest pageRequest) throws DAOException{
		if(sqlParameter.getCountParams()==0)
			sqlParameter = null;
		StringBuffer sqlBuffer = new StringBuffer("select * from bd_corp where dr=0 ");
		sqlBuffer.append(condition);
		sqlBuffer.append(" order by ts desc");
		//new PageRequest(0,10, new Sort(Sort.Direction.ASC, "ipuquotaionid", "ts");
		return baseDao.queryPage(sqlBuffer.toString(), sqlParameter, pageRequest, BdCorpVO.class);
	}

	@Override
	public void updateBdCorp(BdCorpVO entity) throws DAOException {
/*		DateTime date = new DateTime(System.currentTimeMillis());
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String time = df.format(date);*/
        //entity.setTs(time);
		int result = baseDao.update(entity);
	}

	@Override
	public void updateBdCorpByCondition(String sql) throws DAOException {
		Date date = new Date(System.currentTimeMillis());
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String time = df.format(date);
		int result = baseDao.update(sql+",ts='"+time+"'");
	}
	
	@Override
	public String saveBdCorp(BdCorpVO entity) throws DAOException {
		Date date = new Date(System.currentTimeMillis());
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String time = df.format(date);
//        entity.setTs(time);
		return baseDao.insert(entity);
	}
	
	@Override
	public String saveBdCorpWithPK(BdCorpVO entity) throws DAOException {
//		Date date = new Date(System.currentTimeMillis());
//        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//        String time = df.format(date);
//        entity.setTs(time);
		
		return baseDao.insertWithPK(entity);
	}

	@Override
	public void deleteBdCorp(BdCorpVO entity) throws DAOException {
		baseDao.remove(entity);
	}
	
	@Override
	public void deleteBdCorpById(String pk_corp) throws DAOException {
		BdCorpVO entity = new BdCorpVO();
		entity.setPk_corp(pk_corp);
		baseDao.remove(entity);
	}
	
	/**
	 * 逻辑删除，非物理删除
	 */
	@Override
	public void deleteBdCorpByIdTS(String pk_corp) throws DAOException {
		String sql = "update bd_corp set dr=1 where pk_corp='" +pk_corp+ "'";
		int result = baseDao.update(sql);
	}

	@Override
	public String queryDeptByCorp(String pk_corp) throws DAOException {
		String sql = "select top 1 pk_corp from bd_dept where pk_corp='"+pk_corp+"' ";
	    HashMap map = 	baseDao.queryForObject(sql, new MapProcessor());
	    if(null!=map && null!=map.get("pk_corp") && !"".equals(map.get("pk_corp"))){
	    	return map.get("pk_corp").toString();  // 查询是否存在组织的部门，如存在，是不能删除	    
	    	}
		return null;
	}

	@Override
	public void stopCorp(String pk_corp) throws DAOException {
		String sql = "update bd_corp set isseal=2 where pk_corp='" +pk_corp+ "'";
		int result = baseDao.update(sql);
	}

	@Override
	public void startCorp(String pk_corp,String def1) throws DAOException {
		String sql = "update bd_corp set isseal=1,def1='"+def1+"' where pk_corp='" +pk_corp+ "'";
		int result = baseDao.update(sql);
	}
}
