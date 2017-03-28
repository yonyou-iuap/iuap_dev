package com.yonyou.iuap.crm.common.ref;

import iuap.ref.ref.RefClientPageInfo;
import iuap.ref.sdk.refmodel.model.AbstractGridRefModel;
import iuap.ref.sdk.refmodel.vo.RefViewModelVO;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springside.modules.persistence.SearchFilter.Operator;

import com.yonyou.iuap.crm.common.utils.AppTools;
import com.yonyou.iuap.crm.ieop.security.service.IDefineFunctionService;
import com.yonyou.iuap.persistence.bs.dao.BaseDAO;
import com.yonyou.iuap.persistence.jdbc.framework.processor.MapListProcessor;
import com.yonyou.uap.ieop.security.entity.ExtFunction;

@RequestMapping({"/ref/funcref"})
@Controller
public class FunctionRefController extends AbstractGridRefModel implements IRefModelRestEx{
	
	private final Logger logger = LoggerFactory.getLogger(getClass());
	
	@Autowired
	private IDefineFunctionService cpService;
	
	@Autowired
	@Qualifier("baseDAO") private  BaseDAO dao;

	public FunctionRefController() {}

	@Override
	public RefViewModelVO getRefModelInfo(@RequestBody RefViewModelVO refViewModel) {
		refViewModel = super.getRefModelInfo(refViewModel);
		refViewModel.setRefName("功能节点");
		refViewModel.setRefCode("func");
		refViewModel.setRootName("功能节点");
		refViewModel.setStrHiddenFieldCode(new String[]{"refpk"});
		refViewModel.setStrFieldCode(new String[]{"refcode","refname"});
		refViewModel.setStrFieldName(new String[]{"功能节点编码","功能节点名称"});
		//refViewModel.setRefCodeNamePK(new String[]{"code","name"});
		return refViewModel;
	}

	public Map<String, Object> getCommonRefData(
			@RequestBody RefViewModelVO paramRefViewModelVO) {
		Map<String, Object> result = new HashMap<String, Object>();
		
		Map<String, Object> searchParams = new HashMap<String, Object>();
//		PageRequest pageRequest = new PageRequest(0, 100, new Sort(Direction.ASC, "createDate"));
		RefClientPageInfo refClientPageInfo = paramRefViewModelVO.getRefClientPageInfo();
		int currPageIndex = refClientPageInfo.getCurrPageIndex();
		PageRequest pageRequest = new PageRequest(currPageIndex, AppTools.REFPAGESIZE, new Sort(Direction.ASC, "createDate"));
		Page<ExtFunction> categoryPage = null;
		try {
			//参照查询
			String content = paramRefViewModelVO.getContent();
			if(null != content && content.length()>0){
//				searchParams.put(Operator.LIKE+"&@&@funcCode", content);
				searchParams.put(Operator.LIKE+"&@&@funcName", content);
			}
			categoryPage = cpService.getDemoPageForRef(searchParams, pageRequest);
//			RefClientPageInfo refClientPageInfo = new RefClientPageInfo();
			int pageCount = categoryPage.getTotalPages();
//			int currPageIndex = categoryPage.getNumber() + 1;
//			int currPageIndex = categoryPage.getNumber();
			int pageSize = categoryPage.getSize();
			refClientPageInfo.setCurrPageIndex(currPageIndex);
			refClientPageInfo.setPageCount(pageCount);
			refClientPageInfo.setPageSize(pageSize);
			paramRefViewModelVO.setRefClientPageInfo(refClientPageInfo);
			
			List<Map<String, String>> list = new ArrayList();
			
			List<ExtFunction> vos = categoryPage.getContent();
			for(ExtFunction vo : vos){
				HashMap<String, String> user = new HashMap();
				user.put("refpk", vo.getId());
				user.put("refcode", vo.getFuncCode());
				user.put("refname", vo.getFuncName());
				list.add(user);
			}
			
			result.put("dataList", list);
			result.put("refViewModel", paramRefViewModelVO);
			return result;
		} catch (Exception e) {
			logger.error("功能节点参照获取错误",e);
			e.printStackTrace();
			return null;
		}
	}

	@Override
	public List<Map<String,String>> getIntRefData(
			String pks) throws Exception {
		String sql = "select id as pk,func_name as name from ieop_function where id in ("+pks+");";
		List<Map<String,String>> results = dao.queryForList(sql, new MapListProcessor());
		return results;
	}

	@Override
	public List<Map<String, String>> filterRefJSON(@RequestBody RefViewModelVO arg0) {
		Map<String, Object> searchParams = new HashMap<String, Object>();
		PageRequest pageRequest = new PageRequest(0, 100, new Sort(Direction.ASC, "createDate"));
		Page<ExtFunction> categoryPage = null;
		try {
			categoryPage = cpService.getDemoPage(searchParams, pageRequest);
			
			List<Map<String, String>> list = new ArrayList();
			
			List<ExtFunction> vos = categoryPage.getContent();
			for(ExtFunction vo : vos){
				HashMap<String, String> user = new HashMap();
				user.put("refpk", vo.getId());
				user.put("refcode", vo.getFuncCode());
				user.put("refname", vo.getFuncName());
				list.add(user);
			}
			
			return list;
		} catch (Exception e) {
			logger.error("功能节点参照获取错误",e);
			e.printStackTrace();
			return null;
		}
	}

	@Override
	public List<Map<String, String>> matchPKRefJSON(RefViewModelVO arg0) {
		// TODO 自动生成的方法存根
		return null;
	}

	@Override
	public List<Map<String, String>> matchBlurRefJSON(
			RefViewModelVO paramRefViewModelVO) {
		// TODO 自动生成的方法存根
		return null;
	}
	
}
