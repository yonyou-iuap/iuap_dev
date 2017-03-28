package com.yonyou.iuap.crm.common.ref;

import iuap.ref.ref.RefClientPageInfo;
import iuap.ref.sdk.refmodel.model.AbstractGridRefModel;
import iuap.ref.sdk.refmodel.vo.RefViewModelVO;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
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

import com.yonyou.iuap.crm.basedata.entity.TmCountryVO;
import com.yonyou.iuap.persistence.bs.dao.BaseDAO;
import com.yonyou.iuap.persistence.jdbc.framework.processor.MapListProcessor;
import com.yonyou.iuap.persistence.vo.pub.BusinessException;
import com.yonyou.iuap.crm.basedata.service.itf.IProvinceCityService;

@RequestMapping(value="/ref/country")
@Controller
public class CountryRefController extends AbstractGridRefModel implements IRefModelRestEx{
	private final Logger logger = LoggerFactory.getLogger(getClass());
	public CountryRefController() {}
	
	@Autowired 
	@Qualifier("baseDAO") private  BaseDAO dao;
	
	@Autowired private IProvinceCityService  provinceService;
	
	@Override
	public RefViewModelVO getRefModelInfo(@RequestBody RefViewModelVO refViewModel) {
		refViewModel = super.getRefModelInfo(refViewModel);
		refViewModel.setRefName("国家");
		refViewModel.setRefCode("country");
		refViewModel.setRootName("国家");
		refViewModel.setStrHiddenFieldCode(new String[]{"refpk"});
		refViewModel.setStrFieldCode(new String[]{"refcode","refname"});
		refViewModel.setStrFieldName(new String[]{"编码","名称"});
		return refViewModel;
	}
	
	@Override
	public List<Map<String, String>> filterRefJSON(RefViewModelVO arg0) {
		Map<String, Object> searchParams = new HashMap<String, Object>();
		PageRequest pageRequest = new PageRequest(0, 10, new Sort(Direction.ASC, "ts"));
		Page<TmCountryVO> categoryPage = null;
		try {
			categoryPage = provinceService.getCountry(searchParams, pageRequest);
			
			List<Map<String, String>> list = new ArrayList();
			
			List<TmCountryVO> vos = categoryPage.getContent();
			for(TmCountryVO vo : vos){
				HashMap<String, String> provincemap = new HashMap<String, String>();
				provincemap.put("refpk", vo.getPk_country());
				provincemap.put("refcode", vo.getVcountrycode());
				provincemap.put("refname", vo.getVcountryname());
				list.add(provincemap);
			}
			
			return list;
		} catch (BusinessException e) {
			logger.error("国家参照获取错误",e);
			e.printStackTrace();
			return null;
		}
	}

	@Override
	public Map<String, Object> getCommonRefData(RefViewModelVO viewModel) {
		Map<String, Object> result = new HashMap<String, Object>();
		
		Map<String, Object> searchParams = new HashMap<String, Object>();
		RefClientPageInfo refClientPageInfo = viewModel.getRefClientPageInfo();
		String condition =  viewModel.getContent();//获取查询条件
		if(!StringUtils.isEmpty(condition)){
			searchParams.put("condition", condition);
		}
		int currPageIndex = refClientPageInfo.getCurrPageIndex();
		PageRequest pageRequest = new PageRequest(currPageIndex, 10, new Sort(Direction.ASC, "ts"));
		Page<TmCountryVO> categoryPage = null;
		try {
			categoryPage = provinceService.getCountry(searchParams, pageRequest);
			
			int pageCount = categoryPage.getTotalPages();
			int pageSize = categoryPage.getSize();
			refClientPageInfo.setPageCount(pageCount);
			refClientPageInfo.setPageSize(pageSize);
			viewModel.setRefClientPageInfo(refClientPageInfo);

			List<Map<String, String>> list = new ArrayList<Map<String, String>>();
			
			List<TmCountryVO> vos = categoryPage.getContent();
			for(TmCountryVO vo : vos){
				HashMap<String, String> countrymap = new HashMap<String, String>();
				countrymap.put("refpk", vo.getPk_country());
				countrymap.put("refcode", vo.getVcountrycode());
				countrymap.put("refname", vo.getVcountryname());
				list.add(countrymap);
			}
			
			result.put("dataList", list);
			result.put("refViewModel", viewModel);
			return result;
		} catch (BusinessException e) {
			logger.error("国家参照获取错误",e);
			e.printStackTrace();
			return null;
		} catch (Exception e) {
			logger.error("国家参照获取错误",e);
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

	@Override
	public List<Map<String, String>> getIntRefData(String pks) throws Exception {
		String sql = "SELECT pk_country as pk,  `vcountryname`as name FROM tm_country where pk_country in ("+pks+")";
		List<Map<String,String>> results = dao.queryForList(sql, new MapListProcessor());
		return results;
	}

}
