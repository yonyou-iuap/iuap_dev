package com.yonyou.iuap.crm.subsidy.service.impl;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.yonyou.iuap.persistence.bs.dao.DAOException;
import com.yonyou.iuap.persistence.jdbc.framework.SQLParameter;
import com.yonyou.iuap.crm.common.exception.AppBusinessException;
import com.yonyou.iuap.crm.common.utils.AppInvocationInfoProxy;
import com.yonyou.iuap.crm.common.utils.AppTools;
import com.yonyou.iuap.crm.common.utils.CommonUtils;
import com.yonyou.iuap.crm.common.utils.DictCode;
import com.yonyou.iuap.crm.subsidy.entity.CountrysubsidyFundbackVO;
import com.yonyou.iuap.crm.subsidy.entity.CountrysubsidyItemsVO;
import com.yonyou.iuap.crm.subsidy.entity.CountrysubsidyVO;
import com.yonyou.iuap.crm.subsidy.repository.itf.ICountrysubsidyDao;
import com.yonyou.iuap.crm.subsidy.service.itf.ICountrysubsidyService;
import com.yonyou.iuap.crm.system.service.itf.IDataRoleService;

/**
 * 国补申报实现类
* <p>description：</p>
* @author 
* @created 2016年11月21日 上午11:38:29
* @version 1.0
 */
@Service
public class CountrysubsidyServiceImpl implements ICountrysubsidyService{
	@Autowired
	private ICountrysubsidyDao subsidyDao;
	
	@Autowired
	private IDataRoleService dataRoleService;

	/**
	 * 查询国补申请列表
	* @author 
	* @date 2016年11月21日
	* @param searchParams
	* @param pageRequest
	* @return
	* @throws DAOException
	* (non-Javadoc)
	* @see com.yonyou.iuap.crm.subsidy.service.itf.ICountrysubsidyService#getCountrysubsidyBypage(java.util.Map, org.springframework.data.domain.PageRequest)
	 */
	@Override
	public Page<CountrysubsidyVO> getCountrysubsidyBypage(
			Map<String, Object> searchParams, PageRequest pageRequest)
			throws AppBusinessException {
		try{
			StringBuffer condition = new StringBuffer();
			
			StringBuffer conditionItem = new StringBuffer();
			SQLParameter sqlParameter = new SQLParameter();
			for(String key : searchParams.keySet()){
				String value = searchParams.get(key)==null?"":searchParams.get(key).toString();
				if(null != value && value.length()>0){
					//国补单号
					if(null != key && "vbillno".equals(key)){
						condition.append(" and a.vbillno like '%").append(value).append("%'");
					}
					//企业申报文号
					else if(null != key && "vdeclareno".equals(key)){
						condition.append(" and a.vdeclareno like '%").append(value).append("%'");
					}
					//申报城市
					else if(null != key && "vdeclarecity".equals(key)){
						condition.append(" and a.vdeclarecity like '%").append(value).append("%'");
					}
					//车辆识别代码
					else if(null != key && "vvin".equals(key)){
						conditionItem.append(" and vvin like '%").append(value).append("%'");
					}
					//申报日期开始日期
					else if(null != key && "dentrydate_start".equals(key)){
						condition.append(" and a.dentrydate >= ").append(value);
					}
					//申报日期结束日期
					else if(null != key && "dentrydate_end".equals(key)){
						condition.append(" and a.dentrydate <= ").append(value);
					}
					//车系
					else if(null != key && "vvehicleseries".equals(key)){
						conditionItem.append(" and vvehiclemodel in (select pk_model from tm_model where dr=0 and pk_series='").append(value).append("')");
					}
					//车辆运营单位
					else if(null != key && "vrundept".equals(key)){
						conditionItem.append(" and vrundept like '%").append(value).append("%'");
					}
					//超期回款日期
					else if(null != key && "doverduedate_start".equals(key)){
						conditionItem.append(" and doverduedate >= ").append(value);
					}
					//超期回款日期
					else if(null != key && "doverduedate_end".equals(key)){
						conditionItem.append(" and doverduedate <= ").append(value);
					}
					//国补申报状态
					else if(null != key && "vstatus".equals(key)){
						condition.append(" and a.vstatus = '").append(value).append("'");
					}
				} 
			}
			if(null != conditionItem && conditionItem.length()>0){
				condition.append(" and exists (select 1 from SS_COUNTRYSUBSIDY_ITEMS where dr=0 and pk_countrysubsidy=a.pk_countrysubsidy ")
				.append(conditionItem)
				.append(")");
			}
			//增加数据权限控制
			condition.append(dataRoleService.getRoleSql("a.pk_org", "a.vdeclaredept", "a.creator"));
			
			return subsidyDao.getCountrysubsidyBypages(condition.toString(), sqlParameter, pageRequest);
		}catch(DAOException e){
			//Dao层次的异常需要处理成业务异常报给controller
			throw new AppBusinessException(e.getMessage());
		}
	}

	/**
	 * 根据查询条件查询国补申报信息
	* @author 
	* @date 2017年1月19日
	* @param searchParams
	* @return
	* @throws AppBusinessException
	* (non-Javadoc)
	* @see com.yonyou.iuap.crm.subsidy.service.itf.ICountrysubsidyService#getCountrysubsidyByList(java.util.Map)
	 */
	@Override
	public List<CountrysubsidyVO> getCountrysubsidyByList(
			Map<String, Object> searchParams) throws AppBusinessException {
		try{
			StringBuffer condition = new StringBuffer();
			
			StringBuffer conditionItem = new StringBuffer();
			SQLParameter sqlParameter = new SQLParameter();
			for(String key : searchParams.keySet()){
				String value = searchParams.get(key)==null?"":searchParams.get(key).toString();
				if(null != value && value.length()>0){
					//国补单号
					if(null != key && "vbillno".equals(key)){
						condition.append(" and a.vbillno like '%").append(value).append("%'");
					}
					//企业申报文号
					else if(null != key && "vdeclareno".equals(key)){
						condition.append(" and a.vdeclareno like '%").append(value).append("%'");
					}
					//申报城市
					else if(null != key && "vdeclarecity".equals(key)){
						condition.append(" and a.vdeclarecity like '%").append(value).append("%'");
					}
					//车辆识别代码
					else if(null != key && "vvin".equals(key)){
						conditionItem.append(" and vvin like '%").append(value).append("%'");
					}
					//申报日期开始日期
					else if(null != key && "dentrydate_start".equals(key)){
						condition.append(" and a.dentrydate >= ").append(value);
					}
					//申报日期结束日期
					else if(null != key && "dentrydate_end".equals(key)){
						condition.append(" and a.dentrydate <= ").append(value);
					}
					//车系
					else if(null != key && "vvehicleseries".equals(key)){
						conditionItem.append(" and vvehiclemodel in (select pk_model from tm_model where dr=0 and pk_series='").append(value).append("')");
					}
					//车辆运营单位
					else if(null != key && "vrundept".equals(key)){
						conditionItem.append(" and vrundept like '%").append(value).append("%'");
					}
					//超期回款日期
					else if(null != key && "doverduedate_start".equals(key)){
						conditionItem.append(" and doverduedate >= ").append(value);
					}
					//超期回款日期
					else if(null != key && "doverduedate_end".equals(key)){
						conditionItem.append(" and doverduedate <= ").append(value);
					}
					//国补申报状态
					else if(null != key && "vstatus".equals(key)){
						condition.append(" and a.vstatus = '").append(value).append("'");
					}
				} 
			}
			if(null != conditionItem && conditionItem.length()>0){
				condition.append(" and exists (select 1 from SS_COUNTRYSUBSIDY_ITEMS where dr=0 and pk_countrysubsidy=a.pk_countrysubsidy ")
				.append(conditionItem)
				.append(")");
			}
			//增加数据权限控制
			condition.append(dataRoleService.getRoleSql("a.pk_org", "a.vdeclaredept", "a.creator"));
			
			return subsidyDao.getCountrysubsidyByList(condition.toString(), sqlParameter);
		}catch(DAOException e){
			//Dao层次的异常需要处理成业务异常报给controller
			throw new AppBusinessException(e.getMessage());
		}
	}
	/**
	 * 国补申报保存
	* @author 
	* @date 2016年11月21日
	* @param vo
	* @throws AppBusinessException
	* (non-Javadoc)
	* @see com.yonyou.iuap.crm.subsidy.service.itf.ICountrysubsidyService#saveCountrysbusidy(com.yonyou.iuap.crm.subsidy.entity.CountrysubsidyVO)
	 */
	
	@Override
	@Transactional
	public String saveCountrysbusidy(CountrysubsidyVO vo)
			throws AppBusinessException {
		try {
			subsidyDao.save(vo);
			/*
			List<CountrysubsidyItemsVO> itemVOs = vo.getCountrysubsidyItems();
			List<CountrysubsidyFundbackVO> fundbackVOs = vo.getCountrysubsidyFundback();
			if(null != itemVOs && itemVOs.size()>0){
				for(int i=0;i<itemVOs.size();i++){
					CountrysubsidyItemsVO itemVO = itemVOs.get(i);
					itemVO.setPk_countrysubsidy(vo.getPk_countrysubsidy());
				}
				subsidyDao.saveItemBatch(itemVOs);
			}
			if(null != fundbackVOs && fundbackVOs.size()>0){
				for(int i=0;i<fundbackVOs.size();i++){
					CountrysubsidyFundbackVO fundbackVO = fundbackVOs.get(i);
					fundbackVO.setPk_countrysubsidy(vo.getPk_countrysubsidy());
				}
				subsidyDao.saveItemFundbackBatch(fundbackVOs);
			}*/
			return vo.getPk_countrysubsidy();
		} catch (DAOException e) {
			//Dao层次的异常需要处理成业务异常报给controller
			throw new AppBusinessException(e.getMessage());
		}
		
	}

	/**
	 * 根据主键查询国补主表信息
	* @author 
	* @date 2016年11月21日
	* @param pk
	* @return
	* @throws AppBusinessException
	* (non-Javadoc)
	* @see com.yonyou.iuap.crm.subsidy.service.itf.ICountrysubsidyService#getCountrysubsidyWithPk(java.lang.String)
	 */
	@Override
	public CountrysubsidyVO getCountrysubsidyWithPk(String pk)
			throws AppBusinessException {
		try {
			return subsidyDao.queryMain(pk);
		} catch (DAOException e) {
			//Dao层次的异常需要处理成业务异常报给controller
			throw new AppBusinessException(e.getMessage());
		}
	}

	/**
	 * 修改国补申请单
	* @author 
	* @date 2016年11月21日
	* @param vo
	* @throws AppBusinessException
	* (non-Javadoc)
	* @see com.yonyou.iuap.crm.subsidy.service.itf.ICountrysubsidyService#updateCountrysbusidy(com.yonyou.iuap.crm.subsidy.entity.CountrysubsidyVO)
	 */
	@Override
	@Transactional
	public void updateCountrysbusidy(CountrysubsidyVO vo)
			throws AppBusinessException {
		try {
			//修改主表
			subsidyDao.modify(vo);
			List<CountrysubsidyItemsVO> itemVOs = vo.getCountrysubsidyItems();
			List<CountrysubsidyFundbackVO> fundbackVOs = vo.getCountrysubsidyFundback();
			/**
			 * 修改子表信息，有主键的进行修改，无主键的进行新增
			 */
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			List<CountrysubsidyItemsVO> itemAddVOs = new ArrayList<CountrysubsidyItemsVO>();
			List<CountrysubsidyItemsVO> itemUpdateVOs = new ArrayList<CountrysubsidyItemsVO>();
			if(null != itemVOs && itemVOs.size()>0){
				for(int i=0;i<itemVOs.size();i++){
					CountrysubsidyItemsVO tempVO = itemVOs.get(i);
					if(null != tempVO && (null == tempVO.getPk_countrysubsidy() 
											|| tempVO.getPk_countrysubsidy().length()==0)){
						tempVO.setPk_countrysubsidy_items(AppTools.generatePK());
						tempVO.setPk_countrysubsidy(vo.getPk_countrysubsidy());
						tempVO.setCreator(AppInvocationInfoProxy.getPk_User());
						tempVO.setCreationtime(sdf.format(new Date()));
						itemAddVOs.add(tempVO);
					}else{
						tempVO.setModifier(AppInvocationInfoProxy.getPk_User());
						tempVO.setModifiedtime(sdf.format(new Date()));
						itemUpdateVOs.add(tempVO);
					}
				}
				if(null != itemAddVOs && itemAddVOs.size()>0){
					subsidyDao.saveItemBatch(itemAddVOs);
				}
				if(null != itemUpdateVOs && itemUpdateVOs.size()>0){
					subsidyDao.modifyItemBatch(itemUpdateVOs);
				}
			}
			
			/**
			 * 修改子表回款信息
			 */
			List<CountrysubsidyFundbackVO> fundbackAddVOs = new ArrayList<CountrysubsidyFundbackVO>();
			List<CountrysubsidyFundbackVO> fundbackUpdateVOs = new ArrayList<CountrysubsidyFundbackVO>();
			if(null != fundbackVOs && fundbackVOs.size()>0){
				for(int i=0;i<fundbackVOs.size();i++){
					CountrysubsidyFundbackVO tempVO = fundbackVOs.get(i);
					if(null != tempVO && (null == tempVO.getPk_countrysubsidy() 
											|| tempVO.getPk_countrysubsidy().length()==0)){
						tempVO.setPk_countrysubsidy(vo.getPk_countrysubsidy());
						tempVO.setPk_countrysubsidy_fundback(AppTools.generatePK());
						tempVO.setCreator(AppInvocationInfoProxy.getPk_User());
						tempVO.setCreationtime(sdf.format(new Date()));
						fundbackAddVOs.add(tempVO);
					}else{
						tempVO.setModifier(AppInvocationInfoProxy.getPk_User());
						tempVO.setModifiedtime(sdf.format(new Date()));
						fundbackUpdateVOs.add(tempVO);
					}
				}
				if(null != fundbackAddVOs && fundbackAddVOs.size()>0){
					subsidyDao.saveItemFundbackBatch(fundbackAddVOs);
				}
				if(null != fundbackUpdateVOs && fundbackUpdateVOs.size()>0){
					subsidyDao.modifyItemFundbackBatch(fundbackUpdateVOs);
				}
			}
			
		} catch (DAOException e) {
			//Dao层次的异常需要处理成业务异常报给controller
			throw new AppBusinessException(e.getMessage());
		}
		
	}

	/**
	 * 删除国补申请单
	* @author 
	* @date 2016年11月21日
	* @param vo
	* @throws AppBusinessException
	* (non-Javadoc)
	* @see com.yonyou.iuap.crm.subsidy.service.itf.ICountrysubsidyService#deleteCountrysbusidy(com.yonyou.iuap.crm.subsidy.entity.CountrysubsidyVO)
	 */
	@Override
	@Transactional
	public void deleteCountrysbusidy(CountrysubsidyVO vo)
			throws AppBusinessException {
		try {
			subsidyDao.delete(vo);
		} catch (DAOException e) {
			//Dao层次的异常需要处理成业务异常报给controller
			throw new AppBusinessException(e.getMessage());
		}
	}

	/**
	 * 根据外键查询国补申报明细信息
	* @author name
	* @date 2016年11月23日
	* @param fk
	* @return
	* @throws AppBusinessException
	* (non-Javadoc)
	* @see com.yonyou.iuap.crm.subsidy.service.itf.ICountrysubsidyService#queryItemWithFk(java.lang.String)
	 */
	@Override
	public List<CountrysubsidyItemsVO> queryItemWithFk(String fk)
			throws AppBusinessException {
		try {
			return subsidyDao.queryItem(fk);
		} catch (DAOException e) {
			//Dao层次的异常需要处理成业务异常报给controller
			throw new AppBusinessException(e.getMessage());
		}
	}

	/**
	 * 根据外键查询国补申报回款信息
	* @author name
	* @date 2016年11月23日
	* @param fk
	* @return
	* @throws AppBusinessException
	* (non-Javadoc)
	* @see com.yonyou.iuap.crm.subsidy.service.itf.ICountrysubsidyService#queryFundbackWithFk(java.lang.String)
	 */
	@Override
	public List<CountrysubsidyFundbackVO> queryFundbackWithFk(String fk)
			throws AppBusinessException {
		try {
			return subsidyDao.queryFundback(fk);
		} catch (DAOException e) {
			//Dao层次的异常需要处理成业务异常报给controller
			throw new AppBusinessException(e.getMessage());
		}
	}

	/**
	 * 根据主键删除车辆信息
	* @author name
	* @date 2016年11月24日
	* @param pk
	* @throws AppBusinessException
	* (non-Javadoc)
	* @see com.yonyou.iuap.crm.subsidy.service.itf.ICountrysubsidyService#delItemWithPk(java.lang.String)
	 */
	@Override
	@Transactional
	public void delItemWithPk(String pk) throws AppBusinessException {
		try {
			subsidyDao.deleteItemWithPk(pk);
		} catch (DAOException e) {
			//Dao层次的异常需要处理成业务异常报给controller
			throw new AppBusinessException(e.getMessage());
		}
	}

	@Override
	@Transactional
	public void delBatchItem(List<CountrysubsidyItemsVO> entitys)
			throws AppBusinessException {
		try {
			subsidyDao.deleteItemBatch(entitys);
		} catch (DAOException e) {
			//Dao层次的异常需要处理成业务异常报给controller
			throw new AppBusinessException(e.getMessage());
		}
	}

	/**
	 * 查询国补明细-分页
	* @author name
	* @date 2016年11月28日
	* @param searchParams
	* @param pageRequest
	* @return
	* @throws AppBusinessException
	* (non-Javadoc)
	* @see com.yonyou.iuap.crm.subsidy.service.itf.ICountrysubsidyService#getCountrysubsidyItemBypage(java.util.Map, org.springframework.data.domain.PageRequest)
	 */
	@Override
	public Page<CountrysubsidyItemsVO> getCountrysubsidyItemBypage(
			Map<String, Object> searchParams, PageRequest pageRequest)
			throws AppBusinessException {
		try{
			StringBuffer condition = new StringBuffer();
			SQLParameter sqlParameter = new SQLParameter();
			for(String key : searchParams.keySet()){
				String value = searchParams.get(key)==null?"":searchParams.get(key).toString();
				if(null != value && value.length()>0){
					//外键
					if(null != key && "pk_countrysubsidy".equals(key)){
						condition.append(" and a.pk_countrysubsidy = '").append(value).append("'");
					}//外键
					else if(null != key && "vvin".equals(key)){
						condition.append(" and a.vvin like '%").append(value).append("%'");
					}
				}
			}
			return subsidyDao.getCountrysubsidyItemBypages(condition.toString(), sqlParameter, pageRequest);
		}catch(DAOException e){
			//Dao层次的异常需要处理成业务异常报给controller
			throw new AppBusinessException(e.getMessage());
		}
	}

	/**
	 * 查询国补回款-分页
	* @author name
	* @date 2016年11月28日
	* @param searchParams
	* @param pageRequest
	* @return
	* @throws AppBusinessException
	* (non-Javadoc)
	* @see com.yonyou.iuap.crm.subsidy.service.itf.ICountrysubsidyService#getCountrysubsidyFundbackBypage(java.util.Map, org.springframework.data.domain.PageRequest)
	 */
	@Override
	public Page<CountrysubsidyFundbackVO> getCountrysubsidyFundbackBypage(
			Map<String, Object> searchParams, PageRequest pageRequest)
			throws AppBusinessException {
		try{
			StringBuffer condition = new StringBuffer();
			
			SQLParameter sqlParameter = new SQLParameter();
			for(String key : searchParams.keySet()){
				String value = searchParams.get(key)==null?"":searchParams.get(key).toString();
				if(null != value && value.length()>0){
					//外键
					if(null != key && "pk_countrysubsidy".equals(key)){
						condition.append(" and a.pk_countrysubsidy = '").append(value).append("'");
					}else if(null != key && "vvin".equals(key)){
						condition.append(" and a.vvin like '%").append(value).append("%'");
					}
				}
			}
			return subsidyDao.getCountrysubsidyFundbackBypages(condition.toString(), sqlParameter, pageRequest);
		}catch(DAOException e){
			//Dao层次的异常需要处理成业务异常报给controller
			throw new AppBusinessException(e.getMessage());
		}
	}

	/**
	 * 批量删除国补申请单主表信息
	* TODO description
	* @author 
	* @date 2016年11月28日
	* @param entitys
	* @throws AppBusinessException
	 */
	@Override
	@Transactional
	public void deleteBatchCountrysubsidy(List<CountrysubsidyVO> entitys)
			throws AppBusinessException {
		try{
			if(null != entitys && entitys.size()>0){
				for(CountrysubsidyVO mainVO:entitys){
					subsidyDao.deleteItemWithFk(mainVO.getPk_countrysubsidy());
					subsidyDao.deleteFundbackWithFk(mainVO.getPk_countrysubsidy());
				}
				subsidyDao.deleteBatch(entitys);
			}
		}catch(DAOException e){
			//Dao层次的异常需要处理成业务异常报给controller
			throw new AppBusinessException(e.getMessage());
		}
	}

	/**
	 * 导入国补申报车辆信息
	* @author 
	* @date 2016年11月30日
	* @param entitys
	* @return
	* @throws AppBusinessException
	* (non-Javadoc)
	* @see com.yonyou.iuap.crm.subsidy.service.itf.ICountrysubsidyService#importSubsidyItem(java.util.List)
	 */
	@Override
	@Transactional
	public String importSubsidyItem(List<CountrysubsidyItemsVO> entitys)
			throws AppBusinessException {
		try{
			StringBuffer errorBuf = new StringBuffer();
			if(null != entitys && entitys.size()>0){
				//拼接
				StringBuffer sqlBuf = new StringBuffer(" and (");
				for(int i=0;i<entitys.size();i++){
					if(i==0){
						sqlBuf.append(" vvin in ('").append(entitys.get(i).getVvin()).append("'");
					}else if(i%100==0 && i!=0){
						sqlBuf.append(" ) or vvin in ( ").append(entitys.get(i).getVvin()).append("'");
					}else{
						sqlBuf.append(",'").append(entitys.get(i).getVvin()).append("'");
					}
				}
				sqlBuf.append(")) ");
				
				//检查是否超过范围的数据
				StringBuffer checkSqlBuf = new StringBuffer();
				checkSqlBuf.append(sqlBuf);
				checkSqlBuf.append(" and pk_countrysubsidy <> '").append(entitys.get(0).getPk_countrysubsidy()).append("'");
				//查询国补申报中，每个VIN只能申报一次
				List<CountrysubsidyItemsVO> itemList = subsidyDao.queryItemWithContiditon(checkSqlBuf.toString());
				if(null != itemList && itemList.size()>0){
					errorBuf.append("以下VIN已经申报国补不能再次申报:");
					for(int i=0;i<itemList.size();i++){
						if(i==0){
							errorBuf.append(itemList.get(i).getVvin());
						}else{
							errorBuf.append("，").append(itemList.get(i).getVvin());
						}
						
					}
					errorBuf.append("！");
				}else{
					//检查VIN更新还是新增
					StringBuffer querySqlBuf = new StringBuffer();
					querySqlBuf.append(sqlBuf);
					querySqlBuf.append(" and pk_countrysubsidy = '").append(entitys.get(0).getPk_countrysubsidy()).append("'");
					List<CountrysubsidyItemsVO> modifyList = subsidyDao.queryItemWithContiditon(querySqlBuf.toString());
					Map<String,CountrysubsidyItemsVO> modifyMap = new HashMap<String,CountrysubsidyItemsVO>();
					if(null != modifyList && modifyList.size()>0){
						for(CountrysubsidyItemsVO tempVO : modifyList){
							modifyMap.put(tempVO.getVvin(), tempVO);
						}
					}
					//批量新增VO List
					List<CountrysubsidyItemsVO> itemAddList = new ArrayList<CountrysubsidyItemsVO>();
					//批量修改VO List
					List<CountrysubsidyItemsVO> itemModifyList = new ArrayList<CountrysubsidyItemsVO>();
					/**
					 * 检查导入的信息新增还是修改
					 */
					for(int j=0;j<entitys.size();j++){
						String vin = entitys.get(j).getVvin();
						CountrysubsidyItemsVO upItemVO = modifyMap.get(vin);
						//
						if(null != upItemVO){
							entitys.get(j).setPk_countrysubsidy_items(upItemVO.getPk_countrysubsidy_items());
							entitys.get(j).setNtotalback(upItemVO.getNtotalback());
							double nnotback = CommonUtils.sub(entitys.get(j).getNsubsidystandard(), upItemVO.getNtotalback());
							if(nnotback < -0.000001){
								errorBuf.append("VIN:").append(upItemVO.getVvin()).append("已经回款，不能调低补贴申请金额；");
							}else{
								entitys.get(j).setNnotback(nnotback);
								entitys.get(j).setTs(upItemVO.getTs());
								itemModifyList.add(entitys.get(j));
							}
						}
						//新增
						else{
							itemAddList.add(entitys.get(j));
						}
					}
					if(errorBuf.length()==0){
						//批量新增
						if(itemAddList.size()>0){
							subsidyDao.saveItemBatch(entitys);
						}
						//批量修改
						if(itemModifyList.size()>0){
							subsidyDao.modifyItemBatch(entitys);
						}
					}
				}
			}
			return errorBuf.toString();
		}catch(DAOException e){
			//Dao层次的异常需要处理成业务异常报给controller
			throw new AppBusinessException(e.getMessage());
		}
	}

	/**
	 * 国补申报车辆回款信息
	* @author 
	* @date 2016年11月30日
	* @param entitys
	* @return
	* @throws AppBusinessException
	* (non-Javadoc)
	* @see com.yonyou.iuap.crm.subsidy.service.itf.ICountrysubsidyService#importSubsidyFundback(java.util.List)
	 */
	@Override
	@Transactional
	public String importSubsidyFundback(List<CountrysubsidyFundbackVO> entitys)
			throws AppBusinessException {
		try{
			/**
			 * 修改车辆信息中的实际回款金额
			 */
			StringBuffer errorBuf = new StringBuffer();
			if(null != entitys && entitys.size()>0){
				StringBuffer sqlBuf = new StringBuffer(" and vvin in (");
				Map<String,Double> vinFundbackMap = new HashMap<String,Double>();
				for(int i=0;i<entitys.size();i++){
					if(i==0){
						sqlBuf.append("'").append(entitys.get(i).getVvin()).append("'");
					}else{
						sqlBuf.append(",'").append(entitys.get(i).getVvin()).append("'");
					}
					vinFundbackMap.put(entitys.get(i).getVvin(), entitys.get(i).getNfactback());
				}
				sqlBuf.append(") ");
				//国补申报单主键
				sqlBuf.append(" and pk_countrysubsidy = '").append(entitys.get(0).getPk_countrysubsidy()).append("' ");
				List<CountrysubsidyItemsVO> itemList = subsidyDao.queryItemWithContiditon(sqlBuf.toString());
				
				//申报的车辆信息
				Map<String,String> subsidyVinMap = new HashMap<String,String>();
				for(int j=0;j<itemList.size();j++){
					CountrysubsidyItemsVO tempItemVo = itemList.get(j);
					double totalBack = tempItemVo.getNtotalback();//累计回款金额
					double subsidystandard = tempItemVo.getNsubsidystandard();//申请补贴标准
					double notback = tempItemVo.getNnotback();//未回款金额
					double thisBack = vinFundbackMap.get(tempItemVo.getVvin());//车辆识别代码VIN
					//计算本次回款后的 累计回款金额
					double sumBack = CommonUtils.add(totalBack, thisBack);
					if(subsidystandard < sumBack){
						errorBuf.append("VIN:").append(tempItemVo.getVvin()).append("回款总额已经超出申请补贴标准！");
					}else if (sumBack<-0.000001){
						errorBuf.append("VIN:").append(tempItemVo.getVvin()).append("红冲金额不能超出回款金额！");
					}else{
						tempItemVo.setNtotalback(sumBack);
						tempItemVo.setNnotback(CommonUtils.sub(notback, thisBack));
					}
					
					subsidyVinMap.put(tempItemVo.getVvin(), tempItemVo.getVvin());
				}
				for(int i=0;i<entitys.size();i++){
					String tempVIN = entitys.get(i).getVvin();
					if(null == subsidyVinMap.get(tempVIN)){
						errorBuf.append("VIN:").append(tempVIN).append("不属于本次国补申报车辆，不能录入回款！");
					}
				}
				if(errorBuf.length()==0){
					subsidyDao.modifyItemBatch(itemList);
					//保存回款信息
					subsidyDao.saveItemFundbackBatch(entitys);
				}
			}
			return errorBuf.toString();
		}catch(DAOException e){
			//Dao层次的异常需要处理成业务异常报给controller
			throw new AppBusinessException(e.getMessage());
		}
	}

	/**
	 * 根据条件查询国补申报车辆信息
	* @author 
	* @date 2016年11月30日
	* @param sqlCondition
	* @return
	* @throws AppBusinessException
	* (non-Javadoc)
	* @see com.yonyou.iuap.crm.subsidy.service.itf.ICountrysubsidyService#queryItemWithContiditon(java.lang.String)
	 */
	@Override
	public List<CountrysubsidyItemsVO> queryItemWithContiditon(
			String sqlCondition) throws AppBusinessException {
		try{
			return subsidyDao.queryItemWithContiditon(sqlCondition);
		}catch(DAOException e){
			//Dao层次的异常需要处理成业务异常报给controller
			throw new AppBusinessException(e.getMessage());
		}
	}

	/**
	 * 批量完结国补申报单
	* @author 
	* @date 2016年12月1日
	* @param entitys
	* @throws AppBusinessException
	* (non-Javadoc)
	* @see com.yonyou.iuap.crm.subsidy.service.itf.ICountrysubsidyService#finishBatchCountrysubsidy(java.util.List)
	 */
	@Override
	@Transactional
	public void finishBatchCountrysubsidy(List<CountrysubsidyVO> entitys)
			throws AppBusinessException {
		try{
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			if(null != entitys && entitys.size()>0){
				for(CountrysubsidyVO mainVo:entitys){
					mainVo.setModifiedtime(sdf.format(new Date()));
					mainVo.setModifier(AppInvocationInfoProxy.getPk_User());
					mainVo.setVstatus(DictCode.COUNTRY_SUBSIDY_STATUS_FINISH);//已完结
				}
				subsidyDao.modifyBatch(entitys);
			}
		}catch(DAOException e){
			//Dao层次的异常需要处理成业务异常报给controller
			throw new AppBusinessException(e.getMessage());
		}
	}

	/**
	 * 批量撤销完结国补申请单
	* @author name
	* @date 2016年12月1日
	* @param entitys
	* @throws AppBusinessException
	* (non-Javadoc)
	* @see com.yonyou.iuap.crm.subsidy.service.itf.ICountrysubsidyService#unFinishBatchCountrysubsidy(java.util.List)
	 */
	@Override
	@Transactional
	public void unFinishBatchCountrysubsidy(List<CountrysubsidyVO> entitys)
			throws AppBusinessException {
		try{
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			if(null != entitys && entitys.size()>0){
				for(CountrysubsidyVO mainVo:entitys){
					mainVo.setModifiedtime(sdf.format(new Date()));
					mainVo.setModifier(AppInvocationInfoProxy.getPk_User());
					mainVo.setVstatus(DictCode.COUNTRY_SUBSIDY_STATUS_SAVE);//已保存
				}
				subsidyDao.modifyBatch(entitys);
			}
		}catch(DAOException e){
			//Dao层次的异常需要处理成业务异常报给controller
			throw new AppBusinessException(e.getMessage());
		}
	}
}
