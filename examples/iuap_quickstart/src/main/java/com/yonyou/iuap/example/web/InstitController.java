package com.yonyou.iuap.example.web;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.yonyou.iuap.example.entity.meta.Instit;
import com.yonyou.iuap.example.service.InstitService;
import com.yonyou.iuap.example.validator.InstitValidator;

/**
 * <p>
 * Title: InstitController
 * </p>
 * <p>
 * Description:
 * </p>
 * 
 * @author zhukai
 */

@Controller
@RequestMapping(value = "/instit")
public class InstitController extends BaseController {
    public static Logger logger = LoggerFactory.getLogger(InstitController.class);

    @Autowired
    private InstitService institService;

    @Autowired
    private InstitValidator validtor;

    /**
     * 获取机构列表
     * 
     * @return
     */
    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public @ResponseBody Object loadTree() {
        List<Instit> data = institService.selectAll();
        return buildSuccess(data);
    }

    /**
     * 根据父节点获取子节点
     * 
     * @param pid
     * @return
     */
    @RequestMapping(value = "/findByFid", method = RequestMethod.GET)
    public @ResponseBody Object findByFid(@RequestBody String pid) {
        List<Instit> data = institService.findByFid(pid);
        return buildSuccess(data);
    }

    /**
     * 保存机构
     * 
     * @param list
     * @return
     */
    @RequestMapping(value = "/save", method = RequestMethod.POST)
    public @ResponseBody Object save(@RequestBody List<Instit> list) {
        validtor.valid(list);
        institService.save(list);
        return buildSuccess();
    }

    /**
     * 删除机构
     * 
     * @param list
     * @return
     */
    @RequestMapping(value = "/del", method = RequestMethod.POST)
    public @ResponseBody Object del(@RequestBody List<Instit> list) {
        institService.batchDeleteByPrimaryKey(list);
        return buildSuccess();
    }
}
