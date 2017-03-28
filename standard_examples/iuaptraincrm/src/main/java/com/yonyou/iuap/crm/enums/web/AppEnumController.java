package com.yonyou.iuap.crm.enums.web;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.yonyou.iuap.persistence.bs.dao.DAOException;
import com.yonyou.iuap.crm.enums.entity.AppEnumVO;
import com.yonyou.iuap.crm.enums.service.itf.IAppEnumService;

@Controller
@RequestMapping(value = "/bd/enums")
public class AppEnumController {

	@Autowired
	private IAppEnumService enumService;
	
	@RequestMapping(value = "/queryByVtype/{vtype}", method = RequestMethod.GET)
	public @ResponseBody List<Map<String,String>> queryByVtype(@PathVariable("vtype") String vtype) throws DAOException{
		List<Map<String,String>> enums= new ArrayList();
		List<AppEnumVO> enumlist = enumService.queryByVtype(vtype);
		for (AppEnumVO appEnumVO : enumlist) {
			Map<String,String> enummap = new HashMap();
			enummap.put("name", appEnumVO.getVname());
			enummap.put("value", appEnumVO.getVcode());
			enums.add(enummap);
		}
		return enums;
	}
}
