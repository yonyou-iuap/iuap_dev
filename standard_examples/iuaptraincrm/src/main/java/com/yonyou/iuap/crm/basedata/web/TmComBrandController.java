package com.yonyou.iuap.crm.basedata.web;

import com.yonyou.iuap.crm.basedata.entity.TmComBrandVO;
import com.yonyou.iuap.persistence.vo.pub.BusinessException;
import com.yonyou.iuap.crm.basedata.service.itf.ITmComBrandService;
import com.yonyou.iuap.crm.common.exception.AppBusinessException;
import com.yonyou.iuap.crm.common.utils.AppTools;
import com.yonyou.iuap.crm.common.utils.DictCode;

import net.sf.json.JSONObject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springside.modules.beanvalidator.BeanValidators;

import javax.servlet.ServletRequest;
import javax.validation.Validator;

import java.util.HashMap;
import java.util.Map;
/**
 * 
* 竞品品牌信息控制器 
* @author 
* @date 2016年11月21日
 */
@Controller
@RequestMapping(value = "/baseData/combrand")
public class TmComBrandController {
	@Autowired
	private ITmComBrandService tbService;		//	品牌信息服务
	@Autowired
	private Validator validator;				//  验证器
	
	/**
	* 删除品牌信息
	* @author 
	* @date 2016年11月21日
	* @param entity
	* @param model
	* @param request
	* @return
	* @throws AppBusinessException
	 */
	@RequestMapping(value = "/delete", method = RequestMethod.POST)
	public @ResponseBody JSONObject remove(@RequestBody TmComBrandVO entity, Model model, ServletRequest request) throws  AppBusinessException {
		if(entity.getPk_combrand() == null  || entity.getPk_combrand().trim().length()==0){
			throw new AppBusinessException("不存在此品牌信息， pk为空");
		}
		tbService.deleteTmBrandVo(entity);
		JSONObject jsonObject = new JSONObject();  
		jsonObject.put("msg", "success");			
		return jsonObject;
	}
	
	/**
	* 停用品牌信息
	* @author 
	* @date 2016年11月21日
	* @param entity
	* @param model
	* @param request
	* @return
	* @throws AppBusinessException
	 */
	@RequestMapping(value = "/stop", method = RequestMethod.POST)
	public @ResponseBody Object stop(@RequestBody TmComBrandVO entity, Model model, ServletRequest request) throws BusinessException, AppBusinessException {
		Map<String,Object> result = new HashMap<String,Object>();
		try {
			if(entity.getPk_combrand() == null  || entity.getPk_combrand().trim().length()==0){
				throw new AppBusinessException("不存在此品牌信息， pk为空");
			}
			entity.setVstatus(DictCode.ALREADY_STOP_STATUS);
			tbService.updateEntity(entity);
			result.put("flag", "success");
			result.put("msg", "停用成功");
		} catch (AppBusinessException e) {
			result.put("flag", "fail");
			result.put("msg", "停用失败");
			throw new AppBusinessException(e.getMessage());
		}
		return result;
	}
	
	/**
	* 启用品牌信息
	* @author 
	* @date 2016年11月21日
	* @param entity
	* @param model
	* @param request
	* @return
	* @throws AppBusinessException
	 */
	@RequestMapping(value = "/start", method = RequestMethod.POST)
	public @ResponseBody Object start(@RequestBody TmComBrandVO entity, Model model, ServletRequest request) throws  AppBusinessException {
		Map<String,Object> result = new HashMap<String,Object>();
		try {
			if(entity.getPk_combrand() == null  || entity.getPk_combrand().trim().length()==0){
				throw new AppBusinessException("不存在此品牌信息， pk为空");
			}
			entity.setVstatus(DictCode.ALREADY_START_STATUS);
			tbService.updateEntity(entity);
			result.put("flag", "success");
			result.put("msg", "启用成功");
		} catch (AppBusinessException e) {
			result.put("flag", "fail");
			result.put("msg", "启用失败");
			throw new AppBusinessException(e.getMessage());
		}
		return result;
	}
	
	
	/**
	* 保存或更新品牌信息
	* @author 
	* @date 2016年11月21日
	* @param tb
	* @param model
	* @param request
	* @return
	* @throws AppBusinessException
	 */
	@RequestMapping(value = "/save", method = RequestMethod.POST)
	public @ResponseBody Map<String,Object> saveBrand(@RequestBody TmComBrandVO tb, ModelMap model, ServletRequest request) throws  AppBusinessException {
		BeanValidators.validateWithException(validator, tb);
		Map<String,Object> result = new HashMap<String,Object>();
		if(tb.getPk_combrand() != null && tbService.getTmComBrandVo(tb.getPk_combrand()) != null){
			tbService.updateEntity(tb);
		}
		else{
			tb.setVstatus(DictCode.ALREADY_START_STATUS);
			tbService.saveEntity(tb);			
		}
		result.put("flag", AppTools.SUCCESS);
		return result;
	}
	
	
	
	/**
	* 分页查询品牌信息，返回列表JSON
	* @author 
	* @date 2016年11月21日
	* @param sortType
	* @param parammap
	* @param model
	* @param request
	* @return
	* @throws Exception
	 */
	@RequestMapping(value = "/querypage", method = RequestMethod.POST)
	public @ResponseBody Page<TmComBrandVO> loadData(@RequestParam(value = "sortType", defaultValue = "auto") String sortType,
			@RequestBody Map<String, Object> parammap, Model model,
			ServletRequest request) throws Exception {
		int pindex = null== parammap.get("pindex") ? 0 :Integer.valueOf(parammap.get("pindex").toString());
		int psize = null== parammap.get("psize") ? 0 :Integer.valueOf(parammap.get("psize").toString());
		PageRequest pageRequest = buildPageRequest(pindex,psize, "auto");
		Page<TmComBrandVO> categoryPage = tbService.getTmBrandsBypage(parammap,pageRequest);
		return categoryPage;
	}
	
	
	/**
	 * 
	* 建立分页请求
	* @author 
	* @date 2016年11月21日
	* @param pageNumber
	* @param pagzSize
	* @param sortType
	* @return
	 */
	private PageRequest buildPageRequest(int pageNumber, int pagzSize, String sortType) {
		Sort sort = null;
		if ("auto".equals(sortType)) {
			sort = new Sort(Direction.DESC, "ts");
		} else if ("creationtime".equals(sortType)) {
			sort = new Sort(Direction.DESC, "creationtime");
		}
		return new PageRequest(pageNumber, pagzSize, sort);
	}

}
