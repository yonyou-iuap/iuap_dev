package com.yonyou.iuap.crm.basedata.service.impl;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.yonyou.iuap.crm.basedata.entity.TmUserApplyPosVO;
import com.yonyou.iuap.crm.basedata.entity.TmUserApplyVO;
import com.yonyou.iuap.crm.basedata.service.itf.IUserApplyService;
import com.yonyou.iuap.crm.common.base.repository.AppBaseDao;
import com.yonyou.iuap.crm.common.exception.AppBusinessException;
import com.yonyou.iuap.crm.common.utils.AppInvocationInfoProxy;
import com.yonyou.iuap.crm.common.utils.AppTools;
import com.yonyou.iuap.crm.common.utils.DictCode;
import com.yonyou.iuap.crm.ieop.security.entity.DefinePositionVO;
import com.yonyou.iuap.crm.ieop.security.entity.DefineUserPositionVO;
import com.yonyou.iuap.crm.psn.entity.BdPsndocVO;
import com.yonyou.iuap.crm.user.entity.ExtIeopUserVO;
import com.yonyou.iuap.persistence.bs.dao.BaseDAO;
import com.yonyou.iuap.persistence.bs.dao.DAOException;
import com.yonyou.iuap.persistence.jdbc.framework.SQLParameter;
import com.yonyou.iuap.persistence.vo.pub.VOStatus;

@Service
public class UserApplyServiceImpl implements IUserApplyService {
	@Autowired
	private AppBaseDao appBaseDao;

	@Autowired
	@Qualifier("baseDAO") private BaseDAO baseDao;
	/**
	 * 获取用户申请列表
	* TODO description
	* @author 
	* @date 2017年1月16日
	* @return
	 */
	public Page<TmUserApplyVO> queryUserApply(Map<String, Object> parammap,PageRequest pg) throws AppBusinessException{
		StringBuffer sb = new StringBuffer();
		SQLParameter sp = new SQLParameter();
		sb.append("select a.*,b.unitname as orgname,c.deptname as deptname,d.vname as stsname from tm_userapply a left join bd_corp b on a.pk_org=b.pk_corp ");
		sb.append(" left join bd_dept c on a.pk_dept=c.pk_dept  ");
		sb.append(" left join tc_enumcode d on a.vstatus=d.vcode  ");
		sb.append(" where 1=1 ");
		if(null!=parammap.get("vusercode")&&!parammap.get("vusercode").toString().equals("")){
			sb.append(" and a.vusercode = ? ");
			sp.addParam(parammap.get("vusercode"));
		}
		if(null!=parammap.get("vpsncode")&&!parammap.get("vpsncode").toString().equals("")){
			sb.append(" and a.vpsncode = ? ");
			sp.addParam(parammap.get("vpsncode"));
		}
		if(null!=parammap.get("vworkid")&&!parammap.get("vworkid").toString().equals("")){
			sb.append(" and a.vworkid = ? ");
			sp.addParam(parammap.get("vworkid"));
		}
		if(null!=parammap.get("pk_org")&&!parammap.get("pk_org").toString().equals("")){
			sb.append(" and a.pk_org = ? ");
			sp.addParam(parammap.get("pk_org"));
		}
		if(null!=parammap.get("pk_dept")&&!parammap.get("pk_dept").toString().equals("")){
			sb.append(" and a.pk_dept = ? ");
			sp.addParam(parammap.get("pk_dept"));
		}
		if(null!=parammap.get("vstatus")&&!parammap.get("vstatus").toString().equals("")){
			sb.append(" and a.vstatus = ? ");
			sp.addParam(parammap.get("vstatus"));
		}
		try {
			return baseDao.queryPage(sb.toString(), sp, pg, TmUserApplyVO.class);
		} catch (DAOException e) {
			// TODO 自动生成的 catch 块
			e.printStackTrace();
			throw new AppBusinessException(e.getMessage());
		}
	}
	@Override
	public Page<DefinePositionVO> queryPositionUser(Map<String, Object> parammap,PageRequest pg)
			throws AppBusinessException {
		// TODO 自动生成的方法存根
		StringBuffer sb = new StringBuffer("");
		SQLParameter sp = new SQLParameter();
		sb.append(" select a.* from ieop_position a where position_status='").append(DictCode.ALREADY_START_STATUS).append("' ");
		sb.append("  and a.id not in (select b.pk_position from tm_userapply_pos b where 1=1 ");
//		if(userId!=null && !userId.isEmpty()){
//			sb.append(" and b.pk_userapply = '").append(userId).append("'");
//		}
		sb.append(")");
		try {
			return baseDao.queryPage(sb.toString(), sp, pg, DefinePositionVO.class);
		} catch (DAOException e) {
			// TODO 自动生成的 catch 块
			e.printStackTrace();
			throw new AppBusinessException(e.getMessage());
		}
	}
	@Override
	@Transactional
	public void saveUserApply(TmUserApplyVO vo, List<TmUserApplyPosVO> cvos) throws AppBusinessException {
		// TODO 自动生成的方法存根
		//获取当前登录人
		String user = AppInvocationInfoProxy.getPk_User();
		//获取当前登录人组织
		String org = AppInvocationInfoProxy.getPk_Corp();
		//生成当前时间
		Date d = new Date();
		SimpleDateFormat df=new SimpleDateFormat("yyyy-MM-dd HH:MM:ss"); 
		String datestr = df.format(d);
		try {
			if(null==vo.getPk_userapply()||vo.getPk_userapply().equals("")){
				vo.setStatus(VOStatus.NEW);
				vo.setVstatus(DictCode.USER_POS_01);
				vo.setCreator(user);
				vo.setCreationtime(datestr);
				if(null!=cvos&&cvos.size()>0){
					for (TmUserApplyPosVO upvo : cvos) {
						upvo.setStatus(VOStatus.NEW);
					}
				}
			}else{
				vo.setStatus(VOStatus.UPDATED);
				vo.setModifier(user);
				vo.setModifiedtime(datestr);
				if(null!=cvos&&cvos.size()>0){
					for (TmUserApplyPosVO upvo : cvos) {
						if(null==upvo.getPk_userapplypos()||upvo.getPk_userapplypos().equals("")){
							upvo.setStatus(VOStatus.NEW);
						}else{
							upvo.setStatus(VOStatus.UPDATED);
						}
					}
				}
			}
			appBaseDao.mergeWithChild(vo, cvos);
		} catch (DAOException e) {
			// TODO 自动生成的 catch 块
			e.printStackTrace();
		}
	}
	@Override
	@Transactional
	public void deleteUserPosition(TmUserApplyPosVO vo)
			throws AppBusinessException {
		// TODO 自动生成的方法存根
		try {
			baseDao.remove(vo);
		} catch (DAOException e) {
			// TODO 自动生成的 catch 块
			e.printStackTrace();
		}
	}
	@Override
	public List<TmUserApplyPosVO> queryPositionByUser(String userAppId)
			throws AppBusinessException {
		// TODO 自动生成的方法存根
		try {
			List<TmUserApplyPosVO> list = appBaseDao.findListByClause(TmUserApplyPosVO.class, "pk_userapply='"+userAppId+"'");
			return list;
		} catch (DAOException e) {
			// TODO 自动生成的 catch 块
			e.printStackTrace();
			throw new AppBusinessException(e.getMessage());
		}
	}
	@Override
	@Transactional
	public void submitUserApply(TmUserApplyVO vo) throws AppBusinessException {
		// TODO 自动生成的方法存根
		//生成当前时间
		Date d = new Date();
		SimpleDateFormat df=new SimpleDateFormat("yyyy-MM-dd HH:MM:ss"); 
		String datestr = df.format(d);
		vo.setStatus(VOStatus.UPDATED);
		vo.setModifiedtime(datestr);
		vo.setVstatus(DictCode.USER_POS_02);//单据状态为审核中 
		try {
			baseDao.update(vo);
		} catch (DAOException e) {
			// TODO 自动生成的 catch 块
			e.printStackTrace();
		}
	}
	@Override
	@Transactional
	public void deleteUserApply(TmUserApplyVO vo) throws AppBusinessException {
		// TODO 自动生成的方法存根
		//生成当前时间
		try {
			String pk = vo.getPk_userapply();
			baseDao.remove(vo);
			appBaseDao.deleteByFK(TmUserApplyPosVO.class, TmUserApplyVO.class, pk);
		} catch (DAOException e) {
			// TODO 自动生成的 catch 块
			e.printStackTrace();
		}
	}
	@Override
	public void auditSaveUserApply(TmUserApplyVO vo, List<TmUserApplyPosVO> cvos)
			throws AppBusinessException {
		// TODO 自动生成的方法存根
		//获取当前登录人
		String user = AppInvocationInfoProxy.getPk_User();
		//获取当前登录人组织
		String org = AppInvocationInfoProxy.getPk_Corp();
		//生成当前时间
		Date d = new Date();
		SimpleDateFormat df=new SimpleDateFormat("yyyy-MM-dd HH:MM:ss"); 
		String datestr = df.format(d);
		try {
			if(null==vo.getPk_userapply()||vo.getPk_userapply().equals("")){
				vo.setStatus(VOStatus.NEW);
				vo.setCreator(user);
				vo.setCreationtime(datestr);
				if(null!=cvos&&cvos.size()>0){
					for (TmUserApplyPosVO upvo : cvos) {
						upvo.setStatus(VOStatus.NEW);
					}
				}
			}else{
				vo.setStatus(VOStatus.UPDATED);
				vo.setModifier(user);
				vo.setModifiedtime(datestr);
				if(null!=cvos&&cvos.size()>0){
					for (TmUserApplyPosVO upvo : cvos) {
						if(null==upvo.getPk_userapplypos()||upvo.getPk_userapplypos().equals("")){
							upvo.setStatus(VOStatus.NEW);
						}else{
							upvo.setStatus(VOStatus.UPDATED);
						}
					}
				}
			}
			appBaseDao.mergeWithChild(vo, cvos);
		} catch (DAOException e) {
			// TODO 自动生成的 catch 块
			e.printStackTrace();
		}
	}
	
	/**
	 * 审核通过后
	 * 需新增一条人员表数据，如人员编码已存在需提示
	 * 需新增一条用户表数据，关联人员，如用户编码已存在需提示
	 * 需要将对应岗位保存到用户岗位关系表中
	* @author name
	* @date 2017年1月18日
	* @param vo
	* @param cvos
	* @throws AppBusinessException
	* (non-Javadoc)
	* @see com.yonyou.iuap.crm.basedata.service.itf.IUserApplyService#passUserApply(com.yonyou.iuap.crm.basedata.entity.TmUserApplyVO, java.util.List)
	 */
	@Override
	public void passUserApply(TmUserApplyVO vo, List<TmUserApplyPosVO> cvos)
			throws AppBusinessException {
		// TODO 自动生成的方法存根
		try {
			//判断人员是否已存在
			String vpsncode = vo.getVpsncode();//人员编码
			List<BdPsndocVO> list = appBaseDao.findListByClauseWithDR(BdPsndocVO.class, "psncode='"+vpsncode+"'");
			if(null!=list&&list.size()>0){
				throw new AppBusinessException("人员编码已存在！");
			}
			//判断用户是否已存在
			String vusercode = vo.getVusercode();//用户编码
			List<ExtIeopUserVO> ulist = appBaseDao.findListByClauseWithDR(ExtIeopUserVO.class, "login_name='"+vusercode+"'");
			if(null!=ulist&&ulist.size()>0){
				throw new AppBusinessException("用户编码已存在！");
			}
			String user = AppInvocationInfoProxy.getPk_User();
			//生成当前时间
			Date d = new Date();
			SimpleDateFormat df=new SimpleDateFormat("yyyy-MM-dd HH:MM:ss"); 
			String datestr = df.format(d); 
			//新增人员
			BdPsndocVO pvo = new BdPsndocVO();
			String pkpsn = AppTools.generatePK();
			pvo.setPk_psndoc(pkpsn);//人员主键
			pvo.setPsncode(vpsncode);//人员编码
			pvo.setPsnname(vo.getVusername());//名称
			pvo.setPk_corp(vo.getPk_org());//所属组织
			pvo.setPk_dept(vo.getPk_dept());//所属部门
			pvo.setEmail(vo.getVemail());//邮箱
			pvo.setPsnid(vo.getVid());//身份证号
			pvo.setNumber(vo.getVworkid());//工号
			pvo.setPsntel(vo.getVphone());//电话
			pvo.setCreator(user);
			pvo.setCreationtime(new Date());
			appBaseDao.saveWithPK(pvo);
			//新增用户
			String pkusr = AppTools.generatePK();
			ExtIeopUserVO uvo = new ExtIeopUserVO();
			uvo.setId(pkusr);//用户主键
			uvo.setPkPsndoc(pkpsn);//关联人员主键
			uvo.setLoginName(vusercode);//用户编码：登录账号
			uvo.setName(vo.getVusername());//用户名称
			uvo.setLocked(0);//是否锁定：否
			uvo.setPsntel(vo.getVphone());//电话
			uvo.setEmail(vo.getVemail());//邮箱
			uvo.setPkCorp(vo.getPk_org());//所属组织
			uvo.setPkDept(vo.getPk_dept());//所属部门
			uvo.setPassword("5a2ae9e5301af463a04b79ee1e391ba8d65cfa37");//密码默认：123
			uvo.setSalt("e3af403c455cd77a");
			uvo.setDr(0);
			uvo.setCreator(user);
			uvo.setCreationtime(new Date());
			appBaseDao.saveWithPK(uvo);
			//将用户对应岗位新增到用户岗位关系表
			if(null!=cvos&&cvos.size()>0){
				for (TmUserApplyPosVO apvo : cvos) {
					DefineUserPositionVO upvo = new DefineUserPositionVO();
					String pkup = AppTools.generatePK();
					upvo.setId(pkup);
					upvo.setUser_id(pkusr);
					upvo.setUser_code(vusercode);
					upvo.setPosition_id(apvo.getPk_position());
					upvo.setPosition_code(apvo.getPosition_code());
					appBaseDao.saveWithPK(upvo);
				}
			}
			//修改用户申请状态为已审核
			vo.setVstatus(DictCode.USER_POS_04);
			vo.setModifier(user);
			vo.setModifiedtime(datestr);
			appBaseDao.update(vo);
		} catch (DAOException e) {
			// TODO 自动生成的 catch 块
			e.printStackTrace();
			throw new AppBusinessException(e.getMessage());
		}
	}
	
	@Override
	public void rejectUserApply(TmUserApplyVO vo, List<TmUserApplyPosVO> cvos)
			throws AppBusinessException {
		// TODO 自动生成的方法存根
		try {
			String user = AppInvocationInfoProxy.getPk_User();
			//生成当前时间
			Date d = new Date();
			SimpleDateFormat df=new SimpleDateFormat("yyyy-MM-dd HH:MM:ss");
			String datestr = df.format(d); 
			//修改用户申请状态为已保存
			vo.setVstatus(DictCode.USER_POS_01);
			vo.setModifier(user);
			vo.setModifiedtime(datestr);
			appBaseDao.update(vo);
		} catch (DAOException e) {
			// TODO 自动生成的 catch 块
			e.printStackTrace();
			throw new AppBusinessException(e.getMessage());
		}
	}
}
