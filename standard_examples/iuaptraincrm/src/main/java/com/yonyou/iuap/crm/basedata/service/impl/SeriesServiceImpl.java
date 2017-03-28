/**
 * @author lyp
 *
 */
package com.yonyou.iuap.crm.basedata.service.impl;

import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.yonyou.iuap.crm.basedata.entity.SeriesExtVO;
import com.yonyou.iuap.crm.basedata.entity.SeriesVO;
import com.yonyou.iuap.persistence.bs.dao.DAOException;
import com.yonyou.iuap.persistence.jdbc.framework.SQLParameter;
import com.yonyou.iuap.persistence.vo.pub.VOStatus;
import com.yonyou.iuap.crm.basedata.repository.itf.ISeriesDao;
import com.yonyou.iuap.crm.basedata.service.itf.ISeriesService;
import com.yonyou.iuap.crm.common.exception.AppBusinessException;
import com.yonyou.iuap.crm.common.utils.AppTools;

/**
 * 车系信息业务逻辑处理
 * 
 * @author 
 * @date 2016年11月22日
 * @version 1.0
 */
@Service
public class SeriesServiceImpl implements ISeriesService {
	@Autowired
	private ISeriesDao seriesdao;

	/**
	 * 查询车系信息11
	 * 
	 * @author 
	 * @date 2016年11月22日
	 * @param parammap
	 * @param pageRequest
	 * @return
	 * @throws AppBusinessException
	 */
	@Override
	public Page<SeriesExtVO> getSeriesBypage(Map<String, Object> parammap,
			PageRequest pageRequest) throws AppBusinessException {
		// TODO 自动生成的方法存根
		StringBuffer condition = new StringBuffer();
		SQLParameter sqlParameter = new SQLParameter();
		if (null != parammap.get("vseriescode")
				&& !parammap.get("vseriescode").toString().equals("")) {
			condition.append(" and series.vseriescode like ? ");
			sqlParameter.addParam("%" + parammap.get("vseriescode").toString()
					+ "%");
		}
		if (null != parammap.get("vseriesname")
				&& !parammap.get("vseriesname").toString().equals("")) {
			condition.append(" and series.vseriesname like ? ");
			sqlParameter.addParam("%" + parammap.get("vseriesname").toString()
					+ "%");
		}
		if (null != parammap.get("pk_vehicleclassify")
				&& !parammap.get("pk_vehicleclassify").toString().equals("")) {
			condition.append(" and vehicleclass.pk_vehicleclassify = ? ");
			sqlParameter.addParam(parammap.get("pk_vehicleclassify").toString());
		}
		if (null != parammap.get("pk_brand")
				&& !parammap.get("pk_brand").toString().equals("")) {
			condition.append(" and brand.pk_brand = ? ");
			sqlParameter.addParam(parammap.get("pk_brand").toString());
		}
		if (null != parammap.get("vstatus")
				&& !parammap.get("vstatus").toString().equals("")) {
			condition.append(" and series.vstatus like ? ");
			sqlParameter.addParam("%" + parammap.get("vstatus").toString()
					+ "%");
		}
		
		// 添加引用车辆类别时的过滤检索
		if (null != parammap.get("condition")
				&& !"".equals(parammap.get("condition"))) {
			condition.append(" ").append("and (series.vseriesname like").append(" ")
					.append("?").append(" ").append("||")
					.append(" series.vseriescode like").append(" ").append(" ? )");
			sqlParameter.addParam("%" + parammap.get("condition") + "%");
			sqlParameter.addParam("%" + parammap.get("condition") + "%");
		}

		try {
			return seriesdao.getSeriesBypage(condition.toString(),
					sqlParameter, pageRequest);
		} catch (DAOException e) {
			// TODO 自动生成的 catch 块
			throw new AppBusinessException(e.getMessage());
		}
	}

	/**
	 * 保存车系信息
	 * 
	 * @author 
	 * @date 2016年11月22日
	 * @param pavo
	 * @return
	 * @throws AppBusinessException
	 */
	@Override
	@Transactional
	public String saveSeries(SeriesExtVO pavo) throws AppBusinessException {
		// TODO 自动生成的方法存根
		try {
			// 根据车系编码查找实体
			SQLParameter sqlParameter = new SQLParameter();
			String msg = "";
			String condition ="  vseriescode = ?  ";
			sqlParameter.addParam(pavo.getVseriescode().toString());
			List<SeriesVO> voList = seriesdao.findListByClauseWithDR(
					SeriesExtVO.class, sqlParameter,condition);
			// 如果vo中pk为空则新增，否则为修改操作
			if (null == pavo.getPk_series() || pavo.getPk_series().equals("")) {
				// 生成主键
				String pk = AppTools.generatePK();
				pavo.setPk_series(pk);
				if (voList.size() < 1) {// 当根据车系编码查询出来的实体小于1
					pavo.setStatus(VOStatus.NEW);// 新增
					pavo.setVstatus("10051001");
					return seriesdao.saveSeries(pavo, "1");
				} else {
					msg = "车系编码已经存在，请重新输入！";
					throw new AppBusinessException(msg);
				}
			} else if (voList.size() < 1
					|| (voList.size() == 1 && voList.get(0).getPk_series()
							.equals(pavo.getPk_series()))) {// 当为修改时，1、根据车系编码查询出来的实体不存在
															// 2、实体有一个并且两个主键相同（就是同一个）
				pavo.setStatus(VOStatus.UPDATED);// 修改
				return seriesdao.saveSeries(pavo, "2");
			} else {
				msg = "车系编码已经存在，请重新输入！";
				throw new AppBusinessException(msg);
			}

		} catch (DAOException e) {
			// TODO 自动生成的 catch 块
			throw new AppBusinessException(e.getMessage());

		}
	}

	/**
	 * 删除车系信息（逻辑删除）
	 * 
	 * @author 
	 * @date 2016年11月22日
	 * @param entity
	 * @throws AppBusinessException
	 */
	@Override
	@Transactional
	public void deleteSeriesByIdTS(SeriesExtVO entity)
			throws AppBusinessException {
		/**
		 * 添加业务逻辑
		 */

		if (StringUtils.isEmpty(entity.getPk_series())) {
			throw new AppBusinessException("参数为空");
		}
		try {

			seriesdao.deleteSeriesByIdTS(entity);
		} catch (DAOException e) {
			// TODO 自动生成的 catch 块
			throw new AppBusinessException(e.getMessage());
		}
	}

	/**
	 * 通过车系ID查找车系
	 * 
	 * @author 
	 * @date 2016年11月30日
	 * @param seriesId
	 * @return
	 * @throws AppBusinessException
	 *             (non-Javadoc)
	 * @see com.yonyou.iuap.crm.basedata.service.itf.ISeriesService#getSeriesById(java.lang.String)
	 */
	@Override
	@Transactional
	public SeriesExtVO getSeriesById(String seriesId)
			throws AppBusinessException {
		try {
			return seriesdao.getSeriesById(seriesId);
		} catch (DAOException e) {
			throw new AppBusinessException(e.getMessage());
		}

	}

	/**
	 * 更新车系信息
	 * 
	 * @author 
	 * @date 2016年11月30日
	 * @param entity
	 * @throws AppBusinessException
	 *             (non-Javadoc)
	 * @see com.yonyou.iuap.crm.basedata.service.itf.ISeriesService#updateEntity(com.yonyou.iuap.crm.basedata.entity.SeriesVO)
	 */
	@Override
	@Transactional
	public void updateEntity(SeriesExtVO entity) throws AppBusinessException {
		// TODO 自动生成的方法存根
		try {
			seriesdao.updateSeriesVo(entity);
		} catch (DAOException e) {
			// TODO 自动生成的 catch 块
			e.printStackTrace();
		}
	}

	/**
	 * 通过车系编码查找车系
	 * 
	 * @author 
	 * @date 2016年12月1日
	 * @param pavo
	 * @param condition
	 * @throws AppBusinessException
	 *             (non-Javadoc)
	 * @see com.yonyou.iuap.crm.basedata.service.itf.ISeriesService#findListByClauseWithDR(com.yonyou.iuap.crm.basedata.entity.SeriesExtVO,
	 *      java.lang.String)
	 */
	@Override
	@Transactional
	public void findListByClauseWithDR(SeriesExtVO pavo, String condition)
			throws AppBusinessException {
		// TODO 自动生成的方法存根
		SQLParameter sqlParameter = new SQLParameter();
		String conditionsql = "vseriescode = ?" ;
		sqlParameter.addParam(pavo.getVseriescode().toString());
		try {
			seriesdao.findListByClauseWithDR(SeriesExtVO.class, sqlParameter,conditionsql);
		} catch (DAOException e) {
			// TODO 自动生成的 catch 块
			e.printStackTrace();
		}

	}

	/**
	 * 通过条件查询实体
	 * 
	 * @author 
	 * @date 2016年12月13日
	 * @param condition
	 * @return
	 * @throws AppBusinessException
	 *             (non-Javadoc)
	 * @see com.yonyou.iuap.crm.basedata.service.itf.ISeriesService#findListByClause(java.lang.String)
	 */
	@Override
	public List<SeriesExtVO> findListByClause(String condition)
			throws AppBusinessException {
		try {
			return seriesdao.findListByClause(condition);
		} catch (DAOException e) {
			throw new AppBusinessException(e.getMessage());
		}
	}

	/**
	 * 通过车辆类别主键带品牌名称
	 * 
	 * @author 
	 * @date 2016年12月27日
	 * @param sql
	 * @return
	 * @throws AppBusinessException
	 *             (non-Javadoc)
	 * @see com.yonyou.iuap.crm.basedata.service.itf.ISeriesService#getIntRefData(java.lang.String)
	 */
	@Override
	public List<Map<String, String>> getIntRefData(String sql)
			throws AppBusinessException {
		// TODO 自动生成的方法存根
		try {
			return seriesdao.getIntRefData(sql);
		} catch (DAOException e) {
			// TODO 自动生成的 catch 块
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 手机端查询车系信息
	 * 
	 * @param pk_vehicleclassify
	 *            类别pk
	 * @return
	 * @throws AppBusinessException
	 */
	@Override
	public List<Map<String, Object>> querySeries(String pk_vehicleclassify)
			throws AppBusinessException {
		// TODO 自动生成的方法存根
		try {
			return seriesdao.querySeries(pk_vehicleclassify);
		} catch (DAOException e) {
			// TODO 自动生成的 catch 块
			e.printStackTrace();
			return null;
		}
	}

	/**
	 * 手机端查询车系外观图片信息
	 * 
	 * @param pk_series
	 *            车系pk
	 * @return
	 * @throws AppBusinessException
	 */
	@Override
	public List<Map<String, Object>> queryOutTmeries(String pk_series)
			throws AppBusinessException {
		// TODO 自动生成的方法存根
		try {
			return seriesdao.queryOutTmeries(pk_series);
		} catch (DAOException e) {
			// TODO 自动生成的 catch 块
			e.printStackTrace();
			return null;
		}
	}

	/**
	 * 手机端查询车系内饰图片信息
	 * 
	 * @param pk_series
	 *            车系pk
	 * @return
	 * @throws AppBusinessException
	 */
	@Override
	public List<Map<String, Object>> queryInTmeries(String pk_series)
			throws AppBusinessException {
		// TODO 自动生成的方法存根
		try {
			return seriesdao.queryInTmeries(pk_series);
		} catch (DAOException e) {
			// TODO 自动生成的 catch 块
			e.printStackTrace();
			return null;
		}
	}

	/**
	 * 手机端查询车系缩略图片信息
	 * 
	 * @param pk_series
	 *            车系pk
	 * @return
	 * @throws AppBusinessException
	 */
	@Override
	public List<Map<String, Object>> queryThumbTmeries(String pk_series)
			throws AppBusinessException {
		// TODO 自动生成的方法存根
		try {
			return seriesdao.queryThumbTmeries(pk_series);
		} catch (DAOException e) {
			// TODO 自动生成的 catch 块
			e.printStackTrace();
			return null;
		}
	}

}
