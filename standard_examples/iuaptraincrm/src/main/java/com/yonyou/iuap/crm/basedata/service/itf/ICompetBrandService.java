package com.yonyou.iuap.crm.basedata.service.itf;

import com.yonyou.iuap.crm.basedata.entity.TmCompetBrandExtVO;
import com.yonyou.iuap.crm.basedata.entity.TmCompetBrandVO;
import com.yonyou.iuap.crm.basedata.entity.TmCompetbrandSalesVO;
import com.yonyou.iuap.crm.basedata.entity.TmModelVO;
import com.yonyou.iuap.crm.common.exception.AppBusinessException;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import java.util.List;
import java.util.Map;

/**
 * 竞品信息Service接口类 定义了竞品信息的查询、更新、发布、撤销、关闭、保存和删除业务的接口
 * 
 * @author 
 * @date Nov 24, 2016
 */
public interface ICompetBrandService {
	/**
	 * 根据主键查询竞品信息
	 * 
	 * @author 
	 * @date Nov 21, 2016
	 * @param pk_competbrand
	 * @return
	 * @throws AppBusinessException
	 */
	TmCompetBrandVO getCompetBrand(String pk_competbrand)
			throws AppBusinessException;

	/**
	 * 查询竞品信息，带分页
	 * 
	 * @author 
	 * @date Nov 28, 2016
	 * @param searchParams
	 * @param pageRequest
	 * @return
	 * @throws AppBusinessException
	 */
	Page<TmCompetBrandExtVO> getCompetBrandsExtBypage(
			Map<String, Object> searchParams, PageRequest pageRequest)
			throws AppBusinessException;

	/**
	 * 获竞品品牌的销量信息，数据主要来源于商用车上牌信息
	 * 
	 * @author 
	 * @date Dec 27, 2016
	 * @param searchParams
	 * @param pageRequest
	 * @return
	 * @throws AppBusinessException
	 */
	Page<TmCompetbrandSalesVO> getCompetBrandSalesVolume(Map<String, Object> searchParams, PageRequest pageRequest)
			throws AppBusinessException;

	/**
	 * 保存或更新竞品信息
	 * 
	 * @author 
	 * @date Nov 21, 2016
	 * @param entity
	 * @return
	 * @throws AppBusinessException
	 */
	String saveEntity(TmCompetBrandVO entity)
			throws AppBusinessException;
	
	/**
	 * 批量保存或更新竞品信息
	 * 
	 * @author 
	 * @date Nov 21, 2016
	 * @param entities
	 * @return
	 * @throws AppBusinessException
	 */
	void saveEntity(List<TmCompetBrandVO> entities)
			throws AppBusinessException;

	/**
	 * 删除竞品信息，物理删除
	 * 
	 * @author 
	 * @date Nov 21, 2016
	 * @param entities
	 * @throws AppBusinessException
	 */
	void deleteCompetBrand(
			List<? extends TmCompetBrandVO> entities)
			throws AppBusinessException;

	/**
	 * 发布竞品信息
	 * 
	 * @author 
	 * @date Nov 21, 2016
	 * @param entities
	 * @throws AppBusinessException
	 */
	void publishCompetBrand(
			List<? extends TmCompetBrandVO> entities)
			throws AppBusinessException;

	/**
	 * 撤销已发布的竞品信息
	 * 
	 * @author 
	 * @date Nov 21, 2016
	 * @param entities
	 * @throws AppBusinessException
	 */
	void revokeCompetBrand(
			List<? extends TmCompetBrandVO> entities)
			throws AppBusinessException;

	/**
	 * 关闭竞品信息
	 * 
	 * @author 
	 * @date Nov 21, 2016
	 * @param entities
	 * @throws AppBusinessException
	 */
	void closeCompetBrand(
			List<? extends TmCompetBrandVO> entities)
			throws AppBusinessException;

	/**
	 * 枚举车型电量信息
	 * 
	 * @author 
	 * @date Dec 6, 2016
	 * @throws AppBusinessException
	 */
	Page<TmModelVO> enumBattery(Map<String, Object> searchParams, PageRequest pageRequest)
			throws AppBusinessException;
	/**
	 * 获取所有竞品品牌，放到一个Map里
	* @author 
	* @date Dec 23, 2016
	* @return
	* @throws AppBusinessException
	 */
	Map<String,String> getCombrandMap()throws AppBusinessException;
}
