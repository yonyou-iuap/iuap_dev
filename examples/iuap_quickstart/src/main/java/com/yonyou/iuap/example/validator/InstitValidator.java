package com.yonyou.iuap.example.validator;

import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.yonyou.iuap.example.entity.meta.Instit;
import com.yonyou.iuap.example.service.InstitService;
import com.yonyou.iuap.iweb.exception.ValidException;

/**
 * <p>Title: InstitValidator</p>
 * <p>Description: </p>
 * @author zhukai
 */
@Component
public class InstitValidator {
    @Autowired
    private InstitService service;
    

    public void valid(List<Instit> institList) {
        if (CollectionUtils.isEmpty(institList)) {
            throw new ValidException("提交的数据集为空!");
        }
        StringBuilder builder = new StringBuilder();
        for (Instit instit : institList) {
            if (StringUtils.isEmpty(instit.getInstitid())) {
                if (!service.findByCode(instit.getInstitcode()).isEmpty()) {
                    builder.append(instit.getInstitcode()).append(",");
                }
            }
        }
        if (builder.toString().length() > 0) {
            String codeStr = builder.deleteCharAt(builder.length() - 1).toString();
            StringBuilder msgBuilder = new StringBuilder("编码为");
            msgBuilder.append(codeStr).append("的记录已经存在!");
            throw new ValidException(msgBuilder.toString());
        }
    }
}
