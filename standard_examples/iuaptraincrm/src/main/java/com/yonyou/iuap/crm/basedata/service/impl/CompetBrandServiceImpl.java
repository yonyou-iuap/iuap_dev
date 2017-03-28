package com.yonyou.iuap.crm.basedata.service.impl;

import com.yonyou.iuap.crm.basedata.entity.*;
import com.yonyou.iuap.persistence.bs.dao.DAOException;
import com.yonyou.iuap.persistence.jdbc.framework.SQLParameter;
import com.yonyou.iuap.crm.basedata.repository.itf.ICompetBrandDao;
import com.yonyou.iuap.crm.basedata.repository.itf.ITmComBrandDao;
import com.yonyou.iuap.crm.basedata.service.itf.ICompetBrandService;
import com.yonyou.iuap.crm.billcode.service.itf.ICommonComponentsService;
import com.yonyou.iuap.crm.common.exception.AppBusinessException;
import com.yonyou.iuap.crm.common.utils.AppInvocationInfoProxy;
import com.yonyou.iuap.crm.common.utils.AppTools;
import com.yonyou.iuap.crm.common.utils.DictCode;
import com.yonyou.iuap.crm.common.utils.OrderPrefix;
import com.yonyou.iuap.crm.system.service.itf.IDataRoleService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springside.modules.utils.Clock;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 竞品信息Service接口实现类 实现了竞品信息的查询、更新、发布、撤销、关闭、保存和删除业务
 * 
 * @author 
 * @date Nov 24, 2016
 */
@Service
public class CompetBrandServiceImpl implements ICompetBrandService {
	@Autowired
	private ICompetBrandDao dao;
	@Autowired
	private ITmComBrandDao brandDao;
	@Autowired
	private ICommonComponentsService commonService;
	@Autowired
	private IDataRoleService dataRoleService;
	private Clock clock = Clock.DEFAULT;

	/**
	 * 根据主键查询竞品信息
	 * 
	 * @author 
	 * @date Nov 21, 2016
	 * @param pk_competbrand
	 * @return
	 * @throws AppBusinessException
	 *             (non-Javadoc)
	 * @see com.yonyou.iuap.crm.basedata.service.itf.ICompetBrandService#getCompetBrand(java.lang.String)
	 */
	@Override
	public TmCompetBrandVO getCompetBrand(String pk_competbrand)
			throws AppBusinessException {
		try {
			return dao.getCompetBrand(pk_competbrand);
		} catch (DAOException e) {
			throw new AppBusinessException(e.getMessage());
		}
	}

	/**
	 * 查询竞品信息，带分页
	 * 
	 * @author 
	 * @date Nov 28, 2016
	 * @param searchParams
	 * @param pageRequest
	 * @return
	 * @throws AppBusinessException
	 *             (non-Javadoc)
	 * @see com.yonyou.iuap.crm.basedata.service.itf.ICompetBrandService#getCompetBrandsExtBypage(java.util.Map,
	 *      org.springframework.data.domain.PageRequest)
	 */
	@Override
	public Page<TmCompetBrandExtVO> getCompetBrandsExtBypage(
			Map<String, Object> searchParams, PageRequest pageRequest)
			throws AppBusinessException {
		StringBuffer condition = new StringBuffer();
		SQLParameter sqlParameter = new SQLParameter();
		StringBuffer sql = new StringBuffer();
		try {
			Object vcompet = searchParams.get("vcompetbrand");
			Object vcompetmodel = searchParams.get("vmodelname");//根据车型名称
			Object city = searchParams.get("vcity");// 根据城市查询
			Object vlengthMin = searchParams.get("vlengthMin");// 车长下限条件
			Object vlengthMax = searchParams.get("vlengthMax");// 车长上限条件
			Object vstatus = searchParams.get("vstatus");// 根据状态查询
			Object isShipped = searchParams.get("isShipped");// 根据是否投放市场查询
			if (null != vcompet && !vcompet.toString().equals("")) {
				condition.append(" And tcp.vcompetbrand = ? ");
				sqlParameter.addParam(vcompet.toString());
			}
			if (null != vcompetmodel && !vcompetmodel.toString().equals("")) {
				condition.append(" And tcp.vmodelname Like ? ");
				sqlParameter.addParam("%" + vcompetmodel.toString() + "%");
			}
			if (null != vlengthMin && !vlengthMin.toString().equals("")) {
				condition.append(" And tcp.vlength >= ? ");
				sqlParameter.addParam(vlengthMin.toString());
			}
			if (null != vlengthMax && !vlengthMax.toString().equals("")) {
				condition.append(" And tcp.vlength <= ? ");
				sqlParameter.addParam(vlengthMax.toString());
			}
			if (null != city && !city.toString().equals("")) {
				condition
						.append(" And tcp.pk_competbrand In (SELECT pk_competbrand FROM tm_compet_price WHERE vcity ")
								.append("=? AND dr=0) ");
				sqlParameter.addParam(city.toString());
			}
			if (null != vstatus && !vstatus.toString().equals("")) {
				condition.append(" And tcp.vstatus = ? ");
				sqlParameter.addParam(vstatus.toString());
			}
			//是否投放市场，根据其竞品价格子表是否包含数据来判定
			if (null != isShipped &&!isShipped.toString().isEmpty()) {
				if(isShipped.toString().equals(DictCode.BILL_YES_STATUS)){
					condition.append(" AND tcp.pk_competbrand IN (SELECT tcp.pk_competbrand FROM tm_compet_price tcp ")
							.append("WHERE tcp.dr=0 GROUP BY tcp.pk_competbrand) ");
				}else{
					condition.append(" AND tcp.pk_competbrand NOT IN (SELECT tcp.pk_competbrand FROM tm_compet_price ")
							.append("tcp WHERE tcp.dr=0 GROUP BY tcp.pk_competbrand) ");
				}
			}
			sql.append("SELECT tcp.pk_competbrand,tcp.vcompetbrand, tcb.vcbrandname,tcp.vmodelname,tcp.vcompetmodel,")
				.append("tcp.vnoticenum,tcp.vheight,tcp.vwidth, tcp.vlength, tcp.ncurbweight, tcp.nmaxweight, ")
				.append("tcp.npasenger, tcp.vmotorpower, tcp.vbatteryclass, tcp.vstatus,tcp.ts ")
				.append("FROM tm_competbrand AS tcp ")
				.append("LEFT JOIN tm_combrand AS tcb ON tcp.vcompetbrand=tcb.pk_combrand AND tcb.dr=0 WHERE tcp.dr=0");
			return dao.getCompetBrandsExtBypage(condition.toString(),
					sqlParameter, pageRequest, sql.toString());
		} catch (Exception e) {
			throw new AppBusinessException(e.getMessage());
		}
	}

	/**
	 * 保存竞品信息
	 * 
	 * @author 
	 * @date Nov 21, 2016
	 * @param entity
	 * @return
	 * @throws AppBusinessException
	 *             (non-Javadoc)
	 * @see com.yonyou.iuap.crm.basedata.service.itf.ICompetBrandService#saveEntity(com.yonyou.iuap.crm.basedata.entity.TmCompetBrandVO)
	 */
	@Override
	@Transactional
	public String saveEntity(TmCompetBrandVO entity)
			throws AppBusinessException {
		SimpleDateFormat dateFormat = new SimpleDateFormat(
				"yyyy-MM-dd HH:MM:ss");
		try {
			String pk;
			if (entity.getPk_competbrand() == null
					|| entity.getPk_competbrand().trim().length() == 0) {
				// 去重，车型、品牌完全重复时才算重复
				List<TmCompetBrandVO> existData = dao
						.findListByClauseWithDR(" vcompetbrand = '"+entity.getVcompetbrand()+"' AND vmodelname = '"
								+ entity.getVmodelname() + "' ");
				if (existData != null && existData.size() > 0) {
					throw new AppBusinessException("该品牌及车型名称与已有记录重复！");
				}
				pk = AppTools.generatePK();
				//竞品车型编码自动生成
				entity.setVcompetmodel(commonService.generateOrderNo(OrderPrefix.CM));
				entity.setCreator(AppInvocationInfoProxy.getPk_User());
				entity.setPk_competbrand(pk);
				entity.setDr(0);
				entity.setVstatus(DictCode.COMPETBRAND_SAVED_STATUS);// 设置状态为一已保存
				entity.setCreationtime(dateFormat.format(clock.getCurrentDate()));
				pk = dao.saveCompetBrand(entity);
				return pk;
			} else {
				/*if (DictCode.COMPETBRAND_PUBLISHED_STATUS.equals(entity
						.getVstatus())) {
					throw new AppBusinessException("已发布状态不能执行修改");
				}*/
				if (DictCode.COMPETBRAND_CLOSED_STATUS.equals(entity
						.getVstatus())) {
					throw new AppBusinessException("已关闭状态不能执行修改");
				}
				//比对车型和编码是否相同
				if (entity.getVcompetmodel().equals(entity.getVmodelname())) {
					throw new AppBusinessException("车型编码和车型名称不能相同");
				}
				pk = entity.getPk_competbrand();
				TmCompetBrandVO categoryPage = dao.getCompetBrand(pk);
				//去重
				boolean isChanged = false;
				//品牌和车型任一修改都做校验
				if (!categoryPage.getVcompetbrand().equals(entity.getVcompetbrand())) {
					isChanged = true;
				}
				if (!categoryPage.getVmodelname().equals(entity.getVmodelname())) {
					isChanged = true;
				}
				if (isChanged) {
					List<TmCompetBrandVO> existData = dao
							.findListByClauseWithDR(" vcompetbrand = '" + entity.getVcompetbrand() + "' AND vmodelname = '"
									+ entity.getVmodelname() + "' ");
					if (existData != null && existData.size() > 0) {
						throw new AppBusinessException("该品牌及车型名称与已有记录重复！");
					}
				}
				entity.setCreator(categoryPage.getCreator());
				entity.setCreationtime(categoryPage.getCreationtime());
				entity.setModifier(AppInvocationInfoProxy.getPk_User());
				entity.setModifiedtime(dateFormat.format(new Date()));
				entity.setVstatus(categoryPage.getVstatus());
				dao.updateCompetBrand(entity);
				return pk;
			}
		} catch (Exception e) {
			throw new AppBusinessException(e.getMessage());
		}
	}

	/**
	 * 删除竞品信息 根据竞品信息的状态决定不同的删除手段（逻辑或物理）
	 * 
	 * @author 
	 * @date Nov 21, 2016
	 * @param entities
	 * @throws AppBusinessException
	 *             (non-Javadoc)
	 * @see com.yonyou.iuap.crm.basedata.service.itf.ICompetBrandService#
	 *      deleteCompetBrand(List<TmCompetBrandVO>)
	 */
	@Override
	@Transactional
	public void deleteCompetBrand(List<? extends TmCompetBrandVO> entities)
			throws AppBusinessException {
		StringBuffer errMsg = new StringBuffer();
		int count = 0;
		try {
			for (TmCompetBrandVO tmCompetBrandVO : entities) {
				if (tmCompetBrandVO.getPk_competbrand() == null
						|| tmCompetBrandVO.getPk_competbrand().trim().length() == 0) {
					throw new AppBusinessException("数据异常：PK为空");
				}
				// 过滤非法请求
				if (DictCode.COMPETBRAND_SAVED_STATUS.equals(tmCompetBrandVO
						.getVstatus())) {
					dao.deleteCompetBrand(tmCompetBrandVO);
				} /*else if (DictCode.COMPETBRAND_PUBLISHED_STATUS
						.equals(tmCompetBrandVO.getVstatus())) {
					// 已发布状态不能执行删除
					if (count++ > 0)
						errMsg.append("；");
					errMsg.append("删除" + tmCompetBrandVO.getVcompetbrand()
							+ "品牌下" + tmCompetBrandVO.getVcompetmodel()
							+ "车型失败，原因是：已发布状态不能执行删除");
				} */else if (DictCode.COMPETBRAND_CLOSED_STATUS
						.equals(tmCompetBrandVO.getVstatus())) {
					// 已关闭状态不能执行删除
					if (count++ > 0)
						errMsg.append("；");
					errMsg.append("删除" + tmCompetBrandVO.getVcompetbrand()
							+ "品牌下" + tmCompetBrandVO.getVcompetmodel()
							+ "车型失败，原因是：已关闭状态不能执行删除");
				} else {
					// 其他状态执行逻辑删除
					dao.deleteCompetBrandByTS(tmCompetBrandVO);
				}
			}
			// 如果有错误，抛出去
			if (errMsg.length() > 0) {
				throw new AppBusinessException(errMsg.toString());
			}
		} catch (Exception e) {
			throw new AppBusinessException(e.getMessage());
		}
	}

	/**
	 * 发布竞品信息
	 * 
	 * @author 
	 * @date Nov 21, 2016
	 * @param entities
	 * @throws AppBusinessException
	 *             (non-Javadoc)
	 * @see com.yonyou.iuap.crm.basedata.service.itf.ICompetBrandService#publishCompetBrand(List)
	 */
	@Override
	@Transactional
	public void publishCompetBrand(List<? extends TmCompetBrandVO> entities)
			throws AppBusinessException {
		StringBuffer errMsg = new StringBuffer();
		int count = 0;
		try {
			for (TmCompetBrandVO tmCompetBrandVO : entities) {
				if (tmCompetBrandVO.getPk_competbrand() == null
						|| tmCompetBrandVO.getPk_competbrand().trim().length() == 0) {
					throw new AppBusinessException("数据异常：PK为空");
				}
				if (DictCode.COMPETBRAND_PUBLISHED_STATUS
						.equals(tmCompetBrandVO.getVstatus())) {
					// 已发布状态不能重复发布
					if (count++ > 0)
						errMsg.append("；");
					errMsg.append("发布" + tmCompetBrandVO.getVcompetbrand()
							+ "品牌下" + tmCompetBrandVO.getVcompetmodel()
							+ "车型失败，原因是：重复发布");
				} else {
					dao.publishCompetBrand(tmCompetBrandVO);
				}
			}
			// 如果有错误，抛出去
			if (errMsg.length() > 0) {
				throw new AppBusinessException(errMsg.toString());
			}
		} catch (DAOException e) {
			throw new AppBusinessException(e.getMessage());
		}
	}

	/**
	 * 撤销已发布的竞品信息
	 * 
	 * @author 
	 * @date Nov 21, 2016
	 * @param entities
	 * @throws AppBusinessException
	 *             (non-Javadoc)
	 * @see com.yonyou.iuap.crm.basedata.service.itf.ICompetBrandService#revokeCompetBrand(List)
	 */
	@Override
	@Transactional
	public void revokeCompetBrand(List<? extends TmCompetBrandVO> entities)
			throws AppBusinessException {
		StringBuffer errMsg = new StringBuffer();// 保存错误信息
		int count = 0;
		try {
			for (TmCompetBrandVO tmCompetBrandVO : entities) {
				if (tmCompetBrandVO.getPk_competbrand() == null
						|| tmCompetBrandVO.getPk_competbrand().trim().length() == 0) {
					throw new AppBusinessException("数据异常：PK为空");
				}
				// 过滤非法的请求
				if (DictCode.COMPETBRAND_REVOKED_STATUS.equals(tmCompetBrandVO
						.getVstatus())) {// 已撤销不能重复撤销
					if (count++ > 0)
						errMsg.append("；");
					errMsg.append("撤销" + tmCompetBrandVO.getVcompetbrand()
							+ "品牌下" + tmCompetBrandVO.getVcompetmodel()
							+ "车型失败，原因是：重复撤销");
				} else if (!DictCode.COMPETBRAND_PUBLISHED_STATUS
						.equals(tmCompetBrandVO.getVstatus())) {// 非已发布状态不能撤回
					if (count++ > 0)
						errMsg.append("；");
					errMsg.append("撤销" + tmCompetBrandVO.getVcompetbrand()
							+ "品牌下" + tmCompetBrandVO.getVcompetmodel()
							+ "车型失败，原因是：非发布状态不能执行撤销");
				} else {
					dao.revokeCompetBrand(tmCompetBrandVO);
				}
			}
			// 如果有错误，抛出去
			if (errMsg.length() > 0) {
				throw new AppBusinessException(errMsg.toString());
			}
		} catch (DAOException e) {
			throw new AppBusinessException(e.getMessage());
		}
	}

	/**
	 * 关闭竞品信息
	 * 
	 * @author 
	 * @date Nov 21, 2016
	 * @param entities
	 * @throws AppBusinessException
	 *             (non-Javadoc)
	 * @see com.yonyou.iuap.crm.basedata.service.itf.ICompetBrandService#closeCompetBrand(List)
	 */
	@Override
	@Transactional
	public void closeCompetBrand(List<? extends TmCompetBrandVO> entities)
			throws AppBusinessException {
		StringBuffer errMsg = new StringBuffer();// 保存错误信息
		int count = 0;
		try {
			for (TmCompetBrandVO tmCompetBrandVO : entities) {
				if (tmCompetBrandVO.getPk_competbrand() == null
						|| tmCompetBrandVO.getPk_competbrand().trim().length() == 0) {
					throw new AppBusinessException("数据异常：PK为空");
				}
				// 过滤非法的请求
				if (DictCode.COMPETBRAND_CLOSED_STATUS.equals(tmCompetBrandVO
						.getVstatus())) {
					if (count++ > 0)
						errMsg.append("；");
					errMsg.append("关闭" + tmCompetBrandVO.getVcompetbrand()
							+ "品牌下" + tmCompetBrandVO.getVcompetmodel()
							+ "车型失败，原因是：重复关闭");
				} /*else if (!DictCode.COMPETBRAND_PUBLISHED_STATUS
						.equals(tmCompetBrandVO.getVstatus())) {
					if (count++ > 0)
						errMsg.append("；");
					errMsg.append("关闭" + tmCompetBrandVO.getVcompetbrand()
							+ "品牌下" + tmCompetBrandVO.getVcompetmodel()
							+ "车型失败，原因是：非发布状态不能执行关闭");
				} */else {
					dao.closeCompetBrand(tmCompetBrandVO);
				}
			}
			// 如果有错误，抛出去
			if (errMsg.length() > 0) {
				throw new AppBusinessException(errMsg.toString());
			}
		} catch (DAOException e) {
			throw new AppBusinessException(e.getMessage());
		}
	}

	/**
	 * 枚举车型电量信息
	 * 
	 * @author 
	 * @date Dec 6, 2016
	 * @param pageRequest
	 * @param searchParams
	 * @throws AppBusinessException
	 *             (non-Javadoc)
	 * @see com.yonyou.iuap.crm.basedata.service.itf.ICompetBrandService#enumBattery(Map, PageRequest)
	 */
	@Override
	public Page<TmModelVO> enumBattery(Map<String, Object> searchParams,
			PageRequest pageRequest) throws AppBusinessException {
		StringBuffer condition = new StringBuffer();
		SQLParameter sqlParameter = new SQLParameter();
		String sql = "SELECT DISTINCT nvbatterypower,ts FROM tm_model WHERE dr=0;";
		try {
			return dao.getModelBattery(condition.toString(), sqlParameter,
					pageRequest, sql);
		} catch (DAOException e) {
			throw new AppBusinessException(e.getMessage());
		}
	}
	/**
	 * 获取所有竞品品牌，放到一个Map里
	* @author 
	* @date Dec 23, 2016
	* @return
	* @throws AppBusinessException
	* (non-Javadoc)
	* @see com.yonyou.iuap.crm.basedata.service.itf.ICompetBrandService#getCombrandMap()
	 */
	@Override
	public Map<String, String> getCombrandMap() throws AppBusinessException {
		try {
			List<TmComBrandVO> brandList=brandDao.findAllBrands();
			if (brandList == null || brandList.size() == 0) {
				throw new AppBusinessException("尚无竞品品牌记录");
			}
			Map<String,String> brandMap=new HashMap<String, String>();
			for (TmComBrandVO tmComBrandVO : brandList) {
				brandMap.put(tmComBrandVO.getVcbrandname(), tmComBrandVO.getPk_combrand());
			}
			return brandMap;
		} catch (DAOException e) {
			throw new AppBusinessException(e.getMessage());
		}
	}

	/**
	 * 批量保存或更新竞品信息
	 * 
	 * @author 
	 * @date Dec 23, 2016
	 * @param entities
	 * @return
	 * @throws AppBusinessException
	 *             (non-Javadoc)
	 * @see com.yonyou.iuap.crm.basedata.service.itf.ICompetBrandService#saveEntity(java.util.List)
	 */
	@Override
	@Transactional
	public void saveEntity(List<TmCompetBrandVO> entities)
			throws AppBusinessException {
		String pk = AppTools.generatePK();
		SimpleDateFormat dateFormat = new SimpleDateFormat(
				"yyyy-MM-dd HH:MM:ss");
		try {
			String currentDate = dateFormat.format(clock.getCurrentDate());
			for (int i = entities.size() - 1; i >= 0; i--) {
				TmCompetBrandVO tmp = entities.get(i);
				/*//FIXME:车型品牌都相同才算重复吗
				List<TmCompetBrandVO> existData = dao
						.findListByClauseWithDR("vmodelname = '"
								+ tmp.getVmodelname() + "' ");
				if (existData != null && existData.size() > 0) {
					throw new AppBusinessException("品牌："+tmp + tmp.getVmodelname() + "与已有记录重复");
				}*/
				tmp.setVcompetmodel(commonService.generateOrderNo(OrderPrefix.CM));//车型编码自动生成
				tmp.setCreator(AppInvocationInfoProxy.getPk_User());
				tmp.setPk_competbrand(pk);
				tmp.setVstatus(DictCode.COMPETBRAND_SAVED_STATUS);//设置状态
				tmp.setDr(0);
				tmp.setCreationtime(currentDate);
				pk = AppTools.generatePK();
			}
			// 批量新增
			dao.saveCompetBrand(entities);
		} catch (DAOException e) {
			throw new AppBusinessException(e.getMessage());
		}
	}

	/**
	 * 获竞品品牌的销量信息，数据主要来源于商用车上牌信息
	 * 
	 * @author 
	 * @date Dec 27, 2016
	 * @param pageRequest
	 * @param searchParams
	 * @return
	 * @throws AppBusinessException
	 *             (non-Javadoc)
	 * @see com.yonyou.iuap.crm.basedata.service.itf.ICompetBrandService#getCompetBrandSalesVolume(Map, PageRequest)
	 */
	@Override
	public Page<TmCompetbrandSalesVO> getCompetBrandSalesVolume(Map<String, Object> searchParams, PageRequest pageRequest) throws AppBusinessException {
		StringBuffer condition = new StringBuffer();
		StringBuffer sql = new StringBuffer();
		SQLParameter sqlParameter = new SQLParameter();
		try {
			Object vmodelname = searchParams.get("vmodelname");
			if (null == vmodelname || vmodelname.toString().equals("")) {
				throw new AppBusinessException("车型信息丢失，无法查询销量信息");
			}
			sql.append("SELECT vs.vyear,vs.sumup FROM (SELECT vyear,SUM(nquantity) AS sumup FROM mrk_licenseinfo WHERE dr=0 AND vmodel= ? GROUP BY vyear ) AS vs");
			sqlParameter.addParam(vmodelname.toString());
			return dao.getCompetBrandSalesVolume(condition.toString(), sqlParameter, pageRequest, sql.toString());
		} catch (Exception e) {
			throw new AppBusinessException(e.getMessage());
		}
	}
}
