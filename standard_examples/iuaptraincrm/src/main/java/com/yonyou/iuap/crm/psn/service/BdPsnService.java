package com.yonyou.iuap.crm.psn.service;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springside.modules.utils.Clock;

import com.yonyou.iuap.persistence.bs.dao.DAOException;
import com.yonyou.iuap.persistence.jdbc.framework.SQLParameter;
import com.yonyou.iuap.crm.common.exception.AppBusinessException;
import com.yonyou.iuap.crm.common.utils.AppInvocationInfoProxy;
import com.yonyou.iuap.crm.common.utils.AppTools;
import com.yonyou.iuap.crm.psn.entity.BdPsnDutyVO;
import com.yonyou.iuap.crm.psn.entity.BdPsndocVO;
import com.yonyou.iuap.crm.psn.repository.itf.IBdPsnDao;
import com.yonyou.iuap.crm.psn.repository.itf.IBdPsnDutyDao;

@Service
public class BdPsnService {
	private static final Logger logger = LoggerFactory
			.getLogger(BdPsnService.class);
	@Autowired
	private IBdPsnDao dao;
	
	@Autowired
	private IBdPsnDutyDao dutyDao;

	private Clock clock = Clock.DEFAULT;

	public void setClock(Clock clock) {
		this.clock = clock;
	}

	/**
	 * 
	* TODO 根据pk_psndoc判断，确定逻辑为修改或者新增
	* @author 
	* @date 2016年12月6日
	* @param entity
	* @return
	* @throws AppBusinessException
	 */
	@Transactional
	public String savePsnEntity(BdPsndocVO entity) throws AppBusinessException {
		try{
			String pk_psndoc = entity.getPk_psndoc();
			if (null == pk_psndoc || "".equals(pk_psndoc)) {
				pk_psndoc = AppTools.generatePK();
				entity.setPk_psndoc(pk_psndoc);
				entity.setPsnseal(0);
				String creator = AppInvocationInfoProxy.getPk_User();
				entity.setCreator(creator);
				entity.setCreationtime(clock.getCurrentDate());
				dao.savePsn(entity);
				return entity.getPk_psndoc();
			} else {
				String modifier = AppInvocationInfoProxy.getPk_User();
				entity.setModifier(modifier);
				entity.setModifiedtime(clock.getCurrentDate());
				dao.updatePsn(entity);
				return pk_psndoc;
			}
		}catch(DAOException e){
			throw new AppBusinessException(e.getMessage());
		}
	}

	/**
	 * 依据psnname/psncode/pkDept/pkCorp获取人员信息
	 * 联合ieop_user/bd_dept/bd_corp/bd_psn联合几个表查询信息
	* TODO description
	* @author 
	* @date 2016年12月6日
	* @param searchParams
	* @param pageRequest
	* @return
	* @throws AppBusinessException
	 */
	public Page<BdPsndocVO> getBdDeptsBypage(
			Map<String, Object> searchParams, PageRequest pageRequest)
			throws AppBusinessException {
		try{
			StringBuffer condition = new StringBuffer();
			SQLParameter sqlParameter = new SQLParameter();
			for (Map.Entry<String, Object> entry : searchParams.entrySet()) {
				String[] keySplit = entry.getKey().split("_");
				if (keySplit.length == 2) {
					String columnName = keySplit[1];
					if (columnName.equals("psnname")) {
						columnName = "psnname";
					} else if (columnName.equals("psncode")) {
						columnName = "psncode";
					} else if (columnName.equals("pkDept")) {
						columnName = "pk_dept";
					} else if (columnName.equals("pkCorp")) {
						columnName = "pk_corp";
					}
					String comparator = keySplit[0];
					Object value = entry.getValue();
					if (value != null && StringUtils.isNotBlank(value.toString())) {
						condition.append(" and ").append(columnName).append(" ")
								.append(comparator).append("?");
						value = "like".equalsIgnoreCase(comparator) ? "%" + value
								+ "%" : value;
						sqlParameter.addParam(value);
					}
				}
			}
			return dao.getPsnsBypage(condition.toString(), sqlParameter,
					pageRequest);
		}catch(DAOException e){
			throw new AppBusinessException(e.getMessage());
		}
	}
	
	/**
	 * 参照过滤
	* TODO description
	* @author 
	* @date 2016年12月9日
	* @param searchParams
	* @param pageRequest
	* @return
	* @throws AppBusinessException
	 */
	public Page<BdPsndocVO> getBdDeptsForRef(
			Map<String, Object> searchParams, PageRequest pageRequest)
			throws AppBusinessException {
		try{
			StringBuffer condition = new StringBuffer();
			SQLParameter sqlParameter = new SQLParameter();
			for (Map.Entry<String, Object> entry : searchParams.entrySet()) {
				String[] keySplit = entry.getKey().split("&@&@");
				if (keySplit.length == 2) {
					String columnName = keySplit[1];
					if (columnName.equals("psnname")) {
						columnName = "psnname";
					} else if (columnName.equals("psncode")) {
						columnName = "psncode";
					} else if (columnName.equals("pkDept")) {
						columnName = "pk_dept";
					} else if (columnName.equals("pkCorp")) {
						columnName = "pk_corp";
					}
					String comparator = keySplit[0];
					Object value = entry.getValue();
					if (value != null && StringUtils.isNotBlank(value.toString())) {
						condition.append(" and ").append(columnName).append(" ")
								.append(comparator).append("?");
						value = "like".equalsIgnoreCase(comparator) ? "%" + value
								+ "%" : value;
						sqlParameter.addParam(value);
					}
				}
			}
			return dao.getPsnsBypage(condition.toString(), sqlParameter,
					pageRequest);
		}catch(DAOException e){
			throw new AppBusinessException(e.getMessage());
		}
	}


	/**
	 * 
	* TODO 删除人员档案信息
	* @author 
	* @date 2016年12月6日
	* @param pk_psndoc
	* @throws AppBusinessException
	 */
	@Transactional
	public void deletePsnById(String pk_psndoc) throws AppBusinessException {
		try{
			dao.deletePsnById(pk_psndoc);
		}catch(DAOException e){
			throw new AppBusinessException(e.getMessage());
		}
	}

	/**
	 * 
	* TODO 根据人员主键pk_psndoc获取人员档案信息
	* @author 
	* @date 2016年12月6日
	* @param pk_psndoc
	* @return
	* @throws AppBusinessException
	 */
	public BdPsndocVO queryPsnById(String pk_psndoc)
			throws AppBusinessException {
		try{
			return dao.queryPsnById(pk_psndoc);
		}catch(DAOException e){
			throw new AppBusinessException(e.getMessage());
		}
	}

	/**
	 * 根据id获取人员档案信息
	* TODO description
	* @author 
	* @date 2016年12月6日
	* @param wherestr
	* @return
	* @throws AppBusinessException
	 */
	public List<BdPsndocVO> queryPsnByIds(String paramname,String value)
			throws AppBusinessException {
		try{
			String wherestr = "";
			if("pk_corp".endsWith(paramname)){
				wherestr = " and  pk_corp = '"+value+"'";
			}else if("pk_dept".endsWith(paramname)){
				wherestr = " and pk_dept = '"+value+"'";
			}else if("pk_psndoc".endsWith(paramname)){
				wherestr = "and pk_psndoc='"+value+"'";
			}else if("pk_dept".endsWith(paramname)){
				wherestr = "and pk_psndoc='"+value+"'";
			}
			if(!StringUtils.isEmpty(wherestr)){
				return dao.queryPsnListByCondition(wherestr);			
			}else{
				return null;
			}
		}catch(DAOException e){
			throw new AppBusinessException(e.getMessage());
		}
	}

	/**
	 * 
	* TODO 封存人员档案
	* @author 
	* @date 2016年12月6日
	* @param pk_psndoc
	* @return
	* @throws AppBusinessException
	 */
	@Transactional
	public void sealedPsn(String pk_psndoc) throws AppBusinessException {
		try{
			dao.sealedPsn(pk_psndoc);
		}catch(DAOException e){
			throw new AppBusinessException(e.getMessage());
		}
	}

	/**
	 * 
	* TODO 解封人员档案
	* @author 
	* @date 2016年12月6日
	* @param pk_psndoc
	* @throws AppBusinessException
	 */
	@Transactional
	public void unSealedPsn(String pk_psndoc) throws AppBusinessException {
		try{
			dao.unSealedPsn(pk_psndoc);
		}catch(DAOException e){
			throw new AppBusinessException(e.getMessage());
		}
	}

	/**
	 * 批量删除人员档案
	* TODO description
	* @author 
	* @date 2016年12月6日
	* @param entity
	* @return
	* @throws AppBusinessException
	 */
	@Transactional
	public void deletelist(List<BdPsndocVO> entity)
			throws AppBusinessException {
		try{
			dao.deletelist(entity);
		}catch(DAOException e){
			throw new AppBusinessException(e.getMessage());
		}
	}

	/**
	 * 
	* TODO 批量封存人员档案
	* @author 
	* @date 2016年12月6日
	* @param entity
	* @return
	* @throws AppBusinessException
	 */
	@Transactional
	public void stoplist(List<BdPsndocVO> entity) throws AppBusinessException {
		try{
			dao.stoplist(entity);
		}catch(DAOException e){
			throw new AppBusinessException(e.getMessage());
		}
	}

	/**
	 * 
	* TODO 批量启用人员档案
	* @author 
	* @date 2016年12月6日
	* @param entity
	* @throws AppBusinessException
	 */
	@Transactional
	public void startlist(List<BdPsndocVO> entity)
			throws AppBusinessException {
		try{
			dao.startlist(entity);
		}catch(DAOException e){
			throw new AppBusinessException(e.getMessage());
		}
	}


	/**
	 * 
	* TODO 修改
	* @author 
	* @date 2016年12月6日
	* @param id
	* @param email
	* @throws AppBusinessException
	 */
	@Transactional
	public void updateemail(String id, String email)
			throws AppBusinessException {
		try{
			dao.updateemail(id, email);
		}catch(DAOException e){
			throw new AppBusinessException(e.getMessage());
		}
	}

	/**
	 * 
	 * TODO 修改联系方式
	 * 
	 * @author 
	 * @date 2016年12月6日
	 * @param id
	 * @param pntel
	 * @throws AppBusinessException
	 */
	@Transactional
	public void updatetel(String id, String pntel) throws AppBusinessException {
		try{
			dao.updatetel(id, pntel);
		}catch(DAOException e){
			throw new AppBusinessException(e.getMessage());
		}
	}

	/**
	 * 
	 * TODO description
	 * 
	 * @author 
	 * @date 2016年12月6日
	 * @param wherestr
	 * @return
	 * @throws AppBusinessException
	 */
	public List<BdPsndocVO> queryPsnListByCondition(String wherestr)
			throws AppBusinessException {
		try{
			return dao.queryPsnListByCondition(wherestr);
		}catch(DAOException e){
			throw new AppBusinessException(e.getMessage());
		}
	}
	
	/**
	 * 
	* TODO 人员调动
	* @author 
	* @date 2016年12月7日
	* @param pk_psndoc
	* @param oldCorp
	* @param oldDept
	* @param newCorp
	* @param newDept
	* @throws AppBusinessException
	 */
	@Transactional
	public void shiftDept(String pk_psndoc, String oldCorp, String oldDept, String newCorp, String newDept) throws AppBusinessException{
		try{
			String wherestr = " pk_psndoc='"+pk_psndoc+"' and pk_corp='"+oldCorp+"' and pk_dept='"+oldDept+"'";
			List<BdPsndocVO> listpsn = dao.queryPsnListByCondition(wherestr);
			if(listpsn != null || !listpsn.isEmpty()){
				BdPsndocVO vo = listpsn.get(0);
				vo.setPk_corp(newCorp);
				vo.setPk_dept(newDept);
				dao.shiftDept(vo);
				//保存调动历史
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				BdPsnDutyVO entity = new BdPsnDutyVO();
				entity.setPk_psnduty(AppTools.generatePK());
				entity.setPk_psndoc(pk_psndoc);
				entity.setPkCorp(oldCorp);
				entity.setPkDept(oldDept);
				entity.setDef1(sdf.format(new Date()));//调动时间
				dutyDao.savePsnDuty(entity);
	//			entity.set
			}else{
				throw new AppBusinessException("您操作的数据发生了改变,请刷新后重新操作");
			}
		}catch(DAOException e){
			throw new AppBusinessException(e.getMessage());
		}
		
	}

}
