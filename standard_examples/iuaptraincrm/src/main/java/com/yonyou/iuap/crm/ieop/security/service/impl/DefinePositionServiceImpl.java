package com.yonyou.iuap.crm.ieop.security.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import com.yonyou.iuap.crm.common.exception.AppBusinessException;
import com.yonyou.iuap.crm.common.utils.AppTools;
import com.yonyou.iuap.crm.common.utils.DictCode;
import com.yonyou.iuap.crm.ieop.security.entity.DefinePositionVO;
import com.yonyou.iuap.crm.ieop.security.entity.DefineRolePostitionVO;
import com.yonyou.iuap.crm.ieop.security.entity.DefineRoleSaveVO;
import com.yonyou.iuap.crm.ieop.security.entity.DefineUserPositionVO;
import com.yonyou.iuap.crm.ieop.security.repository.IDefinePositionDao;
import com.yonyou.iuap.crm.ieop.security.service.IDefinePositionService;
import com.yonyou.iuap.persistence.bs.dao.DAOException;
import com.yonyou.iuap.persistence.jdbc.framework.SQLParameter;
import com.yonyou.iuap.persistence.vo.pub.VOStatus;
import com.yonyou.uap.ieop.security.entity.RolePermission;
/**
 * 岗位维护
* <p>description：</p>
* @author 
* @created 2016年12月23日 上午9:52:44
* @version 1.0
 */
@Service
public class DefinePositionServiceImpl implements IDefinePositionService {
	@Autowired
	private IDefinePositionDao positionDao;
	
	/**
	 * 分页查询
	* @author 
	* @date 2016年12月23日
	* @param searchParams
	* @param pageRequest
	* @return
	* @throws AppBusinessException
	* (non-Javadoc)
	* @see com.yonyou.iuap.crm.ieop.security.service.IDefinePositionService#getPostitionBypages(java.util.Map, org.springframework.data.domain.PageRequest)
	 */
	@Override
	public Page<DefinePositionVO> getPostitionBypages(
			Map<String, Object> searchParams, PageRequest pageRequest)
			throws AppBusinessException {
		try{
			StringBuffer condition = new StringBuffer();
			
			SQLParameter sqlParameter = new SQLParameter();
			for(String key : searchParams.keySet()){
				String value = searchParams.get(key)==null?"":searchParams.get(key).toString();
				if(null != value && value.length()>0){
					//岗位编码
					if(null != key && "position_code".equals(key)){
						condition.append(" and a.position_code like '%").append(value).append("%'");
					}
					//岗位名称
					else if(null != key && "position_name".equals(key)){
						condition.append(" and a.position_name like '%").append(value).append("%'");
					}
					//岗位名称
					else if(null != key && "roleId".equals(key)){
						condition.append(" and a.id not in (select b.position_id from ieop_role_position b where b.role_id='").append(value).append("')");
					}
				} 
			}
			
			return positionDao.getPositionBypages(condition.toString(), sqlParameter, pageRequest);
		}catch(DAOException e){
			//Dao层次的异常需要处理成业务异常报给controller
			throw new AppBusinessException(e.getMessage());
		}
	}

	/**
	 * 保存
	* @author 
	* @date 2016年12月23日
	* @param vo
	* @return
	* @throws AppBusinessException
	* (non-Javadoc)
	* @see com.yonyou.iuap.crm.ieop.security.service.IDefinePositionService#save(com.yonyou.iuap.crm.ieop.security.entity.DefinePositionVO)
	 */
	@Override
	public String save(DefinePositionVO vo) throws AppBusinessException {
		try{
			StringBuffer errorMsg = new StringBuffer();
			//检查岗位代码和岗位名称不能重复
			StringBuffer sqlBuf = new StringBuffer();
			if(null != vo.getPosition_code()){
				sqlBuf.append(" and position_code = '").append(vo.getPosition_code()).append("'");
				List<DefinePositionVO> checkCodeList = positionDao.queryWithCondition(sqlBuf.toString());
				if(null != checkCodeList && checkCodeList.size()>0){
					errorMsg.append("岗位代码已经存在请重新填写!");
				}
			}else{
				errorMsg.append("岗位代码不能为空!");
			}
			//清空查询条件
			sqlBuf.delete(0,sqlBuf.length());
			if(null != vo.getPosition_name()){
				sqlBuf.append(" and position_name = '").append(vo.getPosition_name()).append("'");
				List<DefinePositionVO> checkNameList = positionDao.queryWithCondition(sqlBuf.toString());
				if(null != checkNameList && checkNameList.size()>0){
					errorMsg.append("岗位名称已经存在请重新填写!");
				}
			}else{
				errorMsg.append("岗位名称不能为空!");
			}
			
			if(errorMsg.length()==0){
				positionDao.save(vo);
			}
			return errorMsg.toString();
		}catch(DAOException e){
			//Dao层次的异常需要处理成业务异常报给controller
			throw new AppBusinessException(e.getMessage());
		}
	}

	/**
	 * 修改
	* @author 
	* @date 2016年12月23日
	* @param vo
	* @return
	* @throws AppBusinessException
	* (non-Javadoc)
	* @see com.yonyou.iuap.crm.ieop.security.service.IDefinePositionService#modify(com.yonyou.iuap.crm.ieop.security.entity.DefinePositionVO)
	 */
	@Override
	public String modify(DefinePositionVO vo) throws AppBusinessException {
		try{
			StringBuffer errorMsg = new StringBuffer();
			//检查岗位代码和岗位名称不能重复
			StringBuffer sqlBuf = new StringBuffer();
			if(null != vo.getPosition_code()){
				sqlBuf.append(" and position_code = '").append(vo.getPosition_code()).append("'");
				sqlBuf.append(" and id <> '").append(vo.getId()).append("'");
				List<DefinePositionVO> checkCodeList = positionDao.queryWithCondition(sqlBuf.toString());
				if(null != checkCodeList && checkCodeList.size()>0){
					errorMsg.append("岗位代码已经存在请重新填写!");
				}
			}else{
				errorMsg.append("岗位代码不能为空!");
			}
			//清空查询条件
			sqlBuf.delete(0,sqlBuf.length());
			if(null != vo.getPosition_name()){
				sqlBuf.append(" and position_name = '").append(vo.getPosition_name()).append("'");
				sqlBuf.append(" and id <> '").append(vo.getId()).append("'");
				List<DefinePositionVO> checkNameList = positionDao.queryWithCondition(sqlBuf.toString());
				if(null != checkNameList && checkNameList.size()>0){
					errorMsg.append("岗位名称已经存在请重新填写!");
				}
			}else{
				errorMsg.append("岗位名称不能为空!");
			}
			
			if(errorMsg.length()==0){
				positionDao.modify(vo);
			}
			return errorMsg.toString();
		}catch(DAOException e){
			//Dao层次的异常需要处理成业务异常报给controller
			throw new AppBusinessException(e.getMessage());
		}
	}

	/**
	 * 根据岗位ID查询角色
	* @author 
	* @date 2016年12月23日
	* @param positionId
	* @return
	* @throws AppBusinessException
	* (non-Javadoc)
	* @see com.yonyou.iuap.crm.ieop.security.service.IDefinePositionService#getRoleByPositionId(java.lang.String)
	 */
	@Override
	public List<DefineRoleSaveVO> getRoleByPositionId(String positionId)
			throws AppBusinessException {
		try{
			List<DefineRoleSaveVO> returnList = new ArrayList<DefineRoleSaveVO>();
			if(null != positionId && positionId.length()>0){
				returnList = positionDao.getRoleByPositionId(positionId);
			}
			return returnList;
		}catch(DAOException e){
			//Dao层次的异常需要处理成业务异常报给controller
			throw new AppBusinessException(e.getMessage());
		}
	}
	
	/**
	 * 根据岗位ID，查询没有分配的角色
	* @author 
	* @date 2016年12月23日
	* @param positionId
	* @return
	* @throws AppBusinessException
	* (non-Javadoc)
	* @see com.yonyou.iuap.crm.ieop.security.service.IDefinePositionService#queryRoleForAdd(java.lang.String)
	 */
	@Override
	public List<DefineRoleSaveVO> queryRoleForAdd(String positionId)
			throws AppBusinessException {
		try{
			List<DefineRoleSaveVO> returnList = new ArrayList<DefineRoleSaveVO>();
			if(null != positionId && positionId.length()>0){
				returnList = positionDao.queryRoleByPositionId(positionId);
			}
			return returnList;
		}catch(DAOException e){
			//Dao层次的异常需要处理成业务异常报给controller
			throw new AppBusinessException(e.getMessage());
		}
	}

	/**
	 * 添加角色
	* @author 
	* @date 2016年12月23日
	* @param vo
	* @return
	* @throws AppBusinessException
	* (non-Javadoc)
	* @see com.yonyou.iuap.crm.ieop.security.service.IDefinePositionService#addRole(com.yonyou.iuap.crm.ieop.security.entity.DefinePositionVO)
	 */
	@Override
	public String addRole(DefinePositionVO vo) throws AppBusinessException {
		try{
			StringBuffer errorMsg = new StringBuffer();
			if(null!=vo && null!=vo.getRoleItems() && vo.getRoleItems().size()>0){
				List<DefineRoleSaveVO> roleList = vo.getRoleItems();
				
				List<DefineRolePostitionVO> rolePositionList = new ArrayList<DefineRolePostitionVO>();
				for(DefineRoleSaveVO roleVO : roleList){
					DefineRolePostitionVO rolePositionVO = new DefineRolePostitionVO();
					rolePositionVO.setId(AppTools.generatePK());
					rolePositionVO.setPosition_id(vo.getId());
					rolePositionVO.setPosition_code(vo.getPosition_code());
					rolePositionVO.setRole_id(roleVO.getId());
					rolePositionVO.setRole_code(roleVO.getRole_code());
					rolePositionVO.setStatus(VOStatus.NEW);
					rolePositionList.add(rolePositionVO);
				}
				if(rolePositionList.size()>0){
					positionDao.saveRoleBatch(rolePositionList);
				}
			}else{
				errorMsg.append("岗位为空或者没有添加角色!");
			}
			return errorMsg.toString();
		}catch(DAOException e){
			//Dao层次的异常需要处理成业务异常报给controller
			throw new AppBusinessException(e.getMessage());
		}
	}
	
	/**
	 * 取消角色
	* @author 
	* @date 2016年12月23日
	* @param vo
	* @return
	* @throws AppBusinessException
	* (non-Javadoc)
	* @see com.yonyou.iuap.crm.ieop.security.service.IDefinePositionService#deleteRole(com.yonyou.iuap.crm.ieop.security.entity.DefinePositionVO)
	 */
	@Override
	public String deleteRole(DefinePositionVO vo) throws AppBusinessException {
		try{
			if(null!=vo && null!=vo.getRoleItems() && vo.getRoleItems().size()>0){
				List<DefineRoleSaveVO> roleList = vo.getRoleItems();
				Map<String,String> roleIdMap = new HashMap<String,String>();
				for(DefineRoleSaveVO roleVO : roleList){
					roleIdMap.put(roleVO.getId(), roleVO.getId());
				}
				List<DefineRolePostitionVO> rolePositionList = positionDao.quereRoleWithPositionId(vo.getId());
				List<DefineRolePostitionVO> delRolePositionList = new ArrayList<DefineRolePostitionVO>();
				for(DefineRolePostitionVO rolePosition : rolePositionList){
					if(null != roleIdMap.get(rolePosition.getRole_id())){
						delRolePositionList.add(rolePosition);
					}
				}
				//删除角色授权
				positionDao.deleteRoleBatch(delRolePositionList);
			}
			
			return null;
		}catch(DAOException e){
			//Dao层次的异常需要处理成业务异常报给controller
			throw new AppBusinessException(e.getMessage());
		}
	}
	
	/**
	 * 启用停用岗位
	* @author 
	* @date 2016年12月25日
	* @param vo
	* @throws AppBusinessException
	* (non-Javadoc)
	* @see com.yonyou.iuap.crm.ieop.security.service.IDefinePositionService#openPosition(com.yonyou.iuap.crm.ieop.security.entity.DefinePositionVO[])
	 */
	@Override
	public void openPosition(DefinePositionVO[] vo) throws AppBusinessException {
		try{
			if(null != vo && vo.length>0){
				List<DefinePositionVO> positionVOList = new ArrayList<DefinePositionVO>();
				for(DefinePositionVO positionVO : vo){
					if(DictCode.ALREADY_START_STATUS.equals(positionVO.getPosition_status())){
						positionVO.setPosition_status(DictCode.ALREADY_STOP_STATUS);
					}else if(DictCode.ALREADY_STOP_STATUS.equals(positionVO.getPosition_status())){
						positionVO.setPosition_status(DictCode.ALREADY_START_STATUS);
					}
					positionVOList.add(positionVO);
				}
				positionDao.modifyBatch(positionVOList);
			}
			
		}catch(DAOException e){
			//Dao层次的异常需要处理成业务异常报给controller
			throw new AppBusinessException(e.getMessage());
		}
	}

	/**
	 * 根据用户ID查询岗位信息
	* @author 
	* @date 2016年12月26日
	* @param userId
	* @return
	* @throws AppBusinessException
	* (non-Javadoc)
	* @see com.yonyou.iuap.crm.ieop.security.service.IDefinePositionService#getPositionWithUserId(java.lang.String)
	 */
	@Override
	public List<DefinePositionVO> getPositionWithUserId(String userId)
			throws AppBusinessException {
		try{
			return positionDao.getPositionWithUserId(userId);
		}catch(DAOException e){
			//Dao层次的异常需要处理成业务异常报给controller
			throw new AppBusinessException(e.getMessage());
		}
	}

	/**
	 * 根据用户ID查询未分配的岗位信息
	* @author 
	* @date 2016年12月26日
	* @param userId
	* @return
	* @throws AppBusinessException
	* (non-Javadoc)
	* @see com.yonyou.iuap.crm.ieop.security.service.IDefinePositionService#queryPositionForUserAdd(java.lang.String)
	 */
	@Override
	public List<DefinePositionVO> queryPositionForUserAdd(String userId)
			throws AppBusinessException {
		try{
			return positionDao.queryPositionForUserAdd(userId);
		}catch(DAOException e){
			//Dao层次的异常需要处理成业务异常报给controller
			throw new AppBusinessException(e.getMessage());
		}
	}

	/**
	 * 为用户批量增加岗位信息
	* @author 
	* @date 2016年12月26日
	* @param userPositionVOArr
	* @return
	* @throws AppBusinessException
	* (non-Javadoc)
	* @see com.yonyou.iuap.crm.ieop.security.service.IDefinePositionService#addUserPosition(com.yonyou.iuap.crm.ieop.security.entity.DefineUserPositionVO[])
	 */
	@Override
	public String addUserPosition(DefineUserPositionVO[] userPositionVOArr)
			throws AppBusinessException {
		try{
			if(null != userPositionVOArr && userPositionVOArr.length>0){
				List<DefineUserPositionVO> volist = new ArrayList<DefineUserPositionVO>();
				for(DefineUserPositionVO tempVO : userPositionVOArr){
					tempVO.setId(AppTools.generatePK());
					tempVO.setStatus(Integer.parseInt(DictCode.ALREADY_START_STATUS));
					tempVO.setStatus(VOStatus.NEW);
					volist.add(tempVO);
				}
				positionDao.addUserPosition(volist);
			}
			
		}catch(DAOException e){
			//Dao层次的异常需要处理成业务异常报给controller
			throw new AppBusinessException(e.getMessage());
		}
		return null;
	}

	/**
	 * 为用户批量删除岗位信息
	* @author 
	* @date 2016年12月26日
	* @param userPositionVOArr
	* @return
	* @throws AppBusinessException
	* (non-Javadoc)
	* @see com.yonyou.iuap.crm.ieop.security.service.IDefinePositionService#deleteUserPosition(com.yonyou.iuap.crm.ieop.security.entity.DefineUserPositionVO[])
	 */
	@Override
	public String deleteUserPosition(DefineUserPositionVO[] userPositionVOArr)
			throws AppBusinessException {
		try{
			if(null != userPositionVOArr && userPositionVOArr.length>0){
				List<DefineUserPositionVO> volist = new ArrayList<DefineUserPositionVO>();
				//需要删除的岗位信息
				Map<String,String> positonMap = new HashMap<String,String>();
				for(DefineUserPositionVO tempVO : userPositionVOArr){
					positonMap.put(tempVO.getPosition_id(), tempVO.getPosition_id());
				}
				List<DefineUserPositionVO> positionList = positionDao.quereyPositionWithUserId(userPositionVOArr[0].getUser_id());
				if(null != positionList && positionList.size()>0){
					for(DefineUserPositionVO userPositionVO : positionList){
						if(null != positonMap.get(userPositionVO.getPosition_id())){
							volist.add(userPositionVO);
						}
					}
				}
				if(null != positionList && positionList.size()>0){
					positionDao.deleteUserPosition(volist);
				}
			}
			
		}catch(DAOException e){
			//Dao层次的异常需要处理成业务异常报给controller
			throw new AppBusinessException(e.getMessage());
		}
		return null;
	}

	/**
	 * 根据角色ID查询关联的岗位信息
	* @author 
	* @date 2016年12月26日
	* @param roleId
	* @return
	* @throws AppBusinessException
	* (non-Javadoc)
	* @see com.yonyou.iuap.crm.ieop.security.service.IDefinePositionService#getPositionWithRoleId(java.lang.String)
	 */
	@Override
	public List<DefinePositionVO> getPositionWithRoleId(String roleId)
			throws AppBusinessException {
		try{
			return positionDao.getPositionWithRoleId(roleId);
		}catch(DAOException e){
			//Dao层次的异常需要处理成业务异常报给controller
			throw new AppBusinessException(e.getMessage());
		}
	}

	/**
	 * 根据角色ID查询未关联的岗位信息
	* @author 
	* @date 2016年12月26日
	* @param roleId
	* @return
	* @throws AppBusinessException
	* (non-Javadoc)
	* @see com.yonyou.iuap.crm.ieop.security.service.IDefinePositionService#queryPositionForRoleAdd(java.lang.String)
	 */
	@Override
	public List<DefinePositionVO> queryPositionForRoleAdd(String roleId)
			throws AppBusinessException {
		try{
			return positionDao.queryPositionForRoleAdd(roleId);
		}catch(DAOException e){
			//Dao层次的异常需要处理成业务异常报给controller
			throw new AppBusinessException(e.getMessage());
		}
	}

	/**
	 * 批量设置角色岗位的关联关系
	* @author 
	* @date 2016年12月26日
	* @param rolePositionVOArr
	* @return
	* @throws AppBusinessException
	* (non-Javadoc)
	* @see com.yonyou.iuap.crm.ieop.security.service.IDefinePositionService#addRolePosition(com.yonyou.iuap.crm.ieop.security.entity.DefineRolePostitionVO[])
	 */
	@Override
	public String addRolePosition(DefineRolePostitionVO[] rolePositionVOArr)
			throws AppBusinessException {
		try{
			if(null != rolePositionVOArr && rolePositionVOArr.length>0){
				List<DefineRolePostitionVO> volist = new ArrayList<DefineRolePostitionVO>();
				for(DefineRolePostitionVO tempVO : rolePositionVOArr){
					tempVO.setId(AppTools.generatePK());
					tempVO.setStatus(Integer.parseInt(DictCode.ALREADY_START_STATUS));
					tempVO.setStatus(VOStatus.NEW);
					volist.add(tempVO);
				}
				positionDao.addRolePosition(volist);
			}
			
		}catch(DAOException e){
			//Dao层次的异常需要处理成业务异常报给controller
			throw new AppBusinessException(e.getMessage());
		}
		return null;
	}

	/**
	 * 批量删除角色岗位关联关系
	* @author 
	* @date 2016年12月26日
	* @param rolePositionVOArr
	* @return
	* @throws AppBusinessException
	* (non-Javadoc)
	* @see com.yonyou.iuap.crm.ieop.security.service.IDefinePositionService#deletePositionForRole(com.yonyou.iuap.crm.ieop.security.entity.DefineRolePostitionVO[])
	 */
	@Override
	public String deletePositionForRole(DefineRolePostitionVO[] rolePositionVOArr)
			throws AppBusinessException {
		try{
			if(null != rolePositionVOArr && rolePositionVOArr.length>0){
				List<DefineRolePostitionVO> volist = new ArrayList<DefineRolePostitionVO>();
				//需要删除的岗位信息
				Map<String,String> positonMap = new HashMap<String,String>();
				for(DefineRolePostitionVO tempVO : rolePositionVOArr){
					positonMap.put(tempVO.getPosition_id(), tempVO.getPosition_id());
				}
				List<DefineRolePostitionVO> positionList = positionDao.quereyPositionWithRoleId(rolePositionVOArr[0].getRole_id());
				if(null != positionList && positionList.size()>0){
					for(DefineRolePostitionVO rolePositionVO : positionList){
						if(null != positonMap.get(rolePositionVO.getPosition_id())){
							volist.add(rolePositionVO);
						}
					}
				}
				if(null != positionList && positionList.size()>0){
					positionDao.deleteRolePosition(volist);
				}
			}
			
		}catch(DAOException e){
			//Dao层次的异常需要处理成业务异常报给controller
			throw new AppBusinessException(e.getMessage());
		}
		return null;
	}
	
	/**
	 * 根据登录用户获取授权信息
	* @author 
	* @date 2016年12月26日
	* @param userId
	* @return
	* @throws AppBusinessException
	* (non-Javadoc)
	 * @see com.yonyou.iuap.crm.ieop.security.service.IDefinePositionService#getStringPermission(java.lang.String)
	 */
	@Override
	public Set<String> getStringPermission(String userId)
			throws AppBusinessException {
		try {
			return positionDao.getStringPermission(userId);
		} catch (DAOException e) {
			// TODO 自动生成的 catch 块
			e.printStackTrace();
		}
		return null;
	}
	
	/**
	 * 根据登录用户获取角色授权信息
	* @author 
	* @date 2016年12月26日
	* @param userId
	* @return
	* @throws AppBusinessException
	* (non-Javadoc)
	 * @see com.yonyou.iuap.crm.ieop.security.service.IDefinePositionService#getStringPermission(java.lang.String)
	 */
	@Override
	public Set<String> findAllRoleByUserId(String userId)
			throws AppBusinessException {
		try {
			return positionDao.findAllRoleByUserId(userId);
		} catch (DAOException e) {
			// TODO 自动生成的 catch 块
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 根据登录用户获取资源授权信息
	* @author 
	* @date 2016年12月26日
	* @param userId
	* @return
	* @throws AppBusinessException
	* (non-Javadoc)
	 * @see com.yonyou.iuap.crm.ieop.security.service.IDefinePositionService#getStringPermission(java.lang.String)
	 */
	@Override
	public List<RolePermission> findAllFuncByUserId(String userId)
			throws AppBusinessException {
		try {
			return positionDao.findAllFuncByUserId(userId);
		} catch (DAOException e) {
			// TODO 自动生成的 catch 块
			e.printStackTrace();
		}
		return null;
	}
}
