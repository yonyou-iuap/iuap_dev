package com.yonyou.iuap.service.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class JdbcTemplateService {
	static final org.slf4j.Logger LOGGER = org.slf4j.LoggerFactory.getLogger(JdbcTemplateService.class); // auto append.

	@Autowired
	private JdbcTemplate jt;

	@Transactional
	public void lockTest() {
		// 锁行
		// String sql = "select * from emall_ad_info where id = '1' for update ";
		// 锁表
		String sql = "select * from emall_ad_info for update ";
		Object o = jt.queryForList(sql);

		String updateSql = "update emall_ad_info set name ='test' where id = '1' ";
		jt.update(updateSql);

		LOGGER.info("{}", o);
	}
}
