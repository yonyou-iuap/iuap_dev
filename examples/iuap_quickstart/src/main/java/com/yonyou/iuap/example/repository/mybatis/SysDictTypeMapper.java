package com.yonyou.iuap.example.repository.mybatis;

import java.util.List;
import java.util.Map;

import com.yonyou.iuap.mybatis.type.PageResult;

import org.apache.ibatis.annotations.Param;
import org.springframework.data.domain.PageRequest;

import com.yonyou.iuap.example.entity.mybatis.SysDictType;
import com.yonyou.iuap.persistence.mybatis.anotation.MyBatisRepository;


@MyBatisRepository
public interface SysDictTypeMapper {
    int deleteByPrimaryKey(String id);

    int insert(SysDictType record);

    int insertSelective(SysDictType record);

    SysDictType selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(SysDictType record);

    int updateByPrimaryKey(SysDictType record);

    PageResult<SysDictType> selectAllByPage(@Param("page") PageRequest pageRequest,
            @Param("search") Map<String, Object> searchParams);

    void batchInsert(List<SysDictType> addList);

    void batchUpdate(List<SysDictType> updateList);

    void batchDeleteByPrimaryKey(List<SysDictType> list);

    List<SysDictType> findByCode(String dicttypecode);
}
