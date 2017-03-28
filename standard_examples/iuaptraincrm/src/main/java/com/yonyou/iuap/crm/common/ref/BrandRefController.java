
/** 
*Copyright 2016 Yonyou Corporation Ltd. All Rights Reserved.
* This software is published under the terms of the Yonyou Software
* License version 1.0, a copy of which has been included with this
* distribution in the LICENSE.txt file.
*
* @Project Name : iuaptraincrm
*
* @File name : BrandRefController.java
*
* @author 
*
* @Date : 2016年11月28日
*
----------------------------------------------------------------------------------
*     Date       Who       Version     Comments
* 1. 2016年11月28日    name    1.0
*
*
*
*
----------------------------------------------------------------------------------
*/
	
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

import com.yonyou.iuap.crm.basedata.entity.TmBrandVO;
import com.yonyou.iuap.crm.basedata.service.itf.ITmBrandService;
import com.yonyou.iuap.crm.common.utils.AppTools;
import com.yonyou.iuap.crm.common.utils.DictCode;
import com.yonyou.iuap.persistence.bs.dao.BaseDAO;
import com.yonyou.iuap.persistence.jdbc.framework.processor.MapListProcessor;
import com.yonyou.iuap.persistence.vo.pub.BusinessException;



/**
 * 品牌参照
 * @author 
 * @date 2016年11月28日
 */
@RequestMapping({"/ref/brandref"})
@Controller
public class BrandRefController extends AbstractGridRefModel implements IRefModelRestEx{
private final Logger logger = LoggerFactory.getLogger(getClass());
	
	@Autowired
	private ITmBrandService cpService;
	
	@Autowired
	@Qualifier("baseDAO") private  BaseDAO dao;

	public BrandRefController() {}

	@Override
	public RefViewModelVO getRefModelInfo(@RequestBody RefViewModelVO refViewModel) {
		refViewModel = super.getRefModelInfo(refViewModel);
		refViewModel.setRefName("品牌");
		refViewModel.setRefCode("brand");
		refViewModel.setRootName("品牌");
		refViewModel.setStrHiddenFieldCode(new String[]{"refpk"});
		refViewModel.setStrFieldCode(new String[]{"refcode","refname"});
		refViewModel.setStrFieldName(new String[]{"品牌编码","品牌名称"});
		return refViewModel;
	}

	@Override
	public Map<String, Object> getCommonRefData(@RequestBody RefViewModelVO paramRefViewModelVO) {

		Map<String, Object> result = new HashMap<String, Object>();
		Map<String, Object> searchParams = new HashMap<String, Object>();
		RefClientPageInfo refClientPageInfo = paramRefViewModelVO.getRefClientPageInfo();
		String condition =  paramRefViewModelVO.getContent();					//获取查询条件
		searchParams.put("condition", condition);
		int currPageIndex = refClientPageInfo.getCurrPageIndex();
		PageRequest pageRequest = new PageRequest(currPageIndex, AppTools.REFPAGESIZE, new Sort(Direction.ASC, "ts"));
		Page<TmBrandVO> categoryPage = null;
		try {
			searchParams.put("vstatus", DictCode.ALREADY_START_STATUS);
			categoryPage = cpService.getTmBrandsBypage(searchParams, pageRequest);
			
			int pageCount = categoryPage.getTotalPages();
			int pageSize = categoryPage.getSize();
			refClientPageInfo.setPageCount(pageCount);
			refClientPageInfo.setPageSize(pageSize);
			paramRefViewModelVO.setRefClientPageInfo(refClientPageInfo);

			List<Map<String, String>> list = new ArrayList<Map<String, String>>();
			
			List<TmBrandVO> vos = categoryPage.getContent();
			for(TmBrandVO vo : vos){
				HashMap<String, String> brandmap = new HashMap<String, String>();
				brandmap.put("refpk", vo.getPk_brand());
				brandmap.put("refcode", vo.getVbrandcode());
				brandmap.put("refname", vo.getVbrandname());
				list.add(brandmap);
			}
			
			result.put("dataList", list);
			result.put("refViewModel", paramRefViewModelVO);
			return result;
		} catch (BusinessException e) {
			logger.error("品牌参照获取错误",e);
			e.printStackTrace();
			return null;
		} catch (Exception e) {
			logger.error("品牌参照获取错误",e);
			e.printStackTrace();
			return null;
		}
	
	}
	
//	@Override
//	public Map<String, Object> getCommonRefData(
//			RefViewModelVO paramRefViewModelVO) {
//		Map<String, Object> result = new HashMap<String, Object>();
//		
//		Map<String, Object> searchParams = new HashMap<String, Object>();
////		PageRequest pageRequest = new PageRequest(0, 100, new Sort(Direction.ASC, "ts"));
//		RefClientPageInfo refClientPageInfo = paramRefViewModelVO.getRefClientPageInfo();
//		int currPageIndex = refClientPageInfo.getCurrPageIndex();
//		PageRequest pageRequest = new PageRequest(currPageIndex, AppTools.REFPAGESIZE, new Sort(Direction.ASC, "ts"));
//		Page<TmBrandVO> categoryPage = null;
//		try {
//			categoryPage = cpService.getTmBrandsBypage(searchParams, pageRequest);
//			int pageCount = categoryPage.getTotalPages();
//			int pageSize = categoryPage.getSize();
//			refClientPageInfo.setCurrPageIndex(currPageIndex);
//			refClientPageInfo.setPageCount(pageCount);
//			refClientPageInfo.setPageSize(pageSize);
//			paramRefViewModelVO.setRefClientPageInfo(refClientPageInfo);
//			
//			List<Map<String, String>> list = new ArrayList();
//			
//			List<TmBrandVO> vos = categoryPage.getContent();
//			for(TmBrandVO vo : vos){
//				HashMap<String, String> user = new HashMap();
//				user.put("refpk", vo.getPk_brand());
//				user.put("refcode", vo.getVbrandcode());
//				user.put("refname", vo.getVbrandname());
//				list.add(user);
//			}
//			result.put("dataList", list);
//			result.put("refViewModel", paramRefViewModelVO);
//			return result;
//		} catch (BusinessException e) {
//			logger.error("品牌参照获取错误",e);
//			e.printStackTrace();
//			return null;
//		}
//	}

	@Override
	public List<Map<String,String>> getIntRefData(
			String pks) throws Exception {
		String sql = "select pk_brand as pk,vbrandname as name from tm_brand where pk_brand in ("+pks+");";
		List<Map<String,String>> results = dao.queryForList(sql, new MapListProcessor());
		return results;
	}

	@Override
	public List<Map<String, String>> filterRefJSON(RefViewModelVO arg0) {
		Map<String, Object> searchParams = new HashMap<String, Object>();
		String condition =  arg0.getContent();					//获取查询条件
		searchParams.put("condition", condition);
		PageRequest pageRequest = new PageRequest(0, 100, new Sort(Direction.ASC, "ts"));
		Page<TmBrandVO> categoryPage = null;
		try {
			searchParams.put("vstatus", DictCode.ALREADY_START_STATUS);
			categoryPage = cpService.getTmBrandsBypage(searchParams, pageRequest);
			List<Map<String, String>> list = new ArrayList();
			List<TmBrandVO> vos = categoryPage.getContent();
			for(TmBrandVO vo : vos){
				HashMap<String, String> user = new HashMap();
				user.put("refpk", vo.getPk_brand());
				user.put("refcode", vo.getVbrandcode());
				user.put("refname", vo.getVbrandname());
				list.add(user);
			}
			
			return list;
		} catch (BusinessException e) {
			logger.error("品牌参照获取错误",e);
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
