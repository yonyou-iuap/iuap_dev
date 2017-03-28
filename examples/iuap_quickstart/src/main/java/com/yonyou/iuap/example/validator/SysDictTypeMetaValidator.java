package com.yonyou.iuap.example.validator;

import java.util.List;

import com.yonyou.iuap.example.entity.mybatis.SysDictType;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.yonyou.iuap.example.entity.meta.SysDictTypeMeta;
import com.yonyou.iuap.example.service.SysDictTypeMetaService;
import com.yonyou.iuap.iweb.exception.ValidException;

/**
 * <p>
 * Title: SysDictTypeMetaValidator
 * </p>
 * <p>
 * Description:
 * </p>
 * 
 * @author zhukai
 */
@Component
public class SysDictTypeMetaValidator {

    @Autowired
    private SysDictTypeMetaService sysDictTypeMetaService;


    public void valid(List<SysDictTypeMeta> sysDictTypeMetaList) {
        if (CollectionUtils.isEmpty(sysDictTypeMetaList)) {
            return;
        }
        StringBuilder builder = new StringBuilder();
        for (SysDictTypeMeta sysDictTypeMeta : sysDictTypeMetaList) {
            if (StringUtils.isEmpty(sysDictTypeMeta.getDictid())) {
                if (!sysDictTypeMetaService.findByCode(sysDictTypeMeta.getDicttypecode()).isEmpty()) {
                    builder.append(sysDictTypeMeta.getDicttypecode()).append(",");
                }
            }
        }
        checkExists(builder);
    }

    public void validUpdate(List<SysDictTypeMeta> sysDictTypeMetaList) {
        StringBuilder builder = new StringBuilder();
        if (CollectionUtils.isNotEmpty(sysDictTypeMetaList)) {
            for (SysDictTypeMeta dictType : sysDictTypeMetaList) {
                List<SysDictTypeMeta> dictList = sysDictTypeMetaService.findByCode(dictType.getDicttypecode());
                if (CollectionUtils.isNotEmpty(dictList)) {
                    checkUpdateConfict(builder, dictType, dictList);
                }
            }
            checkExists(builder);
        }
    }

    private void checkUpdateConfict(StringBuilder builder, SysDictTypeMeta dictType, List<SysDictTypeMeta> dictList) {
        for (SysDictTypeMeta dbType : dictList) {
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
