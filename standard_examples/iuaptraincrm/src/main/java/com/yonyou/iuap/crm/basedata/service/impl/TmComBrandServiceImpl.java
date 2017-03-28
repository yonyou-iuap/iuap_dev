package com.yonyou.iuap.crm.basedata.service.impl;

import com.yonyou.iuap.crm.basedata.entity.TmComBrandVO;
import com.yonyou.iuap.persistence.bs.dao.DAOException;
import com.yonyou.iuap.persistence.jdbc.framework.SQLParameter;
import com.yonyou.iuap.crm.basedata.repository.itf.ITmComBrandDao;
import com.yonyou.iuap.crm.basedata.service.itf.ITmComBrandService;
import com.yonyou.iuap.crm.billcode.service.itf.ICommonComponentsService;
import com.yonyou.iuap.crm.common.exception.AppBusinessException;
import com.yonyou.iuap.crm.common.utils.AppInvocationInfoProxy;
import com.yonyou.iuap.crm.common.utils.AppTools;
import com.yonyou.iuap.crm.common.utils.OrderPrefix;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;

@Service
public class TmComBrandServiceImpl implements ITmComBrandService {

	@Autowired
	private ITmComBrandDao dao;
	@Autowired
	private ICommonComponentsService commonService;
	
	@Override
	public Page<TmComBrandVO> getTmBrandsBypage(Map<String, Object> searchParams,
			PageRequest pageRequest) throws AppBusinessException {
		StringBuffer condition = new StringBuffer();
		SQLParameter sqlParameter = new SQLParameter();
		
		//	拼接查询条件参数
		//  参数： 品牌编码 vbrandcode 和 品牌名称 vbrandname
		if (null != searchParams.get("vcbrandcode")
				&& !searchParams.get("vcbrandcode").toString().equals("")) {
			condition.append(" and vcbrandcode like ? ");
			sqlParameter.addParam("%" + searchParams.get("vcbrandcode").toString()
					+ "%");
		}
		if (null != searchParams.get("vcbrandname")
				&& !searchParams.get("vcbrandname").toString().equals("")) {
			condition.append(" and vcbrandname like ? ");
			sqlParameter.addParam("%" + searchParams.get("vcbrandname") + "%");
		}
		
		if (null != searchParams.get("vstatus")
				&& !searchParams.get("vstatus").toString().equals("")) {
			condition.append(" and vstatus = ? ");
			sqlParameter.addParam(searchParams.get("vstatus"));
		}
		
		// 添加引用品牌时的过滤检索
		if(null!= searchParams.get("condition")&&!"".equals(searchParams.get("condition"))){
			condition.append(" ").append("and (vcbrandname like").append(" ").append("?").append(" ").append("||").append(" vcbrandcode like").append(" ").append(" ? )");
			sqlParameter.addParam("%"+searchParams.get("condition")+ "%");
			sqlParameter.addParam("%"+searchParams.get("condition")+ "%");
		}

		try {
			return dao.getTmBrandsBypage(condition.toString(), sqlParameter,
					pageRequest);
		} catch (DAOException e) {
			e.printStackTrace();
			throw new AppBusinessException(e.getMessage());
		}
	}

	@Override
	public String saveEntity(TmComBrandVO entity) throws AppBusinessException {
		Date d = new Date();
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:MM:ss");
		String datestr = df.format(d);						// 创建时间
		StringBuilder errMessage = new StringBuilder(); 	// 错误信息
		boolean duplicated = false; 						// 品牌编码、米恩才是否重复
		try {
			/*// 校验品牌编码是否重复
			if (checkPropertyDuplicated("vcbrandcode", entity.getVcbrandcode())) {
				duplicated = true;
				errMessage.append("品牌编码已存在，请选择其他编码！");
			}*/
			// 校验品牌名称是否重复
			if (checkPropertyDuplicated("vcbrandname", entity.getVcbrandname())) {
				duplicated = true;
				errMessage.append("品牌名称已存在，请选择其他名称！");
			}
			// 如果重复，则抛出异常提示
			if (duplicated) {
				throw new AppBusinessException(errMessage.toString());
			}
			// 无重复，则保存
			String pk = AppTools.generatePK();
			entity.setPk_combrand(pk);
			entity.setDr(0);
			entity.setVcbrandcode(commonService.generateOrderNo(OrderPrefix.CB));//生成流水号
			entity.setCreator(AppInvocationInfoProxy.getPk_User());
			entity.setPk_org(AppInvocationInfoProxy.getPk_Corp());//设置组织ID
			entity.setCreationtime(datestr);
			return dao.saveTmComBrandVO(entity);
		} catch (DAOException e) {
			e.printStackTrace();
			throw new AppBusinessException(e.getMessage());

		}
	}

	@Override
	public String updateEntity(TmComBrandVO entity) throws AppBusinessException {
		StringBuilder errMessage = new StringBuilder(); // 错误信息
		boolean duplicated = false;						// 名称是否重复

		try {
			//	获取当前需要更新的实体
			TmComBrandVO tmp = dao.getTmComBrandVO(entity.getPk_combrand());
			/*// 改变品牌编码时， 校验品牌编码是否重复
			if (!tmp.getVcbrandcode().equals(entity.getVcbrandcode())) {
				if (checkPropertyDuplicated("vcbrandcode",
						entity.getVcbrandcode())) {
					duplicated = true;
					errMessage.append("品牌编码已存在，请选择其他编码！");
				}
			}*/
			// 改变品牌名称时， 校验品牌名称是否重复
			if (!tmp.getVcbrandname().equals(entity.getVcbrandname())) {
				if (checkPropertyDuplicated("vcbrandname",
						entity.getVcbrandname())) {
					duplicated = true;
					errMessage.append("品牌名称已存在，请选择其他名称！");
				}
			}
			// 如果重复, 抛出提示异常
			if (duplicated) {
				throw new AppBusinessException(errMessage.toString());
			}
			// 无重复， 实行更新
			dao.updateTmComBrandVO(entity);
		} catch (DAOException e) {
			e.printStackTrace();
			throw new AppBusinessException(e.getMessage());
		}
		return "success";
	}

	@Override
	public void deleteTmBrandVo(TmComBrandVO entity)
			throws AppBusinessException {
		try {
			dao.deleteTmComBrandVO(entity);
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
	public TmComBrandVO getTmComBrandVo(String id) throws AppBusinessException {
		try {
			return dao.getTmComBrandVO(id);
		} catch (DAOException e) {
			e.printStackTrace();
			throw new AppBusinessException(e.getMessage());
		}
	}

	@Override
	public void deleteById(String id) throws AppBusinessException {
		try {
			dao.deleteById(id);
		} catch (DAOException e) {
			e.printStackTrace();
			throw new AppBusinessException(e.getMessage());
		}

	}

	@Override
	public boolean checkPropertyDuplicated(String column, String value)
			throws AppBusinessException {
		try {
			return dao.check(column, value);

		} catch (DAOException e) {
			e.printStackTrace();
			throw new AppBusinessException(e.getMessage());
		}
	}

}
