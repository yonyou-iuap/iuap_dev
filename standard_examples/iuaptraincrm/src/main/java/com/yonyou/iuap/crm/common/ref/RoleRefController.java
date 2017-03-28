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
import com.yonyou.iuap.persistence.bs.dao.BaseDAO;
import com.yonyou.iuap.persistence.jdbc.framework.processor.MapListProcessor;
import com.yonyou.uap.ieop.security.entity.Role;
import com.yonyou.uap.ieop.security.service.IRoleService;

@RequestMapping({"/ref/roleref"})
@Controller
public class RoleRefController extends AbstractGridRefModel implements IRefModelRestEx{
	
	private final Logger logger = LoggerFactory.getLogger(getClass());
	
	@Autowired
	private IRoleService ctService;
	
	@Autowired
	@Qualifier("baseDAO") private  BaseDAO dao;

	public RoleRefController() {}

	@Override
	public RefViewModelVO getRefModelInfo(@RequestBody RefViewModelVO refViewModel) {
		refViewModel = super.getRefModelInfo(refViewModel);
		refViewModel.setRefName("角色");
		refViewModel.setRefCode("role");
		refViewModel.setRootName("角色");
		refViewModel.setStrHiddenFieldCode(new String[]{"refpk"});
		refViewModel.setStrFieldCode(new String[]{"refcode","refname"});
		refViewModel.setStrFieldName(new String[]{"角色编码","角色名称"});
		//refViewModel.setRefCodeNamePK(new String[]{"code","name"});
		return refViewModel;
	}

	@Override
	public Map<String, Object> getCommonRefData(
			@RequestBody RefViewModelVO paramRefViewModelVO) {
		Map<String, Object> result = new HashMap<String, Object>();
		
		Map<String, Object> searchParams = new HashMap<String, Object>();
//		PageRequest pageRequest = new PageRequest(0, 100, new Sort(Direction.ASC, "createDate"));
		RefClientPageInfo refClientPageInfo = paramRefViewModelVO.getRefClientPageInfo();
		int currPageIndex = refClientPageInfo.getCurrPageIndex();
		PageRequest pageRequest = new PageRequest(currPageIndex, AppTools.REFPAGESIZE, new Sort(Direction.ASC, "ts"));
		Page<Role> categoryPage = null;
		try {
			categoryPage = ctService.getRolePage(searchParams, pageRequest);
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
			
			List<Role> vos = categoryPage.getContent();
			for(Role vo : vos){
				HashMap<String, String> user = new HashMap();
				user.put("refpk", vo.getId());
				user.put("refcode", vo.getRoleCode());
				user.put("refname", vo.getRoleName());
				list.add(user);
			}
			
			result.put("dataList", list);
			result.put("refViewModel", paramRefViewModelVO);
			return result;
		} catch (Exception e) {
			logger.error("角色参照获取错误",e);
			e.printStackTrace();
			return null;
		}
	}

	@Override
	public List<Map<String,String>> getIntRefData(
			String pks) throws Exception {
		String sql = "select id as pk,role_name as name from ieop_role where id in ("+pks+");";
		List<Map<String,String>> results = dao.queryForList(sql, new MapListProcessor());
		return results;
	}

	@Override
	public List<Map<String, String>> filterRefJSON(RefViewModelVO arg0) {
		// TODO 自动生成的方法存根
//		String pks = arg0.getPks();
		Map<String, Object> searchParams = new HashMap<String, Object>();
		PageRequest pageRequest = new PageRequest(0, 100, new Sort(Direction.ASC, "createDate"));
		Page<Role> categoryPage = null;
		try {
			categoryPage = ctService.getRolePage(searchParams, pageRequest);
			
			List<Map<String, String>> list = new ArrayList();
			
			List<Role> vos = categoryPage.getContent();
			for(Role vo : vos){
				HashMap<String, String> user = new HashMap();
				user.put("refpk", vo.getId());
				user.put("refcode", vo.getRoleCode());
				user.put("refname", vo.getRoleName());
				list.add(user);
			}
			
			return list;
		} catch (Exception e) {
			logger.error("角色参照获取错误",e);
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
