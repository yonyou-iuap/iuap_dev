package com.yonyou.iuap.example.web;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.yonyou.iuap.example.entity.mybatis.SysDictType;
import com.yonyou.iuap.example.service.SysDictTypeService;
import com.yonyou.iuap.example.validator.SysDictTypeValidator;
import com.yonyou.iuap.iweb.datatable.annotation.IWebParameter;
import com.yonyou.iuap.iweb.entity.DataTable;
import com.yonyou.iuap.iweb.entity.Row;
import com.yonyou.iuap.iweb.event.EventResponse;

/**
 * 字典类型（档案）的controller层
 */
@RestController
@RequestMapping(value = "/sysDictType")
public class SysDictTypeController extends BaseController {

    @Autowired
    private SysDictTypeService sysDicTypeService;

    @Autowired
    private SysDictTypeValidator validtor;

    /**
     * data table 列表查询
     * 
     * @param sysDictTypeDataTable
     * @param response
     * @return
     */
    @RequestMapping(value = "/list", method = RequestMethod.POST)
    @ResponseBody
    public EventResponse page(@IWebParameter(id = "dictTypeDa") DataTable<SysDictType> sysDictTypeDataTable,
            @IWebParameter EventResponse response) {
        int pageNumber = 0;
        if (sysDictTypeDataTable.getPageIndex() != null) {
            pageNumber = sysDictTypeDataTable.getPageIndex();
        }
        Map<String, Object> searchParamMap = createSearchParamsStartingWith(sysDictTypeDataTable, "search_");

        Page<SysDictType> result =
                sysDicTypeService.selectAllByPage(new PageRequest(pageNumber, sysDictTypeDataTable.getPageSize(),
                        new Sort(Sort.Direction.DESC, "dict_id")), searchParamMap);

        sysDictTypeDataTable.setPageData(pageNumber, result.getContent(), result.getTotalPages(),
                result.getTotalElements());
        return response;
    }

    /**
     * 行编辑datatable 实现。增删改实现。
     * 
     * @param sysDictTypeDataTable
     * @param response
     * @return
     */
    @RequestMapping(value = "/save", method = RequestMethod.POST)
    @ResponseBody
    public EventResponse save(@IWebParameter(id = "dictTypeDa") DataTable<SysDictType> sysDictTypeDataTable,
            @IWebParameter EventResponse response) {
        List<SysDictType> addList = new ArrayList<>();
        List<SysDictType> updateList = new ArrayList<>();
        List<SysDictType> removeList = new ArrayList<>();
        Row[] rows = sysDictTypeDataTable.getAllPagesRow();
        for (int i = 0; i < rows.length; i++) {
            String status = rows[i].getStatus();

            // 此处调用asDataTransferObject，会在字段修改的时候，联动修改datatable的row的值
            SysDictType item = sysDictTypeDataTable.asDataTransferObject(rows[i]);
            if (Row.STATUS_NEW.equals(status)) {
                addList.add(item);
                rows[i].setStatus(Row.STATUS_UPD);
            } else if (Row.STATUS_UPD.equals(status)) {
                updateList.add(item);
            } else if (Row.STATUS_FDEL.equals(status) || Row.STATUS_DEL.equals(status)) {
                removeList.add(item);
                rows[i].setStatus(Row.STATUS_DEL);
            }
        }
        validtor.valid(addList);
        validtor.validUpdate(updateList);
        sysDicTypeService.save(addList, updateList, removeList);
        return response;
    }

    /**
     * datatable 多行删除实现。
     *
     * @param sysDictTypeDataTable
     * @param response
     * @return
     */
    @RequestMapping(value = "/del", method = RequestMethod.POST)
    @ResponseBody
    public EventResponse delete(@IWebParameter(id = "dictTypeDa") DataTable<SysDictType> sysDictTypeDataTable,
            @IWebParameter EventResponse response) {
        List<SysDictType> removeList = new ArrayList<>();
        Row[] rows = sysDictTypeDataTable.getAllSelectRows();
        for (int j = 0; j < rows.length; j++) {
            SysDictType item = sysDictTypeDataTable.toBean(rows[j]);
            removeList.add(item);
            rows[j].setStatus(Row.STATUS_DEL);
        }
        sysDicTypeService.batchDeleteByPrimaryKey(removeList);
        return response;
    }

    /**
     * 后端创建行
     * 
     * @param sysDictTypeDataTable
     * @param response
     * @return
     */
    @RequestMapping(value = "/createRow", method = RequestMethod.POST)
    @ResponseBody
    public EventResponse createRow(@IWebParameter(id = "dictTypeDa") DataTable<SysDictType> sysDictTypeDataTable,
            @IWebParameter EventResponse response) {
        SysDictType sysDictType = new SysDictType();
        sysDictType.setIsfixed("N");
        sysDictTypeDataTable.add(sysDictType);
        return response;
    }

    /**
     * 获取datatable的查询过滤条件
     * 
     * @param sysDictTypeDataTable
     * @param prefix
     * @return
     */
    private Map<String, Object> createSearchParamsStartingWith(DataTable sysDictTypeDataTable, String prefix) {
        Map<String, Object> params = new HashMap<String, Object>();
        Map<String, Object> dtParam = sysDictTypeDataTable.getParams();
        Set<Map.Entry<String, Object>> set = dtParam.entrySet();
        for (Map.Entry<String, Object> entry : set) {
            String key = entry.getKey();
            if (key.startsWith(prefix)) {
                String unprefixed = key.substring(prefix.length());
                params.put(unprefixed, entry.getValue().toString());
            }
        }
        return params;
    }

}
