package com.yonyou.iuap.crm.ieop.security.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

import com.yonyou.uap.ieop.security.entity.FunctionActivity;
import com.yonyou.uap.ieop.security.entity.UserRole;


/**
* <p>Title: FunctionActivityDao</p>
* <p>Description: </p>
* @project：  security_shiro 
* @Company: Yonyou
* @author zhangyaoc
* @version 1.0   
* @since JDK 1.7.0_67
* @date 2015年6月5日 上午9:04:22 
*/
public interface DefineFunctionActivityDao extends
		PagingAndSortingRepository<FunctionActivity, String>,
		JpaSpecificationExecutor<FunctionActivity> {
	
	@Query("select u from FunctionActivity u where id in (:con)")
	List<FunctionActivity> findByCondition(@Param("con") String[] paramArrayOfString);
	
	List<FunctionActivity> findByActivityUrl(String activityUrl);

	@Query("select u from FunctionActivity u where funcID = ?")
	List<FunctionActivity> findByFuncID(String paramString);
	
	@Query("select u from FunctionActivity u")
	List<FunctionActivity> findActiveFuncActivity();
	
}
