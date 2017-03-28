package com.yonyou.iuap.crm.basedata.web;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Validator;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springside.modules.beanvalidator.BeanValidators;
import org.springside.modules.web.Servlets;

import com.yonyou.iuap.crm.basedata.entity.SeriesVO;
import com.yonyou.iuap.crm.basedata.entity.TmBrandVO;
import com.yonyou.iuap.crm.basedata.entity.TmCompetBrandVO;
import com.yonyou.iuap.crm.basedata.entity.TmModelExtVO;
import com.yonyou.iuap.crm.basedata.entity.TmModelVO;
import com.yonyou.iuap.crm.basedata.entity.TmVehicleClassifyVO;
import com.yonyou.iuap.persistence.vo.pub.VOStatus;
import com.yonyou.iuap.crm.basedata.service.itf.ISeriesService;
import com.yonyou.iuap.crm.basedata.service.itf.ITmBrandService;
import com.yonyou.iuap.crm.basedata.service.itf.ITmModelService;
import com.yonyou.iuap.crm.basedata.service.itf.ITmVehicleClassifyService;
import com.yonyou.iuap.crm.common.exception.AppBusinessException;
import com.yonyou.iuap.crm.common.utils.DictCode;
import com.yonyou.iuap.crm.common.utils.ExcelWriterPoiWriter;
import com.yonyou.iuap.crm.enums.entity.AppEnumVO;
import com.yonyou.iuap.crm.enums.service.itf.IAppEnumService;


@Controller
@RequestMapping(value="/basedata/tmModel")
public class TmModelController {
	private final Logger logger = LoggerFactory.getLogger(getClass());
	
	@Autowired
	private Validator validator;
	
	@Autowired
	private ITmModelService tmModelService;
	@Autowired
	private ISeriesService seriesService;
	@Autowired
	private ITmVehicleClassifyService vehicleClassService;
	@Autowired
	private ITmBrandService brandService;
	
	@Autowired
	private IAppEnumService appEnumService;
	
	@RequestMapping(value="/querypage", method= RequestMethod.POST)
	public @ResponseBody Object page(@RequestParam(value = "pageIndex", defaultValue = "0") int pageNumber, @RequestParam(value = "pageSize", defaultValue = "20") int pageSize, @RequestParam(value = "sortType", defaultValue = "auto") String sortType, ServletRequest request) throws AppBusinessException {
		Map<String,Object> result = new HashMap<String,Object>();
		Map<String, Object> searchParams = new HashMap<String, Object>();
		searchParams = Servlets.getParametersStartingWith(request,null);
		PageRequest pageRequest = buildPageRequest(pageNumber, pageSize, sortType);
		
		Page<TmModelExtVO> accountPage = tmModelService.getTmModelPage(searchParams,pageRequest);
		result.put("data", accountPage);
		result.put("flag", "success");
		result.put("msg", "查询数据成功!");
		
		return result;
	}
	
	@RequestMapping(value="/queryTmCompet",method=RequestMethod.POST)
	public @ResponseBody Object queryTmCompetList(@RequestBody TmModelExtVO entity) throws AppBusinessException{
		Map<String,Object> result = new HashMap<String,Object>();
		try{
			List<TmCompetBrandVO> competBrandList = tmModelService.getTmCompetByModel(entity);
			result.put("data", competBrandList);
			result.put("msg", "查询成功");
			result.put("flag","success");
		}catch(AppBusinessException e){
			String errMsg = "查询竞品失败!";
			result.put("msg", "保存失败!");
			result.put("flag", "fail");
			logger.error("保存出错!",e);
			throw new AppBusinessException(e.getMessage());
		}
		return result;
	}
	
	@RequestMapping(value="/save",method=RequestMethod.POST)
	public @ResponseBody Object save(@RequestBody TmModelExtVO entity) throws AppBusinessException{
		Map<String,Object> result = new HashMap<String,Object>();
		BeanValidators.validateWithException(validator, entity);
		try{
			//设置状态为NEW，才会插入新数据
			entity.setStatus(VOStatus.NEW);
			entity = (TmModelExtVO) tmModelService.saveEntity(entity);
			result.put("msg", "保存成功!");
			result.put("data", entity);
			result.put("flag", "success");

		}catch(AppBusinessException e){
			result.put("msg", "保存失败!");
			result.put("flag", "fail");
			logger.error("保存出错!",e);
			throw new AppBusinessException(e.getMessage());
		}
		return result;
	}
	
	/** 更新 */
	@RequestMapping(value = "update", method = RequestMethod.POST)
	public @ResponseBody Object update(@RequestBody TmModelExtVO entity) throws AppBusinessException {
		Map<String,Object> result = new HashMap<String,Object>();
		BeanValidators.validateWithException(validator, entity);
		try {
			
			//设置状态为更新，才会持久化
			entity.setStatus(VOStatus.UPDATED);
			entity = (TmModelExtVO) tmModelService.saveEntity(entity);
			result.put("data", entity);
			result.put("msg", "更新成功!");
			result.put("flag", "success");
		
		} catch (Exception e) {
			result.put("msg", "更新失败!");
			result.put("flag", "fail");
			logger.error("更新出错!",e);
			throw new AppBusinessException(e.getMessage());
		}
        return result;  
	}
	
	@RequestMapping(value="/delete",method=RequestMethod.POST)
	public @ResponseBody Object delete(@RequestBody TmModelExtVO vo, ModelMap model,
			HttpServletRequest request) throws AppBusinessException{
		Map<String,Object> result = new HashMap<String,Object>();
		try{
			tmModelService.deleteEntity(vo);
			result.put("flag", "success");
			result.put("msg", "删除数据成功!");
				
		}catch(Exception e){
			String errmsg = "删除数据失败!";
			logger.error(errmsg, e);
			result.put("flag", "fail");
			result.put("msg", errmsg);
			throw new AppBusinessException(e.getMessage());
		}
		
		
		return result;
	}
	
	@RequestMapping(value="/start",method=RequestMethod.POST)
	public @ResponseBody Object start(@RequestBody List<TmModelExtVO> vos, ModelMap model,
			HttpServletRequest request) throws AppBusinessException{
		Map<String,Object> result = new HashMap<String,Object>();
		try{
			tmModelService.startOrStop(Boolean.TRUE, vos);
			result.put("flag", "success");
			result.put("msg", "启用成功!");
				
		}catch(Exception e){
			String errmsg = "启用失败!";
			logger.error(errmsg, e);
			result.put("flag", "fail");
			result.put("msg", errmsg);
			throw new AppBusinessException(e.getMessage());
		}
		
		
		return result;
	}
	
	@RequestMapping(value="/stop",method=RequestMethod.POST)
	public @ResponseBody Object stop(@RequestBody List<TmModelExtVO> vos, ModelMap model,
			HttpServletRequest request) throws AppBusinessException{
		Map<String,Object> result = new HashMap<String,Object>();
		try{
			tmModelService.startOrStop(Boolean.FALSE, vos);
			
			result.put("flag", "success");
			result.put("msg", "停用成功!");
				
		}catch(Exception e){
			String errmsg = "停用失败!";
			logger.error(errmsg, e);
			result.put("flag", "fail");
			result.put("msg", errmsg);
			throw new AppBusinessException(e.getMessage());
		}
		
		
		return result;
	}
	
	@RequestMapping(value = "/vehicelDownload", method = RequestMethod.POST)
	public void vehicelDownload(
			@RequestParam String pkModelIds,
			ModelMap model,
			HttpServletRequest request,
			HttpServletResponse response) throws AppBusinessException {
		try {
			//数据导出容器
			List<List<Object>> list=new ArrayList<List<Object>>();
			//标题
			List<Object> listTitle = new LinkedList<Object>();
			listTitle.add("车型编码");
			listTitle.add("车型名称");
			listTitle.add("公告号");
			listTitle.add("所属车系");
			listTitle.add("所属类别");
			listTitle.add("所属品牌");
			listTitle.add("电量");
			listTitle.add("新能源车辆推广目录");
			listTitle.add("免购置税目录");
			listTitle.add("工信部车辆公告");
			listTitle.add("公告有效日期");
			listTitle.add("车辆状态");
			listTitle.add("状态");
			listTitle.add("包装规格");
			listTitle.add("备注");
			list.add(listTitle);
			
			/**
			 * 枚举转换
			 */
			Map<String,String> yesOrNo = new HashMap<String,String>();
			List<AppEnumVO> yesOrNoList = appEnumService.queryByVtype(DictCode.BILL_YES_NO_TYPE);
			if(null != yesOrNoList && yesOrNoList.size()>0){
				for(AppEnumVO enumVO : yesOrNoList){
					yesOrNo.put(enumVO.getVcode(), enumVO.getVname());
				}
			}
			
			/**
			 * 工信部车辆公告
			 */
			Map<String,String> vehicleannoStatus = new HashMap<String,String>();
			List<AppEnumVO> vehicleannoStatusList = appEnumService.queryByVtype(DictCode.NOUNCEMENT_TYPE);
			if(null != vehicleannoStatusList && vehicleannoStatusList.size()>0){
				for(AppEnumVO enumVO : vehicleannoStatusList){
					vehicleannoStatus.put(enumVO.getVcode(), enumVO.getVname());
				}
			}
			/**
			 * 车型状态
			 */
			Map<String,String> status = new HashMap<String,String>();
			List<AppEnumVO> statusList = appEnumService.queryByVtype(DictCode.MODEL_TYPE);
			if(null != statusList && statusList.size()>0){
				for(AppEnumVO enumVO : statusList){
					status.put(enumVO.getVcode(), enumVO.getVname());
				}
			}
			/**
			 * 状态
			 */
			Map<String,String> useStatus = new HashMap<String,String>();
			List<AppEnumVO> useStatusList = appEnumService.queryByVtype(DictCode.ALREADY_START_TYPE);
			if(null != useStatusList && useStatusList.size()>0){
				for(AppEnumVO enumVO : useStatusList){
					useStatus.put(enumVO.getVcode(), enumVO.getVname());
				}
			}
			if(null != pkModelIds && pkModelIds.length()>0){
				pkModelIds = pkModelIds.replaceAll(",", "','");
				
				String sql = " pk_model in ('"+pkModelIds+"')";
				List<TmModelVO> exportList = tmModelService.getModelByCondition(sql);

				if(null != exportList && exportList.size()>0){
					for(int i = 0;i<exportList.size();i++){
						
						TmModelVO tempBean = exportList.get(i);
						SeriesVO series = seriesService.getSeriesById(tempBean.getPk_series());
						
						TmVehicleClassifyVO vehicleClassVO = vehicleClassService.getTmVehicleClassifyVo(series==null?"":series.getPk_vehicleclassify());
						TmBrandVO brand = brandService.getTmBrandVo(vehicleClassVO==null?"":vehicleClassVO.getPk_brand());
						List<Object> tempList = new LinkedList<Object>();
						tempList.add(tempBean.getVmodelcode());//车型编码
						tempList.add(tempBean.getVmodelname());//车型名称
						tempList.add(tempBean.getVannouncenum());//公告号
						tempList.add(series==null?"":series.getVseriesname());//所属车系
						tempList.add(vehicleClassVO==null?"":vehicleClassVO.getVclassname());//车辆种类
						tempList.add(brand==null?"":brand.getVbrandname());//所属品牌
						tempList.add(tempBean.getNvbatterypower());//电池电量
						tempList.add(yesOrNo.get(tempBean.getVmarketcatalog()));//新能源车辆推广目录
						tempList.add(yesOrNo.get(tempBean.getVistaxfree()));   // 免购置税目录
						tempList.add(vehicleannoStatus.get(tempBean.getVvehicleannouncement()));//工信部车辆公告
						tempList.add(tempBean.getVannouncevaliddate());//公告有效日期
						tempList.add(status.get(tempBean.getVstatus()));//车辆状态
						tempList.add(useStatus.get(tempBean.getVusestatus()));//状态
						tempList.add(tempBean.getVremark());//备注
						tempList.add("");//完结原因
						list.add(tempList);
					}
				}
			}
			ExcelWriterPoiWriter.writeExcel(response,list,"车型信息","车型信息");
		} catch (Exception e) {
			throw new AppBusinessException(e.getMessage());
		}
	}
	
	
	/**
	 * 创建分页请求简单示例，业务上按照自己的需求修改.
	 */
	private PageRequest buildPageRequest(int pageNumber, int pagzSize, String sortType) {
		Sort sort = null;
		if ("auto".equals(sortType)) {
			sort = new Sort(Direction.DESC, "ts");
		} else if ("productname".equals(sortType)) {
			sort = new Sort(Direction.ASC, "productname");
		}
		return new PageRequest(pageNumber, pagzSize, sort);
	}

}
