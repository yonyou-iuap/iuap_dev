package com.yonyou.iuap.example.validator;

import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.yonyou.iuap.example.entity.mybatis.SysDictType;
import com.yonyou.iuap.example.service.SysDictTypeService;
import com.yonyou.iuap.iweb.exception.ValidException;

/**
 * 档案校验器 Created by zengxs on 2016/11/15.
 */
@Component
public class SysDictTypeValidator {

    @Autowired
    private SysDictTypeService sysDictTypeService;

    public void valid(List<SysDictType> sysDictTypeList) {
        StringBuilder builder = new StringBuilder();
        if (CollectionUtils.isNotEmpty(sysDictTypeList)) {
            for (SysDictType dictType : sysDictTypeList) {
                if (CollectionUtils.isNotEmpty(sysDictTypeService.findByCode(dictType.getDicttypecode()))) {
                    builder.append(dictType.getDicttypecode()).append(",");
                }
            }
            checkExists(builder);
        }
    }

    public void validUpdate(List<SysDictType> sysDictTypeList) {
        StringBuilder builder = new StringBuilder();
        if (CollectionUtils.isNotEmpty(sysDictTypeList)) {
            for (SysDictType dictType : sysDictTypeList) {
                List<SysDictType> dictList = sysDictTypeService.findByCode(dictType.getDicttypecode());
                if (CollectionUtils.isNotEmpty(dictList)) {
                    checkUpdateConfict(builder, dictType, dictList);
                }
            }
            checkExists(builder);
        }
    }

    private void checkUpdateConfict(StringBuilder builder, SysDictType dictType, List<SysDictType> dictList) {
        for (SysDictType dbType : dictList) {
            if (!dbType.getDictid().equals(dictType.getDictid())) {
                builder.append(dictType.getDicttypecode()).append(",");
                break;
            }
        }
    }

    private void checkExists(StringBuilder builder) {
        if (builder.toString().length() > 0) {
            String codeStr = builder.deleteCharAt(builder.length() - 1).toString();
            StringBuilder msgBuilder = new StringBuilder("编码为");
            msgBuilder.append(codeStr).append("的记录已经存在!");
            throw new ValidException(msgBuilder.toString());
        }
    }
}
