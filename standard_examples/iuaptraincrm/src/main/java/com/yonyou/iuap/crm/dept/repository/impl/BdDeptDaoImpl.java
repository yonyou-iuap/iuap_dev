package com.yonyou.iuap.crm.dept.repository.impl;

import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.yonyou.iuap.crm.corp.entity.BdCorpVO;
import com.yonyou.iuap.crm.dept.entity.BdDeptVO;
import com.yonyou.iuap.crm.dept.repository.itf.IBdDeptDao;
import com.yonyou.iuap.persistence.bs.dao.BaseDAO;
import com.yonyou.iuap.persistence.bs.dao.DAOException;
import com.yonyou.iuap.persistence.jdbc.framework.SQLParameter;
import com.yonyou.iuap.persistence.jdbc.framework.processor.BeanListProcessor;
import com.yonyou.iuap.persistence.jdbc.framework.processor.BeanProcessor;
import com.yonyou.iuap.persistence.jdbc.framework.processor.MapListProcessor;
import com.yonyou.iuap.persistence.jdbc.framework.processor.MapProcessor;

//用于标注数据访问组件，即DAO组件
@Repository
public class BdDeptDaoImpl implements IBdDeptDao {

	@Autowired
	@Qualifier("baseDAO") private BaseDAO baseDao;

	@Override
	public BdDeptVO getBdDept(String pk_dept) throws DAOException {
		String sql = "select * from bd_dept where pk_dept=? and dr=0";
		SQLParameter sqlParameter = new SQLParameter();
		sqlParameter.addParam(pk_dept);
		BdDeptVO vo = baseDao.queryForObject(sql, sqlParameter, new BeanProcessor(BdDeptVO.class));
		return vo;
	}
	
	@Override
	public List<BdDeptVO> getBdDeptnum(String wherestr) throws DAOException {
		String sql = "select * from bd_dept where dr=0 "+wherestr+" order by ts desc";
		return baseDao.queryByClause(BdDeptVO.class, sql);
	}
	@Override
	public List<BdDeptVO> getBdDeptcorp(String pk_corp) throws DAOException {
		String sql = "select * from bd_dept where dr=0 and pk_corp='"+pk_corp+"' order by ts desc";
		return baseDao.queryByClause(BdDeptVO.class, sql);
	}
	
	@Override
	public List<HashMap> getBdDeptsListMap(String wherestr)
			throws DAOException {
		String sql = "select * from bd_dept where dr=0 "+wherestr+" order by ts desc";		
		List<HashMap> listmap = baseDao.queryForList(sql,new MapListProcessor());
		return listmap;
	}
	@Override
	public List<HashMap> getBdDeptsListMapbyc(String wherestr, String pk_corp) throws DAOException {
		String sql = "select * from bd_dept where dr=0 "+wherestr+" and pk_corp= '"+pk_corp+"' order by ts desc";		
		List<HashMap> listmap = baseDao.queryForList(sql,new MapListProcessor());
		return listmap;
	}
	
	@Override
	public List<BdDeptVO> getBdDepts(String wherestr) throws DAOException {
		String sql = "select * from bd_dept where dr=0 "+wherestr+" order by ts desc";
		return baseDao.queryByClause(BdDeptVO.class, sql);
	}
	
	@Override
	public List<BdDeptVO> getBdDeptsbyc(String wherestr,String pk_corp) throws DAOException {
		String sql = "select * from bd_dept where dr=0 "+wherestr+" and pk_corp= '"+pk_corp+"' order by ts desc";		
		return baseDao.queryByClause(BdDeptVO.class, sql);
	}
	
	@Override
	public Page<BdDeptVO> getBdDeptsBypage(String condition, SQLParameter sqlParameter, PageRequest pageRequest) throws DAOException{
		if(sqlParameter.getCountParams()==0)
			sqlParameter = null;
		StringBuffer sqlBuffer = new StringBuffer("select * from bd_dept where dr=0 ");
		sqlBuffer.append(condition);
		sqlBuffer.append(" order by ts desc");
		//new PageRequest(0,10, new Sort(Sort.Direction.ASC, "ipuquotaionid", "ts");
		return baseDao.queryPage(sqlBuffer.toString(), sqlParameter, pageRequest, BdDeptVO.class);
	}

	@Override
	@Transactional
	public void updateBdDept(BdDeptVO entity) throws DAOException {
		Date date = new Date(System.currentTimeMillis());
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String time = df.format(date);
//        entity.setTs(time);
		int result = baseDao.update(entity);
	}

	@Override
	@Transactional
	public void updateBdDeptByCondition(String sql) throws DAOException {
		Date date = new Date(System.currentTimeMillis());
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String time = df.format(date);
		int result = baseDao.update(sql+",ts='"+time+"'");
	}
	
	@Override
	@Transactional
	public String saveBdDept(BdDeptVO entity) throws DAOException {
		Date date = new Date(System.currentTimeMillis());
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String time = df.format(date);
//        entity.setTs(time);
		return baseDao.insert(entity);
	}
	
	@Override
	@Transactional
	public String saveBdDeptWithPK(BdDeptVO entity) throws DAOException {
//		Date date = new Date(System.currentTimeMillis());
//        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//        String time = df.format(date);
//        entity.setTs(time);
		return baseDao.insertWithPK(entity);
	}

	@Override
	@Transactional
	public void deleteBdDept(BdDeptVO entity) throws DAOException {
		baseDao.remove(entity);
	}
	
	@Override
	@Transactional
	public void deleteBdDeptById(String pk_dept) throws DAOException {
		BdDeptVO entity = new BdDeptVO();
		entity.setPk_dept(pk_dept);
		baseDao.remove(entity);
	}
	
	/**
	 * 逻辑删除，非物理删除
	 */
	@Override
	@Transactional
	public void deleteBdDeptByIdTS(String pk_dept) throws DAOException {
		String sql = "update bd_dept set dr=1 where pk_dept='" +pk_dept+ "'";
		int result = baseDao.update(sql);
	}
	
	@Override
	public String queryDeptByDept(String pk_dept) throws DAOException {
		String sql = "select top 1 pk_dept from ieop_user where pk_dept='"+pk_dept+"' ";
	    HashMap map = 	baseDao.queryForObject(sql, new MapProcessor());
	    if(null!=map && null!=map.get("pk_dept") && !"".equals(map.get("pk_dept"))){
	    	return map.get("pk_dept").toString();     //部门下用户
	    	}
		return null;
	}
	@Override
	public List<BdDeptVO> getBdDeptByCodeAndName(String deptcode, String deptname)
			throws DAOException {
		String sql = "select * from bd_dept where (deptcode=? or deptname=?) and dr=0";
		SQLParameter sqlParameter = new SQLParameter();
		sqlParameter.addParam(deptcode);
		sqlParameter.addParam(deptname);
		List<BdDeptVO> vo = baseDao.queryForObject(sql, sqlParameter, new BeanListProcessor(BdDeptVO.class));
		return vo;
	}
	
	@Override
	public void stopDept(String pk_dept) throws DAOException {
		String sql = "update bd_dept set canceled=0 where pk_dept='" +pk_dept+ "'";
		int result = baseDao.update(sql);
	}

	@Override
	public void startDept(String pk_dept) throws DAOException {
		String sql = "update bd_dept set canceled=1 where pk_dept='" +pk_dept+ "'";
		int result = baseDao.update(sql);
	}

	
	@Override
	public Page<BdDeptVO> getBdDept(String condition, SQLParameter sqlParameter, PageRequest pageRequest) throws DAOException{
		if(sqlParameter.getCountParams()==0)
			sqlParameter = null;
		StringBuffer sqlBuffer = new StringBuffer("select * from bd_dept where dr=0 ");
		sqlBuffer.append(condition);
		sqlBuffer.append(" order by ts desc");
		//new PageRequest(0,10, new Sort(Sort.Direction.ASC, "ipuquotaionid", "ts");
		return baseDao.queryPage(sqlBuffer.toString(), sqlParameter, pageRequest, BdDeptVO.class);
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

}
