package com.yonyou.iuap.crm.ieop.security.web.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Validator;

import net.sf.json.JSONObject;

import org.apache.shiro.SecurityUtils;
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
import com.yonyou.iuap.crm.ieop.security.service.IDefineFunctionService;
import com.yonyou.uap.ieop.security.entity.ExtFunction;
import com.yonyou.uap.ieop.security.entity.SecurityUser;
import com.yonyou.uap.ieop.security.service.IExtFunctionService;
import com.yonyou.uap.ieop.security.service.ISecurityUserService;
import com.yonyou.uap.ieop.security.web.BaseController;

/**
 * <p>
 * Title: ExtFunctionController
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
 * @date May 29, 2015 3:18:20 PM
 */
@Controller
@RequestMapping(value = "/security/extfunctiondefine")
public class DefineFunctionController extends BaseController {

	@Autowired
	private IExtFunctionService service;
	
	@Autowired
	private IDefineFunctionService definefuncservice;

	@Autowired
	private ISecurityUserService accountService;
	
	private final Logger logger = LoggerFactory.getLogger(getClass());

	@Autowired
	private Validator validator;
	
	@RequestMapping(value = "rootmenu", method = RequestMethod.GET)
	public @ResponseBody ExtFunction getRootMenu(Model model,
			HttpServletRequest request) throws Exception {
		String username = null;
		if (SecurityUtils.getSubject().getPrincipal() != null)
			username = (String) SecurityUtils.getSubject().getPrincipal();
		if (username == null || SecurityUtils.getSubject().hasRole("admin")) {
			try {
				return definefuncservice.getFuncRoot();
			} catch (Exception e) {
				throw new Exception("获取菜单树出错",e);
			}
		}
		SecurityUser cuser = null;
		try {
			cuser = accountService.findUserByLoginName(username);
		} catch (Exception e) {
			throw new Exception("查询登陆用户错误",e);
		}
		if (cuser!=null) {
			try {
				return definefuncservice.getFuncRootByUser(cuser.getId());
			} catch (Exception e) {
				throw new Exception("获取菜单树出错",e);
			}
		}else{
			return null;
		}
	}
	
	@RequestMapping(value = "page", method = RequestMethod.GET)
	public @ResponseBody Page<ExtFunction> page(
			@RequestParam(value = "page", defaultValue = "1") int pageNumber,
			@RequestParam(value = "page.size", defaultValue = "100") int pageSize,
			@RequestParam(value = "sortType", defaultValue = "auto") String sortType,
			Model model, ServletRequest request) {
		Map<String, Object> searchParams = new HashMap<String, Object>();
//		String queryCon = request.getParameter("searchText");
//		searchParams.put(Operator.LIKE + "_name", queryCon);
		searchParams = Servlets.getParametersStartingWith(request, "search_");
		PageRequest pageRequest = buildPageRequest(pageNumber, pageSize,
				sortType);
		Page<ExtFunction> categoryPage=null;
		try {
			categoryPage = service.getDemoPage(searchParams,
					pageRequest);
		} catch (Exception e) {
			logger.error("查询出错", e);
		}
		return categoryPage;
	}
	
	@RequestMapping(value = "list", method = RequestMethod.GET)
	public @ResponseBody List<ExtFunction> pagelist(Model model, ServletRequest request) throws Exception{
		List<ExtFunction> allFunctions = null;
		try {
			allFunctions = service.findAll();
		} catch (Exception e) {
			throw new Exception("获取功能节点出错");
		}
		return allFunctions;
	}

	
	/**
	 * 功能：根据组织编码，查询组织分页数据
	 * 参数：pk_corp,pageNumber,pageSize,sortType
	 * 返回：Page<ExtFunction>
	 * 日期：2016-04-12
	 * 作者：ytq
	 * @throws Exception 
	 */	
	@RequestMapping(value = "/queryFunsByID", method = RequestMethod.GET)
	public @ResponseBody Page<ExtFunction> queryFunsByID(@RequestParam(value = "", defaultValue = "") String id,@RequestParam(value = "page", defaultValue = "0")  int pageNumber, @RequestParam(value = "page.size", defaultValue = "10") int pageSize, @RequestParam(value = "sortType", defaultValue = "auto") String sortType, Model model, ServletRequest request) throws Exception {
		Page<ExtFunction> categoryPage = service.queryChildFunsByID(id);
		return categoryPage;
	}	

	/**
	 * 功能：根据组织编码，查询组织分页数据
	 * 参数：pk_corp,pageNumber,pageSize,sortType
	 * 返回：Page<ExtFunction>
	 * 日期：2016-04-12
	 * 作者：ytq
	 * @throws Exception 
	 */	
	@RequestMapping(value = "/queryFunByID", method = RequestMethod.GET)
	public @ResponseBody Page<ExtFunction> queryFunByID(@RequestParam(value = "", defaultValue = "") String id,@RequestParam(value = "page", defaultValue = "0")  int pageNumber, @RequestParam(value = "page.size", defaultValue = "10") int pageSize, @RequestParam(value = "sortType", defaultValue = "auto") String sortType, Model model, ServletRequest request) throws Exception {
		Page<ExtFunction> categoryPage = service.queryFunByID(id);
		return categoryPage;
	}	
	
	
	
	/**
	 * 功能：根据meau_url查询本机以及上级
	 * 参数：pk_corp,pageNumber,pageSize,sortType
	 * 返回：Page<ExtFunction>
	 * 日期：2016-04-12
	 * 作者：ytq
	 * @throws Exception 
	 */	
	@RequestMapping(value = "/queryThisAndFathFunByMeauurl", method = RequestMethod.POST)
	public @ResponseBody List<String> queryThisAndFathFunByMeauurl(String id) throws Exception {
		List<String> categoryPage = service.queryThisAndFathFunByMeauurl(id);
		return categoryPage;
	}
	
	/** 进入新增 */
	@RequestMapping(value = "create", method = RequestMethod.GET)
	public @ResponseBody ExtFunction add() {
		ExtFunction entity = new ExtFunction();
		return entity;
	}

	/** 保存新增 */
	@RequestMapping(value = "create", method = RequestMethod.POST)
	public @ResponseBody JSONObject create(@RequestBody ExtFunction entity,
			HttpServletRequest resq) {
		JSONObject result = new  JSONObject();
		try {
			result = service.saveEntity(entity);
			//InnerCodeUtil.generateInnerCodeOfAllTable("ieop_function", "id", "parent_id");
		} catch (Exception e) {
			logger.error("保存出错!", e);
			result.put("flag", AppTools.FAILED);
			result.put("msg", e.getMessage());
		}
		return result;
	}

	/**
	 * 进入更新界面
	 * 
	 * @param id
	 * @param model
	 * @return 需要更新的实体的json结构
	 */
	@RequestMapping(value = "update/{id}", method = RequestMethod.GET)
	public @ResponseBody ExtFunction updateForm(@PathVariable("id") String id,
			Model model) {
		ExtFunction entity=null;
		try {
			entity = service.getFuncById(id);
		} catch (Exception e) {
			logger.error("查询出错", e);
		}
		return entity;
	}

	/** 保存更新 */
	@RequestMapping(value = "update", method = RequestMethod.POST)
	public @ResponseBody JSONObject update(@RequestBody ExtFunction entity) {
		JSONObject result = null ;
		try {
			result = service.saveEntity(entity);
		} catch (Exception e) {
			logger.error("更新出错!", e);
			result.put("flag", AppTools.FAILED);
			result.put("msg", e.getMessage());
		}
		return result;
	}

	/**
	 * 删除实体
	 * 
	 * @param id
	 *            删除的标识
	 * @return 是否删除成功
	 */
	@RequestMapping(value = "delete/{id}", method = RequestMethod.DELETE)
	public @ResponseBody Map<String, Object> delete(@PathVariable("id") String id) {
		Map<String,Object> result = new HashMap<String,Object>();
		try {
			List<ExtFunction> listvo = service.querylast(id);
			List<ExtFunction> listvos = service.getBterSubDepts(id);
			if(listvo!=null && !listvo.isEmpty()){
				result.put("msg","该节点已被引用，无法删除！");
				result.put("flag", AppTools.FAILED);
			}else{
				if(listvos.size()>0){
					result.put("msg", "该功能有下级节点，不能删除！");
					result.put("flag", AppTools.FAILED);
					return result;
				}else{
					service.deleteById(id);
					result.put("flag", AppTools.SUCCESS);
					return result;
				}
			}
			return result;
		} catch (Exception e) {
			logger.error("删除出错", e);
		}
		return result;
	}

	/**
	 * 功能：停用
	 * 参数：pk
	 * 返回：Map<String,Object>
	 * 日期：2016-04-12
	 * 作者：ytq
	 */		
	@RequestMapping(value = "/stop", method = RequestMethod.POST)
	public @ResponseBody JSONObject stopCorpPost(@RequestParam(value = "",required=true) String pk, Model model, ServletRequest request) {
		JSONObject result = new JSONObject(); 	
		try{
			service.stopFun(pk);
			result.put("flag", AppTools.SUCCESS);
		}catch(Exception e){
			logger.error("停用出错："+e);	
			result.put("flag", AppTools.FAILED);
		}	
		return result;
	}	
	
	/**
	 * 功能：启用
	 * 参数：pk
	 * 返回：Map<String,Object>
	 * 日期：2016-04-12
	 * 作者：ytq
	 */			
	@RequestMapping(value = "/start", method = RequestMethod.POST)
	public @ResponseBody JSONObject startCorpPost(@RequestParam(value = "",required=true) String pk, Model model, ServletRequest request){
		JSONObject result = new JSONObject(); 
		try{
			service.startFun(pk);
			result.put("flag", AppTools.SUCCESS);
		}catch(Exception e){
			logger.error("启用出错："+e);	
			result.put("flag", AppTools.FAILED);
		}	
		return result;
	}	
}
