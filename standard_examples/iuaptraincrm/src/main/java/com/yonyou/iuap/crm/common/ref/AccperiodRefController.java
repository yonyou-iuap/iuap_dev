package com.yonyou.iuap.crm.common.ref;

import iuap.ref.sdk.refmodel.model.AbstractTreeGridRefModel;
import iuap.ref.sdk.refmodel.vo.RefViewModelVO;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import com.yonyou.iuap.persistence.bs.dao.BaseDAO;
import com.yonyou.iuap.persistence.bs.dao.DAOException;
import com.yonyou.iuap.persistence.jdbc.framework.processor.MapListProcessor;

@RequestMapping({ "/ref/treeref" })
@Controller
public class AccperiodRefController extends AbstractTreeGridRefModel
		implements IRefModelRestEx {
	@Autowired
	
	@Qualifier("baseDAO") private  BaseDAO dao;

	public AccperiodRefController() {
	}

	public RefViewModelVO getRefModelInfo(
			@RequestBody RefViewModelVO refViewModel) {
		refViewModel = super.getRefModelInfo(refViewModel);
		refViewModel.setRefName("档案");
		refViewModel.setRootName("财务期间");
		refViewModel.setStrFieldCode(new String[] { "refcode", "refname" });
		refViewModel.setStrFieldName(new String[] { "开始时间", "结束时间" });
		return refViewModel;
	}

	// 左侧树
	public List<Map<String, String>> blobRefClassSearch(
			@RequestBody RefViewModelVO refViewModel) {
		List<Map<String, String>> list = null;
		List<Map<String, String>> list2 = new ArrayList(5);
		String sql = "select pk_accperiod,periodyear from bd_accperiod where dr=0 order by periodyear";
		try {
			list = dao.queryForList(sql, new MapListProcessor());
		} catch (DAOException e) {
			// TODO 自动生成的 catch 块
			e.printStackTrace();
		}
		list2 = new ArrayList(list.size());
		String REFNAME = "name";
		String REFCODE = "refcode";
		String REFPK = "refpk";
		String PID = "pid";
		String ID = "id";

		for (int i = 0; i < list.size(); i++) {
			HashMap<String, String> year = new HashMap();
			year.put(REFPK, list.get(i).get("pk_accperiod"));
			year.put(REFCODE, list.get(i).get("periodyear"));
			year.put(REFNAME, list.get(i).get("periodyear"));
			year.put(PID, "");
			year.put(ID, list.get(i).get("pk_accperiod"));
			list2.add(year);
		}
		return list2;
	}

	// 右侧数据
	public Map<String, Object> blobRefSearch(
			@RequestBody RefViewModelVO refViewModel) {
		Map<String, Object> result = new HashMap<String, Object>();
		
		String aa = refViewModel.getCondition();
		List<Map<String, String>> list = null;
		List<Map<String, String>> list2 = null;
		String sql = "select pk_accperiodmonth,accperiodmth,begindate from bd_accperiodmonth where dr=0 and pk_accperiod='"
				+ aa + "' order by begindate";
		try {
			list = dao.queryForList(sql, new MapListProcessor());
		} catch (DAOException e) {
			// TODO 自动生成的 catch 块
			e.printStackTrace();
		}
		list2 = new ArrayList(list.size());
		String REFNAME = "refname";
		String REFCODE = "refcode";
		String REFPK = "refpk";
		String MEMO = "memo";
		for (int i = 0; i < list.size(); i++) {
			HashMap<String, String> month = new HashMap();
			month.put(REFPK, list.get(i).get("pk_accperiodmonth"));
			month.put(REFCODE, list.get(i).get("accperiodmth"));
			month.put(REFNAME, list.get(i).get("begindate").substring(0, 7));
			month.put(MEMO, list.get(i).get("accperiodmth"));
			list2.add(month);
		}

		result.put("dataList", list);
		result.put("refViewModel", refViewModel);
		return result;
	}

	// 参照翻译必须实现的方法
	@Override
	public List<Map<String, String>> getIntRefData(String pks) throws Exception {
		List<Map<String, String>> list = null;
		List<Map<String, String>> list2 = null;
		String sql = "select pk_accperiodmonth as pk,begindate as name from bd_accperiodmonth where pk_accperiodmonth in ("
				+ pks + ")";
		list = dao.queryForList(sql, new MapListProcessor());
		list2 = new ArrayList(list.size());
		String PK = "pk";
		String NAME = "name";

		for (int i = 0; i < list.size(); i++) {
			HashMap<String, String> month = new HashMap();
			month.put(PK, list.get(i).get("pk"));
			month.put(NAME, list.get(i).get("name").substring(0, 7));
			list2.add(month);
		}
		return list2;
	}

	public List<Map<String, String>> filterRefJSON(
			@RequestBody RefViewModelVO refViewModel) {
		List<Map<String, String>> list = new ArrayList();

		return list;
	}

	public List<Map<String, String>> matchPKRefJSON(
			@RequestBody RefViewModelVO refViewModel) {
		List<Map<String, String>> list = new ArrayList();

		return list;
	}

	@Override
	public List<Map<String, String>> matchBlurRefJSON(
			RefViewModelVO paramRefViewModelVO) {
		// TODO 自动生成的方法存根
		return null;
	}
}
