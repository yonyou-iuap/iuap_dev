package com.yonyou.iuap.example.validator;

import com.yonyou.iuap.example.entity.mybatis.CardTable;
import com.yonyou.iuap.example.service.CardTableService;
import com.yonyou.iuap.iweb.exception.ValidException;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * <p>Title: CardTableValidtor</p>
 * <p>Description:卡片表校验器 </p>
 * @author zhukai
 */

@Component
public class CardTableValidator {
    @Autowired
    private CardTableService cardTableService;

    public void valid(List<CardTable> cardTableList) {
        if (CollectionUtils.isEmpty(cardTableList)) {
            throw new ValidException("提交的数据集为空!");
        }
        StringBuilder builder = new StringBuilder();
        for (CardTable cardTable : cardTableList) {
            if (StringUtils.isEmpty(cardTable.getPksystem())) {
                if (!cardTableService.findByCode(cardTable.getCode()).isEmpty()) {
                    builder.append(cardTable.getCode()).append(",");
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
