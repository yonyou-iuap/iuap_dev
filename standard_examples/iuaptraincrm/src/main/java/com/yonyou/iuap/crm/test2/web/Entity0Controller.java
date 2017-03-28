package com.yonyou.iuap.crm.test2.web;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.yonyou.iuap.crm.test2.entity.Entity0;
import com.yonyou.iuap.crm.test2.service.Entity0Service;
import com.yonyou.iuap.web.BaseController;
//import com.yonyou.iuap.example.entity.meta.EnumVo;
//import com.yonyou.iuap.example.utils.EnumUtils;
import com.yonyou.iuap.mvc.type.SearchParams;

/**
 * <p>
 * Title: CardTableController
 * </p>
 * <p>
 * Description: 卡片表示例的controller层
 * </p>
 */
@RestController
@RequestMapping(value = "/Entity0")
public class Entity0Controller extends BaseController {
    
	@Autowired
	private Entity0Service service;
    

    /**
     * 查询分页数据
     * 
     * @param pageRequest
     * @param searchParams
     * @return
     */
	@RequestMapping(value = "/list", method = RequestMethod.GET)
    public @ResponseBody Object page(PageRequest pageRequest, SearchParams searchParams) {
        Page<Entity0> data = service.selectAllByPage(pageRequest, searchParams);
        return buildSuccess(data);
    }

    /**
     * 保存数据
     * 
     * @param list
     * @return
     */
	@RequestMapping(value = "/save", method = RequestMethod.POST)
    public @ResponseBody Object save(@RequestBody List<Entity0> list) {
    	service.save(list);
        return buildSuccess();
    }

    /**
     * 删除数据
     * 
     * @param list
     * @return
     */
	@RequestMapping(value = "/del", method = RequestMethod.POST)
    public @ResponseBody Object del(@RequestBody List<Entity0> list) {
    	service.batchDeleteByPrimaryKey(list);
        return buildSuccess();
    }
    
}
