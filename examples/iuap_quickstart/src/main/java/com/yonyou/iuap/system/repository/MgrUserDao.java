package com.yonyou.iuap.system.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

import com.yonyou.iuap.system.entity.MgrUser;

public interface MgrUserDao extends PagingAndSortingRepository<MgrUser, Long>,JpaSpecificationExecutor<MgrUser> {
	MgrUser findByLoginName(String loginName);
	
	@Query("select u from MgrUser u where roles in (:con)")
	List<MgrUser> findByCondition(@Param("con")String[] con);
	
	MgrUser findById(long id);
	
	//不推荐
	@Query("select max(user.id)+1 from MgrUser user")
	long getNextId();
	
}
