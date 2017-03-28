package com.yonyou.iuap.crm.ieop.security.web.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springside.modules.web.Servlets;

import com.yonyou.iuap.crm.common.utils.AppTools;
import com.yonyou.iuap.crm.ieop.security.service.IDefineRolePermissionService;
import com.yonyou.iuap.persistence.vo.pub.BusinessException;
import com.yonyou.uap.ieop.security.entity.ExtFunction;
import com.yonyou.uap.ieop.security.entity.FunctionActivity;
import com.yonyou.uap.ieop.security.entity.RolePermission;
import com.yonyou.uap.ieop.security.service.IExtFunctionService;
import com.yonyou.uap.ieop.security.service.IExtRolePermissionService;
import com.yonyou.uap.ieop.security.service.IRolePermissionService;
import com.yonyou.uap.ieop.security.web.BaseController;

/**
 * <p>
 * Title: RoleFunctionController
 * </p>
 * <p>
 * Description:
 * </p>
 * 
 * @project： security_shiro
 * @Company: Yonyou
 * @author zhangyaoc
 * @version 1.0
 * @since JDK 1.7.0_67
 * @date May 29, 2015 3:18:34 PM
 * 
 */
@Controller
@RequestMapping(value = "/security/extrolefunctiondefine")
public class DefineRoleFunctionController extends BaseController {

	private final Logger logger = LoggerFactory.getLogger(getClass());
	@Autowired
	private IDefineRolePermissionService defineservice;
	@Autowired
	private IRolePermissionService service;
	@Autowired
	private IExtRolePermissionService extservice;
	@Autowired
	private IExtFunctionService extFuncService;
	
	/**
	 * 角色功能分配
	 * 提供通过获取功能IDS的方式
	* TODO description
	* @author 
	* @date 2016年12月8日
	* @param param
	* @param resq
	* @return
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "createRoleFunc", method = RequestMethod.POST)
	public @ResponseBody Map<String,Object> createRoleFunctionRelation(@RequestBody JSONObject param, HttpServletRequest resq) {
//		JSONArray datas = param.getJSONArray("datas");
		String roleid = param.getString("roleid");
		String rolecode = param.getString("rolecode");
		String funcId = param.getString("datas");
		Map<String,Object> result = new HashMap<String,Object>();
		try {
			defineservice.createRoleFuncRelation(roleid, rolecode, funcId);
		} catch (Exception e) {
			// 记录日志
			logger.error("保存出错!", e);
			result.put("flag", AppTools.FAILED);
			result.put("msg", e.getMessage());
		}
		result.put("flag", AppTools.SUCCESS);
		result.put("msg", "success");
		return result;
	}
	
	/**
	 * 获取有权限的功能树
	 * @param roleId
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "pagePermFunctionDefine/{id}", method = RequestMethod.GET)
	public @ResponseBody List<ExtFunction> permissionFunction(
			@PathVariable("id") String roleId) throws Exception {
		return defineservice.queryHasPermissionFunction(roleId);
	}
	
	/** 进入新增 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "createBatchBtn", method = RequestMethod.POST)
	public @ResponseBody Map<String,Object> createBatchFunctionActivity(@RequestBody JSONObject param, HttpServletRequest resq) {
		JSONArray datas = param.getJSONArray("datas");
//		List<FunctionActivity> list = (List<FunctionActivity>) JSONArray.toCollection(datas,  
//				FunctionActivity.class);
		List<FunctionActivity> list = AppTools.JSONArrayToList(datas,FunctionActivity.class);
		String roleid = param.getString("roleid");
		String rolecode = param.getString("rolecode");
		String funcId = param.getString("funcId");
		Map<String,Object> result = new HashMap<String,Object>();
		try {
			defineservice.createActivityPermissionBatch(list, roleid, rolecode, funcId);
		} catch (Exception e) {
			// 记录日志
			logger.error("保存出错!", e);
			result.put("flag", AppTools.FAILED);
			result.put("msg", e.getMessage());
		}
		result.put("flag", AppTools.SUCCESS);
		result.put("msg", "success");
		return result;
	}
	
	@RequestMapping(value = "page", method = RequestMethod.GET)
	public @ResponseBody Page<RolePermission> page(
			@RequestParam(value = "page", defaultValue = "1") int pageNumber,
			@RequestParam(value = "page.size", defaultValue = "20") int pageSize,
			@RequestParam(value = "sortType", defaultValue = "auto") String sortType,
			Model model, ServletRequest request) {

		Map<String, Object> searchParams = new HashMap<String, Object>();
//		String queryCon = request.getParameter("searchText");
//		searchParams.put(Operator.LIKE + "_roleName", queryCon);
		searchParams = Servlets.getParametersStartingWith(request, "search_");
		PageRequest pageRequest = buildPageRequest(pageNumber, pageSize,
				sortType);

		Page<RolePermission> rolePage=null;
		try {
			rolePage = service.getRoleFuncPage(
					searchParams, pageRequest);
		} catch (Exception e) {
			logger.error("查询出错", e);
		}
		return rolePage;
	}
	
	@RequestMapping(value = "pagePermAction", method = RequestMethod.GET)
	public @ResponseBody List<String> permissionAction(
			@RequestParam(value = "roleId") String roleId,
			@RequestParam(value = "funcId") String funcId,
			Model model, ServletRequest request) throws Exception {
		try {
			ExtFunction extFunc = extFuncService.getFuncById(funcId);
			if(extFunc != null){
				if(extFunc.isEnableAction()){
					//启动按钮权限
					return extservice.queryHasPermissionFuncActivity(roleId, funcId);
				}else{
					throw new BusinessException("没有启用按钮权限");
				}
			}
			return null;
		} catch (Exception e) {
			logger.error("查询出错", e);
			throw e;
		}
	}

	/** 进入新增 */
	@RequestMapping(value = "create", method = RequestMethod.GET)
	public @ResponseBody RolePermission add() {
		RolePermission entity = new RolePermission();
		return entity;
	}
	
	/** 进入新增 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "createBatch", method = RequestMethod.POST)
	public @ResponseBody Map<String,Object> createBatch(@RequestBody JSONObject param, HttpServletRequest resq) {
		JSONArray datas = param.getJSONArray("datas");
//		JSONUtils.getMorpherRegistry().registerMorpher(new TimestampMorpher());
//		List<ExtFunction> list = (List<ExtFunction>) JSONArray.toCollection(datas,  
//                ExtFunction.class);
		List<ExtFunction> list = AppTools.JSONArrayToList(datas,ExtFunction.class);
		String roleid = param.getString("roleid");
		String rolecode = param.getString("rolecode");
		Map<String,Object> result = new HashMap<String,Object>();
		try {
			extservice.createFuncPermissionBatch(list, roleid, rolecode);
		} catch (Exception e) {
			// 记录日志
			logger.error("保存出错!", e);
			result.put("flag", AppTools.FAILED);
			result.put("msg", e.getMessage());
		}
		result.put("flag", AppTools.SUCCESS);
		result.put("msg", "success");
		return result;
	}
	
	/** 保存新增 */
	@RequestMapping(value = "create", method = RequestMethod.POST)
	public @ResponseBody RolePermission create(
			@RequestBody RolePermission entity, HttpServletRequest resq) {

		try {

			entity = service.save(entity);
		} catch (Exception e) {
			// 记录日志
			logger.error("保存出错!", e);
		}
		return entity;
	}

	/**
	 * 进入更新界面
	 * 
	 * @param id
	 * @param model
	 * @return 需要更新的实体的json结构
	 */
	@RequestMapping(value = "update/{id}", method = RequestMethod.GET)
	public @ResponseBody RolePermission updateForm(
			@PathVariable("id") String id, Model model) {
		RolePermission entity=null;
		try {
			entity = service.get(id);
		} catch (Exception e) {
			logger.error("查询出错", e);
		}
		return entity;
	}


	/** 保存更新 */
	@RequestMapping(value = "update", method = RequestMethod.POST)
	public @ResponseBody RolePermission update(@RequestBody RolePermission entity) {
		try {
			entity = service.save(entity);
		} catch (Exception e) {
			logger.error("更新出错!", e);
		}
		return entity;
	}

	/**
	 * 删除实体
	 * 
	 * @param id
	 *            删除的标识
	 * @return 是否删除成功
	 */
	@RequestMapping(value = "delete/{id}", method = RequestMethod.DELETE)
	public @ResponseBody boolean delete(@PathVariable("id") String id) {
		try {
			service.delete(id);
		} catch (Exception e) {
			logger.error("更新出错", e);
		}
		return true;
	}

}
