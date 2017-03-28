package com.yonyou.iuap.crm.ieop.security.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.yonyou.iuap.crm.ieop.security.repository.DefineFunctionActivityDao;
import com.yonyou.iuap.crm.ieop.security.service.IDefineFunctionActivityService;
import com.yonyou.uap.ieop.security.entity.ExtFunction;
import com.yonyou.uap.ieop.security.entity.FunctionActivity;
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
public class DefineFunctionActivityService implements IDefineFunctionActivityService {

	@Autowired
	protected DefineFunctionActivityDao jpaRepository;
	
	@Override
	public List<FunctionActivity> getFuncActivityByFuncID(String funcID)
			throws Exception {
		return jpaRepository.findByFuncID(funcID);
	}

	public DefineFunctionActivityDao getJpaRepository() {
		return jpaRepository;
	}

	public void setJpaRepository(DefineFunctionActivityDao jpaRepository) {
		this.jpaRepository = jpaRepository;
	}
	
}
