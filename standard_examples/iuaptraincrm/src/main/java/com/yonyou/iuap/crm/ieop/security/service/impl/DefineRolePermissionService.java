package com.yonyou.iuap.crm.ieop.security.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.yonyou.iuap.crm.common.exception.AppBusinessException;
import com.yonyou.iuap.crm.common.utils.AppTools;
import com.yonyou.iuap.crm.ieop.security.repository.jdbc.DefineRoleFuncJdbcDao;
import com.yonyou.iuap.crm.ieop.security.service.IDefineRolePermissionService;
import com.yonyou.iuap.persistence.bs.dao.DAOException;
import com.yonyou.uap.ieop.security.entity.ExtFunction;
import com.yonyou.uap.ieop.security.entity.FunctionActivity;
import com.yonyou.uap.ieop.security.entity.RolePermission;
import com.yonyou.uap.ieop.security.repository.RoleFuncDao;
import com.yonyou.uap.ieop.security.service.impl.BaseServiceImpl;

/**
 * 扩展添加了基于funcIds批量保存role角色func功能分配
 * @author fanchj1
 *
 */
@Service
public class DefineRolePermissionService extends BaseServiceImpl<RolePermission,String> implements IDefineRolePermissionService{

	@Autowired
	protected RoleFuncDao jpaRepository;
	
	@Autowired
	private DefineRoleFuncJdbcDao roleFuncdao;
	
	/**
	 * 角色分配功能
	* @author 
	* @date 2016年12月8日
	* @param roleId
	* @param rolecode
	* @param funcId
	* @throws Exception
	* (non-Javadoc)
	* @see com.yonyou.iuap.crm.ieop.security.service.IExtRolePermissionService#createRoleFuncRelation(java.lang.String, java.lang.String, java.lang.String)
	 */
	@Override
	public void createRoleFuncRelation(String roleId, String rolecode,
			String funcId) throws AppBusinessException {
		try{
			if(null != roleId && roleId.length()>0){
				//删除历史信息
				roleFuncdao.deleteBatchRoleFunction(roleId);
				if(null != funcId && funcId.length()>0){
					
					String sql = funcId.replace(",", "','");
					StringBuffer sqlBuf = new StringBuffer("'");
					sqlBuf.append(sql).append("'");
					List<ExtFunction> funcList = roleFuncdao.getFuncListWithCode(sqlBuf.toString());
					Map<String,String> funcMap = new HashMap<String,String>();
					if(null != funcList && funcList.size()>0){
						for(ExtFunction funcBean : funcList){
							funcMap.put(funcBean.getId(), funcBean.getFuncCode());
						}
					}
					
					String[] funcIdArr = funcId.split(",");
					List<RolePermission> rolePerList = new ArrayList<RolePermission>();
					for(String pkFun:funcIdArr){
						if(null != pkFun && pkFun.length()>0){
							RolePermission rp = new RolePermission();
							rp.setId(AppTools.generatePK());
							rp.setRoleId(roleId);
							rp.setRoleCode(rolecode);
							rp.setPermissionId(pkFun);
							rp.setPermissionType("func");
							rp.setPermissionCode(funcMap.get(pkFun));
							rolePerList.add(rp);
						}
					}
					if(null != rolePerList && rolePerList.size()>0){
						jpaRepository.save(rolePerList);
					}
				}
			}
			
		}catch(DAOException e){
			throw new AppBusinessException(e.getMessage());
		}
	}	
	
	@Override
	public List<ExtFunction> queryHasPermissionFunction(String roleid)
			throws Exception {
		// TODO 自动生成的方法存根
		return roleFuncdao.findAllFuncByRoleId(roleid);
	}
	
	/**
	 * 批量保存按钮分配关系
	 * 现有逻辑为先删除后保存
	 */
	@Override
	@Transactional
	public void createActivityPermissionBatch(List<FunctionActivity> datas, String roleId, String rolecode, String funcId)
			throws Exception {
		List<RolePermission> entitys= new ArrayList<RolePermission>();
		List<RolePermission> rps = roleFuncdao.findRolePermActionByRoleIdAndFuncId(roleId, funcId);
		if(!rps.isEmpty()){
			jpaRepository.delete(rps);		
		}
		if(null != datas && datas.size()>0){
			for(FunctionActivity action : datas){
				RolePermission rp = new RolePermission();
				rp.setPermissionId(action.getId());
				rp.setPermissionCode(action.getActivityCode());
				rp.setPermissionType("action");
				rp.setRoleId(roleId);
				rp.setRoleCode(rolecode);
				entitys.add(rp);
			}
			this.beforeBatchSave(entitys);
			jpaRepository.save(entitys);
		}
	}
	
	protected  void beforeSave(RolePermission entity) {
		
		if(entity.getId()==null){
			entity.setId(UUID.randomUUID().toString().replaceAll("-", "").toString());
		}
		
	}
	
	protected  void beforeBatchSave(List<RolePermission> entitys) {
		
		for(RolePermission rp : entitys){
			if(rp.getId()==null){
				rp.setId(UUID.randomUUID().toString().replaceAll("-", "").toString());
			}			
		}
		
	}
}
