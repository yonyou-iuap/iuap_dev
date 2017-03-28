package com.yonyou.iuap.crm.basedata.repository.impl;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Repository;

import com.yonyou.iuap.crm.basedata.entity.TmCompetBrandVO;
import com.yonyou.iuap.crm.basedata.entity.TmModelExtVO;
import com.yonyou.iuap.crm.basedata.entity.TmModelVO;
import com.yonyou.iuap.persistence.bs.dao.DAOException;
import com.yonyou.iuap.persistence.jdbc.framework.SQLParameter;
import com.yonyou.iuap.persistence.jdbc.framework.processor.BeanListProcessor;
import com.yonyou.iuap.crm.basedata.repository.itf.ITmModelDao;
import com.yonyou.iuap.crm.common.base.repository.AppBaseDao;
import com.yonyou.iuap.crm.common.utils.AppInvocationInfoProxy;
import com.yonyou.iuap.crm.common.utils.AppTools;
import com.yonyou.iuap.crm.common.utils.DictCode;
@Repository
public class TmModelDaoImpl implements ITmModelDao {
	
	@Autowired
	private AppBaseDao appBaseDao;
	
	SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:MM:ss");

	@Override
	public TmModelVO queryByPK(String pk) throws DAOException {
		return appBaseDao.findByIdWithDR(TmModelVO.class, pk);
	}

	@Override
	public Page<TmModelExtVO> queryPage(String condition,SQLParameter sqlParameter,
			PageRequest pageRequest) throws DAOException {
		String sql = "SELECT "
				+ "model.* ,"
				+ "series.vseriesname AS seriesname, "
				+ "vehicle.vclassname AS vehicelname, "
				+ "brand.vbrandname AS brandname "
				+ "FROM tm_model model "
				+ "LEFT JOIN tm_series series ON (series.pk_series = model.pk_series AND series.dr = 0) "
				+ "LEFT JOIN tm_vehicleclassify vehicle ON (vehicle.pk_vehicleclassify = series.pk_vehicleclassify AND vehicle.dr =0) "
				+ "LEFT JOIN tm_brand brand ON (brand.pk_brand =vehicle.pk_brand AND brand.dr = 0) "
				+ "WHERE model.dr = 0 ";
		return appBaseDao.findBypageWithJoin(TmModelExtVO.class, condition, sqlParameter, pageRequest, sql);
	}

	@Override
	public String save(TmModelVO vo) throws DAOException {
		if(vo.getPk_model()==null){
			vo.setPk_model(AppTools.generatePK());
			vo.setVusestatus(DictCode.ALREADY_START_STATUS);
			//车型编码设置车型名称+车辆型号
			vo.setVmodelcode(vo.getVmodelname()+"+"+vo.getVannouncenum());
			vo.setCreationtime(df.format(new Date()));
			vo.setCreator(AppInvocationInfoProxy.getPk_User());
			return appBaseDao.saveWithPK(vo);
		}else{
			vo.setModifiedtime(df.format(new Date()));
			vo.setVmodelcode(vo.getVmodelname()+"+"+vo.getVannouncenum());
			vo.setModifier(AppInvocationInfoProxy.getPk_User());
			appBaseDao.update(vo);
			return vo.getPk_model();
		}
		
	}

	@Override
	public void remove(TmModelVO vo) throws DAOException {
		appBaseDao.deleteWithDR(vo);
		
	}

	@Override
	public void remove(List<TmModelVO> vos) throws DAOException {
		appBaseDao.batchDeleteWithDR(vos);
		
	}

	/**
	 * 根据车型查找竞品信息
	 */
	@Override
	public List<TmCompetBrandVO> findTmCompetByModel(TmModelVO vo)
			throws DAOException {
		
		String sql = "SELECT *  FROM tm_competbrand competbrand WHERE competbrand.pk_competbrand IN (SELECT competrelat.pk_competbrand FROM tm_competrelation competrelat WHERE competrelat.pk_model = '"+vo.getPk_model()+ "' AND competrelat.dr =0) ";
		return appBaseDao.findForList(sql, new BeanListProcessor(TmCompetBrandVO.class));
		
	}

	@Override
	public List<TmModelVO> findListByClauseWithDR(String condition) throws DAOException {
		return appBaseDao.findListByClauseWithDR(TmModelVO.class, condition); 
	}
	@Override
	public List<Map<String, Object>> queryModelInfo(String pk_series)
			throws DAOException {
		String sql="select * from tm_model where pk_series='"+pk_series+"'";
		// TODO 自动生成的方法存根
		return appBaseDao.queryList(sql);
	}


}
