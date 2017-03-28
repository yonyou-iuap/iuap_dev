package com.yonyou.iuap.crm.corp.service.impl;

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

import com.yonyou.iuap.crm.common.base.repository.AppBaseDao;
import com.yonyou.iuap.crm.common.utils.AppInvocationInfoProxy;
import com.yonyou.iuap.crm.common.utils.AppTools;
import com.yonyou.iuap.crm.corp.entity.BdCorpVO;
import com.yonyou.iuap.crm.corp.repository.itf.IBdCorpDao;
import com.yonyou.iuap.crm.corp.service.itf.IBdCorpService;
import com.yonyou.iuap.persistence.bs.dao.DAOException;
import com.yonyou.iuap.persistence.jdbc.framework.SQLParameter;
import com.yonyou.iuap.persistence.vo.pub.BusinessException;
import com.yonyou.iuap.persistence.vo.pub.VOStatus;

//用于标注业务层组件
@Service
public class BdCorpService implements IBdCorpService {
	private static Logger logger = LoggerFactory.getLogger(BdCorpService.class);

	@Autowired
	private IBdCorpDao dao;
	@Autowired
	private AppBaseDao baseDao;
	
	private Clock clock = Clock.DEFAULT;
	
	public void setClock(Clock clock) {
        this.clock = clock;
    }
	
	@Override
	public Page<BdCorpVO> getCorpsBypage(Map<String, Object> searchParams, PageRequest pageRequest) {
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
		
		try {
			return dao.getBdCorpsBypage(condition.toString()+" and ifnull( fathercorp,'')=''", sqlParameter, pageRequest);
		} catch (DAOException e) {
			logger.error("查询出错："+e);
		}
		return null;
	}
	
	@Override
	public Page<BdCorpVO> getCorpsByCorp(Map<String, Object> searchParams, PageRequest pageRequest,String pk_corp) throws BusinessException{
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
		
		return dao.getBdCorpsBypage(condition.toString()+" and pk_corp='"+pk_corp+"'", sqlParameter, pageRequest);
	}	
	
	@Override
	public Page<BdCorpVO> getCorpsBypages(Map<String, Object> searchParams, PageRequest pageRequest) throws BusinessException{
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
		
	
	@Override
	@Transactional
	public JSONObject saveEntity(BdCorpVO entity) throws BusinessException{
		/**
		 * 添加业务逻辑
		 */
		JSONObject result = new JSONObject();
		String unitname = entity.getUnitname();
		String unitcode = entity.getUnitcode();	
		String pk =       entity.getPk_corp();
		StringBuffer sb = new StringBuffer();
		int level = 1;
		if(null==entity.getFathercorp() || "".equals(entity.getFathercorp())){
			level = 1;
		}else{
			BdCorpVO fvo = dao.getBdCorp(entity.getFathercorp());
			if(null==fvo){
				result.put("flag", AppTools.FAILED);
				result.put("msg", "上级组织不存在！");
				return result;				
			}
			level = fvo.getCorplevel()+1;
			if(level>10){
				result.put("flag", AppTools.FAILED);
				result.put("msg", "组织级次不能大于10级！");
				return result;
			}
		}
		entity.setCorplevel(level);	
		String wherestr = "";
		if(null!=pk && pk.length()>0){ // 
			wherestr = " and (unitname ='"+unitname+"' or unitcode='"+unitcode+"') and pk_corp<>'"+pk+"'";
		}else{
			wherestr = " and (unitname ='"+unitname+"' or unitcode='"+unitcode+"') ";
		}
		List<BdCorpVO> listvo = dao.getBdCorps(wherestr);
		for(BdCorpVO pvo:listvo){
			String message = "";
			if(unitcode.equals(pvo.getUnitcode().trim())){
				message+="编码重复！";
			}
			if(unitname.equals(pvo.getUnitname().trim())){
				message+="名称重复！";
			}			
			if(null!=message && message.length()>0){
				result.put("flag", AppTools.FAILED);
				result.put("msg", message);
				return result;				
			}

		}		
		if(null==pk || "".equals(pk)){
			String pk_corp = AppTools.generatePK();
			entity.setPk_corp(pk_corp);
		}
		dao.saveBdCorpWithPK(entity);
		result.put("flag", AppTools.SUCCESS);
		result.put("msg", "success");
		result.put("pk_corp", entity.getPk_corp());
		return result;		
	}
	
	@Override
	@Transactional
	public JSONObject saveEntityForNormal(BdCorpVO entity) throws BusinessException{
		/**
		 * 添加业务逻辑
		 */
		JSONObject result = new JSONObject();
		String unitname = entity.getUnitname();
		String unitcode = entity.getUnitcode();		
		String pk = entity.getPk_corp();
		int level = 1;
		if(null==entity.getFathercorp() || "".equals(entity.getFathercorp())){
			level = 1;
		}else{
			BdCorpVO fvo = dao.getBdCorp(entity.getFathercorp());
			level = fvo.getCorplevel()+1;
			if(level>10){
				result.put("flag", AppTools.FAILED);
				result.put("msg", "公司级次不能大于10级！");
				return result;
			}
		}
		entity.setCorplevel(level);	
		if(null==pk || "".equals(pk)){
			// 查询一下是否存在，重名，重编码
			List<BdCorpVO> listvo = dao.getBdCorps(" and (unitname ='"+unitname+"' or unitcode='"+unitcode+"')");
			for(BdCorpVO pvo:listvo){
				String message = "";
				if(unitcode.equals(pvo.getUnitcode().trim())){
					message+="编码重复！";
				}
				if(unitname.equals(pvo.getUnitname().trim())){
					message+="名称重复！";
				}			
				if(null!=message && message.length()>0){
					result.put("flag", AppTools.FAILED);
					result.put("msg", message);
					return result;				
				}
			}
			pk = AppTools.generatePK();
			entity.setPk_corp(pk);
			dao.saveBdCorpWithPK(entity);		
		}else{
			List<BdCorpVO> listvo = dao.getBdCorps(" and pk_corp!='"+pk+"' and (unitname ='"+unitname+"' or unitcode='"+unitcode+"')");
			for(BdCorpVO pvo:listvo){
				String message = "";
				if(unitcode.equals(pvo.getUnitcode().trim())){
					message+="编码重复！";
				}
				if(unitname.equals(pvo.getUnitname().trim())){
					message+="名称重复！";
				}			
				if(null!=message && message.length()>0){
					result.put("flag", AppTools.FAILED);
					result.put("msg", message);
					return result;				
				}
			}	
			/**
			 * 检查如果数据库上级为空
			 * 而设置了上级，则将上级的上级清空作为根节点
			 */
			List<BdCorpVO> checkvoList = dao.getBdCorps(" and pk_corp='"+pk+"'");
			if(null != checkvoList && checkvoList.size()>0){
				//如果原上级为空，即为根组织
				if(null==checkvoList.get(0).getFathercorp() || "".equals(checkvoList.get(0).getFathercorp().toString())){
					//重新分配了根组织，需要修改跟组织的上级为空
					if(null != entity.getFathercorp() && !"".equals(entity.getFathercorp())){
						List<BdCorpVO> rootvoList = dao.getBdCorps(" and pk_corp='"+entity.getFathercorp()+"'");
						if(null != rootvoList && rootvoList.size()>0){
							BdCorpVO rootVO = rootvoList.get(0);
							rootVO.setFathercorp(null);
							rootVO.setStatus(VOStatus.UPDATED);
							dao.updateBdCorp(rootVO);
						}
					}
				}
			}
			dao.updateBdCorp(entity);	
		}
		result.put("flag", AppTools.SUCCESS);
		result.put("msg", "success");
		return result;
	}
	

	@Override
	@Transactional
	public JSONObject saveEntityForException(BdCorpVO entity) throws BusinessException{
		int level = entity.getCorplevel();
		JSONObject result = new JSONObject();
		String pk = AppTools.generatePK();
		entity.setPk_corp(pk);
		entity.setCreator("test");
		entity.setCreationtime(clock.getCurrentDate().toString());
		dao.saveBdCorpWithPK(entity);
		entity.setCreator(AppInvocationInfoProxy.getPk_User());
		if(level ==1){
			throw new RuntimeException(level+"测试");
		}
		dao.updateBdCorp(entity);
		if(level ==2){
			throw new BusinessException(level+"测试");
		}
		result.put("flag", AppTools.SUCCESS);
		result.put("msg", "success");	
		result.put("pk", pk);
		return result;
	}
	
	@Override
	@Transactional
	public void deleteBdCorp(BdCorpVO entity) throws BusinessException{
		/**
		 * 添加业务逻辑
		 */
		dao.deleteBdCorpByIdTS(entity.getPk_corp());
	}
	
	@Override
	@Transactional
	public void deleteBdCorpById(String pk_corp) throws BusinessException{
		/**
		 * 添加业务逻辑
		 */
		dao.deleteBdCorpByIdTS(pk_corp);
	}
	
	@Override
	public BdCorpVO getBdCorp(String pk_corp) throws BusinessException{
		return dao.getBdCorp(pk_corp);
	}
	
	// 返回所有组织
	@Override
	public List<BdCorpVO> getBdCorps() throws BusinessException{
		return dao.getBdCorps(""); 
	}
	
	// 返回组织
	@Override
	public List<HashMap> getBdCorpsListMap(String wherestr) throws BusinessException{
		return dao.getBdCorpsListMap(wherestr); 
	}	
	
	@Override
	public String queryDeptByCorp(String pk_corp) throws BusinessException{
			return dao.queryDeptByCorp(pk_corp);
	}
	
	
	@Override
	@Transactional
	public String stopCorp(String pk_corp) throws BusinessException{
		/**
		 * 添加业务逻辑
		 */
		dao.stopCorp(pk_corp);
		return "success";
	}	
	// 返回组织机构的下级，根据条件
	@Override
	public List<BdCorpVO> getBdSubCorps(String wherestr) throws BusinessException{
		return dao.getBdCorps(wherestr);
	}	
	
	@Override
	@Transactional
	public String startCorp(String pk_corp,String def1) throws BusinessException{
		/**
		 * 添加业务逻辑
		 */
		dao.startCorp(pk_corp,def1);
		return "success";
	}	
}
