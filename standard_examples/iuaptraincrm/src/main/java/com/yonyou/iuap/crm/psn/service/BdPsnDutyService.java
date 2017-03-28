package com.yonyou.iuap.crm.psn.service;

import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springside.modules.utils.Clock;

import com.yonyou.iuap.persistence.jdbc.framework.SQLParameter;
import com.yonyou.iuap.persistence.vo.pub.BusinessException;
import com.yonyou.iuap.crm.common.utils.AppTools;
import com.yonyou.iuap.crm.psn.entity.BdPsnDutyVO;
import com.yonyou.iuap.crm.psn.repository.itf.IBdPsnDutyDao;

@Service
public class BdPsnDutyService {
	@Autowired
	private IBdPsnDutyDao dao;
	
	private Clock clock = Clock.DEFAULT;

	public void setClock(Clock clock) {
		this.clock = clock;
	}

	/**
	 * 
	* TODO 保存人员任职信息，为修改保存和新增保存
	* @author 
	* @date 2016年12月8日
	* @param entity
	* @return
	* @throws BusinessException
	 */
	@Transactional
	public String saveEntity(BdPsnDutyVO entity)
			throws BusinessException {
		String pk = entity.getPk_psnduty();
		if (null == pk || "".equals(pk)) {
			pk = AppTools.generatePK();
			entity.setPk_psnduty(pk);
			dao.savePsnDuty(entity);
			return "保存成功";
		} else {
			dao.updatePsnDuty(entity);
			return "修改成功";
		}
	}

	/**
	 * 
	* TODO 删除人员任职信息记录
	* @author 
	* @date 2016年12月8日
	* @param pk_psnduty
	* @throws BusinessException
	 */
	@Transactional
	public void deletePsnDutyById(String pk_psnduty) throws BusinessException {
		dao.deletePsnDutyById(pk_psnduty);
	}

	/**
	 * 
	* TODO 分页查询pk_psndoc人员任职信息
	* @author 
	* @date 2016年12月8日
	* @param searchParams
	* @param pageRequest
	* @param pk_psndoc
	* @return
	* @throws BusinessException
	 */
	public Page<BdPsnDutyVO> getBterDeptsBypage(
			Map<String, Object> searchParams, PageRequest pageRequest,
			String pk_psndoc) throws BusinessException {
		StringBuffer condition = new StringBuffer();
		SQLParameter sqlParameter = new SQLParameter();
		for (Map.Entry<String, Object> entry : searchParams.entrySet()) {
			String[] keySplit = entry.getKey().split("_");
			if (keySplit.length == 2) {
				String columnName = keySplit[1];
				String comparator = keySplit[0];
				Object value = entry.getValue();
				if (value != null && StringUtils.isNotBlank(value.toString())) {
					condition.append(" and ").append(columnName).append(" ")
							.append(comparator).append("?");
					value = "like".equalsIgnoreCase(comparator) ? value + "%"
							: value;
					sqlParameter.addParam(value);
				}
			}
		}
		return dao.getPsnDutysBypage(condition.toString(), sqlParameter,
				pageRequest, pk_psndoc);
	}
}
