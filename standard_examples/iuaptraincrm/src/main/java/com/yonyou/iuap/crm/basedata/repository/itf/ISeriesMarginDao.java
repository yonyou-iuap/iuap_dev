package com.yonyou.iuap.crm.basedata.repository.itf;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import com.yonyou.iuap.crm.basedata.entity.SeriesMarginExtVO;
import com.yonyou.iuap.crm.basedata.entity.SeriesMarginVO;
import com.yonyou.iuap.persistence.bs.dao.DAOException;
import com.yonyou.iuap.persistence.jdbc.framework.SQLParameter;


public interface ISeriesMarginDao {
	
    /**
     * 无主键保存车系毛利信息
     * @param vo
     * @return
     * @throws DAOException
     */
    public String saveSeriesMargin(SeriesMarginVO vo)  throws DAOException;
    
    /**	
	 * 有主键保存车系毛利信息
	 * @param vo
	 */
	public String saveSeriesMarginWithPK(SeriesMarginVO vo)  throws DAOException;
	
	/**
	 * 批量保存数据
	* @author 
	* @date 2016年12月1日
	* @param voList
	* @return
	* @throws DAOException
	 */
	public void saveAllSeriesMargins(List<SeriesMarginVO> voList)  throws DAOException;
	
	/**
	 * 根据主键删除车系毛利
	 */
	public void deleteSeriesMarginById(String pk_seriesmargin) throws DAOException;
	
	/**
	 * 批量删除车系毛利信息
	 * @param pk_seriesmargins
	 */
	public void deleteSeriesMargins(List<SeriesMarginVO> entitys) throws DAOException;
	
	/**
	 * 修改车系毛利信息
	 * @param vo
	 * @throws DAOException
	 */
	public void updateSeriesMargin(SeriesMarginVO vo) throws DAOException;
	
	/**
	 * 查询分页
	 * @param condition
	 * @param sqlParameter
	 * @param pageRequest
	 * @return
	 * @throws DAOException
	 */
	public Page<SeriesMarginExtVO> getSeriesMarginBypage(String condition,
			SQLParameter sqlParameter, PageRequest pageRequest) throws DAOException;

	/**
	 * 根据条件查找数据
	* @param condition
	* @return
	* @throws DAOException
	 */
	List<SeriesMarginVO> findListByClauseWithDR(String condition)
			throws DAOException;

	/**
	 * 批量修改
	* @author 
	* @date 2016年12月1日
	* @param vos
	* @throws DAOException
	 */
	public void updateAll(List<SeriesMarginVO> vos) throws DAOException;

 

}
