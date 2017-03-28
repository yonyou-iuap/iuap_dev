package com.yonyou.iuap.crm.basedata.service.impl;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import com.yonyou.iuap.crm.basedata.entity.SeriesVO;
import com.yonyou.iuap.crm.basedata.entity.TmCompetBrandVO;
import com.yonyou.iuap.crm.basedata.entity.TmModelExtVO;
import com.yonyou.iuap.crm.basedata.entity.TmModelVO;
import com.yonyou.iuap.crm.basedata.entity.TmVehicleClassifyVO;
import com.yonyou.iuap.persistence.bs.dao.DAOException;
import com.yonyou.iuap.persistence.jdbc.framework.SQLParameter;
import com.yonyou.iuap.crm.basedata.repository.itf.ITmModelDao;
import com.yonyou.iuap.crm.basedata.service.itf.ITmModelService;
import com.yonyou.iuap.crm.common.base.repository.AppBaseDao;
import com.yonyou.iuap.crm.common.exception.AppBusinessException;
import com.yonyou.iuap.crm.common.utils.DictCode;
/**
* <p>description：车型service</p>
* @author 
* @created 2016年11月21日 上午11:43:02
* @version 1.0
*/
@Service
public class TmModelServiceImpl implements ITmModelService {
	
	@Autowired
	private ITmModelDao tmModelDao;
	
	@Autowired
	private AppBaseDao baseDao;


	@Override
	public TmModelVO getEntityById(String id) throws AppBusinessException {
		try{
			TmModelVO tmmodel = tmModelDao.queryByPK(id);
			return tmmodel;
		}catch(DAOException e){
			throw new AppBusinessException(e.getMessage());
		}
		 
	}

	@Override
	public void deleteById(String id) throws AppBusinessException {
		TmModelVO modelVO = new TmModelVO();
		try{
			tmModelDao.remove(modelVO);
		}catch(DAOException e){
			throw new AppBusinessException(e.getMessage());
		}
		
	}

	@Override
	public void batchDelete(List<String> ids) throws AppBusinessException {
		List<TmModelVO> vos = new LinkedList<TmModelVO>();
		for(String id:ids){
			TmModelVO vo = new TmModelVO();
			vo.setPk_model(id);
			vos.add(vo);
		}
		try{
			tmModelDao.remove(vos);
		}catch(DAOException e){
			throw new AppBusinessException(e.getMessage());
		}
		
		
	}

	@Override
	public TmModelVO saveEntity(TmModelVO entity) throws AppBusinessException {
		try{
			entity.setPk_model(tmModelDao.save(entity));
		}catch(DAOException e){
			throw new AppBusinessException(e.getMessage());
		}
		
		return entity;
	}

	@Override
	public Page<TmModelExtVO> getTmModelPage(Map<String, Object> searchParams,
			PageRequest pageRequest) throws AppBusinessException {
		try{
			StringBuffer condition = new StringBuffer();
			SQLParameter sqlParameter = new SQLParameter();
			//车型编码
			if(null != searchParams.get("vmodelcode") && !searchParams.get("vmodelcode").toString().equals("")){
				condition.append(" and model.vmodelcode like ?");
				sqlParameter.addParam("%"+searchParams.get("vmodelcode").toString()+"%");
			}
			//车型名称
			if(null != searchParams.get("vmodelname") && !searchParams.get("vmodelname").toString().equals("")){
				condition.append(" and model.vmodelname like ?");
				sqlParameter.addParam("%"+searchParams.get("vmodelname").toString()+"%");
			}
			List<SeriesVO> seriesList = new ArrayList<SeriesVO>();
			StringBuffer seriesIds = new StringBuffer();
			//所属车系
			if(null != searchParams.get("pk_series") && !searchParams.get("pk_series").toString().equals("")){
				SeriesVO series = baseDao.findByIdWithDR(SeriesVO.class, searchParams.get("pk_series").toString());
				seriesList.add(series);
			}else if(null != searchParams.get("pk_veicleclass") && !searchParams.get("pk_veicleclass").toString().equals("")){
				TmVehicleClassifyVO vehicleClass= baseDao.findByIdWithDR(TmVehicleClassifyVO.class, searchParams.get("pk_veicleclass").toString());
				seriesList = baseDao.findListByClauseWithDR(SeriesVO.class, "pk_vehicleclassify='"+(vehicleClass==null?"": vehicleClass.getPk_vehicleclassify())+"'");
			}
			for(SeriesVO series :seriesList){
				seriesIds.append(series.getPk_series()+"','");
			}
			if(seriesIds.length()>0){
				condition.append(" and model.pk_series IN ('"+seriesIds.toString().substring(0, seriesIds.length()-3)+"')");
//				sqlParameter.addParam(seriesIds.toString().substring(0, seriesIds.length()-3));
			}
			//是否启用
			if(null != searchParams.get("vusestatus") && !searchParams.get("vusestatus").toString().equals("")){
				condition.append(" and model.vusestatus = ?");
				sqlParameter.addParam(searchParams.get("vusestatus"));
			}
			// 添加引用车辆类别时的过滤检索
			if (null != searchParams.get("condition")
					&& !"".equals(searchParams.get("condition"))) {
				condition.append(" ").append("and (model.vmodelname like").append(" ")
						.append("?").append(" ").append("||")
						.append(" model.vmodelcode like").append(" ").append(" ? )");
				sqlParameter.addParam("%" + searchParams.get("condition") + "%");
				sqlParameter.addParam("%" + searchParams.get("condition") + "%");
			}
			
			return tmModelDao.queryPage(condition.toString(),sqlParameter,pageRequest);
		}catch(DAOException e){
			throw new AppBusinessException(e.getMessage());
		}
		
	}

	@Override
	public void deleteEntity(TmModelVO entity) throws AppBusinessException {
		try{
			tmModelDao.remove(entity);
		}catch(DAOException e){
			throw new AppBusinessException(e.getMessage());
		}
		
		
	}

	@Override
	public List<TmCompetBrandVO> getTmCompetByModel(TmModelVO entity)
			throws AppBusinessException {
		try{
			return tmModelDao.findTmCompetByModel(entity);
		}catch(DAOException e){
			throw new AppBusinessException(e.getMessage());
		}
		
	}


	@Override
	public void batchDeleteEntity(List<TmModelVO> entitys)
			throws AppBusinessException {
		try{
			tmModelDao.remove(entitys);
		}catch(DAOException e){
			throw new AppBusinessException(e.getMessage());
		}
		
		
	}

	@Override
	public void startOrStop(Boolean flag,List<TmModelExtVO> vos) throws AppBusinessException {
		for(TmModelExtVO vo:vos){
			if(flag){
				vo.setVusestatus(DictCode.ALREADY_START_STATUS);
			}else{
				vo.setVusestatus(DictCode.ALREADY_STOP_STATUS);
			}
			try{
				tmModelDao.save(vo);
			}catch(DAOException e){
				throw new AppBusinessException(e.getMessage());
			}
		}
	}

	@Override
	public List<TmModelVO> getModelByCondition(String condition)
			throws AppBusinessException {
		try{
			return tmModelDao.findListByClauseWithDR(condition);
		}catch(DAOException e){
			throw new AppBusinessException(e.getMessage());
		}
		
		
	}

	@Override
	public List<Map<String,Object>> queryModelInfo(String pk_series)
			throws AppBusinessException {
		// TODO 自动生成的方法存根
		try{
			return tmModelDao.queryModelInfo(pk_series);
		}catch(DAOException e){
			throw new AppBusinessException(e.getMessage());
		}
	}

	
}
