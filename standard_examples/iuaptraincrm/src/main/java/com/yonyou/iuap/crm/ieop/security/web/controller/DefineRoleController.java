package com.yonyou.iuap.crm.ieop.security.web.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.yonyou.iuap.crm.common.utils.AppTools;
import com.yonyou.iuap.crm.ieop.security.entity.DefineRoleSaveVO;
import com.yonyou.iuap.crm.ieop.security.service.IDefineRoleSaveService;
import com.yonyou.iuap.persistence.vo.pub.VOStatus;
import com.yonyou.uap.ieop.security.entity.Role;
import com.yonyou.uap.ieop.security.service.IExtRoleService;
import com.yonyou.uap.ieop.security.web.BaseController;

/**
 * <p>
 * Title: RoleController
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
 * @date May 29, 2015 3:18:28 PM
 */
@Controller
@RequestMapping(value = "/security/extroledefine")
public class DefineRoleController extends BaseController {
	private final Logger logger = LoggerFactory.getLogger(getClass());

	@Autowired
	private IDefineRoleSaveService rolesaveservice;
	
	@Autowired
	private IExtRoleService roleservice;

	@RequestMapping(value = "page", method = RequestMethod.GET)
	public @ResponseBody Page<DefineRoleSaveVO> page(
			@RequestParam(value = "page", defaultValue = "1") int pageNumber,
			@RequestParam(value = "page.size", defaultValue = "20") int pageSize,
			@RequestParam(value = "sortType", defaultValue = "auto") String sortType,
			Model model, ServletRequest request) {

		Map<String, Object> searchParams = new HashMap<String, Object>();
		PageRequest pageRequest = buildPageRequest(pageNumber, pageSize,
				sortType);
		Page<DefineRoleSaveVO> rolePage = null;
		try {
			rolePage = rolesaveservice.getExtRolePage(searchParams, pageRequest);
		} catch (Exception e) {
			logger.error("查询出错", e);
		}
		return rolePage;
	}

	@RequestMapping(value = "queryArea", method = RequestMethod.POST)
	public @ResponseBody Page<DefineRoleSaveVO> page(
			@RequestParam(value = "page", defaultValue = "1") int pageNumber,
			@RequestParam(value = "page.size", defaultValue = "20") int pageSize,
			@RequestParam(value = "sortType", defaultValue = "auto") String sortType,
			@RequestBody Map map, Model model, ServletRequest request) {

		Map<String, Object> searchParams = (Map<String, Object>) map
				.get("params");
		PageRequest pageRequest = buildPageRequest(pageNumber, pageSize,
				sortType);
		Page<DefineRoleSaveVO> rolePage = null;
		try {
			rolePage = rolesaveservice.getExtRolePage(searchParams, pageRequest);
		} catch (Exception e) {
			logger.error("查询出错", e);
		}
		return rolePage;
	}

	public PageRequest buildPageRequest(int pageNumber, int pagzSize,
			String sortType) {
		Sort sort = null;
		if ("auto".equals(sortType)) {
			sort = new Sort(Direction.DESC, "createDate");
		} else if ("title".equals(sortType)) {
			sort = new Sort(Direction.ASC, "id");
		}
		return new PageRequest(pageNumber - 1, pagzSize, sort);
	}

	/** 进入新增 */
	@RequestMapping(value = "create", method = RequestMethod.GET)
	public @ResponseBody Role add() {
		Role entity = new Role();
		return entity;
	}

	/** 保存新增 */
	@RequestMapping(value = "create", method = RequestMethod.POST)
	public @ResponseBody Map<String, Object> create(
			@RequestBody DefineRoleSaveVO entity, HttpServletRequest resq) {
		Map<String, Object> result = new HashMap<String, Object>();
		try {
			String code = entity.getRole_code();
			String name = entity.getRole_name();
			List<Role> RoleName = roleservice.findByName(name);
			List<Role> RoleCode = roleservice.findByCode(code);

			if (RoleName != null && RoleName.size() > 0) {
				result.put("msg", "角色名称重复");
				result.put("flag", AppTools.FAILED);

				return result;
			}
			if (RoleCode != null && RoleCode.size() > 0) {
				result.put("msg", "角色编码重复");
				result.put("flag", AppTools.FAILED);
				return result;
			}
			// entity = service.saveEntity(entity);
			entity.setStatus(VOStatus.NEW);
			entity = rolesaveservice.saveRole(entity);
		} catch (Exception e) {
			// 记录日志
			logger.error("保存出错!", e);
			result.put("flag", AppTools.FAILED);
		}
		result.put("flag", AppTools.SUCCESS);
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
	public @ResponseBody Role updateForm(@PathVariable("id") String id,
			Model model) {
		Role entity = null;
		try {
			entity = roleservice.findById(id);
		} catch (Exception e) {
			logger.error("查询出错", e);
		}
		return entity;
	}

	/** 保存更新 */
	@RequestMapping(value = "update", method = RequestMethod.POST)
	public @ResponseBody Map<String, Object> update(
			@RequestBody DefineRoleSaveVO entity) {
		Map<String, Object> result = new HashMap<String, Object>();

		try {
			String code = entity.getRole_code();
			String name = entity.getRole_name();
			List<Role> RoleName = roleservice.findByName(name);
			List<Role> RoleCode = roleservice.findByCode(code);

			if (RoleName != null && RoleName.size() > 0) {
				for (int i = 0; i < RoleName.size(); i++) {
					if (!RoleName.get(i).getId().equals(entity.getId())) {
						result.put("msg", "角色名称重复");
						result.put("flag", AppTools.FAILED);
						return result;
					}
				}

			}
			if (RoleCode != null && RoleCode.size() > 0) {
				for (int i = 0; i < RoleCode.size(); i++) {
					if (!RoleCode.get(i).getId().equals(entity.getId())) {
						result.put("msg", "角色编码重复");
						result.put("flag", AppTools.FAILED);
						return result;
					}
				}
			}
			// entity = service.saveEntity(entity);
			entity.setStatus(VOStatus.UPDATED);
			entity = rolesaveservice.saveRole(entity);
		} catch (Exception e) {

			logger.error("更新出错!", e);
			result.put("flag", AppTools.FAILED);

		}
		result.put("flag", AppTools.SUCCESS);
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
	public @ResponseBody boolean delete(@PathVariable("id") String id) {
		try {
			roleservice.deleteById(id);
		} catch (Exception e) {
			logger.error("删除出错", e);
		}
		return true;
	}

	@RequestMapping(value = "delete", method = RequestMethod.DELETE)
	public @ResponseBody Map<String, Object> deleteall(
			@RequestBody List<Role> entitys, HttpServletRequest resq) {
		List<Role> results = null;
		Map<String, Object> result = new HashMap<String, Object>();
		try {

			results = roleservice.deleteAll(entitys);
		} catch (Exception e) {
			// 记录日志
			result.put("flag", AppTools.FAILED);
			logger.error("保存出错!", e);
		}
		result.put("flag", AppTools.SUCCESS);
		return result;
	}
}
