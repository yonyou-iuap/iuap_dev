package com.yonyou.iuap.repository.quotation;


import org.apache.ibatis.annotations.Param;
import org.springframework.data.domain.PageRequest;

import com.yonyou.iuap.entity.quotation.IpuQuotation;
import com.yonyou.iuap.mybatis.type.PageResult;
import com.yonyou.iuap.persistence.mybatis.anotation.MyBatisRepository;
import com.yonyou.iuap.persistence.mybatis.mapper.PageMapper;

@MyBatisRepository
public interface IpuQuotationMapper extends PageMapper<IpuQuotation> {

    int deleteByPrimaryKey(String id);

    int insert(IpuQuotation record);

    IpuQuotation selectByPrimaryKey(String id);

    IpuQuotation selectChildrenByPrimaryKey(String id);

    int updateByPrimaryKeySelective(IpuQuotation record);

    int updateByPrimaryKey(IpuQuotation record);

    PageResult<IpuQuotation> retrievePage(PageRequest pageRequest, @Param("subject") String subject);
}
