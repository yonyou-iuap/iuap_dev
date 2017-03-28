package com.yonyou.iuap.crm.enums.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.yonyou.iuap.cache.CacheManager;
import com.yonyou.iuap.crm.common.exception.AppBusinessException;
import com.yonyou.iuap.crm.common.utils.AppInvocationInfoProxy;
import com.yonyou.iuap.crm.enums.entity.AppEnumVO;
import com.yonyou.iuap.crm.enums.repository.itf.IAppEnumDao;
import com.yonyou.iuap.crm.enums.service.itf.IAppEnumService;
import com.yonyou.iuap.persistence.bs.dao.BaseDAO;
import com.yonyou.iuap.persistence.bs.dao.DAOException;

/**
 * 只能通过界面操作修改，才能通知缓存发生变化
 * @author 
 *
 */
@Service
public class AppEnumService implements IAppEnumService{

	@Autowired
	private IAppEnumDao dao;
	
	@Autowired
	@Qualifier("baseDAO") private BaseDAO baseDao;
	
	@Autowired
    private CacheManager cacheManager;//缓存管理器
	
	@Override
	@Transactional
	public List<AppEnumVO> queryByVtype(String vtype) throws DAOException {
		String tenantId = AppInvocationInfoProxy.getTenantid();
		String key = "enum";
		List<AppEnumVO> list = cacheManager.hget(tenantId+"::"+key, vtype);
		if(list!=null && !list.isEmpty()){
			for(AppEnumVO vo : list){
				System.out.println(vo.getVcode() +"-----"+ vo.getVname());
			}
			return list;
		}
		this.setCache(key);
		List<AppEnumVO> listretry = cacheManager.hget(tenantId+"::"+key, vtype);
		return listretry;
	}

	@Override
	public AppEnumVO getEnum(String code) throws DAOException {
		// TODO 自动生成的方法存根
		String sql = "select * from tc_enumcode where vcode='"+code+"'";
		List<AppEnumVO> list = baseDao.queryByClause(AppEnumVO.class, sql);
		if(null!=list&&list.size()>0){
			return list.get(0);
		}else{
			return null;
		}
	}
	
	/**
	 * 初始化缓存
	 * @param key
	 * @throws DAOException 
	 */
	@Transactional
	private void setCache(String key) throws AppBusinessException{
		String tenantId = AppInvocationInfoProxy.getTenantid();
		Map<String, ArrayList<AppEnumVO>> appenum = new HashMap<String,ArrayList<AppEnumVO>>();
		List<AppEnumVO> all = new ArrayList();
		try {
			all = dao.queryAll();
		} catch (DAOException e) {
			// TODO 自动生成的 catch 块
			e.printStackTrace();
		}
		ArrayList<AppEnumVO> enumvos = null;
		for(AppEnumVO vo: all){
			String enumtype = vo.getVtype();
			if(appenum.containsKey(enumtype)){
				enumvos = appenum.get(enumtype);
			}else{
				enumvos = new ArrayList<AppEnumVO>();
				appenum.put(enumtype, enumvos);
			}
			enumvos.add(vo);
		}
		for(String mapkey : appenum.keySet()){
			ArrayList<AppEnumVO> apps = appenum.get(mapkey);
			cacheManager.hset(tenantId+"::"+key, mapkey, apps);
		}
	}
	
	
	
}
