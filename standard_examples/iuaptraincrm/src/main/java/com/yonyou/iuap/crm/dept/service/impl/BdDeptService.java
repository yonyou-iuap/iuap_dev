package com.yonyou.iuap.crm.dept.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONObject;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springside.modules.utils.Clock;

import com.yonyou.iuap.persistence.jdbc.framework.SQLParameter;
import com.yonyou.iuap.persistence.vo.pub.BusinessException;
import com.yonyou.iuap.crm.common.base.repository.AppBaseDao;
import com.yonyou.iuap.crm.common.utils.AppTools;
import com.yonyou.iuap.crm.corp.entity.BdCorpVO;
import com.yonyou.iuap.crm.corp.service.impl.BdCorpService;
import com.yonyou.iuap.crm.dept.entity.BdDeptVO;
import com.yonyou.iuap.crm.dept.repository.itf.IBdDeptDao;
import com.yonyou.iuap.crm.dept.service.itf.IBdDeptService;

//用于标注业务层组件
@Service
public class BdDeptService implements IBdDeptService {
	private static Logger logger = LoggerFactory.getLogger(BdCorpService.class);
	@Autowired
	private IBdDeptDao dao;
	
	@Autowired
	private AppBaseDao bsdao;
	
	private Clock clock = Clock.DEFAULT;
	
	/* （非 Javadoc）
	 * @see com.yonyou.iuap.crm.dept.service.IBdDeptService#setClock(org.springside.modules.utils.Clock)
	 */
	@Override
	public void setClock(Clock clock) {
        this.clock = clock;
    }
	
	/* （非 Javadoc）
	 * @see com.yonyou.iuap.crm.dept.service.IBdDeptService#getDeptsBypage(java.util.Map, org.springframework.data.domain.PageRequest)
	 */
	@Override
	public Page<BdDeptVO> getBdDeptsBypage(Map<String, Object> searchParams, PageRequest pageRequest) throws BusinessException{
		/**
		 * 添加业务逻辑
		 */
		StringBuffer condition = new StringBuffer();
		SQLParameter sqlParameter = new SQLParameter();
		//添加用户参照索引
		if(null!=searchParams.get("condition")&&!"".equals(searchParams.get("condition"))){
			condition.append(" ").append("and (deptcode like").append(" ").append("?").append(" ").append("||").append(" deptname like").append(" ").append(" ? )");
			sqlParameter.addParam("%"+searchParams.get("condition")+ "%");
			sqlParameter.addParam("%"+searchParams.get("condition")+ "%");
		}
		for(Map.Entry<String, Object> entry : searchParams.entrySet()){
			String[] keySplit = entry.getKey().split("&@&@");
			if(keySplit.length==2){
				String columnName = keySplit[1];
				String comparator = keySplit[0];
				Object value = entry.getValue();
				if(value != null && StringUtils.isNotBlank(value.toString())){
					condition.append(" and ").append(columnName).append(" ").append(comparator).append("?");
					value = "like".equalsIgnoreCase(comparator)?value + "%" : value;
					sqlParameter.addParam(value);
				}
			}
		}
		
		return dao.getBdDeptsBypage(condition.toString(), sqlParameter, pageRequest);
	}
	
	
	/* （非 Javadoc）
	 * @see com.yonyou.iuap.crm.dept.service.IBdDeptService#saveEntity(com.yonyou.iuap.crm.dept.entity.BdDeptVO, java.lang.String)
	 */
	@Override
	@Transactional
	public JSONObject saveEntity(BdDeptVO entity, String pk_corp) throws BusinessException{
		/**
		 * 添加业务逻辑
		 */
		JSONObject result = new JSONObject();
		String deptname = entity.getDeptname();
		String deptcode = entity.getDeptcode();		
		int level = 1;
		if(null==entity.getPkFathedept() || "".equals(entity.getPkFathedept())){
			level = 1;
		}else{
			BdDeptVO fvo = dao.getBdDept(entity.getPkFathedept());
			if(null==fvo){
				result.put("flag", AppTools.FAILED);
				result.put("msg", "上级部门不存在！");
				return result;				
			}
			level = fvo.getDeptlevel()+1;
			if(level>10){
				result.put("flag", AppTools.FAILED);
				result.put("msg", "部门级次不能大于10级！");
				return result;
			}
		}
		entity.setDeptlevel(level);	
		List<BdDeptVO> listvo = dao.getBdDeptsbyc(" and (deptname ='"+deptname+"' or deptcode='"+deptcode+"')",pk_corp);
		for(BdDeptVO pvo:listvo){
			String message = "";
			if(deptcode.equals(pvo.getDeptcode().trim())){
				message+="编码重复！";
			}
			if(deptname.equals(pvo.getDeptname().trim())){
				message+="名称重复！";
			}			
			if(null!=message && message.length()>0){
				result.put("flag", AppTools.FAILED);
				result.put("msg", message);
				return result;				
			}
		}
		String pk_dept = AppTools.generatePK();
		entity.setPk_dept(pk_dept);
		dao.saveBdDeptWithPK(entity);
		result.put("flag", AppTools.SUCCESS);
		result.put("msg", "success");
		return result;		
	}
	
//	@Transactional
//	public String saveEntity(BdDeptVO entity) throws BusinessException{
//		List<BdDeptVO> existvo = dao.getBdDeptByCodeAndName(entity.getDeptname(),entity.getDeptname());
//		if(existvo.isEmpty()){
//			String pk = AppTools.generatePK();
//			entity.setPk_dept(pk);
//			entity.setCreationtime(clock.getCurrentDate());
//			return dao.saveBdDept(entity);
//		}else{
//			throw new BusinessException("编码已经存在！");
//		}
//	}
	
	/* （非 Javadoc）
	 * @see com.yonyou.iuap.crm.dept.service.IBdDeptService#saveEntityForNormal(com.yonyou.iuap.crm.dept.entity.BdDeptVO, java.lang.String)
	 */
	@Override
	@Transactional
	public JSONObject saveEntityForNormal(BdDeptVO entity, String pk_corp ) throws BusinessException{
		/**
		 * 添加业务逻辑
		 */
		JSONObject result = new JSONObject();
		String deptname = entity.getDeptname();
		String deptcode = entity.getDeptcode();		
		String pk = entity.getPk_dept();
		int level = 1;
		if(null==entity.getFathedept() || "".equals(entity.getFathedept())){
			level = 1;
		}else{
			BdDeptVO fvo = dao.getBdDept(entity.getFathedept());
			level = fvo.getDeptlevel()+1;
			if(level>10){
				result.put("flag", AppTools.FAILED);
				result.put("msg", "公司级次不能大于10级！");
				return result;
			}
		}
		entity.setDeptlevel(level);	
		if(null==pk || "".equals(pk)){
			// 查询一下是否存在，重名，重编码
			List<BdDeptVO> listvo = dao.getBdDeptsbyc(" and (deptname ='"+deptname+"' or deptcode='"+deptcode+"')",pk_corp);
			for(BdDeptVO pvo:listvo){
				String message = "";
				if(deptcode.equals(pvo.getDeptcode().trim())){
					message+="编码重复！";
				}
				if(deptname.equals(pvo.getDeptname().trim())){
					message+="名称重复！";
				}			
				if(null!=message && message.length()>0){
					result.put("flag", AppTools.FAILED);
					result.put("msg", message);
					return result;				
				}
			}
			pk = AppTools.generatePK();
			entity.setPk_dept(pk);
			dao.saveBdDeptWithPK(entity);		
		}else{
			List<BdDeptVO> listvo = dao.getBdDeptsbyc(" and pk_dept!='"+pk+"' and (deptname ='"+deptname+"' or deptcode='"+deptcode+"')",pk_corp);
			for(BdDeptVO pvo:listvo){
				String message = "";
				if(deptcode.equals(pvo.getDeptcode().trim())){
					message+="编码重复！";
				}
				if(deptname.equals(pvo.getDeptname().trim())){
					message+="名称重复！";
				}			
				if(null!=message && message.length()>0){
					result.put("flag", AppTools.FAILED);
					result.put("msg", message);
					return result;				
				}
			}		
			dao.updateBdDept(entity);	
		}
		result.put("flag", AppTools.SUCCESS);
		result.put("msg", "success");
		return result;
	}
	
	/* （非 Javadoc）
	 * @see com.yonyou.iuap.crm.dept.service.IBdDeptService#updateEntity(com.yonyou.iuap.crm.dept.entity.BdDeptVO)
	 */
	@Override
	@Transactional
	public String updateEntity(BdDeptVO entity) throws BusinessException{
		BdDeptVO originvo = this.getBdDept(entity.getPk_dept());
		if(!originvo.getDeptcode().equals(entity.getDeptcode())){
			throw new BusinessException("编码不允许修改");
		}else{
			originvo.setDeptcode(entity.getDeptcode());
			originvo.setDeptname(entity.getDeptname());
			originvo.setPk_user(entity.getPk_user());
			originvo.setPkLeader(entity.getPkLeader());
			originvo.setPk_corp(entity.getPk_corp());
			originvo.setCanceled(entity.getCanceled());
			originvo.setModifier("modify");
			originvo.setCreationtime(clock.getCurrentDate());
			dao.updateBdDept(originvo);
			return "success";
		}
	}
	
	
	/* （非 Javadoc）
	 * @see com.yonyou.iuap.crm.dept.service.IBdDeptService#deleteBdDept(com.yonyou.iuap.crm.dept.entity.BdDeptVO)
	 */
	@Override
	@Transactional
	public void deleteBdDept(BdDeptVO entity) throws BusinessException{
		/**
		 * 添加业务逻辑
		 */
		dao.deleteBdDeptByIdTS(entity.getPk_dept());
	}
	
	/* （非 Javadoc）
	 * @see com.yonyou.iuap.crm.dept.service.IBdDeptService#deleteBdDeptById(java.lang.String)
	 */
	@Override
	@Transactional
	public void deleteBdDeptById(String pk_dept) throws BusinessException{
		/**
		 * 添加业务逻辑
		 */
		dao.deleteBdDeptByIdTS(pk_dept);
	}
	
	/* （非 Javadoc）
	 * @see com.yonyou.iuap.crm.dept.service.IBdDeptService#getBdDept(java.lang.String)
	 */
	@Override
	public BdDeptVO getBdDept(String pk_dept) throws BusinessException{
		return dao.getBdDept(pk_dept);
	}
	/* （非 Javadoc）
	 * @see com.yonyou.iuap.crm.dept.service.IBdDeptService#getBdDepts()
	 */
	@Override
	public List<BdDeptVO> getBdDepts() throws BusinessException{
		return dao.getBdDeptnum(""); 
	}
	/* （非 Javadoc）
	 * @see com.yonyou.iuap.crm.dept.service.IBdDeptService#getBdDeptnames(java.lang.String)
	 */
	@Override
	public List<BdDeptVO> getBdDeptnames(String sql) throws BusinessException{
		return dao.getBdDeptnum(sql); 
	}
	/* （非 Javadoc）
	 * @see com.yonyou.iuap.crm.dept.service.IBdDeptService#getBdDeptbycorp(java.lang.String)
	 */
	@Override
	public List<BdDeptVO> getBdDeptbycorp(String pk_corp) throws BusinessException{
		return dao.getBdDeptcorp(pk_corp); 
	}
	
	/* （非 Javadoc）
	 * @see com.yonyou.iuap.crm.dept.service.IBdDeptService#queryDeptByDept(java.lang.String)
	 */
	@Override
	public String queryBdDeptByDept(String pk_dept) throws BusinessException{
		return dao.queryDeptByDept(pk_dept);
	}
	
	/* （非 Javadoc）
	 * @see com.yonyou.iuap.crm.dept.service.IBdDeptService#getBdSubDepts(java.lang.String)
	 */
	@Override
	public List<BdDeptVO> getBdSubDepts(String wherestr) throws BusinessException{
		return dao.getBdDepts(wherestr);
	}
	
	/* （非 Javadoc）
	 * @see com.yonyou.iuap.crm.dept.service.IBdDeptService#stopDept(java.lang.String)
	 */
	@Override
	@Transactional
	public String stopBdDept(String pk_dept) throws BusinessException{
		/**
		 * 添加业务逻辑
		 */
		dao.stopDept(pk_dept);
		return "success";
	}	
	
	// 返回组织
		/* （非 Javadoc）
		 * @see com.yonyou.iuap.crm.dept.service.IBdDeptService#getBdDeptsListMap(java.lang.String)
		 */
		@Override
		public List<HashMap> getBdDeptsListMap(String wherestr) throws BusinessException{
			return dao.getBdDeptsListMap(wherestr); 
		}
	
	/* （非 Javadoc）
	 * @see com.yonyou.iuap.crm.dept.service.IBdDeptService#startDept(java.lang.String)
	 */
	@Override
	@Transactional
	public String startBdDept(String pk_dept) throws BusinessException{
		/**
		 * 添加业务逻辑
		 */
		dao.startDept(pk_dept);
		return "success";
	}
	
/* （非 Javadoc）
 * @see com.yonyou.iuap.crm.dept.service.IBdDeptService#getBdDeptsByDept(java.util.Map, org.springframework.data.domain.PageRequest, java.lang.String)
 */
@Override
public Page<BdDeptVO> getBdDeptsByDept(Map<String, Object> searchParams, PageRequest pageRequest,String pk_dept) throws BusinessException{
	/**
	 * 添加业务逻辑
	 */
	StringBuffer condition = new StringBuffer();
	SQLParameter sqlParameter = new SQLParameter();
	for(Map.Entry<String, Object> entry : searchParams.entrySet()){
		String[] keySplit = entry.getKey().split("_");
		if(keySplit.length==2){
			String columnName = keySplit[1];
			String comparator = keySplit[0];
			Object value = entry.getValue();
			if(value != null && StringUtils.isNotBlank(value.toString())){
				condition.append(" and ").append(columnName).append(" ").append(comparator).append("?");
				value = "like".equalsIgnoreCase(comparator)?value + "%" : value;
				sqlParameter.addParam(value);
			}
		}
	}
	
	return dao.getBdDeptsBypage(condition.toString()+" and pk_dept='"+pk_dept+"'", sqlParameter, pageRequest);
	}

/* （非 Javadoc）
 * @see com.yonyou.iuap.crm.dept.service.IBdDeptService#getBdDept(java.util.Map, org.springframework.data.domain.PageRequest, java.lang.String)
 */
@Override
public Page<BdDeptVO> getBdDept(Map<String, Object> searchParams, PageRequest pageRequest,String pk_dept) throws BusinessException{
	/**
	 * 添加业务逻辑
	 */
	StringBuffer condition = new StringBuffer();
	SQLParameter sqlParameter = new SQLParameter();
	for(Map.Entry<String, Object> entry : searchParams.entrySet()){
		String[] keySplit = entry.getKey().split("_");
		if(keySplit.length==2){
			String columnName = keySplit[1];
			String comparator = keySplit[0];
			Object value = entry.getValue();
			if(value != null && StringUtils.isNotBlank(value.toString())){
				condition.append(" and ").append(columnName).append(" ").append(comparator).append("?");
				value = "like".equalsIgnoreCase(comparator)?value + "%" : value;
				sqlParameter.addParam(value);
			}
		}
	}
	
	return dao.getBdDept(condition.toString()+" and  pk_dept in ((select p.pk_fathedept from bd_dept p where pk_dept='"+pk_dept+"'),'"+pk_dept+"')", sqlParameter, pageRequest);
	}

/* （非 Javadoc）
 * @see com.yonyou.iuap.crm.dept.service.IBdDeptService#getdplist(java.lang.String, java.lang.String, java.lang.String)
 */
@Override
public List<BdDeptVO> getdplist(String deptname, String pk_corp,String sql) throws BusinessException {
	StringBuffer condition = new StringBuffer();
	if((!"".equals(deptname)&&deptname!=null)||(!"".equals(pk_corp)&&pk_corp!=null)){
		condition.append(" pk_dept in (select pk_dept from bd_dept where  dr=0  "+sql+"");
		if(!"".equals(deptname)&&!"null".equals(deptname)){
			condition.append("and deptname !='"+deptname+"'");
		}
		if(!"".equals(pk_corp)&&!"null".equals(pk_corp)){
			condition.append("and pk_corp='"+pk_corp+"'");
		}
		condition.append(") and dr=0");
	}else{
		condition.append("dr=0");
	}
	return bsdao.findListByClause(BdDeptVO.class,condition.toString());
}

/* （非 Javadoc）
 * @see com.yonyou.iuap.crm.dept.service.IBdDeptService#getBdCorpsBypages(java.util.Map, org.springframework.data.domain.PageRequest)
 */
@Override
public Page<BdCorpVO> getBdCorpsBypages(Map<String, Object> searchParams, PageRequest pageRequest) throws BusinessException{
	/**
	 * 添加业务逻辑
	 */
	StringBuffer condition = new StringBuffer();
	SQLParameter sqlParameter = new SQLParameter();
	for(Map.Entry<String, Object> entry : searchParams.entrySet()){
		String[] keySplit = entry.getKey().split("_");
		if(keySplit.length==2){
			String columnName = keySplit[1];
			String comparator = keySplit[0];
			Object value = entry.getValue();
			if(value != null && StringUtils.isNotBlank(value.toString())){
				condition.append(" and ").append(columnName).append(" ").append(comparator).append("?");
				value = "like".equalsIgnoreCase(comparator)?value + "%" : value;
				sqlParameter.addParam(value);
			}
		}
	}
	
	return dao.getBdCorpsBypage(condition.toString(), sqlParameter, pageRequest);
}


}