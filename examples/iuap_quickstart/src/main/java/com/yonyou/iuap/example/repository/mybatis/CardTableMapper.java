package com.yonyou.iuap.example.repository.mybatis;

import com.yonyou.iuap.example.entity.mybatis.CardTable;
import com.yonyou.iuap.mvc.type.SearchParams;
import com.yonyou.iuap.mybatis.type.PageResult;
import com.yonyou.iuap.persistence.mybatis.anotation.MyBatisRepository;

import org.apache.ibatis.annotations.Param;
import org.springframework.data.domain.PageRequest;

import java.util.List;


@MyBatisRepository
public interface CardTableMapper {
	
	/**
	 * 单个增删改查
	 * */
	int insert(CardTable record);
	
	int insertSelective(CardTable record);
	
	int updateByPrimaryKeySelective(CardTable record);

	int updateByPrimaryKey(CardTable record);

	int deleteByPrimaryKey(String pksystem);

	CardTable selectByPrimaryKey(String pksystem);
	
	/**
	 * 根据某一非主键字段查询实体
	 */
	List<CardTable> findByCode(String code);
	
	PageResult<CardTable> selectAllByPage(@Param("page") PageRequest pageRequest, @Param("search") SearchParams searchParams);
    
	/**
	 *批量操作
	 */
    void batchInsert(List<CardTable> addList);

    void batchUpdate(List<CardTable> updateList);

    void batchDeleteByPrimaryKey(List<CardTable> list);
    
}
