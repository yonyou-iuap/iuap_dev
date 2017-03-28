package com.yonyou.iuap.example.validator;

import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.yonyou.iuap.example.entity.meta.CardTableMeta;
import com.yonyou.iuap.example.entity.mybatis.CardTable;
import com.yonyou.iuap.example.service.CardTableMetaService;
import com.yonyou.iuap.iweb.exception.ValidException;

/**
 * <p>Title: CardTableMetaValidator</p>
 * <p>Description: </p>
 * @author zhukai
 */
@Component
public class CardTableMetaValidator {
	
    @Autowired
    private CardTableMetaService cardTableMetaService;

    public void valid(List<CardTableMeta> cardTableMetaList) {
        if (CollectionUtils.isEmpty(cardTableMetaList)) {
            throw new ValidException("提交的数据集为空!");
        }
        StringBuilder builder = new StringBuilder();
        for (CardTableMeta cardTableMeta : cardTableMetaList) {
            if (StringUtils.isEmpty(cardTableMeta.getPksystem())) {
                if (!cardTableMetaService.findByCode(cardTableMeta.getCode()).isEmpty()) {
                    builder.append(cardTableMeta.getCode()).append(",");
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
