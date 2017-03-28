package com.yonyou.iuap.service.trans;



import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.yonyou.iuap.entity.quotation.IpuQuotation;
import com.yonyou.iuap.repository.quotation.IpuQuotationMapper;

/**
 * Mybatis带事务的服务示例，应该配置为DataSourceTransactionManager
 */
@Service
public class MybatisTransService {
	
	@Autowired
	private IpuQuotationMapper mapper;
	
	@Autowired
	private JdbcTemplate jt;
	
	@Transactional
	public int complexQuotationOperate(IpuQuotation quotation){
		int result = 0;
		
		IpuQuotation q = new IpuQuotation();
		q.setIpuquotaionid("1b2d669f-a80a-4334-af17-b8333aa08d5f");
		q.setDescription("test_transaction888");
		
		mapper.updateByPrimaryKey(q);
		
		updateByJdbcTemplate();
		
		result = mapper.insert(quotation);
		
		//报错后会回滚
		//Integer.parseInt("test");
		
		return result;
		
	}
	
	private void updateByJdbcTemplate(){
		String sql = "update mgr_function set func_code='test_transaction888' where id = 100 ";
		jt.execute(sql);
	}

}
