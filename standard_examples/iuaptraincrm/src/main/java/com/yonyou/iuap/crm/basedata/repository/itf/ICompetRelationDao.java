package com.yonyou.iuap.crm.basedata.repository.itf;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import com.yonyou.iuap.crm.basedata.entity.TmCompetRelationVO;
import com.yonyou.iuap.crm.basedata.entity.TmModelCompetExtVO;
import com.yonyou.iuap.persistence.bs.dao.DAOException;
import com.yonyou.iuap.persistence.jdbc.framework.SQLParameter;

/**
 * 
 * 竞品对比车型信息DAO接口类 定义了竞品-对比关系的保存和删除业务的数据访问接口
 * 
 * @author 
 * @date Dec 2, 2016
 */
public interface ICompetRelationDao {

	/**
	 * 保存竞品-对比关系
	 * 
	 * @author 
	 * @date Nov 21, 2016
	 * @param entities
	 *            查询条件字符串
	 * @throws DAOException
	 */
	public void saveCompetRelation(List<TmCompetRelationVO> entities)
			throws DAOException;

	/**
	 * 删除竞品-对比关系，逻辑删除
	 * 
	 * @author 
	 * @date Dec 2, 2016
	 * @param entitys
	 * @throws DAOException
	 */
	public void deleteCompetRelation(List<TmCompetRelationVO> entities)
			throws DAOException;

	/**
	 * 根据条件查询竞品-对比关系
	 * 
	 * @author 
	 * @date Dec 5, 2016
	 * @param entities
	 * @throws DAOException
	 */
	public List<TmCompetRelationVO> findCompetRelationByCause(String condition)
			throws DAOException;

	/**
	 * 根据条件查询竞品-对比车型信息
	 * 
	 * @author 
	 * @date Dec 23, 2016
	 * @param condition
	 * @param sqlParameter
	 * @param pageRequest
	 * @param sql
	 * @return
	 * @throws DAOException
	 */
	public Page<TmModelCompetExtVO> queryModelExtPage(String condition,
			SQLParameter sqlParameter, PageRequest pageRequest,String sql)
			throws DAOException;

}
