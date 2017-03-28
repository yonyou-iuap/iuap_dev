package com.yonyou.iuap.crm.basedata.repository.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Repository;

import com.yonyou.iuap.crm.basedata.entity.TmComBrandVO;
import com.yonyou.iuap.persistence.bs.dao.DAOException;
import com.yonyou.iuap.persistence.jdbc.framework.SQLParameter;
import com.yonyou.iuap.persistence.jdbc.framework.processor.BeanListProcessor;
import com.yonyou.iuap.crm.basedata.repository.itf.ITmComBrandDao;
import com.yonyou.iuap.crm.common.base.repository.AppBaseDao;

@Repository
public class TmComBrandDaoImpl implements ITmComBrandDao {

	@Autowired
	private AppBaseDao baseDao;		// TODO -> AppBaseDAO
	
	@Override
	public Page<TmComBrandVO> getTmBrandsBypage(String condition,
			SQLParameter sqlParameter, PageRequest pageRequest) throws DAOException {
		return baseDao.findBypageWithDR(TmComBrandVO.class, condition, sqlParameter, pageRequest);
	}

	@Override
	public TmComBrandVO getTmComBrandVO(String id) throws DAOException {
		return baseDao.findByIdWithDR(TmComBrandVO.class, id);
	}

	@Override
	public String saveTmComBrandVO(TmComBrandVO entity) throws DAOException {
		// TODO 自动生成的方法存根
		baseDao.saveWithPK(entity);
		return entity.getPk_combrand();
	}

	@Override
	public void updateTmComBrandVO(TmComBrandVO entity) throws DAOException {
		// TODO 自动生成的方法存根
		baseDao.update(entity);
	}

	@Override
	public void deleteTmComBrandVO(TmComBrandVO entity) throws DAOException {
		// TODO 自动生成的方法存根
		baseDao.delete(entity);
	}

	@Override
	public void deleteById(String id) throws DAOException {
		// TODO 自动生成的方法存根
		TmComBrandVO entity = new TmComBrandVO();
		entity.setPk_combrand(id);
		baseDao.delete(entity);
	}

	@Override
	public boolean check(String column, String value) throws DAOException {
		String sql = column + " = '" + value + "'";
		if(baseDao.findListByClauseWithDR(TmComBrandVO.class, sql).isEmpty()){
			return false;
		}
		else{
			return true;
		}
	}

	@Override
	public List<TmComBrandVO> findAllBrands() throws DAOException {
		StringBuffer sql = new StringBuffer();
		sql.append(
				"SELECT tc.pk_combrand, tc.vcbrandname ")
				.append("FROM tm_combrand AS tc ")
				.append("WHERE tc.dr=0");
		BeanListProcessor beanListProcessor = new BeanListProcessor(
				TmComBrandVO.class);
		return baseDao.findForList(sql.toString(),
				beanListProcessor);
	}

}
