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

import com.yonyou.iuap.persistence.bs.dao.BaseDAO;
import com.yonyou.iuap.persistence.bs.dao.DAOException;
import com.yonyou.iuap.persistence.jdbc.framework.processor.MapListProcessor;
import com.yonyou.iuap.persistence.vo.pub.BusinessException;
import com.yonyou.iuap.crm.common.utils.AppTools;
import com.yonyou.iuap.crm.dept.entity.BdDeptVO;
import com.yonyou.iuap.crm.dept.service.itf.IBdDeptService;

@RequestMapping({"/ref/deptref"})
@Controller
public class DeptRefController extends AbstractGridRefModel implements IRefModelRestEx{
	
	private final Logger logger = LoggerFactory.getLogger(getClass());
	
	@Autowired
	private IBdDeptService  ctService;
	
	@Autowired
	@Qualifier("baseDAO") private  BaseDAO dao;

	public DeptRefController() {}

	@Override
	public RefViewModelVO getRefModelInfo(@RequestBody RefViewModelVO refViewModel) {
		refViewModel = super.getRefModelInfo(refViewModel);
		refViewModel.setRefName("部门");
		refViewModel.setRefCode("dept");
		refViewModel.setRootName("部门");
		refViewModel.setStrHiddenFieldCode(new String[]{"refpk"});
		refViewModel.setStrFieldCode(new String[]{"refcode","refname"});
		refViewModel.setStrFieldName(new String[]{"部门编码","部门名称"});
		//refViewModel.setRefCodeNamePK(new String[]{"code","name"});
		return refViewModel;
	}

	public Map<String, Object> getCommonRefData(
			@RequestBody RefViewModelVO paramRefViewModelVO) {
		Map<String, Object> result = new HashMap<String, Object>();
		
		Map<String, Object> searchParams = new HashMap<String, Object>();
		RefClientPageInfo refClientPageInfo = paramRefViewModelVO.getRefClientPageInfo();
		int currPageIndex = refClientPageInfo.getCurrPageIndex();
		
		String condition = paramRefViewModelVO.getContent();// 获取查询条件
		searchParams.put("condition", condition);
		PageRequest pageRequest = new PageRequest(currPageIndex, AppTools.REFPAGESIZE, new Sort(Direction.ASC, "ts"));
		Page<BdDeptVO> categoryPage = null;
		String deptname="";
		String pk_corp="";
		try {
			String[] param =paramRefViewModelVO.getPk_val();
			for(int i=0;param!=null && i<param.length;i++){
				String parm1 = "";
				parm1 = param[i];
				if(parm1.startsWith("deptname")){
					deptname=parm1.substring(parm1.indexOf("deptname=")+9, parm1.length());
					searchParams.put("like&@&@deptname", deptname);
				}else if(parm1.startsWith("pk_corp")){
					pk_corp=parm1.substring(parm1.indexOf("pk_corp=")+8, parm1.length());
					searchParams.put("=&@&@pk_corp", pk_corp);
				}
			}
			categoryPage = ctService.getBdDeptsBypage(searchParams, pageRequest);
			int pageCount = categoryPage.getTotalPages();
			int pageSize = categoryPage.getSize();
			refClientPageInfo.setPageCount(pageCount);
			refClientPageInfo.setPageSize(pageSize);
			paramRefViewModelVO.setRefClientPageInfo(refClientPageInfo);
			
			List<Map<String, String>> list = new ArrayList();
			List<BdDeptVO> vos = categoryPage.getContent();
			for(BdDeptVO vo : vos){
				HashMap<String, String> dept = new HashMap();
				dept.put("refpk", vo.getPk_dept());
				dept.put("refcode", vo.getDeptcode());
				dept.put("refname", vo.getDeptname());
				list.add(dept);
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
		String sql = "select pk_dept as pk,deptname as name from bd_dept where pk_dept in ("+pks+");";
		List<Map<String,String>> results = dao.queryForList(sql, new MapListProcessor());
		return results;
	}

	@Override
	public List<Map<String, String>> filterRefJSON(@RequestBody RefViewModelVO arg0) {
		Map<String, Object> searchParams = new HashMap<String, Object>();
		PageRequest pageRequest = new PageRequest(0, 100, new Sort(Direction.ASC, "ts"));
		Page<BdDeptVO> categoryPage = null;
		try {
			String deptname="";
			String pk_corp="";
			String[] param =arg0.getPk_val();
			for(int i=0;param!=null && i<param.length;i++){
				String parm1 = "";
				parm1 = param[i];
				if(parm1.startsWith("deptname")){
					deptname=parm1.substring(parm1.indexOf("deptname=")+9, parm1.length());
					searchParams.put("like&@&@deptname", deptname);
				}else if(parm1.startsWith("pk_corp")){
					pk_corp=parm1.substring(parm1.indexOf("pk_corp=")+8, parm1.length());
					searchParams.put("=&@&@pk_corp", pk_corp);
				}
			}
			categoryPage = ctService.getBdDeptsBypage(searchParams, pageRequest);
//			categoryPage = ctService.getDpExpsBypage(searchParams, pageRequest);
			RefClientPageInfo refClientPageInfo = new RefClientPageInfo();
			refClientPageInfo.setCurrPageIndex(1);
			refClientPageInfo.setPageCount(5);
			refClientPageInfo.setPageSize(5);
			arg0.setRefClientPageInfo(refClientPageInfo);
			
			List<Map<String, String>> list = new ArrayList();
			
			List<BdDeptVO> vos = categoryPage.getContent();
			for(BdDeptVO vo : vos){
				HashMap<String, String> dept = new HashMap();
				dept.put("refpk", vo.getPk_dept());
				dept.put("refcode", vo.getDeptcode());
				dept.put("refname", vo.getDeptname());
				list.add(dept);
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
				BdDeptVO vo = dao.queryByPK(BdDeptVO.class, pk);
				Map<String, String> map = new HashMap();
				map.put("refpk", vo.getPk_dept()); 
				map.put("refcode", vo.getDeptcode()); 
				map.put("refname", vo.getDeptname()); 
				results.add(map);
			}
		} catch (DAOException e) {
			// TODO 自动生成的 catch 块
			e.printStackTrace();
		}
		return results;
	}
	
}
