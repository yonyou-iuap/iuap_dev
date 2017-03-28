/**
 * 
 */
package com.yonyou.iuap.crm.basedata.service.impl;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import com.yonyou.iuap.crm.basedata.entity.TmSalecutratioVO;
import com.yonyou.iuap.persistence.bs.dao.DAOException;
import com.yonyou.iuap.persistence.jdbc.framework.SQLParameter;
import com.yonyou.iuap.crm.basedata.repository.itf.ITmSalecutratioDao;
import com.yonyou.iuap.crm.basedata.service.itf.ITmSalecutratioService;
import com.yonyou.iuap.crm.common.exception.AppBusinessException;
import com.yonyou.iuap.crm.common.utils.AppTools;

/**
 * service层
* TODO description
* @author 
* @date 2016年11月22日
 */

//用于标注业务层组件
@Service
public class TmSalecutratioServiceImpl implements ITmSalecutratioService {
	@Autowired
	private ITmSalecutratioDao dao;
/**
 * 销售提成系数信息分页
* @author 
* @date 2016年11月22日
* @param searchParams
* @param pageRequest
* @return
* @throws AppBusinessException
* (non-Javadoc)
* @see com.yonyou.iuap.crm.basedata.service.itf.ITmSalecutratioService#getSalecutratiosBypage(java.util.Map, org.springframework.data.domain.PageRequest)
 */
	@Override
	public Page<TmSalecutratioVO> getSalecutratiosBypage(Map<String, Object> searchParams, PageRequest pageRequest)
			throws AppBusinessException {
		StringBuffer condition = new StringBuffer();
		SQLParameter sqlParameter = new SQLParameter();
		for(String key : searchParams.keySet()){
			String value = searchParams.get(key)==null?"":searchParams.get(key).toString();
			if(null != value && value.length()>0){
				//年度过滤
				if(null != key && "nyear".equals(key)){
					condition.append(" and nyear like '%").append(value).append("%'");
				}
			}
		}
			 try {
				return dao.getSalecutratiosBypage(condition.toString(), sqlParameter, pageRequest);
			} catch (DAOException e) {
				// TODO 自动生成的 catch 块
				e.printStackTrace();
			}
			return null;
		}
	/**
	 * 批量删除删除销售提成系数信息
	* @author 
	* @date 2016年11月22日
	* @param entitys
	* @return
	* @throws DAOException
	* (non-Javadoc)
	* @see com.yonyou.iuap.crm.basedata.service.itf.ITmSalecutratioService#remove(java.util.List)
	 */
	@Override
	@Transactional
	public Map<String, Object> remove(List<TmSalecutratioVO> entitys) throws AppBusinessException {
		Map<String, Object> map  = new HashMap<String,Object>();
		String[] apks = new String[entitys.size()];
		for (int i = 0; i < entitys.size(); i++) {
			TmSalecutratioVO tmsalecutratioVO = entitys.get(i);
			apks[i]=tmsalecutratioVO.getPk_salecutratio();
		}
		try {
			dao.remove(apks);
		} catch (DAOException e) {
			// TODO 自动生成的 catch 块
			e.printStackTrace();
		}
		map.put("flag", AppTools.SUCCESS);
		map.put("msg", "删除成功");
		return map;
	}
/**
 * 删除销售提成系数信息保存与修改
* @author 
* @date 2016年11月22日
* @param entitys
* @return
* @throws DAOException
* (non-Javadoc)
* @see com.yonyou.iuap.crm.basedata.service.itf.ITmSalecutratioService#save(java.util.List)
 */
	@Override
	@Transactional
	public Map<String, Object> save(List<TmSalecutratioVO> entitys)
			throws AppBusinessException {
		Map<String,Object> map=new HashMap<String,Object>();
		for(int i=0;i<entitys.size();i++){
			TmSalecutratioVO tmsalecutratioVO = entitys.get(i);
			if(tmsalecutratioVO.getPk_salecutratio() != null){
				try {
					dao.update(tmsalecutratioVO);
				} catch (DAOException e) {
					// TODO 自动生成的 catch 块
					e.printStackTrace();
					throw new AppBusinessException(e.getMessage());
				}
			}
			else{
				try {
					dao.saveSalecutratio(tmsalecutratioVO);
				} catch (DAOException e) {
					// TODO 自动生成的 catch 块
					e.printStackTrace();
					throw new AppBusinessException(e.getMessage());
				}
			}	
		}
		return map;
		
	}
}
