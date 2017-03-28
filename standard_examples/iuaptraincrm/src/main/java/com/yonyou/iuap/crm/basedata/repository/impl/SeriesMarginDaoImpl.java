package com.yonyou.iuap.crm.basedata.repository.impl;

import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Repository;

import com.yonyou.iuap.crm.basedata.entity.SeriesMarginExtVO;
import com.yonyou.iuap.crm.basedata.entity.SeriesMarginVO;
import com.yonyou.iuap.persistence.bs.dao.DAOException;
import com.yonyou.iuap.persistence.jdbc.framework.SQLParameter;
import com.yonyou.iuap.crm.basedata.repository.itf.ISeriesMarginDao;
import com.yonyou.iuap.crm.common.base.repository.AppBaseDao;
import com.yonyou.iuap.crm.common.utils.AppTools;

@Repository
public class SeriesMarginDaoImpl implements ISeriesMarginDao {
	@Autowired
	private AppBaseDao baseDao;
	
	/**
	 * 根据条件查找车系毛利信息
	* @author 
	* @date 2016年11月24日
	* @param condition
	* @return
	* @throws DAOException
	* (non-Javadoc)
	* @see com.yonyou.iuap.crm.basedata.repository.itf.ISeriesMarginDao#findListByClauseWithDR(java.lang.String)
	 */
	@Override
	public List<SeriesMarginVO> findListByClauseWithDR(String condition)
			throws DAOException {
		return baseDao.findListByClauseWithDR(SeriesMarginVO.class, condition);
	}

	/**
	 * 保存新增数据
	* @author 
	* @date 2016年11月22日
	* @param vo
	* @return
	* @throws DAOException
	* (non-Javadoc)
	* @see com.yonyou.iuap.crm.basedata.repository.itf.ISeriesMarginDao#saveSeriesMargin(com.yonyou.iuap.crm.basedata.entity.SeriesMarginVO)
	 */
	@Override
	public String saveSeriesMargin(SeriesMarginVO vo)  throws DAOException{
		String pk = AppTools.generatePK();
		vo.setPk_seriesmargin(pk);
		return baseDao.saveWithPK(vo);
	}
	
	@Override
	public String saveSeriesMarginWithPK(SeriesMarginVO vo) throws DAOException {
		return baseDao.saveWithPK(vo);
	}

	/**
	 * 根据pk删除单条数据
	* @author 
	* @date 2016年11月22日
	* @param pk_seriesmargin
	* @throws DAOException
	* (non-Javadoc)
	* @see com.yonyou.iuap.crm.basedata.repository.itf.ISeriesMarginDao#deleteSeriesMarginById(java.lang.String)
	 */
	@Override
	public void deleteSeriesMarginById(String pk_seriesmargin) throws DAOException {
		SeriesMarginVO vo = new SeriesMarginVO();
		vo.setPk_seriesmargin(pk_seriesmargin);
		baseDao.delete(vo);
	}
	
    /**
     * 批量删除
    * @author 
    * @date 2016年11月22日
    * @param entitys
    * @throws DAOException
    * (non-Javadoc)
    * @see com.yonyou.iuap.crm.basedata.repository.itf.ISeriesMarginDao#deleteSeriesMargins(java.util.List)
     */
	@Override
	public void deleteSeriesMargins(List<SeriesMarginVO> entitys) throws DAOException {
		baseDao.batchDelete(entitys);

	}

	/**
	 * 修改
	* @author 
	* @date 2016年11月22日
	* @param vo
	* @throws DAOException
	* (non-Javadoc)
	* @see com.yonyou.iuap.crm.basedata.repository.itf.ISeriesMarginDao#updateSeriesMargin(com.yonyou.iuap.crm.basedata.entity.SeriesMarginVO)
	 */
	@Override
	public void updateSeriesMargin(SeriesMarginVO vo) throws DAOException {
		/*Date date = new Date(System.currentTimeMillis());
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String time = df.format(date);
        vo.setTs(time);*/
		baseDao.update(vo);
	}
	
	/**
	 * 批量修改
	* @author 
	* @date 2016年11月22日
	* @param vo
	* @throws DAOException
	* (non-Javadoc)
	* @see com.yonyou.iuap.crm.basedata.repository.itf.ISeriesMarginDao#updateSeriesMargin(com.yonyou.iuap.crm.basedata.entity.SeriesMarginVO)
	 */
	@Override
	public void updateAll(List<SeriesMarginVO> vos) throws DAOException {
		Date date = new Date(System.currentTimeMillis());
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String time = df.format(date);
        for(int i=0;i<vos.size();i++){
        	SeriesMarginVO vo = vos.get(i);
        	  vo.setTs(time);
        }
		baseDao.batchUpdate(vos);
	}

	/**
	 * 分页查询
	* @author 
	* @date 2016年11月22日
	* @param condition
	* @param sqlParameter
	* @param pageRequest
	* @return
	* @throws DAOException
	* (non-Javadoc)
	* @see com.yonyou.iuap.crm.basedata.repository.itf.ISeriesMarginDao#getSeriesMarginBypage(java.lang.String, com.yonyou.iuap.persistence.jdbc.framework.SQLParameter, org.springframework.data.domain.PageRequest)
	 */
	@Override
	public Page<SeriesMarginExtVO> getSeriesMarginBypage(String condition,
			SQLParameter sqlParameter, PageRequest pageRequest) throws DAOException {
		return baseDao.findBypageWithDR(SeriesMarginExtVO.class, condition,sqlParameter, pageRequest);
	}

	@Override
	public void saveAllSeriesMargins(List<SeriesMarginVO> voList)
			throws DAOException {
		baseDao.batchSaveWithPK(voList);
	}

}
