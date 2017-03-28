package com.yonyou.iuap.crm.currtype.web;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.yonyou.iuap.crm.currtype.entity.Currtype;
import com.yonyou.iuap.crm.currtype.service.CurrtypeService;
import com.yonyou.iuap.iweb.datatable.annotation.IWebParameter;
import com.yonyou.iuap.iweb.entity.DataTable;
import com.yonyou.iuap.iweb.entity.Row;
import com.yonyou.iuap.iweb.event.EventResponse;
import com.yonyou.uap.ieop.security.web.BaseController;

/**
 * controller层
 */
@RestController
@RequestMapping(value = "/currtype")
public class CurrtypeController extends BaseController {

	@Autowired
	private CurrtypeService service;

	/**
	 * data table 列表查询
	 * @param sysDictTypeDataTable
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/list", method = RequestMethod.POST)
	@ResponseBody
	public EventResponse page(@IWebParameter(id = "currtypeDa") DataTable<Currtype> dataTable, @IWebParameter EventResponse response) {
		int pageNumber = 0;
		if (dataTable.getPageIndex() != null) {
			pageNumber = dataTable.getPageIndex();
		}
		Map<String, Object> searchParamMap = createSearchParamsStartingWith(dataTable, "search_");

		Page<Currtype> result = service.selectAllByPage(new PageRequest(pageNumber, dataTable.getPageSize(), new Sort(Sort.Direction.DESC, "ts")),
				searchParamMap);

		dataTable.setPageData(pageNumber, result.getContent(), result.getTotalPages(), result.getTotalElements());
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
	public EventResponse save(@IWebParameter(id = "currtypeDa") DataTable<Currtype> dataTable, @IWebParameter EventResponse response) {
		List<Currtype> addList = new ArrayList<>();
		List<Currtype> updateList = new ArrayList<>();
		List<Currtype> removeList = new ArrayList<>();
		List<Row> addRows = new ArrayList<>();
		Row[] rows = dataTable.getAllPagesRow();
		for (int i = 0; i < rows.length; i++) {
			String status = rows[i].getStatus();

			// 此处调用asDataTransferObject，会在字段修改的时候，联动修改datatable的row的值
			Currtype item = dataTable.asDataTransferObject(rows[i]);
			if (Row.STATUS_NEW.equals(status)) {
				addRows.add(rows[i]);						
				item.setPk_currtype(UUID.randomUUID().toString() );			
				addList.add(item);
				rows[i].setStatus(Row.STATUS_UPD);
			} else if (Row.STATUS_UPD.equals(status)) {
				updateList.add(item);
			} else if (Row.STATUS_FDEL.equals(status) || Row.STATUS_DEL.equals(status)) {
				removeList.add(item);
				rows[i].setStatus(Row.STATUS_DEL);
			}
		}
		
		service.save(addList, updateList, removeList);
		return response;
	}

	/**
	 * datatable 多行删除实现。
	 * @param sysDictTypeDataTable
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/del", method = RequestMethod.POST)
	@ResponseBody
	public EventResponse delete(@IWebParameter(id = "currtypeDa") DataTable<Currtype> dataTable, @IWebParameter EventResponse response) {
		List<Currtype> removeList = new ArrayList<>();
		Row[] rows = dataTable.getAllSelectRows();
		for (int j = 0; j < rows.length; j++) {
			Currtype item = dataTable.toBean(rows[j]);
			removeList.add(item);
			rows[j].setStatus(Row.STATUS_DEL);
		}
		service.batchDeleteByPrimaryKey(removeList);
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
	public EventResponse createRow(@IWebParameter(id = "currtypeDa") DataTable<Currtype> dataTable, @IWebParameter EventResponse response) {
		Currtype item = new Currtype();
//		item.setCreateTime(new Date() );
		dataTable.add(item);
		return response;
	}

	/**
	 * 获取datatable的查询过滤条件
	 * @param sysDictTypeDataTable
	 * @param prefix
	 * @return
	 */
	private Map<String, Object> createSearchParamsStartingWith(DataTable dataTable, String prefix) {
		Map<String, Object> params = new HashMap<String, Object>();
		Map<String, Object> dtParam = dataTable.getParams();
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
