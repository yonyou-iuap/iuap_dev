package com.yonyou.iuap.crm.subsidy.service.itf;

import java.util.List;
import java.util.Map;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import com.yonyou.iuap.persistence.bs.dao.DAOException;
import com.yonyou.iuap.crm.common.exception.AppBusinessException;
import com.yonyou.iuap.crm.subsidy.entity.CountrysubsidyFundbackVO;
import com.yonyou.iuap.crm.subsidy.entity.CountrysubsidyItemsVO;
import com.yonyou.iuap.crm.subsidy.entity.CountrysubsidyVO;

/**
* <p>国补申报接口：</p>
* @author 
* @created 2016年11月21日 上午11:34:33
* @version 1.0
 */
public interface ICountrysubsidyService {
	/**
	 * 查询国补申请列表
	* TODO description
	* @author 
	* @date 2016年11月21日
	* @param searchParams
	* @param pageRequest
	* @return
	* @throws DAOException
	 */
	public Page<CountrysubsidyVO> getCountrysubsidyBypage(Map<String, Object> searchParams, PageRequest pageRequest) throws AppBusinessException;
	
	/**
	 * 根据查询条条件查询国补信息
	* TODO description
	* @author 
	* @date 2017年1月19日
	* @param searchParams
	* @return
	* @throws AppBusinessException
	 */
	public List<CountrysubsidyVO> getCountrysubsidyByList(Map<String, Object> searchParams) throws AppBusinessException;
	
	/**
	 * 查询国补明细表分页
	* TODO description
	* @author 
	* @date 2016年11月28日
	* @param searchParams
	* @param pageRequest
	* @return
	* @throws AppBusinessException
	 */
	public Page<CountrysubsidyItemsVO> getCountrysubsidyItemBypage(Map<String, Object> searchParams, PageRequest pageRequest) throws AppBusinessException;
	
	/**
	 * 查询国补回款信息分页
	* TODO description
	* @author 
	* @date 2016年11月28日
	* @param searchParams
	* @param pageRequest
	* @return
	* @throws AppBusinessException
	 */
	public Page<CountrysubsidyFundbackVO> getCountrysubsidyFundbackBypage(Map<String, Object> searchParams, PageRequest pageRequest) throws AppBusinessException;
	
	/**
	 * 保存国补申报单
	* TODO description
	* @author 
	* @date 2016年11月21日
	* @param vo
	* @throws AppBusinessException
	 */
	public String saveCountrysbusidy(CountrysubsidyVO vo) throws AppBusinessException;
	
	/**
	 * 根据主键查询主表信息
	* TODO description
	* @author 
	* @date 2016年11月21日
	* @param pk
	* @return
	* @throws AppBusinessException
	 */
	public CountrysubsidyVO getCountrysubsidyWithPk(String pk) throws AppBusinessException;
	
	/**
	 * 修改国补申请单
	* TODO description
	* @author 
	* @date 2016年11月21日
	* @param vo
	* @throws AppBusinessException
	 */
	public void updateCountrysbusidy(CountrysubsidyVO vo) throws AppBusinessException;
	
	/**
	 * 删除国补申请单
	* TODO description
	* @author 
	* @date 2016年11月21日
	* @param vo
	* @throws AppBusinessException
	 */
	public void deleteCountrysbusidy(CountrysubsidyVO vo) throws AppBusinessException;
	
	/**
	 * 批量删除国补申请单信息
	* TODO description
	* @author 
	* @date 2016年11月28日
	* @param entitys
	* @throws AppBusinessException
	 */
	public void deleteBatchCountrysubsidy(List<CountrysubsidyVO> entitys) throws AppBusinessException;
	
	/**
	 * 批量完结国补申报单
	* TODO description
	* @author 
	* @date 2016年12月1日
	* @param entitys
	* @throws AppBusinessException
	 */
	public void finishBatchCountrysubsidy(List<CountrysubsidyVO> entitys) throws AppBusinessException;
	
	/**
	 * 批量撤销完结国补申报单
	* TODO description
	* @author 
	* @date 2016年12月1日
	* @param entitys
	* @throws AppBusinessException
	 */
	public void unFinishBatchCountrysubsidy(List<CountrysubsidyVO> entitys) throws AppBusinessException;
	
	/**
	 * 根据外键查询国补申报明细信息
	* TODO description
	* @author 
	* @date 2016年11月23日
	* @param fk
	* @return
	* @throws AppBusinessException
	 */
	public List<CountrysubsidyItemsVO> queryItemWithFk(String fk) throws AppBusinessException;
	
	/**
	 * 根据条件查询国补申报车辆信息
	* TODO description
	* @author 
	* @date 2016年11月30日
	* @param sqlCondition
	* @return
	* @throws AppBusinessException
	 */
	public List<CountrysubsidyItemsVO> queryItemWithContiditon(String sqlCondition) throws AppBusinessException;
	
	/**
	 * 根据外键查询国补申报的回款信息
	* TODO description
	* @author 
	* @date 2016年11月23日
	* @param fk
	* @return
	* @throws AppBusinessException
	 */
	public List<CountrysubsidyFundbackVO> queryFundbackWithFk(String fk) throws AppBusinessException;
	
	/**
	 * 根据主键删除车辆信息
	* TODO description
	* @author 
	* @date 2016年11月24日
	* @param fk
	* @throws AppBusinessException
	 */
	public void delItemWithPk(String pk) throws AppBusinessException;
	
	/**
	 * 批量删除车辆明细信息
	* TODO description
	* @author 
	* @date 2016年11月24日
	* @param entitys
	* @throws AppBusinessException
	 */
	public void delBatchItem(List<CountrysubsidyItemsVO> entitys) throws AppBusinessException;
	
	/**
	 * 导入国补申报车辆信息
	* TODO description
	* @author 
	* @date 2016年11月30日
	* @param entitys
	* @return
	* @throws AppBusinessException
	 */
	public String importSubsidyItem(List<CountrysubsidyItemsVO> entitys) throws AppBusinessException;

	/**
	 * 导入国补申报车辆的回款信息
	* TODO description
	* @author 
	* @date 2016年11月30日
	* @param entitys
	* @return
	* @throws AppBusinessException
	 */
	public String importSubsidyFundback(List<CountrysubsidyFundbackVO> entitys) throws AppBusinessException;
}
