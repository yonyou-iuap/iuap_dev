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

import com.yonyou.iuap.crm.basedata.entity.SeriesExtVO;
import com.yonyou.iuap.crm.basedata.entity.TmBrandVO;
import com.yonyou.iuap.crm.basedata.entity.TmVehicleClassifyVO;
import com.yonyou.iuap.crm.basedata.service.itf.ISeriesService;
import com.yonyou.iuap.crm.basedata.service.itf.ITmBrandService;
import com.yonyou.iuap.crm.basedata.service.itf.ITmVehicleClassifyService;
import com.yonyou.iuap.crm.common.utils.DictCode;
import com.yonyou.iuap.persistence.bs.dao.BaseDAO;
import com.yonyou.iuap.persistence.bs.dao.DAOException;
import com.yonyou.iuap.persistence.jdbc.framework.processor.MapListProcessor;
import com.yonyou.iuap.persistence.vo.pub.BusinessException;

@RequestMapping(value="/ref/tmseriesref")
@Controller
public class TmSeriesRefController extends AbstractGridRefModel implements IRefModelRestEx{

private final Logger logger = LoggerFactory.getLogger(getClass());


@Autowired
private ISeriesService cpService;
@Autowired
private ITmVehicleClassifyService vehicleService;
@Autowired
private ITmBrandService brandService;

@Autowired
@Qualifier("baseDAO") private  BaseDAO dao;

public TmSeriesRefController() {}

@Override
public RefViewModelVO getRefModelInfo(@RequestBody RefViewModelVO refViewModel) {
	refViewModel = super.getRefModelInfo(refViewModel);
	refViewModel.setDefaultFieldCount(4);
	refViewModel.setRefName("车系");
	refViewModel.setRefCode("series");
	refViewModel.setRootName("车系");
	refViewModel.setStrHiddenFieldCode(new String[]{"refpk"});
	refViewModel.setStrFieldCode(new String[]{"refcode","refname","refvehicle","refbrand"});
	refViewModel.setStrFieldName(new String[]{"车系编码","车系名称","所属类别","所属品牌"});
	refViewModel.setRefCodeNamePK(new String[]{"code","name","vehicle","brand"});
	
	return refViewModel;
}

public Map<String, Object> getCommonRefData(
		@RequestBody RefViewModelVO paramRefViewModelVO) {
	Map<String, Object> result = new HashMap<String, Object>();
	
	Map<String, Object> searchParams = new HashMap<String, Object>();
	String condition =  paramRefViewModelVO.getContent();					//获取查询条件
	searchParams.put("condition", condition);
//	PageRequest pageRequest = new PageRequest(0, 100, new Sort(Direction.ASC, "ts"));
	RefClientPageInfo refClientPageInfo = paramRefViewModelVO.getRefClientPageInfo();
	int currPageIndex = refClientPageInfo.getCurrPageIndex();
	PageRequest pageRequest = new PageRequest(currPageIndex, 10, new Sort(Direction.ASC, "pk_vehicleclassify"));
	String[] pk_val = paramRefViewModelVO.getPk_val();
	String param = "";
	for(int i=0;i<pk_val.length;i++){
		if(pk_val[i].startsWith("pk_vehicleclassify")){
			param = pk_val[i].substring(pk_val[i].indexOf("=")+1, pk_val[i].length());
			searchParams.put("pk_vehicleclassify", param);
		}
	}
	
	searchParams.put("vstatus", DictCode.ALREADY_START_STATUS);
	try {
		Page<SeriesExtVO> seriesPage = cpService.getSeriesBypage(searchParams, pageRequest);
		
		int pageCount = seriesPage.getTotalPages();
//		int currPageIndex = categoryPage.getNumber() + 1;
//		int currPageIndex = categoryPage.getNumber();
		int pageSize = seriesPage.getSize();
		refClientPageInfo.setCurrPageIndex(currPageIndex);
		refClientPageInfo.setPageCount(pageCount);
		refClientPageInfo.setPageSize(pageSize);
		paramRefViewModelVO.setRefClientPageInfo(refClientPageInfo);
		
		List<Map<String, String>> list = new ArrayList();
		
		List<SeriesExtVO> vos = seriesPage.getContent();
		for(SeriesExtVO vo : vos){
			TmVehicleClassifyVO vehicleClass = vehicleService.getTmVehicleClassifyVo(vo.getPk_vehicleclassify());
			vo.setVehiclename(vehicleClass==null?"":vehicleClass.getVclassname());
			TmBrandVO brand = brandService.getTmBrandVo(vehicleClass==null?"":vehicleClass.getPk_brand());
			vo.setBrandname(brand==null?"":brand.getVbrandname());
			HashMap<String, String> series = new HashMap();
			series.put("refpk", vo.getPk_series());
			series.put("refcode",vo.getVseriescode());
			series.put("refname", vo.getVseriesname());
			series.put("refvehicle", vo.getVehiclename());
			series.put("refbrand", vo.getBrandname());
//			user.put("creator", vo.getCreator());
			list.add(series);
		}
		result.put("dataList", list);
		result.put("refViewModel", paramRefViewModelVO);
		return result;
	} catch (BusinessException e) {
		logger.error("车系参照获取错误",e);
		e.printStackTrace();
		return null;
	}
}

@RequestMapping(value="/getIntRefData")
@Override
public List<Map<String,String>> getIntRefData(
		String pks) throws Exception {
	String sql = "select pk_series as pk,vseriesname as name,vehicle.vclassname as vehicle,brand.vbrandname as brand from tm_series series "
			+ "left join tm_vehicleclassify vehicle on vehicle.pk_vehicleclassify = series.pk_vehicleclassify "
			+ "left join tm_brand brand ON brand.pk_brand = vehicle.pk_brand "
			+ "where series.pk_series in ("+pks+");";
	List<Map<String,String>> results = dao.queryForList(sql, new MapListProcessor());
	return results;
}

@Override
public List<Map<String, String>> filterRefJSON(@RequestBody RefViewModelVO arg0) {
	Map<String, Object> result = new HashMap<String, Object>();
	
	Map<String, Object> searchParams = new HashMap<String, Object>();
	RefClientPageInfo refClientPageInfo = arg0.getRefClientPageInfo();
	int currPageIndex = refClientPageInfo.getCurrPageIndex();
	PageRequest pageRequest = new PageRequest(currPageIndex, 10, new Sort(Direction.ASC, "ts"));
	try {
		searchParams.put("vstatus", DictCode.ALREADY_START_STATUS);
		Page<SeriesExtVO> seriesPage = cpService.getSeriesBypage(searchParams, pageRequest);
		
		int pageCount = seriesPage.getTotalPages();
		int pageSize = seriesPage.getSize();
		refClientPageInfo.setCurrPageIndex(currPageIndex);
		refClientPageInfo.setPageCount(pageCount);
		refClientPageInfo.setPageSize(pageSize);
		arg0.setRefClientPageInfo(refClientPageInfo);
		
		List<Map<String, String>> list = new ArrayList();
		
		List<SeriesExtVO> vos = seriesPage.getContent();
		for(SeriesExtVO vo : vos){
			TmVehicleClassifyVO vehicleClass = vehicleService.getTmVehicleClassifyVo(vo.getPk_vehicleclassify());
			vo.setVehiclename(vehicleClass==null?"":vehicleClass.getVclassname());
			TmBrandVO brand = brandService.getTmBrandVo(vehicleClass==null?"":vehicleClass.getPk_brand());
			vo.setBrandname(brand==null?"":brand.getVbrandname());
			HashMap<String, String> series = new HashMap();
			series.put("refpk", vo.getPk_series());
			series.put("refcode",vo.getVseriescode());
			series.put("refname", vo.getVseriesname());
			series.put("refvehicle", vo.getVehiclename());
			series.put("refbrand", vo.getBrandname());
//			user.put("creator", vo.getCreator());
			list.add(series);
		}
		result.put("dataList", list);
		result.put("refViewModel", arg0);
		return list;
	} catch (BusinessException e) {
		logger.error("车系参照获取错误",e);
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
		@RequestBody RefViewModelVO paramRefViewModelVO) {
	// TODO 自动生成的方法存根
	String[] pks = paramRefViewModelVO.getPk_val();
	String pk = "";
	List<Map<String, String>> results = new ArrayList();
	try {
		Map<String, String> map = new HashMap();
		if(pks!=null&&pks.length>0) {
			pk = pks[0];
			SeriesExtVO vo = dao.queryByPK(SeriesExtVO.class, pk);
			map.put("refpk", null==vo ? "" :vo.getPk_series()); 
			map.put("refcode",null==vo ? "" : vo.getVseriescode()); 
			map.put("refname",null==vo ? "" : vo.getVseriesname()); 
			results.add(map);
		}else{
			map.put("refpk", ""); 
			map.put("refcode",""); 
			map.put("refname",""); 
			results.add(map);
		}
	} catch (DAOException e) {
		// TODO 自动生成的 catch 块
		e.printStackTrace();
	}
	return results;
}

}
