package com.yonyou.iuap.repository.demo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.yonyou.iuap.entity.demo.DemoEntity;

public interface DemoEntityJpaDao extends PagingAndSortingRepository<DemoEntity, String> , JpaSpecificationExecutor<DemoEntity>{

	DemoEntity findByCode(String code);
	
	@Query("select d from DemoEntity d where code in (:codes)")
	List<DemoEntity> findByConditions(String[] codes);
	
	@Query(value = "select * from example_demo where id = ?1", nativeQuery=true)
	DemoEntity getDemoByNativeSql(String id);
	
	@Modifying
	@Query(value = "delete from example_demo where id = ?1", nativeQuery=true)
	void deleteDemoByIdWithNativeSql(String id);
}