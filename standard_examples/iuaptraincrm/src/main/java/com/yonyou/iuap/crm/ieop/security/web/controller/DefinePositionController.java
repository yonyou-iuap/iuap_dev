package com.yonyou.iuap.crm.ieop.security.web.controller;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springside.modules.web.Servlets;

import com.yonyou.iuap.crm.common.exception.AppBusinessException;
import com.yonyou.iuap.crm.common.utils.AppInvocationInfoProxy;
import com.yonyou.iuap.crm.common.utils.AppTools;
import com.yonyou.iuap.crm.ieop.security.entity.DefinePositionVO;
import com.yonyou.iuap.crm.ieop.security.entity.DefineRolePostitionVO;
import com.yonyou.iuap.crm.ieop.security.entity.DefineRoleSaveVO;
import com.yonyou.iuap.crm.ieop.security.entity.DefineUserPositionVO;
import com.yonyou.iuap.crm.ieop.security.service.IDefinePositionService;
import com.yonyou.iuap.persistence.vo.pub.BusinessException;
import com.yonyou.iuap.persistence.vo.pub.VOStatus;

/**
 * 岗位维护
* <p>description：</p>
* @author 
* @created 2016年12月23日 上午9:51:01
* @version 1.0
 */
@Controller
@RequestMapping(value = "/security/extposition")
public class DefinePositionController {
	@Autowired
	private IDefinePositionService service;
	
	/**
	 * 分页查询
	* TODO description
	* @author 
	* @date 2016年12月23日
	* @param pageNumber
	* @param pageSize
	* @param sortType
	* @param model
	* @param request
	* @return
	* @throws Exception
	 */
	@RequestMapping(value="/main",method=RequestMethod.GET)
	public @ResponseBody Page<DefinePositionVO> loadData(
			@RequestParam(value = "page", defaultValue = "0") int pageNumber,
			@RequestParam(value = "page.size", defaultValue = "10") int pageSize,
			@RequestParam(value = "sortType", defaultValue = "auto") String sortType,
			Model model, ServletRequest request) throws Exception {
			Map<String, Object> searchParams = new HashMap<String, Object>();
			searchParams = Servlets.getParametersStartingWith(request, "search_");
			PageRequest pageRequest = buildPageRequest(pageNumber, pageSize, "auto");
			Page<DefinePositionVO> categoryPage = service.getPostitionBypages(searchParams, pageRequest);
			return categoryPage;
	}
	
	/**
	 * 保存
	* TODO description
	* @author 
	* @date 2016年12月23日
	* @param mainVo
	* @param model
	* @param request
	* @return
	* @throws BusinessException
	 */
	@RequestMapping(value = "/save", method = RequestMethod.POST)
	public @ResponseBody Object savePosition(
			@RequestBody DefinePositionVO mainVo, ModelMap model,
			HttpServletRequest request) throws BusinessException {
		Map<String,Object> result = new HashMap<String,Object>();
		try {
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			mainVo.setId(AppTools.generatePK());
			mainVo.setStatus(VOStatus.NEW);
			mainVo.setPk_org(AppInvocationInfoProxy.getPk_Corp());//组织
			mainVo.setPk_dept(AppInvocationInfoProxy.getPk_Dept());//部门
			mainVo.setCreator(AppInvocationInfoProxy.getPk_User());//取登录用户的组织和部门信息
			mainVo.setCreationtime(sdf.format(new Date()));
			mainVo.setDr(0);

			String errorMsg = service.save(mainVo);
			if(null != errorMsg && errorMsg.length()>0){
				result.put("msg", errorMsg);
				result.put("flag", "fail");
			}else{
				result.put("msg", "保存成功!");
				result.put("flag", "success");
			}
			
		} catch (Exception e) {
			result.put("msg", "保存失败!");
			result.put("flag", "fail");
		}
        return result;  
	}
	
	/**
	 * 修改
	* TODO description
	* @author 
	* @date 2016年11月21日
	* @param mainVo
	* @param model
	* @param request
	* @return
	* @throws BusinessException
	 */
	@RequestMapping(value = "/update", method = RequestMethod.POST)
	public @ResponseBody Object updateOrderInfo(
			@RequestBody DefinePositionVO mainVo, ModelMap model,
			HttpServletRequest request) throws BusinessException {
		Map<String,Object> result = new HashMap<String,Object>();
		try {
			//设置状态为NEW，才会插入新数据
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			mainVo.setStatus(VOStatus.UPDATED);
			mainVo.setModifier(AppInvocationInfoProxy.getPk_User());//取登录用户的组织和部门信息
			mainVo.setModifiedtime(sdf.format(new Date()));
			String errorMsg = service.modify(mainVo);
			if(null != errorMsg && errorMsg.length()>0){
				result.put("msg", errorMsg);
				result.put("flag", "fail");
			}else{
				result.put("msg", "修改成功!");
				result.put("flag", "success");
			}
		} catch (Exception e) {
			result.put("msg", "修改失败!");
			result.put("flag", "fail");
		}
        return result;  
	}
	
	/**
	 * 根据岗位ID查询角色
	* TODO description
	* @author 
	* @date 2016年12月23日
	* @param positionId
	* @return
	 * @throws AppBusinessException 
	 */
	@RequestMapping(value = "query/{id}", method = RequestMethod.GET)
	public @ResponseBody List<DefineRoleSaveVO> getRoleByPositionId(@PathVariable("id") String positionId) throws AppBusinessException{
		return (List<DefineRoleSaveVO>) service.getRoleByPositionId(positionId);
	}
	
	/**
	 * 根据用户Id查询岗位信息
	* TODO description
	* @author 
	* @date 2016年12月26日
	* @param userId
	* @return
	 * @throws AppBusinessException 
	 */
	@RequestMapping(value = "queryuser/{id}", method = RequestMethod.GET)
	public @ResponseBody List<DefinePositionVO> queryWithUserId(@PathVariable("id") String userId) throws AppBusinessException{
		return (List<DefinePositionVO>) service.getPositionWithUserId(userId);
	}
	
	/**
	 * 根据用户Id查询未分配的岗位信息
	* TODO description
	* @author 
	* @date 2016年12月26日
	* @param userId
	* @return
	 * @throws AppBusinessException 
	 */
	@RequestMapping(value = "queryAddForUser/{id}", method = RequestMethod.GET)
	public @ResponseBody List<DefinePositionVO> queryAddPositionForUser(@PathVariable("id") String userId) throws AppBusinessException{
		return (List<DefinePositionVO>) service.queryPositionForUserAdd(userId);
	}
	
	/**
	 * 为用户分配岗位
	* TODO description
	* @author 
	* @date 2016年12月26日
	* @param mainVo
	* @param model
	* @param request
	* @return
	* @throws BusinessException
	 */
	@RequestMapping(value = "/savePositionForUser", method = RequestMethod.POST)
	public @ResponseBody Object savePositionForUser(
			@RequestBody DefineUserPositionVO[] mainVo, ModelMap model,
			HttpServletRequest request) throws BusinessException {
		Map<String,Object> result = new HashMap<String,Object>();
		try {
			String errorMsg = service.addUserPosition(mainVo);
			result.put("msg", "分配岗位成功");
			result.put("flag", "fail");
		} catch (Exception e) {
			result.put("msg", "分配岗位失败!");
			result.put("flag", "fail");
		}
        return result;  
	}
	
	/**
	 * 为用户删除岗位
	* TODO description
	* @author 
	* @date 2016年12月26日
	* @param mainVo
	* @param model
	* @param request
	* @return
	* @throws BusinessException
	 */
	@RequestMapping(value = "/deletePositionForUser", method = RequestMethod.POST)
	public @ResponseBody Object deletePositionForUser(
			@RequestBody DefineUserPositionVO[] mainVo, ModelMap model,
			HttpServletRequest request) throws BusinessException {
		Map<String,Object> result = new HashMap<String,Object>();
		try {
			String errorMsg = service.deleteUserPosition(mainVo);
			result.put("msg", "删除岗位成功!");
			result.put("flag", "fail");
		} catch (Exception e) {
			result.put("msg", "删除岗位失败!");
			result.put("flag", "fail");
		}
        return result;  
	}
	
	/**
	 * 添加角色
	* TODO description
	* @author 
	* @date 2016年12月23日
	* @param mainVo
	* @param model
	* @param request
	* @return
	* @throws BusinessException
	 */
	@RequestMapping(value = "/saveRole", method = RequestMethod.POST)
	public @ResponseBody Object saveRole(
			@RequestBody DefinePositionVO mainVo, ModelMap model,
			HttpServletRequest request) throws BusinessException {
		Map<String,Object> result = new HashMap<String,Object>();
		try {
			String errorMsg = service.addRole(mainVo);
			if(null != errorMsg && errorMsg.length()>0){
				result.put("msg", errorMsg);
				result.put("flag", "fail");
			}else{
				result.put("msg", "添加成功!");
				result.put("flag", "success");
			}
		} catch (Exception e) {
			result.put("msg", "修改失败!");
			result.put("flag", "fail");
		}
        return result;  
	}
	
	/**
	 * 取消角色授权
	* TODO description
	* @author 
	* @date 2016年12月23日
	* @param mainVo
	* @param model
	* @param request
	* @return
	* @throws BusinessException
	 */
	@RequestMapping(value = "/deleteRole", method = RequestMethod.POST)
	public @ResponseBody Object deleteRole(
			@RequestBody DefinePositionVO mainVo, ModelMap model,
			HttpServletRequest request) throws BusinessException {
		Map<String,Object> result = new HashMap<String,Object>();
		try {
			String errorMsg = service.deleteRole(mainVo);
			if(null != errorMsg && errorMsg.length()>0){
				result.put("msg", errorMsg);
				result.put("flag", "fail");
			}else{
				result.put("msg", "添加成功!");
				result.put("flag", "success");
			}
		} catch (Exception e) {
			result.put("msg", "修改失败!");
			result.put("flag", "fail");
		}
        return result;  
	}
	
	
	@RequestMapping(value = "/openPosition", method = RequestMethod.POST)
	public @ResponseBody Object openPosition(
			@RequestBody DefinePositionVO[] mainVoArr, ModelMap model,
			HttpServletRequest request) throws BusinessException {
		Map<String,Object> result = new HashMap<String,Object>();
		try {
			service.openPosition(mainVoArr);
			result.put("msg", "启用/停用成功！");
			result.put("flag", "fail");
			
		} catch (Exception e) {
			result.put("msg", "启用/停用失败!");
			result.put("flag", "fail");
		}
        return result;  
	}
	
	/**
	 * 根据岗位ID查询没有分配的角色
	* TODO description
	* @author 
	* @date 2016年12月23日
	* @param positionId	
	* @return
	 * @throws AppBusinessException 
	 */
	@RequestMapping(value = "queryAddRole/{id}", method = RequestMethod.GET)
	public @ResponseBody List<DefineRoleSaveVO> queryRoleForAdd(@PathVariable("id") String positionId) throws AppBusinessException{
		return (List<DefineRoleSaveVO>) service.queryRoleForAdd(positionId);
	}
	
	/**
	 * 创建分页请求
	* TODO description
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
			sort = new Sort(Direction.DESC, "creationtime");
		} else if ("creationtime".equals(sortType)) {
			sort = new Sort(Direction.DESC, "creationtime");
		} else if ("id".equals(sortType)) {
			sort = new Sort(Direction.ASC, "id");
		}
		return new PageRequest(pageNumber, pagzSize, sort);
	}
	
	
	/**
	 * 根据角色ID查询岗位信息
	* TODO description
	* @author 
	* @date 2016年12月26日
	* @param roleId
	* @return
	 * @throws AppBusinessException 
	 */
	@RequestMapping(value = "queryForRole/{id}", method = RequestMethod.GET)
	public @ResponseBody List<DefinePositionVO> queryForRole(@PathVariable("id") String roleId) throws AppBusinessException{
		return (List<DefinePositionVO>) service.getPositionWithRoleId(roleId);
	}
	
	/**
	 * 根据角色查询没有关联的岗位
	* TODO description
	* @author 
	* @date 2016年12月26日
	* @param roleId
	* @return
	 * @throws AppBusinessException 
	 */
	@RequestMapping(value = "/queryAddForRole", method = RequestMethod.GET)
	public @ResponseBody Page<DefinePositionVO> queryAddPositionForRole(@RequestParam(value = "id") String roleId,
			@RequestParam(value = "pageSize",defaultValue="10") int pageSize,
			@RequestParam(value = "pageIndex",defaultValue="0") int pageIndex,
			@RequestParam(value = "sortType", defaultValue = "auto") String sortType,
			Model model, ServletRequest request
			) throws AppBusinessException{
		Map<String, Object> searchParams = new HashMap<String, Object>();
		searchParams.put("roleId", roleId);
		PageRequest pageRequest = buildPageRequest(pageIndex, pageSize, "auto");
		Page<DefinePositionVO> categoryPage = service.getPostitionBypages(searchParams, pageRequest);
		return categoryPage;
		
//		return (List<ExtPositionVO>) service.queryPositionForRoleAdd(roleId);
	}
	
	/**
	 * 为角色关联岗位
	* TODO description
	* @author 
	* @date 2016年12月26日
	* @param mainVo
	* @param model
	* @param request
	* @return
	* @throws BusinessException
	 */
	@RequestMapping(value = "/savePositionForRole", method = RequestMethod.POST)
	public @ResponseBody Object savePositionForRole(
			@RequestBody DefineRolePostitionVO[] mainVo, ModelMap model,
			HttpServletRequest request) throws BusinessException {
		Map<String,Object> result = new HashMap<String,Object>();
		try {
			String errorMsg = service.addRolePosition(mainVo);
			result.put("msg", "分配岗位成功");
			result.put("flag", "fail");
		} catch (Exception e) {
			result.put("msg", "分配岗位失败!");
			result.put("flag", "fail");
		}
        return result;  
	}
	
	/**
	 * 删除角色关联的岗位信息
	* TODO description
	* @author 
	* @date 2016年12月26日
	* @param mainVo
	* @param model
	* @param request
	* @return
	* @throws BusinessException
	 */
	@RequestMapping(value = "/deletePositionForRole", method = RequestMethod.POST)
	public @ResponseBody Object deletePositionForRole(
			@RequestBody DefineRolePostitionVO[] mainVo, ModelMap model,
			HttpServletRequest request) throws BusinessException {
		Map<String,Object> result = new HashMap<String,Object>();
		try {
			String errorMsg = service.deletePositionForRole(mainVo);
			result.put("msg", "删除岗位成功!");
			result.put("flag", "fail");
		} catch (Exception e) {
			result.put("msg", "删除岗位失败!");
			result.put("flag", "fail");
		}
        return result;  
	}
}
