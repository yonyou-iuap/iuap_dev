package com.yonyou.login.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

import com.yonyou.login.entity.User;


/**
 * 实现对用户表mgr_user的管理
 *
 * @author taomk 2016年3月18日 下午4:14:33
 *
 */
public interface UserDao extends PagingAndSortingRepository<User, Long>,JpaSpecificationExecutor<User> {
	User findByLoginName(String loginName);
	
	@Query("select u from User u where roles in (:con)")
	List<User> findByCondition(@Param("con")String[] con);
	
	User findById(long id);
	
	@Query("select max(user.id)+1 from User user")
	long getNextId();

	User findByName(String name);
	
}
