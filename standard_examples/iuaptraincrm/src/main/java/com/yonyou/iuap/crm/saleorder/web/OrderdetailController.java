package com.yonyou.iuap.crm.saleorder.web;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.yonyou.iuap.crm.corp.entity.BdCorpVO;
import com.yonyou.iuap.crm.saleorder.entity.Orderdetail;
import com.yonyou.iuap.crm.saleorder.entity.Receiveinfo;
import com.yonyou.iuap.crm.saleorder.entity.Saleorder;
import com.yonyou.iuap.crm.saleorder.service.OrderReceiveinfoService;
import com.yonyou.iuap.crm.saleorder.service.OrderdetailService;
import com.yonyou.iuap.mvc.annotation.FrontModelExchange;
import com.yonyou.iuap.mvc.type.SearchParams;
import com.yonyou.iuap.web.BaseController;

@RestController
@RequestMapping(value = "/orderdetail")
public class OrderdetailController extends BaseController{
    private Logger logger = LoggerFactory.getLogger(OrderdetailController.class);
    
    @Autowired
    private OrderdetailService service;
    
    @Autowired
    private OrderReceiveinfoService receiveService;

    @RequestMapping(value = "/list", method = RequestMethod.GET)
    @ResponseBody
    public Object list(PageRequest pageRequest, @FrontModelExchange(modelType = Saleorder.class) SearchParams searchParams) {
        logger.debug("execute data search.");
        Page<Orderdetail> page = service.selectAllByPage(searchParams.getSearchMap(), pageRequest) ;
        //处理数据查询
        Page<Receiveinfo> infopage = receiveService.selectAllByPage(searchParams.getSearchMap(), pageRequest);
        
        Map<String,Object> map = new HashMap<String,Object>() ;
       
        map.put("detaildata", page) ;      
        map.put("infodata", infopage) ;    
        
        return super.buildMapSuccess(map) ;
    }
    
    @RequestMapping(value="/del", method= RequestMethod.POST)
    @ResponseBody
    public Object delete(@RequestBody Orderdetail child, HttpServletRequest request) {
    	
    	service.deleteEntity(child);
    	return super.buildSuccess(child) ;
    	
    }

	/**
     * 前端树形展示，当数据量小的时候，可以全部查询出来
     * @return
     */  
    @RequestMapping(value = "/BdCorpVO/listall", method = RequestMethod.GET)
    public @ResponseBody Object listAllBdCorpVO() {
        logger.info("后台数据模拟查询");
        List<BdCorpVO> list = service.findAllBdCorpVO();
        return super.buildSuccess(list);
    }
    
}
