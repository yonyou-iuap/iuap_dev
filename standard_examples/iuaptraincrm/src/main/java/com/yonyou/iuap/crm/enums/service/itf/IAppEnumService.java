package com.yonyou.iuap.crm.enums.service.itf;

import java.util.List;

import com.yonyou.iuap.persistence.bs.dao.DAOException;
import com.yonyou.iuap.crm.enums.entity.AppEnumVO;

public interface IAppEnumService {

	public abstract List<AppEnumVO> queryByVtype(String vtype) throws DAOException;
	
	public abstract AppEnumVO getEnum(String code) throws DAOException;
	
}
