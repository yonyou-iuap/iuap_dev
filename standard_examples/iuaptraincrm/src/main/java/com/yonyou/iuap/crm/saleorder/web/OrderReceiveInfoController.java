package com.yonyou.iuap.crm.saleorder.web;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.yonyou.iuap.crm.saleorder.entity.Receiveinfo;
import com.yonyou.iuap.crm.saleorder.entity.Saleorder;
import com.yonyou.iuap.crm.saleorder.service.OrderReceiveinfoService;
import com.yonyou.iuap.mvc.annotation.FrontModelExchange;
import com.yonyou.iuap.mvc.type.SearchParams;
import com.yonyou.iuap.web.BaseController;

@Controller
@RequestMapping(value="/receive")
public class OrderReceiveInfoController extends BaseController{
	
	private Logger logger = LoggerFactory.getLogger(OrderReceiveInfoController.class);
	
    @Autowired
    private OrderReceiveinfoService service;

    @RequestMapping(value = "/list", method = RequestMethod.GET)
    @ResponseBody
    public Object list(PageRequest pageRequest, @FrontModelExchange(modelType = Saleorder.class) SearchParams searchParams) {
        logger.debug("execute data search.");
        Page<Receiveinfo> page = service.selectAllByPage(searchParams.getSearchMap(), pageRequest) ;
        Map<String,Object> map = new HashMap<String,Object>() ;
        map.put("data", page) ;        
        return super.buildMapSuccess(map) ;
    }
    
    @RequestMapping(value="/del", method= RequestMethod.POST)
    @ResponseBody
    public Object delete(@RequestBody Receiveinfo child, HttpServletRequest request) {   	
    	service.deleteEntity(child);
    	return super.buildSuccess(child) ;    	
    }
}
