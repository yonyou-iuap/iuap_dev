
/** 
*Copyright 2016 Yonyou Corporation Ltd. All Rights Reserved.
* This software is published under the terms of the Yonyou Software
* License version 1.0, a copy of which has been included with this
* distribution in the LICENSE.txt file.
*
* @Project Name : iuaptraincrm
*
* @File name : VehicleClassifyRefController.java
*
* @author 
*
* @Date : 2016年11月29日
*
----------------------------------------------------------------------------------
*     Date       Who       Version     Comments
* 1. 2016年11月29日    name    1.0
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

import com.yonyou.iuap.crm.basedata.entity.TmVehicleClassifyVO;
import com.yonyou.iuap.crm.basedata.service.itf.ITmVehicleClassifyService;
import com.yonyou.iuap.crm.common.utils.AppTools;
import com.yonyou.iuap.crm.common.utils.DictCode;
import com.yonyou.iuap.persistence.bs.dao.BaseDAO;
import com.yonyou.iuap.persistence.bs.dao.DAOException;
import com.yonyou.iuap.persistence.jdbc.framework.processor.MapListProcessor;
import com.yonyou.iuap.persistence.vo.pub.BusinessException;


/**
 * 车辆分类参照
 * @author 
 * @date 2016年11月29日
 */
@RequestMapping({"/ref/vehicleclassifyref"})
@Controller
public class VehicleClassifyRefController extends AbstractGridRefModel implements IRefModelRestEx {
private final Logger logger = LoggerFactory.getLogger(getClass());
	
	@Autowired
	private ITmVehicleClassifyService tcService;
	
	@Autowired
	@Qualifier("baseDAO") private  BaseDAO dao;

	public VehicleClassifyRefController() {}

	@Override
	public RefViewModelVO getRefModelInfo(@RequestBody RefViewModelVO refViewModel) {
		refViewModel = super.getRefModelInfo(refViewModel);
		refViewModel.setRefName("车辆类别");
		refViewModel.setRefCode("vehicleclassifyref");
		refViewModel.setRootName("");
		refViewModel.setStrHiddenFieldCode(new String[]{"refpk"});
		refViewModel.setStrFieldCode(new String[]{"refcode","refname"});
		refViewModel.setStrFieldName(new String[]{"车辆类别编码","车辆类别名称"});
		return refViewModel;
	}

	@Override
	public Map<String, Object> getCommonRefData(
			@RequestBody RefViewModelVO paramRefViewModelVO) {
		Map<String, Object> result = new HashMap<String, Object>();
		Map<String, Object> searchParams = new HashMap<String, Object>();
		RefClientPageInfo refClientPageInfo = paramRefViewModelVO.getRefClientPageInfo();
		
		String condition =  paramRefViewModelVO.getContent();					//获取查询条件
		searchParams.put("condition", condition);
		int currPageIndex = refClientPageInfo.getCurrPageIndex();
		
		PageRequest pageRequest = new PageRequest(currPageIndex, AppTools.REFPAGESIZE, new Sort(Direction.ASC, "ts"));
		String[] param = paramRefViewModelVO.getPk_val();
		if(param!=null&&param.length>0){
			searchParams.put("pk_brand", param[0].substring(9));
		}
		Page<TmVehicleClassifyVO> categoryPage = null;
		try {
			searchParams.put("vstatus", DictCode.ALREADY_START_STATUS);
			categoryPage = tcService.getTmVehicleClassifysBypage(searchParams, pageRequest);
			int pageCount = categoryPage.getTotalPages();
			int pageSize = categoryPage.getSize();
			refClientPageInfo.setCurrPageIndex(currPageIndex);
			refClientPageInfo.setPageCount(pageCount);
			refClientPageInfo.setPageSize(pageSize);
			paramRefViewModelVO.setRefClientPageInfo(refClientPageInfo);
			
			List<Map<String, String>> list = new ArrayList();
			
			List<TmVehicleClassifyVO> vos = categoryPage.getContent();
			for(TmVehicleClassifyVO vo : vos){
				HashMap<String, String> user = new HashMap();
				user.put("refpk", vo.getPk_vehicleclassify());
				user.put("refcode", vo.getVclasscode());
				user.put("refname", vo.getVclassname());
				list.add(user);
			}
			result.put("dataList", list);
			result.put("refViewModel", paramRefViewModelVO);
			return result;
		} catch (BusinessException e) {
			logger.error("车辆类别参照获取错误",e);
			e.printStackTrace();
			return null;
		}
	}

	@Override
	public List<Map<String,String>> getIntRefData(
			String pks) throws Exception {
		String sql = "select pk_vehicleclassify as pk,vclassname as name from tm_vehicleclassify where pk_vehicleclassify in ("+pks+");";
		List<Map<String,String>> results = dao.queryForList(sql, new MapListProcessor());
		return results;
	}

	@Override
	public List<Map<String, String>> filterRefJSON(@RequestBody RefViewModelVO arg0) {
		Map<String, Object> searchParams = new HashMap<String, Object>();
		PageRequest pageRequest = new PageRequest(0, 100, new Sort(Direction.ASC, "ts"));
		Page<TmVehicleClassifyVO> categoryPage = null;
		try {
			searchParams.put("vstatus", DictCode.ALREADY_START_STATUS);
			categoryPage = tcService.getTmVehicleClassifysBypage(searchParams, pageRequest);
			
			List<Map<String, String>> list = new ArrayList();
			
			List<TmVehicleClassifyVO> vos = categoryPage.getContent();
			for(TmVehicleClassifyVO vo : vos){
				HashMap<String, String> user = new HashMap();
				user.put("refpk", vo.getPk_vehicleclassify());
				user.put("refcode", vo.getVclasscode());
				user.put("refname", vo.getVclassname());
//				user.put("creator", vo.getCreator());
				list.add(user);
			}
			
			return list;
		} catch (BusinessException e) {
			logger.error("车辆类别参照获取错误",e);
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
		String[] pks = paramRefViewModelVO.getPk_val();
		String pk = "";
		List<Map<String, String>> results = new ArrayList();
		try {
			Map<String, String> map = new HashMap();
			if(pks!=null&&pks.length>0) {
				pk = pks[0];
				TmVehicleClassifyVO vo = dao.queryByPK(TmVehicleClassifyVO.class, pk);
				map.put("refpk", null==vo ? "" :vo.getPk_vehicleclassify()); 
				map.put("refcode",null==vo ? "" : vo.getVclasscode()); 
				map.put("refname",null==vo ? "" : vo.getVclassname()); 
				results.add(map);
			}else{
				map.put("refpk", ""); 
				map.put("refcode",""); 
				map.put("refname",""); 
				results.add(map);
			}
		} catch (DAOException e) {
			// TODO 自动生成的 catch 块
			e.printStackTrace();
		}
		return results;
	}

}
