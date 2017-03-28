package com.yonyou.iuap.example.web;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.yonyou.iuap.example.entity.meta.CardTableMeta;
import com.yonyou.iuap.example.service.CardTableMetaService;
import com.yonyou.iuap.example.validator.CardTableMetaValidator;
import com.yonyou.iuap.mvc.type.SearchParams;

/**
 * <p>Title: CardTableMetaController</p>
 * <p>Description: </p>
 * @author zhukai
 */
@RestController
@RequestMapping(value = "/cardtablemeta")
public class CardTableMetaController extends BaseController{
    
	@Autowired
    private CardTableMetaService cardTableMetaService;
	
	@Autowired
	private CardTableMetaValidator validtor;
    

    /**
     * 查询分页数据
     * 
     * @param pageRequest
     * @param searchParams
     * @return
     */
    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public @ResponseBody Object page(PageRequest pageRequest, SearchParams searchParams) {
        Page<CardTableMeta> data = cardTableMetaService.selectAllByPage(pageRequest, searchParams);
        return buildSuccess(data);
    }

    /**
     * 保存数据
     * 
     * @param list
     * @return
     */
    @RequestMapping(value = "/save", method = RequestMethod.POST)
    public @ResponseBody Object save(@RequestBody List<CardTableMeta> list) {
        validtor.valid(list);
        cardTableMetaService.save(list);
        return buildSuccess();
    }

    /**
     * 删除数据
     * 
     * @param list
     * @return
     */
    @RequestMapping(value = "/del", method = RequestMethod.POST)
    public @ResponseBody Object del(@RequestBody List<CardTableMeta> list) {
    	cardTableMetaService.batchDeleteByPrimaryKey(list);
        return buildSuccess();
    }
}
