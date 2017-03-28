package com.yonyou.iuap.crm.enums.repository.itf;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import com.yonyou.iuap.persistence.bs.dao.DAOException;
import com.yonyou.iuap.persistence.jdbc.framework.SQLParameter;
import com.yonyou.iuap.crm.enums.entity.AppEnumVO;

/**
 * 
 * @author 
 *
 */
public interface IAppEnumDao {

	public abstract void save(AppEnumVO enumvo) throws DAOException;
	
	public abstract void update(AppEnumVO enumvo) throws DAOException;
	
	public abstract void delete(AppEnumVO enumvo) throws DAOException;
	
	public abstract List<AppEnumVO> queryAll() throws DAOException;
	
	public Page<AppEnumVO> queryPage(String condition, SQLParameter arg1,
			PageRequest pageRequest) throws DAOException;
}
