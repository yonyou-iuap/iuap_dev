/**
 * 
 */
package com.yonyou.iuap.crm.basedata.service.impl;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.yonyou.iuap.crm.basedata.entity.TmVehicleClassifyExtVO;
import com.yonyou.iuap.crm.basedata.entity.TmVehicleClassifyVO;
import com.yonyou.iuap.persistence.bs.dao.DAOException;
import com.yonyou.iuap.persistence.jdbc.framework.SQLParameter;
import com.yonyou.iuap.persistence.vo.pub.BusinessException;
import com.yonyou.iuap.persistence.vo.pub.util.StringUtil;
import com.yonyou.iuap.crm.basedata.repository.itf.ITmVehicleClassifyDao;
import com.yonyou.iuap.crm.basedata.service.itf.ITmVehicleClassifyService;
import com.yonyou.iuap.crm.common.exception.AppBusinessException;
import com.yonyou.iuap.crm.common.utils.AppInvocationInfoProxy;
import com.yonyou.iuap.crm.common.utils.AppTools;
import com.yonyou.iuap.crm.common.utils.DictCode;

/**
 * 车辆分类服务实现类
 * 
 * @author 
 * @date 2016年11月21日
 */
@Service
public class TmVehicleClassifyServiceImpl implements ITmVehicleClassifyService {
	@Autowired
	private ITmVehicleClassifyDao dao;

	/**
	 * 获取分页车辆分类扩展信息
	 * 
	 * @author 
	 * @date 2016年12月30日
	 * @param parammap
	 * @param pageRequest
	 * @return
	 * @throws AppBusinessException
	 *             (non-Javadoc)
	 * @see com.yonyou.iuap.crm.basedata.service.itf.ITmVehicleClassifyService#getTmVehicleClassifysExtBypage(java.util.Map,
	 *      org.springframework.data.domain.PageRequest)
	 */
	@Override
	public Page<TmVehicleClassifyExtVO> getTmVehicleClassifysExtBypage(
			Map<String, Object> parammap, PageRequest pageRequest)
			throws AppBusinessException {
		StringBuffer condition = new StringBuffer();
		SQLParameter sqlParameter = new SQLParameter();

		if (null != parammap.get("vclasscode")
				&& !parammap.get("vclasscode").toString().equals("")) {
			condition.append(" and vclasscode like ? ");
			sqlParameter.addParam("%" + parammap.get("vclasscode").toString()
					+ "%");
		}
		if (null != parammap.get("vclassname")
				&& !parammap.get("vclassname").toString().equals("")) {
			condition.append(" and vclassname like ? ");
			sqlParameter.addParam("%" + parammap.get("vclassname") + "%");
		}
		if (null != parammap.get("pk_brand")
				&& !parammap.get("pk_brand").toString().equals("")) {
			condition.append(" and a.pk_brand = ? ");
			sqlParameter.addParam(parammap.get("pk_brand"));
		}
		if (null != parammap.get("vstatus")
				&& !parammap.get("vstatus").toString().equals("")) {
			condition.append(" and a.vstatus = ? ");
			sqlParameter.addParam(parammap.get("vstatus"));
		}

		try {
			String sqlJoin = "select a.*, b.vbrandname  from tm_vehicleclassify a  "
					+ "left join tm_brand b on a.pk_brand=b.pk_brand where a.dr=0";

			return dao.getTmVehicleClassifysExtBypage(sqlJoin,
					condition.toString(), sqlParameter, pageRequest);
		} catch (DAOException e) {
			e.printStackTrace();
			throw new AppBusinessException(e.getMessage());
		}
	}

	/**
	 * 获取分页车辆分类信息
	 * 
	 * @author 
	 * @date 2016年11月21日
	 * @param searchParams
	 * @param pageRequest
	 * @return
	 * @throws BusinessException
	 *             (non-Javadoc)
	 * @see com.yonyou.iuap.crm.basedata.service.itf.ITmVehicleClassifyService#getTmVehicleClassifysBypage(java.util.Map,
	 *      org.springframework.data.domain.PageRequest)
	 */
	@Override
	public Page<TmVehicleClassifyVO> getTmVehicleClassifysBypage(
			Map<String, Object> parammap, PageRequest pageRequest)
			throws AppBusinessException {
		StringBuffer condition = new StringBuffer();
		SQLParameter sqlParameter = new SQLParameter();

		if (null != parammap.get("vclasscode")
				&& !parammap.get("vclasscode").toString().equals("")) {
			condition.append(" and vclasscode like ? ");
			sqlParameter.addParam("%" + parammap.get("vclasscode").toString()
					+ "%");
		}
		if (null != parammap.get("vclassname")
				&& !parammap.get("vclassname").toString().equals("")) {
			condition.append(" and vclassname like ? ");
			sqlParameter.addParam("%" + parammap.get("vclassname") + "%");
		}
		if (null != parammap.get("pk_brand")
				&& !parammap.get("pk_brand").toString().equals("")) {
			condition.append(" and pk_brand like ? ");
			sqlParameter.addParam("%" + parammap.get("pk_brand") + "%");
		}
		if (null != parammap.get("vstatus")
				&& !parammap.get("vstatus").toString().equals("")) {
			condition.append(" and vstatus = ? ");
			sqlParameter.addParam(parammap.get("vstatus"));
		}
		// 添加引用车辆类别时的过滤检索
		if (null != parammap.get("condition")
				&& !"".equals(parammap.get("condition"))) {
			condition.append(" ").append("and (vclassname like").append(" ")
					.append("?").append(" ").append("||")
					.append(" vclasscode like").append(" ").append(" ? )");
			sqlParameter.addParam("%" + parammap.get("condition") + "%");
			sqlParameter.addParam("%" + parammap.get("condition") + "%");
		}

		try {
			return dao.getTmVehicleClassifysBypage(condition.toString(),
					sqlParameter, pageRequest);
		} catch (DAOException e) {
			e.printStackTrace();
			throw new AppBusinessException(e.getMessage());
		}

	}

	/**
	 * 保存车辆分类实体
	 * 
	 * @author 
	 * @date 2016年11月21日
	 * @param entity
	 * @return
	 * @throws BusinessException
	 *             (non-Javadoc)
	 * @see com.yonyou.iuap.crm.basedata.service.itf.ITmVehicleClassifyService#saveEntity(com.yonyou.iuap.crm.basedata.entity.TmVehicleClassifyVO)
	 */
	@Override
	@Transactional
	public String saveEntity(TmVehicleClassifyVO entity)
			throws AppBusinessException {
		Date d = new Date();
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:MM:ss");
		String datestr = df.format(d); // 创建时间

		try {
//			String sqlCondition = " (vclassname='" + entity.getVclassname()
//					+ "' OR vclasscode='" + entity.getVclasscode()
//					+ "') AND pk_brand='" + entity.getPk_brand() + "' ";
			String sqlCondition = " (vclassname = ?  or vclasscode = ?) and pk_brand = ? ";
			SQLParameter para = new SQLParameter();
			para.addParam(entity.getVclassname());
			para.addParam(entity.getVclasscode());
			para.addParam(entity.getPk_brand());
			
			
			List<TmVehicleClassifyVO> duplicateClassify = dao
					.findVehicleClassifyByClause(sqlCondition, para);
			
			
			if (duplicateClassify.size() > 0) {
				throw new AppBusinessException("同一品牌下，车辆类别编码或名称不能重复！");
			}
			
			// 无重复，则保存
			String pk = AppTools.generatePK();
			entity.setPk_vehicleclassify(pk);
			entity.setPk_org(AppInvocationInfoProxy.getPk_Corp());
			entity.setDr(0);
			entity.setVstatus(DictCode.ALREADY_START_STATUS);
			entity.setCreationtime(datestr);
			entity.setCreator(AppInvocationInfoProxy.getPk_User());
			return dao.saveTmVehicleClassifyVo(entity);
		} catch (DAOException e) {
			e.printStackTrace();
			throw new AppBusinessException(e.getMessage());
		}
	}

	/**
	 * 更新车辆分类实体
	 * 
	 * @author 
	 * @date 2016年11月21日
	 * @param entity
	 * @return
	 * @throws BusinessException
	 *             (non-Javadoc)
	 * @see com.yonyou.iuap.crm.basedata.service.itf.ITmVehicleClassifyService#updateEntity(com.yonyou.iuap.crm.basedata.entity.TmVehicleClassifyVO)
	 */
	@Override
	@Transactional
	public String updateEntity(TmVehicleClassifyVO entity)
			throws AppBusinessException {

		try {
			TmVehicleClassifyVO oldEntity = dao.getTmVehicleClassifyVo(entity
					.getPk_vehicleclassify());

			// 重复校验
			// 1 - 品牌发生改变， 相当于重新新建了一个车辆类别
			int duplicateSize = 0;
			if (!oldEntity.getPk_brand().equals(entity.getPk_brand())) {
				duplicateSize = 0;
			}
			// 2 - 品牌没有改变，有两个情况: 1. 都改变  2. 改变1个或0个
			else if (!oldEntity.getVclasscode().equals(entity.getVclasscode())
					&& !oldEntity.getVclassname().equals(entity.getVclassname())) {
				duplicateSize = 0;
			} else {
				duplicateSize = 1;
			}

//			String sqlCondition = " (vclassname='" + entity.getVclassname()
//					+ "' OR vclasscode='" + entity.getVclasscode()
//					+ "') AND pk_brand='" + entity.getPk_brand() + "' ";
			String sqlCondition = " (vclassname=?  OR vclasscode=?) AND pk_brand=? ";
			SQLParameter para = new SQLParameter();
			para.addParam(entity.getVclassname());
			para.addParam(entity.getVclasscode());
			para.addParam(entity.getPk_brand());
			
			
			List<TmVehicleClassifyVO> duplicateClassify = dao
					.findVehicleClassifyByClause(sqlCondition, para);
			
			if (duplicateClassify.size() > duplicateSize) {
				throw new AppBusinessException("同一品牌下，车辆类别编码或名称不能重复！");
			}
			

			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			entity.setModifiedtime(sdf.format(new Date()));
			entity.setModifier(AppInvocationInfoProxy.getPk_User());
			dao.updateTmVehicleClassifyVo(entity);
		} catch (DAOException e) {
			e.printStackTrace();
			throw new AppBusinessException(e.getMessage());
		}
		return "success";
	}

	/**
	 * @author 
	 * @date 2016年12月30日
	 * @param entityLst
	 * @return
	 * @throws AppBusinessException
	 *             (non-Javadoc)
	 * @see com.yonyou.iuap.crm.basedata.service.itf.ITmVehicleClassifyService#batchUpdateEntity(java.util.List)
	 */

	@Override
	@Transactional
	public String batchUpdateEntity(List<TmVehicleClassifyVO> entityLst)
			throws AppBusinessException {

		try {
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			for (TmVehicleClassifyVO entity : entityLst) {
				entity.setModifiedtime(sdf.format(new Date()));
				entity.setModifier(AppInvocationInfoProxy.getPk_User());
			}

			dao.batchUpdateTmVehicleClassifyVo(entityLst);
		} catch (DAOException e) {
			throw new AppBusinessException(e.getMessage());
		}
		return "success";
	}

	/**
	 * 删除车辆分类实体
	 * 
	 * @author 
	 * @date 2016年11月21日
	 * @param entity
	 * @throws BusinessException
	 *             (non-Javadoc)
	 * @see com.yonyou.iuap.crm.basedata.service.itf.ITmVehicleClassifyService#deleteTmVehicleClassifyVo(com.yonyou.iuap.crm.basedata.entity.TmVehicleClassifyVO)
	 */
	@Override
	@Transactional
	public void deleteTmVehicleClassifyVo(TmVehicleClassifyVO entity)
			throws AppBusinessException {
		try {
			dao.deleteTmVehicleClassifyVo(entity);
		} catch (DAOException e) {
			e.printStackTrace();
			throw new AppBusinessException(e.getMessage());
		}
	}

	/**
	 * 根据Id， 获取车辆分类实体
	 * 
	 * @author 
	 * @date 2016年11月21日
	 * @param id
	 * @return
	 * @throws BusinessException
	 *             (non-Javadoc)
	 * @see com.yonyou.iuap.crm.basedata.service.itf.ITmVehicleClassifyService#getTmVehicleClassifyVo(java.lang.String)
	 */
	@Override
	public TmVehicleClassifyVO getTmVehicleClassifyVo(String id)
			throws AppBusinessException {
		try {
			return dao.getTmVehicleClassifyVo(id);
		} catch (DAOException e) {
			e.printStackTrace();
			throw new AppBusinessException(e.getMessage());
		}
	}

	/**
	 * 根据Id，删除车辆分类实体
	 * 
	 * @author 
	 * @date 2016年11月21日
	 * @param id
	 * @throws BusinessException
	 *             (non-Javadoc)
	 * @see com.yonyou.iuap.crm.basedata.service.itf.ITmVehicleClassifyService#deleteById(java.lang.String)
	 */
	@Override
	@Transactional
	public void deleteById(String id) throws AppBusinessException {
		try {
			dao.deleteById(id);
		} catch (DAOException e) {
			e.printStackTrace();
			throw new AppBusinessException(e.getMessage());
		}
	}

//	/**
//	 * 校验列数据是否重复
//	 * @author 
//	 * @date 2016年11月21日
//	 * @param columnName
//	 * @param value
//	 * @return (non-Javadoc)
//	 * @see com.yonyou.iuap.crm.basedata.service.itf.ITmBrandService#checkPropertyDuplicated(java.lang.String,
//	 *      java.lang.String)
//	 */
//
//	@Override
//	public boolean checkPropertyDuplicated(String column, String value)
//			throws AppBusinessException {
//		try {
//			return dao.check(column, value);
//
//		} catch (DAOException e) {
//			e.printStackTrace();
//			throw new AppBusinessException(e.getMessage());
//		}
//	}

	/**
	 * 校验车辆类别必填字段， 编码和名称
	 * 
	 * @author 
	 * @date 2016年11月23日
	 * @param entity
	 * @throws AppBusinessException
	 */
	private void checkPropertyValidation(TmVehicleClassifyVO entity)
			throws AppBusinessException {
		if (StringUtil.isEmptyWithTrim(entity.getVclasscode())
				|| StringUtil.isEmptyWithTrim(entity.getVclassname())) {
			throw new AppBusinessException("车辆类别编码和名称不能为空！");
		}
	}
	/**
	 * 手机端获取车辆分类信息
	 * @return
	 * @throws BusinessException
	 *             (non-Javadoc)
	 * @see com.yonyou.iuap.crm.basedata.service.itf.ITmVehicleClassifyService#getTmVehicleClassifysBypage(java.util.Map,
	 *      org.springframework.data.domain.PageRequest)
	 */
	@Override
	public List<Map<String,Object>> queryTmVehicleClassifys()
			throws AppBusinessException {
		try {
			return dao.queryTmVehicleClassifys();
		} catch (DAOException e) {
			// TODO 自动生成的 catch 块
			e.printStackTrace();
			throw new AppBusinessException(e.getMessage());
		}
	}
}
