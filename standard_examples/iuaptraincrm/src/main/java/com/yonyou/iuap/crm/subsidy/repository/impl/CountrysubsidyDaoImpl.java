package com.yonyou.iuap.crm.subsidy.repository.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Repository;

import com.yonyou.iuap.crm.common.base.repository.AppBaseDao;
import com.yonyou.iuap.crm.subsidy.entity.CountrysubsidyFundbackVO;
import com.yonyou.iuap.crm.subsidy.entity.CountrysubsidyItemsVO;
import com.yonyou.iuap.crm.subsidy.entity.CountrysubsidyVO;
import com.yonyou.iuap.crm.subsidy.repository.itf.ICountrysubsidyDao;
import com.yonyou.iuap.persistence.bs.dao.BaseDAO;
import com.yonyou.iuap.persistence.bs.dao.DAOException;
import com.yonyou.iuap.persistence.jdbc.framework.SQLParameter;
import com.yonyou.iuap.persistence.jdbc.framework.processor.BeanListProcessor;

/**
* <p>国补申请实现类：</p>
* @author 
* @created 2016年11月21日 上午11:27:54
* @version 1.0
 */
@Repository
public class CountrysubsidyDaoImpl implements ICountrysubsidyDao{
	
	@Autowired
	private AppBaseDao appBaseDao;
	@Autowired
	@Qualifier("baseDAO") private BaseDAO baseDao;
	
	/**
	 * 国补分页查询
	* @author 
	* @date 2016年11月21日
	* @param condition
	* @param sqlParameter
	* @param pageRequest
	* @return
	* @throws DAOException
	* (non-Javadoc)
	* @see com.yonyou.iuap.crm.subsidy.repository.itf.ICountrysubsidyDao#getCountrysubsidyBypages(java.lang.String, com.yonyou.iuap.persistence.jdbc.framework.SQLParameter, org.springframework.data.domain.PageRequest)
	 */
	@Override
	public Page<CountrysubsidyVO> getCountrysubsidyBypages(String condition,
			SQLParameter sqlParameter, PageRequest pageRequest)
			throws DAOException {
//		if(sqlParameter.getCountParams()==0)
//			sqlParameter = null;
		StringBuffer sqlBuffer = new StringBuffer("");
		
		sqlBuffer.append(" select a.pk_countrysubsidy,a.vbillno,a.countrysubsidyno,a.vdeclareno,a.dentrydate,c.deptname as vdeclaredept,d.login_name as vdeclarelegal,a.vgovernmentappno,e.name as vdeclarecity,a.visdatacomplete,a.visdataapprove, ");
		sqlBuffer.append(" b.unitname as pk_org,a.vstatus,f.login_name as creator,a.creationtime,g.login_name as modifier,a.modifiedtime,a.ts,a.dr ");
		sqlBuffer.append(" from ss_contrysubsidy a left join bd_corp b on a.pk_org=b.pk_corp left join bd_dept c on a.vdeclaredept=c.pk_dept left join ieop_user d on a.vdeclarelegal = d.id ");
		sqlBuffer.append(" left join tm_region e on a.vdeclarecity= e.pk_region  left join ieop_user f on a.creator = f.id left join ieop_user g on a.modifier = g.id  ");
		sqlBuffer.append(" where a.dr=0 ");
		if(condition!=null && !condition.isEmpty()){
			sqlBuffer.append(condition);
		}
		sqlBuffer.append(" order by a.ts desc");
		return baseDao.queryPage(sqlBuffer.toString(), sqlParameter, pageRequest, CountrysubsidyVO.class);
//		return appBaseDao.findBypage(CountrysubsidyVO.class, sqlBuffer.toString(), sqlParameter, pageRequest);
	}
	
	/**
	 * 根据查询条件查询国补申报信息
	* @author 
	* @date 2017年1月19日
	* @param condition
	* @param sqlParameter
	* @return
	* @throws DAOException
	* (non-Javadoc)
	* @see com.yonyou.iuap.crm.subsidy.repository.itf.ICountrysubsidyDao#getCountrysubsidyByList(java.lang.String, com.yonyou.iuap.persistence.jdbc.framework.SQLParameter)
	 */
	@Override
	public List<CountrysubsidyVO> getCountrysubsidyByList(String condition,
			SQLParameter sqlParameter) throws DAOException {
		StringBuffer sqlBuffer = new StringBuffer("");
		
		sqlBuffer.append(" select a.pk_countrysubsidy,a.vbillno,a.countrysubsidyno,a.vdeclareno,a.dentrydate,c.deptname as vdeclaredept,d.login_name as vdeclarelegal,a.vgovernmentappno,e.name as vdeclarecity,a.visdatacomplete,a.visdataapprove, ");
		sqlBuffer.append(" b.unitname as pk_org,a.vstatus,f.login_name as creator,a.creationtime,g.login_name as modifier,a.modifiedtime,a.ts,a.dr ");
		sqlBuffer.append(" from ss_contrysubsidy a left join bd_corp b on a.pk_org=b.pk_corp left join bd_dept c on a.vdeclaredept=c.pk_dept left join ieop_user d on a.vdeclarelegal = d.id ");
		sqlBuffer.append(" left join tm_region e on a.vdeclarecity= e.pk_region  left join ieop_user f on a.creator = f.id left join ieop_user g on a.modifier = g.id  ");
		sqlBuffer.append(" where a.dr=0 ");
		if(condition!=null && !condition.isEmpty()){
			sqlBuffer.append(condition);
		}
		sqlBuffer.append(" order by a.ts desc");
		return baseDao.queryForList(sqlBuffer.toString(), sqlParameter, new BeanListProcessor(CountrysubsidyVO.class));
	}

	/**
	 * 保存国补主表
	* @author 
	* @date 2016年11月21日
	* @param vo
	* @return
	* @throws DAOException
	* (non-Javadoc)
	* @see com.yonyou.iuap.crm.subsidy.repository.itf.ICountrysubsidyDao#save(com.yonyou.iuap.crm.subsidy.entity.CountrysubsidyVO)
	 */
	@Override
	public String save(CountrysubsidyVO vo) throws DAOException {
		return appBaseDao.saveWithPK(vo);
	}
	
	/**
	 * 保存国补明细表信息
	* @author name
	* @date 2016年11月21日
	* @param entitys
	* @throws DAOException
	* (non-Javadoc)
	* @see com.yonyou.iuap.crm.subsidy.repository.itf.ICountrysubsidyDao#saveItemBatch(java.util.List)
	 */
	@Override
	public void saveItemBatch(List<CountrysubsidyItemsVO> entitys)
			throws DAOException {
		appBaseDao.batchSaveWithPK(entitys);
	}

	/**
	 * 保存国补回款信息
	* @author 
	* @date 2016年11月21日
	* @param entitys
	* @throws DAOException
	* (non-Javadoc)
	* @see com.yonyou.iuap.crm.subsidy.repository.itf.ICountrysubsidyDao#saveItemFundbackBatch(java.util.List)
	 */
	@Override
	public void saveItemFundbackBatch(List<CountrysubsidyFundbackVO> entitys)
			throws DAOException {
		appBaseDao.batchSaveWithPK(entitys);
	}

	/**
	* 修改国补主表信息
	* @author 
	* @date 2016年11月21日
	* @param vo
	* @throws DAOException
	* (non-Javadoc)
	* @see com.yonyou.iuap.crm.subsidy.repository.itf.ICountrysubsidyDao#modify(com.yonyou.iuap.crm.subsidy.entity.CountrysubsidyVO)
	 */
	@Override
	public void modify(CountrysubsidyVO vo) throws DAOException {
		appBaseDao.update(vo);
	}

	/**
	 * 修改国补明细表数据
	* @author 
	* @date 2016年11月21日
	* @param entitys
	* @throws DAOException
	* (non-Javadoc)
	* @see com.yonyou.iuap.crm.subsidy.repository.itf.ICountrysubsidyDao#modifyItemBatch(java.util.List)
	 */
	@Override
	public void modifyItemBatch(List<CountrysubsidyItemsVO> entitys)
			throws DAOException {
		appBaseDao.batchUpdate(entitys);
	}

	/**
	 * 修改国补回款信息
	* @author 
	* @date 2016年11月21日
	* @param entitys
	* @throws DAOException
	* (non-Javadoc)
	* @see com.yonyou.iuap.crm.subsidy.repository.itf.ICountrysubsidyDao#modifyItemFundbackBatch(java.util.List)
	 */
	@Override
	public void modifyItemFundbackBatch(List<CountrysubsidyFundbackVO> entitys)
			throws DAOException {
		appBaseDao.batchUpdate(entitys);
	}

	/**
	 * 删除国补明细表信息
	* @author 
	* @date 2016年11月21日
	* @param entitys
	* @throws DAOException
	* (non-Javadoc)
	* @see com.yonyou.iuap.crm.subsidy.repository.itf.ICountrysubsidyDao#deleteItemBatch(java.util.List)
	 */
	@Override
	public void deleteItemBatch(List<CountrysubsidyItemsVO> entitys)
			throws DAOException {
		appBaseDao.batchDeleteWithDR(entitys);
	}

	/**
	 * 删除国补单据
	* @author 
	* @date 2016年11月21日
	* @param vo
	* @throws DAOException
	* (non-Javadoc)
	* @see com.yonyou.iuap.crm.subsidy.repository.itf.ICountrysubsidyDao#delete(com.yonyou.iuap.crm.subsidy.entity.CountrysubsidyVO)
	 */
	@Override
	public void delete(CountrysubsidyVO vo) throws DAOException {
		//删除明细表信息
		appBaseDao.batchDeleteWithDR(queryItem(vo.getPk_countrysubsidy()));
		//删除回款信息
		appBaseDao.batchDeleteWithDR(queryFundback(vo.getPk_countrysubsidy()));
		//删除主表信息
		appBaseDao.deleteWithDR(vo);
	}

	/**
	 * 根据外键查询国补明细信息
	* @author 
	* @date 2016年11月21日
	* @param fk
	* @return
	* @throws DAOException
	* (non-Javadoc)
	* @see com.yonyou.iuap.crm.subsidy.repository.itf.ICountrysubsidyDao#queryItem(java.lang.String)
	 */
	@Override
	public List<CountrysubsidyItemsVO> queryItem(String fk) throws DAOException {
		List<CountrysubsidyItemsVO> queryList = null;
		if(null != fk && fk.trim().length()>0){
			queryList = appBaseDao.findByFKWithDR(CountrysubsidyItemsVO.class, CountrysubsidyVO.class, fk);
		}
		return queryList;
	}

	/**
	 * 根据外键查询国补回款信息
	* @author 
	* @date 2016年11月21日
	* @param fk
	* @return
	* @throws DAOException
	* (non-Javadoc)
	* @see com.yonyou.iuap.crm.subsidy.repository.itf.ICountrysubsidyDao#queryFundback(java.lang.String)
	 */
	@Override
	public List<CountrysubsidyFundbackVO> queryFundback(String fk)
			throws DAOException {
		List<CountrysubsidyFundbackVO> queryList = null;
		if(null != fk && fk.trim().length()>0){
			queryList = appBaseDao.findByFKWithDR(CountrysubsidyFundbackVO.class, CountrysubsidyVO.class, fk);
		}
		return queryList;
	}

	/**
	 * 根据主键查询主表信息你
	* @author 
	* @date 2016年11月21日
	* @param pk
	* @return
	* @throws DAOException
	* (non-Javadoc)
	* @see com.yonyou.iuap.crm.subsidy.repository.itf.ICountrysubsidyDao#queryMain(java.lang.String)
	 */
	@Override
	public CountrysubsidyVO queryMain(String pk) throws DAOException {
		return appBaseDao.findByIdWithDR(CountrysubsidyVO.class, pk);
	}

	@SuppressWarnings("deprecation")
	@Override
	public void deleteItemWithPk(String pk) throws DAOException {
		appBaseDao.deleteWithDR(CountrysubsidyItemsVO.class, pk);
	}

	/**
	 * 查询国补明细-分页
	* @author name
	* @date 2016年11月28日
	* @param condition
	* @param sqlParameter
	* @param pageRequest
	* @return
	* @throws DAOException
	* (non-Javadoc)
	* @see com.yonyou.iuap.crm.subsidy.repository.itf.ICountrysubsidyDao#getCountrysubsidyItemBypages(java.lang.String, com.yonyou.iuap.persistence.jdbc.framework.SQLParameter, org.springframework.data.domain.PageRequest)
	 */
	@Override
	public Page<CountrysubsidyItemsVO> getCountrysubsidyItemBypages(
			String condition, SQLParameter sqlParameter, PageRequest pageRequest)
			throws DAOException {
		StringBuffer sqlBuffer = new StringBuffer("");
		sqlBuffer.append(" select a.pk_countrysubsidy_items,a.pk_countrysubsidy,a.viscommercial,a.vbuydomain,a.vbuycity,a.vrundept,a.vvehiclekind,a.vvehiclepurpose, ");
		sqlBuffer.append(" 	a.vvehiclemodel,a.vvin,a.vlicense,a.nsubsidystandard,a.ntotalback,a.nnotback,a.npurchaseprice,a.vinvoiceno,a.ninvoiceyear, ");
		sqlBuffer.append(" a.ninvoicemonth,a.ninvoiceday,a.ndlicenseyear,a.ndlicensemonth,a.ndlicenseday,a.dmustbackdate,a.ddeclaredate,a.vapprovepoint, ");
		sqlBuffer.append(" a.vnotdeclarestatus,a.vnotdeclarecomments,a.doverduedate,a.vdeclarestatus,f.login_name as creator,a.creationtime,g.login_name as modifier,a.modifiedtime,a.ts,a.dr ");
		sqlBuffer.append(" from ss_countrysubsidy_items a left join ieop_user f on a.creator = f.id left join ieop_user g on a.modifier = g.id  ");
		sqlBuffer.append(" where a.dr=0");
		if(condition!=null && !condition.isEmpty()){
			sqlBuffer.append(condition);
		}
		sqlBuffer.append(" order by a.ts desc");
		return baseDao.queryPage(sqlBuffer.toString(), sqlParameter, pageRequest, CountrysubsidyItemsVO.class);
	}

	/**
	 * 查询国补回款-分页
	* @author name
	* @date 2016年11月28日
	* @param condition
	* @param sqlParameter
	* @param pageRequest
	* @return
	* @throws DAOException
	* (non-Javadoc)
	* @see com.yonyou.iuap.crm.subsidy.repository.itf.ICountrysubsidyDao#getCountrysubsidyFundbackBypages(java.lang.String, com.yonyou.iuap.persistence.jdbc.framework.SQLParameter, org.springframework.data.domain.PageRequest)
	 */
	@Override
	public Page<CountrysubsidyFundbackVO> getCountrysubsidyFundbackBypages(
			String condition, SQLParameter sqlParameter, PageRequest pageRequest)
			throws DAOException {
		StringBuffer sqlBuffer = new StringBuffer("");
		sqlBuffer.append(" select a.pk_countrysubsidy_fundback,a.pk_countrysubsidy,a.vvin,a.vvehicleseries,a.vsaledept,a.nsubsidystandard, ");
		sqlBuffer.append(" a.dmustbackdate,a.nfactback,a.dfactbackdate,f.login_name as creator,a.creationtime,g.login_name as modifier,a.modifiedtime,a.ts,a.dr ");
		sqlBuffer.append(" from ss_countrysubsidy_fundback a left join ieop_user f on a.creator = f.id left join ieop_user g on a.modifier = g.id  where a.dr=0 ");
		if(condition!=null && !condition.isEmpty()){
			sqlBuffer.append(condition);
		}
		sqlBuffer.append(" order by a.ts desc");
		return baseDao.queryPage(sqlBuffer.toString(), sqlParameter, pageRequest, CountrysubsidyFundbackVO.class);
	}

	/**
	 * 批量删除国补申请单主表信息
	* @author name
	* @date 2016年11月28日
	* @param entitys
	* @throws DAOException
	* (non-Javadoc)
	* @see com.yonyou.iuap.crm.subsidy.repository.itf.ICountrysubsidyDao#deleteBatch(java.util.List)
	 */
	@Override
	public void deleteBatch(List<CountrysubsidyVO> entitys) throws DAOException {
		appBaseDao.batchDeleteWithDR(entitys);
	}

	/**
	 * 根据查询条件，查询车辆信息
	* @author 
	* @date 2016年11月30日
	* @param contiditon
	* @return
	* @throws DAOException
	* (non-Javadoc)
	* @see com.yonyou.iuap.crm.subsidy.repository.itf.ICountrysubsidyDao#queryItemWithContiditon(java.lang.String)
	 */
	@Override
	public List<CountrysubsidyItemsVO> queryItemWithContiditon(String contiditon)
			throws DAOException {
		StringBuffer sqlBuffer = new StringBuffer("dr=0 ");
		sqlBuffer.append(contiditon);
		return appBaseDao.findListByClause(CountrysubsidyItemsVO.class, sqlBuffer.toString());
	}

	/**
	 * 批量修改国补申报单
	* @author 
	* @date 2016年12月1日
	* @param entitys
	* @throws DAOException
	* (non-Javadoc)
	* @see com.yonyou.iuap.crm.subsidy.repository.itf.ICountrysubsidyDao#modifyBatch(java.util.List)
	 */
	@Override
	public void modifyBatch(List<CountrysubsidyVO> entitys) throws DAOException {
		appBaseDao.batchUpdate(entitys);
	}

	/**
	 * 根据外键删除国补申报车辆信息
	* @author 
	* @date 2016年12月2日
	* @param fk
	* @throws DAOException
	* (non-Javadoc)
	* @see com.yonyou.iuap.crm.subsidy.repository.itf.ICountrysubsidyDao#deleteItemWithFk(java.lang.String)
	 */
	@Override
	public void deleteItemWithFk(String fk) throws DAOException {
		appBaseDao.deleteByFKWithDR(CountrysubsidyItemsVO.class, CountrysubsidyVO.class, fk);
	}

	/**
	 * 根据外键删除国补申报车辆回款信息
	* @author 
	* @date 2016年12月2日
	* @param fk
	* @throws DAOException
	* (non-Javadoc)
	* @see com.yonyou.iuap.crm.subsidy.repository.itf.ICountrysubsidyDao#deleteFundbackWithFk(java.lang.String)
	 */
	@Override
	public void deleteFundbackWithFk(String fk) throws DAOException {
		appBaseDao.deleteByFKWithDR(CountrysubsidyFundbackVO.class, CountrysubsidyVO.class, fk);
	}

}
