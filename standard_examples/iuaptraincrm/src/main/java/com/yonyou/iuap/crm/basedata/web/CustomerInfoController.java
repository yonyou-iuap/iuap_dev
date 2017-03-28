/** 
 *Copyright 2016 Yonyou Corporation Ltd. All Rights Reserved.
 * This software is published under the terms of the Yonyou Software
 * License version 1.0, a copy of which has been included with this
 * distribution in the LICENSE.txt file.
 *
 * @Project Name : iuaptraincrm
 *
 * @File name : CustomerInfoController.java
 *
 * @author 
 *
 * @Date : 2016年12月8日
 *
 ----------------------------------------------------------------------------------
 *     Date       Who       Version     Comments
 * 1. 2016年12月8日    name    1.0
 *
 *
 *
 *
 ----------------------------------------------------------------------------------
 */

package com.yonyou.iuap.crm.basedata.web;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Validator;

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
import org.springside.modules.beanvalidator.BeanValidators;

import com.yonyou.iuap.crm.basedata.entity.TmCustomerVehicleExt2VO;
import com.yonyou.iuap.crm.basedata.entity.TmCustomerVehicleExtVO;
import com.yonyou.iuap.crm.basedata.entity.TmCustomercontactorVO;
import com.yonyou.iuap.crm.basedata.entity.TmCustomerinfoExtVO;
import com.yonyou.iuap.crm.basedata.entity.TmCustomerinfoVO;
import com.yonyou.iuap.persistence.vo.pub.BusinessException;
import com.yonyou.iuap.crm.basedata.service.itf.ICustomerInfoService;
import com.yonyou.iuap.crm.common.exception.AppBusinessException;
import com.yonyou.iuap.crm.common.utils.AppInvocationInfoProxy;
import com.yonyou.iuap.crm.common.utils.DictCode;

/**
 * 客户信息维护控制器
 * 
 * @author 
 * @date 2016年12月8日
 */
@Controller
@RequestMapping(value = "/bd/customerinfo")
public class CustomerInfoController {
	@Autowired
	private ICustomerInfoService customerinfoService;
	@Autowired
	private Validator validator;

	/**
	 * 根据查询条件， 获取客户信息分页列表数据
	 * 
	 * @author 
	 * @date 2016年12月12日
	 * @param sortType
	 * @param parammap
	 * @param model
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/queryPage", method = RequestMethod.POST)
	public @ResponseBody Page<TmCustomerinfoExtVO> loadData(
			@RequestParam(value = "sortType", defaultValue = "auto") String sortType,
			@RequestBody Map<String, Object> parammap, Model model,
			ServletRequest request) throws Exception {
		int pindex = null == parammap.get("pindex") ? 0 : Integer
				.valueOf(parammap.get("pindex").toString());
		int psize = null == parammap.get("psize") ? 0 : Integer
				.valueOf(parammap.get("psize").toString());
		PageRequest pageRequest = buildPageRequest(pindex, psize, "auto");

		Page<TmCustomerinfoExtVO> categoryPage = customerinfoService
				.getCustomerinfoExtByPages(parammap, pageRequest);
		return categoryPage;
	}

	/**
	 * 启用客户信息
	 * 
	 * @author 
	 * @date 2016年12月12日
	 * @param entity
	 * @param model
	 * @param request
	 * @return
	 * @throws AppBusinessException
	 */
	@RequestMapping(value = "/start", method = RequestMethod.POST)
	public @ResponseBody JSONObject startCustomerinfo(
			@RequestBody List<TmCustomerinfoVO> entityLst, Model model,
			ServletRequest request) throws AppBusinessException {

		for (TmCustomerinfoVO entity : entityLst) {
			entity.setVstatus(DictCode.ALREADY_START_STATUS);
		}

		customerinfoService.batchUpdateCustomerinfo(entityLst);

		JSONObject jsonObject = new JSONObject();
		jsonObject.put("msg", "success");
		return jsonObject;
	}

	/**
	 * 停用客户信息
	 * 
	 * @author 
	 * @date 2016年12月12日
	 * @param entity
	 * @param model
	 * @param request
	 * @return
	 * @throws AppBusinessException
	 */
	@RequestMapping(value = "/stop", method = RequestMethod.POST)
	public @ResponseBody JSONObject stopCustomerinfo(
			@RequestBody List<TmCustomerinfoVO> entityLst, Model model,
			ServletRequest request) throws AppBusinessException {

		for (TmCustomerinfoVO entity : entityLst) {
			entity.setVstatus(DictCode.ALREADY_STOP_STATUS);
		}

		customerinfoService.batchUpdateCustomerinfo(entityLst);

		JSONObject jsonObject = new JSONObject();
		jsonObject.put("msg", "success");
		return jsonObject;
	}

	/**
	 * 更新客户信息
	 * 
	 * @author 
	 * @date 2016年12月12日
	 * @param mainVo
	 * @param model
	 * @param request
	 * @return
	 * @throws BusinessException
	 */
	@RequestMapping(value = "/update", method = RequestMethod.POST)
	public @ResponseBody Object updateCustomerinfo(
			@RequestBody TmCustomerinfoVO mainVo, ModelMap model,
			HttpServletRequest request) throws BusinessException {

		BeanValidators.validateWithException(validator, mainVo);

		Map<String, Object> result = new HashMap<String, Object>();
		try {
			customerinfoService.updateCustomerinfo(mainVo);
			result.put("msg", "保存成功!");
			result.put("flag", "success");
		} catch (Exception e) {
			result.put("msg", "保存失败!");
			result.put("flag", "fail");

			throw new AppBusinessException(e.getMessage());
		}
		return result;
	}

	/**
	 * 合并客户信息
	 * 
	 * @author 
	 * @date 2016年12月12日
	 * @param mainVo
	 * @param model
	 * @param request
	 * @return
	 * @throws BusinessException
	 */
	@RequestMapping(value = "/merge", method = RequestMethod.POST)
	public @ResponseBody Object mergeCustomerinfo(
			@RequestBody Map<String, Object> mainVo, ModelMap model,
			HttpServletRequest request) throws BusinessException {

		Map<String, Object> result = new HashMap<String, Object>();
		try {
			customerinfoService.mergeCustomerinfo(mainVo);
			result.put("msg", "保存成功!");
			result.put("flag", "success");
		} catch (Exception e) {
			result.put("msg", "保存失败!");
			result.put("flag", "fail");

			throw new AppBusinessException(e.getMessage());
		}
		return result;
	}

	/**
	 * 保存客户信息和联系人信息
	 * 
	 * @author 
	 * @date 2016年12月22日
	 * @param pavo
	 * @param model
	 * @param request
	 * @return
	 * @throws BusinessException
	 */
	@RequestMapping(value = "/saveCustomerinfo", method = RequestMethod.POST)
	public @ResponseBody Object saveCustomerinfo(
			@RequestBody TmCustomerinfoVO pavo, ModelMap model,
			HttpServletRequest request) throws BusinessException {

		BeanValidators.validateWithException(validator, pavo);

		Map<String, Object> result = new HashMap<String, Object>();

		List<TmCustomercontactorVO> mpvos = pavo.getItems();
		try {

			customerinfoService.saveCustomerinfo(pavo, mpvos);
			result.put("msg", "保存成功!");
			result.put("flag", "success");
		} catch (Exception e) {
			result.put("msg", "保存失败!");
			result.put("flag", "fail");

			throw new AppBusinessException(e.getMessage());
		}

		return result;
	}

	@RequestMapping(value = "/save", method = RequestMethod.POST)
	public @ResponseBody JSONObject saveCustomerinfo2(
			@RequestBody TmCustomerinfoVO pavo, ModelMap model,
			HttpServletRequest request) throws BusinessException {

		BeanValidators.validateWithException(validator, pavo);

		JSONObject jsonObject = new JSONObject();
		String msg = customerinfoService.saveCustomerinfo(pavo);

		jsonObject.put("msg", msg);
		return jsonObject;
	}

	/**
	 * 查询与客户相关的车辆信息
	 * 
	 * @author 
	 * @date 2016年12月12日
	 * @param pk
	 * @param pageItemNumber
	 * @param pageItemSize
	 * @param sortItemType
	 * @param model
	 * @param request
	 * @return
	 * @throws BusinessException
	 */
	@RequestMapping(value = "/queryVehicle", method = RequestMethod.GET)
	public @ResponseBody Object loadVehicleData(
			@RequestParam String pk,
			@RequestParam(value = "itempage", defaultValue = "0") int pageItemNumber,
			@RequestParam(value = "itempage.size", defaultValue = "10") int pageItemSize,
			@RequestParam(value = "itemsortType", defaultValue = "auto") String sortItemType,
			ModelMap model, HttpServletRequest request)
			throws BusinessException {
		Map<String, Object> result = new HashMap<String, Object>();

		// 如果主键为空， 返回空值
		if (pk.equals(""))
			return result;
		// 查询条件，根据外键查询
//		Map<String, Object> searchParams = new HashMap<String, Object>();
//		searchParams.put("pk_customerinfo", pk);
		try {
			// 预拨回款明细分页查询
			PageRequest pageItemRequest = buildPageRequest(pageItemNumber,
					pageItemSize, sortItemType);
			Page<TmCustomerVehicleExt2VO> itemList = customerinfoService
					.getCustomerVehicleExt2ByPages(pk, pageItemRequest);

			result.put("itemList", itemList);
			result.put("msg", "查询成功!");
			result.put("flag", "success");
		} catch (Exception e) {
			result.put("msg", "查询失败!");
			result.put("flag", "fail");
			
			throw new AppBusinessException(e.getMessage());
		}
		return result;
	}

	/**
	 * 查询与客户相关的联系人信息
	 * 
	 * @author 
	 * @date 2016年12月12日
	 * @param pk
	 * @param pageItemNumber
	 * @param pageItemSize
	 * @param sortItemType
	 * @param model
	 * @param request
	 * @return
	 * @throws BusinessException
	 */
	@RequestMapping(value = "/queryContact", method = RequestMethod.GET)
	public @ResponseBody Object loadContactorData(
			@RequestParam String pk,
			@RequestParam(value = "itempage", defaultValue = "0") int pageItemNumber,
			@RequestParam(value = "itempage.size", defaultValue = "10") int pageItemSize,
			@RequestParam(value = "itemsortType", defaultValue = "auto") String sortItemType,
			ModelMap model, HttpServletRequest request)
			throws BusinessException {
		Map<String, Object> result = new HashMap<String, Object>();

		// 如果主键为空， 返回空值
		if (pk.equals(""))
			return result;
		// 查询条件，根据外键查询
		Map<String, Object> searchParams = new HashMap<String, Object>();
		searchParams.put("pk_customerinfo", pk);
		
		try {
			// 预拨回款明细分页查询
			PageRequest pageItemRequest = buildPageRequest(pageItemNumber,
					pageItemSize, sortItemType);
			Page<TmCustomercontactorVO> itemList = customerinfoService
					.getCustomercontactorByPages(searchParams, pageItemRequest);

			result.put("itemList", itemList);
			result.put("msg", "查询成功!");
			result.put("flag", "success");
		} catch (Exception e) {
			result.put("msg", "查询失败!");
			result.put("flag", "fail");
			
			throw new AppBusinessException(e.getMessage());
		}
		return result;
	}

	/**
	 * 保存联系人信息
	 * 
	 * @author 
	 * @date 2016年12月12日
	 * @param mainVo
	 * @param model
	 * @param request
	 * @return
	 * @throws BusinessException
	 */
	@RequestMapping(value = "/saveContact", method = RequestMethod.POST)
	public @ResponseBody Object saveContact(
			@RequestBody TmCustomercontactorVO mainVo, ModelMap model,
			HttpServletRequest request) throws BusinessException {
		BeanValidators.validateWithException(validator, mainVo);
		Map<String, Object> result = new HashMap<String, Object>();

		try {
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			mainVo.setCreator(AppInvocationInfoProxy.getPk_User());
			mainVo.setCreationtime(sdf.format(new Date()));

			customerinfoService.saveCustomercontactor(mainVo);
			result.put("msg", "保存成功!");
			result.put("flag", "success");
		} catch (Exception e) {
			result.put("msg", "保存失败!");
			result.put("flag", "fail");
			
			throw new AppBusinessException(e.getMessage());
		}
		return result;
	}

	/**
	 * 批量删除联系人信息
	 * 
	 * @author 
	 * @date 2016年12月12日
	 * @param entitys
	 * @param model
	 * @param request
	 * @return
	 * @throws BusinessException
	 */
	@RequestMapping(value = "/deleteContactors", method = RequestMethod.POST)
	public @ResponseBody Object deleteItems(
			@RequestBody List<TmCustomercontactorVO> entitys, ModelMap model,
			HttpServletRequest request) throws BusinessException {
		Map<String, Object> result = new HashMap<String, Object>();
		try {
			customerinfoService.deleteBatchCustomercontactor(entitys);
			result.put("msg", "删除成功!");
			result.put("flag", "success");
		} catch (Exception e) {
			result.put("msg", "删除失败!");
			result.put("flag", "fail");
			
			throw new AppBusinessException(e.getMessage());
		}
		return result;
	}

	/**
	 * 查询车辆信息
	 * 
	 * @author 
	 * @date 2017年1月16日
	 * @param pk
	 * @param pageItemNumber
	 * @param pageItemSize
	 * @param sortItemType
	 * @param model
	 * @param request
	 * @return
	 * @throws BusinessException
	 */
	@RequestMapping(value = "/queryVehicleExt", method = RequestMethod.POST)
	public @ResponseBody Object queryVehicleExtData(
			@RequestBody Map<String, String> param,
			@RequestParam(value = "itempage", defaultValue = "0") int pageItemNumber,
			@RequestParam(value = "itempage.size", defaultValue = "10") int pageItemSize,
			@RequestParam(value = "itemsortType", defaultValue = "auto") String sortItemType,
			ModelMap model, HttpServletRequest request) throws Exception {
		Map<String, Object> result = new HashMap<String, Object>();
		try {
			// 查询条件，根据外键查询
			Map<String, Object> searchParams = new HashMap<String, Object>();

			PageRequest pageItemRequest = buildPageRequest(pageItemNumber,
					pageItemSize, "auto");
			Page<TmCustomerVehicleExtVO> itemList = customerinfoService
					.getVehicleExtByPages(param, pageItemRequest);

			result.put("itemList", itemList);
			result.put("msg", "查询成功!");
			result.put("flag", "success");
		} catch (Exception e) {
			result.put("msg", "查询失败!");
			result.put("flag", "fail");
			
			throw new AppBusinessException(e.getMessage());
		}
		return result;
	}

	/**
	 * 创建分页请求
	 * 
	 * @author 
	 * @date 2016年11月28日
	 * @param pageNumber
	 * @param pagzSize
	 * @param sortType
	 * @return
	 */
	private PageRequest buildPageRequest(int pageNumber, int pagzSize,
			String sortType) {
		Sort sort = null;
		if ("auto".equals(sortType)) {
			sort = new Sort(Direction.DESC, "ts");
		} else if ("creationtime".equals(sortType)) {
			sort = new Sort(Direction.DESC, "creationtime");
		}
		return new PageRequest(pageNumber, pagzSize, sort);
	}
}
