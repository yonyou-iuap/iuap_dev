package com.yonyou.iuap.crm.psn.repository.itf;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import com.yonyou.iuap.persistence.bs.dao.DAOException;
import com.yonyou.iuap.persistence.jdbc.framework.SQLParameter;
import com.yonyou.iuap.crm.psn.entity.BdPsnDutyVO;

public interface IBdPsnDutyDao {

	/* 修改保存 */
	public void updatePsnDuty(BdPsnDutyVO entity) throws DAOException;

	/* 新增保存 */
	public String savePsnDuty(BdPsnDutyVO entity) throws DAOException;

	/* 查询带分页 */
	public Page<BdPsnDutyVO> getPsnDutysBypage(String condition,
			SQLParameter arg1, PageRequest pageRequest, String pk_psndoc)
			throws DAOException;

	/* 删除 */
	public void deletePsnDutyById(String pk_psnduty) throws DAOException;

}
