package com.yonyou.iuap.example.service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import com.yonyou.iuap.example.entity.meta.Instit;
import com.yonyou.iuap.example.entity.meta.Telbook;
import com.yonyou.iuap.example.repository.meta.TelBookDao;
import com.yonyou.iuap.mvc.type.SearchParams;
import com.yonyou.iuap.persistence.vo.pub.util.StringUtil;
import org.springframework.transaction.annotation.Transactional;

@Service
public class TelBookService {

    @Autowired
    private TelBookDao telbookdao;

    /**
     * 根据某一非主键字段查询实体
     */
    public List<Telbook> findByCode(String code) {
        return telbookdao.findByCode(code);
    }

    /**
     * 分页相关
     */
    public Page<Telbook> selectAllByPage(PageRequest pageRequest, SearchParams searchParams) {
        return telbookdao.selectAllByPage(pageRequest, searchParams.getSearchMap());
    }

    /**
     * 保存（插入和更新）
     */
    @Transactional
    public void save(List<Telbook> recordList) {
        List<Telbook> addList = new ArrayList<>(recordList.size());
        List<Telbook> updateList = new ArrayList<>(recordList.size());
        for (Telbook telbook : recordList) {

            if (telbook.getId() == null) {
                telbook.setId(UUID.randomUUID().toString());
                telbook.setDr(0);
                addList.add(telbook);
            } else {
                updateList.add(telbook);
            }
        }
        if (CollectionUtils.isNotEmpty(addList)) {
            telbookdao.batchInsert(addList);
        }
        if (CollectionUtils.isNotEmpty(updateList)) {
            telbookdao.batchUpdate(updateList);
        }
    }

    /**
     * 批量删除
     */
    public void batchDeleteByPrimaryKey(List<Telbook> list) {
        telbookdao.batchDelete(list);
    }

    /**
     * 根据联系人信息查询所属机构
     *
     * @param userJobPage
     * @return
     */
    public Page<Telbook> selectInstit(Page<Telbook> data) {
        List<Telbook> list = data.getContent();
        Set<String> ids = new HashSet<String>();
        for (Telbook telbook : list) {
            if (!StringUtil.isEmpty(telbook.getInstitid())) {
                ids.add(telbook.getInstitid());
            }
        }
        if (!ids.isEmpty()) {
            List<Instit> instits = this.selectInstitByIds(ids);
            for (Telbook telbook : list) {
                for (Instit instit : instits) {
                    if (telbook.getInstitid() != null && telbook.getInstitid().equalsIgnoreCase(instit.getInstitid())) {
                        telbook.setInstitname(instit.getInstitname());
                    }
                }
            }
            return data;
        }
        return null;
    }

    public List<Instit> selectInstitByIds(Set<String> ids) {
        return telbookdao.selectInstitByIds(ids);
    }



}
