package com.yonyou.iuap.crm.basedata.repository.itf;

import java.util.List;
import java.util.Map;

import javax.transaction.Transactional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import com.yonyou.iuap.crm.basedata.entity.TmCompetBrandVO;
import com.yonyou.iuap.crm.basedata.entity.TmModelExtVO;
import com.yonyou.iuap.crm.basedata.entity.TmModelVO;
import com.yonyou.iuap.persistence.bs.dao.DAOException;
import com.yonyou.iuap.persistence.jdbc.framework.SQLParameter;


/**
* TODO 车型管理dao
* @author 
* @date 2016年11月23日
*/
	
public interface ITmModelDao {
	
	
	/**
	* TODO 根据主键查询
	* @author 
	* @date 2016年11月23日
	* @param pk
	* @return
	* @throws DAOException
	*/
		
	public TmModelVO queryByPK(String pk) throws DAOException;
	
	
	/**
	* TODO 获取分页列表
	* @author name
	 * @param sqlParameter 
	* @date 2016年11月21日
	* @param searchParams
	* @param pageRequest
	* @return
	* @throws DAOException
	*/
		
	public Page<TmModelExtVO> queryPage(String condition,SQLParameter sqlParameter, PageRequest pageRequest) throws DAOException;
	
	
	/**
	* TODO 保存
	* @author 
	* @date 2016年11月23日
	* @param vo
	* @return
	* @throws DAOException
	*/
		
	@Transactional
	public String save(TmModelVO vo) throws DAOException;
	
	
	/**
	* TODO 逻辑删除
	* @author name
	* @date 2016年11月21日
	* @param vo
	* @throws DAOException
	*/
		
	@Transactional
	public void remove(TmModelVO vo) throws DAOException;
	
	
	
	/**
	* TODO 逻辑批量删除
	* @author name
	* @date 2016年11月21日
	* @param vos
	* @throws DAOException
	*/
		
	@Transactional
	public void remove(List<TmModelVO> vos) throws DAOException ;
	
	
	/**
	* TODO 获取车型的竞品信息
	* @author name
	* @date 2016年11月21日
	* @param vo
	* @return
	* @throws DAOException
	*/
		
	public List<TmCompetBrandVO> findTmCompetByModel(TmModelVO vo) throws DAOException;
	
	
	/**
	* TODO 根据条件查询
	* @author 
	* @date 2016年11月23日
	* @param condition
	* @return
	* @throws DAOException
	*/
		
	public List<TmModelVO> findListByClauseWithDR(String condition) throws DAOException;

	/**
	* TODO 根据车系PK查询对应的车型
	* @param pk_series
	* @return
	* @throws DAOException
	*/
	public List<Map<String, Object>> queryModelInfo(String pk_series) throws DAOException;
	

}
