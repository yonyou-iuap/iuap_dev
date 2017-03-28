package com.yonyou.iuap.example.web;

import com.yonyou.iuap.iweb.exception.SysPromptException;
import com.yonyou.iuap.iweb.exception.ValidException;
import com.yonyou.iuap.mvc.annotation.FrontModelExchange;
import com.yonyou.iuap.mvc.type.JsonResponse;
import com.yonyou.iuap.mvc.type.SearchParams;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.yonyou.iuap.example.entity.mybatis.TestType;

import java.util.Date;

/**
 * 
 * Created by zengxs on 2016/11/16.
 */
@RestController
@RequestMapping("/test/type")
public class TypeController extends BaseController {


    /**
     * 测试url
     * <p>
     * http://localhost:8080/iuap-quickstart/test/type/list?search_byteValue=1&search_byteValue2=2&
     * search_shortValue
     * =1&search_shortValue2=2&search_intValue=2&search_intValue2=2&search_longValue2
     * =2&search_longValue
     * =1&search_boolValue=1&search_boolValue2=N&&search_bigDecimal=2&search_bigInteger
     * =2&search_doubleValue
     * =3.231&search_doubleValue2=3&search_floatValue=2.3&search_floatValue2=2.3
     * &search_date=2016-11-16
     * 13:11:11.234&search_ts=1479273071234&search_strList=1&search_strList=2
     * &&search_strList=3&search_intList
     * =1&search_intList=2&search_intList=3&search_dateList=1479273071232
     * &search_dateList=1479273071231&search_dateList=1479273071233
     * </p>
     */

    @RequestMapping(value = "/list", method = RequestMethod.GET)
    @ResponseBody
    public SearchParams testType(@FrontModelExchange(modelType = TestType.class) SearchParams searchParams) {
        return searchParams;
    }


    @RequestMapping(value = "/testError/{type}", method = RequestMethod.GET)
    @ResponseBody
    public JsonResponse testError(@PathVariable("type") String errorType) {
        if ("validError".equals(errorType)) {
            throw new ValidException("errorType is " + errorType);
        } else if ("promptError".equals(errorType)) {
            throw new SysPromptException("test", "100001", "0", new Object[] {new Date(), "1", 20000});
        } else {
            return buildGlobalError(errorType);
        }
    }
}