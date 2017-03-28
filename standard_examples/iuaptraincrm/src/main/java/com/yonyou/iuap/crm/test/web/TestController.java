package com.yonyou.iuap.crm.test.web;

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

import com.yonyou.iuap.crm.test.entity.Test;
import com.yonyou.iuap.crm.test.service.TestService;
import com.yonyou.iuap.crm.currtype.entity.Currtype;
import com.yonyou.iuap.web.BaseController;
//import com.yonyou.iuap.example.entity.meta.EnumVo;
//import com.yonyou.iuap.example.utils.EnumUtils;
import com.yonyou.iuap.mvc.annotation.FrontModelExchange;
import com.yonyou.iuap.mvc.type.SearchParams;

@RestController
@RequestMapping(value = "/Test")
public class TestController extends BaseController {
    private Logger logger = LoggerFactory.getLogger(TestController.class);

    @Autowired
    private TestService service;

    /**
     * 前端传入参数，查询数据，列表展示
     * 
     * @return
     */
    @RequestMapping(value = "/list", method = RequestMethod.GET)
    @ResponseBody
    public Object list(PageRequest pageRequest, @FrontModelExchange(modelType = Test.class) SearchParams searchParams) {
        logger.debug("execute data search.");
        Page<Test> page = service.selectAllByPage(searchParams.getSearchMap(), pageRequest);
        return buildSuccess(page);
    }

    /**
     * 增加主子表数据保存，传入json数据
     * 
     * @param userAndUserJob
     * @param request
     * @return
     */
    @RequestMapping(value = "/add", method = RequestMethod.POST)
    @ResponseBody
    public Object add(@RequestBody Test obj, HttpServletRequest request) {
        logger.debug("execute add operate.");
        Test u = service.save(obj) ;
        return super.buildSuccess(u);
    }


    /**
     * 更新主子表数据更新，传入json数据
     * 
     * @param userAndUserJob
     * @param request
     * @return
     */
    @RequestMapping(value = "/update", method = RequestMethod.POST)
    @ResponseBody
    public Object update(@RequestBody Test obj, HttpServletRequest request) {
        logger.debug("execute add operate.");
        Test u = service.save(obj) ;
        return super.buildSuccess(u); 
    }

    /** 批量删除实体类 */
    @RequestMapping(value = "/delBatch", method = RequestMethod.POST)
    @ResponseBody
    public Object deleteBatch(@RequestBody List<Test> objs, HttpServletRequest request) throws Exception {
        logger.debug("execute del operate.");
        service.batchDeleteEntity(objs);
        return super.buildSuccess(objs);
    }
    
    
	/**
     * 前端树形展示，当数据量小的时候，可以全部查询出来
     * @return
     */  
    @RequestMapping(value = "/Currtype/listall", method = RequestMethod.GET)
    public @ResponseBody Object listAllCurrtype() {
        logger.info("后台数据模拟查询");
        List<Currtype> list = service.findAllCurrtype();
        return super.buildSuccess(list);
    }
    
}
