package com.yonyou.iuap.example.repository;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.yonyou.iuap.example.entity.GoodJpaDemo;

public interface GoodJpaDemoDao extends PagingAndSortingRepository<GoodJpaDemo, String> , JpaSpecificationExecutor<GoodJpaDemo>{

	/*
	//使用native sql示例
	@Query(value = "select * from good_demo where id = ?1", nativeQuery=true)
	GoodJpaDemo getDemoByNativeSql(String id);
	
	@Modifying
	@Query(value = "delete from good_demo where id = ?1", nativeQuery=true)
	void deleteDemoByIdUseSql(String id);
	*/
}
