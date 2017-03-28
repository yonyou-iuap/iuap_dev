package com.yonyou.iuap.example.web;

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

import com.yonyou.iuap.example.entity.meta.Dept;
import com.yonyou.iuap.example.entity.meta.SysUser;
import com.yonyou.iuap.example.entity.meta.SysUserJob;
import com.yonyou.iuap.example.service.SysUserJobService;
import com.yonyou.iuap.mvc.annotation.FrontModelExchange;
import com.yonyou.iuap.mvc.type.SearchParams;

@RestController
@RequestMapping(value = "/sysUserJob")
public class SysUserJobController extends BaseController{
    private Logger logger = LoggerFactory.getLogger(SysUserJobController.class);
    
    @Autowired
    private SysUserJobService userJobService;

    @RequestMapping(value = "/list", method = RequestMethod.GET)
    @ResponseBody
    public Object list(PageRequest pageRequest, @FrontModelExchange(modelType = SysUser.class) SearchParams searchParams) {
        logger.debug("execute data search.");
        Page<SysUserJob> sysUserPage = userJobService.selectAllByPage(searchParams.getSearchMap(), pageRequest) ;
        Map<String,Object> map = new HashMap<String,Object>() ;
        map.put("data", sysUserPage) ;
        
        return super.buildMapSuccess(map) ;
    }
    
    @RequestMapping(value="/del", method= RequestMethod.POST)
    @ResponseBody
    public Object delete(@RequestBody SysUserJob userjob, HttpServletRequest request) {
    	userJobService.deleteEntity(userjob);
    	return super.buildSuccess(userjob) ;
    }
    
    /**
     * 异步加载数据 前端树形展示，当数据量 大 的时候，根据父类id进行异步查询
     * 
     * @param request
     * @return
     */
    @RequestMapping(value = "/dept/listall", method = RequestMethod.GET)
    public @ResponseBody Object listAll(HttpServletRequest request) {
        logger.info("后台数据模拟查询");
        List<Dept> list = userJobService.findAllDept();
        return super.buildSuccess(list);
    }
    
}
