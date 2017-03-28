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

import com.yonyou.iuap.crm.basedata.entity.TmBrandVO;
import com.yonyou.iuap.persistence.bs.dao.DAOException;
import com.yonyou.iuap.persistence.jdbc.framework.SQLParameter;
import com.yonyou.iuap.persistence.vo.pub.util.StringUtil;
import com.yonyou.iuap.crm.basedata.repository.itf.ITmBrandDao;
import com.yonyou.iuap.crm.basedata.service.itf.ITmBrandService;
import com.yonyou.iuap.crm.common.exception.AppBusinessException;
import com.yonyou.iuap.crm.common.utils.AppInvocationInfoProxy;
import com.yonyou.iuap.crm.common.utils.AppTools;
import com.yonyou.iuap.crm.common.utils.DictCode;

/**
 * 
 * 品牌信息业务逻辑处理
 * 
 * @author 
 * @date 2016年11月21日
 */
@Service
public class TmBrandServiceImpl implements ITmBrandService {
	@Autowired
	private ITmBrandDao dao;

	/**
	 * 获取分页数据， 可设置检索条件，分页模式
	 * @author 
	 * @date 2016年11月21日
	 * @param searchParams
	 * @param pageRequest
	 * @return
	 * @throws AppBusinessException
	 *             (non-Javadoc)
	 * @see com.yonyou.iuap.crm.basedata.service.itf.ITmBrandService#getTmBrandsBypage(java.util.Map,
	 *      org.springframework.data.domain.PageRequest)
	 */
	@Override
	public Page<TmBrandVO> getTmBrandsBypage(Map<String, Object> parammap,
			PageRequest pageRequest) throws AppBusinessException {
		StringBuffer condition = new StringBuffer();
		SQLParameter sqlParameter = new SQLParameter();
		
		//	拼接查询条件参数
		//  参数： 品牌编码 vbrandcode 和 品牌名称 vbrandname
		if (null != parammap.get("vbrandcode")
				&& !parammap.get("vbrandcode").toString().equals("")) {
			condition.append(" and vbrandcode like ? ");
			sqlParameter.addParam("%" + parammap.get("vbrandcode").toString()
					+ "%");
		}
		if (null != parammap.get("vbrandname")
				&& !parammap.get("vbrandname").toString().equals("")) {
			condition.append(" and vbrandname like ? ");
			sqlParameter.addParam("%" + parammap.get("vbrandname") + "%");
		}
		if (null != parammap.get("vstatus")
				&& !parammap.get("vstatus").toString().equals("")) {
			condition.append(" and vstatus = ? ");
			sqlParameter.addParam(parammap.get("vstatus"));
		}
		
		// 添加引用品牌时的过滤检索
		if(null!= parammap.get("condition")&&!"".equals(parammap.get("condition"))){
			condition.append(" ").append("and (vbrandname like").append(" ").append("?").append(" ").append("||").append(" vbrandcode like").append(" ").append(" ? )");
			sqlParameter.addParam("%"+parammap.get("condition")+ "%");
			sqlParameter.addParam("%"+parammap.get("condition")+ "%");
		}

		try {
			return dao.getTmBrandsBypage(condition.toString(), sqlParameter,
					pageRequest);
		} catch (DAOException e) {
			e.printStackTrace();
			throw new AppBusinessException(e.getMessage());
		}

	}

	/**
	 * 保存品牌信息数据
	 * @author 
	 * @date 2016年11月21日
	 * @param entity
	 * @return
	 * @throws AppBusinessException
	 *             (non-Javadoc)
	 * @see com.yonyou.iuap.crm.basedata.service.itf.ITmBrandService#saveEntity(com.yonyou.iuap.crm.basedata.entity.TmBrandVO)
	 */
	@Override
	@Transactional
	public String saveEntity(TmBrandVO entity) throws AppBusinessException {
		Date d = new Date();
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:MM:ss");
		String datestr = df.format(d);						// 创建时间
		
		try {

			// 无重复，则保存
			String pk = AppTools.generatePK();
			entity.setPk_brand(pk);
			entity.setPk_org(AppInvocationInfoProxy.getPk_Corp());
			entity.setDr(0);
			entity.setCreationtime(datestr);
			entity.setVstatus(DictCode.ALREADY_START_STATUS);
			entity.setCreator(AppInvocationInfoProxy.getPk_User());
			return dao.saveTmBrandVo(entity);
		} catch (DAOException e) {
			e.printStackTrace();
			throw new AppBusinessException(e.getMessage());

		}

	}

	/**
	* @author 
	* @date 2016年12月30日
	* @param entity
	* @return
	* @throws AppBusinessException
	* (non-Javadoc)
	* @see com.yonyou.iuap.crm.basedata.service.itf.ITmBrandService#batchUpdateEntity(java.util.List)
	*/
		
	@Override
	@Transactional
	public String batchUpdateEntity(List<TmBrandVO> entityLst)
			throws AppBusinessException {
		try {
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			
			for(TmBrandVO entity : entityLst) {
				entity.setModifier(AppInvocationInfoProxy.getPk_User());
				entity.setModifiedtime(sdf.format(new Date()));
			}
			
			dao.batchUpdateTmBrandVo(entityLst);
		} catch (DAOException e) {
			e.printStackTrace();
			throw new AppBusinessException(e.getMessage());
		}
		return "success";
	}
	
	
	/**
	 * 更新品牌信息数据
	 * @author 
	 * @date 2016年11月21日
	 * @param entity
	 * @return
	 * @throws AppBusinessException
	 *             (non-Javadoc)
	 * @see com.yonyou.iuap.crm.basedata.service.itf.ITmBrandService#updateEntity(com.yonyou.iuap.crm.basedata.entity.TmBrandVO)
	 */
	@Override
	@Transactional
	public String updateEntity(TmBrandVO entity) throws AppBusinessException {
		
		try {
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			// 无重复， 实行更新
			entity.setModifier(AppInvocationInfoProxy.getPk_User());
			entity.setModifiedtime(sdf.format(new Date()));
			dao.updateTmBrandVo(entity);
		} catch (DAOException e) {
			e.printStackTrace();
			throw new AppBusinessException(e.getMessage());
		}
		return "success";
	}

	/**
	 * 删除某品牌信息
	 * @author 
	 * @date 2016年11月21日
	 * @param entity
	 * @throws AppBusinessException
	 *             (non-Javadoc)
	 * @see com.yonyou.iuap.crm.basedata.service.itf.ITmBrandService#deleteTmBrandVo(com.yonyou.iuap.crm.basedata.entity.TmBrandVO)
	 */
	@Override
	@Transactional
	public void deleteTmBrandVo(TmBrandVO entity) throws AppBusinessException {
		try {
			dao.deleteTmBrandVo(entity);
		} catch (DAOException e) {
			e.printStackTrace();
			throw new AppBusinessException(e.getMessage());
		}
	}

	/**
	 * 根据Id，获取品牌信息
	 * @author 
	 * @date 2016年11月21日
	 * @param id
	 * @return
	 * @throws AppBusinessException
	 *             (non-Javadoc)
	 * @see com.yonyou.iuap.crm.basedata.service.itf.ITmBrandService#getTmBrandVo(java.lang.String)
	 */
	@Override
	public TmBrandVO getTmBrandVo(String id) throws AppBusinessException {
		try {
			return dao.getTmBrandVo(id);
		} catch (DAOException e) {
			e.printStackTrace();
			throw new AppBusinessException(e.getMessage());
		}
	}

	/**
	 * 根据Id, 删除品牌信息
	 * @author 
	 * @date 2016年11月21日
	 * @param id
	 * @throws AppBusinessException
	 *             (non-Javadoc)
	 * @see com.yonyou.iuap.crm.basedata.service.itf.ITmBrandService#deleteById(java.lang.String)
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
	* 校验品牌必填字段， 编码和名称
	* @author 
	* @date 2016年11月23日
	* @param entity
	* @throws AppBusinessException
	 */
	private void checkPropertyValidation(TmBrandVO entity) throws AppBusinessException {
		if(StringUtil.isEmptyWithTrim(entity.getVbrandcode()) || StringUtil.isEmptyWithTrim(entity.getVbrandname())) {
			throw new AppBusinessException("品牌编码和名称不能为空！");
		}
	}

	
	

}
