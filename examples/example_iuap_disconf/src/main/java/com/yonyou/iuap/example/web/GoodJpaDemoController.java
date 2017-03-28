package com.yonyou.iuap.example.web;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletRequest;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springside.modules.web.Servlets;

import com.yonyou.iuap.example.entity.GoodJpaDemo;
import com.yonyou.iuap.example.service.GoodJpaDemoService;

/**
 * spring MVC控制类示例，开发者需要在此基础上进行修改，适应项目的需求
 */
@RestController
@RequestMapping(value = "/goodsjpa")
public class GoodJpaDemoController {

	private final Logger logger = LoggerFactory.getLogger(getClass());
	
	@Autowired
	private GoodJpaDemoService goodService;
	
	@RequestMapping(value="/page", method= RequestMethod.GET)
	public @ResponseBody Object page(@RequestParam(value = "pageIndex", defaultValue = "0") int pageNumber, @RequestParam(value = "pageSize", defaultValue = "10") int pageSize, @RequestParam(value = "sortType", defaultValue = "auto") String sortType, Model model, ServletRequest request) {
		Map<String,Object> result = new HashMap<String,Object>();
		Map<String, Object> searchParams = new HashMap<String, Object>();
		searchParams = Servlets.getParametersStartingWith(request, "search_");
		PageRequest pageRequest = buildPageRequest(pageNumber, pageSize, sortType);
		try {
			Page<GoodJpaDemo> accountPage = goodService.getDemoPage(searchParams, pageRequest);
			result.put("data", accountPage);
			result.put("flag", "success");
			result.put("msg", "查询数据成功!");
		} catch (Exception e) {
			String errMsg = "查询数据详情失败!";
			result.put("flag", "fail");
			result.put("msg", errMsg);
			logger.error(errMsg, e);
		}
		return result;
	}
	
    /**
	 * 进入详情界面
	 * 
	 * @param id
	 * @param model
	 * @return 需要更新的实体的json结构
	 */
	@RequestMapping(value = "detail/{id}", method = RequestMethod.GET)
	public @ResponseBody Object detail(@PathVariable("id") String id, Model model) {
		Map<String, Object> result = new HashMap<String, Object>();
		try {
			GoodJpaDemo entity = goodService.getEntityById(id);
			result.put("data", entity);
			result.put("flag", "success");
			result.put("msg", "查询数据详情成功!");
		} catch (Exception e) {
			String errMsg = "查询数据详情失败!";
			result.put("flag", "fail");
			result.put("msg", errMsg);
			logger.error(errMsg, e);
		}
		return result;
	}
	
	/** 更新 */
	@RequestMapping(value = "update", method = RequestMethod.POST)
	public @ResponseBody Object update(@RequestBody GoodJpaDemo entity) {
		Map<String,Object> result = new HashMap<String,Object>();
		try {
			//设置状态为更新，才会持久化
			entity = goodService.saveEntity(entity);
			result.put("data", entity);
			result.put("msg", "更新成功!");
			result.put("flag", "success");
		} catch (Exception e) {
			result.put("msg", "更新失败!");
			result.put("flag", "fail");
			logger.error("更新出错!",e);
		}
        return result;  
	}
	
	/** 新增保存 */
	@RequestMapping(value = "save", method = RequestMethod.POST)
	public @ResponseBody Object save(@RequestBody GoodJpaDemo entity) {
		Map<String,Object> result = new HashMap<String,Object>();
		try {
			//设置状态为NEW，才会插入新数据
			entity = goodService.saveEntity(entity);
			result.put("data", entity);
			result.put("msg", "保存成功!");
			result.put("flag", "success");
		} catch (Exception e) {
			result.put("msg", "保存失败!");
			result.put("flag", "fail");
			logger.error("保存出错!",e);
		}
        return result;  
	}
	
	/**
     * 删除实体
     */
    @RequestMapping(value = "del", method = RequestMethod.POST)
	public @ResponseBody Object delete(ServletRequest request) {
    	Map<String,Object> result = new HashMap<String,Object>();
    	try {
    		String jsonStr = request.getParameter("data");
    		
    		if(StringUtils.isNotBlank(jsonStr)){
    			JSONArray jsonArray = JSONArray.fromObject(jsonStr);
    			List<String> ids = new ArrayList<String>();
    			for (int i = 0; i < jsonArray.size(); i++) {
    				JSONObject jb = (JSONObject)jsonArray.get(i);
    				String pid = jb.getString("productid");
    				ids.add(pid);
				}
        		
    			result.put("flag", "success");
    			result.put("msg", "删除数据成功!");
    			goodService.batchDelete(ids);
    		} else {
    			result.put("flag", "fail");
    			result.put("msg", "请选择要删除的数据!");
    		}
		} catch (Exception e) {
			String errmsg = "删除数据失败!";
			logger.error(errmsg, e);
			result.put("flag", "fail");
			result.put("msg", errmsg);
		}
    	return result;
	}
	
	/**
	 * 创建分页请求简单示例，业务上按照自己的需求修改.
	 */
	private PageRequest buildPageRequest(int pageNumber, int pagzSize, String sortType) {
		Sort sort = null;
		if ("auto".equals(sortType)) {
			sort = new Sort(Direction.DESC, "ts");
		} else if ("productname".equals(sortType)) {
			sort = new Sort(Direction.ASC, "productname");
		}
		return new PageRequest(pageNumber, pagzSize, sort);
	}
}
