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

import com.yonyou.iuap.crm.basedata.entity.SeriesVO;
import com.yonyou.iuap.crm.basedata.entity.TmBrandVO;
import com.yonyou.iuap.crm.basedata.entity.TmModelExtVO;
import com.yonyou.iuap.crm.basedata.entity.TmModelVO;
import com.yonyou.iuap.crm.basedata.entity.TmVehicleClassifyVO;
import com.yonyou.iuap.crm.basedata.service.itf.ISeriesService;
import com.yonyou.iuap.crm.basedata.service.itf.ITmBrandService;
import com.yonyou.iuap.crm.basedata.service.itf.ITmModelService;
import com.yonyou.iuap.crm.basedata.service.itf.ITmVehicleClassifyService;
import com.yonyou.iuap.crm.common.utils.DictCode;
import com.yonyou.iuap.persistence.bs.dao.BaseDAO;
import com.yonyou.iuap.persistence.bs.dao.DAOException;
import com.yonyou.iuap.persistence.jdbc.framework.processor.MapListProcessor;
import com.yonyou.iuap.persistence.vo.pub.BusinessException;

@Controller
@RequestMapping(value="/ref/tmmodelref")
public class TmModelRefController extends AbstractGridRefModel implements IRefModelRestEx{

	private final Logger logger = LoggerFactory.getLogger(getClass());
		
	@Autowired
	private ITmModelService cpService;
	
	@Autowired
	private ISeriesService seriesService;
	@Autowired
	private ITmVehicleClassifyService vehicleService;
	@Autowired
	private ITmBrandService brandService;
	
	@Autowired
	@Qualifier("baseDAO") private  BaseDAO dao;
	
	public TmModelRefController() {}
	
	@Override
	public RefViewModelVO getRefModelInfo(@RequestBody RefViewModelVO refViewModel) {
		refViewModel = super.getRefModelInfo(refViewModel);
		refViewModel.setDefaultFieldCount(4);
		refViewModel.setRefName("车型");
		refViewModel.setRefCode("model");
		refViewModel.setRootName("车型");
		refViewModel.setStrHiddenFieldCode(new String[]{"refpk"});
		refViewModel.setStrFieldCode(new String[]{"refcode","refname","refseries","refvehicle","refbrand"});
		refViewModel.setStrFieldName(new String[]{"车型编码","车型名称","所属车系","所属类别","所属品牌"});
		refViewModel.setRefCodeNamePK(new String[]{"code","name"});
		
		return refViewModel;
	}
	
	public Map<String, Object> getCommonRefData(
			@RequestBody RefViewModelVO paramRefViewModelVO) {
		Map<String, Object> searchParams = new HashMap<String, Object>();
		String condition =  paramRefViewModelVO.getContent();					//获取查询条件
		searchParams.put("condition", condition);
		Map<String, Object> result = new HashMap<String, Object>();
		String[] pk_val = paramRefViewModelVO.getPk_val();
		String param ="";
		for(int i=0;i<pk_val.length;i++){
			if(pk_val[i].startsWith("pk_series")){
				param = pk_val[i].substring(pk_val[0].indexOf("=")+1, pk_val[i].length());
				searchParams.put("pk_series", param);
			}
		}
		searchParams.put("vusestatus",DictCode.ALREADY_START_STATUS);
		RefClientPageInfo refClientPageInfo = paramRefViewModelVO.getRefClientPageInfo();
		int currPageIndex = refClientPageInfo.getCurrPageIndex();
		PageRequest pageRequest = new PageRequest(currPageIndex, 10, new Sort(Direction.ASC, "vmodelcode"));
		try {
			Page<TmModelExtVO> tmmodelPage = cpService.getTmModelPage(searchParams, pageRequest);
			
			int pageCount = tmmodelPage.getTotalPages();
	//		int currPageIndex = categoryPage.getNumber() + 1;
	//		int currPageIndex = categoryPage.getNumber();
			int pageSize = tmmodelPage.getSize();
			refClientPageInfo.setCurrPageIndex(currPageIndex);
			refClientPageInfo.setPageCount(pageCount);
			refClientPageInfo.setPageSize(pageSize);
			paramRefViewModelVO.setRefClientPageInfo(refClientPageInfo);
			
			List<Map<String, String>> list = new ArrayList();
			
			List<TmModelExtVO> vos = tmmodelPage.getContent();
			for(TmModelExtVO vo : vos){
				SeriesVO series = seriesService.getSeriesById(vo.getPk_series());
				vo.setSeriesname(series==null?"":series.getVseriesname());
				TmVehicleClassifyVO vehicleClass = vehicleService.getTmVehicleClassifyVo(series==null?"":series.getPk_vehicleclassify());
				vo.setVehicelname(vehicleClass==null?"":vehicleClass.getVclassname());
				TmBrandVO brand = brandService.getTmBrandVo(vehicleClass==null?"":vehicleClass.getPk_brand());
				vo.setBrandname(brand==null?"":brand.getVbrandname());
				HashMap<String, String> tmmodels = new HashMap();
				tmmodels.put("refpk", vo.getPk_model());
				tmmodels.put("refcode",vo.getVmodelcode());
				tmmodels.put("refname", vo.getVmodelname());
				tmmodels.put("refseries", vo.getSeriesname());
				tmmodels.put("refvehicle", vo.getVehicelname());
				tmmodels.put("refbrand", vo.getBrandname());
	//			user.put("creator", vo.getCreator());
				list.add(tmmodels);
			}
			result.put("dataList", list);
			result.put("refViewModel", paramRefViewModelVO);
			return result;
		} catch (BusinessException e) {
			logger.error("车型参照获取错误",e);
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
	public List<Map<String, String>> filterRefJSON(@RequestBody RefViewModelVO arg0) {
		Map<String, Object> searchParams = new HashMap<String, Object>();
		Map<String, Object> result = new HashMap<String, Object>();
		String[] pk_val = arg0.getPk_val();
		String param ="";
		//获取车系
		if(null!=pk_val&&pk_val.length>0){
			param = pk_val[0].substring(pk_val[0].indexOf("=")+1, pk_val[0].length());
			searchParams.put("pk_series", param);
		}
	//	PageRequest pageRequest = new PageRequest(0, 100, new Sort(Direction.ASC, "ts"));
		RefClientPageInfo refClientPageInfo = arg0.getRefClientPageInfo();
		int currPageIndex = refClientPageInfo.getCurrPageIndex();
		PageRequest pageRequest = new PageRequest(currPageIndex, 10, new Sort(Direction.ASC, "ts"));
		try {
			Page<TmModelExtVO> tmmodelPage = cpService.getTmModelPage(searchParams, pageRequest);
			
			int pageCount = tmmodelPage.getTotalPages();
	//		int currPageIndex = categoryPage.getNumber() + 1;
	//		int currPageIndex = categoryPage.getNumber();
			int pageSize = tmmodelPage.getSize();
			refClientPageInfo.setCurrPageIndex(currPageIndex);
			refClientPageInfo.setPageCount(pageCount);
			refClientPageInfo.setPageSize(pageSize);
			arg0.setRefClientPageInfo(refClientPageInfo);
			
			List<Map<String, String>> list = new ArrayList();
			
			List<TmModelExtVO> vos = tmmodelPage.getContent();
			for(TmModelExtVO vo : vos){
				SeriesVO series = seriesService.getSeriesById(vo.getPk_series());
				vo.setSeriesname(series==null?"":series.getVseriesname());
				TmVehicleClassifyVO vehicleClass = vehicleService.getTmVehicleClassifyVo(series==null?"":series.getPk_vehicleclassify());
				vo.setVehicelname(vehicleClass==null?"":vehicleClass.getVclassname());
				TmBrandVO brand = brandService.getTmBrandVo(vehicleClass==null?"":vehicleClass.getPk_brand());
				vo.setBrandname(brand==null?"":brand.getVbrandname());
				HashMap<String, String> tmmodels = new HashMap();
				tmmodels.put("refpk", vo.getPk_model());
				tmmodels.put("refcode",vo.getVmodelcode());
				tmmodels.put("refname", vo.getVmodelname());
				tmmodels.put("refseries", vo.getSeriesname());
				tmmodels.put("refvehicle", vo.getVehicelname());
				tmmodels.put("refbrand", vo.getBrandname());
	//			user.put("creator", vo.getCreator());
				list.add(tmmodels);
			}
			result.put("dataList", list);
			result.put("refViewModel", arg0);
			return list;
		} catch (BusinessException e) {
			logger.error("车型参照获取错误",e);
			e.printStackTrace();
			return null;
		}
	}
	
	@Override
	public List<Map<String, String>> matchPKRefJSON(@RequestBody RefViewModelVO arg0) {
		// TODO 自动生成的方法存根
		return null;
	}
	
	@Override
	public List<Map<String, String>> matchBlurRefJSON(@RequestBody RefViewModelVO paramRefViewModelVO) {
		// TODO 自动生成的方法存根
		String[] pks = paramRefViewModelVO.getPk_val();
		String pk = "";
		List<Map<String, String>> results = new ArrayList();
		try {
			Map<String, String> map = new HashMap();
			if(pks!=null&&pks.length>0) {
				pk = pks[0];
				TmModelVO vo = dao.queryByPK(TmModelVO.class, pk);
				map.put("refpk", null==vo ? "" :vo.getPk_model()); 
				map.put("refcode",null==vo ? "" : vo.getVmodelcode()); 
				map.put("refname",null==vo ? "" : vo.getVmodelname()); 
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
