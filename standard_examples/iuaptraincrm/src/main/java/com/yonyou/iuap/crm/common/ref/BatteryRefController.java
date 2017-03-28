package com.yonyou.iuap.crm.common.ref;

import iuap.ref.ref.RefClientPageInfo;
import iuap.ref.sdk.refmodel.model.AbstractGridRefModel;
import iuap.ref.sdk.refmodel.vo.RefViewModelVO;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import com.yonyou.iuap.crm.basedata.entity.TmModelVO;
import com.yonyou.iuap.persistence.bs.dao.BaseDAO;
import com.yonyou.iuap.persistence.jdbc.framework.processor.MapListProcessor;
import com.yonyou.iuap.persistence.vo.pub.BusinessException;
import com.yonyou.iuap.crm.basedata.service.itf.ICompetBrandService;
import com.yonyou.iuap.crm.common.utils.AppTools;

/**
 * 车型档案电池电量枚举用
* TODO description
* @author 
* @date Dec 6, 2016
 */
@RequestMapping(value="/ref/battery")
@Controller
public class BatteryRefController extends AbstractGridRefModel implements IRefModelRestEx{

	private final Logger logger = LoggerFactory.getLogger(getClass());
		
	@Autowired
	private ICompetBrandService cpService;
	
	@Autowired
	@Qualifier("baseDAO") private  BaseDAO dao;
	
	public BatteryRefController() {}
	
	@Override
	public RefViewModelVO getRefModelInfo(@RequestBody RefViewModelVO refViewModel) {
		refViewModel = super.getRefModelInfo(refViewModel);
		refViewModel.setRefName("电池电量");
		refViewModel.setRefCode("battery");
		refViewModel.setRootName("电池电量");
		refViewModel.setStrHiddenFieldCode(new String[]{"refpk"});
		refViewModel.setStrFieldCode(new String[]{"refcode","refname"});
		refViewModel.setStrFieldName(new String[]{"电池电量","电池电量"});
		return refViewModel;
	}
	
	public Map<String, Object> getCommonRefData(
			RefViewModelVO paramRefViewModelVO) {
		Map<String, Object> result = new HashMap<String, Object>();
		
		Map<String, Object> searchParams = new HashMap<String, Object>();
		RefClientPageInfo refClientPageInfo = paramRefViewModelVO.getRefClientPageInfo();
		int currPageIndex = refClientPageInfo.getCurrPageIndex();
		PageRequest pageRequest = new PageRequest(currPageIndex, AppTools.REFPAGESIZE, new Sort(Direction.ASC, "ts"));
		try {
			Page<TmModelVO> tmmodelPage = cpService.enumBattery(searchParams, pageRequest);
			int pageCount = tmmodelPage.getTotalPages();
			int pageSize = tmmodelPage.getSize();
			refClientPageInfo.setCurrPageIndex(currPageIndex);
			refClientPageInfo.setPageCount(pageCount);
			refClientPageInfo.setPageSize(pageSize);
			paramRefViewModelVO.setRefClientPageInfo(refClientPageInfo);
			List<Map<String, String>> list = new ArrayList<>();
			List<TmModelVO> vos = tmmodelPage.getContent();
			for(TmModelVO vo : vos){
				HashMap<String, String> tmmodels = new HashMap<>();
//				tmmodels.put("refpk", Double.toString(vo.getNvbatterypower()));
//				tmmodels.put("refbattery", Double.toString(vo.getNvbatterypower()));
				list.add(tmmodels);
			}
			result.put("dataList", list);
			result.put("refViewModel", paramRefViewModelVO);
			return result;
		} catch (BusinessException e) {
			logger.error("电量参照获取错误",e);
			e.printStackTrace();
			return null;
		}
	}
	
	@Override
	public List<Map<String,String>> getIntRefData(
			String pks) throws Exception {
		String sql = "select pk_model as pk,vmodelname as name from tm_model where pk_model in ("+pks+");";
		List<Map<String,String>> results = dao.queryForList(sql, new MapListProcessor());
		return results;
	}
	
	@Override
	public List<Map<String, String>> filterRefJSON(RefViewModelVO arg0) {
		Map<String, Object> result = new HashMap<String, Object>();
		
		Map<String, Object> searchParams = new HashMap<String, Object>();
	//	PageRequest pageRequest = new PageRequest(0, 100, new Sort(Direction.ASC, "ts"));
		RefClientPageInfo refClientPageInfo = arg0.getRefClientPageInfo();
		int currPageIndex = refClientPageInfo.getCurrPageIndex();
		PageRequest pageRequest = new PageRequest(currPageIndex, AppTools.REFPAGESIZE, new Sort(Direction.ASC, "ts"));
		try {
			Page<TmModelVO> tmmodelPage = cpService.enumBattery(searchParams, pageRequest);
			int pageCount = tmmodelPage.getTotalPages();
	//		int currPageIndex = categoryPage.getNumber() + 1;
	//		int currPageIndex = categoryPage.getNumber();
			int pageSize = tmmodelPage.getSize();
			refClientPageInfo.setCurrPageIndex(currPageIndex);
			refClientPageInfo.setPageCount(pageCount);
			refClientPageInfo.setPageSize(pageSize);
			arg0.setRefClientPageInfo(refClientPageInfo);
			List<Map<String, String>> list = new ArrayList<>();
			
			List<TmModelVO> vos = tmmodelPage.getContent();
			for(TmModelVO vo : vos){
//				SeriesVO series = seriesService.getSeriesById(vo.getPk_series());
//				vo.setSeriesname(series.getVseriesname());
//				TmVehicleClassifyVO vehicleClass = vehicleService.getTmVehicleClassifyVo(series==null?"":series.getPk_vehicleclassify());
//				vo.setVehicelname(vehicleClass.getVclassname());
//				TmBrandVO brand = brandService.getTmBrandVo(vehicleClass==null?"":vehicleClass.getPk_brand());
//				vo.setBrandname(brand==null?"":brand.getVbrandname());
				HashMap<String, String> tmmodels = new HashMap<>();
//				tmmodels.put("refpk", Double.toString(vo.getNvbatterypower()));
//				tmmodels.put("refcode", Double.toString(vo.getNvbatterypower()));
//				tmmodels.put("refname", Double.toString(vo.getNvbatterypower()));
	//			user.put("creator", vo.getCreator());
				list.add(tmmodels);
			}
			result.put("dataList", list);
			result.put("refViewModel", arg0);
			return list;
		} catch (BusinessException e) {
			logger.error("电量参照获取错误",e);
			e.printStackTrace();
			return null;
		}
	}
	
	@Override
	public List<Map<String, String>> matchPKRefJSON(RefViewModelVO arg0) {
		// TODO 自动生成的方法存根
		return null;
	}
	
	@Override
	public List<Map<String, String>> matchBlurRefJSON(
			RefViewModelVO paramRefViewModelVO) {
		// TODO 自动生成的方法存根
		return null;
	}
}
