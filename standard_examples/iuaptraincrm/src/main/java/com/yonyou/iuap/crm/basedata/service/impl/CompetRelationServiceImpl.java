package com.yonyou.iuap.crm.basedata.service.impl;

import java.text.SimpleDateFormat;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springside.modules.utils.Clock;

import com.yonyou.iuap.crm.basedata.entity.TmCompetRelationVO;
import com.yonyou.iuap.crm.basedata.entity.TmModelCompetExtVO;
import com.yonyou.iuap.persistence.bs.dao.DAOException;
import com.yonyou.iuap.persistence.jdbc.framework.SQLParameter;
import com.yonyou.iuap.crm.basedata.repository.itf.ICompetRelationDao;
import com.yonyou.iuap.crm.basedata.service.itf.ICompetRelationService;
import com.yonyou.iuap.crm.common.exception.AppBusinessException;
import com.yonyou.iuap.crm.common.utils.AppInvocationInfoProxy;
import com.yonyou.iuap.crm.common.utils.AppTools;

@Service
public class CompetRelationServiceImpl implements ICompetRelationService {
	private SimpleDateFormat dataFormat = new SimpleDateFormat(
			"yyyy-MM-dd HH:MM:ss");

	@Autowired
	private ICompetRelationDao dao;
	private Clock clock = Clock.DEFAULT;

	/**
	 * 保存竞品-对比关系
	 * 
	 * @author 
	 * @date Dec 2, 2016
	 * @param entities
	 * @throws AppBusinessException
	 *             (non-Javadoc)
	 * @see com.yonyou.iuap.crm.basedata.service.itf.ICompetRelationService#saveCompetRelation(List)
	 */
	@Override
	public void saveCompetRelation(List<TmModelCompetExtVO> entities)
			throws AppBusinessException {
		try {
			String pk;
			List<TmCompetRelationVO> competRels = new LinkedList<TmCompetRelationVO>();
			for (TmModelCompetExtVO entity : entities) {
				TmCompetRelationVO tmp = new TmCompetRelationVO();
				pk = AppTools.generatePK();
				tmp.setPk_competrelation(pk);
				tmp.setCreator(AppInvocationInfoProxy.getPk_User());
				tmp.setPk_competbrand(entity.getPk_competbrand());
				tmp.setNvbatterypower(entity.getNvpower());//设置电量
				tmp.setPk_model(entity.getPk_model());
				tmp.setDr(0);
				tmp.setCreationtime(dataFormat.format(clock.getCurrentDate()));
				competRels.add(tmp);
			}
			dao.saveCompetRelation(competRels);
		} catch (Exception e) {
			throw new AppBusinessException(e.getMessage());
		}
	}

	/**
	 * 删除竞品-对比关系，逻辑删除
	 * 
	 * @author 
	 * @date Dec 2, 2016
	 * @param searchParams
	 * @throws AppBusinessException
	 *             (non-Javadoc)
	 * @see com.yonyou.iuap.crm.basedata.service.itf.ICompetRelationService#deleteCompetRelation(Map)
	 */
	@Override
	@Transactional
	public void deleteCompetRelation(Map<String, Object> searchParams)
			throws AppBusinessException {
		try {
			StringBuffer condition = new StringBuffer();
			Object pk_competbrand = searchParams.get("pk_competbrand");
			Object pk_model = searchParams.get("pk_model");// 获取到的是一个数组，包含中括号，逗号分隔
			condition.append(" pk_competbrand = '")
					.append(pk_competbrand.toString()).append("'");
			condition.append(" and pk_model in (").append(pk_model.toString())
					.append(") ");
			dao.deleteCompetRelation(dao.findCompetRelationByCause(condition
					.toString()));
		} catch (DAOException e) {
			throw new AppBusinessException(e.getMessage());
		}
	}

	/**
	 * 获取某个竞品下对比车型列表信息
	 * 
	 * @author 
	 * @date Dec 2, 2016
	 * @return
	 * @throws AppBusinessException
	 *             (non-Javadoc)
	 * @see com.yonyou.iuap.crm.basedata.service.itf.ICompetRelationService#getRelatedModels()
	 */
	@Override
	public Page<TmModelCompetExtVO> getRelatedModels(
			Map<String, Object> searchParams, PageRequest pageRequest)
			throws AppBusinessException {
		StringBuffer condition = new StringBuffer();
		SQLParameter sqlParameter = new SQLParameter();
		StringBuffer sql = new StringBuffer();
		try {
			Object pk_competbrand = searchParams.get("pk_competbrand");// 根据竞争品牌
			if (null != pk_competbrand && !pk_competbrand.toString().equals("")) {
				condition
						.append(" and tc.pk_competbrand  = ?");
				sqlParameter.addParam(pk_competbrand.toString());
			}
			sql.append(
					"SELECT tm.*,tc.pk_competbrand,tc.nvbatterypower AS nvpower ")
					.append("FROM tm_competrelation AS tc ")
					.append("LEFT JOIN tm_model AS tm ON tc.pk_model=tm.pk_model AND tm.dr=0 ")
					.append("WHERE tc.dr=0 ");
			return dao.queryModelExtPage(condition.toString(), sqlParameter,
					pageRequest,sql.toString());
		} catch (Exception e) {
			throw new AppBusinessException(e.getMessage());
		}
	}

}
