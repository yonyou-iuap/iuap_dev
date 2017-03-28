package com.yonyou.iuap.example.web;

import java.util.Map;

import org.apache.commons.lang.StringEscapeUtils;

import com.yonyou.iuap.mvc.constants.RequestStatusEnum;
import com.yonyou.iuap.mvc.type.JsonErrorResponse;
import com.yonyou.iuap.mvc.type.JsonResponse;

/**
 * controller父类 用于处理公用逻辑，例如错误处理等信息
 *
 * Created by zengxs on 2016/11/14.
 */
public class BaseController {

    /**
     * 包装错误信息
     * 
     * @param field
     * @param msg
     * @param status
     * @return
     */
    public JsonResponse buildError(String field, String msg, RequestStatusEnum status) {
        JsonErrorResponse errorResponse = new JsonErrorResponse();
        if (RequestStatusEnum.SUCCESS.equals(status)) {
            throw new IllegalArgumentException("状态码设置错误!");
        }
        errorResponse.setSuccess(status.getStatus());
        if (RequestStatusEnum.FAIL_GLOBAL.equals(status)) {
            errorResponse.setMessage(StringEscapeUtils.escapeHtml(msg));
        } else {
            errorResponse.getDetailMsg().put(StringEscapeUtils.escapeHtml(field), StringEscapeUtils.escapeHtml(msg));
        }
        return errorResponse;
    }

    /**
     * 包装错误信息
     *
     * @param msg
     * @return
     */
    public JsonResponse buildGlobalError(String msg) {
        JsonErrorResponse errorResponse = new JsonErrorResponse();
        errorResponse.setMessage(StringEscapeUtils.escapeHtml(msg));
        return errorResponse;
    }


    /**
     * 包装错误信息
     *
     * @param msgMap
     * @param status
     * @return
     */
    public JsonResponse buildError(Map<String, String> msgMap, RequestStatusEnum status) {
        JsonErrorResponse errorResponse = new JsonErrorResponse();
        if (RequestStatusEnum.SUCCESS.equals(status)) {
            throw new IllegalArgumentException("状态码设置错误!");
        }
        errorResponse.setSuccess(status.getStatus());
        for (Map.Entry<String, String> entry : msgMap.entrySet()) {
            errorResponse.getDetailMsg().put(StringEscapeUtils.escapeHtml(entry.getKey()),
                    StringEscapeUtils.escapeHtml(entry.getValue()));
        }
        return errorResponse;
    }

    /**
     * 包装成功返回信息
     * 
     * @param key
     * @param value
     * @return
     */
    public JsonResponse buildSuccess(String key, Object value) {
        JsonResponse response = new JsonResponse();
        response.getDetailMsg().put(key, value);
        return response;
    }

    /**
     * 包装分页成功返回信息
     *
     * @param value
     * @return
     */
    public <T> JsonResponse buildSuccess(Object value) {
        JsonResponse response = new JsonResponse();
        response.getDetailMsg().put("data", value);
        return response;
    }

    public <T> JsonResponse buildSuccess() {
        JsonResponse response = new JsonResponse();
        return response;
    }

    /**
     * 包装成功返回信息
     * 
     * @param msgMap
     * @return
     */
    public JsonResponse buildMapSuccess(Map msgMap) {
        JsonResponse response = new JsonResponse();
        response.setDetailMsg(msgMap);
        return response;
    }
}
