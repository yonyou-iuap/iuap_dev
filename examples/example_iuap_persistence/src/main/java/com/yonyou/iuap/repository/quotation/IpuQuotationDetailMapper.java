package com.yonyou.iuap.repository.quotation;


import com.yonyou.iuap.entity.quotation.IpuQuotationDetail;
import com.yonyou.iuap.persistence.mybatis.anotation.MyBatisRepository;

import java.util.List;

@MyBatisRepository
public interface IpuQuotationDetailMapper {

    int deleteByPrimaryKey(String id);

    int insert(IpuQuotationDetail record);

    IpuQuotationDetail selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(IpuQuotationDetail record);

    int updateByPrimaryKey(IpuQuotationDetail record);

    void reomveDetailByQuotationid(String quotationid);

    void saveBatch(List<IpuQuotationDetail> items);
}