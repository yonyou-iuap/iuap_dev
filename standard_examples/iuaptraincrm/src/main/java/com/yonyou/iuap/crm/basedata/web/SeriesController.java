package com.yonyou.iuap.crm.basedata.web;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.yonyou.iuap.crm.basedata.entity.SeriesExtVO;
import com.yonyou.iuap.crm.basedata.entity.TmBrandVO;
import com.yonyou.iuap.crm.basedata.entity.TmVehicleClassifyVO;
import com.yonyou.iuap.crm.basedata.service.itf.ISeriesService;
import com.yonyou.iuap.crm.basedata.service.itf.ITmBrandService;
import com.yonyou.iuap.crm.basedata.service.itf.ITmVehicleClassifyService;
import com.yonyou.iuap.crm.common.exception.AppBusinessException;
import com.yonyou.iuap.crm.common.utils.AppInvocationInfoProxy;
import com.yonyou.iuap.crm.common.utils.ExcelWriterPoiWriter;

/**
 * 车系信息控制类
 * 
 * @author 
 * @date 2016年11月22日
 * @version 1.0
 */
@Controller
@RequestMapping(value = "/db/series")
public class SeriesController {
	@Autowired
	private ISeriesService ctService;
	@Autowired
	private ITmVehicleClassifyService vehicleClassifyservice;
	@Autowired
	private ITmBrandService brandservice;

	/**
	 * 查询车系信息
	 * 
	 * @author 
	 * @date 2016年11月22日
	 * @param pageNumber
	 * @param pageSize
	 * @param sortType
	 * @param model
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/querypage1", method = RequestMethod.POST)
	public @ResponseBody Page<SeriesExtVO> querySeriesExt(
			@RequestParam(value = "sortType", defaultValue = "auto") String sortType,
			@RequestBody Map<String, Object> parammap, Model model,
			ServletRequest request) throws AppBusinessException {
		// int pageNumber = Integer.parseInt(String.valueOf(parammap.get("pageIndex")));
		// int pageSize = Integer.parseInt(String.valueOf(parammap.get("pagesize")));
		int pindex = null == parammap.get("pindex") ? 0 : Integer
				.valueOf(parammap.get("pindex").toString());
		int psize = null == parammap.get("psize") ? 0 : Integer
				.valueOf(parammap.get("psize").toString());
		PageRequest pageRequest = buildPageRequest(pindex, psize, "auto");
		Page<SeriesExtVO> categoryPage = ctService.getSeriesBypage(parammap,
				pageRequest);
		// 转换参照和枚举
		for (SeriesExtVO seriesExtVO : categoryPage) {
			// 获取车系名称
			String pk_vehicleclassify = seriesExtVO.getPk_vehicleclassify();
			TmVehicleClassifyVO vehicle = vehicleClassifyservice
					.getTmVehicleClassifyVo(pk_vehicleclassify);
			seriesExtVO.setVehiclename(null == vehicle ? "" : vehicle
					.getVclassname());
			// 获取品牌名称
			String pk_brand = vehicle.getPk_brand();
			TmBrandVO brand = brandservice.getTmBrandVo(pk_brand);
			seriesExtVO
					.setBrandname(null == brand ? "" : brand.getVbrandname());
		}
		return categoryPage;
	}

	/**
	 * 创建分页请求
	 * 
	 * @author 
	 * @date 2016年11月22日
	 * @param pageNumber
	 * @param pagzSize
	 * @param sortType
	 * @return
	 */
	private PageRequest buildPageRequest(int pageNumber, int pagzSize,
			String sortType) {
		Sort sort = null;
		if ("auto".equals(sortType)) {
			sort = new Sort(Direction.ASC, "vseriesname");
		} else if ("pk_series".equals(sortType)) {
			sort = new Sort(Direction.ASC, "pk_series");
		}
		return new PageRequest(pageNumber, pagzSize, sort);
	}

	/**
	 * 保存车系信息 TODO description
	 * 
	 * @author 
	 * @date 2016年11月22日
	 * @param pavo
	 * @param model
	 * @param request
	 * @return
	 * @throws AppBusinessException
	 */
	@RequestMapping(value = "/save", method = RequestMethod.POST)
	public @ResponseBody Map<String, Object> saveOrderInfo(
			@RequestBody SeriesExtVO pavo, ModelMap model,
			HttpServletRequest request) throws AppBusinessException {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		pavo.setCreator(AppInvocationInfoProxy.getPk_User());// 取登录用户的组织和部门信息
		pavo.setCreationtime(sdf.format(new Date()));// 设置创建时间
		Map<String, Object> result = new HashMap<String, Object>();
		String msg = ctService.saveSeries(pavo);
		result.put("msg", msg);
		return result;
	}

	/**
	 * 启用车系信息 TODO description
	 * 
	 * @author 
	 * @date 2016年11月30日
	 * @param entity
	 * @param model
	 * @param request
	 * @return
	 * @throws AppBusinessException
	 */
	@RequestMapping(value = "/start", method = RequestMethod.POST)
	public @ResponseBody JSONObject start(@RequestBody SeriesExtVO entity,
			Model model, ServletRequest request) throws AppBusinessException {
		if (entity.getPk_series() == null
				|| entity.getPk_series().trim().length() == 0) {
			throw new AppBusinessException("不存在此车系信息， pk为空");
		}
		entity.setVstatus("10051001");
		ctService.updateEntity(entity);
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("msg", "success");
		return jsonObject;
	}

	/**
	 * 停用车系信息 TODO description
	 * 
	 * @author 
	 * @date 2016年11月30日
	 * @param entity
	 * @param model
	 * @param request
	 * @return
	 * @throws AppBusinessException
	 */
	@RequestMapping(value = "/stop", method = RequestMethod.POST)
	public @ResponseBody JSONObject stop(@RequestBody SeriesExtVO entity,
			Model model, ServletRequest request) throws AppBusinessException {
		if (entity.getPk_series() == null
				|| entity.getPk_series().trim().length() == 0) {
			throw new AppBusinessException("不存在此车系信息， pk为空");
		}
		entity.setVstatus("10051002");
		ctService.updateEntity(entity);
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("msg", "success");
		return jsonObject;
	}

	/**
	 * 逻辑删除
	 * 
	 * @author 
	 * @date 2016年11月22日
	 * @param entity
	 * @param model
	 * @param request
	 * @return
	 * @throws AppBusinessException
	 */
	@RequestMapping(value = "/deletedata", method = RequestMethod.POST)
	public @ResponseBody Map<String, Object> remove(
			@RequestBody SeriesExtVO entity, Model model, ServletRequest request)
			throws AppBusinessException {
		// 判断pk是否为空
		if (entity.getPk_series() == null
				|| entity.getPk_series().trim().length() == 0) {
			throw new AppBusinessException("车系不存在，pk为空！");
		}
		ctService.deleteSeriesByIdTS(entity);// 调用业务逻辑层方法，根据主键逻辑删除
		Map<String, Object> result = new HashMap<String, Object>();
		result.put("msg", "success");
		return result;
	}

	/**
	 * 车系信息导出 TODO description
	 * 
	 * @author 
	 * @date 2017年1月12日
	 * @param pkSeriesIds
	 * @param model
	 * @param request
	 * @param response
	 * @throws AppBusinessException
	 */
	@RequestMapping(value = "/seriesDownload", method = RequestMethod.POST)
	public void seriesDownload(@RequestParam String pkSeriesIds,
			ModelMap model, HttpServletRequest request,
			HttpServletResponse response) throws AppBusinessException {
		try {
			// 数据导出容器
			List<List<Object>> list = new ArrayList<List<Object>>();
			// 标题
			List<Object> listTitle = new LinkedList<Object>();
			// 车系编码、车系名称、所属类别、所属品牌
			listTitle.add("车系编码");
			listTitle.add("车系名称");
			listTitle.add("所属类别");
			listTitle.add("所属品牌");
			list.add(listTitle);
			String sql = "  vstatus ='" + 10051001 + "'";
			List<SeriesExtVO> exportList = ctService.findListByClause(sql);
			if (null != exportList && exportList.size() > 0) {
				for (int i = 0; i < exportList.size(); i++) {
					// 车系编码、车系名称、所属类别、所属品牌
					SeriesExtVO tempBean = exportList.get(i);
					List<Object> tempList = new LinkedList<Object>();
					// 获取车辆所属类别名称
					String pk_vehicleclassify = tempBean
							.getPk_vehicleclassify();
					TmVehicleClassifyVO vehicle = vehicleClassifyservice
							.getTmVehicleClassifyVo(pk_vehicleclassify);
					tempBean.setVehiclename(null == vehicle ? "" : vehicle
							.getVclassname());
					// 获取品牌名称
					String pk_brand = vehicle.getPk_brand();
					TmBrandVO brand = brandservice.getTmBrandVo(pk_brand);
					tempBean.setBrandname(null == brand ? "" : brand
							.getVbrandname());
					tempList.add(tempBean.getVseriescode());// 车系编码
					tempList.add(tempBean.getVseriesname());// 车系名称
					tempList.add(vehicle.getVclassname());// 车辆类别
					tempList.add(brand.getVbrandname());// 所属品牌
					list.add(tempList);
				}
			}
			ExcelWriterPoiWriter.writeExcel(response, list, "车系信息", "车系信息");
		} catch (Exception e) {
			System.out.println("导出失败Error " + e);
		}
	}

	/**
	 * 通过车辆类别主键带品牌名称 TODO description
	 * 
	 * @author 
	 * @date 2016年12月27日
	 * @param pks
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/getIntRefData", method = RequestMethod.GET)
	public @ResponseBody List<Map<String, String>> getIntRefData(String pks)
			throws Exception {
		String sql = "select pk_vehicleclassify as pk,vehicle.vclassname as vehicle,brand.vbrandname as brand from tm_vehicleclassify vehicle "
				+ "left join tm_brand brand ON brand.pk_brand = vehicle.pk_brand "
				+ "where vehicle.pk_vehicleclassify in (" + pks + ");";
		List<Map<String, String>> results = ctService.getIntRefData(sql);
		// List<Map<String,String>> results = dao.queryForList(sql, new
		// MapListProcessor());
		return results;
	}
}
