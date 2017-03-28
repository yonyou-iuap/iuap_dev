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
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import com.yonyou.iuap.crm.psn.entity.BdPsndocVO;
import com.yonyou.iuap.crm.psn.service.BdPsnService;
import com.yonyou.iuap.persistence.bs.dao.BaseDAO;
import com.yonyou.iuap.persistence.jdbc.framework.processor.MapListProcessor;
import com.yonyou.iuap.persistence.vo.pub.BusinessException;

@RequestMapping({"/ref/psnref"})
@Controller
public class PsnRefController extends AbstractGridRefModel implements IRefModelRestEx{
	
	private final Logger logger = LoggerFactory.getLogger(getClass());
	
	@Autowired
	private BdPsnService ctService;
	
	@Autowired
	@Qualifier("baseDAO") private  BaseDAO dao;

	public PsnRefController() {}

	@Override
	public RefViewModelVO getRefModelInfo(@RequestBody RefViewModelVO refViewModel) {
		refViewModel = super.getRefModelInfo(refViewModel);
		refViewModel.setRefName("人员");
		refViewModel.setRefCode("psn");
		refViewModel.setRootName("人员");
		refViewModel.setStrHiddenFieldCode(new String[]{"refpk"});
		refViewModel.setStrFieldCode(new String[]{"refcode","refname"});
		refViewModel.setStrFieldName(new String[]{"人员编码","人员名称"});
		//refViewModel.setRefCodeNamePK(new String[]{"code","name"});
		return refViewModel;
	}

	@Override
	public Map<String, Object> getCommonRefData(
			@RequestBody RefViewModelVO paramRefViewModelVO) {
		int currPageIndex = 0;
		if(paramRefViewModelVO.getRefClientPageInfo().getCurrPageIndex() < 0){
			currPageIndex =0;
		}else{
			currPageIndex =paramRefViewModelVO.getRefClientPageInfo().getCurrPageIndex();
		}
		int pageSize = paramRefViewModelVO.getRefClientPageInfo().getPageSize();
		Map<String, Object> searchParams = new HashMap<String, Object>();
		PageRequest pageRequest = new PageRequest(currPageIndex, 
				pageSize, new Sort(Direction.ASC, "cts"));
		Page<BdPsndocVO> categoryPage = null;
		String pkdept="";
		String pk_corp="";
		String loginName="";
		String id="";
		String sqlvalue="";
		try {
			String[] param =paramRefViewModelVO.getPk_val();
			//参照查询
			String content = paramRefViewModelVO.getContent();
			if(null != content && content.length()>0){
				searchParams.put("like&@&@psnname", content);
			}
			for(int i=0;i<param.length;i++){
				String parm1 = "";
				parm1 = param[i];
				if(parm1.startsWith("pkDept")){
					pkdept=parm1.substring(parm1.indexOf("pkDept=")+7, parm1.length());
					searchParams.put("=&@&@pk_dept", pkdept);
				}else if(parm1.startsWith("pkCorp")){
					pk_corp=parm1.substring(parm1.indexOf("pkCorp=")+7, parm1.length());
					searchParams.put("=&@&@pk_corp", pk_corp);
				}
				else if(parm1.startsWith("loginName")){
					loginName=parm1.substring(parm1.indexOf("loginName=")+10, parm1.length());
					searchParams.put("like&@&@psnname", loginName);
				}
				else if(parm1.startsWith("id")){
					id=parm1.substring(parm1.indexOf("id=")+3, parm1.length());
					searchParams.put("like&@&@psnid", id);
				}
			}
			List<Map<String, String>> list = new ArrayList();


			categoryPage = ctService.getBdDeptsForRef(searchParams, pageRequest);
			List<BdPsndocVO> vos = categoryPage.getContent();
			for(BdPsndocVO vo : vos){
				HashMap<String, String> user = new HashMap();
				user.put("refpk", vo.getPk_psndoc());
				user.put("refcode", vo.getPsncode());
				user.put("refname", vo.getPsnname());
//				user.put("creator", vo.getCreator());
				list.add(user);
			}
			
			Map<String,Object> map = new HashMap<String,Object>();
			Map<String,Object> refmap = new HashMap<String,Object>();
			Map<String,Object> pagemap = new HashMap<String,Object>();
			pagemap.put("pageSize", categoryPage.getSize());
			pagemap.put("currPageIndex", categoryPage.getNumber());
			pagemap.put("pageCount", 10);
			refmap.put("refClientPageInfo",pagemap);
			map.put("dataList", list);
			map.put("refViewModel",refmap);
			return map;
		} catch (BusinessException e) {
			logger.error("部门参照获取错误",e);
			e.printStackTrace();
			return null;
		}
	}

	@Override
	public List<Map<String,String>> getIntRefData(
			String pks) throws Exception {
		String sql = "select pk_psndoc as pk,psnname as name from bd_psndoc where pk_psndoc in ("+pks+");";
		List<Map<String,String>> results = dao.queryForList(sql, new MapListProcessor());
		return results;
	}

	@Override
	public List<Map<String, String>> filterRefJSON(@RequestBody RefViewModelVO arg0) {
		Map<String, Object> searchParams = new HashMap<String, Object>();
		PageRequest pageRequest = new PageRequest(0, 100, new Sort(Direction.ASC, "ts"));
		Page<BdPsndocVO> categoryPage = null;
//			categoryPage = ctService.getBterDeptsBypage(searchParams, pageRequest);
//			RefClientPageInfo refClientPageInfo = new RefClientPageInfo();
//			refClientPageInfo.setCurrPageIndex(1);
//			refClientPageInfo.setPageCount(5);
//			refClientPageInfo.setPageSize(5);
//			arg0.setRefClientPageInfo(refClientPageInfo);
//			String value =  arg0.getContent(); // 根据编号和名称过滤
//			searchParams.put("custcode", value);
		String pkdept="";
		String pk_corp="";
		String loginName="";
		String id="";
		String sqlvalue="";
		try {
			String[] param =arg0.getPk_val();
			for(int i=0;i<param.length;i++){
				String parm1 = "";
				parm1 = param[i];
				if(parm1.startsWith("pkDept")){
					pkdept=parm1.substring(parm1.indexOf("pkDept=")+7, parm1.length());
					searchParams.put("=&@&@pk_dept", pkdept);
				}else if(parm1.startsWith("pkCorp")){
					pk_corp=parm1.substring(parm1.indexOf("pkCorp=")+7, parm1.length());
					searchParams.put("=&@&@pk_corp", pk_corp);
				}
				else if(parm1.startsWith("loginName")){
					loginName=parm1.substring(parm1.indexOf("loginName=")+10, parm1.length());
					searchParams.put("like&@&@psnname", loginName);
				}
				else if(parm1.startsWith("id")){
					id=parm1.substring(parm1.indexOf("id=")+3, parm1.length());
					searchParams.put("like&@&@psnid", id);
				}
			}
			
			
			categoryPage = ctService.getBdDeptsForRef(searchParams, pageRequest);
			
			List<Map<String, String>> list = new ArrayList();
			
			List<BdPsndocVO> vos = categoryPage.getContent();
			for(BdPsndocVO vo : vos){
				HashMap<String, String> user = new HashMap();
				user.put("refpk", vo.getPk_psndoc());
				user.put("refcode", vo.getPsncode());
				user.put("refname", vo.getPsnname());
//				user.put("creator", vo.getCreator());
				list.add(user);
			}
			
			return list;
		} catch (Exception e) {
			logger.error("部门参照获取错误",e);
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
