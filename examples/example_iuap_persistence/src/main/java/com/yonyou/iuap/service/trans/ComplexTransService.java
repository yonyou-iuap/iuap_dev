package com.yonyou.iuap.service.trans;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.yonyou.iuap.entity.quotation.IpuQuotation;
import com.yonyou.iuap.persistence.vo.pub.BusinessException;
import com.yonyou.iuap.repository.demo.DemoEntityJpaDao;
import com.yonyou.iuap.repository.quotation.IpuQuotationMapper;

/**
 * mybatis、jdbctemplate、jpa混合使用时候的事务控制示例
 * 
 * <p>注意：不建议混合使用持久化方式</p>
 */
@Service
public class ComplexTransService {
	
	@Autowired
	private IpuQuotationMapper mapper;
	
	@Autowired
	private JdbcTemplate jt;
	
    @Autowired
    private DemoEntityJpaDao dao;
	
	@Transactional(propagation=Propagation.REQUIRES_NEW)
	public int complexQuotationOperate(IpuQuotation quotation){
		int result = 0;
		
		IpuQuotation q = new IpuQuotation();
		q.setIpuquotaionid("1b2d669f-a80a-4334-af17-b8333aa08d5f");
		q.setDescription("test_transaction");
		
		mapper.updateByPrimaryKey(q);
		
		updateByJdbcTemplate();
		
		result = mapper.insert(quotation);
		
		//dao.delete("testsavebyjdbc");
		
		//Integer.parseInt("test");
		
		return result;
		
	}
	
	private void updateByJdbcTemplate(){
		String sql = "update mgr_function set func_code='test_transaction' where id = 100 ";
		jt.execute(sql);
	}
	
	@Transactional
	public void testTransInJdbcTemplate() throws BusinessException{
		String sql = "update mgr_function set func_code='test_transaction' where id = 100 ";
		jt.execute(sql);
		
		throw new BusinessException("test trans");
	}

}
