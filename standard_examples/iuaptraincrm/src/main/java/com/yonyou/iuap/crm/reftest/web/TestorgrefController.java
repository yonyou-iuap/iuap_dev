package com.yonyou.iuap.crm.reftest.web;

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

import com.yonyou.iuap.crm.reftest.entity.Testorgref;
import com.yonyou.iuap.crm.reftest.service.TestorgrefService;
import com.yonyou.iuap.crm.corp.entity.BdCorpVO;
import com.yonyou.iuap.crm.user.entity.ExtIeopUserVO;
import com.yonyou.iuap.crm.psn.entity.BdPsndocVO;
import com.yonyou.iuap.web.BaseController;

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
@RequestMapping(value = "/testorgref")
public class TestorgrefController extends BaseController {
    
	@Autowired
	private TestorgrefService service;
    

    /**
     * 查询分页数据
     * 
     * @param pageRequest
     * @param searchParams
     * @return
     */
	@RequestMapping(value = "/list", method = RequestMethod.GET)
    public @ResponseBody Object page(PageRequest pageRequest, SearchParams searchParams) {
        Page<Testorgref> data = service.selectAllByPage(pageRequest, searchParams);
        return buildSuccess(data);
    }

    /**
     * 保存数据
     * 
     * @param list
     * @return
     */
	@RequestMapping(value = "/save", method = RequestMethod.POST)
    public @ResponseBody Object save(@RequestBody List<Testorgref> list) {
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
    public @ResponseBody Object del(@RequestBody List<Testorgref> list) {
    	service.batchDeleteByPrimaryKey(list);
        return buildSuccess();
    }
    
	/**
     * 前端树形展示，当数据量小的时候，可以全部查询出来
     * @return
     */  
    @RequestMapping(value = "/BdCorpVO/listall", method = RequestMethod.GET)
    public @ResponseBody Object listAllBdCorpVO() {
        List<BdCorpVO> list = service.findAllBdCorpVO();
        return super.buildSuccess(list);
    }
    
	/**
     * 前端树形展示，当数据量小的时候，可以全部查询出来
     * @return
     */  
    @RequestMapping(value = "/ExtIeopUserVO/listall", method = RequestMethod.GET)
    public @ResponseBody Object listAllExtIeopUserVO() {
        List<ExtIeopUserVO> list = service.findAllExtIeopUserVO();
        return super.buildSuccess(list);
    }
    
	/**
     * 前端树形展示，当数据量小的时候，可以全部查询出来
     * @return
     */  
    @RequestMapping(value = "/BdPsndocVO/listall", method = RequestMethod.GET)
    public @ResponseBody Object listAllBdPsndocVO() {
        List<BdPsndocVO> list = service.findAllBdPsndocVO();
        return super.buildSuccess(list);
    }
    
}
