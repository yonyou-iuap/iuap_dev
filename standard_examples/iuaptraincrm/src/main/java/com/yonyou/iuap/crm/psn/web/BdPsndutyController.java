package com.yonyou.iuap.crm.psn.web;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletRequest;

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

import com.yonyou.iuap.persistence.vo.pub.BusinessException;
import com.yonyou.iuap.crm.common.utils.AppTools;
import com.yonyou.iuap.crm.psn.entity.BdPsnDutyVO;
import com.yonyou.iuap.crm.psn.service.BdPsnDutyService;

@Controller
@RequestMapping(value = "/bd/psnduty")
public class BdPsndutyController {
	
	@Autowired
	private BdPsnDutyService cpdtService;

	/**
	 * 
	 * TODO 分页查询pk_psndoc人员任职信息
	 * 
	 * @author 
	 * @date 2016年12月8日
	 * @param pageNumber
	 * @param pk_psndoc
	 * @param pageSize
	 * @param sortType
	 * @param model
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/page", method = RequestMethod.GET)
	public @ResponseBody Page<BdPsnDutyVO> loadData(
			@RequestParam(value = "page", defaultValue = "0") int pageNumber,
			@RequestParam(value = "pk_psndoc", required = true) String pk_psndoc,
			@RequestParam(value = "page.size", defaultValue = "10") int pageSize,
			@RequestParam(value = "sortType", defaultValue = "auto") String sortType,
			Model model, ServletRequest request) throws Exception {
		Map<String, Object> searchParams = new HashMap<String, Object>();
		PageRequest pageRequest = buildPageRequest(pageNumber, pageSize, "auto");
		Page<BdPsnDutyVO> categoryPage = cpdtService.getBterDeptsBypage(
				searchParams, pageRequest, pk_psndoc);
		return categoryPage;
	}

	private PageRequest buildPageRequest(int pageNumber, int pagzSize,
			String sortType) {
		Sort sort = null;
		if ("auto".equals(sortType)) {
			sort = new Sort(Direction.DESC, "ts");
		} else if ("pk_psndoc".equals(sortType)) {
			sort = new Sort(Direction.ASC, "pk_psndoc");
		}
		return new PageRequest(pageNumber, pagzSize, sort);
	}

	/**
	 * 
	* TODO 保存人员任职信息
	* @author 
	* @date 2016年12月8日
	* @param entity
	* @param model
	* @param request
	* @return
	* @throws BusinessException
	 */
	@RequestMapping(value = "/savePsnduty", method = RequestMethod.POST)
	public @ResponseBody Map<String, Object> savePsndutyn(
			@RequestBody BdPsnDutyVO entity, ModelMap model,
			ServletRequest request) throws BusinessException {
		Map<String, Object> result = new HashMap<String, Object>();
		String pk = cpdtService.saveEntity(entity);
		result.put("flag", AppTools.SUCCESS);
		result.put("msg", pk);
		return result;
	}

	/**
	 * 
	* TODO 删除人员任职信息
	* @author 
	* @date 2016年12月8日
	* @param pk_psnduty
	* @param model
	* @param request
	* @return
	* @throws BusinessException
	 */
	@RequestMapping(value = "/delete", method = RequestMethod.DELETE)
	public @ResponseBody Map<String, Object> delete(
			@RequestParam(value = "pk_psnduty", required = true) String pk_psnduty,
			Model model, ServletRequest request) throws BusinessException {
		Map<String, Object> result = new HashMap<String, Object>();
		cpdtService.deletePsnDutyById(pk_psnduty);
		result.put("msg", "success");
		result.put("flag", AppTools.SUCCESS);
		return result;
	}

}
