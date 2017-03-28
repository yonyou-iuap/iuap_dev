/**
 * 
 */
package com.yonyou.iuap.crm.basedata.web;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Validator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springside.modules.web.Servlets;

import com.yonyou.iuap.crm.basedata.entity.TmSalecutratioVO;
import com.yonyou.iuap.persistence.vo.pub.BusinessException;
import com.yonyou.iuap.crm.basedata.service.itf.ITmSalecutratioService;
import com.yonyou.iuap.crm.common.exception.AppBusinessException;
import com.yonyou.iuap.crm.common.utils.AppTools;

/**
 * 控制层 TODO description
 * 
 * @author 
 * @date 2016年11月22日
 */
@Controller
@RequestMapping(value = "/basedata/ratio")
public class TmSalecutratioController {
	@Autowired
	private Validator validator;

	@Autowired
	private ITmSalecutratioService ratioService;

	/**
	 * 销售提成系数信息加载查询 TODO description
	 * 
	 * @author 
	 * @date 2016年11月22日
	 * @param pageNumber
	 * @param pageSize
	 * @param sortType
	 * @param model
	 * @param request
	 * @return
	 * @throws BusinessException
	 * @throws Exception
	 */
	@RequestMapping(value = "/queryratio", method = RequestMethod.GET)
	public @ResponseBody Page<TmSalecutratioVO> loadData(
			@RequestParam(value = "page", defaultValue = "0") int pageNumber,
			@RequestParam(value = "page.size", defaultValue = "10") int pageSize,
			@RequestParam(value = "sortType", defaultValue = "auto") String sortType,
			Model model, ServletRequest request) throws AppBusinessException {
		Map<String, Object> searchParams = new HashMap<String, Object>();
		searchParams = Servlets.getParametersStartingWith(request, "search_");
		PageRequest pageRequest = buildPageRequest(pageNumber, pageSize, "auto");
		Page<TmSalecutratioVO> salecutratiosBypage = null;
		try {
			salecutratiosBypage = ratioService
					.getSalecutratiosBypage(searchParams, pageRequest);
		} catch (BusinessException e) {
			// TODO 自动生成的 catch 块
			e.printStackTrace();
		}
		return salecutratiosBypage;
	}

	/**
	 * 创建分页请求 TODO description
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
			sort = new Sort(Direction.DESC, "ts");
		} else if ("pk_salecutratio".equals(sortType)) {
			sort = new Sort(Direction.ASC, "pk_salecutratio");
		}
		return new PageRequest(pageNumber, pagzSize, sort);
	}

	/**
	 * 销售提成系数信息批量新增或修改操作 TODO description
	 * 
	 * @author 
	 * @date 2016年11月22日
	 * @param entitys
	 * @param resq
	 * @return
	 * @throws BusinessException
	 */
	@RequestMapping(value = "/save", method = RequestMethod.POST)
	public @ResponseBody Map<String, Object> save(
			@RequestBody List<TmSalecutratioVO> entitys, HttpServletRequest resq)
			throws AppBusinessException {
		Map<String, Object> result = new HashMap<String, Object>();
	
			result = ratioService.save(entitys);
		 
			result.put("flag", AppTools.FAILED);
			return result;
		
		

	}

	/**
	 * 销售提成系数信息批量删除 TODO description
	 * 
	 * @author 
	 * @date 2016年11月22日
	 * @param entitys
	 * @param resq
	 * @return
	 * @throws BusinessException
	 */
	@RequestMapping(value = "/deletedata", method = RequestMethod.POST)
	public @ResponseBody Map<String, Object> remove(
			@RequestBody List<TmSalecutratioVO> entitys, HttpServletRequest resq)
			throws AppBusinessException {
		Map<String, Object> result = new HashMap<String, Object>();
		try {
			result = ratioService.remove(entitys);
		} catch (Exception e) {
			result.put("flag", AppTools.FAILED);
			result.put("msg", e.getMessage());
		}
		return result;

	}
}
