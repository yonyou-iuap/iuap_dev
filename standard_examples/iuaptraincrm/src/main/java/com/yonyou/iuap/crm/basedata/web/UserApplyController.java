package com.yonyou.iuap.crm.basedata.web;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;

import net.sf.json.JSONObject;

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

import com.yonyou.iuap.crm.basedata.entity.TmUserApplyPosVO;
import com.yonyou.iuap.crm.basedata.entity.TmUserApplyVO;
import com.yonyou.iuap.persistence.vo.pub.BusinessException;
import com.yonyou.iuap.crm.basedata.service.itf.IUserApplyService;
import com.yonyou.iuap.crm.common.exception.AppBusinessException;
import com.yonyou.iuap.crm.ieop.security.entity.DefinePositionVO;

@Controller
@RequestMapping(value = "/bd/userapply")
public class UserApplyController {
	@Autowired
	private IUserApplyService userappService;
	/**
	 * 根据查询条件， 获取用户申请信息分页列表数据
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
	public @ResponseBody Page<TmUserApplyVO> loadData(
			@RequestParam(value = "sortType", defaultValue = "auto") String sortType,
			@RequestBody Map<String, Object> parammap, Model model,
			ServletRequest request) throws Exception {
		int pindex = null == parammap.get("pindex") ? 0 : Integer
				.valueOf(parammap.get("pindex").toString());
		int psize = null == parammap.get("psize") ? 0 : Integer
				.valueOf(parammap.get("psize").toString());
		PageRequest pageRequest = buildPageRequest(pindex, psize, "auto");

		Page<TmUserApplyVO> categoryPage = userappService.queryUserApply(parammap, pageRequest);
		return categoryPage;
	}
	
	/**
	 * 根据用户Id查询未分配的岗位信息
	* TODO description
	* @author 
	* @date 2016年1月17日
	* @return
	 * @throws AppBusinessException 
	 */
	@RequestMapping(value = "/queryPositionUser", method = RequestMethod.POST)
	public @ResponseBody Page<DefinePositionVO> queryAddPositionForUser(
			@RequestParam(value = "sortType", defaultValue = "auto") String sortType,
			@RequestBody Map<String, Object> parammap, Model model,
			ServletRequest request) throws AppBusinessException{
		int pindex = null == parammap.get("pindex") ? 0 : Integer
				.valueOf(parammap.get("pindex").toString());
		int psize = null == parammap.get("psize") ? 0 : Integer
				.valueOf(parammap.get("psize").toString());
		PageRequest pageRequest = buildPageRequest(pindex, psize, "auto");
		
		return userappService.queryPositionUser(parammap, pageRequest);
	}
	
	/**
	 * 保存
	* TODO description
	* @author name
	* @date 2017年1月17日
	* @param vo
	* @param model
	* @param request
	* @return
	* @throws AppBusinessException
	 */
	@RequestMapping(value = "/saveUserApply", method = RequestMethod.POST)
	public  @ResponseBody JSONObject saveUserApply(
			@RequestBody TmUserApplyVO vo, ModelMap model,
			HttpServletRequest request) throws AppBusinessException{
		JSONObject jsonObject = new JSONObject();
		List<TmUserApplyPosVO> cvos = vo.getPositions();
		userappService.saveUserApply(vo, cvos);
		jsonObject.put("msg", "保存成功！");
		return jsonObject;
	}
	
	/**
	 * 审核修改
	* TODO description
	* @author 
	* @date 2017年1月17日
	* @param vo
	* @param model
	* @param request
	* @return
	* @throws AppBusinessException
	 */
	@RequestMapping(value = "/auditSaveUserApply", method = RequestMethod.POST)
	public  @ResponseBody JSONObject auditSaveUserApply(
			@RequestBody TmUserApplyVO vo, ModelMap model,
			HttpServletRequest request) throws AppBusinessException{
		JSONObject jsonObject = new JSONObject();
		List<TmUserApplyPosVO> cvos = vo.getPositions();
		userappService.auditSaveUserApply(vo, cvos);
		jsonObject.put("msg", "保存成功！");
		return jsonObject;
	}
			
	/**
	 * 删除
	 * 
	 * @throws BusinessException
	 */
	@RequestMapping(value = "/delPosition", method = RequestMethod.POST)
	public @ResponseBody Map<String, Object> deletePosition(
			@RequestBody TmUserApplyPosVO vo, ModelMap model,
			HttpServletRequest request) throws BusinessException {
		Map<String, Object> result = new HashMap();
		userappService.deleteUserPosition(vo);
		return result;
	}
	
	/**
	 * 
	* TODO description
	* @author 
	* @date 2017年1月17日
	* @param userId
	* @return
	* @throws AppBusinessException
	 */
	@RequestMapping(value = "/queryUserPositionByPk/{id}", method = RequestMethod.GET)
	public @ResponseBody List<TmUserApplyPosVO> queryPositionByUser(@PathVariable("id") String userAppId) throws AppBusinessException{
		return (List<TmUserApplyPosVO>) userappService.queryPositionByUser(userAppId);
	}
	
	/**
	 * 提交用户申请
	* TODO description
	* @author 
	* @date 2017年1月17日
	* @param vo
	* @param model
	* @param request
	* @return
	* @throws BusinessException
	 */
	@RequestMapping(value = "/submitUserApply", method = RequestMethod.POST)
	public @ResponseBody Map<String, Object> submitApplyInfo(
			@RequestBody TmUserApplyVO vo, ModelMap model,
			HttpServletRequest request) throws BusinessException {
		Map<String, Object> result = new HashMap();
		userappService.submitUserApply(vo);
		return result;
	}
	
	/**
	 * 删除用户申请
	* TODO description
	* @author name
	* @date 2017年1月17日
	* @param vo
	* @param model
	* @param request
	* @return
	* @throws BusinessException
	 */
	@RequestMapping(value = "/deleteUserApply", method = RequestMethod.POST)
	public @ResponseBody Map<String, Object> deleteApplyInfo(
			@RequestBody TmUserApplyVO vo, ModelMap model,
			HttpServletRequest request) throws BusinessException {
		Map<String, Object> result = new HashMap();
		userappService.deleteUserApply(vo);
		return result;
	}
	
	/**
	 * 审核通过
	* TODO description
	* @author name
	* @date 2017年1月17日
	* @param vo
	* @param model
	* @param request
	* @return
	* @throws AppBusinessException
	 */
	@RequestMapping(value = "/auditUserApply", method = RequestMethod.POST)
	public  @ResponseBody JSONObject passUserApply(
			@RequestBody TmUserApplyVO vo, ModelMap model,
			HttpServletRequest request) throws AppBusinessException{
		JSONObject jsonObject = new JSONObject();
		List<TmUserApplyPosVO> cvos = vo.getPositions();
		userappService.passUserApply(vo, cvos);
		jsonObject.put("msg", "审核成功！");
		return jsonObject;
	}
	
	/**
	 * 审核驳回
	* TODO description
	* @author name
	* @date 2017年1月17日
	* @param vo
	* @param model
	* @param request
	* @return
	* @throws AppBusinessException
	 */
	@RequestMapping(value = "/rejectUserApply", method = RequestMethod.POST)
	public  @ResponseBody JSONObject rejectUserApply(
			@RequestBody TmUserApplyVO vo, ModelMap model,
			HttpServletRequest request) throws AppBusinessException{
		JSONObject jsonObject = new JSONObject();
		List<TmUserApplyPosVO> cvos = vo.getPositions();
		userappService.rejectUserApply(vo, cvos);
		jsonObject.put("msg", "审核成功！");
		return jsonObject;
	}
	
	/**
	 * 创建分页请求 
	 * @author 
	 * @date 2017年01月16日
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
