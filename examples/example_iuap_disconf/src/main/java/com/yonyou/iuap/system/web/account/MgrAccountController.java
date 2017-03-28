package com.yonyou.iuap.system.web.account;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;
import javax.validation.ConstraintViolationException;
import javax.validation.Validator;

import net.sf.json.JSONObject;

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
import org.springside.modules.beanvalidator.BeanValidators;
import org.springside.modules.web.Servlets;

import com.yonyou.iuap.system.entity.MgrUser;
import com.yonyou.iuap.system.service.AccountService;

/**
 * 用户管理示例，项目不能直接使用，需要按照自己的需求修改！
 */
@Controller
@RequestMapping(value = "/mgr/account")
public class MgrAccountController {
	private final Logger logger = LoggerFactory.getLogger(getClass());
	
	@Autowired
	private AccountService service;
	
	@Autowired
	private Validator validator;
	
	@RequestMapping(value = "page", method = RequestMethod.GET)
	public @ResponseBody Page<MgrUser> page(@RequestParam(value = "page", defaultValue = "1") int pageNumber, @RequestParam(value = "page.size", defaultValue = "20") int pageSize, @RequestParam(value = "sortType", defaultValue = "auto") String sortType, Model model, ServletRequest request) {
		Map<String, Object> searchParams = new HashMap<String, Object>();
		
		//构造综合的查询条件
	    searchParams = Servlets.getParametersStartingWith(request, "search_");
	    
	    //构造分页
		PageRequest pageRequest = buildPageRequest(pageNumber, pageSize, sortType);
		
		//调用服务查询分页数据
		Page<MgrUser> accountPage = service.getAccountPage(searchParams, pageRequest);
		
		//直接返回page对象，springmvc会将数据格式化成json格式
		return accountPage;
	}
	
	/** 进入新增 */  
	@RequestMapping(value="create", method=RequestMethod.GET)  
	public @ResponseBody MgrUser add() {
		MgrUser entity = new MgrUser();
		Long tmpLong = new Long(0);
		entity.setId(tmpLong);
		return entity;
	}
 
	/** 保存新增 */  
    @RequestMapping(value="create", method=RequestMethod.POST)  
    public @ResponseBody Object create(@RequestBody MgrUser entity, HttpServletRequest resq) {
    	JSONObject result = new JSONObject();
		try {
			BeanValidators.validateWithException(validator, entity);
			entity = service.saveEntity(entity);
			result.put("msg", "保存成功");
			result.put("flag", 1);
		} catch (Exception e) {
			String msg = "保存失败!";
			if(e instanceof ConstraintViolationException){
				List<String> vmsg = BeanValidators.extractMessage((ConstraintViolationException)e);
				msg += vmsg.toString();
				
			}
			result.put("msg", msg);
			result.put("flag", 0);
			logger.error("保存出错!",e);
			
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
	public @ResponseBody MgrUser updateForm(@PathVariable("id") Long id, Model model) {
		MgrUser entity = service.getUser(id);
		return entity;
	}
	
	/** 保存更新 */
	@RequestMapping(value = "update", method = RequestMethod.POST)
	public @ResponseBody Object update(@RequestBody MgrUser entity) {
		JSONObject result = new JSONObject();
		try {
			entity = service.saveEntity(entity);
			result.put("msg", "保存成功");
			result.put("flag", 1);
		} catch (Exception e) {
			result.put("msg", "保存失败");
			result.put("flag", 0);
			logger.error("更新出错!",e);
		}
        return result;  
	}
	
	/**
	 * 删除实体
	 * 
	 * @param id 删除的标识
	 * @return 是否删除成功
	 */
	@RequestMapping(value = "delete/{id}",method = RequestMethod.DELETE)
	public @ResponseBody boolean delete(@PathVariable("id") Long id) {
		try {
			service.deleteUser(id);
		} catch (Exception e) {
			logger.error("delete user error,user id is {}", id, e);
			return false;
		}
		return true;
	}
	
	/**
	 * 创建分页请求.
	 */
	private PageRequest buildPageRequest(int pageNumber, int pagzSize, String sortType) {
		Sort sort = null;
		if ("auto".equals(sortType)) {
			sort = new Sort(Direction.DESC, "id");
		} else if ("title".equals(sortType)) {
			sort = new Sort(Direction.ASC, "title");
		}
		return new PageRequest(pageNumber - 1, pagzSize, sort);
	}
}


