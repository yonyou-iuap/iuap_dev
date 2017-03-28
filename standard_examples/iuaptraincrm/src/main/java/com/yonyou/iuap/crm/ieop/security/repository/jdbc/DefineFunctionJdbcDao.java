package com.yonyou.iuap.crm.ieop.security.repository.jdbc;

import java.util.List;

import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.stereotype.Repository;

import com.yonyou.uap.ieop.security.entity.ExtFunction;
import com.yonyou.uap.ieop.security.repository.base.BaseJdbcDao;

/**
 * <p>
 * Title: FunctionJdbcDao
 * </p>
 * <p>
 * Description:
 * </p>
 * 
 * @project： security_shiro
 * @Company: Yonyou
 * @author zhangyaoc
 * @version 1.0
 * @since JDK 1.7.0_67
 * @date 2015年5月29日 下午2:53:09
 */
@Repository
public class DefineFunctionJdbcDao extends BaseJdbcDao<ExtFunction> {

	/**
	 * 结合岗位查询该用户分配的功能，用来构建菜单
	 * @param userId
	 * @return
	 */
	public List<ExtFunction> findAllFuncsByUserId(String userId) {
		String sql = "(select f.* from ieop_function f,ieop_role_permission p,ieop_role_position a,ieop_user_position b,ieop_position d where f.id=p.permission_id and p.role_id=a.role_id and a.position_id=b.position_id and b.user_id=?  and p.permission_type='func' and f.isactive='Y' and a.position_id=d.id and d.position_status='10051001') union (select * from ieop_function fc where fc.iscontrol='N' and fc.isactive='Y')";
		List<ExtFunction> result = (List<ExtFunction>) this.getJdbcTemplate()
				.query(sql, new Object[] { userId },
						BeanPropertyRowMapper.newInstance(ExtFunction.class));
		return result;
	}
	
}
