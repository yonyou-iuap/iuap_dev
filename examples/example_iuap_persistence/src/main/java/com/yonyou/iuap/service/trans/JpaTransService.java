package com.yonyou.iuap.service.trans;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.yonyou.iuap.repository.demo.DemoEntityJpaDao;

/**
 * Jpa带事务的服务示例，spring配置JpaTransactionManager
 */
@Service
public class JpaTransService {

    @Autowired
    private DemoEntityJpaDao dao;
    
	@Autowired
	private JdbcTemplate jt;

    @Transactional
    public void deleteById(String id) {
    	
    	dao.delete(id);
    	
    	updateByJdbcTemplate();
    	
    	//报错之后会回滚
    	//Integer.parseInt("test");
    	
    }
    
	private void updateByJdbcTemplate(){
		String sql = "update mgr_function set func_code='test_transaction888' where id = 100 ";
		jt.execute(sql);
	}
}
