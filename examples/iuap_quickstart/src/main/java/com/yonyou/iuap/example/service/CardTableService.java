package com.yonyou.iuap.example.service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import com.yonyou.iuap.example.entity.mybatis.CardTable;
import com.yonyou.iuap.example.repository.mybatis.CardTableMapper;
import com.yonyou.iuap.mvc.type.SearchParams;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class CardTableService {

    @Autowired
    private CardTableMapper cardTablemapper;


    /**
     * Description:分页查询 Page<CardTable>
     */
    public Page<CardTable> selectAllByPage(PageRequest pageRequest, SearchParams searchParams) {
        return cardTablemapper.selectAllByPage(pageRequest, searchParams).getPage();
    }


    /**
     * Description:批量保存（包括新增和更新） void
     */
    @Transactional
    public void save(List<CardTable> recordList) {
        List<CardTable> addList = new ArrayList<>(recordList.size());
        List<CardTable> updateList = new ArrayList<>(recordList.size());
        for (CardTable cardTable : recordList) {

            if (cardTable.getPksystem() == null) {
                cardTable.setPksystem(UUID.randomUUID().toString());
                cardTable.setDr(0);
                addList.add(cardTable);
            } else {
                updateList.add(cardTable);
            }
        }
        if (CollectionUtils.isNotEmpty(addList)) {
            cardTablemapper.batchInsert(addList);
        }
        if (CollectionUtils.isNotEmpty(updateList)) {
            cardTablemapper.batchUpdate(updateList);
        }
    }

    /**
     * Description:批量删除 void
     */
    public void batchDeleteByPrimaryKey(List<CardTable> list) {
        cardTablemapper.batchDeleteByPrimaryKey(list);
    }

    /**
     * Description:通过非主键字段查询 List<CardTable>
     */
    public List<CardTable> findByCode(String code) {
        return cardTablemapper.findByCode(code);
    }

}
