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

import com.yonyou.iuap.crm.common.utils.AppTools;
import com.yonyou.iuap.crm.corp.entity.BdCorpVO;
import com.yonyou.iuap.crm.corp.service.itf.IBdCorpService;
import com.yonyou.iuap.persistence.bs.dao.BaseDAO;
import com.yonyou.iuap.persistence.bs.dao.DAOException;
import com.yonyou.iuap.persistence.jdbc.framework.processor.MapListProcessor;
import com.yonyou.iuap.persistence.vo.pub.BusinessException;

@RequestMapping({"/ref/corpref"})
@Controller
public class CorpRefController extends AbstractGridRefModel implements IRefModelRestEx{
	
	private final Logger logger = LoggerFactory.getLogger(getClass());
	
	@Autowired
	private IBdCorpService ctService;
	
	@Autowired
	@Qualifier("baseDAO") private  BaseDAO dao;

	public CorpRefController() {}

	@Override
	public RefViewModelVO getRefModelInfo(@RequestBody RefViewModelVO refViewModel) {
		refViewModel = super.getRefModelInfo(refViewModel);
		refViewModel.setRefName("组织");
		refViewModel.setRefCode("corp");
		refViewModel.setRootName("组织");
		refViewModel.setStrHiddenFieldCode(new String[]{"refpk"});
		refViewModel.setStrFieldCode(new String[]{"refcode","refname","creator"});
		refViewModel.setStrFieldName(new String[]{"组织编码","组织名称","创建人"});
		//refViewModel.setRefCodeNamePK(new String[]{"code","name"});
		return refViewModel;
	}

	@Override
	public Map<String, Object> getCommonRefData(
			@RequestBody RefViewModelVO paramRefViewModelVO) {
		Map<String, Object> result = new HashMap<String, Object>();
		
		Map<String, Object> searchParams = new HashMap<String, Object>();
		RefClientPageInfo refClientPageInfo = paramRefViewModelVO.getRefClientPageInfo();
		
		String condition =  paramRefViewModelVO.getContent();//获取查询条件
		searchParams.put("condition", condition);
		
		int currPageIndex = refClientPageInfo.getCurrPageIndex();
		PageRequest pageRequest = new PageRequest(currPageIndex, AppTools.REFPAGESIZE, new Sort(Direction.ASC, "ts"));
		Page<BdCorpVO> categoryPage = null;
		try {
			categoryPage = ctService.getCorpsBypages(searchParams, pageRequest);
//			RefClientPageInfo refClientPageInfo = new RefClientPageInfo();
//			int currPageIndex = categoryPage.getNumber() + 1;
//			int currPageIndex = categoryPage.getNumber();
//			refClientPageInfo.setCurrPageIndex(currPageIndex);
			int pageCount = categoryPage.getTotalPages();
			int pageSize = categoryPage.getSize();
			refClientPageInfo.setPageCount(pageCount);
			refClientPageInfo.setPageSize(pageSize);
			paramRefViewModelVO.setRefClientPageInfo(refClientPageInfo);
			
			List<Map<String, String>> list = new ArrayList();
			
			List<BdCorpVO> vos = categoryPage.getContent();
			for(BdCorpVO vo : vos){
				HashMap<String, String> user = new HashMap();
				user.put("refpk", vo.getPk_corp());
				user.put("refcode", vo.getUnitcode());
				user.put("refname", vo.getUnitname());
				user.put("creator", vo.getCreator());
				list.add(user);
			}
			
			result.put("dataList", list);
			result.put("refViewModel", paramRefViewModelVO);
			return result;
		} catch (BusinessException e) {
			logger.error("部门参照获取错误",e);
			e.printStackTrace();
			return null;
		}
	}

	@Override
	public List<Map<String,String>> getIntRefData(
			String pks) throws Exception {
		String sql = "select pk_corp as pk,unitname as name from bd_corp where pk_corp in ("+pks+");";
		List<Map<String,String>> results = dao.queryForList(sql, new MapListProcessor());
		return results;
	}

	@Override
	public List<Map<String, String>> filterRefJSON(@RequestBody RefViewModelVO arg0) {
		Map<String, Object> searchParams = new HashMap<String, Object>();
		PageRequest pageRequest = new PageRequest(0, 100, new Sort(Direction.ASC, "ts"));
		Page<BdCorpVO> categoryPage = null;
		
		String condition =  arg0.getContent();//获取查询条件
		searchParams.put("condition", condition);
		
		try {
			categoryPage = ctService.getCorpsBypages(searchParams, pageRequest);
			
			List<Map<String, String>> list = new ArrayList();
			
			List<BdCorpVO> vos = categoryPage.getContent();
			for(BdCorpVO vo : vos){
				HashMap<String, String> user = new HashMap();
				user.put("refpk", vo.getPk_corp());
				user.put("refcode", vo.getUnitcode());
				user.put("refname", vo.getUnitname());
				user.put("creator", vo.getCreator());
				list.add(user);
			}
			
			return list;
		} catch (BusinessException e) {
			logger.error("部门参照获取错误",e);
			e.printStackTrace();
			return null;
		}
	}

	@Override
	public List<Map<String, String>> matchPKRefJSON(@RequestBody RefViewModelVO arg0) {
		// TODO 自动生成的方法存根
		return null;
	}

	@Override
	public List<Map<String, String>> matchBlurRefJSON(
			@RequestBody RefViewModelVO paramRefViewModelVO) {
		// TODO 自动生成的方法存根
		String[] pks = paramRefViewModelVO.getPk_val();
		String pk = "";
		List<Map<String, String>> results = new ArrayList();
		try {
			if(pks!=null&&pks.length>0) {
				pk = pks[0];
				BdCorpVO vo = dao.queryByPK(BdCorpVO.class, pk);
				Map<String, String> map = new HashMap();
				map.put("refpk", vo.getPk_corp()); 
				map.put("refcode", vo.getUnitcode()); 
				map.put("refname", vo.getUnitname()); 
				results.add(map);
			}
		} catch (DAOException e) {
			// TODO 自动生成的 catch 块
			e.printStackTrace();
		}
		return results;
	}
	
}
