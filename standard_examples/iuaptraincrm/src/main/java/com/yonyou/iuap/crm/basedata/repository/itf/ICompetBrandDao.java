package com.yonyou.iuap.crm.basedata.repository.itf;

import com.yonyou.iuap.crm.basedata.entity.TmCompetBrandExtVO;
import com.yonyou.iuap.crm.basedata.entity.TmCompetBrandVO;
import com.yonyou.iuap.crm.basedata.entity.TmCompetbrandSalesVO;
import com.yonyou.iuap.crm.basedata.entity.TmModelVO;
import com.yonyou.iuap.persistence.bs.dao.DAOException;
import com.yonyou.iuap.persistence.jdbc.framework.SQLParameter;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import java.util.List;

/**
 * 竞品信息DAO接口类 定义了竞品信息的查询、更新、发布、撤销、关闭、保存和删除业务的数据访问接口
 *
 * @author 
 * @date Nov 24, 2016
 */
public interface ICompetBrandDao {
	/**
	 * 根据主键查询竞品信息
	 *
	 * @author 
	 * @date Nov 21, 2016
	 * @param pk_competbrand
	 * @return
	 * @throws DAOException
	 */
	TmCompetBrandVO getCompetBrand(String pk_competbrand)
			throws DAOException;

	/**
	 * 根据条件查询竞品信息列表，带分页
	 *
	 * @author 
	 * @date Nov 21, 2016
	 * @param condition
	 * @param arg1
	 * @param pageRequest
	 * @return
	 * @throws DAOException
	 */
	Page<TmCompetBrandVO> getCompetBrandsBypage(String condition,
												SQLParameter arg1, PageRequest pageRequest) throws DAOException;

	/**
	 * 根据条件查询竞品信息列表，带分页
	 *
	 * @author 
	 * @date Nov 28, 2016
	 * @param condition
	 * @param pageRequest
	 * @param sqlParameter
	 * @return
	 * @throws DAOException
	 */
	Page<TmCompetBrandExtVO> getCompetBrandsExtBypage(String condition,
													  SQLParameter sqlParameter, PageRequest pageRequest) throws DAOException;

	/**
	 * 根据条件查询竞品信息列表，支持表连接，带分页
	 *
	 * @author 
	 * @date Nov 28, 2016
	 * @param condition
	 * @param pageRequest
	 * @param sql
	 * @param sqlParameter
	 * @return
	 * @throws DAOException
	 */
	Page<TmCompetBrandExtVO> getCompetBrandsExtBypage(String condition,
													  SQLParameter sqlParameter, PageRequest pageRequest,
													  String sql)
			throws DAOException;

	/**
	 * 根据条件查找竞品信息，不带分页
	 *
	 * @author 
	 * @date Nov 21, 2016
	 * @param condition
	 *            查询条件字符串
	 * @return
	 * @throws DAOException
	 */
	List<TmCompetBrandVO> findListByClauseWithDR(String condition)
			throws DAOException;

	/**
	 * 获竞品品牌的销量信息，数据主要来源于商用车上牌信息
	 *
	 * @author 
	 * @date Dec 27, 2016
	 * @param condition
	 * @param sqlParameter
	 * @param pageRequest
	 * @param sql
	 * @return
	 * @throws DAOException
	 */
	Page<TmCompetbrandSalesVO> getCompetBrandSalesVolume(String condition,
														 SQLParameter sqlParameter, PageRequest pageRequest, String sql)
			throws DAOException;

	/**
	 * 保存竞品信息
	 *
	 * @author 
	 * @date Nov 21, 2016
	 * @param entity
	 * @return
	 * @throws DAOException
	 */
	String saveCompetBrand(TmCompetBrandVO entity) throws DAOException;

	/**
	 * 批量保存竞品信息
	 *
	 * @author 
	 * @date Nov 21, 2016
	 * @param entities
	 * @return
	 * @throws DAOException
	 */
	void saveCompetBrand(List<TmCompetBrandVO> entities) throws DAOException;

	/**
	 * 更新竞品信息
	 *
	 * @author 
	 * @date Nov 21, 2016
	 * @param entity
	 * @return
	 * @throws DAOException
	 */
	void updateCompetBrand(TmCompetBrandVO entity) throws DAOException;

	/**
	 * 删除竞品信息，逻辑删除
	 *
	 * @author 
	 * @date Nov 21, 2016
	 * @param entity
	 * @throws DAOException
	 */
	void deleteCompetBrandByTS(TmCompetBrandVO entity)
			throws DAOException;

	/**
	 * 删除竞品信息，物理删除
	 *
	 * @author 
	 * @date Nov 21, 2016
	 * @param entity
	 * @throws DAOException
	 */
	void deleteCompetBrand(TmCompetBrandVO entity) throws DAOException;

	/**
	 * 发布竞品信息
	 *
	 * @author 
	 * @date Nov 21, 2016
	 * @param entity
	 * @throws DAOException
	 */
	void publishCompetBrand(TmCompetBrandVO entity) throws DAOException;

	/**
	 * 撤销已发布的竞品信息
	 *
	 * @author 
	 * @date Nov 21, 2016
	 * @param entity
	 * @throws DAOException
	 */
	void revokeCompetBrand(TmCompetBrandVO entity) throws DAOException;

	/**
	 * 关闭竞品信息
	 *
	 * @author 
	 * @date Nov 21, 2016
	 * @param entity
	 * @throws DAOException
	 */
	void closeCompetBrand(TmCompetBrandVO entity) throws DAOException;

	/**
	 * 获取电池电量枚举信息
	 *
	 * @author 
	 * @date Dec 6, 2016
	 * @param condition
	 * @param pageRequest
	 * @param sql
	 * @param sqlParameter
	 * @throws DAOException
	 */
	Page<TmModelVO> getModelBattery(String condition,
									SQLParameter sqlParameter, PageRequest pageRequest, String sql) throws DAOException;
}
