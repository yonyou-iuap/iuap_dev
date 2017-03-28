package com.yonyou.iuap.crm.basedata.service.itf;

import java.util.List;
import java.util.Map;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import com.yonyou.iuap.crm.basedata.entity.TmUserApplyPosVO;
import com.yonyou.iuap.crm.basedata.entity.TmUserApplyVO;
import com.yonyou.iuap.crm.common.exception.AppBusinessException;
import com.yonyou.iuap.crm.ieop.security.entity.DefinePositionVO;

public interface IUserApplyService {
	public Page<TmUserApplyVO> queryUserApply(Map<String, Object> parammap,PageRequest pg) throws AppBusinessException;
	/**
	 * 根据用户查询未分配的岗位信息
	* TODO description
	* @author 
	* @date 2016年1月17日
	* @param userId
	* @return
	* @throws AppBusinessException
	 */
	public Page<DefinePositionVO> queryPositionUser(Map<String, Object> parammap,PageRequest pg) throws AppBusinessException;
	
	/**
	 * 保存用户申请
	* TODO description
	* @author 
	* @date 2017年1月17日
	* @param vo
	* @param cvos
	* @return
	* @throws AppBusinessException
	 */
	public void saveUserApply(TmUserApplyVO vo,List<TmUserApplyPosVO> cvos) throws AppBusinessException;
	
	/**
	 * 删除用户对应岗位
	* TODO description
	* @author 
	* @date 2017年1月17日
	* @param vo
	* @param cvos
	* @return
	* @throws AppBusinessException
	 */
	public void deleteUserPosition(TmUserApplyPosVO vo) throws AppBusinessException;
	
	/**
	 * 根据用户申请id查找对应岗位
	* TODO description
	* @author 
	* @date 2017年1月17日
	* @param userAppId
	* @return
	* @throws AppBusinessException
	 */
	public List<TmUserApplyPosVO> queryPositionByUser(String userAppId) throws AppBusinessException;
	
	/**
	 *提交用户申请
	* TODO description
	* @author 
	* @date 2017年1月17日
	* @param vo
	* @param cvos
	* @return
	* @throws AppBusinessException
	 */
	public void submitUserApply(TmUserApplyVO vo) throws AppBusinessException;
	
	/**
	 * 删除用户申请
	* TODO description
	* @author 
	* @date 2017年1月17日
	* @param vo
	* @throws AppBusinessException
	 */
	public void deleteUserApply(TmUserApplyVO vo) throws AppBusinessException;
	
	/**
	 * 保存用户申请
	* TODO description
	* @author 
	* @date 2017年1月17日
	* @param vo
	* @param cvos
	* @return
	* @throws AppBusinessException
	 */
	public void auditSaveUserApply(TmUserApplyVO vo,List<TmUserApplyPosVO> cvos) throws AppBusinessException;
	
	/**
	 * 用户申请申请审核通过
	* TODO description
	* @author 
	* @date 2017年1月17日
	* @param vo
	* @param cvos
	* @return
	* @throws AppBusinessException
	 */
	public void passUserApply(TmUserApplyVO vo,List<TmUserApplyPosVO> cvos) throws AppBusinessException;
	
	/**
	 * 审核驳回
	* TODO description
	* @author 
	* @date 2017年1月18日
	* @param vo
	* @param cvos
	* @throws AppBusinessException
	 */
	public void rejectUserApply(TmUserApplyVO vo, List<TmUserApplyPosVO> cvos) throws AppBusinessException;
}

