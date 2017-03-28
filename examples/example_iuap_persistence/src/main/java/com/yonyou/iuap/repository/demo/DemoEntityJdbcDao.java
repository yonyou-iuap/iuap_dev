package com.yonyou.iuap.repository.demo;

import java.sql.SQLException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.yonyou.iuap.entity.demo.DemoEntity;

@Repository
public class DemoEntityJdbcDao {
	
	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	public void updateDemo() throws SQLException {
		
		String sql = "update mgr_function set func_code='test_jdbctemplate' where id = 100 ";
		
		jdbcTemplate.execute(sql);
		
	}
	
	public DemoEntity queryById(String id) throws SQLException {
		DemoEntity result = null;
		
		String sql = "select * from example_demo where id = ? ";
		
		Object o = jdbcTemplate.queryForObject(sql, new Object[]{id}, new BeanPropertyRowMapper(DemoEntity.class));
		if(o!=null){
			result = (DemoEntity)o;
		}
		
		return result;
		
	}
	

}
