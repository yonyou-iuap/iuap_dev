package com.yonyou.iuap.crm.common.ref;

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
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import com.yonyou.iuap.crm.ref.entity.RefinfoVO;
import com.yonyou.iuap.crm.ref.service.RefinfoService;
import com.yonyou.iuap.persistence.bs.dao.BaseDAO;
import com.yonyou.iuap.persistence.jdbc.framework.processor.MapListProcessor;

@RequestMapping({"/ref/refinforef"})
@Controller
public class RefInfoController extends AbstractGridRefModel implements IRefModelRestEx{
	
	private final Logger logger = LoggerFactory.getLogger(getClass());
	
	@Autowired
	private RefinfoService iUIrefService;
	
	@Autowired
	@Qualifier("baseDAO") private  BaseDAO dao;

	public RefInfoController() {}

	@Override
	public RefViewModelVO getRefModelInfo(@RequestBody RefViewModelVO refViewModel) {
		refViewModel = super.getRefModelInfo(refViewModel);
		refViewModel.setRefName("关联档案");
		refViewModel.setRefCode("refinfo");
		refViewModel.setRootName("关联档案");
		refViewModel.setStrHiddenFieldCode(new String[]{"refpk"});
		refViewModel.setStrFieldCode(new String[]{"refcode","refname"});
		refViewModel.setStrFieldName(new String[]{"档案编码","档案名称"});
		//refViewModel.setRefCodeNamePK(new String[]{"code","name"});
		return refViewModel;
	}

	

	@Override
	public List<Map<String,String>> getIntRefData(
			String pks) throws Exception {
		String sql = "select pk_ref as pk,refname as name from ref_refinfo where pk_ref in ("+pks+");";
		List<Map<String,String>> results = dao.queryForList(sql, new MapListProcessor());
		return results;
	}

	@Override
	public List<Map<String, String>> filterRefJSON(RefViewModelVO arg0) {
		// TODO 自动生成的方法存根
//		String pks = arg0.getPks();
		List<RefinfoVO> categoryPage = null;
		try {
			categoryPage = iUIrefService.getRefinfoByMdpk("123456");
			List<Map<String, String>> list = new ArrayList();
			for(RefinfoVO vo : categoryPage){
				HashMap<String, String> user = new HashMap();
				user.put("refpk", vo.getPk_ref());
				user.put("refcode", vo.getRefcode());
				user.put("refname", vo.getRefname());
				list.add(user);
			}
			return list;
		} catch (Exception e) {
			logger.error("关联档案参照获取错误",e);
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
	public Map<String, Object> getCommonRefData(RefViewModelVO paramRefViewModelVO) {
		Map<String, Object> result = new HashMap<String, Object>();

//		String pks = arg0.getPks();
		List<RefinfoVO> categoryPage = null;
		try {
			categoryPage = iUIrefService.getRefinfoByMdpk("123456");
			List<Map<String, String>> list = new ArrayList();
			for(RefinfoVO vo : categoryPage){
				HashMap<String, String> user = new HashMap();
				user.put("refpk", vo.getPk_ref());
				user.put("refcode", vo.getRefcode());
				user.put("refname", vo.getRefname());
				list.add(user);
			}
			result.put("dataList", list);
			result.put("refViewModel", paramRefViewModelVO);
			return result;
		} catch (Exception e) {
			logger.error("关联档案参照获取错误",e);
			e.printStackTrace();
			return null;
		}
	
	}

	@Override
	public List<Map<String, String>> matchBlurRefJSON(RefViewModelVO arg0) {
		// TODO 自动生成的方法存根
		return null;
	}
	
}
