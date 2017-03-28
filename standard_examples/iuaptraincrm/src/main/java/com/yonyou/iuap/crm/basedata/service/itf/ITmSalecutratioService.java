/**
 * 
 */
package com.yonyou.iuap.crm.basedata.service.itf;

import java.util.List;
import java.util.Map;

import javax.transaction.Transactional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import com.yonyou.iuap.crm.basedata.entity.TmSalecutratioVO;
import com.yonyou.iuap.persistence.vo.pub.BusinessException;
import com.yonyou.iuap.crm.common.exception.AppBusinessException;
/**
 * service层业务逻辑层
* TODO description
* @author 
* @date 2016年11月22日
 */
public interface ITmSalecutratioService {
	/**
	 * 加载销售提成系数信息
	* TODO description
	* @author 
	* @date 2016年11月22日
	* @return
	* @throws AppBusinessException
	 */
	public abstract Page<TmSalecutratioVO> getSalecutratiosBypage(Map<String, Object> searchParams, PageRequest pageRequest)
			throws AppBusinessException;
/**
	 * 删除销售提成系数信息
	* TODO description
	* @author 
	* @date 2016年11月22日
	* @param entitys
	* @return
	* @throws AppBusinessException
	 */
	@Transactional
	public abstract Map<String, Object> remove(List<TmSalecutratioVO> entitys) throws AppBusinessException;
	/**
	 * 
	* TODO 保存删除销售提成系数信息
	* @author 
	* @date 2016年11月22日
	* @param entitys
	* @return
	* @throws AppBusinessException
	* @throws BusinessException
	 */
	@Transactional
	public abstract Map<String, Object> save(List<TmSalecutratioVO> entitys)throws AppBusinessException;

}
