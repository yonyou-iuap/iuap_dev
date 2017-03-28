package com.yonyou.iuap.crm.basedata.repository.impl;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Repository;
import org.springside.modules.utils.Clock;

import com.yonyou.iuap.context.InvocationInfoProxy;
import com.yonyou.iuap.crm.basedata.entity.TmSalecutratioVO;
import com.yonyou.iuap.crm.basedata.repository.itf.ITmSalecutratioDao;
import com.yonyou.iuap.crm.common.base.repository.AppBaseDao;
import com.yonyou.iuap.crm.common.utils.AppTools;
import com.yonyou.iuap.persistence.bs.dao.BaseDAO;
import com.yonyou.iuap.persistence.bs.dao.DAOException;
import com.yonyou.iuap.persistence.jdbc.framework.SQLParameter;

/**
 * 
 * TODO 用于标注数据访问组件，即DAO组件
 * 
 * @author 
 * @date 2016年11月22日
 */
@Repository
public class TmSalecutratioDaoImpl implements ITmSalecutratioDao {
	private SimpleDateFormat dataFormat = new SimpleDateFormat("yyyy-MM-dd");
	@Autowired
	@Qualifier("baseDAO") private BaseDAO baseDao;
	@Autowired
	private AppBaseDao appbaseDao;
	private Clock clock = Clock.DEFAULT;

	/**
	 * 销售提成系数信息保存操作
	 * 
	 * @author 
	 * @date 2016年11月22日
	 * @param vo
	 * @return
	 * @throws DAOException
	 *             (non-Javadoc)
	 * @see com.yonyou.iuap.crm.basedata.repository.itf.ITmSalecutratioDao#saveSalecutratio(com.yonyou.iuap.crm.basedata.entity.TmSalecutratioVO)
	 */
	@Override
	@Transactional
	public String saveSalecutratio(TmSalecutratioVO vo) throws DAOException {
		String pk = AppTools.generatePK();
		vo.setPk_salecutratio(pk);
		vo.setNmonth(0);
		vo.setCreator(InvocationInfoProxy.getUserid());
		vo.setCreationtime(dataFormat.format(clock.getCurrentDate()));
		vo.setDr(0);
		String msg = "";
		String condition=" nyear = '"+vo.getNyear()+"' "+" and "+" vinvoice = '"+vo.getVinvoice()+"' "+" and "+"visbyd = '"+vo.getVisbyd()+"' ";
	List<TmSalecutratioVO> voList=	appbaseDao.findListByClauseWithDR(TmSalecutratioVO.class, condition);
	if(voList.size()<1){
		appbaseDao.saveWithPK(vo);
		msg = "保存成功！";}
	else{msg = "年度、开票是否包含地补、是否申报地补合并重复，请重新输入！";
	throw new DAOException(msg);
	}
	
	return msg;
	}

	/**
	 * 销售提成系数信息更新操作
	 * 
	 * @author 
	 * @date 2016年11月22日
	 * @param vo
	 * @throws DAOException
	 *             (non-Javadoc)
	 * @see com.yonyou.iuap.crm.basedata.repository.itf.ITmSalecutratioDao#update(com.yonyou.iuap.crm.basedata.entity.TmSalecutratioVO)
	 */
	@Override
	@Transactional
	public void update(TmSalecutratioVO vo) throws DAOException {
		vo.setModifier(InvocationInfoProxy.getUserid());
		vo.setModifiedtime(dataFormat.format(clock.getCurrentDate()));
		appbaseDao.update(vo);

	}

	/**
	 * 销售提成系数信息分页
	 * 
	 * @author 
	 * @date 2016年11月22日
	 * @param condition
	 * @param sqlParameter
	 * @param pageRequest
	 * @return
	 * @throws DAOException
	 *             (non-Javadoc)
	 * @see com.yonyou.iuap.crm.basedata.repository.itf.ITmSalecutratioDao#getSalecutratiosBypage(java.lang.String,
	 *      com.yonyou.iuap.persistence.jdbc.framework.SQLParameter,
	 *      org.springframework.data.domain.PageRequest)
	 */
	@Override
	@Transactional
	public Page<TmSalecutratioVO> getSalecutratiosBypage(String condition,
			SQLParameter sqlParameter, PageRequest pageRequest)
			throws DAOException {
		StringBuffer sqlBuffer = new StringBuffer("dr=0 ");
		sqlBuffer.append(condition);
		return appbaseDao.findBypage(TmSalecutratioVO.class, sqlBuffer.toString(), sqlParameter, pageRequest);
	}

	/**
	 * 刪除销售提成系数信息
	* @author 
	* @date 2016年11月22日
	* @param apks
	* @throws DAOException
	* (non-Javadoc)
	* @see com.yonyou.iuap.crm.basedata.repository.itf.ITmSalecutratioDao#remove(java.lang.String[])
	 */
	@Override
	@Transactional
	public void remove(String[] apks) throws DAOException {
		List<TmSalecutratioVO> list = new ArrayList<TmSalecutratioVO>();
		for (int i = 0; i < apks.length; i++) {
			TmSalecutratioVO vo = new TmSalecutratioVO();
			vo.setPk_salecutratio(apks[i]);
			list.add(vo);
		}
		baseDao.remove(list);
	}

}
