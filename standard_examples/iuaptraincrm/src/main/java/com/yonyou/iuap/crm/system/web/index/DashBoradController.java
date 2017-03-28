package com.yonyou.iuap.crm.system.web.index;

import java.util.Map;

import javax.servlet.ServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.yonyou.iuap.crm.common.exception.AppBusinessException;
import com.yonyou.iuap.crm.system.service.itf.IDashBoardService;

/**
 * 首页工作台
* TODO description
* @author name
* @date 2016年12月28日
 */
@Controller
@RequestMapping(value = "/dashboard")
public class DashBoradController {
	@Autowired
	private IDashBoardService service;
	
	
	@RequestMapping(value = "/querydb", method = RequestMethod.POST)
	public @ResponseBody Map<String,Object> queryDashBoard(
			@RequestParam(value = "sortType", defaultValue = "auto") String sortType,
			@RequestBody Map<String, Object> parammap, Model model,
			ServletRequest request) throws AppBusinessException {
		Map<String,Object> map = service.queryDashBoard();
//		map.put("data", null);
		return map;
	}
}
