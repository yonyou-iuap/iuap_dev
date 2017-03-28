/**
 * @author 
 *
 */
package com.yonyou.iuap.crm.basedata.repository.itf;

import javax.transaction.Transactional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import com.yonyou.iuap.crm.basedata.entity.TmSalecutratioVO;
import com.yonyou.iuap.persistence.bs.dao.DAOException;
import com.yonyou.iuap.persistence.jdbc.framework.SQLParameter;

/**
 * DAO事务处理层 TODO description
 * 
 * @author 
 * @date 2016年11月22日
 */
public interface ITmSalecutratioDao {
	/**
	 * 查询所有销售提成系数信息
	 * 
	 * @param condition
	 * @param arg1
	 * @param pageRequest
	 * @return
	 * @throws DAOException
	 */
	public Page<TmSalecutratioVO> getSalecutratiosBypage(String condition,
			SQLParameter arg1, PageRequest pageRequest) throws DAOException;

	/**
	 * 保存销售提成系数信息 TODO description
	 * 
	 * @author 
	 * @date 2016年11月22日
	 * @param vo
	 * @return
	 * @throws DAOException
	 */
	@Transactional
	public String saveSalecutratio(TmSalecutratioVO vo) throws DAOException;

	/**
	 * 更新销售提成系数信息 TODO description
	 * 
	 * @author 
	 * @date 2016年11月22日
	 * @param vo
	 * @throws DAOException
	 */
	@Transactional
	public void update(TmSalecutratioVO vo) throws DAOException;

	/**
	 * 删除销售提成系数信息 TODO description
	 * 
	 * @author 
	 * @date 2016年11月22日
	 * @param apks
	 * @throws DAOException
	 */
	@Transactional
	public void remove(String[] apks) throws DAOException;
}
