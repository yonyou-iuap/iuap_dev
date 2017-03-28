package com.yonyou.iuap.crm.basedata.service.impl;

import com.yonyou.iuap.crm.basedata.entity.TmCompetPriceVO;
import com.yonyou.iuap.persistence.bs.dao.DAOException;
import com.yonyou.iuap.persistence.jdbc.framework.SQLParameter;
import com.yonyou.iuap.crm.basedata.repository.itf.ICompetPriceDao;
import com.yonyou.iuap.crm.basedata.service.itf.ICompetPriceService;
import com.yonyou.iuap.crm.common.exception.AppBusinessException;
import com.yonyou.iuap.crm.common.utils.AppInvocationInfoProxy;
import com.yonyou.iuap.crm.common.utils.AppTools;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springside.modules.utils.Clock;

import java.text.SimpleDateFormat;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * 竞品价格信息Service接口实现类 实现了竞品价格信息的查询、保存和删除业务
 * 
 * @author 
 * @date Nov 24, 2016
 */
@Service
public class CompetPriceServiceImpl implements ICompetPriceService {
	private SimpleDateFormat dataFormat = new SimpleDateFormat(
			"yyyy-MM-dd HH:MM:ss");

	@Autowired
	private ICompetPriceDao dao;
	private Clock clock = Clock.DEFAULT;

	/**
	 * 查询竞品价格信息，带查询条件和分页
	 * 
	 * @author 
	 * @date Nov 21, 2016
	 * @param searchParams
	 * @param pageRequest
	 * @return
	 * @throws AppBusinessException
	 *             (non-Javadoc)
	 * @see com.yonyou.iuap.crm.basedata.service.itf.ICompetPriceService#getCompetPricesBypage(java.util.Map,
	 *      org.springframework.data.domain.PageRequest)
	 */
	@Override
	public Page<TmCompetPriceVO> getCompetPricesBypage(
			Map<String, Object> searchParams, PageRequest pageRequest)
			throws AppBusinessException {
		StringBuffer condition = new StringBuffer();
		SQLParameter sqlParameter = new SQLParameter();
		try {
			Object pk_competbrand = searchParams.get("pk_competbrand");// 根据竞品Pk
			if (null != pk_competbrand && !pk_competbrand.toString().equals("")) {
				condition.append(" and pk_competbrand = ? ");
				sqlParameter.addParam(pk_competbrand.toString());
			}
			return dao.getCompetPricesBypage(condition.toString(),
					sqlParameter, pageRequest);
		} catch (Exception e) {
			throw new AppBusinessException(e.getMessage());
		}
	}

	/**
	 * 保存竞品价格信息 如果实体已存在ID值，执行更新操作，反之执行保存
	 * 
	 * @author 
	 * @date Nov 21, 2016
	 * @param entities
	 * @throws AppBusinessException
	 *             (non-Javadoc)
	 * @see com.yonyou.iuap.crm.basedata.service.itf.ICompetPriceService#saveEntities(java.util.List)
	 */
	@Override
	@Transactional
	public void saveEntities(List<TmCompetPriceVO> entities)
			throws AppBusinessException {
		String pk = AppTools.generatePK();
		String currentDate = dataFormat.format(clock.getCurrentDate());
		List<TmCompetPriceVO> toSaveList = new LinkedList<>();//用于保存
		List<TmCompetPriceVO> toUpdateLIst = new LinkedList<>();//用于更新
		for (int i = entities.size() - 1; i >= 0; i--) {
			TmCompetPriceVO tmp = entities.get(i);
			//如果主键非空，则为更新
			if (tmp.getPk_compet_price() != null) {
				toUpdateLIst.add(tmp);
			}
			else {
				tmp.setCreator(AppInvocationInfoProxy.getPk_User());
				tmp.setPk_compet_price(pk);
				tmp.setDr(0);
				tmp.setCreationtime(currentDate);
				pk = AppTools.generatePK();
				toSaveList.add(tmp);
			}
		}
		try {
			// 批量新增/更新
			if (toSaveList.size() > 0) {
				dao.saveCompetPrices(toSaveList);
			}
			if (toUpdateLIst.size() > 0) {
				dao.updateCompetPrice(toUpdateLIst);
			}
		} catch (DAOException e) {
			throw new AppBusinessException(e.getMessage());
		}
	}

	/**
	 * 删除竞品价格信息，逻辑删除
	 * 
	 * @author 
	 * @date Nov 21, 2016
	 * @param entity
	 * @throws AppBusinessException
	 *             (non-Javadoc)
	 * @see com.yonyou.iuap.crm.basedata.service.itf.ICompetPriceService#deleteCompetPriceByTS(com.yonyou.iuap.crm.basedata.entity.TmCompetPriceVO)
	 */
	@Override
	@Transactional
	public void deleteCompetPriceByTS(TmCompetPriceVO entity)
			throws AppBusinessException {
		try {
			dao.deleteCompetPriceByTS(entity);
		} catch (DAOException e) {
			throw new AppBusinessException(e.getMessage());
		}
	}

	/**
	 * 删除竞品价格信息，物理删除
	 * 
	 * @author 
	 * @date Nov 21, 2016
	 * @param entities
	 * @throws AppBusinessException
	 *             (non-Javadoc)
	 * @see com.yonyou.iuap.crm.basedata.service.itf.ICompetPriceService#deleteCompetPrice(List)
	 */
	@Override
	@Transactional
	public void deleteCompetPrice(List<TmCompetPriceVO> entities)
			throws AppBusinessException {
		// 先做空主键的校验
		for (TmCompetPriceVO entity : entities) {
			if (entity.getPk_competbrand() == null
					|| entity.getPk_competbrand().trim().length() == 0) {
				throw new AppBusinessException("pk为空");
			}
		}
		try {
			dao.deleteCompetPrice(entities);
		} catch (DAOException e) {
			throw new AppBusinessException(e.getMessage());
		}
	}

}
