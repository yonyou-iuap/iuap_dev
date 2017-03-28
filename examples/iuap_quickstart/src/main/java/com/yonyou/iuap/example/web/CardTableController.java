package com.yonyou.iuap.example.web;

import com.yonyou.iuap.example.entity.mybatis.CardTable;
import com.yonyou.iuap.example.service.CardTableService;
import com.yonyou.iuap.example.validator.CardTableValidator;
import com.yonyou.iuap.mvc.type.SearchParams;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 * Title: CardTableController
 * </p>
 * <p>
 * Description: 卡片表示例的controller层
 * </p>
 * 
 * @author zhukai
 */
@RestController
@RequestMapping(value = "/cardtable")
public class CardTableController extends BaseController {
    @Autowired
    private CardTableService cardTableService;

    @Autowired
    private CardTableValidator validtor;

    /**
     * 查询分页数据
     * 
     * @param pageRequest
     * @param searchParams
     * @return
     */
    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public @ResponseBody Object page(PageRequest pageRequest, SearchParams searchParams) {
        Page<CardTable> data = cardTableService.selectAllByPage(pageRequest, searchParams);
        return buildSuccess(data);
    }

    /**
     * 保存数据
     * 
     * @param list
     * @return
     */
    @RequestMapping(value = "/save", method = RequestMethod.POST)
    public @ResponseBody Object save(@RequestBody List<CardTable> list) {
        validtor.valid(list);
        cardTableService.save(list);
        return buildSuccess();
    }


    /**
     * 删除数据
     * 
     * @param list
     * @return
     */
    @RequestMapping(value = "/del", method = RequestMethod.POST)
    public @ResponseBody Object del(@RequestBody List<CardTable> list) {
        cardTableService.batchDeleteByPrimaryKey(list);
        return buildSuccess();
    }
}
