package com.yonyou.iuap.crm.psn.repository.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Repository;

import com.yonyou.iuap.persistence.bs.dao.DAOException;
import com.yonyou.iuap.persistence.jdbc.framework.SQLParameter;
import com.yonyou.iuap.crm.common.base.repository.AppBaseDao;
import com.yonyou.iuap.crm.psn.entity.BdPsndocVO;
import com.yonyou.iuap.crm.psn.repository.itf.IBdPsnDao;

/**
 * 
* TODO description
* @author 
* @date 2016年12月6日
 */
@Repository
public class BdPsnDaoImpl implements IBdPsnDao {
	
	@Autowired
	private AppBaseDao baseDao;
	
	/**
	 * 根据pk获取人员档案
	* @author 
	* @date 2016年12月6日
	* @param pk_psndoc
	* @return
	* @throws DAOException
	* (non-Javadoc)
	* @see com.yonyou.iuap.crm.psn.repository.itf.IBdPsnDao#queryPsnById(java.lang.String)
	 */
	@Override
	public BdPsndocVO queryPsnById(String pk_psndoc) throws DAOException {
		return baseDao.findByIdWithDR(BdPsndocVO.class, pk_psndoc);
	}

	/**
	 * 根据编码获取唯一值
	* @author 
	* @date 2016年12月6日
	* @param psncode
	* @return
	* @throws DAOException
	* (non-Javadoc)
	* @see com.yonyou.iuap.crm.psn.repository.itf.IBdPsnDao#queryPsnByCode(java.lang.String)
	 */
	@Override
	public BdPsndocVO queryPsnByCode(String psncode) throws DAOException {
		String condition = " psncode = " + psncode ;
		List<BdPsndocVO> result = baseDao.findListByClauseWithDR(BdPsndocVO.class, condition);
		return result!=null?result.get(0):null;
	}

	/**
	 * 
	* @author 
	* @date 2016年12月6日
	* @param wherestr
	* @return
	* @throws DAOException
	* (non-Javadoc)
	* @see com.yonyou.iuap.crm.psn.repository.itf.IBdPsnDao#queryPsnListByCondition(java.lang.String)
	 */
	@Override
	public List<BdPsndocVO> queryPsnListByCondition(String wherestr)
			throws DAOException {
		return baseDao.findListByClauseWithDR(BdPsndocVO.class, wherestr);
	}


	/**
	 * 修改人员档案
	* @author 
	* @date 2016年12月6日
	* @param entity
	* @throws DAOException
	* (non-Javadoc)
	* @see com.yonyou.iuap.crm.psn.repository.itf.IBdPsnDao#updatePsn(com.yonyou.iuap.crm.psn.entity.BdPsndocVO)
	 */
	@Override
	public void updatePsn(BdPsndocVO entity) throws DAOException {
		baseDao.update(entity);
	}
	
	@Override
	public void shiftDept(BdPsndocVO entity) throws DAOException{
		baseDao.update(entity, "pk_corp", "pk_dept");
	}

	/**
	 * 保存人员档案信息
	* @author 
	* @date 2016年12月6日
	* @param vo
	* @return
	* @throws DAOException
	* (non-Javadoc)
	* @see com.yonyou.iuap.crm.psn.repository.itf.IBdPsnDao#savePsn(com.yonyou.iuap.crm.psn.entity.BdPsndocVO)
	 */
	@Override
	public String savePsn(BdPsndocVO vo) throws DAOException {
        return baseDao.saveWithPK(vo);
	}

	/**
	 * 删除人员档案信息
	* @author 
	* @date 2016年12月6日
	* @param entity
	* @throws DAOException
	* (non-Javadoc)
	* @see com.yonyou.iuap.crm.psn.repository.itf.IBdPsnDao#deletePsn(com.yonyou.iuap.crm.psn.entity.BdPsndocVO)
	 */
	@Override
	public void deletePsn(BdPsndocVO entity) throws DAOException {
		baseDao.deleteWithDR(entity);
	}

	/**
	 * 这个方法联查了很多表
	* @author 
	* @date 2016年12月6日
	* @param condition
	* @param sqlParameter
	* @param pageRequest
	* @return
	* @throws DAOException
	* (non-Javadoc)
	* @see com.yonyou.iuap.crm.psn.repository.itf.IBdPsnDao#getPsnsBypage(java.lang.String, com.yonyou.iuap.persistence.jdbc.framework.SQLParameter, org.springframework.data.domain.PageRequest)
	 */
	@Override
	public Page<BdPsndocVO> getPsnsBypage(String condition,
			SQLParameter sqlParameter, PageRequest pageRequest) throws DAOException {
		if(sqlParameter.getCountParams()==0)
			sqlParameter = null;
		return baseDao.findBypageWithDR(BdPsndocVO.class, condition, sqlParameter, pageRequest);
	}

	/**
	 * 使用主键方式删除数据
	* @author 
	* @date 2016年12月6日
	* @param pk_psndoc
	* @throws DAOException
	* (non-Javadoc)
	* @see com.yonyou.iuap.crm.psn.repository.itf.IBdPsnDao#deletePsnById(java.lang.String)
	 */
	@Deprecated
	@Override
	public void deletePsnById(String pk_psndoc) throws DAOException {
		baseDao.deleteWithDR(BdPsndocVO.class, pk_psndoc);
	}

	/**
	 * isseal 0:正常，1:封存
	* @author 
	* @date 2016年12月6日
	* @param pk_psndoc
	* @throws DAOException
	* (non-Javadoc)
	* @see com.yonyou.iuap.crm.psn.repository.itf.IBdPsnDao#sealedPsn(java.lang.String)
	 */
	@Deprecated
	@Override
	public void sealedPsn(String pk_psndoc) throws DAOException {
		BdPsndocVO vo = new BdPsndocVO();
		vo.setPk_psndoc(pk_psndoc);
		vo.setPsnseal(1);
		baseDao.update(vo, "psnseal");
	}
	
	/**
	 * isseal 0:正常，1:封存
	 * 缺少ts
	* @author 
	* @date 2016年12月6日
	* @param pk_psndoc
	* @throws DAOException
	* (non-Javadoc)
	* @see com.yonyou.iuap.crm.psn.repository.itf.IBdPsnDao#unSealedPsn(java.lang.String)
	 */
	@Deprecated
	@Override
	public void unSealedPsn(String pk_psndoc) throws DAOException {
		BdPsndocVO vo = new BdPsndocVO();
		vo.setPk_psndoc(pk_psndoc);
		vo.setPsnseal(0);
		baseDao.update(vo, "psnseal");
	}
	
	/**
	 * 批量启用人员档案
	* @author 
	* @date 2016年12月6日
	* @param entity
	* @throws DAOException
	* (non-Javadoc)
	* @see com.yonyou.iuap.crm.psn.repository.itf.IBdPsnDao#startlist(java.util.List)
	 */
	@Override
	public void startlist(List<BdPsndocVO> entity) throws DAOException {
		for(BdPsndocVO vo : entity){
			vo.setPsnseal(0);
		}
		baseDao.batchUpdate(entity, "psnseal");
	}
	
	/**
	 * 批量封存人员档案
	* @author 
	* @date 2016年12月6日
	* @param entity
	* @throws DAOException
	* (non-Javadoc)
	* @see com.yonyou.iuap.crm.psn.repository.itf.IBdPsnDao#stoplist(java.util.List)
	 */
	@Override
	public void stoplist(List<BdPsndocVO> entity) throws DAOException {
		for(BdPsndocVO vo : entity){
			vo.setPsnseal(1);
		}
		baseDao.batchUpdate(entity, "psnseal");
	}
	
	/**
	 * 批量删除人员档案
	* @author 
	* @date 2016年12月6日
	* @param entity
	* @throws DAOException
	* (non-Javadoc)
	* @see com.yonyou.iuap.crm.psn.repository.itf.IBdPsnDao#deletelist(java.util.List)
	 */
	@Override
	public void deletelist(List<BdPsndocVO> entity) throws DAOException {
		baseDao.batchDeleteWithDR(entity);
	}
	
	/**
	 * 修改联系电话
	* @author 
	* @date 2016年12月6日
	* @param pk_psndoc
	* @param psntel
	* @throws DAOException
	* (non-Javadoc)
	* @see com.yonyou.iuap.crm.psn.repository.itf.IBdPsnDao#updatetel(java.lang.String, java.lang.String)
	 */
	@Deprecated
	@Override
	public void updatetel(String pk_psndoc, String psntel)
			throws DAOException {
		BdPsndocVO vo = new BdPsndocVO();
		vo.setPk_psndoc(pk_psndoc);
		vo.setPsntel(psntel);
		baseDao.update(vo, "psntel");
	}
	
	/**
	 * 修改邮件地址
	* @author 
	* @date 2016年12月6日
	* @param pk_psndoc
	* @param mail
	* @throws DAOException
	* (non-Javadoc)
	* @see com.yonyou.iuap.crm.psn.repository.itf.IBdPsnDao#updateemail(java.lang.String, java.lang.String)
	 */
	@Deprecated
	@Override
	public void updateemail(String pk_psndoc, String mail)
			throws DAOException {
		BdPsndocVO vo = new BdPsndocVO();
		vo.setPk_psndoc(pk_psndoc);
		vo.setEmail(mail);
		baseDao.update(vo, "email");
	}

}
