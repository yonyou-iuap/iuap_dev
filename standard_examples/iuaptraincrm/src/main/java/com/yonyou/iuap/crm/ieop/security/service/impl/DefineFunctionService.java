package com.yonyou.iuap.crm.ieop.security.service.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springside.modules.persistence.DynamicSpecifications;
import org.springside.modules.persistence.SearchFilter;
import org.springside.modules.persistence.SearchFilter.Operator;

import com.google.common.collect.Maps;
import com.yonyou.iuap.crm.common.exception.AppBusinessException;
import com.yonyou.iuap.crm.ieop.security.repository.jdbc.DefineFunctionJdbcDao;
import com.yonyou.iuap.crm.ieop.security.service.IDefineFunctionService;
import com.yonyou.iuap.persistence.vo.pub.BusinessException;
import com.yonyou.uap.ieop.security.entity.ExtFunction;
import com.yonyou.uap.ieop.security.repository.ExtFunctionDao;
import com.yonyou.uap.ieop.security.service.impl.BaseServiceImpl;

/**
 * <p>
 * Title: ExtFunctionService
 * </p>
 * <p>
 * Description:
 * </p>
 * 
 * @project： security_shiro
 * @Company: Yonyou
 * @author zhangyaoc
 * @version 1.0
 * @since JDK 1.7.0_67
 * @date May 29, 2015 3:14:42 PM
 */
@Service
public class DefineFunctionService extends BaseServiceImpl<ExtFunction, String>
		implements IDefineFunctionService {

	private static final Logger logger = LoggerFactory.getLogger(DefineFunctionService.class);
	
	public DefineFunctionService() {
	}

	@Autowired
	protected ExtFunctionDao jpaRepository;

	@Autowired
	protected DefineFunctionJdbcDao jdbcDao;
	
	//-----------参照功能-------
	/*
	 * （非 Javadoc）
	 * 
	 * @see
	 * com.yonyou.security.service.impl.IMgrExtFunctionService#getDemoPage(java
	 * .util.Map, org.springframework.data.domain.PageRequest)
	 */

	public Page<ExtFunction> getDemoPage(Map<String, Object> searchParams,
			PageRequest pageRequest) {
		Specification<ExtFunction> spec = buildSpecification(searchParams);
		return jpaRepository.findAll(spec, pageRequest);
	}
	
	/**
	 * 功能参照查询
	* TODO description
	* @author 
	* @date 2016年12月9日
	* @param searchParams
	* @param pageRequest
	* @return
	 */
	public Page<ExtFunction> getDemoPageForRef(Map<String, Object> searchParams,
			PageRequest pageRequest) throws AppBusinessException {
//		Map<String, SearchFilter> filters = SearchFilter.parse(searchParams);
		
		Map<String, SearchFilter> filters = Maps.newHashMap();

		for (Entry<String, Object> entry : searchParams.entrySet()) {
			// 过滤掉空值
			String key = entry.getKey();
			Object value = entry.getValue();
			if (StringUtils.isBlank((String) value)) {
				continue;
			}

			// 拆分operator与filedAttribute
			String[] names = StringUtils.split(key, "&@&@");
			if (names.length != 2) {
				throw new IllegalArgumentException(key + " is not a valid search filter name");
			}
			String filedName = names[1];
			Operator operator = Operator.valueOf(names[0]);

			// 创建searchFilter
			SearchFilter filter = new SearchFilter(filedName, operator, value);
			filters.put(key, filter);
		}
		Specification<ExtFunction> spec = DynamicSpecifications.bySearchFilter(
				filters.values(), ExtFunction.class);
		
		return jpaRepository.findAll(spec, pageRequest);
	}
	
	/*
	 * （非 Javadoc）
	 * 
	 * @see
	 * com.yonyou.security.service.impl.IMgrExtFunctionService#buildSpecification
	 * (java.util.Map)
	 */

	public Specification<ExtFunction> buildSpecification(
			Map<String, Object> searchParams) {
		Map<String, SearchFilter> filters = SearchFilter.parse(searchParams);
		Specification<ExtFunction> spec = DynamicSpecifications.bySearchFilter(
				filters.values(), ExtFunction.class);
		return spec;
	}
	//----------功能参照--------
	
	/*
	 * （非 Javadoc）
	 * 
	 * @see com.yonyou.security.service.impl.IMgrExtFunctionService#getFuncRoot()
	 */

	public ExtFunction getFuncRoot() throws BusinessException {
		Map<String, Object> searchParams = new HashMap<String, Object>();
		searchParams.put(Operator.EQ + "_isactive", "Y");
		Specification<ExtFunction> spec = buildSpecification(searchParams);
		List<ExtFunction> allFunc = jpaRepository.findAll(spec);
		ExtFunction root = null;
		if(allFunc != null && !allFunc.isEmpty()){
			root = getRootFunc(allFunc);
			if(root != null)
				initTree(root, allFunc);
		}else{
			throw new BusinessException("还没有进行功能注册");
		}
		
		return root;
	}
	
	/*
	 * （非 Javadoc）
	 * 
	 * @see
	 * com.yonyou.security.service.impl.IMgrExtFunctionService#getFuncRootByUser
	 * (long)
	 */

	public ExtFunction getFuncRootByUser(String userId) throws AppBusinessException {
		List<ExtFunction> allFunc = jdbcDao.findAllFuncsByUserId(userId);
		ExtFunction root = null;
		if(allFunc != null && !allFunc.isEmpty()){
			root = getRootFunc(allFunc);
			if(root != null)
				initTree(root, allFunc);
		}else{
			throw new AppBusinessException("还没有进行功能注册");
		}
		
		return root;
	}

	private void initTree(ExtFunction root, List<ExtFunction> allFunc) {
		List<ExtFunction> childList = getFuncsByParent(root, allFunc);
		root.setChildren(childList);
		for (int i = 0; i < childList.size(); i++) {
			initTree(childList.get(i), allFunc);
		}
	}

	private ExtFunction getRootFunc(List<ExtFunction> allFunc) {
		for (int i = 0; i < allFunc.size(); i++) {
			if ("-1".equals(allFunc.get(i).getParentId())) {
				return allFunc.get(i);
			}
		}
		return null;
	}

	private List<ExtFunction> getFuncsByParent(ExtFunction pFunc,
			List<ExtFunction> allFunc) {
		List<ExtFunction> childrenList = new ArrayList<ExtFunction>();
		for (int i = 0; i < allFunc.size(); i++) {
			if (pFunc.getId().equals(allFunc.get(i).getParentId())) {
				childrenList.add(allFunc.get(i));
			}
		}
		Collections.sort(childrenList,new Comparator<ExtFunction>() {
			@Override
			public int compare(ExtFunction o1, ExtFunction o2) {
				int result = o1.getFuncCode().compareTo(o2.getFuncCode());
				return result;
			}
			
		});
		return childrenList;
	}

	protected ExtFunctionDao getJpaRepository() {
		return jpaRepository;
	}

	protected void setJpaRepository(ExtFunctionDao jpaRepository) {
		this.jpaRepository = jpaRepository;
	}

	public DefineFunctionJdbcDao getJdbcDao() {
		return jdbcDao;
	}

	public void setJdbcDao(DefineFunctionJdbcDao jdbcDao) {
		this.jdbcDao = jdbcDao;
	}
	
}
