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
import org.springframework.web.bind.annotation.RequestBody;

import com.yonyou.iuap.crm.ieop.security.entity.DefinePositionVO;
import com.yonyou.iuap.crm.ieop.security.service.IDefinePositionService;
import com.yonyou.iuap.persistence.bs.dao.BaseDAO;
import com.yonyou.iuap.persistence.jdbc.framework.processor.MapListProcessor;
import com.yonyou.iuap.persistence.vo.pub.BusinessException;

public class PositionRefController extends AbstractGridRefModel implements IRefModelRestEx{
	@Autowired 
	@Qualifier("baseDAO") private  BaseDAO dao;
	@Autowired 
	private IDefinePositionService service;
	
	private final Logger logger = LoggerFactory.getLogger(getClass());
	
	@Override
	public RefViewModelVO getRefModelInfo(@RequestBody RefViewModelVO refViewModel) {
		refViewModel = super.getRefModelInfo(refViewModel);
		refViewModel.setRefName("岗位");
		refViewModel.setRefCode("position");
		refViewModel.setRootName("岗位");
		refViewModel.setStrHiddenFieldCode(new String[]{"refpk"});
		refViewModel.setStrFieldCode(new String[]{"refcode","refname"});
		refViewModel.setStrFieldName(new String[]{"岗位编码","岗位名称"});
		return refViewModel;
	}
	
	@Override
	public List<Map<String, String>> getIntRefData(String pks) throws Exception {
		String sql = "SELECT id as pk,  position_name as name FROM ieop_position where id in ("+pks+")";
		List<Map<String,String>> results = dao.queryForList(sql, new MapListProcessor());
		return results;
	}

	/**
	 * 点击文本框下拉数据
	* @author 
	* @date 2016年12月25日
	* @param arg0
	* @return
	* (non-Javadoc)
	* @see iuap.ref.sdk.refmodel.model.AbstractGridRefModel#filterRefJSON(iuap.ref.sdk.refmodel.vo.RefViewModelVO)
	 */
	@Override
	public List<Map<String, String>> filterRefJSON(RefViewModelVO arg0) {
		Map<String, Object> searchParams = new HashMap<String, Object>();
		PageRequest pageRequest = new PageRequest(0, 10, new Sort(Direction.ASC, "ts"));
		String condition =  arg0.getContent();//获取查询条件
		if(null != condition && condition.length()>0){
			searchParams.put("position_code", condition);
			searchParams.put("position_name", condition);
		}
		
//		searchParams.put("condition", condition);
		Page<DefinePositionVO> categoryPage = null;
		try {
//			String[] param = arg0.getPk_val();
//			String provincepk = "";
//			for(int i=0;i<param.length;i++){
//				String parm1 = "";
//				parm1 = param[i];
//				if(parm1.startsWith("province")){
//					int x= parm1.indexOf("province");
//					provincepk=parm1.substring(parm1.indexOf("province")+9, parm1.length());
//				}
//			}
//			
//			searchParams.put("provincepk", provincepk);
			categoryPage = service.getPostitionBypages(searchParams, pageRequest);
			List<Map<String, String>> list = new ArrayList<Map<String, String>>();
			
			List<DefinePositionVO> vos = categoryPage.getContent();
			for(DefinePositionVO vo : vos){
				HashMap<String, String> provincemap = new HashMap<String, String>();
				provincemap.put("refpk", vo.getId());
				provincemap.put("refcode", vo.getPosition_code());
				provincemap.put("refname", vo.getPosition_name());
				list.add(provincemap);
			}
			
			return list;
		} catch (BusinessException e) {
			logger.error("岗位参照获取错误",e);
			e.printStackTrace();
			return null;
		}
	}

	/**
	 * 点按钮弹出界面查询数据
	* @author 
	* @date 2016年12月25日
	* @param arg0
	* @return
	* (non-Javadoc)
	* @see iuap.ref.sdk.refmodel.model.AbstractGridRefModel#getCommonRefData(iuap.ref.sdk.refmodel.vo.RefViewModelVO)
	 */
	@Override
	public Map<String, Object> getCommonRefData(@RequestBody RefViewModelVO paramRefViewModelVO) {
		Map<String, Object> result = new HashMap<String, Object>();
		
		Map<String, Object> searchParams = new HashMap<String, Object>();
		RefClientPageInfo refClientPageInfo = paramRefViewModelVO.getRefClientPageInfo();
		String condition =  paramRefViewModelVO.getContent();//获取查询条件
		if(null != condition && condition.length()>0){
			searchParams.put("position_code", condition);
			searchParams.put("position_name", condition);
		}
		
//		searchParams.put("condition", condition);
//		String[] param = paramRefViewModelVO.getPk_val();
//		String provincepk = "";
//		for(int i=0;i<param.length;i++){
//			String parm1 = "";
//			parm1 = param[i];
//			if(parm1.startsWith("province")){
//				int x= parm1.indexOf("province");
//				provincepk=parm1.substring(parm1.indexOf("province")+9, parm1.length());
//			}
//		}
//		searchParams.put("provincepk", provincepk);
		int currPageIndex = refClientPageInfo.getCurrPageIndex();
		PageRequest pageRequest = new PageRequest(currPageIndex, 10, new Sort(Direction.ASC, "ts"));
		Page<DefinePositionVO> categoryPage = null;
		try {
			categoryPage = service.getPostitionBypages(searchParams, pageRequest);
			
			int pageCount = categoryPage.getTotalPages();
			int pageSize = categoryPage.getSize();
			refClientPageInfo.setPageCount(pageCount);
			refClientPageInfo.setPageSize(pageSize);
			paramRefViewModelVO.setRefClientPageInfo(refClientPageInfo);

			List<Map<String, String>> list = new ArrayList<Map<String, String>>();
			
			List<DefinePositionVO> vos = categoryPage.getContent();
			for(DefinePositionVO vo : vos){
				HashMap<String, String> provincemap = new HashMap<String, String>();
				provincemap.put("refpk", vo.getId());
				provincemap.put("refcode", vo.getPosition_code());
				provincemap.put("refname", vo.getPosition_name());
				list.add(provincemap);
			}
			
			result.put("dataList", list);
			result.put("refViewModel", paramRefViewModelVO);
			return result;
		} catch (BusinessException e) {
			logger.error("岗位参照获取错误",e);
			e.printStackTrace();
			return null;
		} catch (Exception e) {
			logger.error("岗位参照获取错误",e);
			e.printStackTrace();
			return null;
		}
	}

	@Override
	public List<Map<String, String>> matchBlurRefJSON(RefViewModelVO arg0) {
		// TODO 自动生成的方法存根
		return null;
	}

	@Override
	public List<Map<String, String>> matchPKRefJSON(RefViewModelVO arg0) {
		// TODO 自动生成的方法存根
		return null;
	}

}
