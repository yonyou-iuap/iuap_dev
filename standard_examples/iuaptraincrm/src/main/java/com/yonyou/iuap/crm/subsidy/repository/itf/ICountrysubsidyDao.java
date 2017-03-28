package com.yonyou.iuap.crm.subsidy.repository.itf;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import com.yonyou.iuap.persistence.bs.dao.DAOException;
import com.yonyou.iuap.persistence.jdbc.framework.SQLParameter;
import com.yonyou.iuap.crm.subsidy.entity.CountrysubsidyFundbackVO;
import com.yonyou.iuap.crm.subsidy.entity.CountrysubsidyItemsVO;
import com.yonyou.iuap.crm.subsidy.entity.CountrysubsidyVO;

/**
 * 
* <p>国补申请接口：</p>
* @author 
* @created 2016年11月21日 上午11:27:11
* @version 1.0
 */
public interface ICountrysubsidyDao {
	/**
	 * 
	* TODO 国补分页查询
	* @author 
	* @date 2016年11月21日
	* @param condition
	* @param sqlParameter
	* @param pageRequest
	* @return
	* @throws DAOException
	 */
	public Page<CountrysubsidyVO> getCountrysubsidyBypages(String condition,
			SQLParameter sqlParameter, PageRequest pageRequest) throws DAOException;
	
	/**
	 * 根据条件查询国补信息
	* TODO description
	* @author 
	* @date 2017年1月19日
	* @param condition
	* @param sqlParameter
	* @return
	* @throws DAOException
	 */
	public List<CountrysubsidyVO> getCountrysubsidyByList(String condition,SQLParameter sqlParameter) throws DAOException;
	
	/**
	 * 查询国补明细表分页
	* TODO description
	* @author 
	* @date 2016年11月28日
	* @param condition
	* @param sqlParameter
	* @param pageRequest
	* @return
	* @throws DAOException
	 */
	public Page<CountrysubsidyItemsVO> getCountrysubsidyItemBypages(String condition,
			SQLParameter sqlParameter, PageRequest pageRequest) throws DAOException;
	
	/**
	 * 查询国补回款信息 分页
	* TODO description
	* @author 
	* @date 2016年11月28日
	* @param condition
	* @param sqlParameter
	* @param pageRequest
	* @return
	* @throws DAOException
	 */
	public Page<CountrysubsidyFundbackVO> getCountrysubsidyFundbackBypages(String condition,
			SQLParameter sqlParameter, PageRequest pageRequest) throws DAOException;
	
	/**
	 * 保存国补主表
	* TODO description
	* @author 
	* @date 2016年11月21日
	* @param vo
	* @return
	* @throws DAOException
	 */
	public String save(CountrysubsidyVO vo) throws DAOException;
	
	/**
	 * 保存国补明细表信息
	* TODO description
	* @author 
	* @date 2016年11月21日
	* @param entitys
	* @throws DAOException
	 */
	public void saveItemBatch(List<CountrysubsidyItemsVO> entitys) throws DAOException;
	
	/**
	 * 保存国补回款信息
	* TODO description
	* @author 
	* @date 2016年11月21日
	* @param entitys
	* @throws DAOException
	 */
	public void saveItemFundbackBatch(List<CountrysubsidyFundbackVO> entitys) throws DAOException;
	
	/**
	 * 修改国补主表信息
	* TODO description
	* @author 
	* @date 2016年11月21日
	* @param vo
	* @throws DAOException
	 */
	public void modify(CountrysubsidyVO vo) throws DAOException;
	
	/**
	 * 批量修改主表信息
	* TODO description
	* @author 
	* @date 2016年12月1日
	* @param entitys
	* @throws DAOException
	 */
	public void modifyBatch(List<CountrysubsidyVO> entitys) throws DAOException;
	
	/**
	 * 修改国补明细表数据
	* TODO description
	* @author 
	* @date 2016年11月21日
	* @param entitys
	* @throws DAOException
	 */
	public void modifyItemBatch(List<CountrysubsidyItemsVO> entitys) throws DAOException;
	
	/**
	 * 修改国补回款信息
	* TODO description
	* @author 
	* @date 2016年11月21日
	* @param entitys
	* @throws DAOException
	 */
	public void modifyItemFundbackBatch(List<CountrysubsidyFundbackVO> entitys) throws DAOException;
	
	/**
	 * 删除国补明细表信息
	* TODO description
	* @author 
	* @date 2016年11月21日
	* @param entitys
	* @throws DAOException
	 */
	public void deleteItemBatch(List<CountrysubsidyItemsVO> entitys) throws DAOException;
	
	/**
	 * 根据主键删除车辆信息
	* TODO description
	* @author 
	* @date 2016年11月24日
	* @param pk
	* @throws DAOException
	 */
	public void deleteItemWithPk(String pk) throws DAOException;
	
	/**
	 * 删除国补单据
	* TODO description
	* @author 
	* @date 2016年11月21日
	* @param vo
	* @throws DAOException
	 */
	public void delete(CountrysubsidyVO vo) throws DAOException;
	
	/**
	 * 批量删除国补申请单信息
	* TODO description
	* @author 
	* @date 2016年11月28日
	* @param entitys
	* @throws DAOException
	 */
	public void deleteBatch(List<CountrysubsidyVO> entitys) throws DAOException;
	
	/**
	 * 根据主键查询主表信息
	* TODO description
	* @author 
	* @date 2016年11月21日
	* @param pk
	* @return
	* @throws DAOException
	 */
	public CountrysubsidyVO queryMain(String pk) throws DAOException;
	
	/**
	 * 根据外键查询国补明细信息
	* TODO description
	* @author 
	* @date 2016年11月21日
	* @param fk
	* @return
	* @throws DAOException
	 */
	public List<CountrysubsidyItemsVO> queryItem(String fk) throws DAOException;
	
	/**
	 * 根据外键查询国补回款信息
	* TODO description
	* @author 
	* @date 2016年11月21日
	* @param fk
	* @return
	* @throws DAOException
	 */
	public List<CountrysubsidyFundbackVO> queryFundback(String fk) throws DAOException;
	
	/**
	 * 根据查询条件查询车辆信息
	* TODO description
	* @author 
	* @date 2016年11月30日
	* @param contiditon
	* @return
	* @throws DAOException
	 */
	public List<CountrysubsidyItemsVO> queryItemWithContiditon(String contiditon) throws DAOException;
	
	/**
	 * 根据外键删除申报车辆信息
	* TODO description
	* @author 
	* @date 2016年12月2日
	* @param fk
	* @throws DAOException
	 */
	public void deleteItemWithFk(String fk) throws DAOException;
	
	/**
	 * 根据外键删除申报回款信息
	* TODO description
	* @author 
	* @date 2016年12月2日
	* @param fk
	* @throws DAOException
	 */
	public void deleteFundbackWithFk(String fk) throws DAOException;
}
