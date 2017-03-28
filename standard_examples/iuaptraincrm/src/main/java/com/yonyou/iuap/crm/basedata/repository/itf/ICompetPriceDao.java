package com.yonyou.iuap.crm.basedata.repository.itf;

import com.yonyou.iuap.crm.basedata.entity.TmCompetPriceVO;
import com.yonyou.iuap.persistence.bs.dao.DAOException;
import com.yonyou.iuap.persistence.jdbc.framework.SQLParameter;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import java.util.List;
/**
 * 竞品价格信息DAO接口类
 * 定义了竞品价格信息的查询、保存和删除业务的数据访问接口
 * @author 
 * @date Nov 24, 2016
 */
public interface ICompetPriceDao {

	/**
	 * 根据条件查询竞品价格信息列表
	 * 
	 * @author 
	 * @date Dec 30, 2016
	 * @param condition
	 * @param sqlParameter
	 * @param pageRequest
	 * @return
	 * @throws DAOException
	 */
	public Page<TmCompetPriceVO> getCompetPricesBypage(String condition,
			SQLParameter sqlParameter, PageRequest pageRequest) throws DAOException;

	/**
	 * 保存竞品价格信息
	 * 
	 * @author 
	 * @date Nov 21, 2016
	 * @param entities
	 * @throws DAOException
	 */
	public void saveCompetPrices(List<TmCompetPriceVO> entities)
			throws DAOException;

	/**
	 * 更新竞品价格信息
	 * 
	 * @author 
	 * @date Nov 21, 2016
	 * @param entity
	 * @return
	 * @throws DAOException
	 */
	public void updateCompetPrice(List<TmCompetPriceVO> entity) throws DAOException;

	/**
	 * 删除竞品价格信息，逻辑删除
	 * 
	 * @author 
	 * @date Nov 21, 2016
	 * @param entity
	 * @throws DAOException
	 */
	public void deleteCompetPriceByTS(TmCompetPriceVO entity)
			throws DAOException;

	/**
	 * 删除竞品价格信息，物理删除
	 * 
	 * @author 
	 * @date Nov 21, 2016
	 * @param entities
	 * @throws DAOException
	 */
	public void deleteCompetPrice(List<TmCompetPriceVO> entities) throws DAOException;
}
