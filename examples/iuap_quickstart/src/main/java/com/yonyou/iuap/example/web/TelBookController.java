package com.yonyou.iuap.example.web;

import java.util.List;
import java.util.Map;

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

import com.yonyou.iuap.example.entity.meta.EnumVo;
import com.yonyou.iuap.example.entity.meta.Sex;
import com.yonyou.iuap.example.entity.meta.Telbook;
import com.yonyou.iuap.example.service.TelBookService;
import com.yonyou.iuap.example.utils.EnumUtils;
import com.yonyou.iuap.example.validator.TelBookValidator;
import com.yonyou.iuap.mvc.type.SearchParams;

@Controller
@RequestMapping(value = "/telbook")
public class TelBookController extends BaseController {
    private static Logger logger = LoggerFactory.getLogger(TelBookController.class);

    @Autowired
    private TelBookService telbookservice;

    @Autowired
    private TelBookValidator validtor;

    /**
     *
     * @param pageRequest
     * @param searchParams
     * @return
     */
    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public @ResponseBody Object page(PageRequest pageRequest, SearchParams searchParams) {
        Page<Telbook> tmpdata = telbookservice.selectAllByPage(pageRequest, searchParams);
        Page<Telbook> data = telbookservice.selectInstit(tmpdata);
        return buildSuccess(data);
    }

    @RequestMapping(value = "/save", method = RequestMethod.POST)
    public @ResponseBody Object save(@RequestBody List<Telbook> list) {
        validtor.valid(list);
        telbookservice.save(list);
        return buildSuccess();
    }

    @RequestMapping(value = "/del", method = RequestMethod.POST)
    public @ResponseBody Object del(@RequestBody List<Telbook> list) {
        telbookservice.batchDeleteByPrimaryKey(list);
        return buildSuccess();
    }

    /** 查询枚举值 */
    @RequestMapping(value = "/loadEnum", method = RequestMethod.GET)
    @ResponseBody
    public Object loadEnum() throws Exception {
        Map<String, List<EnumVo>> map = EnumUtils.loadEnum(Sex.class);
        return super.buildMapSuccess(map);
    }

}
