package com.yonyou.iuap.example.web;

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

import com.yonyou.iuap.example.entity.meta.EnumVo;
import com.yonyou.iuap.example.entity.meta.Org;
import com.yonyou.iuap.example.entity.meta.Sex;
import com.yonyou.iuap.example.entity.meta.SysUser;
import com.yonyou.iuap.example.service.SysUserService;
import com.yonyou.iuap.example.utils.EnumUtils;
import com.yonyou.iuap.example.validator.SysUserValidator;
import com.yonyou.iuap.mvc.annotation.FrontModelExchange;
import com.yonyou.iuap.mvc.type.SearchParams;

@RestController
@RequestMapping(value = "/sysUser")
public class SysUserController extends BaseController {
    private Logger logger = LoggerFactory.getLogger(SysUserController.class);

    @Autowired
    private SysUserService sysUserService;

    @Autowired
    private SysUserValidator userValidator;

    /**
     * 前端传入参数，查询数据，列表展示
     * 
     * @return
     */
    @RequestMapping(value = "/list", method = RequestMethod.GET)
    @ResponseBody
    public Object list(PageRequest pageRequest, @FrontModelExchange(modelType = SysUser.class) SearchParams searchParams) {
        logger.debug("execute data search.");
        Page<SysUser> sysUserPage = sysUserService.selectAllByPage(searchParams.getSearchMap(), pageRequest);
        return buildSuccess(sysUserPage);
    }

    /**
     * 增加主子表数据保存，传入json数据
     * 
     * @param user
     * @param request
     * @return
     */
    @RequestMapping(value = "/add", method = RequestMethod.POST)
    @ResponseBody
    public Object add(@RequestBody SysUser user, HttpServletRequest request) {
        logger.debug("execute add operate.");
        userValidator.validAdd(user);
        SysUser u = sysUserService.save(user);
        return super.buildSuccess(u);
    }


    /**
     * 更新主子表数据更新，传入json数据
     * 
     * @param user
     * @param request
     * @return
     */
    @RequestMapping(value = "/update", method = RequestMethod.POST)
    @ResponseBody
    public Object update(@RequestBody SysUser user, HttpServletRequest request) {
        logger.debug("execute add operate.");
        userValidator.validUpdate(user);
        SysUser u = sysUserService.save(user);
        return super.buildSuccess(u);
    }


    /** 批量删除实体类 */
    @RequestMapping(value = "/delBatch", method = RequestMethod.POST)
    @ResponseBody
    public Object deleteBatch(@RequestBody List<SysUser> users, HttpServletRequest request) throws Exception {
        logger.debug("execute del operate.");
        sysUserService.batchDeleteEntity(users);
        return super.buildSuccess(users);
    }

    /** 查询枚举值 */
    @RequestMapping(value = "/loadEnum", method = RequestMethod.GET)
    @ResponseBody
    public Object loadEnum() throws Exception {
        Map<String, List<EnumVo>> map = EnumUtils.loadEnum(Sex.class);
        return super.buildMapSuccess(map);
    }


    /**
     * 前端树形展示，当数据量小的时候，可以全部查询出来
     * 
     * @return
     */
    @RequestMapping(value = "/sysOrg/listall", method = RequestMethod.GET)
    public @ResponseBody Object listAll() {
        logger.info("后台数据模拟查询");
        List<Org> orgs = sysUserService.findAllOrg();
        return super.buildSuccess(orgs);
    }


}
