package com.yonyou.iuap.crm.basedata.web;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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

import com.yonyou.iuap.crm.basedata.entity.TmModelCompetExtVO;
import com.yonyou.uap.ieop.security.exception.RestException;
import com.yonyou.iuap.crm.basedata.service.itf.ICompetRelationService;
import com.yonyou.iuap.crm.common.exception.AppBusinessException;

@Controller
@RequestMapping(value = "/bd/competCompare")
public class CompetRelationController {
	private static Logger logger = LoggerFactory
			.getLogger(CompetRelationController.class);
	@Autowired
	private ICompetRelationService relationService;

	/**
	 * 查找竞品-对比车型的信息 TODO description
	 * 
	 * @author 
	 * @date Dec 5, 2016
	 * @param sortType
	 * @param parammap
	 * @param model
	 * @param request
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/querypage", method = RequestMethod.POST)
	public @ResponseBody Page<TmModelCompetExtVO> page(
			@RequestParam(value = "sortType", defaultValue = "auto") String sortType,
			@RequestBody Map<String, Object> parammap, Model model,
			ServletRequest request) {
		int pindex = null == parammap.get("pindex") ? 0 : Integer
				.valueOf(parammap.get("pindex").toString());
		int psize = null == parammap.get("psize") ? 0 : Integer
				.valueOf(parammap.get("psize").toString());
		PageRequest pageRequest = buildPageRequest(pindex, psize, "auto");
		Page<TmModelCompetExtVO> categoryPage = null;
		try {
			categoryPage = relationService.getRelatedModels(parammap,
					pageRequest);
		} catch (Exception e) {
			logger.error("查询出错", e);
		}
		return categoryPage;
	}

	/**
	 * 保存竞品-对比关系
	 * 
	 * @author 
	 * @date Nov 21, 2016
	 * @param entities
	 * @param model
	 * @param request
	 * @return
	 * @throws AppBusinessException
	 * @throws RestException
	 */
	@RequestMapping(value = "/save", method = RequestMethod.POST)
	public @ResponseBody Object save(
			@RequestBody List<TmModelCompetExtVO> entities, ModelMap model,
			ServletRequest request) throws AppBusinessException, RestException {
		Map<String,Object> result = new HashMap<String,Object>();
		try {
			relationService.saveCompetRelation(entities);
			result.put("flag", "success");
			result.put("msg", "保存成功");
		} catch (AppBusinessException e) {
			result.put("flag", "fail");
			result.put("msg", "保存失败");
			throw new AppBusinessException(e.getMessage());
		}
		return result;
	}

	/**
	 * 创建分页请求
	 * 
	 * @author 
	 * @date Dec 5, 2016
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
		}
		return new PageRequest(pageNumber, pagzSize, sort);
	}

	/**
	 * 删除竞品-对比关系，逻辑删除
	 * 
	 * @author 
	 * @date Nov 21, 2016
	 * @param parammap
	 * @return
	 * @throws AppBusinessException
	 */
	@RequestMapping(value = "/remove", method = RequestMethod.POST)
	public @ResponseBody Object remove(
			@RequestBody Map<String, Object> parammap)
			throws AppBusinessException {
		Map<String,Object> result = new HashMap<String,Object>();
		try {
			relationService.deleteCompetRelation(parammap);
			result.put("flag", "success");
			result.put("msg", "删除成功");
		} catch (AppBusinessException e) {
			result.put("flag", "fail");
			result.put("msg", "删除失败");
			throw new AppBusinessException(e.getMessage());
		}
		return result;
	}
}
