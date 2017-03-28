package com.yonyou.iuap.crm.basedata.service.impl;

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
import org.springside.modules.utils.Clock;

import com.yonyou.iuap.context.InvocationInfoProxy;
import com.yonyou.iuap.crm.basedata.entity.SeriesMarginExtVO;
import com.yonyou.iuap.crm.basedata.entity.SeriesMarginVO;
import com.yonyou.iuap.persistence.bs.dao.DAOException;
import com.yonyou.iuap.persistence.jdbc.framework.SQLParameter;
import com.yonyou.iuap.crm.basedata.repository.itf.ISeriesMarginDao;
import com.yonyou.iuap.crm.basedata.service.itf.ISeriesMarginService;
import com.yonyou.iuap.crm.common.exception.AppBusinessException;
import com.yonyou.iuap.crm.common.utils.AppTools;

//用于标注业务层组件
@Service
public class SeriesMarginService implements ISeriesMarginService {
	private SimpleDateFormat dataFormat = new SimpleDateFormat(
			"yyyy-MM-dd HH:MM:ss");

	@Autowired
	private ISeriesMarginDao dao;

	private Clock clock = Clock.DEFAULT;

	@Override
	public void setClock(Clock clock) {
		this.clock = clock;
	}

	/**
	 * 分页查询
	* @author 
	* @date 2016年11月22日
	* @param searchParams
	* @param pageRequest
	* @return
	* @throws AppBusinessException
	* (non-Javadoc)
	* @see com.yonyou.iuap.crm.basedata.service.itf.ISeriesMarginService#getSeriesMarginsBypage(java.util.Map, org.springframework.data.domain.PageRequest)
	 */
	@Override
	public Page<SeriesMarginExtVO> getSeriesMarginsBypage(
			Map<String, Object> parammap, PageRequest pageRequest)
			throws AppBusinessException {
		StringBuffer condition = new StringBuffer();
		SQLParameter sqlParameter = new SQLParameter();
		if (null != parammap.get("vyear") && !parammap.get("vyear").toString().equals("")) {
			condition.append(" and vyear like ? ");
			sqlParameter.addParam("%" + parammap.get("vyear").toString() + "%");
		}
		if (null != parammap.get("vmonth") && !parammap.get("vmonth").toString().equals("")) {
			condition.append(" and vmonth like ? ");
			sqlParameter.addParam("%" + parammap.get("vmonth") + "%");
		}
		if (null != parammap.get("pk_series") && !parammap.get("pk_series").toString().equals("")) {
			condition.append(" and pk_series like ? ");
			sqlParameter.addParam("%" + parammap.get("pk_series") + "%");
		}
		try {
		return dao.getSeriesMarginBypage(condition.toString(), sqlParameter,
				pageRequest);
		} catch (DAOException e) {
			e.printStackTrace();
			throw new AppBusinessException(e.getMessage());
		}
	}

	/**
	 * 新增
	* @author 
	* @date 2016年11月22日
	* @param entity
	* @return
	* @throws AppBusinessException
	* (non-Javadoc)
	* @see com.yonyou.iuap.crm.basedata.service.itf.ISeriesMarginService#saveEntity(com.yonyou.iuap.crm.basedata.entity.SeriesMarginVO)
	 */
	@Override
	@Transactional
	public String saveEntity(SeriesMarginVO entity) throws AppBusinessException {
		try {
			List<SeriesMarginVO> existDatas = dao
					.findListByClauseWithDR("pk_series = '"
							+ entity.getPk_series()
							+ "' and vyear = '"
							+ entity.getVyear()
							+ "' and vmonth = '"
							+ entity.getVmonth()+"' ");
			if (existDatas != null && existDatas.size() > 0) {
				throw new AppBusinessException("此条车系毛利信息已存在");
			}
		entity.setCreationtime(dataFormat.format(clock.getCurrentDate()));
		return dao.saveSeriesMargin(entity);
		} catch (DAOException e) {
			e.printStackTrace();
			throw new AppBusinessException(e.getMessage());
		}
	}

	/**
	 * 修改
	* @author 
	* @date 2016年11月22日
	* @param entity
	* @return
	* @throws AppBusinessException
	* (non-Javadoc)
	* @see com.yonyou.iuap.crm.basedata.service.itf.ISeriesMarginService#updateEntity(com.yonyou.iuap.crm.basedata.entity.SeriesMarginVO)
	 */
	@Override
	@Transactional
	public String updateEntity(SeriesMarginVO entity) throws AppBusinessException {
		try {
			List<SeriesMarginVO> existDatas = dao
					.findListByClauseWithDR("pk_seriesmargin != '"
							+ entity.getPk_seriesmargin()
							+ "' and pk_series = '"
							+ entity.getPk_series()
							+ "' and vyear = '"
							+ entity.getVyear()
							+ "' and vmonth = '"
							+ entity.getVmonth()+"' ");
			if (existDatas != null && existDatas.size() > 0) {
				throw new AppBusinessException("修改后的车系毛利信息已存在");
			}
		entity.setModifier(InvocationInfoProxy.getUserid());
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
		String time = df.format(clock.getCurrentDate());
		entity.setModifiedtime(time);
		dao.updateSeriesMargin(entity);
		} catch (DAOException e) {
			e.printStackTrace();
			throw new AppBusinessException(e.getMessage());
		}
		return "success";

	}

	/**
	 * 根据PK删除单条数据
	* @author 
	* @date 2016年11月22日
	* @param pk_seriesmargin
	* @throws AppBusinessException
	* (non-Javadoc)
	* @see com.yonyou.iuap.crm.basedata.service.itf.ISeriesMarginService#deleteSeriesMarginById(java.lang.String)
	 */
	@Override
	@Transactional
	public void deleteSeriesMarginById(String pk_seriesmargin)
			throws AppBusinessException {
		try {
		dao.deleteSeriesMarginById(pk_seriesmargin);
		} catch (DAOException e) {
			e.printStackTrace();
			throw new AppBusinessException(e.getMessage());
		}
	}

	/**
	 * 批量删除
	* @author 
	* @date 2016年11月22日
	* @param entitys
	* @return
	* @throws AppBusinessException
	* (non-Javadoc)
	* @see com.yonyou.iuap.crm.basedata.service.itf.ISeriesMarginService#removeSeriesMargins(java.util.List)
	 */
	@Override
	@Transactional
	public Map<String, Object> removeSeriesMargins(List<SeriesMarginVO> entitys)
			throws AppBusinessException {
		Map<String, Object> map = new HashMap<String, Object>();
		try {
		dao.deleteSeriesMargins(entitys);
		} catch (DAOException e) {
			e.printStackTrace();
			throw new AppBusinessException(e.getMessage());
		}
		map.put("flag", AppTools.SUCCESS);
		map.put("msg", "删除成功");
		return map;
	}

	@Override
	@Transactional
	public void saveEntitys(List<SeriesMarginVO> voList)
			throws AppBusinessException {
		try {
			dao.saveAllSeriesMargins(voList);
		} catch (DAOException e) {
			e.printStackTrace();
			throw new AppBusinessException(e.getMessage());
		}
	}
	
	@Override
	@Transactional
	public void importEntitys(List<SeriesMarginVO> voList)
			throws AppBusinessException {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		List<SeriesMarginVO> updateList = new ArrayList<SeriesMarginVO>();
		List<SeriesMarginVO> saveList = new ArrayList<SeriesMarginVO>();
		try {
			for(int i = 0;i<voList.size();i++){
				SeriesMarginVO vo = voList.get(i);
				List<SeriesMarginVO> existDatas = dao
						.findListByClauseWithDR("pk_series = '"
								+ vo.getPk_series()
								+ "' and vyear = '"
								+ vo.getVyear()
								+ "' and vmonth = '"
								+ vo.getVmonth()+"' ");
				if (existDatas != null && existDatas.size() > 0) {
					//修改数据  需要的参数
					SeriesMarginVO data = existDatas.get(0);
					data.setModifier(InvocationInfoProxy.getUserid());
					SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
					String time = df.format(clock.getCurrentDate());
					data.setModifiedtime(time);
					data.setNmargin(vo.getNmargin());
					updateList.add(data);
				}else{
				//新增数据  需要的参数
				vo.setPk_seriesmargin(AppTools.generatePK());
				vo.setCreator(InvocationInfoProxy.getUserid());
				vo.setCreationtime(sdf.format(new Date()));
				saveList.add(vo);
				}
			}
		if(saveList!= null && saveList.size()>0){	
			dao.saveAllSeriesMargins(saveList);
		 }
		if(updateList!= null && updateList.size()>0){	
			dao.updateAll(updateList);
		 }
			
		} catch (DAOException e) {
			e.printStackTrace();
			throw new AppBusinessException(e.getMessage());
		}
	}
}
